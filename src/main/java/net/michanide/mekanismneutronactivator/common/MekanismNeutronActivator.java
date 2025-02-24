package net.michanide.mekanismneutronactivator.common;

import mekanism.common.config.MekanismModConfig;


import net.michanide.mekanismneutronactivator.common.config.MNAConfig;
import net.michanide.mekanismneutronactivator.common.registries.MNABlocks;
import net.michanide.mekanismneutronactivator.common.registries.MNAContainerTypes;
import net.michanide.mekanismneutronactivator.common.registries.MNATileEntityTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MekanismNeutronActivator.MOD_ID)
public class MekanismNeutronActivator
{

    public static MekanismNeutronActivator instance;

    public static final String MOD_ID = "mekanismneutronactivator";
    public static final String MOD_NAME = "Mekanism: Neutron Activator";
    public static final String MOD_NAME_SAFE = "Mekanism_Neutron_Activator";

    public MekanismNeutronActivator()
    {
        MNAConfig.registerConfigs(ModLoadingContext.get());

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::onConfigLoad);
        
        MNABlocks.BLOCKS.register(modEventBus);
        MNATileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);
        MNAContainerTypes.CONTAINER_TYPES.register(modEventBus);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        
    }

    private void onConfigLoad(ModConfigEvent configEvent){
        ModConfig config = configEvent.getConfig();
        if (config.getModId().equals(MOD_ID) && config instanceof MekanismModConfig mnaConfig) {
            mnaConfig.clearCache();
        }
    }

    public static ResourceLocation rl(String path) {
      return new ResourceLocation(MekanismNeutronActivator.MOD_ID, path);
   }

}
