package com.blamejared.crafttweaker.natives.entity.effect;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.blamejared.crafttweaker_annotations.annotations.TaggableElement;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/effect/MobEffect")
@NativeTypeRegistration(value = MobEffect.class, zenCodeName = "crafttweaker.api.entity.effect.MobEffect")
@TaggableElement("minecraft:mob_effect")
public class ExpandMobEffect {
   @ZenCodeType.Method
   public static void applyEffectTick(MobEffect internal, LivingEntity entity, int amplifier) {
      internal.applyEffectTick(entity, amplifier);
   }

   @ZenCodeType.Method
   public static void applyInstantenousEffect(
      MobEffect internal, @Nullable Entity source, @Nullable Entity indirectSource, LivingEntity target, int amplifier, double effectiveness
   ) {
      internal.applyInstantenousEffect(source, indirectSource, target, amplifier, effectiveness);
   }

   @ZenCodeType.Method
   public static boolean shouldApplyEffectTickThisTick(MobEffect internal, int duration, int amplifier) {
      return internal.shouldApplyEffectTickThisTick(duration, amplifier);
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("instantenous")
   public static boolean isInstantenous(MobEffect internal) {
      return internal.isInstantenous();
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("descriptionId")
   public static String getDescriptionId(MobEffect internal) {
      return internal.getDescriptionId();
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("displayName")
   public static Component getDisplayName(MobEffect internal) {
      return internal.getDisplayName();
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("category")
   public static MobEffectCategory getCategory(MobEffect internal) {
      return internal.getCategory();
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("color")
   public static int getColor(MobEffect internal) {
      return internal.getColor();
   }

   @ZenCodeType.Method
   public static MobEffect addAttributeModifier(MobEffect internal, Attribute attribute, ResourceLocation id, double value, Operation operation) {
      return internal.addAttributeModifier(Services.REGISTRY.holderOrThrow(Registries.ATTRIBUTE, attribute), id, value, operation);
   }

   @ZenCodeType.Method
   public static Map<Attribute, AttributeModifier> getAttributeModifiers(MobEffect internal, int amplifier) {
      Map<Attribute, AttributeModifier> attributes = new HashMap<>();
      internal.createModifiers(amplifier, (attributeHolder, attributeModifier) -> attributes.put((Attribute)attributeHolder.value(), attributeModifier));
      return attributes;
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("beneficial")
   public static boolean isBeneficial(MobEffect internal) {
      return internal.isBeneficial();
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("registryName")
   public static ResourceLocation getRegistryName(MobEffect internal) {
      return BuiltInRegistries.MOB_EFFECT.getKey(internal);
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("commandString")
   public static String getCommandString(MobEffect internal) {
      return "<mobeffect:" + BuiltInRegistries.MOB_EFFECT.getKey(internal) + ">";
   }
}
