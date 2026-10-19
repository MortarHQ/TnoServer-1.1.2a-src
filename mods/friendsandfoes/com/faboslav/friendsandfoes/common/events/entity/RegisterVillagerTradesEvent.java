package com.faboslav.friendsandfoes.common.events.entity;

import com.faboslav.friendsandfoes.common.events.base.EventHandler;
import java.util.function.BiConsumer;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;

public record RegisterVillagerTradesEvent(VillagerProfession type, BiConsumer<Integer, ItemListing> trade) {
   public static final EventHandler<RegisterVillagerTradesEvent> EVENT = new EventHandler<>();

   public void register(int level, ItemListing trade) {
      this.trade.accept(level, trade);
   }
}
