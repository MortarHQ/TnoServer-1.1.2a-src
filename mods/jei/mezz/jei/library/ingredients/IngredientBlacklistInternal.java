package mezz.jei.library.ingredients;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.ITypedIngredient;
import mezz.jei.api.ingredients.subtypes.UidContext;
import mezz.jei.api.runtime.IIngredientManager;

public class IngredientBlacklistInternal implements IIngredientManager.IIngredientListener {
   private final Set<Object> uidBlacklist = new HashSet<>();
   private WeakReference<IngredientVisibility> ingredientVisibilityRef = new WeakReference<>(null);

   public <V> void addIngredientToBlacklist(ITypedIngredient<V> typedIngredient, IIngredientHelper<V> ingredientHelper) {
      Object uid = ingredientHelper.getUid(typedIngredient, UidContext.Ingredient);
      if (this.uidBlacklist.add(uid)) {
         this.notifyListenersOfVisibilityChange(typedIngredient, false);
      }
   }

   public <V> void removeIngredientFromBlacklist(ITypedIngredient<V> typedIngredient, IIngredientHelper<V> ingredientHelper) {
      Object uid = ingredientHelper.getUid(typedIngredient, UidContext.Ingredient);
      if (this.uidBlacklist.remove(uid)) {
         this.notifyListenersOfVisibilityChange(typedIngredient, true);
      }
   }

   public <V> boolean isIngredientBlacklistedByApi(ITypedIngredient<V> typedIngredient, IIngredientHelper<V> ingredientHelper) {
      Object uid = ingredientHelper.getUid(typedIngredient, UidContext.Ingredient);
      Object uidWild = ingredientHelper.getGroupingUid(typedIngredient);
      return uid.equals(uidWild) ? this.uidBlacklist.contains(uid) : this.uidBlacklist.contains(uid) || this.uidBlacklist.contains(uidWild);
   }

   public void registerListener(IngredientVisibility ingredientVisibility) {
      this.ingredientVisibilityRef = new WeakReference<>(ingredientVisibility);
   }

   @Override
   public <V> void onIngredientsAdded(IIngredientHelper<V> ingredientHelper, Collection<ITypedIngredient<V>> ingredients) {
      for (ITypedIngredient<V> ingredient : ingredients) {
         this.removeIngredientFromBlacklist(ingredient, ingredientHelper);
      }
   }

   @Override
   public <V> void onIngredientsRemoved(IIngredientHelper<V> ingredientHelper, Collection<ITypedIngredient<V>> ingredients) {
      for (ITypedIngredient<V> ingredient : ingredients) {
         this.addIngredientToBlacklist(ingredient, ingredientHelper);
      }
   }

   private <T> void notifyListenersOfVisibilityChange(ITypedIngredient<T> ingredient, boolean visible) {
      IngredientVisibility ingredientVisibility = this.ingredientVisibilityRef.get();
      if (ingredientVisibility != null) {
         ingredientVisibility.notifyListeners(ingredient, visible);
      }
   }
}
