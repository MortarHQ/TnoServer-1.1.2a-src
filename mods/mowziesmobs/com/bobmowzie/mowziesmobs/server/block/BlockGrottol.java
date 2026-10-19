package com.bobmowzie.mowziesmobs.server.block;

import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.stream.Collector;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class BlockGrottol extends HorizontalDirectionalBlock {
   public static final MapCodec<BlockGrottol> CODEC = simpleCodec(BlockGrottol::new);
   public static final EnumProperty<BlockGrottol.Variant> VARIANT = EnumProperty.create("variant", BlockGrottol.Variant.class);
   private static final VoxelShape BOUNDS = Shapes.box(0.0625, 0.0, 0.0625, 0.9375, 0.9375, 0.9375);

   public BlockGrottol(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)this.getStateDefinition().any()).setValue(FACING, Direction.NORTH))
            .setValue(VARIANT, BlockGrottol.Variant.DIAMOND)
      );
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder);
      builder.add(new Property[]{FACING});
      builder.add(new Property[]{VARIANT});
   }

   @Deprecated
   public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
      return BOUNDS;
   }

   @Deprecated
   public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
      return super.canSurvive(state, world, pos) && hasSupport(world, pos);
   }

   public void neighborChanged(BlockState state, Level world, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
      if (hasSupport(world, pos)) {
         world.removeBlock(pos, false);
      }
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
   }

   private static boolean hasSupport(BlockGetter world, BlockPos pos) {
      return world.getBlockState(pos.below()).isSolid();
   }

   @NotNull
   protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
      return CODEC;
   }

   public enum Variant implements StringRepresentable {
      DIAMOND(0, "diamond"),
      BLACK_PINK(1, "black_pink");

      private static final Int2ObjectMap<BlockGrottol.Variant> LOOKUP = Stream.of(values())
         .collect(Collector.of(Int2ObjectOpenHashMap::new, (map, variant) -> map.put(variant.getIndex(), variant), (left, right) -> {
            throw new IllegalStateException();
         }, map -> {
            map.defaultReturnValue(DIAMOND);
            return Int2ObjectMaps.unmodifiable(map);
         }));
      private final int index;
      private final String name;

      Variant(int index, String name) {
         this.index = index;
         this.name = name;
      }

      public final int getIndex() {
         return this.index;
      }

      public final String getSerializedName() {
         return this.name;
      }

      public static BlockGrottol.Variant valueOf(int index) {
         return (BlockGrottol.Variant)LOOKUP.get(index);
      }
   }
}
