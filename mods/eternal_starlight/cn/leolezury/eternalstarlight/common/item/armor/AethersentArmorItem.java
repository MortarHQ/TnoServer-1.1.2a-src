package cn.leolezury.eternalstarlight.common.item.armor;

import cn.leolezury.eternalstarlight.common.registry.ESAttributes;
import com.google.common.base.Suppliers;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemAttributeModifiers.Builder;
import net.minecraft.world.item.component.ItemAttributeModifiers.Entry;

public class AethersentArmorItem extends ArmorItem {
   private final Supplier<ItemAttributeModifiers> extraModifiers;

   public AethersentArmorItem(Holder<ArmorMaterial> holder, Type type, Properties properties) {
      super(holder, type, properties);
      this.extraModifiers = Suppliers.memoize(
         () -> {
            Builder builder = ItemAttributeModifiers.builder();
            EquipmentSlotGroup equipmentSlotGroup = EquipmentSlotGroup.bySlot(type.getSlot());
            ResourceLocation resourceLocation = ResourceLocation.withDefaultNamespace("armor." + type.getName());
            builder.add(
               ESAttributes.METEOR_COUNTERATTACK_CHANCE.asHolder(), new AttributeModifier(resourceLocation, 0.25, Operation.ADD_VALUE), equipmentSlotGroup
            );
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
}
