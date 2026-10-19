package dev.ftb.mods.ftbfiltersystem.util;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import dev.ftb.mods.ftbfiltersystem.util.neoforge.PlatformUtilImpl;
import net.minecraft.world.item.ItemStack;

public class PlatformUtil {
   @ExpectPlatform
   @Transformed
   public static boolean hasComponentPatch(ItemStack stack) {
      return PlatformUtilImpl.hasComponentPatch(stack);
   }
}
