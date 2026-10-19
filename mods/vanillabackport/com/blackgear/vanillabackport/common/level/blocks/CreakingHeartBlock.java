package com.blackgear.vanillabackport.common.level.blocks;

import com.blackgear.vanillabackport.client.registries.ModSoundEvents;
import com.blackgear.vanillabackport.common.level.blockentities.CreakingHeartBlockEntity;
import com.blackgear.vanillabackport.common.level.blocks.states.CreakingHeartState;
import com.blackgear.vanillabackport.common.registries.ModBlockEntities;
import com.blackgear.vanillabackport.common.registries.ModBlockStateProperties;
import com.blackgear.vanillabackport.core.VanillaBackport;
import com.blackgear.vanillabackport.core.data.tags.ModBlockTags;
import com.blackgear.vanillabackport.core.util.LevelUtils;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.Nullable;

public class CreakingHeartBlock extends BaseEntityBlock {
   public static final MapCodec<CreakingHeartBlock> CODEC = simpleCodec(CreakingHeartBlock::new);
   public static final EnumProperty<Axis> AXIS = BlockStateProperties.AXIS;
   public static final EnumProperty<CreakingHeartState> STATE = ModBlockStateProperties.CREAKING_HEART_STATE;
   public static final BooleanProperty NATURAL = ModBlockStateProperties.NATURAL;

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }

   public CreakingHeartBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(AXIS, Axis.Y)).setValue(STATE, CreakingHeartState.UPROOTED))
            .setValue(NATURAL, false)
      );
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new CreakingHeartBlockEntity(pos, state);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
      if (level.isClientSide()) {
         return null;
      } else {
         return state.getValue(STATE) != CreakingHeartState.UPROOTED
            ? createTickerHelper(blockEntityType, ModBlockEntities.CREAKING_HEART.get(), CreakingHeartBlockEntity::serverTick)
            : null;
      }
   }

   public static boolean isNaturalNight(Level level) {
      return LevelUtils.isMoonVisible(level);
   }

   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      if (((Boolean)VanillaBackport.COMMON_CONFIG.doCreakingHeartsWorkOnDay.get() || isNaturalNight(level))
         && state.getValue(STATE) != CreakingHeartState.UPROOTED
         && random.nextInt(16) == 0
         && isSurroundedByLogs(level, pos)) {
         level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), ModSoundEvents.CREAKING_HEART_IDLE.get(), SoundSource.BLOCKS, 1.0F, 1.0F, false);
      }
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.MODEL;
   }

   public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
      level.scheduleTick(pos, this, 1);
      return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
   }

   public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      BlockState updatedState = updateState(state, level, pos);
      if (updatedState != state) {
         level.setBlock(pos, updatedState, 3);
      }
   }

   private static BlockState updateState(BlockState state, Level level, BlockPos pos) {
      boolean hasRequiredLogs = hasRequiredLogs(state, level, pos);
      boolean isUprooted = state.getValue(STATE) == CreakingHeartState.UPROOTED;
      return hasRequiredLogs && isUprooted
         ? (BlockState)state.setValue(
            STATE,
            !VanillaBackport.COMMON_CONFIG.doCreakingHeartsWorkOnDay.get() && !isNaturalNight(level) ? CreakingHeartState.DORMANT : CreakingHeartState.AWAKE
         )
         : state;
   }

   public static boolean hasRequiredLogs(BlockState state, LevelReader level, BlockPos pos) {
      Axis axis = (Axis)state.getValue(AXIS);

      for (Direction direction : directions(axis)) {
         BlockPos neighborPos = pos.relative(direction);
         BlockState neighborState = level.getBlockState(neighborPos);
         if (!neighborState.is(ModBlockTags.CREAKING_HEART_HOLDERS)) {
            return false;
         }

         if (neighborState.hasProperty(AXIS) && neighborState.getValue(AXIS) != axis) {
            return false;
         }
      }

      return true;
   }

   private static Direction[] directions(Axis axis) {
      return switch (axis) {
         case X -> new Direction[]{Direction.NORTH, Direction.SOUTH};
         case Y -> new Direction[]{Direction.UP, Direction.DOWN};
         case Z -> new Direction[]{Direction.EAST, Direction.WEST};
         default -> throw new MatchException(null, null);
      };
   }

   private static boolean isSurroundedByLogs(LevelAccessor level, BlockPos pos) {
      for (Direction direction : Direction.values()) {
         BlockPos neighborPos = pos.relative(direction);
         BlockState neighborState = level.getBlockState(neighborPos);
         if (!neighborState.is(ModBlockTags.CREAKING_HEART_HOLDERS)) {
            return false;
         }
      }

      return true;
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return updateState((BlockState)this.defaultBlockState().setValue(AXIS, context.getClickedFace().getAxis()), context.getLevel(), context.getClickedPos());
   }

   public BlockState rotate(BlockState state, Rotation rotation) {
      return RotatedPillarBlock.rotatePillar(state, rotation);
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{AXIS, STATE, NATURAL});
   }

   public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
      if (level.getBlockEntity(pos) instanceof CreakingHeartBlockEntity heart) {
         heart.removeProtector(player.damageSources().playerAttack(player));
         this.tryAwardExperience(player, state, level, pos);
      }

      return super.playerWillDestroy(level, pos, state, player);
   }

   private void tryAwardExperience(Player player, BlockState state, Level level, BlockPos pos) {
      if (!player.getAbilities().instabuild && !player.isSpectator() && (Boolean)state.getValue(NATURAL) && level instanceof ServerLevel server) {
         this.popExperience(server, pos, level.random.nextIntBetweenInclusive(20, 24));
      }
   }

   public boolean hasAnalogOutputSignal(BlockState state) {
      return true;
   }

   public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
      if (state.getValue(STATE) == CreakingHeartState.UPROOTED) {
         return 0;
      } else {
         return level.getBlockEntity(pos) instanceof CreakingHeartBlockEntity heart ? heart.getAnalogOutputSignal() : 0;
      }
   }
}
