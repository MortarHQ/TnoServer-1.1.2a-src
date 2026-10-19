package cc.sighs.oelib.bless;

import cc.sighs.oelib.config.ConfigAccess;
import cc.sighs.oelib.config.ConfigManager;
import cc.sighs.oelib.config.ConfigRecordCodecBuilder;
import cc.sighs.oelib.config.ConfigUnit;
import cc.sighs.oelib.config.field.ConfigField;
import cc.sighs.oelib.config.model.ConfigStorageFormat;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;

public record FestivalToastConfig(boolean enabled, boolean chineseFestivalsOnlyForChineseLanguage, Map<String, FestivalToastConfig.FestivalEntry> festivals) {
   private static final String FILE_NAME = "oelib_festivals";
   public static final ConfigUnit<FestivalToastConfig> UNIT = ConfigRecordCodecBuilder.createClient(
      ResourceLocation.fromNamespaceAndPath("oelib", "festival_toast"),
      instance -> instance.group(
            ConfigField.bool("enabled").defaultValue(true).tooltip().comment("是否启用节日祝福").<FestivalToastConfig>forGetter(FestivalToastConfig::enabled),
            ConfigField.bool("chineseFestivalsOnlyForChineseLanguage")
               .defaultValue(true)
               .tooltip()
               .comment("仅在中文语言环境显示中文节日")
               .<FestivalToastConfig>forGetter(FestivalToastConfig::chineseFestivalsOnlyForChineseLanguage),
            ((ConfigField.MapBuilder)ConfigField.map("festivals", Codec.STRING, FestivalToastConfig.FestivalEntry.CODEC).defaultValue(defaultFestivals()))
               .forGetter(FestivalToastConfig::festivals)
         )
         .apply(instance, FestivalToastConfig::new),
      meta -> meta.directory("oelib").fileName("oelib_festivals").format(ConfigStorageFormat.TOML)
   );
   public static final ConfigAccess<FestivalToastConfig> ACCESS = new ConfigAccess<>(UNIT);

   public static FestivalToastConfig get() {
      return UNIT.get();
   }

   public static void register() {
      ConfigManager.registerClient(UNIT);
   }

   private static Map<String, FestivalToastConfig.FestivalEntry> defaultFestivals() {
      Map<String, FestivalToastConfig.FestivalEntry> m = new HashMap<>();
      m.put("new_year", new FestivalToastConfig.FestivalEntry(true, ""));
      m.put("chongyang", new FestivalToastConfig.FestivalEntry(true, ""));
      m.put("valentine", new FestivalToastConfig.FestivalEntry(true, ""));
      return m;
   }

   public record FestivalEntry(boolean enabled, String lastShownDate) {
      public static final Codec<FestivalToastConfig.FestivalEntry> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               Codec.BOOL.fieldOf("enabled").orElse(true).forGetter(FestivalToastConfig.FestivalEntry::enabled),
               Codec.STRING.fieldOf("lastShownDate").orElse("").forGetter(FestivalToastConfig.FestivalEntry::lastShownDate)
            )
            .apply(instance, FestivalToastConfig.FestivalEntry::new)
      );
   }
}
