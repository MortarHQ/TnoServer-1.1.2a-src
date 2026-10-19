package com.github.wal_bos.moreskills.gene;

import com.github.wal_bos.moreskills.ability.skill.unique.GeneEditorSkill;
import com.github.wal_bos.moreskills.item.GeneticSyringeItem;
import com.github.wal_bos.moreskills.registry.menu.ModMenus;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class GeneEditorMenu extends AbstractContainerMenu {
   public static final int BTN_PREV = 1;
   public static final int BTN_NEXT = 2;
   public static final int BTN_ADD = 3;
   public static final int BTN_REMOVE = 4;
   public static final int BTN_CLEAR = 5;
   public static final int BTN_WRITE = 6;
   public static final int BTN_ADD_MAX = 7;
   public static final int BTN_REMOVE_MAX = 8;
   public static final int BTN_SELECT_BASE = 40;
   public static final int BTN_ADD_AMOUNT_BASE = 1000000;
   public static final int BTN_REMOVE_AMOUNT_BASE = 1100000000;
   public static final int AMOUNT_ENCODE_LIMIT = 999000000;
   public static final int SYRINGE_SLOT = 0;
   public static final int HOTBAR_START = 1;
   public static final int PAGE_SIZE = 5;
   private static final int SYRINGE_X = 387;
   private static final int SYRINGE_Y = 52;
   private static final int HOTBAR_X = 147;
   private static final int HOTBAR_Y = 214;
   private final Player player;
   private final boolean isServer;
   private final SimpleContainer syringeContainer = new SimpleContainer(1);
   private final List<String> keys = new ArrayList<>();
   private final List<String> counts = new ArrayList<>();
   private final List<String> chosen = new ArrayList<>();
   private final List<String> workshopKeys = new ArrayList<>();
   private final List<String> workshopAmounts = new ArrayList<>();
   private final DataSlot page = DataSlot.standalone();
   private final DataSlot selected = DataSlot.standalone();
   private final DataSlot workshopSize = DataSlot.standalone();

   public static GeneEditorMenu server(int id, Inventory inv, ServerPlayer player) {
      GeneEditorMenu menu = new GeneEditorMenu(id, inv, player);
      menu.refreshFromPlayer();
      return menu;
   }

   private GeneEditorMenu(int id, Inventory inv, Player player) {
      super((MenuType)ModMenus.GENE_EDITOR_MENU.get(), id);
      this.player = player;
      this.isServer = true;
      this.addSlots(inv);
      this.addCommonDataSlots();
   }

   public GeneEditorMenu(int id, Inventory inv, RegistryFriendlyByteBuf buf) {
      super((MenuType)ModMenus.GENE_EDITOR_MENU.get(), id);
      this.player = inv.player;
      this.isServer = false;
      int size = buf.readVarInt();

      for (int i = 0; i < size; i++) {
         this.keys.add(buf.readUtf(32767));
         this.counts.add(buf.readUtf(32767));
         this.chosen.add(buf.readUtf(32767));
      }

      int wsSize = buf.readVarInt();

      for (int i = 0; i < wsSize; i++) {
         this.workshopKeys.add(buf.readUtf(32767));
         this.workshopAmounts.add(buf.readUtf(32767));
      }

      this.workshopSize.set(this.workshopKeys.size());
      this.addSlots(inv);
      this.addCommonDataSlots();
      this.clampSelected();
   }

   private void addSlots(Inventory inv) {
      this.addSlot(new Slot(this.syringeContainer, 0, 387, 52) {
         public boolean mayPlace(ItemStack stack) {
            return stack.getItem() instanceof GeneticSyringeItem;
         }

         public int getMaxStackSize() {
            return 1;
         }
      });

      for (int col = 0; col < 9; col++) {
         this.addSlot(new Slot(inv, col, 147 + col * 18, 214));
      }
   }

   private void addCommonDataSlots() {
      this.addDataSlot(this.page);
      this.addDataSlot(this.selected);
      this.addDataSlot(this.workshopSize);
   }

   public static int encodeAddAmount(int amount) {
      return amount >= 999000000 ? 7 : 1000000 + Math.max(1, amount);
   }

   public static int encodeRemoveAmount(int amount) {
      return amount >= 999000000 ? 8 : 1100000000 + Math.max(1, amount);
   }

   public List<String> getKeysPage() {
      int from = this.page.get() * 5;
      int to = Math.min(this.keys.size(), from + 5);
      return from >= to ? List.of() : this.keys.subList(from, to);
   }

   public int getPage() {
      return this.page.get();
   }

   public int getMaxPage() {
      return this.keys.isEmpty() ? 0 : (this.keys.size() - 1) / 5;
   }

   public String getSelectedKey() {
      int idx = this.selected.get();
      return idx >= 0 && idx < this.keys.size() ? this.keys.get(idx) : "";
   }

   public String getSelectedAvailableText() {
      int idx = this.selected.get();
      return idx >= 0 && idx < this.counts.size() ? GeneEditorSkill.formatAmount(this.getSelectedKey(), raw(this.counts.get(idx))) : "0";
   }

   public String getSelectedChosenText() {
      int idx = this.selected.get();
      return idx >= 0 && idx < this.chosen.size() ? GeneEditorSkill.formatAmount(this.getSelectedKey(), raw(this.chosen.get(idx))) : "0";
   }

   public boolean selectedIsStat() {
      return this.getSelectedKey().startsWith("stat:");
   }

   public int getWorkshopSize() {
      return this.workshopSize.get();
   }

   public String getWorkshopKey(int index) {
      return index >= 0 && index < this.workshopKeys.size() ? this.workshopKeys.get(index) : "";
   }

   public String getWorkshopAmountText(int index) {
      return index >= 0 && index < this.workshopAmounts.size()
         ? GeneEditorSkill.formatAmount(this.getWorkshopKey(index), raw(this.workshopAmounts.get(index)))
         : "";
   }

   public String displayName(String key) {
      return GeneEditorSkill.getDisplayName(key);
   }

   public ItemStack getSyringeStack() {
      return this.syringeContainer.getItem(0);
   }

   public void clientSelectPageButton(int id) {
      if (!this.isServer) {
         if (id == 1) {
            if (this.page.get() > 0) {
               this.page.set(this.page.get() - 1);
            }

            this.clampSelected();
         } else if (id == 2) {
            if (this.page.get() < this.getMaxPage()) {
               this.page.set(this.page.get() + 1);
            }

            this.clampSelected();
         } else if (id >= 40 && id < 45) {
            int abs = this.page.get() * 5 + id - 40;
            if (abs >= 0 && abs < this.keys.size()) {
               this.selected.set(abs);
            }
         } else if (id != 5 && id != 6) {
            if (id != 3 && id != 7 && (id < 1000000 || id >= 1000000000)) {
               if (id == 4 || id == 8 || id >= 1100000000) {
                  double amount = id == 8 ? Double.MAX_VALUE : (id == 4 ? 1.0 : id - 1100000000);
                  this.removeClient(this.getSelectedKey(), amount);
               }
            } else {
               double amount = id == 7 ? Double.MAX_VALUE : (id == 3 ? 1.0 : id - 1000000);
               this.addClient(this.getSelectedKey(), amount);
            }
         } else {
            if (id == 5) {
               this.returnWorkshopToArchiveClient();
            }

            if (id == 6) {
               this.clearWorkshopClient();
            }
         }
      }
   }

   public boolean clickMenuButton(Player player, int id) {
      if (!this.isServer) {
         return false;
      }

      if (player instanceof ServerPlayer serverPlayer) {
         this.processSyringeSlot(serverPlayer);
         if (id >= 1000000 && id < 1000000000) {
            GeneEditorSkill.addWorkshopKey(serverPlayer, this.getSelectedKey(), id - 1000000);
            this.refreshFromPlayer();
            return true;
         }

         if (id >= 1100000000) {
            GeneEditorSkill.removeWorkshopKey(serverPlayer, this.getSelectedKey(), id - 1100000000);
            this.refreshFromPlayer();
            return true;
         }

         if (id == 1) {
            if (this.page.get() > 0) {
               this.page.set(this.page.get() - 1);
            }

            this.refreshFromPlayer();
            return true;
         } else if (id == 2) {
            if (this.page.get() < this.getMaxPage()) {
               this.page.set(this.page.get() + 1);
            }

            this.refreshFromPlayer();
            return true;
         } else {
            if (id == 3 || id == 7) {
               GeneEditorSkill.addWorkshopKey(serverPlayer, this.getSelectedKey(), id == 7 ? Double.MAX_VALUE : 1.0);
               this.refreshFromPlayer();
               return true;
            }

            if (id == 4 || id == 8) {
               GeneEditorSkill.removeWorkshopKey(serverPlayer, this.getSelectedKey(), id == 8 ? Double.MAX_VALUE : 1.0);
               this.refreshFromPlayer();
               return true;
            }

            if (id == 5) {
               GeneEditorSkill.clearWorkshop(serverPlayer);
               this.refreshFromPlayer();
               return true;
            }

            if (id == 6) {
               this.processSyringeSlot(serverPlayer);
               GeneEditorSkill.writeWorkshopToSyringe(serverPlayer, this.syringeContainer.getItem(0));
               this.syringeContainer.setChanged();
               this.refreshFromPlayer();
               return true;
            }

            if (id >= 40 && id < 45) {
               int abs = this.page.get() * 5 + id - 40;
               if (abs >= 0 && abs < this.keys.size()) {
                  this.selected.set(abs);
               }

               this.refreshFromPlayer();
               return true;
            } else {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   private void processSyringeSlot(ServerPlayer serverPlayer) {
      ItemStack stack = this.syringeContainer.getItem(0);
      if (!stack.isEmpty() && stack.getItem() instanceof GeneticSyringeItem && GeneticSyringeItem.isSample(stack)) {
         GeneEditorSkill.recordSampleSyringe(serverPlayer, stack);
         this.syringeContainer.setChanged();
      }
   }

   private void refreshFromPlayer() {
      if (this.player instanceof ServerPlayer serverPlayer) {
         this.keys.clear();
         this.counts.clear();
         this.chosen.clear();
         this.workshopKeys.clear();
         this.workshopAmounts.clear();
         GeneEditorSkill.fillStorageLists(serverPlayer, this.keys, this.counts, this.chosen);

         for (String key : GeneEditorSkill.getWorkshopKeys(serverPlayer)) {
            this.workshopKeys.add(key);
            this.workshopAmounts.add(Double.toString(GeneEditorSkill.getWorkshopAmount(serverPlayer, key)));
         }

         if (this.page.get() > this.getMaxPage()) {
            this.page.set(this.getMaxPage());
         }

         this.clampSelected();
         this.workshopSize.set(this.workshopKeys.size());
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

   private void addClient(String key, double requested) {
      int idx = this.keys.indexOf(key);
      if (idx >= 0 && !(requested <= 0.0)) {
         double available = raw(this.counts.get(idx));
         if (!(available <= 0.0)) {
            double add = key.startsWith("skill:") ? 1.0 : requested;
            if (!key.startsWith("skill:") && requested >= 4.4942328371557893E307) {
               add = available;
            }

            add = Math.min(available, Math.max(1.0, Math.floor(add)));
            if (!(add <= 0.0)) {
               this.counts.set(idx, Double.toString(available - add));
               this.chosen.set(idx, Double.toString(raw(this.chosen.get(idx)) + add));
               int w = this.workshopKeys.indexOf(key);
               if (w < 0) {
                  this.workshopKeys.add(key);
                  this.workshopAmounts.add(Double.toString(add));
               } else {
                  this.workshopAmounts.set(w, Double.toString(raw(this.workshopAmounts.get(w)) + add));
               }

               this.workshopSize.set(this.workshopKeys.size());
            }
         }
      }
   }

   private void removeClient(String key, double requested) {
      int idx = this.keys.indexOf(key);
      int w = this.workshopKeys.indexOf(key);
      if (idx >= 0 && w >= 0 && !(requested <= 0.0)) {
         double current = raw(this.workshopAmounts.get(w));
         if (!(current <= 0.0)) {
            double remove = key.startsWith("skill:") ? 1.0 : requested;
            if (!key.startsWith("skill:") && requested >= 4.4942328371557893E307) {
               remove = current;
            }

            remove = Math.min(current, Math.max(1.0, Math.floor(remove)));
            this.counts.set(idx, Double.toString(raw(this.counts.get(idx)) + remove));
            this.chosen.set(idx, Double.toString(Math.max(0.0, raw(this.chosen.get(idx)) - remove)));
            double left = current - remove;
            if (left <= 0.0) {
               this.workshopKeys.remove(w);
               this.workshopAmounts.remove(w);
            } else {
               this.workshopAmounts.set(w, Double.toString(left));
            }

            this.workshopSize.set(this.workshopKeys.size());
         }
      }
   }

   private void returnWorkshopToArchiveClient() {
      for (int i = 0; i < this.workshopKeys.size(); i++) {
         String key = this.workshopKeys.get(i);
         int idx = this.keys.indexOf(key);
         if (idx >= 0) {
            double amount = raw(this.workshopAmounts.get(i));
            this.counts.set(idx, Double.toString(raw(this.counts.get(idx)) + amount));
            this.chosen.set(idx, Double.toString(Math.max(0.0, raw(this.chosen.get(idx)) - amount)));
         }
      }

      this.clearWorkshopClient();
   }

   private void clearWorkshopClient() {
      this.workshopKeys.clear();
      this.workshopAmounts.clear();

      for (int i = 0; i < this.chosen.size(); i++) {
         this.chosen.set(i, "0");
      }

      this.workshopSize.set(0);
   }

   private static double raw(String text) {
      if (text != null && !text.isBlank()) {
         try {
            return Double.parseDouble(text);
         } catch (Exception ignored) {
            return 0.0;
         }
      } else {
         return 0.0;
      }
   }

   public void broadcastChanges() {
      if (this.isServer && this.player instanceof ServerPlayer serverPlayer) {
         this.processSyringeSlot(serverPlayer);
      }

      super.broadcastChanges();
      if (this.isServer) {
         this.refreshFromPlayer();
      }
   }

   public boolean stillValid(Player player) {
      return true;
   }

   public ItemStack quickMoveStack(Player player, int index) {
      ItemStack result = ItemStack.EMPTY;
      Slot slot = (Slot)this.slots.get(index);
      if (slot != null && slot.hasItem()) {
         ItemStack stack = slot.getItem();
         result = stack.copy();
         if (index == 0) {
            if (!this.moveItemStackTo(stack, 1, 10, true)) {
               return ItemStack.EMPTY;
            }
         } else {
            if (!(stack.getItem() instanceof GeneticSyringeItem)) {
               return ItemStack.EMPTY;
            }

            if (!this.moveItemStackTo(stack, 0, 1, false)) {
               return ItemStack.EMPTY;
            }
         }

         if (stack.isEmpty()) {
            slot.setByPlayer(ItemStack.EMPTY);
         } else {
            slot.setChanged();
         }

         return result;
      } else {
         return ItemStack.EMPTY;
      }
   }

   public void removed(Player player) {
      super.removed(player);
      if (!player.level().isClientSide()) {
         ItemStack stack = this.syringeContainer.removeItemNoUpdate(0);
         if (!stack.isEmpty() && !player.getInventory().add(stack)) {
            player.drop(stack, false);
         }
      }
   }
}
