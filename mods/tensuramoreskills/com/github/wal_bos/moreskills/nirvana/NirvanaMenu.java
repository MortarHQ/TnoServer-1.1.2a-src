package com.github.wal_bos.moreskills.nirvana;

import com.github.wal_bos.moreskills.registry.menu.ModMenus;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

public class NirvanaMenu extends AbstractContainerMenu {
   private final Player player;
   private final long karma;
   private final NirvanaData.Sort sort;
   private final List<CompoundTag> lives;
   private final List<String> unlocked;

   public static NirvanaMenu server(int id, Inventory inv, Player player) {
      return server(id, inv, player, NirvanaData.Sort.NEWEST);
   }

   public static NirvanaMenu server(int id, Inventory inv, Player player, NirvanaData.Sort sort) {
      return new NirvanaMenu(id, inv, player, NirvanaData.getKarma(player), sort, NirvanaData.sortedLives(player, sort), NirvanaData.unlockedStrings(player));
   }

   private NirvanaMenu(int id, Inventory inv, Player player, long karma, NirvanaData.Sort sort, List<CompoundTag> lives, List<String> unlocked) {
      super((MenuType)ModMenus.NIRVANA_ARCHIVE_MENU.get(), id);
      this.player = player;
      this.karma = karma;
      this.sort = sort;
      this.lives = lives;
      this.unlocked = unlocked;
   }

   public NirvanaMenu(int id, Inventory inv, RegistryFriendlyByteBuf buf) {
      this(id, inv, inv.player, NirvanaData.readMenuSnapshot(buf));
   }

   private NirvanaMenu(int id, Inventory inv, Player player, NirvanaData.Snapshot snapshot) {
      this(id, inv, player, snapshot.karma(), snapshot.sort(), snapshot.lives(), snapshot.unlocked());
   }

   public long getKarma() {
      return this.karma;
   }

   public NirvanaData.Sort getSort() {
      return this.sort;
   }

   public List<CompoundTag> getLives() {
      return this.lives;
   }

   public List<String> getUnlocked() {
      return this.unlocked;
   }

   public boolean isUnlocked(CompoundTag life, String skill, int mode) {
      if (!life.hasUUID("Id")) {
         return false;
      }

      String key = life.getUUID("Id") + "|" + skill + "|" + mode;
      return this.unlocked.contains(key);
   }

   public ItemStack quickMoveStack(Player player, int index) {
      return ItemStack.EMPTY;
   }

   public boolean stillValid(Player player) {
      return this.player == player;
   }
}
