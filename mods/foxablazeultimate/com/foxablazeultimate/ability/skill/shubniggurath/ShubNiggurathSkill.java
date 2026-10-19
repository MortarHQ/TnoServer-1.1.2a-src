package com.foxablazeultimate.ability.skill.shubniggurath;

import com.foxablazeultimate.config.ShubNiggurathConfig;
import com.foxablazeultimate.world.FoxAblazeGameRules;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.unique.ITrulyUnique;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class ShubNiggurathSkill extends Skill {
   private static final String LAST_ARCHIVE_SCAN = "ShubLastArchiveScan";
   public static final int MODE_WAREHOUSE = 0;
   public static final int MODE_GIFT = 1;

   public ShubNiggurathSkill() {
      super(SkillType.ULTIMATE);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "textures/skill/shub_niggurath.png");
   }

   public double getDefaultAcquiringMagiculeCost() {
      return Double.POSITIVE_INFINITY;
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      return false;
   }

   public int getModes(ManasSkillInstance instance) {
      return ShubNiggurathWarehouse.hasPendingGift(instance) ? 2 : 1;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (!ShubNiggurathWarehouse.hasPendingGift(instance)) {
         return -1;
      } else {
         return mode == 0 ? 1 : 0;
      }
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return mode == 1 ? "shub_niggurath.gift" : "shub_niggurath.warehouse";
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return !entity.level().isClientSide();
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof ServerPlayer player) {
         this.claimUniqueOwnership(player);
         ShubNiggurathWarehouse.migrateLegacyState(player, instance);
      }

      int interval = Math.max(1, ShubNiggurathConfig.get().ShubNiggurath.archiveScanIntervalTicks);
      long now = entity.level().getGameTime();
      long lastScan = instance.getOrCreateTag().getLong("ShubLastArchiveScan");
      if (lastScan <= 0L || now - lastScan >= interval) {
         ShubNiggurathWarehouse.archiveMasteredSkills(instance, entity);
         instance.getOrCreateTag().putLong("ShubLastArchiveScan", now);
         instance.markDirty();
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity instanceof ServerPlayer player) {
         ShubNiggurathWarehouse.migrateLegacyState(player, instance);
         boolean pendingGift = ShubNiggurathWarehouse.hasPendingGift(instance);
         if (pendingGift && player.isShiftKeyDown()) {
            ShubNiggurathWarehouse.cancelPendingGift(player, instance, true);
            player.displayClientMessage(Component.translatable("foxablazeultimate.skill.shub_niggurath.gift.cancelled").withStyle(ChatFormatting.YELLOW), true);
            ShubNiggurathWarehouse.openWarehouse(player, instance, keyNumber);
         } else if (mode == 1 && pendingGift) {
            ShubNiggurathWarehouse.executePendingGift(player, instance, keyNumber);
         } else {
            ShubNiggurathWarehouse.openWarehouse(player, instance, keyNumber);
         }
      }
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onLearnSkill(instance, entity);
      if (!entity.level().isClientSide()) {
         if (entity instanceof ServerPlayer player) {
            this.claimUniqueOwnership(player);
            ShubNiggurathWarehouse.migrateLegacyState(player, instance);
         }

         ShubNiggurathWarehouse.archiveMasteredSkills(instance, entity);
      }
   }

   public boolean onDeath(ManasSkillInstance instance, LivingEntity owner, DamageSource source) {
      if (owner instanceof ServerPlayer player) {
         ShubNiggurathWarehouse.cancelPendingGift(player, instance, true);
      }

      return true;
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof ServerPlayer player) {
         ShubNiggurathWarehouse.cancelPendingGift(player, instance, true);
         this.releaseUniqueOwnership(player);
      }

      super.onForgetSkill(instance, entity);
   }

   private void claimUniqueOwnership(ServerPlayer player) {
      if (isTrulyUniqueEnabled(player)) {
         ResourceLocation skillId = this.getRegistryName();
         ServerLevel overworld = overworldOf(player);
         if (skillId != null && overworld != null) {
            ITrulyUnique unique = TensuraStorages.getUniqueStorageFrom(overworld);
            UUID owner = unique.getOwner(skillId);
            if (owner == null) {
               unique.addSkill(skillId, player.getUUID());
               unique.markDirty();
            }
         }
      }
   }

   private void releaseUniqueOwnership(ServerPlayer player) {
      ResourceLocation skillId = this.getRegistryName();
      ServerLevel overworld = overworldOf(player);
      if (skillId != null && overworld != null) {
         ITrulyUnique unique = TensuraStorages.getUniqueStorageFrom(overworld);
         if (player.getUUID().equals(unique.getOwner(skillId))) {
            unique.removeSkill(skillId);
            unique.markDirty();
         }
      }
   }

   private static boolean isTrulyUniqueEnabled(ServerPlayer player) {
      return player.level().getGameRules().getBoolean(FoxAblazeGameRules.SHUB_NIGGURATH_TRULY_UNIQUE);
   }

   private static ServerLevel overworldOf(ServerPlayer player) {
      MinecraftServer server = player.getServer();
      return server == null ? null : server.overworld();
   }
}
