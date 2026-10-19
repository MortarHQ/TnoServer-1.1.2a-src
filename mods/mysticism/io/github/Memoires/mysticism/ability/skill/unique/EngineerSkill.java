package io.github.Memoires.mysticism.ability.skill.unique;

import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.entity.skill.BubbleShieldBarrierEntity;
import io.github.Memoires.mysticism.entity.skill.DispenserEntity;
import io.github.Memoires.mysticism.entity.skill.SentryEntity;
import io.github.Memoires.mysticism.registry.entity.MysticismSkillEntityTypes;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import java.util.Arrays;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class EngineerSkill extends Skill {
   public static final UniqueMysticismSkillConfig.Engineer CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class)).Engineer;

   public EngineerSkill() {
      super(SkillType.UNIQUE);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/unique/engineer.png");
   }

   public int getModes(ManasSkillInstance instance) {
      return 2;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      return mode == 0 ? 1 : 0;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "engineer.build";
         case 1 -> "engineer.bubble_shield";
         default -> super.getModeId(instance, mode);
      };
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      if (mode == 1) {
         return instance.isMastered(entity) ? CONFIG.bubbleShieldCostMastered : CONFIG.bubbleShieldCost;
      } else {
         return 0.0;
      }
   }

   public boolean canScroll(ManasSkillInstance instance, LivingEntity entity, int mode) {
      return instance.getMastery() >= 0.0 && mode == 0;
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (mode == 1) {
         this.bubbleShield(instance, entity);
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity living, int heldTicks, int mode) {
      if (mode == 0) {
         this.build(instance, living, heldTicks);
         return true;
      } else {
         return false;
      }
   }

   private void build(ManasSkillInstance instance, LivingEntity entity, int heldTicks) {
      CompoundTag skillTag = instance.getOrCreateTag();
      Level level = entity.level();
      String building = skillTag != null ? skillTag.getString("building") : "Sentry";
      if (entity instanceof Player player) {
         if (heldTicks < 100 && heldTicks % 20 == 0) {
            level.playSound(
               null, entity.blockPosition(), (SoundEvent)SoundEvents.NOTE_BLOCK_PLING.value(), TensuraSkill.ABILITY_SOUND, 0.4F, 1.5F + heldTicks / 100.0F
            );
         }

         if (level instanceof ServerLevel serverLevel && heldTicks < 100) {
            for (int i = 0; i < 5; i++) {
               double angle = (Math.PI * 2) * i / 5.0 + heldTicks / 10.0;
               double radius = 0.5 + heldTicks / 100.0;
               double offsetX = Math.cos(angle) * radius;
               double offsetZ = Math.sin(angle) * radius;
               serverLevel.sendParticles(
                  ParticleTypes.ELECTRIC_SPARK, entity.getX() + offsetX, entity.getY() + 1.0, entity.getZ() + offsetZ, 1, 0.01, 0.01, 0.01, 0.001
               );
            }
         }

         player.displayClientMessage(
            Component.translatable("tensura.skill.output_number", new Object[]{building}).setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_AQUA)), true
         );
         if (building.equals("Sentry") && heldTicks == 100) {
            BlockHitResult pos = ObjectSelectionHelper.getPlayerPOVHitResult(level, player, Fluid.NONE, 3.0);
            BlockPos blockPos = pos.getBlockPos();
            BlockPos belowPos = blockPos.below();
            BlockState belowState = level.getBlockState(belowPos);
            if (belowState.isFaceSturdy(level, belowPos, Direction.UP)) {
               instance.addMasteryPoint(entity);
               SentryEntity sentry = new SentryEntity((EntityType<? extends TamableAnimal>)MysticismSkillEntityTypes.SENTRY.get(), level);
               sentry.moveTo(blockPos.getX() + 0.5, blockPos.getY() + 1.0, blockPos.getZ() + 0.5, entity.getYRot(), 0.0F);
               sentry.setSentryLevel(1);
               sentry.tame(player);
               sentry.finalizeSpawn((ServerLevelAccessor)level, level.getCurrentDifficultyAt(sentry.blockPosition()), MobSpawnType.MOB_SUMMONED, null);
               sentry.lookAt(entity, 0.0F, 0.0F);
               level.addFreshEntity(sentry);
               level.playSound(null, blockPos, SoundEvents.EVOKER_CAST_SPELL, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
               TensuraParticleHelper.addServerParticlesAroundSelf(sentry, ParticleTypes.FLASH, 2.0);
               instance.setCoolDown(instance.isMastered(player) ? CONFIG.buildCooldownMastered : CONFIG.buildCooldown, 0);
               EnergyHelper.isOutOfEnergy(entity, 0.0, instance.isMastered(player) ? CONFIG.buildSentryCostMastered : CONFIG.buildSentryCost);
            }
         } else if (building.equals("Dispenser") && heldTicks == 100) {
            BlockHitResult pos = ObjectSelectionHelper.getPlayerPOVHitResult(level, player, Fluid.NONE, 3.0);
            BlockPos blockPos = pos.getBlockPos();
            BlockPos belowPos = blockPos.below();
            BlockState belowState = level.getBlockState(belowPos);
            if (belowState.isFaceSturdy(level, belowPos, Direction.UP)) {
               instance.addMasteryPoint(entity);
               DispenserEntity dispenser = new DispenserEntity((EntityType<? extends TamableAnimal>)MysticismSkillEntityTypes.DISPENSER.get(), level);
               dispenser.moveTo(blockPos.getX() + 0.5, blockPos.getY() + 1.0, blockPos.getZ() + 0.5, entity.getYRot(), 0.0F);
               dispenser.setDispenserLevel(1);
               dispenser.tame(player);
               dispenser.finalizeSpawn((ServerLevelAccessor)level, level.getCurrentDifficultyAt(dispenser.blockPosition()), MobSpawnType.MOB_SUMMONED, null);
               dispenser.lookAt(entity, 0.0F, 0.0F);
               level.addFreshEntity(dispenser);
               level.playSound(null, blockPos, SoundEvents.EVOKER_CAST_SPELL, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
               TensuraParticleHelper.addServerParticlesAroundSelf(dispenser, ParticleTypes.FLASH, 2.0);
               instance.setCoolDown(instance.isMastered(player) ? CONFIG.buildCooldownMastered : CONFIG.buildCooldown, 0);
               EnergyHelper.isOutOfEnergy(entity, 0.0, instance.isMastered(player) ? CONFIG.buildDispenserCostMastered : CONFIG.buildDispenserCost);
            }
         }
      }
   }

   private void bubbleShield(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof Player player) {
         Level level = player.level();
         BlockPos pos = player.getOnPos();
         BlockPos belowPos = pos.below();
         BlockState belowState = level.getBlockState(belowPos);
         if (belowState.isFaceSturdy(level, belowPos, Direction.UP)) {
            instance.addMasteryPoint(entity);
            BubbleShieldBarrierEntity bubbleShieldBarrier = BubbleShieldBarrierEntity.create(level, entity);
            bubbleShieldBarrier.setSize(CONFIG.bubbleShieldSize);
            bubbleShieldBarrier.setLife(CONFIG.bubbleShieldLifespan);
            bubbleShieldBarrier.moveTo(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, entity.getYRot(), 0.0F);
            if (instance.isMastered(entity)) {
               bubbleShieldBarrier.setLife(CONFIG.bubbleShieldLifespanMastered);
            }

            level.addFreshEntity(bubbleShieldBarrier);
            level.playSound(null, pos, (SoundEvent)TensuraSoundEvents.GENERIC_CAST.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
            instance.setCoolDown(instance.isMastered(entity) ? CONFIG.bubbleShieldCooldownMastered : CONFIG.bubbleShieldCooldown, 1);
         }
      }
   }

   public void onScroll(ManasSkillInstance instance, LivingEntity living, double delta, int mode) {
      if (mode == 0) {
         CompoundTag tag = instance.getOrCreateTag();
         String[] buildings = new String[]{"Sentry", "Dispenser"};
         String currentBuilding = tag.getString("building");
         int currentIndex = Arrays.asList(buildings).indexOf(currentBuilding);
         if (currentIndex == -1) {
            currentIndex = 0;
         }

         int newIndex = currentIndex + (delta > 0.0 ? -1 : 1);
         if (newIndex >= buildings.length) {
            newIndex = 0;
         } else if (newIndex < 0) {
            newIndex = buildings.length - 1;
         }

         String newBuilding = buildings[newIndex];
         if (!newBuilding.equals(currentBuilding)) {
            tag.putString("building", newBuilding);
            instance.markDirty();
            if (living instanceof Player player) {
               player.displayClientMessage(
                  Component.translatable("tensura.skill.output_number", new Object[]{newBuilding}).setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_AQUA)),
                  true
               );
               player.level()
                  .playSound(
                     null, player.getX(), player.getY(), player.getZ(), SoundEvents.ILLUSIONER_PREPARE_BLINDNESS, TensuraSkill.ABILITY_SOUND, 2.0F, 1.0F
                  );
            }
         }
      }
   }
}
