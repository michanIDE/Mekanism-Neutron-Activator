package net.michanide.mekanismneutronactivator.common.config;

import net.michanide.mekanismneutronactivator.common.MekanismNeutronActivator;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.config.ModConfig;

public class MNAModConfig extends ModConfig{
    
    // WIP

    public MNAModConfig(ModContainer container, IMNAConfig config){
        super(config.getConfigType(), config.getConfigSpec(), container, MekanismNeutronActivator.MOD_NAME + "/" + config.getFileName() + ".toml");
    }

}
