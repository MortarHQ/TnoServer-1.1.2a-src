package de.ellpeck.naturesaura.packet;

import de.ellpeck.naturesaura.NaturesAura;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.chunk.AuraChunk;
import de.ellpeck.naturesaura.events.ClientEvents;
import java.util.ArrayList;
import java.util.Collection;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record PacketAuraChunk(int chunkX, int chunkZ, Collection<CompoundTag> drainSpots) implements CustomPacketPayload {
   public static final Type<PacketAuraChunk> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("naturesaura", "aura_chunk"));
   public static final StreamCodec<RegistryFriendlyByteBuf, PacketAuraChunk> CODEC = StreamCodec.composite(
      ByteBufCodecs.INT,
      PacketAuraChunk::chunkX,
      ByteBufCodecs.INT,
      PacketAuraChunk::chunkZ,
      ByteBufCodecs.collection(ArrayList::new, ByteBufCodecs.COMPOUND_TAG),
      PacketAuraChunk::drainSpots,
      PacketAuraChunk::new
   );

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   @OnlyIn(Dist.CLIENT)
   public static void onMessage(PacketAuraChunk message, IPayloadContext ctx) {
      ClientEvents.PENDING_AURA_CHUNKS.add(message);
   }

   public boolean tryHandle(Level level) {
      try {
         LevelChunk chunk = level.getChunk(this.chunkX, this.chunkZ);
         if (chunk.isEmpty()) {
            return false;
         }

         AuraChunk auraChunk = (AuraChunk)chunk.getData(NaturesAuraAPI.AURA_CHUNK_ATTACHMENT);
         auraChunk.setSpots(this.drainSpots);
         return true;
      } catch (Exception e) {
         NaturesAura.LOGGER.error("There was an error handling an aura chunk packet", e);
         return true;
      }
   }
}
