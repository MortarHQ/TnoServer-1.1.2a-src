package com.github.wal_bos.moreskills.fungus;

import com.github.wal_bos.moreskills.ability.skill.unique.FungusInfectionSkill;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;

public final class FungusInfectionProvider {
   private FungusInfectionProvider() {
   }

   public static void open(ServerPlayer player) {
      MenuProvider provider = new SimpleMenuProvider(
         (id, inv, p) -> FungusInfectionMenu.server(id, inv, player), Component.translatable("tensuramoreskills.gui.fungus_infection.title")
      );
      player.openMenu(provider, buf -> FungusInfectionSkill.writeStorageSnapshot(player, buf));
   }
}
