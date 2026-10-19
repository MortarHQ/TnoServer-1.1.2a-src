package bigchadguys.sellingbin.net;

import bigchadguys.sellingbin.SellingBinMod;
import bigchadguys.sellingbin.data.bit.ArrayBitBuffer;
import bigchadguys.sellingbin.data.serializable.IBitSerializable;
import dev.architectury.platform.Platform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.util.thread.BlockableEventLoop;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public abstract class ModPacket<T extends PacketListener> implements CustomPacketPayload, IBitSerializable {
   public abstract void onReceive(T var1);

   public final void write(FriendlyByteBuf buf) {
      ArrayBitBuffer buffer = ArrayBitBuffer.empty();
      this.writeBits(buffer);
      buf.writeLongArray(buffer.toLongArray());
   }

   public final void read(FriendlyByteBuf buf) {
      ArrayBitBuffer buffer = ArrayBitBuffer.backing(buf.readLongArray(), 0);
      this.readBits(buffer);
   }

   public void apply(T listener) {
      BlockableEventLoop<?> engine = switch (Platform.getEnvironment()) {
         case CLIENT -> this.getClientEngine(listener);
         case SERVER -> this.getServerEngine(listener);
         default -> throw new MatchException(null, null);
      };
      if (engine != null && !engine.isSameThread()) {
         engine.executeIfPossible(() -> {
            if (listener.isAcceptingMessages()) {
               try {
                  this.apply(listener);
               } catch (Exception exception) {
                  SellingBinMod.LOGGER.error("Failed to handle packet {}, suppressing error", this, exception);
               }
            } else {
               SellingBinMod.LOGGER.debug("Ignoring packet due to disconnection: {}", this);
            }
         });
      } else {
         this.onReceive(listener);
      }
   }

   @OnlyIn(Dist.CLIENT)
   public BlockableEventLoop<?> getClientEngine(T listener) {
      if (listener instanceof ServerGamePacketListenerImpl handler) {
         return handler.player.getServer();
      } else {
         if (listener instanceof ClientPacketListener) {
            return Minecraft.getInstance();
         }

         SellingBinMod.LOGGER.error("Failed to handle packet {}, engine {} is unknown", this, listener);
         return null;
      }
   }

   @OnlyIn(Dist.DEDICATED_SERVER)
   public BlockableEventLoop<?> getServerEngine(T listener) {
      if (listener instanceof ServerGamePacketListenerImpl handler) {
         return handler.player.getServer();
      } else {
         SellingBinMod.LOGGER.error("Failed to handle packet {}, engine {} is unknown", this, listener);
         return null;
      }
   }
}
