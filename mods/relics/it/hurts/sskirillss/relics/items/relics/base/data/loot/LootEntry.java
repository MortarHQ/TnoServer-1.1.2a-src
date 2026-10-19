package it.hurts.sskirillss.relics.items.relics.base.data.loot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LootEntry {
   private List<String> dimensions;
   private List<String> biomes;
   private List<String> tables;
   private int weight;

   private static int $default$weight() {
      return 1;
   }

   public static LootEntry.LootEntryBuilder builder() {
      return new LootEntry.LootEntryBuilder();
   }

   public List<String> getDimensions() {
      return this.dimensions;
   }

   public List<String> getBiomes() {
      return this.biomes;
   }

   public List<String> getTables() {
      return this.tables;
   }

   public int getWeight() {
      return this.weight;
   }

   public void setDimensions(List<String> dimensions) {
      this.dimensions = dimensions;
   }

   public void setBiomes(List<String> biomes) {
      this.biomes = biomes;
   }

   public void setTables(List<String> tables) {
      this.tables = tables;
   }

   public void setWeight(int weight) {
      this.weight = weight;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof LootEntry other)) {
         return false;
      } else {
         if (!other.canEqual(this)) {
            return false;
         }

         if (this.getWeight() != other.getWeight()) {
            return false;
         }

         Object this$dimensions = this.getDimensions();
         Object other$dimensions = other.getDimensions();
         if (this$dimensions == null ? other$dimensions == null : this$dimensions.equals(other$dimensions)) {
            Object this$biomes = this.getBiomes();
            Object other$biomes = other.getBiomes();
            if (this$biomes == null ? other$biomes == null : this$biomes.equals(other$biomes)) {
               Object this$tables = this.getTables();
               Object other$tables = other.getTables();
               return this$tables == null ? other$tables == null : this$tables.equals(other$tables);
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof LootEntry;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + this.getWeight();
      Object $dimensions = this.getDimensions();
      result = result * 59 + ($dimensions == null ? 43 : $dimensions.hashCode());
      Object $biomes = this.getBiomes();
      result = result * 59 + ($biomes == null ? 43 : $biomes.hashCode());
      Object $tables = this.getTables();
      return result * 59 + ($tables == null ? 43 : $tables.hashCode());
   }

   @Override
   public String toString() {
      return "LootEntry(dimensions="
         + this.getDimensions()
         + ", biomes="
         + this.getBiomes()
         + ", tables="
         + this.getTables()
         + ", weight="
         + this.getWeight()
         + ")";
   }

   public LootEntry(List<String> dimensions, List<String> biomes, List<String> tables, int weight) {
      this.dimensions = dimensions;
      this.biomes = biomes;
      this.tables = tables;
      this.weight = weight;
   }

   public static class LootEntryBuilder {
      private boolean weight$set;
      private int weight$value;
      private List<String> dimensions = new ArrayList<>();
      private List<String> biomes = new ArrayList<>();
      private List<String> tables = new ArrayList<>();

      public LootEntry.LootEntryBuilder dimension(String... dimensions) {
         return this.dimensions(Arrays.asList(dimensions));
      }

      public LootEntry.LootEntryBuilder dimensions(List<String> dimensions) {
         this.dimensions.addAll(dimensions);
         return this;
      }

      public LootEntry.LootEntryBuilder biome(String... biomes) {
         return this.biomes(Arrays.asList(biomes));
      }

      public LootEntry.LootEntryBuilder biomes(List<String> biomes) {
         this.biomes.addAll(biomes);
         return this;
      }

      public LootEntry.LootEntryBuilder table(String... table) {
         return this.tables(Arrays.asList(table));
      }

      public LootEntry.LootEntryBuilder tables(List<String> tables) {
         this.tables.addAll(tables);
         return this;
      }

      LootEntryBuilder() {
      }

      public LootEntry.LootEntryBuilder weight(int weight) {
         this.weight$value = weight;
         this.weight$set = true;
         return this;
      }

      public LootEntry build() {
         int weight$value = this.weight$value;
         if (!this.weight$set) {
            weight$value = LootEntry.$default$weight();
         }

         return new LootEntry(this.dimensions, this.biomes, this.tables, weight$value);
      }

      @Override
      public String toString() {
         return "LootEntry.LootEntryBuilder(dimensions="
            + this.dimensions
            + ", biomes="
            + this.biomes
            + ", tables="
            + this.tables
            + ", weight$value="
            + this.weight$value
            + ")";
      }
   }
}
