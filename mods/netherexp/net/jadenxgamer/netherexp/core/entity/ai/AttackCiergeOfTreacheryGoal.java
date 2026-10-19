package net.jadenxgamer.netherexp.core.entity.ai;

import net.jadenxgamer.netherexp.core.block.CiergeOfTreacheryBlock;
import net.jadenxgamer.netherexp.core.block.entity.CiergeOfTreacheryBlockEntity;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class AttackCiergeOfTreacheryGoal extends MoveToBlockGoal {
   PathfinderMob entity;
   int attackDelay = 60;

   public AttackCiergeOfTreacheryGoal(PathfinderMob pathfinderMob, int range) {
      super(pathfinderMob, 1.0, range, range);
      this.entity = pathfinderMob;
   }

   public double acceptedDistance() {
      return 2.0;
   }

   protected boolean isValidTarget(LevelReader level, BlockPos pos) {
      BlockState state = level.getBlockState(pos);
      return state.is(JNEBlocks.CIERGE_OF_TREACHERY.get());
   }

   @NotNull
   protected BlockPos getMoveToTarget() {
      return this.blockPos;
   }

   public void tick() {
      super.tick();
      Level level = this.entity.level();
      BlockPos target = this.getMoveToTarget();
      BlockState state = level.getBlockState(target);
      if (this.isReachedTarget()
         && level.getBlockEntity(target) instanceof CiergeOfTreacheryBlockEntity ciergeOfTreachery
         && !(Boolean)state.getValue(CiergeOfTreacheryBlock.COMPLETED)
         && (Boolean)state.getValue(CiergeOfTreacheryBlock.LIT)) {
         int health = ciergeOfTreachery.getHealth();
         if (this.attackDelay <= 0 && !(Boolean)state.getValue(CiergeOfTreacheryBlock.BROKEN)) {
            if (health > 0) {
               ciergeOfTreachery.setHealth(health - 1);
               level.playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.WITHER_BREAK_BLOCK, SoundSource.BLOCKS, 1.0F, 1.0F);
            } else {
               level.setBlock(
                  target,
                  (BlockState)((BlockState)level.getBlockState(target).setValue(CiergeOfTreacheryBlock.LIT, false))
                     .setValue(CiergeOfTreacheryBlock.BROKEN, true),
                  2
               );
               level.playSound(
                  null, target.getX(), target.getY(), target.getZ(), JNESoundEvents.CIERGE_OF_TREACHERY_DEFEAT.get(), SoundSource.PLAYERS, 1.0F, 1.0F
               );
               level.playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.WITHER_BREAK_BLOCK, SoundSource.BLOCKS, 0.5F, 1.0F);
            }

            this.attackDelay = 60;
         } else {
            this.attackDelay--;
         }
      }
   }

   public boolean canUse() {
      return this.entity.getTarget() == null && !this.entity.getType().is(JNETags.EntityTypes.IGNORES_CIERGE_OF_TREACHERY) && super.canUse();
   }

   public boolean canContinueToUse() {
      BlockPos target = this.getMoveToTarget();
      BlockState targetState = this.entity.level().getBlockState(target);
      return targetState.is(JNEBlocks.CIERGE_OF_TREACHERY.get())
         && !(Boolean)targetState.getValue(CiergeOfTreacheryBlock.COMPLETED)
         && (Boolean)targetState.getValue(CiergeOfTreacheryBlock.LIT)
         && super.canContinueToUse();
   }

   public void start() {
      super.start();
   }

   public void stop() {
      super.stop();
   }
}
