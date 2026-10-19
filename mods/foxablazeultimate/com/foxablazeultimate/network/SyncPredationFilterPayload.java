package com.foxablazeultimate.network;

import com.foxablazeultimate.client.FoxAblazeUltimateClientAccess;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public record SyncPredationFilterPayload(int slotCount, List<ItemStack> items) implements CustomPacketPayload {
   public static final Type<SyncPredationFilterPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "sync_predation_filter"));
   public static final StreamCodec<RegistryFriendlyByteBuf, SyncPredationFilterPayload> STREAM_CODEC = CustomPacketPayload.codec(
      SyncPredationFilterPayload::encode, SyncPredationFilterPayload::new
   );

   public SyncPredationFilterPayload(RegistryFriendlyByteBuf buf) {
      this(buf.readVarInt(), readItems(buf));
   }

   private static List<ItemStack> readItems(RegistryFriendlyByteBuf buf) {
      int size = buf.readVarInt();
      List<ItemStack> list = new ArrayList<>(size);

      for (int i = 0; i < size; i++) {
         list.add((ItemStack)ItemStack.OPTIONAL_STREAM_CODEC.decode(buf));
      }

      return list;
   }

   public void encode(RegistryFriendlyByteBuf buf) {
      buf.writeVarInt(this.slotCount);
      buf.writeVarInt(this.items.size());

      for (ItemStack stack : this.items) {
         ItemStack.OPTIONAL_STREAM_CODEC.encode(buf, stack);
      }
   }

   public void handle(PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(() -> FoxAblazeUltimateClientAccess.handleSyncPredationFilter(this));
      }
   }

   public Type<SyncPredationFilterPayload> type() {
      return TYPE;
   }
}
