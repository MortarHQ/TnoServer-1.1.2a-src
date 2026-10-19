package it.hurts.sskirillss.relics.items.relics.base.data;

import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.StyleData;

public class RelicData {
   private AbilitiesData abilities;
   private LevelingData leveling;
   private StyleData style;
   private LootData loot;

   private static AbilitiesData $default$abilities() {
      return AbilitiesData.builder().build();
   }

   private static LevelingData $default$leveling() {
      return LevelingData.builder().build();
   }

   private static StyleData $default$style() {
      return StyleData.builder().build();
   }

   private static LootData $default$loot() {
      return LootData.builder().build();
   }

   RelicData(AbilitiesData abilities, LevelingData leveling, StyleData style, LootData loot) {
      this.abilities = abilities;
      this.leveling = leveling;
      this.style = style;
      this.loot = loot;
   }

   public static RelicData.RelicDataBuilder builder() {
      return new RelicData.RelicDataBuilder();
   }

   public AbilitiesData getAbilities() {
      return this.abilities;
   }

   public LevelingData getLeveling() {
      return this.leveling;
   }

   public StyleData getStyle() {
      return this.style;
   }

   public LootData getLoot() {
      return this.loot;
   }

   public void setAbilities(AbilitiesData abilities) {
      this.abilities = abilities;
   }

   public void setLeveling(LevelingData leveling) {
      this.leveling = leveling;
   }

   public void setStyle(StyleData style) {
      this.style = style;
   }

   public void setLoot(LootData loot) {
      this.loot = loot;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof RelicData other)) {
         return false;
      } else {
         if (!other.canEqual(this)) {
            return false;
         }

         Object this$abilities = this.getAbilities();
         Object other$abilities = other.getAbilities();
         if (this$abilities == null ? other$abilities == null : this$abilities.equals(other$abilities)) {
            Object this$leveling = this.getLeveling();
            Object other$leveling = other.getLeveling();
            if (this$leveling == null ? other$leveling == null : this$leveling.equals(other$leveling)) {
               Object this$style = this.getStyle();
               Object other$style = other.getStyle();
               if (this$style == null ? other$style == null : this$style.equals(other$style)) {
                  Object this$loot = this.getLoot();
                  Object other$loot = other.getLoot();
                  return this$loot == null ? other$loot == null : this$loot.equals(other$loot);
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
      return other instanceof RelicData;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $abilities = this.getAbilities();
      result = result * 59 + ($abilities == null ? 43 : $abilities.hashCode());
      Object $leveling = this.getLeveling();
      result = result * 59 + ($leveling == null ? 43 : $leveling.hashCode());
      Object $style = this.getStyle();
      result = result * 59 + ($style == null ? 43 : $style.hashCode());
      Object $loot = this.getLoot();
      return result * 59 + ($loot == null ? 43 : $loot.hashCode());
   }

   @Override
   public String toString() {
      return "RelicData(abilities="
         + this.getAbilities()
         + ", leveling="
         + this.getLeveling()
         + ", style="
         + this.getStyle()
         + ", loot="
         + this.getLoot()
         + ")";
   }

   public static class RelicDataBuilder {
      private boolean abilities$set;
      private AbilitiesData abilities$value;
      private boolean leveling$set;
      private LevelingData leveling$value;
      private boolean style$set;
      private StyleData style$value;
      private boolean loot$set;
      private LootData loot$value;

      RelicDataBuilder() {
      }

      public RelicData.RelicDataBuilder abilities(AbilitiesData abilities) {
         this.abilities$value = abilities;
         this.abilities$set = true;
         return this;
      }

      public RelicData.RelicDataBuilder leveling(LevelingData leveling) {
         this.leveling$value = leveling;
         this.leveling$set = true;
         return this;
      }

      public RelicData.RelicDataBuilder style(StyleData style) {
         this.style$value = style;
         this.style$set = true;
         return this;
      }

      public RelicData.RelicDataBuilder loot(LootData loot) {
         this.loot$value = loot;
         this.loot$set = true;
         return this;
      }

      public RelicData build() {
         AbilitiesData abilities$value = this.abilities$value;
         if (!this.abilities$set) {
            abilities$value = RelicData.$default$abilities();
         }

         LevelingData leveling$value = this.leveling$value;
         if (!this.leveling$set) {
            leveling$value = RelicData.$default$leveling();
         }

         StyleData style$value = this.style$value;
         if (!this.style$set) {
            style$value = RelicData.$default$style();
         }

         LootData loot$value = this.loot$value;
         if (!this.loot$set) {
            loot$value = RelicData.$default$loot();
         }

         return new RelicData(abilities$value, leveling$value, style$value, loot$value);
      }

      @Override
      public String toString() {
         return "RelicData.RelicDataBuilder(abilities$value="
            + this.abilities$value
            + ", leveling$value="
            + this.leveling$value
            + ", style$value="
            + this.style$value
            + ", loot$value="
            + this.loot$value
            + ")";
      }
   }
}
