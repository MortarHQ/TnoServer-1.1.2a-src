package com.finderfeed.fdlib.network;

import com.finderfeed.fdlib.FDHelpers;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.util.thread.EffectiveSide;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = "fdlib", bus = Bus.MOD)
public class PacketHandler {
   @SubscribeEvent
   public static void autoRegisterPackets(RegisterPayloadHandlersEvent event) {
      PayloadRegistrar registrar = event.registrar("fdlib").versioned("1").optional();

      for (Class<?> clazz : FDHelpers.getAnnotatedClasses(RegisterFDPacket.class)) {
         RegisterFDPacket annotation = clazz.getAnnotation(RegisterFDPacket.class);
         Type<FDPacket> type = FDPacket.typeFor(annotation);

         try {
            Constructor<?> constructor;
            try {
               constructor = clazz.getConstructor(FriendlyByteBuf.class);
            } catch (Exception e) {
               constructor = clazz.getConstructor(RegistryFriendlyByteBuf.class);
            }

            Constructor<?> finalConstructor = constructor;
            StreamCodec<? super RegistryFriendlyByteBuf, FDPacket> codec = StreamCodec.of((buf, payload) -> payload.write(buf), buf -> {
               try {
                  return (FDPacket)finalConstructor.newInstance(buf);
               } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                  throw new RuntimeException(ex);
               }
            });
            registrar.playBidirectional(type, codec, (payload, context) -> context.enqueueWork(() -> {
               if (EffectiveSide.get().isClient()) {
                  payload.clientAction(context);
               } else {
                  payload.serverAction(context);
               }
            }));
         } catch (Exception e) {
            throw new RuntimeException(e);
         }
      }
   }
}
