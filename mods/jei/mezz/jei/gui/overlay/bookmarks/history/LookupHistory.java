package mezz.jei.gui.overlay.bookmarks.history;

import com.mojang.serialization.Codec;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import mezz.jei.api.helpers.ICodecHelper;
import mezz.jei.api.recipe.IRecipeManager;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.gui.bookmarks.IBookmark;
import mezz.jei.gui.config.ILookupHistoryConfig;
import mezz.jei.gui.overlay.IIngredientGridSource;
import mezz.jei.gui.overlay.elements.IElement;
import net.minecraft.core.RegistryAccess;
import org.jetbrains.annotations.Unmodifiable;

public class LookupHistory implements IIngredientGridSource {
   private final List<IBookmark> elements = new LinkedList<>();
   private final List<IIngredientGridSource.SourceListChangedListener> listeners = new ArrayList<>();
   private final IRecipeManager recipeManager;
   private final IIngredientManager ingredientManager;
   private final RegistryAccess registryAccess;
   private final ICodecHelper codecHelper;
   private final Supplier<Integer> maxElements;
   private final ILookupHistoryConfig lookupHistoryConfig;
   private final Codec<IBookmark> bookmarkCodec;

   public LookupHistory(
      IRecipeManager recipeManager,
      IIngredientManager ingredientManager,
      RegistryAccess registryAccess,
      ICodecHelper codecHelper,
      Supplier<Integer> maxElements,
      ILookupHistoryConfig lookupHistoryConfig,
      Codec<IBookmark> bookmarkCodec
   ) {
      this.recipeManager = recipeManager;
      this.ingredientManager = ingredientManager;
      this.registryAccess = registryAccess;
      this.codecHelper = codecHelper;
      this.maxElements = maxElements;
      this.lookupHistoryConfig = lookupHistoryConfig;
      this.bookmarkCodec = bookmarkCodec;
      List<IBookmark> loaded = lookupHistoryConfig.load(recipeManager, ingredientManager, registryAccess, codecHelper, bookmarkCodec);
      this.elements.addAll(loaded);
   }

   public void add(IBookmark element) {
      this.elements.remove(element);
      this.elements.addFirst(element);
      if (this.elements.size() > this.maxElements.get()) {
         this.elements.removeLast();
      }

      this.notifyListeners();
      this.lookupHistoryConfig.save(this.recipeManager, this.ingredientManager, this.registryAccess, this.codecHelper, this.elements, this.bookmarkCodec);
   }

   @Override
   public @Unmodifiable List<IElement<?>> getElements() {
      return this.elements.stream().map(IBookmark::getElement).toList();
   }

   @Override
   public void addSourceListChangedListener(IIngredientGridSource.SourceListChangedListener listener) {
      this.listeners.add(listener);
   }

   private void notifyListeners() {
      for (IIngredientGridSource.SourceListChangedListener listener : this.listeners) {
         listener.onSourceListChanged();
      }
   }
}
