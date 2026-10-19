package com.mortal_cultivation.event.client;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientRawInputEvent;
import dev.architectury.event.events.client.ClientRawInputEvent.KeyPressed;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

public class KeyInputEventHandler {
   public static void init() {
   }

   static {
      ClientRawInputEvent.KEY_PRESSED
         .register(
            (KeyPressed)(client, key, scanCode, action, modifiers) -> {
               Minecraft minecraft = Minecraft.getInstance();
               Screen currentScreen = minecraft.screen;
               return !(currentScreen instanceof AbstractContainerScreen) || action != 1 || key != 82 && key != 90 && key != 88 && key != 67
                  ? EventResult.pass()
                  : EventResult.interruptFalse();
            }
         );
   }
}
