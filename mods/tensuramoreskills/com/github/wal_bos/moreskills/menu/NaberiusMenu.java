package com.github.wal_bos.moreskills.menu;

import com.github.wal_bos.moreskills.ability.skill.shades.NaberiusSkill;
import com.github.wal_bos.moreskills.registry.menu.ModMenus;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

public class NaberiusMenu extends AbstractContainerMenu {
   private final Player player;
   private final DataSlot pageMode = DataSlot.standalone();
   private final List<NaberiusMenu.Entry> entries = new ArrayList<>();
   private int activeCount = 0;

   public NaberiusMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
      super((MenuType)ModMenus.NABERIUS_MENU.get(), id);
      this.player = inventory.player;
      this.addDataSlot(this.pageMode);
      int mode = buf != null ? buf.readVarInt() : 1;
      this.pageMode.set(mode);
      this.activeCount = buf != null ? buf.readVarInt() : 0;
      int size = buf != null ? buf.readVarInt() : 0;

      for (int i = 0; i < size; i++) {
         this.entries
            .add(
               new NaberiusMenu.Entry(
                  buf.readVarInt(), buf.readUtf(), buf.readDouble(), buf.readDouble(), buf.readVarInt(), buf.readBoolean(), buf.readBoolean()
               )
            );
      }
   }

   public NaberiusMenu(int id, Inventory inventory, int initialMode) {
      super((MenuType)ModMenus.NABERIUS_MENU.get(), id);
      this.player = inventory.player;
      this.addDataSlot(this.pageMode);
      this.pageMode.set(initialMode);
      this.rebuildEntries();
   }

   public void rebuildEntries() {
      this.entries.clear();
      this.activeCount = 0;
      if (this.player instanceof ServerPlayer sp) {
         this.activeCount = NaberiusSkill.getAliveActiveSummonCount(sp);

         for (NaberiusSkill.ArchiveEntryView view : NaberiusSkill.getArchiveEntries(sp)) {
            this.entries.add(new NaberiusMenu.Entry(view.index(), view.name(), view.hp(), view.ep(), view.count(), view.selected(), view.player()));
         }
      }
   }

   public static void writeExtraData(ServerPlayer player, FriendlyByteBuf buf, int mode) {
      buf.writeVarInt(mode);
      buf.writeVarInt(NaberiusSkill.getAliveActiveSummonCount(player));
      List<NaberiusSkill.ArchiveEntryView> entries = NaberiusSkill.getArchiveEntries(player);
      buf.writeVarInt(entries.size());

      for (NaberiusSkill.ArchiveEntryView view : entries) {
         buf.writeVarInt(view.index());
         buf.writeUtf(view.name());
         buf.writeDouble(view.hp());
         buf.writeDouble(view.ep());
         buf.writeVarInt(view.count());
         buf.writeBoolean(view.selected());
         buf.writeBoolean(view.player());
      }
   }

   public Player getPlayer() {
      return this.player;
   }

   public int getPageMode() {
      return this.pageMode.get();
   }

   public List<NaberiusMenu.Entry> getEntries() {
      return this.entries;
   }

   public int getActiveCount() {
      return this.activeCount;
   }

   public boolean stillValid(Player player) {
      return true;
   }

   public ItemStack quickMoveStack(Player player, int index) {
      return ItemStack.EMPTY;
   }

   public record Entry(int index, String name, double hp, double ep, int count, boolean selected, boolean player) {
   }
}
