package net.p3pp3rf1y.sophisticatedcore.util;

import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.world.item.ItemStack;

public class ItemStackHelper {
   private ItemStackHelper() {
   }

   public static boolean areItemStackComponentsEqualIgnoreDurability(ItemStack stackA, ItemStack stackB) {
      if (stackA.isEmpty() && stackB.isEmpty()) {
         return true;
      } else if (stackA.isEmpty() || stackB.isEmpty()) {
         return false;
      } else {
         return stackA.getComponents().isEmpty() && !stackB.getComponents().isEmpty()
            ? false
            : stackA.getComponents().isEmpty() || areComponentsEqualIgnoreDurability(stackA.getComponents(), stackB.getComponents());
      }
   }

   public static boolean areComponentsEqualIgnoreDurability(DataComponentMap componentsA, @Nullable DataComponentMap componentsB) {
      if (componentsA == componentsB) {
         return true;
      }

      if (componentsB != null && componentsA.size() == componentsB.size()) {
         for (TypedDataComponent<?> typedDataComponent : componentsA) {
            if (!componentsB.has(typedDataComponent.type())) {
               return false;
            }

            if (!typedDataComponent.type().equals(DataComponents.DAMAGE)
               && !Objects.equals(typedDataComponent.value(), componentsB.get(typedDataComponent.type()))) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }
}
