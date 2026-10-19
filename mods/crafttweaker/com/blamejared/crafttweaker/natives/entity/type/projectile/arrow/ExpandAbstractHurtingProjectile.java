package com.blamejared.crafttweaker.natives.entity.type.projectile.arrow;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/type/projectile/AbstractHurtingProjectile")
@NativeTypeRegistration(value = AbstractHurtingProjectile.class, zenCodeName = "crafttweaker.api.entity.type.projectile.AbstractHurtingProjectile")
public class ExpandAbstractHurtingProjectile {
   @ZenCodeType.Getter("accelerationPower")
   public static double getAccelerationPower(AbstractHurtingProjectile internal) {
      return internal.accelerationPower;
   }

   @ZenCodeType.Method
   @ZenCodeType.Setter("accelerationPower")
   public static void setAccelerationPower(AbstractHurtingProjectile internal, double accelerationPower) {
      internal.accelerationPower = accelerationPower;
   }
}
