package servercore_libs.net.kyori.adventure.resource;

import java.util.UUID;
import java.util.function.BiConsumer;
import org.jetbrains.annotations.NotNull;
import servercore_libs.net.kyori.adventure.audience.Audience;

@FunctionalInterface
public interface ResourcePackCallback {
   @NotNull
   static ResourcePackCallback noOp() {
      return ResourcePackCallbacks.NO_OP;
   }

   @NotNull
   static ResourcePackCallback onTerminal(@NotNull final BiConsumer<UUID, Audience> success, @NotNull final BiConsumer<UUID, Audience> failure) {
      return (uuid, status, audience) -> {
         if (status == ResourcePackStatus.SUCCESSFULLY_LOADED) {
            success.accept(uuid, audience);
         } else if (!status.intermediate()) {
            failure.accept(uuid, audience);
         }
      };
   }

   void packEventReceived(@NotNull final UUID uuid, @NotNull final ResourcePackStatus status, @NotNull final Audience audience);
}
