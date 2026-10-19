package twilightforest.data.tags;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.decoration.PaintingVariants;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.dimension.DimensionType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import twilightforest.TFRegistries;
import twilightforest.TwilightForestMod;
import twilightforest.init.TFBannerPatterns;
import twilightforest.init.TFBlockEntities;
import twilightforest.init.TFDimensionData;
import twilightforest.init.custom.WoodPalettes;
import twilightforest.util.woods.WoodPalette;

public class CustomTagGenerator {
   public static class BannerPatternTagGenerator extends TagsProvider<BannerPattern> {
      public static final TagKey<BannerPattern> NAGA_BANNER_PATTERN = create("pattern_item/naga");
      public static final TagKey<BannerPattern> LICH_BANNER_PATTERN = create("pattern_item/lich");
      public static final TagKey<BannerPattern> MINOSHROOM_BANNER_PATTERN = create("pattern_item/minoshroom");
      public static final TagKey<BannerPattern> HYDRA_BANNER_PATTERN = create("pattern_item/hydra");
      public static final TagKey<BannerPattern> KNIGHT_PHANTOM_BANNER_PATTERN = create("pattern_item/knight_phantom");
      public static final TagKey<BannerPattern> UR_GHAST_BANNER_PATTERN = create("pattern_item/ur_ghast");
      public static final TagKey<BannerPattern> ALPHA_YETI_BANNER_PATTERN = create("pattern_item/alpha_yeti");
      public static final TagKey<BannerPattern> SNOW_QUEEN_BANNER_PATTERN = create("pattern_item/snow_queen");
      public static final TagKey<BannerPattern> QUEST_RAM_BANNER_PATTERN = create("pattern_item/quest_ram");

      public BannerPatternTagGenerator(PackOutput output, CompletableFuture<Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
         super(output, Registries.BANNER_PATTERN, provider, "twilightforest", existingFileHelper);
      }

      protected void addTags(Provider provider) {
         this.tag(NAGA_BANNER_PATTERN).add(TFBannerPatterns.NAGA);
         this.tag(LICH_BANNER_PATTERN).add(TFBannerPatterns.LICH);
         this.tag(MINOSHROOM_BANNER_PATTERN).add(TFBannerPatterns.MINOSHROOM);
         this.tag(HYDRA_BANNER_PATTERN).add(TFBannerPatterns.HYDRA);
         this.tag(KNIGHT_PHANTOM_BANNER_PATTERN).add(TFBannerPatterns.KNIGHT_PHANTOM);
         this.tag(UR_GHAST_BANNER_PATTERN).add(TFBannerPatterns.UR_GHAST);
         this.tag(ALPHA_YETI_BANNER_PATTERN).add(TFBannerPatterns.ALPHA_YETI);
         this.tag(SNOW_QUEEN_BANNER_PATTERN).add(TFBannerPatterns.SNOW_QUEEN);
         this.tag(QUEST_RAM_BANNER_PATTERN).add(TFBannerPatterns.QUESTING_RAM);
      }

      private static TagKey<BannerPattern> create(String name) {
         return TagKey.create(Registries.BANNER_PATTERN, TwilightForestMod.prefix(name));
      }

      public String getName() {
         return "Twilight Forest Banner Pattern Tags";
      }
   }

   public static class BlockEntityTagGenerator extends TagsProvider<BlockEntityType<?>> {
      public static final TagKey<BlockEntityType<?>> RELOCATION_NOT_SUPPORTED = TagKey.create(
         Registries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("c", "relocation_not_supported")
      );
      public static final TagKey<BlockEntityType<?>> IMMOVABLE = TagKey.create(
         Registries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("c", "immovable")
      );

      public BlockEntityTagGenerator(PackOutput output, CompletableFuture<Provider> provider, @Nullable ExistingFileHelper helper) {
         super(output, Registries.BLOCK_ENTITY_TYPE, provider, "twilightforest", helper);
      }

      protected void addTags(Provider provider) {
         this.tag(RELOCATION_NOT_SUPPORTED)
            .add(
               new ResourceKey[]{
                  TFBlockEntities.ANTIBUILDER.getKey(),
                  TFBlockEntities.BEANSTALK_GROWER.getKey(),
                  TFBlockEntities.NAGA_SPAWNER.getKey(),
                  TFBlockEntities.LICH_SPAWNER.getKey(),
                  TFBlockEntities.MINOSHROOM_SPAWNER.getKey(),
                  TFBlockEntities.HYDRA_SPAWNER.getKey(),
                  TFBlockEntities.KNIGHT_PHANTOM_SPAWNER.getKey(),
                  TFBlockEntities.UR_GHAST_SPAWNER.getKey(),
                  TFBlockEntities.ALPHA_YETI_SPAWNER.getKey(),
                  TFBlockEntities.SNOW_QUEEN_SPAWNER.getKey(),
                  TFBlockEntities.FINAL_BOSS_SPAWNER.getKey()
               }
            );
         this.tag(IMMOVABLE)
            .add(
               new ResourceKey[]{
                  TFBlockEntities.ANTIBUILDER.getKey(),
                  TFBlockEntities.BEANSTALK_GROWER.getKey(),
                  TFBlockEntities.NAGA_SPAWNER.getKey(),
                  TFBlockEntities.LICH_SPAWNER.getKey(),
                  TFBlockEntities.MINOSHROOM_SPAWNER.getKey(),
                  TFBlockEntities.HYDRA_SPAWNER.getKey(),
                  TFBlockEntities.KNIGHT_PHANTOM_SPAWNER.getKey(),
                  TFBlockEntities.UR_GHAST_SPAWNER.getKey(),
                  TFBlockEntities.ALPHA_YETI_SPAWNER.getKey(),
                  TFBlockEntities.SNOW_QUEEN_SPAWNER.getKey(),
                  TFBlockEntities.FINAL_BOSS_SPAWNER.getKey()
               }
            );
      }

      public String getName() {
         return "Twilight Forest Block Entity Tags";
      }
   }

   public static class DimensionTypeTagGenerator extends TagsProvider<DimensionType> {
      public static final TagKey<DimensionType> ALLOWS_MAGIC_MAP_CHARTING = TagKey.create(
         Registries.DIMENSION_TYPE, TwilightForestMod.prefix("allows_magic_map_charting")
      );

      public DimensionTypeTagGenerator(PackOutput output, CompletableFuture<Provider> provider, @Nullable ExistingFileHelper helper) {
         super(output, Registries.DIMENSION_TYPE, provider, "twilightforest", helper);
      }

      protected void addTags(Provider provider) {
         this.tag(ALLOWS_MAGIC_MAP_CHARTING).add(TFDimensionData.TWILIGHT_DIM_TYPE);
      }

      public String getName() {
         return "Twilight Forest DimensionType Tags";
      }
   }

   public static class PaintingVariantTagGenerator extends TagsProvider<PaintingVariant> {
      public static final TagKey<PaintingVariant> LICH_TOWER_PAINTINGS = TagKey.create(Registries.PAINTING_VARIANT, TwilightForestMod.prefix("tower_paintings"));
      public static final TagKey<PaintingVariant> LICH_BOSS_PAINTINGS = TagKey.create(
         Registries.PAINTING_VARIANT, TwilightForestMod.prefix("tower_boss_paintings")
      );

      public PaintingVariantTagGenerator(PackOutput output, CompletableFuture<Provider> provider, @Nullable ExistingFileHelper helper) {
         super(output, Registries.PAINTING_VARIANT, provider, "twilightforest", helper);
      }

      protected void addTags(Provider provider) {
         this.tag(LICH_TOWER_PAINTINGS)
            .add(
               new ResourceKey[]{
                  PaintingVariants.KEBAB,
                  PaintingVariants.AZTEC,
                  PaintingVariants.ALBAN,
                  PaintingVariants.AZTEC2,
                  PaintingVariants.BOMB,
                  PaintingVariants.PLANT,
                  PaintingVariants.WASTELAND,
                  PaintingVariants.POOL,
                  PaintingVariants.COURBET,
                  PaintingVariants.SEA,
                  PaintingVariants.SUNSET,
                  PaintingVariants.CREEBET,
                  PaintingVariants.WANDERER,
                  PaintingVariants.GRAHAM,
                  PaintingVariants.MATCH,
                  PaintingVariants.BUST,
                  PaintingVariants.STAGE,
                  PaintingVariants.VOID,
                  PaintingVariants.SKULL_AND_ROSES,
                  PaintingVariants.WITHER,
                  PaintingVariants.FIGHTERS,
                  PaintingVariants.POINTER,
                  PaintingVariants.PIGSCENE,
                  PaintingVariants.BURNING_SKULL,
                  PaintingVariants.SKELETON,
                  PaintingVariants.DONKEY_KONG,
                  PaintingVariants.EARTH,
                  PaintingVariants.WIND,
                  PaintingVariants.WATER,
                  PaintingVariants.FIRE,
                  PaintingVariants.BAROQUE,
                  PaintingVariants.MEDITATIVE,
                  PaintingVariants.PRAIRIE_RIDE,
                  PaintingVariants.UNPACKED,
                  PaintingVariants.BACKYARD,
                  PaintingVariants.BOUQUET,
                  PaintingVariants.CAVEBIRD,
                  PaintingVariants.CHANGING,
                  PaintingVariants.COTAN,
                  PaintingVariants.ENDBOSS,
                  PaintingVariants.FERN,
                  PaintingVariants.FINDING,
                  PaintingVariants.LOWMIST,
                  PaintingVariants.ORB,
                  PaintingVariants.OWLEMONS,
                  PaintingVariants.PASSAGE,
                  PaintingVariants.POND,
                  PaintingVariants.SUNFLOWERS,
                  PaintingVariants.TIDES
               }
            );
         this.tag(LICH_BOSS_PAINTINGS)
            .add(
               new ResourceKey[]{
                  PaintingVariants.KEBAB,
                  PaintingVariants.AZTEC,
                  PaintingVariants.ALBAN,
                  PaintingVariants.AZTEC2,
                  PaintingVariants.BOMB,
                  PaintingVariants.PLANT,
                  PaintingVariants.WASTELAND,
                  PaintingVariants.POOL,
                  PaintingVariants.COURBET,
                  PaintingVariants.SEA,
                  PaintingVariants.SUNSET,
                  PaintingVariants.CREEBET,
                  PaintingVariants.WANDERER,
                  PaintingVariants.GRAHAM,
                  PaintingVariants.MATCH,
                  PaintingVariants.BUST,
                  PaintingVariants.STAGE,
                  PaintingVariants.VOID,
                  PaintingVariants.SKULL_AND_ROSES,
                  PaintingVariants.WITHER,
                  PaintingVariants.FIGHTERS,
                  PaintingVariants.POINTER,
                  PaintingVariants.PIGSCENE,
                  PaintingVariants.BURNING_SKULL,
                  PaintingVariants.SKELETON,
                  PaintingVariants.DONKEY_KONG,
                  PaintingVariants.BAROQUE,
                  PaintingVariants.MEDITATIVE,
                  PaintingVariants.PRAIRIE_RIDE,
                  PaintingVariants.BACKYARD,
                  PaintingVariants.BOUQUET,
                  PaintingVariants.CAVEBIRD,
                  PaintingVariants.CHANGING,
                  PaintingVariants.COTAN,
                  PaintingVariants.ENDBOSS,
                  PaintingVariants.FERN,
                  PaintingVariants.FINDING,
                  PaintingVariants.LOWMIST,
                  PaintingVariants.ORB,
                  PaintingVariants.OWLEMONS,
                  PaintingVariants.PASSAGE,
                  PaintingVariants.POND,
                  PaintingVariants.SUNFLOWERS,
                  PaintingVariants.TIDES
               }
            );
      }

      public String getName() {
         return "Twilight Forest PaintingVariant Tags";
      }
   }

   public static class WoodPaletteTagGenerator extends TagsProvider<WoodPalette> {
      public static final TagKey<WoodPalette> WELL_SWIZZLE_MASK = create("well_swizzle_mask");
      public static final TagKey<WoodPalette> DRUID_HUT_SWIZZLE_MASK = create("druid_hut_swizzle_mask");
      public static final TagKey<WoodPalette> COMMON_PALETTES = create("common");
      public static final TagKey<WoodPalette> UNCOMMON_PALETTES = create("uncommon");
      public static final TagKey<WoodPalette> RARE_PALETTES = create("rare");
      public static final TagKey<WoodPalette> TREASURE_PALETTES = create("treasure");

      public WoodPaletteTagGenerator(PackOutput output, CompletableFuture<Provider> provider, @Nullable ExistingFileHelper helper) {
         super(output, TFRegistries.Keys.WOOD_PALETTES, provider, "twilightforest", helper);
      }

      protected void addTags(Provider provider) {
         this.tag(WELL_SWIZZLE_MASK).add(WoodPalettes.OAK);
         this.tag(DRUID_HUT_SWIZZLE_MASK).add(new ResourceKey[]{WoodPalettes.OAK, WoodPalettes.SPRUCE, WoodPalettes.BIRCH});
         this.tag(COMMON_PALETTES).add(new ResourceKey[]{WoodPalettes.SPRUCE, WoodPalettes.CANOPY});
         this.tag(UNCOMMON_PALETTES).add(new ResourceKey[]{WoodPalettes.OAK, WoodPalettes.DARKWOOD, WoodPalettes.TWILIGHT_OAK});
         this.tag(RARE_PALETTES).add(new ResourceKey[]{WoodPalettes.BIRCH, WoodPalettes.JUNGLE, WoodPalettes.MANGROVE});
         this.tag(TREASURE_PALETTES).add(new ResourceKey[]{WoodPalettes.TIMEWOOD, WoodPalettes.TRANSWOOD, WoodPalettes.MINEWOOD, WoodPalettes.SORTWOOD});
      }

      private static TagKey<WoodPalette> create(String name) {
         return TagKey.create(TFRegistries.Keys.WOOD_PALETTES, TwilightForestMod.prefix(name));
      }

      public String getName() {
         return "Twilight Forest Wood Palette Tags";
      }
   }
}
