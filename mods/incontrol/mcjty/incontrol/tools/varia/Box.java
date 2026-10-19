package mcjty.incontrol.tools.varia;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;

public record Box(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
   public boolean isValid() {
      if (this.minX >= this.maxX) {
         return false;
      } else {
         return this.minY >= this.maxY ? false : this.minZ < this.maxZ;
      }
   }

   public boolean in(BlockPos pos) {
      if (pos.getX() < this.minX || pos.getX() > this.maxX) {
         return false;
      } else {
         return pos.getY() < this.minY || pos.getY() > this.maxY ? false : pos.getZ() >= this.minZ && pos.getZ() <= this.maxZ;
      }
   }

   public boolean in(BlockPos pos, int maxOffset) {
      if (pos.getX() < this.minX - maxOffset || pos.getX() > this.maxX + maxOffset) {
         return false;
      } else {
         return pos.getY() < this.minY - maxOffset || pos.getY() > this.maxY + maxOffset
            ? false
            : pos.getZ() >= this.minZ - maxOffset && pos.getZ() <= this.maxZ + maxOffset;
      }
   }

   public BlockPos randomPos(Random random, @Nullable BlockPos groupCenterPos, int groupDistance) {
      if (groupCenterPos != null && groupDistance >= 0) {
         if (groupDistance == 0) {
            return groupCenterPos;
         }

         for (int counter = 100; counter > 0; counter--) {
            BlockPos attempt = new BlockPos(
               groupCenterPos.getX() - groupDistance + random.nextInt(groupDistance * 2),
               groupCenterPos.getY() - groupDistance + random.nextInt(groupDistance * 2),
               groupCenterPos.getZ() - groupDistance + random.nextInt(groupDistance * 2)
            );
            if (this.in(attempt, 2)) {
               return attempt;
            }
         }

         return groupCenterPos;
      } else {
         return new BlockPos(
            this.minX + random.nextInt(this.maxX - this.minX + 1),
            this.minY + random.nextInt(this.maxY - this.minY + 1),
            this.minZ + random.nextInt(this.maxZ - this.minZ + 1)
         );
      }
   }
}
