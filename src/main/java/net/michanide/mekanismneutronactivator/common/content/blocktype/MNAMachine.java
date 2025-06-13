package net.michanide.mekanismneutronactivator.common.content.blocktype;

import java.util.function.Supplier;
import java.util.EnumSet;

import mekanism.api.Upgrade;
import mekanism.api.text.ILangEntry;
import mekanism.common.block.attribute.AttributeParticleFX;
import mekanism.common.block.attribute.AttributeStateFacing;
import mekanism.common.block.attribute.AttributeUpgradeSupport;
import mekanism.common.block.attribute.Attributes;
import mekanism.common.content.blocktype.BlockTypeTile;
import mekanism.common.lib.math.Pos3D;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import mekanism.common.tile.base.TileEntityMekanism;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;

public class MNAMachine<TILE extends TileEntityMekanism> extends BlockTypeTile<TILE>{
    
    public MNAMachine(Supplier<TileEntityTypeRegistryObject<TILE>> tileEntityRegistrar, ILangEntry description) {
        super(tileEntityRegistrar, description);
        add(new AttributeParticleFX()
              .add(ParticleTypes.SMOKE, rand -> new Pos3D(rand.nextFloat() * 0.6F - 0.3F, rand.nextFloat() * 6.0F / 16.0F, 0.52))
              .add(DustParticleOptions.REDSTONE, rand -> new Pos3D(rand.nextFloat() * 0.6F - 0.3F, rand.nextFloat() * 6.0F / 16.0F, 0.52)));
        add(Attributes.ACTIVE_LIGHT, new AttributeStateFacing(), Attributes.INVENTORY, Attributes.SECURITY, Attributes.REDSTONE, Attributes.COMPARATOR);
        add(new AttributeUpgradeSupport(EnumSet.of(Upgrade.SPEED, Upgrade.ENERGY, Upgrade.MUFFLING)));
    }

    public static class MNAMachineBuilder<MNAMACHINE extends MNAMachine<TILE>, TILE extends TileEntityMekanism, T extends MNAMachineBuilder<MNAMACHINE, TILE, T>> extends BlockTileBuilder<MNAMACHINE, TILE, T> {

        protected MNAMachineBuilder(MNAMACHINE holder) {
            super(holder);
        }

        public static <TILE extends TileEntityMekanism> MNAMachineBuilder<MNAMachine<TILE>, TILE, ?> createMNAMachine(Supplier<TileEntityTypeRegistryObject<TILE>> tileEntityRegistrar, ILangEntry description) {
            return new MNAMachineBuilder<>(new MNAMachine<>(tileEntityRegistrar, description));
        }
    }
}
