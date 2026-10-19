package com.mafuyu404.oneenoughitem.init.config;

import cc.sighs.oelib.config.ConfigManager;
import cc.sighs.oelib.config.ConfigRecordCodecBuilder;
import cc.sighs.oelib.config.ConfigUnit;
import cc.sighs.oelib.config.datafix.ConfigFixRegistry;
import cc.sighs.oelib.config.datafix.ConfigFixRegistry.FixContext;
import cc.sighs.oelib.config.field.ConfigField;
import cc.sighs.oelib.config.field.ConfigField.BoolBuilder;
import cc.sighs.oelib.config.field.ConfigField.MapBuilder;
import cc.sighs.oelib.config.model.ConfigStorageFormat;
import com.mafuyu404.oneenoughitem.data.Replacements;
import com.mojang.serialization.Codec;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;

public record OEIConfig(boolean deeperReplace, boolean enableLite, boolean clearFoodProperties, OEIConfig.DefaultRules defaultRules) {
   private static final String FILE_NAME = "common";
   public static final ConfigUnit<OEIConfig> UNIT = ConfigRecordCodecBuilder.create(
      ResourceLocation.fromNamespaceAndPath("oei", "common_config"),
      instance -> instance.group(
            ((BoolBuilder)((BoolBuilder)ConfigField.bool("Deeper_Replace").defaultValue(false)).tooltip()).forGetter(OEIConfig::deeperReplace),
            ((BoolBuilder)((BoolBuilder)ConfigField.bool("Enable_Lite").defaultValue(false)).tooltip()).forGetter(OEIConfig::enableLite),
            ((BoolBuilder)((BoolBuilder)ConfigField.bool("Clear_Food_Properties").defaultValue(false)).tooltip()).forGetter(OEIConfig::clearFoodProperties),
            ((MapBuilder)((MapBuilder)((MapBuilder)ConfigField.map("Default_Rules_data", Codec.STRING, Replacements.ProcessingMode.CODEC)
                        .defaultValue(new HashMap()))
                     .tooltip())
                  .comment("config.oei.default_rules.data"))
               .forGetter(cfg -> cfg.defaultRules().data().orElseGet(HashMap::new)),
            ((MapBuilder)((MapBuilder)((MapBuilder)ConfigField.map("Default_Rules_tag", Codec.STRING, Replacements.ProcessingMode.CODEC)
                        .defaultValue(new HashMap()))
                     .tooltip())
                  .comment("config.oei.default_rules.tag"))
               .forGetter(cfg -> cfg.defaultRules().tag().orElseGet(HashMap::new))
         )
         .apply(
            instance,
            (deeperReplace, enableLite, clearFoodProperties, data, tag) -> new OEIConfig(
               deeperReplace,
               enableLite,
               clearFoodProperties,
               new OEIConfig.DefaultRules(data.isEmpty() ? Optional.empty() : Optional.of(data), tag.isEmpty() ? Optional.empty() : Optional.of(tag))
            )
         ),
      meta -> meta.directory("oei").fileName("common").format(ConfigStorageFormat.JSON)
   );

   public static void register() {
      ConfigFixRegistry.register(ResourceLocation.fromNamespaceAndPath("oei", "common_config"), 1, b -> b.fix(0, 1, dyn -> {
         FixContext ctx = new FixContext(dyn);
         dyn = ctx.rename("common.Deeper_Replace", "Deeper_Replace");
         dyn = new FixContext(dyn).rename("common.Clear_Food_Properties", "Clear_Food_Properties");
         dyn = new FixContext(dyn).rename("common.Enable_Lite", "Enable_Lite");
         dyn = new FixContext(dyn).rename("common.Default_Rules.data", "Default_Rules_data");
         dyn = new FixContext(dyn).rename("common.Default_Rules.tag", "Default_Rules_tag");
         dyn = new FixContext(dyn).rename("Default_Rules.data", "Default_Rules_data");
         return new FixContext(dyn).rename("Default_Rules.tag", "Default_Rules_tag");
      }));
      ConfigManager.registerServer(UNIT, player -> player.hasPermissions(4));
   }

   public static OEIConfig get() {
      return (OEIConfig)UNIT.get();
   }

   public record DefaultRules(Optional<Map<String, Replacements.ProcessingMode>> data, Optional<Map<String, Replacements.ProcessingMode>> tag) {
      public Replacements.Rules toRules() {
         return new Replacements.Rules(this.data, this.tag);
      }
   }
}
