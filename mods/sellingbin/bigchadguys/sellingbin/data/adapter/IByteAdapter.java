package bigchadguys.sellingbin.data.adapter;

import io.netty.buffer.ByteBuf;
import java.util.Optional;

public interface IByteAdapter<T, C> {
   void writeBytes(T var1, ByteBuf var2, C var3);

   Optional<T> readBytes(ByteBuf var1, C var2);
}
