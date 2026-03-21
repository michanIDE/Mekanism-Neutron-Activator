package net.michanide.mekanismneutronactivator.common.config;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import mekanism.common.config.IMekanismConfig;
import mekanism.common.config.MekanismConfigHelper;
import net.michanide.mekanismneutronactivator.common.MekanismNeutronActivator;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.IConfigSpec;
import net.neoforged.fml.event.config.ModConfigEvent;

public class MNAConfig {
    
    private MNAConfig() {
    }

    private static final Map<IConfigSpec, IMekanismConfig> KNOWN_CONFIGS = new HashMap<>();
    public static final MNAGeneralConfig general = new MNAGeneralConfig();

    public static void registerConfigs(ModContainer modContainer){
        MekanismConfigHelper.registerConfig(KNOWN_CONFIGS, modContainer, general);
    }

    public static void onConfigLoad(ModConfigEvent configEvent) {
        MekanismConfigHelper.onConfigLoad(configEvent, MekanismNeutronActivator.MOD_ID, KNOWN_CONFIGS);
    }

    public static Collection<IMekanismConfig> getConfigs() {
        return Collections.unmodifiableCollection(KNOWN_CONFIGS.values());
    }
}
