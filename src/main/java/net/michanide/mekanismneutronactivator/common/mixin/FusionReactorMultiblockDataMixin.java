package net.michanide.mekanismneutronactivator.common.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mekanism.common.lib.multiblock.MultiblockData;
import mekanism.common.util.WorldUtils;
import mekanism.generators.common.content.fusion.FusionReactorMultiblockData;
import mekanism.generators.common.tile.fusion.TileEntityFusionReactorBlock;
import net.michanide.mekanismneutronactivator.common.tile.machine.TileEntityFusionNeutronActivator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

@Mixin(FusionReactorMultiblockData.class)
public class FusionReactorMultiblockDataMixin extends MultiblockData{

    public FusionReactorMultiblockDataMixin(TileEntityFusionReactorBlock tile){
        super(tile);
    }

    @Inject(at = {@At("TAIL")}, method = "burnFuel()J", remap = false)
    private void sendFuelBurned(CallbackInfoReturnable<Long> callback){
        Long mixinFuelBurned = callback.getReturnValue();
        Level world = getWorld();
        BlockPos posMNA = this.getMinPos().offset(2,-2,2);
        BlockEntity tile = WorldUtils.getTileEntity(world, posMNA);

        if(tile != null && tile instanceof TileEntityFusionNeutronActivator) {
            TileEntityFusionNeutronActivator tileMNA = (TileEntityFusionNeutronActivator) tile;
            tileMNA.setFuelBurned(mixinFuelBurned);
        }
    }

}
