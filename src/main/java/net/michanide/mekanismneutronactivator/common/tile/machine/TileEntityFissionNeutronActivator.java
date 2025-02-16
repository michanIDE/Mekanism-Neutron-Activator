package net.michanide.mekanismneutronactivator.common.tile.machine;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.IContentsListener;
import mekanism.api.RelativeSide;
import mekanism.api.annotations.NonNull;
import mekanism.api.chemical.ChemicalTankBuilder;
import mekanism.api.chemical.attribute.ChemicalAttributeValidator;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.IGasTank;
import mekanism.api.chemical.gas.attribute.GasAttributes;
import mekanism.api.recipes.GasToGasRecipe;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.cache.OneInputCachedRecipe;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.inputs.InputHelper;
import mekanism.api.recipes.outputs.IOutputHandler;
import mekanism.api.recipes.outputs.OutputHelper;
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
import mekanism.common.inventory.slot.chemical.GasInventorySlot;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.recipe.IMekanismRecipeTypeProvider;
import mekanism.common.recipe.MekanismRecipeType;
import mekanism.common.recipe.lookup.ISingleRecipeLookupHandler.ChemicalRecipeLookupHandler;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.SingleChemical;
import mekanism.common.registries.MekanismGases;
import mekanism.common.tile.TileEntityRadioactiveWasteBarrel;
import mekanism.common.tile.base.SubstanceType;
import mekanism.common.tile.component.TileComponentConfig;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.tile.interfaces.IBoundingBlock;
import mekanism.common.tile.prefab.TileEntityRecipeMachine;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.WorldUtils;
import net.michanide.mekanismneutronactivator.common.config.MNAConfig;
import net.michanide.mekanismneutronactivator.common.registries.MNABlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class TileEntityFissionNeutronActivator extends TileEntityRecipeMachine<GasToGasRecipe> implements IBoundingBlock, ChemicalRecipeLookupHandler<Gas, GasStack, GasToGasRecipe> {
    
    private static final List<RecipeError> TRACKED_ERROR_TYPES = List.of(
          RecipeError.NOT_ENOUGH_INPUT,
          RecipeError.NOT_ENOUGH_OUTPUT_SPACE,
          RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT
    );
    public static final CachedLongValue MAX_GAS_CONF = MNAConfig.general.fissionNeutronActivatorMaxTankSize;
    public static final CachedLongValue OUTPUT_RATE_CONF = MNAConfig.general.fissionNeutronActivatorOutputRate;
    public static final CachedDoubleValue PRODUCTION_RATE = MNAConfig.general.fissionNeutronActivatorProductionRate;

    @WrappingComputerMethod(wrapper = ComputerChemicalTankWrapper.class, methodNames = {"getInput", "getInputCapacity", "getInputNeeded", "getInputFilledPercentage"})
    public IGasTank inputTank;
    @WrappingComputerMethod(wrapper = ComputerChemicalTankWrapper.class, methodNames = {"getOutput", "getOutputCapacity", "getOutputNeeded", "getOutputFilledPercentage"})
    public IGasTank outputTank;

    @SyntheticComputerMethod(getter = "getProductionRate")
    private float productionRate;

    private final IOutputHandler<@NonNull GasStack> outputHandler;
    private final IInputHandler<@NonNull GasStack> inputHandler;

    @WrappingComputerMethod(wrapper = ComputerIInventorySlotWrapper.class, methodNames = "getInputItem")
    private GasInventorySlot inputSlot;
    @WrappingComputerMethod(wrapper = ComputerIInventorySlotWrapper.class, methodNames = "getOutputItem")
    private GasInventorySlot outputSlot;

    private int plutoniumDecayCounter;
    private long lastProcessTick = -1;

    public TileEntityFissionNeutronActivator(BlockPos pos, BlockState state) {
        super(MNABlocks.FISSION_NEUTRON_ACTIVATOR, pos, state, TRACKED_ERROR_TYPES);
        configComponent = new TileComponentConfig(this, TransmissionType.ITEM, TransmissionType.GAS);
        configComponent.setupIOConfig(TransmissionType.ITEM, inputSlot, outputSlot, RelativeSide.FRONT);
        configComponent.setupIOConfig(TransmissionType.GAS, inputTank, outputTank, RelativeSide.FRONT, false, true).setEjecting(true);
        configComponent.addDisabledSides(RelativeSide.TOP);

        ejectorComponent = new TileComponentEjector(this, OUTPUT_RATE_CONF);
        ejectorComponent.setOutputData(configComponent, TransmissionType.ITEM, TransmissionType.GAS)
              .setCanTankEject(tank -> tank != inputTank);
        inputHandler = InputHelper.getInputHandler(inputTank, RecipeError.NOT_ENOUGH_INPUT);
        outputHandler = OutputHelper.getOutputHandler(outputTank, RecipeError.NOT_ENOUGH_OUTPUT_SPACE);

        plutoniumDecayCounter = 0;
    }

    @Nonnull
    @Override
    public IChemicalTankHolder<Gas, GasStack, IGasTank> getInitialGasTanks(IContentsListener listener, IContentsListener recipeCacheListener) {
        ChemicalTankHelper<Gas, GasStack, IGasTank> builder = ChemicalTankHelper.forSideGasWithConfig(this::getDirection, this::getConfig);
        //Allow extracting out of the input gas tank if it isn't external OR the output tank is empty AND the input is radioactive
        builder.addTank(inputTank = ChemicalTankBuilder.GAS.create(MAX_GAS_CONF.get(),
              (type, automationType) -> automationType != AutomationType.EXTERNAL || (outputTank.isEmpty() && type.has(GasAttributes.Radiation.class)),
              ChemicalTankBuilder.GAS.alwaysTrueBi, this::containsRecipe, ChemicalAttributeValidator.ALWAYS_ALLOW, recipeCacheListener));
        builder.addTank(outputTank = ChemicalTankBuilder.GAS.output(MAX_GAS_CONF.get(), listener));
        return builder.build();
    }

    @Nonnull
    @Override
    protected IInventorySlotHolder getInitialInventory(IContentsListener listener, IContentsListener recipeCacheListener) {
        InventorySlotHelper builder = InventorySlotHelper.forSideWithConfig(this::getDirection, this::getConfig);
        builder.addSlot(inputSlot = GasInventorySlot.fill(inputTank, listener, 5, 56));
        builder.addSlot(outputSlot = GasInventorySlot.drain(outputTank, listener, 155, 56));
        inputSlot.setSlotType(ContainerSlotType.INPUT);
        inputSlot.setSlotOverlay(SlotOverlay.MINUS);
        outputSlot.setSlotType(ContainerSlotType.OUTPUT);
        outputSlot.setSlotOverlay(SlotOverlay.PLUS);
        return builder.build();
    }

    @Override
    protected void onUpdateServer() {
        super.onUpdateServer();
        inputSlot.fillTank();
        outputSlot.drainTank();
        productionRate = recalculateProductionRateAndDecay();
        recipeCacheLookupMonitor.updateAndProcess();
    }

    @Nonnull
    @Override
    public IMekanismRecipeTypeProvider<GasToGasRecipe, SingleChemical<Gas, GasStack, GasToGasRecipe>> getRecipeType() {
        return MekanismRecipeType.ACTIVATING;
    }

    @Nullable
    @Override
    public GasToGasRecipe getRecipe(int cacheIndex) {
        return findFirstRecipe(inputHandler);
    }

    private boolean canFunction() {
        return MekanismUtils.canFunction(this);
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
            if(wasteTank.getType() == MekanismGases.NUCLEAR_WASTE.getChemical()){
                // System.out.println("Waste tank is nuclear waste");
                productionRate = (float)PRODUCTION_RATE.get();
            } else if(wasteTank.getType() == MekanismGases.PLUTONIUM.getChemical()){
                isPlutonium = true;
                // System.out.println("Waste tank is plutonium");
                productionRate = (float)(PRODUCTION_RATE.get() * MNAConfig.general.fissionNeutronActivatorPlutoniumMultiplier.get());
            }
            if(isPlutonium && world.getGameTime() > lastProcessTick){
                // lastProcessTick = world.getGameTime();
                // System.out.println("Plutonium decay counter: " + plutoniumDecayCounter);
                if(++plutoniumDecayCounter >= MekanismConfig.general.radioactiveWasteBarrelProcessTicks.get()){
                    plutoniumDecayCounter = 0;
                    wasteTank.shrinkStack(MekanismConfig.general.radioactiveWasteBarrelDecayAmount.get(), Action.EXECUTE);
                    // System.out.println("Plutonium decayed");
                }
            }
        } else {
            plutoniumDecayCounter = 0;
        }
        return productionRate;
    }

    @Nonnull
    @Override
    public CachedRecipe<GasToGasRecipe> createNewCachedRecipe(@Nonnull GasToGasRecipe recipe, int cacheIndex) {
        return OneInputCachedRecipe.chemicalToChemical(recipe, recheckAllRecipeErrors, inputHandler, outputHandler)
              .setErrorsChanged(this::onErrorsChanged)
              .setCanHolderFunction(this::canFunction)
              .setActive(this::setActive)
              .setOnFinish(this::markForSave)
              //Edge case handling, this should almost always end up being 1
              .setRequiredTicks(() -> productionRate > 0 && productionRate < 1 ? (int) Math.ceil(1 / productionRate) : 1)
              .setBaselineMaxOperations(() -> productionRate > 0 && productionRate < 1 ? 1 : (int) productionRate);
    }

    @Nonnull
    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(worldPosition, worldPosition.offset(1, 2, 1));
    }

    @Override
    public int getRedstoneLevel() {
        return MekanismUtils.redstoneLevelFromContents(inputTank.getStored(), inputTank.getCapacity());
    }

    @Override
    protected boolean makesComparatorDirty(@Nullable SubstanceType type) {
        return type == SubstanceType.GAS;
    }
}
