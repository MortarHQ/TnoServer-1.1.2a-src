package net.unusual.block_factorys_bosses.util;

import java.util.function.Consumer;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RiseUtil {
   public static void ifBoolPresent(CompoundTag tag, String name, Consumer<Boolean> consumer) {
      if (tag.contains(name)) {
         consumer.accept(tag.getBoolean(name));
      }
   }
}
