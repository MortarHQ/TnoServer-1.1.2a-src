package com.blamejared.crafttweaker.api.action.villager;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.List;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;

public class ActionAddWanderingTrade extends ActionTradeBase {
   private final ItemListing trade;

   public ActionAddWanderingTrade(int level, ItemListing trade) {
      super(level);
      this.trade = trade;
   }

   @Override
   protected Int2ObjectMap<ItemListing[]> getTrades() {
      return VillagerTrades.WANDERING_TRADER_TRADES;
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
      return String.format("Adding Wandering Trader trade for Level: '%s'", this.level);
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
      return String.format("Undoing addition of Wandering Trader trade for Level: '%s'", this.level);
   }
}
