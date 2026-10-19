package bigchadguys.sellingbin.data.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;

public interface TilePlacement<T> extends TilePredicate {
   boolean isSubsetOf(T var1);

   boolean isSubsetOf(BlockGetter var1, BlockPos var2);

   void fillInto(T var1);

   void place(LevelAccessor var1, BlockPos var2, int var3);

   T copy();
}
