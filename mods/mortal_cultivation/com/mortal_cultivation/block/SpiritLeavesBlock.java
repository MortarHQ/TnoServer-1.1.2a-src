package com.mortal_cultivation.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mortal_cultivation.registry.BlockRegistry;
import com.mortal_cultivation.registry.WorldgenRegistry;
import com.mortal_cultivation.registry.item.CultivationItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.phys.BlockHitResult;

public class SpiritLeavesBlock extends LeavesBlock {
   public static final MapCodec<SpiritLeavesBlock> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(Codec.BOOL.fieldOf("fruiting").forGetter(block -> block.fruiting), propertiesCodec()).apply(instance, SpiritLeavesBlock::new)
   );
   public static final IntegerProperty FRUITING_DAY = IntegerProperty.create("fruiting_day", 0, 31);
   private static final long TICKS_PER_DAY = 24000L;
   private static final float DAILY_FRUIT_CHANCE = 0.005F;
   private static final int FRUIT_SPACING = 3;
   private final boolean fruiting;

   public SpiritLeavesBlock(boolean fruiting, Properties properties) {
      super(properties);
      this.fruiting = fruiting;
      this.registerDefaultState((BlockState)this.defaultBlockState().setValue(FRUITING_DAY, 0));
   }

   public MapCodec<? extends SpiritLeavesBlock> codec() {
      return CODEC;
   }

   public static int fruitingDay(long dayTime) {
      return (int)Math.floorMod(Math.floorDiv(dayTime, 24000L), 32L);
   }

   public static BlockState withFruit(BlockState state, int day) {
      return (BlockState)((BlockState)((BlockState)((BlockState)((SpiritLeavesBlock)BlockRegistry.SPIRIT_FRUIT_LEAVES.get())
                  .defaultBlockState()
                  .setValue(DISTANCE, (Integer)state.getValue(DISTANCE)))
               .setValue(PERSISTENT, (Boolean)state.getValue(PERSISTENT)))
            .setValue(WATERLOGGED, (Boolean)state.getValue(WATERLOGGED)))
         .setValue(FRUITING_DAY, day);
   }

   public static boolean canGrowFruit(LevelSimulatedReader level, BlockPos pos) {
      BlockPos min = pos.offset(-3, -3, -3);
      BlockPos max = pos.offset(3, 3, 3);
      if (level instanceof LevelReader reader && !reader.hasChunksAt(min, max)) {
         return false;
      } else {
         boolean exposed = false;

         for (Direction direction : Direction.values()) {
            if (level.isStateAtPosition(pos.relative(direction), BlockStateBase::isAir)) {
               exposed = true;
               break;
            }
         }

         if (!exposed) {
            return false;
         }

         for (BlockPos nearby : BlockPos.betweenClosed(min, max)) {
            if (level.isStateAtPosition(nearby, state -> state.is((Block)BlockRegistry.SPIRIT_FRUIT_LEAVES.get()))) {
               return false;
            }
         }

         return true;
      }
   }

   protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
      if (!this.fruiting) {
         return super.useWithoutItem(state, level, pos, player, hitResult);
      }

      if (level.isClientSide) {
         return InteractionResult.SUCCESS;
      }

      ItemStack fruit = harvestFruit((ServerLevel)level, pos, player);
      if (fruit.isEmpty()) {
         return InteractionResult.PASS;
      }

      Block.popResourceFromFace(level, pos, hitResult.getDirection(), fruit);
      return InteractionResult.CONSUME;
   }

   public static ItemStack harvestFruit(ServerLevel level, BlockPos pos, Entity picker) {
      BlockState currentState = level.getBlockState(pos);
      if (!currentState.is((Block)BlockRegistry.SPIRIT_FRUIT_LEAVES.get())) {
         return ItemStack.EMPTY;
      }

      BlockState next = (BlockState)((BlockState)((BlockState)((BlockState)((SpiritLeavesBlock)BlockRegistry.SPIRIT_LEAVES.get())
                  .defaultBlockState()
                  .setValue(DISTANCE, (Integer)currentState.getValue(DISTANCE)))
               .setValue(PERSISTENT, (Boolean)currentState.getValue(PERSISTENT)))
            .setValue(WATERLOGGED, (Boolean)currentState.getValue(WATERLOGGED)))
         .setValue(FRUITING_DAY, fruitingDay(level.getDayTime()));
      if (!level.setBlock(pos, next, 3)) {
         return ItemStack.EMPTY;
      }

      level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
      level.gameEvent(GameEvent.BLOCK_CHANGE, pos, Context.of(picker, next));
      return new ItemStack((ItemLike)CultivationItems.SPIRIT_FRUIT.get());
   }

   protected boolean isRandomlyTicking(BlockState state) {
      return !this.fruiting || super.isRandomlyTicking(state);
   }

   protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if (this.decaying(state)) {
         super.randomTick(state, level, pos, random);
      } else if (!this.fruiting) {
         int day = fruitingDay(level.getDayTime());
         if ((Integer)state.getValue(FRUITING_DAY) != day) {
            BlockState next = (BlockState)state.setValue(FRUITING_DAY, day);
            int updateFlags = 0;
            if ((Integer)state.getValue(DISTANCE) < 7
               && random.nextFloat() < 0.005F
               && level.getBiome(pos).is(WorldgenRegistry.SPIRIT_FORESTS)
               && canGrowFruit(level, pos)) {
               next = withFruit(state, day);
               updateFlags = 2;
            }

            level.setBlock(pos, next, updateFlags);
         }
      }
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)super.getStateForPlacement(context).setValue(FRUITING_DAY, fruitingDay(context.getLevel().getDayTime()));
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder);
      builder.add(new Property[]{FRUITING_DAY});
   }

   public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
      return 60;
   }

   public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
      return 30;
   }
}
