package net.michanide.mekanismneutronactivator.client;

import mekanism.client.ClientTickHandler;
import mekanism.client.render.RenderTickHandler;
import mekanism.client.sound.SoundHandler;
import net.michanide.mekanismneutronactivator.client.render.tileentity.RenderFusionNeutronActivator;
import net.michanide.mekanismneutronactivator.common.MekanismNeutronActivator;
import net.michanide.mekanismneutronactivator.common.registries.MNATileEntityTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = MekanismNeutronActivator.MOD_ID, value = Dist.CLIENT)
public class Clientregistration {

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event){
        MinecraftForge.EVENT_BUS.register(new ClientTickHandler());
        MinecraftForge.EVENT_BUS.register(new RenderTickHandler());
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, SoundHandler::onTilePlaySound);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(MNATileEntityTypes.FUSION_NEUTRON_ACTIVATOR.get(), RenderFusionNeutronActivator::new);
    }
}
