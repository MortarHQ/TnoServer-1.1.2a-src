package twilightforest.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.event.EventHooks;
import twilightforest.entity.boss.Naga;
import twilightforest.init.TFSounds;
import twilightforest.util.entities.EntityUtil;

public class NagaMovementPattern extends Goal {
   private final Naga naga;
   private NagaMovementPattern.MovementState state;
   private int stateCounter;
   private boolean clockwise;
   private boolean stunCalculated;

   public NagaMovementPattern(Naga naga) {
      this.naga = naga;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      this.stop();
   }

   public boolean canUse() {
      return this.naga.getTarget() != null && this.naga.areSelfAndTargetInHome(this.naga.getTarget());
   }

   public void stop() {
      this.state = NagaMovementPattern.MovementState.CIRCLE;
      this.stateCounter = 15;
      this.clockwise = false;
   }

   public void tick() {
      if (!this.naga.getNavigation().isDone()) {
         if (this.naga.getNavigation().isStuck()) {
            this.naga.getNavigation().stop();
         }
      } else {
         if (this.stateCounter-- <= 0) {
            this.transitionState();
         } else {
            switch (this.state) {
               case INTIMIDATE:
                  this.naga.getNavigation().stop();
                  if (this.naga.getTarget() != null) {
                     this.naga.getLookControl().setLookAt(this.naga.getTarget(), 30.0F, 30.0F);
                     this.naga.lookAt(this.naga.getTarget(), 30.0F, 30.0F);
                  }

                  this.naga.zza = 0.1F;
                  if (!this.stunCalculated) {
                     float healthRatio = 1.0F - this.naga.getHealth() / this.naga.getMaxHealth() - 0.25F;
                     float chance = Mth.clamp(
                        healthRatio + this.naga.level().getCurrentDifficultyAt(this.naga.blockPosition()).getDifficulty().getId() * 0.05F, 0.0F, 0.5F
                     );
                     float randChance = this.naga.getRandom().nextFloat() * 0.75F;
                     boolean stunless = randChance < chance;
                     this.naga.setStunlessCharging(stunless);
                     this.stunCalculated = true;
                  }
                  break;
               case CRUMBLE:
                  this.naga.getNavigation().stop();
                  this.crumbleBelowTarget(2);
                  this.crumbleBelowTarget(3);
                  break;
               case CHARGE:
               case STUNLESS_CHARGE:
                  if (this.naga.getTarget() != null) {
                     BlockPos tpoint = this.findCirclePoint(this.clockwise, 5.0, Math.PI);
                     this.naga.getNavigation().moveTo(tpoint.getX(), tpoint.getY(), tpoint.getZ(), 1.5);
                  }

                  this.naga.setCharging(true);
                  break;
               case CIRCLE:
                  this.naga.setCharging(false);
                  this.naga.setDazed(false);
                  double radius = this.stateCounter % 2 == 0 ? 12.0 : 14.0;
                  double rotation = 1.0;
                  if (this.stateCounter == 2) {
                     radius = 16.0;
                  }

                  if (this.stateCounter == 1) {
                     rotation = 0.1;
                  }

                  if (this.naga.getTarget() != null) {
                     BlockPos tpoint = this.findCirclePoint(this.clockwise, radius, rotation);
                     this.naga.getNavigation().moveTo(tpoint.getX(), tpoint.getY(), tpoint.getZ(), 1.0);
                  }
                  break;
               case DAZE:
                  this.naga.getNavigation().stop();
                  this.naga.setDazed(true);
                  this.naga.setCharging(false);
            }
         }
      }
   }

   private void transitionState() {
      switch (this.state) {
         case INTIMIDATE:
            this.clockwise = !this.clockwise;
            if (this.naga.getTarget() != null && this.naga.getTarget().getBoundingBox().minY > this.naga.getBoundingBox().maxY) {
               this.doCrumblePlayer();
            } else {
               this.doCharge(this.naga.isStunlessCharging());
            }
            break;
         case CRUMBLE:
            this.doCharge(this.naga.isStunlessCharging());
            break;
         case CHARGE:
         case STUNLESS_CHARGE:
         case DAZE:
            this.doCircle();
            break;
         case CIRCLE:
            this.doIntimidate();
      }
   }

   public void doDaze() {
      this.state = NagaMovementPattern.MovementState.DAZE;
      this.naga.getNavigation().stop();
      this.stateCounter = 60 + this.naga.getRandom().nextInt(40);
   }

   public void doCircle() {
      this.state = NagaMovementPattern.MovementState.CIRCLE;
      this.stateCounter = this.stateCounter + 10 + this.naga.getRandom().nextInt(10);
      this.stunCalculated = false;
   }

   public void forceCircle() {
      this.state = NagaMovementPattern.MovementState.CIRCLE;
      this.stateCounter = 10 + this.naga.getRandom().nextInt(10);
      this.stunCalculated = false;
   }

   public void doCrumblePlayer() {
      this.state = NagaMovementPattern.MovementState.CRUMBLE;
      this.naga.getNavigation().stop();
      this.stateCounter = 20 + this.naga.getRandom().nextInt(20);
   }

   private void doCharge(boolean stunless) {
      this.state = stunless ? NagaMovementPattern.MovementState.STUNLESS_CHARGE : NagaMovementPattern.MovementState.CHARGE;
      this.stateCounter = 2;
   }

   private void doIntimidate() {
      this.state = NagaMovementPattern.MovementState.INTIMIDATE;
      this.naga.playSound((SoundEvent)TFSounds.NAGA_RATTLE.get(), 4.0F, this.naga.getVoicePitch());
      this.naga.gameEvent(GameEvent.ENTITY_ACTION);
      this.stateCounter = this.stateCounter + 15 + this.naga.getRandom().nextInt(10);
   }

   private void crumbleBelowTarget(int range) {
      if (EventHooks.canEntityGrief(this.naga.level(), this.naga) && this.naga.getTarget() != null) {
         int floor = (int)this.naga.getBoundingBox().minY;
         int targetY = (int)this.naga.getTarget().getBoundingBox().minY;
         if (targetY > floor) {
            int dx = (int)this.naga.getTarget().getX() + this.naga.getRandom().nextInt(range) - this.naga.getRandom().nextInt(range);
            int dz = (int)this.naga.getTarget().getZ() + this.naga.getRandom().nextInt(range) - this.naga.getRandom().nextInt(range);
            int dy = targetY - this.naga.getRandom().nextInt(range) + this.naga.getRandom().nextInt(range > 1 ? range - 1 : range);
            if (dy <= floor) {
               dy = targetY;
            }

            BlockPos pos = new BlockPos(dx, dy, dz);
            if (EntityUtil.canDestroyBlock(this.naga.level(), pos, this.naga)) {
               this.naga.level().destroyBlock(pos, true);

               for (int k = 0; k < 20; k++) {
                  double d = this.naga.getRandom().nextGaussian() * 0.02;
                  double d1 = this.naga.getRandom().nextGaussian() * 0.02;
                  double d2 = this.naga.getRandom().nextGaussian() * 0.02;
                  this.naga
                     .level()
                     .addParticle(
                        ParticleTypes.CRIT,
                        this.naga.getX() + this.naga.getRandom().nextFloat() * this.naga.getBbWidth() * 2.0F - this.naga.getBbWidth(),
                        this.naga.getY() + this.naga.getRandom().nextFloat() * this.naga.getBbHeight(),
                        this.naga.getZ() + this.naga.getRandom().nextFloat() * this.naga.getBbWidth() * 2.0F - this.naga.getBbWidth(),
                        d,
                        d1,
                        d2
                     );
               }
            }
         }
      }
   }

   private BlockPos findCirclePoint(boolean clockwise, double radius, double rotation) {
      LivingEntity toCircle = this.naga.getTarget();
      double vecx = this.naga.getX() - toCircle.getX();
      double vecz = this.naga.getZ() - toCircle.getZ();
      float rangle = (float)Math.atan2(vecz, vecx);
      rangle += (float)(clockwise ? rotation : -rotation);
      double dx = Mth.cos(rangle) * radius;
      double dz = Mth.sin(rangle) * radius;
      double dy = Math.min(this.naga.getBoundingBox().minY, toCircle.getY());
      return BlockPos.containing(toCircle.getX() + dx, dy, toCircle.getZ() + dz);
   }

   public NagaMovementPattern.MovementState getState() {
      return this.state;
   }

   public enum MovementState {
      INTIMIDATE,
      CRUMBLE,
      CHARGE,
      STUNLESS_CHARGE,
      CIRCLE,
      DAZE;
   }
}
