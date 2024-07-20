package net.michanide.mekanismneutronactivator.common.config;

import net.michanide.mekanismneutronactivator.common.MekanismNeutronActivator;
import java.nio.file.Path;
import java.util.function.Function;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;

import mekanism.common.config.IMekanismConfig;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.config.ConfigFileTypeHandler;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

public class MNAModConfig extends ModConfig{
    
    private static final MNAConfigFileTypeHandler MNA_TOML = new MNAConfigFileTypeHandler();

    private final IMekanismConfig mnaConfig;

    public MNAModConfig(ModContainer container, IMekanismConfig config){
        super(config.getConfigType(), config.getConfigSpec(), container, MekanismNeutronActivator.MOD_NAME_SAFE + "/" + config.getFileName() + ".toml");
        this.mnaConfig = config;
    }

    @Override
    public ConfigFileTypeHandler getHandler() {
        return MNA_TOML;
    }

    public void clearCache() {
        mnaConfig.clearCache();
    }

    private static class MNAConfigFileTypeHandler extends ConfigFileTypeHandler {
        private static Path getPath (Path configBasePath) {
            if (configBasePath.endsWith("serverconfig")){
                return FMLPaths.CONFIGDIR.get();
            }
            return configBasePath;
        }

        @Override
        public Function<ModConfig, CommentedFileConfig> reader(Path configBasePath) {
            return super.reader(getPath(configBasePath));
        }

        @Override
        public void unload(Path configBasePath, ModConfig config) {
            super.unload(getPath(configBasePath), config);
        }
    }
}
