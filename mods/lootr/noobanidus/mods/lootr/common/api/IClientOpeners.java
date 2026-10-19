package noobanidus.mods.lootr.common.api;

import java.util.Set;
import java.util.UUID;
import net.minecraft.world.entity.player.Player;
import noobanidus.mods.lootr.common.api.annotation.ClientOnly;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.ApiStatus.Internal;

@Internal
public interface IClientOpeners extends IOpeners {
   @ClientOnly
   @Nullable
   Set<UUID> getClientOpeners();

   boolean isClientOpened();

   void setClientOpened(boolean var1);

   @Override
   default boolean clearOpeners() {
      boolean result = IOpeners.super.clearOpeners();
      Set<UUID> clientOpeners = this.getClientOpeners();
      if (clientOpeners != null && !clientOpeners.isEmpty()) {
         clientOpeners.clear();
         this.markChanged();
         return true;
      } else {
         return result;
      }
   }

   default boolean hasClientOpened(PlayerContext context) {
      if (this.isClientOpened()) {
         return true;
      }

      if (!context.hasPlayer()) {
         return false;
      }

      assert context.player() != null;
      return this.hasClientOpened(context.player());
   }

   default boolean hasClientOpened(Player player) {
      return this.hasClientOpened(player.getUUID());
   }

   default boolean hasClientOpened(UUID uuid) {
      if (this.isClientOpened()) {
         return true;
      }

      Set<UUID> clientOpeners = this.getClientOpeners();
      return clientOpeners != null && !clientOpeners.isEmpty() && clientOpeners.contains(uuid);
   }
}
