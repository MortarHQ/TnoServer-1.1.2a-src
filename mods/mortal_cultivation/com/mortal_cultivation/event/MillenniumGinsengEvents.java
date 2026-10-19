package com.mortal_cultivation.event;

import com.mortal_cultivation.block.MillenniumGinsengBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.LeftClickBlock.Action;
import net.neoforged.neoforge.event.level.BlockEvent.BreakEvent;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.GAME)
public final class MillenniumGinsengEvents {
   private MillenniumGinsengEvents() {
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public static void onStartDigging(LeftClickBlock event) {
      if (event.getAction() == Action.START) {
         Level level = event.getLevel();
         BlockPos pos = event.getPos();
         Player player = event.getEntity();
         if (level.hasChunkAt(pos) && !player.isSpectator() && !MillenniumGinsengBlock.canHarvestQuietly(player)) {
            BlockState state = level.getBlockState(pos);
            if (state.getBlock() instanceof MillenniumGinsengBlock) {
               if (level.isClientSide) {
                  event.setCanceled(true);
               } else if (level instanceof ServerLevel serverLevel
                  && player instanceof ServerPlayer serverPlayer
                  && mayDig(serverLevel, pos, state, serverPlayer)) {
                  event.setCanceled(true);
                  MillenniumGinsengBlock.startle(serverLevel, pos, serverPlayer);
               }
            }
         }
      }
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public static void onBreak(BreakEvent event) {
      Player player = event.getPlayer();
      if (event.getState().getBlock() instanceof MillenniumGinsengBlock && !MillenniumGinsengBlock.canHarvestQuietly(player)) {
         event.setCanceled(true);
         if (event.getLevel() instanceof ServerLevel level
            && player instanceof ServerPlayer serverPlayer
            && level.hasChunkAt(event.getPos())
            && mayDig(level, event.getPos(), event.getState(), serverPlayer)) {
            MillenniumGinsengBlock.startle(level, event.getPos(), serverPlayer);
         }
      }
   }

   private static boolean mayDig(ServerLevel level, BlockPos pos, BlockState state, ServerPlayer player) {
      return !player.isSpectator()
         && player.canInteractWithBlock(pos, 1.0)
         && level.getWorldBorder().isWithinBounds(pos)
         && level.mayInteract(player, pos)
         && !player.blockActionRestricted(level, pos, player.gameMode.getGameModeForPlayer())
         && player.getMainHandItem().getItem().canAttackBlock(state, level, pos, player);
   }
}
