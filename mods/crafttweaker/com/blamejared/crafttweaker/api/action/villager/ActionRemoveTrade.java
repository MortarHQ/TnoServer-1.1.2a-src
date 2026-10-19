package com.blamejared.crafttweaker.api.action.villager;

import com.blamejared.crafttweaker.api.villager.ITradeRemover;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;

public class ActionRemoveTrade extends ActionTradeBase {
   private final ITradeRemover tradeRemover;
   private final List<ItemListing> removed;

   public ActionRemoveTrade(VillagerProfession profession, int level, ITradeRemover tradeRemover) {
      super(profession, level);
      this.tradeRemover = tradeRemover;
      this.removed = new ArrayList<>();
   }

   @Override
   public void apply() {
      List<ItemListing> tradeList = this.getTradeList();
      this.apply(tradeList);
      this.setTradeList(tradeList);
   }

   @Override
   public void apply(List<ItemListing> tradeList) {
      tradeList.forEach(iTrade -> {
         if (this.tradeRemover.shouldRemove(iTrade)) {
            this.removed.add(iTrade);
         }
      });
      tradeList.removeAll(this.removed);
   }

   @Override
   public String describe() {
      return String.format("Removing Villager trade for Profession: '%s' and Level: '%s'", this.profession.toString(), this.level);
   }

   @Override
   public void undo() {
      List<ItemListing> tradeList = this.getTradeList();
      this.undo(tradeList);
      this.setTradeList(tradeList);
   }

   @Override
   public void undo(List<ItemListing> tradeList) {
      tradeList.addAll(this.removed);
   }

   @Override
   public String describeUndo() {
      return String.format("Undoing removal of Villager trade for Profession: '%s' and Level: '%s'", this.profession.toString(), this.level);
   }
}
