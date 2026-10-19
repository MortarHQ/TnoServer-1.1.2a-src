package net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.emi;

import com.google.common.collect.Maps;
import dev.emi.emi.api.EmiDragDropHandler;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.Bounds;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
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

public class EmiStorageGhostDragDropHandler<T extends StorageScreenBase<?>> implements EmiDragDropHandler<T> {
   private final BiFunction<T, EmiIngredient, Map<Bounds, Consumer<EmiIngredient>>> bounds = (screen, ingredient) -> {
      Map<Bounds, Consumer<EmiIngredient>> map = Maps.newHashMap();
      if (ingredient.getEmiStacks().isEmpty()) {
         return map;
      }

      EmiStack emiGhostStack = (EmiStack)ingredient.getEmiStacks().getFirst();
      if (!emiGhostStack.isEmpty()) {
         ItemStack ghostStack = emiGhostStack.getItemStack();
         FluidStack fluidStack = CapabilityHelper.getFromCapability(
            ghostStack,
            FluidHandler.ITEM,
            null,
            fluidHandler -> fluidHandler.getTanks() > 0 ? fluidHandler.getFluidInTank(0) : FluidStack.EMPTY,
            FluidStack.EMPTY
         );
         if (!fluidStack.isEmpty()) {
            screen.getUpgradeSettingsControl()
               .getOpenTab()
               .filter(tab -> tab instanceof PumpUpgradeTab.Advanced)
               .map(PumpUpgradeTab.Advanced.class::cast)
               .ifPresent(pumpUpgradeTab -> this.addFluidTargets(pumpUpgradeTab, fluidStack, map));
         }

         ((StorageContainerMenuBase)screen.getMenu())
            .getOpenContainer()
            .ifPresent(
               c -> c.getSlots()
                  .forEach(
                     s -> {
                        if (s instanceof IFilterSlot && s.mayPlace(ghostStack)) {
                           map.put(
                              new Bounds(screen.getLeftX() + s.x, screen.getTopY() + s.y, 18, 18),
                              i -> PacketDistributor.sendToServer(new SetGhostSlotPayload(ghostStack, s.index), new CustomPacketPayload[0])
                           );
                        }
                     }
                  )
            );
      } else if (emiGhostStack.getKey() instanceof Fluid fluid) {
         screen.getUpgradeSettingsControl()
            .getOpenTab()
            .filter(tab -> tab instanceof PumpUpgradeTab.Advanced)
            .map(PumpUpgradeTab.Advanced.class::cast)
            .ifPresent(pumpUpgradeTab -> this.addFluidTargets(pumpUpgradeTab, new FluidStack(fluid, 1), map));
      }

      return map;
   };

   public boolean dropStack(T screen, EmiIngredient stack, int x, int y) {
      Map<Bounds, Consumer<EmiIngredient>> bounds = this.bounds.apply(screen, stack);

      for (Bounds b : bounds.keySet()) {
         if (b.contains(x, y)) {
            bounds.get(b).accept(stack);
            return true;
         }
      }

      return false;
   }

   public void render(T screen, EmiIngredient dragged, GuiGraphics draw, int mouseX, int mouseY, float delta) {
      for (Bounds b : this.bounds.apply(screen, dragged).keySet()) {
         draw.fill(b.x(), b.y(), b.x() + b.width(), b.y() + b.height(), -2010989773);
      }
   }

   private void addFluidTargets(PumpUpgradeTab.Advanced pumpUpgradeTab, FluidStack ghostFluid, Map<Bounds, Consumer<EmiIngredient>> map) {
      List<Position> slotTopLeftPositions = pumpUpgradeTab.getFluidFilterControl().getSlotTopLeftPositions();

      for (int slot = 0; slot < slotTopLeftPositions.size(); slot++) {
         Position position = slotTopLeftPositions.get(slot);
         int finalSlot = slot;
         map.put(new Bounds(position.x(), position.y(), 17, 17), ingredient -> pumpUpgradeTab.getFluidFilterControl().setFluid(finalSlot, ghostFluid));
      }
   }
}
