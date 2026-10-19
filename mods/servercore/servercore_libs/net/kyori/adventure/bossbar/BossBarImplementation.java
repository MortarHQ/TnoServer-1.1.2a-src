package servercore_libs.net.kyori.adventure.bossbar;

import java.util.Collections;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.ApiStatus.Internal;

@Internal
public interface BossBarImplementation {
   @Internal
   @NotNull
   static <I extends BossBarImplementation> I get(@NotNull final BossBar bar, @NotNull final Class<I> type) {
      return BossBarImpl.ImplementationAccessor.get(bar, type);
   }

   @Internal
   @NotNull
   default Iterable<? extends BossBarViewer> viewers() {
      return Collections.emptyList();
   }

   @Internal
   interface Provider {
      @Internal
      @NotNull
      BossBarImplementation create(@NotNull final BossBar bar);
   }
}
