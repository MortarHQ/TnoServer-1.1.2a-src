package com.chronodawn.neoforge.event;

import com.chronodawn.ChronoDawn;
import com.chronodawn.worldgen.protection.BlockProtectionHandler;
import com.chronodawn.worldgen.protection.PermanentProtectionHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.neoforged.neoforge.event.level.BlockEvent.BreakEvent;

@EventBusSubscriber(modid = "chronodawn")
public class BlockProtectionEventHandler {
   @SubscribeEvent
   public static void onBlockBreak(BreakEvent event) {
      Player player = event.getPlayer();
      Level level = (Level)event.getLevel();
      if (!player.isCreative()) {
         boolean isPermanentlyProtected = PermanentProtectionHandler.isProtected(level, event.getPos());
         boolean isBossRoomProtected = BlockProtectionHandler.isProtected(level, event.getPos());
         if (isPermanentlyProtected) {
            player.displayClientMessage(Component.translatable("message.chronodawn.permanent_protected"), true);
            ChronoDawn.LOGGER.debug("Blocked permanent protected block break at {} by {}", event.getPos(), player.getName().getString());
            event.setCanceled(true);
         } else {
            if (isBossRoomProtected) {
               player.displayClientMessage(Component.translatable("message.chronodawn.boss_room_protected"), true);
               event.setCanceled(true);
            }
         }
      }
   }

   @SubscribeEvent
   public static void onRightClickBlock(RightClickBlock event) {
      Player player = event.getEntity();
      Level level = event.getLevel();
      if (!player.isCreative()) {
         ItemStack heldItem = player.getItemInHand(event.getHand());
         if (!heldItem.isEmpty() && heldItem.getItem() instanceof BlockItem) {
            BlockPos pos = event.getPos().relative(event.getFace());
            boolean isPermanentlyProtected = PermanentProtectionHandler.isProtected(level, pos);
            boolean isBossRoomProtected = BlockProtectionHandler.isProtected(level, pos);
            if (isPermanentlyProtected) {
               player.displayClientMessage(Component.translatable("message.chronodawn.permanent_no_placement"), true);
               ChronoDawn.LOGGER.debug("Blocked permanent protected block placement at {} by {}", pos, player.getName().getString());
               event.setCancellationResult(InteractionResult.FAIL);
               event.setCanceled(true);
            } else {
               if (isBossRoomProtected) {
                  player.displayClientMessage(Component.translatable("message.chronodawn.boss_room_no_placement"), true);
                  ChronoDawn.LOGGER.debug("Blocked boss room protected block placement at {} by {}", pos, player.getName().getString());
                  event.setCancellationResult(InteractionResult.FAIL);
                  event.setCanceled(true);
               }
            }
         }
      }
   }
}
