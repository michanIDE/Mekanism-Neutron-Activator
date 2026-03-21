package net.michanide.mekanismneutronactivator.common.registries;

import mekanism.common.block.attribute.AttributeCustomSelectionBox;
import mekanism.common.block.attribute.AttributeHasBounding;
import mekanism.common.block.attribute.AttributeParticleFX;
import mekanism.common.block.attribute.AttributeUpgradeSupport;
import mekanism.common.block.attribute.Attributes;
import mekanism.common.lib.transmitter.TransmissionType;
import net.michanide.mekanismneutronactivator.common.MNALang;
import net.michanide.mekanismneutronactivator.common.content.blocktype.BlockShapes;
import net.michanide.mekanismneutronactivator.common.content.blocktype.MNAMachine;
import net.michanide.mekanismneutronactivator.common.content.blocktype.MNAMachine.MNAMachineBuilder;
import net.michanide.mekanismneutronactivator.common.tile.machine.TileEntityFissionNeutronActivator;
import net.michanide.mekanismneutronactivator.common.tile.machine.TileEntityFusionNeutronActivator;

public class MNABlockTypes {

    private MNABlockTypes(){
    }

    public static final MNAMachine<TileEntityFusionNeutronActivator> FUSION_NEUTRON_ACTIVATOR = MNAMachineBuilder
        .createMNAMachine(() -> MNATileEntityTypes.FUSION_NEUTRON_ACTIVATOR, MNALang.DESCRIPTION_FUSION_NEUTRON_ACTIVATOR)
        .withGui(() -> MNAContainerTypes.FUSION_NEUTRON_ACTIVATOR)
        .without(AttributeParticleFX.class, AttributeUpgradeSupport.class)
        .withCustomShape(BlockShapes.FUSION_NEUTRON_ACTIVATOR)
        .with(AttributeCustomSelectionBox.JSON)
        .withSideConfig(TransmissionType.CHEMICAL, TransmissionType.ITEM)
        .with(AttributeHasBounding.ABOVE_ONLY)
        .withComputerSupport("fusionNeutronActivator")
        .replace(Attributes.ACTIVE)
        .build();

    public static final MNAMachine<TileEntityFissionNeutronActivator> FISSION_NEUTRON_ACTIVATOR = MNAMachineBuilder
        .createMNAMachine(() -> MNATileEntityTypes.FISSION_NEUTRON_ACTIVATOR, MNALang.DESCRIPTION_FISSION_NEUTRON_ACTIVATOR)
        .withGui(() -> MNAContainerTypes.FISSION_NEUTRON_ACTIVATOR)
        .without(AttributeParticleFX.class, AttributeUpgradeSupport.class)
        .withCustomShape(BlockShapes.FISSION_NEUTRON_ACTIVATOR)
        .with(AttributeCustomSelectionBox.JSON)
        .withSideConfig(TransmissionType.CHEMICAL, TransmissionType.ITEM)
        .with(AttributeHasBounding.ABOVE_ONLY)
        .withComputerSupport("fissionNeutronActivator")
        .replace(Attributes.ACTIVE)
        .build();

}
