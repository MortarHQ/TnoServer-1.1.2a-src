package com.benji.netherman.common.network;

import com.benji.netherman.init.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record AzazelBoostPacket() implements CustomPacketPayload {
   public static final Type<AzazelBoostPacket> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("netherman", "azazel_boost"));
   public static final StreamCodec<FriendlyByteBuf, AzazelBoostPacket> STREAM_CODEC = StreamCodec.unit(new AzazelBoostPacket());

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void handle(AzazelBoostPacket data, IPayloadContext context) {
      context.enqueueWork(() -> {
         if (context.player() instanceof ServerPlayer player && player.isFallFlying()) {
            ItemStack chestStack = player.getItemBySlot(EquipmentSlot.CHEST);
            if (chestStack.is((Item)ModItems.AZAZEL_CHESTPLATE.get()) && player.level() instanceof ServerLevel serverLevel) {
               chestStack.hurtAndBreak(10, serverLevel, player, item -> player.onEquippedItemBroken(item, EquipmentSlot.CHEST));
            }
         }
      });
   }
}
