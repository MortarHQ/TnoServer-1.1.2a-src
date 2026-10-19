package com.bobmowzie.mowziesmobs.server.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger.SimpleInstance;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public class SculptorFailureTrigger extends SimpleCriterionTrigger<SculptorFailureTrigger.Instance> {
   public void trigger(@NotNull ServerPlayer player) {
      super.trigger(player, instance -> true);
   }

   @NotNull
   public Codec<SculptorFailureTrigger.Instance> codec() {
      return SculptorFailureTrigger.Instance.CODEC;
   }

   public record Instance(Optional<ContextAwarePredicate> player) implements SimpleInstance {
      public static final Codec<SculptorFailureTrigger.Instance> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(SculptorFailureTrigger.Instance::player))
            .apply(instance, SculptorFailureTrigger.Instance::new)
      );
   }
}
