package twilightforest.data.tags;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import twilightforest.TwilightForestMod;
import twilightforest.init.TFBiomes;

public class BiomeTagGenerator extends BiomeTagsProvider {
   public static final TagKey<Biome> IS_TWILIGHT = TagKey.create(Registries.BIOME, TwilightForestMod.prefix("in_twilight_forest"));
   public static final TagKey<Biome> VALID_QUEST_GROVE_BIOMES = TagKey.create(Registries.BIOME, TwilightForestMod.prefix("valid_quest_grove_biomes"));
   public static final TagKey<Biome> VALID_HOLLOW_TREE_BIOMES = TagKey.create(Registries.BIOME, TwilightForestMod.prefix("valid_hollow_tree_biomes"));
   public static final TagKey<Biome> VALID_HEDGE_MAZE_BIOMES = TagKey.create(Registries.BIOME, TwilightForestMod.prefix("valid_hedge_maze_biomes"));
   public static final TagKey<Biome> VALID_HOLLOW_HILL_BIOMES = TagKey.create(Registries.BIOME, TwilightForestMod.prefix("valid_hollow_hill_biomes"));
   public static final TagKey<Biome> VALID_CAMP_BIOMES = TagKey.create(Registries.BIOME, TwilightForestMod.prefix("valid_camp_biomes"));
   public static final TagKey<Biome> VALID_MUSHROOM_TOWER_BIOMES = TagKey.create(Registries.BIOME, TwilightForestMod.prefix("valid_mushroom_tower_biomes"));
   public static final TagKey<Biome> VALID_NAGA_COURTYARD_BIOMES = TagKey.create(Registries.BIOME, TwilightForestMod.prefix("valid_naga_courtyard_biomes"));
   public static final TagKey<Biome> VALID_LICH_TOWER_BIOMES = TagKey.create(Registries.BIOME, TwilightForestMod.prefix("valid_lich_tower_biomes"));
   public static final TagKey<Biome> VALID_LABYRINTH_BIOMES = TagKey.create(Registries.BIOME, TwilightForestMod.prefix("valid_labyrinth_biomes"));
   public static final TagKey<Biome> VALID_HYDRA_LAIR_BIOMES = TagKey.create(Registries.BIOME, TwilightForestMod.prefix("valid_hydra_lair_biomes"));
   public static final TagKey<Biome> VALID_KNIGHT_STRONGHOLD_BIOMES = TagKey.create(
      Registries.BIOME, TwilightForestMod.prefix("valid_knight_stronghold_biomes")
   );
   public static final TagKey<Biome> VALID_DARK_TOWER_BIOMES = TagKey.create(Registries.BIOME, TwilightForestMod.prefix("valid_dark_tower_biomes"));
   public static final TagKey<Biome> VALID_YETI_CAVE_BIOMES = TagKey.create(Registries.BIOME, TwilightForestMod.prefix("valid_yeti_cave_biomes"));
   public static final TagKey<Biome> VALID_AURORA_PALACE_BIOMES = TagKey.create(Registries.BIOME, TwilightForestMod.prefix("valid_aurora_palace_biomes"));
   public static final TagKey<Biome> VALID_TROLL_CAVE_BIOMES = TagKey.create(Registries.BIOME, TwilightForestMod.prefix("valid_troll_cave_biomes"));
   public static final TagKey<Biome> VALID_GIANT_HOUSE_BIOMES = TagKey.create(Registries.BIOME, TwilightForestMod.prefix("valid_giant_house_biomes"));
   public static final TagKey<Biome> VALID_FINAL_CASTLE_BIOMES = TagKey.create(Registries.BIOME, TwilightForestMod.prefix("valid_final_castle_biomes"));

   public BiomeTagGenerator(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
      super(output, provider, "twilightforest", helper);
   }

   protected void addTags(Provider provider) {
      this.tag(IS_TWILIGHT)
         .add(
            new ResourceKey[]{
               TFBiomes.CLEARING,
               TFBiomes.DENSE_FOREST,
               TFBiomes.DENSE_MUSHROOM_FOREST,
               TFBiomes.FIREFLY_FOREST,
               TFBiomes.FOREST,
               TFBiomes.MUSHROOM_FOREST,
               TFBiomes.OAK_SAVANNAH,
               TFBiomes.SPOOKY_FOREST,
               TFBiomes.ENCHANTED_FOREST,
               TFBiomes.DENSE_MUSHROOM_FOREST,
               TFBiomes.LAKE,
               TFBiomes.STREAM,
               TFBiomes.UNDERGROUND,
               TFBiomes.SWAMP,
               TFBiomes.FIRE_SWAMP,
               TFBiomes.DARK_FOREST,
               TFBiomes.DARK_FOREST_CENTER,
               TFBiomes.SNOWY_FOREST,
               TFBiomes.GLACIER,
               TFBiomes.HIGHLANDS,
               TFBiomes.THORNLANDS,
               TFBiomes.FINAL_PLATEAU
            }
         );
      this.tag(VALID_QUEST_GROVE_BIOMES).add(TFBiomes.ENCHANTED_FOREST);
      this.tag(VALID_MUSHROOM_TOWER_BIOMES).add(TFBiomes.DENSE_MUSHROOM_FOREST);
      this.tag(VALID_CAMP_BIOMES)
         .add(new ResourceKey[]{TFBiomes.OAK_SAVANNAH, TFBiomes.CLEARING, TFBiomes.MUSHROOM_FOREST, TFBiomes.FOREST, TFBiomes.FIREFLY_FOREST});
      this.tag(VALID_HOLLOW_TREE_BIOMES)
         .add(
            new ResourceKey[]{
               TFBiomes.DENSE_FOREST,
               TFBiomes.FIRE_SWAMP,
               TFBiomes.DENSE_MUSHROOM_FOREST,
               TFBiomes.FIREFLY_FOREST,
               TFBiomes.FOREST,
               TFBiomes.MUSHROOM_FOREST,
               TFBiomes.OAK_SAVANNAH,
               TFBiomes.ENCHANTED_FOREST
            }
         );
      this.tag(VALID_HEDGE_MAZE_BIOMES)
         .add(
            new ResourceKey[]{
               TFBiomes.CLEARING,
               TFBiomes.DENSE_FOREST,
               TFBiomes.DENSE_MUSHROOM_FOREST,
               TFBiomes.FIREFLY_FOREST,
               TFBiomes.FOREST,
               TFBiomes.MUSHROOM_FOREST,
               TFBiomes.OAK_SAVANNAH,
               TFBiomes.SPOOKY_FOREST
            }
         );
      this.tag(VALID_HOLLOW_HILL_BIOMES)
         .add(
            new ResourceKey[]{
               TFBiomes.CLEARING,
               TFBiomes.DENSE_FOREST,
               TFBiomes.DENSE_MUSHROOM_FOREST,
               TFBiomes.FIREFLY_FOREST,
               TFBiomes.FOREST,
               TFBiomes.MUSHROOM_FOREST,
               TFBiomes.OAK_SAVANNAH,
               TFBiomes.SPOOKY_FOREST
            }
         );
      this.tag(VALID_NAGA_COURTYARD_BIOMES)
         .add(
            new ResourceKey[]{
               TFBiomes.CLEARING,
               TFBiomes.DENSE_FOREST,
               TFBiomes.DENSE_MUSHROOM_FOREST,
               TFBiomes.FIREFLY_FOREST,
               TFBiomes.FOREST,
               TFBiomes.MUSHROOM_FOREST,
               TFBiomes.OAK_SAVANNAH,
               TFBiomes.SPOOKY_FOREST
            }
         );
      this.tag(VALID_LICH_TOWER_BIOMES)
         .add(
            new ResourceKey[]{
               TFBiomes.CLEARING,
               TFBiomes.DENSE_FOREST,
               TFBiomes.DENSE_MUSHROOM_FOREST,
               TFBiomes.FIREFLY_FOREST,
               TFBiomes.FOREST,
               TFBiomes.MUSHROOM_FOREST,
               TFBiomes.OAK_SAVANNAH,
               TFBiomes.SPOOKY_FOREST
            }
         );
      this.tag(VALID_LABYRINTH_BIOMES).add(TFBiomes.SWAMP);
      this.tag(VALID_HYDRA_LAIR_BIOMES).add(TFBiomes.FIRE_SWAMP);
      this.tag(VALID_KNIGHT_STRONGHOLD_BIOMES).add(TFBiomes.DARK_FOREST);
      this.tag(VALID_DARK_TOWER_BIOMES).add(TFBiomes.DARK_FOREST_CENTER);
      this.tag(VALID_YETI_CAVE_BIOMES).add(TFBiomes.SNOWY_FOREST);
      this.tag(VALID_AURORA_PALACE_BIOMES).add(TFBiomes.GLACIER);
      this.tag(VALID_TROLL_CAVE_BIOMES).add(TFBiomes.HIGHLANDS);
      this.tag(VALID_GIANT_HOUSE_BIOMES).add(TFBiomes.HIGHLANDS);
      this.tag(VALID_FINAL_CASTLE_BIOMES).add(TFBiomes.FINAL_PLATEAU);
      this.tag(BiomeTags.WITHOUT_WANDERING_TRADER_SPAWNS).addTag(IS_TWILIGHT);
      this.tag(BiomeTags.WITHOUT_PATROL_SPAWNS).addTag(IS_TWILIGHT);
      this.tag(BiomeTags.WITHOUT_ZOMBIE_SIEGES).addTag(IS_TWILIGHT);
      this.tag(BiomeTags.SPAWNS_COLD_VARIANT_FROGS).add(new ResourceKey[]{TFBiomes.SNOWY_FOREST, TFBiomes.GLACIER});
      this.tag(BiomeTags.SPAWNS_WARM_VARIANT_FROGS).add(new ResourceKey[]{TFBiomes.OAK_SAVANNAH, TFBiomes.FIRE_SWAMP});
      this.tag(BiomeTags.SPAWNS_SNOW_FOXES).add(new ResourceKey[]{TFBiomes.SNOWY_FOREST, TFBiomes.GLACIER});
      this.tag(BiomeTags.SPAWNS_WHITE_RABBITS).add(new ResourceKey[]{TFBiomes.SNOWY_FOREST, TFBiomes.GLACIER});
      this.tag(BiomeTags.SNOW_GOLEM_MELTS).add(new ResourceKey[]{TFBiomes.OAK_SAVANNAH, TFBiomes.FIRE_SWAMP});
      this.tag(BiomeTags.HAS_CLOSER_WATER_FOG).add(new ResourceKey[]{TFBiomes.SPOOKY_FOREST, TFBiomes.SWAMP, TFBiomes.FIRE_SWAMP});
   }

   public String getName() {
      return "Twilight Forest Biome Tags";
   }
}
