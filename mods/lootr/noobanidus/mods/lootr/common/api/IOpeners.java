package noobanidus.mods.lootr.common.api;

import java.util.Set;
import java.util.UUID;
import net.minecraft.world.entity.player.Player;
import noobanidus.mods.lootr.common.api.annotation.ServerOnly;
import org.jetbrains.annotations.Nullable;

public interface IOpeners extends IMarkChanged {
   @ServerOnly
   @Nullable
   Set<UUID> getVisualOpeners();

   @Nullable
   Set<UUID> getActualOpeners();

   default boolean addOpener(Player player) {
      boolean result1 = this.addVisualOpener(player);
      boolean result2 = this.addActualOpener(player);
      return result1 || result2;
   }

   default boolean clearOpeners() {
      Set<UUID> openers = this.getVisualOpeners();
      if (openers == null) {
         return false;
      } else if (!openers.isEmpty()) {
         openers.clear();
         this.markChanged();
         return true;
      } else {
         return false;
      }
   }

   default boolean addVisualOpener(UUID uuid) {
      Set<UUID> openers = this.getVisualOpeners();
      if (openers == null) {
         return false;
      } else if (openers.add(uuid)) {
         this.markChanged();
         return true;
      } else {
         return false;
      }
   }

   default boolean hasVisualOpened(UUID uuid) {
      Set<UUID> openers = this.getVisualOpeners();
      return openers == null ? false : !openers.isEmpty() && openers.contains(uuid);
   }

   default boolean removeVisualOpener(UUID uuid) {
      Set<UUID> openers = this.getVisualOpeners();
      if (openers == null) {
         return false;
      } else if (openers.remove(uuid)) {
         this.markChanged();
         return true;
      } else {
         return false;
      }
   }

   default boolean addActualOpener(UUID uuid) {
      Set<UUID> openers = this.getActualOpeners();
      if (openers == null) {
         return false;
      } else if (openers.add(uuid)) {
         this.markChanged();
         return true;
      } else {
         return false;
      }
   }

   @Deprecated
   default boolean hasOpened(UUID uuid) {
      return this.hasServerOpened(uuid);
   }

   default boolean hasServerOpened(UUID uuid) {
      Set<UUID> openers = this.getActualOpeners();
      return openers == null ? false : !openers.isEmpty() && openers.contains(uuid);
   }

   @Deprecated
   default boolean hasOpened(Player player) {
      return this.hasServerOpened(player.getUUID());
   }

   default boolean hasServerOpened(Player player) {
      return this.hasServerOpened(player.getUUID());
   }

   default boolean addActualOpener(Player player) {
      return this.addActualOpener(player.getUUID());
   }

   default boolean addVisualOpener(Player player) {
      return this.addVisualOpener(player.getUUID());
   }

   default boolean hasVisualOpened(Player player) {
      return this.hasVisualOpened(player.getUUID());
   }

   default boolean removeVisualOpener(Player player) {
      return this.removeVisualOpener(player.getUUID());
   }
}
