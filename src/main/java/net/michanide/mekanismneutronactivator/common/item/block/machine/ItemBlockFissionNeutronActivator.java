package net.michanide.mekanismneutronactivator.common.item.block.machine;

import javax.annotation.Nonnull;

import java.util.function.Consumer;
import mekanism.common.block.prefab.BlockTile;
import mekanism.common.item.block.machine.ItemBlockMachine;
import net.michanide.mekanismneutronactivator.client.render.RenderPropertiesProvider;
import net.michanide.mekanismneutronactivator.common.content.blocktype.MNAMachine;
import net.michanide.mekanismneutronactivator.common.tile.machine.TileEntityFissionNeutronActivator;
import net.minecraftforge.client.IItemRenderProperties;

public class ItemBlockFissionNeutronActivator extends ItemBlockMachine {

    public ItemBlockFissionNeutronActivator(BlockTile<TileEntityFissionNeutronActivator, MNAMachine<TileEntityFissionNeutronActivator>> block) {
        super(block);
    }

    @Override
    public void initializeClient(@Nonnull Consumer<IItemRenderProperties> consumer) {
        consumer.accept(RenderPropertiesProvider.fissionActivator());
    }
    
}
