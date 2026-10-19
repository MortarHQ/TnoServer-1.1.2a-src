package net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.rei;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.drag.DraggableBoundsProvider;
import me.shedaniel.rei.api.client.gui.drag.DraggableStack;
import me.shedaniel.rei.api.client.gui.drag.DraggableStackVisitor;
import me.shedaniel.rei.api.client.gui.drag.DraggedAcceptorResult;
import me.shedaniel.rei.api.client.gui.drag.DraggingContext;
import me.shedaniel.rei.api.client.gui.drag.DraggableStackVisitor.BoundsProvider;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.shapes.VoxelShape;
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

public class ReiStorageGhostIngredientHandler<S extends StorageScreenBase<?>> implements DraggableStackVisitor<S> {
   private final Class<S> handingScreenClass;

   public ReiStorageGhostIngredientHandler(Class<S> handingScreenClass) {
      this.handingScreenClass = handingScreenClass;
   }

   public DraggedAcceptorResult acceptDraggedStack(DraggingContext<S> context, DraggableStack stack) {
      Point cursor = context.getCurrentPosition();
      if (cursor != null) {
         Optional<ReiGhostTarget> target = this.getDraggableAcceptingBounds(context, stack)
            .map(ReiGhostTarget.class::cast)
            .filter(b -> b.contains(cursor.getX(), cursor.getY()))
            .findFirst();
         if (target.isPresent()) {
            target.get().accept();
            return DraggedAcceptorResult.CONSUMED;
         }
      }

      return super.acceptDraggedStack(context, stack);
   }

   public Stream<BoundsProvider> getDraggableAcceptingBounds(DraggingContext<S> context, DraggableStack stack) {
      List<BoundsProvider> targets = new ArrayList<>();
      StorageScreenBase<?> screen = (StorageScreenBase<?>)context.getScreen();
      if (stack.getStack().getType() == VanillaEntryTypes.ITEM) {
         StorageContainerMenuBase<?> menu = (StorageContainerMenuBase<?>)((StorageScreenBase)context.getScreen()).getMenu();
         if (stack.getStack().getValue() instanceof ItemStack ghostStack) {
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
                  .ifPresent(pumpUpgradeTab -> this.addFluidTargets(pumpUpgradeTab, fluidStack, targets));
               return targets.stream();
            }

            menu.getOpenContainer().ifPresent(c -> c.getSlots().forEach(s -> {
               if (s instanceof IFilterSlot && s.mayPlace(ghostStack)) {
                  targets.add(new ReiGhostTarget() {
                     public VoxelShape bounds() {
                        return DraggableBoundsProvider.fromRectangle(new Rectangle(screen.getGuiLeft() + s.x, screen.getGuiTop() + s.y, 17, 17));
                     }

                     @Override
                     public void accept() {
                        PacketDistributor.sendToServer(new SetGhostSlotPayload(ghostStack, s.index), new CustomPacketPayload[0]);
                     }
                  });
               }
            }));
         }
      } else if (stack.getStack().getType() == VanillaEntryTypes.FLUID) {
         screen.getUpgradeSettingsControl()
            .getOpenTab()
            .filter(tab -> tab instanceof PumpUpgradeTab.Advanced)
            .map(PumpUpgradeTab.Advanced.class::cast)
            .ifPresent(pumpUpgradeTab -> {
               dev.architectury.fluid.FluidStack ghostFluidStack = (dev.architectury.fluid.FluidStack)stack.getStack().castValue();
               this.addFluidTargets(pumpUpgradeTab, new FluidStack(ghostFluidStack.getFluid(), (int)ghostFluidStack.getAmount()), targets);
            });
      }

      return targets.stream();
   }

   private void addFluidTargets(final PumpUpgradeTab.Advanced pumpUpgradeTab, final FluidStack ghostFluid, List<BoundsProvider> targets) {
      List<Position> slotTopLeftPositions = pumpUpgradeTab.getFluidFilterControl().getSlotTopLeftPositions();
      final AtomicInteger slot = new AtomicInteger();
      slot.set(0);

      while (slot.get() < slotTopLeftPositions.size()) {
         final Position position = slotTopLeftPositions.get(slot.get());
         targets.add(new ReiGhostTarget() {
            private final int slotIndex = slot.get();

            public VoxelShape bounds() {
               return DraggableBoundsProvider.fromRectangle(new Rectangle(position.x(), position.y(), 17, 17));
            }

            @Override
            public void accept() {
               pumpUpgradeTab.getFluidFilterControl().setFluid(this.slotIndex, ghostFluid);
            }
         });
         slot.incrementAndGet();
      }
   }

   public <R extends Screen> boolean isHandingScreen(R screen) {
      return this.handingScreenClass.isInstance(screen);
   }
}
