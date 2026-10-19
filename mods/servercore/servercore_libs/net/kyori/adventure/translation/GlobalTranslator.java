package servercore_libs.net.kyori.adventure.translation;

import java.util.Locale;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import servercore_libs.net.kyori.adventure.text.Component;
import servercore_libs.net.kyori.adventure.text.renderer.TranslatableComponentRenderer;
import servercore_libs.net.kyori.examination.Examinable;

public interface GlobalTranslator extends Translator, Examinable {
   @NotNull
   static GlobalTranslator translator() {
      return GlobalTranslatorImpl.INSTANCE;
   }

   @Deprecated
   @ScheduledForRemoval(inVersion = "5.0.0")
   @NotNull
   static GlobalTranslator get() {
      return GlobalTranslatorImpl.INSTANCE;
   }

   @NotNull
   static TranslatableComponentRenderer<Locale> renderer() {
      return GlobalTranslatorImpl.INSTANCE.renderer;
   }

   @NotNull
   static Component render(@NotNull final Component component, @NotNull final Locale locale) {
      return renderer().render(component, locale);
   }

   @NotNull
   Iterable<? extends Translator> sources();

   boolean addSource(@NotNull final Translator source);

   boolean removeSource(@NotNull final Translator source);
}
