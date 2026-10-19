package bigchadguys.sellingbin.world.data;

import bigchadguys.sellingbin.SellingBinInventory;
import bigchadguys.sellingbin.block.BinMaterial;
import bigchadguys.sellingbin.data.adapter.Adapters;
import bigchadguys.sellingbin.data.serializable.ISerializable;
import bigchadguys.sellingbin.init.ModConfigs;
import bigchadguys.sellingbin.init.ModWorldData;
import bigchadguys.sellingbin.net.SellingBinEntriesS2CPacket;
import bigchadguys.sellingbin.net.SellingBinTradesS2CPacket;
import bigchadguys.sellingbin.trade.Trade;
import com.google.gson.JsonObject;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.event.events.common.TickEvent;
import dev.architectury.event.events.common.PlayerEvent.PlayerJoin;
import dev.architectury.event.events.common.TickEvent.Server;
import dev.architectury.networking.NetworkManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class SellingBinData extends WorldData {
   public static List<Trade> CLIENT_TRADES;
   public static Map<String, Integer> CLIENT_TIME_LEFT = new HashMap<>();
   private List<Trade> lastTrades;
   private final Map<UUID, Map<String, SellingBinData.Entry>> entries = new HashMap<>();

   public SellingBinData.Entry getOrCreate(Player player, BinMaterial material) {
      return this.entries.computeIfAbsent(player.getUUID(), unused -> new HashMap<>()).computeIfAbsent(material.getId(), unused -> new SellingBinData.Entry());
   }

   public void onTick(MinecraftServer server) {
      if (this.lastTrades == null) {
         this.lastTrades = ModConfigs.SELLING_BIN.getTrades();
      }

      if (this.lastTrades != ModConfigs.SELLING_BIN.getTrades()) {
         this.lastTrades = ModConfigs.SELLING_BIN.getTrades();

         for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            this.sendTradesToClient(player);
         }
      }

      if (server.getTickCount() % 10 == 0) {
         for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            this.sendEntriesToClient(player);
         }
      }

      this.entries.forEach((uuid, entries) -> entries.forEach((id, entry) -> {
         BinMaterial material = BinMaterial.of(id);
         if (material != null) {
            if (entry.getTimeLeft() > material.getUpdateDelayTicks()) {
               entry.setTimeLeft(material.getUpdateDelayTicks());
            }

            if (entry.getTimeLeft() < 0L) {
               entry.inventory.executeTrades();
               entry.setTimeLeft(material.getUpdateDelayTicks());
            }

            entry.setTimeLeft(entry.getTimeLeft() - 1L);
         }
      }));
   }

   public void sendTradesToClient(ServerPlayer player) {
      NetworkManager.sendToPlayer(player, new SellingBinTradesS2CPacket(ModConfigs.SELLING_BIN.getTrades()));
   }

   public void sendEntriesToClient(ServerPlayer player) {
      Map<String, Integer> timeLeft = new HashMap<>();
      this.entries.getOrDefault(player.getUUID(), new HashMap<>()).forEach((key, value) -> timeLeft.put(key, (int)value.getTimeLeft()));
      if (!timeLeft.isEmpty()) {
         NetworkManager.sendToPlayer(player, new SellingBinEntriesS2CPacket(timeLeft));
      }
   }

   @Override
   public Optional<CompoundTag> writeNbt() {
      CompoundTag nbt = new CompoundTag();
      CompoundTag entriesNbt = new CompoundTag();
      this.entries.forEach((uuid, entries) -> {
         CompoundTag entryNbt = new CompoundTag();
         entries.forEach((id, entry) -> entry.writeNbt().ifPresent(tag -> entryNbt.put(id, tag)));
         entriesNbt.put(uuid.toString(), entryNbt);
      });
      nbt.put("entries", entriesNbt);
      return Optional.of(nbt);
   }

   public void readNbt(CompoundTag nbt) {
      CompoundTag entriesNbt = nbt.getCompound("entries");
      this.entries.clear();

      for (String key : entriesNbt.getAllKeys()) {
         UUID uuid = UUID.fromString(key);
         Map<String, SellingBinData.Entry> map = new HashMap<>();
         CompoundTag entryNbt = entriesNbt.getCompound(key);

         for (String key2 : entryNbt.getAllKeys()) {
            SellingBinData.Entry entry = new SellingBinData.Entry();
            entry.readNbt(entryNbt.getCompound(key2));
            map.put(key2, entry);
         }

         this.entries.put(uuid, map);
      }
   }

   public boolean isDirty() {
      return true;
   }

   public static void initCommon() {
      PlayerEvent.PLAYER_JOIN.register((PlayerJoin)player -> {
         SellingBinData data = ModWorldData.SELLING_BIN.getGlobal(player.serverLevel());
         data.sendTradesToClient(player);
         data.sendEntriesToClient(player);
      });
      TickEvent.SERVER_POST.register((Server)server -> {
         SellingBinData data = ModWorldData.SELLING_BIN.getGlobal(server);
         data.onTick(server);
      });
   }

   public static class Entry implements ISerializable<CompoundTag, JsonObject> {
      private final SellingBinInventory inventory = new SellingBinInventory(null, 0);
      private long timeLeft = 0L;

      public SellingBinInventory getInventory() {
         return this.inventory;
      }

      public long getTimeLeft() {
         return this.timeLeft;
      }

      public void setTimeLeft(long timeLeft) {
         this.timeLeft = timeLeft;
      }

      @Override
      public Optional<CompoundTag> writeNbt() {
         return Optional.of(new CompoundTag()).map(nbt -> {
            this.inventory.writeNbt().ifPresent(tag -> nbt.put("inventory", tag));
            Adapters.LONG.writeNbt(this.timeLeft).ifPresent(tag -> nbt.put("timeLeft", tag));
            return (CompoundTag)nbt;
         });
      }

      public void readNbt(CompoundTag nbt) {
         this.inventory.readNbt(nbt.getCompound("inventory"));
         this.timeLeft = Adapters.LONG.readNbt(nbt.get("timeLeft")).orElse(0L);
      }
   }
}
