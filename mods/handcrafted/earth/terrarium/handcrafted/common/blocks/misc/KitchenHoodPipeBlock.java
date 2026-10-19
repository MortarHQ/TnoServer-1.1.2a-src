package earth.terrarium.handcrafted.common.blocks.misc;

import com.mojang.serialization.MapCodec;
import earth.terrarium.handcrafted.common.blocks.base.Hammerable;
import earth.terrarium.handcrafted.common.blocks.base.SimpleBlock;
import earth.terrarium.handcrafted.common.registry.ModSoundEvents;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class KitchenHoodPipeBlock extends SimpleBlock implements Hammerable {
   public static final MapCodec<KitchenHoodPipeBlock> CODEC = simpleCodec(KitchenHoodPipeBlock::new);
   public static final BooleanProperty CORNER = BooleanProperty.create("corner");
   public static final VoxelShape SHAPE = Stream.of(
         Block.box(3.0, 0.0, 3.0, 13.0, 3.0, 13.0), Block.box(3.0, 13.0, 3.0, 13.0, 16.0, 13.0), Block.box(4.0, 3.0, 4.0, 12.0, 13.0, 12.0)
      )
      .reduce(Shapes::or)
      .get();
   public static final VoxelShape NORTH_SHAPE = Stream.of(
         Block.box(3.0, 0.0, 3.0, 13.0, 3.0, 13.0),
         Block.box(3.0, 3.0, 13.0, 13.0, 13.0, 16.0),
         Block.box(4.0, 3.0, 4.0, 12.0, 12.0, 12.0),
         Block.box(4.0, 4.0, 12.0, 12.0, 12.0, 13.0)
      )
      .reduce(Shapes::or)
      .get();
   public static final VoxelShape EAST_SHAPE = Stream.of(
         Block.box(3.0, 0.0, 3.0, 13.0, 3.0, 13.0),
         Block.box(0.0, 3.0, 3.0, 3.0, 13.0, 13.0),
         Block.box(4.0, 3.0, 4.0, 12.0, 12.0, 12.0),
         Block.box(3.0, 4.0, 4.0, 4.0, 12.0, 12.0)
      )
      .reduce(Shapes::or)
      .get();
   public static final VoxelShape SOUTH_SHAPE = Stream.of(
         Block.box(3.0, 0.0, 3.0, 13.0, 3.0, 13.0),
         Block.box(3.0, 3.0, 0.0, 13.0, 13.0, 3.0),
         Block.box(4.0, 3.0, 4.0, 12.0, 12.0, 12.0),
         Block.box(4.0, 4.0, 3.0, 12.0, 12.0, 4.0)
      )
      .reduce(Shapes::or)
      .get();
   public static final VoxelShape WEST_SHAPE = Stream.of(
         Block.box(3.0, 0.0, 3.0, 13.0, 3.0, 13.0),
         Block.box(13.0, 3.0, 3.0, 16.0, 13.0, 13.0),
         Block.box(4.0, 3.0, 4.0, 12.0, 12.0, 12.0),
         Block.box(12.0, 4.0, 4.0, 13.0, 12.0, 12.0)
      )
      .reduce(Shapes::or)
      .get();

   public KitchenHoodPipeBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(CORNER, false)).setValue(FACING, Direction.NORTH))
            .setValue(WATERLOGGED, false)
      );
   }

   protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
      return CODEC;
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      if (!(Boolean)state.getValue(CORNER)) {
         return SHAPE;
      }

      Direction direction = (Direction)state.getValue(FACING);

      return switch (direction) {
         case NORTH -> NORTH_SHAPE;
         case SOUTH -> SOUTH_SHAPE;
         case WEST -> WEST_SHAPE;
         default -> EAST_SHAPE;
      };
   }

   @Override
   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, CORNER, WATERLOGGED});
   }

   @Override
   public void onHammer(Level level, BlockPos pos, BlockState state, Direction side, Player user, Vec3 hitPos) {
      level.setBlock(
         pos,
         (BlockState)((BlockState)state.setValue(CORNER, !(Boolean)state.getValue(CORNER)))
            .setValue(FACING, side.getAxis().isHorizontal() ? side : (Direction)state.getValue(FACING)),
         3
      );
      level.playSound(null, pos, (SoundEvent)ModSoundEvents.HAMMER_STONE.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
   }
}
