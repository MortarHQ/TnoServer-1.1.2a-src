package dev.ftbqlang.network;

import dev.ftbqlang.server.LanguageService;
import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public final class LanguageNetwork {
   private static Consumer<LanguageStatePayload> clientHandler = payload -> {};

   private LanguageNetwork() {
   }

   public static void register(RegisterPayloadHandlersEvent event) {
      PayloadRegistrar registrar = event.registrar("1");
      registrar.playToServer(QueryPayload.TYPE, QueryPayload.STREAM_CODEC, (payload, context) -> {
         if (context.player() instanceof ServerPlayer player) {
            LanguageService.query(player, payload.bookOpened());
         }
      });
      registrar.playToServer(SelectLanguagePayload.TYPE, SelectLanguagePayload.STREAM_CODEC, (payload, context) -> {
         if (context.player() instanceof ServerPlayer player) {
            LanguageService.select(player, payload.locale());
         }
      });
      registrar.playToClient(LanguageStatePayload.TYPE, LanguageStatePayload.STREAM_CODEC, (payload, context) -> clientHandler.accept(payload));
   }

   public static void setClientHandler(Consumer<LanguageStatePayload> handler) {
      clientHandler = Objects.requireNonNull(handler);
   }
}
