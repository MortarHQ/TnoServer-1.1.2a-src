package it.hurts.sskirillss.relics.badges.ability;

import it.hurts.sskirillss.relics.badges.base.AbilityBadge;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import net.minecraft.world.item.ItemStack;

public class FlawlessAbilityBadge extends AbilityBadge {
   public FlawlessAbilityBadge() {
      super("flawless_ability");
   }

   @Override
   public boolean isVisible(ItemStack stack, String ability) {
      return stack.getItem() instanceof IRelicItem relic ? relic.isAbilityFlawless(stack, ability) : false;
   }
}
