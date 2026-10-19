package com.github.wal_bos.moreskills.gene;

import com.github.wal_bos.moreskills.ability.skill.unique.GeneEditorSkill;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;

public final class GeneEditorProvider {
   private GeneEditorProvider() {
   }

   public static void open(ServerPlayer player) {
      MenuProvider provider = new SimpleMenuProvider(
         (id, inv, p) -> GeneEditorMenu.server(id, inv, player), Component.translatable("tensuramoreskills.gui.gene_editor.title")
      );
      player.openMenu(provider, buf -> GeneEditorSkill.writeStorageSnapshot(player, buf));
   }
}
