package com.benji.netherman.client;

import com.benji.netherman.client.gui.NoteScreen;
import com.benji.netherman.client.gui.QuotaScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;

public class ClientActionDelegate {
   public static void openNoteScreen() {
      NoteScreen.openScreen();
   }

   public static void openQuotaScreen(ItemStack stack) {
      Minecraft.getInstance().setScreen(new QuotaScreen(stack));
   }
}
