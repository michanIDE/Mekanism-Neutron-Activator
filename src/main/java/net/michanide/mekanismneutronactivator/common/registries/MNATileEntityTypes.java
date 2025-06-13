package net.michanide.mekanismneutronactivator.common.registries;

import mekanism.common.registration.impl.TileEntityTypeDeferredRegister;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import mekanism.common.tile.base.TileEntityMekanism;
import net.michanide.mekanismneutronactivator.common.MekanismNeutronActivator;
import net.michanide.mekanismneutronactivator.common.tile.machine.TileEntityFissionNeutronActivator;
import net.michanide.mekanismneutronactivator.common.tile.machine.TileEntityFusionNeutronActivator;

public class MNATileEntityTypes {
    
    private MNATileEntityTypes() {
    }

    public static final TileEntityTypeDeferredRegister TILE_ENTITY_TYPES = new TileEntityTypeDeferredRegister(MekanismNeutronActivator.MOD_ID);

    public static final TileEntityTypeRegistryObject<TileEntityFusionNeutronActivator> FUSION_NEUTRON_ACTIVATOR = TILE_ENTITY_TYPES.register(MNABlocks.FUSION_NEUTRON_ACTIVATOR, TileEntityFusionNeutronActivator::new, TileEntityMekanism::tickServer, TileEntityMekanism::tickClient);
    public static final TileEntityTypeRegistryObject<TileEntityFissionNeutronActivator> FISSION_NEUTRON_ACTIVATOR = TILE_ENTITY_TYPES.register(MNABlocks.FISSION_NEUTRON_ACTIVATOR, TileEntityFissionNeutronActivator::new, TileEntityMekanism::tickServer, TileEntityMekanism::tickClient);

}
