package com.iafenvoy.iceandfire.item.block;

import com.iafenvoy.iceandfire.item.block.entity.PixieHouseBlockEntity;
import com.iafenvoy.iceandfire.registry.IafBlockEntities;
import com.mojang.serialization.MapCodec;
import java.util.Locale;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MapColor;

public class PixieHouseBlock extends BaseEntityBlock {
   private static final MapCodec<? extends BaseEntityBlock> CODEC = simpleCodec(s -> new PixieHouseBlock());
   public static final DirectionProperty FACING = DirectionProperty.create("facing", Plane.HORIZONTAL);

   public PixieHouseBlock() {
      super(
         Properties.of()
            .mapColor(MapColor.WOOD)
            .instrument(NoteBlockInstrument.BASS)
            .ignitedByLava()
            .noOcclusion()
            .dynamicShape()
            .strength(2.0F, 5.0F)
            .randomTicks()
      );
      this.registerDefaultState((BlockState)((BlockState)this.getStateDefinition().any()).setValue(FACING, Direction.NORTH));
   }

   public static String name(String type) {
      return String.format(Locale.ROOT, "pixie_house_%s", type);
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING});
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
   }

   public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
      this.dropPixie(worldIn, pos);
      popResource(worldIn, pos, new ItemStack(this, 0));
      super.onRemove(state, worldIn, pos, newState, isMoving);
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.MODEL;
   }

   public void dropPixie(Level world, BlockPos pos) {
      if (world.getBlockEntity(pos) != null && world.getBlockEntity(pos) instanceof PixieHouseBlockEntity house && house.hasPixie) {
         house.releasePixie();
      }
   }

   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> entityType) {
      return level.isClientSide
         ? createTickerHelper(entityType, (BlockEntityType)IafBlockEntities.PIXIE_HOUSE.get(), PixieHouseBlockEntity::tickClient)
         : createTickerHelper(entityType, (BlockEntityType)IafBlockEntities.PIXIE_HOUSE.get(), PixieHouseBlockEntity::tickServer);
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new PixieHouseBlockEntity(pos, state);
   }
}
