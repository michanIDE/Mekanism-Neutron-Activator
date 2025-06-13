package net.michanide.mekanismneutronactivator.common;

import com.mojang.logging.LogUtils;

import mekanism.common.config.MekanismModConfig;
import net.michanide.mekanismneutronactivator.common.config.MNAConfig;
import net.michanide.mekanismneutronactivator.common.registries.MNABlocks;
import net.michanide.mekanismneutronactivator.common.registries.MNAContainerTypes;
import net.michanide.mekanismneutronactivator.common.registries.MNATileEntityTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

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
            mnaConfig.clearCache(configEvent);
        }
    }

    public static ResourceLocation rl(String path) {
      return new ResourceLocation(MekanismNeutronActivator.MOD_ID, path);
    }

}
