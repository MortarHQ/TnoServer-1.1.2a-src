package net.unusual.block_factorys_bosses.entity.boss.kraken.goals;

import java.util.Comparator;
import java.util.EnumSet;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.entity.boss.kraken.KrakenEntity;
import net.unusual.block_factorys_bosses.entity.boss.kraken.KrakenTentacleEntity;
import net.unusual.block_factorys_bosses.entity.boss.kraken.KrakenTentacleEntityPart;
import net.unusual.block_factorys_bosses.entity.decoration.CratePileEntity;
import net.unusual.block_factorys_bosses.entity.projectile.ThrownCrateEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class KrakenTentacleCrateGoal extends Goal {
   protected KrakenTentacleEntity tentacleEntity;
   protected final String attackAnimation;
   protected final float rotationRangeScale;
   protected final int duration;
   protected final int hitOnTick;
   protected int tick;
   @Nullable
   protected LivingEntity target = null;

   public KrakenTentacleCrateGoal(KrakenTentacleEntity tentacleEntity, String attackAnimation, float rotationRangeScale, int duration, int hitOnTick) {
      this.tentacleEntity = tentacleEntity;
      this.attackAnimation = attackAnimation;
      this.rotationRangeScale = rotationRangeScale;
      this.duration = this.adjustedTickDelay(duration);
      this.hitOnTick = this.adjustedTickDelay(hitOnTick);
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean isInterruptable() {
      return false;
   }

   public boolean canUse() {
      if (this.tentacleEntity.getTentacleType() != KrakenTentacleEntity.TentacleType.CRATE) {
         return false;
      }

      LivingEntity target = this.tentacleEntity.getTarget();
      if (target == null) {
         return false;
      }

      if (this.tentacleEntity.getRandom().nextFloat() >= 0.2F) {
         return false;
      }

      KrakenEntity kraken = this.tentacleEntity.getOwnerKraken();
      return kraken == null || !kraken.isKnockedDown() && !kraken.allTentaclesBusy()
         ? this.tentacleEntity.hasLineOfSight(target, this.tentacleEntity.getTargetRotationRange() * this.rotationRangeScale)
         : false;
   }

   public boolean canContinueToUse() {
      return this.target != null && !this.target.isDeadOrDying() ? this.tick < this.duration : false;
   }

   public void start() {
      super.start();
      this.target = this.tentacleEntity.getTarget();
      this.tentacleEntity.triggerAnim(this.attackAnimation);
      if (this.target != null) {
         this.target
            .level()
            .getEntitiesOfClass(CratePileEntity.class, this.tentacleEntity.getBoundingBox().inflate(32.0))
            .stream()
            .min(Comparator.comparingDouble(value -> value.distanceTo(this.tentacleEntity)))
            .ifPresent(crateEntity -> {
               this.tentacleEntity.getEntityData().set(KrakenTentacleEntity.DATA_LERP_POSITION, crateEntity.position());
               this.tentacleEntity.getEntityData().set(KrakenTentacleEntity.DATA_LERP_TIME, this.tentacleEntity.level().getGameTime());
            });
      }
   }

   public void stop() {
      super.stop();
      this.tick = 0;
      this.target = null;
      this.tentacleEntity.getEntityData().set(KrakenTentacleEntity.DATA_LERP_POSITION, Vec3.ZERO);
      this.tentacleEntity.getEntityData().set(KrakenTentacleEntity.DATA_LERP_TIME, 0L);
      this.tentacleEntity.stopTriggeredAnim(this.attackAnimation);
   }

   public void tick() {
      super.tick();
      this.tick++;
      if (this.target != null) {
         this.tentacleEntity.getLookControl().setLookAt(this.target);
      }

      if (this.tick == this.hitOnTick) {
         this.performHit();
      }
   }

   public void performHit() {
      if (this.target != null && this.tentacleEntity.level() instanceof ServerLevel level) {
         KrakenTentacleEntityPart var14 = this.tentacleEntity.getThrowPart();
         ThrownCrateEntity snowball = new ThrownCrateEntity((EntityType<? extends ThrownCrateEntity>)BossesRiseEntities.THROWN_CRATE.get(), level);
         snowball.setOwner(this.tentacleEntity);
         snowball.setPos(var14.getX(), var14.getEyeY(), var14.getZ());
         double d0 = this.target.getEyeY() - 1.1F;
         double d1 = this.target.getX() - var14.getX();
         double d2 = d0 - snowball.getY();
         double d3 = this.target.getZ() - var14.getZ();
         double d4 = Math.sqrt(d1 * d1 + d3 * d3) * 0.2F;
         snowball.shoot(d1, d2 + d4, d3, 3.2F, 6.0F);
         this.tentacleEntity.playSound(SoundEvents.SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.tentacleEntity.getRandom().nextFloat() * 0.4F + 0.8F));
         level.addFreshEntity(snowball);
      }
   }
}
