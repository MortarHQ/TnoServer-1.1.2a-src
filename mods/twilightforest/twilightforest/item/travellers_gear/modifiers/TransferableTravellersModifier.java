package twilightforest.item.travellers_gear.modifiers;

import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public interface TransferableTravellersModifier extends InsertableTravellersModifier {
   boolean transfer(ItemStack var1, List<Ingredient> var2);
}
