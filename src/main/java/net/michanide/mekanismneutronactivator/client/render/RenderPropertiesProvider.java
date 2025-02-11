package net.michanide.mekanismneutronactivator.client.render;

import mekanism.client.render.RenderPropertiesProvider.MekRenderProperties;
import net.michanide.mekanismneutronactivator.client.render.item.block.RenderFissionNeutronActivatorItem;
import net.michanide.mekanismneutronactivator.client.render.item.block.RenderFusionNeutronActivatorItem;
import net.minecraftforge.client.IItemRenderProperties;

public class RenderPropertiesProvider {
    private RenderPropertiesProvider(){
    }

    public static IItemRenderProperties fusionActivator() {
        return new MekRenderProperties(RenderFusionNeutronActivatorItem.RENDERER);
    }
    public static IItemRenderProperties fissionActivator() {
        return new MekRenderProperties(RenderFissionNeutronActivatorItem.RENDERER);
    }
}
