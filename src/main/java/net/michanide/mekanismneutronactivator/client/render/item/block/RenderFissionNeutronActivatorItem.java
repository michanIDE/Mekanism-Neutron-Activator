package net.michanide.mekanismneutronactivator.client.render.item.block;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import mekanism.client.render.item.MekanismISTER;
import net.michanide.mekanismneutronactivator.client.model.ModelFissionNeutronActivator;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemStack;

public class RenderFissionNeutronActivatorItem extends MekanismISTER{

    public static final RenderFissionNeutronActivatorItem RENDERER = new RenderFissionNeutronActivatorItem();
    private ModelFissionNeutronActivator fissionNeutronActivator;

    @Override
    public void onResourceManagerReload(@Nonnull ResourceManager resourceManager) {
        fissionNeutronActivator = new ModelFissionNeutronActivator(getEntityModels());
    }

    @Override
    public void renderByItem(@Nonnull ItemStack stack, @Nonnull TransformType transformType, @Nonnull PoseStack matrix, @Nonnull MultiBufferSource renderer, int light, int overlayLight) {
        matrix.pushPose();
        matrix.translate(0.5, 0.5, 0.5);
        matrix.mulPose(Vector3f.ZP.rotationDegrees(180));
        matrix.translate(0, -0.55, 0);
        fissionNeutronActivator.render(matrix, renderer, light, overlayLight, stack.hasFoil());
        matrix.popPose();
    }
}
