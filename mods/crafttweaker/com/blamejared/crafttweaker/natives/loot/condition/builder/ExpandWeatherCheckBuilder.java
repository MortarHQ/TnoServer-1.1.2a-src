package com.blamejared.crafttweaker.natives.loot.condition.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.storage.loot.predicates.WeatherCheck.Builder;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/condition/builder/WeatherCheckLootConditionBuilder")
@NativeTypeRegistration(value = Builder.class, zenCodeName = "crafttweaker.api.loot.condition.builder.WeatherCheckLootConditionBuilder")
public final class ExpandWeatherCheckBuilder {
   @ZenCodeType.Method
   public static Builder raining(Builder internal, @ZenCodeType.OptionalBoolean(true) Boolean raining) {
      return internal.setRaining(raining);
   }

   @ZenCodeType.Method
   public static Builder thundering(Builder internal, @ZenCodeType.OptionalBoolean(true) Boolean thundering) {
      return internal.setThundering(thundering);
   }
}
