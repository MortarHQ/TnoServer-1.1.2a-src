package mezz.jei.library.gui.ingredients;

import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.Nullable;

public interface ICycler {
   <T> Optional<T> getCycled(List<@Nullable T> var1);
}
