package de.keksuccino.konkrete.gui.content.scrollarea;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

@Deprecated(forRemoval = true)
public class LegacyScrollAreaCompat {
   private static final List<LegacyScrollAreaCompat.MouseScrollCallback> SCROLL_CALLBACKS = new ArrayList<>();

   public static void notifyCallbacks(float scrollDelta) {
      SCROLL_CALLBACKS.forEach(callback -> callback.scroll(scrollDelta));
   }

   protected static void registerScrollCallback(@NotNull LegacyScrollAreaCompat.MouseScrollCallback callback) {
      SCROLL_CALLBACKS.add(Objects.requireNonNull(callback));
   }

   @FunctionalInterface
   protected interface MouseScrollCallback {
      void scroll(float var1);
   }
}
