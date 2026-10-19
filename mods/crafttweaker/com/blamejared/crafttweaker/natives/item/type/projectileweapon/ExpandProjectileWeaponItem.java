package com.blamejared.crafttweaker.natives.item.type.projectileweapon;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import java.util.function.Predicate;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/item/type/projectileweapon/ProjectileWeaponItem")
@NativeTypeRegistration(value = ProjectileWeaponItem.class, zenCodeName = "crafttweaker.api.item.type.projectileweapon.ProjectileWeaponItem")
public class ExpandProjectileWeaponItem {
   @ZenCodeType.StaticExpansionMethod
   public static Predicate<ItemStack> ARROW_ONLY() {
      return ProjectileWeaponItem.ARROW_ONLY;
   }

   @ZenCodeType.StaticExpansionMethod
   public static Predicate<ItemStack> ARROW_OR_FIREWORK() {
      return ProjectileWeaponItem.ARROW_OR_FIREWORK;
   }

   @ZenCodeType.Getter("supportedHeldProjectiles")
   public static Predicate<ItemStack> getSupportedHeldProjectiles(ProjectileWeaponItem internal) {
      return internal.getSupportedHeldProjectiles();
   }

   @ZenCodeType.Getter("allSupportedProjectiles")
   public static Predicate<ItemStack> getAllSupportedProjectiles(ProjectileWeaponItem internal) {
      return internal.getAllSupportedProjectiles();
   }

   @ZenCodeType.Getter("defaultProjectileRange")
   public static int getDefaultProjectileRange(ProjectileWeaponItem internal) {
      return internal.getDefaultProjectileRange();
   }
}
