package de.keksuccino.konkrete.localization;

import de.keksuccino.konkrete.mixin.mixins.client.IMixinClientLanguage;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.resources.language.ClientLanguage;
import net.minecraft.locale.Language;

@Deprecated(forRemoval = true)
public class LocaleUtils {
   public static String getKeyForString(String s) {
      try {
         Language l = Language.getInstance();
         if (!(l instanceof ClientLanguage)) {
            return null;
         }

         Map<String, String> storage = ((IMixinClientLanguage)l).getStorageKonkrete();

         for (Entry<String, String> m : storage.entrySet()) {
            if (m.getValue().equals(s)) {
               return m.getKey();
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }

      return null;
   }
}
