package net.unusual.block_factorys_bosses.block;

import com.google.common.base.Optional;
import com.google.common.collect.Iterators;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.unusual.block_factorys_bosses.block.entity.HugeDoorBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class HugeDoorBlock extends Block implements EntityBlock {
   public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
   public static final EnumProperty<HugeDoorBlock.Part> PART = EnumProperty.create(
      "type", HugeDoorBlock.Part.class, new HugeDoorBlock.Part[]{HugeDoorBlock.Part.ROOT, HugeDoorBlock.Part.BLOCK, HugeDoorBlock.Part.HALF_BLOCK}
   );
   public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
   public static final BooleanProperty MOVING = BooleanProperty.create("moving");
   private static final VoxelShape VERTICAL_SLAB_NORTH = Shapes.box(0.0, 0.0, 0.5, 1.0, 1.0, 1.0);
   private static final VoxelShape VERTICAL_SLAB_EAST = Shapes.box(0.0, 0.0, 0.0, 0.5, 1.0, 1.0);
   private static final VoxelShape VERTICAL_SLAB_SOUTH = Shapes.box(0.0, 0.0, 0.0, 1.0, 1.0, 0.5);
   private static final VoxelShape VERTICAL_SLAB_WEST = Shapes.box(0.5, 0.0, 0.0, 1.0, 1.0, 1.0);
   private final Predicate<ItemStack> isValidKeyItem;
   private final BiFunction<BlockPos, BlockState, ? extends HugeDoorBlockEntity> blockEntitySupplier;
   public static final int openingDuration = 150;
   public static final int closingDuration = 0;

   public HugeDoorBlock(
      Properties properties, Predicate<ItemStack> isValidKeyItem, BiFunction<BlockPos, BlockState, ? extends HugeDoorBlockEntity> blockEntitySupplier
   ) {
      super(properties);
      this.isValidKeyItem = isValidKeyItem;
      this.blockEntitySupplier = blockEntitySupplier;
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.getStateDefinition().any()).setValue(FACING, Direction.NORTH))
                  .setValue(PART, HugeDoorBlock.Part.ROOT))
               .setValue(OPEN, false))
            .setValue(MOVING, false)
      );
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, PART, OPEN, MOVING});
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return state.getValue(PART) != HugeDoorBlock.Part.ROOT ? null : this.blockEntitySupplier.apply(pos, state);
   }

   protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if ((Boolean)state.getValue(MOVING)) {
         if (!(level.getBlockEntity(pos) instanceof HugeDoorBlockEntity blockEntity)) {
            return;
         }

         Boolean var8 = (Boolean)state.getValue(OPEN);
         if (blockEntity.isFinishedMoving(var8 ? 0 : 150)) {
            state = (BlockState)state.setValue(MOVING, false);
            if (!var8) {
               this.openDoor(level, state, pos);
            }
         }
      }
   }

   private boolean scheduleDoorToggle(Level level, BlockState rootBlockState, BlockPos rootPos) {
      if (level.getBlockEntity(rootPos) instanceof HugeDoorBlockEntity blockEntity) {
         if ((Boolean)rootBlockState.getValue(MOVING)) {
            return false;
         }

         boolean isClosing = (Boolean)rootBlockState.getValue(OPEN);
         if (isClosing) {
            return false;
         }

         level.setBlock(rootPos, (BlockState)rootBlockState.setValue(MOVING, true), 3);
         blockEntity.setStartedMoving();
         level.scheduleTick(rootPos, rootBlockState.getBlock(), isClosing ? 0 : 150);
         return true;
      } else {
         return false;
      }
   }

   public boolean isValidKeyItem(ItemStack stack) {
      return this.isValidKeyItem.test(stack);
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
   ) {
      if (!this.isValidKeyItem(stack)) {
         return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
      }

      BlockPos rootPos = findRootBlockPos(level, state, pos);
      if (rootPos == null) {
         return ItemInteractionResult.FAIL;
      }

      BlockState rootState = level.getBlockState(rootPos);
      if ((Boolean)rootState.getValue(OPEN)) {
         return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
      }

      if (!this.scheduleDoorToggle(level, rootState, rootPos)) {
         return ItemInteractionResult.FAIL;
      }

      stack.consume(1, player);
      return ItemInteractionResult.SUCCESS;
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      Level level = context.getLevel();
      BlockState state = (BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
      Vec3i offsetToCorner = getTopOuterOffset(state);
      BlockPos rootPos = context.getClickedPos().offset(offsetToCorner.getX() / -2, 0, offsetToCorner.getZ() / -2);
      BlockPos otherCornerPos = rootPos.offset(offsetToCorner);
      if (level.isOutsideBuildHeight(otherCornerPos)) {
         return null;
      }

      for (BlockPos blockPos : BlockPos.betweenClosed(rootPos, otherCornerPos)) {
         if (!level.getBlockState(blockPos).canBeReplaced(context) || !level.isInWorldBounds(blockPos)) {
            return null;
         }
      }

      if (!rootPos.equals(context.getClickedPos())) {
         state = (BlockState)state.setValue(PART, HugeDoorBlock.Part.BLOCK);
      }

      return state;
   }

   public void setPlacedBy(Level level, BlockPos placedPos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
      this.placeDoor(level, state, placedPos);
      super.setPlacedBy(level, placedPos, state, placer, stack);
   }

   private void placeDoor(Level level, BlockState state, BlockPos placedPos) {
      Vec3i offsetToCorner = getTopOuterOffset(state);
      BlockPos rootPos = placedPos.offset(offsetToCorner.getX() / -2, 0, offsetToCorner.getZ() / -2);
      BlockState rootState = (BlockState)state.setValue(PART, HugeDoorBlock.Part.ROOT);
      BlockState blockState = (BlockState)state.setValue(PART, HugeDoorBlock.Part.BLOCK);
      BlockState halfBlockState = (BlockState)state.setValue(PART, HugeDoorBlock.Part.HALF_BLOCK);
      Boolean isOpen = (Boolean)rootState.getValue(OPEN);
      getDoorBlocks(rootState, rootPos, true, !isOpen, isOpen, false).forEachRemaining(blockPos -> {
         if (!blockPos.equals(placedPos)) {
            level.setBlock(blockPos, blockPos.equals(rootPos) ? rootState : blockState, 3);
         }
      });
      getDoorBlocks(rootState, rootPos, false, false, isOpen, isOpen).forEachRemaining(blockPos -> level.setBlock(blockPos, halfBlockState, 3));
   }

   protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
      super.onRemove(state, level, pos, newState, movedByPiston);
      if (!state.is(newState.getBlock())) {
         this.removeDoor(level, state, pos);
      }
   }

   private void removeDoor(Level level, BlockState initiatingState, BlockPos initiatingPos) {
      BlockPos rootPos = findRootBlockPos(level, initiatingState, initiatingPos);
      if (rootPos != null) {
         if (!rootPos.equals(initiatingPos)) {
            if (initiatingState.getValue(OPEN) == level.getBlockState(rootPos).getValue(OPEN)) {
               level.setBlock(rootPos, Blocks.AIR.defaultBlockState(), 3);
            }
         } else {
            Boolean isOpen = (Boolean)initiatingState.getValue(OPEN);
            getDoorBlocks(initiatingState, initiatingPos, true, !isOpen, isOpen, true).forEachRemaining(blockPos -> {
               if (level.getBlockState(blockPos).is(initiatingState.getBlock())) {
                  level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
               }
            });
         }
      }
   }

   private void openDoor(Level level, BlockState rootBlockState, BlockPos rootPos) {
      level.setBlock(rootPos, (BlockState)rootBlockState.setValue(OPEN, true), 3);
      getDoorBlocks(rootBlockState, rootPos, false, true, false, false)
         .forEachRemaining(blockPos -> level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3));
      BlockState openState = (BlockState)((BlockState)rootBlockState.setValue(PART, HugeDoorBlock.Part.BLOCK)).setValue(OPEN, true);
      BlockState openHalfState = (BlockState)openState.setValue(PART, HugeDoorBlock.Part.HALF_BLOCK);
      getDoorBlocks(rootBlockState, rootPos, true, false, true, false).forEachRemaining(blockPos -> {
         if (!rootPos.equals(blockPos)) {
            BlockState existingBlockState = level.getBlockState(blockPos);
            if (existingBlockState.is(rootBlockState.getBlock()) || existingBlockState.canBeReplaced()) {
               level.setBlock(blockPos, openState, 3);
            }
         }
      });
      getDoorBlocks(rootBlockState, rootPos, false, false, false, true).forEachRemaining(blockPos -> {
         if (level.getBlockState(blockPos).canBeReplaced()) {
            level.setBlock(blockPos, openHalfState, 3);
         }
      });
   }

   protected RenderShape getRenderShape(BlockState state) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      if (state.getValue(PART) != HugeDoorBlock.Part.HALF_BLOCK) {
         return Shapes.block();
      }

      Direction facing = (Direction)state.getValue(FACING);

      return switch (facing) {
         case NORTH -> VERTICAL_SLAB_NORTH;
         case EAST -> VERTICAL_SLAB_EAST;
         case SOUTH -> VERTICAL_SLAB_SOUTH;
         case WEST -> VERTICAL_SLAB_WEST;
         default -> throw new IllegalStateException();
      };
   }

   protected BlockState rotate(BlockState state, Rotation rotation) {
      return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
   }

   protected BlockState mirror(BlockState state, Mirror mirror) {
      return mirror == Mirror.NONE
         ? state
         : (BlockState)state.setValue(FACING, mirror.getRotation((Direction)state.getValue(FACING)).rotate((Direction)state.getValue(FACING)));
   }

   @Nullable
   public static BlockPos findRootBlockPos(LevelAccessor level, BlockState originState, BlockPos originPos) {
      if (!(originState.getBlock() instanceof HugeDoorBlock)) {
         return null;
      }

      if (originState.getValue(PART) == HugeDoorBlock.Part.ROOT) {
         return originPos;
      }

      Boolean isOpen = (Boolean)originState.getValue(OPEN);
      Direction facing = (Direction)originState.getValue(FACING);
      BlockPos maxRootPos = originPos.subtract(getTopOuterOffset(originState));
      if (isOpen) {
         maxRootPos = maxRootPos.relative(facing, (getWidth(originState) - 1) / -2);
      }

      Optional<BlockPos> rootPos = Iterators.tryFind(getDoorBlocks(originState, maxRootPos, true, !isOpen, isOpen, true), pos -> {
         BlockState state = level.getBlockState(pos);
         return !state.is(originState.getBlock()) ? false : state.getValue(PART) == HugeDoorBlock.Part.ROOT && state.getValue(FACING) == facing;
      });
      return (BlockPos)rootPos.transform(BlockPos::immutable).orNull();
   }

   public static Iterator<@NotNull BlockPos> getDoorBlocks(
      BlockState rootState, BlockPos rootPos, boolean hinges, boolean closedWings, boolean openWings, boolean includeOpenWingTips
   ) {
      Direction facing = (Direction)rootState.getValue(FACING);
      Direction otherHingeDirection = getDirectionToOtherHinge(facing);
      int width = getWidth(rootState);
      int height = getHeight(rootState);
      Vec3i doorWingOffset = new Vec3i(0, height - 1, 0).relative(facing, (width - 1) / 2);
      BlockPos otherHingePos = rootPos.relative(otherHingeDirection, width - 1);
      ArrayList<Iterator<BlockPos>> iterators = new ArrayList<>(4);
      if (hinges && closedWings) {
         iterators.add(BlockPos.betweenClosed(rootPos, rootPos.offset(getTopOuterOffset(rootState))).iterator());
      } else if (hinges) {
         iterators.add(BlockPos.betweenClosed(rootPos, rootPos.above(height - 1)).iterator());
         iterators.add(BlockPos.betweenClosed(otherHingePos, otherHingePos.above(height - 1)).iterator());
      } else if (closedWings) {
         iterators.add(
            BlockPos.betweenClosed(rootPos.relative(otherHingeDirection), rootPos.relative(otherHingeDirection, width - 1).above(height - 1)).iterator()
         );
      }

      if (openWings && includeOpenWingTips) {
         iterators.add(BlockPos.betweenClosed(otherHingePos, otherHingePos.offset(doorWingOffset)).iterator());
         iterators.add(BlockPos.betweenClosed(rootPos, rootPos.offset(doorWingOffset)).iterator());
      } else if (openWings) {
         Vec3i doorWingTiplessOffset = new Vec3i(0, height - 1, 0).relative(facing, (width - 2) / 2);
         iterators.add(BlockPos.betweenClosed(otherHingePos, otherHingePos.offset(doorWingTiplessOffset)).iterator());
         iterators.add(BlockPos.betweenClosed(rootPos, rootPos.offset(doorWingTiplessOffset)).iterator());
      } else if (includeOpenWingTips && width % 2 == 1) {
         iterators.add(
            BlockPos.betweenClosed(otherHingePos.offset(doorWingOffset.getX(), 0, doorWingOffset.getZ()), otherHingePos.offset(doorWingOffset)).iterator()
         );
         iterators.add(BlockPos.betweenClosed(rootPos.offset(doorWingOffset.getX(), 0, doorWingOffset.getZ()), rootPos.offset(doorWingOffset)).iterator());
      }

      return Iterators.concat(iterators.toArray(new Iterator[0]));
   }

   public static int getWidth(BlockState state) {
      return 5;
   }

   public static int getHeight(BlockState state) {
      return 8;
   }

   public static Direction getDirectionToOtherHinge(BlockState blockState) {
      return getDirectionToOtherHinge((Direction)blockState.getValue(FACING));
   }

   public static Direction getDirectionToOtherHinge(Direction facingDirection) {
      return facingDirection.getCounterClockWise();
   }

   public static Vec3i getTopOuterOffset(BlockState state) {
      return new Vec3i(0, getHeight(state) - 1, 0).relative(getDirectionToOtherHinge(state), getWidth(state) - 1);
   }

   public static Vector3f getHorizontalCenterOffset(BlockState state) {
      Vec3i offset = getTopOuterOffset(state);
      return new Vector3f(offset.getX() / 2.0F, 0.0F, offset.getZ() / 2.0F);
   }

   public enum Part implements StringRepresentable {
      ROOT("root"),
      BLOCK("block"),
      HALF_BLOCK("half_block");

      private final String name;

      Part(String name) {
         this.name = name;
      }

      public String getSerializedName() {
         return this.name;
      }
   }
}
