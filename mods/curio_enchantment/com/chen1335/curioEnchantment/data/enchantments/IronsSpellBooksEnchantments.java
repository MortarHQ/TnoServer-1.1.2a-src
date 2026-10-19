package com.chen1335.curioEnchantment.data.enchantments;

import com.chen1335.curioEnchantment.CurioEnchantment;
import com.chen1335.curioEnchantment.API.objects.CETags;
import com.chen1335.curioEnchantment.API.objects.DataComponentTypes;
import com.chen1335.curioEnchantment.API.objects.enchantmentEffects.CurioEnchantmentAttributeEffect;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.core.HolderSet.Named;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.Enchantment.Builder;

public class IronsSpellBooksEnchantments extends EnchantmentsProvider {
   public static final ResourceKey<Enchantment> QUICK_CASTING = key("quick_casting");
   public static final ResourceKey<Enchantment> DEVOTED = key("devoted");
   public static final ResourceKey<Enchantment> VARIOUS = key("various");
   public static final ResourceKey<Enchantment> KNOWLEDGE = key("knowledge");
   public static final ResourceKey<Enchantment> ANCIENT_WISDOM = key("ancient_wisdom");

   @Override
   void bootstrap(BootstrapContext<Enchantment> context) {
      Named<Item> curioEnchantable = context.lookup(Registries.ITEM).getOrThrow(CETags.ItemTags.SPELLBOOK_ENCHANTABLE);
      this.register(
         context,
         ANCIENT_WISDOM,
         Enchantment.enchantment(
            Enchantment.definition(curioEnchantable, 2, 1, Enchantment.constantCost(200), Enchantment.constantCost(200), 1, new EquipmentSlotGroup[0])
         )
      );
      this.register(
         context,
         KNOWLEDGE,
         Enchantment.enchantment(
            Enchantment.definition(curioEnchantable, 5, 1, Enchantment.constantCost(30), Enchantment.constantCost(50), 1, new EquipmentSlotGroup[0])
         )
      );
      this.register(
         context,
         VARIOUS,
         Enchantment.enchantment(
            Enchantment.definition(curioEnchantable, 6, 3, Enchantment.dynamicCost(20, 7), Enchantment.dynamicCost(20, 11), 1, new EquipmentSlotGroup[0])
         )
      );
      this.register(
         context,
         DEVOTED,
         Enchantment.enchantment(
            Enchantment.definition(curioEnchantable, 6, 3, Enchantment.dynamicCost(20, 7), Enchantment.dynamicCost(20, 11), 1, new EquipmentSlotGroup[0])
         )
      );
      this.register(
         context,
         QUICK_CASTING,
         Enchantment.enchantment(
               Enchantment.definition(curioEnchantable, 10, 5, Enchantment.dynamicCost(15, 7), Enchantment.dynamicCost(20, 11), 1, new EquipmentSlotGroup[0])
            )
            .withEffect(
               (DataComponentType)DataComponentTypes.CURIO_ATTRIBUTE.value(),
               new CurioEnchantmentAttributeEffect(AttributeRegistry.CAST_TIME_REDUCTION, LevelBasedValue.perLevel(0.04F), Operation.ADD_MULTIPLIED_BASE)
            )
      );
   }

   @Override
   protected void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Builder builder) {
      Named<Enchantment> exclusive = context.lookup(Registries.ENCHANTMENT)
         .getOrThrow(TagKey.create(Registries.ENCHANTMENT, CurioEnchantment.id(key.location().getPath() + "_exclusive")));
      builder.exclusiveWith(exclusive);
      super.register(context, key, builder);
   }

   @Override
   String getModId() {
      return "irons_spellbooks";
   }
}
