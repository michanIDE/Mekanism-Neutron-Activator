package net.michanide.mekanismneutronactivator.common.registries;

import java.util.function.Predicate;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.common.attachments.component.AttachedEjector;
import mekanism.common.attachments.component.AttachedSideConfig;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.attachments.containers.ContainsRecipe;
import mekanism.common.attachments.containers.chemical.ChemicalTanksBuilder;
import mekanism.common.attachments.containers.item.ItemSlotsBuilder;
import mekanism.common.block.prefab.BlockTile.BlockTileModel;
import mekanism.common.item.block.ItemBlockTooltip;
import mekanism.common.recipe.IMekanismRecipeTypeProvider;
import mekanism.common.recipe.MekanismRecipeType;
import mekanism.common.recipe.lookup.cache.IInputRecipeCache;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.SingleChemical;
import mekanism.common.registration.impl.BlockDeferredRegister;
import mekanism.common.registration.impl.BlockRegistryObject;
import mekanism.common.registries.MekanismDataComponents;
import net.michanide.mekanismneutronactivator.common.MekanismNeutronActivator;
import net.michanide.mekanismneutronactivator.common.content.blocktype.MNAMachine;
import net.michanide.mekanismneutronactivator.common.tile.machine.TileEntityFissionNeutronActivator;
import net.michanide.mekanismneutronactivator.common.tile.machine.TileEntityFusionNeutronActivator;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.MapColor;

public class MNABlocks {

    private MNABlocks(){

    }

    public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(MekanismNeutronActivator.MOD_ID);

    public static final BlockRegistryObject<BlockTileModel<TileEntityFusionNeutronActivator, MNAMachine<TileEntityFusionNeutronActivator>>, ItemBlockTooltip<BlockTileModel<TileEntityFusionNeutronActivator, MNAMachine<TileEntityFusionNeutronActivator>>>> FUSION_NEUTRON_ACTIVATOR =
		BLOCKS.register("fusion_neutron_activator", () -> new BlockTileModel<>(MNABlockTypes.FUSION_NEUTRON_ACTIVATOR, properties -> properties.mapColor(MapColor.COLOR_BLUE)),
			(block, properties) -> new ItemBlockTooltip<>(block, true, properties
					.component(MekanismDataComponents.EJECTOR, AttachedEjector.DEFAULT)
					.component(MekanismDataComponents.SIDE_CONFIG, AttachedSideConfig.SNA)
			)
		).forItemHolder(holder -> holder
			.addAttachmentOnlyContainers(ContainerType.CHEMICAL, () -> ChemicalTanksBuilder.builder()
					.addBasic(TileEntityFusionNeutronActivator.MAX_GAS_CONF, isValidInput(MekanismRecipeType.ACTIVATING, SingleChemical::containsInput))
					.addBasic(TileEntityFusionNeutronActivator.MAX_GAS_CONF)
					.build()
			).addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
					.addChemicalFillSlot(0)
					.addChemicalDrainSlot(1)
					.build()
			)
		);

    public static final BlockRegistryObject<BlockTileModel<TileEntityFissionNeutronActivator, MNAMachine<TileEntityFissionNeutronActivator>>, ItemBlockTooltip<BlockTileModel<TileEntityFissionNeutronActivator, MNAMachine<TileEntityFissionNeutronActivator>>>> FISSION_NEUTRON_ACTIVATOR =
		BLOCKS.register("fission_neutron_activator", () -> new BlockTileModel<>(MNABlockTypes.FISSION_NEUTRON_ACTIVATOR, properties -> properties.mapColor(MapColor.COLOR_BLUE)),
			(block, properties) -> new ItemBlockTooltip<>(block, true, properties
					.component(MekanismDataComponents.EJECTOR, AttachedEjector.DEFAULT)
					.component(MekanismDataComponents.SIDE_CONFIG, AttachedSideConfig.SNA)
			)
		).forItemHolder(holder -> holder
			.addAttachmentOnlyContainers(ContainerType.CHEMICAL, () -> ChemicalTanksBuilder.builder()
					.addBasic(TileEntityFusionNeutronActivator.MAX_GAS_CONF, isValidInput(MekanismRecipeType.ACTIVATING, SingleChemical::containsInput))
					.addBasic(TileEntityFusionNeutronActivator.MAX_GAS_CONF)
					.build()
			).addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
					.addChemicalFillSlot(0)
					.addChemicalDrainSlot(1)
					.build()
			)
		);

    private static <VANILLA_INPUT extends RecipeInput, RECIPE extends MekanismRecipe<VANILLA_INPUT>, INPUT_CACHE extends IInputRecipeCache> Predicate<ChemicalStack> isValidInput(IMekanismRecipeTypeProvider<VANILLA_INPUT, RECIPE, INPUT_CACHE> recipeType, ContainsRecipe<INPUT_CACHE, ChemicalStack> containsRecipe){
		return (chemical) -> containsRecipe.check(recipeType.getInputCache(), (Level)null, chemical);
	}  
}
