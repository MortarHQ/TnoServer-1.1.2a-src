package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.advancements.critereon.MobEffectsPredicate;
import net.minecraft.advancements.critereon.MobEffectsPredicate.MobEffectInstancePredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/MobEffectsPredicate")
@NativeTypeRegistration(value = MobEffectsPredicate.class, zenCodeName = "crafttweaker.api.predicate.MobEffectsPredicate")
public final class ExpandMobEffectsPredicate {
   @ZenCodeType.StaticExpansionMethod
   public static MobEffectsPredicate create(Map<MobEffect, MobEffectInstancePredicate> map) {
      return new MobEffectsPredicate(
         map.entrySet()
            .stream()
            .map(entry -> Map.entry(Services.REGISTRY.holderOrThrow(Registries.MOB_EFFECT, entry.getKey()), entry.getValue()))
            .collect(Collectors.toMap(Entry::getKey, Entry::getValue))
      );
   }

   @ZenCodeType.StaticExpansionMethod
   public static MobEffectsPredicate create(MobEffect effect, MobEffectInstancePredicate predicate) {
      return new MobEffectsPredicate(new LinkedHashMap<>(Map.of(Services.REGISTRY.holderOrThrow(Registries.MOB_EFFECT, effect), predicate)));
   }

   @ZenCodeType.Method
   public static boolean matches(MobEffectsPredicate internal, Entity entity) {
      return internal.matches(entity);
   }
}
