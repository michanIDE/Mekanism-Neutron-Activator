package net.michanide.mekanismneutronactivator.common.config;

import mekanism.common.config.BaseMekanismConfig;
import mekanism.common.config.value.CachedDoubleValue;
import mekanism.common.config.value.CachedLongValue;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig.Type;

public class MNAGeneralConfig extends BaseMekanismConfig {
    
    private final ForgeConfigSpec configSpec;

    public final CachedLongValue fusionNeutronActivatorMaxTankSize;
    public final CachedDoubleValue fusionNeutronActivatorMultiplier;
    public final CachedLongValue fusionNeutronActivatorOutputRate;

    public final CachedLongValue fissionNeutronActivatorMaxTankSize;
    public final CachedDoubleValue fissionNeutronActivatorProductionRate;
    public final CachedDoubleValue fissionNeutronActivatorPlutoniumMultiplier;
    public final CachedLongValue fissionNeutronActivatorOutputRate;

    MNAGeneralConfig() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("MNA General Config. This config is synced from server to client.").push("MNA-general");

        fusionNeutronActivatorMaxTankSize = CachedLongValue.wrap(this, builder.comment("Max tank size of fusion neutron activator.")
            .defineInRange("fusionNeutronActivatorMaxTankSize", 100_000L, 1L, Long.MAX_VALUE));
        fusionNeutronActivatorMultiplier = CachedDoubleValue.wrap(this, builder.comment("Multiplier of fusion neutron activator's production rate. Actual producton rate is this value multiplied by fusion fuel burned.")
            .defineInRange("fusionNeutronActivatorMultiplier", 3.0, 0.0, Double.MAX_VALUE));
        fusionNeutronActivatorOutputRate = CachedLongValue.wrap(this, builder.comment("Output rate of fusion neutron activator.")
            .defineInRange("fusionNeutronActivatorOutputRate", 4_096L, 1L, Long.MAX_VALUE));

        fissionNeutronActivatorMaxTankSize = CachedLongValue.wrap(this, builder.comment("Max tank size of fission neutron activator.")
            .defineInRange("fissionNeutronActivatorMaxTankSize", 1_000L, 1L, Long.MAX_VALUE));
        fissionNeutronActivatorProductionRate = CachedDoubleValue.wrap(this, builder.comment("Production rate of fission neutron activator's operation. (Inverse of operating ticks)")
            .defineInRange("fissionNeutronActivatorOperatingTicks", 0.0025, 0.0, Double.MAX_VALUE));
        fissionNeutronActivatorPlutoniumMultiplier = CachedDoubleValue.wrap(this, builder.comment("Multiplier of fission neutron activator's production rate. When you use barrel containing plutonium, the production rate is multiplied by this value.")
            .defineInRange("fissionNeutronActivatorPlutoniumMultiplier", 10.0, 0.0, Double.MAX_VALUE));
        fissionNeutronActivatorOutputRate = CachedLongValue.wrap(this, builder.comment("Output rate of fission neutron activator.")
            .defineInRange("fissionNeutronActivatorOutputRate", 4_096L, 1L, Long.MAX_VALUE));

        
        builder.pop();
        configSpec = builder.build();
    }

    @Override
    public String getFileName() {
        return "MNA-general";
    }

    @Override
    public ForgeConfigSpec getConfigSpec() {
        return configSpec;
    }

    @Override
    public Type getConfigType() {
        return Type.SERVER;
    }

    @Override
    public boolean addToContainer() {
        return false;
    }
    
}
