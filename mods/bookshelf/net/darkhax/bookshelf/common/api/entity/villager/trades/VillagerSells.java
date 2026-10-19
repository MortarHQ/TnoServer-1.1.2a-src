package net.darkhax.bookshelf.common.api.entity.villager.trades;

import java.util.function.Supplier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import org.jetbrains.annotations.NotNull;

public record VillagerSells(Supplier<ItemStack> itemToBuy, int emeraldCost, int maxUses, int villagerXp, float priceMultiplier) implements ItemListing {
   public MerchantOffer getOffer(@NotNull Entity villager, @NotNull RandomSource rng) {
      return new MerchantOffer(new ItemCost(Items.EMERALD, this.emeraldCost), this.itemToBuy.get(), this.maxUses, this.villagerXp, this.priceMultiplier);
   }
}
