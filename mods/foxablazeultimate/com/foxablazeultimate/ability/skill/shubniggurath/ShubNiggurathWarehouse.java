package com.foxablazeultimate.ability.skill.shubniggurath;

import com.foxablazeultimate.FoxAblazeUltimateMod;
import com.foxablazeultimate.ability.skill.ciel.CielPassiveHandler;
import com.foxablazeultimate.config.ShubNiggurathConfig;
import com.foxablazeultimate.network.OpenShubWarehousePayload;
import com.foxablazeultimate.registry.FoxAblazeUltimateSkills;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.storage.api.StorageHolder;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ability.AbilitySlot;
import io.github.manasmods.tensura.storage.ability.IAbility;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import io.github.manasmods.tensura.util.SubordinateHelper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public final class ShubNiggurathWarehouse {
   public static final String ARCHIVE_TAG = "ShubNiggurathSkillArchive";
   private static final String PENDING_GIFT_SKILL = "ShubPendingGiftSkill";
   private static final String PENDING_GIFT_PRESET = "ShubPendingGiftPreset";
   private static final String PENDING_GIFT_SLOT = "ShubPendingGiftSlot";
   private static final String SYNTHESIS_COOLDOWN_END = "ShubSynthesisCooldownEnd";
   private static final String COPY_COOLDOWN_END = "ShubCopyCooldownEnd";
   private static final String GIFT_COOLDOWN_END = "ShubGiftCooldownEnd";
   private static final String COOLDOWN_MIGRATED = "ShubCooldownMigratedV2";

   private ShubNiggurathWarehouse() {
   }

   public static void archiveMasteredSkills(ManasSkillInstance shub, LivingEntity owner) {
      Set<ResourceLocation> archived = readArchivedSkills(shub);
      boolean ultimateGiftingUnlocked = canGiftUltimateSkills(shub, owner);
      boolean changed = false;

      for (ManasSkillInstance learned : SkillAPI.getSkillsFrom(owner).getLearnedSkills()) {
         if (!(learned.getMastery() < 0.0) && !learned.isTemporarySkill()) {
            ResourceLocation id = learned.getSkillId();
            ManasSkill skill = lookup(id);
            if (id != null && (isWarehouseEligibleSkill(skill) || ultimateGiftingUnlocked && isGiftableUltimateSkill(skill))) {
               changed |= archived.add(id);
            }
         }
      }

      if (changed) {
         writeArchivedSkills(shub, archived);
         SkillAPI.getSkillsFrom(owner).markDirty();
      }
   }

   public static void archiveSkills(ManasSkillInstance shub, Collection<ResourceLocation> skills) {
      Set<ResourceLocation> archived = readArchivedSkills(shub);
      boolean changed = false;

      for (ResourceLocation id : skills) {
         if (id != null && isArchivableSkill(lookup(id))) {
            changed |= archived.add(id);
         }
      }

      if (changed) {
         writeArchivedSkills(shub, archived);
      }
   }

   public static Set<ResourceLocation> readArchivedSkills(ManasSkillInstance shub) {
      Set<ResourceLocation> result = new LinkedHashSet<>();
      ListTag list = shub.getOrCreateTag().getList("ShubNiggurathSkillArchive", 8);
      boolean changed = false;

      for (int i = 0; i < list.size(); i++) {
         ResourceLocation id = ResourceLocation.tryParse(list.getString(i));
         if (id == null || !isArchivableSkill(lookup(id))) {
            changed = true;
         } else if (!result.add(id)) {
            changed = true;
         }
      }

      if (changed) {
         writeArchivedSkills(shub, result);
      }

      return result;
   }

   private static void writeArchivedSkills(ManasSkillInstance shub, Collection<ResourceLocation> skills) {
      ListTag list = new ListTag();
      skills.stream().sorted(Comparator.comparing(ResourceLocation::toString)).forEach(id -> list.add(StringTag.valueOf(id.toString())));
      shub.getOrCreateTag().put("ShubNiggurathSkillArchive", list);
      shub.markDirty();
   }

   public static List<ShubNiggurathWarehouse.CreationRecipe> getConfiguredRecipes() {
      List<ShubNiggurathWarehouse.CreationRecipe> recipes = new ArrayList<>();
      List<String> configured = ShubNiggurathConfig.get().ShubNiggurath.creationRecipes;
      if (configured == null) {
         return recipes;
      }

      for (String raw : configured) {
         if (raw != null && !raw.isBlank()) {
            String[] outputSplit = raw.trim().split("=", 2);
            String[] inputSplit = outputSplit.length == 2 ? outputSplit[0].split("\\+", 2) : new String[0];
            if (outputSplit.length == 2 && inputSplit.length == 2) {
               ResourceLocation first = ResourceLocation.tryParse(inputSplit[0].trim());
               ResourceLocation second = ResourceLocation.tryParse(inputSplit[1].trim());
               ResourceLocation result = ResourceLocation.tryParse(outputSplit[1].trim());
               if (first == null || second == null || result == null) {
                  warnInvalidRecipe(raw);
               } else if (isWarehouseEligibleSkill(lookup(first)) && isWarehouseEligibleSkill(lookup(second)) && isWarehouseEligibleSkill(lookup(result))) {
                  recipes.add(new ShubNiggurathWarehouse.CreationRecipe(first, second, result));
               } else {
                  warnDisallowedRecipe(raw);
               }
            } else {
               warnInvalidRecipe(raw);
            }
         }
      }

      return recipes;
   }

   private static void warnInvalidRecipe(String recipe) {
      FoxAblazeUltimateMod.LOGGER.warn("[Shub-Niggurath] Ignoring invalid creation recipe: {}", recipe);
   }

   private static void warnDisallowedRecipe(String recipe) {
      FoxAblazeUltimateMod.LOGGER.warn("[Shub-Niggurath] Ignoring creation recipe containing a skill outside the warehouse tiers: {}", recipe);
   }

   public static void openWarehouse(ServerPlayer player, ManasSkillInstance shub, int abilitySlot) {
      migrateLegacyState(player, shub);
      archiveMasteredSkills(shub, player);
      ShubNiggurathWarehouse.ActiveSlot activeSlot = getActiveShubSlot(player, abilitySlot);
      if (activeSlot != null && activeSlot.abilitySlot().getMode() != 0) {
         activeSlot.abilitySlot().setMode(0);
         syncAbility(player, activeSlot.ability());
      }

      boolean ultimateGiftingUnlocked = canGiftUltimateSkills(shub, player);
      List<ResourceLocation> visibleSkills = readArchivedSkills(shub)
         .stream()
         .filter(id -> isWarehouseEligibleSkill(lookup(id)) || ultimateGiftingUnlocked && isGiftableUltimateSkill(lookup(id)))
         .sorted(Comparator.comparing(ResourceLocation::toString))
         .toList();
      List<ResourceLocation> giftOnlySkills = visibleSkills.stream().filter(id -> isGiftableUltimateSkill(lookup(id))).toList();
      List<OpenShubWarehousePayload.RecipeEntry> recipes = getConfiguredRecipes()
         .stream()
         .filter(
            recipe -> isWarehouseEligibleSkill(lookup(recipe.first()))
               && isWarehouseEligibleSkill(lookup(recipe.second()))
               && isWarehouseEligibleSkill(lookup(recipe.result()))
         )
         .map(recipe -> new OpenShubWarehousePayload.RecipeEntry(recipe.first(), recipe.second(), recipe.result()))
         .toList();
      NetworkManager.sendToPlayer(player, new OpenShubWarehousePayload(0, abilitySlot, visibleSkills, giftOnlySkills, recipes));
   }

   public static void handleWarehouseAction(ServerPlayer player, int action, int abilitySlot, ResourceLocation primary, ResourceLocation secondary) {
      Optional<ManasSkillInstance> shubOptional = SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)FoxAblazeUltimateSkills.SHUB_NIGGURATH.get());
      if (!shubOptional.isEmpty() && primary != null) {
         ManasSkillInstance shub = shubOptional.get();
         if (player.isAlive() && !player.isSpectator() && shub.canInteractSkill(player)) {
            migrateLegacyState(player, shub);
            ShubNiggurathWarehouse.ActiveSlot activeSlot = getActiveShubSlot(player, abilitySlot);
            if (activeSlot == null) {
               fail(player, "tensura.ability.activation_failed");
            } else {
               if (activeSlot.abilitySlot().getMode() != 0) {
                  activeSlot.abilitySlot().setMode(0);
                  syncAbility(player, activeSlot.ability());
               }

               archiveMasteredSkills(shub, player);
               switch (action) {
                  case 0:
                     copyToSelf(player, shub, primary);
                     break;
                  case 1:
                     synthesizeInformation(player, shub, primary, secondary, abilitySlot);
                     break;
                  case 2:
                     prepareGift(player, shub, primary, activeSlot);
               }
            }
         } else {
            fail(player, "tensura.ability.activation_failed");
         }
      }
   }

   private static void synthesizeInformation(ServerPlayer player, ManasSkillInstance shub, ResourceLocation first, ResourceLocation second, int abilitySlot) {
      if (second != null) {
         if (first.equals(second)) {
            fail(player, "foxablazeultimate.skill.shub_niggurath.create.missing_information");
         } else if (isOperationOnCooldown(player, shub, "ShubSynthesisCooldownEnd")) {
            fail(player, "foxablazeultimate.skill.shub_niggurath.warehouse.cooldown");
         } else {
            Set<ResourceLocation> archived = readArchivedSkills(shub);
            if (archived.contains(first) && archived.contains(second)) {
               ShubNiggurathWarehouse.CreationRecipe recipe = findRecipe(first, second);
               if (recipe == null
                  || !isWarehouseEligibleSkill(lookup(first))
                  || !isWarehouseEligibleSkill(lookup(second))
                  || !isWarehouseEligibleSkill(lookup(recipe.result()))) {
                  fail(player, "foxablazeultimate.skill.shub_niggurath.create.missing_information");
               } else if (archived.contains(recipe.result())) {
                  fail(player, "foxablazeultimate.skill.shub_niggurath.create.already_archived");
               } else {
                  double epCost = configuredPercent(ShubNiggurathConfig.get().ShubNiggurath.synthesisPermanentEpCostPercent);
                  if (!canConsumePermanentEp(player, epCost)) {
                     fail(player, "foxablazeultimate.skill.shub_niggurath.create.insufficient_ep", epCost);
                  } else {
                     Optional<ManasSkillInstance> firstInstance = SkillAPI.getSkillsFrom(player).getSkill(first);
                     Optional<ManasSkillInstance> secondInstance = SkillAPI.getSkillsFrom(player).getSkill(second);
                     if (!firstInstance.isEmpty()
                        && !secondInstance.isEmpty()
                        && !firstInstance.get().isTemporarySkill()
                        && !secondInstance.get().isTemporarySkill()
                        && !(firstInstance.get().getMastery() < 0.0)
                        && !(secondInstance.get().getMastery() < 0.0)) {
                        ManasSkill resultSkill = lookup(recipe.result());
                        if (isReplicableSkill(resultSkill) && !SkillAPI.getSkillsFrom(player).getSkill(resultSkill).isPresent()) {
                           ManasSkillInstance firstSnapshot = copyInstance(firstInstance.get());
                           ManasSkillInstance secondSnapshot = copyInstance(secondInstance.get());
                           IAbility ability = TensuraStorages.getAbilityFrom(player);
                           List<ShubNiggurathWarehouse.SkillSlotSnapshot> slotSnapshots = snapshotMaterialSlots(ability, first, second);
                           boolean consumeMaterials = ShubNiggurathConfig.get().ShubNiggurath.synthesisConsumesMaterials;
                           if (consumeMaterials) {
                              SkillAPI.getSkillsFrom(player).forgetSkill(first);
                              if (SkillAPI.getSkillsFrom(player).getSkill(first).isPresent()) {
                                 restoreSynthesisState(player, firstSnapshot, secondSnapshot, slotSnapshots);
                                 fail(player, "foxablazeultimate.skill.shub_niggurath.create.failed");
                                 return;
                              }

                              SkillAPI.getSkillsFrom(player).forgetSkill(second);
                              if (SkillAPI.getSkillsFrom(player).getSkill(second).isPresent()) {
                                 restoreSynthesisState(player, firstSnapshot, secondSnapshot, slotSnapshots);
                                 fail(player, "foxablazeultimate.skill.shub_niggurath.create.failed");
                                 return;
                              }
                           }

                           if (!grantReplicatedSkill(player, resultSkill)) {
                              if (consumeMaterials) {
                                 restoreSynthesisState(player, firstSnapshot, secondSnapshot, slotSnapshots);
                              }

                              fail(player, "foxablazeultimate.skill.shub_niggurath.create.failed");
                           } else {
                              consumePermanentEp(player, epCost);
                              archiveSkills(shub, List.of(recipe.result()));
                              completeOperation(player, shub, "ShubSynthesisCooldownEnd", ShubNiggurathConfig.get().ShubNiggurath.creationCooldownSeconds);
                              player.sendSystemMessage(
                                 Component.translatable(
                                       "foxablazeultimate.skill.shub_niggurath.create.success", new Object[]{displayName(lookup(recipe.result()))}
                                    )
                                    .withStyle(ChatFormatting.GOLD)
                              );
                              if (consumeMaterials) {
                                 player.sendSystemMessage(
                                    Component.translatable("foxablazeultimate.skill.shub_niggurath.create.materials_consumed")
                                       .withStyle(ChatFormatting.DARK_RED)
                                 );
                              }

                              playSuccess(player);
                              openWarehouse(player, shub, abilitySlot);
                           }
                        } else {
                           fail(player, "foxablazeultimate.skill.shub_niggurath.create.already_archived");
                        }
                     } else {
                        fail(player, "foxablazeultimate.skill.shub_niggurath.create.missing_materials");
                     }
                  }
               }
            } else {
               fail(player, "foxablazeultimate.skill.shub_niggurath.create.missing_information");
            }
         }
      }
   }

   private static ShubNiggurathWarehouse.CreationRecipe findRecipe(ResourceLocation first, ResourceLocation second) {
      return getConfiguredRecipes()
         .stream()
         .filter(recipe -> recipe.first().equals(first) && recipe.second().equals(second) || recipe.first().equals(second) && recipe.second().equals(first))
         .findFirst()
         .orElse(null);
   }

   private static void copyToSelf(ServerPlayer player, ManasSkillInstance shub, ResourceLocation skillId) {
      if (isOperationOnCooldown(player, shub, "ShubCopyCooldownEnd")) {
         fail(player, "foxablazeultimate.skill.shub_niggurath.warehouse.cooldown");
      } else if (!readArchivedSkills(shub).contains(skillId)) {
         fail(player, "foxablazeultimate.skill.shub_niggurath.copy.not_archived");
      } else {
         ManasSkill skill = lookup(skillId);
         double epCost = configuredPercent(ShubNiggurathConfig.get().ShubNiggurath.copyPermanentEpCostPercent);
         if (!canConsumePermanentEp(player, epCost)) {
            fail(player, "foxablazeultimate.skill.shub_niggurath.copy.insufficient_ep", epCost);
         } else if (!grantReplicatedSkill(player, skill)) {
            fail(player, "foxablazeultimate.skill.shub_niggurath.copy.failed");
         } else {
            consumePermanentEp(player, epCost);
            completeOperation(player, shub, "ShubCopyCooldownEnd", ShubNiggurathConfig.get().ShubNiggurath.copyCooldownSeconds);
            player.sendSystemMessage(
               Component.translatable("foxablazeultimate.skill.shub_niggurath.copy.success", new Object[]{displayName(skill)}).withStyle(ChatFormatting.GOLD)
            );
            playSuccess(player);
         }
      }
   }

   private static void prepareGift(ServerPlayer player, ManasSkillInstance shub, ResourceLocation skillId, ShubNiggurathWarehouse.ActiveSlot currentSlot) {
      if (isOperationOnCooldown(player, shub, "ShubGiftCooldownEnd")) {
         fail(player, "foxablazeultimate.skill.shub_niggurath.warehouse.cooldown");
      } else {
         ManasSkill skill = lookup(skillId);
         if (!readArchivedSkills(shub).contains(skillId)) {
            fail(player, "foxablazeultimate.skill.shub_niggurath.copy.failed");
         } else if (isUltimateSkill(skill) && !canGiftUltimateSkills(shub, player)) {
            fail(player, "foxablazeultimate.skill.shub_niggurath.gift.ultimate.requires_mastery");
         } else if (!isGiftableSkill(shub, player, skill)) {
            fail(player, "foxablazeultimate.skill.shub_niggurath.copy.failed");
         } else {
            cancelPendingGift(player, shub, true);
            ShubNiggurathWarehouse.ActiveSlot refreshed = getActiveShubSlot(player, currentSlot.slot());
            if (refreshed == null) {
               fail(player, "tensura.ability.activation_failed");
            } else {
               CompoundTag tag = shub.getOrCreateTag();
               tag.putString("ShubPendingGiftSkill", skillId.toString());
               tag.putInt("ShubPendingGiftPreset", refreshed.preset());
               tag.putInt("ShubPendingGiftSlot", refreshed.slot());
               refreshed.abilitySlot().setMode(1);
               syncSkillAndAbility(player, shub, refreshed.ability());
               String preparedKey = isUltimateSkill(skill)
                  ? "foxablazeultimate.skill.shub_niggurath.gift.ultimate.prepared"
                  : "foxablazeultimate.skill.shub_niggurath.gift.prepared";
               player.displayClientMessage(Component.translatable(preparedKey, new Object[]{displayName(skill)}).withStyle(ChatFormatting.GOLD), true);
            }
         }
      }
   }

   public static void executePendingGift(ServerPlayer player, ManasSkillInstance shub, int keyNumber) {
      if (isOperationOnCooldown(player, shub, "ShubGiftCooldownEnd")) {
         fail(player, "foxablazeultimate.skill.shub_niggurath.warehouse.cooldown");
      } else {
         CompoundTag tag = shub.getTag();
         if (tag != null && tag.contains("ShubPendingGiftSkill")) {
            int preset = tag.getInt("ShubPendingGiftPreset");
            int slot = tag.getInt("ShubPendingGiftSlot");
            ShubNiggurathWarehouse.ActiveSlot activeSlot = getActiveShubSlot(player, keyNumber);
            if (activeSlot != null && activeSlot.preset() == preset && activeSlot.slot() == slot && activeSlot.abilitySlot().getMode() == 1) {
               ResourceLocation skillId = ResourceLocation.tryParse(tag.getString("ShubPendingGiftSkill"));
               ManasSkill skill = lookup(skillId);
               if (skillId != null && readArchivedSkills(shub).contains(skillId) && isGiftableSkill(shub, player, skill)) {
                  double range = Math.max(1.0, ShubNiggurathConfig.get().ShubNiggurath.giftRange);
                  LivingEntity target = ObjectSelectionHelper.getTargetingEntity(player, range, false);
                  boolean ultimateGift = isUltimateSkill(skill);
                  if (ultimateGift && target instanceof Player) {
                     fail(player, "foxablazeultimate.skill.shub_niggurath.gift.ultimate.players_forbidden");
                  } else if (ultimateGift && !isEligibleUltimateGiftTarget(player, target)) {
                     fail(player, "foxablazeultimate.skill.shub_niggurath.gift.ultimate.subordinate_only");
                  } else if ((ultimateGift || isEligibleGiftTarget(player, target)) && target != null && !(player.distanceToSqr(target) > range * range)) {
                     if (ultimateGift) {
                        int maximum = Math.max(0, ShubNiggurathConfig.get().ShubNiggurath.maxUltimateSkillsPerSubordinate);
                        long current = countUltimateSkills(target);
                        if (current >= maximum) {
                           fail(player, "foxablazeultimate.skill.shub_niggurath.gift.ultimate.limit", current, maximum);
                           return;
                        }
                     }

                     double magiculeCostPercent = configuredPercent(ShubNiggurathConfig.get().ShubNiggurath.giftMagiculeCostPercent);
                     if (!canConsumeCurrentMagicule(player, magiculeCostPercent)) {
                        fail(player, "foxablazeultimate.skill.shub_niggurath.gift.insufficient_magicule");
                     } else if (!grantGiftedSkill(shub, player, target, skill)) {
                        fail(player, "foxablazeultimate.skill.shub_niggurath.gift.failed");
                     } else {
                        consumeCurrentMagicule(player, magiculeCostPercent);
                        completeOperation(player, shub, "ShubGiftCooldownEnd", ShubNiggurathConfig.get().ShubNiggurath.giftCooldownSeconds);
                        cancelPendingGift(player, shub, true);
                        player.sendSystemMessage(
                           Component.translatable(
                                 "foxablazeultimate.skill.shub_niggurath.gift.success", new Object[]{displayName(skill), target.getDisplayName()}
                              )
                              .withStyle(ChatFormatting.GOLD)
                        );
                        if (target instanceof ServerPlayer targetPlayer) {
                           targetPlayer.sendSystemMessage(
                              Component.translatable(
                                    "foxablazeultimate.skill.shub_niggurath.gift.received", new Object[]{player.getDisplayName(), displayName(skill)}
                                 )
                                 .withStyle(ChatFormatting.GOLD)
                           );
                        }

                        playSuccess(player);
                     }
                  } else {
                     fail(player, "foxablazeultimate.skill.shub_niggurath.gift.invalid_target");
                  }
               } else {
                  cancelPendingGift(player, shub, true);
                  fail(player, "foxablazeultimate.skill.shub_niggurath.copy.not_archived");
               }
            } else {
               fail(player, "foxablazeultimate.skill.shub_niggurath.gift.wrong_slot");
            }
         }
      }
   }

   public static boolean hasPendingGift(ManasSkillInstance shub) {
      CompoundTag tag = shub.getTag();
      return tag != null && tag.contains("ShubPendingGiftSkill");
   }

   private static void clearPendingGiftState(ManasSkillInstance shub) {
      CompoundTag tag = shub.getTag();
      if (tag != null) {
         tag.remove("ShubPendingGiftSkill");
         tag.remove("ShubPendingGiftPreset");
         tag.remove("ShubPendingGiftSlot");
         shub.markDirty();
      }
   }

   public static void cancelPendingGift(ServerPlayer player, ManasSkillInstance shub, boolean restoreSlot) {
      CompoundTag tag = shub.getTag();
      boolean hadPendingGift = tag != null && tag.contains("ShubPendingGiftSkill");
      IAbility ability = TensuraStorages.getAbilityFrom(player);
      boolean abilityChanged = false;
      if (restoreSlot) {
         for (int preset = 0; preset < ability.getPresets().size(); preset++) {
            for (AbilitySlot abilitySlot : ability.getAbilitySlots(preset)) {
               if (((ShubNiggurathSkill)FoxAblazeUltimateSkills.SHUB_NIGGURATH.get()).equals(abilitySlot.getSkill()) && abilitySlot.getMode() != 0) {
                  abilitySlot.setMode(0);
                  abilityChanged = true;
               }
            }
         }
      }

      if (hadPendingGift) {
         clearPendingGiftState(shub);
      }

      if (hadPendingGift || abilityChanged) {
         syncSkillAndAbility(player, shub, ability);
      }
   }

   private static ShubNiggurathWarehouse.ActiveSlot getActiveShubSlot(ServerPlayer player, int requestedSlot) {
      IAbility ability = TensuraStorages.getAbilityFrom(player);
      int preset = ability.getActivePreset();
      if (requestedSlot >= 0 && requestedSlot < ability.getAbilitySlots(preset).size()) {
         AbilitySlot slot = ability.getAbilitySlot(preset, requestedSlot);
         return !((ShubNiggurathSkill)FoxAblazeUltimateSkills.SHUB_NIGGURATH.get()).equals(slot.getSkill())
            ? null
            : new ShubNiggurathWarehouse.ActiveSlot(ability, preset, requestedSlot, slot);
      } else {
         return null;
      }
   }

   private static void syncAbility(ServerPlayer player, IAbility ability) {
      ability.markDirty();
      ((StorageHolder)player).manasCore$sync(player);
   }

   private static void syncSkillAndAbility(ServerPlayer player, ManasSkillInstance shub, IAbility ability) {
      shub.markDirty();
      SkillAPI.getSkillsFrom(player).markDirty();
      ability.markDirty();
      ((StorageHolder)player).manasCore$sync(player);
   }

   public static void migrateLegacyState(ServerPlayer player, ManasSkillInstance shub) {
      CompoundTag tag = shub.getOrCreateTag();
      if (!tag.getBoolean("ShubCooldownMigratedV2")) {
         List<Integer> legacy = new ArrayList<>(shub.getCooldownList());
         migrateLegacyCooldown(player, tag, legacy, 0, "ShubSynthesisCooldownEnd");
         migrateLegacyCooldown(player, tag, legacy, 1, "ShubCopyCooldownEnd");
         migrateLegacyCooldown(player, tag, legacy, 2, "ShubGiftCooldownEnd");
         shub.setCoolDownList(new ArrayList<>(List.of(0)));
         tag.putBoolean("ShubCooldownMigratedV2", true);
         syncSkillState(player, shub);
      }

      IAbility ability = TensuraStorages.getAbilityFrom(player);
      boolean pendingGift = hasPendingGift(shub);
      int pendingPreset = pendingGift ? tag.getInt("ShubPendingGiftPreset") : -1;
      int pendingSlot = pendingGift ? tag.getInt("ShubPendingGiftSlot") : -1;
      boolean pendingSlotFound = false;
      boolean pendingGiftCleared = false;
      boolean abilityChanged = false;

      for (int preset = 0; preset < ability.getPresets().size(); preset++) {
         List<AbilitySlot> slots = ability.getAbilitySlots(preset);

         for (int slotIndex = 0; slotIndex < slots.size(); slotIndex++) {
            AbilitySlot slot = slots.get(slotIndex);
            if (((ShubNiggurathSkill)FoxAblazeUltimateSkills.SHUB_NIGGURATH.get()).equals(slot.getSkill())) {
               boolean isPendingSlot = pendingGift && preset == pendingPreset && slotIndex == pendingSlot;
               if (isPendingSlot) {
                  pendingSlotFound = true;
                  if (slot.getMode() != 0 && slot.getMode() != 1) {
                     slot.setMode(1);
                     abilityChanged = true;
                  }
               } else if (slot.getMode() != 0) {
                  slot.setMode(0);
                  abilityChanged = true;
               }
            }
         }
      }

      if (pendingGift && !pendingSlotFound) {
         clearPendingGiftState(shub);
         pendingGift = false;
         pendingGiftCleared = true;
      }

      if (!pendingGift) {
         for (int preset = 0; preset < ability.getPresets().size(); preset++) {
            for (AbilitySlot slot : ability.getAbilitySlots(preset)) {
               if (((ShubNiggurathSkill)FoxAblazeUltimateSkills.SHUB_NIGGURATH.get()).equals(slot.getSkill()) && slot.getMode() != 0) {
                  slot.setMode(0);
                  abilityChanged = true;
               }
            }
         }
      }

      if (abilityChanged || pendingGiftCleared) {
         syncSkillAndAbility(player, shub, ability);
      }
   }

   private static void migrateLegacyCooldown(ServerPlayer player, CompoundTag tag, List<Integer> legacy, int index, String key) {
      if (index < legacy.size() && legacy.get(index) > 0) {
         long migratedEnd = currentGameTime(player) + legacy.get(index).intValue() * 20L;
         tag.putLong(key, Math.max(tag.getLong(key), migratedEnd));
      }
   }

   private static boolean isOperationOnCooldown(ServerPlayer player, ManasSkillInstance shub, String cooldownKey) {
      CompoundTag tag = shub.getTag();
      return tag != null && tag.getLong(cooldownKey) > currentGameTime(player);
   }

   private static void completeOperation(ServerPlayer player, ManasSkillInstance shub, String cooldownKey, int cooldownSeconds) {
      long end = currentGameTime(player) + Math.max(0, cooldownSeconds) * 20L;
      shub.getOrCreateTag().putLong(cooldownKey, end);
      shub.addMasteryPoint(player);
      syncSkillState(player, shub);
   }

   private static long currentGameTime(ServerPlayer player) {
      return player.getServer().overworld().getGameTime();
   }

   private static void syncSkillState(ServerPlayer player, ManasSkillInstance shub) {
      shub.markDirty();
      SkillAPI.getSkillsFrom(player).markDirty();
      ((StorageHolder)player).manasCore$sync(player);
   }

   private static boolean grantReplicatedSkill(LivingEntity target, ManasSkill skill) {
      return !isReplicableSkill(skill) ? false : grantSkill(target, skill);
   }

   private static boolean grantGiftedSkill(ManasSkillInstance shub, ServerPlayer owner, LivingEntity target, ManasSkill skill) {
      if (!isGiftableSkill(shub, owner, skill)) {
         return false;
      }

      if (isUltimateSkill(skill)) {
         int maximum = Math.max(0, ShubNiggurathConfig.get().ShubNiggurath.maxUltimateSkillsPerSubordinate);
         if (!isEligibleUltimateGiftTarget(owner, target) || countUltimateSkills(target) >= maximum) {
            return false;
         }
      }

      return grantSkill(target, skill);
   }

   private static boolean grantSkill(LivingEntity target, ManasSkill skill) {
      Optional<ManasSkillInstance> existing = SkillAPI.getSkillsFrom(target).getSkill(skill);
      if (existing.isPresent()) {
         ManasSkillInstance instance = existing.get();
         if (instance.isTemporarySkill()) {
            instance.setRemoveTime(-1);
            instance.markDirty();
            SkillAPI.getSkillsFrom(target).markDirty();
            return true;
         } else {
            return false;
         }
      } else {
         TensuraSkillInstance created = new TensuraSkillInstance(skill);
         created.setMastery(0.0);
         created.getOrCreateTag().putBoolean("NoMagiculeCost", true);
         created.getOrCreateTag().putBoolean("GiftedInstance", true);
         created.getOrCreateTag().putString("ShubNiggurathCreator", FoxAblazeUltimateSkills.SHUB_NIGGURATH.getId().toString());
         created.markDirty();
         return SkillHelper.learnSkill(target, created);
      }
   }

   private static ManasSkillInstance copyInstance(ManasSkillInstance instance) {
      return (ManasSkillInstance)(instance instanceof TensuraSkillInstance tensura ? tensura.clone() : instance.copy());
   }

   private static List<ShubNiggurathWarehouse.SkillSlotSnapshot> snapshotMaterialSlots(IAbility ability, ResourceLocation first, ResourceLocation second) {
      List<ShubNiggurathWarehouse.SkillSlotSnapshot> snapshots = new ArrayList<>();

      for (int preset = 0; preset < ability.getPresets().size(); preset++) {
         List<AbilitySlot> slots = ability.getAbilitySlots(preset);

         for (int slot = 0; slot < slots.size(); slot++) {
            AbilitySlot abilitySlot = slots.get(slot);
            ManasSkill skill = abilitySlot.getSkill();
            if (skill != null && skill.getRegistryName() != null && (first.equals(skill.getRegistryName()) || second.equals(skill.getRegistryName()))) {
               snapshots.add(new ShubNiggurathWarehouse.SkillSlotSnapshot(preset, slot, skill, abilitySlot.getMode()));
            }
         }
      }

      return snapshots;
   }

   private static void restoreSynthesisState(
      ServerPlayer player, ManasSkillInstance first, ManasSkillInstance second, List<ShubNiggurathWarehouse.SkillSlotSnapshot> slots
   ) {
      SkillAPI.getSkillsFrom(player).updateSkill(first, false);
      SkillAPI.getSkillsFrom(player).updateSkill(second, false);
      IAbility ability = TensuraStorages.getAbilityFrom(player);

      for (ShubNiggurathWarehouse.SkillSlotSnapshot snapshot : slots) {
         ability.setAbilitySlot(snapshot.preset(), snapshot.slot(), snapshot.skill(), snapshot.mode());
      }

      syncSkillAndAbility(player, first, ability);
   }

   private static double configuredPercent(double percent) {
      return Math.max(0.0, Math.min(100.0, percent));
   }

   private static boolean canConsumePermanentEp(LivingEntity entity, double percent) {
      return percent <= 0.0 || EnergyHelper.getBaseMaxEP(entity) > 0.0;
   }

   private static void consumePermanentEp(LivingEntity entity, double percent) {
      if (!(percent <= 0.0)) {
         EnergyHelper.multiplyMaxEP(entity, 1.0 - percent / 100.0);
         IExistence existence = TensuraStorages.getExistenceFrom(entity);
         existence.setAura(Math.min(existence.getAura(), EnergyHelper.getMaxAura(entity)));
         existence.setMagicule(Math.min(existence.getMagicule(), EnergyHelper.getMaxMagicule(entity)));
         existence.markDirty();
         if (entity instanceof ServerPlayer player) {
            SkillAPI.getSkillsFrom(player).markDirty();
            ((StorageHolder)player).manasCore$sync(player);
         }
      }
   }

   private static boolean canConsumeCurrentMagicule(LivingEntity entity, double percent) {
      if (percent <= 0.0) {
         return true;
      }

      IExistence existence = TensuraStorages.getExistenceFrom(entity);
      return existence.getMagicule() > 0.0;
   }

   private static void consumeCurrentMagicule(LivingEntity entity, double percent) {
      if (!(percent <= 0.0)) {
         IExistence existence = TensuraStorages.getExistenceFrom(entity);
         double current = Math.max(0.0, existence.getMagicule());
         double cost = CielPassiveHandler.optimizeMagiculeCost(entity, current * percent / 100.0);
         existence.setMagicule(Math.max(0.0, current - cost));
         existence.markDirty();
         if (entity instanceof ServerPlayer player) {
            ((StorageHolder)player).manasCore$sync(player);
         }
      }
   }

   public static boolean isEligibleGiftTarget(ServerPlayer owner, LivingEntity target) {
      return target != null && target != owner && target.isAlive()
         ? SubordinateHelper.isSubordinate(owner, target) || target instanceof Player && owner.isAlliedTo(target)
         : false;
   }

   private static boolean isEligibleUltimateGiftTarget(ServerPlayer owner, LivingEntity target) {
      return target != null && target != owner && target.isAlive() && !(target instanceof Player) && SubordinateHelper.isSubordinate(owner, target);
   }

   private static boolean isReplicableSkill(ManasSkill skill) {
      return isWarehouseEligibleSkill(skill);
   }

   private static boolean isGiftableSkill(ManasSkillInstance shub, LivingEntity owner, ManasSkill skill) {
      return isReplicableSkill(skill) || canGiftUltimateSkills(shub, owner) && isGiftableUltimateSkill(skill);
   }

   private static boolean canGiftUltimateSkills(ManasSkillInstance shub, LivingEntity owner) {
      return ShubNiggurathConfig.get().ShubNiggurath.enableMasteredUltimateSkillGifting && shub.isMastered(owner);
   }

   private static boolean isArchivableSkill(ManasSkill skill) {
      return isWarehouseEligibleSkill(skill) || isGiftableUltimateSkill(skill);
   }

   private static boolean isGiftableUltimateSkill(ManasSkill skill) {
      return isUltimateSkill(skill)
         && skill != FoxAblazeUltimateSkills.SHUB_NIGGURATH.get()
         && skill != FoxAblazeUltimateSkills.CIEL.get()
         && skill != FoxAblazeUltimateSkills.FORTUNA.get()
         && FoxAblazeUltimateSkills.yogSothothHolder().map(holder -> !holder.getId().equals(skill.getRegistryName())).orElse(true);
   }

   private static boolean isUltimateSkill(ManasSkill skill) {
      return skill instanceof Skill typed && typed.getType() == SkillType.ULTIMATE;
   }

   private static long countUltimateSkills(LivingEntity target) {
      return SkillAPI.getSkillsFrom(target)
         .getLearnedSkills()
         .stream()
         .<ManasSkill>map(ManasSkillInstance::getSkill)
         .filter(ShubNiggurathWarehouse::isUltimateSkill)
         .count();
   }

   private static boolean isWarehouseEligibleSkill(ManasSkill skill) {
      if (skill instanceof Skill typed && skill != FoxAblazeUltimateSkills.SHUB_NIGGURATH.get()) {
         return switch (typed.getType()) {
            case INTRINSIC, COMMON, EXTRA -> true;
            case RESISTANCE, UNIQUE, ULTIMATE -> false;
            default -> throw new MatchException(null, null);
         };
      } else {
         return false;
      }
   }

   private static ManasSkill lookup(ResourceLocation id) {
      return id == null ? null : (ManasSkill)SkillAPI.getSkillRegistry().get(id);
   }

   private static Component displayName(ManasSkill skill) {
      return skill == null ? Component.translatable("tensura.skill.empty") : skill.getChatDisplayName(false);
   }

   private static void fail(ServerPlayer player, String key) {
      player.displayClientMessage(Component.translatable(key).withStyle(ChatFormatting.RED), true);
   }

   private static void fail(ServerPlayer player, String key, Object... args) {
      player.displayClientMessage(Component.translatable(key, args).withStyle(ChatFormatting.RED), true);
   }

   private static void playSuccess(ServerPlayer player) {
      player.level()
         .playSound(null, player.getX(), player.getY(), player.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_CAST.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
   }

   private record ActiveSlot(IAbility ability, int preset, int slot, AbilitySlot abilitySlot) {
   }

   public record CreationRecipe(ResourceLocation first, ResourceLocation second, ResourceLocation result) {
   }

   private record SkillSlotSnapshot(int preset, int slot, ManasSkill skill, int mode) {
   }
}
