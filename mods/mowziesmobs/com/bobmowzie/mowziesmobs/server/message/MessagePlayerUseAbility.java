package com.bobmowzie.mowziesmobs.server.message;

import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.capability.AbilityData;
import com.bobmowzie.mowziesmobs.server.capability.DataHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record MessagePlayerUseAbility(int index) implements CustomPacketPayload {
   public static final Type<MessagePlayerUseAbility> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("mowziesmobs", "message_player_use_ability"));
   public static final StreamCodec<ByteBuf, MessagePlayerUseAbility> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.INT, MessagePlayerUseAbility::index, MessagePlayerUseAbility::new
   );

   public static void handleServer(MessagePlayerUseAbility packet, IPayloadContext context) {
      context.enqueueWork(
         () -> {
            Player player = context.player();
            AbilityHandler.INSTANCE
               .sendAbilityMessage(player, DataHandler.<AbilityData>getData(player, DataHandler.ABILITY_DATA).getAbilityTypesOnEntity(player)[packet.index()]);
         }
      );
   }

   @NotNull
   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
