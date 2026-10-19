package terrablender.worldgen.surface;

import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.SurfaceRules.Context;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;
import net.minecraft.world.level.levelgen.SurfaceRules.SurfaceRule;

public record NamespacedSurfaceRuleSource(RuleSource base, Map<String, RuleSource> sources) implements RuleSource {
   public static final KeyDispatchDataCodec<NamespacedSurfaceRuleSource> CODEC = KeyDispatchDataCodec.of(
      RecordCodecBuilder.mapCodec(
         builder -> builder.group(
               RuleSource.CODEC.fieldOf("base").forGetter(NamespacedSurfaceRuleSource::base),
               Codec.unboundedMap(Codec.STRING, RuleSource.CODEC).fieldOf("sources").forGetter(NamespacedSurfaceRuleSource::sources)
            )
            .apply(builder, NamespacedSurfaceRuleSource::new)
      )
   );

   public KeyDispatchDataCodec<? extends RuleSource> codec() {
      return CODEC;
   }

   public SurfaceRule apply(Context context) {
      Builder<String, SurfaceRule> rules = new Builder();
      this.sources.entrySet().forEach(entry -> rules.put(entry.getKey(), (SurfaceRule)entry.getValue().apply(context)));
      return new NamespacedSurfaceRuleSource.NamespacedRule(context, (SurfaceRule)this.base.apply(context), rules.build());
   }

   record NamespacedRule(Context context, SurfaceRule baseRule, Map<String, SurfaceRule> rules) implements SurfaceRule {
      public BlockState tryApply(int x, int y, int z) {
         Holder<Biome> biome = (Holder<Biome>)this.context.biome.get();
         BlockState state = null;
         if (biome.is(key -> this.rules.containsKey(key.location().getNamespace()))) {
            state = this.rules.get(((ResourceKey)biome.unwrapKey().get()).location().getNamespace()).tryApply(x, y, z);
         }

         if (state == null) {
            state = this.baseRule.tryApply(x, y, z);
         }

         return state;
      }
   }
}
