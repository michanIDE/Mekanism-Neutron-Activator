package net.michanide.mekanismneutronactivator.common.config;

import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;

public class MNAConfig {
    
    private MNAConfig() {
    }

    public static final GeneralConfig general = new GeneralConfig();

    public static void registerConfigs(ModLoadingContext modLoadingContext){
        ModContainer modContainer = modLoadingContext.getActiveContainer();
        MNAConfigHelper.registerConfig(modContainer, general);
    }
}
