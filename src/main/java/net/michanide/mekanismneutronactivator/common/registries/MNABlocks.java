package net.michanide.mekanismneutronactivator.common.registries;

import mekanism.common.attachments.component.AttachedEjector;
import mekanism.common.attachments.component.AttachedSideConfig;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.attachments.containers.chemical.ChemicalTanksBuilder;
import mekanism.common.attachments.containers.item.ItemSlotsBuilder;
import mekanism.common.block.prefab.BlockTile.BlockTileModel;
import mekanism.common.content.blocktype.Machine;
import mekanism.common.item.block.ItemBlockTooltip;
import mekanism.common.item.block.machine.ItemBlockMachine;
import mekanism.common.recipe.MekanismRecipeType;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.SingleChemical;
import mekanism.common.registration.impl.BlockDeferredRegister;
import mekanism.common.registration.impl.BlockRegistryObject;
import mekanism.common.registries.MekanismBlockTypes;
import mekanism.common.registries.MekanismDataComponents;
import mekanism.common.tile.machine.TileEntitySolarNeutronActivator;
import net.michanide.mekanismneutronactivator.common.MekanismNeutronActivator;
import net.michanide.mekanismneutronactivator.common.content.blocktype.MNAMachine;
import net.michanide.mekanismneutronactivator.common.tile.machine.TileEntityFissionNeutronActivator;
import net.michanide.mekanismneutronactivator.common.tile.machine.TileEntityFusionNeutronActivator;
import net.minecraft.world.level.material.MapColor;

public class MNABlocks {

    private MNABlocks(){

    }

    public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(MekanismNeutronActivator.MOD_ID);

    public static final BlockRegistryObject<BlockTileModel<TileEntityFusionNeutronActivator, Machine<TileEntityFusionNeutronActivator>>, ItemBlockTooltip<BlockTileModel<TileEntityFusionNeutronActivator, Machine<TileEntityFusionNeutronActivator>>>> FUSION_NEUTRON_ACTIVATOR =
          BLOCKS.register("fusion_neutron_activator", () -> new BlockTileModel<>(MNABlockTypes.FUSION_NEUTRON_ACTIVATOR, properties -> properties.mapColor(MapColor.COLOR_BLUE)),
                (block, properties) -> new ItemBlockTooltip<>(block, true, properties
                      .component(MekanismDataComponents.EJECTOR, AttachedEjector.DEFAULT)
                      .component(MekanismDataComponents.SIDE_CONFIG, AttachedSideConfig.SNA)
                )
          ).forItemHolder(holder -> holder
                .addAttachmentOnlyContainers(ContainerType.CHEMICAL, () -> ChemicalTanksBuilder.builder()
                      .addBasic(TileEntityFusionNeutronActivator.MAX_GAS, MekanismRecipeType.ACTIVATING, SingleChemical::containsInput)
                      .addBasic(TileEntityFusionNeutronActivator.MAX_GAS)
                      .build()
                ).addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                      .addChemicalFillSlot(0)
                      .addChemicalDrainSlot(1)
                      .build()
                )
          );

    public static final BlockRegistryObject<BlockTileModel<TileEntityFissionNeutronActivator, Machine<TileEntityFissionNeutronActivator>>, ItemBlockTooltip<BlockTileModel<TileEntityFissionNeutronActivator, Machine<TileEntityFissionNeutronActivator>>>> FISSION_NEUTRON_ACTIVATOR =
          BLOCKS.register("fission_neutron_activator", () -> new BlockTileModel<>(MNABlockTypes.FISSION_NEUTRON_ACTIVATOR, properties -> properties.mapColor(MapColor.COLOR_BLUE)),
                (block, properties) -> new ItemBlockTooltip<>(block, true, properties
                      .component(MekanismDataComponents.EJECTOR, AttachedEjector.DEFAULT)
                      .component(MekanismDataComponents.SIDE_CONFIG, AttachedSideConfig.SNA)
                )
          ).forItemHolder(holder -> holder
                .addAttachmentOnlyContainers(ContainerType.CHEMICAL, () -> ChemicalTanksBuilder.builder()
                      .addBasic(TileEntityFissionNeutronActivator.MAX_GAS, MekanismRecipeType.ACTIVATING, SingleChemical::containsInput)
                      .addBasic(TileEntityFissionNeutronActivator.MAX_GAS)
                      .build()
                ).addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                      .addChemicalFillSlot(0)
                      .addChemicalDrainSlot(1)
                      .build()
                )
          );
}
