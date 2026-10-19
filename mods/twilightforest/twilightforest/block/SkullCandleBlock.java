package twilightforest.block;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SkullBlock.Type;
import net.minecraft.world.level.block.SkullBlock.Types;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import twilightforest.components.item.SkullCandles;
import twilightforest.init.TFDataComponents;

public class SkullCandleBlock extends AbstractSkullCandleBlock {
   public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;
   public static final MapCodec<SkullCandleBlock> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(Type.CODEC.fieldOf("kind").forGetter(AbstractSkullCandleBlock::getType), propertiesCodec())
         .apply(instance, SkullCandleBlock::new)
   );
   protected static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 8.0, 12.0);
   protected static final VoxelShape PIGLIN_SHAPE = Block.box(3.0, 0.0, 3.0, 13.0, 8.0, 13.0);
   protected static final VoxelShape ONE_CANDLE = Block.box(7.0, 8.0, 7.0, 9.0, 14.0, 9.0);
   protected static final VoxelShape TWO_CANDLE = Block.box(5.0, 8.0, 6.0, 11.0, 14.0, 9.0);
   protected static final VoxelShape THREE_CANDLE = Block.box(5.0, 8.0, 6.0, 10.0, 14.0, 11.0);
   protected static final VoxelShape FOUR_CANDLE = Block.box(5.0, 8.0, 5.0, 11.0, 14.0, 10.0);
   protected static final VoxelShape SKULL_WITH_ONE = Shapes.or(SHAPE, ONE_CANDLE);
   protected static final VoxelShape SKULL_WITH_TWO = Shapes.or(SHAPE, TWO_CANDLE);
   protected static final VoxelShape SKULL_WITH_THREE = Shapes.or(SHAPE, THREE_CANDLE);
   protected static final VoxelShape SKULL_WITH_FOUR = Shapes.or(SHAPE, FOUR_CANDLE);
   protected static final VoxelShape PIGLIN_SKULL_WITH_ONE = Shapes.or(PIGLIN_SHAPE, ONE_CANDLE);
   protected static final VoxelShape PIGLIN_SKULL_WITH_TWO = Shapes.or(PIGLIN_SHAPE, TWO_CANDLE);
   protected static final VoxelShape PIGLIN_SKULL_WITH_THREE = Shapes.or(PIGLIN_SHAPE, THREE_CANDLE);
   protected static final VoxelShape PIGLIN_SKULL_WITH_FOUR = Shapes.or(PIGLIN_SHAPE, FOUR_CANDLE);
   private static final Int2ObjectMap<List<Vec3>> PARTICLE_OFFSETS = (Int2ObjectMap<List<Vec3>>)Util.make(() -> {
      Int2ObjectMap<List<Vec3>> var0 = new Int2ObjectOpenHashMap();
      var0.defaultReturnValue(ImmutableList.of());
      var0.put(1, ImmutableList.of(new Vec3(0.5, 0.96, 0.5)));
      var0.put(2, ImmutableList.of(new Vec3(0.375, 0.88, 0.5), new Vec3(0.625, 0.96, 0.44)));
      var0.put(3, ImmutableList.of(new Vec3(0.5, 0.73, 0.625), new Vec3(0.375, 0.88, 0.5), new Vec3(0.56, 0.96, 0.44)));
      var0.put(4, ImmutableList.of(new Vec3(0.44, 0.73, 0.56), new Vec3(0.625, 0.88, 0.56), new Vec3(0.375, 0.88, 0.375), new Vec3(0.56, 0.96, 0.375)));
      return Int2ObjectMaps.unmodifiable(var0);
   });

   public SkullCandleBlock(Type type, Properties properties) {
      super(type, properties);
      this.registerDefaultState((BlockState)((BlockState)this.getStateDefinition().any()).setValue(ROTATION, 0));
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext ctx) {
      boolean piglin = this.getType() == Types.PIGLIN;

      return switch (state.getValue(CANDLES)) {
         case 2 -> piglin ? PIGLIN_SKULL_WITH_TWO : SKULL_WITH_TWO;
         case 3 -> piglin ? PIGLIN_SKULL_WITH_THREE : SKULL_WITH_THREE;
         case 4 -> piglin ? PIGLIN_SKULL_WITH_FOUR : SKULL_WITH_FOUR;
         default -> piglin ? PIGLIN_SKULL_WITH_ONE : SKULL_WITH_ONE;
      };
   }

   @Override
   public Iterable<Vec3> getParticleOffsets(BlockState state, LevelAccessor accessor, BlockPos pos) {
      return (Iterable<Vec3>)PARTICLE_OFFSETS.get(state.getValue(CANDLES));
   }

   public BlockState getStateForPlacement(BlockPlaceContext ctx) {
      return (BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(ROTATION, Mth.floor(ctx.getRotation() * 16.0F / 360.0F + 0.5) & 15))
            .setValue(LIGHTING, LightableBlock.Lighting.NONE))
         .setValue(CANDLES, ((SkullCandles)ctx.getItemInHand().getOrDefault(TFDataComponents.SKULL_CANDLES, SkullCandles.DEFAULT)).count());
   }

   public BlockState rotate(BlockState state, Rotation rot) {
      return (BlockState)state.setValue(ROTATION, rot.rotate((Integer)state.getValue(ROTATION), 16));
   }

   public BlockState mirror(BlockState state, Mirror mirror) {
      return (BlockState)state.setValue(ROTATION, mirror.mirror((Integer)state.getValue(ROTATION), 16));
   }

   @Override
   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder.add(new Property[]{ROTATION}));
   }
}
