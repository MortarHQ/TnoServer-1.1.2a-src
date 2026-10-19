package com.blamejared.crafttweaker.api.action.villager;

import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.crafttweaker.api.action.internal.CraftTweakerAction;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;

public abstract class ActionTradeBase extends CraftTweakerAction implements IUndoableAction {
   protected VillagerProfession profession;
   protected final int level;

   public ActionTradeBase(int level) {
      this.level = level;
   }

   public ActionTradeBase(VillagerProfession profession, int level) {
      this.profession = profession;
      this.level = level;
   }

   public abstract void apply(List<ItemListing> var1);

   public abstract void undo(List<ItemListing> var1);

   protected Int2ObjectMap<ItemListing[]> getTrades() {
      return VillagerTrades.TRADES.computeIfAbsent(this.profession, villagerProfession -> new Int2ObjectArrayMap());
   }

   protected List<ItemListing> getTradeList() {
      ItemListing[] iTrades = (ItemListing[])this.getTrades().computeIfAbsent(this.level, integer -> new ItemListing[0]);
      return new ArrayList<>(Arrays.asList(iTrades));
   }

   protected void setTradeList(List<ItemListing> tradeList) {
      this.getTrades().put(this.level, tradeList.toArray(new ItemListing[0]));
   }

   public int getLevel() {
      return this.level;
   }

   public VillagerProfession getProfession() {
      return this.profession;
   }
}
