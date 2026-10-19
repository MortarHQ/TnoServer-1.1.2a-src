package twilightforest.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import twilightforest.entity.boss.Minoshroom;
import twilightforest.init.TFDamageTypes;
import twilightforest.init.TFSounds;

public class GroundAttackGoal extends Goal {
   private static final double MIN_RANGE_SQ = 2.0;
   private static final double MAX_RANGE_SQ = 9.0;
   private static final int FREQ = 24;
   private final Minoshroom attacker;
   private LivingEntity attackTarget;
   private int cooldown;
   private int attackTick;

   public GroundAttackGoal(Minoshroom minoshroom) {
      this.attacker = minoshroom;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean canUse() {
      this.attackTarget = this.attacker.getTarget();
      if (this.cooldown-- <= 0 && this.attackTarget != null) {
         double distance = this.attacker.distanceToSqr(this.attackTarget);
         if (distance < 2.0 || distance > 9.0) {
            return false;
         } else if (!this.attacker.onGround()) {
            return false;
         } else {
            return this.attacker.hasLineOfSight(this.attackTarget) ? this.attacker.getRandom().nextInt(24) == 0 : this.attacker.getRandom().nextInt(20) == 0;
         }
      } else {
         return false;
      }
   }

   public void start() {
      this.cooldown = 200 + this.attacker.getRandom().nextInt(200);
      this.attackTick = 30 + this.attacker.getRandom().nextInt(30);
      this.attacker.setMaxCharge(this.attackTick);
      this.attacker.setGroundAttackCharge(true);
   }

   public boolean canContinueToUse() {
      return this.attackTick >= 0;
   }

   public void stop() {
      this.attackTick = 0;
      this.attackTarget = null;
   }

   public void tick() {
      this.attacker.getLookControl().setLookAt(this.attackTarget, 30.0F, 30.0F);
      this.attacker.getMoveControl().operation = Operation.WAIT;
      if (this.attackTick-- <= 0) {
         this.attacker.setGroundAttackCharge(false);
         this.attacker.playSound((SoundEvent)TFSounds.MINOSHROOM_SLAM.get(), 2.0F, 1.0F + this.attacker.getRandom().nextFloat() * 0.1F);
         this.attacker.gameEvent(GameEvent.HIT_GROUND);
         AABB selection = new AABB(
            this.attacker.blockPosition().getX() - 7.5F,
            this.attacker.blockPosition().getY(),
            this.attacker.blockPosition().getZ() - 7.5F,
            this.attacker.blockPosition().getX() + 7.5F,
            this.attacker.blockPosition().getY() + 3.0F,
            this.attacker.blockPosition().getZ() + 7.5F
         );

         for (Entity entity : this.attacker.level().getEntitiesOfClass(Entity.class, selection, entityx -> entityx instanceof Player)) {
            if (entity.onGround()) {
               entity.push(0.0, 0.23, 0.0);
               entity.hurt(
                  TFDamageTypes.getEntityDamageSource(this.attacker.level(), TFDamageTypes.SLAM, this.attacker),
                  (float)(this.attacker.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.5)
               );
            }
         }
      }
   }
}
