package net.regions_unexplored.block.sapling;

import java.util.Optional;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.regions_unexplored.registry.data.RUConfiguredFeatures;

public interface RUTreeGrowers {
   TreeGrower ASHEN = create("ashen", RUConfiguredFeatures.TREE_ASHEN);
   TreeGrower ALPHA_OAK = create("alpha_oak", RUConfiguredFeatures.TREE_ALPHA_OAK);
   TreeGrower APPLE_OAK = create("apple_oak", 0.2F, RUConfiguredFeatures.TREE_APPLE_OAK, RUConfiguredFeatures.TREE_BIG_APPLE_OAK);
   TreeGrower BLUE_BIOSHROOM = create("blue_bioshroom", RUConfiguredFeatures.TREE_GIANT_BLUE_BIOSHROOM);
   TreeGrower GREEN_BIOSHROOM = create("green_bioshroom", RUConfiguredFeatures.TREE_GIANT_GREEN_BIOSHROOM);
   TreeGrower PINK_BIOSHROOM = create("pink_bioshroom", RUConfiguredFeatures.TREE_GIANT_PINK_BIOSHROOM);
   TreeGrower YELLOW_BIOSHROOM = create("yellow_bioshroom", RUConfiguredFeatures.TREE_YELLOW_BIOSHROOM_LARGE);
   TreeGrower BAMBOO = create("bamboo", RUConfiguredFeatures.TREE_BAMBOO);
   RuUltraFromMegaTreeGrower BAOBAB = new RuUltraFromMegaTreeGrower(
      "baobab", Optional.of(RUConfiguredFeatures.TREE_ULTRA_BAOBAB), Optional.of(RUConfiguredFeatures.TREE_MEGA_BAOBAB), Optional.empty(), Optional.empty()
   );
   TreeGrower BLACKWOOD = new TreeGrower(
      "blackwood",
      0.1F,
      Optional.of(RUConfiguredFeatures.TREE_GIANT_BLACKWOOD),
      Optional.empty(),
      Optional.of(RUConfiguredFeatures.TREE_BIG_BLACKWOOD),
      Optional.of(RUConfiguredFeatures.TREE_BLACKWOOD),
      Optional.empty(),
      Optional.empty()
   );
   TreeGrower SAGUARO_CACTUS = new TreeGrower("saguaro_cactus", Optional.empty(), Optional.of(RUConfiguredFeatures.TREE_SAGUARO_CACTUS), Optional.empty());
   TreeGrower CYPRESS = new TreeGrower(
      "cypress",
      0.25F,
      Optional.empty(),
      Optional.empty(),
      Optional.of(RUConfiguredFeatures.TREE_CYPRESS),
      Optional.of(RUConfiguredFeatures.TREE_GIANT_CYPRESS),
      Optional.empty(),
      Optional.empty()
   );
   TreeGrower DEAD_PINE = new TreeGrower(
      "dead_pine",
      0.1F,
      Optional.empty(),
      Optional.empty(),
      Optional.of(RUConfiguredFeatures.TREE_DEAD_PINE),
      Optional.of(RUConfiguredFeatures.TREE_DEAD_STRIPPED_PINE),
      Optional.of(RUConfiguredFeatures.TREE_DEAD_PINE_TALL),
      Optional.of(RUConfiguredFeatures.TREE_DEAD_STRIPPED_PINE_MOUNTAIN)
   );
   TreeGrower DEAD = new TreeGrower(
      "dead",
      0.1F,
      Optional.empty(),
      Optional.empty(),
      Optional.of(RUConfiguredFeatures.TREE_DEAD),
      Optional.of(RUConfiguredFeatures.TREE_BIG_DEAD),
      Optional.of(RUConfiguredFeatures.TREE_DEAD_BOG),
      Optional.empty()
   );
   TreeGrower EUCALYPTUS = new TreeGrower(
      "eucalyptus",
      0.33F,
      Optional.empty(),
      Optional.empty(),
      Optional.of(RUConfiguredFeatures.TREE_EUCALYPTUS),
      Optional.of(RUConfiguredFeatures.TREE_SMALL_EUCALYPTUS),
      Optional.empty(),
      Optional.empty()
   );
   TreeGrower FLOWERING_OAK = new TreeGrower(
      "flowering_oak",
      0.2F,
      Optional.empty(),
      Optional.empty(),
      Optional.of(RUConfiguredFeatures.TREE_FLOWERING_OAK),
      Optional.of(RUConfiguredFeatures.TREE_BIG_FLOWERING_OAK),
      Optional.empty(),
      Optional.empty()
   );
   TreeGrower GOLDEN_LARCH = new TreeGrower(
      "golden_larch",
      0.25F,
      Optional.empty(),
      Optional.empty(),
      Optional.of(RUConfiguredFeatures.TREE_LARCH_GOLDEN),
      Optional.of(RUConfiguredFeatures.TREE_LARCH_GOLDEN_LARGE),
      Optional.empty(),
      Optional.empty()
   );
   TreeGrower LARCH = new TreeGrower(
      "larch",
      0.25F,
      Optional.empty(),
      Optional.empty(),
      Optional.of(RUConfiguredFeatures.TREE_LARCH),
      Optional.of(RUConfiguredFeatures.TREE_LARCH_LARGE),
      Optional.empty(),
      Optional.empty()
   );
   TreeGrower JOSHUA = new TreeGrower(
      "joshua",
      0.33F,
      Optional.empty(),
      Optional.empty(),
      Optional.of(RUConfiguredFeatures.TREE_JOSHUA_MEDIUM),
      Optional.of(RUConfiguredFeatures.TREE_JOSHUA_LARGE),
      Optional.empty(),
      Optional.empty()
   );
   TreeGrower MAPLE = new TreeGrower(
      "maple",
      0.1F,
      Optional.empty(),
      Optional.empty(),
      Optional.of(RUConfiguredFeatures.TREE_MAPLE),
      Optional.of(RUConfiguredFeatures.TREE_BIG_MAPLE),
      Optional.empty(),
      Optional.empty()
   );
   TreeGrower RED_MAPLE = new TreeGrower(
      "red_maple",
      0.1F,
      Optional.empty(),
      Optional.empty(),
      Optional.of(RUConfiguredFeatures.TREE_RED_MAPLE),
      Optional.of(RUConfiguredFeatures.TREE_BIG_RED_MAPLE),
      Optional.empty(),
      Optional.empty()
   );
   TreeGrower ORANGE_MAPLE = new TreeGrower(
      "orange_maple",
      0.1F,
      Optional.empty(),
      Optional.empty(),
      Optional.of(RUConfiguredFeatures.TREE_ORANGE_MAPLE),
      Optional.of(RUConfiguredFeatures.TREE_BIG_ORANGE_MAPLE),
      Optional.empty(),
      Optional.empty()
   );
   TreeGrower BRIMWOOD = new TreeGrower(
      "brimwood",
      0.25F,
      Optional.empty(),
      Optional.empty(),
      Optional.of(RUConfiguredFeatures.TREE_BRIM_WILLOW),
      Optional.of(RUConfiguredFeatures.TREE_TALL_BRIM_WILLOW),
      Optional.empty(),
      Optional.empty()
   );
   TreeGrower COBALT = new TreeGrower("cobalt", Optional.empty(), Optional.of(RUConfiguredFeatures.TREE_COBALT), Optional.empty());
   TreeGrower SILVER_BIRCH = new TreeGrower(
      "silver_birch",
      0.25F,
      Optional.empty(),
      Optional.empty(),
      Optional.of(RUConfiguredFeatures.TREE_SILVER_BIRCH),
      Optional.of(RUConfiguredFeatures.TREE_SILVER_BIRCH_TALL),
      Optional.empty(),
      Optional.empty()
   );
   TreeGrower SMALL_OAK = new TreeGrower("small_oak", Optional.empty(), Optional.of(RUConfiguredFeatures.TREE_SMALL_OAK), Optional.empty());
   TreeGrower SOCOTRA = new TreeGrower(
      "socotra",
      0.1F,
      Optional.empty(),
      Optional.empty(),
      Optional.of(RUConfiguredFeatures.TREE_LARGE_SOCOTRA),
      Optional.of(RUConfiguredFeatures.TREE_SMALL_SOCOTRA),
      Optional.empty(),
      Optional.empty()
   );
   TreeGrower WILLOW = new TreeGrower(
      "willow",
      0.1F,
      Optional.empty(),
      Optional.empty(),
      Optional.of(RUConfiguredFeatures.TREE_WILLOW),
      Optional.of(RUConfiguredFeatures.TREE_BIG_WILLOW),
      Optional.empty(),
      Optional.empty()
   );
   TreeGrower MAGNOLIA = new TreeGrower(
      "magnolia",
      0.1F,
      Optional.empty(),
      Optional.empty(),
      Optional.of(RUConfiguredFeatures.TREE_MAGNOLIA),
      Optional.of(RUConfiguredFeatures.TREE_BIG_MAGNOLIA),
      Optional.empty(),
      Optional.empty()
   );
   TreeGrower WHITE_MAGNOLIA = new TreeGrower(
      "white_magnolia",
      0.1F,
      Optional.empty(),
      Optional.empty(),
      Optional.of(RUConfiguredFeatures.TREE_WHITE_MAGNOLIA),
      Optional.of(RUConfiguredFeatures.TREE_BIG_WHITE_MAGNOLIA),
      Optional.empty(),
      Optional.empty()
   );
   TreeGrower PINK_MAGNOLIA = new TreeGrower(
      "pink_magnolia",
      0.1F,
      Optional.empty(),
      Optional.empty(),
      Optional.of(RUConfiguredFeatures.TREE_PINK_MAGNOLIA),
      Optional.of(RUConfiguredFeatures.TREE_BIG_PINK_MAGNOLIA),
      Optional.empty(),
      Optional.empty()
   );
   TreeGrower BLUE_MAGNOLIA = new TreeGrower(
      "blue_magnolia",
      0.1F,
      Optional.empty(),
      Optional.empty(),
      Optional.of(RUConfiguredFeatures.TREE_BLUE_MAGNOLIA),
      Optional.of(RUConfiguredFeatures.TREE_BIG_BLUE_MAGNOLIA),
      Optional.empty(),
      Optional.empty()
   );
   RuUltraFromSuperTreeGrower KAPOK = new RuUltraFromSuperTreeGrower(
      "kapok", Optional.empty(), Optional.of(RUConfiguredFeatures.TREE_KAPOK), Optional.empty(), Optional.empty()
   );
   RuUltraFromSuperTreeGrower REDWOOD = new RuUltraFromSuperTreeGrower(
      "redwood",
      Optional.of(RUConfiguredFeatures.TREE_REDWOOD_LARGE),
      Optional.of(RUConfiguredFeatures.TREE_REDWOOD_MEDIUM),
      Optional.of(RUConfiguredFeatures.TREE_REDWOOD_SMALL),
      Optional.empty()
   );
   TreeGrower PALM = create("palm", RUConfiguredFeatures.TREE_PALM, RUConfiguredFeatures.TREE_TALL_PALM);
   TreeGrower PINE = create("pine", RUConfiguredFeatures.TREE_PINE, RUConfiguredFeatures.TREE_PINE_TALL);
   TreeGrower SKY_WISTERIA = create("sky_wisteria", RUConfiguredFeatures.TREE_WISTERIA_SKY, RUConfiguredFeatures.TREE_WISTERIA_LARGE_SKY);
   TreeGrower LAVENDER_WISTERIA = create("lavender_wisteria", RUConfiguredFeatures.TREE_WISTERIA_LAVENDER, RUConfiguredFeatures.TREE_WISTERIA_LARGE_LAVENDER);
   TreeGrower SALMON_WISTERIA = create("salmon_wisteria", RUConfiguredFeatures.TREE_WISTERIA_SALMON, RUConfiguredFeatures.TREE_WISTERIA_LARGE_SALMON);

   static TreeGrower create(String name, ResourceKey<ConfiguredFeature<?, ?>> tree) {
      return new TreeGrower(name, 0.0F, Optional.empty(), Optional.empty(), Optional.of(tree), Optional.empty(), Optional.empty(), Optional.empty());
   }

   static TreeGrower create(String name, ResourceKey<ConfiguredFeature<?, ?>> tree, ResourceKey<ConfiguredFeature<?, ?>> secondaryTree) {
      return create(name, 0.1F, tree, secondaryTree);
   }

   static TreeGrower create(String name, float secondaryChance, ResourceKey<ConfiguredFeature<?, ?>> tree, ResourceKey<ConfiguredFeature<?, ?>> secondaryTree) {
      return new TreeGrower(
         name, secondaryChance, Optional.empty(), Optional.empty(), Optional.of(tree), Optional.of(secondaryTree), Optional.empty(), Optional.empty()
      );
   }
}
