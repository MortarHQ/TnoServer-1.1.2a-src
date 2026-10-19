package com.chronodawn.items;

import com.chronodawn.ChronoDawn;
import com.chronodawn.blocks.ChronoDawnPortalBlock;
import com.chronodawn.core.portal.PortalFrameValidator;
import com.chronodawn.core.portal.PortalRegistry;
import com.chronodawn.core.portal.PortalStateMachine;
import com.chronodawn.data.ChronoDawnGlobalState;
import com.chronodawn.registry.ModBlocks;
import com.chronodawn.registry.ModDimensions;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class TimeHourglassItem extends Item {
   public TimeHourglassItem(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return new Properties().stacksTo(64);
   }

   public InteractionResult useOn(UseOnContext context) {
      Level level = context.getLevel();
      BlockPos clickedPos = context.getClickedPos();
      Player player = context.getPlayer();
      if (level.isClientSide()) {
         return InteractionResult.SUCCESS;
      }

      MinecraftServer server = level.getServer();
      if (server == null) {
         return InteractionResult.FAIL;
      }

      if (level.dimension().equals(ModDimensions.CHRONO_DAWN_DIMENSION)) {
         ChronoDawnGlobalState globalState = ChronoDawnGlobalState.get(server);
         if (globalState.arePortalsUnstable()) {
            if (player != null) {
               player.displayClientMessage(Component.translatable("message.chronodawn.portal.unstable"), true);
            }

            return InteractionResult.FAIL;
         }
      }

      PortalFrameValidator.PortalFrameData frameData = this.findPortalFrame(level, clickedPos);
      if (frameData == null) {
         if (player != null) {
            player.displayClientMessage(Component.translatable("message.chronodawn.portal.invalid_frame"), true);
         }

         return InteractionResult.FAIL;
      } else {
         this.fillPortalBlocks(level, frameData);
         UUID portalId = UUID.randomUUID();
         PortalStateMachine portal = new PortalStateMachine(portalId, level.dimension(), frameData.getBottomLeft());
         portal.activate();
         PortalRegistry.getInstance().registerPortal(portal);
         level.playSound(null, clickedPos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
         if (player != null && !player.isCreative()) {
            context.getItemInHand().shrink(1);
         }

         if (player != null) {
            player.displayClientMessage(Component.translatable("message.chronodawn.portal.ignited"), true);
         }

         ChronoDawn.LOGGER
            .debug(
               "Portal ignited at {} in dimension {} by player {}",
               new Object[]{frameData.getBottomLeft(), level.dimension().location(), player != null ? player.getName().getString() : "unknown"}
            );
         return InteractionResult.CONSUME;
      }
   }

   private PortalFrameValidator.PortalFrameData findPortalFrame(Level level, BlockPos clickedPos) {
      BlockState clickedState = level.getBlockState(clickedPos);
      if (!clickedState.is((Block)ModBlocks.CLOCKSTONE_BLOCK.get())) {
         return null;
      }

      for (Axis axis : new Axis[]{Axis.X, Axis.Z}) {
         for (int dx = -22; dx <= 22; dx++) {
            for (int dy = -22; dy <= 22; dy++) {
               BlockPos testPos = clickedPos.offset(axis == Axis.X ? dx : 0, dy, axis == Axis.Z ? dx : 0);
               PortalFrameValidator.PortalFrameData frameData = PortalFrameValidator.validateFrame(level, testPos, axis);
               if (frameData != null) {
                  return frameData;
               }
            }
         }
      }

      return null;
   }

   private void fillPortalBlocks(Level level, PortalFrameValidator.PortalFrameData frameData) {
      Set<BlockPos> interiorPositions = frameData.getInteriorPositions();
      BlockState portalState = (BlockState)((Block)ModBlocks.CHRONO_DAWN_PORTAL.get())
         .defaultBlockState()
         .setValue(ChronoDawnPortalBlock.AXIS, frameData.getAxis());

      for (BlockPos pos : interiorPositions) {
         level.setBlock(pos, portalState, 3);
      }

      ChronoDawn.LOGGER.debug("Filled {} portal blocks in frame at {}", interiorPositions.size(), frameData.getBottomLeft());
   }
}
