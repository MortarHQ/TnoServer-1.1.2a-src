package com.blamejared.crafttweaker.api.item;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.component.ComponentAccess;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.IntData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.op.IDataOps;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.IIngredientWithAmount;
import com.blamejared.crafttweaker.api.ingredient.condition.IIngredientCondition;
import com.blamejared.crafttweaker.api.ingredient.condition.IngredientConditions;
import com.blamejared.crafttweaker.api.ingredient.transformer.IIngredientTransformer;
import com.blamejared.crafttweaker.api.ingredient.transformer.IngredientTransformers;
import com.blamejared.crafttweaker.api.ingredient.vanilla.type.IngredientIItemStack;
import com.blamejared.crafttweaker.api.util.ItemStackUtil;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.item.IItemStack")
@Document("vanilla/api/item/IItemStack")
public interface IItemStack extends IIngredient, IIngredientWithAmount, DataComponentHolder, ComponentAccess<IItemStack> {
   ResourceLocation INGREDIENT_ID = CraftTweakerConstants.rl("iitemstack");
   Codec<IItemStack> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            ItemStack.CODEC.fieldOf("item").forGetter(IItemStack::getInternal),
            Codec.BOOL.fieldOf("mutable").forGetter(IItemStack::isMutable),
            IngredientConditions.CODEC.optionalFieldOf("conditions", IngredientConditions.EMPTY).forGetter(IIngredient::conditions),
            IngredientTransformers.CODEC.optionalFieldOf("transformers", IngredientTransformers.EMPTY).forGetter(IIngredient::transformers)
         )
         .apply(instance, IItemStack::of)
   );
   StreamCodec<RegistryFriendlyByteBuf, IItemStack> STREAM_CODEC = StreamCodec.composite(
      ItemStack.STREAM_CODEC,
      IItemStack::getInternal,
      ByteBufCodecs.BOOL,
      IItemStack::isMutable,
      IngredientConditions.STREAM_CODEC,
      IIngredient::conditions,
      IngredientTransformers.STREAM_CODEC,
      IIngredient::transformers,
      IItemStack::of
   );
   @ZenCodeType.Field
   String CRAFTTWEAKER_DATA_KEY = "CraftTweakerData";
   @ZenCodeType.Field
   ResourceLocation BASE_ATTACK_DAMAGE_ID = Item.BASE_ATTACK_DAMAGE_ID;
   @ZenCodeType.Field
   ResourceLocation BASE_ATTACK_SPEED_ID = Item.BASE_ATTACK_SPEED_ID;

   static IItemStack empty() {
      return IItemStackConstants.EMPTY_STACK.get();
   }

   static IItemStack of(ItemLike item) {
      return of(new ItemStack(item));
   }

   static IItemStack of(ItemStack stack) {
      return Services.PLATFORM.createItemStack(stack, IngredientConditions.EMPTY, IngredientTransformers.EMPTY);
   }

   static IItemStack of(ItemStack stack, IngredientConditions conditions, IngredientTransformers transformers) {
      return Services.PLATFORM.createItemStack(stack, conditions, transformers);
   }

   static IItemStack of(ItemStack stack, boolean mutable) {
      return mutable ? ofMutable(stack) : of(stack);
   }

   static IItemStack ofMutable(ItemStack stack) {
      return Services.PLATFORM.createItemStackMutable(stack, IngredientConditions.EMPTY, IngredientTransformers.EMPTY);
   }

   static IItemStack ofMutable(ItemStack stack, IngredientConditions conditions, IngredientTransformers transformers) {
      return Services.PLATFORM.createItemStackMutable(stack, conditions, transformers);
   }

   static IItemStack of(ItemStack stack, boolean mutable, IngredientConditions conditions, IngredientTransformers transformers) {
      return mutable ? ofMutable(stack, conditions, transformers) : of(stack, conditions, transformers);
   }

   @ZenCodeType.Method
   IItemStack copy();

   @ZenCodeType.Getter("registryName")
   default ResourceLocation getRegistryName() {
      return BuiltInRegistries.ITEM.getKey(this.getInternal().getItem());
   }

   @ZenCodeType.Getter("owner")
   default String getOwner() {
      return this.getRegistryName().getNamespace();
   }

   @Override
   default boolean isEmpty() {
      return this.getInternal().isEmpty();
   }

   @ZenCodeType.Getter("prototype")
   default DataComponentMap getPrototype() {
      return this.getInternal().getPrototype();
   }

   @ZenCodeType.Getter("componentsPatch")
   default DataComponentPatch getComponentsPatch() {
      return this.getInternal().getComponentsPatch();
   }

   @ZenCodeType.Method
   default <T> IItemStack with(DataComponentType<T> type, @ZenCodeType.Nullable T value) {
      return this.modify(itemStack -> itemStack.set(type, value));
   }

   @ZenCodeType.Method
   default <T> IItemStack without(DataComponentType<T> type) {
      return this.remove(type);
   }

   @ZenCodeType.Method
   default IItemStack withoutComponents() {
      return this.modify(itemStack -> {
         for (TypedDataComponent<?> component : itemStack.getComponents()) {
            itemStack.remove(component.type());
         }
      });
   }

   @ZenCodeType.Method
   default IItemStack withJsonComponent(DataComponentType type, @ZenCodeType.Nullable IData value) {
      return this.modify(itemStack -> {
         if (value == null) {
            itemStack.remove(type);
         } else {
            Codec<?> codec = type.codecOrThrow();
            DataResult<? extends Pair<?, IData>> decode = codec.decode(IDataOps.INSTANCE.withTagAddingRegistryAccess(), value);
            itemStack.set(type, ((Pair)decode.getOrThrow()).getFirst());
         }
      });
   }

   @ZenCodeType.Method
   default IItemStack withJsonComponents(IData value) {
      return this.modify(itemStack -> {
         DataResult<Pair<DataComponentPatch, IData>> decoded = DataComponentPatch.CODEC.decode(IDataOps.INSTANCE.withTagAddingRegistryAccess(), value);
         Pair<DataComponentPatch, IData> pair = (Pair<DataComponentPatch, IData>)decoded.getOrThrow();
         itemStack.applyComponents((DataComponentPatch)pair.getFirst());
      });
   }

   @ZenCodeType.Method
   default <T, U> IItemStack update(DataComponentType<T> type, T defaultValue, U data, BiFunction<T, U, T> operator) {
      return this.modify(itemStack -> itemStack.update(type, defaultValue, data, operator));
   }

   @ZenCodeType.Method
   default <T> IItemStack update(DataComponentType<T> type, T defaultValue, UnaryOperator<T> operator) {
      return this.modify(itemStack -> itemStack.update(type, defaultValue, operator));
   }

   @ZenCodeType.Method
   default <T> IItemStack remove(DataComponentType<T> type) {
      return this.modify(itemStack -> itemStack.remove(type));
   }

   @ZenCodeType.Method
   default IItemStack applyComponents(DataComponentMap map) {
      return this.modify(itemStack -> itemStack.applyComponents(map));
   }

   @ZenCodeType.Method
   default IItemStack applyComponents(DataComponentPatch patch) {
      return this.modify(itemStack -> itemStack.applyComponents(patch));
   }

   @ZenCodeType.Method
   default IItemStack applyComponentsAndValidate(DataComponentPatch patch) {
      return this.modify(itemStack -> itemStack.applyComponentsAndValidate(patch));
   }

   @ZenCodeType.Getter("displayName")
   default Component getDisplayName() {
      return this.getInternal().getDisplayName();
   }

   @ZenCodeType.Getter("hoverName")
   default Component getHoverName() {
      return this.getInternal().getHoverName();
   }

   @ZenCodeType.Getter("hasFoil")
   default boolean hasFoil() {
      return this.getInternal().hasFoil();
   }

   @ZenCodeType.Getter("isEnchantable")
   default boolean isEnchantable() {
      return this.getInternal().isEnchantable();
   }

   @ZenCodeType.Getter("amount")
   @Override
   default int amount() {
      return this.getInternal().getCount();
   }

   @ZenCodeType.Operator(ZenCodeType.OperatorType.MUL)
   default IItemStack withAmount(int amount) {
      return this.modify(itemStack -> itemStack.setCount(amount));
   }

   @ZenCodeType.Method
   default IItemStack grow(@ZenCodeType.OptionalInt(1) int amount) {
      return this.withAmount(this.amount() + amount);
   }

   @ZenCodeType.Method
   default IItemStack shrink(@ZenCodeType.OptionalInt(1) int amount) {
      return this.withAmount(this.amount() - amount);
   }

   @ZenCodeType.Getter("stackable")
   default boolean isStackable() {
      return this.getInternal().isStackable();
   }

   @ZenCodeType.Method
   default Map<Attribute, List<AttributeModifier>> getAttributes(EquipmentSlot slotType) {
      Map<Attribute, List<AttributeModifier>> ret = new HashMap<>();
      this.getInternal()
         .forEachModifier(
            slotType,
            (attributeHolder, attributeModifier) -> ret.computeIfAbsent((Attribute)attributeHolder.value(), attribute -> new ArrayList<>())
               .add(attributeModifier)
         );
      return ret;
   }

   @ZenCodeType.Getter("damageableItem")
   default boolean isDamageableItem() {
      return this.getInternal().isDamageableItem();
   }

   @ZenCodeType.Getter("damaged")
   default boolean isDamaged() {
      return this.getInternal().isDamaged();
   }

   @ZenCodeType.Getter("maxDamage")
   @Override
   default int getMaxDamage() {
      return this.getInternal().getMaxDamage();
   }

   @ZenCodeType.Getter("descriptionId")
   default String getDescriptionId() {
      return this.getInternal().getDescriptionId();
   }

   @Override
   default boolean matches(IItemStack stack) {
      return ItemStackUtil.areStacksTheSame(this.getInternal(), stack.getInternal(), this.conditions()) && this.conditions().test(stack);
   }

   @Override
   default String getCommandString() {
      return ItemStackUtil.getCommandString(this.getInternal(), this.isMutable());
   }

   @ZenCodeType.Method
   default int getUseDuration(LivingEntity entity) {
      return this.getInternal().getUseDuration(entity);
   }

   @ZenCodeType.Getter("useOnRelease")
   default boolean useOnRelease() {
      return this.getInternal().useOnRelease();
   }

   @ZenCodeType.Getter("burnTime")
   default int getBurnTime() {
      return Services.EVENT.getBurnTime(this);
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("potPatternId")
   default @ZenCodeType.Nullable ResourceKey<DecoratedPotPattern> getPotPatternId() {
      return DecoratedPotPatterns.getPatternFromItem(this.getInternal().getItem());
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("potPattern")
   default @ZenCodeType.Nullable DecoratedPotPattern getPotPattern() {
      return (DecoratedPotPattern)BuiltInRegistries.DECORATED_POT_PATTERN.get(this.getPotPatternId());
   }

   @ZenCodeType.Method
   @ZenCodeType.Operator(ZenCodeType.OperatorType.MOD)
   default Percentaged<IItemStack> percent(double percentage) {
      return new Percentaged<>(this, percentage / 100.0, iItemStack -> iItemStack.getCommandString() + " % " + percentage);
   }

   @ZenCodeType.Caster(implicit = true)
   default Percentaged<IItemStack> asWeightedItemStack() {
      return this.percent(100.0);
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("definition")
   @ZenCodeType.Caster(implicit = true)
   default Item getDefinition() {
      return this.getInternal().getItem();
   }

   @ZenCodeType.Method
   @ZenCodeType.Caster(implicit = true)
   default ItemLike asItemLike() {
      return this.getInternal().getItem();
   }

   @ZenCodeType.Method
   IItemStack asMutable();

   @ZenCodeType.Method
   IItemStack asImmutable();

   @ZenCodeType.Method
   @ZenCodeType.Getter("isImmutable")
   boolean isImmutable();

   @ZenCodeType.Method
   @ZenCodeType.Getter("isMutable")
   default boolean isMutable() {
      return !this.isImmutable();
   }

   @ZenCodeType.Method
   @ZenCodeType.Caster(implicit = true)
   ItemStack getInternal();

   @Override
   default Ingredient asVanillaIngredient() {
      return this.getInternal().isEmpty() ? Ingredient.EMPTY : IngredientIItemStack.ingredient(this);
   }

   @ZenCodeType.Method
   default ItemStack getImmutableInternal() {
      return this.getInternal().copy();
   }

   @ZenCodeType.Method
   @ZenCodeType.Caster(implicit = true)
   @Override
   default IIngredientWithAmount asIIngredientWithAmount() {
      return this;
   }

   default IItemStack ingredient() {
      return this;
   }

   @Override
   default IData asIData() {
      IData data = IIngredient.super.asIData();
      assert data instanceof MapData;
      data.put("count", new IntData(this.amount()));
      return data;
   }

   @Override
   default IIngredient transform(IIngredientTransformer transformer) {
      return this.modifyThis(iItemStack -> iItemStack.transformers().add(transformer));
   }

   @Override
   default IIngredient condition(IIngredientCondition condition) {
      return this.modifyThis(iItemStack -> iItemStack.conditions().add(condition));
   }

   IItemStack modify(Consumer<ItemStack> var1);

   IItemStack modifyThis(Consumer<IItemStack> var1);

   @NotNull
   default DataComponentMap getComponents() {
      return this.getInternal().getComponents();
   }

   default <T> T get(@NotNull DataComponentType<? extends T> type) {
      return (T)this.getInternal().get(type);
   }

   @NotNull
   default <T> T getOrDefault(@NotNull DataComponentType<? extends T> type, @NotNull T value) {
      return (T)this.getInternal().getOrDefault(type, value);
   }

   default boolean has(@NotNull DataComponentType<?> type) {
      return this.getInternal().has(type);
   }

   default <U> IItemStack _without(DataComponentType<U> componentType) {
      return this.without(componentType);
   }

   default <U> IItemStack _with(DataComponentType<U> componentType, @Nullable U value) {
      return this.with(componentType, value);
   }

   @Override
   default <U> U _get(DataComponentType<? extends U> componentType) {
      return this.get(componentType);
   }

   @Override
   default <U> boolean _has(DataComponentType<U> componentType) {
      return this.has(componentType);
   }
}
