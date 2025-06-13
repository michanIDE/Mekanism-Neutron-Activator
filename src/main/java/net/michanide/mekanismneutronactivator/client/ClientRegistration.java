package net.michanide.mekanismneutronactivator.client;

import mekanism.client.ClientRegistrationUtil;
import mekanism.client.ClientTickHandler;
import mekanism.client.render.RenderTickHandler;
import mekanism.client.sound.SoundHandler;
import net.michanide.mekanismneutronactivator.client.gui.GuiFissionNeutronActivator;
import net.michanide.mekanismneutronactivator.client.gui.GuiFusionNeutronActivator;
import net.michanide.mekanismneutronactivator.common.MekanismNeutronActivator;
import net.michanide.mekanismneutronactivator.common.registries.MNAContainerTypes;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(modid = MekanismNeutronActivator.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistration {

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event){
        MinecraftForge.EVENT_BUS.register(new ClientTickHandler());
        MinecraftForge.EVENT_BUS.register(new RenderTickHandler());
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, SoundHandler::onTilePlaySound);

        // ClientRegistrationUtil.setRenderLayer(RenderType.cutout(), MNABlocks.FUSION_NEUTRON_ACTIVATOR);
        // ClientRegistrationUtil.setRenderLayer(RenderType.cutout(), MNABlocks.FISSION_NEUTRON_ACTIVATOR);
    }

    // @SubscribeEvent
    // public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event){
    //     event.registerBlockEntityRenderer(MNATileEntityTypes.FUSION_NEUTRON_ACTIVATOR.get(), RenderFusionNeutronActivator::new);
    //     event.registerBlockEntityRenderer(MNATileEntityTypes.FISSION_NEUTRON_ACTIVATOR.get(), RenderFissionNeutronActivator::new);
    // }

    // @SubscribeEvent
    // public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event){
    //     event.registerLayerDefinition(ModelFusionNeutronActivator.ACTIVATOR_LAYER, ModelFusionNeutronActivator::createLayerDefinition);
    //     event.registerLayerDefinition(ModelFissionNeutronActivator.ACTIVATOR_LAYER, ModelFissionNeutronActivator::createLayerDefinition);
    // }

    // @SubscribeEvent
    // public static void registerClientReloadListeners(RegisterClientReloadListenersEvent event){
    //     event.registerReloadListener(RenderFusionNeutronActivatorItem.RENDERER);
    //     event.registerReloadListener(RenderFissionNeutronActivatorItem.RENDERER);
    // }
    
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void registerContainers(RegisterEvent event) {
        event.register(Registries.MENU, helper -> {
            ClientRegistrationUtil.registerScreen(MNAContainerTypes.FUSION_NEUTRON_ACTIVATOR, GuiFusionNeutronActivator::new);
            ClientRegistrationUtil.registerScreen(MNAContainerTypes.FISSION_NEUTRON_ACTIVATOR, GuiFissionNeutronActivator::new);
        });
    }
}