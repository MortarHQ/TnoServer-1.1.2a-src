package com.github.wal_bos.moreskills.handler.avalon;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.registry.skill.AllSkills;
import com.github.wal_bos.moreskills.support.RoyalSkillSupport;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;

public final class AvalonRoyalLawsHandler implements AvalonModeHandler {
   private static final String SELECTED = "AvalonSelectedRoyalLaw";
   private static final String ACTIVE = "AvalonActiveRoyalLaw";
   private static UUID activeOwner;
   private static final List<AvalonLawHandler> LAWS = List.of(
      new AvalonLawOfSanctuaryHandler(),
      new AvalonLawOfSacredPurityHandler(),
      new AvalonLawOfEndlessReservoirHandler(),
      new AvalonLawOfSilentThroneHandler(),
      new AvalonLawOfCrushingHeavenHandler(),
      new AvalonLawOfClosedPrisonHandler(),
      new AvalonLawOfReversedProvidenceHandler(),
      new AvalonLawOfSeveredExistenceHandler()
   );

   public static void init() {
      AvalonLawOfReversedProvidenceHandler.init();
      AvalonLawOfSeveredExistenceHandler.init();
   }

   @Override
   public String modeId() {
      return "avalon_god_of_eternal_kingdom.royal_laws";
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return (Double)MoreSkillsConfigs.GRAND.avalon.royalLawCost.get();
   }

   @Override
   public void activate(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      CompoundTag tag = instance.getOrCreateTag();
      int selected = Math.floorMod(tag.getInt("AvalonSelectedRoyalLaw"), LAWS.size());
      if (entity.isShiftKeyDown()) {
         selected = (selected + 1) % LAWS.size();
         tag.putInt("AvalonSelectedRoyalLaw", selected);
         instance.markDirty();
         entity.sendSystemMessage(Component.literal("Royal Law selected: " + LAWS.get(selected).name()).withStyle(ChatFormatting.GOLD));
      } else if (!AvalonKingdomHandler.isInsideOwnedKingdom(entity)) {
         entity.sendSystemMessage(Component.literal("Royal Laws can only be declared inside your Kingdom of Avalon.").withStyle(ChatFormatting.RED));
      } else {
         int active = tag.contains("AvalonActiveRoyalLaw") ? tag.getInt("AvalonActiveRoyalLaw") : -1;
         if (active == selected) {
            deactivate(instance, entity, active);
            tag.putInt("AvalonActiveRoyalLaw", -1);
            if (entity.getUUID().equals(activeOwner)) {
               activeOwner = null;
            }

            instance.markDirty();
            entity.sendSystemMessage(Component.literal(LAWS.get(selected).name() + " withdrawn.").withStyle(ChatFormatting.GRAY));
         } else if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
            withdrawOtherRoyalLaw(entity);
            if (active >= 0 && active < LAWS.size()) {
               deactivate(instance, entity, active);
            }

            activeOwner = entity.getUUID();
            tag.putInt("AvalonActiveRoyalLaw", selected);
            LAWS.get(selected).onActivate(instance, entity);
            instance.markDirty();
            instance.addMasteryPoint(entity);
            entity.sendSystemMessage(Component.literal(LAWS.get(selected).name() + " is now the sole Royal Law.").withStyle(ChatFormatting.GOLD));
            if (entity.level() instanceof ServerLevel level) {
               LAWS.get(selected).sendVfx(level, entity, ((Double)MoreSkillsConfigs.GRAND.avalon.royalLawRadius.get()).floatValue(), 80, selected);
               level.playSound(null, entity.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 3.0F, 0.8F + selected * 0.05F);
            }
         }
      }
   }

   @Override
   public void tick(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getOrCreateTag();
      int active = tag.contains("AvalonActiveRoyalLaw") ? tag.getInt("AvalonActiveRoyalLaw") : -1;
      if (active >= 0 && active < LAWS.size()) {
         if (activeOwner == null) {
            activeOwner = entity.getUUID();
         }

         if (entity.getUUID().equals(activeOwner) && AvalonKingdomHandler.isInsideOwnedKingdom(entity)) {
            LAWS.get(active).tick(instance, entity);
            if (entity.tickCount % 40 == 0 && entity.level() instanceof ServerLevel level) {
               LAWS.get(active).sendVfx(level, entity, ((Double)MoreSkillsConfigs.GRAND.avalon.royalLawRadius.get()).floatValue(), 50, active);
            }
         } else {
            deactivate(instance, entity, active);
            tag.putInt("AvalonActiveRoyalLaw", -1);
            if (entity.getUUID().equals(activeOwner)) {
               activeOwner = null;
            }

            instance.markDirty();
         }
      }
   }

   private static void deactivate(ManasSkillInstance instance, LivingEntity entity, int active) {
      if (active >= 0 && active < LAWS.size()) {
         LAWS.get(active).onDeactivate(instance, entity);
      }
   }

   public static boolean isLawActive(LivingEntity owner, int law) {
      Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(owner).getSkill((ManasSkill)AllSkills.AVALON_GOD_OF_ETERNAL_KINGDOM.get());
      if (optional.isEmpty()) {
         return false;
      }

      CompoundTag tag = optional.get().getOrCreateTag();
      return owner.getUUID().equals(activeOwner)
         && AvalonKingdomHandler.isInsideOwnedKingdom(owner)
         && tag.contains("AvalonActiveRoyalLaw")
         && tag.getInt("AvalonActiveRoyalLaw") == law;
   }

   public static int activeLaw(ManasSkillInstance instance) {
      CompoundTag tag = instance.getOrCreateTag();
      return tag.contains("AvalonActiveRoyalLaw") ? tag.getInt("AvalonActiveRoyalLaw") : -1;
   }

   @Override
   public void cleanup(LivingEntity entity) {
      Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(entity).getSkill((ManasSkill)AllSkills.AVALON_GOD_OF_ETERNAL_KINGDOM.get());
      if (!optional.isEmpty()) {
         ManasSkillInstance instance = optional.get();
         int active = activeLaw(instance);
         deactivate(instance, entity, active);
         instance.getOrCreateTag().putInt("AvalonActiveRoyalLaw", -1);
         if (entity.getUUID().equals(activeOwner)) {
            activeOwner = null;
         }

         instance.markDirty();
      }
   }

   private static void withdrawOtherRoyalLaw(LivingEntity newOwner) {
      if (activeOwner != null && !activeOwner.equals(newOwner.getUUID()) && newOwner.level() instanceof ServerLevel level) {
         LivingEntity previousOwner = findLiving(level.getServer(), activeOwner);
         if (previousOwner != null) {
            Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(previousOwner).getSkill((ManasSkill)AllSkills.AVALON_GOD_OF_ETERNAL_KINGDOM.get());
            if (optional.isPresent()) {
               ManasSkillInstance previousInstance = optional.get();
               int previousLaw = activeLaw(previousInstance);
               deactivate(previousInstance, previousOwner, previousLaw);
               previousInstance.getOrCreateTag().putInt("AvalonActiveRoyalLaw", -1);
               previousInstance.markDirty();
               RoyalSkillSupport.sync(previousOwner);
               previousOwner.sendSystemMessage(Component.literal("Your Royal Law was superseded by another sovereign.").withStyle(ChatFormatting.GRAY));
            }
         }

         activeOwner = null;
      }
   }

   private static LivingEntity findLiving(MinecraftServer server, UUID uuid) {
      for (ServerLevel level : server.getAllLevels()) {
         if (level.getEntity(uuid) instanceof LivingEntity living) {
            return living;
         }
      }

      return null;
   }
}
