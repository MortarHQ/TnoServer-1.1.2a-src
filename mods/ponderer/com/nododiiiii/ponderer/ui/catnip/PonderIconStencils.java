package com.nododiiiii.ponderer.ui.catnip;

import com.mojang.math.Axis;
import net.createmod.catnip.data.Couple;
import net.createmod.catnip.gui.UIRenderHelper;
import net.createmod.catnip.gui.element.DelegatedStencilElement;
import net.createmod.catnip.gui.element.FadableScreenElement;
import net.createmod.catnip.gui.widget.AbstractSimiWidget;
import net.createmod.catnip.gui.widget.BoxWidget;
import net.createmod.catnip.theme.Color;
import net.createmod.ponder.enums.PonderGuiTextures;

public final class PonderIconStencils {
   private static final int DEFAULT_ICON_SIZE = 16;
   private static final int TEXTURE_ICON_SIZE = 16;

   private PonderIconStencils() {
   }

   public static DelegatedStencilElement centered(PonderGuiTextures texture) {
      return centered(texture, 16);
   }

   public static DelegatedStencilElement centered(PonderGuiTextures texture, int iconSize) {
      return transformed(texture, 0.0F, false, iconSize);
   }

   public static DelegatedStencilElement mirrored(PonderGuiTextures texture) {
      return mirrored(texture, 16);
   }

   public static DelegatedStencilElement mirrored(PonderGuiTextures texture, int iconSize) {
      return transformed(texture, 0.0F, true, iconSize);
   }

   public static DelegatedStencilElement rotated(PonderGuiTextures texture, float degrees) {
      return rotated(texture, degrees, 16);
   }

   public static DelegatedStencilElement rotated(PonderGuiTextures texture, float degrees, int iconSize) {
      return transformed(texture, degrees, false, iconSize);
   }

   public static BoxWidget attach(BoxWidget button, DelegatedStencilElement icon) {
      return attach(button, icon, (FadableScreenElement)BoxWidget.gradientFactory.apply(button));
   }

   public static BoxWidget attachFail(BoxWidget button, DelegatedStencilElement icon) {
      return attach(button, icon, failGradient(button));
   }

   public static BoxWidget attach(BoxWidget button, DelegatedStencilElement icon, FadableScreenElement renderer) {
      return (BoxWidget)button.showingElement(icon.withElementRenderer(renderer));
   }

   private static FadableScreenElement failGradient(BoxWidget button) {
      return (graphics, width, height, alpha) -> {
         Couple<Color> colors = button.active ? AbstractSimiWidget.COLOR_FAIL : button.getColorDisabled();
         UIRenderHelper.angledGradient(graphics, 0.0F, 0, height / 2, height, width, colors);
      };
   }

   private static DelegatedStencilElement transformed(PonderGuiTextures texture, float degrees, boolean mirrorX, int iconSize) {
      return (DelegatedStencilElement)new DelegatedStencilElement().withStencilRenderer((graphics, width, height, alpha) -> {
         graphics.pose().pushPose();
         graphics.pose().translate(width / 2.0F, height / 2.0F, 0.0F);
         if (degrees != 0.0F) {
            graphics.pose().mulPose(Axis.ZP.rotationDegrees(degrees));
         }

         float scale = iconSize / 16.0F;
         graphics.pose().scale(scale, scale, 1.0F);
         if (mirrorX) {
            graphics.pose().scale(-1.0F, 1.0F, 1.0F);
         }

         texture.render(graphics, -8, -8);
         graphics.pose().popPose();
      }).withBounds(iconSize, iconSize);
   }
}
