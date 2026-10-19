package com.blamejared.crafttweaker.api.data.op;

import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import java.util.function.Supplier;
import net.minecraft.nbt.CollectionTag;
import net.minecraft.nbt.Tag;

final class SameTypeListDataAdapter<T extends IData> implements ListDataAdapter {
   private final IData.Type type;
   private final T data;

   private SameTypeListDataAdapter(IData.Type type, Supplier<T> creator) {
      this.type = type;
      this.data = creator.get();
   }

   static <T extends IData> SameTypeListDataAdapter<T> list(IData data) {
      IData.Type type = typeFromCollectionType(data);
      return new SameTypeListDataAdapter<>(type, () -> GenericUtil.uncheck(data.copyInternal()));
   }

   static <T extends IData> SameTypeListDataAdapter<T> element(IData data, Supplier<T> creator) {
      SameTypeListDataAdapter<T> adapter = new SameTypeListDataAdapter<>(data.getType(), creator);
      return GenericUtil.uncheck(adapter.apply(data));
   }

   private static IData.Type typeFromCollectionType(IData data) {
      CollectionTag<?> tag = GenericUtil.uncheck(data.getInternal());

      return switch (tag.getElementType()) {
         case 0 -> IData.Type.EMPTY;
         case 1 -> IData.Type.BYTE;
         case 2 -> IData.Type.SHORT;
         case 3 -> IData.Type.INT;
         case 4 -> IData.Type.LONG;
         case 5 -> IData.Type.FLOAT;
         case 6 -> IData.Type.DOUBLE;
         case 7 -> IData.Type.BYTE_ARRAY;
         case 8 -> IData.Type.STRING;
         case 9 -> IData.Type.LIST;
         case 10 -> IData.Type.MAP;
         case 11 -> IData.Type.INT_ARRAY;
         case 12 -> IData.Type.LONG_ARRAY;
         default -> throw new IllegalStateException("Unexpected value: " + tag.getElementType());
      };
   }

   @Override
   public IData finish() {
      return this.data;
   }

   public ListDataAdapter apply(IData data) {
      if (data.getType() == this.type) {
         GenericUtil.<CollectionTag>uncheck(this.data.getInternal()).add((Tag)GenericUtil.uncheck(data.getInternal()));
         return this;
      } else {
         AnyTypeListDataAdapter adapter = AnyTypeListDataAdapter.list(this.finish());
         return adapter.apply(data);
      }
   }

   @Override
   public String toString() {
      return "ListDataAdapter[%s/%s]".formatted(this.type, this.data);
   }
}
