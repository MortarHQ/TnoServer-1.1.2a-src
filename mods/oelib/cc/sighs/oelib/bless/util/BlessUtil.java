package cc.sighs.oelib.bless.util;

import cc.sighs.oelib.bless.FestivalToastConfig;
import cc.sighs.oelib.bless.OverlayRegistry;
import cc.sighs.oelib.bless.render.AbstractShaderOverlay;
import com.nlf.calendar.Lunar;
import com.nlf.calendar.Solar;
import java.time.LocalDate;
import java.util.HashMap;
import net.minecraft.client.Minecraft;

public class BlessUtil {
   public static void checkFestival(
      AbstractShaderOverlay overlay, LocalDate today, Solar solar, Lunar lunar, boolean chineseLanguage, FestivalToastConfig config
   ) {
      String id = overlay.festivalId();
      FestivalToastConfig.FestivalEntry entry = config.festivals().get(id);
      boolean enabled = entry == null || entry.enabled();
      if (enabled) {
         if (!overlay.isChineseFestival() || !config.chineseFestivalsOnlyForChineseLanguage() || chineseLanguage) {
            boolean match = isFestivalMatch(overlay, solar, lunar);
            if (match) {
               String lastDate = entry != null ? entry.lastShownDate() : "";
               if (!today.toString().equals(lastDate)) {
                  HashMap<String, FestivalToastConfig.FestivalEntry> map = new HashMap<>(config.festivals());
                  map.put(id, new FestivalToastConfig.FestivalEntry(true, today.toString()));
                  FestivalToastConfig.ACCESS.set(FestivalToastConfig::festivals, map);
                  overlay.show();
               }
            }
         }
      }
   }

   public static boolean isFestivalMatch(AbstractShaderOverlay overlay, Solar solar, Lunar lunar) {
      String id = overlay.festivalId();
      String name = overlay.festivalName();

      return switch (id) {
         case "new_year", "chongyang" -> lunar.getFestivals().contains(name);
         case "valentine" -> solar.getFestivals().contains(name);
         default -> false;
      };
   }

   public static boolean isChineseLanguage(Minecraft minecraft, FestivalToastConfig config) {
      String code = minecraft.options.languageCode;
      return code.isEmpty() ? false : code.startsWith("zh");
   }

   public static void showFestivalToast() {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.player != null) {
         FestivalToastConfig config = FestivalToastConfig.get();
         if (config.enabled()) {
            LocalDate today = LocalDate.now();
            boolean chineseLanguage = isChineseLanguage(minecraft, config);
            Solar solar = Solar.fromYmd(today.getYear(), today.getMonthValue(), today.getDayOfMonth());
            Lunar lunar = solar.getLunar();

            for (AbstractShaderOverlay overlay : OverlayRegistry.REGISTERED_OVERLAYS) {
               checkFestival(overlay, today, solar, lunar, chineseLanguage, config);
            }
         }
      }
   }
}
