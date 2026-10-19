package servercore_libs.net.kyori.adventure.chat;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import servercore_libs.net.kyori.adventure.internal.Internals;
import servercore_libs.net.kyori.adventure.key.Key;
import servercore_libs.net.kyori.adventure.text.Component;

final class ChatTypeImpl implements ChatType {
   private final Key key;

   ChatTypeImpl(@NotNull final Key key) {
      this.key = key;
   }

   @NotNull
   @Override
   public Key key() {
      return this.key;
   }

   @Override
   public String toString() {
      return Internals.toString(this);
   }

   static final class BoundImpl implements ChatType.Bound {
      private final ChatType chatType;
      private final Component name;
      @Nullable
      private final Component target;

      BoundImpl(final ChatType chatType, final Component name, @Nullable final Component target) {
         this.chatType = chatType;
         this.name = name;
         this.target = target;
      }

      @NotNull
      @Override
      public ChatType type() {
         return this.chatType;
      }

      @NotNull
      @Override
      public Component name() {
         return this.name;
      }

      @Nullable
      @Override
      public Component target() {
         return this.target;
      }

      @Override
      public String toString() {
         return Internals.toString(this);
      }
   }
}
