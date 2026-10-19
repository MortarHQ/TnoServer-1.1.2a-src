package com.blamejared.crafttweaker.api.villager;

import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;

public interface ITradeRemover {
   boolean shouldRemove(ItemListing var1);
}
