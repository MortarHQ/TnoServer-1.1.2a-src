package com.b1n_ry.yigd.block;

import com.b1n_ry.yigd.Yigd;
import com.b1n_ry.yigd.block.entity.GraveBlockEntity;
import com.b1n_ry.yigd.components.GraveComponent;
import com.b1n_ry.yigd.config.GraveConfig;
import com.b1n_ry.yigd.config.GraveRenderingConfig;
import com.b1n_ry.yigd.config.YigdConfig;
import com.b1n_ry.yigd.data.DeathInfoManager;
import com.b1n_ry.yigd.data.GraveStatus;
import com.b1n_ry.yigd.data.TimePoint;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.MapCodec;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.ScheduledTick;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GraveBlock extends BaseEntityBlock implements EntityBlock {
   private static VoxelShape SHAPE_EAST;
   private static VoxelShape SHAPE_WEST;
   private static VoxelShape SHAPE_SOUTH;
   private static VoxelShape SHAPE_NORTH;

   public GraveBlock(Properties properties) {
      super(properties);
   }

   @NotNull
   protected MapCodec<? extends BaseEntityBlock> codec() {
      return (MapCodec<? extends BaseEntityBlock>)Yigd.GRAVE_BLOCK_CODEC.value();
   }

   @Nullable
   public GraveBlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
      return new GraveBlockEntity(blockPos, blockState);
   }

   protected void createBlockStateDefinition(@NotNull Builder<Block, BlockState> builder) {
      builder.add(new Property[]{BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.WATERLOGGED});
   }

   public void setPlacedBy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity placer, @NotNull ItemStack stack) {
      if (level.getBlockEntity(pos) instanceof GraveBlockEntity grave && stack.has(DataComponents.CUSTOM_NAME)) {
         GraveComponent graveComponent = grave.getComponent();
         if (graveComponent == null) {
            grave.setGraveText(stack.getDisplayName());
            grave.setChanged();
         }
      }

      super.setPlacedBy(level, pos, state, placer, stack);
   }

   @Nullable
   public BlockState getStateForPlacement(@NotNull BlockPlaceContext ctx) {
      Direction dir = ctx.getHorizontalDirection().getOpposite();
      BlockState state = this.defaultBlockState();
      FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
      return (BlockState)((BlockState)state.setValue(BlockStateProperties.HORIZONTAL_FACING, dir))
         .setValue(BlockStateProperties.WATERLOGGED, fluidState.is(Fluids.WATER));
   }

   @NotNull
   protected BlockState updateShape(
      @NotNull BlockState state,
      @NotNull Direction direction,
      @NotNull BlockState neighborState,
      @NotNull LevelAccessor level,
      @NotNull BlockPos pos,
      @NotNull BlockPos neighborPos
   ) {
      if ((Boolean)state.getValue(BlockStateProperties.WATERLOGGED)) {
         level.getFluidTicks().schedule(ScheduledTick.probe(Fluids.WATER, pos));
      }

      return direction.getAxis().isHorizontal() ? state : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
   }

   @NotNull
   protected FluidState getFluidState(BlockState state) {
      return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
   }

   @NotNull
   protected RenderShape getRenderShape(@NotNull BlockState state) {
      GraveRenderingConfig config = YigdConfig.getConfig().graveRendering;
      return config.useCustomFeatureRenderer ? RenderShape.INVISIBLE : RenderShape.MODEL;
   }

   @NotNull
   protected VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
      Direction direction = (Direction)state.getValue(BlockStateProperties.HORIZONTAL_FACING);

      return switch (direction) {
         case EAST -> SHAPE_EAST;
         case WEST -> SHAPE_WEST;
         case SOUTH -> SHAPE_SOUTH;
         default -> SHAPE_NORTH;
      };
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
      return createTickerHelper(type, (BlockEntityType)Yigd.GRAVE_BLOCK_ENTITY.get(), GraveBlockEntity::tick);
   }

   @NotNull
   protected InteractionResult useWithoutItem(
      @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hit
   ) {
      YigdConfig config = YigdConfig.getConfig();
      InteractionHand hand = player.getUsedItemHand();
      if (player instanceof ServerPlayer && !(player instanceof FakePlayer)) {
         if (!level.isClientSide && level.getBlockEntity(pos) instanceof GraveBlockEntity grave) {
            GraveComponent graveComponent = grave.getComponent();
            if (graveComponent == null) {
               UUID graveId = grave.getGraveId();
               if (graveId != null) {
                  Optional<GraveComponent> component = DeathInfoManager.INSTANCE.getGrave(graveId);
                  if (component.isPresent()) {
                     graveComponent = component.get();
                  }
               }

               if (graveComponent == null) {
                  return this.interactWithNonPlayerGrave(grave, state, level, pos, player, hand, hit);
               }
            }

            if (config.graveConfig.persistentGraves.enabled && graveComponent.getStatus() == GraveStatus.CLAIMED && hand == InteractionHand.MAIN_HAND) {
               MutableComponent message = graveComponent.getDeathMessage().copy();
               TimePoint creationTime = graveComponent.getCreationTime();
               if (config.graveConfig.persistentGraves.showDeathDay) {
                  message.append(Component.translatable("text.yigd.message.on_day", new Object[]{creationTime.getDay()}));
               }

               if (config.graveConfig.persistentGraves.showDeathIrlTime) {
                  message.append(
                     Component.translatable(
                        "text.yigd.message.irl_time",
                        new Object[]{
                           creationTime.getMonthName(),
                           creationTime.getDate(),
                           creationTime.getYear(),
                           creationTime.getHour(config.graveConfig.persistentGraves.useAmPm),
                           creationTime.getMinute(),
                           creationTime.getTimePostfix(config.graveConfig.persistentGraves.useAmPm)
                        }
                     )
                  );
               }

               player.sendSystemMessage(message);
               return InteractionResult.SUCCESS;
            }

            if (config.graveConfig.retrieveMethods.onClick) {
               return graveComponent.claim((ServerPlayer)player, (ServerLevel)level, grave.getPreviousState(), pos, player.getItemInHand(hand));
            }
         }

         return InteractionResult.FAIL;
      } else {
         return InteractionResult.PASS;
      }
   }

   private InteractionResult interactWithNonPlayerGrave(
      GraveBlockEntity grave, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult ignoredHit
   ) {
      if (player.isShiftKeyDown()) {
         return InteractionResult.FAIL;
      }

      ItemStack stack = player.getItemInHand(hand);
      if (stack.is(Items.PLAYER_HEAD) && stack.has(DataComponents.PROFILE)) {
         ResolvableProfile profile = (ResolvableProfile)stack.get(DataComponents.PROFILE);
         if (profile == null) {
            return InteractionResult.PASS;
         }

         grave.setGraveSkull(profile);
         grave.setChanged();
         world.sendBlockUpdated(pos, state, state, 3);
         if (!player.isCreative()) {
            stack.shrink(1);
         }

         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.PASS;
      }
   }

   public void stepOn(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Entity entity) {
      if (!level.isClientSide && entity instanceof ServerPlayer player) {
         GraveConfig graveConfig = YigdConfig.getConfig().graveConfig;
         if ((graveConfig.retrieveMethods.onStand || graveConfig.retrieveMethods.onSneak && player.isShiftKeyDown())
            && level.getBlockEntity(pos) instanceof GraveBlockEntity grave) {
            GraveComponent graveComponent = grave.getComponent();
            if (graveComponent == null) {
               UUID graveId = grave.getGraveId();
               if (graveId != null) {
                  Optional<GraveComponent> component = DeathInfoManager.INSTANCE.getGrave(graveId);
                  if (component.isPresent()) {
                     graveComponent = component.get();
                  }
               }
            }

            if (graveComponent != null && graveComponent.getStatus() != GraveStatus.CLAIMED) {
               graveComponent.claim(player, (ServerLevel)level, grave.getPreviousState(), pos, player.getMainHandItem());
            }
         }
      }

      super.stepOn(level, pos, state, entity);
   }

   public void playerDestroy(
      @NotNull Level level,
      @NotNull Player player,
      @NotNull BlockPos pos,
      @NotNull BlockState state,
      @Nullable BlockEntity blockEntity,
      @NotNull ItemStack tool
   ) {
      YigdConfig config = YigdConfig.getConfig();
      if (!level.isClientSide
         && blockEntity instanceof GraveBlockEntity grave
         && grave.getComponent() != null
         && grave.getComponent().getStatus() != GraveStatus.CLAIMED) {
         if (config.graveConfig.retrieveMethods.onBreak) {
            InteractionResult claimResult = grave.getComponent().claim((ServerPlayer)player, (ServerLevel)level, grave.getPreviousState(), pos, tool);
            if (claimResult != InteractionResult.FAIL) {
               return;
            }
         }

         level.setBlockAndUpdate(pos, state);
         Optional<GraveBlockEntity> be = level.getBlockEntity(pos, (BlockEntityType)Yigd.GRAVE_BLOCK_ENTITY.get());
         if (be.isPresent()) {
            GraveBlockEntity graveBlockEntity = be.get();
            graveBlockEntity.setPreviousState(grave.getPreviousState());
            Optional<GraveComponent> component = DeathInfoManager.INSTANCE.getGrave(grave.getGraveId());
            component.ifPresent(graveBlockEntity::setComponent);
            Yigd.END_OF_TICK.add(() -> {
               graveBlockEntity.setChanged();
               level.sendBlockUpdated(pos, state, state, 3);
            });
            return;
         }
      }

      super.playerDestroy(level, player, pos, state, blockEntity, tool);
   }

   protected float getDestroyProgress(@NotNull BlockState state, @NotNull Player player, @NotNull BlockGetter level, @NotNull BlockPos pos) {
      if (level.getBlockEntity(pos) instanceof GraveBlockEntity grave && grave.isUnclaimed() && !YigdConfig.getConfig().graveConfig.retrieveMethods.onBreak) {
         return super.getDestroyProgress(state, player, level, pos);
      } else {
         float f = 0.8F;
         int i = EventHooks.doPlayerHarvestCheck(player, state, level, pos) ? 30 : 100;
         return player.getDigSpeed(state, pos) / f / i;
      }
   }

   public static void reloadShapeFromJson(JsonObject json) throws IllegalStateException {
      VoxelShape voxelShapeNorth = Shapes.empty();
      VoxelShape voxelShapeSouth = Shapes.empty();
      VoxelShape voxelShapeEast = Shapes.empty();
      VoxelShape voxelShapeWest = Shapes.empty();

      for (JsonElement element : json.getAsJsonArray("elements")) {
         JsonObject o = element.getAsJsonObject();
         JsonArray from = o.getAsJsonArray("from");
         JsonArray to = o.getAsJsonArray("to");
         double x1 = from.get(0).getAsDouble() / 16.0;
         double y1 = from.get(1).getAsDouble() / 16.0;
         double z1 = from.get(2).getAsDouble() / 16.0;
         double x2 = to.get(0).getAsDouble() / 16.0;
         double y2 = to.get(1).getAsDouble() / 16.0;
         double z2 = to.get(2).getAsDouble() / 16.0;
         voxelShapeNorth = Shapes.or(voxelShapeNorth, Shapes.create(x1, y1, z1, x2, y2, z2));
         voxelShapeEast = Shapes.or(voxelShapeEast, Shapes.create(1.0 - z2, y1, x1, 1.0 - z1, y2, x2));
         voxelShapeSouth = Shapes.or(voxelShapeSouth, Shapes.create(1.0 - x2, y1, 1.0 - z2, 1.0 - x1, y2, 1.0 - z1));
         voxelShapeWest = Shapes.or(voxelShapeWest, Shapes.create(z1, y1, 1.0 - x2, z2, y2, 1.0 - x1));
      }

      SHAPE_NORTH = voxelShapeNorth;
      SHAPE_EAST = voxelShapeEast;
      SHAPE_SOUTH = voxelShapeSouth;
      SHAPE_WEST = voxelShapeWest;
   }

   static {
      VoxelShape bottom = Shapes.create(0.0, 0.0, 0.0, 1.0, 0.0625, 1.0);
      VoxelShape supportEast = Shapes.create(0.0625, 0.0625, 0.125, 0.375, 0.1875, 0.875);
      VoxelShape bustEast = Shapes.create(0.125, 0.1875, 0.1875, 0.3125, 0.9375, 0.8125);
      VoxelShape topEast = Shapes.create(0.125, 0.9375, 0.25, 0.3125, 1.0, 0.75);
      VoxelShape supportWest = Shapes.create(0.625, 0.0625, 0.125, 0.9375, 0.1875, 0.875);
      VoxelShape bustWest = Shapes.create(0.6875, 0.1875, 0.1875, 0.875, 0.9375, 0.8125);
      VoxelShape topWest = Shapes.create(0.6875, 0.9375, 0.25, 0.875, 1.0, 0.75);
      VoxelShape supportSouth = Shapes.create(0.125, 0.0625, 0.0625, 0.875, 0.1875, 0.375);
      VoxelShape bustSouth = Shapes.create(0.1875, 0.1875, 0.125, 0.8125, 0.9375, 0.3125);
      VoxelShape topSouth = Shapes.create(0.25, 0.9375, 0.125, 0.75, 1.0, 0.3125);
      VoxelShape supportNorth = Shapes.create(0.125, 0.0625, 0.625, 0.875, 0.1875, 0.9375);
      VoxelShape bustNorth = Shapes.create(0.1875, 0.1875, 0.6875, 0.8125, 0.9375, 0.875);
      VoxelShape topNorth = Shapes.create(0.25, 0.9375, 0.6875, 0.75, 1.0, 0.875);
      SHAPE_EAST = Shapes.or(bottom, new VoxelShape[]{supportEast, bustEast, topEast});
      SHAPE_WEST = Shapes.or(bottom, new VoxelShape[]{supportWest, bustWest, topWest});
      SHAPE_SOUTH = Shapes.or(bottom, new VoxelShape[]{supportSouth, bustSouth, topSouth});
      SHAPE_NORTH = Shapes.or(bottom, new VoxelShape[]{supportNorth, bustNorth, topNorth});
   }
}
