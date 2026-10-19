package com.natamus.collective_common_fabric.fakeplayer;

import com.mojang.authlib.GameProfile;
import java.util.Set;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.class_1282;
import net.minecraft.class_1657;
import net.minecraft.class_2535;
import net.minecraft.class_2561;
import net.minecraft.class_2596;
import net.minecraft.class_2598;
import net.minecraft.class_2709;
import net.minecraft.class_2793;
import net.minecraft.class_2797;
import net.minecraft.class_2799;
import net.minecraft.class_2803;
import net.minecraft.class_2805;
import net.minecraft.class_2811;
import net.minecraft.class_2813;
import net.minecraft.class_2815;
import net.minecraft.class_2817;
import net.minecraft.class_2820;
import net.minecraft.class_2824;
import net.minecraft.class_2827;
import net.minecraft.class_2828;
import net.minecraft.class_2833;
import net.minecraft.class_2836;
import net.minecraft.class_2838;
import net.minecraft.class_2840;
import net.minecraft.class_2842;
import net.minecraft.class_2846;
import net.minecraft.class_2848;
import net.minecraft.class_2851;
import net.minecraft.class_2853;
import net.minecraft.class_2855;
import net.minecraft.class_2856;
import net.minecraft.class_2859;
import net.minecraft.class_2863;
import net.minecraft.class_2866;
import net.minecraft.class_2868;
import net.minecraft.class_2870;
import net.minecraft.class_2871;
import net.minecraft.class_2873;
import net.minecraft.class_2875;
import net.minecraft.class_2877;
import net.minecraft.class_2879;
import net.minecraft.class_2884;
import net.minecraft.class_2885;
import net.minecraft.class_2886;
import net.minecraft.class_3218;
import net.minecraft.class_3222;
import net.minecraft.class_3244;
import net.minecraft.class_3445;
import net.minecraft.class_3753;
import net.minecraft.class_4210;
import net.minecraft.class_4211;
import net.minecraft.class_5194;
import net.minecraft.class_5427;
import net.minecraft.class_7471;
import net.minecraft.class_7472;
import net.minecraft.class_7640;
import net.minecraft.class_7648;
import net.minecraft.class_7861;
import net.minecraft.class_8791;
import net.minecraft.class_8792;
import net.minecraft.class_2556.class_7602;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FakePlayer extends class_3222 {
   MinecraftServer minecraftServer = null;

   public FakePlayer(class_3218 level, GameProfile name) {
      super(level.method_8503(), level, name, class_8791.method_53821());
      this.minecraftServer = level.method_8503();
   }

   public void method_7353(@NotNull class_2561 chatComponent, boolean actionBar) {
   }

   public void method_7342(@NotNull class_3445 stat, int amount) {
   }

   public boolean method_5679(@NotNull class_1282 source) {
      return true;
   }

   public boolean method_7256(@NotNull class_1657 player) {
      return false;
   }

   public void method_6078(@NotNull class_1282 source) {
   }

   public void method_5773() {
   }

   public void method_14213(@NotNull class_8791 p_301998_) {
   }

   @Nullable
   public MinecraftServer method_5682() {
      return this.minecraftServer;
   }

   @ParametersAreNonnullByDefault
   private static class FakePlayerNetHandler extends class_3244 {
      private static final class_2535 DUMMY_CONNECTION = new class_2535(class_2598.field_11942);

      public FakePlayerNetHandler(MinecraftServer server, class_3222 player) {
         super(server, DUMMY_CONNECTION, player, class_8792.method_53824(player.method_7334(), false));
      }

      public void method_18784() {
      }

      public void method_14372() {
      }

      public void method_52396(class_2561 message) {
      }

      public void method_12067(class_2851 packet) {
      }

      public void method_12078(class_2833 packet) {
      }

      public void method_12050(class_2793 packet) {
      }

      public void method_12047(class_2853 packet) {
      }

      public void method_30303(class_5427 packet) {
      }

      public void method_12058(class_2859 packet) {
      }

      public void method_12059(class_2805 packet) {
      }

      public void method_12077(class_2870 packet) {
      }

      public void method_12049(class_2871 packet) {
      }

      public void method_12084(class_2838 packet) {
      }

      public void method_12060(class_2855 packet) {
      }

      public void method_12057(class_2866 packet) {
      }

      public void method_12051(class_2875 packet) {
      }

      public void method_16383(class_3753 packet) {
      }

      public void method_27273(class_5194 packet) {
      }

      public void method_12080(class_2863 packet) {
      }

      public void method_12053(class_2820 packet) {
      }

      public void method_12063(class_2828 packet) {
      }

      public void method_14363(double x, double y, double z, float yaw, float pitch) {
      }

      public void method_12066(class_2846 packet) {
      }

      public void method_12046(class_2885 packet) {
      }

      public void method_12065(class_2886 packet) {
      }

      public void method_12073(class_2884 packet) {
      }

      public void method_52395(class_2856 p_295695_) {
      }

      public void method_12064(class_2836 packet) {
      }

      public void method_14364(class_2596<?> packet) {
      }

      public void method_52391(class_2596<?> packet, @Nullable class_7648 sendListener) {
      }

      public void method_12056(class_2868 packet) {
      }

      public void method_12048(class_2797 packet) {
      }

      public void method_12052(class_2879 packet) {
      }

      public void method_12045(class_2848 packet) {
      }

      public void method_12062(class_2824 packet) {
      }

      public void method_12068(class_2799 packet) {
      }

      public void method_12054(class_2815 packet) {
      }

      public void method_12076(class_2813 packet) {
      }

      public void method_12061(class_2840 packet) {
      }

      public void method_12055(class_2811 packet) {
      }

      public void method_12070(class_2873 packet) {
      }

      public void method_12071(class_2877 packet) {
      }

      public void method_52393(class_2827 p_294627_) {
      }

      public void method_52392(class_2817 p_294276_) {
      }

      public void method_12069(class_2803 p_301979_) {
      }

      public void method_12083(class_2842 packet) {
      }

      public void method_19475(class_4210 packet) {
      }

      public void method_19476(class_4211 packet) {
      }

      public void method_14360(double x, double y, double z, float yaw, float pitch, Set<class_2709> relativeSet) {
      }

      public void method_41255(int sequence) {
      }

      public void method_43667(class_7472 packet) {
      }

      public void method_44898(class_7640 packet) {
      }

      public void method_44897(class_7471 message) {
      }

      public void method_45170(class_7471 message, class_7602 boundChatType) {
      }

      public void method_45168(class_2561 content, class_7602 boundChatType) {
      }

      public void method_46367(class_7861 packet) {
      }
   }
}
