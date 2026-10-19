package it.hurts.sskirillss.relics.items.relics.base.data.loot;

import it.hurts.sskirillss.relics.config.data.LootConfigData;
import it.hurts.sskirillss.relics.config.data.LootEntryConfigData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LootData {
   private List<LootEntry> entries;

   public LootConfigData toConfigData() {
      return new LootConfigData(
         this.entries.stream().map(entry -> new LootEntryConfigData(entry.getDimensions(), entry.getBiomes(), entry.getTables(), entry.getWeight())).toList()
      );
   }

   LootData(List<LootEntry> entries) {
      this.entries = entries;
   }

   public static LootData.LootDataBuilder builder() {
      return new LootData.LootDataBuilder();
   }

   public List<LootEntry> getEntries() {
      return this.entries;
   }

   public void setEntries(List<LootEntry> entries) {
      this.entries = entries;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof LootData other)) {
         return false;
      } else {
         if (!other.canEqual(this)) {
            return false;
         }

         Object this$entries = this.getEntries();
         Object other$entries = other.getEntries();
         return this$entries == null ? other$entries == null : this$entries.equals(other$entries);
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof LootData;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $entries = this.getEntries();
      return result * 59 + ($entries == null ? 43 : $entries.hashCode());
   }

   @Override
   public String toString() {
      return "LootData(entries=" + this.getEntries() + ")";
   }

   public static class LootDataBuilder {
      private List<LootEntry> entries = new ArrayList<>();

      public LootData.LootDataBuilder entry(LootEntry... entries) {
         this.entries.addAll(Arrays.asList(entries));
         return this;
      }

      LootDataBuilder() {
      }

      public LootData.LootDataBuilder entries(List<LootEntry> entries) {
         this.entries = entries;
         return this;
      }

      public LootData build() {
         return new LootData(this.entries);
      }

      @Override
      public String toString() {
         return "LootData.LootDataBuilder(entries=" + this.entries + ")";
      }
   }
}
