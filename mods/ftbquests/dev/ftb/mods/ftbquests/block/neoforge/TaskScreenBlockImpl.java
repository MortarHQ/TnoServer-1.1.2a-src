package dev.ftb.mods.ftbquests.block.neoforge;

import dev.ftb.mods.ftbquests.block.entity.TaskScreenAuxBlockEntity;
import dev.ftb.mods.ftbquests.block.entity.TaskScreenBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;

public class TaskScreenBlockImpl {
   public static BlockEntitySupplier<TaskScreenBlockEntity> blockEntityProvider() {
      return NeoForgeTaskScreenBlockEntity::new;
   }

   public static BlockEntitySupplier<TaskScreenAuxBlockEntity> blockEntityAuxProvider() {
      return NeoForgeTaskScreenAuxBlockEntity::new;
   }
}
