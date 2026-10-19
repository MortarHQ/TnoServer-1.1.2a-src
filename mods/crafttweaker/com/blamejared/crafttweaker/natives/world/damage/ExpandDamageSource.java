package com.blamejared.crafttweaker.natives.world.damage;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/world/damage/DamageSource")
@NativeTypeRegistration(value = DamageSource.class, zenCodeName = "crafttweaker.api.world.damage.DamageSource")
public class ExpandDamageSource {
   @ZenCodeType.StaticExpansionMethod
   public static DamageSource create(DamageType type, @ZenCodeType.Optional Entity directEntity, @ZenCodeType.Optional Entity causingEntity) {
      return new DamageSource(Services.REGISTRY.holderOrThrow(Registries.DAMAGE_TYPE, type), directEntity, causingEntity);
   }

   @ZenCodeType.StaticExpansionMethod
   public static DamageSource create(DamageType type, Vec3 damageSourcePosition) {
      return new DamageSource(Services.REGISTRY.holderOrThrow(Registries.DAMAGE_TYPE, type), damageSourcePosition);
   }

   @ZenCodeType.Getter("foodExhaustion")
   public static float getFoodExhaustion(DamageSource internal) {
      return internal.getFoodExhaustion();
   }

   @ZenCodeType.Getter("isDirect")
   public static boolean isDirect(DamageSource internal) {
      return internal.isDirect();
   }

   @ZenCodeType.Getter("directEntity")
   public static @ZenCodeType.Nullable Entity getDirectEntity(DamageSource internal) {
      return internal.getDirectEntity();
   }

   @ZenCodeType.Getter("entity")
   public static @ZenCodeType.Nullable Entity getEntity(DamageSource internal) {
      return internal.getEntity();
   }

   @ZenCodeType.Method
   public static Component getLocalizedDeathMessage(DamageSource internal, LivingEntity entity) {
      return internal.getLocalizedDeathMessage(entity);
   }

   @ZenCodeType.Getter("msgId")
   public static String getMsgId(DamageSource internal) {
      return internal.getMsgId();
   }

   @ZenCodeType.Getter("scalesWithDifficulty")
   public static boolean scalesWithDifficulty(DamageSource internal) {
      return internal.scalesWithDifficulty();
   }

   @ZenCodeType.Getter("isCreativePlayer")
   public static boolean isCreativePlayer(DamageSource internal) {
      return internal.isCreativePlayer();
   }

   @ZenCodeType.Getter("sourcePosition")
   public static @ZenCodeType.Nullable Vec3 getSourcePosition(DamageSource internal) {
      return internal.getSourcePosition();
   }

   @ZenCodeType.Getter("sourcePositionRaw")
   public static @ZenCodeType.Nullable Vec3 sourcePositionRaw(DamageSource internal) {
      return internal.sourcePositionRaw();
   }

   @ZenCodeType.Method
   public static boolean isIn(DamageSource internal, KnownTag<DamageType> tag) {
      return internal.is(tag.getTagKey());
   }

   @ZenCodeType.Getter("type")
   public static DamageType type(DamageSource internal) {
      return internal.type();
   }
}
