package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class NocturnalMilletTopBlock extends CropBlock {
   public static final MapCodec<NocturnalMilletTopBlock> CODEC = simpleCodec(NocturnalMilletTopBlock::new);
   public static final IntegerProperty AGE = BlockStateProperties.AGE_2;
   public static final BooleanProperty FORGOTTEN = BooleanProperty.create("forgotten");
   public static final BooleanProperty WITHERED = BooleanProperty.create("withered");
   private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
      Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0), Block.box(0.0, 0.0, 0.0, 16.0, 10.0, 16.0), Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
   };

   public MapCodec<NocturnalMilletTopBlock> codec() {
      return CODEC;
   }

   public NocturnalMilletTopBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)this.getStateDefinition().any()).setValue(this.getAgeProperty(), 0)).setValue(FORGOTTEN, false))
            .setValue(WITHERED, false)
      );
   }

   protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
      return state.is((Block)ESBlocks.NOCTURNAL_MILLET_STALK.get());
   }

   protected IntegerProperty getAgeProperty() {
      return AGE;
   }

   public int getMaxAge() {
      return 2;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{AGE, FORGOTTEN, WITHERED});
   }

   protected boolean isRandomlyTicking(BlockState state) {
      return true;
   }

   protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if (level.getRawBrightness(pos, 0) >= 7) {
         int age = this.getAge(state);
         if ((Boolean)state.getValue(WITHERED)) {
            for (int x = -5; x <= 5; x++) {
               for (int y = -1; y <= 1; y++) {
                  for (int z = -5; z <= 5; z++) {
                     BlockPos nearbyPos = pos.offset(x, y, z);
                     Block block = level.getBlockState(nearbyPos).getBlock();
                     if (block instanceof CropBlock && !(block instanceof NocturnalMilletBottomBlock) && !(block instanceof NocturnalMilletTopBlock)) {
                        level.destroyBlock(nearbyPos, false);
                     }
                  }
               }
            }
         } else if ((Boolean)state.getValue(FORGOTTEN)) {
            if (age == 0) {
               level.setBlock(pos, (BlockState)((BlockState)state.setValue(AGE, age + 1)).setValue(FORGOTTEN, true), 2);
            } else if (age == 1) {
               int absorption = 0;

               for (int x = -5; x <= 5; x++) {
                  for (int y = -1; y <= 1; y++) {
                     for (int z = -5; z <= 5; z++) {
                        BlockPos nearbyPos = pos.offset(x, y, z);
                        Block block = level.getBlockState(nearbyPos).getBlock();
                        if (block instanceof CropBlock && !(block instanceof NocturnalMilletBottomBlock) && !(block instanceof NocturnalMilletTopBlock)) {
                           level.destroyBlock(nearbyPos, false);
                           absorption++;
                        }

                        if (absorption == 2) {
                           break;
                        }
                     }
                  }
               }

               level.setBlock(pos, (BlockState)((BlockState)state.setValue(AGE, random.nextInt(4 - absorption) == 0 ? 2 : age)).setValue(FORGOTTEN, true), 2);
            }
         } else if (age < this.getMaxAge() - 1) {
            level.setBlock(pos, this.getStateForAge(age + random.nextInt(2)), 2);
         } else {
            level.setBlock(pos, this.getStateForAge(this.getMaxAge()), 2);
         }
      }
   }

   public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
      return (Boolean)state.getValue(WITHERED);
   }

   public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
      BlockState belowState = level.getBlockState(pos.below());
      level.setBlockAndUpdate(pos, (BlockState)state.setValue(WITHERED, false));
      if (belowState.is((Block)ESBlocks.NOCTURNAL_MILLET_STALK.get()) && (Boolean)belowState.getValue(WITHERED)) {
         level.setBlockAndUpdate(pos.below(), (BlockState)belowState.setValue(WITHERED, false));
      }
   }

   protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
      BlockState belowState = level.getBlockState(pos.below());
      return direction == Direction.DOWN && belowState.is((Block)ESBlocks.NOCTURNAL_MILLET_STALK.get())
         ? (BlockState)state.setValue(FORGOTTEN, (Boolean)belowState.getValue(FORGOTTEN))
         : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
   }

   protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
      int age = (Integer)state.getValue(AGE);
      boolean forgotten = (Boolean)state.getValue(FORGOTTEN);
      if (age == 2) {
         popResource(level, pos, new ItemStack(forgotten ? (ItemLike)ESItems.FORGOTTEN_NOCTURNAL_MILLET.get() : (ItemLike)ESItems.NOCTURNAL_MILLET.get(), 1));
         level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
         BlockState newState;
         if (!forgotten) {
            if (level.getRandom().nextDouble() < 0.2) {
               newState = (BlockState)((BlockState)state.setValue(AGE, 1)).setValue(WITHERED, true);
               level.setBlock(pos.below(), (BlockState)level.getBlockState(pos.below()).setValue(WITHERED, true), 2);
            } else {
               newState = (BlockState)state.setValue(AGE, 1);
            }
         } else {
            newState = (BlockState)state.setValue(AGE, 1);
         }

         level.setBlock(pos, newState, 2);
         level.gameEvent(GameEvent.BLOCK_CHANGE, pos, Context.of(player, newState));
         return InteractionResult.sidedSuccess(level.isClientSide);
      } else {
         return super.useWithoutItem(state, level, pos, player, hitResult);
      }
   }

   protected ItemLike getBaseSeedId() {
      return (ItemLike)ESItems.NOCTURNAL_MILLET_SEEDS.get();
   }

   protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return SHAPE_BY_AGE[state.getValue(WITHERED) && !state.getValue(FORGOTTEN) ? 1 : this.getAge(state)];
   }
}
