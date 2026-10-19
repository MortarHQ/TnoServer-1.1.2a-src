package com.tom.storagemod.menu;

import com.tom.storagemod.Content;
import com.tom.storagemod.block.entity.InventoryCableConnectorBlockEntity;
import com.tom.storagemod.inventory.RemoteConnections;
import com.tom.storagemod.network.NetworkHandler;
import com.tom.storagemod.util.DataSlots;
import com.tom.storagemod.util.IDataReceiver;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class InventoryLinkMenu extends AbstractContainerMenu implements IDataReceiver {
   private InventoryCableConnectorBlockEntity te;
   private Inventory pinv;
   private boolean sentList;
   public int beaconLvl;

   public InventoryLinkMenu(int id, Inventory playerInv) {
      this(id, playerInv, null);
   }

   public InventoryLinkMenu(int id, Inventory playerInv, InventoryCableConnectorBlockEntity tile) {
      super(Content.inventoryLink.get(), id);
      this.te = tile;
      this.pinv = playerInv;
      this.addDataSlot(DataSlots.create(v -> this.beaconLvl = v, () -> this.te != null ? this.te.getBeaconLevel() : 0));
   }

   @Override
   public void receive(CompoundTag tag) {
      if (!this.pinv.player.isSpectator() && this.te != null) {
         UUID id = null;
         if (tag.contains("id")) {
            id = tag.getUUID("id");
         }

         if (id == null) {
            UUID chn = RemoteConnections.get(this.pinv.player.level()).makeChannel(tag.getString("d"), tag.getBoolean("p"), this.pinv.player);
            this.te.setChannel(chn);
         } else if (tag.getBoolean("select")) {
            RemoteConnections.Channel c = RemoteConnections.get(this.pinv.player.level()).getChannel(id);
            if (c != null && c.canAccess(this.pinv.player)) {
               this.te.setChannel(id);
            }
         } else if (tag.contains("p")) {
            RemoteConnections.get(this.pinv.player.level()).editChannel(id, tag.getBoolean("p"), this.pinv.player.getUUID());
         } else {
            RemoteConnections.get(this.pinv.player.level()).removeChannel(id, this.pinv.player.getUUID());
         }

         this.sentList = false;
      }
   }

   public boolean stillValid(Player p_38874_) {
      return this.te != null ? this.te.stillValid(p_38874_) : true;
   }

   public void broadcastChanges() {
      if (this.te != null) {
         if (!this.sentList) {
            CompoundTag mainTag = new CompoundTag();
            UUID chn = this.te.getChannel();
            if (chn != null) {
               mainTag.putUUID("selected", chn);
            }

            ListTag list = new ListTag();
            RemoteConnections.get(this.pinv.player.level()).streamChannels(this.pinv.player).map(InventoryLinkMenu.LinkChannel::new).forEach(c -> {
               CompoundTag t = new CompoundTag();
               c.saveToClient(t);
               list.add(t);
            });
            mainTag.put("list", list);
            NetworkHandler.sendTo((ServerPlayer)this.pinv.player, mainTag);
            this.sentList = true;
         }

         super.broadcastChanges();
      }
   }

   public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
      return ItemStack.EMPTY;
   }

   public static class LinkChannel {
      public UUID id;
      public String displayName;
      public boolean publicChannel;
      public UUID owner;
      public String ownerName;

      public LinkChannel(Entry<UUID, RemoteConnections.Channel> e) {
         RemoteConnections.Channel c = e.getValue();
         this.id = e.getKey();
         this.displayName = c.displayName;
         this.publicChannel = c.publicChannel;
         this.owner = c.owner;
         this.ownerName = c.ownerName;
      }

      public LinkChannel(CompoundTag tag) {
         this.id = tag.getUUID("id");
         this.displayName = tag.getString("d");
         this.publicChannel = tag.getBoolean("p");
         this.owner = tag.getUUID("o");
         this.ownerName = tag.getString("on");
      }

      public LinkChannel(boolean isPublic, String name) {
         this.publicChannel = isPublic;
         this.displayName = name;
      }

      public void saveToClient(CompoundTag t) {
         t.putUUID("id", this.id);
         t.putString("d", this.displayName);
         t.putBoolean("p", this.publicChannel);
         t.putUUID("o", this.owner);
         t.putString("on", this.ownerName == null ? "" : this.ownerName);
      }

      public void saveToServer(CompoundTag t) {
         t.putBoolean("p", this.publicChannel);
         t.putString("d", this.displayName);
      }

      public static void loadAll(ListTag list, Map<UUID, InventoryLinkMenu.LinkChannel> connections) {
         for (int i = 0; i < list.size(); i++) {
            CompoundTag t = list.getCompound(i);
            UUID channel = t.getUUID("id");
            connections.put(channel, new InventoryLinkMenu.LinkChannel(t));
         }
      }
   }
}
