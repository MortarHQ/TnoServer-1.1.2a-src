package com.nododiiiii.ponderer.ui.catnip;

import javax.annotation.Nullable;
import net.createmod.catnip.config.ui.ConfigScreenList;

public final class ListScrollPreserver {
   private double rememberedScroll;

   public double capture(@Nullable ConfigScreenList list) {
      this.rememberedScroll = current(list);
      return this.rememberedScroll;
   }

   public void remember(double scroll) {
      this.rememberedScroll = Math.max(0.0, scroll);
   }

   public double remembered() {
      return this.rememberedScroll;
   }

   public void restoreRemembered(@Nullable ConfigScreenList list) {
      restore(list, this.rememberedScroll);
   }

   public static double current(@Nullable ConfigScreenList list) {
      return list != null ? list.getScrollAmount() : 0.0;
   }

   public static void restore(@Nullable ConfigScreenList list, double scroll) {
      if (list != null) {
         list.setScrollAmount(Math.max(0.0, scroll));
      }
   }

   public static void restoreAfterSearch(@Nullable ConfigScreenList list, @Nullable Double preservedScroll, @Nullable String searchQuery) {
      if (preservedScroll != null && (searchQuery == null || searchQuery.isEmpty())) {
         restore(list, preservedScroll);
      }
   }
}
