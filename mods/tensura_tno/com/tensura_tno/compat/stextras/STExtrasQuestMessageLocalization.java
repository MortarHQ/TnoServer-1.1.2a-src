package com.tensura_tno.compat.stextras;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public final class STExtrasQuestMessageLocalization {
   private static final String LANG_PREFIX = "@lang:";

   private STExtrasQuestMessageLocalization() {
   }

   public static MutableComponent completed(ResourceLocation questId, @Nullable String originalName) {
      return Component.translatable("stextras.quest.completed", new Object[]{questName(questId, originalName)});
   }

   public static MutableComponent questName(ResourceLocation questId, @Nullable String originalName) {
      if (originalName != null && originalName.startsWith("@lang:")) {
         MutableComponent name = Component.empty();

         for (String part : originalName.substring("@lang:".length()).split("\\+")) {
            if (!part.isEmpty()) {
               name.append(part.startsWith("#") ? Component.literal(part.substring(1)) : Component.translatable(part));
            }
         }

         return name;
      } else {
         String fallback = originalName != null ? originalName : questId.toString();
         return Component.translatableWithFallback(questId.getNamespace() + ".quest.name." + questId.getPath(), fallback);
      }
   }
}
