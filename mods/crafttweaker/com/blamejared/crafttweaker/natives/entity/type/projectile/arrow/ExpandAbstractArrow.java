package com.blamejared.crafttweaker.natives.entity.type.projectile.arrow;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/type/projectile/arrow/Arrow")
@NativeTypeRegistration(value = AbstractArrow.class, zenCodeName = "crafttweaker.api.entity.type.projectile.arrow.Arrow")
public class ExpandAbstractArrow {
   @ZenCodeType.Method
   @ZenCodeType.Setter("soundEvent")
   public static void setSoundEvent(AbstractArrow internal, SoundEvent event) {
      internal.setSoundEvent(event);
   }

   @ZenCodeType.Method
   @ZenCodeType.Setter("baseDamage")
   public static void setBaseDamage(AbstractArrow internal, double damage) {
      internal.setBaseDamage(damage);
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("baseDamage")
   public static double getBaseDamage(AbstractArrow internal) {
      return internal.getBaseDamage();
   }

   @ZenCodeType.Method
   @ZenCodeType.Setter("isCritArrow")
   public static void setCritArrow(AbstractArrow internal, boolean crit) {
      internal.setCritArrow(crit);
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("isCritArrow")
   public static boolean isCritArrow(AbstractArrow internal) {
      return internal.isCritArrow();
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("pierceLevel")
   public static byte getPierceLevel(AbstractArrow internal) {
      return internal.getPierceLevel();
   }

   @ZenCodeType.Getter("weaponItem")
   public static IItemStack getWeaponItem(AbstractArrow internal) {
      return IItemStack.of(internal.getWeaponItem());
   }

   @ZenCodeType.Method
   @ZenCodeType.Setter("isNoPhysics")
   public static void setNoPhysics(AbstractArrow internal, boolean noPhysics) {
      internal.setNoPhysics(noPhysics);
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("isNoPhysics")
   public static boolean isNoPhysics(AbstractArrow internal) {
      return internal.isNoPhysics();
   }
}
