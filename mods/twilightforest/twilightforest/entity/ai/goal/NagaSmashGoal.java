package twilightforest.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.event.EventHooks;
import twilightforest.entity.boss.Naga;
import twilightforest.util.entities.EntityUtil;

public class NagaSmashGoal extends Goal {
   private final Naga naga;

   public NagaSmashGoal(Naga naga) {
      this.naga = naga;
   }

   public boolean canUse() {
      return this.naga.horizontalCollision && EventHooks.canEntityGrief(this.naga.level(), this.naga);
   }

   public void start() {
      if (!this.naga.level().isClientSide()) {
         AABB bb = this.naga.getBoundingBox();
         int minx = Mth.floor(bb.minX - 0.75);
         int miny = Mth.floor(bb.minY + 1.01);
         int minz = Mth.floor(bb.minZ - 0.75);
         int maxx = Mth.floor(bb.maxX + 0.75);
         int maxy = Mth.floor(bb.maxY + 0.0);
         int maxz = Mth.floor(bb.maxZ + 0.75);
         BlockPos min = new BlockPos(minx, miny, minz);
         BlockPos max = new BlockPos(maxx, maxy, maxz);
         if (this.naga.level().hasChunksAt(min, max)) {
            for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
               BlockState state = this.naga.level().getBlockState(pos);
               if (state.is(BlockTags.LEAVES) || this.naga.shouldDestroyAllBlocks() && EntityUtil.canDestroyBlock(this.naga.level(), pos, this.naga)) {
                  this.naga.level().destroyBlock(pos, !state.is(BlockTags.LEAVES));
               }
            }
         }
      }
   }
}
