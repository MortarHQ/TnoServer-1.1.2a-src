package it.hurts.sskirillss.relics.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public record AbilityComponent(Map<String, StatComponent> stats, ResearchComponent research, LockComponent lock, AbilityExtenderComponent extender, int points) {
   public static final AbilityComponent EMPTY = new AbilityComponent(Map.of(), ResearchComponent.EMPTY, LockComponent.EMPTY, AbilityExtenderComponent.EMPTY, 0);
   public static final Codec<AbilityComponent> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            Codec.unboundedMap(Codec.STRING, StatComponent.CODEC).fieldOf("stats").forGetter(AbilityComponent::stats),
            ResearchComponent.CODEC.fieldOf("research").forGetter(AbilityComponent::research),
            LockComponent.CODEC.fieldOf("lock").forGetter(AbilityComponent::lock),
            AbilityExtenderComponent.CODEC.fieldOf("extender").forGetter(AbilityComponent::extender),
            Codec.INT.fieldOf("points").forGetter(AbilityComponent::points)
         )
         .apply(instance, AbilityComponent::new)
   );

   public static AbilityComponent.AbilityComponentBuilder builder() {
      return new AbilityComponent.AbilityComponentBuilder();
   }

   public AbilityComponent.AbilityComponentBuilder toBuilder() {
      AbilityComponent.AbilityComponentBuilder builder = new AbilityComponent.AbilityComponentBuilder()
         .research(this.research)
         .lock(this.lock)
         .extender(this.extender)
         .points(this.points);
      if (this.stats != null) {
         builder.stats(this.stats);
      }

      return builder;
   }

   public static class AbilityComponentBuilder {
      private ArrayList<String> stats$key;
      private ArrayList<StatComponent> stats$value;
      private ResearchComponent research;
      private LockComponent lock;
      private AbilityExtenderComponent extender;
      private int points;

      AbilityComponentBuilder() {
      }

      public AbilityComponent.AbilityComponentBuilder stat(String statKey, StatComponent statValue) {
         if (this.stats$key == null) {
            this.stats$key = new ArrayList<>();
            this.stats$value = new ArrayList<>();
         }

         this.stats$key.add(statKey);
         this.stats$value.add(statValue);
         return this;
      }

      public AbilityComponent.AbilityComponentBuilder stats(Map<? extends String, ? extends StatComponent> stats) {
         if (stats == null) {
            throw new NullPointerException("stats cannot be null");
         }

         if (this.stats$key == null) {
            this.stats$key = new ArrayList<>();
            this.stats$value = new ArrayList<>();
         }

         for (Entry<? extends String, ? extends StatComponent> $lombokEntry : stats.entrySet()) {
            this.stats$key.add($lombokEntry.getKey());
            this.stats$value.add($lombokEntry.getValue());
         }

         return this;
      }

      public AbilityComponent.AbilityComponentBuilder clearStats() {
         if (this.stats$key != null) {
            this.stats$key.clear();
            this.stats$value.clear();
         }

         return this;
      }

      public AbilityComponent.AbilityComponentBuilder research(ResearchComponent research) {
         this.research = research;
         return this;
      }

      public AbilityComponent.AbilityComponentBuilder lock(LockComponent lock) {
         this.lock = lock;
         return this;
      }

      public AbilityComponent.AbilityComponentBuilder extender(AbilityExtenderComponent extender) {
         this.extender = extender;
         return this;
      }

      public AbilityComponent.AbilityComponentBuilder points(int points) {
         this.points = points;
         return this;
      }

      public AbilityComponent build() {
         return new AbilityComponent(
            switch (this.stats$key == null ? 0 : this.stats$key.size()) {
               case 0 -> Collections.emptyMap();
               case 1 -> Collections.singletonMap(this.stats$key.get(0), this.stats$value.get(0));
               default -> {
                  Map<String, StatComponent> stats = new LinkedHashMap<>(
                     this.stats$key.size() < 1073741824 ? 1 + this.stats$key.size() + (this.stats$key.size() - 3) / 3 : Integer.MAX_VALUE
                  );

                  for (int $i = 0; $i < this.stats$key.size(); $i++) {
                     stats.put(this.stats$key.get($i), this.stats$value.get($i));
                  }

                  yield Collections.unmodifiableMap(stats);
               }
            },
            this.research,
            this.lock,
            this.extender,
            this.points
         );
      }

      @Override
      public String toString() {
         return "AbilityComponent.AbilityComponentBuilder(stats$key="
            + this.stats$key
            + ", stats$value="
            + this.stats$value
            + ", research="
            + this.research
            + ", lock="
            + this.lock
            + ", extender="
            + this.extender
            + ", points="
            + this.points
            + ")";
      }
   }
}
