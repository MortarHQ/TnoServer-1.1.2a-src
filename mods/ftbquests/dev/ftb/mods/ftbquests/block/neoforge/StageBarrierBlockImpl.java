package dev.ftb.mods.ftbquests.block.neoforge;

import dev.ftb.mods.ftbquests.block.entity.StageBarrierBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;

public class StageBarrierBlockImpl {
   public static BlockEntitySupplier<StageBarrierBlockEntity> stageBlockEntityProvider() {
      return NeoForgeStageBarrierBlockEntity::new;
   }
}
