package artifacts.item;

import artifacts.Artifacts;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;

public abstract class ArtifactItem extends Item {
   public ArtifactItem(Properties properties) {
      super(properties.stacksTo(1).rarity(Rarity.RARE).fireResistant());
   }

   public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> tooltipList, TooltipFlag tooltipFlag) {
      if (Artifacts.CONFIG.client.showTooltips.get()) {
         List<MutableComponent> tooltip = new ArrayList<>();
         this.addTooltip(tooltip);
         tooltip.forEach(line -> tooltipList.add(line.withStyle(ChatFormatting.GRAY)));
      }
   }

   protected void addTooltip(List<MutableComponent> tooltip) {
      if (this.isCosmetic()) {
         tooltip.add(Component.translatable("%s.tooltip.cosmetic".formatted("artifacts")).withStyle(ChatFormatting.ITALIC));
      } else {
         this.addEffectsTooltip(tooltip);
      }
   }

   protected void addEffectsTooltip(List<MutableComponent> tooltip) {
      tooltip.add(Component.translatable("%s.tooltip.item.%s".formatted("artifacts", this.getTooltipItemName())));
   }

   protected MutableComponent tooltipLine(String lineId, Object... args) {
      return Component.translatable("%s.tooltip.item.%s.%s".formatted("artifacts", this.getTooltipItemName(), lineId), args);
   }

   protected String getTooltipItemName() {
      return BuiltInRegistries.ITEM.getKey(this).getPath();
   }

   public abstract boolean isCosmetic();
}
