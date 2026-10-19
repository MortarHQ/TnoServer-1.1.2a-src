package nadiendev.constructionwand.wand.undo;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public interface ISnapshot {
   BlockPos getPos();

   BlockState getBlockState();

   ItemStack getRequiredItems();

   boolean execute(Level var1, Player var2, BlockHitResult var3);

   boolean canRestore(Level var1, Player var2);

   boolean restore(Level var1, Player var2);

   void forceRestore(Level var1);
}
