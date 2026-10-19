package net.blay09.mods.waystones.network.message;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.blay09.mods.balm.api.BalmEnvironment;
import net.blay09.mods.waystones.core.InMemoryPlayerWaystoneData;
import net.blay09.mods.waystones.core.PlayerWaystoneManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class SortingIndexMessage implements CustomPacketPayload {
   public static final Type<SortingIndexMessage> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("waystones", "sorting_index"));
   private final List<UUID> sortingIndex;

   public SortingIndexMessage(List<UUID> sortingIndex) {
      this.sortingIndex = sortingIndex;
   }

   public static void encode(FriendlyByteBuf buf, SortingIndexMessage message) {
      buf.writeShort(message.sortingIndex.size());

      for (UUID waystoneUid : message.sortingIndex) {
         buf.writeUUID(waystoneUid);
      }
   }

   public static SortingIndexMessage decode(FriendlyByteBuf buf) {
      int count = buf.readShort();
      ArrayList<UUID> sortingIndex = new ArrayList<>();

      for (int i = 0; i < count; i++) {
         sortingIndex.add(buf.readUUID());
      }

      return new SortingIndexMessage(sortingIndex);
   }

   public static void handle(Player player, SortingIndexMessage message) {
      InMemoryPlayerWaystoneData playerWaystoneData = (InMemoryPlayerWaystoneData)PlayerWaystoneManager.getPlayerWaystoneData(BalmEnvironment.CLIENT);
      playerWaystoneData.setSortingIndex(player, message.sortingIndex);
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
