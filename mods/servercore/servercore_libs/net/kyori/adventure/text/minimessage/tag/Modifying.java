package servercore_libs.net.kyori.adventure.text.minimessage.tag;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.ApiStatus.OverrideOnly;
import servercore_libs.net.kyori.adventure.text.Component;
import servercore_libs.net.kyori.adventure.text.minimessage.tree.Node;

@OverrideOnly
public interface Modifying extends Tag {
   default void visit(@NotNull final Node current, final int depth) {
   }

   default void postVisit() {
   }

   Component apply(@NotNull final Component current, final int depth);
}
