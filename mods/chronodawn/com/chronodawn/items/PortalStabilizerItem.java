package com.chronodawn.items;

import com.chronodawn.ChronoDawn;
import com.chronodawn.core.portal.PortalRegistry;
import com.chronodawn.core.portal.PortalState;
import com.chronodawn.core.portal.PortalStateMachine;
import com.chronodawn.data.ChronoDawnGlobalState;
import com.chronodawn.registry.ModBlocks;
import com.chronodawn.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class PortalStabilizerItem extends Item {
   public PortalStabilizerItem(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return new Properties().stacksTo(1).durability(1);
   }

   public InteractionResult useOn(UseOnContext context) {
      Level level = context.getLevel();
      BlockPos clickedPos = context.getClickedPos();
      Player player = context.getPlayer();
      ItemStack itemStack = context.getItemInHand();
      if (level.isClientSide()) {
         return InteractionResult.SUCCESS;
      }

      BlockState clickedBlock = level.getBlockState(clickedPos);
      if (!clickedBlock.is((Block)ModBlocks.CLOCKSTONE_BLOCK.get())) {
         if (player != null) {
            player.displayClientMessage(Component.translatable("item.chronodawn.portal_stabilizer.invalid_block"), true);
         }

         return InteractionResult.FAIL;
      } else {
         PortalStateMachine portal = this.findNearbyPortal(level, clickedPos);
         if (portal == null) {
            if (player != null) {
               player.displayClientMessage(Component.translatable("item.chronodawn.portal_stabilizer.no_portal"), true);
            }

            return InteractionResult.FAIL;
         } else if (portal.getCurrentState() == PortalState.STABILIZED) {
            if (player != null) {
               player.displayClientMessage(Component.translatable("item.chronodawn.portal_stabilizer.already_stabilized"), true);
            }

            return InteractionResult.FAIL;
         } else if (!portal.getCurrentState().canBeStabilized()) {
            if (player != null) {
               player.displayClientMessage(Component.translatable("item.chronodawn.portal_stabilizer.cannot_stabilize"), true);
            }

            return InteractionResult.FAIL;
         } else if (portal.stabilize()) {
            if (level instanceof ServerLevel serverLevel) {
               ChronoDawnGlobalState globalState = ChronoDawnGlobalState.get(serverLevel.getServer());
               globalState.markPortalStabilized();
            }

            this.playStabilizationEffect(level, portal.getPosition());
            if (level instanceof ServerLevel serverLevel) {
               this.broadcastStabilizationMessage(serverLevel);
            }

            itemStack.shrink(1);
            ChronoDawn.LOGGER.debug("Portal {} stabilized by player {}", portal.getPortalId(), player != null ? player.getName().getString() : "Unknown");
            return InteractionResult.CONSUME;
         } else {
            return InteractionResult.FAIL;
         }
      }
   }

   private PortalStateMachine findNearbyPortal(Level level, BlockPos pos) {
      PortalRegistry registry = PortalRegistry.getInstance();

      for (int x = -5; x <= 5; x++) {
         for (int y = -5; y <= 5; y++) {
            for (int z = -5; z <= 5; z++) {
               BlockPos searchPos = pos.offset(x, y, z);
               PortalStateMachine portal = registry.getPortalAt(searchPos);
               if (portal != null) {
                  return portal;
               }
            }
         }
      }

      return null;
   }

   private void playStabilizationEffect(Level level, BlockPos portalPos) {
      if (level instanceof ServerLevel serverLevel) {
         for (int centerPos = 0; centerPos <= 3; centerPos++) {
            for (int y = 0; y <= 4; y++) {
               if (centerPos == 0 || centerPos == 3 || y == 0 || y == 4) {
                  BlockPos framePos = portalPos.offset(centerPos, y, 0);
                  if (level.getBlockState(framePos).is((Block)ModBlocks.CLOCKSTONE_BLOCK.get())) {
                     serverLevel.sendParticles(
                        ParticleTypes.END_ROD, framePos.getX() + 0.5, framePos.getY() + 0.5, framePos.getZ() + 0.5, 10, 0.3, 0.3, 0.3, 0.05
                     );
                     serverLevel.sendParticles(ParticleTypes.FLAME, framePos.getX() + 0.5, framePos.getY() + 0.5, framePos.getZ() + 0.5, 5, 0.2, 0.2, 0.2, 0.02);
                  }
               }
            }
         }

         BlockPos centerPos = portalPos.offset(1, 2, 0);
         level.playSound(null, centerPos, (SoundEvent)ModSounds.PORTAL_STABILIZE_SPAWN.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
         level.playSound(null, centerPos, (SoundEvent)ModSounds.PORTAL_STABILIZE_ACTIVATE.get(), SoundSource.BLOCKS, 0.5F, 1.2F);
      }
   }

   private void broadcastStabilizationMessage(ServerLevel serverLevel) {
      Component message = Component.translatable("item.chronodawn.portal_stabilizer.success_reignite_required");

      for (ServerPlayer player : serverLevel.getServer().getPlayerList().getPlayers()) {
         player.displayClientMessage(message, false);
      }
   }
}
