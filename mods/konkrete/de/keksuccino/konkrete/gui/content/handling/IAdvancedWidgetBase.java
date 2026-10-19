package de.keksuccino.konkrete.gui.content.handling;

import de.keksuccino.konkrete.input.CharData;
import de.keksuccino.konkrete.input.KeyboardData;

@Deprecated(forRemoval = true)
public interface IAdvancedWidgetBase {
   @Deprecated
   default void onTick() {
   }

   void onKeyPress(KeyboardData var1);

   @Deprecated
   default void onKeyReleased(KeyboardData d) {
   }

   void onCharTyped(CharData var1);

   @Deprecated
   default void onMouseClicked(double mouseX, double mouseY, int mouseButton) {
   }
}
