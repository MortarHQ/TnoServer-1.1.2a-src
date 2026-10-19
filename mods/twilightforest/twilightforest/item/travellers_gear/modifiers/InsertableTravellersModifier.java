package twilightforest.item.travellers_gear.modifiers;

import net.minecraft.world.item.ItemStack;

public interface InsertableTravellersModifier extends TravellersModifier {
   boolean addModifier(ItemStack var1);

   void removeModifier(ItemStack var1);
}
