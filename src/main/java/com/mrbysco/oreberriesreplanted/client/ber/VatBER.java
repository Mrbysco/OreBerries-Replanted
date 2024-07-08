package com.mrbysco.oreberriesreplanted.client.ber;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.mrbysco.oreberriesreplanted.blockentity.VatBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import org.joml.Matrix4f;

import java.util.Random;

public class VatBER implements BlockEntityRenderer<VatBlockEntity> {
	public VatBER(BlockEntityRendererProvider.Context context) {
	}

	@Override
	public void render(VatBlockEntity vat, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLightIn, int combinedOverlayIn) {
		final ClientLevel level = Minecraft.getInstance().level;
		FluidStack fluidStack = vat.tank.getFluidInTank(0);
		if (!fluidStack.isEmpty()) {
			Fluid fluid = fluidStack.getFluid();
			TextureAtlasSprite fluidTexture = getFluidStillSprite(fluid);

			poseStack.pushPose();
			poseStack.translate(0.5, 0.25, 0.5);
			PoseStack.Pose matrixLast = poseStack.last();
			Matrix4f pose = matrixLast.pose();
			VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.translucent());

			final int color = IClientFluidTypeExtensions.of(fluid).getTintColor(fluidStack);
			float r = ((color >> 16) & 0xFF) / 255f;
			float g = ((color >> 8) & 0xFF) / 255f;
			float b = ((color) & 0xFF) / 255f;
			float a = 0.875f;

			float width = 14 / 16f;
			float height = 7 / 16f;

			float minU = fluidTexture.getU(0);
			float maxU = fluidTexture.getU(1);
			float minV = fluidTexture.getV(0);
			float maxV = fluidTexture.getV(1);
			float percent = fluidStack.getAmount() >= 200 ? (fluidStack.getAmount() / (float) vat.tank.getCapacity()) : 0.1f;

			vertexConsumer.addVertex(pose, -width / 2, -height / 2 + percent * height, -width / 2).setColor(r, g, b, a)
					.setUv(minU, minV)
					.setOverlay(OverlayTexture.NO_OVERLAY)
					.setLight(combinedLightIn)
					.setNormal(matrixLast, 0, 1, 0);

			vertexConsumer.addVertex(pose, -width / 2, -height / 2 + percent * height, width / 2).setColor(r, g, b, a)
					.setUv(minU, maxV)
					.setOverlay(OverlayTexture.NO_OVERLAY)
					.setLight(combinedLightIn)
					.setNormal(matrixLast, 0, 1, 0);

			vertexConsumer.addVertex(pose, width / 2, -height / 2 + percent * height, width / 2).setColor(r, g, b, a)
					.setUv(maxU, maxV)
					.setOverlay(OverlayTexture.NO_OVERLAY)
					.setLight(combinedLightIn)
					.setNormal(matrixLast, 0, 1, 0);

			vertexConsumer.addVertex(pose, width / 2, -height / 2 + percent * height, -width / 2).setColor(r, g, b, a)
					.setUv(maxU, minV)
					.setOverlay(OverlayTexture.NO_OVERLAY)
					.setLight(combinedLightIn)
					.setNormal(matrixLast, 0, 1, 0);

			if (bufferSource instanceof MultiBufferSource.BufferSource) {
				((MultiBufferSource.BufferSource) bufferSource).endBatch();
			}
			poseStack.popPose();
		}
		ItemStack berryStack = vat.handler.getStackInSlot(0);
		int count = berryStack.getCount();
		if (!berryStack.isEmpty()) {
			int size = count >= 4 ? (count / 4) : 1;
			for (int i = 0; i < size; i++) {
				Random random = new Random(0);
				poseStack.pushPose();
				poseStack.translate(0.5, 1.0, 0.5);
				poseStack.translate(0, -0.9, -0.1875);
				poseStack.translate(0, i * 0.03125, 0.125);
				poseStack.mulPose(Axis.XP.rotationDegrees((float) 90));
				poseStack.translate(i * 0.0125, i * 0.0125, 0);
				poseStack.mulPose(Axis.ZP.rotationDegrees(i * random.nextInt(360)));
				Minecraft.getInstance().getItemRenderer().renderStatic(berryStack, ItemDisplayContext.GROUND, combinedLightIn, combinedOverlayIn, poseStack, bufferSource, level, 0);
				poseStack.popPose();
			}
		}
	}

	private TextureAtlasSprite getFluidStillSprite(Fluid fluid) {
		return Minecraft.getInstance()
				.getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
				.apply(IClientFluidTypeExtensions.of(fluid).getStillTexture());
	}

}
