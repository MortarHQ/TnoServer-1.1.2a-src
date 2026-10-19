package io.github.Memoires.mysticism.block;

import io.github.Memoires.mysticism.registry.dimension.MysticismDimensions;
import io.github.manasmods.tensura.registry.dimension.TensuraDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;

public class ElementalRealmPortal extends Block implements Portal, SimpleWaterloggedBlock {
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

   public ElementalRealmPortal() {
      super(
         Properties.of()
            .isViewBlocking((blockState, blockGetter, blockPos) -> false)
            .noLootTable()
            .noOcclusion()
            .noCollission()
            .sound(SoundType.AMETHYST)
            .strength(-1.0F, 3600000.0F)
      );
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(WATERLOGGED, Boolean.TRUE));
   }

   public RenderShape getRenderShape(BlockState pState) {
      return RenderShape.INVISIBLE;
   }

   public boolean skipRendering(BlockState state, BlockState stateFrom, Direction direction) {
      return stateFrom.is(this) ? true : super.skipRendering(state, stateFrom, direction);
   }

   public boolean propagatesSkylightDown(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
      return true;
   }

   public boolean isCollisionShapeFullBlock(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
      return false;
   }

   public boolean canBeReplaced(BlockState pState, Fluid pFluid) {
      return false;
   }

   public boolean canBeReplaced(BlockState pState, BlockPlaceContext pUseContext) {
      return false;
   }

   public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
      return true;
   }

   protected void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
      if (entity.canUsePortal(false)
         && Shapes.joinIsNotEmpty(
            Shapes.create(entity.getBoundingBox().move(-blockPos.getX(), -blockPos.getY(), -blockPos.getZ())),
            blockState.getShape(level, blockPos),
            BooleanOp.AND
         )) {
         entity.setAsInsidePortal(this, blockPos);
      }
   }

   public DimensionTransition getPortalDestination(ServerLevel serverLevel, Entity entity, BlockPos blockPos) {
      ResourceKey<Level> current = serverLevel.dimension();
      ResourceKey<Level> destinationKey;
      if (current == Level.OVERWORLD) {
         destinationKey = MysticismDimensions.ELEMENTAL_REALM;
      } else if (current == MysticismDimensions.ELEMENTAL_REALM) {
         destinationKey = Level.OVERWORLD;
      } else if (current == TensuraDimensions.LABYRINTH) {
         destinationKey = MysticismDimensions.ELEMENTAL_REALM;
      } else {
         destinationKey = Level.OVERWORLD;
      }

      ServerLevel destination = serverLevel.getServer().getLevel(destinationKey);
      if (destination == null) {
         return null;
      }

      Vec3 targetPos = new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ());
      return new DimensionTransition(
         destination,
         targetPos,
         entity.getDeltaMovement(),
         entity.getYRot(),
         entity.getXRot(),
         DimensionTransition.PLAY_PORTAL_SOUND.then(DimensionTransition.PLACE_PORTAL_TICKET)
      );
   }

   public BlockState updateShape(
      BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos
   ) {
      if ((Boolean)pState.getValue(WATERLOGGED)) {
         pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
      }

      return super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
   }

   public FluidState getFluidState(BlockState pState) {
      return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> pBuilder) {
      pBuilder.add(new Property[]{WATERLOGGED});
   }

   public BlockState getStateForPlacement(BlockPlaceContext pContext) {
      FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
      return (BlockState)this.defaultBlockState().setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
   }
}
