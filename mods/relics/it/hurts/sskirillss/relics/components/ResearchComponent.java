package it.hurts.sskirillss.relics.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record ResearchComponent(Map<String, List<Integer>> links, boolean researched) {
   public static final ResearchComponent EMPTY = new ResearchComponent(new HashMap<>(), false);
   public static final Codec<ResearchComponent> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            Codec.unboundedMap(Codec.STRING, Codec.list(Codec.INT)).fieldOf("links").forGetter(ResearchComponent::links),
            Codec.BOOL.fieldOf("researched").forGetter(ResearchComponent::researched)
         )
         .apply(instance, ResearchComponent::new)
   );

   public static ResearchComponent.ResearchComponentBuilder builder() {
      return new ResearchComponent.ResearchComponentBuilder();
   }

   public ResearchComponent.ResearchComponentBuilder toBuilder() {
      return new ResearchComponent.ResearchComponentBuilder().links(this.links).researched(this.researched);
   }

   public static class ResearchComponentBuilder {
      private Map<String, List<Integer>> links;
      private boolean researched;

      ResearchComponentBuilder() {
      }

      public ResearchComponent.ResearchComponentBuilder links(Map<String, List<Integer>> links) {
         this.links = links;
         return this;
      }

      public ResearchComponent.ResearchComponentBuilder researched(boolean researched) {
         this.researched = researched;
         return this;
      }

      public ResearchComponent build() {
         return new ResearchComponent(this.links, this.researched);
      }

      @Override
      public String toString() {
         return "ResearchComponent.ResearchComponentBuilder(links=" + this.links + ", researched=" + this.researched + ")";
      }
   }
}
