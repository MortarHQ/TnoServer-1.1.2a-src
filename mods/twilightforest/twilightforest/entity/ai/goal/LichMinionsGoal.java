package twilightforest.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import twilightforest.entity.boss.Lich;
import twilightforest.entity.monster.LichMinion;
import twilightforest.entity.projectile.LichBolt;
import twilightforest.entity.projectile.LichBomb;
import twilightforest.init.TFItems;
import twilightforest.init.TFSounds;

public class LichMinionsGoal extends Goal {
   public static final float ATTACK_RANGE = 20.0F;
   private final Lich lich;
   private int seeTime;
   private boolean strafingClockwise;
   private boolean strafingBackwards;
   private int strafingTime;

   public LichMinionsGoal(Lich boss) {
      this.lich = boss;
      this.strafingTime = -1;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean canUse() {
      return this.lich.getPhase() == 2 && !this.lich.isShadowClone();
   }

   public void start() {
      this.lich.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)TFItems.ZOMBIE_SCEPTER.get()));
   }

   public void stop() {
      super.stop();
      this.seeTime = 0;
   }

   public void tick() {
      if (this.lich.getTeleportInvisibility() <= 0) {
         LivingEntity targetedEntity = this.lich.getTarget();
         if (targetedEntity != null) {
            float dist = this.lich.distanceTo(targetedEntity);
            if (this.lich.getAttackCooldown() % 15 == 0) {
               this.checkAndSpawnMinions();
            }

            boolean hasLineOfSight = this.lich.getSensing().hasLineOfSight(targetedEntity);
            if (hasLineOfSight != this.seeTime > 0) {
               this.seeTime = 0;
            }

            if (hasLineOfSight) {
               this.seeTime++;
            } else {
               this.seeTime--;
            }

            if (dist < 20.0F && this.seeTime >= 20) {
               this.lich.getNavigation().stop();
               this.strafingTime++;
            } else {
               this.strafingTime = -1;
            }

            if (this.strafingTime >= 20) {
               if (this.lich.getRandom().nextFloat() < 0.3) {
                  this.strafingClockwise = !this.strafingClockwise;
               }

               if (this.lich.getRandom().nextFloat() < 0.3) {
                  this.strafingBackwards = !this.strafingBackwards;
               }

               this.strafingTime = 0;
            }

            if (this.strafingTime > -1) {
               if (dist > 15.0) {
                  this.strafingBackwards = false;
               } else if (dist < 5.0) {
                  this.strafingBackwards = true;
               }

               this.lich.getMoveControl().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
               this.lich.lookAt(targetedEntity, 30.0F, 30.0F);
            } else {
               this.lich.getLookControl().setLookAt(targetedEntity, 30.0F, 30.0F);
            }

            if (this.lich.getAttackCooldown() == 0) {
               if (dist < 2.0F) {
                  this.lich.doHurtTarget(targetedEntity);
                  this.lich.swing(InteractionHand.MAIN_HAND);
                  this.lich.setAttackCooldown(20);
               } else if (dist < 20.0F && this.lich.getSensing().hasLineOfSight(targetedEntity)) {
                  if (this.lich.getNextAttackType() == 0) {
                     this.lich.launchProjectileAt(new LichBolt(this.lich.level(), this.lich));
                  } else {
                     this.lich.launchProjectileAt(new LichBomb(this.lich.level(), this.lich));
                  }

                  this.lich.swing(InteractionHand.MAIN_HAND);
                  this.lich.setNextAttackType(this.lich.getRandom().nextBoolean() ? 0 : 1);
                  this.lich.setAttackCooldown(60);
               } else {
                  this.lich.teleportToNewTarget(targetedEntity, 20.0F, null);
                  this.lich.setAttackCooldown(20);
               }
            }
         }
      }
   }

   private void checkAndSpawnMinions() {
      if (!this.lich.level().isClientSide() && this.lich.getMinionsToSummon() > 0) {
         int minions = this.lich.countMyMinions();
         if (minions < 3) {
            this.spawnMinionAt();
            this.lich.setMinionsToSummon(this.lich.getMinionsToSummon() - 1);
         }
      }
   }

   private void spawnMinionAt() {
      LivingEntity targetedEntity = this.lich.getTarget();
      Vec3 minionSpot = this.lich.findVecInLOSOf(targetedEntity);
      if (minionSpot != null && this.lich.level() instanceof ServerLevelAccessor accessor) {
         LichMinion minion = new LichMinion(this.lich.level(), this.lich);
         minion.setPos(minionSpot.x(), minionSpot.y(), minionSpot.z());
         EventHooks.finalizeMobSpawn(
            minion, accessor, this.lich.level().getCurrentDifficultyAt(BlockPos.containing(minionSpot)), MobSpawnType.MOB_SUMMONED, null
         );
         this.lich.level().addFreshEntity(minion);
         minion.setTarget(targetedEntity);
         minion.spawnAnim();
         minion.playSound(
            (SoundEvent)TFSounds.MINION_SUMMON.get(), 1.0F, ((this.lich.getRandom().nextFloat() - this.lich.getRandom().nextFloat()) * 0.7F + 1.0F) * 0.75F
         );
         this.lich.swing(InteractionHand.MAIN_HAND);
         this.lich
            .makeMagicTrail(
               this.lich.getEyePosition(),
               minion.getEyePosition(),
               this.lich.getRandom().nextFloat() * 0.0625F + 0.125F,
               this.lich.getRandom().nextFloat() * 0.0625F + 0.125F,
               this.lich.getRandom().nextFloat() * 0.0625F + 0.125F
            );
      }
   }
}
