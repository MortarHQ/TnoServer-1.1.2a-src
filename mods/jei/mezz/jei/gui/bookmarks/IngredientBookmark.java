package mezz.jei.gui.bookmarks;

import java.util.Objects;
import mezz.jei.api.ingredients.ITypedIngredient;
import mezz.jei.gui.overlay.elements.IElement;
import mezz.jei.gui.overlay.elements.IngredientBookmarkElement;

public class IngredientBookmark<T> implements IBookmark {
   private final IElement<T> element;
   private final Object uid;
   private final ITypedIngredient<T> typedIngredient;
   private boolean visible = true;

   IngredientBookmark(ITypedIngredient<T> typedIngredient, Object uid) {
      this.typedIngredient = typedIngredient;
      this.uid = uid;
      this.element = new IngredientBookmarkElement<>(this);
   }

   @Override
   public BookmarkType getType() {
      return BookmarkType.INGREDIENT;
   }

   public ITypedIngredient<T> getIngredient() {
      return this.typedIngredient;
   }

   @Override
   public IElement<?> getElement() {
      return this.element;
   }

   @Override
   public boolean isVisible() {
      return this.visible;
   }

   @Override
   public void setVisible(boolean visible) {
      this.visible = visible;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.uid, this.typedIngredient.getType());
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else {
         return !(obj instanceof IngredientBookmark<?> ingredientBookmark)
            ? false
            : ingredientBookmark.uid.equals(this.uid) && ingredientBookmark.typedIngredient.getType().equals(this.typedIngredient.getType());
      }
   }

   @Override
   public String toString() {
      return "IngredientBookmark{uid=" + this.uid + ", typedIngredient=" + this.typedIngredient + ", visible=" + this.visible + "}";
   }
}
