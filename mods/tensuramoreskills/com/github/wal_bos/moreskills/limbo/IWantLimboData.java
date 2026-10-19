package com.github.wal_bos.moreskills.limbo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public final class IWantLimboData {
   private static final String ROOT = "TensuraMoreSkillsIWantLimbo";
   private static final String USED_KILLER_KEYS = "TensuraMoreSkillsIWantUsedKillerKeys";

   private IWantLimboData() {
   }

   public static CompoundTag root(ServerPlayer player) {
      CompoundTag persistent = player.getPersistentData();
      if (!persistent.contains("TensuraMoreSkillsIWantLimbo")) {
         persistent.put("TensuraMoreSkillsIWantLimbo", new CompoundTag());
      }

      return persistent.getCompound("TensuraMoreSkillsIWantLimbo");
   }

   public static boolean isActive(ServerPlayer player) {
      return root(player).getBoolean("Active");
   }

   public static void set(
      ServerPlayer player,
      UUID killer,
      List<IWantLimboData.StoredCard> cards,
      List<IWantLimboData.StoredPoolCard> pool,
      int rerollsLeft,
      Vec3 originalPosition,
      ResourceKey<Level> originalDimension,
      float originalYaw,
      float originalPitch
   ) {
      CompoundTag root = root(player);
      root.putBoolean("Active", true);
      root.putUUID("Killer", killer);
      root.putString("OriginalDimension", originalDimension.location().toString());
      root.putDouble("OriginalX", originalPosition.x);
      root.putDouble("OriginalY", originalPosition.y);
      root.putDouble("OriginalZ", originalPosition.z);
      root.putFloat("OriginalYaw", originalYaw);
      root.putFloat("OriginalPitch", originalPitch);
      root.putInt("RerollsLeft", Math.max(0, rerollsLeft));
      root.remove("PendingSkill");
      root.remove("PendingTicks");
      root.remove("PendingSuccess");
      putCards(root, cards);
      putPool(root, pool);
   }

   public static void setLimboPosition(ServerPlayer player, ResourceKey<Level> dimension, BlockPos center) {
      CompoundTag root = root(player);
      root.putString("LimboDimension", dimension.location().toString());
      root.putLong("LimboCenter", center.asLong());
   }

   public static ResourceKey<Level> limboDimension(ServerPlayer player) {
      String value = root(player).getString("LimboDimension");
      ResourceLocation id = ResourceLocation.tryParse(value);
      return id == null ? player.level().dimension() : ResourceKey.create(Registries.DIMENSION, id);
   }

   public static BlockPos limboCenter(ServerPlayer player) {
      CompoundTag root = root(player);
      return !root.contains("LimboCenter") ? player.blockPosition() : BlockPos.of(root.getLong("LimboCenter"));
   }

   public static List<IWantLimboData.StoredCard> cards(ServerPlayer player) {
      List<IWantLimboData.StoredCard> cards = new ArrayList<>();
      ListTag list = root(player).getList("Cards", 10);

      for (int i = 0; i < list.size(); i++) {
         CompoundTag tag = list.getCompound(i);
         ResourceLocation skill = ResourceLocation.tryParse(tag.getString("Skill"));
         if (skill != null) {
            IWantLimboData.Rarity rarity;
            try {
               rarity = IWantLimboData.Rarity.valueOf(tag.getString("Rarity"));
            } catch (IllegalArgumentException ex) {
               rarity = IWantLimboData.Rarity.BRONZE;
            }

            cards.add(new IWantLimboData.StoredCard(tag.getInt("Id"), skill, rarity));
         }
      }

      return cards;
   }

   public static List<IWantLimboData.StoredPoolCard> pool(ServerPlayer player) {
      List<IWantLimboData.StoredPoolCard> cards = new ArrayList<>();
      ListTag list = root(player).getList("Pool", 10);

      for (int i = 0; i < list.size(); i++) {
         CompoundTag tag = list.getCompound(i);
         ResourceLocation skill = ResourceLocation.tryParse(tag.getString("Skill"));
         if (skill != null) {
            IWantLimboData.Rarity rarity;
            try {
               rarity = IWantLimboData.Rarity.valueOf(tag.getString("Rarity"));
            } catch (IllegalArgumentException ex) {
               rarity = IWantLimboData.Rarity.BRONZE;
            }

            cards.add(new IWantLimboData.StoredPoolCard(skill, rarity, tag.getInt("Weight")));
         }
      }

      return cards;
   }

   public static void updateCards(ServerPlayer player, List<IWantLimboData.StoredCard> cards) {
      putCards(root(player), cards);
   }

   private static void putCards(CompoundTag root, List<IWantLimboData.StoredCard> cards) {
      ListTag list = new ListTag();

      for (IWantLimboData.StoredCard card : cards) {
         CompoundTag tag = new CompoundTag();
         tag.putInt("Id", card.id());
         tag.putString("Skill", card.skill().toString());
         tag.putString("Rarity", card.rarity().name());
         list.add(tag);
      }

      root.put("Cards", list);
   }

   private static void putPool(CompoundTag root, List<IWantLimboData.StoredPoolCard> pool) {
      ListTag list = new ListTag();

      for (IWantLimboData.StoredPoolCard card : pool) {
         CompoundTag tag = new CompoundTag();
         tag.putString("Skill", card.skill().toString());
         tag.putString("Rarity", card.rarity().name());
         tag.putInt("Weight", card.weight());
         list.add(tag);
      }

      root.put("Pool", list);
   }

   public static int rerollsLeft(ServerPlayer player) {
      return Math.max(0, root(player).getInt("RerollsLeft"));
   }

   public static void consumeReroll(ServerPlayer player) {
      CompoundTag root = root(player);
      root.putInt("RerollsLeft", Math.max(0, root.getInt("RerollsLeft") - 1));
   }

   public static boolean hasUsedKiller(ServerPlayer player, Entity killer) {
      String key = killerKey(killer);
      ListTag list = player.getPersistentData().getList("TensuraMoreSkillsIWantUsedKillerKeys", 8);

      for (int i = 0; i < list.size(); i++) {
         if (key.equals(list.getString(i))) {
            return true;
         }
      }

      return false;
   }

   public static void markKillerUsed(ServerPlayer player, Entity killer) {
      String key = killerKey(killer);
      ListTag list = player.getPersistentData().getList("TensuraMoreSkillsIWantUsedKillerKeys", 8);

      for (int i = 0; i < list.size(); i++) {
         if (key.equals(list.getString(i))) {
            return;
         }
      }

      list.add(StringTag.valueOf(key));
      player.getPersistentData().put("TensuraMoreSkillsIWantUsedKillerKeys", list);
   }

   public static void copyPermanentData(ServerPlayer oldPlayer, ServerPlayer newPlayer) {
      CompoundTag oldData = oldPlayer.getPersistentData();
      CompoundTag newData = newPlayer.getPersistentData();
      if (oldData.contains("TensuraMoreSkillsIWantUsedKillerKeys")) {
         newData.put("TensuraMoreSkillsIWantUsedKillerKeys", oldData.getList("TensuraMoreSkillsIWantUsedKillerKeys", 8).copy());
      }
   }

   private static String killerKey(Entity killer) {
      if (killer instanceof ServerPlayer) {
         return "player:" + killer.getUUID();
      }

      ResourceLocation id = BuiltInRegistries.ENTITY_TYPE.getKey(killer.getType());
      return "mob:" + (id == null ? killer.getType().toString() : id.toString());
   }

   public static void setPending(ServerPlayer player, ResourceLocation skill, int ticks, boolean success) {
      CompoundTag root = root(player);
      root.putString("PendingSkill", skill.toString());
      root.putInt("PendingTicks", Math.max(1, ticks));
      root.putBoolean("PendingSuccess", success);
   }

   public static boolean hasPending(ServerPlayer player) {
      return root(player).contains("PendingSkill");
   }

   public static IWantLimboData.PendingResult tickPending(ServerPlayer player) {
      CompoundTag root = root(player);
      if (!root.contains("PendingSkill")) {
         return IWantLimboData.PendingResult.none();
      }

      int ticks = root.getInt("PendingTicks") - 1;
      root.putInt("PendingTicks", ticks);
      if (ticks > 0) {
         return IWantLimboData.PendingResult.waiting();
      }

      boolean success = root.getBoolean("PendingSuccess");
      return new IWantLimboData.PendingResult(true, success);
   }

   public static void clear(ServerPlayer player) {
      player.getPersistentData().remove("TensuraMoreSkillsIWantLimbo");
   }

   public record PendingResult(boolean ready, boolean success) {
      public static IWantLimboData.PendingResult none() {
         return new IWantLimboData.PendingResult(false, false);
      }

      public static IWantLimboData.PendingResult waiting() {
         return new IWantLimboData.PendingResult(false, false);
      }
   }

   public enum Rarity {
      BRONZE(-3900614),
      SILVER(-3483672),
      GOLD(-10646);

      private final int color;

      Rarity(int color) {
         this.color = color;
      }

      public int color() {
         return this.color;
      }
   }

   public record StoredCard(int id, ResourceLocation skill, IWantLimboData.Rarity rarity) {
   }

   public record StoredPoolCard(ResourceLocation skill, IWantLimboData.Rarity rarity, int weight) {
   }
}
