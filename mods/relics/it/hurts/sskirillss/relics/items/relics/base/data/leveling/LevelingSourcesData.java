package it.hurts.sskirillss.relics.items.relics.base.data.leveling;

import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.GemColor;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.GemShape;
import java.util.LinkedHashMap;
import java.util.Map;

public class LevelingSourcesData {
   private Map<String, LevelingSourceData> sources;

   public static LevelingSourcesData.LevelingSourcesDataBuilder builder() {
      return new LevelingSourcesData.LevelingSourcesDataBuilder();
   }

   public Map<String, LevelingSourceData> getSources() {
      return this.sources;
   }

   public void setSources(Map<String, LevelingSourceData> sources) {
      this.sources = sources;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof LevelingSourcesData other)) {
         return false;
      } else {
         if (!other.canEqual(this)) {
            return false;
         }

         Object this$sources = this.getSources();
         Object other$sources = other.getSources();
         return this$sources == null ? other$sources == null : this$sources.equals(other$sources);
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof LevelingSourcesData;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $sources = this.getSources();
      return result * 59 + ($sources == null ? 43 : $sources.hashCode());
   }

   @Override
   public String toString() {
      return "LevelingSourcesData(sources=" + this.getSources() + ")";
   }

   public LevelingSourcesData(Map<String, LevelingSourceData> sources) {
      this.sources = sources;
   }

   public static class LevelingSourcesDataBuilder {
      private Map<String, LevelingSourceData> sources = new LinkedHashMap<>();

      public LevelingSourcesData.LevelingSourcesDataBuilder source(LevelingSourceData source) {
         this.sources.put(source.getId(), source);
         return this;
      }

      LevelingSourcesDataBuilder() {
         LevelingSourceData entry = LevelingSourceData.genericBuilder("spreading").initialValue(25).gem(GemShape.OVAL, GemColor.YELLOW).build();
         this.sources.put(entry.getId(), entry);
      }

      public LevelingSourcesData.LevelingSourcesDataBuilder sources(Map<String, LevelingSourceData> sources) {
         this.sources = sources;
         return this;
      }

      public LevelingSourcesData build() {
         return new LevelingSourcesData(this.sources);
      }

      @Override
      public String toString() {
         return "LevelingSourcesData.LevelingSourcesDataBuilder(sources=" + this.sources + ")";
      }
   }
}
