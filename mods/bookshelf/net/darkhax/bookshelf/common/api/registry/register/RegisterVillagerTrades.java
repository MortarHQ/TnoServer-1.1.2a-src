package net.darkhax.bookshelf.common.api.registry.register;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.darkhax.bookshelf.common.api.entity.villager.MerchantTier;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;

public class RegisterVillagerTrades {
   private final Map<VillagerProfession, Multimap<Integer, ItemListing>> villagerTrades = new HashMap<>();
   private final List<ItemListing> rareTrades = new ArrayList<>();
   private final List<ItemListing> commonTrades = new ArrayList<>();

   public void addTrade(VillagerProfession profession, int tier, ItemListing trade) {
      this.villagerTrades.computeIfAbsent(profession, p -> ArrayListMultimap.create()).put(tier, trade);
   }

   public void addTrade(VillagerProfession profession, MerchantTier tier, ItemListing trade) {
      this.addTrade(profession, tier.ordinal() + 1, trade);
   }

   public void addWanderingTrade(ItemListing trade, boolean isRare) {
      (isRare ? this.rareTrades : this.commonTrades).add(trade);
   }

   public void addRareWanderingTrade(ItemListing trade) {
      this.addWanderingTrade(trade, true);
   }

   public void addCommonWanderingTrade(ItemListing trade) {
      this.addWanderingTrade(trade, false);
   }

   public Map<VillagerProfession, Multimap<Integer, ItemListing>> getVillagerTrades() {
      return Collections.unmodifiableMap(this.villagerTrades);
   }

   public List<ItemListing> getRareWanderingTrades() {
      return Collections.unmodifiableList(this.rareTrades);
   }

   public List<ItemListing> getCommonWanderingTrades() {
      return Collections.unmodifiableList(this.commonTrades);
   }
}
