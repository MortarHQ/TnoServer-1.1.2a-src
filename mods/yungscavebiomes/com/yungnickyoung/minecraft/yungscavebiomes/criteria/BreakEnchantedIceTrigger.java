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

public class BreakEnchantedIceTrigger extends SimpleCriterionTrigger<BreakEnchantedIceTrigger.TriggerInstance> {
   public void trigger(ServerPlayer serverPlayer) {
      this.trigger(serverPlayer, BreakEnchantedIceTrigger.TriggerInstance::matches);
   }

   @NotNull
   public Codec<BreakEnchantedIceTrigger.TriggerInstance> codec() {
      return BreakEnchantedIceTrigger.TriggerInstance.CODEC;
   }

   public record TriggerInstance(Optional<ContextAwarePredicate> player) implements SimpleInstance {
      public static final Codec<BreakEnchantedIceTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
         builder -> builder.group(EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(BreakEnchantedIceTrigger.TriggerInstance::player))
            .apply(builder, BreakEnchantedIceTrigger.TriggerInstance::new)
      );

      public boolean matches() {
         return true;
      }
   }
}
