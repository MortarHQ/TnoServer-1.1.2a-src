package com.tensura_tno.food;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.Nullable;

public class FoodEPConfig {
   @SerializedName("ep")
   @Nullable
   private String ep;
   @SerializedName("mp")
   @Nullable
   private String mp;
   @SerializedName("ap")
   @Nullable
   private String ap;

   public FoodEPConfig(@Nullable String ep, @Nullable String mp, @Nullable String ap) {
      this.ep = ep;
      this.mp = mp;
      this.ap = ap;
   }

   @Nullable
   public String getEp() {
      return this.ep;
   }

   @Nullable
   public String getMp() {
      return this.mp;
   }

   @Nullable
   public String getAp() {
      return this.ap;
   }

   public static boolean isPercent(String value) {
      return value != null && value.trim().endsWith("%");
   }

   public static double resolve(@Nullable String value, double maxRef) {
      if (value != null && !value.isBlank()) {
         value = value.trim();

         try {
            if (isPercent(value)) {
               double pct = Double.parseDouble(value.substring(0, value.length() - 1));
               return maxRef * (pct / 100.0);
            } else {
               return Double.parseDouble(value);
            }
         } catch (NumberFormatException e) {
            return 0.0;
         }
      } else {
         return 0.0;
      }
   }
}
