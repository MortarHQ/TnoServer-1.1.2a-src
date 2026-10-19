package mezz.jei.gui.input;

import java.util.Optional;
import mezz.jei.common.input.IInternalKeyMappings;
import net.minecraft.client.gui.screens.Screen;

public interface IUserInputHandler {
   Optional<IUserInputHandler> handleUserInput(Screen var1, UserInput var2, IInternalKeyMappings var3);

   default void unfocus() {
   }

   default Optional<IUserInputHandler> handleMouseScrolled(double mouseX, double mouseY, double scrollDeltaX, double scrollDeltaY) {
      return Optional.empty();
   }
}
