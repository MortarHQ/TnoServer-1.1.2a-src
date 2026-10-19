package dev.ftb.mods.ftbxmodcompat.generic.currency;

import dev.ftb.mods.ftblibrary.integration.currency.CurrencyProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.sirgrantd.sg_economy.api.EconomyEventProvider;
import net.sirgrantd.sg_economy.api.SGEconomyApi;

public enum SGEconomyProvider implements CurrencyProvider {
   INSTANCE;

   private final EconomyEventProvider sgEconomy = SGEconomyApi.get();

   public String getName() {
      return "SG Economy";
   }

   public int getTotalCurrency(Player player) {
      return this.sgEconomy.getBalanceAsInt(player);
   }

   public boolean takeCurrency(Player player, int amount) {
      int coins = this.getTotalCurrency(player);
      if (coins >= amount) {
         this.sgEconomy.withdrawBalanceAsInt(player, amount);
         return true;
      } else {
         return false;
      }
   }

   public void giveCurrency(Player player, int amount) {
      this.sgEconomy.depositBalanceAsInt(player, amount);
   }

   public Component coinName(boolean plural) {
      return Component.translatable(plural ? "text.coins" : "text.coin");
   }
}
