package net.michanide.mekanismneutronactivator.common.registries;

import mekanism.common.block.prefab.BlockTile.BlockTileModel;
import mekanism.common.registration.impl.BlockDeferredRegister;
import mekanism.common.registration.impl.BlockRegistryObject;
import net.michanide.mekanismneutronactivator.common.MekanismNeutronActivator;
import net.michanide.mekanismneutronactivator.common.content.blocktype.MNAMachine;
import net.michanide.mekanismneutronactivator.common.item.block.machine.ItemBlockFissionNeutronActivator;
import net.michanide.mekanismneutronactivator.common.item.block.machine.ItemBlockFusionNeutronActivator;
import net.michanide.mekanismneutronactivator.common.tile.machine.TileEntityFissionNeutronActivator;
import net.michanide.mekanismneutronactivator.common.tile.machine.TileEntityFusionNeutronActivator;

public class MNABlocks {

    private MNABlocks(){

    }

    public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(MekanismNeutronActivator.MOD_ID);

    public static final BlockRegistryObject<BlockTileModel<TileEntityFusionNeutronActivator, MNAMachine<TileEntityFusionNeutronActivator>>, ItemBlockFusionNeutronActivator> FUSION_NEUTRON_ACTIVATOR = BLOCKS.register("fusion_neutron_activator", () -> new BlockTileModel<>(MNABlockTypes.FUSION_NEUTRON_ACTIVATOR), ItemBlockFusionNeutronActivator::new);
    public static final BlockRegistryObject<BlockTileModel<TileEntityFissionNeutronActivator, MNAMachine<TileEntityFissionNeutronActivator>>, ItemBlockFissionNeutronActivator> FISSION_NEUTRON_ACTIVATOR = BLOCKS.register("fission_neutron_activator", () -> new BlockTileModel<>(MNABlockTypes.FISSION_NEUTRON_ACTIVATOR), ItemBlockFissionNeutronActivator::new);
    
}
