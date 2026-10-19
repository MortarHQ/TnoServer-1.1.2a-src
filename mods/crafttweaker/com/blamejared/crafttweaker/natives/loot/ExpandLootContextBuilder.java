package com.blamejared.crafttweaker.natives.loot;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.mixin.common.access.loot.AccessLootContext;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/LootContextBuilder")
@NativeTypeRegistration(value = Builder.class, zenCodeName = "crafttweaker.api.loot.LootContextBuilder")
public final class ExpandLootContextBuilder {
   @ZenCodeType.StaticExpansionMethod
   public static Builder create(LootParams params) {
      return new Builder(params);
   }

   @ZenCodeType.StaticExpansionMethod
   public static Builder copy(LootContext context) {
      return new Builder(((AccessLootContext)context).crafttweaker$getParams());
   }

   @ZenCodeType.Method
   public static Builder withOptionalRandomSeed(Builder internal, @ZenCodeType.OptionalLong long seed) {
      return internal.withOptionalRandomSeed(seed);
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("level")
   public static ServerLevel getLevel(Builder internal) {
      return internal.getLevel();
   }

   @ZenCodeType.Method
   public static LootContext create(Builder internal, @ZenCodeType.Optional @ZenCodeType.Nullable ResourceLocation key) {
      return internal.create(Optional.ofNullable(key));
   }
}
