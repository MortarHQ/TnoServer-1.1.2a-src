package com.nododiiiii.ponderer.compat.jei;

import com.nododiiiii.ponderer.platform.PondererServices;
import com.nododiiiii.ponderer.ui.AbstractStepEditorScreen;
import com.nododiiiii.ponderer.ui.IdFieldMode;
import com.nododiiiii.ponderer.ui.JeiAwareScreen;
import java.util.List;
import javax.annotation.Nullable;
import net.createmod.catnip.gui.element.ScreenElement;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.level.material.Fluid;

public final class JeiCompat {
   private static boolean checked = false;
   private static boolean available = false;

   private JeiCompat() {
   }

   public static boolean isAvailable() {
      if (!checked) {
         checked = true;
         available = PondererServices.PLATFORM.isModLoaded("jei");
      }

      return available;
   }

   public static void setActiveEditor(AbstractStepEditorScreen screen, IdFieldMode mode) {
      if (isAvailable()) {
         PondererJeiPlugin.setActiveEditor(screen, mode);
      }
   }

   public static void setActiveScreen(JeiAwareScreen screen, IdFieldMode mode) {
      if (isAvailable()) {
         PondererJeiPlugin.setActiveScreen(screen, mode);
      }
   }

   public static void clearActiveEditor() {
      if (isAvailable()) {
         PondererJeiPlugin.clearActiveEditor();
      }
   }

   public static boolean shouldRenderPonderUiOverlayManually(Screen screen) {
      return !isAvailable() ? false : PondererJeiPlugin.shouldRenderPonderUiOverlayManually(screen);
   }

   public static void renderPonderUiOverlay(Screen screen, GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      if (isAvailable()) {
         PondererJeiPlugin.renderPonderUiOverlay(screen, graphics, mouseX, mouseY, partialTicks);
      }
   }

   public static void renderPonderUiTooltips(Screen screen, GuiGraphics graphics, int mouseX, int mouseY) {
      if (isAvailable()) {
         PondererJeiPlugin.renderPonderUiTooltips(screen, graphics, mouseX, mouseY);
      }
   }

   @Nullable
   public static ScreenElement createIngredientElement(Object ingredient) {
      return !isAvailable() ? null : JeiIngredientHelper.createScreenElement(ingredient);
   }

   @Nullable
   public static ScreenElement createFluidIngredientElement(Fluid fluid, int amount) {
      return !isAvailable() ? null : JeiIngredientHelper.createFluidScreenElement(fluid, amount);
   }

   @Nullable
   public static ScreenElement resolveIngredientById(String id) {
      return !isAvailable() ? null : JeiIngredientHelper.resolveById(id);
   }

   @Nullable
   public static ScreenElement resolveIngredientById(String id, @Nullable String kind) {
      return !isAvailable() ? null : JeiIngredientHelper.resolveById(id, kind);
   }

   @Nullable
   public static String resolveIngredientId(Object typedIngredient) {
      return !isAvailable() ? null : JeiIngredientHelper.resolveId(typedIngredient);
   }

   @Nullable
   public static JeiCompat.IngredientDescriptor resolveIngredientDescriptor(Object typedIngredient) {
      return !isAvailable() ? null : JeiIngredientHelper.resolveDescriptor(typedIngredient);
   }

   public static List<String[]> getAllExtraIngredientEntries() {
      return !isAvailable() ? List.of() : JeiIngredientHelper.getAllExtraEntries();
   }

   public record IngredientDescriptor(String id, @Nullable String kind) {
   }
}
