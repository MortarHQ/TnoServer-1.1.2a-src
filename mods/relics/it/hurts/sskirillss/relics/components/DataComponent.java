package it.hurts.sskirillss.relics.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record DataComponent(AbilitiesComponent abilities, LevelingComponent leveling) {
   public static final DataComponent EMPTY = new DataComponent(AbilitiesComponent.EMPTY, LevelingComponent.EMPTY);
   public static final Codec<DataComponent> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            AbilitiesComponent.CODEC.fieldOf("abilities").forGetter(DataComponent::abilities),
            LevelingComponent.CODEC.fieldOf("leveling").forGetter(DataComponent::leveling)
         )
         .apply(instance, DataComponent::new)
   );

   public static DataComponent.DataComponentBuilder builder() {
      return new DataComponent.DataComponentBuilder();
   }

   public DataComponent.DataComponentBuilder toBuilder() {
      return new DataComponent.DataComponentBuilder().abilities(this.abilities).leveling(this.leveling);
   }

   public static class DataComponentBuilder {
      private AbilitiesComponent abilities;
      private LevelingComponent leveling;

      DataComponentBuilder() {
      }

      public DataComponent.DataComponentBuilder abilities(AbilitiesComponent abilities) {
         this.abilities = abilities;
         return this;
      }

      public DataComponent.DataComponentBuilder leveling(LevelingComponent leveling) {
         this.leveling = leveling;
         return this;
      }

      public DataComponent build() {
         return new DataComponent(this.abilities, this.leveling);
      }

      @Override
      public String toString() {
         return "DataComponent.DataComponentBuilder(abilities=" + this.abilities + ", leveling=" + this.leveling + ")";
      }
   }
}
