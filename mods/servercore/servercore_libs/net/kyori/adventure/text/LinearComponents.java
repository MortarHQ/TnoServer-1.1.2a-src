package servercore_libs.net.kyori.adventure.text;

import org.jetbrains.annotations.NotNull;
import servercore_libs.net.kyori.adventure.text.format.Style;
import servercore_libs.net.kyori.adventure.text.format.StyleBuilderApplicable;

public final class LinearComponents {
   private LinearComponents() {
   }

   @NotNull
   public static Component linear(@NotNull final ComponentBuilderApplicable... applicables) {
      int length = applicables.length;
      if (length == 0) {
         return Component.empty();
      }

      if (length == 1) {
         ComponentBuilderApplicable ap0 = applicables[0];
         if (ap0 instanceof ComponentLike) {
            return ((ComponentLike)ap0).asComponent();
         } else {
            throw nothingComponentLike();
         }
      } else {
         TextComponentImpl.BuilderImpl builder = new TextComponentImpl.BuilderImpl();
         Style.Builder style = null;

         for (int i = 0; i < length; i++) {
            ComponentBuilderApplicable applicable = applicables[i];
            if (applicable instanceof StyleBuilderApplicable) {
               if (style == null) {
                  style = Style.style();
               }

               style.apply((StyleBuilderApplicable)applicable);
            } else if (style != null && applicable instanceof ComponentLike) {
               builder.applicableApply(((ComponentLike)applicable).asComponent().style(style));
            } else {
               builder.applicableApply(applicable);
            }
         }

         int size = builder.children.size();
         if (size == 0) {
            throw nothingComponentLike();
         } else {
            return size == 1 && !builder.hasStyle() ? builder.children.get(0) : builder.build();
         }
      }
   }

   private static IllegalStateException nothingComponentLike() {
      return new IllegalStateException("Cannot build component linearly - nothing component-like was given");
   }
}
