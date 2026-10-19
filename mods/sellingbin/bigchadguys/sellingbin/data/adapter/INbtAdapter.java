package bigchadguys.sellingbin.data.adapter;

import java.util.Optional;
import net.minecraft.nbt.Tag;

public interface INbtAdapter<T, N extends Tag, C> {
   Optional<N> writeNbt(T var1, C var2);

   Optional<T> readNbt(N var1, C var2);
}
