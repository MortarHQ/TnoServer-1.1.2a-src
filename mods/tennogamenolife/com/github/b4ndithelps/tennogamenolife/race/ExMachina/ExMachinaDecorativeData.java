package com.github.b4ndithelps.tennogamenolife.race.ExMachina;

public class ExMachinaDecorativeData {
   private boolean hasWeapon = false;
   private boolean previousHasWeapon = false;
   private boolean isCancelAnimationPlaying = false;
   private int weaponAnimationTimer = 0;
   private int cancelAnimationTimer = 0;
   private boolean decorativeEnabled = true;

   public boolean hasWeapon() {
      return this.hasWeapon;
   }

   public void setHasWeapon(boolean hasWeapon) {
      this.previousHasWeapon = this.hasWeapon;
      this.hasWeapon = hasWeapon;
   }

   public boolean previousHasWeapon() {
      return this.previousHasWeapon;
   }

   public boolean isCancelAnimationPlaying() {
      return this.isCancelAnimationPlaying;
   }

   public void setCancelAnimationPlaying(boolean cancelAnimationPlaying) {
      this.isCancelAnimationPlaying = cancelAnimationPlaying;
   }

   public int getWeaponAnimationTimer() {
      return this.weaponAnimationTimer;
   }

   public void setWeaponAnimationTimer(int weaponAnimationTimer) {
      this.weaponAnimationTimer = weaponAnimationTimer;
   }

   public void incrementWeaponAnimationTimer() {
      this.weaponAnimationTimer++;
   }

   public int getCancelAnimationTimer() {
      return this.cancelAnimationTimer;
   }

   public void setCancelAnimationTimer(int cancelAnimationTimer) {
      this.cancelAnimationTimer = cancelAnimationTimer;
   }

   public void incrementCancelAnimationTimer() {
      this.cancelAnimationTimer++;
   }

   public boolean isDecorativeEnabled() {
      return this.decorativeEnabled;
   }

   public void setDecorativeEnabled(boolean decorativeEnabled) {
      this.decorativeEnabled = decorativeEnabled;
   }

   public void resetAnimations() {
      this.isCancelAnimationPlaying = false;
      this.weaponAnimationTimer = 0;
      this.cancelAnimationTimer = 0;
   }

   public void reset() {
      this.hasWeapon = false;
      this.previousHasWeapon = false;
      this.resetAnimations();
      this.decorativeEnabled = true;
   }

   public String getAnimationStateDescription() {
      if (this.isCancelAnimationPlaying) {
         return "CANCEL_WEAPON (Timer: " + this.cancelAnimationTimer + ")";
      } else {
         return this.hasWeapon ? "WEAPON (Timer: " + this.weaponAnimationTimer + ")" : "IDLE";
      }
   }

   @Override
   public String toString() {
      return String.format(
         "ExMachinaDecorativeData{hasWeapon=%s, previousHasWeapon=%s, isCancelAnimationPlaying=%s, weaponAnimationTimer=%d, cancelAnimationTimer=%d, decorativeEnabled=%s}",
         this.hasWeapon,
         this.previousHasWeapon,
         this.isCancelAnimationPlaying,
         this.weaponAnimationTimer,
         this.cancelAnimationTimer,
         this.decorativeEnabled
      );
   }
}
