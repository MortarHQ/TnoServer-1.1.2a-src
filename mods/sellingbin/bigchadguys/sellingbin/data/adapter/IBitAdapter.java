package bigchadguys.sellingbin.data.adapter;

import bigchadguys.sellingbin.data.bit.BitBuffer;
import java.util.Optional;

public interface IBitAdapter<T, C> {
   void writeBits(T var1, BitBuffer var2, C var3);

   Optional<T> readBits(BitBuffer var1, C var2);
}
