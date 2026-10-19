package com.blamejared.crafttweaker.api.data.op;

import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.ListData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import java.util.function.Function;

final class AnyTypeListDataAdapter implements ListDataAdapter {
   static final String WRAPPER_KEY = "$$wrapped$$";
   private final ListData data = new ListData();

   private AnyTypeListDataAdapter() {
   }

   static AnyTypeListDataAdapter element(IData data) {
      AnyTypeListDataAdapter adapter = new AnyTypeListDataAdapter();
      return GenericUtil.uncheck(adapter.apply(data));
   }

   static AnyTypeListDataAdapter list(IData data) {
      ListDataAdapter adapter = new AnyTypeListDataAdapter();
      ListDataAdapter result = data.asList().stream().reduce(adapter, Function::apply, OpUtils.noCombiner());
      return GenericUtil.uncheck(result);
   }

   @Override
   public IData finish() {
      return this.data;
   }

   public ListDataAdapter apply(IData data) {
      this.append(data);
      return this;
   }

   @Override
   public String toString() {
      return "ListDataAdapter[ANY/%s]".formatted(this.data);
   }

   private void append(IData data) {
      this.data.add(data.getType() == IData.Type.MAP && !this.wrapper(data) ? data : this.wrap(data));
   }

   private IData wrap(IData data) {
      MapData wrapper = new MapData();
      wrapper.put("$$wrapped$$", data);
      return wrapper;
   }

   private boolean wrapper(IData data) {
      return data.getType() == IData.Type.MAP && data.getKeys().size() == 1 && data.getAt("$$wrapped$$") != null;
   }
}
