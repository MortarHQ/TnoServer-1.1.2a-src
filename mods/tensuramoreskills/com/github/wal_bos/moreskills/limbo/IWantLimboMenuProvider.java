package com.github.wal_bos.moreskills.limbo;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;

public final class IWantLimboMenuProvider {
   private IWantLimboMenuProvider() {
   }

   public static void open(ServerPlayer player) {
      open(player, -1);
   }

   public static void open(ServerPlayer player, int focusedCard) {
      List<IWantLimboData.StoredCard> cards = IWantLimboData.cards(player);
      int rerollsLeft = IWantLimboData.rerollsLeft(player);
      MenuProvider provider = new SimpleMenuProvider(
         (id, inv, p) -> IWantLimboMenu.server(id, inv, player, cards, focusedCard, rerollsLeft),
         Component.translatable("tensuramoreskills.gui.i_want_limbo.title")
      );
      player.openMenu(provider, buf -> IWantLimboMenu.writeSnapshot(buf, cards, focusedCard, rerollsLeft));
   }
}
