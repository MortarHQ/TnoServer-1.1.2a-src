package com.blamejared.crafttweaker.api.villager.trade.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.villager.trade.type.BasicTradeListing")
@Document("vanilla/api/villager/trade/type/BasicTradeListing")
public class BasicTradeListing implements ItemListing, IBasicItemListing {
   protected final ItemStack price;
   protected final ItemStack price2;
   protected final ItemStack forSale;
   protected final int maxTrades;
   protected final int xp;
   protected final float priceMult;

   @ZenCodeType.Constructor
   public BasicTradeListing(ItemStack price, ItemStack price2, ItemStack forSale, int maxTrades, int xp, float priceMult) {
      this.price = price;
      this.price2 = price2;
      this.forSale = forSale;
      this.maxTrades = maxTrades;
      this.xp = xp;
      this.priceMult = priceMult;
   }

   @ZenCodeType.Constructor
   public BasicTradeListing(ItemStack price, ItemStack forSale, int maxTrades, int xp, float priceMult) {
      this(price, ItemStack.EMPTY, forSale, maxTrades, xp, priceMult);
   }

   @ZenCodeType.Constructor
   public BasicTradeListing(int emeralds, ItemStack forSale, int maxTrades, int xp, float mult) {
      this(new ItemStack(Items.EMERALD, emeralds), forSale, maxTrades, xp, mult);
   }

   @ZenCodeType.Constructor
   public BasicTradeListing(int emeralds, ItemStack forSale, int maxTrades, int xp) {
      this(new ItemStack(Items.EMERALD, emeralds), forSale, maxTrades, xp, 1.0F);
   }

   @Nullable
   public MerchantOffer getOffer(Entity trader, RandomSource rand) {
      ItemCost priceCost = new ItemCost(this.price.getItemHolder(), this.price.getCount(), DataComponentPredicate.allOf(this.price.getComponents()), this.price);
      Optional<ItemCost> price2Cost = Optional.empty();
      if (!this.price2.isEmpty()) {
         price2Cost = Optional.of(
            new ItemCost(this.price2.getItemHolder(), this.price2.getCount(), DataComponentPredicate.allOf(this.price2.getComponents()), this.price2)
         );
      }

      return new MerchantOffer(priceCost, price2Cost, this.forSale, this.maxTrades, this.xp, this.priceMult);
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("price")
   @Override
   public ItemStack getPrice() {
      return this.price;
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("price2")
   @Override
   public ItemStack getPrice2() {
      return this.price2;
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("forSale")
   @Override
   public ItemStack getForSale() {
      return this.forSale;
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("maxTrades")
   @Override
   public int getMaxTrades() {
      return this.maxTrades;
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("xp")
   @Override
   public int getXp() {
      return this.xp;
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("priceMult")
   @Override
   public float getPriceMult() {
      return this.priceMult;
   }
}
