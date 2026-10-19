package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.items.CuriosItem.AttributeContainer;
import com.google.common.base.Suppliers;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemAttributeModifiers.Builder;

public class Cataclysm_Armor extends ArmorItem {
   private final Supplier<ItemAttributeModifiers> catdefaultModifiers;

   public Cataclysm_Armor(Holder<ArmorMaterial> pMaterial, Type pType, Properties pProperties, AttributeContainer... attributes) {
      super(pMaterial, pType, pProperties);
      this.catdefaultModifiers = Suppliers.memoize(
         () -> {
            int i = ((ArmorMaterial)this.material.value()).getDefense(this.type);
            float f = ((ArmorMaterial)this.material.value()).toughness();
            Builder itemattributemodifiers$builder = ItemAttributeModifiers.builder();
            EquipmentSlotGroup equipmentslotgroup = EquipmentSlotGroup.bySlot(this.type.getSlot());
            ResourceLocation resourcelocation = ResourceLocation.withDefaultNamespace("armor." + this.type.getName());
            itemattributemodifiers$builder.add(Attributes.ARMOR, new AttributeModifier(resourcelocation, i, Operation.ADD_VALUE), equipmentslotgroup);
            itemattributemodifiers$builder.add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(resourcelocation, f, Operation.ADD_VALUE), equipmentslotgroup);
            float f1 = ((ArmorMaterial)this.material.value()).knockbackResistance();
            if (f1 > 0.0F) {
               itemattributemodifiers$builder.add(
                  Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(resourcelocation, f1, Operation.ADD_VALUE), equipmentslotgroup
               );
            }

            for (AttributeContainer holder : attributes) {
               itemattributemodifiers$builder.add(holder.attribute(), holder.createModifier(pType.getSlot().getName()), equipmentslotgroup);
            }

            return itemattributemodifiers$builder.build();
         }
      );
   }

   public ItemAttributeModifiers getDefaultAttributeModifiers() {
      return this.catdefaultModifiers.get();
   }

   public static ItemAttributeModifiers createArmorAttributes(
      Holder<ArmorMaterial> material, float configDefenseMultiplier, float configToughness, float configKnockback, Type type, AttributeContainer... attributes
   ) {
      Builder builder = ItemAttributeModifiers.builder();
      EquipmentSlotGroup slotGroup = EquipmentSlotGroup.bySlot(type.getSlot());
      ResourceLocation resLoc = ResourceLocation.withDefaultNamespace("armor." + type.getName());
      int finalDefense = Math.round(configDefenseMultiplier * ((ArmorMaterial)material.value()).getDefense(type));
      if (finalDefense > 0) {
         builder.add(Attributes.ARMOR, new AttributeModifier(resLoc, finalDefense, Operation.ADD_VALUE), slotGroup);
      }

      if (configToughness > 0.0F) {
         builder.add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(resLoc, configToughness, Operation.ADD_VALUE), slotGroup);
      }

      if (configKnockback > 0.0F) {
         builder.add(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(resLoc, configKnockback, Operation.ADD_VALUE), slotGroup);
      }

      for (AttributeContainer holder : attributes) {
         AttributeModifier modifier = holder.createModifier(type.getSlot().getName());
         if (Math.abs(modifier.amount()) > 1.0E-5) {
            builder.add(holder.attribute(), modifier, slotGroup);
         }
      }

      return builder.build();
   }

   public static ItemAttributeModifiers createAttributes(Holder<ArmorMaterial> material, Type type, AttributeContainer... attributes) {
      Builder builder = ItemAttributeModifiers.builder();
      EquipmentSlotGroup slotGroup = EquipmentSlotGroup.bySlot(type.getSlot());
      ResourceLocation id = ResourceLocation.fromNamespaceAndPath("cataclysm", "armor." + type.getName());
      int defense = ((ArmorMaterial)material.value()).getDefense(type);
      if (defense > 0) {
         builder.add(Attributes.ARMOR, new AttributeModifier(id, defense, Operation.ADD_VALUE), slotGroup);
      }

      float toughness = ((ArmorMaterial)material.value()).toughness();
      if (toughness > 0.0F) {
         builder.add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(id, toughness, Operation.ADD_VALUE), slotGroup);
      }

      float knockback = ((ArmorMaterial)material.value()).knockbackResistance();
      if (knockback > 0.0F) {
         builder.add(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(id, knockback, Operation.ADD_VALUE), slotGroup);
      }

      for (AttributeContainer holder : attributes) {
         AttributeModifier modifier = holder.createModifier(type.getSlot().getName());
         if (Math.abs(modifier.amount()) > 1.0E-5) {
            builder.add(holder.attribute(), modifier, slotGroup);
         }
      }

      return builder.build();
   }
}
