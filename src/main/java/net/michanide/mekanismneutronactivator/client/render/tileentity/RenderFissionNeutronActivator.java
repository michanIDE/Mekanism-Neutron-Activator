package net.michanide.mekanismneutronactivator.client.render.tileentity;

import javax.annotation.ParametersAreNonnullByDefault;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;

import mekanism.client.render.MekanismRenderer;
import mekanism.client.render.tileentity.IWireFrameRenderer;
import mekanism.client.render.tileentity.MekanismTileEntityRenderer;
import net.michanide.mekanismneutronactivator.common.base.ProfilerConstants;
import net.michanide.mekanismneutronactivator.common.tile.machine.TileEntityFissionNeutronActivator;
import net.michanide.mekanismneutronactivator.client.model.ModelFissionNeutronActivator;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.block.entity.BlockEntity;

@ParametersAreNonnullByDefault
public class RenderFissionNeutronActivator extends MekanismTileEntityRenderer<TileEntityFissionNeutronActivator> implements IWireFrameRenderer {

    private final ModelFissionNeutronActivator model;

    public RenderFissionNeutronActivator(BlockEntityRendererProvider.Context context) {
        super(context);
        model = new ModelFissionNeutronActivator(context.getModelSet());
    }

    @Override
    protected void render(TileEntityFissionNeutronActivator tile, float partialTick, PoseStack matrix, MultiBufferSource renderer, int light, int overlayLight,
        ProfilerFiller profiler) {
        performTranslations(tile, matrix);
        model.render(matrix, renderer, light, overlayLight, false);
        matrix.popPose();
    }

    @Override
    protected String getProfilerSection() {
        return ProfilerConstants.FISSION_NEUTRON_ACTIVATOR;
    }

    @Override
    public boolean shouldRenderOffScreen(TileEntityFissionNeutronActivator tile) {
        return true;
    }

    @SuppressWarnings("null")
    @Override
    public void renderWireFrame(BlockEntity tile, float partialTick, PoseStack matrix, VertexConsumer buffer, float red, float green, float blue, float alpha) {
        if (tile instanceof TileEntityFissionNeutronActivator fna) {
            performTranslations(fna, matrix);
            model.renderWireFrame(matrix, buffer, red, green, blue, alpha);
            matrix.popPose();
        }
    }

    /**
     * Make sure to call {@link PoseStack#popPose()} afterwards
     */
    private void performTranslations(TileEntityFissionNeutronActivator tile, PoseStack matrix) {
        matrix.pushPose();
        matrix.translate(0.5, 1.5, 0.5);
        MekanismRenderer.rotate(matrix, tile.getDirection(), 0, 180, 90, 270);
        matrix.mulPose(Vector3f.ZP.rotationDegrees(180));
    }
}
