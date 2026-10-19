package dev.latvian.mods.rhino;

import dev.latvian.mods.rhino.type.TypeInfo;
import dev.latvian.mods.rhino.util.Deletable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NativeJavaMap extends NativeJavaObject {
   public final Map map;
   public final TypeInfo mapKeyType;
   public final TypeInfo mapValueType;

   public NativeJavaMap(Context cx, Scriptable scope, Object jo, Map map, TypeInfo type) {
      super(scope, jo, type, cx);
      this.map = map;
      this.mapKeyType = type.param(0);
      this.mapValueType = type.param(1);
   }

   @Override
   public String getClassName() {
      return "JavaMap";
   }

   @Override
   public boolean has(Context cx, String name, Scriptable start) {
      return this.map.containsKey(name) ? true : super.has(cx, name, start);
   }

   @Override
   public boolean has(Context cx, int index, Scriptable start) {
      return this.map.containsKey(index) ? true : super.has(cx, index, start);
   }

   @Override
   public Object get(Context cx, String name, Scriptable start) {
      return this.map.containsKey(name) ? cx.javaToJS(this.map.get(cx.jsToJava(name, this.mapKeyType)), start, this.mapValueType) : super.get(cx, name, start);
   }

   @Override
   public Object get(Context cx, int index, Scriptable start) {
      return this.map.containsKey(index)
         ? cx.javaToJS(this.map.get(cx.jsToJava(index, this.mapKeyType)), start, this.mapValueType)
         : super.get(cx, index, start);
   }

   @Override
   public void put(Context cx, String name, Scriptable start, Object value) {
      this.map.put(name, cx.jsToJava(value, this.mapValueType));
   }

   @Override
   public void put(Context cx, int index, Scriptable start, Object value) {
      this.map.put(index, cx.jsToJava(value, this.mapValueType));
   }

   @Override
   public Object[] getIds(Context cx) {
      List<Object> ids = new ArrayList<>(this.map.size());

      for (Object key : this.map.keySet()) {
         if (key instanceof Integer) {
            ids.add(key);
         } else {
            ids.add(ScriptRuntime.toString(cx, key));
         }
      }

      return ids.toArray();
   }

   @Override
   public void delete(Context cx, String name) {
      Deletable.deleteObject(this.map.remove(name));
   }

   @Override
   public void delete(Context cx, int index) {
      Deletable.deleteObject(this.map.remove(index));
   }

   @Override
   protected void initMembers(Context cx, Scriptable scope) {
      super.initMembers(cx, scope);
      this.addCustomFunction("hasOwnProperty", TypeInfo.BOOLEAN, this::hasOwnProperty, TypeInfo.STRING);
   }

   private boolean hasOwnProperty(Context cx, Object[] args) {
      return this.map.containsKey(ScriptRuntime.toString(cx, args[0]));
   }
}
