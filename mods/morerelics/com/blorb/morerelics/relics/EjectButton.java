package com.blorb.morerelics.relics;

import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootData;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootEntry;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.misc.LootEntries;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import top.theillusivec4.curios.api.SlotContext;

public class EjectButton extends MoreRelicBase {
   public EjectButton(Properties properties) {
   }

   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("nope")
                     .stat(
                        StatData.builder("speed")
                           .initialValue(1.5, 2.0)
                           .upgradeModifier(UpgradeOperation.ADD, 0.5)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .maxLevel(3)
                     .requiredLevel(0)
                     .build()
               )
               .build()
         )
         .leveling(LevelingData.builder().maxLevel(3).initialCost(100).step(30).build())
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.WILDCARD}).build())
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      LivingEntity livingEntity = slotContext.entity();
      if (!livingEntity.level().isClientSide) {
         if (livingEntity.getHealth() / livingEntity.getMaxHealth() <= 0.2F) {
            EntityUtils.resetAttribute(
               livingEntity, stack, Attributes.MOVEMENT_SPEED, (float)this.getStatValue(stack, "nope", "speed"), Operation.ADD_MULTIPLIED_TOTAL
            );
         } else {
            EntityUtils.removeAttribute(livingEntity, stack, Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_TOTAL);
         }
      }
   }

   public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
      LivingEntity livingEntity = slotContext.entity();
      if (stack.getItem() != newStack.getItem()) {
         EntityUtils.removeAttribute(livingEntity, stack, Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_TOTAL);
      }
   }
}
