package io.github.Memoires.mysticism.network.s2c;

import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public record OpenCustomStorageMenuPayload(
   OpenCustomStorageMenuPayload.StorageType storageType,
   int containerId,
   int size,
   int stackSize,
   int page,
   boolean mastered,
   int entityId,
   ResourceLocation skill
) implements CustomPacketPayload {
   public static final Type<OpenCustomStorageMenuPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("mysticism", "open_custom_storage_menu"));
   public static final StreamCodec<FriendlyByteBuf, OpenCustomStorageMenuPayload> STREAM_CODEC = CustomPacketPayload.codec(
      OpenCustomStorageMenuPayload::encode, OpenCustomStorageMenuPayload::new
   );

   public OpenCustomStorageMenuPayload(FriendlyByteBuf buf) {
      this(
         (OpenCustomStorageMenuPayload.StorageType)buf.readEnum(OpenCustomStorageMenuPayload.StorageType.class),
         buf.readInt(),
         buf.readInt(),
         buf.readInt(),
         buf.readInt(),
         buf.readBoolean(),
         buf.readInt(),
         buf.readResourceLocation()
      );
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeEnum(this.storageType);
      buf.writeInt(this.containerId);
      buf.writeInt(this.size);
      buf.writeInt(this.stackSize);
      buf.writeInt(this.page);
      buf.writeBoolean(this.mastered);
      buf.writeInt(this.entityId);
      buf.writeResourceLocation(this.skill);
   }

   public void handle(PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(() -> {
            switch (this.storageType()) {
               case BOOK:
                  ClientAccess.handleOpenBookStorageMenuPayload(this);
                  break;
               case WEAPON:
                  ClientAccess.handleOpenWeaponStorageMenuPayload(this);
            }
         });
      }
   }

   public Type<OpenCustomStorageMenuPayload> type() {
      return TYPE;
   }

   public enum StorageType {
      BOOK,
      WEAPON;
   }
}
