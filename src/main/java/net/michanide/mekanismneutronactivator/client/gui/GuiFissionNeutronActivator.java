package net.michanide.mekanismneutronactivator.client.gui;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.vertex.PoseStack;

import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.client.gui.GuiConfigurableTile;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiGasGauge;
import mekanism.client.gui.element.progress.GuiProgress;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.jei.MekanismJEIRecipeType;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.inventory.warning.WarningTracker.WarningType;
import net.michanide.mekanismneutronactivator.common.tile.machine.TileEntityFissionNeutronActivator;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GuiFissionNeutronActivator extends GuiConfigurableTile<TileEntityFissionNeutronActivator, MekanismTileContainer<TileEntityFissionNeutronActivator>>{
    
    public GuiFissionNeutronActivator(MekanismTileContainer<TileEntityFissionNeutronActivator> container, Inventory inv, Component title) {
        super(container, inv, title);
        inventoryLabelY += 2;
        titleLabelY = 4;
        dynamicSlots = true;
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addRenderableWidget(new GuiGasGauge(() -> tile.inputTank, () -> tile.getGasTanks(null), GaugeType.STANDARD, this, 25, 13))
              .warning(WarningType.NO_MATCHING_RECIPE, tile.getWarningCheck(RecipeError.NOT_ENOUGH_INPUT));
        addRenderableWidget(new GuiGasGauge(() -> tile.outputTank, () -> tile.getGasTanks(null), GaugeType.STANDARD, this, 133, 13))
              .warning(WarningType.NO_SPACE_IN_OUTPUT, tile.getWarningCheck(RecipeError.NOT_ENOUGH_OUTPUT_SPACE));
        addRenderableWidget(new GuiProgress(tile::getActive, ProgressType.LARGE_RIGHT, this, 64, 39).jeiCategories(MekanismJEIRecipeType.ACTIVATING))
              .warning(WarningType.INPUT_DOESNT_PRODUCE_OUTPUT, tile.getWarningCheck(RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT));
    }

    @Override
    protected void drawForegroundText(@Nonnull PoseStack matrix, int mouseX, int mouseY) {
        renderTitleText(matrix);
        drawString(matrix, playerInventoryTitle, inventoryLabelX, inventoryLabelY, titleTextColor());
        super.drawForegroundText(matrix, mouseX, mouseY);
    }
}
