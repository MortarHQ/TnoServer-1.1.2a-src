package com.iafenvoy.iceandfire.item.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class ReturningStateBlock extends Block {
   public static final BooleanProperty REVERTS = BooleanProperty.create("revert");
   private final BlockState returnState;

   public ReturningStateBlock(Properties props, BlockState returnToState) {
      super(props);
      this.returnState = returnToState;
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(REVERTS, Boolean.FALSE));
   }

   public static ReturningStateBlock builder(
      float hardness,
      float resistance,
      SoundType sound,
      boolean slippery,
      MapColor color,
      NoteBlockInstrument instrument,
      PushReaction reaction,
      boolean ignited,
      BlockState returnToState
   ) {
      Properties props = Properties.of().mapColor(color).sound(sound).strength(hardness, resistance).friction(0.98F).randomTicks();
      if (instrument != null) {
         props.instrument(instrument);
      }

      if (reaction != null) {
         props.pushReaction(reaction);
      }

      if (ignited) {
         props.ignitedByLava();
      }

      return new ReturningStateBlock(props, returnToState);
   }

   public static ReturningStateBlock builder(
      float hardness,
      float resistance,
      SoundType sound,
      MapColor color,
      NoteBlockInstrument instrument,
      PushReaction reaction,
      boolean ignited,
      BlockState returnToState
   ) {
      Properties props = Properties.of().mapColor(color).sound(sound).strength(hardness, resistance).randomTicks();
      if (instrument != null) {
         props.instrument(instrument);
      }

      if (reaction != null) {
         props.pushReaction(reaction);
      }

      if (ignited) {
         props.ignitedByLava();
      }

      return new ReturningStateBlock(props, returnToState);
   }

   public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
      if (!worldIn.isClientSide) {
         if (!worldIn.hasChunksAt(pos.offset(-3, -3, -3), pos.offset(3, 3, 3))) {
            return;
         }

         if ((Boolean)state.getValue(REVERTS) && rand.nextInt(3) == 0) {
            worldIn.setBlockAndUpdate(pos, this.returnState);
         }
      }
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{REVERTS});
   }
}
