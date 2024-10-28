package net.michanide.mekanismneutronactivator.common.config;

import mekanism.common.config.value.CachedDoubleValue;
import mekanism.common.config.value.CachedLongValue;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig.Type;

public class GeneralConfig extends BaseMNAConfig {
    
    private final ForgeConfigSpec configSpec;

    public final CachedLongValue fusionNeutronActivatorMaxTankSize;
    public final CachedDoubleValue fusionNeutronActivatorMultiplier;

    GeneralConfig() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("General Config. This config is synced from server to client.").push("general");

        fusionNeutronActivatorMaxTankSize = CachedLongValue.wrap(this, builder.comment("Max tank size of fusion neutron activator.")
            .define("fusionNeutronActivatorMaxTankSize", 100_000L));
        fusionNeutronActivatorMultiplier = CachedDoubleValue.wrap(this, builder.comment("Multiplier of fusion neutron activator's production rate. Actual producton rate is this value multiplied by fusion fuel burned.")
            .define("fusionNeutronActivatorMultiplier", 3.0));

        configSpec = builder.build();
    }

    @Override
    public String getFileName() {
        return "general";
    }

    @Override
    public ForgeConfigSpec getConfigSpec() {
        return configSpec;
    }

    @Override
    public Type getConfigType() {
        return Type.SERVER;
    }
    
}
