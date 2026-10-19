package servercore_libs.net.kyori.adventure.text.minimessage.tree;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

@NonExtendable
public interface Node {
   @NotNull
   @Override
   String toString();

   @NotNull
   List<? extends Node> children();

   @Nullable
   Node parent();

   @NonExtendable
   interface Root extends Node {
      @NotNull
      String input();
   }
}
