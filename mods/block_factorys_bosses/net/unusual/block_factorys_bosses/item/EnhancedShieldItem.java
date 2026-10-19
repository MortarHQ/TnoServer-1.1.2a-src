package net.unusual.block_factorys_bosses.item;

import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemAttributeModifiers.Builder;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.init.BossesRiseAttributes;

public class EnhancedShieldItem extends ShieldItem {
   public EnhancedShieldItem() {
      super(new Properties().durability(450));
   }

   public boolean isValidRepairItem(ItemStack itemstack, ItemStack repairitem) {
      return false;
   }

   public static ItemAttributeModifiers createAttributes() {
      Builder builder = ItemAttributeModifiers.builder();
      builder.add(
         BossesRiseAttributes.ROLL_COUNT.getDelegate(),
         new AttributeModifier(BossesRise.prefix("effect.roll.offhand"), 1.0, Operation.ADD_VALUE),
         EquipmentSlotGroup.OFFHAND
      );
      return builder.build();
   }
}
