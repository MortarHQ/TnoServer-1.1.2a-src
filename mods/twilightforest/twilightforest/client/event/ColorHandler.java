package twilightforest.client.event;

import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent.Block;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent.Item;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import twilightforest.block.ClimbableHollowLogBlock;
import twilightforest.components.item.PotionFlaskComponent;
import twilightforest.enums.HollowLogVariants;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFDataComponents;
import twilightforest.init.TFEntities;
import twilightforest.init.TFItems;
import twilightforest.util.ColorUtil;
import twilightforest.util.SimplexNoiseHelper;

public class ColorHandler {
   public static final Int2IntFunction CANOPY_COLORIZER = color -> 0xFF000000 | ((color & 16711422) + 4627046) / 2;
   public static final Int2IntFunction MANGROVE_COLORIZER = color -> 0xFF000000 | ((color & 16711422) + 12641940) / 2;

   protected static void registerBlockColors(Block event) {
      BlockColors blockColors = event.getBlockColors();
      event.register(
         (state, getter, pos, tintIndex) -> 0xFF000000
            | ColorUtil.hsvToRGB(
               getter == null
                  ? 0.45F
                  : SimplexNoiseHelper.rippleFractalNoise(2, 128.0F, pos != null ? pos.above(128) : new BlockPos(0, 0, 0), 0.37F, 0.67F, 1.5F),
               1.0F,
               1.0F
            ),
         new net.minecraft.world.level.block.Block[]{(net.minecraft.world.level.block.Block)TFBlocks.AURORA_BLOCK.get()}
      );
      event.register(
         (state, getter, pos, tintIndex) -> {
            int normalColor = blockColors.getColor(
               ((net.minecraft.world.level.block.Block)TFBlocks.AURORA_BLOCK.get()).defaultBlockState(), getter, pos, tintIndex
            );
            int red = normalColor >> 16 & 0xFF;
            int blue = normalColor & 0xFF;
            int green = normalColor >> 8 & 0xFF;
            float[] hsb = ColorUtil.rgbToHSV(red, green, blue);
            return 0xFF000000 | ColorUtil.hsvToRGB(hsb[0], hsb[1] * 0.5F, Math.min(hsb[2] + 0.4F, 0.9F));
         },
         new net.minecraft.world.level.block.Block[]{
            (net.minecraft.world.level.block.Block)TFBlocks.AURORA_PILLAR.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.AURORA_SLAB.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.AURORALIZED_GLASS.get()
         }
      );
      event.register(
         (state, getter, pos, tintIndex) -> blockColors.getColor(Blocks.SHORT_GRASS.defaultBlockState(), getter, pos, tintIndex),
         new net.minecraft.world.level.block.Block[]{
            (net.minecraft.world.level.block.Block)TFBlocks.SMOKER.get(), (net.minecraft.world.level.block.Block)TFBlocks.FIRE_JET.get()
         }
      );
      event.register(
         (state, getter, pos, tintIndex) -> getter != null && pos != null ? -14647248 : -9321636,
         new net.minecraft.world.level.block.Block[]{(net.minecraft.world.level.block.Block)TFBlocks.HUGE_LILY_PAD.get()}
      );
      event.register((state, getter, pos, tintIndex) -> {
         if (getter != null && pos != null) {
            int fade = pos.getX() * 16 + pos.getY() * 16 + pos.getZ() * 16;
            if ((fade & 256) != 0) {
               fade = 255 - (fade & 0xFF);
            }

            fade &= 255;
            float spring = (255 - fade) / 255.0F;
            float fall = fade / 255.0F;
            int red = (int)(spring * 106.0F + fall * 251.0F);
            int green = (int)(spring * 156.0F + fall * 108.0F);
            int blue = (int)(spring * 23.0F + fall * 27.0F);
            return 0xFF000000 | red << 16 | green << 8 | blue;
         } else {
            return -9790441;
         }
      }, new net.minecraft.world.level.block.Block[]{(net.minecraft.world.level.block.Block)TFBlocks.TIME_LEAVES.get()});
      event.register((state, getter, pos, tintIndex) -> {
         if (getter != null && pos != null) {
            int fade = pos.getX() * 27 + pos.getY() * 63 + pos.getZ() * 39;
            if ((fade & 256) != 0) {
               fade = 255 - (fade & 0xFF);
            }

            fade &= 255;
            float spring = (255 - fade) / 255.0F;
            float fall = fade / 255.0F;
            int red = (int)(spring * 108.0F + fall * 96.0F);
            int green = (int)(spring * 204.0F + fall * 107.0F);
            int blue = (int)(spring * 234.0F + fall * 121.0F);
            return 0xFF000000 | red << 16 | green << 8 | blue;
         } else {
            return -9646870;
         }
      }, new net.minecraft.world.level.block.Block[]{(net.minecraft.world.level.block.Block)TFBlocks.TRANSFORMATION_LEAVES.get()});
      event.register((state, getter, pos, tintIndex) -> {
         if (getter != null && pos != null) {
            int fade = pos.getX() * 31 + pos.getY() * 33 + pos.getZ() * 32;
            if ((fade & 256) != 0) {
               fade = 255 - (fade & 0xFF);
            }

            fade &= 255;
            float spring = (255 - fade) / 255.0F;
            float fall = fade / 255.0F;
            int red = (int)(spring * 252.0F + fall * 237.0F);
            int green = (int)(spring * 241.0F + fall * 172.0F);
            int blue = (int)(spring * 68.0F + fall * 9.0F);
            return 0xFF000000 | red << 16 | green << 8 | blue;
         } else {
            return -200380;
         }
      }, new net.minecraft.world.level.block.Block[]{(net.minecraft.world.level.block.Block)TFBlocks.MINING_LEAVES.get()});
      event.register((state, getter, pos, tintIndex) -> {
         if (getter != null && pos != null) {
            int fade = pos.getX() * 63 + pos.getY() * 63 + pos.getZ() * 63;
            if ((fade & 256) != 0) {
               fade = 255 - (fade & 0xFF);
            }

            fade &= 255;
            float spring = (255 - fade) / 255.0F;
            float fall = fade / 255.0F;
            int red = (int)(spring * 54.0F + fall * 168.0F);
            int green = (int)(spring * 76.0F + fall * 199.0F);
            int blue = (int)(spring * 3.0F + fall * 43.0F);
            return 0xFF000000 | red << 16 | green << 8 | blue;
         } else {
            return -13218813;
         }
      }, new net.minecraft.world.level.block.Block[]{(net.minecraft.world.level.block.Block)TFBlocks.SORTING_LEAVES.get()});
      event.register(
         (state, getter, pos, tintIndex) -> {
            if (getter != null && pos != null) {
               float f = SimplexNoiseHelper.rippleFractalNoise(2, 32.0F, pos, 0.4F, 1.0F, 2.0F);
               return 0xFF000000 | ColorUtil.hsvToRGB(0.1F, 1.0F - f, (f + 2.0F) / 3.0F);
            } else {
               return -1;
            }
         },
         new net.minecraft.world.level.block.Block[]{
            (net.minecraft.world.level.block.Block)TFBlocks.TOWERWOOD.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.CRACKED_TOWERWOOD.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.INFESTED_TOWERWOOD.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.MOSSY_TOWERWOOD.get()
         }
      );
      event.register(
         (state, getter, pos, tintIndex) -> getter != null && pos != null ? BiomeColors.getAverageFoliageColor(getter, pos) : FoliageColor.getDefaultColor(),
         new net.minecraft.world.level.block.Block[]{
            (net.minecraft.world.level.block.Block)TFBlocks.TWILIGHT_OAK_LEAVES.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.DARK_LEAVES.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.HARDENED_DARK_LEAVES.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.GIANT_LEAVES.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.FALLEN_LEAVES.get()
         }
      );
      event.register(
         (state, getter, pos, tintIndex) -> getter != null && pos != null
            ? CANOPY_COLORIZER.applyAsInt(BiomeColors.getAverageFoliageColor(getter, pos))
            : FoliageColor.getEvergreenColor(),
         new net.minecraft.world.level.block.Block[]{(net.minecraft.world.level.block.Block)TFBlocks.CANOPY_LEAVES.get()}
      );
      event.register(
         (state, getter, pos, tintIndex) -> getter != null && pos != null
            ? MANGROVE_COLORIZER.applyAsInt(BiomeColors.getAverageFoliageColor(getter, pos))
            : FoliageColor.getBirchColor(),
         new net.minecraft.world.level.block.Block[]{(net.minecraft.world.level.block.Block)TFBlocks.MANGROVE_LEAVES.get()}
      );
      event.register((state, getter, pos, tintIndex) -> {
         if (getter != null && pos != null) {
            int red = pos.getX() * 32 + pos.getY() * 16;
            if ((red & 256) != 0) {
               red = 255 - (red & 0xFF);
            }

            red &= 255;
            int green = pos.getY() * 32 + pos.getZ() * 16;
            if ((green & 256) != 0) {
               green = 255 - (green & 0xFF);
            }

            green ^= 255;
            int blue = pos.getX() * 16 + pos.getZ() * 32;
            if ((blue & 256) != 0) {
               blue = 255 - (blue & 0xFF);
            }

            blue &= 255;
            return 0xFF000000 | red << 16 | green << 8 | blue;
         } else {
            return FoliageColor.getDefaultColor();
         }
      }, new net.minecraft.world.level.block.Block[]{(net.minecraft.world.level.block.Block)TFBlocks.RAINBOW_OAK_LEAVES.get()});
      event.register(
         (state, getter, pos, tintIndex) -> FoliageColor.getEvergreenColor(),
         new net.minecraft.world.level.block.Block[]{
            (net.minecraft.world.level.block.Block)TFBlocks.BEANSTALK_LEAVES.get(), (net.minecraft.world.level.block.Block)TFBlocks.THORN_LEAVES.get()
         }
      );
      event.register(
         (state, getter, pos, tintIndex) -> getter != null && pos != null ? BiomeColors.getAverageGrassColor(getter, pos) : GrassColor.getDefaultColor(),
         new net.minecraft.world.level.block.Block[]{
            (net.minecraft.world.level.block.Block)TFBlocks.FIDDLEHEAD.get(), (net.minecraft.world.level.block.Block)TFBlocks.POTTED_FIDDLEHEAD.get()
         }
      );
      event.register(
         (state, getter, pos, tintIndex) -> {
            if (tintIndex == 0) {
               return -1;
            } else {
               return getter != null && pos != null ? BiomeColors.getAverageGrassColor(getter, pos) : GrassColor.getDefaultColor();
            }
         },
         new net.minecraft.world.level.block.Block[]{
            (net.minecraft.world.level.block.Block)TFBlocks.HOLLOW_OAK_LOG_HORIZONTAL.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.HOLLOW_SPRUCE_LOG_HORIZONTAL.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.HOLLOW_BIRCH_LOG_HORIZONTAL.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.HOLLOW_JUNGLE_LOG_HORIZONTAL.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.HOLLOW_ACACIA_LOG_HORIZONTAL.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.HOLLOW_DARK_OAK_LOG_HORIZONTAL.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.HOLLOW_CRIMSON_STEM_HORIZONTAL.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.HOLLOW_WARPED_STEM_HORIZONTAL.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.HOLLOW_VANGROVE_LOG_HORIZONTAL.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.HOLLOW_CHERRY_LOG_HORIZONTAL.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.HOLLOW_TWILIGHT_OAK_LOG_HORIZONTAL.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.HOLLOW_CANOPY_LOG_HORIZONTAL.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.HOLLOW_MANGROVE_LOG_HORIZONTAL.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.HOLLOW_DARK_LOG_HORIZONTAL.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.HOLLOW_TIME_LOG_HORIZONTAL.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.HOLLOW_TRANSFORMATION_LOG_HORIZONTAL.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.HOLLOW_MINING_LOG_HORIZONTAL.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.HOLLOW_SORTING_LOG_HORIZONTAL.get()
         }
      );
      event.register(
         (state, getter, pos, tintIndex) -> {
            if (state.getValue(ClimbableHollowLogBlock.VARIANT) != HollowLogVariants.Climbable.VINE || tintIndex != 1) {
               return -1;
            } else {
               return getter != null && pos != null ? BiomeColors.getAverageFoliageColor(getter, pos) : FoliageColor.getDefaultColor();
            }
         },
         new net.minecraft.world.level.block.Block[]{
            (net.minecraft.world.level.block.Block)TFBlocks.HOLLOW_OAK_LOG_CLIMBABLE.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.HOLLOW_SPRUCE_LOG_CLIMBABLE.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.HOLLOW_BIRCH_LOG_CLIMBABLE.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.HOLLOW_JUNGLE_LOG_CLIMBABLE.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.HOLLOW_ACACIA_LOG_CLIMBABLE.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.HOLLOW_DARK_OAK_LOG_CLIMBABLE.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.HOLLOW_CRIMSON_STEM_CLIMBABLE.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.HOLLOW_WARPED_STEM_CLIMBABLE.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.HOLLOW_VANGROVE_LOG_CLIMBABLE.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.HOLLOW_CHERRY_LOG_CLIMBABLE.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.HOLLOW_TWILIGHT_OAK_LOG_CLIMBABLE.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.HOLLOW_CANOPY_LOG_CLIMBABLE.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.HOLLOW_MANGROVE_LOG_CLIMBABLE.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.HOLLOW_DARK_LOG_CLIMBABLE.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.HOLLOW_TIME_LOG_CLIMBABLE.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.HOLLOW_TRANSFORMATION_LOG_CLIMBABLE.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.HOLLOW_MINING_LOG_CLIMBABLE.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.HOLLOW_SORTING_LOG_CLIMBABLE.get()
         }
      );
      event.register(
         (state, getter, pos, tintIndex) -> {
            if (tintIndex != 1) {
               return -1;
            } else {
               return getter != null && pos != null ? BiomeColors.getAverageFoliageColor(getter, pos) : FoliageColor.getDefaultColor();
            }
         },
         new net.minecraft.world.level.block.Block[]{
            (net.minecraft.world.level.block.Block)TFBlocks.TWILIGHT_PORTAL_MINIATURE_STRUCTURE.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.NAGA_COURTYARD_MINIATURE_STRUCTURE.get()
         }
      );
      event.register(
         (state, getter, pos, tintIndex) -> -65281,
         new net.minecraft.world.level.block.Block[]{
            (net.minecraft.world.level.block.Block)TFBlocks.PINK_CASTLE_RUNE_BRICK.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.PINK_CASTLE_DOOR.get()
         }
      );
      event.register(
         (state, getter, pos, tintIndex) -> -16711681,
         new net.minecraft.world.level.block.Block[]{
            (net.minecraft.world.level.block.Block)TFBlocks.BLUE_CASTLE_RUNE_BRICK.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.BLUE_CASTLE_DOOR.get()
         }
      );
      event.register(
         (state, getter, pos, tintIndex) -> -256,
         new net.minecraft.world.level.block.Block[]{
            (net.minecraft.world.level.block.Block)TFBlocks.YELLOW_CASTLE_RUNE_BRICK.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.YELLOW_CASTLE_DOOR.get()
         }
      );
      event.register(
         (state, getter, pos, tintIndex) -> -11861886,
         new net.minecraft.world.level.block.Block[]{
            (net.minecraft.world.level.block.Block)TFBlocks.VIOLET_CASTLE_RUNE_BRICK.get(),
            (net.minecraft.world.level.block.Block)TFBlocks.VIOLET_CASTLE_DOOR.get()
         }
      );
      event.register(
         (state, getter, pos, tintIndex) -> -10743692,
         new net.minecraft.world.level.block.Block[]{(net.minecraft.world.level.block.Block)TFBlocks.VIOLET_FORCE_FIELD.get()}
      );
      event.register(
         (state, getter, pos, tintIndex) -> -391810,
         new net.minecraft.world.level.block.Block[]{(net.minecraft.world.level.block.Block)TFBlocks.PINK_FORCE_FIELD.get()}
      );
      event.register(
         (state, getter, pos, tintIndex) -> -42238,
         new net.minecraft.world.level.block.Block[]{(net.minecraft.world.level.block.Block)TFBlocks.ORANGE_FORCE_FIELD.get()}
      );
      event.register(
         (state, getter, pos, tintIndex) -> -7739647,
         new net.minecraft.world.level.block.Block[]{(net.minecraft.world.level.block.Block)TFBlocks.GREEN_FORCE_FIELD.get()}
      );
      event.register(
         (state, getter, pos, tintIndex) -> -15868161,
         new net.minecraft.world.level.block.Block[]{(net.minecraft.world.level.block.Block)TFBlocks.BLUE_FORCE_FIELD.get()}
      );
   }

   protected static void registerItemColors(Item event) {
      BlockColors blockColors = event.getBlockColors();
      event.register(
         (stack, tintIndex) -> stack.getItem() instanceof BlockItem blocc
            ? blockColors.getColor(blocc.getBlock().defaultBlockState(), null, null, tintIndex)
            : -1,
         new ItemLike[]{
            (ItemLike)TFBlocks.AURORA_BLOCK.get(),
            (ItemLike)TFBlocks.AURORA_PILLAR.get(),
            (ItemLike)TFBlocks.AURORA_SLAB.get(),
            (ItemLike)TFBlocks.AURORALIZED_GLASS.get(),
            (ItemLike)TFBlocks.DARK_LEAVES.get(),
            (ItemLike)TFBlocks.GIANT_LEAVES.get(),
            (ItemLike)TFBlocks.SMOKER.get(),
            (ItemLike)TFBlocks.FIRE_JET.get(),
            (ItemLike)TFBlocks.TIME_LEAVES.get(),
            (ItemLike)TFBlocks.TRANSFORMATION_LEAVES.get(),
            (ItemLike)TFBlocks.MINING_LEAVES.get(),
            (ItemLike)TFBlocks.SORTING_LEAVES.get(),
            (ItemLike)TFBlocks.TWILIGHT_OAK_LEAVES.get(),
            (ItemLike)TFBlocks.CANOPY_LEAVES.get(),
            (ItemLike)TFBlocks.MANGROVE_LEAVES.get(),
            (ItemLike)TFBlocks.RAINBOW_OAK_LEAVES.get(),
            (ItemLike)TFBlocks.THORN_LEAVES.get(),
            (ItemLike)TFBlocks.BEANSTALK_LEAVES.get(),
            (ItemLike)TFBlocks.FALLEN_LEAVES.get(),
            (ItemLike)TFBlocks.FIDDLEHEAD.get(),
            (ItemLike)TFBlocks.POTTED_FIDDLEHEAD.get(),
            (ItemLike)TFBlocks.PINK_CASTLE_RUNE_BRICK.get(),
            (ItemLike)TFBlocks.BLUE_CASTLE_RUNE_BRICK.get(),
            (ItemLike)TFBlocks.YELLOW_CASTLE_RUNE_BRICK.get(),
            (ItemLike)TFBlocks.VIOLET_CASTLE_RUNE_BRICK.get(),
            (ItemLike)TFBlocks.YELLOW_CASTLE_DOOR.get(),
            (ItemLike)TFBlocks.BLUE_CASTLE_DOOR.get(),
            (ItemLike)TFBlocks.PINK_CASTLE_DOOR.get(),
            (ItemLike)TFBlocks.VIOLET_CASTLE_DOOR.get(),
            (ItemLike)TFBlocks.PINK_FORCE_FIELD.get(),
            (ItemLike)TFBlocks.BLUE_FORCE_FIELD.get(),
            (ItemLike)TFBlocks.GREEN_FORCE_FIELD.get(),
            (ItemLike)TFBlocks.ORANGE_FORCE_FIELD.get(),
            (ItemLike)TFBlocks.VIOLET_FORCE_FIELD.get(),
            (ItemLike)TFBlocks.HUGE_LILY_PAD.get(),
            (ItemLike)TFBlocks.TWILIGHT_PORTAL_MINIATURE_STRUCTURE.get(),
            (ItemLike)TFBlocks.NAGA_COURTYARD_MINIATURE_STRUCTURE.get()
         }
      );
      event.register(
         (stack, index) -> index != 1 ? -1 : DyedItemColor.getOrDefault(stack, -4337703),
         new ItemLike[]{
            (ItemLike)TFItems.ARCTIC_HELMET.get(),
            (ItemLike)TFItems.ARCTIC_CHESTPLATE.get(),
            (ItemLike)TFItems.ARCTIC_LEGGINGS.get(),
            (ItemLike)TFItems.ARCTIC_BOOTS.get()
         }
      );
      event.register((stack, index) -> {
         if (index > 0) {
            return -1;
         }

         PotionFlaskComponent contents = (PotionFlaskComponent)stack.getOrDefault(TFDataComponents.POTION_FLASK_CONTENTS, PotionFlaskComponent.EMPTY);
         return contents.potion().potion().isEmpty() ? -1 : contents.potion().getColor();
      }, new ItemLike[]{(ItemLike)TFItems.BRITTLE_FLASK.get(), (ItemLike)TFItems.GREATER_FLASK.get()});

      for (DeferredSpawnEggItem egg : TFEntities.SPAWN_EGGS
         .getEntries()
         .stream()
         .<Object>map(DeferredHolder::get)
         .map(DeferredSpawnEggItem.class::cast)
         .toList()) {
         event.register((stack, index) -> ARGB32.opaque(egg.getColor(index)), new ItemLike[]{egg});
      }
   }
}
