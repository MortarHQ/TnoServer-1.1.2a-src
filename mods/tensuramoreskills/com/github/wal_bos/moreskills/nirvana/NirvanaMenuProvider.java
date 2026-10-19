package com.github.wal_bos.moreskills.nirvana;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;

public final class NirvanaMenuProvider {
   private NirvanaMenuProvider() {
   }

   public static void open(ServerPlayer player) {
      open(player, NirvanaData.Sort.NEWEST);
   }

   public static void open(ServerPlayer player, NirvanaData.Sort sort) {
      MenuProvider provider = new SimpleMenuProvider(
         (id, inv, p) -> NirvanaMenu.server(id, inv, player, sort), Component.translatable("tensuramoreskills.gui.nirvana.title")
      );
      player.openMenu(provider, buf -> NirvanaData.writeMenuSnapshot(player, buf, sort));
   }
}
