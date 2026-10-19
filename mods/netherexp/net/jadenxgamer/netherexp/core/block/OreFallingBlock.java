package net.jadenxgamer.netherexp.core.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class OreFallingBlock extends FallingBlock {
   private final IntProvider xpRange;
   private final ColorRGBA dustColor;
   public static final MapCodec<OreFallingBlock> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(
            IntProvider.codec(0, 10).fieldOf("experience").forGetter(block -> block.xpRange),
            ColorRGBA.CODEC.fieldOf("falling_dust_color").forGetter(block -> block.dustColor),
            propertiesCodec()
         )
         .apply(instance, OreFallingBlock::new)
   );

   public OreFallingBlock(IntProvider xpRange, ColorRGBA dustColor, Properties properties) {
      super(properties);
      this.xpRange = xpRange;
      this.dustColor = dustColor;
   }

   protected MapCodec<? extends FallingBlock> codec() {
      return CODEC;
   }

   protected void spawnAfterBreak(BlockState state, ServerLevel level, BlockPos pos, ItemStack stack, boolean dropExperience) {
      super.spawnAfterBreak(state, level, pos, stack, dropExperience);
   }

   public int getExpDrop(BlockState state, LevelAccessor level, BlockPos pos, @Nullable BlockEntity blockEntity, @Nullable Entity breaker, ItemStack tool) {
      return this.xpRange.sample(level.getRandom());
   }

   public int getDustColor(BlockState state, BlockGetter level, BlockPos pos) {
      return this.dustColor.rgba();
   }
}
