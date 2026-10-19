package com.github.wal_bos.moreskills.limbo;

import com.github.wal_bos.moreskills.registry.menu.ModMenus;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

public class IWantLimboMenu extends AbstractContainerMenu {
   private final Player player;
   private final List<IWantLimboData.StoredCard> cards;
   private final int focusedCard;
   private final int rerollsLeft;

   public static IWantLimboMenu server(int id, Inventory inv, Player player, List<IWantLimboData.StoredCard> cards, int focusedCard, int rerollsLeft) {
      return new IWantLimboMenu(id, inv, player, cards, focusedCard, rerollsLeft);
   }

   public IWantLimboMenu(int id, Inventory inv, RegistryFriendlyByteBuf buf) {
      this(id, inv, inv.player, readCards(buf), buf.readInt(), buf.readInt());
   }

   private IWantLimboMenu(int id, Inventory inv, Player player, List<IWantLimboData.StoredCard> cards, int focusedCard, int rerollsLeft) {
      super((MenuType)ModMenus.I_WANT_LIMBO_MENU.get(), id);
      this.player = player;
      this.cards = cards;
      this.focusedCard = focusedCard;
      this.rerollsLeft = Math.max(0, rerollsLeft);
   }

   public List<IWantLimboData.StoredCard> getCards() {
      return this.cards;
   }

   public int getFocusedCard() {
      return this.focusedCard;
   }

   public int getRerollsLeft() {
      return this.rerollsLeft;
   }

   public static void writeSnapshot(RegistryFriendlyByteBuf buf, List<IWantLimboData.StoredCard> cards, int focusedCard, int rerollsLeft) {
      buf.writeInt(cards.size());

      for (IWantLimboData.StoredCard card : cards) {
         buf.writeInt(card.id());
         buf.writeResourceLocation(card.skill());
         buf.writeEnum(card.rarity());
      }

      buf.writeInt(focusedCard);
      buf.writeInt(Math.max(0, rerollsLeft));
   }

   private static List<IWantLimboData.StoredCard> readCards(RegistryFriendlyByteBuf buf) {
      int size = buf.readInt();
      List<IWantLimboData.StoredCard> cards = new ArrayList<>();

      for (int i = 0; i < size; i++) {
         int id = buf.readInt();
         ResourceLocation skill = buf.readResourceLocation();
         IWantLimboData.Rarity rarity = (IWantLimboData.Rarity)buf.readEnum(IWantLimboData.Rarity.class);
         cards.add(new IWantLimboData.StoredCard(id, skill, rarity));
      }

      return cards;
   }

   public boolean stillValid(Player player) {
      return this.player == player;
   }

   public ItemStack quickMoveStack(Player player, int index) {
      return ItemStack.EMPTY;
   }
}
