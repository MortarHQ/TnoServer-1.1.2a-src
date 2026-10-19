package dev.ftb.mods.ftblibrary.integration.currency;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

public interface CurrencyProvider {
   @NonExtendable
   default boolean isValidProvider() {
      return true;
   }

   String getName();

   int getTotalCurrency(Player var1);

   boolean takeCurrency(Player var1, int var2);

   void giveCurrency(Player var1, int var2);

   Component coinName(boolean var1);
}
