package it.hurts.sskirillss.relics.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public record AbilitiesComponent(Map<String, AbilityComponent> abilities) {
   public static final AbilitiesComponent EMPTY = new AbilitiesComponent(Map.of());
   public static final Codec<AbilitiesComponent> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(Codec.unboundedMap(Codec.STRING, AbilityComponent.CODEC).fieldOf("abilities").forGetter(AbilitiesComponent::abilities))
         .apply(instance, AbilitiesComponent::new)
   );

   public static AbilitiesComponent.AbilitiesComponentBuilder builder() {
      return new AbilitiesComponent.AbilitiesComponentBuilder();
   }

   public AbilitiesComponent.AbilitiesComponentBuilder toBuilder() {
      AbilitiesComponent.AbilitiesComponentBuilder builder = new AbilitiesComponent.AbilitiesComponentBuilder();
      if (this.abilities != null) {
         builder.abilities(this.abilities);
      }

      return builder;
   }

   public static class AbilitiesComponentBuilder {
      private ArrayList<String> abilities$key;
      private ArrayList<AbilityComponent> abilities$value;

      AbilitiesComponentBuilder() {
      }

      public AbilitiesComponent.AbilitiesComponentBuilder ability(String abilityKey, AbilityComponent abilityValue) {
         if (this.abilities$key == null) {
            this.abilities$key = new ArrayList<>();
            this.abilities$value = new ArrayList<>();
         }

         this.abilities$key.add(abilityKey);
         this.abilities$value.add(abilityValue);
         return this;
      }

      public AbilitiesComponent.AbilitiesComponentBuilder abilities(Map<? extends String, ? extends AbilityComponent> abilities) {
         if (abilities == null) {
            throw new NullPointerException("abilities cannot be null");
         }

         if (this.abilities$key == null) {
            this.abilities$key = new ArrayList<>();
            this.abilities$value = new ArrayList<>();
         }

         for (Entry<? extends String, ? extends AbilityComponent> $lombokEntry : abilities.entrySet()) {
            this.abilities$key.add($lombokEntry.getKey());
            this.abilities$value.add($lombokEntry.getValue());
         }

         return this;
      }

      public AbilitiesComponent.AbilitiesComponentBuilder clearAbilities() {
         if (this.abilities$key != null) {
            this.abilities$key.clear();
            this.abilities$value.clear();
         }

         return this;
      }

      public AbilitiesComponent build() {
         return new AbilitiesComponent(
            switch (this.abilities$key == null ? 0 : this.abilities$key.size()) {
               case 0 -> Collections.emptyMap();
               case 1 -> Collections.singletonMap(this.abilities$key.get(0), this.abilities$value.get(0));
               default -> {
                  Map<String, AbilityComponent> abilities = new LinkedHashMap<>(
                     this.abilities$key.size() < 1073741824 ? 1 + this.abilities$key.size() + (this.abilities$key.size() - 3) / 3 : Integer.MAX_VALUE
                  );

                  for (int $i = 0; $i < this.abilities$key.size(); $i++) {
                     abilities.put(this.abilities$key.get($i), this.abilities$value.get($i));
                  }

                  yield Collections.unmodifiableMap(abilities);
               }
            }
         );
      }

      @Override
      public String toString() {
         return "AbilitiesComponent.AbilitiesComponentBuilder(abilities$key=" + this.abilities$key + ", abilities$value=" + this.abilities$value + ")";
      }
   }
}
