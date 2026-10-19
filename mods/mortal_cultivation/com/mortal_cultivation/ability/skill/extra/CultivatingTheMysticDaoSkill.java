package com.mortal_cultivation.ability.skill.extra;

import com.github.manasmods.tensura.capability.race.TensuraPlayerCapability;
import com.mortal_cultivation.ability.skill.base.LegacySkill;
import com.mortal_cultivation.entity.TaoistArrayEntity;
import com.mortal_cultivation.registry.CultivationEntities;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class CultivatingTheMysticDaoSkill extends LegacySkill {
   private static final Set<UUID> MEDITATING_PLAYERS = ConcurrentHashMap.newKeySet();
   private final double skillCastCost = 0.0;
   private final double learnCost = 2.0;
   private TaoistArrayEntity currentArray = null;
   private Vec3 lastPlayerPosition = null;
   private int arrayCreationAttempts = 0;
   private int ticksSinceLastCheck = 0;
   private Double fixedHoverHeight = null;
   private Vec3 fixedHoverPosition = null;
   private boolean isHovering = false;

   public CultivatingTheMysticDaoSkill() {
      super(SkillType.EXTRA);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "textures/skill/extra/cultivating_the_mystic_dao.png");
   }

   @Override
   public boolean meetEPRequirement(Player entity, double curEP) {
      return false;
   }

   @Override
   public double learningCost() {
      return 2.0;
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return 0.0;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
      return instance.isMastered(living);
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return instance.isMastered(entity) && instance.isToggled();
   }

   @Override
   public void onPressed(ManasSkillInstance instance, LivingEntity entity) {
      if (!(EnergyHelper.isOutOfMagiculeConsuming(entity, this.magiculeCost(entity, instance)) > 0.0)) {
         ;
      }
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (EnergyHelper.isOutOfMagiculeConsuming(entity, this.magiculeCost(entity, instance) * 2.0) > 0.0) {
         instance.setToggled(false);
      } else {
         super.onTick(instance, entity);
      }
   }

   @Override
   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks) {
      setMeditating(entity, true);
      if (heldTicks == 1) {
         if (!entity.level().isClientSide) {
            this.createTaoistArray(entity);
         }

         this.startHovering(entity);
      }

      if (heldTicks % 20 == 0 && EnergyHelper.isOutOfMagiculeConsuming(entity, this.magiculeCost(entity, instance)) > 0.0) {
         if (!entity.level().isClientSide) {
            this.removeTaoistArray();
         }

         this.stopHovering(entity);
         setMeditating(entity, false);
         return false;
      } else {
         if (!entity.level().isClientSide) {
            this.maintainHovering(entity);
            this.manageArrayPosition(entity);
            if (heldTicks % 5 == 0) {
               this.spawnMeditationParticles(entity);
            }

            if (heldTicks % 20 == 0) {
               this.restorePlayerStats(entity);
            }
         }

         if (entity.level().isClientSide
            && this.isHovering
            && this.fixedHoverHeight != null
            && this.fixedHoverPosition != null
            && entity instanceof Player player) {
            player.setNoGravity(true);
            player.setDeltaMovement(0.0, 0.0, 0.0);
            player.teleportTo(this.fixedHoverPosition.x, this.fixedHoverHeight, this.fixedHoverPosition.z);
            player.setOnGround(false);
            player.fallDistance = 0.0F;
            player.zza = 0.0F;
            player.xxa = 0.0F;
         }

         if (heldTicks % 60 == 0 && heldTicks > 0) {
            this.addMasteryPoint(instance, entity);
         }

         return true;
      }
   }

   @Override
   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks) {
      if (!entity.level().isClientSide) {
         this.removeTaoistArray();
         this.lastPlayerPosition = null;
         this.arrayCreationAttempts = 0;
         this.ticksSinceLastCheck = 0;
      }

      this.stopHovering(entity);
      setMeditating(entity, false);
   }

   private void createTaoistArray(LivingEntity entity) {
      try {
         this.removeTaoistArray();
         if (entity.level().isClientSide) {
            return;
         }

         Vec3 playerPos = entity.position();
         Vec3 arrayPos = new Vec3(playerPos.x, playerPos.y + 0.1, playerPos.z);
         this.currentArray = new TaoistArrayEntity((EntityType<?>)CultivationEntities.TAOIST_ARRAY.get(), entity.level());
         this.currentArray.setPos(arrayPos.x, arrayPos.y, arrayPos.z);
         this.currentArray.setDeltaMovement(0.0, 0.0, 0.0);
         this.currentArray.setNoGravity(true);
         boolean success = entity.level().addFreshEntity(this.currentArray);
         if (!success) {
            this.currentArray = null;
            this.arrayCreationAttempts++;
         } else {
            this.arrayCreationAttempts = 0;
         }
      } catch (Exception e) {
         this.currentArray = null;
         this.arrayCreationAttempts++;
      }
   }

   private void removeTaoistArray() {
      try {
         if (this.currentArray != null && !this.currentArray.isRemoved()) {
            this.currentArray.forceRemove();
         }

         this.currentArray = null;
      } catch (Exception var2) {
      }
   }

   private void spawnMeditationParticles(LivingEntity entity) {
      if (entity.level() instanceof ServerLevel serverLevel) {
         RandomSource var24 = entity.getRandom();
         Vec3 entityPos = entity.position();

         for (int i = 0; i < 3; i++) {
            double offsetX = (var24.nextDouble() - 0.5) * 2.0;
            double offsetY = var24.nextDouble() * 2.0;
            double offsetZ = (var24.nextDouble() - 0.5) * 2.0;
            double particleX = entityPos.x + offsetX;
            double particleY = entityPos.y + offsetY;
            double particleZ = entityPos.z + offsetZ;
            double velocityX = (var24.nextDouble() - 0.5) * 0.02;
            double velocityY = 0.05 + var24.nextDouble() * 0.03;
            double velocityZ = (var24.nextDouble() - 0.5) * 0.02;
            serverLevel.sendParticles(ParticleTypes.WHITE_ASH, particleX, particleY, particleZ, 1, velocityX, velocityY, velocityZ, 0.01);
         }
      }
   }

   private void restorePlayerStats(LivingEntity entity) {
      if (entity instanceof Player player) {
         try {
            float maxHealth = player.getMaxHealth();
            float currentHealth = player.getHealth();
            float healAmount = maxHealth * 0.01F;
            if (currentHealth < maxHealth) {
               player.heal(healAmount);
            }

            try {
               double currentMagicule = TensuraPlayerCapability.getMagicule(player);
               double baseMagicule = TensuraPlayerCapability.getBaseMagicule(player);
               double magiculeRestore = baseMagicule * 0.01;
               if (currentMagicule < baseMagicule) {
                  double newMagicule = Math.min(baseMagicule, currentMagicule + magiculeRestore);
                  TensuraPlayerCapability.setMagicule(player, newMagicule);
               }
            } catch (Exception var14) {
            }
         } catch (Exception var15) {
         }
      }
   }

   private void manageArrayPosition(LivingEntity entity) {
      try {
         Vec3 currentPlayerPos = entity.position();
         this.ticksSinceLastCheck++;
         boolean shouldCheck = this.ticksSinceLastCheck >= 5 || this.currentArray == null;
         if (shouldCheck) {
            this.ticksSinceLastCheck = 0;
            if (this.currentArray == null) {
               this.createTaoistArray(entity);
               this.lastPlayerPosition = currentPlayerPos;
               return;
            }

            if (!this.isArrayValid(entity)) {
               this.createTaoistArray(entity);
               this.lastPlayerPosition = currentPlayerPos;
               return;
            }
         }

         if (this.currentArray != null && !this.currentArray.isRemoved()) {
            boolean positionChanged = false;
            if (this.lastPlayerPosition == null) {
               positionChanged = true;
            } else {
               double deltaX = Math.abs(currentPlayerPos.x - this.lastPlayerPosition.x);
               double deltaY = Math.abs(currentPlayerPos.y - this.lastPlayerPosition.y);
               double deltaZ = Math.abs(currentPlayerPos.z - this.lastPlayerPosition.z);
               if (deltaX > 0.01 || deltaY > 0.01 || deltaZ > 0.01) {
                  positionChanged = true;
               }
            }

            if (positionChanged) {
               this.updateArrayToPlayerPosition(entity, currentPlayerPos);
               this.lastPlayerPosition = currentPlayerPos;
            }
         }
      } catch (Exception e) {
         this.currentArray = null;
         this.createTaoistArray(entity);
         this.lastPlayerPosition = entity.position();
      }
   }

   private boolean isArrayValid(LivingEntity entity) {
      try {
         if (this.currentArray == null) {
            return false;
         } else if (this.currentArray.isRemoved()) {
            this.currentArray = null;
            return false;
         } else if (!this.currentArray.isAlive()) {
            this.currentArray = null;
            return false;
         } else if (this.currentArray.level() != entity.level()) {
            this.currentArray = null;
            return false;
         } else if (this.currentArray.tickCount <= 0) {
            return true;
         } else {
            double distance = this.currentArray.distanceTo(entity);
            if (distance > 15.0) {
               this.currentArray.discard();
               this.currentArray = null;
               return false;
            } else {
               return true;
            }
         }
      } catch (Exception e) {
         this.currentArray = null;
         return false;
      }
   }

   private void recreateArray(LivingEntity entity, Vec3 playerPos) {
      try {
         if (this.currentArray != null) {
            try {
               this.currentArray.discard();
            } catch (Exception var4) {
            }

            this.currentArray = null;
         }

         this.createTaoistArray(entity);
         this.lastPlayerPosition = playerPos;
      } catch (Exception e) {
         this.currentArray = null;
         this.lastPlayerPosition = null;
      }
   }

   private void updateArrayToPlayerPosition(LivingEntity entity, Vec3 playerPos) {
      try {
         if (this.currentArray != null && !this.currentArray.isRemoved()) {
            Vec3 newArrayPos = new Vec3(playerPos.x, playerPos.y + 0.25, playerPos.z);
            this.currentArray.setPos(newArrayPos.x, newArrayPos.y, newArrayPos.z);
            this.currentArray.moveTo(newArrayPos.x, newArrayPos.y, newArrayPos.z);
            this.currentArray.setPosRaw(newArrayPos.x, newArrayPos.y, newArrayPos.z);
            this.currentArray.setDeltaMovement(0.0, 0.0, 0.0);
            double distance = this.currentArray.distanceTo(entity);
            if (distance > 1.0) {
               this.currentArray.teleportTo(newArrayPos.x, newArrayPos.y, newArrayPos.z);
            }
         }
      } catch (Exception e) {
         this.recreateArray(entity, playerPos);
      }
   }

   private void startHovering(LivingEntity entity) {
      try {
         if (entity instanceof Player player) {
            this.fixedHoverHeight = player.getY();
            this.fixedHoverPosition = new Vec3(player.getX(), this.fixedHoverHeight, player.getZ());
            this.isHovering = true;
            player.setNoGravity(true);
            player.setDeltaMovement(0.0, 0.0, 0.0);
            player.teleportTo(this.fixedHoverPosition.x, this.fixedHoverHeight, this.fixedHoverPosition.z);
            player.setOnGround(false);
            player.fallDistance = 0.0F;
            player.zza = 0.0F;
            player.xxa = 0.0F;
            player.setSprinting(false);
         }
      } catch (Exception e) {
         this.fixedHoverHeight = null;
         this.fixedHoverPosition = null;
         this.isHovering = false;
      }
   }

   private void maintainHovering(LivingEntity entity) {
      try {
         if (this.isHovering && this.fixedHoverHeight != null && this.fixedHoverPosition != null && entity instanceof Player player) {
            player.setNoGravity(true);
            Vec3 currentPos = player.position();
            double currentY = currentPos.y;
            double targetY = this.fixedHoverHeight;
            double targetX = this.fixedHoverPosition.x;
            double targetZ = this.fixedHoverPosition.z;
            if (Math.abs(currentY - targetY) > 1.0E-4 || Math.abs(currentPos.x - targetX) > 1.0E-4 || Math.abs(currentPos.z - targetZ) > 1.0E-4) {
               player.teleportTo(targetX, targetY, targetZ);
               player.setPos(targetX, targetY, targetZ);
               player.setPosRaw(targetX, targetY, targetZ);
               player.moveTo(targetX, targetY, targetZ);
            }

            player.setDeltaMovement(0.0, 0.0, 0.0);
            player.zza = 0.0F;
            player.xxa = 0.0F;
            player.setSprinting(false);
            player.setOnGround(false);
            player.fallDistance = 0.0F;
            player.setNoGravity(true);
            player.setPos(targetX, targetY, targetZ);
         }
      } catch (Exception var12) {
      }
   }

   private void stopHovering(LivingEntity entity) {
      try {
         if (entity instanceof Player player) {
            player.setNoGravity(false);
            player.zza = 0.0F;
            player.xxa = 0.0F;
            this.isHovering = false;
            this.fixedHoverHeight = null;
            this.fixedHoverPosition = null;
         }
      } catch (Exception e) {
         this.isHovering = false;
         this.fixedHoverHeight = null;
         this.fixedHoverPosition = null;
      }
   }

   public static boolean isMeditating(LivingEntity entity) {
      return entity != null && MEDITATING_PLAYERS.contains(entity.getUUID());
   }

   private static void setMeditating(LivingEntity entity, boolean meditating) {
      if (entity != null) {
         if (meditating) {
            MEDITATING_PLAYERS.add(entity.getUUID());
         } else {
            MEDITATING_PLAYERS.remove(entity.getUUID());
         }
      }
   }

   public boolean isHovering() {
      return this.isHovering;
   }

   public Double getFixedHoverHeight() {
      return this.fixedHoverHeight;
   }
}
