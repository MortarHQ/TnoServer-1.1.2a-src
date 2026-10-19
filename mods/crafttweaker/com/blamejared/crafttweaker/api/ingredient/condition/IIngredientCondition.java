package com.blamejared.crafttweaker.api.ingredient.condition;

import com.blamejared.crafttweaker.CraftTweakerRegistries;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.google.common.base.Predicates;
import com.mojang.serialization.Codec;
import java.util.function.Predicate;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.ingredient.condition.IIngredientCondition")
@Document("vanilla/api/ingredient/condition/IIngredientCondition")
public interface IIngredientCondition {
   Codec<IIngredientCondition> CODEC = IIngredientConditionSerializer.CODEC
      .dispatch(IIngredientCondition::getSerializer, IIngredientConditionSerializer::codec);
   StreamCodec<RegistryFriendlyByteBuf, IIngredientCondition> STREAM_CODEC = ByteBufCodecs.registry(CraftTweakerRegistries.Keys.CONDITIONER_SERIALIZER)
      .dispatch(IIngredientCondition::getSerializer, IIngredientConditionSerializer::streamCodec);

   @ZenCodeType.Method
   boolean matches(IItemStack var1);

   @ZenCodeType.Method
   String getCommandString(String var1);

   default Predicate<DataComponentType<?>> componentFilter() {
      return Predicates.alwaysTrue();
   }

   IIngredientConditionSerializer<? extends IIngredientCondition> getSerializer();

   default ResourceLocation getType() {
      return this.getSerializer().getType();
   }
}
