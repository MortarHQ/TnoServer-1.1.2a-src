package com.blackgear.vanillabackport.common.level.blocks;

import com.blackgear.vanillabackport.client.registries.ModParticles;
import com.blackgear.vanillabackport.client.registries.ModSoundEvents;
import com.blackgear.vanillabackport.common.level.blockentities.PotentSulfurBlockEntity;
import com.blackgear.vanillabackport.common.level.blocks.states.PotentSulfurState;
import com.blackgear.vanillabackport.common.registries.ModBlockEntities;
import com.blackgear.vanillabackport.common.registries.ModBlockStateProperties;
import com.blackgear.vanillabackport.core.VanillaBackport;
import com.blackgear.vanillabackport.core.data.tags.ModBlockTags;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public class PotentSulfurBlock extends BaseEntityBlock {
   public static final MapCodec<PotentSulfurBlock> CODEC = simpleCodec(PotentSulfurBlock::new);
   public static final EnumProperty<PotentSulfurState> STATE = ModBlockStateProperties.POTENT_SULFUR_STATE;

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }

   public PotentSulfurBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)this.defaultBlockState().setValue(STATE, PotentSulfurState.DRY));
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{STATE});
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new PotentSulfurBlockEntity(pos, state);
   }

   protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
      return validBlockState(state, level, pos);
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return validBlockState(this.defaultBlockState(), context.getLevel(), context.getClickedPos());
   }

   private static BlockState validBlockState(BlockState state, LevelReader level, BlockPos pos) {
      if (!level.getFluidState(pos.above()).isSourceOfType(Fluids.WATER)) {
         return (BlockState)state.setValue(STATE, PotentSulfurState.DRY);
      }

      BlockState belowState = level.getBlockState(pos.below());
      if (belowState.is(ModBlockTags.CAUSES_CONTINUOUS_GEYSER_ERUPTIONS)) {
         return (BlockState)state.setValue(STATE, PotentSulfurState.CONTINUOUS);
      }

      if (!belowState.is(ModBlockTags.CAUSES_PERIODIC_GEYSER_ERUPTIONS)) {
         return (BlockState)state.setValue(STATE, PotentSulfurState.WET);
      }

      boolean isGeyser = state.getValue(STATE) == PotentSulfurState.ERUPTING || state.getValue(STATE) == PotentSulfurState.DORMANT;
      if (!isGeyser && level.getBlockEntity(pos) instanceof PotentSulfurBlockEntity potentSulfur) {
         potentSulfur.resetCountdown();
      }

      return state.getValue(STATE) == PotentSulfurState.ERUPTING ? state : (BlockState)state.setValue(STATE, PotentSulfurState.DORMANT);
   }

   protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
      super.onPlace(state, level, pos, oldState, movedByPiston);
      if (state.getValue(STATE) == PotentSulfurState.ERUPTING || state.getValue(STATE) == PotentSulfurState.CONTINUOUS) {
         level.blockEvent(pos, this, 0, 0);
         level.playSound(
            null,
            pos,
            state.getValue(STATE) == PotentSulfurState.CONTINUOUS ? ModSoundEvents.GEYSER_CONTINUOUS_START.get() : ModSoundEvents.GEYSER_ERUPTION_START.get(),
            SoundSource.BLOCKS,
            1.0F,
            1.0F
         );
         level.gameEvent(GameEvent.BLOCK_ACTIVATE, pos, Context.of(state));
      }
   }

   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      if (state.getValue(STATE) != PotentSulfurState.DRY && level.getFluidState(pos.above()).isSourceOfType(Fluids.WATER)) {
         spawnBubbleParticlesAt(level, random, pos.getX(), pos.getY() + 1, pos.getZ());
         spawnBubbleParticlesAt(level, random, pos.getX(), pos.getY() + 1, pos.getZ());
         if (random.nextInt(10) == 0) {
            level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), ModSoundEvents.NOXIOUS_GAS.get(), SoundSource.AMBIENT, 1.0F, 1.0F, false);
         }
      }
   }

   private static void spawnBubbleParticlesAt(Level level, RandomSource random, double x, double y, double z) {
      level.addAlwaysVisibleParticle(
         (ParticleOptions)ModParticles.SULFUR_BUBBLES.get(), x + random.nextFloat(), y + random.nextFloat(), z + random.nextFloat(), 0.0, 0.0, 0.0
      );
   }

   public boolean triggerEvent(BlockState state, Level level, BlockPos pos, int id, int param) {
      if (level.getBlockEntity(pos) instanceof PotentSulfurBlockEntity potentSulfur) {
         potentSulfur.eruptionTick = level.getGameTime();
      }

      return true;
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
      boolean client = level.isClientSide();
      if (!(Boolean)VanillaBackport.COMMON_CONFIG.doGeysersErupt.get()) {
         return createTickerHelper(
            type,
            ModBlockEntities.POTENT_SULFUR.get(),
            state.getValue(STATE) == PotentSulfurState.DRY
               ? null
               : (client ? PotentSulfurBlockEntity.CLIENT_NOXIOUS_GAS_TICKER : PotentSulfurBlockEntity.SERVER_NAUSEA_EFFECT_TICKER)
         );
      }

      return createTickerHelper(
         type,
         ModBlockEntities.POTENT_SULFUR.get(),
         switch ((PotentSulfurState)state.getValue(STATE)) {
            case DRY -> null;
            case WET -> client ? PotentSulfurBlockEntity.CLIENT_NOXIOUS_GAS_TICKER : PotentSulfurBlockEntity.SERVER_NAUSEA_EFFECT_TICKER;
            case DORMANT -> client
               ? PotentSulfurBlockEntity.CLIENT_NOXIOUS_GAS_TICKER
               : PotentSulfurBlockEntity.sequence(PotentSulfurBlockEntity.SERVER_WAITING_COUNTDOWN_TICKER, PotentSulfurBlockEntity.SERVER_NAUSEA_EFFECT_TICKER);
            case ERUPTING -> client
               ? (BlockEntityTicker)PotentSulfurBlockEntity.CLIENT_GEYSER_PLUME_TICKER.apply((T)((SoundEvent)ModSoundEvents.GEYSER_ERUPTION_ACTIVE.get()))
               : PotentSulfurBlockEntity.sequence(PotentSulfurBlockEntity.SERVER_LAUNCH_ENTITY_TICKER, PotentSulfurBlockEntity.SERVER_WAITING_COUNTDOWN_TICKER);
            case CONTINUOUS -> client
               ? (BlockEntityTicker)PotentSulfurBlockEntity.CLIENT_GEYSER_PLUME_TICKER.apply((T)((SoundEvent)ModSoundEvents.GEYSER_CONTINUOUS_ACTIVE.get()))
               : PotentSulfurBlockEntity.SERVER_LAUNCH_ENTITY_TICKER;
         }
      );
   }

   protected RenderShape getRenderShape(BlockState state) {
      return RenderShape.MODEL;
   }
}
