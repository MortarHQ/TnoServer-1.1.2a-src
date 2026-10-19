package com.b1n_ry.yigd.config;

import com.b1n_ry.yigd.util.DropRule;
import java.util.ArrayList;
import java.util.List;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.CollapsibleObject;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.EnumHandler;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.EnumHandler.EnumDisplayOption;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

public class InventoryConfig {
   public boolean dropPlayerHead = false;
   @CollapsibleObject
   public InventoryConfig.ItemLossConfig itemLoss = new InventoryConfig.ItemLossConfig();
   public boolean loseSoulboundLevelOnDeath = false;
   public List<Integer> vanishingSlots = new ArrayList<>();
   public List<Integer> soulboundSlots = new ArrayList<>();
   public List<Integer> dropOnGroundSlots = new ArrayList<>();

   public static class ItemLossConfig {
      public boolean enabled = false;
      public boolean affectStacks = false;
      public boolean usePercentRange = true;
      public int lossRangeFrom = 0;
      public int lossRangeTo = 100;
      public boolean weightedSelection = true;
      @EnumHandler(option = EnumDisplayOption.BUTTON)
      public DropRule lossDropRule = DropRule.DESTROY;
      @Comment("Chance of losing an item (iterated over every item picked up by lossRange)")
      public int percentChanceOfLoss = 50;
      @Comment("If true, you can lose soulbound items from the item loss feature")
      public boolean canLoseSoulbound = false;
      public boolean includeModdedInventories = true;
   }
}
