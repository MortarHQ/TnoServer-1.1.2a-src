package it.hurts.sskirillss.relics.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record StatComponent(double initialValue) {
   public static final StatComponent EMPTY = new StatComponent(0.0);
   public static final Codec<StatComponent> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(Codec.DOUBLE.fieldOf("initialValue").forGetter(StatComponent::initialValue)).apply(instance, StatComponent::new)
   );

   public static StatComponent.StatComponentBuilder builder() {
      return new StatComponent.StatComponentBuilder();
   }

   public StatComponent.StatComponentBuilder toBuilder() {
      return new StatComponent.StatComponentBuilder().initialValue(this.initialValue);
   }

   public static class StatComponentBuilder {
      private double initialValue;

      StatComponentBuilder() {
      }

      public StatComponent.StatComponentBuilder initialValue(double initialValue) {
         this.initialValue = initialValue;
         return this;
      }

      public StatComponent build() {
         return new StatComponent(this.initialValue);
      }

      @Override
      public String toString() {
         return "StatComponent.StatComponentBuilder(initialValue=" + this.initialValue + ")";
      }
   }
}
