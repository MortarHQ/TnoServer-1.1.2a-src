package noobanidus.mods.lootr.common.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger.SimpleInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import noobanidus.mods.lootr.common.api.advancement.IAdvancementTrigger;
import noobanidus.mods.lootr.common.api.registry.LootrRegistry;

public class AdvancementTrigger extends SimpleCriterionTrigger<AdvancementTrigger.TriggerInstance> implements IAdvancementTrigger {
   public Codec<AdvancementTrigger.TriggerInstance> codec() {
      return AdvancementTrigger.TriggerInstance.CODEC;
   }

   @Override
   public void trigger(ServerPlayer player, ResourceLocation advancementId) {
      this.trigger(player, instance -> instance.test(advancementId));
   }

   public static Criterion<AdvancementTrigger.TriggerInstance> completed(ResourceLocation advancementId) {
      return ((AdvancementTrigger)LootrRegistry.getAdvancementTrigger())
         .createCriterion(new AdvancementTrigger.TriggerInstance(Optional.empty(), Optional.of(advancementId)));
   }

   public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<ResourceLocation> advancement) implements SimpleInstance {
      public static final Codec<AdvancementTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
         codec -> codec.group(
               ContextAwarePredicate.CODEC.optionalFieldOf("player").forGetter(AdvancementTrigger.TriggerInstance::player),
               ResourceLocation.CODEC.optionalFieldOf("advancement").forGetter(AdvancementTrigger.TriggerInstance::advancement)
            )
            .apply(codec, AdvancementTrigger.TriggerInstance::new)
      );

      public boolean test(ResourceLocation advancementId) {
         return this.advancement.isEmpty() || this.advancement.get().equals(advancementId);
      }
   }
}
