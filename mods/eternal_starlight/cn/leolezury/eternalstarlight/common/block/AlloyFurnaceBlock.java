package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.block.entity.AlloyFurnaceBlockEntity;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.util.ESBlockUtil;
import com.mojang.serialization.MapCodec;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.WorldlyContainerHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.Nullable;

public class AlloyFurnaceBlock extends BaseEntityBlock implements WorldlyContainerHolder, WeatheringGolemSteel {
   public static final MapCodec<AlloyFurnaceBlock> CODEC = simpleCodec(AlloyFurnaceBlock::new);
   public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
   public static final IntegerProperty X_OFFSET = IntegerProperty.create("x_offset", 0, 1);
   public static final IntegerProperty Y_OFFSET = IntegerProperty.create("y_offset", 0, 2);
   public static final IntegerProperty Z_OFFSET = IntegerProperty.create("z_offset", 0, 2);
   private static final Map<Pair<Direction, Triple<Integer, Integer, Integer>>, VoxelShape> SHAPES = (Map<Pair<Direction, Triple<Integer, Integer, Integer>>, VoxelShape>)Util.make(
      () -> {
         Map<Pair<Direction, Triple<Integer, Integer, Integer>>, VoxelShape> map = new HashMap<>();

         for (int x = 0; x <= 2; x++) {
            for (int z = 0; z <= 2; z++) {
               for (int y = 0; y <= 2; y++) {
                  VoxelShape shape = Shapes.block();
                  if (x == 0 && y == 0 && z == 2) {
                     shape = Shapes.or(Block.box(0.0, 0.0, 0.0, 4.0, 16.0, 16.0), Block.box(4.0, 0.0, 0.0, 16.0, 16.0, 6.0));
                  } else if (x == 1 && y == 0 && z == 2) {
                     shape = Shapes.or(Block.box(0.0, 0.0, 0.0, 4.0, 16.0, 16.0), Block.box(4.0, 0.0, 10.0, 16.0, 16.0, 16.0));
                  } else if (x == 0 && y == 1 && z == 2) {
                     shape = Shapes.or(
                        Block.box(0.0, 0.0, 0.0, 4.0, 16.0, 16.0),
                        new VoxelShape[]{Block.box(4.0, 0.0, 0.0, 16.0, 12.0, 6.0), Block.box(7.0, 12.0, 0.0, 13.0, 16.0, 3.0)}
                     );
                  } else if (x == 1 && y == 1 && z == 2) {
                     shape = Shapes.or(
                        Block.box(0.0, 0.0, 0.0, 4.0, 16.0, 16.0),
                        new VoxelShape[]{Block.box(4.0, 0.0, 10.0, 16.0, 12.0, 16.0), Block.box(7.0, 12.0, 13.0, 13.0, 16.0, 16.0)}
                     );
                  } else if (x == 0 && y == 2 && z == 2) {
                     shape = Block.box(0.0, 0.0, 0.0, 13.0, 6.0, 3.0);
                  } else if (x == 1 && y == 2 && z == 2) {
                     shape = Block.box(0.0, 0.0, 13.0, 13.0, 6.0, 16.0);
                  } else if (x == 0 && y == 2 && z == 1) {
                     shape = Block.box(7.0, 0.0, 0.0, 16.0, 6.0, 3.0);
                  } else if (x == 1 && y == 2 && z == 1) {
                     shape = Shapes.or(
                        Block.box(7.0, 0.0, 13.0, 16.0, 6.0, 16.0),
                        new VoxelShape[]{Block.box(0.0, 0.0, 7.0, 5.0, 8.0, 15.0), Block.box(0.0, 8.0, 6.0, 6.0, 12.0, 16.0)}
                     );
                  } else if (x == 0 && y == 2) {
                     shape = Shapes.or(Block.box(3.0, 0.0, 0.0, 13.0, 12.0, 9.0), Block.box(2.0, 12.0, 0.0, 14.0, 16.0, 10.0));
                  } else if (x == 1 && y == 2) {
                     shape = Shapes.or(
                        Block.box(3.0, 0.0, 15.0, 13.0, 12.0, 16.0),
                        new VoxelShape[]{
                           Block.box(2.0, 12.0, 14.0, 14.0, 16.0, 16.0),
                           Block.box(13.0, 0.0, 7.0, 16.0, 8.0, 15.0),
                           Block.box(12.0, 8.0, 6.0, 16.0, 12.0, 16.0)
                        }
                     );
                  }

                  for (Direction direction : Direction.values()) {
                     if (direction.getAxis() != Axis.Y) {
                        map.put(Pair.of(direction, Triple.of(x, y, z)), ESBlockUtil.rotateVoxelShape(shape, -90, 180, direction));
                     }
                  }
               }
            }
         }

         return map;
      }
   );
   private static final Map<Item, AlloyFurnaceCoolingItem> COOLING_REGISTRY = new HashMap<>();

   public AlloyFurnaceBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(FACING, Direction.NORTH)).setValue(X_OFFSET, 0))
               .setValue(Y_OFFSET, 0))
            .setValue(Z_OFFSET, 1)
      );
   }

   protected MapCodec<AlloyFurnaceBlock> codec() {
      return CODEC;
   }

   public static void registerCoolingItem(Item item, AlloyFurnaceCoolingItem coolingItem) {
      COOLING_REGISTRY.put(item, coolingItem);
   }

   public static void registerCoolingItem(TagKey<Item> itemTag, AlloyFurnaceCoolingItem coolingItem) {
      for (Holder<Item> holder : BuiltInRegistries.ITEM.getTagOrEmpty(itemTag)) {
         COOLING_REGISTRY.put((Item)holder.value(), coolingItem);
      }
   }

   public static AlloyFurnaceCoolingItem getCoolingItem(Item item) {
      return COOLING_REGISTRY.get(item);
   }

   public static int getCoolDuration(Item item) {
      AlloyFurnaceCoolingItem coolingItem = getCoolingItem(item);
      return coolingItem == null ? 0 : coolingItem.duration();
   }

   public static int getCoolEfficiency(Item item) {
      AlloyFurnaceCoolingItem coolingItem = getCoolingItem(item);
      return coolingItem == null ? 0 : coolingItem.efficiency();
   }

   public static Map<Item, AlloyFurnaceCoolingItem> getCoolingRegistry() {
      return Collections.unmodifiableMap(COOLING_REGISTRY);
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      Level level = context.getLevel();
      BlockPos pos = context.getClickedPos();
      Player player = context.getPlayer();
      CollisionContext collisionContext = player == null ? CollisionContext.empty() : CollisionContext.of(player);
      Direction facing = context.getHorizontalDirection().getOpposite();
      VoxelShape shape = Shapes.empty();

      for (int x = 0; x <= 1; x++) {
         for (int z = -1; z <= 1; z++) {
            for (int y = 0; y <= 2; y++) {
               Vec3 rotated = new Vec3(x, 0.0, z).yRot((-facing.toYRot() + 90.0F) * (float) (Math.PI / 180.0));
               int rotatedX = Math.round((float)rotated.x);
               int rotatedZ = Math.round((float)rotated.z);
               if (!level.getBlockState(pos.offset(rotatedX, y, rotatedZ)).canBeReplaced(context)
                  || !level.getWorldBorder().isWithinBounds(pos.offset(rotatedX, y, rotatedZ))) {
                  return null;
               }

               shape = Shapes.or(
                  shape,
                  ((BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(X_OFFSET, x)).setValue(Y_OFFSET, y)).setValue(Z_OFFSET, z + 1))
                     .getCollisionShape(level, pos, collisionContext)
                     .move(pos.getX() + rotatedX, pos.getY() + y, pos.getZ() + rotatedZ)
               );
            }
         }
      }

      if (!level.isUnobstructed(null, shape)) {
         return null;
      }

      BlockState state = super.getStateForPlacement(context);
      return state != null ? (BlockState)state.setValue(FACING, facing) : null;
   }

   public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
      super.setPlacedBy(level, pos, state, placer, stack);
      Direction facing = (Direction)state.getValue(FACING);

      for (int x = 0; x <= 1; x++) {
         for (int z = -1; z <= 1; z++) {
            for (int y = 0; y <= 2; y++) {
               if (x != 0 || y != 0 || z != 0) {
                  Vec3 rotated = new Vec3(x, 0.0, z).yRot((-facing.toYRot() + 90.0F) * (float) (Math.PI / 180.0));
                  int rotatedX = Math.round((float)rotated.x);
                  int rotatedZ = Math.round((float)rotated.z);
                  level.setBlockAndUpdate(
                     pos.offset(rotatedX, y, rotatedZ),
                     (BlockState)((BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(FACING, facing)).setValue(X_OFFSET, x))
                           .setValue(Y_OFFSET, y))
                        .setValue(Z_OFFSET, z + 1)
                  );
               }
            }
         }
      }
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
   ) {
      return this.use(stack, state, level, pos, player);
   }

   @Override
   public void spawnWaxOrScrapeParticles(Level level, BlockPos pos, ParticleOptions particle) {
      BlockState state = level.getBlockState(pos);
      Direction facing = (Direction)state.getValue(FACING);
      Vec3 centerRotated = new Vec3(((Integer)state.getValue(X_OFFSET)).intValue(), 0.0, (Integer)state.getValue(Z_OFFSET) - 1)
         .yRot((-facing.toYRot() + 90.0F) * (float) (Math.PI / 180.0));
      BlockPos centerPos = pos.offset(-Math.round((float)centerRotated.x), -(Integer)state.getValue(Y_OFFSET), -Math.round((float)centerRotated.z));

      for (int x = 0; x <= 1; x++) {
         for (int z = -1; z <= 1; z++) {
            for (int y = 0; y <= 1; y++) {
               Vec3 rotated = new Vec3(x, 0.0, z).yRot((-facing.toYRot() + 90.0F) * (float) (Math.PI / 180.0));
               int rotatedX = Math.round((float)rotated.x);
               int rotatedZ = Math.round((float)rotated.z);
               BlockPos partPos = centerPos.offset(rotatedX, y, rotatedZ);
               if (z == 1) {
                  ParticleUtils.spawnParticleInBlock(level, partPos, 3, particle);
               } else {
                  ParticleUtils.spawnParticlesOnBlockFaces(level, partPos, particle, UniformInt.of(3, 5));
               }
            }
         }
      }
   }

   @Override
   public void placeTransformedBlock(Level level, BlockPos pos, BlockState state) {
      Direction facing = (Direction)state.getValue(FACING);
      Vec3 centerRotated = new Vec3(((Integer)state.getValue(X_OFFSET)).intValue(), 0.0, (Integer)state.getValue(Z_OFFSET) - 1)
         .yRot((-facing.toYRot() + 90.0F) * (float) (Math.PI / 180.0));
      BlockPos centerPos = pos.offset(-Math.round((float)centerRotated.x), -(Integer)state.getValue(Y_OFFSET), -Math.round((float)centerRotated.z));
      level.setBlockAndUpdate(centerPos, (BlockState)((BlockState)((BlockState)state.setValue(X_OFFSET, 0)).setValue(Y_OFFSET, 0)).setValue(Z_OFFSET, 1));

      for (int x = 0; x <= 1; x++) {
         for (int z = -1; z <= 1; z++) {
            for (int y = 0; y <= 2; y++) {
               if (x != 0 || y != 0 || z != 0) {
                  Vec3 rotated = new Vec3(x, 0.0, z).yRot((-facing.toYRot() + 90.0F) * (float) (Math.PI / 180.0));
                  int rotatedX = Math.round((float)rotated.x);
                  int rotatedZ = Math.round((float)rotated.z);
                  BlockPos partPos = centerPos.offset(rotatedX, y, rotatedZ);
                  if (level.getBlockState(partPos).canBeReplaced() && level.getWorldBorder().isWithinBounds(partPos)) {
                     level.setBlockAndUpdate(
                        partPos, (BlockState)((BlockState)((BlockState)state.setValue(X_OFFSET, x)).setValue(Y_OFFSET, y)).setValue(Z_OFFSET, z + 1)
                     );
                  }
               }
            }
         }
      }
   }

   public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
      this.changeOverTime(blockState, serverLevel, blockPos, randomSource);
   }

   public boolean isRandomlyTicking(BlockState state) {
      return !this.isOxidized() && !this.isWaxed();
   }

   protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
      if (level.isClientSide) {
         return InteractionResult.SUCCESS;
      }

      Direction facing = (Direction)state.getValue(FACING);
      int x = (Integer)state.getValue(X_OFFSET);
      int z = (Integer)state.getValue(Z_OFFSET) - 1;
      Vec3 rotated = new Vec3(x, 0.0, z).yRot((-facing.toYRot() + 90.0F) * (float) (Math.PI / 180.0));
      int rotatedX = Math.round((float)rotated.x);
      int rotatedZ = Math.round((float)rotated.z);
      BlockPos centerPos = pos.offset(-rotatedX, -(Integer)state.getValue(Y_OFFSET), -rotatedZ);
      if (level.getBlockEntity(centerPos) instanceof AlloyFurnaceBlockEntity entity) {
         player.openMenu(entity);
      }

      return InteractionResult.CONSUME;
   }

   protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
      if (!state.is(newState.getBlock())) {
         if (level.getBlockEntity(pos) instanceof AlloyFurnaceBlockEntity entity) {
            if (!entity.isValidBlockState(newState)) {
               if (level instanceof ServerLevel) {
                  Containers.dropContents(level, pos, entity);
               }

               super.onRemove(state, level, pos, newState, isMoving);
            }
         } else {
            super.onRemove(state, level, pos, newState, isMoving);
         }
      }

      Direction facing = (Direction)state.getValue(FACING);
      if ((Integer)state.getValue(X_OFFSET) == 0
         && (Integer)state.getValue(Y_OFFSET) == 0
         && (Integer)state.getValue(Z_OFFSET) == 1
         && (
            !newState.is(this)
               || newState.getValue(FACING) != facing
               || (Integer)newState.getValue(X_OFFSET) != 0
               || (Integer)newState.getValue(Y_OFFSET) != 0
               || (Integer)newState.getValue(Z_OFFSET) != 1
         )) {
         for (int x = 0; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
               for (int y = 0; y <= 2; y++) {
                  Vec3 rotated = new Vec3(x, 0.0, z).yRot((-facing.toYRot() + 90.0F) * (float) (Math.PI / 180.0));
                  int rotatedX = Math.round((float)rotated.x);
                  int rotatedZ = Math.round((float)rotated.z);
                  if (level.getBlockState(pos.offset(rotatedX, y, rotatedZ)).is(this) && (x != 0 || y != 0 || z != 0)) {
                     level.destroyBlock(pos.offset(rotatedX, y, rotatedZ), false);
                  }
               }
            }
         }
      } else {
         int x = (Integer)state.getValue(X_OFFSET);
         int z = (Integer)state.getValue(Z_OFFSET) - 1;
         Vec3 rotated = new Vec3(x, 0.0, z).yRot((-facing.toYRot() + 90.0F) * (float) (Math.PI / 180.0));
         int rotatedX = Math.round((float)rotated.x);
         int rotatedZ = Math.round((float)rotated.z);
         this.checkStructure(level, pos.offset(-rotatedX, -(Integer)state.getValue(Y_OFFSET), -rotatedZ));
      }
   }

   public void checkStructure(LevelAccessor level, BlockPos pos) {
      BlockState state = level.getBlockState(pos);
      if (state.is(this) && (Integer)state.getValue(X_OFFSET) == 0 && (Integer)state.getValue(Y_OFFSET) == 0 && (Integer)state.getValue(Z_OFFSET) == 1) {
         Direction facing = (Direction)state.getValue(FACING);

         for (int x = 0; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
               for (int y = 0; y <= 2; y++) {
                  Vec3 rotated = new Vec3(x, 0.0, z).yRot((-facing.toYRot() + 90.0F) * (float) (Math.PI / 180.0));
                  int rotatedX = Math.round((float)rotated.x);
                  int rotatedZ = Math.round((float)rotated.z);
                  BlockState partState = level.getBlockState(pos.offset(rotatedX, y, rotatedZ));
                  if (!partState.is(this)
                     || partState.getValue(FACING) != facing
                     || (Integer)partState.getValue(X_OFFSET) != x
                     || (Integer)partState.getValue(Y_OFFSET) != y
                     || (Integer)partState.getValue(Z_OFFSET) != z + 1) {
                     level.destroyBlock(pos, false);
                     return;
                  }
               }
            }
         }
      }
   }

   protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return SHAPES.getOrDefault(
         Pair.of(
            (Direction)state.getValue(FACING),
            Triple.of((Integer)state.getValue(X_OFFSET), (Integer)state.getValue(Y_OFFSET), (Integer)state.getValue(Z_OFFSET))
         ),
         Shapes.block()
      );
   }

   protected BlockState rotate(BlockState state, Rotation rotation) {
      return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
   }

   protected BlockState mirror(BlockState state, Mirror mirror) {
      return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, X_OFFSET, Z_OFFSET, Y_OFFSET});
   }

   public RenderShape getRenderShape(BlockState blockState) {
      return blockState.getValue(X_OFFSET) == 0 && blockState.getValue(Y_OFFSET) == 0 && blockState.getValue(Z_OFFSET) == 1
         ? RenderShape.ENTITYBLOCK_ANIMATED
         : RenderShape.INVISIBLE;
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
      return blockState.getValue(X_OFFSET) == 0 && blockState.getValue(Y_OFFSET) == 0 && blockState.getValue(Z_OFFSET) == 1
         ? new AlloyFurnaceBlockEntity(blockPos, blockState)
         : null;
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
      return blockState.getValue(X_OFFSET) == 0 && blockState.getValue(Y_OFFSET) == 0 && blockState.getValue(Z_OFFSET) == 1
         ? createTickerHelper(blockEntityType, ESBlockEntities.ALLOY_FURNACE.get(), AlloyFurnaceBlockEntity::tick)
         : null;
   }

   public WorldlyContainer getContainer(BlockState state, LevelAccessor level, BlockPos pos) {
      Direction facing = (Direction)state.getValue(FACING);
      int x = (Integer)state.getValue(X_OFFSET);
      int y = (Integer)state.getValue(Y_OFFSET);
      int z = (Integer)state.getValue(Z_OFFSET) - 1;
      Vec3 rotated = new Vec3(x, 0.0, z).yRot(((int)(-facing.toYRot()) + 90) % 360 * (float) (Math.PI / 180.0));
      int rotatedX = Math.round((float)rotated.x);
      int rotatedZ = Math.round((float)rotated.z);
      BlockPos centerPos = pos.offset(-rotatedX, -y, -rotatedZ);
      return (WorldlyContainer)(level.getBlockEntity(centerPos) instanceof AlloyFurnaceBlockEntity entity ? entity : new AlloyFurnaceBlock.EmptyContainer());
   }

   static class EmptyContainer extends SimpleContainer implements WorldlyContainer {
      public EmptyContainer() {
         super(0);
      }

      public int[] getSlotsForFace(Direction side) {
         return new int[0];
      }

      public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
         return false;
      }

      public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
         return false;
      }
   }
}
