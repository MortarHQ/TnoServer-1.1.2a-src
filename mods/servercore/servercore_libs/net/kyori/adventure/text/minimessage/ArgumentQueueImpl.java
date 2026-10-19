package servercore_libs.net.kyori.adventure.text.minimessage;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.Tag;
import servercore_libs.net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;

final class ArgumentQueueImpl<T extends Tag.Argument> implements ArgumentQueue {
   private final Context context;
   final List<T> args;
   private int ptr = 0;

   ArgumentQueueImpl(final Context context, final List<T> args) {
      this.context = context;
      this.args = args;
   }

   @NotNull
   @Override
   public T pop() {
      if (!this.hasNext()) {
         throw this.context.newException("Missing argument for this tag!", this);
      } else {
         return this.args.get(this.ptr++);
      }
   }

   @NotNull
   @Override
   public T popOr(@NotNull final String errorMessage) {
      Objects.requireNonNull(errorMessage, "errorMessage");
      if (!this.hasNext()) {
         throw this.context.newException(errorMessage, this);
      } else {
         return this.args.get(this.ptr++);
      }
   }

   @NotNull
   @Override
   public T popOr(@NotNull final Supplier<String> errorMessage) {
      Objects.requireNonNull(errorMessage, "errorMessage");
      if (!this.hasNext()) {
         throw this.context.newException(Objects.requireNonNull(errorMessage.get(), "errorMessage.get()"), this);
      } else {
         return this.args.get(this.ptr++);
      }
   }

   @Nullable
   @Override
   public T peek() {
      return this.hasNext() ? this.args.get(this.ptr) : null;
   }

   @Override
   public boolean hasNext() {
      return this.ptr < this.args.size();
   }

   @Override
   public void reset() {
      this.ptr = 0;
   }

   @Override
   public String toString() {
      return this.args.toString();
   }
}
