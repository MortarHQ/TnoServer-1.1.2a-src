package servercore_libs.net.kyori.adventure.text;

import org.jetbrains.annotations.NotNull;
import servercore_libs.net.kyori.adventure.util.Buildable;

public interface BuildableComponent<C extends BuildableComponent<C, B>, B extends ComponentBuilder<C, B>> extends Buildable<C, B>, Component {
   @NotNull
   B toBuilder();
}
