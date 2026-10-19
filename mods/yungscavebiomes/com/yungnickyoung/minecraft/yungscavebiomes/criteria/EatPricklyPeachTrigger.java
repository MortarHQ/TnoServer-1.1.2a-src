package com.yungnickyoung.minecraft.yungscavebiomes.criteria;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger.SimpleInstance;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public class EatPricklyPeachTrigger extends SimpleCriterionTrigger<EatPricklyPeachTrigger.TriggerInstance> {
   public void trigger(ServerPlayer serverPlayer) {
      this.trigger(serverPlayer, EatPricklyPeachTrigger.TriggerInstance::matches);
   }

   @NotNull
   public Codec<EatPricklyPeachTrigger.TriggerInstance> codec() {
      return EatPricklyPeachTrigger.TriggerInstance.CODEC;
   }

   public record TriggerInstance(Optional<ContextAwarePredicate> player) implements SimpleInstance {
      public static final Codec<EatPricklyPeachTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
         builder -> builder.group(EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(EatPricklyPeachTrigger.TriggerInstance::player))
            .apply(builder, EatPricklyPeachTrigger.TriggerInstance::new)
      );

      public boolean matches() {
         return true;
      }
   }
}
