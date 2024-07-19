package net.michanide.mekanismneutronactivator.common.config;

import java.nio.file.Path;
import net.michanide.mekanismneutronactivator.common.MekanismNeutronActivator;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.loading.FMLPaths;

public class MNAConfigHelper {
    private MNAConfigHelper(){

    }

    public static final Path CONFIG_DIR = FMLPaths.getOrCreateGameRelativePath(FMLPaths.CONFIGDIR.get().resolve(MekanismNeutronActivator.MOD_NAME_SAFE), MekanismNeutronActivator.MOD_NAME_SAFE);

    public static void registerConfig(ModContainer modContainer, IMNAConfig config) {
        MNAModConfig modConfig = new MNAModConfig(modContainer, config);
        if (config.addToContainer()){
            modContainer.addConfig(modConfig);
        }
    }
}
