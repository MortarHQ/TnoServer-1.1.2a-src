package com.faboslav.friendsandfoes.common.network.packet;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.api.MoobloomVariant;
import com.faboslav.friendsandfoes.common.api.MoobloomVariantManager;
import com.faboslav.friendsandfoes.common.events.lifecycle.DatapackSyncEvent;
import com.faboslav.friendsandfoes.common.network.MessageHandler;
import com.mojang.serialization.DataResult;
import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.ClientboundPacketType;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record MoobloomVariantsSyncPacket(List<MoobloomVariant> moobloomVariants) implements Packet<MoobloomVariantsSyncPacket> {
   public static final ResourceLocation ID = FriendsAndFoes.makeID("moobloom_variants_sync_packet");
   public static final ClientboundPacketType<MoobloomVariantsSyncPacket> TYPE = new MoobloomVariantsSyncPacket.Handler();

   public static void sendToClient(DatapackSyncEvent event) {
      MessageHandler.DEFAULT_CHANNEL
         .sendToPlayer(new MoobloomVariantsSyncPacket(MoobloomVariantManager.MOOBLOOM_VARIANT_MANAGER.getMoobloomVariants()), event.player());
   }

   public PacketType<MoobloomVariantsSyncPacket> type() {
      return TYPE;
   }

   public static class Handler implements ClientboundPacketType<MoobloomVariantsSyncPacket> {
      public ResourceLocation id() {
         return MoobloomVariantsSyncPacket.ID;
      }

      public Runnable handle(MoobloomVariantsSyncPacket packet) {
         return () -> MoobloomVariantManager.MOOBLOOM_VARIANT_MANAGER.setMoobloomVariants(packet.moobloomVariants());
      }

      public MoobloomVariantsSyncPacket decode(RegistryFriendlyByteBuf buf) {
         List<MoobloomVariant> parsedMoobloomVariants = new ArrayList<>();
         CompoundTag data = buf.readNbt();
         if (data == null) {
            FriendsAndFoes.getLogger().error("Moobloom Variant packet is empty");
            return new MoobloomVariantsSyncPacket(parsedMoobloomVariants);
         }

         for (Tag moobloomVariant : data.getList("moobloom_variants", 10)) {
            DataResult<MoobloomVariant> parsedMoobloomVariant = MoobloomVariant.CODEC.parse(NbtOps.INSTANCE, moobloomVariant);
            parsedMoobloomVariant.error().ifPresent(error -> FriendsAndFoes.getLogger().error("Failed to parse Moobloom Variant packet entry: {}", error));
            parsedMoobloomVariant.result().ifPresent(parsedMoobloomVariants::add);
         }

         return new MoobloomVariantsSyncPacket(parsedMoobloomVariants);
      }

      public void encode(MoobloomVariantsSyncPacket packet, RegistryFriendlyByteBuf buf) {
         CompoundTag data = new CompoundTag();
         ListTag parsedMoobloomVariants = new ListTag();

         for (MoobloomVariant moobloomVariant : packet.moobloomVariants()) {
            DataResult<Tag> parsedMoobloomVariant = MoobloomVariant.CODEC.encodeStart(NbtOps.INSTANCE, moobloomVariant);
            parsedMoobloomVariant.error().ifPresent(error -> FriendsAndFoes.getLogger().error("Failed to encode Moobloom Variant packet entry: {}", error));
            parsedMoobloomVariant.result().ifPresent(parsedMoobloomVariants::add);
         }

         data.put("moobloom_variants", parsedMoobloomVariants);
         buf.writeNbt(data);
      }
   }
}
