package com.github.L_Ender.cataclysm.blocks;

import com.github.L_Ender.cataclysm.blockentities.Mechanical_fusion_Anvil_Block_Entity;
import com.github.L_Ender.cataclysm.init.ModTileentites;
import com.github.L_Ender.cataclysm.inventory.WeaponfusionMenu;
import com.mojang.serialization.MapCodec;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Mechanical_fusion_Anvil extends BaseEntityBlock {
   public static final MapCodec<Mechanical_fusion_Anvil> CODEC = simpleCodec(Mechanical_fusion_Anvil::new);
   private static final Component CONTAINER_TITLE = Component.translatable("cataclysm.container.weapon_fusion");
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
   private static final VoxelShape X_BASE = Block.box(1.0, 0.0, 3.0, 15.0, 3.0, 13.0);
   private static final VoxelShape Z_BASE = Block.box(3.0, 0.0, 1.0, 13.0, 3.0, 15.0);
   private static final VoxelShape X_LEG1 = Block.box(3.0, 3.0, 5.0, 13.0, 8.0, 11.0);
   private static final VoxelShape X_TOP = Block.box(1.0, 8.0, 0.0, 15.0, 13.0, 16.0);
   private static final VoxelShape Z_LEG1 = Block.box(5.0, 3.0, 3.0, 11.0, 8.0, 13.0);
   private static final VoxelShape Z_TOP = Block.box(0.0, 8.0, 1.0, 16.0, 13.0, 15.0);
   private static final VoxelShape X_AXIS_AABB = Shapes.or(X_BASE, new VoxelShape[]{X_LEG1, X_TOP});
   private static final VoxelShape Z_AXIS_AABB = Shapes.or(Z_BASE, new VoxelShape[]{Z_LEG1, Z_TOP});

   public MapCodec<Mechanical_fusion_Anvil> codec() {
      return CODEC;
   }

   public Mechanical_fusion_Anvil(Properties p_54257_) {
      super(p_54257_);
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH));
   }

   public BlockState getStateForPlacement(BlockPlaceContext p_48781_) {
      return (BlockState)this.defaultBlockState().setValue(FACING, p_48781_.getHorizontalDirection().getClockWise());
   }

   protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
      if (pLevel.isClientSide) {
         return InteractionResult.SUCCESS;
      }

      pPlayer.openMenu(pState.getMenuProvider(pLevel, pPos));
      pPlayer.awardStat(Stats.INTERACT_WITH_SMITHING_TABLE);
      return InteractionResult.CONSUME;
   }

   public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
      return new SimpleMenuProvider((i, inv, player) -> new WeaponfusionMenu(i, inv, ContainerLevelAccess.create(level, pos)), CONTAINER_TITLE);
   }

   public VoxelShape getShape(BlockState p_48816_, BlockGetter p_48817_, BlockPos p_48818_, CollisionContext p_48819_) {
      Direction direction = (Direction)p_48816_.getValue(FACING);
      return direction.getAxis() == Axis.X ? X_AXIS_AABB : Z_AXIS_AABB;
   }

   public BlockState rotate(BlockState p_48811_, Rotation p_48812_) {
      return (BlockState)p_48811_.setValue(FACING, p_48812_.rotate((Direction)p_48811_.getValue(FACING)));
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> p_48814_) {
      p_48814_.add(new Property[]{FACING});
   }

   protected boolean isPathfindable(BlockState p_51023_, PathComputationType p_51026_) {
      return false;
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new Mechanical_fusion_Anvil_Block_Entity(pos, state);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_152180_, BlockState p_152181_, BlockEntityType<T> p_152182_) {
      return createTickerHelper(p_152182_, (BlockEntityType)ModTileentites.MECHANICAL_FUSION_ANVIL.get(), Mechanical_fusion_Anvil_Block_Entity::commonTick);
   }
}
