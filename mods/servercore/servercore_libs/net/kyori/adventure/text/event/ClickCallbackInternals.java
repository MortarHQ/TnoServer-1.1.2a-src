package servercore_libs.net.kyori.adventure.text.event;

import org.jetbrains.annotations.NotNull;
import servercore_libs.net.kyori.adventure.audience.Audience;
import servercore_libs.net.kyori.adventure.permission.PermissionChecker;
import servercore_libs.net.kyori.adventure.util.Services;
import servercore_libs.net.kyori.adventure.util.TriState;

final class ClickCallbackInternals {
   static final PermissionChecker ALWAYS_FALSE = PermissionChecker.always(TriState.FALSE);
   static final ClickCallback.Provider PROVIDER = Services.service(ClickCallback.Provider.class).orElseGet(ClickCallbackInternals.Fallback::new);

   private ClickCallbackInternals() {
   }

   static final class Fallback implements ClickCallback.Provider {
      @NotNull
      @Override
      public ClickEvent create(@NotNull final ClickCallback<Audience> callback, final ClickCallback.@NotNull Options options) {
         return ClickEvent.suggestCommand("Callbacks are not supported on this platform!");
      }
   }
}
