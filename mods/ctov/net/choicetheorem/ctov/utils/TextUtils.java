package net.choicetheorem.ctov.utils;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public class TextUtils {
   private static final MutableComponent NO_EFFECTS = Component.translatable("effect.none").withStyle(ChatFormatting.GRAY);

   public static MutableComponent getTranslatable(String string, Object... args) {
      return Component.translatable("ctov." + string, args);
   }

   public static ResourceLocation res(String name) {
      return ResourceLocation.fromNamespaceAndPath("ctov", name);
   }
}
