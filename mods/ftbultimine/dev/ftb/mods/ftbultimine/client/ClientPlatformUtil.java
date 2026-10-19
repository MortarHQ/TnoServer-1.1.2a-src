package dev.ftb.mods.ftbultimine.client;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import dev.ftb.mods.ftblibrary.ui.input.Key;
import dev.ftb.mods.ftbultimine.client.neoforge.ClientPlatformUtilImpl;
import net.minecraft.client.KeyMapping;

public class ClientPlatformUtil {
   @ExpectPlatform
   @Transformed
   public static boolean doesKeybindMatch(KeyMapping keyMapping, int keyCode, int scanCode, int modifiers) {
      return ClientPlatformUtilImpl.doesKeybindMatch(keyMapping, keyCode, scanCode, modifiers);
   }

   public static boolean doesKeybindMatch(KeyMapping keyMapping, Key key) {
      return doesKeybindMatch(keyMapping, key.keyCode, key.scanCode, key.modifiers.modifiers);
   }
}
