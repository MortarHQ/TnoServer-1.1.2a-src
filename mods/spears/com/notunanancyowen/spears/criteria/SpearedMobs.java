package com.notunanancyowen.spears.criteria;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.notunanancyowen.spears.Spears;
import java.util.Optional;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger.SimpleInstance;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ExtraCodecs;

public class SpearedMobs extends SimpleCriterionTrigger<SpearedMobs.AllSpearedMobs> {
   public Codec<SpearedMobs.AllSpearedMobs> codec() {
      return SpearedMobs.AllSpearedMobs.CODEC;
   }

   public void spearMob(ServerPlayer serverPlayerEntity, int i) {
      this.trigger(serverPlayerEntity, arg -> arg.spearedMobsOver(i));
   }

   public record AllSpearedMobs(Optional<ContextAwarePredicate> player, Optional<Integer> count) implements SimpleInstance {
      public static final Codec<SpearedMobs.AllSpearedMobs> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(SpearedMobs.AllSpearedMobs::player),
               ExtraCodecs.POSITIVE_INT.optionalFieldOf("count").forGetter(SpearedMobs.AllSpearedMobs::count)
            )
            .apply(instance, SpearedMobs.AllSpearedMobs::new)
      );

      public static Criterion<SpearedMobs.AllSpearedMobs> setSpearedMobs(int i) {
         return Spears.SPEARED_MOBS.createCriterion(new SpearedMobs.AllSpearedMobs(Optional.empty(), Optional.of(i)));
      }

      public boolean spearedMobsOver(int i) {
         return this.count.isEmpty() || i >= this.count.get();
      }
   }
}
