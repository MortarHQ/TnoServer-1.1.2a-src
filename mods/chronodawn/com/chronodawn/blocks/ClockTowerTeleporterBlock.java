package com.chronodawn.blocks;

import com.chronodawn.core.teleport.TeleporterChargingHandler;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;

public class ClockTowerTeleporterBlock extends BaseEntityBlock {
   public static final MapCodec<ClockTowerTeleporterBlock> CODEC = simpleCodec(ClockTowerTeleporterBlock::new);
   public static final EnumProperty<ClockTowerTeleporterBlock.TeleportDirection> DIRECTION = EnumProperty.create(
      "direction", ClockTowerTeleporterBlock.TeleportDirection.class
   );
   private static final int FLOOR_HEIGHT = 8;

   public ClockTowerTeleporterBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(DIRECTION, ClockTowerTeleporterBlock.TeleportDirection.UP));
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new ClockTowerTeleporterBlockEntity(pos, state);
   }

   protected RenderShape getRenderShape(BlockState state) {
      return RenderShape.MODEL;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{DIRECTION});
   }

   protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
      if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
         TeleporterChargingHandler.startCharging(
            serverPlayer, pos, ((ClockTowerTeleporterBlock.TeleportDirection)state.getValue(DIRECTION)).name(), level.getGameTime()
         );
      }

      return InteractionResult.SUCCESS;
   }

   public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
      return !level.isClientSide && !player.isCreative() ? state : super.playerWillDestroy(level, pos, state, player);
   }

   protected void attack(BlockState state, Level level, BlockPos pos, Player player) {
      if (!level.isClientSide && !player.isCreative()) {
         player.displayClientMessage(Component.translatable("message.chronodawn.teleporter_unbreakable"), true);
      }

      super.attack(state, level, pos, player);
   }

   protected float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
      return !player.isCreative() ? 0.0F : super.getDestroyProgress(state, player, level, pos);
   }

   public enum TeleportDirection implements StringRepresentable {
      UP("up"),
      DOWN("down");

      private final String name;

      TeleportDirection(String name) {
         this.name = name;
      }

      public String getSerializedName() {
         return this.name;
      }
   }
}
