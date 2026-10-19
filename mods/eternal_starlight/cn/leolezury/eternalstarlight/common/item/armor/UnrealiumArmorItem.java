package cn.leolezury.eternalstarlight.common.item.armor;

import cn.leolezury.eternalstarlight.common.registry.ESAttributes;
import com.google.common.base.Suppliers;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemAttributeModifiers.Builder;
import net.minecraft.world.item.component.ItemAttributeModifiers.Entry;

public class UnrealiumArmorItem extends ArmorItem {
   private final Supplier<ItemAttributeModifiers> extraModifiers;

   public UnrealiumArmorItem(Holder<ArmorMaterial> materialHolder, Type type, Properties properties) {
      super(materialHolder, type, properties);
      this.extraModifiers = Suppliers.memoize(
         () -> {
            Builder builder = ItemAttributeModifiers.builder();
            EquipmentSlotGroup equipmentSlotGroup = EquipmentSlotGroup.bySlot(type.getSlot());
            ResourceLocation resourceLocation = ResourceLocation.withDefaultNamespace("armor." + type.getName());
            builder.add(Attributes.MOVEMENT_SPEED, new AttributeModifier(resourceLocation, 0.05, Operation.ADD_VALUE), equipmentSlotGroup);
            builder.add(Attributes.MOVEMENT_SPEED, new AttributeModifier(resourceLocation, 0.1, Operation.ADD_MULTIPLIED_TOTAL), equipmentSlotGroup);
            builder.add(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(resourceLocation, -0.05, Operation.ADD_MULTIPLIED_TOTAL), equipmentSlotGroup);
            builder.add(
               ESAttributes.ENEMY_FOLLOW_RANGE_MULTIPLIER.asHolder(), new AttributeModifier(resourceLocation, -0.15, Operation.ADD_VALUE), equipmentSlotGroup
            );
            if (type == Type.HELMET) {
               builder.add(ESAttributes.FOG_VISION.asHolder(), new AttributeModifier(resourceLocation, 50.0, Operation.ADD_VALUE), equipmentSlotGroup);
            }

            if (type == Type.CHESTPLATE) {
               builder.add(Attributes.EXPLOSION_KNOCKBACK_RESISTANCE, new AttributeModifier(resourceLocation, 1.0, Operation.ADD_VALUE), equipmentSlotGroup);
            }

            if (type == Type.LEGGINGS) {
               builder.add(Attributes.GRAVITY, new AttributeModifier(resourceLocation, -0.02, Operation.ADD_VALUE), equipmentSlotGroup);
            }

            if (type == Type.BOOTS) {
               builder.add(Attributes.MOVEMENT_EFFICIENCY, new AttributeModifier(resourceLocation, 1.0, Operation.ADD_VALUE), equipmentSlotGroup);
               builder.add(Attributes.STEP_HEIGHT, new AttributeModifier(resourceLocation, 0.5, Operation.ADD_VALUE), equipmentSlotGroup);
            }

            return builder.build();
         }
      );
   }

   public ItemAttributeModifiers getDefaultAttributeModifiers() {
      ItemAttributeModifiers modifiers = super.getDefaultAttributeModifiers();
      Builder builder = ItemAttributeModifiers.builder();

      for (Entry entry : modifiers.modifiers()) {
         builder.add(entry.attribute(), entry.modifier(), entry.slot());
      }

      for (Entry entry : this.extraModifiers.get().modifiers()) {
         builder.add(entry.attribute(), entry.modifier(), entry.slot());
      }

      return builder.build();
   }

   public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
      if (this.getType() == Type.HELMET) {
         list.add(CommonComponents.EMPTY);
         list.add(Component.translatable("item.modifiers.armor").withStyle(ChatFormatting.GRAY));
         list.add(Component.literal(" ").append(Component.translatable("tooltip.eternal_starlight.unrealium_helmet")).withStyle(ChatFormatting.DARK_PURPLE));
         list.add(
            Component.literal(" ").append(Component.translatable("tooltip.eternal_starlight.unrealium_helmet.mute")).withStyle(ChatFormatting.DARK_PURPLE)
         );
      } else if (this.getType() == Type.CHESTPLATE) {
         list.add(CommonComponents.EMPTY);
         list.add(Component.translatable("item.modifiers.armor").withStyle(ChatFormatting.GRAY));
         list.add(Component.literal(" ").append(Component.translatable("tooltip.eternal_starlight.unrealium_chestplate")).withStyle(ChatFormatting.DARK_PURPLE));
         list.add(
            Component.literal(" ").append(Component.translatable("tooltip.eternal_starlight.unrealium_chestplate.mute")).withStyle(ChatFormatting.DARK_PURPLE)
         );
      } else if (this.getType() == Type.LEGGINGS) {
         list.add(CommonComponents.EMPTY);
         list.add(Component.translatable("item.modifiers.armor").withStyle(ChatFormatting.GRAY));
         list.add(
            Component.literal(" ").append(Component.translatable("tooltip.eternal_starlight.unrealium_leggings.mute")).withStyle(ChatFormatting.DARK_PURPLE)
         );
      } else if (this.getType() == Type.BOOTS) {
         list.add(CommonComponents.EMPTY);
         list.add(Component.translatable("item.modifiers.armor").withStyle(ChatFormatting.GRAY));
         list.add(Component.literal(" ").append(Component.translatable("tooltip.eternal_starlight.unrealium_boots.mute")).withStyle(ChatFormatting.DARK_PURPLE));
      }

      super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
   }
}
