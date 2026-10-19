package net.p3pp3rf1y.sophisticatedbackpacks.compat.recipeviewers.common.subtypes;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.world.item.Item;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;
import net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.common.subtypes.PropertyBasedSubtypeInterpreter;

public class SubtypeInterpreters {
   private static final PropertyBasedSubtypeInterpreter backpackSubtypeInterpreter = new BackpackSubtypeInterpreter();

   public static Map<Item, PropertyBasedSubtypeInterpreter> getSubtypeInterpreters() {
      return new HashMap<Item, PropertyBasedSubtypeInterpreter>() {
         {
            ModItems.ITEMS
               .getEntries()
               .stream()
               .filter(holder -> holder.get() instanceof BackpackItem)
               .forEach(item -> this.put((Item)item.get(), SubtypeInterpreters.backpackSubtypeInterpreter));
         }
      };
   }
}
