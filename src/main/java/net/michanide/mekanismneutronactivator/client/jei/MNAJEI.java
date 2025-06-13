package net.michanide.mekanismneutronactivator.client.jei;

import javax.annotation.Nonnull;

import mekanism.client.jei.CatalystRegistryHelper;
import mekanism.client.jei.MekanismJEI;
import mekanism.client.jei.MekanismJEIRecipeType;
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
        return MekanismNeutronActivator.rl("jei_plugin");
    }

    @Override
    public void registerItemSubtypes(@Nonnull ISubtypeRegistration registry) {
        MekanismJEI.registerItemSubtypes(registry, MNABlocks.BLOCKS.getAllBlocks());
    }

    @Override
    public void registerRecipeCatalysts(@Nonnull IRecipeCatalystRegistration registry) {
        CatalystRegistryHelper.register(registry, MekanismJEIRecipeType.ACTIVATING, MNABlocks.FUSION_NEUTRON_ACTIVATOR);
        CatalystRegistryHelper.register(registry, MekanismJEIRecipeType.ACTIVATING, MNABlocks.FISSION_NEUTRON_ACTIVATOR);
    }
}
