package bigchadguys.sellingbin.data.serializable;

import java.util.Optional;
import net.minecraft.nbt.Tag;

public interface INbtSerializable<N extends Tag> {
   Optional<N> writeNbt();

   void readNbt(N var1);
}
