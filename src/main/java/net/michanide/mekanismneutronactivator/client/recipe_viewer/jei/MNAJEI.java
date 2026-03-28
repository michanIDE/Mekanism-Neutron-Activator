package net.michanide.mekanismneutronactivator.client.recipe_viewer.jei;

import java.util.List;

import javax.annotation.Nonnull;

import mekanism.client.recipe_viewer.jei.CatalystRegistryHelper;
import mekanism.client.recipe_viewer.jei.MekanismJEI;
import mekanism.client.recipe_viewer.type.RecipeViewerRecipeType;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.michanide.mekanismneutronactivator.common.MekanismNeutronActivator;
import net.michanide.mekanismneutronactivator.common.registries.MNABlocks;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class MNAJEI implements IModPlugin{

    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(MekanismNeutronActivator.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerItemSubtypes(@Nonnull ISubtypeRegistration registry) {
        if (MekanismJEI.shouldLoad()) {
            MekanismJEI.registerItemSubtypes(registry, MNABlocks.BLOCKS.getSecondaryEntries());
        }
    }

    @Override
    public void registerRecipeCatalysts(@Nonnull IRecipeCatalystRegistration registry) {
        CatalystRegistryHelper.register(registry, MekanismJEI.genericRecipeType(RecipeViewerRecipeType.ACTIVATING), List.of(MNABlocks.FUSION_NEUTRON_ACTIVATOR));
        CatalystRegistryHelper.register(registry, MekanismJEI.genericRecipeType(RecipeViewerRecipeType.ACTIVATING), List.of(MNABlocks.FISSION_NEUTRON_ACTIVATOR));
    }
}

