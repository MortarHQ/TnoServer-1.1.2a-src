package com.github.wal_bos.moreskills.handler;

import com.github.wal_bos.moreskills.registry.MoreSkillsGameRules;
import com.github.wal_bos.moreskills.registry.skill.AllSkills;
import com.github.wal_bos.moreskills.registry.skill.ShadeSkills;
import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.SkillEvents;
import io.github.manasmods.manascore.skill.api.Skills;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public final class UniqueOwnerSkillHandler {
   private static final Set<ResourceLocation> EXCLUSIVE_SKILLS = new LinkedHashSet<>();
   private static boolean initialized = false;

   private UniqueOwnerSkillHandler() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         registerDefaultSkills();
         SkillEvents.UNLOCK_SKILL.register(UniqueOwnerSkillHandler::onUnlockSkill);
         SkillEvents.REMOVE_SKILL.register(UniqueOwnerSkillHandler::onRemoveSkill);
      }
   }

   public static void addSkill(ResourceLocation id) {
      if (id != null) {
         EXCLUSIVE_SKILLS.add(id);
      }
   }

   public static void addSkill(ManasSkill skill) {
      if (skill != null) {
         EXCLUSIVE_SKILLS.add(skill.getRegistryName());
      }
   }

   public static boolean isExclusive(ResourceLocation id) {
      return id != null && EXCLUSIVE_SKILLS.contains(id);
   }

   public static boolean isExclusive(ManasSkillInstance instance) {
      return instance != null && isExclusive(instance.getSkillId());
   }

   public static List<ResourceLocation> getExclusiveSkills() {
      return new ArrayList<>(EXCLUSIVE_SKILLS);
   }

   private static void registerDefaultSkills() {
      addSkill(ShadeSkills.RONOVA.getId());
      addSkill(ShadeSkills.NABERIUS.getId());
      addSkill(ShadeSkills.ISTAROTH.getId());
      addSkill(ShadeSkills.ASMODAY.getId());
      addSkill(AllSkills.CAEDROS_GOD_OF_CONQUEST.getId());
   }

   private static EventResult onUnlockSkill(ManasSkillInstance instance, LivingEntity entity, Changeable<MutableComponent> unlockMessage) {
      if (entity == null || entity.level().isClientSide()) {
         return EventResult.pass();
      }

      if (!isUniqueOwnerDataEnabled(entity)) {
         return EventResult.pass();
      }

      if (!isExclusive(instance)) {
         syncEntity(entity);
         return EventResult.pass();
      }

      if (instance.getMastery() < 0.0) {
         return EventResult.pass();
      }

      if (entity instanceof ServerPlayer player) {
         if (player.level() instanceof ServerLevel serverLevel) {
            UniqueOwnerSkillData var11 = getData(serverLevel);
            MinecraftServer server = serverLevel.getServer();
            refreshOnlineOwners(var11, server);
            UUID owner = player.getUUID();
            ResourceLocation skillId = instance.getSkillId();
            UUID savedOwner = var11.getOwner(skillId);
            if (savedOwner != null && !savedOwner.equals(owner)) {
               ServerPlayer savedOwnerPlayer = server.getPlayerList().getPlayer(savedOwner);
               if (savedOwnerPlayer != null && !hasSkill(savedOwnerPlayer, skillId)) {
                  var11.removeSkillOwner(skillId);
                  savedOwner = null;
               }
            }

            if (savedOwner != null && !savedOwner.equals(owner)) {
               forgetExclusive(player, skillId);
               deny(player, Component.literal("Another player already owns ").append(instance.getChatDisplayName(true)).append(Component.literal(".")));
               return EventResult.interruptFalse();
            } else {
               forceThisAsOnlyExclusive(player, var11, skillId);
               return EventResult.pass();
            }
         } else {
            return EventResult.pass();
         }
      } else {
         return EventResult.interruptFalse();
      }
   }

   private static EventResult onRemoveSkill(ManasSkillInstance instance, LivingEntity entity, Changeable<MutableComponent> message) {
      if (entity == null || entity.level().isClientSide()) {
         return EventResult.pass();
      } else if (!isUniqueOwnerDataEnabled(entity)) {
         return EventResult.pass();
      } else if (!isExclusive(instance)) {
         syncEntity(entity);
         return EventResult.pass();
      } else {
         releaseSkill(instance.getSkillId(), entity);
         return EventResult.pass();
      }
   }

   public static void learnExclusiveSkill(ManasSkillInstance instance, LivingEntity entity) {
      if (entity != null && !entity.level().isClientSide()) {
         if (isUniqueOwnerDataEnabled(entity)) {
            if (!isExclusive(instance)) {
               syncEntity(entity);
            } else if (entity instanceof ServerPlayer player) {
               if (player.level() instanceof ServerLevel serverLevel) {
                  UniqueOwnerSkillData var10 = getData(serverLevel);
                  MinecraftServer server = serverLevel.getServer();
                  refreshOnlineOwners(var10, server);
                  UUID owner = player.getUUID();
                  ResourceLocation skillId = instance.getSkillId();
                  UUID savedOwner = var10.getOwner(skillId);
                  if (savedOwner != null && !savedOwner.equals(owner)) {
                     ServerPlayer savedOwnerPlayer = server.getPlayerList().getPlayer(savedOwner);
                     if (savedOwnerPlayer != null && !hasSkill(savedOwnerPlayer, skillId)) {
                        var10.removeSkillOwner(skillId);
                        savedOwner = null;
                     }
                  }

                  if (savedOwner != null && !savedOwner.equals(owner)) {
                     forgetExclusive(player, skillId);
                     deny(player, Component.literal("Another player already owns this exclusive authority."));
                  } else {
                     forceThisAsOnlyExclusive(player, var10, skillId);
                  }
               }
            }
         }
      }
   }

   public static boolean canClaimExclusiveSkill(ResourceLocation skillId, LivingEntity entity) {
      if (entity == null || entity.level().isClientSide()) {
         return false;
      }

      if (!isExclusive(skillId)) {
         return false;
      }

      if (SkillAPI.getSkillRegistry().get(skillId) == null) {
         return false;
      }

      if (entity instanceof ServerPlayer player) {
         if (player.level() instanceof ServerLevel serverLevel) {
            if (hasSkill(player, skillId)) {
               return false;
            }

            if (!isUniqueOwnerDataEnabled(player)) {
               return true;
            }

            UniqueOwnerSkillData data = getData(serverLevel);
            MinecraftServer server = serverLevel.getServer();
            refreshOnlineOwners(data, server);
            UUID owner = player.getUUID();
            UUID savedOwner = data.getOwner(skillId);
            if (savedOwner != null && !savedOwner.equals(owner)) {
               ServerPlayer savedOwnerPlayer = server.getPlayerList().getPlayer(savedOwner);
               if (savedOwnerPlayer != null && !hasSkill(savedOwnerPlayer, skillId)) {
                  data.removeSkillOwner(skillId);
                  return true;
               } else {
                  return false;
               }
            } else {
               return true;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public static List<ResourceLocation> getClaimableExclusiveSkills(LivingEntity entity) {
      if (entity != null && !entity.level().isClientSide()) {
         if (!(entity instanceof ServerPlayer player)) {
            return List.of();
         } else if (!(player.level() instanceof ServerLevel serverLevel)) {
            return List.of();
         } else if (!isUniqueOwnerDataEnabled(player)) {
            List<ResourceLocation> result = new ArrayList<>();

            for (ResourceLocation skillId : EXCLUSIVE_SKILLS) {
               if (SkillAPI.getSkillRegistry().get(skillId) != null && !hasSkill(player, skillId)) {
                  result.add(skillId);
               }
            }

            return result;
         } else {
            UniqueOwnerSkillData data = getData(serverLevel);
            MinecraftServer server = serverLevel.getServer();
            refreshOnlineOwners(data, server);
            UUID owner = player.getUUID();
            List<ResourceLocation> result = new ArrayList<>();

            for (ResourceLocation skillId : EXCLUSIVE_SKILLS) {
               if (SkillAPI.getSkillRegistry().get(skillId) == null) {
                  data.removeSkillOwner(skillId);
               } else if (!hasSkill(player, skillId)) {
                  UUID savedOwner = data.getOwner(skillId);
                  if (savedOwner != null && !savedOwner.equals(owner)) {
                     ServerPlayer savedOwnerPlayer = server.getPlayerList().getPlayer(savedOwner);
                     if (savedOwnerPlayer != null && !hasSkill(savedOwnerPlayer, skillId)) {
                        data.removeSkillOwner(skillId);
                        result.add(skillId);
                     }
                  } else {
                     result.add(skillId);
                  }
               }
            }

            return result;
         }
      } else {
         return List.of();
      }
   }

   public static void registerOwner(ResourceLocation skillId, LivingEntity entity) {
      if (entity != null && !entity.level().isClientSide()) {
         if (isUniqueOwnerDataEnabled(entity)) {
            if (!isExclusive(skillId)) {
               syncEntity(entity);
            } else if (entity instanceof ServerPlayer player) {
               if (player.level() instanceof ServerLevel serverLevel) {
                  UniqueOwnerSkillData var9 = getData(serverLevel);
                  MinecraftServer server = serverLevel.getServer();
                  refreshOnlineOwners(var9, server);
                  UUID owner = player.getUUID();
                  UUID savedOwner = var9.getOwner(skillId);
                  if (savedOwner != null && !savedOwner.equals(owner)) {
                     ServerPlayer savedOwnerPlayer = server.getPlayerList().getPlayer(savedOwner);
                     if (savedOwnerPlayer == null || hasSkill(savedOwnerPlayer, skillId)) {
                        return;
                     }

                     var9.removeSkillOwner(skillId);
                  }

                  forceThisAsOnlyExclusive(player, var9, skillId);
               }
            }
         }
      }
   }

   public static void releaseSkill(ManasSkillInstance instance, LivingEntity entity) {
      if (entity != null && !entity.level().isClientSide()) {
         if (isUniqueOwnerDataEnabled(entity)) {
            if (!isExclusive(instance)) {
               syncEntity(entity);
            } else {
               releaseSkill(instance.getSkillId(), entity);
            }
         }
      }
   }

   public static void releaseSkill(ResourceLocation skillId, LivingEntity entity) {
      if (entity != null && !entity.level().isClientSide()) {
         if (isUniqueOwnerDataEnabled(entity)) {
            if (!isExclusive(skillId)) {
               syncEntity(entity);
            } else if (entity.level() instanceof ServerLevel serverLevel) {
               UniqueOwnerSkillData var6 = getData(serverLevel);
               UUID owner = entity.getUUID();
               UUID savedOwner = var6.getOwner(skillId);
               if (owner.equals(savedOwner)) {
                  var6.removeOwnerSkill(owner, skillId);
               }
            }
         }
      }
   }

   public static void releaseOwner(LivingEntity entity) {
      if (entity != null && !entity.level().isClientSide()) {
         if (isUniqueOwnerDataEnabled(entity)) {
            if (entity.level() instanceof ServerLevel serverLevel) {
               UniqueOwnerSkillData data = getData(serverLevel);
               data.removeOwner(entity.getUUID());
            }
         }
      }
   }

   public static void syncEntity(LivingEntity entity) {
      if (entity != null && !entity.level().isClientSide()) {
         if (isUniqueOwnerDataEnabled(entity)) {
            if (entity instanceof ServerPlayer player) {
               if (player.level() instanceof ServerLevel serverLevel) {
                  UniqueOwnerSkillData var9 = getData(serverLevel);
                  UUID owner = player.getUUID();
                  List actualOwned = getOwnedExclusiveSkillIds(player);
                  ResourceLocation savedOwned = var9.getOwnedSkill(owner);
                  if (actualOwned.isEmpty()) {
                     if (savedOwned != null) {
                        var9.removeOwner(owner);
                     }
                  } else if (actualOwned.size() == 1) {
                     ResourceLocation onlySkill = (ResourceLocation)actualOwned.get(0);
                     if (!var9.ownerHasSkill(owner, onlySkill)) {
                        UUID oldOwner = var9.getOwner(onlySkill);
                        if (oldOwner == null || oldOwner.equals(owner)) {
                           var9.forceOwnerSkill(owner, onlySkill);
                        }
                     }
                  } else {
                     ResourceLocation keptSkill = (ResourceLocation)actualOwned.get(actualOwned.size() - 1);
                     forceThisAsOnlyExclusive(player, var9, keptSkill);
                  }
               }
            }
         }
      }
   }

   public static void refreshOnlineOwners(UniqueOwnerSkillData data, MinecraftServer server) {
      if (data != null && server != null) {
         if (isUniqueOwnerDataEnabled(server)) {
            for (ResourceLocation skillId : List.copyOf(data.getSkillIds())) {
               if (isExclusive(skillId) && SkillAPI.getSkillRegistry().get(skillId) != null) {
                  UUID owner = data.getOwner(skillId);
                  if (owner == null) {
                     data.removeSkillOwner(skillId);
                  } else {
                     ServerPlayer player = server.getPlayerList().getPlayer(owner);
                     if (player != null && !hasSkill(player, skillId)) {
                        data.removeSkillOwner(skillId);
                     }
                  }
               } else {
                  data.removeSkillOwner(skillId);
               }
            }

            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
               syncEntity(player);
            }
         }
      }
   }

   public static void resetAll(ServerLevel level) {
      getData(level).clear();
   }

   private static void forceThisAsOnlyExclusive(ServerPlayer player, UniqueOwnerSkillData data, ResourceLocation keptSkill) {
      if (player != null && data != null && keptSkill != null) {
         if (isUniqueOwnerDataEnabled(player)) {
            UUID owner = player.getUUID();
            UUID oldOwner = data.getOwner(keptSkill);
            if (oldOwner != null && !oldOwner.equals(owner)) {
               MinecraftServer server = player.getServer();
               ServerPlayer oldOwnerPlayer = server == null ? null : server.getPlayerList().getPlayer(oldOwner);
               if (oldOwnerPlayer != null && hasSkill(oldOwnerPlayer, keptSkill)) {
                  return;
               }

               data.removeSkillOwner(keptSkill);
            }

            ResourceLocation oldSavedSkill = data.getOwnedSkill(owner);
            if (oldSavedSkill != null && !oldSavedSkill.equals(keptSkill)) {
               data.removeOwnerSkill(owner, oldSavedSkill);
               forgetExclusive(player, oldSavedSkill);
            }

            for (ResourceLocation skillId : List.copyOf(getOwnedExclusiveSkillIds(player))) {
               if (!skillId.equals(keptSkill)) {
                  forgetExclusive(player, skillId);
                  data.removeOwnerSkill(owner, skillId);
                  data.removeSkillOwner(skillId);
               }
            }

            data.forceOwnerSkill(owner, keptSkill);
            SkillAPI.getSkillsFrom(player).markDirty();
         }
      }
   }

   private static void forgetExclusive(ServerPlayer player, ResourceLocation skillId) {
      if (player != null && skillId != null) {
         ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(skillId);
         if (skill != null) {
            Skills skills = SkillAPI.getSkillsFrom(player);
            if (skills.getSkill(skillId).isPresent()) {
               skills.forgetSkill(skill);
               skills.markDirty();
            }
         }
      }
   }

   private static boolean hasSkill(LivingEntity entity, ResourceLocation skillId) {
      if (entity != null && skillId != null) {
         Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(entity).getSkill(skillId);
         return optional.isPresent() && optional.get().getMastery() >= 0.0;
      } else {
         return false;
      }
   }

   private static List<ResourceLocation> getOwnedExclusiveSkillIds(LivingEntity entity) {
      if (entity == null) {
         return List.of();
      }

      Skills skills = SkillAPI.getSkillsFrom(entity);
      List<ResourceLocation> result = new ArrayList<>();

      for (ManasSkillInstance learned : List.copyOf(skills.getLearnedSkills())) {
         if (learned != null && learned.getSkill() != null && !(learned.getMastery() < 0.0) && isExclusive(learned)) {
            ResourceLocation skillId = learned.getSkillId();
            if (!result.contains(skillId)) {
               result.add(skillId);
            }
         }
      }

      return result;
   }

   public static UniqueOwnerSkillData getData(ServerLevel level) {
      return UniqueOwnerSkillData.get(level.getServer());
   }

   private static boolean isUniqueOwnerDataEnabled(LivingEntity entity) {
      return entity != null && entity.level().getGameRules().getBoolean(MoreSkillsGameRules.UNIQUE_OWNER_DATA);
   }

   private static boolean isUniqueOwnerDataEnabled(MinecraftServer server) {
      if (server == null) {
         return false;
      }

      ServerLevel overworld = server.getLevel(Level.OVERWORLD);
      return overworld != null && overworld.getGameRules().getBoolean(MoreSkillsGameRules.UNIQUE_OWNER_DATA);
   }

   private static void deny(LivingEntity entity, Component message) {
      Component red = message.copy().withStyle(ChatFormatting.RED);
      entity.sendSystemMessage(red);
      if (entity instanceof ServerPlayer player) {
         player.displayClientMessage(red, true);
      }
   }
}
