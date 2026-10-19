package net.unusual.block_factorys_bosses.item;

import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemAttributeModifiers.Builder;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;

public class DragonGuardShieldItem extends ShieldItem {
   public DragonGuardShieldItem() {
      super(new Properties().durability(750).fireResistant().attributes(createAttributes()));
   }

   public boolean isValidRepairItem(ItemStack itemstack, ItemStack repairitem) {
      return Ingredient.of(new ItemStack[]{new ItemStack(Items.NETHERITE_SCRAP), new ItemStack((ItemLike)BossesRiseItems.DRAGON_BONE.get())}).test(repairitem);
   }

   public static ItemAttributeModifiers createAttributes() {
      Builder builder = ItemAttributeModifiers.builder();
      builder.add(Attributes.MOVEMENT_SPEED, new AttributeModifier(BossesRise.prefix("effect.slow"), -0.01, Operation.ADD_VALUE), EquipmentSlotGroup.OFFHAND);
      builder.add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(BossesRise.prefix("effect.tough"), 2.0, Operation.ADD_VALUE), EquipmentSlotGroup.OFFHAND);
      builder.add(Attributes.ARMOR, new AttributeModifier(BossesRise.prefix("effect.prot"), 1.0, Operation.ADD_VALUE), EquipmentSlotGroup.OFFHAND);
      return builder.build();
   }
}
