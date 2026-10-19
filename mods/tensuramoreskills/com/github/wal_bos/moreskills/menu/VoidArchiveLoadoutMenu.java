package com.github.wal_bos.moreskills.menu;

import com.github.wal_bos.moreskills.ability.skill.unique.VoidArchiveForgeData;
import com.github.wal_bos.moreskills.ability.skill.unique.VoidArchiveSkill;
import com.github.wal_bos.moreskills.registry.menu.ModMenus;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

public class VoidArchiveLoadoutMenu extends AbstractContainerMenu {
   public static final int BUTTON_SELECT_MOVE_BASE = 1000;
   public static final int BUTTON_EQUIP_MOVE_BASE = 2000;
   public static final int BUTTON_UNEQUIP_MOVE_BASE = 3000;
   public static final int BUTTON_DELETE_MOVE_BASE = 4000;
   private final Player player;
   private final boolean isServer;
   private final List<String> moveNames = new ArrayList<>();
   private final List<String> moveSubs = new ArrayList<>();
   private final List<Integer> equipped = new ArrayList<>();
   private final DataSlot activeMove = DataSlot.standalone();
   private final DataSlot moveCount = DataSlot.standalone();

   private VoidArchiveLoadoutMenu(int containerId, Inventory inventory, Player player) {
      super((MenuType)ModMenus.VOID_ARCHIVE_LOADOUT_MENU.get(), containerId);
      this.player = player;
      this.isServer = true;
      this.addDataSlot(this.activeMove);
      this.addDataSlot(this.moveCount);
   }

   public static VoidArchiveLoadoutMenu server(int containerId, Inventory inventory, ServerPlayer player) {
      VoidArchiveLoadoutMenu menu = new VoidArchiveLoadoutMenu(containerId, inventory, player);
      menu.refreshFromServer();
      return menu;
   }

   public VoidArchiveLoadoutMenu(int containerId, Inventory inventory, RegistryFriendlyByteBuf buf) {
      super((MenuType)ModMenus.VOID_ARCHIVE_LOADOUT_MENU.get(), containerId);
      this.player = inventory.player;
      this.isServer = false;
      this.addDataSlot(this.activeMove);
      this.addDataSlot(this.moveCount);
      this.activeMove.set(buf.readVarInt());
      int size = buf.readVarInt();

      for (int i = 0; i < size; i++) {
         this.moveNames.add(buf.readUtf(32767));
         this.moveSubs.add(buf.readUtf(32767));
         this.equipped.add(buf.readBoolean() ? 1 : 0);
      }

      this.moveCount.set(this.moveNames.size());
   }

   public static void writeExtraData(ServerPlayer player, RegistryFriendlyByteBuf buf) {
      Optional<ManasSkillInstance> optional = getArchiveInstance(player);
      if (!optional.isEmpty() && optional.get().getSkill() instanceof VoidArchiveSkill holder) {
         ManasSkillInstance var9 = optional.get();
         buf.writeVarInt(holder.getSelectedCreatedMoveIndex(var9));
         List moves = holder.getCreatedMoves(var9);
         buf.writeVarInt(moves.size());

         for (int i = 0; i < moves.size(); i++) {
            VoidArchiveForgeData.CreatedMove move = (VoidArchiveForgeData.CreatedMove)moves.get(i);
            VoidArchiveForgeData.ComponentData data = VoidArchiveForgeData.describeMove(move);
            buf.writeUtf(data.title());
            buf.writeUtf(data.subtitle());
            buf.writeBoolean(holder.isCreatedMoveEquipped(var9, i));
         }
      } else {
         buf.writeVarInt(0);
         buf.writeVarInt(0);
      }
   }

   public boolean clickMenuButton(Player player, int buttonId) {
      Optional<ManasSkillInstance> optional = getArchiveInstance(player);
      if (!optional.isEmpty() && optional.get().getSkill() instanceof VoidArchiveSkill holder && player instanceof ServerPlayer sp) {
         ManasSkillInstance var8 = optional.get();
         if (buttonId >= 1000 && buttonId < 2000) {
            holder.setActiveCreatedMove(var8, buttonId - 1000);
            syncArchive(sp, var8);
            return true;
         } else if (buttonId >= 2000 && buttonId < 3000) {
            boolean ok = holder.equipCreatedMove(var8, buttonId - 2000, sp);
            syncArchive(sp, var8);
            return ok;
         } else if (buttonId >= 3000 && buttonId < 4000) {
            holder.unequipCreatedMove(var8, buttonId - 3000);
            syncArchive(sp, var8);
            return true;
         } else if (buttonId >= 4000) {
            holder.removeCreatedMove(var8, buttonId - 4000);
            syncArchive(sp, var8);
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private void refreshFromServer() {
      Optional<ManasSkillInstance> optional = getArchiveInstance(this.player);
      if (!optional.isEmpty() && optional.get().getSkill() instanceof VoidArchiveSkill holder) {
         ManasSkillInstance var7 = optional.get();
         this.moveNames.clear();
         this.moveSubs.clear();
         this.equipped.clear();
         List moves = holder.getCreatedMoves(var7);

         for (int i = 0; i < moves.size(); i++) {
            VoidArchiveForgeData.ComponentData data = VoidArchiveForgeData.describeMove((VoidArchiveForgeData.CreatedMove)moves.get(i));
            this.moveNames.add(data.title());
            this.moveSubs.add(data.subtitle());
            this.equipped.add(holder.isCreatedMoveEquipped(var7, i) ? 1 : 0);
         }

         this.activeMove.set(holder.getSelectedCreatedMoveIndex(var7));
         this.moveCount.set(this.moveNames.size());
      } else {
         this.moveNames.clear();
         this.moveSubs.clear();
         this.equipped.clear();
         this.activeMove.set(0);
         this.moveCount.set(0);
      }
   }

   public int getMoveCount() {
      return this.moveCount.get();
   }

   public int getActiveMoveIndex() {
      return this.activeMove.get();
   }

   public boolean isEquipped(int index) {
      return index >= 0 && index < this.equipped.size() && this.equipped.get(index) != 0;
   }

   public Component getMoveName(int index) {
      return index >= 0 && index < this.moveNames.size() ? Component.literal(this.moveNames.get(index)) : Component.literal("None");
   }

   public Component getMoveSubtitle(int index) {
      return index >= 0 && index < this.moveSubs.size() ? Component.literal(this.moveSubs.get(index)) : Component.empty();
   }

   private static Optional<ManasSkillInstance> getArchiveInstance(Player player) {
      if (player == null) {
         return Optional.empty();
      }

      Skills skills = SkillAPI.getSkillsFrom(player);
      return skills == null ? Optional.empty() : skills.getSkill(VoidArchiveSkill.SKILL_ID);
   }

   private static void syncArchive(ServerPlayer sp, ManasSkillInstance instance) {
      instance.markDirty();
      Skills skills = SkillAPI.getSkillsFrom(sp);
      if (skills != null) {
         skills.markDirty();
      }

      sp.containerMenu.broadcastChanges();
   }

   public void broadcastChanges() {
      super.broadcastChanges();
      if (this.isServer) {
         this.refreshFromServer();
      }
   }

   public ItemStack quickMoveStack(Player player, int index) {
      return ItemStack.EMPTY;
   }

   public boolean stillValid(Player player) {
      return true;
   }
}
