package com.tensura_guild.data;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import java.util.List;
import java.util.Locale;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record GuildLevelData(int level, int experience) {
   private static final List<String> RANKS = List.of("F", "E", "D", "C", "B", "A", "S");
   private static final int[] RANK_MAX_EXPERIENCE = new int[]{100, 300, 500, 700, 1000, 2000};
   public static final int MIN_LEVEL = 1;
   public static final int MAX_LEVEL = RANKS.size();
   public static final GuildLevelData DEFAULT = fromTotalExperience(0);
   public static final Codec<GuildLevelData> CODEC = Codec.INT
      .listOf(2, 2)
      .xmap(values -> new GuildLevelData((Integer)values.get(0), (Integer)values.get(1)), data -> List.of(data.level(), data.experience()));
   public static final StreamCodec<ByteBuf, GuildLevelData> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.VAR_INT, GuildLevelData::level, ByteBufCodecs.VAR_INT, GuildLevelData::experience, GuildLevelData::new
   );

   public GuildLevelData {
      level = Math.max(1, Math.min(MAX_LEVEL, level));
      experience = Math.max(0, experience);
   }

   public static GuildLevelData fromTotalExperience(int totalExperience) {
      int normalizedExperience = Math.max(0, totalExperience);
      return new GuildLevelData(levelForExperience(normalizedExperience), normalizedExperience);
   }

   public int totalExperience() {
      int normalizedExperience = Math.max(0, this.experience);
      int normalizedLevel = Math.max(1, Math.min(MAX_LEVEL, this.level));
      int derivedLevel = levelForExperience(normalizedExperience);
      if (normalizedLevel > derivedLevel) {
         long legacyTotalExperience = (normalizedLevel - 1) * 1000L + normalizedExperience;
         return (int)Math.min(2147483647L, legacyTotalExperience);
      } else {
         return normalizedExperience;
      }
   }

   public String rank() {
      return RANKS.get(this.levelIndex() - 1);
   }

   public static String normalizeRank(String rank) {
      String normalized = rank == null ? "" : rank.trim().toUpperCase(Locale.ROOT);
      return RANKS.contains(normalized) ? normalized : RANKS.get(0);
   }

   public static int levelIndexForRank(String rank) {
      String normalized = normalizeRank(rank);
      int index = RANKS.indexOf(normalized);
      return index < 0 ? 1 : index + 1;
   }

   public static String rankForLevel(int level) {
      int normalizedLevel = Math.max(1, Math.min(MAX_LEVEL, level));
      return RANKS.get(normalizedLevel - 1);
   }

   public static List<String> ranksUpToLevel(int level) {
      int normalizedLevel = Math.max(1, Math.min(MAX_LEVEL, level));
      return List.copyOf(RANKS.subList(0, normalizedLevel));
   }

   public int levelIndex() {
      return levelForExperience(this.totalExperience());
   }

   public boolean isMaxRank() {
      return this.levelIndex() >= MAX_LEVEL;
   }

   public int currentLevelExperience() {
      return this.isMaxRank() ? 1 : Math.max(0, this.totalExperience() - this.currentRankMinExperience());
   }

   public int experienceToNextLevel() {
      return this.isMaxRank() ? 1 : this.currentRankMaxExperience() - this.currentRankMinExperience();
   }

   public int displayMaxExperience() {
      return this.isMaxRank() ? Math.max(this.maxRankMinExperience(), this.totalExperience()) : this.currentRankMaxExperience();
   }

   public float progress() {
      if (this.isMaxRank()) {
         return 1.0F;
      }

      int range = this.experienceToNextLevel();
      return range <= 0 ? 1.0F : Math.min(1.0F, Math.max(0.0F, (float)this.currentLevelExperience() / range));
   }

   public GuildLevelData addExperience(int amount) {
      if (amount <= 0) {
         return this;
      }

      long nextTotalExperience = (long)this.totalExperience() + amount;
      return fromTotalExperience((int)Math.min(2147483647L, nextTotalExperience));
   }

   public GuildLevelData removeExperience(int amount) {
      return amount <= 0 ? this : fromTotalExperience(Math.max(0, this.totalExperience() - amount));
   }

   private int currentRankMinExperience() {
      return minExperienceForLevel(this.levelIndex());
   }

   private int currentRankMaxExperience() {
      return maxExperienceForLevel(this.levelIndex());
   }

   private static int levelForExperience(int totalExperience) {
      for (int i = RANK_MAX_EXPERIENCE.length - 1; i >= 0; i--) {
         if (totalExperience > RANK_MAX_EXPERIENCE[i]) {
            return i + 2;
         }
      }

      return 1;
   }

   private static int minExperienceForLevel(int level) {
      return level <= 1 ? 0 : maxExperienceForLevel(level - 1) + 1;
   }

   private static int maxExperienceForLevel(int level) {
      int index = level - 1;
      return index >= 0 && index < RANK_MAX_EXPERIENCE.length ? RANK_MAX_EXPERIENCE[index] : Integer.MAX_VALUE;
   }

   private int maxRankMinExperience() {
      return minExperienceForLevel(MAX_LEVEL);
   }
}
