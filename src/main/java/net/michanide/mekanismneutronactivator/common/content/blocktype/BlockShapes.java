package net.michanide.mekanismneutronactivator.common.content.blocktype;

import mekanism.common.util.EnumUtils;
import mekanism.common.util.VoxelShapeUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockShapes {

    private BlockShapes(){
        
    }

    private static VoxelShape box(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return Block.box(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public static final VoxelShape[] FUSION_NEUTRON_ACTIVATOR = new VoxelShape[EnumUtils.HORIZONTAL_DIRECTIONS.length];
    
    static {
        VoxelShapeUtils.setShape(VoxelShapeUtils.combine(
              box(6, 27, 4, 10, 28, 14), // panel_base
              box(7, 26, 8, 9, 27, 14), // panel_connector
              box(0, 0, 0, 16, 4, 16), // base_bottom
              box(0, 5, 0, 16, 14, 16), // base_top
              box(2, 4, 2, 14, 5, 14), // base_middle
              box(1, 4, 3, 2, 5, 5), // thing1
              box(1, 4, 6, 2, 5, 10), // thing2
              box(1, 4, 11, 2, 5, 13), // thing3
              box(14, 4, 11, 15, 5, 13), // thing4
              box(14, 4, 6, 15, 5, 10), // thing5
              box(14, 4, 3, 15, 5, 5), // thing6
              box(6, 4, 14, 10, 5, 16), // energy_connector
              box(4, 3, 0, 12, 11, 1), // port
              box(6, 14, 14, 10, 28, 16), // energy
              box(5, 14, 3, 11, 15, 9), // tray
              box(7, 14, 9, 9, 15, 14), // cable
              box(6, 14, 12, 10, 16, 13), // brace1
              box(6, 14, 10, 10, 16, 11), // brace2
              box(5, 14, 10, 6, 15, 11), // brace3
              box(5, 14, 12, 6, 15, 13), // brace4
              box(10, 14, 10, 11, 15, 11), // brace5
              box(10, 14, 12, 11, 15, 13), // brace6
              box(7, 25, 5, 9, 26, 7), // emitter
              box(6, 26, 4, 10, 27, 8), // laser

              box(4, 28, 0, 12, 29, 16), // panel_mid (extended to be slightly wider)
              //Rough estimates of the two panels
              //Right
              box(12, 29, 0, 14, 30, 16),
              box(14, 30, 0, 16.5, 31, 16),
              //Left
              box(2, 29, 0, 4, 30, 16),
              box(-0.5, 30, 0, 2, 31, 16)
        ), FUSION_NEUTRON_ACTIVATOR);
    }
}
