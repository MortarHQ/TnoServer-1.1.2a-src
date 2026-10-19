package dev.ftb.mods.ftbfiltersystem.integration.jei;

import dev.ftb.mods.ftbfiltersystem.client.gui.GhostDropReceiver;
import java.util.List;
import java.util.function.Predicate;
import mezz.jei.api.gui.handlers.IGhostIngredientHandler;
import mezz.jei.api.gui.handlers.IGhostIngredientHandler.Target;
import mezz.jei.api.ingredients.ITypedIngredient;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.world.item.ItemStack;

public class FFSGhostHandler<S extends Screen & GhostDropReceiver> implements IGhostIngredientHandler<S> {
   private final Predicate<ItemStack> pred;

   public FFSGhostHandler() {
      this(s -> !s.isEmpty());
   }

   public FFSGhostHandler(Predicate<ItemStack> pred) {
      this.pred = pred;
   }

   public <I> List<Target<I>> getTargetsTyped(S gui, ITypedIngredient<I> ingredient, boolean doStart) {
      return this.pred.test(ingredient.getItemStack().orElse(ItemStack.EMPTY)) ? List.of(new FFSGhostHandler.GhostTarget<>(gui)) : List.of();
   }

   public void onComplete() {
   }

   record GhostTarget<I>(GhostDropReceiver gui) implements Target<I> {
      public Rect2i getArea() {
         return this.gui.getGhostDropRegion();
      }

      public void accept(I ingredient) {
         if (ingredient instanceof ItemStack stack) {
            this.gui.receiveGhostDrop(stack);
         }
      }
   }
}
