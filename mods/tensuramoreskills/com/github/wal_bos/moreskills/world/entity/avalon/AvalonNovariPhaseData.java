package com.github.wal_bos.moreskills.world.entity.avalon;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.BossEvent.BossBarColor;

public final class AvalonNovariPhaseData {
   private AvalonNovariPhaseData() {
   }

   public static int maxPhase() {
      return 5;
   }

   public static Component name(int phase) {
      return switch (phase) {
         case 1 -> Component.literal("Avalon, The Grand Magus").withStyle(ChatFormatting.AQUA);
         case 2 -> Component.literal("Avalon, The Faltering Seal").withStyle(ChatFormatting.DARK_AQUA);
         case 3 -> Component.literal("Novari of Desire").withStyle(ChatFormatting.GOLD);
         case 4 -> Component.literal("Novari, The Shattered Seal").withStyle(ChatFormatting.YELLOW);
         case 5 -> Component.literal("Novari, Desire of Endlessness").withStyle(ChatFormatting.LIGHT_PURPLE);
         default -> Component.literal("Avalon");
      };
   }

   public static Component startQuote(int phase) {
      return switch (phase) {
         case 1 -> Component.literal("<Avalon> Turn back.. This burden is for me alone... If you step forward.. I will have no choice but to fight back.")
            .withStyle(ChatFormatting.GRAY);
         case 2 -> Component.literal(
               "<Avalon> It is clawing its way to the surface! Forgive me... I can no longer contain the power of Oblivion as well as before, run while you still can."
            )
            .withStyle(ChatFormatting.DARK_PURPLE);
         case 3 -> Component.literal(
               "<Novari> Ah, finally... a chance to stretch these limbs. Go on, then. Strike me. Give me everything you have. I promise to give it right back."
            )
            .withStyle(ChatFormatting.GOLD);
         case 4 -> Component.literal("<Novari> The world's phenomena belong to me! Your magic, your unique talents, your very breath... I claim it all.")
            .withStyle(ChatFormatting.YELLOW);
         case 5 -> Component.literal(
               "<Novari> Feast your eyes on the void! Everything you love, everything you are, it will all be digested into my eternal eternity! Die!"
            )
            .withStyle(ChatFormatting.LIGHT_PURPLE);
         default -> Component.empty();
      };
   }

   public static Component endQuote(int phase) {
      return switch (phase) {
         case 1 -> Component.literal("<Avalon> My focus.. it is shattering. The seal is slipping! I cannot.. you must strike harder, or we are all doomed!")
            .withStyle(ChatFormatting.AQUA);
         case 2 -> Component.literal("<Avalon> I lost... Avalon is no more. The world.. belongs to their Endless Desire...")
            .withStyle(ChatFormatting.DARK_PURPLE);
         case 3 -> Component.literal(
               "<Novari> Is that the absolute peak of your strength? How pathetic. Let me show you what happens when the seal truly shatters!"
            )
            .withStyle(ChatFormatting.GOLD);
         case 4 -> Component.literal(
               "<Novari> No... NO! I will not be contained again! If I cannot possess this world, I will reduce it to absolute nothingness!"
            )
            .withStyle(ChatFormatting.RED);
         case 5 -> Component.literal(
               "<Novari> Buried... again? In the dark... by a weakling like you... No... Avalon... do not celebrate... I am... your shadow... I will... return..."
            )
            .withStyle(ChatFormatting.DARK_GRAY);
         default -> Component.empty();
      };
   }

   public static BossBarColor barColor(int phase) {
      return switch (phase) {
         case 1 -> BossBarColor.BLUE;
         case 2 -> BossBarColor.PURPLE;
         case 3 -> BossBarColor.YELLOW;
         case 4 -> BossBarColor.WHITE;
         case 5 -> BossBarColor.RED;
         default -> BossBarColor.WHITE;
      };
   }

   public static double health(int phase) {
      return switch (phase) {
         case 1 -> 15000.0;
         case 2 -> 25000.0;
         case 3 -> 35000.0;
         case 4 -> 55000.0;
         case 5 -> 100000.0;
         default -> 15000.0;
      };
   }

   public static double spiritualHealth(int phase) {
      return switch (phase) {
         case 1 -> 10000.0;
         case 2 -> 5000.0;
         case 3 -> 55000.0;
         case 4 -> 65000.0;
         case 5 -> 165000.0;
         default -> 10000.0;
      };
   }

   public static double armor(int phase) {
      return switch (phase) {
         case 1 -> 50.0;
         case 2 -> 40.0;
         case 3 -> 40.0;
         case 4, 5 -> 255.0;
         default -> 50.0;
      };
   }

   public static double attack(int phase) {
      return switch (phase) {
         case 1 -> 30.0;
         case 2 -> 45.0;
         case 3 -> 90.0;
         case 4 -> 120.0;
         case 5 -> 150.0;
         default -> 30.0;
      };
   }

   public static double minEp(int phase) {
      return switch (phase) {
         case 1 -> 5000000.0;
         case 2 -> 7500000.0;
         case 3 -> 1.75E7;
         case 4 -> 2.75E7;
         case 5 -> 3.75E7;
         default -> 5000000.0;
      };
   }

   public static double maxEp(int phase) {
      return switch (phase) {
         case 1 -> 7500000.0;
         case 2 -> 1.0E7;
         case 3 -> 2.0E7;
         case 4 -> 3.0E7;
         case 5 -> 4.0E7;
         default -> 7500000.0;
      };
   }
}
