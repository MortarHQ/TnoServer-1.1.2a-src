package twilightforest.compat.jei.subtype;

import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import twilightforest.init.TFDataComponents;

public class CasketSubtypeInterpreter implements ISubtypeInterpreter<ItemStack> {
   public static final CasketSubtypeInterpreter INSTANCE = new CasketSubtypeInterpreter();

   @Nullable
   public Object getSubtypeData(ItemStack stack, @NotNull UidContext context) {
      Integer damage = (Integer)stack.get(TFDataComponents.CASKET_DAMAGE);
      return damage == null ? null : damage.toString();
   }

   @NotNull
   public String getLegacyStringSubtypeInfo(ItemStack ingredient, @NotNull UidContext context) {
      Integer damage = (Integer)ingredient.get(TFDataComponents.CASKET_DAMAGE);
      return damage == null ? "" : damage.toString();
   }
}
