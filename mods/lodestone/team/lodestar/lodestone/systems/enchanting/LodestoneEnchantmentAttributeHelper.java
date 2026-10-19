package team.lodestar.lodestone.systems.enchanting;

import com.google.common.collect.Multimap;
import java.util.Map.Entry;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemAttributeModifiers.Builder;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.neoforged.neoforge.common.util.AttributeUtil;
import org.apache.commons.lang3.mutable.MutableFloat;
import team.lodestar.lodestone.registry.common.LodestoneEnchantmentComponents;

public class LodestoneEnchantmentAttributeHelper {
   public static float getAttributeValue(ItemStack stack, @Nullable Holder<Enchantment> filter, Holder<Attribute> attribute, float baseValue) {
      MutableFloat mutable = new MutableFloat(baseValue);
      LodestoneEnchantmentDataHelper.runIterationOnItem(
         stack, filter, (enchantment, level) -> ((Enchantment)enchantment.value()).getEffects(EnchantmentEffectComponents.ATTRIBUTES).forEach(effect -> {
            if (effect.attribute().equals(attribute)) {
               AttributeModifier modifier = effect.getModifier(level, EquipmentSlotGroup.ANY);
               mutable.setValue(compute(modifier, mutable.getValue().floatValue()));
            }
         })
      );
      LodestoneEnchantmentDataHelper.runIterationOnItem(
         stack,
         filter,
         (enchantment, level) -> ((Enchantment)enchantment.value()).getEffects(LodestoneEnchantmentComponents.SLOT_BOUND_ATTRIBUTES.get()).forEach(effect -> {
            if (effect.attribute().equals(attribute)) {
               AttributeModifier modifier = effect.getModifier(level, EquipmentSlotGroup.ANY);
               mutable.setValue(compute(modifier, mutable.getValue().floatValue()));
            }
         })
      );
      return mutable.getValue();
   }

   public static float getBaseValue(ItemAttributeModifiers modifiers, float baseValue, Holder<Attribute> attribute) {
      return compute(modifiers, e -> e.attribute().equals(attribute) && isBaseAttribute(e), baseValue);
   }

   public static boolean isBaseAttribute(net.minecraft.world.item.component.ItemAttributeModifiers.Entry entry) {
      return !entry.modifier().id().getPath().contains("enchantment");
   }

   public static ItemAttributeModifiers asAttributeModifiers(Multimap<Holder<Attribute>, AttributeModifier> map) {
      Builder builder = ItemAttributeModifiers.builder();

      for (Entry<Holder<Attribute>, AttributeModifier> entry : map.entries()) {
         builder.add(entry.getKey(), entry.getValue(), EquipmentSlotGroup.ANY);
      }

      return builder.build();
   }

   public static float compute(
      ItemAttributeModifiers modifiers, Predicate<net.minecraft.world.item.component.ItemAttributeModifiers.Entry> filter, double baseValue
   ) {
      double value = baseValue;
      Multimap<Holder<Attribute>, AttributeModifier> matching = findMatching(modifiers, filter);

      for (Entry<Holder<Attribute>, AttributeModifier> entry : matching.entries()) {
         AttributeModifier modifier = entry.getValue();
         value = compute(modifier, value);
      }

      return (float)value;
   }

   public static Multimap<Holder<Attribute>, AttributeModifier> findMatching(
      ItemAttributeModifiers modifiers, Predicate<net.minecraft.world.item.component.ItemAttributeModifiers.Entry> filter
   ) {
      Multimap<Holder<Attribute>, AttributeModifier> map = AttributeUtil.sortedMap();

      for (net.minecraft.world.item.component.ItemAttributeModifiers.Entry entry : modifiers.modifiers()) {
         if (filter.test(entry)) {
            map.put(entry.attribute(), entry.modifier());
         }
      }

      return map;
   }

   public static float compute(AttributeModifier modifier, double baseValue) {
      double value = baseValue;
      double d1 = modifier.amount();

      value += switch (modifier.operation()) {
         case ADD_VALUE -> d1;
         case ADD_MULTIPLIED_BASE -> d1 * baseValue;
         case ADD_MULTIPLIED_TOTAL -> d1 * value;
         default -> throw new MatchException(null, null);
      };
      return (float)value;
   }
}
