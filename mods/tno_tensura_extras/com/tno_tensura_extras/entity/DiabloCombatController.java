package com.tno_tensura_extras.entity;

import com.mojang.datafixers.util.Pair;
import com.tno_tensura_extras.event.DiabloCombatEvents;
import com.tno_tensura_extras.registry.ExtrasEntityTypes;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.entity.TensuraProjectile;
import io.github.manasmods.tensura.entity.magic.MagicCircle;
import io.github.manasmods.tensura.entity.variant.MagicCircleVariant;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.Iterator;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

final class DiabloCombatController {
   private final DiabloEntity diablo;
   @Nullable
   private LivingEntity actionTarget;
   @Nullable
   private DiabloAttackAnimation attack;
   @Nullable
   private DiabloCombatController.Spell spell;
   @Nullable
   private MagicCircle circle;
   @Nullable
   private Vec3 dodgeDestination;
   private int actionTick;
   private int castDelay;
   private int meleeCount;
   private int spellCursor;
   private int combatTicks;
   private long nextBlock;
   private long counterAt;
   private long nextDecoy;
   private boolean comboHitLanded;
   private long nextMelee;
   private long nextSpell;
   private long nextDodge;
   private long dodgeEnds;
   private long retreatUntil;
   private long nextMove;
   private int strafeSide = 1;

   DiabloCombatController(DiabloEntity diablo) {
      this.diablo = diablo;
   }

   void tick() {
      LivingEntity target = this.diablo.getTarget();
      if (!this.diablo.isAlive()
         || this.diablo.isNoAi()
         || this.diablo.isOrderedToSit()
         || this.diablo.isSleeping()
         || this.diablo.isPassenger()
         || !this.diablo.isAllowedCombatTarget(target)) {
         this.stop();
      } else if (this.diablo.isSkillChanneling()) {
         this.cancelAction();
         this.dodgeDestination = null;
      } else {
         long now = this.diablo.level().getGameTime();
         this.combatTicks++;
         if (this.counterAt != 0L) {
            if (this.actionTarget == target) {
               this.clearWalk();
               this.face(target);
               if (now >= this.counterAt) {
                  this.counterAt = 0L;
                  this.beginMelee(target, DiabloAttackAnimation.CROSS);
               }

               return;
            }

            this.cancelAction();
         }

         if (this.dodgeDestination != null) {
            if (now < this.dodgeEnds && !(this.diablo.position().distanceToSqr(this.dodgeDestination) < 0.09)) {
               this.clearWalk();
               this.face(target);
               return;
            }

            this.dodgeDestination = null;
            this.nextMove = 0L;
         }

         if (now < this.nextDodge || this.diablo.tickCount % 5 != 0 || !this.tryDodge(now)) {
            this.face(target);
            if (this.actionTarget != null && this.actionTarget != target) {
               this.cancelAction();
            }

            if (this.attack != null) {
               this.tickMelee(now);
            } else if (this.spell != null) {
               this.tickSpell(now);
            } else if (!this.diablo.isAnalyzingMagic()) {
               boolean close = this.diablo.isWithinMeleeAttackRange(target);
               if (target instanceof ServerPlayer player
                  && this.combatTicks >= 80
                  && now >= this.nextDecoy
                  && this.diablo.distanceToSqr(player) <= 144.0
                  && this.diablo.hasLineOfSight(player)
                  && this.tryDecoy(player, now)) {
                  return;
               }

               if ((now < this.nextSpell || close && now >= this.retreatUntil || !this.startSpell(target))
                  && close
                  && now >= this.nextMelee
                  && (now >= this.retreatUntil || this.diablo.isCombatPathBlocked())) {
                  this.startMelee(target);
               }
            }

            if (now >= this.nextMove && !this.isDodging()) {
               this.updateSpacing(target, now);
               this.nextMove = now + 8L;
            }
         }
      }
   }

   private void startMelee(LivingEntity target) {
      double distance = this.diablo.distanceToSqr(target);
      if (target.getY() > this.diablo.getY() + 0.7) {
         this.attack = DiabloAttackAnimation.UPPERCUT;
      } else if (distance > 4.0) {
         this.attack = DiabloAttackAnimation.THRUST;
      } else if (this.meleeCount % 7 == 6) {
         this.attack = DiabloAttackAnimation.HEAVY;
      } else if (this.meleeCount % 5 == 4) {
         this.attack = DiabloAttackAnimation.COMBO;
      } else if (this.meleeCount % 3 == 2) {
         this.attack = DiabloAttackAnimation.CROSS;
      } else {
         this.attack = this.meleeCount % 2 == 0 ? DiabloAttackAnimation.SLASH_RIGHT : DiabloAttackAnimation.SLASH_LEFT;
      }

      this.beginMelee(target, this.attack);
   }

   private void beginMelee(LivingEntity target, DiabloAttackAnimation animation) {
      this.attack = animation;
      this.actionTarget = target;
      this.actionTick = 0;
      this.comboHitLanded = false;
      this.diablo.playCombatAnimation(this.attack.animation);
   }

   boolean tryBlock(DamageSource source) {
      if (source.getDirectEntity() instanceof ServerPlayer player
         && source.is(DamageTypes.PLAYER_ATTACK)
         && !this.diablo.isNoAi()
         && !this.diablo.isOrderedToSit()
         && !this.diablo.isSleeping()
         && !this.diablo.isPassenger()
         && !this.diablo.isSkillChanneling()
         && !this.diablo.isAnalyzingMagic()
         && this.spell == null
         && !this.isDodging()
         && this.counterAt == 0L
         && this.diablo.isAllowedCombatTarget(player)
         && this.diablo.isWithinMeleeAttackRange(player)
         && this.diablo.hasLineOfSight(player)) {
         long now = this.diablo.level().getGameTime();
         if (now >= this.nextBlock && (this.attack == null || this.actionTick > this.attack.hitTicks[this.attack.hitTicks.length - 1])) {
            Vec3 towards = player.position().subtract(this.diablo.position()).multiply(1.0, 0.0, 1.0);
            if (!inFront(this.diablo.getLookAngle(), towards)) {
               return false;
            }

            this.cancelAction();
            BrainUtils.setTargetOfEntity(this.diablo, player);
            this.actionTarget = player;
            this.counterAt = now + 6L;
            this.nextBlock = now + 100L;
            this.retreatUntil = 0L;
            this.clearWalk();
            this.face(player);
            this.diablo.playCombatAnimation("claw_block");
            this.diablo.playSound(SoundEvents.SHIELD_BLOCK, 1.0F, 1.0F);
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   static boolean inFront(Vec3 facing, Vec3 offset) {
      Vec3 horizontalFacing = facing.multiply(1.0, 0.0, 1.0).normalize();
      Vec3 horizontalOffset = offset.multiply(1.0, 0.0, 1.0).normalize();
      return horizontalFacing.lengthSqr() > 0.01 && horizontalOffset.lengthSqr() > 0.01 && horizontalFacing.dot(horizontalOffset) >= 0.5;
   }

   private boolean tryDecoy(ServerPlayer target, long now) {
      DiabloDecoyEntity decoy = (DiabloDecoyEntity)ExtrasEntityTypes.DIABLO_DECOY.get().create(this.diablo.level());
      this.nextDecoy = now + 40L;
      if (decoy == null) {
         return false;
      }

      decoy.moveTo(this.diablo.getX(), this.diablo.getY(), this.diablo.getZ(), this.diablo.getYRot(), this.diablo.getXRot());
      decoy.initialize(this.diablo, target);
      if (!this.diablo.blinkBehind(target)) {
         return false;
      }

      this.diablo.level().addFreshEntity(decoy);
      this.nextDecoy = now + 400L;
      this.retreatUntil = 0L;
      this.clearWalk();
      this.face(target);
      this.beginMelee(target, DiabloAttackAnimation.THRUST);
      return true;
   }

   private void tickMelee(long now) {
      this.actionTick++;
      if (this.attack.hitsAt(this.actionTick)
         && this.diablo.isAllowedCombatTarget(this.actionTarget)
         && this.diablo.isWithinMeleeAttackRange(this.actionTarget)
         && this.diablo.hasLineOfSight(this.actionTarget)) {
         int previousInvulnerability = this.actionTarget.invulnerableTime;
         boolean followUp = this.attack == DiabloAttackAnimation.COMBO && this.comboHitLanded && this.actionTarget.getLastHurtByMob() == this.diablo;
         if (followUp) {
            this.actionTarget.invulnerableTime = 0;
         }

         boolean landed;
         try {
            landed = this.diablo.strike(this.actionTarget, this.attack);
         } finally {
            if (followUp) {
               this.actionTarget.invulnerableTime = Math.max(previousInvulnerability, this.actionTarget.invulnerableTime);
            }
         }

         this.comboHitLanded |= landed;
      }

      if (this.actionTick >= this.attack.duration) {
         this.attack = null;
         this.actionTarget = null;
         this.nextMelee = now + 1L;
         if (++this.meleeCount % 2 == 0) {
            this.retreatUntil = now + 70L;
            this.nextMove = 0L;
            Vec3 away = this.diablo.position().subtract(this.diablo.getTarget().position()).multiply(1.0, 0.0, 1.0).normalize();
            if (now >= this.nextDodge) {
               this.startDodge(away, now);
            }
         }
      }
   }

   @Nullable
   private ManasSkillInstance ready(DiabloCombatController.Spell choice) {
      return SkillAPI.getSkillsFrom(this.diablo)
         .getSkill(ResourceLocation.fromNamespaceAndPath("tensura", choice.id))
         .filter(instance -> instance.canInteractSkill(this.diablo) && !instance.onCoolDown(0))
         .orElse(null);
   }

   private boolean startSpell(LivingEntity target) {
      if (this.diablo.canCastMagics(this.diablo) && this.diablo.hasLineOfSight(target)) {
         double distance = this.diablo.distanceToSqr(target);
         DiabloCombatController.Spell[] choices = DiabloCombatController.Spell.values();

         for (int offset = 0; offset < choices.length; offset++) {
            int index = (this.spellCursor + offset) % choices.length;
            DiabloCombatController.Spell choice = choices[index];
            if (!(distance > choice.range * choice.range)
               && (choice != DiabloCombatController.Spell.MUD && choice != DiabloCombatController.Spell.WATER || target.onGround())
               && (
                  choice != DiabloCombatController.Spell.BARRIER
                     || !(this.diablo.getHealth() > this.diablo.getMaxHealth() * 0.75F)
                        && !this.diablo.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_BARRIER))
               )) {
               ManasSkillInstance instance = this.ready(choice);
               if (instance != null) {
                  if (EnergyHelper.isOutOfEnergy(this.diablo, instance, 0)) {
                     return false;
                  }

                  this.spell = choice;
                  this.spellCursor = (index + 1) % choices.length;
                  this.actionTarget = target;
                  this.actionTick = 0;
                  boolean instant = SkillAPI.getSkillsFrom(this.diablo)
                     .getSkill(ResourceLocation.fromNamespaceAndPath("tensura", "chant_annulment"))
                     .filter(skill -> skill.isToggled() && skill.canInteractSkill(this.diablo))
                     .isPresent();
                  this.castDelay = instant ? 5 : 12;
                  this.circle = MagicCircle.castMagicCircle(
                     2.5F, this.castDelay + 20, choice.circle, this.diablo, new CompoundTag(), 1.8F, Vec3.ZERO, instance, 0, Pair.of(0.0, 0.0)
                  );
                  this.diablo.playCombatAnimation("attack");
                  return true;
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private void tickSpell(long now) {
      if (this.diablo.isAllowedCombatTarget(this.actionTarget)
         && this.diablo.canCastMagics(this.diablo)
         && this.diablo.hasLineOfSight(this.actionTarget)
         && !(this.diablo.distanceToSqr(this.actionTarget) > this.spell.range * this.spell.range)
         && (this.circle == null || this.circle.isAlive())) {
         if (++this.actionTick >= this.castDelay) {
            ManasSkillInstance instance = this.ready(this.spell);
            if (instance != null) {
               switch (this.spell) {
                  case FIRE:
                     this.diablo.shootFireBall(this.actionTarget, 1.5F);
                     break;
                  case ICE:
                     this.diablo.shootIceBreaker(this.actionTarget, 1.5F);
                     break;
                  case WIND:
                     this.diablo.shootTornadoBlade(this.actionTarget, 1.2F);
                     break;
                  case ACID:
                     this.diablo.shootAcidShell(this.actionTarget, 2.0F);
                     break;
                  case MUD:
                     this.diablo.spawnMudSpears(this.actionTarget);
                     break;
                  case THUNDER:
                     this.diablo.castThunderOrb();
                     break;
                  case WATER:
                     this.diablo.spawnWaterJail(this.actionTarget);
                     break;
                  case STONE:
                     this.diablo.burstStoneShot(this.diablo::isAllowedCombatTarget);
                     break;
                  case BARRIER:
                     this.diablo.activateBarrier();
               }

               instance.setCoolDown(this.spell.cooldownSeconds, 0);
               SkillAPI.getSkillsFrom(this.diablo).markActiveTick();
               SkillAPI.getSkillsFrom(this.diablo).markDirty();
            }

            this.spell = null;
            this.circle = null;
            this.actionTarget = null;
            this.nextSpell = now + 35L;
            this.nextMelee = now + 3L;
         }
      } else {
         this.cancelAction();
         this.nextSpell = now + 10L;
      }
   }

   private void updateSpacing(LivingEntity target, long now) {
      Vec3 away = this.diablo.position().subtract(target.position()).multiply(1.0, 0.0, 1.0).normalize();
      double distance = this.diablo.distanceTo(target);
      if (now < this.retreatUntil && distance < 8.0 && away.lengthSqr() > 0.01) {
         Vec3 point = this.diablo.position().add(away.scale(4.0)).add(away.z * this.strafeSide * 2.0, 0.0, -away.x * this.strafeSide * 2.0);
         if (this.safeGroundPoint(point)) {
            this.walkTo(point, 1.5F);
            return;
         }

         this.retreatUntil = now;
      }

      if (this.spell != null || now < this.retreatUntil && distance <= 14.0) {
         Vec3 side = new Vec3(away.z * this.strafeSide, 0.0, -away.x * this.strafeSide).scale(2.5);
         Vec3 point = this.diablo.position().add(side);
         if (this.safeGroundPoint(point)) {
            this.walkTo(point, 1.1F);
         } else {
            this.strafeSide = -this.strafeSide;
         }
      } else {
         this.diablo
            .getBrain()
            .setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityTracker(target, false), this.attack == null ? 1.35F : 0.9F, 0));
      }
   }

   private boolean tryDodge(long now) {
      Iterator var3 = this.diablo.level().getEntitiesOfClass(Projectile.class, this.diablo.getBoundingBox().inflate(12.0)).iterator();

      while (true) {
         Vec3 away;
         while (true) {
            if (!var3.hasNext()) {
               return false;
            }

            Projectile projectile = (Projectile)var3.next();
            LivingEntity owner = DiabloCombatEvents.findAttacker(projectile.getOwner());
            if (projectile.isAlive() && this.diablo.isAllowedCombatTarget(owner) && !(projectile instanceof MagicCircle)) {
               Vec3 velocity = projectile.getDeltaMovement();
               Vec3 offset = this.diablo.getBoundingBox().getCenter().subtract(projectile.getBoundingBox().getCenter());
               double speed = velocity.lengthSqr();
               if (speed > 0.0025) {
                  double radius = 1.1 + Math.min(3.0, projectile.getBbWidth() * 0.5);
                  if (incomingTrajectory(offset, velocity, radius)) {
                     away = new Vec3(-velocity.z, 0.0, velocity.x).normalize();
                     break;
                  }
               } else if (projectile instanceof TensuraProjectile magic
                  && (!(magic.getDamage() <= 0.0F) || !(magic.getSecondaryDamage() <= 0.0F))
                  && projectile.getBoundingBox().inflate(1.5).intersects(this.diablo.getBoundingBox())) {
                  away = offset.multiply(1.0, 0.0, 1.0).normalize();
                  break;
               }
            }
         }

         if (this.startDodge(away, now) || this.startDodge(away.scale(-1.0), now)) {
            return true;
         }
      }
   }

   private boolean startDodge(Vec3 direction, long now) {
      if (direction.lengthSqr() < 0.01) {
         return false;
      }

      Vec3 point = this.diablo.position().add(direction.normalize().scale(3.0));
      if (!this.safeGroundPoint(point)) {
         return false;
      }

      Vec3 offset = point.subtract(this.diablo.position());

      for (int i = 1; i <= 8; i++) {
         if (!this.diablo.level().noCollision(this.diablo, this.diablo.getBoundingBox().move(offset.scale(i / 8.0)))) {
            return false;
         }
      }

      this.cancelAction();
      this.dodgeDestination = point;
      this.dodgeEnds = now + 9L;
      this.nextDodge = now + 30L;
      this.nextMelee = now + 9L;
      this.clearWalk();
      this.face(this.diablo.getTarget());
      return true;
   }

   private boolean safeGroundPoint(Vec3 point) {
      AABB box = this.diablo.getBoundingBox().move(point.subtract(this.diablo.position()));
      BlockPos pos = BlockPos.containing(point);
      return this.diablo.level().hasChunkAt(pos)
         && this.diablo.level().getWorldBorder().isWithinBounds(box)
         && this.diablo.level().noCollision(this.diablo, box)
         && !this.diablo.level().containsAnyLiquid(box)
         && (this.diablo.isFlying() || this.diablo.level().getBlockState(pos.below()).blocksMotion());
   }

   static boolean withinClawRange(AABB own, AABB other) {
      double dx = Math.max(0.0, Math.max(own.minX - other.maxX, other.minX - own.maxX));
      double dz = Math.max(0.0, Math.max(own.minZ - other.maxZ, other.minZ - own.maxZ));
      double dy = Math.max(0.0, Math.max(own.minY - other.maxY, other.minY - own.maxY));
      return dx * dx + dz * dz <= 2.5600000000000005 && dy <= 0.75;
   }

   static boolean incomingTrajectory(Vec3 offset, Vec3 velocity, double radius) {
      double speed = velocity.lengthSqr();
      if (speed <= 0.0025) {
         return false;
      }

      double time = offset.dot(velocity) / speed;
      return time >= 0.0 && time <= 12.0 && offset.subtract(velocity.scale(time)).lengthSqr() <= radius * radius;
   }

   private void walkTo(Vec3 point, float speed) {
      this.diablo.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(point, speed, 0));
   }

   private void face(LivingEntity target) {
      this.diablo.lookAt(Anchor.EYES, target.getEyePosition());
      this.diablo.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(target, true));
   }

   private void clearWalk() {
      this.diablo.getNavigation().stop();
      this.diablo.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
      this.diablo.getBrain().eraseMemory(MemoryModuleType.PATH);
   }

   boolean isBusy() {
      return this.attack != null || this.spell != null || this.counterAt != 0L || this.isDodging();
   }

   boolean isCastingMagic() {
      return this.spell != null;
   }

   boolean isDodging() {
      return this.dodgeDestination != null;
   }

   Vec3 getDodgeMotion() {
      if (this.dodgeDestination == null) {
         return Vec3.ZERO;
      }

      Vec3 delta = this.dodgeDestination.subtract(this.diablo.position()).multiply(1.0, 0.0, 1.0);
      return delta.lengthSqr() < 0.01 ? Vec3.ZERO : delta.normalize().scale(Math.min(0.4, delta.length()));
   }

   private void cancelAction() {
      if (this.attack != null || this.spell != null || this.counterAt != 0L) {
         this.diablo.stopTriggeredAnim("bodyController", null);
      }

      this.counterAt = 0L;
      this.attack = null;
      this.spell = null;
      this.actionTarget = null;
      if (this.circle != null && this.circle.isAlive()) {
         this.circle.discard();
      }

      this.circle = null;
   }

   void stop() {
      this.cancelAction();
      this.dodgeDestination = null;
      this.retreatUntil = 0L;
      this.meleeCount = 0;
      this.combatTicks = 0;
   }

   void save(CompoundTag tag) {
      long now = this.diablo.level().getGameTime();
      tag.putLong("DiabloBlockCooldown", Math.max(0L, this.nextBlock - now));
      tag.putLong("DiabloDecoyCooldown", Math.max(0L, this.nextDecoy - now));
   }

   void load(CompoundTag tag) {
      long now = this.diablo.level().getGameTime();
      this.nextBlock = now + Math.clamp(tag.getLong("DiabloBlockCooldown"), 0, 100);
      this.nextDecoy = now + Math.clamp(tag.getLong("DiabloDecoyCooldown"), 0, 400);
   }

   private enum Spell {
      FIRE("fire_ball", MagicCircleVariant.FLAME, 40.0, 4),
      ICE("ice_breaker", MagicCircleVariant.ICE, 40.0, 5),
      WIND("tornado_blade", MagicCircleVariant.WIND, 40.0, 5),
      ACID("acid_shell", MagicCircleVariant.WATER, 40.0, 5),
      MUD("mud_spears", MagicCircleVariant.EARTH, 32.0, 5),
      THUNDER("thunder_orb", MagicCircleVariant.LIGHTNING, 32.0, 10),
      WATER("water_jail", MagicCircleVariant.WATER, 10.0, 8),
      STONE("stone_shot", MagicCircleVariant.EARTH, 12.0, 6),
      BARRIER("reinforced_barrier", MagicCircleVariant.BARRIER, 32.0, 20);

      final String id;
      final MagicCircleVariant circle;
      final double range;
      final int cooldownSeconds;

      Spell(String id, MagicCircleVariant circle, double range, int cooldownSeconds) {
         this.id = id;
         this.circle = circle;
         this.range = range;
         this.cooldownSeconds = cooldownSeconds;
      }
   }
}
