package net.michanide.mekanismneutronactivator.common.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import mekanism.generators.common.content.fusion.FusionReactorMultiblockData;

@Mixin(FusionReactorMultiblockData.class)
public interface FusionFuelBurnedAccessor{

    @Accessor(value = "lastBurned", remap = false)
    long getLastBurned();

}
