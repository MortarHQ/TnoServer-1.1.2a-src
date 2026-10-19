package net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.jei;

import java.util.ArrayList;
import java.util.List;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.handlers.IGhostIngredientHandler;
import mezz.jei.api.gui.handlers.IGhostIngredientHandler.Target;
import mezz.jei.api.ingredients.ITypedIngredient;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.PacketDistributor;
import net.p3pp3rf1y.sophisticatedcore.client.gui.SettingsScreen;
import net.p3pp3rf1y.sophisticatedcore.common.gui.SettingsContainerMenu;
import net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.common.SetMemorySlotPayload;
import net.p3pp3rf1y.sophisticatedcore.settings.memory.MemorySettingsTab;

public class JeiSettingsGhostIngredientHandler<S extends SettingsScreen> implements IGhostIngredientHandler<S> {
   private S targetedScreen;

   public <I> List<Target<I>> getTargetsTyped(S gui, ITypedIngredient<I> ingredient, boolean doStart) {
      List<Target<I>> targets = new ArrayList<>();
      if (ingredient.getType() != VanillaTypes.ITEM_STACK) {
         return targets;
      }

      gui.startMouseDragHandledByOther();
      this.targetedScreen = gui;
      gui.getSettingsTabControl().getOpenTab().ifPresent(tab -> {
         if (tab instanceof MemorySettingsTab) {
            ingredient.getItemStack().ifPresent(ghostStack -> ((SettingsContainerMenu)gui.getMenu()).getStorageInventorySlots().forEach(s -> {
               if (s.getItem().isEmpty()) {
                  targets.add(new Target<I>() {
                     public Rect2i getArea() {
                        return new Rect2i(gui.getGuiLeft() + s.x, gui.getGuiTop() + s.y, 17, 17);
                     }

                     public void accept(Object i) {
                        PacketDistributor.sendToServer(new SetMemorySlotPayload(ghostStack, s.index), new CustomPacketPayload[0]);
                     }
                  });
               }
            }));
         }
      });
      return targets;
   }

   public void onComplete() {
      this.targetedScreen.stopMouseDragHandledByOther();
   }
}
