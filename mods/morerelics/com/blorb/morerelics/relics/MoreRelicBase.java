package com.blorb.morerelics.relics;

import com.blorb.morerelics.MoreRelicsUtil;
import it.hurts.sskirillss.relics.items.relics.base.RelicItem;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import top.theillusivec4.curios.api.SlotContext;

public abstract class MoreRelicBase extends RelicItem {
   public MoreRelicBase(Properties properties) {
      super(properties);
   }

   public MoreRelicBase() {
      super(new Properties().rarity(Rarity.RARE).stacksTo(1));
   }

   public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
      entity.setGlowingTag(true);
      return super.onEntityItemUpdate(stack, entity);
   }

   public String getConfigRoute() {
      return "morerelics";
   }

   public boolean canEquipMultipleCopies() {
      return true;
   }

   public List<Component> getSlotsTooltip(List<Component> tooltips, TooltipContext context, ItemStack stack) {
      if (!this.canEquipMultipleCopies()) {
         List<Component> newList = new ArrayList<>(tooltips.stream().toList());
         newList.add(Component.translatable("tooltip.morerelics.only_one_disclaimer").withColor(Color.ORANGE.getRGB()));
         return newList;
      } else {
         return super.getSlotsTooltip(tooltips, context, stack);
      }
   }

   public boolean canEquip(SlotContext slotContext, ItemStack stack) {
      if (this.canEquipMultipleCopies()) {
         return super.canEquip(slotContext, stack);
      }

      boolean alreadyEquipped = MoreRelicsUtil.actuallyGetEquippedRelicsWithSlots(slotContext.entity())
         .stream()
         .anyMatch(
            entry -> entry.stack().getItem().getClass() == this.getClass()
               && (slotContext.index() != entry.slotIndex() || !Objects.equals(slotContext.identifier(), entry.slotId()))
         );
      return alreadyEquipped && super.canEquip(slotContext, stack) ? false : super.canEquip(slotContext, stack);
   }
}
