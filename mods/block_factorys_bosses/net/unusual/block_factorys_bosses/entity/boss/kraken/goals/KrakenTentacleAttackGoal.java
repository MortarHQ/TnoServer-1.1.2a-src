package net.unusual.block_factorys_bosses.entity.boss.kraken.goals;

import java.util.EnumSet;
import java.util.function.Consumer;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.unusual.block_factorys_bosses.entity.boss.kraken.KrakenEntity;
import net.unusual.block_factorys_bosses.entity.boss.kraken.KrakenTentacleEntity;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class KrakenTentacleAttackGoal extends Goal {
   protected KrakenTentacleEntity tentacleEntity;
   protected final String attackAnimation;
   protected final float rotationRangeScale;
   private final float attackProbability;
   protected final int duration;
   protected final int lookTicks;
   protected final int hitOnTick;
   protected int tick;
   protected Consumer<KrakenTentacleEntity> attackCallback;

   public KrakenTentacleAttackGoal(
      KrakenTentacleEntity tentacleEntity,
      String attackAnimation,
      float rotationRangeScale,
      float attackProbability,
      int duration,
      int lookTicks,
      int hitOnTick,
      Consumer<KrakenTentacleEntity> attackCallback
   ) {
      this.tentacleEntity = tentacleEntity;
      this.attackAnimation = attackAnimation;
      this.rotationRangeScale = rotationRangeScale;
      this.attackProbability = attackProbability;
      this.duration = this.adjustedTickDelay(duration);
      this.lookTicks = this.adjustedTickDelay(lookTicks);
      this.hitOnTick = this.adjustedTickDelay(hitOnTick);
      this.attackCallback = attackCallback;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean isInterruptable() {
      return false;
   }

   public boolean canUse() {
      if (this.tentacleEntity.getTentacleType() != KrakenTentacleEntity.TentacleType.MELEE) {
         return false;
      }

      LivingEntity target = this.tentacleEntity.getTarget();
      if (target == null) {
         return false;
      }

      if (this.tentacleEntity.getRandom().nextFloat() >= this.attackProbability) {
         return false;
      }

      KrakenEntity kraken = this.tentacleEntity.getOwnerKraken();
      return kraken == null || !kraken.isKnockedDown() && !kraken.allTentaclesBusy()
         ? this.tentacleEntity.hasLineOfSight(target, this.tentacleEntity.getTargetRotationRange() * this.rotationRangeScale)
         : false;
   }

   public boolean canContinueToUse() {
      return this.tick < this.duration;
   }

   public void start() {
      super.start();
      if (this.tentacleEntity.getTarget() != null) {
         this.tentacleEntity.lookAt(this.tentacleEntity.getTarget(), 30.0F, 30.0F);
      }

      this.tentacleEntity.triggerAnim(this.attackAnimation);
   }

   public void stop() {
      super.stop();
      this.tick = 0;
      this.tentacleEntity.stopTriggeredAnim(this.attackAnimation);
   }

   public void tick() {
      super.tick();
      this.tick++;
      if (this.tick < this.lookTicks) {
         LivingEntity target = this.tentacleEntity.getTarget();
         if (target != null) {
            this.tentacleEntity.getLookControl().setLookAt(target);
         }
      }

      if (this.tick == this.hitOnTick) {
         this.attackCallback.accept(this.tentacleEntity);
      }
   }
}
