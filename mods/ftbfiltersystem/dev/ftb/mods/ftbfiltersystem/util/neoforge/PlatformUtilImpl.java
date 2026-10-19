package dev.ftb.mods.ftbfiltersystem.util.neoforge;

import net.minecraft.world.item.ItemStack;

public class PlatformUtilImpl {
   public static boolean hasComponentPatch(ItemStack stack) {
      return !stack.isComponentsPatchEmpty();
   }
}
