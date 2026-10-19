package it.hurts.sskirillss.relics.config.data;

import java.util.List;

public class LootEntryConfigData {
   private List<String> dimensions;
   private List<String> biomes;
   private List<String> tables;
   private int weight;

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
      } else if (!(o instanceof LootEntryConfigData other)) {
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
      return other instanceof LootEntryConfigData;
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
      return "LootEntryConfigData(dimensions="
         + this.getDimensions()
         + ", biomes="
         + this.getBiomes()
         + ", tables="
         + this.getTables()
         + ", weight="
         + this.getWeight()
         + ")";
   }

   public LootEntryConfigData() {
   }

   public LootEntryConfigData(List<String> dimensions, List<String> biomes, List<String> tables, int weight) {
      this.dimensions = dimensions;
      this.biomes = biomes;
      this.tables = tables;
      this.weight = weight;
   }
}
