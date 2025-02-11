package net.michanide.mekanismneutronactivator.client;

import mekanism.client.ClientRegistrationUtil;
import mekanism.client.ClientTickHandler;
import mekanism.client.render.RenderTickHandler;
import mekanism.client.sound.SoundHandler;
import net.michanide.mekanismneutronactivator.client.gui.GuiFissionNeutronActivator;
import net.michanide.mekanismneutronactivator.client.gui.GuiFusionNeutronActivator;
import net.michanide.mekanismneutronactivator.client.model.ModelFissionNeutronActivator;
import net.michanide.mekanismneutronactivator.client.model.ModelFusionNeutronActivator;
import net.michanide.mekanismneutronactivator.client.render.item.block.RenderFissionNeutronActivatorItem;
import net.michanide.mekanismneutronactivator.client.render.item.block.RenderFusionNeutronActivatorItem;
import net.michanide.mekanismneutronactivator.client.render.tileentity.RenderFissionNeutronActivator;
import net.michanide.mekanismneutronactivator.client.render.tileentity.RenderFusionNeutronActivator;
import net.michanide.mekanismneutronactivator.common.MekanismNeutronActivator;
import net.michanide.mekanismneutronactivator.common.registries.MNABlocks;
import net.michanide.mekanismneutronactivator.common.registries.MNAContainerTypes;
import net.michanide.mekanismneutronactivator.common.registries.MNATileEntityTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = MekanismNeutronActivator.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistration {

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event){
        MinecraftForge.EVENT_BUS.register(new ClientTickHandler());
        MinecraftForge.EVENT_BUS.register(new RenderTickHandler());
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, SoundHandler::onTilePlaySound);

        ClientRegistrationUtil.setRenderLayer(RenderType.cutout(), MNABlocks.FUSION_NEUTRON_ACTIVATOR);
        ClientRegistrationUtil.setRenderLayer(RenderType.cutout(), MNABlocks.FISSION_NEUTRON_ACTIVATOR);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(MNATileEntityTypes.FUSION_NEUTRON_ACTIVATOR.get(), RenderFusionNeutronActivator::new);
        event.registerBlockEntityRenderer(MNATileEntityTypes.FISSION_NEUTRON_ACTIVATOR.get(), RenderFissionNeutronActivator::new);
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(ModelFusionNeutronActivator.ACTIVATOR_LAYER, ModelFusionNeutronActivator::createLayerDefinition);
        event.registerLayerDefinition(ModelFissionNeutronActivator.ACTIVATOR_LAYER, ModelFissionNeutronActivator::createLayerDefinition);
    }

    @SubscribeEvent
    public static void registerClientReloadListeners(RegisterClientReloadListenersEvent event){
        event.registerReloadListener(RenderFusionNeutronActivatorItem.RENDERER);
        event.registerReloadListener(RenderFissionNeutronActivatorItem.RENDERER);
    }
    
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void registerContainers(RegistryEvent.Register<MenuType<?>> event) {
        ClientRegistrationUtil.registerScreen(MNAContainerTypes.FUSION_NEUTRON_ACTIVATOR, GuiFusionNeutronActivator::new);
        ClientRegistrationUtil.registerScreen(MNAContainerTypes.FISSION_NEUTRON_ACTIVATOR, GuiFissionNeutronActivator::new);
    }
}