package team.lodestar.lodestone.systems.item;

import com.google.common.collect.Multimap;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemAttributeModifiers.Builder;
import net.minecraft.world.item.component.ItemAttributeModifiers.Entry;

public abstract class LodestoneArmorItem extends ArmorItem {
   protected Multimap<Attribute, AttributeModifier> attributes;

   public LodestoneArmorItem(Holder<ArmorMaterial> pMaterial, Type pType, int durabilityFactor, Properties pProperties) {
      super(pMaterial, pType, pProperties.durability(pType.getDurability(durabilityFactor)));
   }

   public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
      ItemAttributeModifiers modifiers = super.getDefaultAttributeModifiers(stack);
      Builder builder = ItemAttributeModifiers.builder();

      for (Entry entry : modifiers.modifiers()) {
         builder.add(entry.attribute(), entry.modifier(), entry.slot());
      }

      for (Entry entry : this.createExtraAttributes()) {
         builder.add(entry.attribute(), entry.modifier(), entry.slot());
      }

      return builder.build();
   }

   public abstract List<Entry> createExtraAttributes();
}
