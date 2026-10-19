package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ShortBushBlock extends BushBlock {
   public static final MapCodec<ShortBushBlock> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(Codec.INT.fieldOf("height").forGetter(block -> block.height), propertiesCodec()).apply(instance, ShortBushBlock::new)
   );
   private final int height;
   private final VoxelShape shape;

   public ShortBushBlock(Properties properties) {
      this(3, properties);
   }

   public ShortBushBlock(int height, Properties properties) {
      super(properties);
      this.height = height;
      this.shape = Block.box(2.0, 0.0, 2.0, 14.0, height, 14.0);
   }

   protected MapCodec<? extends ShortBushBlock> codec() {
      return CODEC;
   }

   public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
      return this.shape;
   }
}
