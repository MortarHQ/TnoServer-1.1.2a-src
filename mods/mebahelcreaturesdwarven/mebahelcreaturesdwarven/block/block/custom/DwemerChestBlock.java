package mebahelcreaturesdwarven.block.block.custom;

import mebahelcreaturesdwarven.block.entity.ModBlockEntities;
import mebahelcreaturesdwarven.block.entity.custom.DwemerChestBlockEntity;
import mebahelcreaturesdwarven.config.ModMultiplayerChest;
import net.mebahelsapi.block.block.custom.base.BaseChestBlock;
import net.minecraft.class_4970.class_2251;

public class DwemerChestBlock extends BaseChestBlock {
   public DwemerChestBlock(class_2251 settings) {
      super(settings, () -> ModBlockEntities.DWEMER_CHEST_ENTITY, DwemerChestBlockEntity::new);
   }

   protected boolean isMultiplayerEnabled() {
      return ModMultiplayerChest.turnOnMultiplayerDraugrChest;
   }
}
