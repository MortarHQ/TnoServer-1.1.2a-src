package dev.ftb.mods.ftblibrary.util;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import dev.ftb.mods.ftblibrary.util.neoforge.TextComponentUtilsImpl;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class TextComponentUtils {
   @ExpectPlatform
   @Transformed
   public static Component withLinks(String message) {
      return TextComponentUtilsImpl.withLinks(message);
   }

   public static Component hotkeyTooltip(String txt) {
      return Component.literal("[")
         .withStyle(ChatFormatting.DARK_GRAY)
         .append(Component.literal(txt).withStyle(ChatFormatting.GRAY))
         .append(Component.literal("]").withStyle(ChatFormatting.DARK_GRAY));
   }

   public static Component translatedDimension(ResourceKey<Level> key) {
      return translatedDimension(key.location());
   }

   public static Component translatedDimension(ResourceLocation dimId) {
      return Component.translatableWithFallback(dimId.toLanguageKey("dimension"), dimId.toString());
   }
}
