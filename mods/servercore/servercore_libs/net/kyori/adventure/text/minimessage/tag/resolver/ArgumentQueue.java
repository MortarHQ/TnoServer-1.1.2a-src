package servercore_libs.net.kyori.adventure.text.minimessage.tag.resolver;

import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.Tag;

@NonExtendable
public interface ArgumentQueue {
   Tag.@NotNull Argument pop();

   Tag.@NotNull Argument popOr(@NotNull final String errorMessage);

   Tag.@NotNull Argument popOr(@NotNull final Supplier<String> errorMessage);

   Tag.@Nullable Argument peek();

   boolean hasNext();

   void reset();
}
