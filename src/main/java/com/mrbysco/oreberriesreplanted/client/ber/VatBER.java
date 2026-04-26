package com.mrbysco.oreberriesreplanted.client.ber;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mrbysco.oreberriesreplanted.blockentity.VatBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.renderer.block.FluidStateModelSet;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.fluid.FluidTintSource;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.item.ItemResource;
import org.jspecify.annotations.Nullable;

import java.util.Random;

public class VatBER implements BlockEntityRenderer<VatBlockEntity, VatRenderState> {
	private final ItemModelResolver itemModelResolver;

	public VatBER(BlockEntityRendererProvider.Context context) {
		this.itemModelResolver = context.itemModelResolver();
	}

	@Override
	public VatRenderState createRenderState() {
		return new VatRenderState();
	}

	@Override
	public void extractRenderState(VatBlockEntity blockEntity, VatRenderState renderState, float partialTick, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
		BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPosition, breakProgress);
		renderState.tankCapacity = blockEntity.tank.getCapacityAsInt(0, FluidResource.EMPTY);
		if (blockEntity.tank.getAmountAsInt(0) > 0) {
			renderState.fluidStack = blockEntity.tank.getResource(0).toStack(blockEntity.tank.getAmountAsInt(0));
		} else {
			renderState.fluidStack = FluidStack.EMPTY;
		}

		ItemResource berryResource = blockEntity.handler.getResource(0);
		if (berryResource.isEmpty()) {
			renderState.berryStack = null;
			renderState.berryAmount = 0;
		} else {
			ItemStack berryStack = berryResource.toStack(blockEntity.handler.getAmountAsInt(0));
			ItemStackRenderState itemstackrenderstate = new ItemStackRenderState();
			this.itemModelResolver.updateForTopItem(itemstackrenderstate, berryStack, ItemDisplayContext.ON_SHELF, blockEntity.getLevel(), null, 0);
			renderState.berryStack = itemstackrenderstate;
			renderState.berryAmount = berryStack.getCount();
		}
	}

	@Override
	public void submit(VatRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
		final FluidStack fluidStack = renderState.fluidStack;
		final int packedLight = renderState.lightCoords;
		if (!fluidStack.isEmpty()) {
			Fluid fluid = fluidStack.getFluid();
			TextureAtlasSprite fluidTexture = getFluidStillSprite(fluid);
			if (fluidTexture == null) return;

			poseStack.pushPose();
			poseStack.translate(0.5, 0.25, 0.5);

			final int color = getTintColor(fluid);
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
			float percent = fluidStack.getAmount() >= 200 ? (fluidStack.getAmount() / (float) renderState.tankCapacity) : 0.1f;

			nodeCollector.submitCustomGeometry(poseStack, RenderTypes.translucentMovingBlock(), (pose, vertexConsumer) -> {
				vertexConsumer.addVertex(pose, -width / 2, -height / 2 + percent * height, -width / 2).setColor(r, g, b, a)
						.setUv(minU, minV)
						.setOverlay(OverlayTexture.NO_OVERLAY)
						.setLight(packedLight)
						.setNormal(pose, 0, 1, 0);

				vertexConsumer.addVertex(pose, -width / 2, -height / 2 + percent * height, width / 2).setColor(r, g, b, a)
						.setUv(minU, maxV)
						.setOverlay(OverlayTexture.NO_OVERLAY)
						.setLight(packedLight)
						.setNormal(pose, 0, 1, 0);

				vertexConsumer.addVertex(pose, width / 2, -height / 2 + percent * height, width / 2).setColor(r, g, b, a)
						.setUv(maxU, maxV)
						.setOverlay(OverlayTexture.NO_OVERLAY)
						.setLight(packedLight)
						.setNormal(pose, 0, 1, 0);

				vertexConsumer.addVertex(pose, width / 2, -height / 2 + percent * height, -width / 2).setColor(r, g, b, a)
						.setUv(maxU, minV)
						.setOverlay(OverlayTexture.NO_OVERLAY)
						.setLight(packedLight)
						.setNormal(pose, 0, 1, 0);
			});

			poseStack.popPose();
		}
		final ItemStackRenderState berryStack = renderState.berryStack;
		final int count = renderState.berryAmount;
		if (count > 0) {
			int size = count >= 4 ? (count / 4) : 1;
			for (int i = 0; i < size; i++) {
				Random random = new Random(0);
				poseStack.pushPose();
				poseStack.translate(0.5, 1.0, 0.5);
				poseStack.translate(0, -0.9, -0.1875);
				poseStack.translate(0, i * 0.0625, 0.125);
				poseStack.mulPose(Axis.XP.rotationDegrees((float) 90));
				poseStack.translate(i * 0.0125, i * 0.0125, 0);
				poseStack.mulPose(Axis.ZP.rotationDegrees(i * random.nextInt(360)));
				berryStack.submit(poseStack, nodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
				poseStack.popPose();
			}
		}
	}

	@Nullable
	private TextureAtlasSprite getFluidStillSprite(Fluid fluid) {
		if (fluid == Fluids.EMPTY) return null;
		return getFluidModel(fluid).stillMaterial().sprite();
	}

	public FluidModel getFluidModel(Fluid fluid) {
		Minecraft minecraft = Minecraft.getInstance();
		ModelManager modelManager = minecraft.getModelManager();
		FluidStateModelSet fluidStateModelSet = modelManager.getFluidStateModelSet();
		return fluidStateModelSet.get(fluid.defaultFluidState());
	}

	public int getTintColor(Fluid fluid) {
		FluidModel fluidModel = getFluidModel(fluid);
		FluidTintSource tintSource = fluidModel.fluidTintSource();
		if (tintSource == null) {
			return 0xFFFFFFFF;
		}
		return tintSource.colorAsStack(new FluidStack(fluid, 1000));
	}
}
