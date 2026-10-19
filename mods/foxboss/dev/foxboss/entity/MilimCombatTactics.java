package dev.foxboss.entity;

import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
import io.github.manasmods.manascore.skill.impl.TickingSkill;
import io.github.manasmods.tensura.ability.magic.Magic;
import io.github.manasmods.tensura.config.ability.skill.ExtraSkillConfig;
import io.github.manasmods.tensura.config.ability.skill.ExtraSkillConfig.SpatialMotion;
import io.github.manasmods.tensura.entity.projectile.magic.AuraBulletProjectile;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ability.IAbility;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

final class MilimCombatTactics {
   private static final int SLOT = 2;
   private static final String[] PASSIVES = new String[]{
      "magic_sense", "dragon_ear", "infinite_regeneration", "physical_attack_nullification", "magic_nullification"
   };
   private final MilimEntity milim;
   private ManasSkillInstance active;
   private int heldTicks;
   private int barrierCooldown;
   private int hakiCooldown;
   private int movementCooldown;
   private int pursuitTicks;
   private int specialCooldown;
   private static final ResourceLocation REMOVED_BULLET = ResourceLocation.fromNamespaceAndPath("tensura", "magic_bullet");

   MilimCombatTactics(MilimEntity milim) {
      this.milim = milim;
   }

   boolean busy() {
      return this.active != null;
   }

   void cancelForRetarget() {
      this.release(false);
      this.preferPursuit(40);
   }

   boolean prefersPursuit() {
      return this.pursuitTicks > 0 || this.specialCooldown > 0;
   }

   void preferPursuit(int ticks) {
      this.pursuitTicks = Math.max(this.pursuitTicks, ticks);
   }

   void tick() {
      this.removeLegacyBullet();
      if (this.barrierCooldown > 0) {
         this.barrierCooldown--;
      }

      if (this.hakiCooldown > 0) {
         this.hakiCooldown--;
      }

      if (this.movementCooldown > 0) {
         this.movementCooldown--;
      }

      if (this.pursuitTicks > 0) {
         this.pursuitTicks--;
      }

      if (this.specialCooldown > 0) {
         this.specialCooldown--;
      }

      if (this.milim.isAlive() && !this.milim.isNoAi() && !this.milim.isDemo() && !this.milim.isDebugAnimation() && !this.milim.isOrderedToSit()) {
         if (this.milim.tickCount % 20 == 0) {
            for (String name : PASSIVES) {
               ManasSkillInstance skill = this.skill(name);
               if (skill != null && !skill.isToggled() && skill.canBeToggled(this.milim) && skill.canInteractSkill(this.milim)) {
                  skill.setToggled(true);
                  skill.onToggleOn(this.milim);
                  SkillAPI.getSkillsFrom(this.milim).checkAndMarkDirty(skill);
               }
            }

            ManasSkillInstance sense = this.skill("magic_sense");
            if (this.active == null && this.milim.hasCombatTarget() && sense != null && !sense.isToggled() && this.milim.tickCount % 100 == 20) {
               this.press("magic_sense");
            }
         }

         if (this.active == null
            && this.milim.hasCombatTarget()
            && this.barrierCooldown <= 0
            && (this.milim.action() == 0 || this.milim.isCombatMelee())
            && this.milim.getAttributeValue(TensuraAttributes.MULTILAYER_BARRIER) <= 0.0) {
            this.barrierCooldown = 160;
            this.press("multilayer_barrier");
         }

         if (this.active != null) {
            LivingEntity target = this.milim.getTarget();
            if (target != null && target.isAlive() && this.milim.canAttack(target) && this.active.canInteractSkill(this.milim)) {
               Optional<TickingSkill> held = SkillAPI.getSkillsFrom(this.milim)
                  .getHeldSkills()
                  .stream()
                  .filter(s -> s.getSkill() == this.active.getSkill() && s.getKeyNumber() == 2)
                  .findFirst();
               if (held.isEmpty()) {
                  this.release(false);
               } else {
                  this.heldTicks = held.get().getDuration();
                  String name = this.active.getSkillId().getPath();
                  if (name.equals("demon_lord_haki")) {
                     if (this.heldTicks >= 20 || this.milim.distanceToSqr(target) > 100.0) {
                        this.release(true);
                     }
                  } else if (name.equals("flight")) {
                     this.aim(target.position().add(0.0, target.getBbHeight() * 0.5 + 1.0, 0.0));
                     int castTime = ((Magic)this.active.getSkill()).getCastingTime(this.active, this.milim);
                     if (this.heldTicks >= castTime + 100 || this.milim.distanceToSqr(target) <= 4.0) {
                        this.release(true);
                     }
                  }
               }
            } else {
               this.release(false);
            }
         }
      } else {
         this.release(false);
      }
   }

   boolean choose(LivingEntity target, double distance) {
      if (this.active != null) {
         return true;
      }

      if (this.barrierCooldown <= 0 && this.milim.getAttributeValue(TensuraAttributes.MULTILAYER_BARRIER) <= 0.0) {
         this.barrierCooldown = 160;
         if (this.press("multilayer_barrier")) {
            return true;
         }
      }

      if (this.movementCooldown <= 0 && (distance > 576.0 || !this.milim.hasLineOfSight(target))) {
         this.movementCooldown = 120;
         if (this.blinkNear(target)) {
            return true;
         }
      }

      return this.prefersPursuit() ? false : this.tryDefensiveHaki(target);
   }

   boolean tryDefensiveHaki(LivingEntity target) {
      if (this.active == null && this.specialCooldown <= 0 && this.hakiCooldown <= 0 && !(this.milim.distanceToSqr(target) >= 100.0) && this.needsHaki(target)) {
         this.hakiCooldown = 300;
         return this.begin("demon_lord_haki");
      } else {
         return false;
      }
   }

   private boolean needsHaki(LivingEntity target) {
      if (target.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.FEAR))) {
         return false;
      } else {
         double enemyEP = TensuraStorages.getExistenceFrom(target).getEP();
         if (TensuraStorages.getExistenceFrom(this.milim).getEP() <= enemyEP * 2.0) {
            return false;
         } else {
            return this.milim.getHealth() < this.milim.getMaxHealth() * 0.5F
               ? true
               : this.milim
                     .level()
                     .getEntitiesOfClass(
                        LivingEntity.class,
                        this.milim.getBoundingBox().inflate(8.0),
                        enemy -> enemy.isAlive()
                           && this.milim.canAttack(enemy)
                           && (enemy == target || enemy.getLastHurtMob() == this.milim || enemy instanceof Mob mob && mob.getTarget() == this.milim)
                     )
                     .size()
                  >= 2;
         }
      }
   }

   private void removeLegacyBullet() {
      SkillStorage skills = SkillAPI.getSkillsFrom(this.milim);
      skills.getSkill(REMOVED_BULLET)
         .ifPresent(
            bullet -> {
               if (this.milim.level().getEntity(bullet.getOrCreateTag().getInt("BulletID")) instanceof AuraBulletProjectile projectile
                  && projectile.getOwner() == this.milim
                  && projectile.noPhysics) {
                  projectile.discard();
               }

               bullet.getOrCreateTag().putInt("BulletID", 0);
               bullet.getOrCreateTag().putInt("PowerScale", 0);

               for (TickingSkill held : List.copyOf(skills.getHeldSkills())) {
                  if (held.getSkill() == bullet.getSkill()) {
                     skills.handleSkillRelease(bullet, held.getKeyNumber(), held.getMode(), false);
                  }
               }

               skills.forgetSkill(REMOVED_BULLET, null);
               IAbility abilities = TensuraStorages.getAbilityFrom(this.milim);
               if (abilities.removeSkillFromPresets(bullet.getSkill())) {
                  abilities.markDirty();
               }
            }
         );
   }

   private boolean blinkNear(LivingEntity target) {
      ManasSkillInstance skill = this.skill("spatial_motion");
      if (skill == null) {
         return false;
      } else {
         Vec3 away = this.milim.position().subtract(target.position()).multiply(1.0, 0.0, 1.0).normalize();
         this.aim(target.position().add(away.scale(2.5)).add(0.0, -0.01, 0.0));
         SpatialMotion config = ((ExtraSkillConfig)ConfigRegistry.getConfig(ExtraSkillConfig.class)).SpatialMotion;
         BlockHitResult hit = ObjectSelectionHelper.getPlayerPOVHitResult(
            this.milim.level(), this.milim, Fluid.NONE, skill.isMastered(this.milim) ? config.blinkRangeMastered : config.blinkRange
         );
         if (hit.getType() == Type.BLOCK && hit.getDirection() == Direction.UP) {
            BlockPos pos = hit.getBlockPos().relative(hit.getDirection());
            Vec3 destination = Vec3.atBottomCenterOf(pos);
            AABB box = this.milim.getBoundingBox().move(destination.subtract(this.milim.position()));
            return this.milim.level().hasChunkAt(pos)
                  && this.milim.level().getWorldBorder().isWithinBounds(box)
                  && this.milim.level().noCollision(this.milim, box)
                  && !(destination.distanceToSqr(this.milim.position()) < 9.0)
               ? this.press("spatial_motion")
               : false;
         } else {
            return false;
         }
      }
   }

   private void aim(Vec3 point) {
      this.milim.getLookControl().setLookAt(point.x, point.y, point.z, 360.0F, 360.0F);
      Vec3 delta = point.subtract(this.milim.getEyePosition());
      float yaw = (float)Math.toDegrees(Mth.atan2(delta.z, delta.x)) - 90.0F;
      this.milim.setYRot(yaw);
      this.milim.setYHeadRot(yaw);
      this.milim.setYBodyRot(yaw);
      this.milim.setXRot((float)(-Math.toDegrees(Mth.atan2(delta.y, delta.horizontalDistance()))));
   }

   private ManasSkillInstance skill(String name) {
      return (ManasSkillInstance)SkillAPI.getSkillsFrom(this.milim).getSkill(ResourceLocation.fromNamespaceAndPath("tensura", name)).orElse(null);
   }

   private boolean begin(String name) {
      ManasSkillInstance candidate = this.skill(name);
      if (candidate != null && candidate.canInteractSkill(this.milim) && !candidate.onCoolDown(0)) {
         IAbility abilities = TensuraStorages.getAbilityFrom(this.milim);
         abilities.setAbilitySlot(2, candidate.getSkill(), 0);
         abilities.markDirty();
         if (!SkillAPI.getSkillsFrom(this.milim).startHoldSkill(candidate, 2, 0)) {
            return false;
         }

         this.active = candidate;
         this.heldTicks = 0;
         return true;
      } else {
         return false;
      }
   }

   private boolean press(String name) {
      if (!this.begin(name)) {
         return false;
      }

      this.release(true);
      return true;
   }

   private boolean release(boolean fire) {
      if (this.active == null) {
         return false;
      }

      String name = this.active.getSkillId().getPath();
      SkillAPI.getSkillsFrom(this.milim).handleSkillRelease(this.active, 2, 0, false);
      this.active = null;
      this.milim.setXRot(0.0F);
      this.milim.finishTacticalFlight();
      if (!name.equals("multilayer_barrier") && !name.equals("magic_sense")) {
         this.specialCooldown = 60;
         this.preferPursuit(100);
      }

      return fire;
   }
}
