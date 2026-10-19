package com.sighs.oneenoughblock.init;

import cc.sighs.oelib.config.ConfigManager;
import cc.sighs.oelib.config.ConfigRecordCodecBuilder;
import cc.sighs.oelib.config.ConfigUnit;
import cc.sighs.oelib.config.datafix.ConfigFixRegistry;
import cc.sighs.oelib.config.datafix.ConfigFixRegistry.FixContext;
import cc.sighs.oelib.config.field.ConfigField;
import cc.sighs.oelib.config.field.ConfigField.BoolBuilder;
import cc.sighs.oelib.config.field.ConfigField.MapBuilder;
import cc.sighs.oelib.config.model.ConfigStorageFormat;
import com.mafuyu404.oneenoughitem.data.Replacements.ProcessingMode;
import com.mafuyu404.oneenoughitem.init.config.OEIConfig.DefaultRules;
import com.mojang.serialization.Codec;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;

public record OEBConfig(boolean replaceExistedBlock, boolean extendedBlockProperty, DefaultRules defaultRules) {
   private static final String FILE_NAME = "common";
   public static final ConfigUnit<OEBConfig> UNIT = ConfigRecordCodecBuilder.create(
      ResourceLocation.fromNamespaceAndPath("oeb", "common_config"),
      instance -> instance.group(
            ((BoolBuilder)((BoolBuilder)((BoolBuilder)ConfigField.bool("Replace_Existed_Block").defaultValue(false)).tooltip())
                  .comment("config.oei.common.replace_existed_block"))
               .forGetter(OEBConfig::replaceExistedBlock),
            ((BoolBuilder)((BoolBuilder)ConfigField.bool("Extended_Block_Property").defaultValue(false)).tooltip()).forGetter(OEBConfig::extendedBlockProperty),
            ((MapBuilder)((MapBuilder)((MapBuilder)ConfigField.map("Default_Rules_data", Codec.STRING, ProcessingMode.CODEC).defaultValue(new HashMap()))
                     .tooltip())
                  .comment("config.oeb.default_rules.data"))
               .forGetter(cfg -> (Map)cfg.defaultRules().data().orElseGet(HashMap::new)),
            ((MapBuilder)((MapBuilder)((MapBuilder)ConfigField.map("Default_Rules_tag", Codec.STRING, ProcessingMode.CODEC).defaultValue(new HashMap()))
                     .tooltip())
                  .comment("config.oeb.default_rules.tag"))
               .forGetter(cfg -> (Map)cfg.defaultRules().tag().orElseGet(HashMap::new))
         )
         .apply(
            instance,
            (replaceExistedBlock, extendedBlockProperty, data, tag) -> new OEBConfig(
               replaceExistedBlock,
               extendedBlockProperty,
               new DefaultRules(data.isEmpty() ? Optional.empty() : Optional.of(data), tag.isEmpty() ? Optional.empty() : Optional.of(tag))
            )
         ),
      meta -> meta.directory("oeb").fileName("common").format(ConfigStorageFormat.JSON)
   );

   public static OEBConfig get() {
      return (OEBConfig)UNIT.get();
   }

   public static void register() {
      ConfigFixRegistry.register(ResourceLocation.fromNamespaceAndPath("oeb", "common_config"), 1, b -> b.fix(0, 1, dyn -> {
         FixContext ctx = new FixContext(dyn);
         dyn = ctx.rename("common.Replace_Existed_Block", "Replace_Existed_Block");
         dyn = new FixContext(dyn).rename("common.Default_Rules.data", "Default_Rules_data");
         dyn = new FixContext(dyn).rename("common.Default_Rules.tag", "Default_Rules_tag");
         dyn = new FixContext(dyn).rename("Default_Rules.data", "Default_Rules_data");
         return new FixContext(dyn).rename("Default_Rules.tag", "Default_Rules_tag");
      }));
      ConfigManager.registerServer(UNIT, player -> player.hasPermissions(4));
   }
}
