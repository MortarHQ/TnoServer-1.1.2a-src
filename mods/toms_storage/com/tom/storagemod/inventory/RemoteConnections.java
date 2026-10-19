package com.tom.storagemod.inventory;

import com.tom.storagemod.components.WorldPos;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;

public class RemoteConnections extends SavedData {
   private static final String CONNECTIONS_TAG = "connections";
   public static final String CHANNEL_ID = "id";
   public static final String OWNER_ID = "owner";
   public static final String PUBLIC_TAG = "public";
   public static final String DISPLAY_NAME = "name";
   private static final String ID = "toms_storage_rc";
   private static final Factory<RemoteConnections> FACTORY = new Factory(RemoteConnections::new, RemoteConnections::new, DataFixTypes.LEVEL);
   public static final String OWNER_NAME = "owner_name";
   private Map<UUID, RemoteConnections.Channel> connections = new HashMap<>();

   private RemoteConnections() {
   }

   private RemoteConnections(CompoundTag tag, Provider provider) {
      ListTag list = tag.getList("connections", 10);

      for (int i = 0; i < list.size(); i++) {
         CompoundTag t = list.getCompound(i);
         UUID channel = t.getUUID("id");
         this.connections.put(channel, new RemoteConnections.Channel(t));
      }
   }

   public static RemoteConnections get(Level world) {
      ServerLevel sw = (ServerLevel)world;
      return (RemoteConnections)sw.getServer().overworld().getDataStorage().computeIfAbsent(FACTORY, "toms_storage_rc");
   }

   public CompoundTag save(CompoundTag tag, Provider provider) {
      ListTag list = new ListTag();
      this.connections.forEach((k, v) -> {
         CompoundTag t = new CompoundTag();
         t.putUUID("id", k);
         v.save(t);
         list.add(t);
      });
      tag.put("connections", list);
      return tag;
   }

   public RemoteConnections.Channel getChannel(UUID connection) {
      return this.connections.get(connection);
   }

   public UUID makeChannel(String name, boolean isPublic, Player owner) {
      UUID id = UUID.randomUUID();
      if (name.isEmpty() || name.length() > 50) {
         name = "Channel " + System.currentTimeMillis();
      }

      this.connections.put(id, new RemoteConnections.Channel(owner.getGameProfile().getId(), owner.getGameProfile().getName(), isPublic, name));
      this.setDirty();
      return id;
   }

   public void removeChannel(UUID id, UUID player) {
      RemoteConnections.Channel c = this.connections.get(id);
      if (c != null && c.owner.equals(player)) {
         this.connections.remove(id);
         this.setDirty();
      }
   }

   public void editChannel(UUID id, boolean pub, UUID player) {
      RemoteConnections.Channel c = this.connections.get(id);
      if (c != null && c.owner.equals(player)) {
         c.publicChannel = pub;
         this.setDirty();
      }
   }

   public void invalidateCache(UUID id) {
      RemoteConnections.Channel c = this.connections.get(id);
      if (c != null) {
         c.connectors.clear();
      }
   }

   public Stream<Entry<UUID, RemoteConnections.Channel>> streamChannels(Player player) {
      return this.connections.entrySet().stream().filter(c -> c.getValue().canAccess(player));
   }

   public static class Channel {
      public Set<WorldPos> connectors = new HashSet<>();
      public UUID owner;
      public String ownerName;
      public boolean publicChannel;
      public String displayName;

      public Channel(UUID owner, String ownerName, boolean publicChannel, String displayName) {
         this.owner = owner;
         this.ownerName = ownerName;
         this.publicChannel = publicChannel;
         this.displayName = displayName;
      }

      private Channel(CompoundTag t) {
         this(t.getUUID("owner"), t.getString("owner_name"), t.getBoolean("public"), t.getString("name"));
      }

      public void register(ServerLevel world, BlockPos blockPos) {
         WorldPos pos = new WorldPos(world.dimension(), blockPos);
         this.connectors.add(pos);
      }

      public Set<IInventoryLink> findOthers(ServerLevel world, BlockPos blockPos, int lvl) {
         WorldPos pos = new WorldPos(world.dimension(), blockPos);
         this.connectors.add(pos);
         Set<IInventoryLink> found = new HashSet<>();
         Iterator<WorldPos> posItr = this.connectors.iterator();

         while (posItr.hasNext()) {
            WorldPos dimPos = posItr.next();
            if (!dimPos.equals(pos)) {
               if (dimPos.getBlockEntity(world) instanceof IInventoryLink link) {
                  if (link.isAccessibleFrom(world, blockPos, lvl)) {
                     found.add(link);
                  }
               } else {
                  posItr.remove();
               }
            }
         }

         return found;
      }

      public void save(CompoundTag t) {
         t.putUUID("owner", this.owner);
         t.putBoolean("public", this.publicChannel);
         t.putString("name", this.displayName);
         t.putString("owner_name", this.ownerName);
      }

      public boolean canAccess(Player player) {
         return this.publicChannel || this.owner.equals(player.getUUID());
      }
   }
}
