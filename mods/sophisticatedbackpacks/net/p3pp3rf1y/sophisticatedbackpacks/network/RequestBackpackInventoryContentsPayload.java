package net.p3pp3rf1y.sophisticatedbackpacks.network;

import io.netty.buffer.ByteBuf;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.p3pp3rf1y.sophisticatedbackpacks.SophisticatedBackpacks;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackStorage;

public record RequestBackpackInventoryContentsPayload(UUID backpackUuid) implements CustomPacketPayload {
   public static final Type<RequestBackpackInventoryContentsPayload> TYPE = new Type(SophisticatedBackpacks.getRL("request_backpack_inventory_contents"));
   public static final StreamCodec<ByteBuf, RequestBackpackInventoryContentsPayload> STREAM_CODEC = StreamCodec.composite(
      UUIDUtil.STREAM_CODEC, RequestBackpackInventoryContentsPayload::backpackUuid, RequestBackpackInventoryContentsPayload::new
   );

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void handlePayload(RequestBackpackInventoryContentsPayload payload, IPayloadContext context) {
      CompoundTag backpackContents = BackpackStorage.get().getOrCreateBackpackContents(payload.backpackUuid);
      CompoundTag inventoryContents = new CompoundTag();
      Tag inventoryNbt = backpackContents.get("inventory");
      if (inventoryNbt != null) {
         inventoryContents.put("inventory", inventoryNbt);
      }

      Tag upgradeNbt = backpackContents.get("upgradeInventory");
      if (upgradeNbt != null) {
         inventoryContents.put("upgradeInventory", upgradeNbt);
      }

      if (context.player() instanceof ServerPlayer serverPlayer) {
         PacketDistributor.sendToPlayer(serverPlayer, new BackpackContentsPayload(payload.backpackUuid, inventoryContents), new CustomPacketPayload[0]);
      }
   }
}
