package net.michanide.mekanismneutronactivator.common.registries;

import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.registration.impl.ContainerTypeDeferredRegister;
import mekanism.common.registration.impl.ContainerTypeRegistryObject;
import net.michanide.mekanismneutronactivator.common.MekanismNeutronActivator;
import net.michanide.mekanismneutronactivator.common.tile.machine.TileEntityFissionNeutronActivator;
import net.michanide.mekanismneutronactivator.common.tile.machine.TileEntityFusionNeutronActivator;

public class MNAContainerTypes {
    private MNAContainerTypes(){
    }

    public static final ContainerTypeDeferredRegister CONTAINER_TYPES = new ContainerTypeDeferredRegister(MekanismNeutronActivator.MOD_ID);

    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityFusionNeutronActivator>> FUSION_NEUTRON_ACTIVATOR = CONTAINER_TYPES.register(MNABlocks.FUSION_NEUTRON_ACTIVATOR, TileEntityFusionNeutronActivator.class);
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityFissionNeutronActivator>> FISSION_NEUTRON_ACTIVATOR = CONTAINER_TYPES.register(MNABlocks.FISSION_NEUTRON_ACTIVATOR, TileEntityFissionNeutronActivator.class);
}
