package com.github.L_Ender.cataclysm.entity.etc;

public interface IShieldEntity {
   int getShieldCooldownTime();

   void setShieldCooldownTime(int var1);

   void disableShield(boolean var1);

   boolean isShieldDisabled();
}
