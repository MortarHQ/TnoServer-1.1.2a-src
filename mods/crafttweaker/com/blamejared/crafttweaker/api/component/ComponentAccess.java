package com.blamejared.crafttweaker.api.component;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.network.Filterable;
import net.minecraft.util.Unit;
import net.minecraft.world.LockCode;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.AdventureModePredicate;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.item.component.DebugStickState;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.component.Fireworks;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.component.LodestoneTracker;
import net.minecraft.world.item.component.MapDecorations;
import net.minecraft.world.item.component.MapItemColor;
import net.minecraft.world.item.component.MapPostProcessing;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.item.component.SeededContainerLoot;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.item.component.WritableBookContent;
import net.minecraft.world.item.component.WrittenBookContent;
import net.minecraft.world.item.component.ItemAttributeModifiers.Entry;
import net.minecraft.world.item.component.Tool.Rule;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments.Mutable;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.minecraft.world.level.block.entity.BannerPatternLayers.Layer;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity.Occupant;
import net.minecraft.world.level.saveddata.maps.MapId;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Nullable;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.component.ComponentAccess")
@Document("vanilla/api/component/ComponentAccess")
public interface ComponentAccess<T extends ComponentAccess<T>> {
   @ZenCodeType.Getter("hasCustomData")
   default boolean hasCustomData() {
      return this._has(DataComponents.CUSTOM_DATA);
   }

   @ZenCodeType.Getter("customData")
   default CustomData getCustomData() {
      return this._get(DataComponents.CUSTOM_DATA);
   }

   @ZenCodeType.Method
   default T withCustomData(MapData customData) {
      return this.withCustomData(CustomData.of(customData.getInternal()));
   }

   @ZenCodeType.Method
   default T withCustomData(CustomData customData) {
      return this._with(DataComponents.CUSTOM_DATA, customData);
   }

   @ZenCodeType.Method
   default T withoutCustomData() {
      return this._without(DataComponents.CUSTOM_DATA);
   }

   @ZenCodeType.Getter("hasMaxStackSize")
   default boolean hasMaxStackSize() {
      return this._has(DataComponents.MAX_STACK_SIZE);
   }

   @ZenCodeType.Getter("maxStackSize")
   default int getMaxStackSize() {
      return this.<Integer>_get(DataComponents.MAX_STACK_SIZE);
   }

   @ZenCodeType.Method
   default T withMaxStackSize(int maxStackSize) {
      return this._with(DataComponents.MAX_STACK_SIZE, maxStackSize);
   }

   @ZenCodeType.Method
   default T withoutMaxStackSize() {
      return this._without(DataComponents.MAX_STACK_SIZE);
   }

   @ZenCodeType.Getter("hasMaxDamage")
   default boolean hasMaxDamage() {
      return this._has(DataComponents.MAX_DAMAGE);
   }

   @ZenCodeType.Getter("maxDamage")
   default int getMaxDamage() {
      return this.<Integer>_get(DataComponents.MAX_DAMAGE);
   }

   @ZenCodeType.Method
   default T withMaxDamage(int maxDamage) {
      return this._with(DataComponents.MAX_DAMAGE, maxDamage);
   }

   @ZenCodeType.Method
   default T withoutMaxDamage() {
      return this._without(DataComponents.MAX_DAMAGE);
   }

   @ZenCodeType.Getter("hasDamage")
   default boolean hasDamage() {
      return this._has(DataComponents.DAMAGE);
   }

   @ZenCodeType.Getter("damage")
   default int getDamage() {
      return this.<Integer>_get(DataComponents.DAMAGE);
   }

   @ZenCodeType.Method
   default T withDamage(int damage) {
      return this._with(DataComponents.DAMAGE, damage);
   }

   @ZenCodeType.Method
   default T withoutDamage() {
      return this._without(DataComponents.DAMAGE);
   }

   @ZenCodeType.Getter("hasUnbreakable")
   default boolean hasUnbreakable() {
      return this._has(DataComponents.UNBREAKABLE);
   }

   @ZenCodeType.Getter("unbreakable")
   default Unbreakable getUnbreakable() {
      return this._get(DataComponents.UNBREAKABLE);
   }

   @ZenCodeType.Method
   default T withUnbreakable(@ZenCodeType.OptionalBoolean(true) boolean showInTooltip) {
      return this.withUnbreakable(new Unbreakable(showInTooltip));
   }

   @ZenCodeType.Method
   default T withUnbreakable(Unbreakable unbreakable) {
      return this._with(DataComponents.UNBREAKABLE, unbreakable);
   }

   @ZenCodeType.Method
   default T withoutUnbreakable() {
      return this._without(DataComponents.UNBREAKABLE);
   }

   @ZenCodeType.Getter("hasCustomName")
   default boolean hasCustomName() {
      return this._has(DataComponents.CUSTOM_NAME);
   }

   @ZenCodeType.Getter("customName")
   default Component getCustomName() {
      return this._get(DataComponents.CUSTOM_NAME);
   }

   @ZenCodeType.Method
   default T withCustomName(Component name) {
      return this._with(DataComponents.CUSTOM_NAME, name);
   }

   @ZenCodeType.Method
   default T withoutCustomName() {
      return this._without(DataComponents.CUSTOM_NAME);
   }

   @ZenCodeType.Getter("hasItemName")
   default boolean hasItemName() {
      return this._has(DataComponents.ITEM_NAME);
   }

   @ZenCodeType.Getter("itemName")
   default Component getItemName() {
      return this._get(DataComponents.ITEM_NAME);
   }

   @ZenCodeType.Method
   default T withItemName(Component name) {
      return this._with(DataComponents.ITEM_NAME, name);
   }

   @ZenCodeType.Method
   default T withoutItemName() {
      return this._without(DataComponents.ITEM_NAME);
   }

   @ZenCodeType.Getter("hasLore")
   default boolean hasLore() {
      return this._has(DataComponents.LORE);
   }

   @ZenCodeType.Getter("lore")
   default ItemLore getLore() {
      return this._get(DataComponents.LORE);
   }

   @ZenCodeType.Method
   default T withLore(List<Component> components) {
      return this.withLore(new ItemLore(components));
   }

   @ZenCodeType.Method
   default T withLore(Component components) {
      return this.withLore(new ItemLore(Collections.singletonList(components)));
   }

   @ZenCodeType.Method
   default T withLore(ItemLore lore) {
      return this._with(DataComponents.LORE, lore);
   }

   @ZenCodeType.Method
   default T withoutLore() {
      return this._without(DataComponents.LORE);
   }

   @ZenCodeType.Getter("hasRarity")
   default boolean hasRarity() {
      return this._has(DataComponents.RARITY);
   }

   @ZenCodeType.Getter("rarity")
   default Rarity getRarity() {
      return this._get(DataComponents.RARITY);
   }

   @ZenCodeType.Method
   default T withRarity(Rarity rarity) {
      return this._with(DataComponents.RARITY, rarity);
   }

   @ZenCodeType.Method
   default T withoutRarity() {
      return this._without(DataComponents.RARITY);
   }

   @ZenCodeType.Getter("hasEnchantments")
   default boolean hasEnchantments() {
      return this._has(DataComponents.ENCHANTMENTS);
   }

   @ZenCodeType.Getter("enchantments")
   default ItemEnchantments getEnchantments() {
      return this._get(DataComponents.ENCHANTMENTS);
   }

   @ZenCodeType.Method
   default T withEnchantments(ItemEnchantments enchantments) {
      return this._with(DataComponents.ENCHANTMENTS, enchantments);
   }

   @ZenCodeType.Method
   default T withEnchantment(Enchantment enchantment, @ZenCodeType.OptionalInt(1) int level) {
      Mutable mutable = new Mutable(this.hasEnchantments() ? this.getEnchantments() : ItemEnchantments.EMPTY);
      mutable.set(Services.REGISTRY.holderOrThrow(Registries.ENCHANTMENT, enchantment), level);
      return this.withEnchantments(mutable.toImmutable());
   }

   @ZenCodeType.Method
   default T withoutEnchantment(Enchantment enchantment) {
      Mutable mutable = new Mutable(this.hasEnchantments() ? this.getEnchantments() : ItemEnchantments.EMPTY);
      mutable.removeIf(enchantmentHolder -> enchantmentHolder.value() == enchantment);
      return this.withEnchantments(mutable.toImmutable());
   }

   @ZenCodeType.Method
   default T withoutEnchantments() {
      return this._without(DataComponents.ENCHANTMENTS);
   }

   @ZenCodeType.Getter("hasCanPlaceOn")
   default boolean hasCanPlaceOn() {
      return this._has(DataComponents.CAN_PLACE_ON);
   }

   @ZenCodeType.Getter("canPlaceOn")
   default AdventureModePredicate getCanPlaceOn() {
      return this._get(DataComponents.CAN_PLACE_ON);
   }

   @ZenCodeType.Method
   default T withCanPlaceOn(List<BlockPredicate> predicates, @ZenCodeType.OptionalBoolean(true) boolean showInTooltip) {
      return this.withCanPlaceOn(new AdventureModePredicate(predicates, showInTooltip));
   }

   @ZenCodeType.Method
   default T withCanPlaceOn(AdventureModePredicate predicate) {
      return this._with(DataComponents.CAN_PLACE_ON, predicate);
   }

   @ZenCodeType.Method
   default T withoutCanPlaceOn() {
      return this._without(DataComponents.CAN_PLACE_ON);
   }

   @ZenCodeType.Getter("hasCanBreak")
   default boolean hasCanBreak() {
      return this._has(DataComponents.CAN_BREAK);
   }

   @ZenCodeType.Getter("canBreak")
   default AdventureModePredicate getCanBreak() {
      return this._get(DataComponents.CAN_BREAK);
   }

   @ZenCodeType.Method
   default T withCanBreak(List<BlockPredicate> predicates, @ZenCodeType.OptionalBoolean(true) boolean showInTooltip) {
      return this.withCanBreak(new AdventureModePredicate(predicates, showInTooltip));
   }

   @ZenCodeType.Method
   default T withCanBreak(AdventureModePredicate predicate) {
      return this._with(DataComponents.CAN_BREAK, predicate);
   }

   @ZenCodeType.Method
   default T withoutCanBreak() {
      return this._without(DataComponents.CAN_BREAK);
   }

   @ZenCodeType.Getter("hasAttributeModifiers")
   default boolean hasAttributeModifiers() {
      return this._has(DataComponents.ATTRIBUTE_MODIFIERS);
   }

   @ZenCodeType.Getter("attributeModifiers")
   default ItemAttributeModifiers getAttributeModifiers() {
      return this._get(DataComponents.ATTRIBUTE_MODIFIERS);
   }

   @ZenCodeType.Method
   default T withAttributeModifiers(ItemAttributeModifiers modifiers) {
      return this._with(DataComponents.ATTRIBUTE_MODIFIERS, modifiers);
   }

   @ZenCodeType.Method
   default T withAttributeModifiers(Entry modifier, @ZenCodeType.OptionalBoolean(true) boolean showInTooltip) {
      return this._with(DataComponents.ATTRIBUTE_MODIFIERS, new ItemAttributeModifiers(List.of(modifier), showInTooltip));
   }

   @ZenCodeType.Method
   default T withAttributeModifiers(List<Entry> modifiers, @ZenCodeType.OptionalBoolean(true) boolean showInTooltip) {
      return this._with(DataComponents.ATTRIBUTE_MODIFIERS, new ItemAttributeModifiers(modifiers, showInTooltip));
   }

   @ZenCodeType.Method
   default T withoutAttributeModifiers() {
      return this._without(DataComponents.ATTRIBUTE_MODIFIERS);
   }

   @ZenCodeType.Getter("hasCustomModelData")
   default boolean hasCustomModelData() {
      return this._has(DataComponents.CUSTOM_MODEL_DATA);
   }

   @ZenCodeType.Getter("customModelData")
   default CustomModelData getCustomModelData() {
      return this._get(DataComponents.CUSTOM_MODEL_DATA);
   }

   @ZenCodeType.Method
   default T withCustomModelData(int value) {
      return this.withCustomModelData(new CustomModelData(value));
   }

   @ZenCodeType.Method
   default T withCustomModelData(CustomModelData data) {
      return this._with(DataComponents.CUSTOM_MODEL_DATA, data);
   }

   @ZenCodeType.Method
   default T withoutCustomModelData() {
      return this._without(DataComponents.CUSTOM_MODEL_DATA);
   }

   @ZenCodeType.Getter("hideAdditionalTooltip")
   default boolean hideAdditionalTooltip() {
      return this._has(DataComponents.HIDE_ADDITIONAL_TOOLTIP);
   }

   @ZenCodeType.Method
   default T withHideAdditionalTooltip() {
      return this._with(DataComponents.HIDE_ADDITIONAL_TOOLTIP, Unit.INSTANCE);
   }

   @ZenCodeType.Method
   default T withoutHideAdditionalTooltip() {
      return this._without(DataComponents.HIDE_ADDITIONAL_TOOLTIP);
   }

   @ZenCodeType.Getter("hideTooltip")
   default boolean hideTooltip() {
      return this._has(DataComponents.HIDE_TOOLTIP);
   }

   @ZenCodeType.Method
   default T withHideTooltip() {
      return this._with(DataComponents.HIDE_TOOLTIP, Unit.INSTANCE);
   }

   @ZenCodeType.Method
   default T withoutHideTooltip() {
      return this._without(DataComponents.HIDE_TOOLTIP);
   }

   @ZenCodeType.Getter("hasRepairCost")
   default boolean hasRepairCost() {
      return this._has(DataComponents.REPAIR_COST);
   }

   @ZenCodeType.Getter("repairCost")
   default int repairCost() {
      return this.<Integer>_get(DataComponents.REPAIR_COST);
   }

   @ZenCodeType.Method
   default T withRepairCost(int cost) {
      return this._with(DataComponents.REPAIR_COST, cost);
   }

   @ZenCodeType.Method
   default T withoutRepairCost() {
      return this._without(DataComponents.REPAIR_COST);
   }

   @ZenCodeType.Getter("creativeSlotLock")
   default boolean creativeSlotLock() {
      return this._has(DataComponents.CREATIVE_SLOT_LOCK);
   }

   @ZenCodeType.Method
   default T withCreativeSlotLock() {
      return this._with(DataComponents.CREATIVE_SLOT_LOCK, Unit.INSTANCE);
   }

   @ZenCodeType.Method
   default T withoutCreativeSlotLock() {
      return this._without(DataComponents.CREATIVE_SLOT_LOCK);
   }

   @ZenCodeType.Getter("hasEnchantmentGlintOverride")
   default boolean hasEnchantmentGlintOverride() {
      return this._has(DataComponents.ENCHANTMENT_GLINT_OVERRIDE);
   }

   @ZenCodeType.Getter("enchantmentGlintOverride")
   default boolean getEnchantmentGlintOverride() {
      return this.<Boolean>_get(DataComponents.ENCHANTMENT_GLINT_OVERRIDE);
   }

   @ZenCodeType.Method
   default T withEnchantmentGlintOverride(boolean value) {
      return this._with(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, value);
   }

   @ZenCodeType.Method
   default T withoutEnchantmentGlintOverride() {
      return this._without(DataComponents.ENCHANTMENT_GLINT_OVERRIDE);
   }

   @ZenCodeType.Getter("isIntangibleProjectile")
   default boolean isIntangibleProjectile() {
      return this._has(DataComponents.INTANGIBLE_PROJECTILE);
   }

   @ZenCodeType.Method
   default T withIntangibleProjectile() {
      return this._with(DataComponents.INTANGIBLE_PROJECTILE, Unit.INSTANCE);
   }

   @ZenCodeType.Method
   default T withoutIntangibleProjectile() {
      return this._without(DataComponents.INTANGIBLE_PROJECTILE);
   }

   @ZenCodeType.Getter("hasFood")
   default boolean hasFood() {
      return this._has(DataComponents.FOOD);
   }

   @ZenCodeType.Getter("food")
   default FoodProperties getFood() {
      return this._get(DataComponents.FOOD);
   }

   @ZenCodeType.Method
   default T withFood(FoodProperties food) {
      return this._with(DataComponents.FOOD, food);
   }

   @ZenCodeType.Method
   default T withoutFood() {
      return this._without(DataComponents.FOOD);
   }

   @ZenCodeType.Getter("isFireResistant")
   default boolean isFireResistant() {
      return this._has(DataComponents.FIRE_RESISTANT);
   }

   @ZenCodeType.Method
   default T withFireResistant() {
      return this._with(DataComponents.FIRE_RESISTANT, Unit.INSTANCE);
   }

   @ZenCodeType.Method
   default T withoutFireResistant() {
      return this._without(DataComponents.FIRE_RESISTANT);
   }

   @ZenCodeType.Getter("hasTool")
   default boolean hasTool() {
      return this._has(DataComponents.TOOL);
   }

   @ZenCodeType.Getter("tool")
   default Tool getTool() {
      return this._get(DataComponents.TOOL);
   }

   @ZenCodeType.Method
   default T withTool(List<Rule> rules, float defaultMiningSpeed, int damagePerBlock) {
      return this.withTool(new Tool(rules, defaultMiningSpeed, damagePerBlock));
   }

   @ZenCodeType.Method
   default T withTool(Tool tool) {
      return this._with(DataComponents.TOOL, tool);
   }

   @ZenCodeType.Method
   default T withoutTool() {
      return this._without(DataComponents.TOOL);
   }

   @ZenCodeType.Getter("hasStoredEnchantments")
   default boolean hasStoredEnchantments() {
      return this._has(DataComponents.STORED_ENCHANTMENTS);
   }

   @ZenCodeType.Getter("storedEnchantments")
   default ItemEnchantments getStoredEnchantments() {
      return this._get(DataComponents.STORED_ENCHANTMENTS);
   }

   @ZenCodeType.Method
   default T withStoredEnchantments(ItemEnchantments enchantments) {
      return this._with(DataComponents.STORED_ENCHANTMENTS, enchantments);
   }

   @ZenCodeType.Method
   default T withoutStoredEnchantments() {
      return this._without(DataComponents.STORED_ENCHANTMENTS);
   }

   @ZenCodeType.Getter("hasDyedColor")
   default boolean hasDyedColor() {
      return this._has(DataComponents.DYED_COLOR);
   }

   @ZenCodeType.Getter("dyedColor")
   default DyedItemColor getDyedColor() {
      return this._get(DataComponents.DYED_COLOR);
   }

   @ZenCodeType.Method
   default T withDyedColor(int rgb, @ZenCodeType.OptionalBoolean(true) boolean showInTooltip) {
      return this.withDyedColor(new DyedItemColor(rgb, showInTooltip));
   }

   @ZenCodeType.Method
   default T withDyedColor(DyedItemColor color) {
      return this._with(DataComponents.DYED_COLOR, color);
   }

   @ZenCodeType.Method
   default T withoutDyedColor() {
      return this._without(DataComponents.DYED_COLOR);
   }

   @ZenCodeType.Getter("hasMapColor")
   default boolean hasMapColor() {
      return this._has(DataComponents.MAP_COLOR);
   }

   @ZenCodeType.Getter("mapColor")
   default MapItemColor getMapColor() {
      return this._get(DataComponents.MAP_COLOR);
   }

   @ZenCodeType.Method
   default T withMapColor(int rgb) {
      return this.withMapColor(new MapItemColor(rgb));
   }

   @ZenCodeType.Method
   default T withMapColor(MapItemColor color) {
      return this._with(DataComponents.MAP_COLOR, color);
   }

   @ZenCodeType.Method
   default T withoutMapColor() {
      return this._without(DataComponents.MAP_COLOR);
   }

   @ZenCodeType.Getter("hasMapId")
   default boolean hasMapId() {
      return this._has(DataComponents.MAP_ID);
   }

   @ZenCodeType.Getter("mapId")
   default MapId getMapId() {
      return this._get(DataComponents.MAP_ID);
   }

   @ZenCodeType.Method
   default T withMapId(int id) {
      return this.withMapId(new MapId(id));
   }

   @ZenCodeType.Method
   default T withMapId(MapId mapId) {
      return this._with(DataComponents.MAP_ID, mapId);
   }

   @ZenCodeType.Method
   default T withoutMapId() {
      return this._without(DataComponents.MAP_ID);
   }

   @ZenCodeType.Getter("hasMapDecorations")
   default boolean hasMapDecorations() {
      return this._has(DataComponents.MAP_DECORATIONS);
   }

   @ZenCodeType.Getter("mapDecorations")
   default MapDecorations getMapDecorations() {
      return this._get(DataComponents.MAP_DECORATIONS);
   }

   @ZenCodeType.Method
   default T withMapDecorations(Map<String, net.minecraft.world.item.component.MapDecorations.Entry> decorations) {
      return this.withMapDecorations(new MapDecorations(decorations));
   }

   @ZenCodeType.Method
   default T withMapDecorations(MapDecorations decorations) {
      return this._with(DataComponents.MAP_DECORATIONS, decorations);
   }

   @ZenCodeType.Method
   default T withoutMapDecorations() {
      return this._without(DataComponents.MAP_DECORATIONS);
   }

   @ZenCodeType.Getter("hasMapPostProcessing")
   default boolean hasMapPostProcessing() {
      return this._has(DataComponents.MAP_POST_PROCESSING);
   }

   @ZenCodeType.Getter("mapPostProcessing")
   default MapPostProcessing getMapPostProcessing() {
      return this._get(DataComponents.MAP_POST_PROCESSING);
   }

   @ZenCodeType.Method
   default T withMapPostProcessing(MapPostProcessing value) {
      return this._with(DataComponents.MAP_POST_PROCESSING, value);
   }

   @ZenCodeType.Method
   default T withoutMapPostProcessing() {
      return this._without(DataComponents.MAP_POST_PROCESSING);
   }

   @ZenCodeType.Getter("hasChargedProjectiles")
   default boolean hasChargedProjectiles() {
      return this._has(DataComponents.CHARGED_PROJECTILES);
   }

   @ZenCodeType.Getter("chargedProjectiles")
   default ChargedProjectiles getChargedProjectiles() {
      return this._get(DataComponents.CHARGED_PROJECTILES);
   }

   @ZenCodeType.Method
   default T withChargedProjectiles(IItemStack item) {
      return this.withChargedProjectiles(ChargedProjectiles.of(item.getInternal()));
   }

   @ZenCodeType.Method
   default T withChargedProjectiles(List<IItemStack> items) {
      return this.withChargedProjectiles(ChargedProjectiles.of(Lists.transform(items, IItemStack::getInternal)));
   }

   @ZenCodeType.Method
   default T withChargedProjectiles(ChargedProjectiles chargedProjectiles) {
      return this._with(DataComponents.CHARGED_PROJECTILES, chargedProjectiles);
   }

   @ZenCodeType.Method
   default T withoutChargedProjectiles() {
      return this._without(DataComponents.CHARGED_PROJECTILES);
   }

   @ZenCodeType.Getter("hasBundleContents")
   default boolean hasBundleContents() {
      return this._has(DataComponents.BUNDLE_CONTENTS);
   }

   @ZenCodeType.Getter("bundleContents")
   default BundleContents getBundleContents() {
      return this._get(DataComponents.BUNDLE_CONTENTS);
   }

   @ZenCodeType.Method
   default T withBundleContents(List<IItemStack> contents) {
      return this._with(DataComponents.BUNDLE_CONTENTS, new BundleContents(Lists.transform(contents, IItemStack::getInternal)));
   }

   @ZenCodeType.Method
   default T withBundleContents(BundleContents contents) {
      return this._with(DataComponents.BUNDLE_CONTENTS, contents);
   }

   @ZenCodeType.Method
   default T withoutBundleContents() {
      return this._without(DataComponents.BUNDLE_CONTENTS);
   }

   @ZenCodeType.Getter("hasPotionContents")
   default boolean hasPotionContents() {
      return this._has(DataComponents.POTION_CONTENTS);
   }

   @ZenCodeType.Getter("potionContents")
   default PotionContents getPotionContents() {
      return this._get(DataComponents.POTION_CONTENTS);
   }

   @ZenCodeType.Method
   default T withPotionContents(Potion potion) {
      return this.withPotionContents(new PotionContents(Services.REGISTRY.holderOrThrow(Registries.POTION, potion)));
   }

   @ZenCodeType.Method
   default T withPotionContents(Potion potion, List<MobEffectInstance> customEffects) {
      return this.withPotionContents(
         new PotionContents(Optional.of(Services.REGISTRY.holderOrThrow(Registries.POTION, potion)), Optional.empty(), customEffects)
      );
   }

   @ZenCodeType.Method
   default T withPotionContents(Potion potion, int customColor, List<MobEffectInstance> customEffects) {
      return this.withPotionContents(
         new PotionContents(Optional.of(Services.REGISTRY.holderOrThrow(Registries.POTION, potion)), Optional.of(customColor), customEffects)
      );
   }

   @ZenCodeType.Method
   default T withPotionContents(PotionContents contents) {
      return this._with(DataComponents.POTION_CONTENTS, contents);
   }

   @ZenCodeType.Method
   default T withoutPotionContents() {
      return this._without(DataComponents.POTION_CONTENTS);
   }

   @ZenCodeType.Getter("hasSuspiciousStewEffects")
   default boolean hasSuspiciousStewEffects() {
      return this._has(DataComponents.SUSPICIOUS_STEW_EFFECTS);
   }

   @ZenCodeType.Getter("suspiciousStewEffects")
   default SuspiciousStewEffects getSuspiciousStewEffects() {
      return this._get(DataComponents.SUSPICIOUS_STEW_EFFECTS);
   }

   @ZenCodeType.Method
   default T withSuspiciousStewEffects(List<net.minecraft.world.item.component.SuspiciousStewEffects.Entry> effects) {
      return this.withSuspiciousStewEffects(new SuspiciousStewEffects(effects));
   }

   @ZenCodeType.Method
   default T withSuspiciousStewEffects(SuspiciousStewEffects suspiciousStewEffects) {
      return this._with(DataComponents.SUSPICIOUS_STEW_EFFECTS, suspiciousStewEffects);
   }

   @ZenCodeType.Method
   default T withoutSuspiciousStewEffects() {
      return this._without(DataComponents.SUSPICIOUS_STEW_EFFECTS);
   }

   @ZenCodeType.Getter("hasWritableBookContent")
   default boolean hasWritableBookContent() {
      return this._has(DataComponents.WRITABLE_BOOK_CONTENT);
   }

   @ZenCodeType.Getter("writableBookContent")
   default WritableBookContent getWritableBookContent() {
      return this._get(DataComponents.WRITABLE_BOOK_CONTENT);
   }

   @ZenCodeType.Method
   default T withWritableBookContent(List<Filterable<String>> pages) {
      return this.withWritableBookContent(new WritableBookContent(pages));
   }

   @ZenCodeType.Method
   default T withWritableBookContent(WritableBookContent content) {
      return this._with(DataComponents.WRITABLE_BOOK_CONTENT, content);
   }

   @ZenCodeType.Method
   default T withoutWritableBookContent() {
      return this._without(DataComponents.WRITABLE_BOOK_CONTENT);
   }

   @ZenCodeType.Getter("hasWrittenBookContent")
   default boolean hasWrittenBookContent() {
      return this._has(DataComponents.WRITABLE_BOOK_CONTENT);
   }

   @ZenCodeType.Getter("writtenBookContent")
   default WrittenBookContent getWrittenBookContent() {
      return this._get(DataComponents.WRITTEN_BOOK_CONTENT);
   }

   @ZenCodeType.Method
   default T withWrittenBookContent(WrittenBookContent content) {
      return this._with(DataComponents.WRITTEN_BOOK_CONTENT, content);
   }

   @ZenCodeType.Method
   default T withoutWrittenBookContent() {
      return this._without(DataComponents.WRITTEN_BOOK_CONTENT);
   }

   @ZenCodeType.Getter("hasTrim")
   default boolean hasTrim() {
      return this._has(DataComponents.TRIM);
   }

   @ZenCodeType.Getter("trim")
   default ArmorTrim getTrim() {
      return this._get(DataComponents.TRIM);
   }

   @ZenCodeType.Method
   default T withTrim(ArmorTrim trim) {
      return this._with(DataComponents.TRIM, trim);
   }

   @ZenCodeType.Method
   default T withoutTrim() {
      return this._without(DataComponents.TRIM);
   }

   @ZenCodeType.Getter("hasDebugStickState")
   default boolean hasDebugStickState() {
      return this._has(DataComponents.DEBUG_STICK_STATE);
   }

   @ZenCodeType.Getter("debugStickState")
   default DebugStickState getDebugStickState() {
      return this._get(DataComponents.DEBUG_STICK_STATE);
   }

   @ZenCodeType.Method
   default T withDebugStickState(DebugStickState state) {
      return this._with(DataComponents.DEBUG_STICK_STATE, state);
   }

   @ZenCodeType.Method
   default T withoutDebugStickState() {
      return this._without(DataComponents.DEBUG_STICK_STATE);
   }

   @ZenCodeType.Getter("hasEntityData")
   default boolean hasEntityData() {
      return this._has(DataComponents.ENTITY_DATA);
   }

   @ZenCodeType.Getter("entityData")
   default CustomData getEntityData() {
      return this._get(DataComponents.ENTITY_DATA);
   }

   @ZenCodeType.Method
   default T withEntityData(MapData data) {
      return this.withEntityData(CustomData.of(data.getInternal()));
   }

   @ZenCodeType.Method
   default T withEntityData(CustomData data) {
      return this._with(DataComponents.ENTITY_DATA, data);
   }

   @ZenCodeType.Method
   default T withoutEntityDate() {
      return this._without(DataComponents.ENTITY_DATA);
   }

   @ZenCodeType.Getter("hasBucketEntityData")
   default boolean hasBucketEntityData() {
      return this._has(DataComponents.BUCKET_ENTITY_DATA);
   }

   @ZenCodeType.Getter("bucketEntityData")
   default CustomData getBucketEntityData() {
      return this._get(DataComponents.BUCKET_ENTITY_DATA);
   }

   @ZenCodeType.Method
   default T withBucketEntityData(MapData data) {
      return this.withBucketEntityData(CustomData.of(data.getInternal()));
   }

   @ZenCodeType.Method
   default T withBucketEntityData(CustomData data) {
      return this._with(DataComponents.BUCKET_ENTITY_DATA, data);
   }

   @ZenCodeType.Method
   default T withoutBucketEntityData() {
      return this._without(DataComponents.BUCKET_ENTITY_DATA);
   }

   @ZenCodeType.Getter("hasBlockEntityData")
   default boolean hasBlockEntityData() {
      return this._has(DataComponents.BLOCK_ENTITY_DATA);
   }

   @ZenCodeType.Getter("blockEntityData")
   default CustomData getBlockEntityData() {
      return this._get(DataComponents.BLOCK_ENTITY_DATA);
   }

   @ZenCodeType.Method
   default T withBlockEntityData(MapData data) {
      return this.withBlockEntityData(CustomData.of(data.getInternal()));
   }

   @ZenCodeType.Method
   default T withBlockEntityData(CustomData data) {
      return this._with(DataComponents.BLOCK_ENTITY_DATA, data);
   }

   @ZenCodeType.Method
   default T withoutBlockEntityData() {
      return this._without(DataComponents.BLOCK_ENTITY_DATA);
   }

   @ZenCodeType.Getter("hasInstrument")
   default boolean hasInstrument() {
      return this._has(DataComponents.INSTRUMENT);
   }

   @ZenCodeType.Getter("instrument")
   default Instrument getInstrument() {
      return (Instrument)this.<Holder>_get(DataComponents.INSTRUMENT).value();
   }

   @ZenCodeType.Method
   default T withInstrument(Instrument instrument) {
      return this._with(DataComponents.INSTRUMENT, Services.REGISTRY.holderOrThrow(Registries.INSTRUMENT, instrument));
   }

   @ZenCodeType.Method
   default T withoutInstrument() {
      return this._without(DataComponents.INSTRUMENT);
   }

   @ZenCodeType.Getter("hasOminousBottleAmplifier")
   default boolean hasOminousBottleAmplifier() {
      return this._has(DataComponents.OMINOUS_BOTTLE_AMPLIFIER);
   }

   @ZenCodeType.Getter("ominousBottleAmplifier")
   default int getOminousBottleAmplifier() {
      return this.<Integer>_get(DataComponents.OMINOUS_BOTTLE_AMPLIFIER);
   }

   @ZenCodeType.Method
   default T withOminousBottleAmplifier(int amplifier) {
      return this._with(DataComponents.OMINOUS_BOTTLE_AMPLIFIER, amplifier);
   }

   @ZenCodeType.Method
   default T withoutOminousBottleAmplifier() {
      return this._without(DataComponents.OMINOUS_BOTTLE_AMPLIFIER);
   }

   @ZenCodeType.Getter("hasRecipes")
   default boolean hasRecipes() {
      return this._has(DataComponents.RECIPES);
   }

   @ZenCodeType.Getter("recipes")
   default List<ResourceLocation> getRecipes() {
      return this._get(DataComponents.RECIPES);
   }

   @ZenCodeType.Method
   default T withRecipes(List<ResourceLocation> recipes) {
      return this._with(DataComponents.RECIPES, recipes);
   }

   @ZenCodeType.Method
   default T withoutRecipes() {
      return this._without(DataComponents.RECIPES);
   }

   @ZenCodeType.Getter("hasLodestoneTracker")
   default boolean hasLodestoneTracker() {
      return this._has(DataComponents.LODESTONE_TRACKER);
   }

   @ZenCodeType.Getter("lodestoneTracker")
   default LodestoneTracker getLodestoneTracker() {
      return this._get(DataComponents.LODESTONE_TRACKER);
   }

   @ZenCodeType.Method
   default T withLodestoneTracker(LodestoneTracker tracker) {
      return this._with(DataComponents.LODESTONE_TRACKER, tracker);
   }

   @ZenCodeType.Method
   default T withoutLodestoneTracker() {
      return this._without(DataComponents.LODESTONE_TRACKER);
   }

   @ZenCodeType.Getter("hasFireworkExplosion")
   default boolean hasFireworkExplosion() {
      return this._has(DataComponents.FIREWORK_EXPLOSION);
   }

   @ZenCodeType.Getter("fireworkExplosion")
   default FireworkExplosion getFireworkExplosion() {
      return this._get(DataComponents.FIREWORK_EXPLOSION);
   }

   @ZenCodeType.Method
   default T withFireworkExplosion(FireworkExplosion explosion) {
      return this._with(DataComponents.FIREWORK_EXPLOSION, explosion);
   }

   @ZenCodeType.Method
   default T withoutFireworkExplosion() {
      return this._without(DataComponents.FIREWORK_EXPLOSION);
   }

   @ZenCodeType.Getter("hasFireworks")
   default boolean hasFireworks() {
      return this._has(DataComponents.FIREWORKS);
   }

   @ZenCodeType.Getter("fireworks")
   default Fireworks getFireworks() {
      return this._get(DataComponents.FIREWORKS);
   }

   @ZenCodeType.Method
   default T withFireworks(int flightDuration, List<FireworkExplosion> explosions) {
      return this.withFireworks(new Fireworks(flightDuration, explosions));
   }

   @ZenCodeType.Method
   default T withFireworks(Fireworks fireworks) {
      return this._with(DataComponents.FIREWORKS, fireworks);
   }

   @ZenCodeType.Method
   default T withoutFireworks() {
      return this._without(DataComponents.FIREWORKS);
   }

   @ZenCodeType.Getter("hasProfile")
   default boolean hasProfile() {
      return this._has(DataComponents.PROFILE);
   }

   @ZenCodeType.Getter("profile")
   default ResolvableProfile getProfile() {
      return this._get(DataComponents.PROFILE);
   }

   @ZenCodeType.Method
   default T withProfile(ResolvableProfile profile) {
      return this._with(DataComponents.PROFILE, profile);
   }

   @ZenCodeType.Method
   default T withoutProfile() {
      return this._without(DataComponents.PROFILE);
   }

   @ZenCodeType.Getter("hasNoteBlockSound")
   default boolean hasNoteBlockSound() {
      return this._has(DataComponents.NOTE_BLOCK_SOUND);
   }

   @ZenCodeType.Getter("noteBlockSound")
   default ResourceLocation getNoteBlockSound() {
      return this._get(DataComponents.NOTE_BLOCK_SOUND);
   }

   @ZenCodeType.Method
   default T withNoteBlockSound(ResourceLocation sound) {
      return this._with(DataComponents.NOTE_BLOCK_SOUND, sound);
   }

   @ZenCodeType.Method
   default T withoutNoteBlockSound() {
      return this._without(DataComponents.NOTE_BLOCK_SOUND);
   }

   @ZenCodeType.Getter("hasBannerPatterns")
   default boolean hasBannerPatterns() {
      return this._has(DataComponents.BANNER_PATTERNS);
   }

   @ZenCodeType.Getter("bannerPatterns")
   default BannerPatternLayers getBannerPatterns() {
      return this._get(DataComponents.BANNER_PATTERNS);
   }

   @ZenCodeType.Method
   default T withBannerPatterns(List<Layer> layers) {
      return this.withBannerPatterns(new BannerPatternLayers(layers));
   }

   @ZenCodeType.Method
   default T withBannerPatterns(BannerPatternLayers layers) {
      return this._with(DataComponents.BANNER_PATTERNS, layers);
   }

   @ZenCodeType.Method
   default T withoutBannerPatterns() {
      return this._without(DataComponents.BANNER_PATTERNS);
   }

   @ZenCodeType.Getter("hasBaseColor")
   default boolean hasBaseColor() {
      return this._has(DataComponents.BASE_COLOR);
   }

   @ZenCodeType.Getter("baseColor")
   default DyeColor getBaseColor() {
      return this._get(DataComponents.BASE_COLOR);
   }

   @ZenCodeType.Method
   default T withBaseColor(DyeColor color) {
      return this._with(DataComponents.BASE_COLOR, color);
   }

   @ZenCodeType.Method
   default T withoutBaseColor() {
      return this._without(DataComponents.BASE_COLOR);
   }

   @ZenCodeType.Getter("hasPotDecorations")
   default boolean hasPotDecorations() {
      return this._has(DataComponents.POT_DECORATIONS);
   }

   @ZenCodeType.Getter("potDecorations")
   default PotDecorations getPotDecorations() {
      return this._get(DataComponents.POT_DECORATIONS);
   }

   @ZenCodeType.Method
   default T withPotDecorations(Item back, Item left, Item right, Item front) {
      return this.withPotDecorations(new PotDecorations(back, left, right, front));
   }

   @ZenCodeType.Method
   default T withPotDecorations(PotDecorations decorations) {
      return this._with(DataComponents.POT_DECORATIONS, decorations);
   }

   @ZenCodeType.Method
   default T withoutPotDecorations() {
      return this._without(DataComponents.POT_DECORATIONS);
   }

   @ZenCodeType.Getter("hasContainer")
   default boolean hasContainer() {
      return this._has(DataComponents.CONTAINER);
   }

   @ZenCodeType.Getter("container")
   default ItemContainerContents getContainer() {
      return this._get(DataComponents.CONTAINER);
   }

   @ZenCodeType.Method
   default T withContainer(List<IItemStack> contents) {
      return this.withContainer(ItemContainerContents.fromItems(Lists.transform(contents, IItemStack::getInternal)));
   }

   @ZenCodeType.Method
   default T withContainer(ItemContainerContents contents) {
      return this._with(DataComponents.CONTAINER, contents);
   }

   @ZenCodeType.Method
   default T withoutContainer() {
      return this._without(DataComponents.CONTAINER);
   }

   @ZenCodeType.Getter("hasBlockState")
   default boolean hasBlockState() {
      return this._has(DataComponents.BLOCK_STATE);
   }

   @ZenCodeType.Getter("blockState")
   default BlockItemStateProperties getBlockState() {
      return this._get(DataComponents.BLOCK_STATE);
   }

   @ZenCodeType.Method
   default T withBlockState(BlockItemStateProperties properties) {
      return this._with(DataComponents.BLOCK_STATE, properties);
   }

   @ZenCodeType.Method
   default T withoutBlockState() {
      return this._without(DataComponents.BLOCK_STATE);
   }

   @ZenCodeType.Getter("hasBees")
   default boolean hasBees() {
      return this._has(DataComponents.BEES);
   }

   @ZenCodeType.Getter("bees")
   default List<Occupant> getBees() {
      return this._get(DataComponents.BEES);
   }

   @ZenCodeType.Method
   default T withBees(List<Occupant> occupants) {
      return this._with(DataComponents.BEES, occupants);
   }

   @ZenCodeType.Method
   default T withoutBees() {
      return this._without(DataComponents.BEES);
   }

   @ZenCodeType.Getter("hasLock")
   default boolean hasLock() {
      return this._has(DataComponents.LOCK);
   }

   @ZenCodeType.Getter("lockComponent")
   default LockCode getLock() {
      return this._get(DataComponents.LOCK);
   }

   @ZenCodeType.Method
   default T withLock(String code) {
      return this.withLock(new LockCode(code));
   }

   @ZenCodeType.Method
   default T withLock(LockCode lock) {
      return this._with(DataComponents.LOCK, lock);
   }

   @ZenCodeType.Method
   default T withoutLock() {
      return this._without(DataComponents.LOCK);
   }

   @ZenCodeType.Getter("hasContainerLoot")
   default boolean hasContainerLoot() {
      return this._has(DataComponents.CONTAINER_LOOT);
   }

   @ZenCodeType.Getter("containerLoot")
   default SeededContainerLoot getContainerLoot() {
      return this._get(DataComponents.CONTAINER_LOOT);
   }

   @ZenCodeType.Method
   default T withContainerLoot(ResourceKey<LootTable> lootTable, long seed) {
      return this.withContainerLoot(new SeededContainerLoot(lootTable, seed));
   }

   @ZenCodeType.Method
   default T withContainerLoot(SeededContainerLoot loot) {
      return this._with(DataComponents.CONTAINER_LOOT, loot);
   }

   @ZenCodeType.Method
   default T withoutContainerLoot() {
      return this._without(DataComponents.CONTAINER_LOOT);
   }

   <U> U _get(DataComponentType<? extends U> var1);

   <U> T _with(DataComponentType<U> var1, @Nullable U var2);

   <U> T _without(DataComponentType<U> var1);

   <U> boolean _has(DataComponentType<U> var1);
}
