package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import java.util.Optional;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.advancements.critereon.MobEffectsPredicate.MobEffectInstancePredicate;
import net.minecraft.world.effect.MobEffectInstance;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/MobEffectPredicate")
@NativeTypeRegistration(value = MobEffectInstancePredicate.class, zenCodeName = "crafttweaker.api.predicate.MobEffectPredicate")
public final class ExpandMobEffectsPredicateMobEffectInstancePredicate {
   @ZenCodeType.StaticExpansionMethod
   public static MobEffectInstancePredicate any() {
      return new MobEffectInstancePredicate();
   }

   @ZenCodeType.StaticExpansionMethod
   public static MobEffectInstancePredicate create(Ints amplifier, Ints duration, @ZenCodeType.Nullable Boolean ambient, @ZenCodeType.Nullable Boolean visible) {
      return new MobEffectInstancePredicate(amplifier, duration, Optional.ofNullable(ambient), Optional.ofNullable(visible));
   }

   @ZenCodeType.StaticExpansionMethod
   public static MobEffectInstancePredicate create(Ints amplifier, Ints duration, Boolean ambient) {
      return create(amplifier, duration, ambient, null);
   }

   @ZenCodeType.StaticExpansionMethod
   public static MobEffectInstancePredicate create(Ints amplifier, Ints duration) {
      return create(amplifier, duration, null, null);
   }

   @ZenCodeType.StaticExpansionMethod
   public static MobEffectInstancePredicate ambient() {
      return create(Ints.ANY, Ints.ANY, true, null);
   }

   @ZenCodeType.StaticExpansionMethod
   public static MobEffectInstancePredicate amplifier(Ints amplifier) {
      return create(amplifier, Ints.ANY, null, null);
   }

   @ZenCodeType.StaticExpansionMethod
   public static MobEffectInstancePredicate duration(Ints duration) {
      return create(Ints.ANY, duration, null, null);
   }

   @ZenCodeType.Method
   public static boolean matches(MobEffectInstancePredicate internal, @ZenCodeType.Nullable MobEffectInstance instance) {
      return internal.matches(instance);
   }
}
