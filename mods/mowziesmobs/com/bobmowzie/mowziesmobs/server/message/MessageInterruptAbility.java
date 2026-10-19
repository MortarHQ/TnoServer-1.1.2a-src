package com.bobmowzie.mowziesmobs.server.message;

import com.bobmowzie.mowziesmobs.MMCommon;
import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.capability.AbilityData;
import com.bobmowzie.mowziesmobs.server.capability.DataHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record MessageInterruptAbility(int entityId, int index) implements CustomPacketPayload {
   public static final Type<MessageInterruptAbility> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("mowziesmobs", "message_interrupt_ability"));
   public static final StreamCodec<ByteBuf, MessageInterruptAbility> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.INT, MessageInterruptAbility::entityId, ByteBufCodecs.INT, MessageInterruptAbility::index, MessageInterruptAbility::new
   );

   public static void handleClient(MessageInterruptAbility packet, IPayloadContext context) {
      context.enqueueWork(() -> {
         Level level = MMCommon.PROXY.getClientLevel();
         if (level != null && level.getEntity(packet.entityId()) instanceof LivingEntity living) {
            AbilityData data = DataHandler.getData(living, DataHandler.ABILITY_DATA);
            AbilityType<?, ?> abilityType = data.getAbilityTypesOnEntity(living)[packet.index()];
            Ability<?> instance = data.getAbilityMap().get(abilityType);
            if (instance.isUsing()) {
               instance.interrupt();
            }
         }
      });
   }

   @NotNull
   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
