package org.crypticdev.stextras.handler;

import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.SkillEvents;
import io.github.manasmods.manascore.skill.api.SkillEvents.RemoveSkillEvent;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ability.AbilitySlot;
import io.github.manasmods.tensura.storage.ability.IAbility;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.crypticdev.stextras.storage.STExtarsStorage;

public class HandleSkillLock {
   private static final Map<HandleSkillLock.ResetKey, HandleSkillLock.PendingSkillReset> PENDING_SKILL_RESETS = new ConcurrentHashMap<>();

   public static void init() {
      TensuraListeners();
   }

   private static void TensuraListeners() {
      SkillEvents.REMOVE_SKILL.register((RemoveSkillEvent)(instance, player, event) -> {
         boolean skillLocked = false;
         if (player instanceof ServerPlayer sp) {
            skillLocked = STExtarsStorage.Player.isSkillLocked(sp, instance.getSkillId());
            if (skillLocked) {
               int soulGrade = STExtarsStorage.Player.getSoulGradeLevel(sp);
               int lossPercent = masteryLossPercent(soulGrade, instance.isMastered(sp));
               double lossFactor = lossPercent / 100.0F;
               double current = instance.getMastery();
               double newMastery = current * (1.0 - lossFactor);
               if (shouldResetLockedSkillData(instance.getSkillId())) {
                  queueSkillReset(sp, instance, newMastery);
                  return EventResult.pass();
               }

               instance.setMastery(newMastery);
            }
         }

         return skillLocked ? EventResult.interrupt(false) : EventResult.pass();
      });
   }

   public static int masteryLossPercent(int soulGrade, boolean mastered) {
      soulGrade = Math.max(0, Math.min(soulGrade, 6));
      int loss = 100 - soulGrade * 15;
      if (mastered) {
         loss = 75 - soulGrade * 10;
      }

      return Math.max(0, Math.min(loss, 100));
   }

   private static boolean shouldResetLockedSkillData(ResourceLocation skillId) {
      if (STExtarsStorage.Player.CONFIG_SKILL_LOCK.RestSkillData) {
         return true;
      }

      List<String> resetSkills = STExtarsStorage.Player.CONFIG_SKILL_LOCK.RestSkillDataSkill;
      if (skillId != null && resetSkills != null && !resetSkills.isEmpty()) {
         String skillIdText = skillId.toString();

         for (String resetSkill : resetSkills) {
            if (resetSkill != null && skillIdText.equals(resetSkill.trim())) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private static void queueSkillReset(ServerPlayer player, ManasSkillInstance instance, double newMastery) {
      MinecraftServer server = player.getServer();
      if (server != null) {
         HandleSkillLock.ResetKey key = new HandleSkillLock.ResetKey(player.getUUID(), instance.getSkillId());
         HandleSkillLock.PendingSkillReset reset = new HandleSkillLock.PendingSkillReset(
            instance.getSkill(), Math.max(0.0, Math.min(newMastery, instance.getMaxMastery())), List.copyOf(captureAbilitySlots(player, instance.getSkill()))
         );
         PENDING_SKILL_RESETS.put(key, reset);
         server.execute(() -> restoreSkillAfterRemoval(server, key));
      }
   }

   private static void restoreSkillAfterRemoval(MinecraftServer server, HandleSkillLock.ResetKey key) {
      HandleSkillLock.PendingSkillReset reset = PENDING_SKILL_RESETS.remove(key);
      if (reset != null) {
         ServerPlayer player = server.getPlayerList().getPlayer(key.playerId());
         if (player != null) {
            SkillStorage skillStorage = SkillAPI.getSkillsFrom(player);
            if (!skillStorage.getSkill(key.skillId()).isPresent()) {
               ManasSkillInstance freshInstance = reset.skill().createDefaultInstance();
               freshInstance.getOrCreateTag().putBoolean("NoMagiculeCost", true);
               freshInstance.setMastery(reset.mastery());
               if (skillStorage.learnSkill(freshInstance, null)) {
                  restoreAbilitySlots(player, reset.skill(), reset.abilitySlots());
               }
            }
         }
      }
   }

   private static List<HandleSkillLock.AbilityRestoreData> captureAbilitySlots(ServerPlayer player, ManasSkill skill) {
      IAbility abilityData = TensuraStorages.getAbilityFrom(player);
      if (abilityData == null) {
         return List.of();
      }

      List<HandleSkillLock.AbilityRestoreData> slotsToRestore = new ArrayList<>();
      int presetCount = abilityData.getPresets().size();

      for (int presetIndex = 0; presetIndex < presetCount; presetIndex++) {
         List<AbilitySlot> presetSlots = abilityData.getAbilitySlots(presetIndex);

         for (int slotIndex = 0; slotIndex < presetSlots.size(); slotIndex++) {
            AbilitySlot slot = presetSlots.get(slotIndex);
            if (slot != null && !slot.isEmpty() && slot.getSkill() != null && skill.equals(slot.getSkill())) {
               slotsToRestore.add(new HandleSkillLock.AbilityRestoreData(presetIndex, slotIndex, slot.getMode()));
            }
         }
      }

      return slotsToRestore;
   }

   private static void restoreAbilitySlots(ServerPlayer player, ManasSkill skill, List<HandleSkillLock.AbilityRestoreData> abilitySlots) {
      if (!abilitySlots.isEmpty()) {
         IAbility abilityData = TensuraStorages.getAbilityFrom(player);
         if (abilityData != null) {
            int presetCount = abilityData.getPresets().size();

            for (HandleSkillLock.AbilityRestoreData abilitySlot : abilitySlots) {
               if (abilitySlot.presetIndex() >= 0 && abilitySlot.presetIndex() < presetCount) {
                  List<AbilitySlot> presetSlots = abilityData.getAbilitySlots(abilitySlot.presetIndex());
                  if (abilitySlot.slotIndex() >= 0 && abilitySlot.slotIndex() < presetSlots.size()) {
                     abilityData.setAbilitySlot(abilitySlot.presetIndex(), abilitySlot.slotIndex(), skill, abilitySlot.mode());
                  }
               }
            }

            abilityData.markDirty();
         }
      }
   }

   private record AbilityRestoreData(int presetIndex, int slotIndex, int mode) {
   }

   private record PendingSkillReset(ManasSkill skill, double mastery, List<HandleSkillLock.AbilityRestoreData> abilitySlots) {
   }

   private record ResetKey(UUID playerId, ResourceLocation skillId) {
   }
}
