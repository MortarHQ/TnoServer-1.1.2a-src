package com.foxablazeaqzl.wiki.client;

import net.minecraft.client.Minecraft;

public final class FoxWikiClientEvents {
   private FoxWikiClientEvents() {
   }

   public static void onClientTick(Minecraft minecraft) {
      if (minecraft.player == null) {
         FoxWikiUnreadState.clearPlayer();
         FoxItemWikiUnlockState.clearPlayer();
      } else {
         FoxWikiUnreadState.observeLearnedWikiSkills(minecraft.player);
         FoxItemWikiUnlockState.observeInventory(minecraft.player);
      }
   }
}
