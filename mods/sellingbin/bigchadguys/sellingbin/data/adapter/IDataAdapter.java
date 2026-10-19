package bigchadguys.sellingbin.data.adapter;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Optional;

public interface IDataAdapter<T, C> {
   void writeData(T var1, DataOutput var2, C var3) throws IOException;

   Optional<T> readData(DataInput var1, C var2) throws IOException;
}
