package com.mortal_cultivation.spiritroot;

import com.mortal_cultivation.MortalCultivation;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.magic.Element;
import io.github.manasmods.tensura.ability.magic.spiritual.SpiritualMagic.SpiritLevel;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.spirit.ISpiritWielder;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public final class SpiritRootData {
   private static final String SPIRIT_ROOT_TAG = "SpiritRoot";
   private static final String PRIMARY_ROOT_TAG = "Primary";
   private static final String CATEGORY_TAG = "Category";
   private static final String VARIANT_TAG = "Variant";
   private static final String ASSIGNED_AT_TAG = "AssignedAt";

   private SpiritRootData() {
   }

   public static Optional<SpiritRootType> getPrimaryRoot(Player player) {
      CompoundTag rootTag = getRootTag(player);
      return rootTag != null && rootTag.contains("Primary") ? SpiritRootType.byId(rootTag.getString("Primary")) : Optional.empty();
   }

   public static boolean hasRoot(Player player) {
      return getPrimaryRoot(player).isPresent();
   }

   public static boolean hasPrimaryRoot(Player player, SpiritRootType root) {
      return getPrimaryRoot(player)
         .map(storedRoot -> storedRoot == root || storedRoot == SpiritRootType.HEAVEN && root.getCategory() == SpiritRootType.Category.COMMON)
         .orElse(false);
   }

   public static SpiritRootType ensureFoundationRoot(ServerPlayer player) {
      Optional<SpiritRootType> existingRoot = getPrimaryRoot(player);
      if (existingRoot.isPresent()) {
         syncLinkedSystems(player, existingRoot.get(), false);
         return existingRoot.get();
      } else {
         SpiritRootType root = SpiritRootType.randomCommonRoot(player.getRandom());
         writeRoot(player, root, "foundation");
         syncLinkedSystems(player, root, true);
         return root;
      }
   }

   public static SpiritRootType backfillFoundationRoot(ServerPlayer player) {
      SpiritRootType root = SpiritRootType.randomCommonRoot(player.getRandom());
      writeRoot(player, root, "backfill");
      syncLinkedSystems(player, root, true);
      return root;
   }

   public static SpiritRootType setRoot(ServerPlayer player, SpiritRootType root) {
      Optional<SpiritRootType> existingRoot = getPrimaryRoot(player);
      if (existingRoot.isPresent() && existingRoot.get() != root) {
         forgetElementalSkillBundle(player, existingRoot.get());
         clearLinkedSpiritCompanion(player, existingRoot.get());
      }

      writeRoot(player, root, "command");
      syncLinkedSystems(player, root, false);
      return root;
   }

   public static SpiritRootType refreshRoot(ServerPlayer player) {
      Optional<SpiritRootType> existingRoot = getPrimaryRoot(player);
      SpiritRootType newRoot = SpiritRootType.randomCommonRoot(player.getRandom());
      if (existingRoot.isPresent()) {
         SpiritRootType oldRoot = existingRoot.get();

         for (int attempt = 0; attempt < 16 && newRoot == oldRoot; attempt++) {
            newRoot = SpiritRootType.randomCommonRoot(player.getRandom());
         }

         if (newRoot == oldRoot) {
            List<SpiritRootType> alternatives = List.of(SpiritRootType.values())
               .stream()
               .filter(root -> root != oldRoot)
               .filter(root -> root == SpiritRootType.HEAVEN || root.canRollRandomly())
               .toList();
            if (!alternatives.isEmpty()) {
               newRoot = alternatives.get(player.getRandom().nextInt(alternatives.size()));
            }
         }
      }

      return setRoot(player, newRoot);
   }

   public static void clearRoot(ServerPlayer player) {
      clearRoot(player, false);
   }

   public static void clearRoot(ServerPlayer player, boolean clearSpiritCompanion) {
      Optional<SpiritRootType> existingRoot = getPrimaryRoot(player);
      if (existingRoot.isEmpty()) {
         SpiritRootNetwork.syncToClient(player, null);
      } else {
         forgetElementalSkillBundle(player, existingRoot.get());
         if (clearSpiritCompanion) {
            clearLinkedSpiritCompanion(player, existingRoot.get());
         }

         removeRootTag(player);
         SpiritRootNetwork.syncToClient(player, null);
      }
   }

   public static void syncCurrentRoot(ServerPlayer player) {
      Optional<SpiritRootType> root = getPrimaryRoot(player);
      if (root.isPresent()) {
         syncLinkedSystems(player, root.get(), false);
      } else {
         SpiritRootNetwork.syncToClient(player, null);
      }
   }

   public static MutableComponent createRootSummary(SpiritRootType root) {
      return Component.translatable(
         "mortal_cultivation.command.spirit_root.summary", new Object[]{root.getDisplayName(), root.getSpiritDisplayName(), root.getInspirationSkillName()}
      );
   }

   public static MutableComponent createInlineDisplay(@Nullable SpiritRootType root) {
      return root == null
         ? Component.empty()
         : Component.literal("(")
            .setStyle(Style.EMPTY.withColor(root.getColor()))
            .append(root.getDisplayName())
            .append(Component.literal(")").setStyle(Style.EMPTY.withColor(root.getColor())));
   }

   public static MutableComponent createStatusDisplay(@Nullable SpiritRootType root) {
      MutableComponent rootName = root == null
         ? Component.translatable("mortal_cultivation.spirit_root.none").withStyle(ChatFormatting.GRAY)
         : root.getDisplayName();
      return Component.translatable("mortal_cultivation.spirit_root.status", new Object[]{rootName});
   }

   private static void syncLinkedSystems(ServerPlayer player, SpiritRootType root, boolean notifyPlayer) {
      boolean spiritGranted = ensureSpiritCompanions(player, root);
      int grantedSkillCount = ensureElementalSkillBundle(player, root);
      SpiritRootNetwork.syncToClient(player, root);
      if (notifyPlayer) {
         player.sendSystemMessage(
            Component.translatable("mortal_cultivation.spirit_root.awakened", new Object[]{root.getDisplayName(), root.getSpiritDisplayName()})
         );
         if (grantedSkillCount > 0) {
            player.sendSystemMessage(Component.translatable("mortal_cultivation.spirit_root.skill_bundle", new Object[]{root.getDisplayName()}));
         } else if (spiritGranted) {
            player.sendSystemMessage(Component.translatable("mortal_cultivation.spirit_root.resonance", new Object[]{root.getSpiritDisplayName()}));
         }
      }
   }

   private static boolean ensureSpiritCompanions(ServerPlayer player, SpiritRootType root) {
      ISpiritWielder spiritWielder = TensuraStorages.getSpiritFrom(player);
      boolean updated = false;

      for (Element element : root.getResonantElements()) {
         SpiritLevel currentLevel = spiritWielder.getSpiritLevel(element);
         if ((currentLevel == null || currentLevel.getId() < root.getSpiritLevel().getId()) && spiritWielder.setSpiritLevel(element, root.getSpiritLevel())) {
            updated = true;
         }
      }

      if (updated) {
         spiritWielder.markDirty();
      }

      return updated;
   }

   private static int ensureElementalSkillBundle(ServerPlayer player, SpiritRootType root) {
      int grantedSkillCount = 0;

      for (ManasSkill skill : root.getGrantedSkills()) {
         if (SkillHelper.learnSkill(player, skill, -1, null)) {
            grantedSkillCount++;
         }
      }

      return grantedSkillCount;
   }

   private static void forgetElementalSkillBundle(ServerPlayer player, SpiritRootType root) {
      SkillStorage skills = SkillAPI.getSkillsFrom(player);

      for (ManasSkill skill : root.getGrantedSkills()) {
         skills.forgetSkill(skill);
      }

      skills.markDirty();
   }

   private static void clearLinkedSpiritCompanion(ServerPlayer player, SpiritRootType root) {
      ISpiritWielder spiritWielder = TensuraStorages.getSpiritFrom(player);

      try {
         Field field = spiritWielder.getClass().getDeclaredField("spiritLevels");
         field.setAccessible(true);
         Object levels = field.get(spiritWielder);
         boolean updated = false;
         if (levels instanceof int[] intLevels) {
            for (Element element : root.getResonantElements()) {
               int idx = element.getId();
               if (idx < intLevels.length && intLevels[idx] != 0) {
                  intLevels[idx] = 0;
                  updated = true;
               }
            }
         } else if (levels instanceof List<?> rawList) {
            List<Integer> intList = (List<Integer>)rawList;

            for (Element element : root.getResonantElements()) {
               int idx = element.getId();
               if (idx < intList.size() && intList.get(idx) != 0) {
                  intList.set(idx, 0);
                  updated = true;
               }
            }
         }

         if (updated) {
            spiritWielder.markDirty();
         }
      } catch (Exception e) {
         MortalCultivation.LOGGER.warn("Failed to clear spirit companion level: {}", e.getMessage());
      }
   }

   private static void writeRoot(ServerPlayer player, SpiritRootType root, String assignedAt) {
      CompoundTag playerData = player.getPersistentData();
      CompoundTag modData = getOrCreateModData(playerData);
      CompoundTag rootData = modData.contains("SpiritRoot") ? modData.getCompound("SpiritRoot") : new CompoundTag();
      rootData.putString("Primary", root.getId());
      rootData.putString("Category", root.getCategory().getId());
      rootData.putString("Variant", "standard");
      rootData.putString("AssignedAt", assignedAt);
      modData.put("SpiritRoot", rootData);
      ensureModDataSaved(playerData, modData);
   }

   private static CompoundTag getRootTag(Player player) {
      CompoundTag playerData = player.getPersistentData();
      if (!playerData.contains("PlayerPersisted")) {
         return null;
      }

      CompoundTag persistedData = playerData.getCompound("PlayerPersisted");
      if (!persistedData.contains("mortal_cultivation")) {
         return null;
      }

      CompoundTag modData = persistedData.getCompound("mortal_cultivation");
      return !modData.contains("SpiritRoot") ? null : modData.getCompound("SpiritRoot");
   }

   private static void removeRootTag(ServerPlayer player) {
      CompoundTag playerData = player.getPersistentData();
      if (playerData.contains("PlayerPersisted")) {
         CompoundTag persistedData = playerData.getCompound("PlayerPersisted");
         if (persistedData.contains("mortal_cultivation")) {
            CompoundTag modData = persistedData.getCompound("mortal_cultivation");
            modData.remove("SpiritRoot");
            ensureModDataSaved(playerData, modData);
         }
      }
   }

   private static CompoundTag getOrCreateModData(CompoundTag playerData) {
      if (!playerData.contains("PlayerPersisted")) {
         playerData.put("PlayerPersisted", new CompoundTag());
      }

      CompoundTag persistedData = playerData.getCompound("PlayerPersisted");
      if (!persistedData.contains("mortal_cultivation")) {
         persistedData.put("mortal_cultivation", new CompoundTag());
      }

      return persistedData.getCompound("mortal_cultivation");
   }

   private static void ensureModDataSaved(CompoundTag playerData, CompoundTag modData) {
      CompoundTag persistedData = playerData.getCompound("PlayerPersisted");
      persistedData.put("mortal_cultivation", modData);
      playerData.put("PlayerPersisted", persistedData);
   }
}
