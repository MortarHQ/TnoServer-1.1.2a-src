package bigchadguys.sellingbin.util;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

public class ItemUtils {
   public static CompoundTag getNbt(ItemStack stack) {
      DataResult<Tag> data = DataComponentMap.CODEC.encodeStart(NbtOps.INSTANCE, stack.getComponents());
      return data.result().filter(nbtElement -> nbtElement instanceof CompoundTag).orElse(null);
   }

   public static void setNbt(ItemStack stack, CompoundTag nbt) {
      DataResult<Pair<DataComponentMap, Tag>> result = nbt == null ? null : DataComponentMap.CODEC.decode(NbtOps.INSTANCE, nbt);
      DataComponentMap components = result == null ? null : result.result().<DataComponentMap>map(Pair::getFirst).orElse(null);
      if (components == null) {
         components = new PatchedDataComponentMap(stack.getPrototype());
      }

      stack.applyComponents(components);
   }
}
