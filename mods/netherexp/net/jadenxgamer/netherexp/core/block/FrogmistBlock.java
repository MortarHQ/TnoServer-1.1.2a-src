package net.jadenxgamer.netherexp.core.block;

import net.jadenxgamer.netherexp.config.JNEConfigImpl;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.block.enums.Liquidlogged;
import net.jadenxgamer.netherexp.core.block.interfaces.LiquidloggedBlock;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class FrogmistBlock extends Block implements LiquidloggedBlock {
   public static final EnumProperty<Liquidlogged.AllFluids> LIQUIDLOGGED = EnumProperty.create("liquidlogged", Liquidlogged.AllFluids.class);
   public static final IntegerProperty OPACITY = IntegerProperty.create("opacity", 1, 4);
   private static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);
   private static final VoxelShape INVISIBLE_SHAPE = Block.box(0.0, 0.0, 0.0, 0.001, 0.001, 0.001);

   public FrogmistBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.defaultBlockState().setValue(LIQUIDLOGGED, Liquidlogged.AllFluids.AIR)).setValue(OPACITY, 1));
   }

   protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      if (JNEConfigImpl.COMMON.isLoaded() && (Boolean)JNEConfigs.CAN_ANYTHING_BREAK_FROGMIST.get()) {
         return SHAPE;
      } else {
         return ((EntityCollisionContext)context).getEntity() instanceof Player player && player.getMainHandItem().is(JNETags.Items.FROGMIST_VISIBLE_ITEMS)
            ? SHAPE
            : INVISIBLE_SHAPE;
      }
   }

   public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
      return !context.isSecondaryUseActive() && context.getItemInHand().getItem() == this.asItem() && state.getValue(OPACITY) < 4
         ? true
         : super.canBeReplaced(state, context);
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
      Liquidlogged.AllFluids liquid = Liquidlogged.AllFluids.AIR;
      if (fluidState.getType() == Fluids.WATER) {
         liquid = Liquidlogged.AllFluids.WATER;
      } else if (fluidState.getType() == Fluids.LAVA) {
         liquid = Liquidlogged.AllFluids.LAVA;
      } else if (fluidState.getType() == JNEFluids.ECTOPLASM_SOURCE.get()) {
         liquid = Liquidlogged.AllFluids.ECTOPLASM;
      }

      return (BlockState)((BlockState)this.defaultBlockState().setValue(LIQUIDLOGGED, liquid)).setValue(OPACITY, 1);
   }

   protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
      if (state.getValue(LIQUIDLOGGED) == Liquidlogged.AllFluids.WATER) {
         level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
      } else if (state.getValue(LIQUIDLOGGED) == Liquidlogged.AllFluids.LAVA) {
         level.scheduleTick(pos, Fluids.LAVA, Fluids.LAVA.getTickDelay(level));
      } else if (state.getValue(LIQUIDLOGGED) == Liquidlogged.AllFluids.ECTOPLASM) {
         level.scheduleTick(pos, (Fluid)JNEFluids.ECTOPLASM_SOURCE.get(), ((FlowingFluid)JNEFluids.ECTOPLASM_SOURCE.get()).getTickDelay(level));
      }

      return state;
   }

   public FluidState getFluidState(BlockState state) {
      if (state.getValue(LIQUIDLOGGED) == Liquidlogged.AllFluids.WATER) {
         return Fluids.WATER.getSource(true);
      } else if (state.getValue(LIQUIDLOGGED) == Liquidlogged.AllFluids.LAVA) {
         return Fluids.LAVA.getSource(true);
      } else {
         return state.getValue(LIQUIDLOGGED) == Liquidlogged.AllFluids.ECTOPLASM
            ? ((FlowingFluid)JNEFluids.ECTOPLASM_SOURCE.get()).getSource(true)
            : Fluids.EMPTY.defaultFluidState();
      }
   }

   protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      return level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP) || level.getFluidState(pos.below()).getType() != Fluids.EMPTY;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{LIQUIDLOGGED, OPACITY});
   }

   @Override
   public EnumProperty<Liquidlogged.AllFluids> getLiquidloggedBlockState() {
      return LIQUIDLOGGED;
   }

   protected void spawnDestroyParticles(Level level, Player player, BlockPos pos, BlockState state) {
      level.playSound(null, pos, this.soundType.getBreakSound(), SoundSource.BLOCKS, this.soundType.volume, this.soundType.pitch);
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
   ) {
      if (!level.isClientSide) {
         Integer opacity = (Integer)state.getValue(OPACITY);
         if (stack.is(JNETags.Items.FROGMIST_VISIBLE_ITEMS) && !player.isSecondaryUseActive() && opacity < 4) {
            level.playSound(null, pos, this.getSoundType(state, level, pos, player).getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
            level.setBlock(pos, (BlockState)state.cycle(OPACITY), 3);
            return ItemInteractionResult.SUCCESS;
         }
      }

      return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
   }
}
