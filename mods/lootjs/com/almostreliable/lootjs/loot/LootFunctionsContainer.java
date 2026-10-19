package com.almostreliable.lootjs.loot;

import com.almostreliable.lootjs.LootJS;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.LimitCount;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetCustomDataFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemDamageFunction;
import net.minecraft.world.level.storage.loot.functions.SetLoreFunction;
import net.minecraft.world.level.storage.loot.functions.SetPotionFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.functions.ToggleTooltips;
import net.minecraft.world.level.storage.loot.functions.ListOperation.ReplaceAll;
import net.minecraft.world.level.storage.loot.functions.SetEnchantmentsFunction.Builder;
import net.minecraft.world.level.storage.loot.functions.ToggleTooltips.ComponentToggle;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public interface LootFunctionsContainer<F> {
   default F enchantRandomly() {
      return this.addFunction(EnchantRandomlyFunction.randomEnchantment().build());
   }

   default F enchantRandomly(HolderSet<Enchantment> enchantments) {
      net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction.Builder enchantRandomlyFunctionBuilder = EnchantRandomlyFunction.randomEnchantment();
      enchantRandomlyFunctionBuilder.withOneOf(enchantments);
      return this.addFunction(enchantRandomlyFunctionBuilder.build());
   }

   default F enchantWithLevels(NumberProvider numberProvider) {
      net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction.Builder builder = EnchantWithLevelsFunction.enchantWithLevels(
         LootJS.lookup(), numberProvider
      );
      return this.addFunction(builder.build());
   }

   default F enchant(boolean add, Consumer<Builder> action) {
      Builder builder = new Builder(add);
      action.accept(builder);
      return this.addFunction(builder.build());
   }

   default F enchant(Consumer<Builder> action) {
      return this.enchant(false, action);
   }

   default F applyEnchantmentBonus(Holder<Enchantment> enchantment, NumberProvider count) {
      return this.addFunction(new net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction.Builder(enchantment, count).build());
   }

   default F applyEnchantmentBonus(NumberProvider count) {
      return this.addFunction(EnchantedCountIncreaseFunction.lootingMultiplier(LootJS.lookup(), count).build());
   }

   default F applyBinomialDistributionBonus(Holder<Enchantment> enchantment, float probability, int extra) {
      net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction.Builder<? extends net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction.Builder<?>> applyBonusBuilder = ApplyBonusCount.addBonusBinomialDistributionCount(
         enchantment, probability, extra
      );
      return this.addFunction(applyBonusBuilder.build());
   }

   default F applyOreBonus(Holder<Enchantment> enchantment) {
      net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction.Builder<? extends net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction.Builder<?>> applyBonusBuilder = ApplyBonusCount.addOreBonusCount(
         enchantment
      );
      return this.addFunction(applyBonusBuilder.build());
   }

   default F applyBonus(Holder<Enchantment> enchantment, int multiplier) {
      net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction.Builder<? extends net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction.Builder<?>> applyBonusBuilder = ApplyBonusCount.addUniformBonusCount(
         enchantment, multiplier
      );
      return this.addFunction(applyBonusBuilder.build());
   }

   default F simulateExplosionDecay() {
      return this.addFunction(ApplyExplosionDecay.explosionDecay().build());
   }

   default F smelt() {
      return this.addFunction(SmeltItemFunction.smelted().build());
   }

   default F damage(NumberProvider numberProvider) {
      return this.addFunction(SetItemDamageFunction.setDamage(numberProvider).build());
   }

   default F addPotion(Potion potion) {
      return this.addFunction(SetPotionFunction.setPotion(BuiltInRegistries.POTION.wrapAsHolder(potion)).build());
   }

   default F addAttributes(Consumer<AddAttributesFunction.Builder> action) {
      AddAttributesFunction.Builder builder = new AddAttributesFunction.Builder();
      action.accept(builder);
      return this.addFunction(builder.build());
   }

   default F limitCount(@Nullable NumberProvider numberProviderMin, @Nullable NumberProvider numberProviderMax) {
      IntRange intRange = new IntRange(numberProviderMin, numberProviderMax);
      return this.addFunction(LimitCount.limitCount(intRange).build());
   }

   default F setCount(NumberProvider numberProvider) {
      return this.addFunction(SetItemCountFunction.setCount(numberProvider).build());
   }

   default F addLore(Component... components) {
      net.minecraft.world.level.storage.loot.functions.SetLoreFunction.Builder builder = SetLoreFunction.setLore();

      for (Component c : components) {
         builder.addLine(c);
      }

      return this.addFunction(builder.build());
   }

   default F replaceLore(Component... components) {
      net.minecraft.world.level.storage.loot.functions.SetLoreFunction.Builder builder = SetLoreFunction.setLore();

      for (Component c : components) {
         builder.addLine(c);
      }

      return this.addFunction(builder.setMode(ReplaceAll.INSTANCE).build());
   }

   default F setName(Component component) {
      return this.addFunction(SetComponentsFunction.setComponent(DataComponents.CUSTOM_NAME, component).build());
   }

   default F setCustomData(CompoundTag tag) {
      return this.addFunction(SetCustomDataFunction.setCustomData(tag).build());
   }

   default F toggleTooltips(Map<String, Boolean> toggles) {
      Map<ComponentToggle<?>, Boolean> map = new HashMap<>();
      toggles.forEach((name, flag) -> {
         ResourceLocation id = ResourceLocation.parse(name);
         DataComponentType<?> type = (DataComponentType<?>)BuiltInRegistries.DATA_COMPONENT_TYPE.get(id);
         if (type == null) {
            throw new IllegalArgumentException("Component type not found: " + name);
         }

         ComponentToggle<?> toggle = (ComponentToggle<?>)ToggleTooltips.TOGGLES.get(type);
         if (toggle == null) {
            throw new IllegalArgumentException("Can't toggle tooltip visiblity for: " + name);
         }

         map.put(toggle, flag);
      });
      return this.addFunction(new ToggleTooltips(List.of(), map));
   }

   default F jsonFunction(JsonObject json) {
      RegistryOps<JsonElement> regOps = RegistryOps.create(JsonOps.INSTANCE, LootJS.lookup());
      LootItemFunction function = (LootItemFunction)LootItemFunctions.ROOT_CODEC.parse(regOps, json).getOrThrow();
      return this.addFunction(function);
   }

   F addFunction(LootItemFunction var1);
}
