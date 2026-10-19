package bigchadguys.sellingbin.data.serializable;

import bigchadguys.sellingbin.data.bit.BitBuffer;

public interface IBitSerializable {
   void writeBits(BitBuffer var1);

   void readBits(BitBuffer var1);
}
