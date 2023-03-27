package com.mrbysco.oreberriesreplanted.client.ber;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.mrbysco.oreberriesreplanted.blockentity.VatBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.Random;

public class VatBER implements BlockEntityRenderer<VatBlockEntity> {
	public VatBER(BlockEntityRendererProvider.Context context) {
	}

	@Override
	public void render(VatBlockEntity vat, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLightIn, int combinedOverlayIn) {
		FluidStack fluidStack = vat.tank.getFluidInTank(0);
		if (!fluidStack.isEmpty()) {
			Fluid fluid = fluidStack.getFluid();
			TextureAtlasSprite fluidTexture = getFluidStillSprite(fluid);

			poseStack.pushPose();
			poseStack.translate(0.5, 0.25, 0.5);
			PoseStack.Pose matrixLast = poseStack.last();
			Matrix4f pose = matrixLast.pose();
			Matrix3f normal = matrixLast.normal();
			VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.translucent());

			final int color = IClientFluidTypeExtensions.of(fluid).getTintColor(fluidStack);
			float r = ((color >> 16) & 0xFF) / 255f;
			float g = ((color >> 8) & 0xFF) / 255f;
			float b = ((color) & 0xFF) / 255f;
			float a = 0.875f;

			float width = 14 / 16f;
			float height = 7 / 16f;

			float minU = fluidTexture.getU(3);
			float maxU = fluidTexture.getU(13);
			float minV = fluidTexture.getV(3);
			float maxV = fluidTexture.getV(13);
			float percent = fluidStack.getAmount() >= 200 ? (fluidStack.getAmount() / (float) vat.tank.getCapacity()) : 0.1f;

			vertexConsumer.vertex(pose, -width / 2, -height / 2 + percent * height, -width / 2).color(r, g, b, a)
					.uv(minU, minV)
					.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(combinedLightIn).normal(normal, 0, 1, 0)
					.endVertex();

			vertexConsumer.vertex(pose, -width / 2, -height / 2 + percent * height, width / 2).color(r, g, b, a)
					.uv(minU, maxV)
					.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(combinedLightIn).normal(normal, 0, 1, 0)
					.endVertex();

			vertexConsumer.vertex(pose, width / 2, -height / 2 + percent * height, width / 2).color(r, g, b, a)
					.uv(maxU, maxV)
					.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(combinedLightIn).normal(normal, 0, 1, 0)
					.endVertex();

			vertexConsumer.vertex(pose, width / 2, -height / 2 + percent * height, -width / 2).color(r, g, b, a)
					.uv(maxU, minV)
					.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(combinedLightIn).normal(normal, 0, 1, 0)
					.endVertex();

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
				Minecraft.getInstance().getItemRenderer().renderStatic(berryStack, TransformType.GROUND, combinedLightIn, combinedOverlayIn, poseStack, bufferSource, 0);
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
