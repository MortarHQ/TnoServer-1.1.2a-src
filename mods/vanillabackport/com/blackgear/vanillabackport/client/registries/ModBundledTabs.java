package com.blackgear.vanillabackport.client.registries;

import com.blackgear.vanillabackport.client.api.tabs.BundledTabs;
import com.blackgear.vanillabackport.common.registries.ModBlocks;
import com.blackgear.vanillabackport.common.registries.ModItems;
import com.blackgear.vanillabackport.common.registries.ModPaintingVariants;
import com.blackgear.vanillabackport.core.FeatureFlag;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.ItemLike;

public class ModBundledTabs {
   private static final Map<BundledTabs, FeatureFlag> FILTERS = new LinkedHashMap<>();
   public static final BundledTabs BUNDLES_OF_BRAVERY = register(
      BundledTabs.builder()
         .title(Component.translatable("bundled_tab.bundles_of_bravery.title"))
         .icon(new ItemStack(Items.BUNDLE))
         .displayItems((provider, output) -> {
            output.accept(Items.BUNDLE);
            output.accept((ItemLike)ModItems.WHITE_BUNDLE.get());
            output.accept((ItemLike)ModItems.LIGHT_GRAY_BUNDLE.get());
            output.accept((ItemLike)ModItems.GRAY_BUNDLE.get());
            output.accept((ItemLike)ModItems.BLACK_BUNDLE.get());
            output.accept((ItemLike)ModItems.BROWN_BUNDLE.get());
            output.accept((ItemLike)ModItems.RED_BUNDLE.get());
            output.accept((ItemLike)ModItems.ORANGE_BUNDLE.get());
            output.accept((ItemLike)ModItems.YELLOW_BUNDLE.get());
            output.accept((ItemLike)ModItems.LIME_BUNDLE.get());
            output.accept((ItemLike)ModItems.GREEN_BUNDLE.get());
            output.accept((ItemLike)ModItems.CYAN_BUNDLE.get());
            output.accept((ItemLike)ModItems.LIGHT_BLUE_BUNDLE.get());
            output.accept((ItemLike)ModItems.BLUE_BUNDLE.get());
            output.accept((ItemLike)ModItems.PURPLE_BUNDLE.get());
            output.accept((ItemLike)ModItems.MAGENTA_BUNDLE.get());
            output.accept((ItemLike)ModItems.PINK_BUNDLE.get());
         })
         .build()
   );
   public static final BundledTabs THE_GARDEN_AWAKENS = register(
      BundledTabs.builder()
         .title(Component.translatable("bundled_tab.the_garden_awakens.title"))
         .icon(new ItemStack((ItemLike)ModBlocks.OPEN_EYEBLOSSOM.get()))
         .displayItems((provider, output) -> {
            output.accept((ItemLike)ModBlocks.CREAKING_HEART.get());
            output.accept((ItemLike)ModBlocks.PALE_OAK_LOG.get());
            output.accept((ItemLike)ModBlocks.STRIPPED_PALE_OAK_LOG.get());
            output.accept((ItemLike)ModBlocks.PALE_OAK_WOOD.get());
            output.accept((ItemLike)ModBlocks.STRIPPED_PALE_OAK_WOOD.get());
            output.accept((ItemLike)ModBlocks.PALE_OAK_PLANKS.get());
            output.accept((ItemLike)ModBlocks.PALE_OAK_STAIRS.get());
            output.accept((ItemLike)ModBlocks.PALE_OAK_SLAB.get());
            output.accept((ItemLike)((Supplier)ModBlocks.PALE_OAK_SIGN.getFirst()).get());
            output.accept((ItemLike)((Supplier)ModBlocks.PALE_OAK_HANGING_SIGN.getFirst()).get());
            output.accept((ItemLike)ModBlocks.PALE_OAK_BUTTON.get());
            output.accept((ItemLike)ModBlocks.PALE_OAK_PRESSURE_PLATE.get());
            output.accept((ItemLike)ModBlocks.PALE_OAK_DOOR.get());
            output.accept((ItemLike)ModBlocks.PALE_OAK_FENCE.get());
            output.accept((ItemLike)ModBlocks.PALE_OAK_FENCE_GATE.get());
            output.accept((ItemLike)ModBlocks.PALE_OAK_TRAPDOOR.get());
            output.accept((ItemLike)ModItems.PALE_OAK_BOAT.get());
            output.accept((ItemLike)ModItems.PALE_OAK_CHEST_BOAT.get());
            output.accept((ItemLike)ModBlocks.PALE_HANGING_MOSS.get());
            output.accept((ItemLike)ModBlocks.PALE_MOSS_BLOCK.get());
            output.accept((ItemLike)ModBlocks.PALE_MOSS_CARPET.get());
            output.accept((ItemLike)ModBlocks.PALE_OAK_LEAVES.get());
            output.accept((ItemLike)ModBlocks.PALE_OAK_SAPLING.get());
            output.accept((ItemLike)ModItems.CREAKING_SPAWN_EGG.get());
            output.accept((ItemLike)ModBlocks.CLOSED_EYEBLOSSOM.get());
            output.accept((ItemLike)ModBlocks.OPEN_EYEBLOSSOM.get());
            output.accept((ItemLike)ModBlocks.RESIN_BLOCK.get());
            output.accept((ItemLike)ModBlocks.RESIN_BRICKS.get());
            output.accept((ItemLike)ModBlocks.CHISELED_RESIN_BRICKS.get());
            output.accept((ItemLike)ModBlocks.RESIN_BRICK_STAIRS.get());
            output.accept((ItemLike)ModBlocks.RESIN_BRICK_SLAB.get());
            output.accept((ItemLike)ModBlocks.RESIN_BRICK_WALL.get());
            output.accept((ItemLike)ModBlocks.RESIN_CLUMP.get());
            output.accept((ItemLike)ModItems.RESIN_BRICK.get());
         })
         .build()
   );
   public static final BundledTabs SPRING_TO_LIFE = register(
      BundledTabs.builder()
         .title(Component.translatable("bundled_tab.spring_to_life.title"))
         .icon(new ItemStack((ItemLike)ModBlocks.WILDFLOWERS.get()))
         .displayItems((provider, output) -> {
            output.accept((ItemLike)ModBlocks.BUSH.get());
            output.accept((ItemLike)ModBlocks.FIREFLY_BUSH.get());
            output.accept((ItemLike)ModBlocks.WILDFLOWERS.get());
            output.accept((ItemLike)ModBlocks.LEAF_LITTER.get());
            output.accept((ItemLike)ModBlocks.CACTUS_FLOWER.get());
            output.accept((ItemLike)ModBlocks.SHORT_DRY_GRASS.get());
            output.accept((ItemLike)ModBlocks.TALL_DRY_GRASS.get());
            output.accept((ItemLike)ModItems.BROWN_EGG.get());
            output.accept((ItemLike)ModItems.BLUE_EGG.get());
         })
         .build()
   );
   public static final BundledTabs CHASE_THE_SKIES = register(
      BundledTabs.builder()
         .title(Component.translatable("bundled_tab.chase_the_skies.title"))
         .icon(new ItemStack((ItemLike)ModItems.BLUE_HARNESS.get()))
         .displayItems((provider, output) -> {
            output.accept((ItemLike)ModBlocks.DRIED_GHAST.get());
            output.accept((ItemLike)ModItems.HAPPY_GHAST_SPAWN_EGG.get());
            output.accept((ItemLike)ModItems.WHITE_HARNESS.get());
            output.accept((ItemLike)ModItems.ORANGE_HARNESS.get());
            output.accept((ItemLike)ModItems.MAGENTA_HARNESS.get());
            output.accept((ItemLike)ModItems.LIGHT_BLUE_HARNESS.get());
            output.accept((ItemLike)ModItems.YELLOW_HARNESS.get());
            output.accept((ItemLike)ModItems.LIME_HARNESS.get());
            output.accept((ItemLike)ModItems.PINK_HARNESS.get());
            output.accept((ItemLike)ModItems.GRAY_HARNESS.get());
            output.accept((ItemLike)ModItems.LIGHT_GRAY_HARNESS.get());
            output.accept((ItemLike)ModItems.CYAN_HARNESS.get());
            output.accept((ItemLike)ModItems.PURPLE_HARNESS.get());
            output.accept((ItemLike)ModItems.BLUE_HARNESS.get());
            output.accept((ItemLike)ModItems.BROWN_HARNESS.get());
            output.accept((ItemLike)ModItems.GREEN_HARNESS.get());
            output.accept((ItemLike)ModItems.RED_HARNESS.get());
            output.accept((ItemLike)ModItems.BLACK_HARNESS.get());
            output.accept((ItemLike)ModItems.MUSIC_DISC_TEARS.get());
         })
         .build()
   );
   public static final BundledTabs HOT_AS_LAVA = register(
      BundledTabs.builder()
         .title(Component.translatable("bundled_tab.hot_as_lava.title"))
         .icon(new ItemStack((ItemLike)ModItems.MUSIC_DISC_LAVA_CHICKEN.get()))
         .displayItems(
            (provider, output) -> {
               output.accept((ItemLike)ModItems.MUSIC_DISC_LAVA_CHICKEN.get());
               provider.lookup(Registries.PAINTING_VARIANT)
                  .ifPresent(
                     variants -> variants.listElements()
                        .filter(holder -> holder.is(ModPaintingVariants.DENNIS))
                        .forEach(
                           reference -> {
                              RegistryOps<Tag> ops = provider.createSerializationContext(NbtOps.INSTANCE);
                              CustomData customData = ((CustomData)CustomData.EMPTY.update(ops, Painting.VARIANT_MAP_CODEC, reference).getOrThrow())
                                 .update(tag -> tag.putString("id", "minecraft:painting"));
                              ItemStack stack = new ItemStack(Items.PAINTING);
                              stack.set(DataComponents.ENTITY_DATA, customData);
                              output.accept(stack);
                           }
                        )
                  );
            }
         )
         .build()
   );
   public static final BundledTabs CHAOS_CUBED = register(
      BundledTabs.builder()
         .title(Component.translatable("bundled_tab.chaos_cubed.title"))
         .icon(new ItemStack((ItemLike)ModItems.SULFUR_CUBE_BUCKET.get()))
         .displayItems((provider, output) -> {
            output.accept((ItemLike)ModBlocks.CINNABAR.get());
            output.accept((ItemLike)ModBlocks.CINNABAR_SLAB.get());
            output.accept((ItemLike)ModBlocks.CINNABAR_STAIRS.get());
            output.accept((ItemLike)ModBlocks.CINNABAR_WALL.get());
            output.accept((ItemLike)ModBlocks.POLISHED_CINNABAR.get());
            output.accept((ItemLike)ModBlocks.POLISHED_CINNABAR_SLAB.get());
            output.accept((ItemLike)ModBlocks.POLISHED_CINNABAR_STAIRS.get());
            output.accept((ItemLike)ModBlocks.POLISHED_CINNABAR_WALL.get());
            output.accept((ItemLike)ModBlocks.CINNABAR_BRICKS.get());
            output.accept((ItemLike)ModBlocks.CINNABAR_BRICK_SLAB.get());
            output.accept((ItemLike)ModBlocks.CINNABAR_BRICK_STAIRS.get());
            output.accept((ItemLike)ModBlocks.CINNABAR_BRICK_WALL.get());
            output.accept((ItemLike)ModBlocks.CHISELED_CINNABAR.get());
            output.accept((ItemLike)ModBlocks.POTENT_SULFUR.get());
            output.accept((ItemLike)ModBlocks.SULFUR.get());
            output.accept((ItemLike)ModBlocks.SULFUR_SLAB.get());
            output.accept((ItemLike)ModBlocks.SULFUR_STAIRS.get());
            output.accept((ItemLike)ModBlocks.SULFUR_WALL.get());
            output.accept((ItemLike)ModBlocks.POLISHED_SULFUR.get());
            output.accept((ItemLike)ModBlocks.POLISHED_SULFUR_SLAB.get());
            output.accept((ItemLike)ModBlocks.POLISHED_SULFUR_STAIRS.get());
            output.accept((ItemLike)ModBlocks.POLISHED_SULFUR_WALL.get());
            output.accept((ItemLike)ModBlocks.SULFUR_BRICKS.get());
            output.accept((ItemLike)ModBlocks.SULFUR_BRICK_SLAB.get());
            output.accept((ItemLike)ModBlocks.SULFUR_BRICK_STAIRS.get());
            output.accept((ItemLike)ModBlocks.SULFUR_BRICK_WALL.get());
            output.accept((ItemLike)ModBlocks.CHISELED_SULFUR.get());
            output.accept((ItemLike)ModBlocks.SULFUR_SPIKE.get());
            output.accept((ItemLike)ModItems.SULFUR_CUBE_BUCKET.get());
            output.accept((ItemLike)ModItems.SULFUR_CUBE_SPAWN_EGG.get());
            output.accept((ItemLike)ModItems.MUSIC_DISC_BOUNCE.get());
         })
         .build()
   );

   public static BundledTabs register(BundledTabs builder) {
      FILTERS.put(builder, FeatureFlag.DEFAULT);
      return builder;
   }

   public static List<BundledTabs> getTabs() {
      return FILTERS.entrySet().stream().filter(entry -> entry.getValue().isEnabled()).map(Entry::getKey).toList();
   }
}
