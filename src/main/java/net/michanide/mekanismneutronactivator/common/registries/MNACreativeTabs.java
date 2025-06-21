package net.michanide.mekanismneutronactivator.common.registries;

import mekanism.common.registration.impl.CreativeTabDeferredRegister;
import mekanism.common.registration.impl.CreativeTabRegistryObject;
import mekanism.common.registries.MekanismCreativeTabs;
import net.michanide.mekanismneutronactivator.common.MNALang;
import net.michanide.mekanismneutronactivator.common.MekanismNeutronActivator;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;

public class MNACreativeTabs {
    
    public static final CreativeTabDeferredRegister CREATIVE_TABS = new CreativeTabDeferredRegister(MekanismNeutronActivator.MOD_ID, MNACreativeTabs::addToExistingTabs);

    public static final CreativeTabRegistryObject MNA = CREATIVE_TABS.registerMain(MNALang.MEKANISM_NEUTRON_ACTIVATOR, MNABlocks.FUSION_NEUTRON_ACTIVATOR, builder ->
          builder.withTabsBefore(MekanismCreativeTabs.MEKANISM.key())
                .displayItems((displayParameters, output) -> {
                    CreativeTabDeferredRegister.addToDisplay(MNABlocks.BLOCKS, output);
                })
    );

    private static void addToExistingTabs(BuildCreativeModeTabContentsEvent event) {
        ResourceKey<CreativeModeTab> tabKey = event.getTabKey();
        if (tabKey == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            CreativeTabDeferredRegister.addToDisplay(event, MNABlocks.FUSION_NEUTRON_ACTIVATOR, MNABlocks.FISSION_NEUTRON_ACTIVATOR);
        }
    }

}
