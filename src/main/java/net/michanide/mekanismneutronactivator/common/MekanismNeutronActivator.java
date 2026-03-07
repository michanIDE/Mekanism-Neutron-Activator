package net.michanide.mekanismneutronactivator.common;

import net.michanide.mekanismneutronactivator.common.registries.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(MekanismNeutronActivator.MOD_ID)
public class MekanismNeutronActivator
{

    // public static MekanismNeutronActivator instance;

    public static final String MOD_ID = "mekanismneutronactivator";
    public static final String MOD_NAME = "Mekanism: Neutron Activator";
    public static final String MOD_NAME_SAFE = "Mekanism_Neutron_Activator";

    public MekanismNeutronActivator(ModContainer modContainer, IEventBus modEventBus)
    {
        // MNAConfig.registerConfigs(ModLoadingContext.get());

        // IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // modEventBus.addListener(this::commonSetup);
        // modEventBus.addListener(this::onConfigLoad);
        
        addRegistrationListeners(modEventBus);
    }

    private void addRegistrationListeners(IEventBus modEventBus) {
        MNABlocks.BLOCKS.register(modEventBus);
        MNACreativeTabs.CREATIVE_TABS.register(modEventBus);
        MNATileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);
        MNAContainerTypes.CONTAINER_TYPES.register(modEventBus);
    }

    // private void commonSetup(FMLCommonSetupEvent event) {
        
    // }

    // private void onConfigLoad(ModConfigEvent configEvent){
    //     ModConfig config = configEvent.getConfig();
    //     if (config.getModId().equals(MOD_ID) && config instanceof MekanismModConfig mnaConfig) {
    //         mnaConfig.clearCache(configEvent);
    //     }
    // }

    // public static ResourceLocation rl(String path) {
    //   return new ResourceLocation(MekanismNeutronActivator.MOD_ID, path);
    // }

}
