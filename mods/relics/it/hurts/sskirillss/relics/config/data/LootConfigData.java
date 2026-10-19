package it.hurts.sskirillss.relics.config.data;

import it.hurts.octostudios.octolib.module.config.annotation.Prop;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootData;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootEntry;
import java.util.List;

public class LootConfigData {
   @Prop(comment = "List of conditions for obtaining the relic. Supports both direct ID specification and regular expressions.")
   private List<LootEntryConfigData> entries;

   public LootData toData(IRelicItem relic) {
      LootData data = relic.getLootData();
      data.setEntries(
         this.entries.stream().map(entry -> new LootEntry(entry.getDimensions(), entry.getBiomes(), entry.getTables(), entry.getWeight())).toList()
      );
      return data;
   }

   public List<LootEntryConfigData> getEntries() {
      return this.entries;
   }

   public void setEntries(List<LootEntryConfigData> entries) {
      this.entries = entries;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof LootConfigData other)) {
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
      return other instanceof LootConfigData;
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
      return "LootConfigData(entries=" + this.getEntries() + ")";
   }

   public LootConfigData() {
   }

   public LootConfigData(List<LootEntryConfigData> entries) {
      this.entries = entries;
   }
}
