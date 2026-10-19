package net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.jei;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.handlers.IGhostIngredientHandler;
import mezz.jei.api.gui.handlers.IGhostIngredientHandler.Target;
import mezz.jei.api.ingredients.ITypedIngredient;
import mezz.jei.api.neoforge.NeoForgeTypes;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.capabilities.Capabilities.FluidHandler;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.network.PacketDistributor;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.common.gui.IFilterSlot;
import net.p3pp3rf1y.sophisticatedcore.common.gui.StorageContainerMenuBase;
import net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.common.SetGhostSlotPayload;
import net.p3pp3rf1y.sophisticatedcore.upgrades.pump.PumpUpgradeTab;
import net.p3pp3rf1y.sophisticatedcore.util.CapabilityHelper;

public class JeiStorageGhostIngredientHandler<S extends StorageScreenBase<?>> implements IGhostIngredientHandler<S> {
   public <I> List<Target<I>> getTargetsTyped(S gui, ITypedIngredient<I> ingredient, boolean doStart) {
      List<Target<I>> targets = new ArrayList<>();
      if (ingredient.getType() == VanillaTypes.ITEM_STACK) {
         StorageContainerMenuBase<?> container = (StorageContainerMenuBase<?>)gui.getMenu();
         ingredient.getItemStack()
            .ifPresent(
               ghostStack -> {
                  FluidStack fluidStack = CapabilityHelper.getFromCapability(
                     ghostStack,
                     FluidHandler.ITEM,
                     null,
                     fluidHandler -> fluidHandler.getTanks() > 0 ? fluidHandler.getFluidInTank(0) : FluidStack.EMPTY,
                     FluidStack.EMPTY
                  );
                  if (!fluidStack.isEmpty()) {
                     gui.getUpgradeSettingsControl()
                        .getOpenTab()
                        .filter(tab -> tab instanceof PumpUpgradeTab.Advanced)
                        .map(PumpUpgradeTab.Advanced.class::cast)
                        .ifPresent(pumpUpgradeTab -> this.addFluidTargets(pumpUpgradeTab, fluidStack, targets));
                  } else {
                     container.getOpenContainer().ifPresent(c -> c.getSlots().forEach(s -> {
                        if (s instanceof IFilterSlot && s.mayPlace(ghostStack)) {
                           targets.add(new Target<I>() {
                              public Rect2i getArea() {
                                 return new Rect2i(gui.getGuiLeft() + s.x, gui.getGuiTop() + s.y, 17, 17);
                              }

                              public void accept(Object i) {
                                 PacketDistributor.sendToServer(new SetGhostSlotPayload(ghostStack, s.index), new CustomPacketPayload[0]);
                              }
                           });
                        }
                     }));
                  }
               }
            );
      } else if (ingredient.getType() == NeoForgeTypes.FLUID_STACK) {
         gui.getUpgradeSettingsControl()
            .getOpenTab()
            .filter(tab -> tab instanceof PumpUpgradeTab.Advanced)
            .map(PumpUpgradeTab.Advanced.class::cast)
            .ifPresent(
               pumpUpgradeTab -> NeoForgeTypes.FLUID_STACK
                  .castIngredient(ingredient.getIngredient())
                  .ifPresent(ghostFluid -> this.addFluidTargets(pumpUpgradeTab, ghostFluid, targets))
            );
      }

      return targets;
   }

   private <I> void addFluidTargets(final PumpUpgradeTab.Advanced pumpUpgradeTab, final FluidStack ghostFluid, List<Target<I>> targets) {
      List<Position> slotTopLeftPositions = pumpUpgradeTab.getFluidFilterControl().getSlotTopLeftPositions();
      final AtomicInteger slot = new AtomicInteger();
      slot.set(0);

      while (slot.get() < slotTopLeftPositions.size()) {
         final Position position = slotTopLeftPositions.get(slot.get());
         targets.add(new Target<I>() {
            private final int slotIndex = slot.get();

            public Rect2i getArea() {
               return new Rect2i(position.x(), position.y(), 17, 17);
            }

            public void accept(I i) {
               pumpUpgradeTab.getFluidFilterControl().setFluid(this.slotIndex, ghostFluid);
            }
         });
         slot.incrementAndGet();
      }
   }

   public void onComplete() {
   }
}
