package com.b1n_ry.yigd.compat.misc_compat_mods;

import com.b1n_ry.yigd.components.InventoryComponent;
import com.b1n_ry.yigd.data.DeathContext;
import com.b1n_ry.yigd.events.YigdEvents;
import com.b1n_ry.yigd.util.DropRule;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.NeoForge;

public class TwilightCompat {
   public static void init() {
      NeoForge.EVENT_BUS.addListener(TwilightCompat::adjustDropRules);
   }

   public static void adjustDropRules(YigdEvents.AdjustDropRuleEvent event) {
      DeathContext context = event.getDeathContext();
      InventoryComponent inventoryComponent = event.getInventoryComponent();
      int selectedSlot = context.player().getInventory().selected;
      Item keepingCharm3 = (Item)BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("twilightforest", "charm_of_keeping_3"));
      Item keepingCharm2 = (Item)BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("twilightforest", "charm_of_keeping_2"));
      Item keepingCharm1 = (Item)BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("twilightforest", "charm_of_keeping_1"));
      boolean tier3 = inventoryComponent.containsAny(stack -> stack.is(keepingCharm3), mod -> true, slot -> true);
      boolean tier2 = tier3 || inventoryComponent.containsAny(stack -> stack.is(keepingCharm2), mod -> true, slot -> true);
      boolean tier1 = tier2 || inventoryComponent.containsAny(stack -> stack.is(keepingCharm1), mod -> true, slot -> true);
      Item firstMatch = tier3 ? keepingCharm3 : (tier2 ? keepingCharm2 : (tier1 ? keepingCharm1 : null));
      if (firstMatch != null) {
         int hotbarSize = 9;
         int afterOffhandIndex = inventoryComponent.mainSize + inventoryComponent.armorSize + inventoryComponent.offHandSize;
         inventoryComponent.handleGraveItems(mod -> true, (stack, slot, graveItem) -> {
            if ((slot < inventoryComponent.mainSize || slot >= afterOffhandIndex) && slot != selectedSlot && slot >= 0) {
               if (tier2 && slot < 9) {
                  graveItem.dropRule = DropRule.KEEP;
               } else if (tier3) {
                  graveItem.dropRule = DropRule.KEEP;
               }
            } else {
               graveItem.dropRule = DropRule.KEEP;
            }

            if (stack.is(firstMatch)) {
               graveItem.dropRule = DropRule.DESTROY;
            }
         });
      }
   }
}
