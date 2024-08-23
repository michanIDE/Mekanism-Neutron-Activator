package net.michanide.mekanismneutronactivator.client.render.item.block;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import mekanism.client.render.item.MekanismISTER;
import net.michanide.mekanismneutronactivator.client.model.ModelFusionNeutronActivator;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemStack;

public class RenderFusionNeutronActivatorItem extends MekanismISTER{

    public static final RenderFusionNeutronActivatorItem RENDERER = new RenderFusionNeutronActivatorItem();
    private ModelFusionNeutronActivator fusionNeutronActivator;

    @Override
    public void onResourceManagerReload(@Nonnull ResourceManager resourceManager) {
        fusionNeutronActivator = new ModelFusionNeutronActivator(getEntityModels());
    }

    @Override
    public void renderByItem(@Nonnull ItemStack stack, @Nonnull TransformType transformType, @Nonnull PoseStack matrix, @Nonnull MultiBufferSource renderer, int light, int overlayLight) {
        matrix.pushPose();
        matrix.translate(0.5, 0.5, 0.5);
        matrix.mulPose(Vector3f.ZP.rotationDegrees(180));
        matrix.translate(0, -0.55, 0);
        fusionNeutronActivator.render(matrix, renderer, light, overlayLight, stack.hasFoil());
        matrix.popPose();
    }
}
