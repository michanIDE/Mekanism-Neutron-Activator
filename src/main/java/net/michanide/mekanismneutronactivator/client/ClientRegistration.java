package net.michanide.mekanismneutronactivator.client;

import mekanism.client.ClientRegistrationUtil;
import net.michanide.mekanismneutronactivator.client.gui.GuiFissionNeutronActivator;
import net.michanide.mekanismneutronactivator.client.gui.GuiFusionNeutronActivator;
import net.michanide.mekanismneutronactivator.common.MekanismNeutronActivator;
import net.michanide.mekanismneutronactivator.common.registries.MNAContainerTypes;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;



@EventBusSubscriber(modid = MekanismNeutronActivator.MOD_ID, value = Dist.CLIENT)
public class ClientRegistration {

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        ClientRegistrationUtil.registerScreen(event, MNAContainerTypes.FUSION_NEUTRON_ACTIVATOR, GuiFusionNeutronActivator::new);
        ClientRegistrationUtil.registerScreen(event, MNAContainerTypes.FISSION_NEUTRON_ACTIVATOR, GuiFissionNeutronActivator::new);
    }
}