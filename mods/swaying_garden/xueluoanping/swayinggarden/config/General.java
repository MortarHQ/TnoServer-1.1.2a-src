package xueluoanping.swayinggarden.config;

import java.util.List;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;

public class General {
   public static ModConfigSpec CLIENT_CONFIG;
   public static ConfigValue<List<? extends String>> blackList;
   public static ConfigValue<List<? extends String>> wheatLike;
   public static ConfigValue<List<? extends String>> grassLike;
   public static ConfigValue<List<? extends String>> saplingLike;
   public static ConfigValue<List<? extends String>> doubleBlockPlantsTops;
   public static ConfigValue<List<? extends String>> doubleBlockPlantsBottoms;
   public static ConfigValue<List<? extends String>> vineLike;
   public static ConfigValue<List<? extends String>> padLike;
   public static ConfigValue<List<? extends String>> leavesLike;
   public static ConfigValue<List<? extends String>> customLike;

   public static boolean isValidRegex(Object o) {
      return o instanceof String;
   }

   static {
      Builder COMMON_BUILDER = new Builder();
      COMMON_BUILDER.comment(
         "This mod is designed to make it easier for modded plant blocks to quickly support shaders effects, preventing them from awkwardly standing in swaying blossom."
      );
      COMMON_BUILDER.comment("Base settings")
         .comment(
            "Tags or regular expressions are allowed. Tags must start with # and not use regex. Also ! to target class is available (use ~ if want to get more matches). Use % to add state check."
         )
         .push("Base");
      blackList = COMMON_BUILDER.comment("List of blocks not applied.").defineListAllowEmpty("BlackList", List.of(), General::isValidRegex);
      wheatLike = COMMON_BUILDER.comment("List of small crops.")
         .defineListAllowEmpty("Wheat Like", List.of("~net.minecraft.world.level.block.CropBlock"), () -> "", General::isValidRegex);
      grassLike = COMMON_BUILDER.comment("List of small flowers and grasses.")
         .defineListAllowEmpty(
            "Grass Like",
            List.of(
               "#minecraft:small_flowers",
               "regions_unexplored:clover",
               "biomesoplenty:clover",
               "natures_spirit:.*_grass",
               "regions_unexplored:.*_grass",
               "natures_spirit:.*_fern",
               "natures_spirit:sage_succulent",
               "terrainslabs:(?!snow)[^:]*_on_top",
               "bountifulfares:wild_.*",
               "wildernature:hazelnut_bush",
               "vinery:.*_grape_bush_.*",
               "farm_and_charm:tomato_crop%age=0",
               "farmersdelight:budding_tomatoes",
               "vinery:.*_grape_bush",
               "flower_picking:.*",
               "biomesoplenty:.*_grass",
               "biomesoplenty:sprout",
               "biomeswevegone:.*_grass",
               "biomeswevegone:clover_patch"
            ),
            () -> "",
            General::isValidRegex
         );
      saplingLike = COMMON_BUILDER.comment("List of small saplings.")
         .defineListAllowEmpty(
            "Sapling Like",
            List.of(
               "#minecraft:saplings",
               ".*berry_bush",
               "natures_spirit:.*_bearberries",
               "natures_spirit:.*_bitter_sprouts",
               "biomesoplenty:bush",
               "biomesoplenty:tundra_shrub",
               "regions_unexplored:.*_shrub",
               "biomeswevegone:horseweed",
               "biomeswevegone:winter_succulent"
            ),
            () -> "",
            General::isValidRegex
         );
      doubleBlockPlantsTops = COMMON_BUILDER.comment("List of upper parts of double block plants.")
         .defineListAllowEmpty(
            "Double Block Plants Uppers",
            List.of(
               "#minecraft:tall_flowers%half:upper",
               "#regions_unexplored:shrubs%half:upper",
               "regions_unexplored:.*_tall_grass%half:upper",
               "natures_spirit:tall_.*_grass%half:upper",
               "natures_spirit:large_.*_fern%half:upper",
               "regions_unexplored:windswept_grass%half:upper",
               "regions_unexplored:cattail%half:upper",
               "farm_and_charm:wild_corn%half:upper",
               "farm_and_charm:tomato_crop",
               "flower_picking:.*%half:upper",
               "customized:rice_crop%grow_placed=true",
               "biomesoplenty:sea_oats%half=upper",
               "biomesoplenty:barley%half=upper",
               "regions_unexplored:elephant_ear%half=upper",
               "biomeswevegone:tall_.*_grass%half=upper",
               "naturalphilosophy:.*%half=upper"
            ),
            () -> "",
            General::isValidRegex
         );
      doubleBlockPlantsBottoms = COMMON_BUILDER.comment("List of lower parts of double block plants.")
         .defineListAllowEmpty(
            "Double Block Plants Lowers",
            List.of(
               "#minecraft:tall_flowers%half:lower",
               "#regions_unexplored:shrubs%half:lower",
               "regions_unexplored:.*_tall_grass%half:lower",
               "natures_spirit:tall_.*_grass%half:lower",
               "natures_spirit:large_.*_fern%half:lower",
               "regions_unexplored:windswept_grass%half:lower",
               "regions_unexplored:cattail%half:lower",
               "farm_and_charm:wild_corn%half:lower",
               "farm_and_charm:tomato_crop_body",
               "flower_picking:.*%half:lower",
               "customized:rice_crop%grow_placed=false",
               "biomesoplenty:sea_oats%half=lower",
               "biomesoplenty:barley%half=lower",
               "regions_unexplored:elephant_ear%half=lower",
               "biomeswevegone:tall_.*_grass%half=lower",
               "naturalphilosophy:.*%half=lower"
            ),
            () -> "",
            General::isValidRegex
         );
      vineLike = COMMON_BUILDER.comment("List of small vine-like plants.")
         .defineListAllowEmpty(
            "Vine Like",
            List.of("atmospheric:passion_vine", "regions_unexplored:kapok_vines.*", "natures_spirit:.*_wisteria_vines.*", "vinery:jungle_grape_bush_white"),
            () -> "",
            General::isValidRegex
         );
      leavesLike = COMMON_BUILDER.comment("List of small leaf-like plants.")
         .defineListAllowEmpty(
            "Leaf Like",
            List.of(
               "#minecraft:leaves",
               "#dynamictrees:foliage",
               "environmental:.*_hanging_wisteria_leaves",
               "environmental:cattail_stalk",
               "environmental:cattail",
               "biomesoplenty:bramble_leaves",
               "biomesoplenty:high_grass.*",
               "biomesoplenty:huge_clover_petal"
            ),
            () -> "",
            General::isValidRegex
         );
      padLike = COMMON_BUILDER.comment("List of small lily pad-like plants.")
         .defineListAllowEmpty(
            "Pad Like",
            List.of(
               "regions_unexplored:flowering_lily_pad",
               "biomesoplenty:huge_lily_pad",
               "environmental:giant_lily_pad",
               "environmental:large_lily_pad",
               "natures_spirit:helvola",
               "natures_spirit:lotus_flower",
               "natures_spirit:lotus_stem"
            ),
            () -> "",
            General::isValidRegex
         );
      customLike = COMMON_BUILDER.comment(
            "List of custom-like plants, use '@' to split template and targets, template string can get from F3+i at target blocks, such as minecraft:tall_grass[half=lower]@#minecraft:tall_flowers[half=lower]."
         )
         .defineListAllowEmpty("Custom Like", List.of("minecraft:vine@teastory:rice_plant"), () -> "", General::isValidRegex);
      COMMON_BUILDER.pop();
      CLIENT_CONFIG = COMMON_BUILDER.build();
   }
}
