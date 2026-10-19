package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.LightPredicate;
import net.minecraft.advancements.critereon.LightPredicate.Builder;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/LightPredicate")
@NativeTypeRegistration(value = LightPredicate.class, zenCodeName = "crafttweaker.api.predicate.LightPredicate")
public final class ExpandLightPredicate {
   @ZenCodeType.StaticExpansionMethod
   public static Builder create() {
      return Builder.light();
   }

   @ZenCodeType.StaticExpansionMethod
   public static Builder create(Ints level) {
      return Builder.light().setComposite(level);
   }

   @ZenCodeType.Method
   public static boolean matches(LightPredicate internal, ServerLevel level, BlockPos pos) {
      return internal.matches(level, pos);
   }
}
