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

public class SandstormEndTrigger extends SimpleCriterionTrigger<SandstormEndTrigger.TriggerInstance> {
   public void trigger(ServerPlayer serverPlayer) {
      this.trigger(serverPlayer, SandstormEndTrigger.TriggerInstance::matches);
   }

   @NotNull
   public Codec<SandstormEndTrigger.TriggerInstance> codec() {
      return SandstormEndTrigger.TriggerInstance.CODEC;
   }

   public record TriggerInstance(Optional<ContextAwarePredicate> player) implements SimpleInstance {
      public static final Codec<SandstormEndTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
         builder -> builder.group(EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(SandstormEndTrigger.TriggerInstance::player))
            .apply(builder, SandstormEndTrigger.TriggerInstance::new)
      );

      public boolean matches() {
         return true;
      }
   }
}
