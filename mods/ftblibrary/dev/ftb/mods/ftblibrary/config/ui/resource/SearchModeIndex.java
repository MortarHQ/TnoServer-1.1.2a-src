package dev.ftb.mods.ftblibrary.config.ui.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SearchModeIndex<T extends ResourceSearchMode<?>> {
   private final List<T> modes = new ArrayList<>();
   private int modeIdx = 0;

   public void appendMode(T mode) {
      this.modes.add(mode);
   }

   public void prependMode(T mode) {
      this.modes.addFirst(mode);
   }

   public Optional<T> getCurrentSearchMode() {
      return this.modeIdx >= 0 && this.modeIdx < this.modes.size() ? Optional.of(this.modes.get(this.modeIdx)) : Optional.empty();
   }

   public void nextMode() {
      if (!this.modes.isEmpty()) {
         this.modeIdx = (this.modeIdx + 1) % this.modes.size();
      }
   }
}
