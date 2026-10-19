package com.chen1335.curioEnchantment.data.enchantments;

import com.chen1335.curioEnchantment.API.objects.CETags;
import com.chen1335.curioEnchantment.API.objects.DataComponentTypes;
import com.chen1335.curioEnchantment.API.objects.enchantmentEffects.CurioEnchantmentAttributeEffect;
import net.minecraft.core.HolderSet.Named;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.Enchantment.Builder;

public class CEEnchantments extends EnchantmentsProvider {
   public static final ResourceKey<Enchantment> SOLID = key("solid");
   public static final ResourceKey<Enchantment> GROW = key("grow");
   public static final ResourceKey<Enchantment> LIGHT = key("light");
   public static final ResourceKey<Enchantment> QUICK_CLAW = key("quick_claw");

   @Override
   public void bootstrap(BootstrapContext<Enchantment> context) {
      Named<Item> curioEnchantable = context.lookup(Registries.ITEM).getOrThrow(CETags.ItemTags.CURIOS_ENCHANTABLE);
      Named<Item> handsEnchantable = context.lookup(Registries.ITEM).getOrThrow(CETags.ItemTags.HANDS_ENCHANTABLE);
      this.register(
         context,
         QUICK_CLAW,
         Enchantment.enchantment(
               Enchantment.definition(handsEnchantable, 10, 4, Enchantment.dynamicCost(15, 6), Enchantment.dynamicCost(20, 11), 1, new EquipmentSlotGroup[0])
            )
            .withEffect(
               (DataComponentType)DataComponentTypes.CURIO_ATTRIBUTE.value(),
               new CurioEnchantmentAttributeEffect(Attributes.MINING_EFFICIENCY, LevelBasedValue.perLevel(0.02F), Operation.ADD_MULTIPLIED_BASE)
            )
      );
      this.register(
         context,
         LIGHT,
         Enchantment.enchantment(
               Enchantment.definition(curioEnchantable, 10, 2, Enchantment.dynamicCost(12, 20), Enchantment.dynamicCost(37, 20), 1, new EquipmentSlotGroup[0])
            )
            .withEffect(
               (DataComponentType)DataComponentTypes.CURIO_ATTRIBUTE.value(),
               new CurioEnchantmentAttributeEffect(Attributes.MOVEMENT_SPEED, LevelBasedValue.perLevel(0.05F), Operation.ADD_MULTIPLIED_BASE)
            )
      );
      this.register(
         context,
         SOLID,
         Enchantment.enchantment(
               Enchantment.definition(curioEnchantable, 10, 4, Enchantment.dynamicCost(1, 11), Enchantment.dynamicCost(12, 11), 1, new EquipmentSlotGroup[0])
            )
            .withEffect(
               (DataComponentType)DataComponentTypes.CURIO_ATTRIBUTE.value(),
               new CurioEnchantmentAttributeEffect(Attributes.ARMOR, LevelBasedValue.perLevel(0.25F), Operation.ADD_VALUE)
            )
      );
      this.register(
         context,
         GROW,
         Enchantment.enchantment(
               Enchantment.definition(curioEnchantable, 6, 5, Enchantment.dynamicCost(1, 11), Enchantment.dynamicCost(12, 11), 1, new EquipmentSlotGroup[0])
            )
            .withEffect(
               (DataComponentType)DataComponentTypes.CURIO_ATTRIBUTE.value(),
               new CurioEnchantmentAttributeEffect(Attributes.MAX_HEALTH, LevelBasedValue.perLevel(0.2F), Operation.ADD_VALUE)
            )
      );
   }

   @Override
   protected void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Builder builder) {
      Named<Enchantment> exclusive = context.lookup(Registries.ENCHANTMENT)
         .getOrThrow(TagKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath("curio_enchantment", key.location().getPath() + "_exclusive")));
      builder.exclusiveWith(exclusive);
      super.register(context, key, builder);
   }

   @Override
   String getModId() {
      return "curio_enchantment";
   }
}
