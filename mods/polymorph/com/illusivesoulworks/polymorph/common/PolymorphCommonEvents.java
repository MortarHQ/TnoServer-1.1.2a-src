package com.illusivesoulworks.polymorph.common;

import com.illusivesoulworks.polymorph.common.integration.PolymorphIntegrations;
import com.illusivesoulworks.polymorph.common.util.BlockEntityTicker;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;

public class PolymorphCommonEvents {
   public static void levelTick(Level level) {
      if (!level.isClientSide() && level.getGameTime() % 5L == 0L) {
         BlockEntityTicker.tick();
      }
   }

   public static void playerDisconnected(ServerPlayer serverPlayer) {
      BlockEntityTicker.remove(serverPlayer);
   }

   public static void openContainer(Player player, AbstractContainerMenu containerMenu) {
      if (!player.level().isClientSide() && player instanceof ServerPlayer serverPlayerEntity) {
         PolymorphIntegrations.openContainer(containerMenu, serverPlayerEntity);
      }
   }
}
