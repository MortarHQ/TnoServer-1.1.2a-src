package com.github.wal_bos.moreskills.item;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;

public class ExcaliburItem extends SwordItem {
   private final ExcaliburItem.Stage stage;

   public ExcaliburItem(Tier tier, Properties properties, ExcaliburItem.Stage stage) {
      super(tier, properties);
      this.stage = stage;
   }

   public ExcaliburItem.Stage getStage() {
      return this.stage;
   }

   public enum Stage {
      EXCALIBUR,
      TRUE_EXCALIBUR,
      DIVINE_EXCALIBUR;
   }
}
