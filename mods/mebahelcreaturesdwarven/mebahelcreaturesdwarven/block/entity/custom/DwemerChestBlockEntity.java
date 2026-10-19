package mebahelcreaturesdwarven.block.entity.custom;

import mebahelcreaturesdwarven.block.entity.ModBlockEntities;
import mebahelcreaturesdwarven.config.ModMultiplayerChest;
import mebahelcreaturesdwarven.sound.ModSounds;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.mebahelsapi.block.entity.base.BaseChestBlockEntity;
import net.minecraft.class_2338;
import net.minecraft.class_2561;
import net.minecraft.class_2680;

public class DwemerChestBlockEntity extends BaseChestBlockEntity {
   public DwemerChestBlockEntity(class_2338 pos, class_2680 state) {
      super(ModBlockEntities.DWEMER_CHEST_ENTITY, pos, state, 36);
   }

   protected class_2561 getChestTitle() {
      return class_2561.method_43471("block.mebahelcreaturesdwarven.dwemer_chest");
   }

   protected String getLogPrefix() {
      return "[DwemerChest]";
   }

   @Environment(EnvType.CLIENT)
   public void playOpenSound() {
      this.playChestSound(ModSounds.DWARVEN_CHEST_OPEN);
   }

   @Environment(EnvType.CLIENT)
   public void playCloseSound() {
      this.playChestSound(ModSounds.DWARVEN_CHEST_CLOSE);
   }

   protected boolean isMultiplayerEnabled() {
      return ModMultiplayerChest.turnOnMultiplayerDraugrChest;
   }
}
