package net.p3pp3rf1y.sophisticatedcore.controller;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.p3pp3rf1y.sophisticatedcore.util.NBTHelper;
import net.p3pp3rf1y.sophisticatedcore.util.WorldHelper;

public interface IControllerBoundable {
   String CONTROLLER_POS_TAG = "controllerPos";

   void setControllerPos(BlockPos var1);

   Optional<BlockPos> getControllerPos();

   void removeControllerPos();

   BlockPos getStorageBlockPos();

   Level getStorageBlockLevel();

   default boolean canBeConnected() {
      return this.getControllerPos().isEmpty();
   }

   void registerController(ControllerBlockEntityBase var1);

   void unregisterController();

   boolean canConnectStorages();

   default void runOnController(Level level, Consumer<ControllerBlockEntityBase> toRun) {
      this.getControllerPos().flatMap(pos -> WorldHelper.getLoadedBlockEntity(level, pos, ControllerBlockEntityBase.class)).ifPresent(toRun);
   }

   default void saveControllerPos(CompoundTag tag) {
      this.getControllerPos().ifPresent(p -> tag.putLong("controllerPos", p.asLong()));
   }

   default void loadControllerPos(CompoundTag tag) {
      NBTHelper.getLong(tag, "controllerPos").ifPresent(value -> {
         BlockPos controllerPos = BlockPos.of(value);
         this.setControllerPos(controllerPos);
      });
   }

   default void addToController(Level level, BlockPos pos, BlockPos controllerPos) {
   }

   default void addToAdjacentController() {
      Level level = this.getStorageBlockLevel();
      if (!level.isClientSide()) {
         BlockPos pos = this.getStorageBlockPos();

         for (Direction dir : Direction.values()) {
            BlockPos offsetPos = pos.offset(dir.getNormal());
            WorldHelper.getBlockEntity(level, offsetPos, IControllerBoundable.class).ifPresentOrElse(s -> {
               if (s.canConnectStorages()) {
                  s.getControllerPos().ifPresent(controllerPos -> this.addToController(level, pos, controllerPos));
               }
            }, () -> this.addToController(level, pos, offsetPos));
            if (this.getControllerPos().isPresent()) {
               break;
            }
         }
      }
   }
}
