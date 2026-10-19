package bigchadguys.sellingbin.data.serializable;

import io.netty.buffer.ByteBuf;

public interface IByteSerializable {
   void writeBytes(ByteBuf var1);

   void readBytes(ByteBuf var1);
}
