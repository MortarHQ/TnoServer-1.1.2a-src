package org.crypticdev.stextras.quest.assignment;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import org.crypticdev.stextras.quest.definition.QuestCategory;
import org.crypticdev.stextras.quest.definition.QuestDefinition;
import org.crypticdev.stextras.quest.definition.QuestRarity;
import org.crypticdev.stextras.quest.registry.QuestRegistry;
import org.crypticdev.stextras.storage.quest.ISTExtrasQuests;
import org.crypticdev.stextras.storage.quest.STExtrasQuestStorage;

public final class RequiredQuestSelector {
   public static List<ResourceLocation> selectRequiredForPrestige(int prestige, RandomSource rng) {
      return selectRequiredForPrestige(prestige, rng, Set.of());
   }

   public static List<ResourceLocation> selectRequiredForPrestige(int prestige, RandomSource rng, Set<String> blockedSignatures) {
      Map<Integer, List<QuestDefinition>> byPrestige = QuestRegistry.INSTANCE
         .byCategory(QuestCategory.REQUIRED)
         .stream()
         .collect(Collectors.groupingBy(QuestDefinition -> QuestDefinition.prestige));
      List<QuestDefinition> pool = byPrestige.getOrDefault(prestige, List.of());
      if (pool.isEmpty()) {
         Optional<Integer> nearest = byPrestige.keySet()
            .stream()
            .min(Comparator.<Integer>comparingInt(value -> Math.abs(value - prestige)).thenComparingInt(value -> value));
         pool = nearest.map(byPrestige::get).orElse(List.of());
      }

      if (pool.isEmpty()) {
         return List.of();
      }

      Map<QuestRarity, List<QuestDefinition>> byRarity = new EnumMap<>(QuestRarity.class);

      for (QuestRarity rarity : QuestRarity.values()) {
         byRarity.put(rarity, new ArrayList<>());
      }

      for (QuestDefinition QuestDefinition : pool) {
         byRarity.computeIfAbsent(QuestDefinition.rarity, ignored -> new ArrayList<>()).add(QuestDefinition);
      }

      int totalCount = Math.max(1, questCountForPrestige(prestige));
      Map<QuestRarity, Integer> baseWeights = rarityWeightsForPrestige(prestige, rng);
      List<ResourceLocation> selected = new ArrayList<>();
      Set<ResourceLocation> used = new HashSet<>();
      Set<String> usedSignatures = QuestDuplicateSignatures.shouldSkipDupes() ? new HashSet<>(blockedSignatures) : Set.of();

      for (int i = 0; i < totalCount; i++) {
         Map<QuestRarity, Integer> liveWeights = new EnumMap<>(QuestRarity.class);
         int weightSum = 0;

         for (Entry<QuestRarity, Integer> entry : baseWeights.entrySet()) {
            List<QuestDefinition> remaining = byRarity.getOrDefault(entry.getKey(), List.of())
               .stream()
               .filter(QuestDefinition -> !used.contains(QuestDefinition.id))
               .filter(QuestDefinition -> !QuestDuplicateSignatures.isBlocked(QuestDefinition, usedSignatures))
               .toList();
            if (entry.getValue() > 0 && !remaining.isEmpty()) {
               liveWeights.put(entry.getKey(), entry.getValue());
               weightSum += entry.getValue();
            }
         }

         QuestDefinition chosen = null;
         if (weightSum > 0) {
            QuestRarity rarity = weightedRandomRarity(rng, liveWeights);
            List<QuestDefinition> bucket = byRarity.getOrDefault(rarity, List.of())
               .stream()
               .filter(QuestDefinition -> !used.contains(QuestDefinition.id))
               .filter(QuestDefinition -> !QuestDuplicateSignatures.isBlocked(QuestDefinition, usedSignatures))
               .toList();
            if (!bucket.isEmpty()) {
               chosen = bucket.get(rng.nextInt(bucket.size()));
            }
         }

         if (chosen == null) {
            List<QuestDefinition> fallback = pool.stream()
               .filter(QuestDefinition -> !used.contains(QuestDefinition.id))
               .filter(QuestDefinition -> !QuestDuplicateSignatures.isBlocked(QuestDefinition, usedSignatures))
               .toList();
            if (fallback.isEmpty()) {
               break;
            }

            chosen = fallback.get(rng.nextInt(fallback.size()));
         }

         used.add(chosen.id);
         QuestDuplicateSignatures.addSignature(usedSignatures, chosen);
         selected.add(chosen.id);
      }

      return selected;
   }

   public static void applyRequiredOnCharacterReset(ServerPlayer player, int prestige) {
      RandomSource rng = RandomSource.create(seedFor(player.server, prestige * 31L ^ Instant.now().getEpochSecond()));
      STExtrasQuestStorage store = ((ISTExtrasQuests)player).stextras$getQuests();
      Set<String> blockedSignatures = QuestDuplicateSignatures.collectActiveSignatures(store, QuestCategory.REQUIRED);
      List<ResourceLocation> chosen = selectRequiredForPrestige(prestige, rng, blockedSignatures);
      store.clearCategory(QuestCategory.REQUIRED);

      for (ResourceLocation id : chosen) {
         store.addActive(id);
      }
   }

   public static QuestRarity weightedRandomRarity(RandomSource rng, Map<QuestRarity, Integer> weights) {
      int total = weights.values().stream().mapToInt(Integer::intValue).sum();
      if (total <= 0) {
         return QuestRarity.NOVICE;
      }

      int roll = rng.nextInt(total);
      int cumulative = 0;

      for (Entry<QuestRarity, Integer> entry : weights.entrySet()) {
         cumulative += entry.getValue();
         if (roll < cumulative) {
            return entry.getKey();
         }
      }

      return QuestRarity.NOVICE;
   }

   public static int questCountForPrestige(int prestige) {
      double min = 1.0;
      double max = 6.0;
      if (prestige <= 0) {
         return 1;
      }

      double growth = Math.log(prestige + 1) / Math.log(prestige + 20);
      double value = min + (max - min) * growth;
      return (int)Math.round(Math.min(value, max));
   }

   public static Map<QuestRarity, Integer> rarityWeightsForPrestige(int prestige, RandomSource rng) {
      Map<QuestRarity, Integer> weights = new EnumMap<>(QuestRarity.class);
      double novice = 0.0;
      double adept = 0.0;
      double intermediate = 0.0;
      double expert = 0.0;
      double master = 0.0;
      if (prestige <= 3) {
         novice = 70 - prestige * 10;
         adept = 30 + prestige * 10;
      } else if (prestige <= 7) {
         adept = Math.max(0, 60 - (prestige - 4) * 10);
         intermediate = (prestige - 3) * 15;
         novice = Math.max(0, 10 - (prestige - 4) * 2);
      } else if (prestige <= 10) {
         intermediate = Math.max(0, 70 - (prestige - 8) * 15);
         expert = (prestige - 7) * 15;
         adept = Math.max(0, 10 - (prestige - 8) * 3);
      } else {
         expert = Math.max(0, 60 - (prestige - 10) * 5);
         master = Math.min(100, (prestige - 9) * 5);
         intermediate = Math.max(0, 20 - (prestige - 10) * 2);
      }

      double variance = 0.05;
      novice *= 1.0 + (rng.nextDouble() * 2.0 - 1.0) * variance;
      adept *= 1.0 + (rng.nextDouble() * 2.0 - 1.0) * variance;
      intermediate *= 1.0 + (rng.nextDouble() * 2.0 - 1.0) * variance;
      expert *= 1.0 + (rng.nextDouble() * 2.0 - 1.0) * variance;
      master *= 1.0 + (rng.nextDouble() * 2.0 - 1.0) * variance;
      double total = novice + adept + intermediate + expert + master;
      if (total <= 0.0) {
         total = 1.0;
      }

      weights.put(QuestRarity.NOVICE, (int)(novice / total * 100.0));
      weights.put(QuestRarity.ADEPT, (int)(adept / total * 100.0));
      weights.put(QuestRarity.INTERMEDIATE, (int)(intermediate / total * 100.0));
      weights.put(QuestRarity.EXPERT, (int)(expert / total * 100.0));
      weights.put(QuestRarity.MASTER, (int)(master / total * 100.0));
      return weights;
   }

   private static long seedFor(MinecraftServer server, long salt) {
      long worldSeed = server.overworld().getSeed();
      return worldSeed ^ salt * -7046029254386353131L;
   }

   private RequiredQuestSelector() {
   }
}
