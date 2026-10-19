package net.unusual.block_factorys_bosses.mixins;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.BasaltColumnsFeature;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BasaltColumnsFeature.class)
public class BasaltColumnsFeatureMixin {
   @Shadow
   @Final
   @Mutable
   private static ImmutableList<Block> CANNOT_PLACE_ON;

   @Inject(method = "<clinit>", at = @At("TAIL"))
   private static void addAdditionalCannotPlaceOnBlocks(CallbackInfo ci) {
      Builder<Block> builder = ImmutableList.builder();
      builder.addAll(CANNOT_PLACE_ON);
      builder.add(Blocks.WARPED_STAIRS);
      builder.add(Blocks.DEEPSLATE_TILE_STAIRS);
      builder.add(Blocks.DARK_PRISMARINE_STAIRS);
      builder.add(Blocks.PRISMARINE_BRICKS);
      builder.add(Blocks.DARK_PRISMARINE);
      builder.add(Blocks.PRISMARINE_BRICK_STAIRS);
      builder.add(Blocks.COBBLED_DEEPSLATE);
      builder.add(Blocks.COBBLED_DEEPSLATE_SLAB);
      builder.add(Blocks.DEEPSLATE);
      builder.add(Blocks.CYAN_WOOL);
      builder.add(Blocks.CYAN_CONCRETE_POWDER);
      builder.add(Blocks.GRAY_CONCRETE_POWDER);
      builder.add(Blocks.PRISMARINE_STAIRS);
      builder.add(Blocks.WAXED_WEATHERED_COPPER_TRAPDOOR);
      builder.add(Blocks.DEEPSLATE_TILE_SLAB);
      builder.add(Blocks.BLACK_CANDLE);
      builder.add(Blocks.CYAN_CANDLE);
      builder.add(Blocks.PRISMARINE_WALL);
      builder.add(Blocks.COBBLED_DEEPSLATE_STAIRS);
      builder.add(Blocks.WAXED_WEATHERED_CUT_COPPER_STAIRS);
      builder.add(Blocks.WARPED_WART_BLOCK);
      builder.add(Blocks.WAXED_WEATHERED_COPPER_BULB);
      CANNOT_PLACE_ON = builder.build();
   }
}
