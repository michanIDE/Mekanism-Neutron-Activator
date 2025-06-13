package net.michanide.mekanismneutronactivator.common.registries;

import mekanism.common.block.prefab.BlockTile.BlockTileModel;
import mekanism.common.item.block.machine.ItemBlockMachine;
import mekanism.common.registration.impl.BlockDeferredRegister;
import mekanism.common.registration.impl.BlockRegistryObject;
import net.michanide.mekanismneutronactivator.common.MekanismNeutronActivator;
import net.michanide.mekanismneutronactivator.common.content.blocktype.MNAMachine;
import net.michanide.mekanismneutronactivator.common.tile.machine.TileEntityFissionNeutronActivator;
import net.michanide.mekanismneutronactivator.common.tile.machine.TileEntityFusionNeutronActivator;
import net.minecraft.world.level.material.MapColor;

public class MNABlocks {

    private MNABlocks(){

    }

    public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(MekanismNeutronActivator.MOD_ID);

    public static final BlockRegistryObject<BlockTileModel<TileEntityFusionNeutronActivator, MNAMachine<TileEntityFusionNeutronActivator>>, ItemBlockMachine> FUSION_NEUTRON_ACTIVATOR = BLOCKS.register("fusion_neutron_activator", () -> new BlockTileModel<>(MNABlockTypes.FUSION_NEUTRON_ACTIVATOR, properties -> properties.mapColor(MapColor.COLOR_BLUE)), ItemBlockMachine::new);
    public static final BlockRegistryObject<BlockTileModel<TileEntityFissionNeutronActivator, MNAMachine<TileEntityFissionNeutronActivator>>, ItemBlockMachine> FISSION_NEUTRON_ACTIVATOR = BLOCKS.register("fission_neutron_activator", () -> new BlockTileModel<>(MNABlockTypes.FISSION_NEUTRON_ACTIVATOR, properties -> properties.mapColor(MapColor.COLOR_BLUE)), ItemBlockMachine::new);
    
}
