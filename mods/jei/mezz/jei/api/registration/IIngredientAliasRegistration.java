package mezz.jei.api.registration;

import java.util.Collection;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.ITypedIngredient;
import net.minecraft.world.item.ItemStack;

public interface IIngredientAliasRegistration {
   default void addAlias(ItemStack itemStack, String alias) {
      this.addAlias(VanillaTypes.ITEM_STACK, itemStack, alias);
   }

   <I> void addAlias(IIngredientType<I> var1, I var2, String var3);

   <I> void addAlias(ITypedIngredient<I> var1, String var2);

   <I> void addAliases(IIngredientType<I> var1, I var2, Collection<String> var3);

   <I> void addAliases(ITypedIngredient<I> var1, Collection<String> var2);

   <I> void addAliases(IIngredientType<I> var1, Collection<I> var2, String var3);

   <I> void addAliases(Collection<ITypedIngredient<I>> var1, String var2);

   <I> void addAliases(IIngredientType<I> var1, Collection<I> var2, Collection<String> var3);

   <I> void addAliases(Collection<ITypedIngredient<I>> var1, Collection<String> var2);
}
