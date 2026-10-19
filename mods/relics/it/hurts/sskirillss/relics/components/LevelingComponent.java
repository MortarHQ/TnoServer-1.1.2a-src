package it.hurts.sskirillss.relics.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record LevelingComponent(int level, int experience, int points, int luck) {
   public static final LevelingComponent EMPTY = new LevelingComponent(0, 0, 0, 0);
   public static final Codec<LevelingComponent> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            Codec.INT.fieldOf("level").forGetter(LevelingComponent::level),
            Codec.INT.fieldOf("experience").forGetter(LevelingComponent::experience),
            Codec.INT.fieldOf("points").forGetter(LevelingComponent::points),
            Codec.INT.fieldOf("luck").forGetter(LevelingComponent::luck)
         )
         .apply(instance, LevelingComponent::new)
   );

   public static LevelingComponent.LevelingComponentBuilder builder() {
      return new LevelingComponent.LevelingComponentBuilder();
   }

   public LevelingComponent.LevelingComponentBuilder toBuilder() {
      return new LevelingComponent.LevelingComponentBuilder().level(this.level).experience(this.experience).points(this.points).luck(this.luck);
   }

   public static class LevelingComponentBuilder {
      private int level;
      private int experience;
      private int points;
      private int luck;

      LevelingComponentBuilder() {
      }

      public LevelingComponent.LevelingComponentBuilder level(int level) {
         this.level = level;
         return this;
      }

      public LevelingComponent.LevelingComponentBuilder experience(int experience) {
         this.experience = experience;
         return this;
      }

      public LevelingComponent.LevelingComponentBuilder points(int points) {
         this.points = points;
         return this;
      }

      public LevelingComponent.LevelingComponentBuilder luck(int luck) {
         this.luck = luck;
         return this;
      }

      public LevelingComponent build() {
         return new LevelingComponent(this.level, this.experience, this.points, this.luck);
      }

      @Override
      public String toString() {
         return "LevelingComponent.LevelingComponentBuilder(level="
            + this.level
            + ", experience="
            + this.experience
            + ", points="
            + this.points
            + ", luck="
            + this.luck
            + ")";
      }
   }
}
