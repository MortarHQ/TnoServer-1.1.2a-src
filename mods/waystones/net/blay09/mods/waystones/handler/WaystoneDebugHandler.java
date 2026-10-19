package net.blay09.mods.waystones.handler;

import net.blay09.mods.balm.api.event.UseBlockEvent;
import net.blay09.mods.waystones.block.entity.WaystoneBlockEntityBase;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class WaystoneDebugHandler {
   public static void onWaystoneUsed(UseBlockEvent event) {
      Level level = event.getLevel();
      Player player = event.getPlayer();
      ItemStack heldItem = player.getItemInHand(event.getHand());
      if (player.getAbilities().instabuild) {
         if (!(level.getBlockEntity(event.getHitResult().getBlockPos()) instanceof WaystoneBlockEntityBase waystoneBase)) {
            return;
         }

         if (heldItem.getItem() == Items.BAMBOO) {
            if (!level.isClientSide) {
               waystoneBase.uninitializeWaystone();
               player.displayClientMessage(Component.literal("Waystone was successfully reset - it will re-initialize once it is next loaded."), false);
            }

            event.setResult(InteractionResult.SUCCESS);
         } else if (heldItem.getItem() == Items.STICK) {
            if (!level.isClientSide) {
               player.displayClientMessage(Component.literal("Server UUID: " + waystoneBase.getWaystone().getWaystoneUid()), false);
            }

            if (level.isClientSide) {
               player.displayClientMessage(Component.literal("Client UUID: " + waystoneBase.getWaystone().getWaystoneUid()), false);
            }

            event.setResult(InteractionResult.SUCCESS);
         }
      }
   }
}
