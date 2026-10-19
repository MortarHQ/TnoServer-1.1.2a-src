package twilightforest.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SkullBlock.Type;
import net.minecraft.world.level.block.SkullBlock.Types;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import twilightforest.components.item.SkullCandles;
import twilightforest.init.TFDataComponents;

public class WallSkullCandleBlock extends AbstractSkullCandleBlock {
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
   public static final MapCodec<WallSkullCandleBlock> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(Type.CODEC.fieldOf("kind").forGetter(AbstractSkullCandleBlock::getType), propertiesCodec())
         .apply(instance, WallSkullCandleBlock::new)
   );
   private static final Map<Direction, VoxelShape> AABBS = Maps.newEnumMap(
      ImmutableMap.of(
         Direction.NORTH,
         Block.box(4.0, 4.0, 8.0, 12.0, 12.0, 16.0),
         Direction.SOUTH,
         Block.box(4.0, 4.0, 0.0, 12.0, 12.0, 8.0),
         Direction.EAST,
         Block.box(0.0, 4.0, 4.0, 8.0, 12.0, 12.0),
         Direction.WEST,
         Block.box(8.0, 4.0, 4.0, 16.0, 12.0, 12.0)
      )
   );
   private static final Map<Direction, VoxelShape> PIGLIN_AABBS = Maps.newEnumMap(
      ImmutableMap.of(
         Direction.NORTH,
         Block.box(3.0, 4.0, 8.0, 13.0, 12.0, 16.0),
         Direction.SOUTH,
         Block.box(3.0, 4.0, 0.0, 13.0, 12.0, 8.0),
         Direction.EAST,
         Block.box(0.0, 4.0, 3.0, 8.0, 12.0, 13.0),
         Direction.WEST,
         Block.box(8.0, 4.0, 3.0, 16.0, 12.0, 13.0)
      )
   );
   private static final Int2ObjectMap<List<Vec3>> PARTICLE_OFFSETS = (Int2ObjectMap<List<Vec3>>)Util.make(() -> {
      Int2ObjectMap<List<Vec3>> var0 = new Int2ObjectOpenHashMap();
      var0.defaultReturnValue(ImmutableList.of());
      var0.put(1, ImmutableList.of(new Vec3(0.5, 1.25, 0.5)));
      var0.put(2, ImmutableList.of(new Vec3(0.375, 1.19, 0.5), new Vec3(0.625, 1.25, 0.44)));
      var0.put(3, ImmutableList.of(new Vec3(0.5, 1.063, 0.625), new Vec3(0.375, 1.19, 0.5), new Vec3(0.56, 1.25, 0.44)));
      var0.put(4, ImmutableList.of(new Vec3(0.44, 1.063, 0.56), new Vec3(0.625, 1.19, 0.56), new Vec3(0.375, 1.19, 0.375), new Vec3(0.56, 1.25, 0.375)));
      return Int2ObjectMaps.unmodifiable(var0);
   });

   public WallSkullCandleBlock(Type type, Properties properties) {
      super(type, properties);
      this.registerDefaultState((BlockState)((BlockState)this.getStateDefinition().any()).setValue(FACING, Direction.NORTH));
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      return this.getType() == Types.PIGLIN ? PIGLIN_AABBS.get(state.getValue(FACING)) : AABBS.get(state.getValue(FACING));
   }

   @Override
   public Iterable<Vec3> getParticleOffsets(BlockState state, LevelAccessor accessor, BlockPos pos) {
      return (Iterable<Vec3>)PARTICLE_OFFSETS.get(state.getValue(CANDLES));
   }

   public BlockState getStateForPlacement(BlockPlaceContext ctx) {
      BlockState state = this.defaultBlockState();
      BlockGetter getter = ctx.getLevel();
      BlockPos pos = ctx.getClickedPos();
      Direction[] directions = ctx.getNearestLookingDirections();

      for (Direction dir : directions) {
         if (dir.getAxis().isHorizontal()) {
            Direction var10 = dir.getOpposite();
            state = (BlockState)state.setValue(FACING, var10);
            if (!getter.getBlockState(pos.relative(dir)).canBeReplaced(ctx)) {
               return (BlockState)((BlockState)state.setValue(LIGHTING, LightableBlock.Lighting.NONE))
                  .setValue(CANDLES, ((SkullCandles)ctx.getItemInHand().getOrDefault(TFDataComponents.SKULL_CANDLES, SkullCandles.DEFAULT)).count());
            }
         }
      }

      return null;
   }

   @Override
   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource rand) {
      Direction dir = (Direction)state.getValue(FACING);
      if (state.getValue(LIGHTING) != LightableBlock.Lighting.NONE) {
         this.getParticleOffsets(state, level, pos).forEach(offset -> {
            Vec3 trueOffset = offset.add(pos.getX() - dir.getStepX() * 0.25F, pos.getY(), pos.getZ() - dir.getStepZ() * 0.25F);
            this.addParticlesAndSound(level, trueOffset.x(), trueOffset.y(), trueOffset.z(), rand, (LightableBlock.Lighting)state.getValue(LIGHTING));
         });
      }
   }

   public BlockState rotate(BlockState state, Rotation rot) {
      return (BlockState)state.setValue(FACING, rot.rotate((Direction)state.getValue(FACING)));
   }

   public BlockState mirror(BlockState state, Mirror mirror) {
      return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
   }

   @Override
   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder.add(new Property[]{FACING}));
   }
}
