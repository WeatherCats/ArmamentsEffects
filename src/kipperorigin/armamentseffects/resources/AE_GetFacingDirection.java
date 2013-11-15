package kipperorigin.armamentseffects.resources;

import org.bukkit.block.BlockFace;

public class AE_GetFacingDirection {
    
    public static final BlockFace[] axis = { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };
    public static final BlockFace[] radial = { BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST };
   
    /**
    * -z = north
    * +z = south
    * -x = west
    * +x = east
    */
    public static BlockFace yawToFace(float yaw) {
        return yawToFace(yaw, true);
    }

    public static BlockFace yawToFace(float yaw, boolean useSubCardinalDirections) {
        if (useSubCardinalDirections) {
            return radial[Math.round(yaw / 45f) & 0x7];
        } else {
            return axis[Math.round(yaw / 90f) & 0x3];
        }
    }
}

