package dev.ftb.mods.ftbquests.block.neoforge;

import dev.ftb.mods.ftbquests.block.entity.QuestBarrierBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;

public class QuestBarrierBlockImpl {
   public static BlockEntitySupplier<QuestBarrierBlockEntity> questBlockEntityProvider() {
      return NeoForgeQuestBarrierBlockEntity::new;
   }
}
