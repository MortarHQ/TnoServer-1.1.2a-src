package com.blamejared.crafttweaker.natives.loot.param;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootParams.Builder;
import net.minecraft.world.level.storage.loot.LootParams.DynamicDrop;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/LootParamsBuilder")
@NativeTypeRegistration(value = Builder.class, zenCodeName = "crafttweaker.api.loot.LootParamsBuilder")
public final class ExpandLootParamsBuilder {
   @ZenCodeType.StaticExpansionMethod
   public static Builder create(ServerLevel level) {
      return new Builder(level);
   }

   @ZenCodeType.Getter
   public static ServerLevel getLevel(Builder internal) {
      return internal.getLevel();
   }

   @ZenCodeType.Method
   public static <T> Builder withParameter(Builder internal, Class<T> tClass, LootContextParam<T> param, T value) {
      return internal.withParameter(param, value);
   }

   @ZenCodeType.Method
   public static <T> Builder withOptionalParameter(Builder internal, Class<T> tClass, LootContextParam<T> param, @ZenCodeType.Nullable T value) {
      return internal.withOptionalParameter(param, value);
   }

   @ZenCodeType.Method
   public static <T> T getParameter(Builder internal, Class<T> tClass, LootContextParam<T> param) {
      return (T)internal.getParameter(param);
   }

   @ZenCodeType.Method
   public static <T> @ZenCodeType.Nullable T getOptionalParameter(Builder internal, Class<T> tClass, LootContextParam<T> param) {
      return (T)internal.getOptionalParameter(param);
   }

   @ZenCodeType.Method
   public static Builder withDynamicDrop(Builder internal, ResourceLocation key, DynamicDrop drop) {
      return internal.withDynamicDrop(key, drop);
   }

   @ZenCodeType.Method
   public static Builder withLuck(Builder internal, float luck) {
      return internal.withLuck(luck);
   }

   @ZenCodeType.Method
   public static LootParams build(Builder internal, LootContextParamSet params) {
      return internal.create(params);
   }
}
