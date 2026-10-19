package io.github.Memoires.mysticism.world;

import io.github.Memoires.mysticism.data.MysticismBiomeTags;
import io.github.Memoires.mysticism.registry.entity.MysticismMonsterEntityTypes;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;

public class MysticismBiomeModification {
   public static final List<MysticismBiomeModification.MobSpawningTemplate> MOB_SPAWNING = new ArrayList<>();

   public static void initMobSpawning() {
      addSpawn(MysticismBiomeTags.EntitySpawn.OKAMI, (EntityType<?>)MysticismMonsterEntityTypes.OKAMI.get(), 35, 1, 3, 1.5);
      addSpawn(MysticismBiomeTags.EntitySpawn.DARKNESS_BIOME, (EntityType<?>)MysticismMonsterEntityTypes.SHADOW_IMP.get(), 35, 1, 3, 1.5);
   }

   private static void addSpawn(TagKey<Biome> biome, EntityType<?> type, int weight, int min, int max, double charge) {
      addSpawn(biome, type, weight, min, max, 4.0, charge);
   }

   private static void addSpawn(TagKey<Biome> biome, EntityType<?> type, int weight, int min, int max, double budget, double charge) {
      MOB_SPAWNING.add(new MysticismBiomeModification.MobSpawningTemplate(type, biome, weight, min, max, budget, charge));
   }

   public static class MobSpawningTemplate {
      private final EntityType<?> type;
      private final TagKey<Biome> biome;
      private final int weight;
      private final int minPack;
      private final int maxPack;
      private final double budget;
      private final double charge;

      public EntityType<?> getType() {
         return this.type;
      }

      public TagKey<Biome> getBiome() {
         return this.biome;
      }

      public int getWeight() {
         return this.weight;
      }

      public int getMinPack() {
         return this.minPack;
      }

      public int getMaxPack() {
         return this.maxPack;
      }

      public double getBudget() {
         return this.budget;
      }

      public double getCharge() {
         return this.charge;
      }

      public MobSpawningTemplate(EntityType<?> type, TagKey<Biome> biome, int weight, int minPack, int maxPack, double budget, double charge) {
         this.type = type;
         this.biome = biome;
         this.weight = weight;
         this.minPack = minPack;
         this.maxPack = maxPack;
         this.budget = budget;
         this.charge = charge;
      }
   }
}
