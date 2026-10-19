package net.mebahelsapi;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.mebahelsapi.screen.ModChestScreen;
import net.mebahelsapi.screenhandler.ModScreenHandlers;
import net.mebahelsapi.util.ChestOpenSyncClient;
import net.minecraft.class_3929;

@Environment(EnvType.CLIENT)
public class MebahelsAPIClient implements ClientModInitializer {
   public void onInitializeClient() {
      class_3929.method_17542(ModScreenHandlers.BASE_CHEST_SCREEN_HANDLER, ModChestScreen::new);
      ChestOpenSyncClient.registerClientReceiver();
   }
}
