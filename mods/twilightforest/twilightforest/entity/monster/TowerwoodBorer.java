package twilightforest.entity.monster;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity.MovementEmission;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.ClimbOnTopOfPowderSnowGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.event.EventHooks;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFSounds;

public class TowerwoodBorer extends Monster {
   private TowerwoodBorer.SummonBorersGoal summonBorers;

   public TowerwoodBorer(EntityType<? extends TowerwoodBorer> type, Level world) {
      super(type, world);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(1, new FloatGoal(this));
      this.goalSelector.addGoal(1, new ClimbOnTopOfPowderSnowGoal(this, this.level()));
      this.goalSelector.addGoal(3, this.summonBorers = new TowerwoodBorer.SummonBorersGoal(this));
      this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0, false));
      this.goalSelector.addGoal(5, new TowerwoodBorer.HideInTowerwoodGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]).setAlertOthers(new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public static Builder registerAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, 15.0)
         .add(Attributes.MOVEMENT_SPEED, 0.27)
         .add(Attributes.ATTACK_DAMAGE, 5.0)
         .add(Attributes.FOLLOW_RANGE, 8.0);
   }

   protected MovementEmission getMovementEmission() {
      return MovementEmission.EVENTS;
   }

   public boolean isSteppingCarefully() {
      return false;
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)TFSounds.TOWERWOOD_BORER_AMBIENT.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)TFSounds.TOWERWOOD_BORER_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)TFSounds.TOWERWOOD_BORER_DEATH.get();
   }

   public boolean hurt(DamageSource source, float amount) {
      if (this.isInvulnerableTo(source)) {
         return false;
      }

      if ((source.getEntity() != null || source.is(DamageTypeTags.ALWAYS_TRIGGERS_SILVERFISH)) && this.summonBorers != null) {
         this.summonBorers.notifyHurt();
      }

      return super.hurt(source, amount);
   }

   protected void playStepSound(BlockPos pos, BlockState block) {
      this.playSound((SoundEvent)TFSounds.TOWERWOOD_BORER_STEP.get(), 0.15F, 1.0F);
   }

   public void tick() {
      this.yBodyRot = this.getYRot();
      super.tick();
   }

   private static class HideInTowerwoodGoal extends RandomStrollGoal {
      private Direction facing;
      private boolean doMerge;

      public HideInTowerwoodGoal(TowerwoodBorer borer) {
         super(borer, 1.0, 10);
         this.setFlags(EnumSet.of(Flag.MOVE));
      }

      public boolean canUse() {
         if (this.mob.getTarget() != null) {
            return false;
         }

         if (!this.mob.getNavigation().isDone()) {
            return false;
         }

         RandomSource random = this.mob.getRandom();
         if (random.nextInt(10) == 0 && EventHooks.canEntityGrief(this.mob.level(), this.mob)) {
            this.facing = Direction.getRandom(random);
            BlockPos blockpos = BlockPos.containing(this.mob.getX(), this.mob.getY() + 0.5, this.mob.getZ()).relative(this.facing);
            BlockState state = this.mob.level().getBlockState(blockpos);
            if (state.is(TFBlocks.TOWERWOOD)) {
               this.doMerge = true;
               return true;
            }
         }

         this.doMerge = false;
         return super.canUse();
      }

      public boolean canContinueToUse() {
         return !this.doMerge && super.canContinueToUse();
      }

      public void start() {
         if (!this.doMerge) {
            super.start();
         } else {
            Level level = this.mob.level();
            BlockPos blockpos = BlockPos.containing(this.mob.getX(), this.mob.getY() + 0.5, this.mob.getZ()).relative(this.facing);
            BlockState state = level.getBlockState(blockpos);
            if (state.is(TFBlocks.TOWERWOOD) && this.mob.getRandom().nextInt(5) == 0) {
               level.setBlock(blockpos, ((Block)TFBlocks.INFESTED_TOWERWOOD.get()).defaultBlockState(), 3);
               this.mob.spawnAnim();
               this.mob.discard();
            }
         }
      }
   }

   private static class SummonBorersGoal extends Goal {
      private final TowerwoodBorer borer;
      private int lookForFriends;

      public SummonBorersGoal(TowerwoodBorer borer) {
         this.borer = borer;
      }

      public void notifyHurt() {
         if (this.lookForFriends == 0) {
            this.lookForFriends = 20;
         }
      }

      public boolean canUse() {
         return this.lookForFriends > 0;
      }

      public void tick() {
         this.lookForFriends--;
         if (this.lookForFriends <= 0) {
            Level world = this.borer.level();
            RandomSource random = this.borer.getRandom();
            BlockPos pos = new BlockPos(this.borer.blockPosition());

            for (int i = 0; i <= 5 && i >= -5; i = (i <= 0 ? 1 : 0) - i) {
               for (int j = 0; j <= 10 && j >= -10; j = (j <= 0 ? 1 : 0) - j) {
                  for (int k = 0; k <= 10 && k >= -10; k = (k <= 0 ? 1 : 0) - k) {
                     BlockPos offsetPos = pos.offset(j, i, k);
                     BlockState state = world.getBlockState(offsetPos);
                     if (state.is(TFBlocks.INFESTED_TOWERWOOD)) {
                        if (EventHooks.canEntityGrief(world, this.borer)) {
                           world.destroyBlock(offsetPos, true);
                           this.borer.gameEvent(GameEvent.BLOCK_DESTROY);
                        } else {
                           world.setBlock(offsetPos, ((Block)TFBlocks.TOWERWOOD.get()).defaultBlockState(), 3);
                        }

                        if (random.nextBoolean()) {
                           return;
                        }
                     }
                  }
               }
            }
         }
      }
   }
}
