package noobanidus.mods.lootr.common.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger.SimpleInstance;
import net.minecraft.server.level.ServerPlayer;
import noobanidus.mods.lootr.common.api.advancement.ILootedStatTrigger;
import noobanidus.mods.lootr.common.api.registry.LootrRegistry;

public class LootedStatTrigger extends SimpleCriterionTrigger<LootedStatTrigger.TriggerInstance> implements ILootedStatTrigger {
   @Override
   public void trigger(ServerPlayer player) {
      this.trigger(player, instance -> instance.test(player));
   }

   public Codec<LootedStatTrigger.TriggerInstance> codec() {
      return LootedStatTrigger.TriggerInstance.CODEC;
   }

   public static Criterion<LootedStatTrigger.TriggerInstance> looted(int count) {
      return ((LootedStatTrigger)LootrRegistry.getStatTrigger().getTrigger())
         .createCriterion(new LootedStatTrigger.TriggerInstance(Optional.empty(), Ints.exactly(count)));
   }

   public record TriggerInstance(Optional<ContextAwarePredicate> player, Ints score) implements SimpleInstance {
      public static final Codec<LootedStatTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
         codec -> codec.group(
               ContextAwarePredicate.CODEC.optionalFieldOf("player").forGetter(LootedStatTrigger.TriggerInstance::player),
               Ints.CODEC.optionalFieldOf("score", Ints.ANY).forGetter(LootedStatTrigger.TriggerInstance::score)
            )
            .apply(codec, LootedStatTrigger.TriggerInstance::new)
      );

      public boolean test(ServerPlayer player) {
         return this.score.matches(player.getStats().getValue(LootrRegistry.getLootedStat()));
      }
   }
}
