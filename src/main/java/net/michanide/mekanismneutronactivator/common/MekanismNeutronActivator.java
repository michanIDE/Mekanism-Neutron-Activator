package net.michanide.mekanismneutronactivator.common;

import com.mojang.logging.LogUtils;

import net.michanide.mekanismneutronactivator.common.config.MNAConfig;
import net.michanide.mekanismneutronactivator.common.registries.modBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("mekanismneutronactivator")
public class MekanismNeutronActivator
{
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public static MekanismNeutronActivator instance;

    public static final String MOD_ID = "mekanismneutronactivator";
    public static final String MOD_NAME = "Mekanism: Neutron Activator";

    public MekanismNeutronActivator()
    {
        MNAConfig.registerConfigs(ModLoadingContext.get());

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::onConfigLoad);

        modBlocks.BLOCKS.register(modEventBus);
    }

    private void commonSetup(FMLCommonSetupEvent event) {

    }

    private void onConfigLoad(ModConfigEvent configEvent){

    }

}
