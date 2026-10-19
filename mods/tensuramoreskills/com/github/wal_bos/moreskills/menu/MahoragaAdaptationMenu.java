package com.github.wal_bos.moreskills.menu;

import com.github.wal_bos.moreskills.ability.skill.ultimate.DivineGeneralMahoragaSkill;
import com.github.wal_bos.moreskills.registry.menu.ModMenus;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

public class MahoragaAdaptationMenu extends AbstractContainerMenu {
   private final List<MahoragaAdaptationMenu.Entry> entries = new ArrayList<>();
   private int selectedIndex;
   private final boolean toggled;

   private MahoragaAdaptationMenu(int containerId, Inventory inventory, boolean toggled, int selectedIndex, List<MahoragaAdaptationMenu.Entry> entries) {
      super((MenuType)ModMenus.MAHORAGA_ADAPTATION_MENU.get(), containerId);
      this.toggled = toggled;
      this.selectedIndex = selectedIndex;
      this.entries.addAll(entries);
   }

   public static MahoragaAdaptationMenu server(int containerId, Inventory inventory, ServerPlayer player, boolean toggled) {
      List<MahoragaAdaptationMenu.Entry> entries = DivineGeneralMahoragaSkill.buildMenuEntries(player);
      int selected = DivineGeneralMahoragaSkill.getSelectedIndex(player, entries.size());
      return new MahoragaAdaptationMenu(containerId, inventory, toggled, selected, entries);
   }

   public static MahoragaAdaptationMenu client(int containerId, Inventory inventory, FriendlyByteBuf buf) {
      boolean toggled = buf.readBoolean();
      int selectedIndex = buf.readVarInt();
      int size = buf.readVarInt();
      List<MahoragaAdaptationMenu.Entry> entries = new ArrayList<>();

      for (int i = 0; i < size; i++) {
         boolean damage = buf.readBoolean();
         String id = buf.readUtf();
         boolean enabled = buf.readBoolean();
         int stage = buf.readVarInt();
         int reduction = buf.readVarInt();
         entries.add(new MahoragaAdaptationMenu.Entry(damage, id, enabled, stage, reduction));
      }

      return new MahoragaAdaptationMenu(containerId, inventory, toggled, selectedIndex, entries);
   }

   public static void writeExtraData(ServerPlayer player, FriendlyByteBuf buf, boolean toggled) {
      List<MahoragaAdaptationMenu.Entry> entries = DivineGeneralMahoragaSkill.buildMenuEntries(player);
      int selected = DivineGeneralMahoragaSkill.getSelectedIndex(player, entries.size());
      buf.writeBoolean(toggled);
      buf.writeVarInt(selected);
      buf.writeVarInt(entries.size());

      for (MahoragaAdaptationMenu.Entry entry : entries) {
         buf.writeBoolean(entry.damage());
         buf.writeUtf(entry.id());
         buf.writeBoolean(entry.enabled());
         buf.writeVarInt(entry.stage());
         buf.writeVarInt(entry.reduction());
      }
   }

   public List<MahoragaAdaptationMenu.Entry> getEntries() {
      return this.entries;
   }

   public MahoragaAdaptationMenu.Entry getEntry(int index) {
      return this.entries.get(index);
   }

   public int getSelectedIndex() {
      return this.selectedIndex;
   }

   public void setSelectedIndexClient(int index) {
      if (this.entries.isEmpty()) {
         this.selectedIndex = 0;
      } else {
         this.selectedIndex = Math.max(0, Math.min(index, this.entries.size() - 1));
      }
   }

   public void toggleEntryClient(int index) {
      if (index >= 0 && index < this.entries.size()) {
         MahoragaAdaptationMenu.Entry old = this.entries.get(index);
         this.entries.set(index, old.withEnabled(!old.enabled()));
         this.selectedIndex = index;
      }
   }

   public boolean isSkillToggled() {
      return this.toggled;
   }

   public List<Integer> getDamageIndices() {
      List<Integer> out = new ArrayList<>();

      for (int i = 0; i < this.entries.size(); i++) {
         if (this.entries.get(i).damage()) {
            out.add(i);
         }
      }

      return out;
   }

   public List<Integer> getEffectIndices() {
      List<Integer> out = new ArrayList<>();

      for (int i = 0; i < this.entries.size(); i++) {
         if (!this.entries.get(i).damage()) {
            out.add(i);
         }
      }

      return out;
   }

   public boolean stillValid(Player player) {
      return true;
   }

   public ItemStack quickMoveStack(Player player, int index) {
      return ItemStack.EMPTY;
   }

   public record Entry(boolean damage, String id, boolean enabled, int stage, int reduction) {
      public MahoragaAdaptationMenu.Entry withEnabled(boolean value) {
         return new MahoragaAdaptationMenu.Entry(this.damage, this.id, value, this.stage, this.reduction);
      }
   }
}
