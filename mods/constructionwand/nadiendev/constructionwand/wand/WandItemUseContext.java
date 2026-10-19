package nadiendev.constructionwand.wand;

import nadiendev.constructionwand.basics.WandUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class WandItemUseContext extends BlockPlaceContext {
   public WandItemUseContext(Level world, Player player, BlockHitResult rayTraceResult, BlockPos pos, BlockItem item) {
      super(
         world,
         player,
         InteractionHand.MAIN_HAND,
         new ItemStack(item),
         new BlockHitResult(getBlockHitVec(rayTraceResult, pos), rayTraceResult.getDirection(), pos, false)
      );
   }

   private static Vec3 getBlockHitVec(BlockHitResult rayTraceResult, BlockPos pos) {
      Vec3 hitVec = rayTraceResult.getLocation();
      Vec3 blockDelta = WandUtil.blockPosVec(rayTraceResult.getBlockPos()).subtract(WandUtil.blockPosVec(pos));
      return blockDelta.add(hitVec);
   }

   public boolean canPlace() {
      return this.replaceClicked;
   }
}
