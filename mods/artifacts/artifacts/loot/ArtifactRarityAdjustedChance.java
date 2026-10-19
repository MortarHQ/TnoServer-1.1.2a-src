package artifacts.loot;

import artifacts.Artifacts;
import artifacts.registry.ModLootConditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition.Builder;

public record ArtifactRarityAdjustedChance(float defaultProbability) implements LootItemCondition {
   public static final MapCodec<ArtifactRarityAdjustedChance> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(Codec.FLOAT.fieldOf("default_probability").forGetter(ArtifactRarityAdjustedChance::defaultProbability))
         .apply(instance, ArtifactRarityAdjustedChance::new)
   );

   public LootItemConditionType getType() {
      return (LootItemConditionType)ModLootConditions.ARTIFACT_RARITY_ADJUSTED_CHANCE.value();
   }

   public boolean test(LootContext context) {
      if (Artifacts.CONFIG.general.artifactRarity.get() > 9999.0) {
         return false;
      }

      float r = (float)Artifacts.CONFIG.general.artifactRarity.get().doubleValue();
      float p = this.defaultProbability;
      float adjustedProbability = p / (p + r - r * p);
      return context.getRandom().nextFloat() < adjustedProbability;
   }

   public static Builder adjustedChance(float probability) {
      return () -> new ArtifactRarityAdjustedChance(probability);
   }
}
