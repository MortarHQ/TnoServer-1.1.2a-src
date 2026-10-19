package artifacts.equipment;

import java.util.function.BiFunction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface EquipmentSlotProvider {
   <T> T reduceEquipment(LivingEntity var1, T var2, BiFunction<ItemStack, T, T> var3);

   boolean tryEquipItem(LivingEntity var1, ItemStack var2);
}
