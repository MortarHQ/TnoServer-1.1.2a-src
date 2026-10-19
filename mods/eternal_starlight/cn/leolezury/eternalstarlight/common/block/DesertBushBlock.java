package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DesertBushBlock extends BushBlock {
   public static final MapCodec<DesertBushBlock> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(Codec.INT.fieldOf("height").forGetter(block -> block.height), propertiesCodec()).apply(instance, DesertBushBlock::new)
   );
   private final int height;
   private final VoxelShape shape;

   public DesertBushBlock(Properties properties) {
      this(3, properties);
   }

   public DesertBushBlock(int height, Properties properties) {
      super(properties);
      this.height = height;
      this.shape = Block.box(2.0, 0.0, 2.0, 14.0, height, 14.0);
   }

   protected MapCodec<DesertBushBlock> codec() {
      return CODEC;
   }

   protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
      return super.mayPlaceOn(blockState, blockGetter, blockPos) || blockState.is(BlockTags.SAND) || blockState.is(ESTags.Blocks.BASE_STONE_STARLIGHT);
   }

   public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
      return this.shape;
   }
}
