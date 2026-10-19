package com.blamejared.crafttweaker.api.action.villager;

import java.util.List;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;

public class ActionAddTrade extends ActionTradeBase {
   private final ItemListing trade;

   public ActionAddTrade(VillagerProfession profession, int level, ItemListing trade) {
      super(profession, level);
      this.trade = trade;
   }

   @Override
   public void apply() {
      List<ItemListing> tradeList = this.getTradeList();
      this.apply(tradeList);
      this.setTradeList(tradeList);
   }

   @Override
   public void apply(List<ItemListing> tradeList) {
      tradeList.add(this.trade);
   }

   @Override
   public String describe() {
      return String.format("Adding Villager trade for Profession: '%s' and Level: '%s'", this.profession.toString(), this.level);
   }

   @Override
   public void undo() {
      List<ItemListing> tradeList = this.getTradeList();
      this.undo(tradeList);
      this.setTradeList(tradeList);
   }

   @Override
   public void undo(List<ItemListing> tradeList) {
      tradeList.remove(this.trade);
   }

   @Override
   public String describeUndo() {
      return String.format("Undoing addition of Villager trade for Profession: '%s' and Level: '%s'", this.profession.toString(), this.level);
   }
}
