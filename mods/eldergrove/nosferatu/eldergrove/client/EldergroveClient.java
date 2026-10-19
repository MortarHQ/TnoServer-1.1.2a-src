package nosferatu.eldergrove.client;

import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent.Block;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent.Item;
import nosferatu.eldergrove.EldergroveBlocks;
import nosferatu.eldergrove.EldergroveItems;

@EventBusSubscriber(modid = "eldergrove", bus = Bus.MOD, value = Dist.CLIENT)
public final class EldergroveClient {
   private static final int MAGICAL_FOREST_EDGE_COLOR = 7649374;
   private static final int MAGICAL_FOREST_COLOR = 5626232;
   private static final int MAGICAL_FOREST_DEEP_COLOR = 4442525;
   private static final int MAGICAL_FOREST_LEAF_COLOR = 6477497;

   private EldergroveClient() {
   }

   @SubscribeEvent
   public static void registerBlockColors(Block event) {
      event.register(
         EldergroveClient::eldergroveGrassColor,
         new net.minecraft.world.level.block.Block[]{
            (net.minecraft.world.level.block.Block)EldergroveBlocks.ELDERGROVE_GRASS_FAINT.get(),
            (net.minecraft.world.level.block.Block)EldergroveBlocks.ELDERGROVE_GRASS.get(),
            (net.minecraft.world.level.block.Block)EldergroveBlocks.ELDERGROVE_GRASS_DEEP.get()
         }
      );
      event.register(
         EldergroveClient::plantColor, new net.minecraft.world.level.block.Block[]{Blocks.SHORT_GRASS, Blocks.TALL_GRASS, Blocks.FERN, Blocks.LARGE_FERN}
      );
      event.register(
         (state, level, pos, tintIndex) -> 6477497,
         new net.minecraft.world.level.block.Block[]{(net.minecraft.world.level.block.Block)EldergroveBlocks.ELDERWOOD_LEAVES.get()}
      );
   }

   @SubscribeEvent
   public static void registerItemColors(Item event) {
      event.register(
         (stack, tintIndex) -> 5626232,
         new ItemLike[]{
            (ItemLike)EldergroveItems.ELDERGROVE_GRASS_FAINT.get(),
            (ItemLike)EldergroveItems.ELDERGROVE_GRASS.get(),
            (ItemLike)EldergroveItems.ELDERGROVE_GRASS_DEEP.get()
         }
      );
      event.register((stack, tintIndex) -> 6477497, new ItemLike[]{(ItemLike)EldergroveItems.ELDERWOOD_LEAVES.get()});
   }

   private static int eldergroveGrassColor(BlockState state, BlockAndTintGetter level, BlockPos pos, int tintIndex) {
      int target = magicalGrassTarget(state, pos);
      if (level != null && pos != null) {
         int vanilla = BiomeColors.getAverageGrassColor(level, pos);
         double amount = state.is((net.minecraft.world.level.block.Block)EldergroveBlocks.ELDERGROVE_GRASS_FAINT.get())
            ? 0.45
            : (state.is((net.minecraft.world.level.block.Block)EldergroveBlocks.ELDERGROVE_GRASS.get()) ? 0.72 : 0.92);
         return blend(vanilla, target, amount);
      } else {
         return target;
      }
   }

   private static int plantColor(BlockState state, BlockAndTintGetter level, BlockPos pos, int tintIndex) {
      if (level != null && pos != null) {
         BlockState below = getBelowSafely(level, pos);
         if (below == null) {
            return BiomeColors.getAverageGrassColor(level, pos);
         } else if (below.is((net.minecraft.world.level.block.Block)EldergroveBlocks.ELDERGROVE_GRASS_FAINT.get())) {
            return blend(BiomeColors.getAverageGrassColor(level, pos), 7649374, 0.35);
         } else if (below.is((net.minecraft.world.level.block.Block)EldergroveBlocks.ELDERGROVE_GRASS.get())) {
            return blend(BiomeColors.getAverageGrassColor(level, pos), 5626232, 0.6);
         } else {
            return below.is((net.minecraft.world.level.block.Block)EldergroveBlocks.ELDERGROVE_GRASS_DEEP.get())
               ? blend(BiomeColors.getAverageGrassColor(level, pos), 4442525, 0.8)
               : BiomeColors.getAverageGrassColor(level, pos);
         }
      } else {
         return 5626232;
      }
   }

   private static BlockState getBelowSafely(BlockAndTintGetter level, BlockPos pos) {
      try {
         return level.getBlockState(pos.below());
      } catch (RuntimeException ignored) {
         return null;
      }
   }

   private static int magicalGrassTarget(BlockState state, BlockPos pos) {
      int base = state.is((net.minecraft.world.level.block.Block)EldergroveBlocks.ELDERGROVE_GRASS_FAINT.get())
         ? 7649374
         : (state.is((net.minecraft.world.level.block.Block)EldergroveBlocks.ELDERGROVE_GRASS.get()) ? 5626232 : 4442525);
      if (pos == null) {
         return base;
      } else {
         int variant = Math.floorMod(pos.getX() * 734287 + pos.getY() * 159733 + pos.getZ() * 912271, 3);
         if (variant == 0) {
            return blend(base, 9092703, 0.18);
         } else {
            return variant == 1 ? blend(base, 5433488, 0.16) : blend(base, 4573361, 0.14);
         }
      }
   }

   private static int blend(int from, int to, double amount) {
      double clamped = Math.max(0.0, Math.min(1.0, amount));
      int fromR = from >> 16 & 0xFF;
      int fromG = from >> 8 & 0xFF;
      int fromB = from & 0xFF;
      int toR = to >> 16 & 0xFF;
      int toG = to >> 8 & 0xFF;
      int toB = to & 0xFF;
      int r = (int)(fromR + (toR - fromR) * clamped);
      int g = (int)(fromG + (toG - fromG) * clamped);
      int b = (int)(fromB + (toB - fromB) * clamped);
      return r << 16 | g << 8 | b;
   }
}
