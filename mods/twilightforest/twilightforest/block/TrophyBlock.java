package twilightforest.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import twilightforest.enums.BossVariant;

public class TrophyBlock extends AbstractTrophyBlock {
   public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;
   public static final MapCodec<TrophyBlock> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(
            BossVariant.CODEC.fieldOf("variant").forGetter(AbstractTrophyBlock::getVariant),
            Codec.INT.fieldOf("pedestal_comparator_strength").forGetter(AbstractTrophyBlock::getComparatorValue),
            propertiesCodec()
         )
         .apply(instance, TrophyBlock::new)
   );
   public static final VoxelShape GHAST_SHAPE = Block.box(4.0, 8.0, 4.0, 12.0, 16.0, 12.0);
   protected static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 8.0, 12.0);
   protected static final VoxelShape YETI_X_SHAPE = Block.box(3.25, 0.0, 4.5, 12.75, 10.0, 11.5);
   protected static final VoxelShape YETI_Z_SHAPE = Block.box(4.5, 0.0, 3.25, 11.5, 10.0, 12.75);
   protected static final VoxelShape YETI_CORNER_SHAPE = Block.box(4.5, 0.0, 4.5, 11.5, 10.0, 11.5);

   public TrophyBlock(BossVariant variant, int value, Properties properties) {
      super(variant, value, properties);
      this.registerDefaultState((BlockState)((BlockState)this.getStateDefinition().any()).setValue(ROTATION, 0));
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      if (((AbstractTrophyBlock)state.getBlock()).getVariant() == BossVariant.UR_GHAST) {
         return GHAST_SHAPE;
      }

      if (((AbstractTrophyBlock)state.getBlock()).getVariant() == BossVariant.ALPHA_YETI) {
         return switch (state.getValue(ROTATION)) {
            case 2, 6, 10, 14 -> YETI_CORNER_SHAPE;
            case 3, 4, 5, 11, 12, 13 -> YETI_Z_SHAPE;
            default -> YETI_X_SHAPE;
         };
      } else {
         return SHAPE;
      }
   }

   public VoxelShape getOcclusionShape(BlockState state, BlockGetter getter, BlockPos pos) {
      return Shapes.empty();
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)this.defaultBlockState().setValue(ROTATION, Mth.floor(context.getRotation() * 16.0F / 360.0F + 0.5) & 15);
   }

   public BlockState rotate(BlockState state, Rotation rot) {
      return (BlockState)state.setValue(ROTATION, rot.rotate((Integer)state.getValue(ROTATION), 16));
   }

   public BlockState mirror(BlockState state, Mirror mirror) {
      return (BlockState)state.setValue(ROTATION, mirror.mirror((Integer)state.getValue(ROTATION), 16));
   }

   @Override
   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{ROTATION, POWERED});
   }
}
