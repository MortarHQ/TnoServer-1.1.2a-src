package mezz.jei.gui.input.handlers;

import java.util.Optional;
import mezz.jei.common.input.IInternalKeyMappings;
import mezz.jei.gui.bookmarks.BookmarkList;
import mezz.jei.gui.input.CombinedRecipeFocusSource;
import mezz.jei.gui.input.IUserInputHandler;
import mezz.jei.gui.input.UserInput;
import mezz.jei.gui.overlay.bookmarks.BookmarkOverlay;
import net.minecraft.client.gui.screens.Screen;

public class BookmarkInputHandler implements IUserInputHandler {
   private final CombinedRecipeFocusSource focusSource;
   private final BookmarkList bookmarkList;
   private final BookmarkOverlay bookmarkOverlay;

   public BookmarkInputHandler(CombinedRecipeFocusSource focusSource, BookmarkList bookmarkList, BookmarkOverlay bookmarkOverlay) {
      this.focusSource = focusSource;
      this.bookmarkList = bookmarkList;
      this.bookmarkOverlay = bookmarkOverlay;
   }

   @Override
   public Optional<IUserInputHandler> handleUserInput(Screen screen, UserInput input, IInternalKeyMappings keyBindings) {
      return input.is(keyBindings.getBookmark()) ? this.handleBookmark(input, keyBindings) : Optional.empty();
   }

   private Optional<IUserInputHandler> handleBookmark(UserInput input, IInternalKeyMappings keyBindings) {
      return this.focusSource.getIngredientUnderMouse(input, keyBindings).findFirst().flatMap(clicked -> {
         if (!input.isSimulate() && !this.bookmarkList.onElementBookmarked(clicked.getElement(), input, this.bookmarkOverlay)) {
            return Optional.empty();
         }

         IUserInputHandler handler = new SameElementInputHandler(this, clicked::isMouseOver);
         return Optional.of(handler);
      });
   }
}
