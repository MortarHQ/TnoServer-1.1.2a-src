package mcjty.incontrol.tools.varia;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class LookAtTools {
   public static HitResult getMovingObjectPositionFromPlayer(LevelAccessor worldIn, Player playerIn, boolean useLiquids) {
      float pitch = playerIn.getXRot();
      float yaw = playerIn.getYRot();
      Vec3 vec3 = getPlayerEyes(playerIn);
      float f2 = Mth.cos(-yaw * (float) (Math.PI / 180.0) - (float) Math.PI);
      float f3 = Mth.sin(-yaw * (float) (Math.PI / 180.0) - (float) Math.PI);
      float f4 = -Mth.cos(-pitch * (float) (Math.PI / 180.0));
      float f5 = Mth.sin(-pitch * (float) (Math.PI / 180.0));
      float f6 = f3 * f4;
      float f7 = f2 * f4;
      double reach = 5.0;
      if (playerIn instanceof ServerPlayer) {
      }

      Vec3 vec31 = vec3.add(f6 * reach, f5 * reach, f7 * reach);
      ClipContext context = new ClipContext(vec3, vec31, Block.COLLIDER, useLiquids ? Fluid.ANY : Fluid.NONE, playerIn);
      return worldIn.clip(context);
   }

   private static Vec3 getPlayerEyes(Player playerIn) {
      double x = playerIn.getX();
      double y = playerIn.getY() + playerIn.getEyeHeight();
      double z = playerIn.getZ();
      return new Vec3(x, y, z);
   }
}
