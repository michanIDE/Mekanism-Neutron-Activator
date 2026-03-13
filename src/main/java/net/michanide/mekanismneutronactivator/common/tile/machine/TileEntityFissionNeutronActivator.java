package net.michanide.mekanismneutronactivator.common.tile.machine;

import java.util.List;

import javax.annotation.Nullable;

import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.IContentsListener;
import mekanism.api.RelativeSide;
import org.jetbrains.annotations.NotNull;

import mekanism.api.chemical.BasicChemicalTank;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.chemical.attribute.ChemicalAttributeValidator;
import mekanism.api.functions.ConstantPredicates;
import mekanism.api.recipes.ChemicalToChemicalRecipe;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.cache.OneInputCachedRecipe;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.inputs.InputHelper;
import mekanism.api.recipes.outputs.IOutputHandler;
import mekanism.api.recipes.outputs.OutputHelper;
import mekanism.api.recipes.vanilla_input.SingleChemicalRecipeInput;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mekanism.client.recipe_viewer.type.RecipeViewerRecipeType;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.capabilities.holder.chemical.ChemicalTankHelper;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import mekanism.common.capabilities.holder.slot.IInventorySlotHolder;
import mekanism.common.capabilities.holder.slot.InventorySlotHelper;
import mekanism.common.capabilities.chemical.StackedWasteBarrel;
import mekanism.common.config.MekanismConfig;
import mekanism.common.config.value.CachedDoubleValue;
import mekanism.common.config.value.CachedLongValue;
import mekanism.common.integration.computer.SpecialComputerMethodWrapper.ComputerChemicalTankWrapper;
import mekanism.common.integration.computer.SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper;
import mekanism.common.integration.computer.annotation.SyntheticComputerMethod;
import mekanism.common.integration.computer.annotation.WrappingComputerMethod;
import mekanism.common.inventory.container.slot.ContainerSlotType;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.inventory.slot.chemical.ChemicalInventorySlot;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.recipe.IMekanismRecipeTypeProvider;
import mekanism.common.recipe.MekanismRecipeType;
import mekanism.common.recipe.lookup.ISingleRecipeLookupHandler.ChemicalRecipeLookupHandler;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.SingleChemical;
import mekanism.common.registries.MekanismGases;
import mekanism.common.tile.TileEntityRadioactiveWasteBarrel;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.tile.interfaces.IBoundingBlock;
import mekanism.common.tile.prefab.TileEntityRecipeMachine;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.WorldUtils;
import net.michanide.mekanismneutronactivator.common.config.MNAConfig;
import net.michanide.mekanismneutronactivator.common.registries.MNABlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class TileEntityFissionNeutronActivator extends TileEntityRecipeMachine<ChemicalToChemicalRecipe> implements IBoundingBlock, ChemicalRecipeLookupHandler<ChemicalToChemicalRecipe> {
    
    private static final List<RecipeError> TRACKED_ERROR_TYPES = List.of(
          RecipeError.NOT_ENOUGH_INPUT,
          RecipeError.NOT_ENOUGH_OUTPUT_SPACE,
          RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT
    );
    public static final CachedLongValue MAX_GAS_CONF = MNAConfig.general.fissionNeutronActivatorMaxTankSize;
    public static final CachedLongValue OUTPUT_RATE_CONF = MNAConfig.general.fissionNeutronActivatorOutputRate;
    public static final CachedDoubleValue PRODUCTION_RATE = MNAConfig.general.fissionNeutronActivatorProductionRate;

    @WrappingComputerMethod(wrapper = ComputerChemicalTankWrapper.class, methodNames = {"getInput", "getInputCapacity", "getInputNeeded", "getInputFilledPercentage"}, docPlaceholder = "input tank")
    public IChemicalTank inputTank;
    @WrappingComputerMethod(wrapper = ComputerChemicalTankWrapper.class, methodNames = {"getOutput", "getOutputCapacity", "getOutputNeeded", "getOutputFilledPercentage"}, docPlaceholder = "output tank")
    public IChemicalTank outputTank;

    @SyntheticComputerMethod(getter = "getProductionRate")
    private float productionRate;
    private boolean settingsChecked;

    private final IOutputHandler<@NotNull ChemicalStack> outputHandler;
    private final IInputHandler<@NotNull ChemicalStack> inputHandler;

    @WrappingComputerMethod(wrapper = ComputerIInventorySlotWrapper.class, methodNames = "getInputItem", docPlaceholder = "input slot")
    private ChemicalInventorySlot inputSlot;
    @WrappingComputerMethod(wrapper = ComputerIInventorySlotWrapper.class, methodNames = "getOutputItem", docPlaceholder = "output slot")
    private ChemicalInventorySlot outputSlot;

    private int plutoniumDecayCounter;
    private long lastProcessTick = -1;

    public TileEntityFissionNeutronActivator(BlockPos pos, BlockState state) {
        super(MNABlocks.FISSION_NEUTRON_ACTIVATOR, pos, state, TRACKED_ERROR_TYPES);
        configComponent.setupIOConfig(TransmissionType.ITEM, inputSlot, outputSlot, RelativeSide.FRONT);
        configComponent.setupIOConfig(TransmissionType.CHEMICAL, inputTank, outputTank, RelativeSide.FRONT, false, true).setEjecting(true);
        configComponent.addDisabledSides(RelativeSide.TOP);

        ejectorComponent = new TileComponentEjector(this);
        ejectorComponent.setOutputData(configComponent, TransmissionType.ITEM, TransmissionType.CHEMICAL)
              .setCanTankEject(tank -> tank != inputTank);
        inputHandler = InputHelper.getInputHandler(inputTank, RecipeError.NOT_ENOUGH_INPUT);
        outputHandler = OutputHelper.getOutputHandler(outputTank, RecipeError.NOT_ENOUGH_OUTPUT_SPACE);

        plutoniumDecayCounter = 0;
    }

    @NotNull
    @Override
    public IChemicalTankHolder getInitialChemicalTanks(IContentsListener listener, IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        ChemicalTankHelper builder = ChemicalTankHelper.forSideWithConfig(this);
        //Allow extracting out of the input gas tank if it isn't external OR the output tank is empty AND the input is radioactive
        builder.addTank(inputTank = BasicChemicalTank.createModern(MAX_GAS_CONF.get(), ChemicalTankHelper.radioactiveInputTankPredicate(() -> outputTank),
              ConstantPredicates.alwaysTrueBi(), this::containsRecipe, ChemicalAttributeValidator.ALWAYS_ALLOW, recipeCacheListener));
        builder.addTank(outputTank = BasicChemicalTank.output(MAX_GAS_CONF.get(), recipeCacheUnpauseListener));
        return builder.build();
    }

    @NotNull
    @Override
    protected IInventorySlotHolder getInitialInventory(IContentsListener listener, IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        InventorySlotHelper builder = InventorySlotHelper.forSideWithConfig(this);
        builder.addSlot(inputSlot = ChemicalInventorySlot.fill(inputTank, listener, 5, 56));
        builder.addSlot(outputSlot = ChemicalInventorySlot.drain(outputTank, listener, 155, 56));
        inputSlot.setSlotType(ContainerSlotType.INPUT);
        inputSlot.setSlotOverlay(SlotOverlay.MINUS);
        outputSlot.setSlotType(ContainerSlotType.OUTPUT);
        outputSlot.setSlotOverlay(SlotOverlay.PLUS);
        return builder.build();
    }

    @Override
    protected boolean onUpdateServer() {
        boolean sendUpdatePacket = super.onUpdateServer();
        inputSlot.fillTank();
        outputSlot.drainTank();
        productionRate = recalculateProductionRateAndDecay();
        recipeCacheLookupMonitor.updateAndProcess();
        return sendUpdatePacket;
    }

    @NotNull
    @Override
    public IMekanismRecipeTypeProvider<SingleChemicalRecipeInput, ChemicalToChemicalRecipe, SingleChemical<ChemicalToChemicalRecipe>> getRecipeType() {
        return MekanismRecipeType.ACTIVATING;
    }

    @Override
    public IRecipeViewerRecipeType<ChemicalToChemicalRecipe> recipeViewerType() {
        return RecipeViewerRecipeType.ACTIVATING;
    }

    @Nullable
    @Override
    public ChemicalToChemicalRecipe getRecipe(int cacheIndex) {
        return findFirstRecipe(inputHandler);
    }

    @Override
    public boolean canFunction() {
        return super.canFunction();
    }

    private float recalculateProductionRateAndDecay() {
        Level world = getLevel();
        boolean isPlutonium = false;
        float productionRate = 0;

        if (world == null || !canFunction()) {
            plutoniumDecayCounter = 0;
            return 0;
        }

        BlockPos dstBlock = this.getBlockPos().above(2);
        BlockEntity aboveEntity = WorldUtils.getTileEntity(world, dstBlock);
        if(aboveEntity != null && aboveEntity instanceof TileEntityRadioactiveWasteBarrel){
            TileEntityRadioactiveWasteBarrel barrel = (TileEntityRadioactiveWasteBarrel) aboveEntity;
            StackedWasteBarrel wasteTank = barrel.getGasTank();
            if(wasteTank.getType() == MekanismGases.NUCLEAR_WASTE.get()){
                // System.out.println("Waste tank is nuclear waste");
                productionRate = (float)PRODUCTION_RATE.get();
            } else if(wasteTank.getType() == MekanismGases.PLUTONIUM.get()){
                isPlutonium = true;
                // System.out.println("Waste tank is plutonium");
                productionRate = (float)(PRODUCTION_RATE.get() * MNAConfig.general.fissionNeutronActivatorPlutoniumMultiplier.get());
            }
            if(isPlutonium && world.getGameTime() > lastProcessTick){
                if(++plutoniumDecayCounter >= MekanismConfig.general.radioactiveWasteBarrelProcessTicks.get()){
                    plutoniumDecayCounter = 0;
                    wasteTank.shrinkStack(MekanismConfig.general.radioactiveWasteBarrelDecayAmount.get(), Action.EXECUTE);
                }
            }
        } else {
            plutoniumDecayCounter = 0;
        }
        return productionRate;
    }

    @NotNull
    @Override
    public CachedRecipe<ChemicalToChemicalRecipe> createNewCachedRecipe(@NotNull ChemicalToChemicalRecipe recipe, int cacheIndex) {
        return OneInputCachedRecipe.chemicalToChemical(recipe, recheckAllRecipeErrors, inputHandler, outputHandler)
              .setErrorsChanged(this::onErrorsChanged)
              .setCanHolderFunction(this::canFunction)
              .setActive(this::setActive)
              .setOnFinish(this::markForSave)
              //Edge case handling, this should almost always end up being 1
              .setRequiredTicks(() -> productionRate > 0 && productionRate < 1 ? Mth.ceil(1 / productionRate) : 1)
              .setBaselineMaxOperations(() -> productionRate > 0 && productionRate < 1 ? 1 : (int) productionRate);
    }

    @Override
    public int getRedstoneLevel() {
        return MekanismUtils.redstoneLevelFromContents(inputTank.getStored(), inputTank.getCapacity());
    }

    @Override
    protected boolean makesComparatorDirty(ContainerType<?, ?, ?> type) {
        return type == ContainerType.CHEMICAL;
    }
}
