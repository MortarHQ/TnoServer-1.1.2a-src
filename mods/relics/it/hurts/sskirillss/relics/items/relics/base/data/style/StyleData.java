package it.hurts.sskirillss.relics.items.relics.base.data.style;

import java.util.function.BiFunction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class StyleData {
   private BiFunction<Player, ItemStack, TooltipData> tooltip;
   private BiFunction<Player, ItemStack, BeamsData> beams;

   StyleData(BiFunction<Player, ItemStack, TooltipData> tooltip, BiFunction<Player, ItemStack, BeamsData> beams) {
      this.tooltip = tooltip;
      this.beams = beams;
   }

   public static StyleData.StyleDataBuilder builder() {
      return new StyleData.StyleDataBuilder();
   }

   public BiFunction<Player, ItemStack, TooltipData> getTooltip() {
      return this.tooltip;
   }

   public BiFunction<Player, ItemStack, BeamsData> getBeams() {
      return this.beams;
   }

   public void setTooltip(BiFunction<Player, ItemStack, TooltipData> tooltip) {
      this.tooltip = tooltip;
   }

   public void setBeams(BiFunction<Player, ItemStack, BeamsData> beams) {
      this.beams = beams;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof StyleData other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$tooltip = this.getTooltip();
         Object other$tooltip = other.getTooltip();
         if (this$tooltip == null ? other$tooltip == null : this$tooltip.equals(other$tooltip)) {
            Object this$beams = this.getBeams();
            Object other$beams = other.getBeams();
            return this$beams == null ? other$beams == null : this$beams.equals(other$beams);
         } else {
            return false;
         }
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof StyleData;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $tooltip = this.getTooltip();
      result = result * 59 + ($tooltip == null ? 43 : $tooltip.hashCode());
      Object $beams = this.getBeams();
      return result * 59 + ($beams == null ? 43 : $beams.hashCode());
   }

   @Override
   public String toString() {
      return "StyleData(tooltip=" + this.getTooltip() + ", beams=" + this.getBeams() + ")";
   }

   public static class StyleDataBuilder {
      private BiFunction<Player, ItemStack, TooltipData> tooltip = (player, stack) -> TooltipData.builder().build();
      private BiFunction<Player, ItemStack, BeamsData> beams = (player, stack) -> BeamsData.builder().build();

      public StyleData.StyleDataBuilder tooltip(TooltipData tooltip) {
         return this.tooltip((player, stack) -> tooltip);
      }

      public StyleData.StyleDataBuilder tooltip(BiFunction<Player, ItemStack, TooltipData> tooltip) {
         this.tooltip = tooltip;
         return this;
      }

      public StyleData.StyleDataBuilder beams(BeamsData beams) {
         return this.beams((player, stack) -> beams);
      }

      public StyleData.StyleDataBuilder beams(BiFunction<Player, ItemStack, BeamsData> beams) {
         this.beams = beams;
         return this;
      }

      StyleDataBuilder() {
      }

      public StyleData build() {
         return new StyleData(this.tooltip, this.beams);
      }

      @Override
      public String toString() {
         return "StyleData.StyleDataBuilder(tooltip=" + this.tooltip + ", beams=" + this.beams + ")";
      }
   }
}
