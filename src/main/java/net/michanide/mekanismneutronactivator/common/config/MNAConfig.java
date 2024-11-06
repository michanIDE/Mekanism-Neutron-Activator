package net.michanide.mekanismneutronactivator.common.config;

import mekanism.common.config.MekanismConfigHelper;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;

public class MNAConfig {
    
    private MNAConfig() {
    }

    public static final MNAGeneralConfig general = new MNAGeneralConfig();

    public static void registerConfigs(ModLoadingContext modLoadingContext){
        ModContainer modContainer = modLoadingContext.getActiveContainer();
        MekanismConfigHelper.registerConfig(modContainer, general);
    }
}
