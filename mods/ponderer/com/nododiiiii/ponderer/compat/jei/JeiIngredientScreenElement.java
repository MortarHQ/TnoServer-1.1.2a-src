package com.nododiiiii.ponderer.compat.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import javax.annotation.Nullable;
import mezz.jei.api.ingredients.IIngredientRenderer;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.ITypedIngredient;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.api.runtime.IJeiRuntime;
import net.createmod.catnip.gui.element.ScreenElement;
import net.minecraft.client.gui.GuiGraphics;

public class JeiIngredientScreenElement implements ScreenElement {
   private final ITypedIngredient<?> typedIngredient;

   private JeiIngredientScreenElement(ITypedIngredient<?> typedIngredient) {
      this.typedIngredient = typedIngredient;
   }

   @Nullable
   public static JeiIngredientScreenElement of(ITypedIngredient<?> typedIngredient) {
      return PondererJeiPlugin.getRuntime() == null ? null : new JeiIngredientScreenElement(typedIngredient);
   }

   public void render(GuiGraphics graphics, int x, int y) {
      IJeiRuntime rt = PondererJeiPlugin.getRuntime();
      if (rt != null) {
         this.renderTyped(graphics, rt.getIngredientManager(), x, y);
      }
   }

   private <T> void renderTyped(GuiGraphics graphics, IIngredientManager mgr, int x, int y) {
      ITypedIngredient<T> typed = (ITypedIngredient<T>)this.typedIngredient;
      IIngredientType<T> type = typed.getType();
      IIngredientRenderer<T> renderer = mgr.getIngredientRenderer(type);
      PoseStack poseStack = graphics.pose();
      poseStack.pushPose();
      poseStack.translate(x, y, 0.0F);
      renderer.render(graphics, typed.getIngredient());
      poseStack.popPose();
   }
}
