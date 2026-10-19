package net.p3pp3rf1y.sophisticatedcore.controller;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.settings.memory.MemorySettingsCategory;
import net.p3pp3rf1y.sophisticatedcore.util.WorldHelper;

public interface IControllableStorage extends IControllerBoundable {
   IStorageWrapper getStorageWrapper();

   @Override
   default boolean canConnectStorages() {
      return true;
   }

   default boolean hasStorageData() {
      return true;
   }

   default void tryToAddToController() {
      this.addToAdjacentController();
   }

   default void removeFromController() {
      Level level = this.getStorageBlockLevel();
      if (!level.isClientSide()) {
         this.getControllerPos()
            .flatMap(p -> WorldHelper.getBlockEntity(level, p, ControllerBlockEntityBase.class))
            .ifPresent(c -> c.removeStorage(this.getStorageBlockPos()));
         this.removeControllerPos();
      }
   }

   @Override
   default void addToController(Level level, BlockPos pos, BlockPos controllerPos) {
      WorldHelper.getBlockEntity(level, controllerPos, ControllerBlockEntityBase.class).ifPresent(c -> c.addStorage(pos));
   }

   @Override
   default void registerController(ControllerBlockEntityBase controllerBlockEntity) {
      this.setControllerPos(controllerBlockEntity.getBlockPos());
      if (this.hasStorageData() && controllerBlockEntity.getLevel() != null && !controllerBlockEntity.getLevel().isClientSide()) {
         this.registerListeners();
      }
   }

   @Override
   default void unregisterController() {
      this.removeControllerPos();
      this.getStorageWrapper().getInventoryForInputOutput().unregisterStackKeyListeners();
      this.getStorageWrapper().getSettingsHandler().getTypeCategory(MemorySettingsCategory.class).unregisterListeners();
      this.getStorageWrapper().getInventoryHandler().unregisterFilterItemsChangeListener();
   }

   private void registerListeners() {
      this.registerInventoryStackListeners();
      this.getStorageWrapper()
         .getSettingsHandler()
         .getTypeCategory(MemorySettingsCategory.class)
         .registerListeners(
            i -> this.runOnController(this.getStorageBlockLevel(), controller -> controller.addStorageMemorizedItem(this.getStorageBlockPos(), i)),
            i -> this.runOnController(this.getStorageBlockLevel(), controller -> controller.removeStorageMemorizedItem(this.getStorageBlockPos(), i)),
            i -> this.runOnController(this.getStorageBlockLevel(), controller -> controller.addStorageMemorizedStack(this.getStorageBlockPos(), i)),
            i -> this.runOnController(this.getStorageBlockLevel(), controller -> controller.removeStorageMemorizedStack(this.getStorageBlockPos(), i))
         );
      this.getStorageWrapper()
         .getInventoryHandler()
         .registerFilterItemsChangeListener(
            items -> this.runOnController(this.getStorageBlockLevel(), controller -> controller.setStorageFilterItems(this.getStorageBlockPos(), items))
         );
      this.getStorageWrapper().registerOnInventoryInputOutputHandlerRefreshListener(this::onInventoryInputOutputHandlerRefresh);
   }

   default void onInventoryInputOutputHandlerRefresh() {
      this.runOnController(this.getStorageBlockLevel(), controllerBlockEntityBase -> controllerBlockEntityBase.clearCachedHandler(this.getStorageBlockPos()));
   }

   default void registerInventoryStackListeners() {
      this.getStorageWrapper()
         .getInventoryForInputOutput()
         .registerTrackingListeners(
            isk -> this.runOnController(this.getStorageBlockLevel(), controller -> controller.addStorageStack(this.getStorageBlockPos(), isk)),
            isk -> this.runOnController(this.getStorageBlockLevel(), controller -> controller.removeStorageStack(this.getStorageBlockPos(), isk)),
            () -> this.runOnController(this.getStorageBlockLevel(), controller -> controller.addStorageWithEmptySlots(this.getStorageBlockPos())),
            () -> this.runOnController(this.getStorageBlockLevel(), controller -> controller.removeStorageWithEmptySlots(this.getStorageBlockPos()))
         );
   }

   default void registerWithControllerOnLoad() {
      this.getControllerPos().ifPresentOrElse(controllerPos -> {
         Level level = this.getStorageBlockLevel();
         if (!level.isClientSide()) {
            WorldHelper.getLoadedBlockEntity(level, controllerPos, ControllerBlockEntityBase.class).ifPresent(controller -> {
               if (controller.isStorageConnected(this.getStorageBlockPos())) {
                  controller.addStorageStacksAndRegisterListeners(this.getStorageBlockPos());
               } else {
                  this.removeControllerPos();
                  this.tryToAddToController();
               }
            });
         }
      }, this::tryToAddToController);
   }

   default void changeSlots(int newSlots) {
      this.getControllerPos()
         .ifPresent(
            controllerPos -> {
               Level level = this.getStorageBlockLevel();
               if (!level.isClientSide()) {
                  WorldHelper.getLoadedBlockEntity(level, controllerPos, ControllerBlockEntityBase.class)
                     .ifPresent(
                        controller -> controller.changeSlots(
                           this.getStorageBlockPos(), newSlots, this.getStorageWrapper().getInventoryForInputOutput().hasEmptySlots()
                        )
                     );
               }
            }
         );
   }

   default void updateEmptySlots() {
      this.getControllerPos()
         .ifPresent(
            controllerPos -> {
               Level level = this.getStorageBlockLevel();
               if (!level.isClientSide()) {
                  WorldHelper.getLoadedBlockEntity(level, controllerPos, ControllerBlockEntityBase.class)
                     .ifPresent(
                        controller -> controller.updateEmptySlots(
                           this.getStorageBlockPos(), this.getStorageWrapper().getInventoryForInputOutput().hasEmptySlots()
                        )
                     );
               }
            }
         );
   }
}
