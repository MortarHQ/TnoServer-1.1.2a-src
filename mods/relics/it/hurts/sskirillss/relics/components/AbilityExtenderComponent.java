package it.hurts.sskirillss.relics.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record AbilityExtenderComponent(int cooldownCap, int cooldown, boolean ticking) {
   public static final AbilityExtenderComponent EMPTY = new AbilityExtenderComponent(0, 0, false);
   public static final Codec<AbilityExtenderComponent> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            Codec.INT.fieldOf("cooldownCap").forGetter(AbilityExtenderComponent::cooldownCap),
            Codec.INT.fieldOf("cooldown").forGetter(AbilityExtenderComponent::cooldown),
            Codec.BOOL.fieldOf("ticking").forGetter(AbilityExtenderComponent::ticking)
         )
         .apply(instance, AbilityExtenderComponent::new)
   );

   public static AbilityExtenderComponent.AbilityExtenderComponentBuilder builder() {
      return new AbilityExtenderComponent.AbilityExtenderComponentBuilder();
   }

   public AbilityExtenderComponent.AbilityExtenderComponentBuilder toBuilder() {
      return new AbilityExtenderComponent.AbilityExtenderComponentBuilder().cooldownCap(this.cooldownCap).cooldown(this.cooldown).ticking(this.ticking);
   }

   public static class AbilityExtenderComponentBuilder {
      private int cooldownCap;
      private int cooldown;
      private boolean ticking;

      AbilityExtenderComponentBuilder() {
      }

      public AbilityExtenderComponent.AbilityExtenderComponentBuilder cooldownCap(int cooldownCap) {
         this.cooldownCap = cooldownCap;
         return this;
      }

      public AbilityExtenderComponent.AbilityExtenderComponentBuilder cooldown(int cooldown) {
         this.cooldown = cooldown;
         return this;
      }

      public AbilityExtenderComponent.AbilityExtenderComponentBuilder ticking(boolean ticking) {
         this.ticking = ticking;
         return this;
      }

      public AbilityExtenderComponent build() {
         return new AbilityExtenderComponent(this.cooldownCap, this.cooldown, this.ticking);
      }

      @Override
      public String toString() {
         return "AbilityExtenderComponent.AbilityExtenderComponentBuilder(cooldownCap="
            + this.cooldownCap
            + ", cooldown="
            + this.cooldown
            + ", ticking="
            + this.ticking
            + ")";
      }
   }
}
