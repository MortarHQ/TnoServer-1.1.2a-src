package twilightforest.item;

import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import twilightforest.TwilightForestMod;
import twilightforest.init.TFItems;

public class GiantSwordItem extends SwordItem {
   public GiantSwordItem(Tier material, Properties properties) {
      super(material, properties);
   }

   public static ItemAttributeModifiers createGiantAttributes(Tier tier, int damage, float speed) {
      return SwordItem.createAttributes(tier, damage, speed)
         .withModifierAdded(
            Attributes.BLOCK_INTERACTION_RANGE,
            new AttributeModifier(TwilightForestMod.prefix("reach_modifier"), 2.5, Operation.ADD_VALUE),
            EquipmentSlotGroup.HAND
         )
         .withModifierAdded(
            Attributes.ENTITY_INTERACTION_RANGE,
            new AttributeModifier(TwilightForestMod.prefix("range_modifier"), 2.5, Operation.ADD_VALUE),
            EquipmentSlotGroup.HAND
         );
   }

   public boolean isValidRepairItem(ItemStack stack, ItemStack material) {
      return material.is((Item)TFItems.IRONWOOD_INGOT.get()) || super.isValidRepairItem(stack, material);
   }
}
