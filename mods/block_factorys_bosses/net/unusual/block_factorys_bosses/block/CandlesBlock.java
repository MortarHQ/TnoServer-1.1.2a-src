package net.unusual.block_factorys_bosses.block;

import java.util.function.Supplier;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CandlesBlock extends Block {
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
   private static final VoxelShape X_BOX = box(5.0, 0.0, 0.0, 11.0, 23.0, 16.0);
   private static final VoxelShape Z_BOX = box(0.0, 0.0, 5.0, 16.0, 23.0, 11.0);
   public static final Vec3[] FLAME_POINTS_Z = new Vec3[]{new Vec3(0.5, 1.63, 0.5), new Vec3(0.18, 1.55, 0.5), new Vec3(0.87, 1.55, 0.5)};
   public static final Vec3[] FLAME_POINTS_X = new Vec3[]{new Vec3(0.5, 1.63, 0.5), new Vec3(0.5, 1.55, 0.18), new Vec3(0.5, 1.55, 0.87)};
   private final Supplier<SimpleParticleType> particleType;

   public CandlesBlock(Supplier<SimpleParticleType> particleType) {
      super(Properties.of().sound(SoundType.STONE).strength(1.0F, 10.0F).lightLevel(s -> 15).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH));
      this.particleType = particleType;
   }

   public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
      return true;
   }

   public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
      return 0;
   }

   public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
      return Shapes.empty();
   }

   public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
      return switch ((Direction)state.getValue(FACING)) {
         case EAST, WEST -> X_BOX;
         default -> Z_BOX;
      };
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING});
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
   }

   public BlockState rotate(BlockState state, Rotation rot) {
      return (BlockState)state.setValue(FACING, rot.rotate((Direction)state.getValue(FACING)));
   }

   public BlockState mirror(BlockState state, Mirror mirrorIn) {
      return state.rotate(mirrorIn.getRotation((Direction)state.getValue(FACING)));
   }

   @OnlyIn(Dist.CLIENT)
   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      int x = pos.getX();
      int y = pos.getY();
      int z = pos.getZ();
      Axis facing = ((Direction)state.getValue(FACING)).getAxis();
      if (random.nextBoolean()) {
         for (Vec3 point : facing == Axis.X ? FLAME_POINTS_X : FLAME_POINTS_Z) {
            level.addParticle((ParticleOptions)this.particleType.get(), x + point.x, y + point.y, z + point.z, 0.0, 0.0, 0.0);
         }
      } else {
         for (Vec3 point : facing == Axis.X ? FLAME_POINTS_X : FLAME_POINTS_Z) {
            level.addParticle(
               (ParticleOptions)this.particleType.get(),
               x + point.x + Mth.nextDouble(random, -0.05, 0.05),
               y + point.y + Mth.nextDouble(random, -0.05, 0.05),
               z + point.z + Mth.nextDouble(random, -0.05, 0.05),
               0.0,
               0.0,
               0.0
            );
         }
      }
   }
}
