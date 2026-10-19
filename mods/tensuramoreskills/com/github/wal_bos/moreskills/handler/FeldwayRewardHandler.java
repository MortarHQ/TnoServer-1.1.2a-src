package com.github.wal_bos.moreskills.handler;

import com.github.wal_bos.moreskills.net.msg.OpenFeldwayRewardScreenPayload;
import com.github.wal_bos.moreskills.registry.MoreSkillsGameRules;
import com.github.wal_bos.moreskills.world.entity.FeldwayAwakenedEntity;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.data.TensuraBiomeTags;
import io.github.manasmods.tensura.item.misc.ResetScrollItem;
import io.github.manasmods.tensura.item.misc.ResetScrollItem.ResetType;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.player.ITensuraPlayer;
import io.github.manasmods.tensura.util.SubordinateHelper;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent.Stop;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber(modid = "tensuramoreskills")
public final class FeldwayRewardHandler {
   private static final ResourceLocation CANCEL_REWARD_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "cancel_feldway_reward");
   private static final Map<UUID, Integer> LAST_RESET_COUNTER = new HashMap<>();
   private static final Map<UUID, FeldwayRewardHandler.PendingResetScroll> PENDING_RESET_SCROLLS = new HashMap<>();

   private FeldwayRewardHandler() {
   }

   public static void tryOpenReward(FeldwayAwakenedEntity boss, DamageSource source) {
      if (boss != null && source != null) {
         if (boss.level() instanceof ServerLevel serverLevel) {
            ServerPlayer player = getRewardPlayer(source);
            if (player != null) {
               if (!player.isCreative() && !player.isSpectator()) {
                  FeldwayRewardData data = getData(serverLevel);
                  UUID uuid = player.getUUID();
                  if (isFeldwayRewardOneTime(serverLevel) && data.hasRewarded(uuid)) {
                     player.sendSystemMessage(Component.literal("You have already claimed Feldway's reward.").withStyle(ChatFormatting.RED));
                  } else {
                     presentReward(player, data);
                  }
               }
            }
         }
      }
   }

   public static void claim(ServerPlayer player, ResourceLocation selectedSkill) {
      if (player != null && selectedSkill != null) {
         if (CANCEL_REWARD_ID.equals(selectedSkill)) {
            cancelPendingReward(player);
         } else if (player.level() instanceof ServerLevel serverLevel) {
            FeldwayRewardData var7 = getData(serverLevel);
            UUID uuid = player.getUUID();
            if (isFeldwayRewardOneTime(serverLevel) && var7.hasRewarded(uuid)) {
               var7.clearPending(uuid);
               player.closeContainer();
               player.sendSystemMessage(Component.literal("You have already claimed Feldway's reward.").withStyle(ChatFormatting.RED));
            } else if (!var7.isPending(uuid)) {
               player.sendSystemMessage(Component.literal("You do not have a pending Feldway reward.").withStyle(ChatFormatting.RED));
            } else if (!UniqueOwnerSkillHandler.isExclusive(selectedSkill)) {
               refreshPendingReward(player, var7, "That authority is no longer a valid Feldway reward.");
            } else if (!UniqueOwnerSkillHandler.canClaimExclusiveSkill(selectedSkill, player)) {
               refreshPendingReward(player, var7, "That authority was taken or is already owned. Choose another.");
            } else {
               ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(selectedSkill);
               if (skill == null) {
                  refreshPendingReward(player, var7, "That authority no longer exists. Choose another.");
               } else {
                  boolean learned = SkillHelper.learnSkill(player, skill);
                  if (!learned) {
                     refreshPendingReward(player, var7, "That authority could not be learned. Choose another.");
                  } else {
                     UniqueOwnerSkillHandler.registerOwner(selectedSkill, player);
                     completeReward(player, var7);
                     syncPlayer(player);
                     player.closeContainer();
                     player.sendSystemMessage(
                        Component.literal("Feldway's authority has accepted you: ").append(skill.getChatDisplayName(true)).withStyle(ChatFormatting.GOLD)
                     );
                  }
               }
            }
         }
      }
   }

   private static void cancelPendingReward(ServerPlayer player) {
      if (player.level() instanceof ServerLevel serverLevel) {
         FeldwayRewardData var4 = getData(serverLevel);
         UUID uuid = player.getUUID();
         if (var4.isPending(uuid)) {
            var4.clearPending(uuid);
            player.sendSystemMessage(
               Component.literal("Feldway's pending reward was abandoned. Defeat Feldway again to receive a new reward.").withStyle(ChatFormatting.YELLOW)
            );
         }
      }
   }

   private static void presentReward(ServerPlayer player, FeldwayRewardData data) {
      List<ResourceLocation> choices = UniqueOwnerSkillHandler.getClaimableExclusiveSkills(player);
      if (choices.isEmpty()) {
         grantRandomUnique(player, data);
      } else {
         data.setPending(player.getUUID());
         NetworkManager.sendToPlayer(player, new OpenFeldwayRewardScreenPayload(choices));
      }
   }

   private static void refreshPendingReward(ServerPlayer player, FeldwayRewardData data, String message) {
      if (message != null && !message.isEmpty()) {
         player.sendSystemMessage(Component.literal(message).withStyle(ChatFormatting.RED));
      }

      presentReward(player, data);
   }

   private static void grantRandomUnique(ServerPlayer player, FeldwayRewardData data) {
      List<ManasSkill> pool = new ArrayList<>();

      for (ManasSkill manasSkill : SkillAPI.getSkillRegistry()) {
         if (isUniqueSkill(manasSkill)
            && !UniqueOwnerSkillHandler.isExclusive(manasSkill.getRegistryName())
            && SkillAPI.getSkillsFrom(player).getSkill(manasSkill).isEmpty()) {
            pool.add(manasSkill);
         }
      }

      while (!pool.isEmpty()) {
         ManasSkill skill = pool.remove(player.getRandom().nextInt(pool.size()));
         if (SkillHelper.learnSkill(player, skill)) {
            completeReward(player, data);
            syncPlayer(player);
            player.closeContainer();
            player.sendSystemMessage(
               Component.literal("No authorities were available. You received a random unique: ")
                  .append(skill.getChatDisplayName(true))
                  .withStyle(ChatFormatting.GOLD)
            );
            return;
         }
      }

      data.clearPending(player.getUUID());
      player.closeContainer();
      player.sendSystemMessage(
         Component.literal("No Feldway rewards are currently learnable for you. Your reward was not consumed.").withStyle(ChatFormatting.RED)
      );
   }

   private static void completeReward(ServerPlayer player, FeldwayRewardData data) {
      if (player != null && data != null) {
         if (player.level().getGameRules().getBoolean(MoreSkillsGameRules.FELDWAY_REWARD_ONE_TIME)) {
            data.markRewarded(player.getUUID());
         } else {
            data.clearPending(player.getUUID());
         }
      }
   }

   private static boolean isFeldwayRewardOneTime(ServerLevel level) {
      return level.getGameRules().getBoolean(MoreSkillsGameRules.FELDWAY_REWARD_ONE_TIME);
   }

   private static boolean isUniqueSkill(ManasSkill manasSkill) {
      return manasSkill instanceof Skill skill ? skill.getType() == SkillType.UNIQUE : false;
   }

   private static ServerPlayer getRewardPlayer(DamageSource source) {
      ServerPlayer player = resolveRewardPlayer(source.getEntity());
      return player != null ? player : resolveRewardPlayer(source.getDirectEntity());
   }

   private static ServerPlayer resolveRewardPlayer(Entity entity) {
      if (entity instanceof ServerPlayer player) {
         return player;
      } else {
         if (entity instanceof Projectile projectile) {
            Entity owner = projectile.getOwner();
            if (owner instanceof ServerPlayer player) {
               return player;
            }

            if (owner instanceof LivingEntity livingOwner && SubordinateHelper.getSubordinateOwner(livingOwner) instanceof ServerPlayer player) {
               return player;
            }
         }

         return entity instanceof LivingEntity living && SubordinateHelper.getSubordinateOwner(living) instanceof ServerPlayer player ? player : null;
      }
   }

   @SubscribeEvent
   public static void onResetScrollStopped(Stop event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         ItemStack stack = event.getItem();
         if (stack.getItem() instanceof ResetScrollItem resetScroll) {
            ResetType type = resetScroll.getResetType();
            if (type != ResetType.RESET_RACE) {
               if (type == ResetType.RESET_SKILL || type == ResetType.RESET_ALL) {
                  int useTicks = resetScroll.getUseDuration(stack, player) - event.getDuration();
                  if (useTicks >= 10) {
                     if (!player.isSpectator()) {
                        if (!SkillUtils.inSpiritualWorld(player.level().dimension())
                           && !player.level().getBiome(player.blockPosition()).is(TensuraBiomeTags.IS_UNSAFE_FOR_SPAWN)) {
                           PENDING_RESET_SCROLLS.put(
                              player.getUUID(),
                              new FeldwayRewardHandler.PendingResetScroll(stack, stack.getCount(), player.hasInfiniteMaterials(), stack.isEmpty())
                           );
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         UUID var9 = player.getUUID();
         boolean hasPendingScroll = PENDING_RESET_SCROLLS.containsKey(var9);
         if (hasPendingScroll || player.tickCount % 20 == 0) {
            boolean resetFromScroll = hasPendingScroll && consumeSuccessfulResetScroll(player);
            ITensuraPlayer playerData = TensuraStorages.getPlayerDataFrom(player);
            int currentCounter = Math.max(0, playerData.getResetCounter());
            Integer previousCounter = LAST_RESET_COUNTER.put(var9, currentCounter);
            boolean resetFromCounter = previousCounter != null && currentCounter > previousCounter;
            if (resetFromScroll || resetFromCounter) {
               resetTrackedPlayer(player);
               LAST_RESET_COUNTER.put(var9, Math.max(0, TensuraStorages.getPlayerDataFrom(player).getResetCounter()));
            }
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         LAST_RESET_COUNTER.put(player.getUUID(), Math.max(0, TensuraStorages.getPlayerDataFrom(player).getResetCounter()));
         UniqueOwnerSkillHandler.syncEntity(player);
         if (player.level() instanceof ServerLevel serverLevel) {
            FeldwayRewardData data = getData(serverLevel);
            if (data.isPending(player.getUUID()) && (!isFeldwayRewardOneTime(serverLevel) || !data.hasRewarded(player.getUUID()))) {
               presentReward(player, data);
            }
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerLoggedOut(PlayerLoggedOutEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         UUID var3 = player.getUUID();
         LAST_RESET_COUNTER.remove(var3);
         PENDING_RESET_SCROLLS.remove(var3);
      }
   }

   private static boolean consumeSuccessfulResetScroll(ServerPlayer player) {
      FeldwayRewardHandler.PendingResetScroll pending = PENDING_RESET_SCROLLS.remove(player.getUUID());
      if (pending == null) {
         return false;
      } else if (pending.infiniteMaterials()) {
         return true;
      } else if (player.getCooldowns().isOnCooldown(pending.stack().getItem())) {
         return true;
      } else {
         return pending.emptyAtStop() ? true : pending.stack().getCount() < pending.countAtStop();
      }
   }

   private static void resetTrackedPlayer(ServerPlayer player) {
      if (player.level() instanceof ServerLevel serverLevel) {
         getData(serverLevel).resetPlayer(player.getUUID());
         resetExclusiveOwnerSkills(player, serverLevel);
      }
   }

   private static void resetExclusiveOwnerSkills(ServerPlayer player, ServerLevel serverLevel) {
      Skills skills = SkillAPI.getSkillsFrom(player);
      boolean changed = false;

      for (ResourceLocation skillId : UniqueOwnerSkillHandler.getExclusiveSkills()) {
         Optional<ManasSkillInstance> optional = skills.getSkill(skillId);
         if (!optional.isEmpty()) {
            ManasSkillInstance instance = optional.get();
            if (!(instance.getMastery() < 0.0)) {
               skills.forgetSkill(instance.getSkill());
               changed = true;
            }
         }
      }

      UniqueOwnerSkillHandler.getData(serverLevel).removeOwner(player.getUUID());
      if (changed) {
         skills.markDirty();
         syncPlayer(player);
      }
   }

   public static FeldwayRewardData getData(ServerLevel level) {
      MinecraftServer server = level.getServer();
      ServerLevel overworld = server.getLevel(Level.OVERWORLD);
      return overworld == null
         ? (FeldwayRewardData)level.getDataStorage().computeIfAbsent(FeldwayRewardData.factory(), FeldwayRewardData.name())
         : (FeldwayRewardData)overworld.getDataStorage().computeIfAbsent(FeldwayRewardData.factory(), FeldwayRewardData.name());
   }

   public static void resetPlayer(ServerPlayer player) {
      if (player != null) {
         if (player.level() instanceof ServerLevel) {
            resetTrackedPlayer(player);
            player.sendSystemMessage(Component.literal("Your Feldway reward data was reset.").withStyle(ChatFormatting.YELLOW));
         }
      }
   }

   private static void syncPlayer(ServerPlayer player) {
      try {
         Method method = player.getClass().getMethod("manasCore$sync");
         method.invoke(player);
      } catch (ReflectiveOperationException var3) {
         try {
            Method method = player.getClass().getMethod("manasCore$sync", ServerPlayer.class);
            method.invoke(player, player);
         } catch (ReflectiveOperationException var2) {
         }
      }
   }

   public static void resetAll(ServerLevel level) {
      getData(level).resetAll();
      UniqueOwnerSkillHandler.resetAll(level);
   }

   private record PendingResetScroll(ItemStack stack, int countAtStop, boolean infiniteMaterials, boolean emptyAtStop) {
   }
}
