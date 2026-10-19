package net.unusual.block_factorys_bosses.entity.boss.yeti.goals;

import java.util.EnumSet;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;
import net.unusual.block_factorys_bosses.entity.boss.yeti.YetiEntity;

public class YetiChaseGoal extends Goal {
   protected final YetiEntity yeti;
   private final double speedModifier;
   private Path path;
   private double pathedTargetX;
   private double pathedTargetY;
   private double pathedTargetZ;
   private long lastCanUseCheck;

   public YetiChaseGoal(YetiEntity yeti, double speedModifier) {
      this.yeti = yeti;
      this.speedModifier = speedModifier;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean canUse() {
      if ((Integer)this.yeti.getEntityData().get(YetiEntity.DATA_IS_ENRAGED) <= 1) {
         return false;
      }

      long i = this.yeti.level().getGameTime();
      if (i - this.lastCanUseCheck < 20L) {
         return false;
      }

      this.lastCanUseCheck = i;
      LivingEntity livingentity = this.yeti.getTarget();
      if (livingentity == null) {
         return false;
      }

      if (!livingentity.isAlive()) {
         return false;
      }

      this.path = this.yeti.getNavigation().createPath(livingentity, 0);
      return this.path != null || this.yeti.isWithinMeleeAttackRange(livingentity);
   }

   public boolean canContinueToUse() {
      LivingEntity livingentity = this.yeti.getTarget();
      if (livingentity == null) {
         return false;
      } else {
         return !livingentity.isAlive() ? false : !(livingentity instanceof Player player && (player.isSpectator() || player.isCreative()));
      }
   }

   public void start() {
      this.yeti.getNavigation().moveTo(this.path, this.speedModifier);
      this.yeti.setAggressive(true);
   }

   public void stop() {
      LivingEntity livingentity = this.yeti.getTarget();
      if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
         this.yeti.setTarget(null);
      }

      this.yeti.setAggressive(false);
      this.yeti.getNavigation().stop();
   }

   public boolean requiresUpdateEveryTick() {
      return true;
   }

   public void tick() {
      LivingEntity target = this.yeti.getTarget();
      if (target != null) {
         this.yeti.getLookControl().setLookAt(target, 30.0F, 30.0F);
         if (this.pathedTargetX == 0.0 && this.pathedTargetY == 0.0 && this.pathedTargetZ == 0.0
            || target.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0
            || this.yeti.getRandom().nextFloat() < 0.05F) {
            this.pathedTargetX = target.getX();
            this.pathedTargetY = target.getY();
            this.pathedTargetZ = target.getZ();
            PathNavigation navigation = this.yeti.getNavigation();
            Path path = navigation.createPath(target, 1);
            if (path != null) {
               navigation.moveTo(path, this.speedModifier);
            }
         }
      }
   }
}
