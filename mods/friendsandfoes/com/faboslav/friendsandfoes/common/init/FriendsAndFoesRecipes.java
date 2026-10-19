package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.events.item.RegisterBrewingRecipesEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;

public final class FriendsAndFoesRecipes {
   public static void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {
      event.registrator().accept(Potions.AWKWARD, (Item)FriendsAndFoesItems.CRAB_CLAW.get(), FriendsAndFoesPotions.REACHING.holder());
      event.registrator().accept(FriendsAndFoesPotions.REACHING.holder(), Items.REDSTONE, FriendsAndFoesPotions.LONG_REACHING.holder());
      event.registrator().accept(FriendsAndFoesPotions.REACHING.holder(), Items.GLOWSTONE_DUST, FriendsAndFoesPotions.STRONG_REACHING.holder());
   }
}
