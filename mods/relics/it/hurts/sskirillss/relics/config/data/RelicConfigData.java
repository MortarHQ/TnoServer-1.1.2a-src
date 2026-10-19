package it.hurts.sskirillss.relics.config.data;

import it.hurts.octostudios.octolib.module.config.annotation.IgnoreProp;
import it.hurts.octostudios.octolib.module.config.impl.OctoConfig;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.level.RelicLootModifier;

public class RelicConfigData implements OctoConfig {
   @IgnoreProp
   private IRelicItem relic;
   private AbilitiesConfigData abilitiesData;
   private LevelingConfigData levelingData;
   private LootConfigData lootData;

   public RelicConfigData(IRelicItem relic) {
      this.relic = relic;
      this.setAbilitiesData(relic.getAbilitiesData().toConfigData());
      this.setLevelingData(relic.getLevelingData().toConfigData());
      this.setLootData(relic.getLootData().toConfigData());
   }

   public RelicData toData(IRelicItem relic) {
      RelicData data = relic.getRelicData();
      data.setAbilities(this.abilitiesData.toData(relic));
      data.setLeveling(this.levelingData.toData(relic));
      data.setLoot(this.lootData.toData(relic));
      return data;
   }

   public void onLoadObject(Object object) {
      this.relic.setRelicData(((RelicConfigData)object).toData(this.relic));
      RelicLootModifier.processRelicCache(this.relic);
   }

   public IRelicItem getRelic() {
      return this.relic;
   }

   public AbilitiesConfigData getAbilitiesData() {
      return this.abilitiesData;
   }

   public LevelingConfigData getLevelingData() {
      return this.levelingData;
   }

   public LootConfigData getLootData() {
      return this.lootData;
   }

   public void setRelic(IRelicItem relic) {
      this.relic = relic;
   }

   public void setAbilitiesData(AbilitiesConfigData abilitiesData) {
      this.abilitiesData = abilitiesData;
   }

   public void setLevelingData(LevelingConfigData levelingData) {
      this.levelingData = levelingData;
   }

   public void setLootData(LootConfigData lootData) {
      this.lootData = lootData;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof RelicConfigData other)) {
         return false;
      } else {
         if (!other.canEqual(this)) {
            return false;
         }

         Object this$relic = this.getRelic();
         Object other$relic = other.getRelic();
         if (this$relic == null ? other$relic == null : this$relic.equals(other$relic)) {
            Object this$abilitiesData = this.getAbilitiesData();
            Object other$abilitiesData = other.getAbilitiesData();
            if (this$abilitiesData == null ? other$abilitiesData == null : this$abilitiesData.equals(other$abilitiesData)) {
               Object this$levelingData = this.getLevelingData();
               Object other$levelingData = other.getLevelingData();
               if (this$levelingData == null ? other$levelingData == null : this$levelingData.equals(other$levelingData)) {
                  Object this$lootData = this.getLootData();
                  Object other$lootData = other.getLootData();
                  return this$lootData == null ? other$lootData == null : this$lootData.equals(other$lootData);
               } else {
                  return false;
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof RelicConfigData;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $relic = this.getRelic();
      result = result * 59 + ($relic == null ? 43 : $relic.hashCode());
      Object $abilitiesData = this.getAbilitiesData();
      result = result * 59 + ($abilitiesData == null ? 43 : $abilitiesData.hashCode());
      Object $levelingData = this.getLevelingData();
      result = result * 59 + ($levelingData == null ? 43 : $levelingData.hashCode());
      Object $lootData = this.getLootData();
      return result * 59 + ($lootData == null ? 43 : $lootData.hashCode());
   }

   @Override
   public String toString() {
      return "RelicConfigData(relic="
         + this.getRelic()
         + ", abilitiesData="
         + this.getAbilitiesData()
         + ", levelingData="
         + this.getLevelingData()
         + ", lootData="
         + this.getLootData()
         + ")";
   }

   public RelicConfigData() {
   }
}
