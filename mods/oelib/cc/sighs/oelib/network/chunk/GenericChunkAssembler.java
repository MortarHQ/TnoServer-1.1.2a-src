package cc.sighs.oelib.network.chunk;

import cc.sighs.oelib.OELib;
import cc.sighs.oelib.network.api.INetworkContext;
import cc.sighs.oelib.network.api.INetworkPacket;
import cc.sighs.oelib.network.api.NetworkPacketTypes;
import cc.sighs.oelib.network.serialization.NetworkSerialization;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.ReferenceCountUtil;
import java.util.BitSet;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public final class GenericChunkAssembler {
   private static final Map<UUID, GenericChunkAssembler.Session> sessions = new ConcurrentHashMap<>();
   private static final AtomicLong BYTES_IN_ASSEMBLY = new AtomicLong(0L);
   private static final long QUOTA_BYTES = 67108864L;
   private static final long EXPIRE_MS = 30000L;
   private static final ScheduledExecutorService CLEANUP = Executors.newSingleThreadScheduledExecutor(r -> {
      Thread t = new Thread(r, "GenericChunkAssembler-Cleanup");
      t.setDaemon(true);
      return t;
   });

   private GenericChunkAssembler() {
   }

   public static void receiveChunk(
      UUID sessionId, int totalSize, short chunkIndex, short totalChunks, ResourceLocation typeId, byte[] chunkData, INetworkContext context
   ) {
      GenericChunkAssembler.Session s = sessions.computeIfAbsent(sessionId, id -> {
         long after = BYTES_IN_ASSEMBLY.addAndGet(totalSize);
         if (after > 67108864L) {
            BYTES_IN_ASSEMBLY.addAndGet(-totalSize);
            OELib.LOGGER.warn("Chunk assembly quota exceeded: {} bytes in assembly, reject session {}", after, id);
            return null;
         } else {
            return new GenericChunkAssembler.Session(totalChunks, totalSize, typeId);
         }
      });
      if (s != null) {
         if (s.add(chunkIndex, chunkData)) {
            try {
               CompositeByteBuf composite = s.assembleComposite();
               Class<?> clazz = NetworkPacketTypes.classOf(typeId);
               if (clazz == null || !CustomPacketPayload.class.isAssignableFrom(clazz)) {
                  OELib.LOGGER.warn("Chunk target {} is not a registered CustomPacketPayload", typeId);
                  ReferenceCountUtil.release(composite);
                  sessions.remove(sessionId);
                  return;
               }

               Class<CustomPacketPayload> c = (Class<CustomPacketPayload>)clazz;
               StreamCodec<RegistryFriendlyByteBuf, CustomPacketPayload> codec = NetworkSerialization.autoCodec(c);
               RegistryFriendlyByteBuf buf = new RegistryFriendlyByteBuf(composite, context.registryAccess());
               CustomPacketPayload payload = (CustomPacketPayload)codec.decode(buf);
               ReferenceCountUtil.release(composite);
               sessions.remove(sessionId);

               try {
                  if (payload instanceof INetworkPacket<?> p) {
                     context.enqueueWork(() -> p.handle(context));
                  } else {
                     OELib.LOGGER.warn("Decoded payload {} does not implement INetworkPacket", typeId);
                  }
               } catch (Throwable t) {
                  OELib.LOGGER.error("Failed to dispatch reassembled payload {}", typeId, t);
               }
            } catch (Throwable t) {
               OELib.LOGGER.error("Failed to reassemble payload {}", typeId, t);
               sessions.remove(sessionId);
            }
         }
      }
   }

   static {
      CLEANUP.scheduleAtFixedRate(() -> {
         try {
            sessions.entrySet().removeIf(e -> {
               GenericChunkAssembler.Session s = e.getValue();
               if (s.expired()) {
                  OELib.LOGGER.debug("Cleanup expired chunk session {}", e.getKey());
                  s.releaseAll();
                  return true;
               } else {
                  return false;
               }
            });
         } catch (Throwable t) {
            OELib.LOGGER.warn("Cleanup task failed", t);
         }
      }, 30L, 30L, TimeUnit.SECONDS);
   }

   private static final class Session {
      final int total;
      final int totalSize;
      final ResourceLocation typeId;
      final BitSet received;
      final ByteBuf[] parts;
      final long startMs;
      int count;

      Session(int totalChunks, int totalSize, ResourceLocation typeId) {
         this.total = totalChunks;
         this.totalSize = totalSize;
         this.typeId = typeId;
         this.received = new BitSet(totalChunks);
         this.parts = new ByteBuf[totalChunks];
         this.count = 0;
         this.startMs = System.currentTimeMillis();
      }

      synchronized boolean add(int idx, byte[] data) {
         if (idx >= 0 && idx < this.total) {
            if (!this.received.get(idx)) {
               this.parts[idx] = Unpooled.wrappedBuffer(data);
               this.received.set(idx);
               this.count++;
            }

            return this.count == this.total;
         } else {
            OELib.LOGGER.warn("Invalid chunk index {} of {} for {}", new Object[]{idx, this.total, this.typeId});
            return false;
         }
      }

      CompositeByteBuf assembleComposite() {
         CompositeByteBuf composite = Unpooled.compositeBuffer(this.total);

         for (int i = 0; i < this.total; i++) {
            if (this.parts[i] == null) {
               throw new IllegalStateException("Missing chunk " + i + " for " + this.typeId);
            }

            composite.addComponent(true, this.parts[i].retain());
         }

         GenericChunkAssembler.BYTES_IN_ASSEMBLY.addAndGet(-this.totalSize);
         return composite;
      }

      boolean expired() {
         return System.currentTimeMillis() - this.startMs > 30000L;
      }

      void releaseAll() {
         long released = 0L;

         for (int i = 0; i < this.total; i++) {
            if (this.parts[i] != null) {
               released += this.parts[i].readableBytes();
               ReferenceCountUtil.release(this.parts[i]);
               this.parts[i] = null;
            }
         }

         GenericChunkAssembler.BYTES_IN_ASSEMBLY.addAndGet(-Math.max(0L, this.totalSize - released));
      }
   }
}
