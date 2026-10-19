package servercore_libs.net.kyori.adventure.identity;

import java.util.Locale;
import java.util.UUID;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import servercore_libs.net.kyori.adventure.key.Key;
import servercore_libs.net.kyori.adventure.pointer.Pointer;
import servercore_libs.net.kyori.adventure.text.Component;
import servercore_libs.net.kyori.examination.Examinable;
import servercore_libs.net.kyori.examination.ExaminableProperty;

public interface Identity extends Examinable, Identified {
   Pointer<String> NAME = Pointer.pointer(String.class, Key.key("adventure", "name"));
   Pointer<UUID> UUID = Pointer.pointer(UUID.class, Key.key("adventure", "uuid"));
   Pointer<Component> DISPLAY_NAME = Pointer.pointer(Component.class, Key.key("adventure", "display_name"));
   Pointer<Locale> LOCALE = Pointer.pointer(Locale.class, Key.key("adventure", "locale"));

   @NotNull
   static Identity nil() {
      return NilIdentity.INSTANCE;
   }

   @NotNull
   static Identity identity(@NotNull final UUID uuid) {
      return uuid.equals(NilIdentity.NIL_UUID) ? NilIdentity.INSTANCE : new IdentityImpl(uuid);
   }

   @NotNull
   UUID uuid();

   @NotNull
   @Override
   default Identity identity() {
      return this;
   }

   @NotNull
   @Override
   default Stream<? extends ExaminableProperty> examinableProperties() {
      return Stream.of(ExaminableProperty.of("uuid", this.uuid()));
   }
}
