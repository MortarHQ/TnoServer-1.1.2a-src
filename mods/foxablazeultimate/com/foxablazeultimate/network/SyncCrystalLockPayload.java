package com.foxablazeultimate.network;

import com.foxablazeultimate.client.ClientCrystalLockState;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record SyncCrystalLockPayload(List<ResourceLocation> lockedSkills) implements CustomPacketPayload {
   public static final Type<SyncCrystalLockPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "sync_crystal_lock"));
   public static final StreamCodec<FriendlyByteBuf, SyncCrystalLockPayload> STREAM_CODEC = CustomPacketPayload.codec(
      SyncCrystalLockPayload::encode, SyncCrystalLockPayload::new
   );

   public SyncCrystalLockPayload(FriendlyByteBuf buf) {
      this(buf.readList(FriendlyByteBuf::readResourceLocation));
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeCollection(this.lockedSkills, FriendlyByteBuf::writeResourceLocation);
   }

   public void handle(PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(() -> {
            Set<ResourceLocation> set = new HashSet<>(this.lockedSkills);
            ClientCrystalLockState.replaceAll(set);
         });
      }
   }

   public Type<SyncCrystalLockPayload> type() {
      return TYPE;
   }
}
