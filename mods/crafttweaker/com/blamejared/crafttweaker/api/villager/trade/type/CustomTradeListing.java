package com.blamejared.crafttweaker.api.villager.trade.type;

import java.util.function.BiFunction;
import javax.annotation.Nullable;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.trading.MerchantOffer;

public class CustomTradeListing implements ItemListing {
   private final BiFunction<Entity, RandomSource, MerchantOffer> offerGenerator;

   public CustomTradeListing(BiFunction<Entity, RandomSource, MerchantOffer> offerGenerator) {
      this.offerGenerator = offerGenerator;
   }

   @Nullable
   public MerchantOffer getOffer(Entity entity, RandomSource random) {
      return this.offerGenerator.apply(entity, random);
   }
}
