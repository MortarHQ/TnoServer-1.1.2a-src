package com.github.wal_bos.moreskills.fungus;

import com.github.wal_bos.moreskills.ability.skill.unique.FungusInfectionSkill;
import com.github.wal_bos.moreskills.registry.menu.ModMenus;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

public class FungusInfectionMenu extends AbstractContainerMenu {
   public static final int BTN_PREV = 1;
   public static final int BTN_NEXT = 2;
   public static final int BTN_UP_DUR = 3;
   public static final int BTN_UP_AMP = 4;
   public static final int BTN_WS_SET_BASE = 10;
   public static final int BTN_WS_SET_1 = 10;
   public static final int BTN_WS_SET_2 = 11;
   public static final int BTN_WS_SET_3 = 12;
   public static final int BTN_WS_SET_4 = 13;
   public static final int BTN_WS_SET_5 = 14;
   public static final int BTN_WS_SET_6 = 15;
   public static final int BTN_WS_SET_7 = 16;
   public static final int BTN_WS_SET_8 = 17;
   public static final int BTN_WS_SET_9 = 18;
   public static final int BTN_WS_SET_10 = 19;
   public static final int BTN_SELECT_BASE = 20;
   private static final int PAGE_SIZE = 6;
   private static final int WORKSHOP_SLOTS = 10;
   private final Player player;
   private final boolean isServer;
   private final List<String> keys = new ArrayList<>();
   private final List<Integer> counts = new ArrayList<>();
   private final List<Integer> durLvls = new ArrayList<>();
   private final List<Integer> ampLvls = new ArrayList<>();
   private final List<Integer> spent = new ArrayList<>();
   private final DataSlot page = DataSlot.standalone();
   private final DataSlot selected = DataSlot.standalone();
   private final DataSlot selectedCount = DataSlot.standalone();
   private final DataSlot selectedDur = DataSlot.standalone();
   private final DataSlot selectedAmp = DataSlot.standalone();
   private final DataSlot selectedAvail = DataSlot.standalone();
   private final DataSlot workshopLimit = DataSlot.standalone();
   private final DataSlot[] ws = new DataSlot[10];

   public static FungusInfectionMenu server(int id, Inventory inv, ServerPlayer player) {
      FungusInfectionMenu menu = new FungusInfectionMenu(id, inv, player);
      menu.refreshFromPlayer();
      return menu;
   }

   private FungusInfectionMenu(int id, Inventory inv, Player player) {
      super((MenuType)ModMenus.FUNGUS_INFECTION_MENU.get(), id);
      this.player = player;
      this.isServer = true;
      this.addCommonDataSlots();

      for (DataSlot slot : this.ws) {
         slot.set(-1);
      }
   }

   public FungusInfectionMenu(int id, Inventory inv, RegistryFriendlyByteBuf buf) {
      super((MenuType)ModMenus.FUNGUS_INFECTION_MENU.get(), id);
      this.player = inv.player;
      this.isServer = false;
      int size = buf.readVarInt();

      for (int i = 0; i < size; i++) {
         this.keys.add(buf.readUtf(32767));
         this.counts.add(buf.readVarInt());
         this.durLvls.add(buf.readVarInt());
         this.ampLvls.add(buf.readVarInt());
         this.spent.add(buf.readVarInt());
      }

      int limit = buf.readVarInt();
      int[] read = new int[10];

      for (int i = 0; i < 10; i++) {
         read[i] = buf.readVarInt();
      }

      this.addCommonDataSlots();
      this.workshopLimit.set(limit);

      for (int i = 0; i < 10; i++) {
         this.ws[i].set(read[i]);
      }

      this.clampSelected();
      this.refreshSelectedClient();
   }

   private void addCommonDataSlots() {
      this.addDataSlot(this.page);
      this.addDataSlot(this.selected);
      this.addDataSlot(this.selectedCount);
      this.addDataSlot(this.selectedDur);
      this.addDataSlot(this.selectedAmp);
      this.addDataSlot(this.selectedAvail);
      this.addDataSlot(this.workshopLimit);

      for (int i = 0; i < 10; i++) {
         this.ws[i] = DataSlot.standalone();
         this.addDataSlot(this.ws[i]);
      }
   }

   public List<String> getKeysPage() {
      int currentPage = this.page.get();
      int from = currentPage * 6;
      int to = Math.min(this.keys.size(), from + 6);
      return from >= to ? List.of() : this.keys.subList(from, to);
   }

   public int getPage() {
      return this.page.get();
   }

   public int getMaxPage() {
      return this.keys.isEmpty() ? 0 : (this.keys.size() - 1) / 6;
   }

   public int getWorkshopLimit() {
      return this.workshopLimit.get();
   }

   public int getWorkshopIndex(int slot) {
      return slot >= 0 && slot < 10 ? this.ws[slot].get() : -1;
   }

   public String getWorkshopKey(int slot) {
      int idx = this.getWorkshopIndex(slot);
      return idx >= 0 && idx < this.keys.size() ? this.keys.get(idx) : "";
   }

   public int getSelectedCount() {
      return this.selectedCount.get();
   }

   public int getSelectedDur() {
      return this.selectedDur.get();
   }

   public int getSelectedAmp() {
      return this.selectedAmp.get();
   }

   public int getSelectedAvail() {
      return this.selectedAvail.get();
   }

   public String getSelectedKey() {
      int idx = this.selected.get();
      return idx >= 0 && idx < this.keys.size() ? this.keys.get(idx) : "";
   }

   public boolean clickMenuButton(Player player, int id) {
      if (!this.isServer) {
         return false;
      }

      if (player instanceof ServerPlayer serverPlayer) {
         if (id == 1) {
            int current = this.page.get();
            if (current > 0) {
               this.page.set(current - 1);
            }

            this.clampSelected();
            this.refreshFromPlayer();
            return true;
         } else if (id == 2) {
            int current = this.page.get();
            int max = this.getMaxPage();
            if (current < max) {
               this.page.set(current + 1);
            }

            this.clampSelected();
            this.refreshFromPlayer();
            return true;
         } else {
            if (id == 3) {
               this.upgrade(serverPlayer, true);
               this.refreshFromPlayer();
               return true;
            }

            if (id == 4) {
               this.upgrade(serverPlayer, false);
               this.refreshFromPlayer();
               return true;
            }

            if (id >= 10 && id < 20) {
               return this.setWorkshopSlot(serverPlayer, id - 10);
            }

            if (id >= 20 && id < 26) {
               int local = id - 20;
               int abs = this.page.get() * 6 + local;
               if (abs >= 0 && abs < this.keys.size()) {
                  this.selected.set(abs);
                  this.refreshFromPlayer();
                  return true;
               }
            }

            return false;
         }
      } else {
         return false;
      }
   }

   private boolean setWorkshopSlot(ServerPlayer player, int slot) {
      int limit = FungusInfectionSkill.getWorkshopLimit(player);
      if (slot >= limit) {
         return false;
      } else {
         int idx = this.selected.get();
         if (idx >= 0 && idx < this.keys.size()) {
            FungusInfectionSkill.setWorkshopKey(player, slot, this.keys.get(idx));
            this.refreshFromPlayer();
            return true;
         } else {
            return false;
         }
      }
   }

   private void upgrade(ServerPlayer player, boolean duration) {
      int idx = this.selected.get();
      if (idx >= 0 && idx < this.keys.size()) {
         String key = this.keys.get(idx);
         int count = FungusInfectionSkill.getAnalyzeCount(player, key);
         int spentPoints = FungusInfectionSkill.getSpent(player, key);
         int available = Math.max(0, count - spentPoints);
         if (available >= 2) {
            if (duration) {
               int level = FungusInfectionSkill.getUpgradeDur(player, key);
               FungusInfectionSkill.setUpgradeDur(player, key, level + 1);
            } else {
               int level = FungusInfectionSkill.getUpgradeAmp(player, key);
               FungusInfectionSkill.setUpgradeAmp(player, key, level + 1);
            }

            FungusInfectionSkill.setSpent(player, key, spentPoints + 2);
         }
      }
   }

   private void refreshFromPlayer() {
      if (this.player instanceof ServerPlayer serverPlayer) {
         this.keys.clear();
         this.counts.clear();
         this.durLvls.clear();
         this.ampLvls.clear();
         this.spent.clear();
         FungusInfectionSkill.fillStorageLists(serverPlayer, this.keys, this.counts, this.durLvls, this.ampLvls, this.spent);
         int var5 = this.getMaxPage();
         if (this.page.get() > var5) {
            this.page.set(var5);
         }

         this.clampSelected();
         int limit = FungusInfectionSkill.getWorkshopLimit(serverPlayer);
         this.workshopLimit.set(limit);

         for (int i = 0; i < 10; i++) {
            this.setWsIndex(serverPlayer, i, this.ws[i]);
         }

         this.refreshSelectedFromLists();
      }
   }

   private void setWsIndex(ServerPlayer player, int slot, DataSlot out) {
      String key = FungusInfectionSkill.getWorkshopKey(player, slot);
      if (key.isEmpty()) {
         out.set(-1);
      } else {
         out.set(this.keys.indexOf(key));
      }
   }

   private void refreshSelectedClient() {
      this.refreshSelectedFromLists();
   }

   private void refreshSelectedFromLists() {
      int idx = this.selected.get();
      if (idx >= 0 && idx < this.keys.size()) {
         this.selectedCount.set(this.counts.get(idx));
         this.selectedDur.set(this.durLvls.get(idx));
         this.selectedAmp.set(this.ampLvls.get(idx));
         this.selectedAvail.set(Math.max(0, this.counts.get(idx) - this.spent.get(idx)));
      } else {
         this.selectedCount.set(0);
         this.selectedDur.set(0);
         this.selectedAmp.set(0);
         this.selectedAvail.set(0);
      }
   }

   private void clampSelected() {
      if (this.keys.isEmpty()) {
         this.selected.set(0);
      } else {
         int idx = this.selected.get();
         if (idx < 0) {
            idx = 0;
         }

         if (idx >= this.keys.size()) {
            idx = this.keys.size() - 1;
         }

         this.selected.set(idx);
      }
   }

   public void broadcastChanges() {
      super.broadcastChanges();
      if (this.isServer) {
         this.refreshFromPlayer();
      } else {
         this.refreshSelectedClient();
      }
   }

   public boolean stillValid(Player player) {
      return true;
   }

   public ItemStack quickMoveStack(Player player, int slot) {
      return ItemStack.EMPTY;
   }
}
