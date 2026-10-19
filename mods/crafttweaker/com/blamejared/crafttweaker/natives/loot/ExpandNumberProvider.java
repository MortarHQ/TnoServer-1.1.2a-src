package com.blamejared.crafttweaker.natives.loot;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.storage.loot.LootContext.EntityTarget;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.ScoreboardValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/NumberProvider")
@NativeTypeRegistration(value = NumberProvider.class, zenCodeName = "crafttweaker.api.loot.NumberProvider")
public final class ExpandNumberProvider {
   @ZenCodeType.StaticExpansionMethod
   public static NumberProvider between(float min, float max) {
      return UniformGenerator.between(min, max);
   }

   @ZenCodeType.StaticExpansionMethod
   public static NumberProvider binomial(int n, int p) {
      return BinomialDistributionGenerator.binomial(n, p);
   }

   @ZenCodeType.StaticExpansionMethod
   public static NumberProvider exactly(float value) {
      return ConstantValue.exactly(value);
   }

   @ZenCodeType.StaticExpansionMethod
   public static NumberProvider scoreboard(EntityTarget target, String score, @ZenCodeType.OptionalFloat(1.0F) float scale) {
      return ScoreboardValue.fromScoreboard(target, score, scale);
   }
}
