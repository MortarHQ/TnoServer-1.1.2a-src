package net.darkhax.bookshelf.common.api.entity.villager.trades;

import java.util.function.Supplier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.trading.MerchantOffer;
import org.jetbrains.annotations.NotNull;

public record VillagerOffers(Supplier<MerchantOffer>... offers) implements ItemListing {
   @SafeVarargs
   public VillagerOffers {
   }

   public MerchantOffer getOffer(@NotNull Entity entity, @NotNull RandomSource randomSource) {
      return this.offers[randomSource.nextInt(this.offers.length)].get();
   }
}
