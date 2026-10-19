package it.hurts.octostudios.reliquified_twilight_forest.item;

import it.hurts.octostudios.reliquified_twilight_forest.gui.tooltip.BundleLikeTooltip;
import it.hurts.octostudios.reliquified_twilight_forest.init.DataComponentRegistry;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import org.jetbrains.annotations.NotNull;

public abstract class BundleLikeItem extends Item implements BundleLike {
   public BundleLikeItem() {
      super(new Properties().component(DataComponentRegistry.BUNDLE_LIKE_CONTENTS, List.of()).rarity(Rarity.RARE).stacksTo(1));
   }

   @NotNull
   public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
      return !stack.has(DataComponents.HIDE_TOOLTIP) && !stack.has(DataComponents.HIDE_ADDITIONAL_TOOLTIP)
         ? Optional.ofNullable((List)stack.get(DataComponentRegistry.BUNDLE_LIKE_CONTENTS))
            .map(list -> new BundleLikeTooltip((List<ItemStack>)list, this.getMaxSlots(stack)))
         : Optional.empty();
   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return slotChanged;
   }
}
