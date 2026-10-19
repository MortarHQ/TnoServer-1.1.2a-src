package net.p3pp3rf1y.sophisticatedcore.controller;

import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.p3pp3rf1y.sophisticatedcore.util.WorldHelper;

public interface ILinkable extends IControllerBoundable {
   default void linkToController(BlockPos controllerPos) {
      if (this.canBeLinked() && !this.isLinked()) {
         Level level = this.getStorageBlockLevel();
         if (!level.isClientSide() && WorldHelper.getLoadedBlockEntity(level, controllerPos, ControllerBlockEntityBase.class).isPresent()) {
            boolean hadControllerPos = this.getControllerPos().isPresent();
            if (!hadControllerPos) {
               this.setControllerPos(controllerPos);
            }

            this.runOnController(level, controller -> {
               if (!controller.addLinkedBlock(this.getStorageBlockPos()) && !hadControllerPos) {
                  this.removeControllerPos();
               }
            });
         }
      }
   }

   default void unlinkFromController() {
      Level level = this.getStorageBlockLevel();
      if (!level.isClientSide() && this.isLinked()) {
         this.runOnController(level, controller -> controller.removeLinkedBlock(this.getStorageBlockPos()));
         this.removeControllerPos();
         this.setNotLinked();
      }
   }

   default void setNotLinked() {
   }

   Set<BlockPos> getConnectablePositions();

   boolean connectLinkedSelf();

   @Override
   void setControllerPos(BlockPos var1);

   @Override
   Optional<BlockPos> getControllerPos();

   boolean isLinked();

   default boolean canBeLinked() {
      return true;
   }
}
