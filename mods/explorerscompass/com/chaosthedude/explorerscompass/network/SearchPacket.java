package com.chaosthedude.explorerscompass.network;

import com.chaosthedude.explorerscompass.ExplorersCompass;
import com.chaosthedude.explorerscompass.items.ExplorersCompassItem;
import com.chaosthedude.explorerscompass.util.ItemUtils;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SearchPacket(ResourceLocation groupKey, List<ResourceLocation> structureKeys, BlockPos pos, boolean isGroup) implements CustomPacketPayload {
   public static final Type<SearchPacket> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("explorerscompass", "search"));
   public static final StreamCodec<FriendlyByteBuf, SearchPacket> CODEC = StreamCodec.ofMember(SearchPacket::write, SearchPacket::read);

   public static SearchPacket read(FriendlyByteBuf buf) {
      ResourceLocation groupKey = buf.readResourceLocation();
      List<ResourceLocation> structureKeys = new ArrayList<>();
      int numStructures = buf.readInt();

      for (int i = 0; i < numStructures; i++) {
         structureKeys.add(buf.readResourceLocation());
      }

      BlockPos pos = buf.readBlockPos();
      boolean isGroup = buf.readBoolean();
      return new SearchPacket(groupKey, structureKeys, pos, isGroup);
   }

   public void write(FriendlyByteBuf buf) {
      buf.writeResourceLocation(this.groupKey);
      buf.writeInt(this.structureKeys.size());

      for (ResourceLocation key : this.structureKeys) {
         buf.writeResourceLocation(key);
      }

      buf.writeBlockPos(this.pos);
      buf.writeBoolean(this.isGroup);
   }

   public static void handle(SearchPacket packet, IPayloadContext context) {
      if (context.flow().isServerbound()) {
         context.enqueueWork(
            () -> {
               ItemStack stack = ItemUtils.getHeldItem(context.player(), ExplorersCompass.explorersCompass);
               if (!stack.isEmpty()) {
                  ExplorersCompassItem explorersCompass = (ExplorersCompassItem)stack.getItem();
                  explorersCompass.searchForStructure(
                     (ServerLevel)context.player().level(), context.player(), packet.groupKey, packet.structureKeys, packet.pos, stack, packet.isGroup
                  );
               }
            }
         );
      }
   }

   public Type<SearchPacket> type() {
      return TYPE;
   }
}
