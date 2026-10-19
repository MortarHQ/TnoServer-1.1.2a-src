package com.blackgear.vanillabackport.client;

import com.blackgear.platform.common.CreativeTabs;
import com.blackgear.platform.common.CreativeTabs.Modifier;
import com.blackgear.vanillabackport.common.registries.ModBlocks;
import com.blackgear.vanillabackport.common.registries.ModItems;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public interface CreativeTabIntegration {
   Modifier BUILDING_BLOCKS = (flag, output, operator) -> {
      output.addAllAfter(
         Items.CHERRY_BUTTON,
         List.of(
            (ItemLike)ModBlocks.PALE_OAK_LOG.get(),
            (ItemLike)ModBlocks.PALE_OAK_WOOD.get(),
            (ItemLike)ModBlocks.STRIPPED_PALE_OAK_LOG.get(),
            (ItemLike)ModBlocks.STRIPPED_PALE_OAK_WOOD.get(),
            (ItemLike)ModBlocks.PALE_OAK_PLANKS.get(),
            (ItemLike)ModBlocks.PALE_OAK_STAIRS.get(),
            (ItemLike)ModBlocks.PALE_OAK_SLAB.get(),
            (ItemLike)ModBlocks.PALE_OAK_FENCE.get(),
            (ItemLike)ModBlocks.PALE_OAK_FENCE_GATE.get(),
            (ItemLike)ModBlocks.PALE_OAK_DOOR.get(),
            (ItemLike)ModBlocks.PALE_OAK_TRAPDOOR.get(),
            (ItemLike)ModBlocks.PALE_OAK_PRESSURE_PLATE.get(),
            (ItemLike)ModBlocks.PALE_OAK_BUTTON.get()
         )
      );
      output.addAllAfter(
         Items.MUD_BRICK_WALL,
         List.of(
            (ItemLike)ModBlocks.RESIN_BRICKS.get(),
            (ItemLike)ModBlocks.RESIN_BRICK_STAIRS.get(),
            (ItemLike)ModBlocks.RESIN_BRICK_SLAB.get(),
            (ItemLike)ModBlocks.RESIN_BRICK_WALL.get(),
            (ItemLike)ModBlocks.CHISELED_RESIN_BRICKS.get()
         )
      );
      output.addAllAfter(
         Items.CUT_RED_SANDSTONE_SLAB,
         List.of(
            (ItemLike)ModBlocks.CINNABAR.get(),
            (ItemLike)ModBlocks.CINNABAR_STAIRS.get(),
            (ItemLike)ModBlocks.CINNABAR_SLAB.get(),
            (ItemLike)ModBlocks.CINNABAR_WALL.get(),
            (ItemLike)ModBlocks.CHISELED_CINNABAR.get(),
            (ItemLike)ModBlocks.POLISHED_CINNABAR.get(),
            (ItemLike)ModBlocks.POLISHED_CINNABAR_STAIRS.get(),
            (ItemLike)ModBlocks.POLISHED_CINNABAR_SLAB.get(),
            (ItemLike)ModBlocks.POLISHED_CINNABAR_WALL.get(),
            (ItemLike)ModBlocks.CINNABAR_BRICKS.get(),
            (ItemLike)ModBlocks.CINNABAR_BRICK_STAIRS.get(),
            (ItemLike)ModBlocks.CINNABAR_BRICK_SLAB.get(),
            (ItemLike)ModBlocks.CINNABAR_BRICK_WALL.get(),
            (ItemLike)ModBlocks.SULFUR.get(),
            (ItemLike)ModBlocks.SULFUR_STAIRS.get(),
            (ItemLike)ModBlocks.SULFUR_SLAB.get(),
            (ItemLike)ModBlocks.SULFUR_WALL.get(),
            (ItemLike)ModBlocks.CHISELED_SULFUR.get(),
            (ItemLike)ModBlocks.POLISHED_SULFUR.get(),
            (ItemLike)ModBlocks.POLISHED_SULFUR_STAIRS.get(),
            (ItemLike)ModBlocks.POLISHED_SULFUR_SLAB.get(),
            (ItemLike)ModBlocks.POLISHED_SULFUR_WALL.get(),
            (ItemLike)ModBlocks.SULFUR_BRICKS.get(),
            (ItemLike)ModBlocks.SULFUR_BRICK_STAIRS.get(),
            (ItemLike)ModBlocks.SULFUR_BRICK_SLAB.get(),
            (ItemLike)ModBlocks.SULFUR_BRICK_WALL.get()
         )
      );
   };
   Modifier COLORED_BLOCKS = (flag, output, operator) -> {};
   Modifier NATURAL_BLOCKS = (flag, output, operator) -> {
      output.addAllAfter(
         Items.MOSS_CARPET,
         List.of((ItemLike)ModBlocks.PALE_MOSS_BLOCK.get(), (ItemLike)ModBlocks.PALE_MOSS_CARPET.get(), (ItemLike)ModBlocks.PALE_HANGING_MOSS.get())
      );
      output.addAllAfter(
         Items.PRISMARINE,
         List.of(
            (ItemLike)ModBlocks.CINNABAR.get(),
            (ItemLike)ModBlocks.SULFUR.get(),
            (ItemLike)ModBlocks.SULFUR_SPIKE.get(),
            (ItemLike)ModBlocks.POTENT_SULFUR.get()
         )
      );
      output.addAfter(Items.CHERRY_LOG, (ItemLike)ModBlocks.PALE_OAK_LOG.get());
      output.addAfter(Items.CHERRY_LEAVES, (ItemLike)ModBlocks.PALE_OAK_LEAVES.get());
      output.addAfter(Items.CHERRY_SAPLING, (ItemLike)ModBlocks.PALE_OAK_SAPLING.get());
      output.addAllAfter(Items.FERN, List.of((ItemLike)ModBlocks.SHORT_DRY_GRASS.get(), (ItemLike)ModBlocks.BUSH.get()));
      output.addAllAfter(
         Items.TORCHFLOWER,
         List.of((ItemLike)ModBlocks.CACTUS_FLOWER.get(), (ItemLike)ModBlocks.CLOSED_EYEBLOSSOM.get(), (ItemLike)ModBlocks.OPEN_EYEBLOSSOM.get())
      );
      output.addAllAfter(Items.PINK_PETALS, List.of((ItemLike)ModBlocks.WILDFLOWERS.get(), (ItemLike)ModBlocks.LEAF_LITTER.get()));
      output.addAfter(Items.SPORE_BLOSSOM, (ItemLike)ModBlocks.FIREFLY_BUSH.get());
      output.addAfter(Items.LARGE_FERN, (ItemLike)ModBlocks.TALL_DRY_GRASS.get());
      output.addAfter(Items.SNIFFER_EGG, (ItemLike)ModBlocks.DRIED_GHAST.get());
      output.addAfter(Items.HONEY_BLOCK, (ItemLike)ModBlocks.RESIN_BLOCK.get());
   };
   Modifier FUNCTIONAL_BLOCKS = (flag, output, operator) -> output.addAllAfter(
      Items.CHERRY_HANGING_SIGN,
      List.of((ItemLike)((Supplier)ModBlocks.PALE_OAK_SIGN.getFirst()).get(), (ItemLike)((Supplier)ModBlocks.PALE_OAK_HANGING_SIGN.getFirst()).get())
   );
   Modifier REDSTONE_BLOCKS = (flag, output, operator) -> {};
   Modifier TOOLS_AND_UTILITIES = (flag, output, operator) -> {
      output.addAfter(Items.TADPOLE_BUCKET, (ItemLike)ModItems.SULFUR_CUBE_BUCKET.get());
      if (flag.contains(FeatureFlags.BUNDLE)) {
         output.addAllAfter(
            Items.BUNDLE,
            List.of(
               (ItemLike)ModItems.WHITE_BUNDLE.get(),
               (ItemLike)ModItems.LIGHT_GRAY_BUNDLE.get(),
               (ItemLike)ModItems.GRAY_BUNDLE.get(),
               (ItemLike)ModItems.BLACK_BUNDLE.get(),
               (ItemLike)ModItems.BROWN_BUNDLE.get(),
               (ItemLike)ModItems.RED_BUNDLE.get(),
               (ItemLike)ModItems.ORANGE_BUNDLE.get(),
               (ItemLike)ModItems.YELLOW_BUNDLE.get(),
               (ItemLike)ModItems.LIME_BUNDLE.get(),
               (ItemLike)ModItems.GREEN_BUNDLE.get(),
               (ItemLike)ModItems.CYAN_BUNDLE.get(),
               (ItemLike)ModItems.LIGHT_BLUE_BUNDLE.get(),
               (ItemLike)ModItems.BLUE_BUNDLE.get(),
               (ItemLike)ModItems.PURPLE_BUNDLE.get(),
               (ItemLike)ModItems.MAGENTA_BUNDLE.get(),
               (ItemLike)ModItems.PINK_BUNDLE.get()
            )
         );
      }

      output.addAllAfter(
         Items.SADDLE,
         List.of(
            (ItemLike)ModItems.WHITE_HARNESS.get(),
            (ItemLike)ModItems.LIGHT_GRAY_HARNESS.get(),
            (ItemLike)ModItems.GRAY_HARNESS.get(),
            (ItemLike)ModItems.BLACK_HARNESS.get(),
            (ItemLike)ModItems.BROWN_HARNESS.get(),
            (ItemLike)ModItems.RED_HARNESS.get(),
            (ItemLike)ModItems.ORANGE_HARNESS.get(),
            (ItemLike)ModItems.YELLOW_HARNESS.get(),
            (ItemLike)ModItems.LIME_HARNESS.get(),
            (ItemLike)ModItems.GREEN_HARNESS.get(),
            (ItemLike)ModItems.CYAN_HARNESS.get(),
            (ItemLike)ModItems.LIGHT_BLUE_HARNESS.get(),
            (ItemLike)ModItems.BLUE_HARNESS.get(),
            (ItemLike)ModItems.PURPLE_HARNESS.get(),
            (ItemLike)ModItems.MAGENTA_HARNESS.get(),
            (ItemLike)ModItems.PINK_HARNESS.get()
         )
      );
      output.addAllAfter(Items.CHERRY_CHEST_BOAT, List.of((ItemLike)ModItems.PALE_OAK_BOAT.get(), (ItemLike)ModItems.PALE_OAK_CHEST_BOAT.get()));
      output.addAllAfter(
         Items.MUSIC_DISC_RELIC,
         List.of((ItemLike)ModItems.MUSIC_DISC_TEARS.get(), (ItemLike)ModItems.MUSIC_DISC_LAVA_CHICKEN.get(), (ItemLike)ModItems.MUSIC_DISC_BOUNCE.get())
      );
   };
   Modifier COMBAT = (flag, output, operator) -> output.addAllAfter(Items.EGG, List.of((ItemLike)ModItems.BROWN_EGG.get(), (ItemLike)ModItems.BLUE_EGG.get()));
   Modifier FOOD_AND_DRINKS = (flag, output, operator) -> {};
   Modifier INGREDIENTS = (flag, output, operator) -> {
      output.addAllAfter(Items.EGG, List.of((ItemLike)ModItems.BROWN_EGG.get(), (ItemLike)ModItems.BLUE_EGG.get()));
      output.addAfter(Items.HONEYCOMB, (ItemLike)ModBlocks.RESIN_CLUMP.get());
      output.addAfter(Items.NETHER_BRICK, (ItemLike)ModItems.RESIN_BRICK.get());
   };
   Modifier SPAWN_EGGS = (flag, output, operator) -> {
      output.addAfter(Items.SPAWNER, (ItemLike)ModBlocks.CREAKING_HEART.get());
      output.addAfter(Items.COW_SPAWN_EGG, (ItemLike)ModItems.CREAKING_SPAWN_EGG.get());
      output.addAfter(Items.GUARDIAN_SPAWN_EGG, (ItemLike)ModItems.HAPPY_GHAST_SPAWN_EGG.get());
      output.addAfter(Items.SNIFFER_SPAWN_EGG, (ItemLike)ModItems.SULFUR_CUBE_SPAWN_EGG.get());
   };

   static void bootstrap() {
      CreativeTabs.modify(CreativeModeTabs.BUILDING_BLOCKS, BUILDING_BLOCKS);
      CreativeTabs.modify(CreativeModeTabs.COLORED_BLOCKS, COLORED_BLOCKS);
      CreativeTabs.modify(CreativeModeTabs.NATURAL_BLOCKS, NATURAL_BLOCKS);
      CreativeTabs.modify(CreativeModeTabs.FUNCTIONAL_BLOCKS, FUNCTIONAL_BLOCKS);
      CreativeTabs.modify(CreativeModeTabs.REDSTONE_BLOCKS, REDSTONE_BLOCKS);
      CreativeTabs.modify(CreativeModeTabs.TOOLS_AND_UTILITIES, TOOLS_AND_UTILITIES);
      CreativeTabs.modify(CreativeModeTabs.COMBAT, COMBAT);
      CreativeTabs.modify(CreativeModeTabs.FOOD_AND_DRINKS, FOOD_AND_DRINKS);
      CreativeTabs.modify(CreativeModeTabs.INGREDIENTS, INGREDIENTS);
      CreativeTabs.modify(CreativeModeTabs.SPAWN_EGGS, SPAWN_EGGS);
   }
}
