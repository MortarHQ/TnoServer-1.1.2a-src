package com.almostreliable.lootjs.kube;

import com.almostreliable.lootjs.LootEvents;
import com.almostreliable.lootjs.LootJS;
import com.almostreliable.lootjs.core.LootType;
import com.almostreliable.lootjs.core.entry.ItemLootEntry;
import com.almostreliable.lootjs.core.entry.LootEntry;
import com.almostreliable.lootjs.core.entry.SingleLootEntry;
import com.almostreliable.lootjs.core.filters.IdFilter;
import com.almostreliable.lootjs.core.filters.ItemFilter;
import com.almostreliable.lootjs.core.filters.LootTableFilter;
import com.almostreliable.lootjs.kube.wrappers.BasicWrapper;
import com.almostreliable.lootjs.kube.wrappers.ItemFilterWrapper;
import com.almostreliable.lootjs.kube.wrappers.ItemPredicateWrapper;
import com.almostreliable.lootjs.kube.wrappers.LootEntryWrapper;
import com.almostreliable.lootjs.kube.wrappers.MinMaxBoundsWrapper;
import com.almostreliable.lootjs.kube.wrappers.MobEffectsPredicateWrapper;
import com.almostreliable.lootjs.kube.wrappers.NumberProviderWrapper;
import com.almostreliable.lootjs.kube.wrappers.StatePropsPredicateWrapper;
import com.almostreliable.lootjs.loot.LootCondition;
import com.almostreliable.lootjs.loot.LootFunction;
import com.almostreliable.lootjs.loot.Predicates;
import com.almostreliable.lootjs.util.BlockFilter;
import dev.latvian.mods.kubejs.block.state.BlockStatePredicate;
import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.plugin.builtin.wrapper.IngredientWrapper;
import dev.latvian.mods.kubejs.plugin.builtin.wrapper.ItemWrapper;
import dev.latvian.mods.kubejs.script.BindingRegistry;
import dev.latvian.mods.kubejs.script.ConsoleJS;
import dev.latvian.mods.kubejs.script.TypeDescriptionRegistry;
import dev.latvian.mods.kubejs.script.TypeWrapperRegistry;
import dev.latvian.mods.rhino.type.RecordTypeInfo;
import dev.latvian.mods.rhino.type.TypeInfo;
import java.util.regex.Pattern;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.DamageSourcePredicate;
import net.minecraft.advancements.critereon.DistancePredicate;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.EntityEquipmentPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.advancements.critereon.EntityTypePredicate;
import net.minecraft.advancements.critereon.FluidPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.LightPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.MobEffectsPredicate;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds.Doubles;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.advancements.critereon.PlayerPredicate.AdvancementPredicate;

public class LootJSPlugin implements KubeJSPlugin {
   public void initStartup() {
      LootJS.DEBUG_ACTION = s -> ConsoleJS.SERVER.info(s);
      LootEvents.listen(registry -> {
         LootTableEventJS event = new LootTableEventJS(registry);
         LootJSEvent.LOOT_TABLES.post(event);
      });
      LootEvents.listenModifiers(modifiers -> {
         LootModificationEventJS event = new LootModificationEventJS(modifiers);
         LootJSEvent.MODIFIERS.post(event);
      });
   }

   public void registerEvents(EventGroupRegistry registry) {
      registry.register(LootJSEvent.GROUP);
   }

   public void registerBindings(BindingRegistry bindings) {
      bindings.add("LootType", LootType.class);
      bindings.add("ItemFilter", ItemFilter.class);
      bindings.add("LootEntry", LootEntry.class);
      bindings.add("LootCondition", new LootCondition());
      bindings.add("LootFunction", new LootFunction());
      bindings.add("Range", Doubles.class);
      bindings.add("NumberProvider", NumberProviderWrapper.class);
      bindings.add("Predicates", Predicates.class);
      bindings.add("ItemPredicate", ItemPredicate.class);
      bindings.add("EntityPredicate", EntityPredicate.class);
      bindings.add("EntityEquipmentPredicate", EntityEquipmentPredicate.class);
      bindings.add("LocationPredicate", LocationPredicate.class);
      bindings.add("DistancePredicate", DistancePredicate.class);
      bindings.add("BlockPredicate", BlockPredicate.class);
      bindings.add("FluidPredicate", FluidPredicate.class);
      bindings.add("LightPredicate", LightPredicate.class);
      bindings.add("EnchantmentPredicate", EnchantmentPredicate.class);
      bindings.add("MobEffectsPredicate", MobEffectsPredicate.class);
      bindings.add("NbtPredicate", NbtPredicate.class);
   }

   public void registerTypeWrappers(TypeWrapperRegistry registry) {
      registry.register(BlockFilter.class, BasicWrapper::ofBlockFilter);
      registry.register(LootEntry.class, LootEntryWrapper::ofLootEntry);
      registry.register(ItemLootEntry.class, LootEntryWrapper::ofItemLootEntry);
      registry.register(Doubles.class, MinMaxBoundsWrapper::ofMinMaxDoubles);
      registry.register(Ints.class, MinMaxBoundsWrapper::ofMinMaxInt);
      registry.register(ItemPredicate.class, ItemPredicateWrapper::of);
      registry.register(EntityTypePredicate.class, BasicWrapper::ofEntityTypePredicate);
      registry.register(NbtPredicate.class, BasicWrapper::ofNbtPredicate);
      registry.register(StatePropertiesPredicate.class, StatePropsPredicateWrapper::of);
      registry.register(MobEffectsPredicate.class, MobEffectsPredicateWrapper::of);
      registry.register(LightPredicate.class, BasicWrapper::ofLightPredicate);
      registry.register(DamageSourcePredicate.class, BasicWrapper::ofDamageSourcePredicate);
      registry.register(AdvancementPredicate.class, BasicWrapper::ofAdvancementPredicate);
      registry.register(EntitySubPredicate.class, BasicWrapper::ofEntitySubPredicate);
      registry.register(IdFilter.class, BasicWrapper::ofIdFilter);
      registry.register(LootTableFilter.class, BasicWrapper::ofLootTableFilter);
      registry.register(ItemFilter.class, ItemFilterWrapper::ofItemFilter);
   }

   public void registerTypeDescriptions(TypeDescriptionRegistry registry) {
      registry.register(ItemFilter.class, TypeInfo.of(ItemFilter.class).or(IngredientWrapper.TYPE_INFO));
      registry.register(LootEntry.class, TypeInfo.of(LootEntry.class).or(ItemWrapper.TYPE_INFO));
      registry.register(SingleLootEntry.class, TypeInfo.of(SingleLootEntry.class).or(ItemWrapper.TYPE_INFO));
      registry.register(BlockFilter.class, TypeInfo.of(BlockFilter.class).or(TypeInfo.of(BlockStatePredicate.class)));
      registry.register(IdFilter.class, TypeInfo.of(IdFilter.class).or(TypeInfo.STRING).or(TypeInfo.of(Pattern.class)));
      registry.register(
         ItemPredicate.class, ((RecordTypeInfo)TypeInfo.of(ItemPredicate.class)).createCombinedType(new TypeInfo[]{TypeInfo.of(ItemFilter.class)})
      );
   }
}
