package net.darkhax.bookshelf.common.api.menu.data;

import java.util.Arrays;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.ContainerData;

public class BlockPosData implements ContainerData {
   private final int[] pos;
   private final boolean mutable;

   public BlockPosData(BlockPos pos) {
      this(pos, false);
   }

   public BlockPosData(BlockPos pos, boolean mutable) {
      this.pos = new int[]{pos.getX(), pos.getY(), pos.getZ()};
      this.mutable = mutable;
   }

   public int get(int slot) {
      return this.pos[slot];
   }

   public void set(int slot, int value) {
      if (this.mutable) {
         this.pos[slot] = value;
      }
   }

   public int getCount() {
      return 3;
   }

   public BlockPos getPos() {
      return new BlockPos(this.pos[0], this.pos[1], this.pos[2]);
   }

   public static BlockPos readPos(ContainerData data) {
      if (data.getCount() != 3) {
         throw new IllegalStateException(
            "Can not read BlockPos from container data. Expected 3 values, found " + data.getCount() + ". data=" + Arrays.toString(toArray(data))
         );
      } else {
         return new BlockPos(data.get(0), data.get(1), data.get(2));
      }
   }

   private static int[] toArray(ContainerData containerData) {
      int[] data = new int[containerData.getCount()];

      for (int i = 0; i < containerData.getCount(); i++) {
         data[i] = containerData.get(i);
      }

      return data;
   }
}
