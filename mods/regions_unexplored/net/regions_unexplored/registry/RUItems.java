package net.regions_unexplored.registry;

import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.regions_unexplored.RegionsUnexplored;
import net.regions_unexplored.item.RUItemUtils;
import net.regions_unexplored.item.type.IridescentRingItem;

public interface RUItems {
   Supplier<Item> SALMONBERRY = RUItemUtils.register(
      "salmonberry", p -> new BlockItem(RUBlocks.SALMONBERRY_BUSH.get(), p.food(RUItemUtils.food(3, 0.3F, t -> t)))
   );
   Supplier<Item> DUSKMELON_SLICE = RUItemUtils.register(
      "duskmelon_slice",
      p -> new BlockItem(RUBlocks.DUSKMELON.get(), p.food(RUItemUtils.food(5, 1.1F, t -> t.effect(new MobEffectInstance(MobEffects.BLINDNESS, 240), 1.0F))))
   );
   Supplier<Item> HANGING_EARLIGHT_FRUIT = RUItemUtils.register(
      "hanging_earlight_fruit",
      p -> new BlockItem(
         RUBlocks.HANGING_EARLIGHT.get(), p.food(RUItemUtils.food(6, 0.4F, t -> t.effect(new MobEffectInstance(MobEffects.GLOWING, 200), 0.1F)))
      )
   );
   Supplier<Item> MEADOW_SAGE = RUItemUtils.register(
      "meadow_sage",
      p -> new BlockItem(RUBlocks.MEADOW_SAGE.get(), p.food(RUItemUtils.food(2, 0.15F, t -> t.effect(new MobEffectInstance(MobEffects.HEAL, 20), 0.5F))))
   );
   Supplier<Item> IRIDESCENT_RING = RUItemUtils.register("iridescent_ring", IridescentRingItem::new);

   static void applyAliases(BiConsumer<ResourceLocation, ResourceLocation> consumer) {
      consumer.accept(RegionsUnexplored.id("medium_grass"), RegionsUnexplored.id("grass_sprouts"));
      consumer.accept(RegionsUnexplored.id("stone_bud"), RegionsUnexplored.id("grass_sprouts"));
      consumer.accept(RegionsUnexplored.id("cactus_flower"), RegionsUnexplored.id("saguaro_cactus_flower"));
      consumer.accept(RegionsUnexplored.id("maple_leaf_pile"), RegionsUnexplored.id("maple_leaf_litter"));
      consumer.accept(RegionsUnexplored.id("red_maple_leaf_pile"), RegionsUnexplored.id("red_maple_leaf_litter"));
      consumer.accept(RegionsUnexplored.id("orange_maple_leaf_pile"), RegionsUnexplored.id("orange_maple_leaf_litter"));
      consumer.accept(RegionsUnexplored.id("silver_birch_leaf_pile"), RegionsUnexplored.id("silver_birch_leaf_litter"));
      consumer.accept(RegionsUnexplored.id("enchanted_birch_leaf_pile"), RegionsUnexplored.id("enchanted_birch_leaf_litter"));
      consumer.accept(RegionsUnexplored.id("mauve_branch"), RegionsUnexplored.id("wisteria_branch"));
      consumer.accept(RegionsUnexplored.id("mauve_shrub"), RegionsUnexplored.id("lavender_wisteria_shrub"));
      consumer.accept(RegionsUnexplored.id("mauve_leaves"), RegionsUnexplored.id("lavender_wisteria_leaves"));
      consumer.accept(RegionsUnexplored.id("mauve_sapling"), RegionsUnexplored.id("lavender_wisteria_sapling"));
      consumer.accept(RegionsUnexplored.id("enchanted_birch_shrub"), RegionsUnexplored.id("sky_wisteria_shrub"));
      consumer.accept(RegionsUnexplored.id("enchanted_birch_leaves"), RegionsUnexplored.id("sky_wisteria_leaves"));
      consumer.accept(RegionsUnexplored.id("enchanted_birch_sapling"), RegionsUnexplored.id("sky_wisteria_sapling"));
      consumer.accept(RegionsUnexplored.id("mauve_log"), RegionsUnexplored.id("wisteria_log"));
      consumer.accept(RegionsUnexplored.id("mauve_wood"), RegionsUnexplored.id("wisteria_wood"));
      consumer.accept(RegionsUnexplored.id("stripped_mauve_log"), RegionsUnexplored.id("stripped_wisteria_log"));
      consumer.accept(RegionsUnexplored.id("stripped_mauve_wood"), RegionsUnexplored.id("stripped_wisteria_wood"));
      consumer.accept(RegionsUnexplored.id("mauve_planks"), RegionsUnexplored.id("wisteria_planks"));
      consumer.accept(RegionsUnexplored.id("mauve_stairs"), RegionsUnexplored.id("wisteria_stairs"));
      consumer.accept(RegionsUnexplored.id("mauve_slab"), RegionsUnexplored.id("wisteria_slab"));
      consumer.accept(RegionsUnexplored.id("mauve_fence"), RegionsUnexplored.id("wisteria_fence"));
      consumer.accept(RegionsUnexplored.id("mauve_fence_gate"), RegionsUnexplored.id("wisteria_fence_gate"));
      consumer.accept(RegionsUnexplored.id("mauve_door"), RegionsUnexplored.id("wisteria_door"));
      consumer.accept(RegionsUnexplored.id("mauve_trapdoor"), RegionsUnexplored.id("wisteria_trapdoor"));
      consumer.accept(RegionsUnexplored.id("mauve_pressure_plate"), RegionsUnexplored.id("wisteria_pressure_plate"));
      consumer.accept(RegionsUnexplored.id("mauve_button"), RegionsUnexplored.id("wisteria_button"));
      consumer.accept(RegionsUnexplored.id("mauve_sign"), RegionsUnexplored.id("wisteria_sign"));
      consumer.accept(RegionsUnexplored.id("mauve_hanging_sign"), RegionsUnexplored.id("wisteria_hanging_sign"));
      consumer.accept(RegionsUnexplored.id("mauve_boat"), RegionsUnexplored.id("wisteria_boat"));
      consumer.accept(RegionsUnexplored.id("mauve_chest_boat"), RegionsUnexplored.id("wisteria_chest_boat"));
   }

   static void init() {
   }
}
