package dev.ftb.mods.ftblibrary.ui.input.neoforge;

import com.mojang.blaze3d.platform.InputConstants.Key;
import net.minecraft.client.KeyMapping;

public class KeyImpl {
   public static boolean matchesWithoutConflicts(KeyMapping keyBinding, Key keyCode) {
      return keyBinding.isActiveAndMatches(keyCode);
   }
}
