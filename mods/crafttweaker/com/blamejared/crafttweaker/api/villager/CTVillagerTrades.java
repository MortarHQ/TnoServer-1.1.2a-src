package com.blamejared.crafttweaker.api.villager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.villager.ActionAddTrade;
import com.blamejared.crafttweaker.api.action.villager.ActionAddWanderingTrade;
import com.blamejared.crafttweaker.api.action.villager.ActionRemoveTrade;
import com.blamejared.crafttweaker.api.action.villager.ActionRemoveWanderingTrade;
import com.blamejared.crafttweaker.api.action.villager.ActionTradeBase;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.villager.trade.type.BasicTradeListing;
import com.blamejared.crafttweaker.api.villager.trade.type.CustomTradeListing;
import com.blamejared.crafttweaker.api.villager.trade.type.IBasicItemListing;
import com.blamejared.crafttweaker.mixin.common.access.villager.AccessDyedArmorForEmeralds;
import com.blamejared.crafttweaker.mixin.common.access.villager.AccessEmeraldForItems;
import com.blamejared.crafttweaker.mixin.common.access.villager.AccessEnchantedItemForEmeralds;
import com.blamejared.crafttweaker.mixin.common.access.villager.AccessItemsAndEmeraldsToItems;
import com.blamejared.crafttweaker.mixin.common.access.villager.AccessItemsForEmeralds;
import com.blamejared.crafttweaker.mixin.common.access.villager.AccessTippedArrowForItemsAndEmeralds;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades.DyedArmorForEmeralds;
import net.minecraft.world.entity.npc.VillagerTrades.EmeraldForItems;
import net.minecraft.world.entity.npc.VillagerTrades.EnchantBookForEmeralds;
import net.minecraft.world.entity.npc.VillagerTrades.EnchantedItemForEmeralds;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.entity.npc.VillagerTrades.ItemsAndEmeraldsToItems;
import net.minecraft.world.entity.npc.VillagerTrades.ItemsForEmeralds;
import net.minecraft.world.entity.npc.VillagerTrades.SuspiciousStewForEmerald;
import net.minecraft.world.entity.npc.VillagerTrades.TippedArrowForItemsAndEmeralds;
import net.minecraft.world.entity.npc.VillagerTrades.TreasureMapForEmeralds;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.villagers.VillagerTrades")
@Document("vanilla/api/villager/VillagerTrades")
public class CTVillagerTrades {
   @ZenCodeGlobals.Global("villagerTrades")
   public static final CTVillagerTrades INSTANCE = new CTVillagerTrades();
   public static final List<ActionTradeBase> ACTIONS_VILLAGER_TRADES = new ArrayList<>();
   public static final List<ActionTradeBase> ACTION_WANDERING_TRADES = new ArrayList<>();
   public static final Map<Class<ItemListing>, Function<ItemListing, CTTradeObject>> TRADE_CONVERTER = new HashMap<>();

   @ZenCodeType.Method
   public void addTrade(VillagerProfession profession, int villagerLevel, BiFunction<Entity, RandomSource, @ZenCodeType.Nullable MerchantOffer> offerGenerator) {
      CustomTradeListing trade = new CustomTradeListing(offerGenerator);
      this.addTradeInternal(profession, villagerLevel, trade);
   }

   @ZenCodeType.Method
   public void addTrade(
      VillagerProfession profession,
      int villagerLevel,
      int emeralds,
      ItemStack forSale,
      int maxTrades,
      int xp,
      @ZenCodeType.OptionalFloat(1.0F) float priceMult
   ) {
      BasicTradeListing trade = new BasicTradeListing(emeralds, forSale, maxTrades, xp, priceMult);
      this.addTradeInternal(profession, villagerLevel, trade);
   }

   @ZenCodeType.Method
   public void addTrade(
      VillagerProfession profession,
      int villagerLevel,
      ItemStack input1,
      ItemStack forSale,
      int maxTrades,
      int xp,
      @ZenCodeType.OptionalFloat(1.0F) float priceMult
   ) {
      BasicTradeListing trade = new BasicTradeListing(input1, forSale, maxTrades, xp, priceMult);
      this.addTradeInternal(profession, villagerLevel, trade);
   }

   @ZenCodeType.Method
   public void addTrade(
      VillagerProfession profession,
      int villagerLevel,
      ItemStack input1,
      ItemStack input2,
      ItemStack forSale,
      int maxTrades,
      int xp,
      @ZenCodeType.OptionalFloat(1.0F) float priceMult
   ) {
      BasicTradeListing trade = new BasicTradeListing(input1, input2, forSale, maxTrades, xp, priceMult);
      this.addTradeInternal(profession, villagerLevel, trade);
   }

   @ZenCodeType.Method
   public void addTrade(VillagerProfession profession, int villagerLevel, ItemListing trade) {
      this.addTradeInternal(profession, villagerLevel, trade);
   }

   @ZenCodeType.Method
   public void removeBasicTrade(
      VillagerProfession profession,
      int villagerLevel,
      IItemStack forSale,
      @ZenCodeType.Optional("<item:minecraft:air>") IItemStack price,
      @ZenCodeType.Optional("<item:minecraft:air>") IItemStack price2
   ) {
      this.removeTradeInternal(profession, villagerLevel, trade -> {
         if (trade instanceof IBasicItemListing basicTrade) {
            boolean saleMatches = forSale.matches(IItemStack.ofMutable(basicTrade.getForSale()));
            if (price.isEmpty() && price2.isEmpty()) {
               return saleMatches;
            }

            boolean priceMatches = price.matches(IItemStack.ofMutable(basicTrade.getPrice()));
            if (!price.isEmpty() && price2.isEmpty()) {
               return saleMatches && priceMatches;
            }

            boolean price2Matches = price2.matches(IItemStack.ofMutable(basicTrade.getPrice2()));
            return saleMatches && priceMatches && price2Matches;
         } else {
            return false;
         }
      });
   }

   @ZenCodeType.Method
   public void removeEmeraldForItemsTrade(VillagerProfession profession, int villagerLevel, IItemStack tradeFor) {
      this.removeTradeInternal(profession, villagerLevel, trade -> {
         if (trade instanceof EmeraldForItems) {
            return tradeFor.matches(IItemStack.of(((AccessEmeraldForItems)trade).crafttweaker$getItemStack().itemStack()));
         } else {
            return trade instanceof IBasicItemListing basicTrade ? tradeFor.matches(IItemStack.of(basicTrade.getForSale())) : false;
         }
      });
   }

   @ZenCodeType.Method
   public void removeItemsForEmeraldsTrade(VillagerProfession profession, int villagerLevel, IItemStack sellingItem) {
      this.removeTradeInternal(profession, villagerLevel, trade -> {
         if (trade instanceof ItemsForEmeralds) {
            return sellingItem.matches(IItemStack.ofMutable(((AccessItemsForEmeralds)trade).crafttweaker$getItemStack()));
         } else {
            return trade instanceof IBasicItemListing basicTrade ? IItemStack.ofMutable(basicTrade.getPrice()).matches(sellingItem) : false;
         }
      });
   }

   @ZenCodeType.Method
   public void removeItemsAndEmeraldsToItemsTrade(VillagerProfession profession, int villagerLevel, IItemStack sellingItem, IItemStack buyingItem) {
      this.removeTradeInternal(profession, villagerLevel, trade -> {
         if (trade instanceof ItemsAndEmeraldsToItems) {
            if (sellingItem.matches(IItemStack.ofMutable(((AccessItemsAndEmeraldsToItems)trade).crafttweaker$getToItem()))) {
               return buyingItem.matches(IItemStack.ofMutable(((AccessItemsAndEmeraldsToItems)trade).crafttweaker$getFromItem().itemStack()));
            }
         } else if (trade instanceof IBasicItemListing basicTrade && sellingItem.matches(IItemStack.ofMutable(basicTrade.getPrice()))) {
            return buyingItem.matches(IItemStack.ofMutable(basicTrade.getForSale()));
         }

         return false;
      });
   }

   @ZenCodeType.Method
   public void removeTippedArrowForItemsAndEmeraldsTrade(VillagerProfession profession, int villagerLevel, IItemStack potionStack, Item sellingItem) {
      this.removeTradeInternal(
         profession,
         villagerLevel,
         trade -> trade instanceof TippedArrowForItemsAndEmeralds
               && potionStack.matches(IItemStack.ofMutable(((AccessTippedArrowForItemsAndEmeralds)trade).crafttweaker$getToItem()))
            ? sellingItem == ((AccessTippedArrowForItemsAndEmeralds)trade).crafttweaker$getFromItem()
            : false
      );
   }

   @ZenCodeType.Method
   public void removeDyedArmorForEmeraldsTrade(VillagerProfession profession, int villagerLevel, Item buyingItem) {
      this.removeTradeInternal(
         profession,
         villagerLevel,
         trade -> trade instanceof DyedArmorForEmeralds ? ((AccessDyedArmorForEmeralds)trade).crafttweaker$getItem() == buyingItem : false
      );
   }

   @ZenCodeType.Method
   public void removeTreasureMapForEmeraldsTrade(VillagerProfession profession, int villagerLevel) {
      this.removeTradeInternal(profession, villagerLevel, trade -> trade instanceof TreasureMapForEmeralds);
   }

   @ZenCodeType.Method
   public void removeEnchantBookForEmeraldsTrade(VillagerProfession profession, int villagerLevel) {
      this.removeTradeInternal(profession, villagerLevel, trade -> trade instanceof EnchantBookForEmeralds);
   }

   @ZenCodeType.Method
   public void removeEnchantedItemForEmeraldsTrade(VillagerProfession profession, int villagerLevel, IItemStack buyingItem) {
      this.removeTradeInternal(
         profession,
         villagerLevel,
         trade -> trade instanceof EnchantedItemForEmeralds
            ? buyingItem.matches(IItemStack.ofMutable(((AccessEnchantedItemForEmeralds)trade).crafttweaker$getItemStack()))
            : false
      );
   }

   @ZenCodeType.Method
   public void removeSuspiciousStewForEmeraldTrade(VillagerProfession profession, int villagerLevel) {
      this.removeTradeInternal(profession, villagerLevel, trade -> trade instanceof SuspiciousStewForEmerald);
   }

   @ZenCodeType.Method
   public void removeAllTrades(VillagerProfession profession, int villagerLevel) {
      this.removeTradeInternal(profession, villagerLevel, trade -> true);
   }

   @ZenCodeType.Method
   public void removeTrade(
      VillagerProfession profession,
      int villagerLevel,
      IIngredient buying,
      IIngredient selling,
      @ZenCodeType.Optional("<item:minecraft:air>") IIngredient secondBuying
   ) {
      this.removeTradeInternal(profession, villagerLevel, trade -> {
         Function<ItemListing, CTTradeObject> tradeFunc = TRADE_CONVERTER.get(trade.getClass());
         if (tradeFunc == null) {
            return false;
         } else {
            CTTradeObject tradeObject = tradeFunc.apply(trade);
            if (!buying.matches(tradeObject.getBuyingStack())) {
               return false;
            } else {
               return !selling.matches(tradeObject.getSellingStack()) ? false : secondBuying.matches(tradeObject.getBuyingStackSecond());
            }
         }
      });
   }

   @ZenCodeType.Method
   public void removeTradesSelling(VillagerProfession profession, int villagerLevel, IIngredient selling) {
      this.removeTradeInternal(profession, villagerLevel, trade -> {
         Function<ItemListing, CTTradeObject> tradeFunc = TRADE_CONVERTER.get(trade.getClass());
         if (tradeFunc == null) {
            return false;
         }

         CTTradeObject tradeObject = tradeFunc.apply(trade);
         return selling.matches(tradeObject.getSellingStack());
      });
   }

   @ZenCodeType.Method
   public void removeTradesBuying(VillagerProfession profession, int villagerLevel, IIngredient buying) {
      this.removeTradeInternal(profession, villagerLevel, trade -> {
         Function<ItemListing, CTTradeObject> tradeFunc = TRADE_CONVERTER.get(trade.getClass());
         if (tradeFunc == null) {
            return false;
         }

         CTTradeObject tradeObject = tradeFunc.apply(trade);
         return buying.matches(tradeObject.getBuyingStack());
      });
   }

   @ZenCodeType.Method
   public void removeTradesBuying(VillagerProfession profession, int villagerLevel, IIngredient buying, IIngredient secondBuying) {
      this.removeTradeInternal(profession, villagerLevel, trade -> {
         Function<ItemListing, CTTradeObject> tradeFunc = TRADE_CONVERTER.get(trade.getClass());
         if (tradeFunc == null) {
            return false;
         }

         CTTradeObject tradeObject = tradeFunc.apply(trade);
         return !buying.matches(tradeObject.getBuyingStack()) ? false : secondBuying.matches(tradeObject.getBuyingStackSecond());
      });
   }

   @ZenCodeType.Method
   public void addWanderingTrade(int rarity, ItemListing trade) {
      this.addWanderingTradeInternal(rarity, trade);
   }

   @ZenCodeType.Method
   public void addWanderingTrade(int rarity, int emeralds, ItemStack forSale, int maxTrades, int xp) {
      BasicTradeListing trade = new BasicTradeListing(emeralds, forSale, maxTrades, xp, 1.0F);
      this.addWanderingTradeInternal(rarity, trade);
   }

   @ZenCodeType.Method
   public void addWanderingTrade(int rarity, IItemStack price, IItemStack forSale, int maxTrades, int xp) {
      BasicTradeListing trade = new BasicTradeListing(price.getInternal(), forSale.getInternal(), maxTrades, xp, 1.0F);
      this.addWanderingTradeInternal(rarity, trade);
   }

   @ZenCodeType.Method
   public void removeWanderingTrade(int rarity, IIngredient tradeFor) {
      this.removeWanderingTradeInternal(rarity, trade -> {
         Function<ItemListing, CTTradeObject> tradeFunc = TRADE_CONVERTER.get(trade.getClass());
         if (tradeFunc == null) {
            return false;
         }

         CTTradeObject tradeObject = tradeFunc.apply(trade);
         return tradeFor.matches(tradeObject.getSellingStack());
      });
   }

   @ZenCodeType.Method
   public void removeAllWanderingTrades(int rarity) {
      this.removeWanderingTradeInternal(rarity, trade -> true);
   }

   private void addTradeInternal(VillagerProfession profession, int villagerLevel, ItemListing trade) {
      this.apply(new ActionAddTrade(profession, villagerLevel, trade), false);
   }

   private void addWanderingTradeInternal(int villagerLevel, ItemListing trade) {
      this.apply(new ActionAddWanderingTrade(villagerLevel, trade), true);
   }

   private void removeTradeInternal(VillagerProfession profession, int villagerLevel, ITradeRemover remover) {
      this.apply(new ActionRemoveTrade(profession, villagerLevel, remover), false);
   }

   private void removeWanderingTradeInternal(int villagerLevel, ITradeRemover remover) {
      this.apply(new ActionRemoveWanderingTrade(villagerLevel, remover), true);
   }

   private void apply(ActionTradeBase action, boolean wandering) {
      if (wandering) {
         ACTION_WANDERING_TRADES.add(action);
      } else {
         ACTIONS_VILLAGER_TRADES.add(action);
      }

      CraftTweakerAPI.apply(action);
   }

   public static void clear() {
      ACTIONS_VILLAGER_TRADES.clear();
      ACTION_WANDERING_TRADES.clear();
   }
}
