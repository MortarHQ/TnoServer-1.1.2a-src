package com.blamejared.crafttweaker.api.ingredient;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.action.item.ActionModifyAttribute;
import com.blamejared.crafttweaker.api.action.item.ActionSetBurnTime;
import com.blamejared.crafttweaker.api.action.item.tooltip.ActionAddShiftedTooltip;
import com.blamejared.crafttweaker.api.action.item.tooltip.ActionAddTooltip;
import com.blamejared.crafttweaker.api.action.item.tooltip.ActionClearTooltip;
import com.blamejared.crafttweaker.api.action.item.tooltip.ActionModifyShiftedTooltip;
import com.blamejared.crafttweaker.api.action.item.tooltip.ActionModifyTooltip;
import com.blamejared.crafttweaker.api.action.item.tooltip.ActionRemoveRegexTooltip;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.op.IDataOps;
import com.blamejared.crafttweaker.api.ingredient.condition.IIngredientCondition;
import com.blamejared.crafttweaker.api.ingredient.condition.IngredientConditions;
import com.blamejared.crafttweaker.api.ingredient.condition.type.ConditionAnyDamage;
import com.blamejared.crafttweaker.api.ingredient.condition.type.ConditionCustom;
import com.blamejared.crafttweaker.api.ingredient.condition.type.ConditionDamaged;
import com.blamejared.crafttweaker.api.ingredient.condition.type.ConditionDamagedAtLeast;
import com.blamejared.crafttweaker.api.ingredient.condition.type.ConditionDamagedAtMost;
import com.blamejared.crafttweaker.api.ingredient.transformer.IIngredientTransformer;
import com.blamejared.crafttweaker.api.ingredient.transformer.IngredientTransformers;
import com.blamejared.crafttweaker.api.ingredient.transformer.type.TransformCustom;
import com.blamejared.crafttweaker.api.ingredient.transformer.type.TransformDamage;
import com.blamejared.crafttweaker.api.ingredient.transformer.type.TransformReplace;
import com.blamejared.crafttweaker.api.ingredient.transformer.type.TransformReuse;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientList;
import com.blamejared.crafttweaker.api.ingredient.type.IngredientWithAmount;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.tooltip.ITooltipFunction;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.crafting.Ingredient;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.ingredient.IIngredient")
@Document("vanilla/api/ingredient/IIngredient")
public interface IIngredient extends CommandStringDisplayable {
   ResourceLocation INGREDIENT_ID = CraftTweakerConstants.rl("iingredient");
   Codec<IIngredient> CODEC = Ingredient.CODEC.xmap(IIngredient::fromIngredient, IIngredient::asVanillaIngredient);
   StreamCodec<RegistryFriendlyByteBuf, IIngredient> STREAM_CODEC = Ingredient.CONTENTS_STREAM_CODEC
      .map(IIngredient::fromIngredient, IIngredient::asVanillaIngredient);

   @ZenCodeType.Method
   boolean matches(IItemStack var1);

   @ZenCodeType.Method
   @ZenCodeType.Getter("empty")
   default boolean isEmpty() {
      return this.asVanillaIngredient().isEmpty();
   }

   @ZenCodeType.Method
   @ZenCodeType.Operator(ZenCodeType.OperatorType.CONTAINS)
   default boolean contains(IIngredient ingredient) {
      return Arrays.stream(ingredient.getItems()).allMatch(this::matches);
   }

   @ZenCodeType.Method
   @ZenCodeType.Caster(implicit = true)
   Ingredient asVanillaIngredient();

   @ZenCodeType.Method
   default IItemStack getRemainingItem(IItemStack stack) {
      return !this.transformers().isEmpty() ? this.transformers().apply(stack) : Services.PLATFORM.getRemainingItem(stack.getInternal());
   }

   @ZenCodeType.Getter("commandString")
   @Override
   String getCommandString();

   @ZenCodeType.Getter("items")
   IItemStack[] getItems();

   @ZenCodeType.Method
   @ZenCodeType.Setter("burnTime")
   default void setBurnTime(int time) {
      CraftTweakerAPI.apply(new ActionSetBurnTime(this, time));
   }

   @ZenCodeType.Method
   default void clearTooltip(@ZenCodeType.OptionalBoolean boolean leaveName) {
      CraftTweakerAPI.apply(new ActionClearTooltip(this, leaveName));
   }

   @ZenCodeType.Method
   default void addTooltip(Component content) {
      CraftTweakerAPI.apply(new ActionAddTooltip(this, content));
   }

   @ZenCodeType.Method
   default void addShiftTooltip(Component content, @ZenCodeType.Optional Component showMessage) {
      CraftTweakerAPI.apply(new ActionAddShiftedTooltip(this, content, showMessage));
   }

   @ZenCodeType.Method
   default void modifyTooltip(ITooltipFunction function) {
      CraftTweakerAPI.apply(new ActionModifyTooltip(this, function));
   }

   @ZenCodeType.Method
   default void modifyShiftTooltip(ITooltipFunction shiftedFunction, @ZenCodeType.Optional ITooltipFunction unshiftedFunction) {
      CraftTweakerAPI.apply(new ActionModifyShiftedTooltip(this, shiftedFunction, unshiftedFunction));
   }

   @ZenCodeType.Method
   default void removeTooltip(String regex) {
      CraftTweakerAPI.apply(new ActionRemoveRegexTooltip(this, Pattern.compile(regex)));
   }

   @Deprecated(forRemoval = true)
   @ZenCodeType.Method
   default void addGlobalAttributeModifier(Attribute attribute, ResourceLocation id, double value, Operation operation, EquipmentSlot[] slotTypes) {
      AttributeModifier modifier = new AttributeModifier(id, value, operation);
      this.addModifier(attribute, slotTypes, modifier);
   }

   @Deprecated
   private void addModifier(Attribute attribute, EquipmentSlot[] slotTypes, AttributeModifier modifier) {
      Set<EquipmentSlot> validSlots = new HashSet<>(Arrays.asList(slotTypes));
      CraftTweakerAPI.apply(new ActionModifyAttribute(this, event -> {
         if (validSlots.contains(event.getSlotType())) {
            if (event.getModifiers().containsEntry(attribute, modifier)) {
               event.removeModifier(attribute, modifier);
            }

            event.addModifier(attribute, modifier);
         }
      }));
   }

   @Deprecated(forRemoval = true)
   @ZenCodeType.Method
   default void removeGlobalAttribute(Attribute attribute, EquipmentSlot[] slotTypes) {
      Set<EquipmentSlot> validSlots = new HashSet<>(Arrays.asList(slotTypes));
      CraftTweakerAPI.apply(new ActionModifyAttribute(this, event -> {
         if (validSlots.contains(event.getSlotType())) {
            event.removeAttribute(attribute);
         }
      }));
   }

   @Deprecated(forRemoval = true)
   @ZenCodeType.Method
   default void removeGlobalAttributeModifier(String uuid, EquipmentSlot[] slotTypes) {
      this.removeGlobalAttributeModifier(UUID.fromString(uuid), slotTypes);
   }

   @Deprecated(forRemoval = true)
   @ZenCodeType.Method
   default void removeGlobalAttributeModifier(UUID uuid, EquipmentSlot[] slotTypes) {
      Set<EquipmentSlot> validSlots = new HashSet<>(Arrays.asList(slotTypes));
      CraftTweakerAPI.apply(
         new ActionModifyAttribute(
            this,
            event -> {
               if (validSlots.contains(event.getSlotType())) {
                  event.getModifiers()
                     .entries()
                     .stream()
                     .filter(entry -> ((AttributeModifier)entry.getValue()).id().equals(uuid))
                     .forEach(entry -> event.removeModifier((Attribute)entry.getKey(), (AttributeModifier)entry.getValue()));
               }
            }
         )
      );
   }

   static IIngredient fromIngredient(Ingredient ingredient) {
      return IngredientConverter.fromIngredient(ingredient);
   }

   @ZenCodeType.Caster(implicit = true)
   default MapData asMapData() {
      IData data = this.asIData();
      return data instanceof MapData ? (MapData)data : new MapData();
   }

   @ZenCodeType.Caster(implicit = true)
   default IData asIData() {
      return (IData)Ingredient.CODEC_NONEMPTY
         .encodeStart(IDataOps.INSTANCE.withRegistryAccess(), this.asVanillaIngredient())
         .getOrThrow(IllegalArgumentException::new);
   }

   @ZenCodeType.Operator(ZenCodeType.OperatorType.OR)
   default IIngredientList or(IIngredient other) {
      return new IIngredientList(new IIngredient[]{this, other});
   }

   @ZenCodeType.Method
   @ZenCodeType.Operator(ZenCodeType.OperatorType.MUL)
   default IIngredientWithAmount mul(int amount) {
      return new IngredientWithAmount(this, amount);
   }

   @ZenCodeType.Method
   @ZenCodeType.Caster(implicit = true)
   default IIngredientWithAmount asIIngredientWithAmount() {
      return this instanceof IIngredientWithAmount ? (IIngredientWithAmount)this : this.mul(1);
   }

   IngredientTransformers transformers();

   @ZenCodeType.Method
   default IIngredient transformReplace(IItemStack replaceWith) {
      return this.transform(new TransformReplace(replaceWith));
   }

   @ZenCodeType.Method
   default IIngredient transformDamage(@ZenCodeType.OptionalInt(1) int amount) {
      return this.transform(new TransformDamage(amount));
   }

   @ZenCodeType.Method
   default IIngredient transformCustom(String uid, @ZenCodeType.Optional Function<IItemStack, IItemStack> function) {
      return this.transform(new TransformCustom(uid, function));
   }

   @ZenCodeType.Method
   default IIngredient reuse() {
      return this.transform(TransformReuse.INSTANCE);
   }

   default IIngredient transform(IIngredientTransformer transformer) {
      this.transformers().add(transformer);
      return this;
   }

   IngredientConditions conditions();

   @ZenCodeType.Method
   default IIngredient onlyDamaged() {
      return this.condition(ConditionDamaged.INSTANCE);
   }

   @ZenCodeType.Method
   default IIngredient onlyDamagedAtLeast(int minDamage) {
      return this.condition(new ConditionDamagedAtLeast(minDamage));
   }

   @ZenCodeType.Method
   default IIngredient onlyDamagedAtMost(int maxDamage) {
      return this.condition(new ConditionDamagedAtMost(maxDamage));
   }

   @ZenCodeType.Method
   default IIngredient anyDamage() {
      return this.condition(ConditionAnyDamage.INSTANCE);
   }

   @ZenCodeType.Method
   default IIngredient onlyIf(String uid, @ZenCodeType.Optional Predicate<IItemStack> function) {
      return this.condition(new ConditionCustom(uid, function));
   }

   @ZenCodeType.Method
   default IIngredient condition(IIngredientCondition condition) {
      this.conditions().add(condition);
      return this;
   }
}
