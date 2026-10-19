package dev.ftb.mods.ftbultimine.client.neoforge;

import net.minecraft.client.KeyMapping;

public class ClientPlatformUtilImpl {
   public static boolean doesKeybindMatch(KeyMapping keyMapping, int keyCode, int scanCode, int modifiers) {
      if (keyMapping.matches(keyCode, scanCode)) {
         return switch (keyMapping.getKeyModifier()) {
            case NONE -> modifiers == 0;
            case SHIFT -> (modifiers & 1) != 0;
            case CONTROL -> (modifiers & 2) != 0;
            case ALT -> (modifiers & 4) != 0;
            default -> throw new MatchException(null, null);
         };
      } else {
         return false;
      }
   }
}
