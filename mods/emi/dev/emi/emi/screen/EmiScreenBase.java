package dev.emi.emi.screen;

import dev.emi.emi.api.widget.Bounds;
import dev.emi.emi.mixin.accessor.HandledScreenAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class EmiScreenBase {
   private final Screen screen;
   private final Bounds bounds;

   private EmiScreenBase(Screen screen, Bounds bounds) {
      this.screen = screen;
      this.bounds = bounds;
   }

   public Screen screen() {
      return this.screen;
   }

   public Bounds bounds() {
      return this.bounds;
   }

   public boolean isEmpty() {
      return this.screen == null;
   }

   public static EmiScreenBase getCurrent() {
      Minecraft client = Minecraft.getInstance();
      return of(client.screen);
   }

   public static EmiScreenBase of(Screen screen) {
      if (screen instanceof AbstractContainerScreen hs) {
         HandledScreenAccessor hsa = (HandledScreenAccessor)hs;
         AbstractContainerMenu sh = hs.getMenu();
         if (sh.slots != null && !sh.slots.isEmpty()) {
            int extra = 0;
            if (hs instanceof RecipeUpdateListener provider && provider.getRecipeBookComponent().isVisible()) {
               extra = 177;
            }

            Bounds bounds = new Bounds(hsa.getX() - extra, hsa.getY(), hsa.getBackgroundWidth() + extra, hsa.getBackgroundHeight());
            return new EmiScreenBase(screen, bounds);
         }
      } else if (screen instanceof RecipeScreen rs) {
         return new EmiScreenBase(rs, rs.getBounds());
      }

      return new EmiScreenBase(null, Bounds.EMPTY);
   }
}
