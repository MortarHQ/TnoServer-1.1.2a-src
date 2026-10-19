package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.items.CuriosItem.AttributeContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemAttributeModifiers.Builder;

public class Cataclysm_Weapon extends Item {
   public Cataclysm_Weapon(Properties group) {
      super(group);
   }

   public static ItemAttributeModifiers createAttributes(int attackDamage, float attackSpeed) {
      return createAttributes(attackDamage, attackSpeed);
   }

   public static ItemAttributeModifiers createAttributes(float p_331976_, float p_332104_, AttributeContainer... attributes) {
      Builder builder = ItemAttributeModifiers.builder();
      builder.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, p_331976_, Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
      builder.add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, p_332104_, Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);

      for (AttributeContainer holder : attributes) {
         AttributeModifier modifier = holder.createModifier(EquipmentSlot.MAINHAND.getName());
         if (modifier.amount() != 0.0) {
            builder.add(holder.attribute(), modifier, EquipmentSlotGroup.MAINHAND);
         }
      }

      return builder.build();
   }
}
