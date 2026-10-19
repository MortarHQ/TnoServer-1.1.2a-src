package io.github.Memoires.mysticism.ability.skill.unique;

import io.github.Memoires.mysticism.ability.subclass.IWeaponStorage;
import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.entity.skill.EnkiduEntity;
import io.github.Memoires.mysticism.entity.skill.GateEntity;
import io.github.Memoires.mysticism.menu.container.WeaponStorageContainer;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.Memoires.mysticism.registry.entity.MysticismSkillEntityTypes;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import io.github.manasmods.tensura.util.EnergyHelper.DrainType;
import io.github.manasmods.tensura.util.EnergyHelper.GainType;
import java.util.Comparator;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class GatekeeperSkill extends Skill implements IWeaponStorage {
   private static final UniqueMysticismSkillConfig.Gatekeeper CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class)).Gatekeeper;

   public GatekeeperSkill() {
      super(SkillType.UNIQUE);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/unique/gatekeeper.png");
   }

   public double getAcquiringMagiculeCost(ManasSkillInstance instance) {
      return CONFIG.mpAcquirement;
   }

   public int getModes(ManasSkillInstance instance) {
      return 3;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (reverse) {
         return mode == 0 ? 2 : mode - 1;
      } else {
         return mode == 2 ? 0 : mode + 1;
      }
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> CONFIG.enkiduCost;
         case 2 -> CONFIG.babylonCost;
         default -> 0.0;
      };
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "gatekeeper.enkidu";
         case 1 -> "gatekeeper.kings_storehouse";
         case 2 -> "gatekeeper.gate_of_babylon";
         default -> super.getModeId(instance, mode);
      };
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return false;
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return this.isInSlot(entity, instance);
   }

   public boolean shouldTriggerReleaseOnHeldInterrupt(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      return mode == 2;
   }

   public void onTick(ManasSkillInstance instance, LivingEntity living) {
      living.addEffect(new MobEffectInstance(MobEffects.LUCK, 200, 4, false, false, false));
      CompoundTag tag = instance.getOrCreateTag();
      if (!living.level().isClientSide && !tag.getBoolean("GateActive")) {
         this.cleanupGates(living);
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity instanceof ServerPlayer player) {
         if (mode == 0) {
            LivingEntity target = (LivingEntity)ObjectSelectionHelper.getTargetingEntity(
               LivingEntity.class, entity, CONFIG.enkiduRange, 0.75, false, true, false
            );
            if (target == null) {
               entity.sendSystemMessage(Component.translatable("tensura.targeting.not_targeted").withStyle(ChatFormatting.RED));
               instance.setCoolDown(CONFIG.enkiduCooldown, mode);
               return;
            }

            if (target.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.ENKIDU))) {
               target.removeEffect(TensuraMobEffects.getReference(MysticismMobEffects.ENKIDU));
               entity.swing(InteractionHand.MAIN_HAND, true);
               entity.level()
                  .playSound(
                     null,
                     entity.getX(),
                     entity.getY(),
                     entity.getZ(),
                     (SoundEvent)TensuraSoundEvents.DEBUFF_DEACTIVATE.get(),
                     TensuraSkill.ABILITY_SOUND,
                     1.0F,
                     1.0F
                  );
               TensuraParticleHelper.addServerParticlesAroundSelf(target, ParticleTypes.FLASH, 1.0);
               TensuraParticleHelper.addServerParticlesAroundSelf(target, ParticleTypes.COMPOSTER, 1.0);
            } else {
               if (target instanceof Player targetPlayer && targetPlayer.getAbilities().invulnerable) {
                  return;
               }

               double cost = this.getMagiculeCost(entity, instance, mode);
               if (EnergyHelper.isOutOfEnergy(entity, 0.0, cost)) {
                  return;
               }

               instance.addMasteryPoint(entity);
               instance.setCoolDown(CONFIG.enkiduCooldown, mode);
               int duration = instance.isMastered(entity) ? CONFIG.enkiduDurationMastered : CONFIG.enkiduDuration;
               duration *= 20;
               MobEffectInstance enkidu = new MobEffectInstance(TensuraMobEffects.getReference(MysticismMobEffects.ENKIDU), duration, 0, false, false, false);
               TensuraMobEffect.addEffect(target, enkidu, entity, this, mode);
               TensuraDamageHelper.markHurt(target, entity);
               entity.swing(InteractionHand.MAIN_HAND, true);
               EnkiduEntity enkiduEntity = new EnkiduEntity(target.level(), target);
               enkiduEntity.setLifespan(duration);
               target.level().addFreshEntity(enkiduEntity);
               entity.level()
                  .playSound(
                     null,
                     entity.getX(),
                     entity.getY(),
                     entity.getZ(),
                     (SoundEvent)TensuraSoundEvents.DEBUFF_ACTIVATE.get(),
                     TensuraSkill.ABILITY_SOUND,
                     1.0F,
                     1.0F
                  );
            }
         } else if (mode == 1) {
            this.openWeaponStorageMenu(player, instance);
            player.playNotifySound((SoundEvent)TensuraSoundEvents.SPATIAL_STORAGE.get(), TensuraSkill.ABILITY_SOUND, 0.5F, 1.0F);
         } else if (mode == 2) {
            CompoundTag tag = instance.getOrCreateTag();
            tag.putBoolean("GateActive", true);
            if (entity.isShiftKeyDown()) {
               int subMode = (tag.getInt("GateSubMode") + 1) % 2;
               tag.putInt("GateSubMode", subMode);
               player.sendSystemMessage(
                  Component.literal("Gate Configuration: " + (subMode == 0 ? "Direct Fire" : "Surround Attack")).withStyle(ChatFormatting.GOLD)
               );
            }

            this.tickGateSpawning(entity, instance);
         }
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity living, int heldTicks, int mode) {
      if (mode == 2 && !living.level().isClientSide) {
         CompoundTag tag = instance.getOrCreateTag();
         double tickCost = CONFIG.babylonContinousCost;
         if (heldTicks % 20 == 0) {
            if (EnergyHelper.isOutOfEnergy(living, 0.0, tickCost)) {
               tag.putBoolean("GateActive", false);
               this.cleanupGates(living);
               return false;
            }

            EnergyHelper.drainEnergy(living, living, tickCost, false, DrainType.MAGICULE, GainType.NONE);
         }

         tag.putBoolean("GateActive", true);
         this.tickGateSpawning(living, instance);
         return true;
      } else {
         return false;
      }
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      if (mode == 2 && !entity.level().isClientSide) {
         instance.getOrCreateTag().putBoolean("GateActive", false);
         this.cleanupGates(entity);
      }
   }

   private void cleanupGates(LivingEntity owner) {
      for (GateEntity gate : owner.level()
         .getEntitiesOfClass(GateEntity.class, owner.getBoundingBox().inflate(40.0), gatex -> owner.getUUID().equals(gatex.getOwnerUUID()))) {
         gate.discard();
      }
   }

   private void tickGateSpawning(LivingEntity owner, ManasSkillInstance instance) {
      WeaponStorageContainer container = this.getWeaponStorage(instance, owner.registryAccess(), owner);
      int filledSlots = container.getAllWeapons().size();
      if (filledSlots <= 0) {
         this.cleanupGates(owner);
      } else {
         int subMode = instance.getOrCreateTag().getInt("GateSubMode");
         int targetGateCount = subMode == 0 ? Math.min(filledSlots, 3) : 4;
         List<GateEntity> activeGates = owner.level()
            .getEntitiesOfClass(GateEntity.class, owner.getBoundingBox().inflate(40.0), gatex -> owner.getUUID().equals(gatex.getOwnerUUID()));
         activeGates.sort(Comparator.comparingInt(Entity::getId));
         if (activeGates.size() > targetGateCount) {
            for (int i = targetGateCount; i < activeGates.size(); i++) {
               activeGates.get(i).discard();
            }

            activeGates = activeGates.subList(0, targetGateCount);
         }

         while (activeGates.size() < targetGateCount) {
            GateEntity newGate = new GateEntity((EntityType<? extends Entity>)MysticismSkillEntityTypes.GATE.get(), owner.level(), owner, instance);
            owner.level().addFreshEntity(newGate);
            activeGates.add(newGate);
         }

         activeGates.sort(Comparator.comparingInt(Entity::getId));
         if (subMode == 0) {
            double spacing = 2.5;

            for (int i = 0; i < activeGates.size(); i++) {
               GateEntity gate = activeGates.get(i);
               gate.setIsSurround(false);
               double offset = (i - (activeGates.size() - 1) / 2.0) * spacing;
               this.snapGateBehind(gate, owner, offset);
            }
         } else {
            LivingEntity target = (LivingEntity)ObjectSelectionHelper.getTargetingEntity(LivingEntity.class, owner, 25.0, 1.0, false, true, false);
            LivingEntity centerTarget = target != null ? target : owner;
            double radius = 3.5;
            double angleStep = (Math.PI * 2) / activeGates.size();

            for (int i = 0; i < activeGates.size(); i++) {
               GateEntity gate = activeGates.get(i);
               gate.setIsSurround(true);
               double angle = i * angleStep;
               double x = centerTarget.getX() + radius * Math.cos(angle);
               double z = centerTarget.getZ() + radius * Math.sin(angle);
               double y = centerTarget.getY() + 1.5 + Math.sin(angle * 2.0) * 0.5;
               gate.setPos(x, y, z);
               Vec3 gatePos = gate.position();
               Vec3 targetPos = centerTarget.getEyePosition();
               double dx = targetPos.x - gatePos.x;
               double dy = targetPos.y - gatePos.y;
               double dz = targetPos.z - gatePos.z;
               double horizontalDistance = Math.sqrt(dx * dx + dz * dz);
               float yaw = (float)(Math.atan2(dz, dx) * (180.0 / Math.PI)) - 90.0F;
               float pitch = (float)(-(Math.atan2(dy, horizontalDistance) * (180.0 / Math.PI)));
               gate.setYRot(yaw);
               gate.setXRot(pitch);
               gate.yRotO = yaw;
               gate.xRotO = pitch;
            }
         }
      }
   }

   private void snapGateBehind(GateEntity gate, LivingEntity owner, double sideOffset) {
      float yawRad = owner.getYRot() * (float) (Math.PI / 180.0);
      double behindX = owner.getX() + Math.sin(yawRad) * 1.5 + Math.cos(yawRad) * sideOffset;
      double behindZ = owner.getZ() - Math.cos(yawRad) * 1.5 + Math.sin(yawRad) * sideOffset;
      double behindY = owner.getEyeY() - 0.2;
      gate.setPos(behindX, behindY, behindZ);
      float yaw = owner.getYRot();
      float pitch = owner.getXRot();
      gate.setYRot(yaw);
      gate.setXRot(pitch);
      gate.yRotO = yaw;
      gate.xRotO = pitch;
   }

   @Override
   public WeaponStorageContainer getWeaponStorage(ManasSkillInstance instance, Provider provider, LivingEntity entity) {
      int size = instance.isMastered(entity) ? 20 : 10;
      WeaponStorageContainer container = new WeaponStorageContainer(size, 1);
      container.fromTag(instance.getOrCreateTag().getList("WeaponStorage", 10), provider);
      return container;
   }
}
