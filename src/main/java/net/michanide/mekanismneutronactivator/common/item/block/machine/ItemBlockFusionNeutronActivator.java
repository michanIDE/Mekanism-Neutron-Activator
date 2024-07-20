package net.michanide.mekanismneutronactivator.common.item.block.machine;

import javax.annotation.Nonnull;

import java.util.function.Consumer;
import mekanism.client.render.RenderPropertiesProvider;
import mekanism.common.block.prefab.BlockTile;
import mekanism.common.content.blocktype.Machine;
import mekanism.common.item.block.machine.ItemBlockMachine;
import net.michanide.mekanismneutronactivator.common.content.blocktype.MNAMachine;
import net.michanide.mekanismneutronactivator.common.tile.machine.TileEntityFusionNeutronActivator;
import net.minecraftforge.client.IItemRenderProperties;

public class ItemBlockFusionNeutronActivator extends ItemBlockMachine {

    public ItemBlockFusionNeutronActivator(BlockTile<TileEntityFusionNeutronActivator, MNAMachine<TileEntityFusionNeutronActivator>> block) {
        super(block);
    }

    @Override
    public void initializeClient(@Nonnull Consumer<IItemRenderProperties> consumer) {
        consumer.accept(RenderPropertiesProvider.activator());
    }
    
}