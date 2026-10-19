package com.progwml6.ironchest.common.item;

import com.progwml6.ironchest.common.Util;
import com.progwml6.ironchest.common.block.IronChestsTypes;

public enum IronChestsUpgradeType {
   IRON_TO_GOLD(IronChestsTypes.IRON, IronChestsTypes.GOLD),
   GOLD_TO_DIAMOND(IronChestsTypes.GOLD, IronChestsTypes.DIAMOND),
   COPPER_TO_IRON(IronChestsTypes.COPPER, IronChestsTypes.IRON),
   DIAMOND_TO_CRYSTAL(IronChestsTypes.DIAMOND, IronChestsTypes.CRYSTAL),
   WOOD_TO_IRON(IronChestsTypes.WOOD, IronChestsTypes.IRON),
   WOOD_TO_COPPER(IronChestsTypes.WOOD, IronChestsTypes.COPPER),
   DIAMOND_TO_OBSIDIAN(IronChestsTypes.DIAMOND, IronChestsTypes.OBSIDIAN);

   public final String name = Util.toEnglishName(this.name());
   public final IronChestsTypes source;
   public final IronChestsTypes target;

   IronChestsUpgradeType(IronChestsTypes source, IronChestsTypes target) {
      this.source = source;
      this.target = target;
   }

   public boolean canUpgrade(IronChestsTypes from) {
      return from == this.source;
   }
}
