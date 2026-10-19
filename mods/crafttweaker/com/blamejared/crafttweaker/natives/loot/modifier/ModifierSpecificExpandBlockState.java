package com.blamejared.crafttweaker.natives.loot.modifier;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.loot.LootManager;
import com.blamejared.crafttweaker.api.loot.condition.LootConditions;
import com.blamejared.crafttweaker.api.loot.modifier.ILootModifier;
import com.blamejared.crafttweaker.natives.loot.condition.ExpandInvertedLootItemCondition;
import com.blamejared.crafttweaker.natives.loot.condition.ExpandLootItemBlockStatePropertyCondition;
import com.blamejared.crafttweaker.natives.loot.condition.ExpandMatchTool;
import com.blamejared.crafttweaker.natives.predicate.ExpandEnchantmentPredicate;
import com.blamejared.crafttweaker.natives.predicate.ExpandItemPredicate;
import com.blamejared.crafttweaker.natives.predicate.ExpandStatePropertiesPredicate;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.List;
import net.minecraft.advancements.critereon.ItemEnchantmentsPredicate;
import net.minecraft.advancements.critereon.ItemSubPredicates;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.state.BlockState;
import org.openzen.zencode.java.ZenCodeType;

@Document("vanilla/api/loot/modifier/BlockStateLootModifiers")
@ZenCodeType.Expansion("crafttweaker.api.block.BlockState")
@ZenRegister
public final class ModifierSpecificExpandBlockState {
   @ZenCodeType.Method
   public static void addBlockLootModifier(BlockState internal, String name, ILootModifier modifier) {
      ModifierSpecificExpandBlock.addLootModifier(internal.getBlock(), name, modifier);
   }

   @ZenCodeType.Method
   public static void addTargetedLootModifier(BlockState internal, String name, ILootModifier modifier) {
      Builder properties = ExpandStatePropertiesPredicate.create();
      internal.getProperties().forEach(it -> properties.hasProperty(it, internal.getValue(it).toString()));
      LootManager.INSTANCE
         .getModifierManager()
         .register(name, LootConditions.only(ExpandLootItemBlockStatePropertyCondition.create(internal.getBlock()).setProperties(properties)), modifier);
   }

   @ZenCodeType.Method
   public static void addNoSilkTouchLootModifier(BlockState internal, String name, ILootModifier modifier) {
      Builder properties = ExpandStatePropertiesPredicate.create();
      internal.getProperties().forEach(it -> properties.hasProperty(it, internal.getValue(it).toString()));
      LootManager.INSTANCE
         .getModifierManager()
         .register(
            name,
            LootConditions.allOf(
               ExpandLootItemBlockStatePropertyCondition.create(internal.getBlock()).setProperties(properties),
               ExpandInvertedLootItemCondition.create(
                  ExpandMatchTool.create(
                     ExpandItemPredicate.create()
                        .withSubPredicate(
                           ItemSubPredicates.ENCHANTMENTS,
                           ItemEnchantmentsPredicate.enchantments(
                              List.of(
                                 ExpandEnchantmentPredicate.create(
                                    Services.REGISTRY.getOrThrow(Registries.ENCHANTMENT, Enchantments.SILK_TOUCH), Ints.atLeast(1)
                                 )
                              )
                           )
                        )
                  )
               )
            ),
            modifier
         );
   }

   @ZenCodeType.Method
   public static void addToolLootModifier(BlockState internal, String name, IItemStack tool, ILootModifier modifier) {
      addToolLootModifier(internal, name, tool, false, modifier);
   }

   @ZenCodeType.Method
   public static void addToolLootModifier(BlockState internal, String name, IItemStack tool, boolean matchComponents, ILootModifier modifier) {
      Builder properties = ExpandStatePropertiesPredicate.create();
      internal.getProperties().forEach(it -> properties.hasProperty(it, internal.getValue(it).toString()));
      net.minecraft.advancements.critereon.ItemPredicate.Builder predicateBuilder = ExpandItemPredicate.create(tool);
      if (matchComponents) {
         predicateBuilder.hasComponents(DataComponentPredicate.allOf(tool.getComponents()));
      }

      LootManager.INSTANCE
         .getModifierManager()
         .register(
            name,
            LootConditions.allOf(
               ExpandLootItemBlockStatePropertyCondition.create(internal.getBlock()).setProperties(properties), ExpandMatchTool.create(predicateBuilder)
            ),
            modifier
         );
   }
}
