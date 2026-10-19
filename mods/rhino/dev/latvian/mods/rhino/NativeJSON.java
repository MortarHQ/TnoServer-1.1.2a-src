package dev.latvian.mods.rhino;

import dev.latvian.mods.rhino.json.JsonParser;
import java.util.Map;
import java.util.Map.Entry;

public class NativeJSON extends IdScriptableObject {
   private static final Object JSON_TAG = "JSON";
   protected static final int MAX_STRINGIFY_GAP_LENGTH = 10;
   protected static final int Id_toSource = 1;
   protected static final int Id_parse = 2;
   protected static final int Id_stringify = 3;
   protected static final int LAST_METHOD_ID = 3;
   protected static final int MAX_ID = 3;

   static void init(Scriptable scope, boolean sealed, Context cx) {
      NativeJSON obj = new NativeJSON();
      obj.activatePrototypeMap(3);
      obj.setPrototype(getObjectPrototype(scope, cx));
      obj.setParentScope(scope);
      if (sealed) {
         obj.sealObject(cx);
      }

      defineProperty(scope, "JSON", obj, 2, cx);
   }

   private static Object parse(Context cx, Scriptable scope, String jtext) {
      try {
         return new JsonParser(scope).parseValue(cx, jtext);
      } catch (JsonParser.ParseException ex) {
         throw ScriptRuntime.constructError(cx, "SyntaxError", ex.getMessage());
      }
   }

   public static Object parse(Context cx, Scriptable scope, String jtext, Callable reviver) {
      Object unfiltered = parse(cx, scope, jtext);
      Scriptable root = cx.newObject(scope);
      root.put(cx, "", root, unfiltered);
      return walk(cx, scope, reviver, root, "");
   }

   private static Object walk(Context cx, Scriptable scope, Callable reviver, Scriptable holder, Object name) {
      Object property;
      if (name instanceof Number) {
         property = holder.get(cx, ((Number)name).intValue(), holder);
      } else {
         property = holder.get(cx, (String)name, holder);
      }

      if (property instanceof Scriptable val) {
         if (val instanceof NativeArray) {
            long len = ((NativeArray)val).getLength();

            for (long i = 0L; i < len; i++) {
               if (i > 2147483647L) {
                  String id = Long.toString(i);
                  Object newElement = walk(cx, scope, reviver, val, id);
                  if (newElement == Undefined.INSTANCE) {
                     val.delete(cx, id);
                  } else {
                     val.put(cx, id, val, newElement);
                  }
               } else {
                  int idx = (int)i;
                  Object newElement = walk(cx, scope, reviver, val, idx);
                  if (newElement == Undefined.INSTANCE) {
                     val.delete(cx, idx);
                  } else {
                     val.put(cx, idx, val, newElement);
                  }
               }
            }
         } else {
            Object[] keys = val.getIds(cx);

            for (Object p : keys) {
               Object newElement = walk(cx, scope, reviver, val, p);
               if (newElement == Undefined.INSTANCE) {
                  if (p instanceof Number) {
                     val.delete(cx, ((Number)p).intValue());
                  } else {
                     val.delete(cx, (String)p);
                  }
               } else if (p instanceof Number) {
                  val.put(cx, ((Number)p).intValue(), val, newElement);
               } else {
                  val.put(cx, (String)p, val, newElement);
               }
            }
         }
      }

      return reviver.call(cx, scope, holder, new Object[]{name, property});
   }

   public static String stringify(Object value, Object replacer, Object space, Context cx) {
      StringBuilder builder = new StringBuilder();
      stringify0(cx, value, builder);
      return builder.toString();
   }

   private static void escape(StringBuilder builder, String string) {
      builder.append('"');
      builder.append(string.replace("\"", "\\\""));
      builder.append('"');
   }

   private static void stringify0(Context cx, Object v, StringBuilder builder) {
      if (v == null || v instanceof Boolean || v instanceof Number) {
         builder.append(v);
      } else if (v instanceof CharSequence) {
         escape(builder, v.toString());
      } else if (v instanceof NativeString) {
         escape(builder, ScriptRuntime.toString(cx, v));
      } else if (v instanceof NativeNumber) {
         builder.append(ScriptRuntime.toNumber(cx, v));
      } else if (v instanceof Map<?, ?> map) {
         builder.append('{');
         boolean first = true;

         for (Entry<?, ?> entry : map.entrySet()) {
            if (first) {
               first = false;
            } else {
               builder.append(',');
            }

            escape(builder, String.valueOf(entry.getKey()));
            builder.append(':');
            stringify0(cx, entry.getValue(), builder);
         }

         builder.append('}');
      } else if (v instanceof Iterable<?> itr) {
         builder.append('[');
         boolean first = true;

         for (Object value : itr) {
            if (first) {
               first = false;
            } else {
               builder.append(',');
            }

            stringify0(cx, value, builder);
         }

         builder.append(']');
      } else {
         stringify0(cx, cx.getCachedClassStorage(false).get(Wrapper.unwrapped(v).getClass()).getDebugInfo(), builder);
      }
   }

   protected NativeJSON() {
   }

   @Override
   public String getClassName() {
      return "JSON";
   }

   @Override
   protected void initPrototypeId(int id, Context cx) {
      if (id <= 3) {
         String name;
         int arity;
         switch (id) {
            case 1:
               arity = 0;
               name = "toSource";
               break;
            case 2:
               arity = 2;
               name = "parse";
               break;
            case 3:
               arity = 3;
               name = "stringify";
               break;
            default:
               throw new IllegalStateException(String.valueOf(id));
         }

         this.initPrototypeMethod(JSON_TAG, id, name, arity, cx);
      } else {
         throw new IllegalStateException(String.valueOf(id));
      }
   }

   @Override
   public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
      if (!f.hasTag(JSON_TAG)) {
         return super.execIdCall(f, cx, scope, thisObj, args);
      }

      int methodId = f.methodId();
      switch (methodId) {
         case 1:
            return "JSON";
         case 2:
            String jtext = ScriptRuntime.toString(cx, args, 0);
            Object reviver = null;
            if (args.length > 1) {
               reviver = args[1];
            }

            if (reviver instanceof Callable) {
               return parse(cx, scope, jtext, (Callable)reviver);
            }

            return parse(cx, scope, jtext);
         case 3:
            Object value = null;
            Object replacer = null;
            Object space = null;
            switch (args.length) {
               case 3:
                  space = args[2];
               case 2:
                  replacer = args[1];
               case 1:
                  value = args[0];
               case 0:
               default:
                  return this.stringifyJSON(value, replacer, space, cx);
            }
         default:
            throw new IllegalStateException(String.valueOf(methodId));
      }
   }

   public String stringifyJSON(Object value, Object replacer, Object space, Context cx) {
      return stringify(value, replacer, space, cx);
   }

   @Override
   protected int findPrototypeId(String s) {
      return switch (s) {
         case "toSource" -> 1;
         case "parse" -> 2;
         case "stringify" -> 3;
         default -> 0;
      };
   }
}
