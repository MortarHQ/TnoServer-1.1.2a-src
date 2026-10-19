package com.tno_tensura_extras.entity;

enum DiabloAttackAnimation {
   SLASH_RIGHT("attack_slash_right", 16, 0.8, 7),
   SLASH_LEFT("attack_slash_left", 18, 1.0, 8),
   CROSS("attack_cross", 22, 1.3, 10),
   THRUST("attack_thrust", 19, 1.1, 8),
   UPPERCUT("attack_uppercut", 21, 1.2, 9),
   HEAVY("attack_heavy", 30, 1.6, 14),
   COMBO("attack_combo", 36, 0.6, 7, 14, 22);

   final String animation;
   final int duration;
   final double damageMultiplier;
   final int[] hitTicks;

   DiabloAttackAnimation(String animation, int duration, double damageMultiplier, int... hitTicks) {
      this.animation = animation;
      this.duration = duration;
      this.damageMultiplier = damageMultiplier;
      this.hitTicks = hitTicks;
   }

   boolean hitsAt(int tick) {
      for (int hit : this.hitTicks) {
         if (hit == tick) {
            return true;
         }
      }

      return false;
   }
}
