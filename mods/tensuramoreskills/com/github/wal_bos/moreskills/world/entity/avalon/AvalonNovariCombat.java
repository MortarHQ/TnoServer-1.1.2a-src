package com.github.wal_bos.moreskills.world.entity.avalon;

import com.github.wal_bos.moreskills.world.entity.AvalonNovariEntity;
import net.minecraft.world.entity.LivingEntity;

public final class AvalonNovariCombat {
   public static final int ATTACK_OBLIVION_ORB = 1;
   public static final int ATTACK_SPATIAL_COMPRESSION = 2;
   public static final int ATTACK_ERASURE_RUNE = 3;
   public static final int ATTACK_FIRE_STORM = 4;
   public static final int ATTACK_BLIZZARD = 5;
   public static final int ATTACK_MENTAL_CRUSH = 6;
   public static final int ATTACK_MUDHAND = 7;
   public static final int ATTACK_THUNDER_RAIN = 8;
   public static final int ATTACK_WATER_JAIL = 9;
   public static final int ATTACK_UNSTABLE_OBLIVION = 10;
   public static final int ATTACK_FIRE_BALL = 11;
   public static final int ATTACK_ACID_SHELL = 12;
   public static final int ATTACK_ICE_BREAKER = 13;
   public static final int ATTACK_TORNADO_BLADE = 14;
   public static final int ATTACK_MUD_SPEARS = 15;
   public static final int ATTACK_STONE_SHOT = 16;
   public static final int ATTACK_THUNDER_ORB = 17;
   public static final int ATTACK_REINFORCED_BARRIER = 18;
   public static final int ATTACK_USURPER_BAIT = 30;
   public static final int ATTACK_DECOY_SHIFT = 31;
   public static final int ATTACK_SERRATED_REFLECTION = 32;
   public static final int ATTACK_PHENOMENON_THEFT = 33;
   public static final int ATTACK_VACUUM_AURA = 34;
   public static final int ATTACK_INFINITE_MAW = 35;
   public static final int ATTACK_OBLIVION_WAVE = 36;
   public static final int ATTACK_UNLEASHED_HOARD = 37;

   private AvalonNovariCombat() {
   }

   public static void tickAttack(AvalonNovariEntity boss) {
      int id = boss.getAttackId();
      if (id != 0) {
         if (id < 30) {
            AvalonNovariMagic.tick(boss, boss.getAttackTicks());
         } else {
            NovariBlackWhiteAttacks.tick(boss, boss.getAttackTicks());
         }
      }
   }

   public static void tryStartAttack(AvalonNovariEntity boss) {
      if (boss.getAttackId() == 0) {
         if (boss.getGlobalCooldown() <= 0) {
            LivingEntity target = boss.resolveTarget();
            if (target != null) {
               int next = chooseAttack(boss, target);
               if (next != 0) {
                  boss.setAttack(next);
                  boss.markForcedAttack(next);
               }
            }
         }
      }
   }

   private static int chooseAttack(AvalonNovariEntity boss, LivingEntity target) {
      int forced = boss.nextForcedAttack();
      if (forced != 0) {
         return forced;
      }

      int phase = boss.getPhase();
      int roll = boss.getRandom().nextInt(100);
      if (phase <= 1) {
         if (roll < 12) {
            return 1;
         } else if (roll < 22) {
            return 2;
         } else if (roll < 31) {
            return 3;
         } else if (roll < 38) {
            return 11;
         } else if (roll < 45) {
            return 12;
         } else if (roll < 52) {
            return 13;
         } else if (roll < 59) {
            return 14;
         } else if (roll < 66) {
            return 9;
         } else if (roll < 73) {
            return 15;
         } else if (roll < 80) {
            return 16;
         } else if (roll < 87) {
            return 17;
         } else if (roll < 93) {
            return 18;
         } else if (roll < 96) {
            return 4;
         } else {
            return roll < 98 ? 5 : 6;
         }
      } else if (phase == 2) {
         if (roll < 16) {
            return 10;
         } else if (roll < 26) {
            return 2;
         } else if (roll < 34) {
            return 3;
         } else if (roll < 41) {
            return 11;
         } else if (roll < 48) {
            return 12;
         } else if (roll < 55) {
            return 13;
         } else if (roll < 62) {
            return 14;
         } else if (roll < 69) {
            return 9;
         } else if (roll < 76) {
            return 15;
         } else if (roll < 83) {
            return 16;
         } else if (roll < 90) {
            return 17;
         } else {
            return roll < 95 ? 18 : 8;
         }
      } else if (phase == 3) {
         if (roll < 28) {
            return 30;
         } else if (roll < 48) {
            return 31;
         } else if (roll < 62) {
            return 34;
         } else if (roll < 76) {
            return 33;
         } else {
            return roll < 90 ? 10 : 2;
         }
      } else if (phase == 4) {
         if (roll < 18) {
            return 30;
         } else if (roll < 32) {
            return 31;
         } else if (roll < 48) {
            return 32;
         } else if (roll < 64) {
            return 33;
         } else if (roll < 78) {
            return 34;
         } else {
            return roll < 91 ? 36 : 10;
         }
      } else if (roll < 13) {
         return 30;
      } else if (roll < 25) {
         return 31;
      } else if (roll < 39) {
         return 32;
      } else if (roll < 52) {
         return 33;
      } else if (roll < 65) {
         return 34;
      } else if (roll < 78) {
         return 35;
      } else {
         return roll < 92 ? 36 : 37;
      }
   }

   public static int attackBit(int id) {
      return switch (id) {
         case 1 -> 1;
         case 2 -> 2;
         case 3 -> 4;
         case 4 -> 8;
         case 5 -> 16;
         case 6 -> 32;
         case 7 -> 64;
         case 8 -> 128;
         case 9 -> 256;
         case 10 -> 512;
         case 11 -> 262144;
         case 12 -> 524288;
         case 13 -> 1048576;
         case 14 -> 2097152;
         case 15 -> 4194304;
         case 16 -> 8388608;
         case 17 -> 16777216;
         case 18 -> 33554432;
         default -> 0;
         case 30 -> 1024;
         case 31 -> 2048;
         case 32 -> 4096;
         case 33 -> 8192;
         case 34 -> 16384;
         case 35 -> 32768;
         case 36 -> 65536;
         case 37 -> 131072;
      };
   }
}
