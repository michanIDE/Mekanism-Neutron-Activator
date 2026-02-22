package net.michanide.mekanismneutronactivator.client;

import mekanism.client.ClientRegistrationUtil;
import net.michanide.mekanismneutronactivator.client.gui.GuiFissionNeutronActivator;
import net.michanide.mekanismneutronactivator.client.gui.GuiFusionNeutronActivator;
import net.michanide.mekanismneutronactivator.common.MekanismNeutronActivator;
import net.michanide.mekanismneutronactivator.common.registries.MNAContainerTypes;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(modid = MekanismNeutronActivator.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistration {

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void registerContainers(RegisterEvent event) {
        event.register(Registries.MENU, helper -> {
            ClientRegistrationUtil.registerScreen(MNAContainerTypes.FUSION_NEUTRON_ACTIVATOR, GuiFusionNeutronActivator::new);
            ClientRegistrationUtil.registerScreen(MNAContainerTypes.FISSION_NEUTRON_ACTIVATOR, GuiFissionNeutronActivator::new);
        });
    }
}