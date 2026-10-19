package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.block.entity.ESPortalBlockEntity;
import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import cn.leolezury.eternalstarlight.common.world.ESTeleporter;
import com.mojang.serialization.MapCodec;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.block.RenderShape;
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
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ESPortalBlock extends BaseEntityBlock implements Portal {
   public static final MapCodec<ESPortalBlock> CODEC = simpleCodec(ESPortalBlock::new);
   public static final EnumProperty<Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
   public static final BooleanProperty CENTER = BooleanProperty.create("center");
   public static final IntegerProperty SIZE = IntegerProperty.create("size", 0, 20);
   protected static final VoxelShape X_AABB = Block.box(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);
   protected static final VoxelShape Z_AABB = Block.box(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);

   public ESPortalBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(AXIS, Axis.X)).setValue(CENTER, false)).setValue(SIZE, 2)
      );
   }

   protected MapCodec<ESPortalBlock> codec() {
      return CODEC;
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
      return blockState.getValue(CENTER) ? new ESPortalBlockEntity(blockPos, blockState) : null;
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
      return blockState.getValue(CENTER) ? createTickerHelper(blockEntityType, ESBlockEntities.STARLIGHT_PORTAL.get(), ESPortalBlockEntity::tick) : null;
   }

   public RenderShape getRenderShape(BlockState blockState) {
      return ESConfig.INSTANCE.enablePortalShader ? RenderShape.ENTITYBLOCK_ANIMATED : RenderShape.MODEL;
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return state.getValue(AXIS) == Axis.X ? X_AABB : Z_AABB;
   }

   public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
      Axis axis = (Axis)state.getValue(AXIS);
      Direction leftDir;
      Direction rightDir;
      if (axis == Axis.X) {
         leftDir = Direction.EAST;
         rightDir = Direction.WEST;
      } else {
         leftDir = Direction.NORTH;
         rightDir = Direction.SOUTH;
      }

      for (Direction direction : List.of(leftDir, rightDir, Direction.UP, Direction.DOWN)) {
         if (!level.getBlockState(currentPos.relative(direction)).is(ESTags.Blocks.PORTAL_FRAME_BLOCKS)
            && (!level.getBlockState(currentPos.relative(direction)).is(this) || level.getBlockState(currentPos.relative(direction)).getValue(AXIS) != axis)) {
            return Blocks.AIR.defaultBlockState();
         }
      }

      return state;
   }

   public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
      if (entity.canUsePortal(false)) {
         entity.setAsInsidePortal(this, pos);
      }
   }

   public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
      return ItemStack.EMPTY;
   }

   public BlockState rotate(BlockState state, Rotation rot) {
      return switch (rot) {
         case COUNTERCLOCKWISE_90, CLOCKWISE_90 -> {
            switch ((Axis)state.getValue(AXIS)) {
               case Z:
                  yield (BlockState)state.setValue(AXIS, Axis.X);
               case X:
                  yield (BlockState)state.setValue(AXIS, Axis.Z);
               default:
                  yield state;
            }
         }
         default -> state;
      };
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{AXIS, CENTER, SIZE});
   }

   public static boolean validateAndPlacePortal(LevelAccessor level, BlockPos framePos) {
      ESPortalBlock.Validator validator = new ESPortalBlock.Validator(level, framePos, Axis.X);
      if (!validator.isValid()) {
         validator = new ESPortalBlock.Validator(level, framePos, Axis.Z);
         if (validator.isValid()) {
            validator.fillPortal();
            return true;
         } else {
            return false;
         }
      } else {
         validator.fillPortal();
         return true;
      }
   }

   public static boolean placePortal(LevelAccessor level, BlockPos bottomPos, Axis axis, int size) {
      List<BlockPos> framePositions = new ArrayList<>();
      List<BlockPos> hollowPositions = new ArrayList<>();
      Direction rightDir;
      Direction leftDir;
      if (axis == Axis.X) {
         leftDir = Direction.EAST;
         rightDir = Direction.WEST;
      } else {
         leftDir = Direction.NORTH;
         rightDir = Direction.SOUTH;
      }

      for (int height = -size; height <= size; height++) {
         int hollowWidth = size - Math.abs(height);
         framePositions.add(new BlockPos(0, height + size, 0).relative(leftDir, hollowWidth));
         framePositions.add(new BlockPos(0, height + size, 0).relative(rightDir, hollowWidth));
         if (hollowWidth >= 1) {
            for (int i = 0; i < hollowWidth; i++) {
               hollowPositions.add(new BlockPos(0, height + size, 0).relative(leftDir, i));
               hollowPositions.add(new BlockPos(0, height + size, 0).relative(rightDir, i));
            }
         }
      }

      WorldBorder border = level.getWorldBorder();
      BlockPos center = bottomPos.above(size);

      for (BlockPos blockPos : framePositions) {
         if (!level.isEmptyBlock(blockPos.offset(bottomPos)) || !border.isWithinBounds(blockPos.offset(bottomPos))) {
            return false;
         }
      }

      for (BlockPos blockPos : hollowPositions) {
         if (!level.isEmptyBlock(blockPos.offset(bottomPos)) || !border.isWithinBounds(blockPos.offset(bottomPos))) {
            return false;
         }
      }

      for (BlockPos blockPos : framePositions) {
         level.setBlock(blockPos.offset(bottomPos), ESBlocks.CHISELED_VOIDSTONE.get().defaultBlockState(), 3);
      }

      for (BlockPos blockPos : hollowPositions) {
         level.setBlock(blockPos.offset(bottomPos), ESBlocks.CHISELED_VOIDSTONE.get().defaultBlockState(), 3);
      }

      for (BlockPos blockPos : hollowPositions) {
         level.setBlock(
            blockPos.offset(bottomPos),
            (BlockState)((BlockState)((BlockState)((ESPortalBlock)ESBlocks.STARLIGHT_PORTAL.get()).defaultBlockState().setValue(AXIS, axis))
                  .setValue(CENTER, blockPos.offset(bottomPos).equals(center)))
               .setValue(SIZE, size),
            3
         );
      }

      return true;
   }

   @Nullable
   public DimensionTransition getPortalDestination(ServerLevel serverLevel, Entity entity, BlockPos blockPos) {
      Level entityLevel = entity.level();
      MinecraftServer server = entityLevel.getServer();
      ResourceKey<Level> destination = entity.level().dimension() == ESDimensions.STARLIGHT_KEY ? Level.OVERWORLD : ESDimensions.STARLIGHT_KEY;
      if (server != null) {
         ServerLevel destinationLevel = server.getLevel(destination);
         if (destinationLevel != null && ESPlatform.INSTANCE.postTravelToDimensionEvent(entity, destination)) {
            return ESTeleporter.getPortalInfo(entity, blockPos, destinationLevel);
         }
      }

      return null;
   }

   public int getPortalTransitionTime(ServerLevel level, Entity entity) {
      return entity instanceof Player player ? (player.getAbilities().invulnerable ? 1 : 80) : 0;
   }

   public static class Validator {
      private static final int MAX_SIZE = 10;
      private static final int MIN_SIZE = 2;
      private final LevelAccessor level;
      private final Axis axis;
      private final List<BlockPos> frames = new ArrayList<>();
      private final List<BlockPos> hollows = new ArrayList<>();
      private final BlockPos center;
      private final int portalSize;

      public Validator(LevelAccessor level, BlockPos framePos, Axis axis) {
         this.level = level;
         this.axis = axis;
         Direction rightDir;
         Direction leftDir;
         if (axis == Axis.X) {
            leftDir = Direction.EAST;
            rightDir = Direction.WEST;
         } else {
            leftDir = Direction.NORTH;
            rightDir = Direction.SOUTH;
         }

         for (int size = 2; size <= 10; size++) {
            List<BlockPos> framePositions = new ArrayList<>();
            List<BlockPos> hollowPositions = new ArrayList<>();

            for (int height = -size; height <= size; height++) {
               int hollowWidth = size - Math.abs(height);
               framePositions.add(new BlockPos(0, height, 0).relative(leftDir, hollowWidth));
               framePositions.add(new BlockPos(0, height, 0).relative(rightDir, hollowWidth));
               if (hollowWidth >= 1) {
                  for (int i = 0; i < hollowWidth; i++) {
                     hollowPositions.add(new BlockPos(0, height, 0).relative(leftDir, i));
                     hollowPositions.add(new BlockPos(0, height, 0).relative(rightDir, i));
                  }
               }
            }

            for (BlockPos blockPos : framePositions) {
               BlockPos offset = framePos.subtract(blockPos);
               List<BlockPos> offsetFrames = framePositions.stream().map(pos -> pos.offset(offset)).toList();
               List<BlockPos> offsetHollows = hollowPositions.stream().map(pos -> pos.offset(offset)).toList();
               boolean correctFrames = this.validateBlocks(offsetFrames, pos -> !level.getBlockState(pos).is(ESTags.Blocks.PORTAL_FRAME_BLOCKS));
               boolean correctHollows = this.validateBlocks(
                  offsetHollows, pos -> !level.getBlockState(pos).is((Block)ESBlocks.STARLIGHT_PORTAL.get()) && !level.getBlockState(pos).isAir()
               );
               if (correctFrames && correctHollows) {
                  this.frames.addAll(offsetFrames);
                  this.hollows.addAll(offsetHollows);
                  this.center = offset;
                  this.portalSize = size;
                  return;
               }
            }
         }

         this.center = BlockPos.ZERO;
         this.portalSize = 0;
      }

      private boolean validateBlocks(List<BlockPos> positions, Function<BlockPos, Boolean> function) {
         for (BlockPos offsetFrame : positions) {
            if (function.apply(offsetFrame)) {
               return false;
            }
         }

         return true;
      }

      public boolean isValid() {
         return !this.frames.isEmpty() && !this.hollows.isEmpty();
      }

      public void fillPortal() {
         for (BlockPos blockPos : this.frames) {
            this.level.setBlock(blockPos, ESBlocks.CHISELED_VOIDSTONE.get().defaultBlockState(), 3);
         }

         for (BlockPos blockPos : this.hollows) {
            this.level.setBlock(blockPos, ESBlocks.CHISELED_VOIDSTONE.get().defaultBlockState(), 3);
         }

         for (BlockPos blockPos : this.hollows) {
            this.level
               .setBlock(
                  blockPos,
                  (BlockState)((BlockState)((BlockState)((ESPortalBlock)ESBlocks.STARLIGHT_PORTAL.get())
                           .defaultBlockState()
                           .setValue(ESPortalBlock.AXIS, this.axis))
                        .setValue(ESPortalBlock.CENTER, blockPos.equals(this.center)))
                     .setValue(ESPortalBlock.SIZE, this.portalSize),
                  3
               );
         }
      }
   }
}
