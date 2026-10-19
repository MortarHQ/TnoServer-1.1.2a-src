package com.bobmowzie.mowziesmobs.server.message.mouse;

import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.PlayerAbility;
import com.bobmowzie.mowziesmobs.server.capability.AbilityData;
import com.bobmowzie.mowziesmobs.server.capability.DataHandler;
import com.bobmowzie.mowziesmobs.server.capability.PlayerData;
import com.bobmowzie.mowziesmobs.server.power.Power;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record MessageRightMouseDown() implements CustomPacketPayload {
   public static final Type<MessageRightMouseDown> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("mowziesmobs", "message_right_mouse_down"));
   public static final StreamCodec<ByteBuf, MessageRightMouseDown> STREAM_CODEC = StreamCodec.unit(new MessageRightMouseDown());

   public static void handleServer(MessageRightMouseDown packet, IPayloadContext context) {
      context.enqueueWork(() -> {
         Player player = context.player();
         PlayerData data = DataHandler.getData(player, DataHandler.PLAYER_DATA);
         data.setMouseRightDown(true);

         for (Power power : data.getPowers()) {
            power.onRightMouseDown(player);
         }

         AbilityData abilityData = DataHandler.getData(player, DataHandler.ABILITY_DATA);
         if (abilityData != null) {
            for (Ability<?> ability : abilityData.getAbilities()) {
               if (ability instanceof PlayerAbility playerAbility) {
                  playerAbility.onRightMouseDown(player);
               }
            }
         }
      });
   }

   @NotNull
   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
