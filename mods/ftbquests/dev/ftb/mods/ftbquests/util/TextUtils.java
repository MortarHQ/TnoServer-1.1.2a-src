package dev.ftb.mods.ftbquests.util;

import com.google.gson.JsonParseException;
import dev.ftb.mods.ftblibrary.util.client.ClientTextComponentUtils;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component.Serializer;
import net.minecraft.network.chat.contents.PlainTextContents;
import org.apache.commons.lang3.text.translate.UnicodeUnescaper;

public class TextUtils {
   private static final UnicodeUnescaper UNESCAPER = new UnicodeUnescaper();

   public static Component parseRawText(String str, Provider provider) {
      String str2 = str.trim();
      if (str2.startsWith("[") && str2.endsWith("]") || str2.startsWith("{") && str2.endsWith("}")) {
         try {
            MutableComponent res = Serializer.fromJson(UNESCAPER.translate(str2), provider);
            if (res != null) {
               return res;
            }
         } catch (JsonParseException var4) {
         }
      }

      return ClientTextComponentUtils.parse(UNESCAPER.translate(str));
   }

   public static List<String> fromListTag(ListTag tag) {
      List<String> res = new ArrayList<>();
      tag.forEach(el -> {
         if (el.getId() == 8) {
            res.add(el.getAsString());
         }
      });
      return res;
   }

   public static boolean isComponentEmpty(Component c) {
      return c.getSiblings().isEmpty() && c.getContents() == PlainTextContents.EMPTY;
   }
}
