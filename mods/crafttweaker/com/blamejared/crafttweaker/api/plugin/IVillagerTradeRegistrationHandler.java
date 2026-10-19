package com.blamejared.crafttweaker.api.plugin;

import com.blamejared.crafttweaker.api.villager.CTTradeObject;
import java.util.function.Function;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;

public interface IVillagerTradeRegistrationHandler {
   <T extends ItemListing> void registerTradeConverter(Class<T> var1, Function<T, CTTradeObject> var2);
}
