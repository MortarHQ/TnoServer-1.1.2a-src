package servercore_libs.net.kyori.adventure.text;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import org.jetbrains.annotations.Debug.Renderer;
import servercore_libs.net.kyori.adventure.text.format.Style;
import servercore_libs.net.kyori.examination.ExaminableProperty;
import servercore_libs.net.kyori.examination.string.StringExaminer;

@Deprecated
@ScheduledForRemoval(inVersion = "5.0.0")
@Renderer(text = "this.debuggerString()", childrenArray = "this.children().toArray()", hasChildren = "!this.children().isEmpty()")
public abstract class AbstractComponent implements Component {
   protected final List<Component> children;
   protected final Style style;

   protected AbstractComponent(@NotNull final List<? extends ComponentLike> children, @NotNull final Style style) {
      this.children = ComponentLike.asComponents(children, IS_NOT_EMPTY);
      this.style = style;
   }

   @NotNull
   @Override
   public final List<Component> children() {
      return this.children;
   }

   @NotNull
   @Override
   public final Style style() {
      return this.style;
   }

   @Override
   public boolean equals(@Nullable final Object other) {
      if (this == other) {
         return true;
      }

      if (!(other instanceof AbstractComponent)) {
         return false;
      }

      AbstractComponent that = (AbstractComponent)other;
      return Objects.equals(this.children, that.children) && Objects.equals(this.style, that.style);
   }

   @Override
   public int hashCode() {
      int result = this.children.hashCode();
      return 31 * result + this.style.hashCode();
   }

   @Override
   public abstract String toString();

   private String debuggerString() {
      Stream<? extends ExaminableProperty> examinablePropertiesWithoutChildren = this.examinableProperties()
         .filter(property -> !property.name().equals("children"));
      return StringExaminer.simpleEscaping().examine(this.examinableName(), examinablePropertiesWithoutChildren);
   }
}
