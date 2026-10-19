package mezz.jei.gui.overlay;

import java.util.List;
import mezz.jei.gui.overlay.elements.IElement;
import org.jetbrains.annotations.Unmodifiable;

public interface IIngredientGridSource {
   @Unmodifiable List<IElement<?>> getElements();

   void addSourceListChangedListener(IIngredientGridSource.SourceListChangedListener var1);

   interface SourceListChangedListener {
      void onSourceListChanged();
   }
}
