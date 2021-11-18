package com.mrbysco.oreberriesreplanted.client.tesr;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mrbysco.oreberriesreplanted.tile.VatTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;

import java.util.Random;

public class VatTESR extends TileEntityRenderer<VatTile> {
	public VatTESR(TileEntityRendererDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	public void render(VatTile vatTile, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int combinedLightIn, int combinedOverlayIn) {
		FluidStack fluidStack = vatTile.tank.getFluidInTank(0);
		if(!fluidStack.isEmpty()) {
			Fluid fluid = fluidStack.getFluid();
			FluidAttributes fluidAttributes = fluid.getAttributes();
			TextureAtlasSprite fluidTexture = getFluidStillSprite(fluidAttributes, fluidStack);

			matrixStack.pushPose();
			matrixStack.translate(0.5, 0.25, 0.5);
			MatrixStack.Entry matrixLast = matrixStack.last();
			Matrix4f pose = matrixLast.pose();
			Matrix3f normal = matrixLast.normal();
			IVertexBuilder builder = renderTypeBuffer.getBuffer(RenderType.translucent());

			final int color = fluidStack.getFluid().getAttributes().getColor(fluidStack);
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
			float percent = fluidStack.getAmount() >= 200 ? (fluidStack.getAmount() / (float)vatTile.tank.getCapacity()) : 0.1f;

			builder.vertex(pose, -width / 2, -height / 2 + percent * height, -width / 2).color(r, g, b, a)
					.uv(minU, minV)
					.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(combinedLightIn).normal(normal, 0, 1, 0)
					.endVertex();

			builder.vertex(pose, -width / 2, -height / 2 + percent * height, width / 2).color(r, g, b, a)
					.uv(minU, maxV)
					.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(combinedLightIn).normal(normal, 0, 1, 0)
					.endVertex();

			builder.vertex(pose, width / 2, -height / 2 + percent * height, width / 2).color(r, g, b, a)
					.uv(maxU, maxV)
					.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(combinedLightIn).normal(normal, 0, 1, 0)
					.endVertex();

			builder.vertex(pose, width / 2, -height / 2 + percent * height, -width / 2).color(r, g, b, a)
					.uv(maxU, minV)
					.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(combinedLightIn).normal(normal, 0, 1, 0)
					.endVertex();

			if (renderTypeBuffer instanceof IRenderTypeBuffer.Impl) {
				((IRenderTypeBuffer.Impl) renderTypeBuffer).endBatch();
			}
			matrixStack.popPose();
		}
		ItemStack berryStack = vatTile.handler.getStackInSlot(0);
		int count = berryStack.getCount();
		if(!berryStack.isEmpty()) {
			int size = count >= 4 ? (count / 4) : 1;
			for(int i = 0; i < size; i++) {
				Random random = new Random(0);
				matrixStack.pushPose();
				matrixStack.translate(0.5, 1.0, 0.5);
				matrixStack.translate(0, -0.9, -0.1875);
				matrixStack.translate(0, i * 0.03125, 0.125);
				matrixStack.mulPose(Vector3f.XP.rotationDegrees((float) 90));
				matrixStack.translate(i * 0.0125, i * 0.0125, 0);
				matrixStack.mulPose(Vector3f.ZP.rotationDegrees(i * random.nextInt(360)));
				Minecraft.getInstance().getItemRenderer().renderStatic(berryStack, TransformType.GROUND, combinedLightIn, combinedOverlayIn, matrixStack, renderTypeBuffer);
				matrixStack.popPose();
			}
		}
	}

	private TextureAtlasSprite getFluidStillSprite(FluidAttributes attributes, FluidStack fluidStack) {
		return Minecraft.getInstance()
				.getTextureAtlas(PlayerContainer.BLOCK_ATLAS)
				.apply(attributes.getStillTexture(fluidStack));
	}

}
