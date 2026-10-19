package noobanidus.mods.lootr.common.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger.SimpleInstance;
import net.minecraft.server.level.ServerPlayer;
import noobanidus.mods.lootr.common.api.advancement.IContainerTrigger;
import noobanidus.mods.lootr.common.api.advancement.ITrigger;

public class ContainerTrigger extends SimpleCriterionTrigger<ContainerTrigger.TriggerInstance> implements IContainerTrigger {
   @Override
   public void trigger(ServerPlayer player, UUID condition) {
      this.trigger(player, ContainerTrigger.TriggerInstance::test);
   }

   public Codec<ContainerTrigger.TriggerInstance> codec() {
      return ContainerTrigger.TriggerInstance.CODEC;
   }

   public static Criterion<ContainerTrigger.TriggerInstance> looted(ITrigger trigger) {
      return ((ContainerTrigger)trigger.getTrigger()).createCriterion(new ContainerTrigger.TriggerInstance(Optional.empty()));
   }

   public record TriggerInstance(Optional<ContextAwarePredicate> player) implements SimpleInstance {
      public static final Codec<ContainerTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
         codec -> codec.group(ContextAwarePredicate.CODEC.optionalFieldOf("player").forGetter(ContainerTrigger.TriggerInstance::player))
            .apply(codec, ContainerTrigger.TriggerInstance::new)
      );

      public boolean test() {
         return true;
      }
   }
}
