package net.regions_unexplored.worldgen.rulesource;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.SurfaceRules.Context;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;
import net.minecraft.world.level.levelgen.SurfaceRules.SurfaceRule;
import net.regions_unexplored.config.RUConfigHandler;

public record ConfigRuleSource(String key, RuleSource onEnabled, RuleSource onDisabled) implements RuleSource {
   public static final KeyDispatchDataCodec<ConfigRuleSource> CODEC = KeyDispatchDataCodec.of(
      RecordCodecBuilder.mapCodec(
         i -> i.group(
               Codec.STRING.fieldOf("key").forGetter(ConfigRuleSource::key),
               RuleSource.CODEC.fieldOf("on_enabled").forGetter(ConfigRuleSource::onEnabled),
               RuleSource.CODEC.fieldOf("on_disabled").forGetter(ConfigRuleSource::onDisabled)
            )
            .apply(i, ConfigRuleSource::new)
      )
   );

   public KeyDispatchDataCodec<? extends RuleSource> codec() {
      return CODEC;
   }

   public SurfaceRule apply(Context context) {
      RuleSource source = RUConfigHandler.COMMON.test(this.key) ? this.onEnabled : this.onDisabled;
      return (SurfaceRule)source.apply(context);
   }
}
