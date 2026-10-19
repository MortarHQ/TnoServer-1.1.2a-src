package net.blay09.mods.waystones.network.message;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.blay09.mods.waystones.core.PlayerWaystoneManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class PlayerWaystoneCooldownsMessage implements CustomPacketPayload {
   public static final Type<PlayerWaystoneCooldownsMessage> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("waystones", "player_waystone_cooldowns"));
   private final Map<ResourceLocation, Long> cooldowns;

   public PlayerWaystoneCooldownsMessage(Map<ResourceLocation, Long> cooldowns) {
      this.cooldowns = cooldowns;
   }

   public static void encode(FriendlyByteBuf buf, PlayerWaystoneCooldownsMessage message) {
      buf.writeByte(message.cooldowns.size());

      for (Entry<ResourceLocation, Long> entry : message.cooldowns.entrySet()) {
         buf.writeResourceLocation(entry.getKey());
         buf.writeLong(entry.getValue());
      }
   }

   public static PlayerWaystoneCooldownsMessage decode(FriendlyByteBuf buf) {
      byte size = buf.readByte();
      HashMap<ResourceLocation, Long> cooldowns = new HashMap<>(size);

      for (int i = 0; i < size; i++) {
         cooldowns.put(buf.readResourceLocation(), buf.readLong());
      }

      return new PlayerWaystoneCooldownsMessage(cooldowns);
   }

   public static void handle(Player player, PlayerWaystoneCooldownsMessage message) {
      PlayerWaystoneManager.resetCooldowns(player);
      message.cooldowns.forEach((key, timestamp) -> PlayerWaystoneManager.setCooldownUntil(player, key, timestamp));
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
