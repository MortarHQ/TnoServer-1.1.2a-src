package dev.latvian.mods.rhino;

import dev.latvian.mods.rhino.regexp.NativeRegExp;
import dev.latvian.mods.rhino.util.DataObject;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

public class NativeArray extends IdScriptableObject implements List, DataObject {
   private static final Object ARRAY_TAG = "Array";
   private static final Long NEGATIVE_ONE = -1L;
   private static final int Id_length = 1;
   private static final int MAX_INSTANCE_ID = 1;
   private static final int Id_constructor = 1;
   private static final int Id_toString = 2;
   private static final int Id_toLocaleString = 3;
   private static final int Id_toSource = 4;
   private static final int Id_join = 5;
   private static final int Id_reverse = 6;
   private static final int Id_sort = 7;
   private static final int Id_push = 8;
   private static final int Id_pop = 9;
   private static final int Id_shift = 10;
   private static final int Id_unshift = 11;
   private static final int Id_splice = 12;
   private static final int Id_concat = 13;
   private static final int Id_slice = 14;
   private static final int Id_indexOf = 15;
   private static final int Id_lastIndexOf = 16;
   private static final int Id_every = 17;
   private static final int Id_filter = 18;
   private static final int Id_forEach = 19;
   private static final int Id_map = 20;
   private static final int Id_some = 21;
   private static final int Id_find = 22;
   private static final int Id_findIndex = 23;
   private static final int Id_reduce = 24;
   private static final int Id_reduceRight = 25;
   private static final int Id_fill = 26;
   private static final int Id_keys = 27;
   private static final int Id_values = 28;
   private static final int Id_entries = 29;
   private static final int Id_includes = 30;
   private static final int Id_copyWithin = 31;
   private static final int SymbolId_iterator = 32;
   private static final int MAX_PROTOTYPE_ID = 32;
   private static final int ConstructorId_join = -5;
   private static final int ConstructorId_reverse = -6;
   private static final int ConstructorId_sort = -7;
   private static final int ConstructorId_push = -8;
   private static final int ConstructorId_pop = -9;
   private static final int ConstructorId_shift = -10;
   private static final int ConstructorId_unshift = -11;
   private static final int ConstructorId_splice = -12;
   private static final int ConstructorId_concat = -13;
   private static final int ConstructorId_slice = -14;
   private static final int ConstructorId_indexOf = -15;
   private static final int ConstructorId_lastIndexOf = -16;
   private static final int ConstructorId_every = -17;
   private static final int ConstructorId_filter = -18;
   private static final int ConstructorId_forEach = -19;
   private static final int ConstructorId_map = -20;
   private static final int ConstructorId_some = -21;
   private static final int ConstructorId_find = -22;
   private static final int ConstructorId_findIndex = -23;
   private static final int ConstructorId_reduce = -24;
   private static final int ConstructorId_reduceRight = -25;
   private static final int ConstructorId_isArray = -26;
   private static final int ConstructorId_of = -27;
   private static final int ConstructorId_from = -28;
   private static final int DEFAULT_INITIAL_CAPACITY = 10;
   private static final double GROW_FACTOR = 1.5;
   private static final int MAX_PRE_GROW_SIZE = 1431655764;
   private static int maximumInitialCapacity = 10000;
   private final Context localContext;
   private long length;
   private int lengthAttr = 6;
   private Object[] dense;
   private boolean denseOnly;

   static void init(Scriptable scope, boolean sealed, Context cx) {
      NativeArray obj = new NativeArray(cx, 0L);
      obj.exportAsJSClass(32, scope, sealed, cx);
   }

   static int getMaximumInitialCapacity() {
      return maximumInitialCapacity;
   }

   static void setMaximumInitialCapacity(int maximumInitialCapacity) {
      NativeArray.maximumInitialCapacity = maximumInitialCapacity;
   }

   private static long toArrayIndex(Context cx, Object id) {
      if (id instanceof String) {
         return toArrayIndex(cx, (String)id);
      } else {
         return id instanceof Number ? toArrayIndex(((Number)id).doubleValue()) : -1L;
      }
   }

   private static long toArrayIndex(Context cx, String id) {
      long index = toArrayIndex(ScriptRuntime.toNumber(cx, id));
      return Long.toString(index).equals(id) ? index : -1L;
   }

   private static long toArrayIndex(double d) {
      if (!Double.isNaN(d)) {
         long index = ScriptRuntime.toUint32(d);
         if (index == d && index != 4294967295L) {
            return index;
         }
      }

      return -1L;
   }

   private static int toDenseIndex(Context cx, Object id) {
      long index = toArrayIndex(cx, id);
      return 0L <= index && index < 2147483647L ? (int)index : -1;
   }

   private static Object jsConstructor(Context cx, Scriptable scope, Object[] args) {
      if (args.length == 0) {
         return new NativeArray(cx, 0L);
      }

      Object arg0 = args[0];
      if (args.length <= 1 && arg0 instanceof Number) {
         long len = ScriptRuntime.toUint32(cx, arg0);
         if (len != ((Number)arg0).doubleValue()) {
            String msg = ScriptRuntime.getMessage0("msg.arraylength.bad");
            throw ScriptRuntime.rangeError(cx, msg);
         } else {
            return new NativeArray(cx, len);
         }
      } else {
         return new NativeArray(cx, args);
      }
   }

   private static Scriptable callConstructorOrCreateArray(Context cx, Scriptable scope, Scriptable arg, long length, boolean lengthAlways) {
      Scriptable result = null;
      if (arg instanceof Function) {
         try {
            Object[] args = !lengthAlways && length <= 0L ? ScriptRuntime.EMPTY_OBJECTS : new Object[]{length};
            result = ((Function)arg).construct(cx, scope, args);
         } catch (EcmaError ee) {
            if (!"TypeError".equals(ee.getName())) {
               throw ee;
            }
         }
      }

      if (result == null) {
         result = cx.newArray(scope, length > 2147483647L ? 0 : (int)length);
      }

      return result;
   }

   private static Object js_from(Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
      Scriptable items = ScriptRuntime.toObject(cx, scope, args.length >= 1 ? args[0] : Undefined.INSTANCE);
      Object mapArg = args.length >= 2 ? args[1] : Undefined.INSTANCE;
      Scriptable thisArg = Undefined.SCRIPTABLE_INSTANCE;
      boolean mapping = !Undefined.isUndefined(mapArg);
      Function mapFn = null;
      if (mapping) {
         if (!(mapArg instanceof Function)) {
            throw ScriptRuntime.typeError0(cx, "msg.map.function.not");
         }

         mapFn = (Function)mapArg;
         if (args.length >= 3) {
            thisArg = ensureScriptable(args[2], cx);
         }
      }

      Object iteratorProp = getProperty(items, SymbolKey.ITERATOR, cx);
      if (!(items instanceof NativeArray) && iteratorProp != NOT_FOUND && !Undefined.isUndefined(iteratorProp)) {
         Object iterator = ScriptRuntime.callIterator(cx, scope, items);
         if (!Undefined.isUndefined(iterator)) {
            Scriptable result = callConstructorOrCreateArray(cx, scope, thisObj, 0L, false);
            long k = 0L;

            try (IteratorLikeIterable it = new IteratorLikeIterable(cx, scope, iterator)) {
               for (Object temp : it) {
                  if (mapping) {
                     temp = mapFn.call(cx, scope, thisArg, new Object[]{temp, k});
                  }

                  defineElem(cx, result, k, temp);
                  k++;
               }
            }

            setLengthProperty(cx, result, k);
            return result;
         }
      }

      long length = getLengthProperty(cx, items, false);
      Scriptable result = callConstructorOrCreateArray(cx, scope, thisObj, length, true);

      for (long k = 0L; k < length; k++) {
         Object temp = getRawElem(items, k, cx);
         if (temp != NOT_FOUND) {
            if (mapping) {
               temp = mapFn.call(cx, scope, thisArg, new Object[]{temp, k});
            }

            defineElem(cx, result, k, temp);
         }
      }

      setLengthProperty(cx, result, length);
      return result;
   }

   private static Object js_of(Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
      Scriptable result = callConstructorOrCreateArray(cx, scope, thisObj, args.length, true);

      for (int i = 0; i < args.length; i++) {
         defineElem(cx, result, i, args[i]);
      }

      setLengthProperty(cx, result, args.length);
      return result;
   }

   static long getLengthProperty(Context cx, Scriptable obj, boolean throwIfTooLarge) {
      if (obj instanceof NativeString) {
         return ((NativeString)obj).getLength();
      }

      if (obj instanceof NativeArray) {
         return ((NativeArray)obj).getLength();
      }

      Object len = getProperty(obj, "length", cx);
      if (len == NOT_FOUND) {
         return 0L;
      }

      double doubleLen = ScriptRuntime.toNumber(cx, len);
      if (doubleLen > 9.007199254740991E15) {
         if (throwIfTooLarge) {
            String msg = ScriptRuntime.getMessage0("msg.arraylength.bad");
            throw ScriptRuntime.rangeError(cx, msg);
         } else {
            return 2147483647L;
         }
      } else {
         return doubleLen < 0.0 ? 0L : ScriptRuntime.toUint32(cx, len);
      }
   }

   private static Object setLengthProperty(Context cx, Scriptable target, long length) {
      Object len = ScriptRuntime.wrapNumber(length);
      putProperty(target, "length", len, cx);
      return len;
   }

   private static void deleteElem(Scriptable target, long index, Context cx) {
      int i = (int)index;
      if (i == index) {
         target.delete(cx, i);
      } else {
         target.delete(cx, Long.toString(index));
      }
   }

   private static Object getElem(Context cx, Scriptable target, long index) {
      Object elem = getRawElem(target, index, cx);
      return elem != NOT_FOUND ? elem : Undefined.INSTANCE;
   }

   private static Object getRawElem(Scriptable target, long index, Context cx) {
      return index > 2147483647L ? getProperty(target, Long.toString(index), cx) : getProperty(target, (int)index, cx);
   }

   private static void defineElem(Context cx, Scriptable target, long index, Object value) {
      if (index > 2147483647L) {
         String id = Long.toString(index);
         target.put(cx, id, target, value);
      } else {
         target.put(cx, (int)index, target, value);
      }
   }

   private static void setElem(Context cx, Scriptable target, long index, Object value) {
      if (index > 2147483647L) {
         String id = Long.toString(index);
         putProperty(target, id, value, cx);
      } else {
         putProperty(target, (int)index, value, cx);
      }
   }

   private static void setRawElem(Context cx, Scriptable target, long index, Object value) {
      if (value == NOT_FOUND) {
         deleteElem(target, index, cx);
      } else {
         setElem(cx, target, index, value);
      }
   }

   private static String toStringHelper(Context cx, Scriptable scope, Scriptable thisObj, boolean toLocale) {
      Scriptable o = ScriptRuntime.toObject(cx, scope, thisObj);
      int length = (int)getLengthProperty(cx, o, false);
      if (length == 0) {
         return "[]";
      }

      StringBuilder result = new StringBuilder(256);
      result.append('[');

      for (int i = 0; i < length; i++) {
         if (i > 0) {
            result.append(", ");
         }

         Object elem = getRawElem(o, i, cx);
         if (elem != NOT_FOUND && elem != null && elem != Undefined.INSTANCE) {
            result.append(ScriptRuntime.uneval(cx, scope, elem));
         }
      }

      result.append(']');
      return result.toString();
   }

   private static String toSource(Context cx, Scriptable scope, Scriptable thisObj) {
      Scriptable o = ScriptRuntime.toObject(cx, scope, thisObj);
      int length = (int)getLengthProperty(cx, o, false);
      if (length == 0) {
         return "[]";
      }

      StringBuilder result = new StringBuilder(256);
      result.append('[');

      for (int i = 0; i < length; i++) {
         if (i > 0) {
            result.append(", ");
         }

         Object elem = getRawElem(o, i, cx);
         if (elem != NOT_FOUND && elem != null && elem != Undefined.INSTANCE) {
            result.append(ScriptRuntime.uneval(cx, scope, elem));
         }
      }

      result.append(']');
      return result.toString();
   }

   private static String js_join(Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
      Scriptable o = ScriptRuntime.toObject(cx, scope, thisObj);
      long llength = getLengthProperty(cx, o, false);
      int length = (int)llength;
      if (llength != length) {
         throw Context.reportRuntimeError1("msg.arraylength.too.big", String.valueOf(llength), cx);
      }

      String separator = args.length >= 1 && args[0] != Undefined.INSTANCE ? ScriptRuntime.toString(cx, args[0]) : ",";
      if (o instanceof NativeArray na && na.denseOnly) {
         StringBuilder sb = new StringBuilder();

         for (int i = 0; i < length; i++) {
            if (i != 0) {
               sb.append(separator);
            }

            if (i < na.dense.length) {
               Object temp = na.dense[i];
               if (temp != null && temp != Undefined.INSTANCE && temp != NOT_FOUND) {
                  sb.append(ScriptRuntime.toString(cx, temp));
               }
            }
         }

         return sb.toString();
      } else {
         if (length == 0) {
            return "";
         }

         String[] buf = new String[length];
         int total_size = 0;

         for (int i = 0; i != length; i++) {
            Object temp = getElem(cx, o, i);
            if (temp != null && temp != Undefined.INSTANCE) {
               String str = ScriptRuntime.toString(cx, temp);
               total_size += str.length();
               buf[i] = str;
            }
         }

         total_size += (length - 1) * separator.length();
         StringBuilder sb = new StringBuilder(total_size);

         for (int i = 0; i != length; i++) {
            if (i != 0) {
               sb.append(separator);
            }

            String str = buf[i];
            if (str != null) {
               sb.append(str);
            }
         }

         return sb.toString();
      }
   }

   private static Scriptable js_reverse(Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
      Scriptable o = ScriptRuntime.toObject(cx, scope, thisObj);
      if (o instanceof NativeArray na && na.denseOnly) {
         int i = 0;

         for (int j = (int)na.length - 1; i < j; j--) {
            Object temp = na.dense[i];
            na.dense[i] = na.dense[j];
            na.dense[j] = temp;
            i++;
         }

         return o;
      } else {
         long len = getLengthProperty(cx, o, false);
         long half = len / 2L;

         for (long i = 0L; i < half; i++) {
            long j = len - i - 1L;
            Object temp1 = getRawElem(o, i, cx);
            Object temp2 = getRawElem(o, j, cx);
            setRawElem(cx, o, i, temp2);
            setRawElem(cx, o, j, temp1);
         }

         return o;
      }
   }

   private static Scriptable js_sort(Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
      Scriptable o = ScriptRuntime.toObject(cx, scope, thisObj);
      Comparator<Object> comparator;
      if (args.length > 0 && Undefined.INSTANCE != args[0]) {
         Callable jsCompareFunction = ScriptRuntime.getValueFunctionAndThis(cx, args[0]);
         Scriptable funThis = cx.lastStoredScriptable();
         Object[] cmpBuf = new Object[2];
         comparator = new NativeArray.ElementComparator((x, y) -> {
            cmpBuf[0] = x;
            cmpBuf[1] = y;
            Object ret = jsCompareFunction.call(cx, scope, funThis, cmpBuf);
            double d = ScriptRuntime.toNumber(cx, ret);
            int cmp = Double.compare(d, 0.0);
            if (cmp < 0) {
               return -1;
            } else {
               return cmp > 0 ? 1 : 0;
            }
         });
      } else {
         comparator = new NativeArray.ElementComparator(new NativeArray.StringLikeComparator(cx));
      }

      long llength = getLengthProperty(cx, o, false);
      int length = (int)llength;
      if (llength != length) {
         throw Context.reportRuntimeError1("msg.arraylength.too.big", String.valueOf(llength), cx);
      }

      Object[] working = new Object[length];

      for (int i = 0; i != length; i++) {
         working[i] = getRawElem(o, i, cx);
      }

      Sorting.get().hybridSort(working, comparator);

      for (int i = 0; i < length; i++) {
         setRawElem(cx, o, i, working[i]);
      }

      return o;
   }

   private static Object js_push(Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
      Scriptable o = ScriptRuntime.toObject(cx, scope, thisObj);
      if (o instanceof NativeArray na && na.denseOnly && na.ensureCapacity((int)na.length + args.length)) {
         for (Object arg : args) {
            na.dense[(int)(na.length++)] = arg;
         }

         return ScriptRuntime.wrapNumber(na.length);
      } else {
         long length = getLengthProperty(cx, o, false);

         for (int i = 0; i < args.length; i++) {
            setElem(cx, o, length + i, args[i]);
         }

         length += args.length;
         return setLengthProperty(cx, o, length);
      }
   }

   private static Object js_pop(Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
      Scriptable o = ScriptRuntime.toObject(cx, scope, thisObj);
      if (o instanceof NativeArray na && na.denseOnly && na.length > 0L) {
         na.length--;
         Object result = na.dense[(int)na.length];
         na.dense[(int)na.length] = NOT_FOUND;
         return result;
      } else {
         long length = getLengthProperty(cx, o, false);
         Object result;
         if (length > 0L) {
            result = getElem(cx, o, --length);
            deleteElem(o, length, cx);
         } else {
            result = Undefined.INSTANCE;
         }

         setLengthProperty(cx, o, length);
         return result;
      }
   }

   private static Object js_shift(Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
      Scriptable o = ScriptRuntime.toObject(cx, scope, thisObj);
      if (o instanceof NativeArray na && na.denseOnly && na.length > 0L) {
         na.length--;
         Object result = na.dense[0];
         System.arraycopy(na.dense, 1, na.dense, 0, (int)na.length);
         na.dense[(int)na.length] = NOT_FOUND;
         return result == NOT_FOUND ? Undefined.INSTANCE : result;
      } else {
         long length = getLengthProperty(cx, o, false);
         Object result;
         if (length > 0L) {
            long i = 0L;
            length--;
            result = getElem(cx, o, i);
            if (length > 0L) {
               for (long var13 = 1L; var13 <= length; var13++) {
                  Object temp = getRawElem(o, var13, cx);
                  setRawElem(cx, o, var13 - 1L, temp);
               }
            }

            deleteElem(o, length, cx);
         } else {
            result = Undefined.INSTANCE;
         }

         setLengthProperty(cx, o, length);
         return result;
      }
   }

   private static Object js_unshift(Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
      Scriptable o = ScriptRuntime.toObject(cx, scope, thisObj);
      if (o instanceof NativeArray na && na.denseOnly && na.ensureCapacity((int)na.length + args.length)) {
         System.arraycopy(na.dense, 0, na.dense, args.length, (int)na.length);
         System.arraycopy(args, 0, na.dense, 0, args.length);
         na.length += args.length;
         return ScriptRuntime.wrapNumber(na.length);
      } else {
         long length = getLengthProperty(cx, o, false);
         int argc = args.length;
         if (args.length > 0) {
            if (length > 0L) {
               for (long last = length - 1L; last >= 0L; last--) {
                  Object temp = getRawElem(o, last, cx);
                  setRawElem(cx, o, last + argc, temp);
               }
            }

            for (int i = 0; i < args.length; i++) {
               setElem(cx, o, i, args[i]);
            }
         }

         length += args.length;
         return setLengthProperty(cx, o, length);
      }
   }

   private static Object js_splice(Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
      Scriptable o = ScriptRuntime.toObject(cx, scope, thisObj);
      NativeArray na = null;
      boolean denseMode = false;
      if (o instanceof NativeArray) {
         na = (NativeArray)o;
         denseMode = na.denseOnly;
      }

      scope = getTopLevelScope(scope);
      int argc = args.length;
      if (argc == 0) {
         return cx.newArray(scope, 0);
      }

      long length = getLengthProperty(cx, o, false);
      long begin = toSliceIndex(ScriptRuntime.toInteger(cx, args[0]), length);
      argc--;
      long count;
      if (args.length == 1) {
         count = length - begin;
      } else {
         double dcount = ScriptRuntime.toInteger(cx, args[1]);
         if (dcount < 0.0) {
            count = 0L;
         } else if (dcount > length - begin) {
            count = length - begin;
         } else {
            count = (long)dcount;
         }

         argc--;
      }

      long end = begin + count;
      Object result;
      if (count != 0L) {
         if (denseMode) {
            int intLen = (int)(end - begin);
            Object[] copy = new Object[intLen];
            System.arraycopy(na.dense, (int)begin, copy, 0, intLen);
            result = cx.newArray(scope, copy);
         } else {
            Scriptable resultArray = cx.newArray(scope, 0);

            for (long last = begin; last != end; last++) {
               Object temp = getRawElem(o, last, cx);
               if (temp != NOT_FOUND) {
                  setElem(cx, resultArray, last - begin, temp);
               }
            }

            setLengthProperty(cx, resultArray, end - begin);
            result = resultArray;
         }
      } else {
         result = cx.newArray(scope, 0);
      }

      long delta = argc - count;
      if (denseMode && length + delta < 2147483647L && na.ensureCapacity((int)(length + delta))) {
         System.arraycopy(na.dense, (int)end, na.dense, (int)(begin + argc), (int)(length - end));
         if (argc > 0) {
            System.arraycopy(args, 2, na.dense, (int)begin, argc);
         }

         if (delta < 0L) {
            Arrays.fill(na.dense, (int)(length + delta), (int)length, NOT_FOUND);
         }

         na.length = length + delta;
         return result;
      } else {
         if (delta > 0L) {
            for (long last = length - 1L; last >= end; last--) {
               Object temp = getRawElem(o, last, cx);
               setRawElem(cx, o, last + delta, temp);
            }
         } else if (delta < 0L) {
            for (long last = end; last < length; last++) {
               Object temp = getRawElem(o, last, cx);
               setRawElem(cx, o, last + delta, temp);
            }

            for (long k = length - 1L; k >= length + delta; k--) {
               deleteElem(o, k, cx);
            }
         }

         int argoffset = args.length - argc;

         for (int i = 0; i < argc; i++) {
            setElem(cx, o, begin + i, args[i + argoffset]);
         }

         setLengthProperty(cx, o, length + delta);
         return result;
      }
   }

   private static boolean isConcatSpreadable(Context cx, Scriptable scope, Object val) {
      if (val instanceof Scriptable) {
         Object spreadable = getProperty((Scriptable)val, SymbolKey.IS_CONCAT_SPREADABLE, cx);
         if (spreadable != NOT_FOUND && !Undefined.isUndefined(spreadable)) {
            return ScriptRuntime.toBoolean(cx, spreadable);
         }
      }

      return js_isArray(val);
   }

   private static long concatSpreadArg(Context cx, Scriptable result, Scriptable arg, long offset) {
      long srclen = getLengthProperty(cx, arg, false);
      long newlen = srclen + offset;
      if (newlen <= 2147483647L
         && result instanceof NativeArray denseResult
         && denseResult.denseOnly
         && arg instanceof NativeArray denseArg
         && denseArg.denseOnly) {
         denseResult.ensureCapacity((int)newlen);
         System.arraycopy(denseArg.dense, 0, denseResult.dense, (int)offset, (int)srclen);
         return newlen;
      } else {
         long dstpos = offset;

         for (long srcpos = 0L; srcpos < srclen; dstpos++) {
            Object temp = getRawElem(arg, srcpos, cx);
            if (temp != NOT_FOUND) {
               defineElem(cx, result, dstpos, temp);
            }

            srcpos++;
         }

         return newlen;
      }
   }

   private static long doConcat(Context cx, Scriptable scope, Scriptable result, Object arg, long offset) {
      if (isConcatSpreadable(cx, scope, arg)) {
         return concatSpreadArg(cx, result, (Scriptable)arg, offset);
      }

      defineElem(cx, result, offset, arg);
      return offset + 1L;
   }

   private static Scriptable js_concat(Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
      Scriptable o = ScriptRuntime.toObject(cx, scope, thisObj);
      scope = getTopLevelScope(scope);
      Scriptable result = cx.newArray(scope, 0);
      long length = doConcat(cx, scope, result, o, 0L);

      for (Object arg : args) {
         length = doConcat(cx, scope, result, arg, length);
      }

      setLengthProperty(cx, result, length);
      return result;
   }

   private static Scriptable js_slice(Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
      Scriptable o = ScriptRuntime.toObject(cx, scope, thisObj);
      Scriptable result = cx.newArray(scope, 0);
      long len = getLengthProperty(cx, o, false);
      long begin;
      long end;
      if (args.length == 0) {
         begin = 0L;
         end = len;
      } else {
         begin = toSliceIndex(ScriptRuntime.toInteger(cx, args[0]), len);
         if (args.length != 1 && args[1] != Undefined.INSTANCE) {
            end = toSliceIndex(ScriptRuntime.toInteger(cx, args[1]), len);
         } else {
            end = len;
         }
      }

      for (long slot = begin; slot < end; slot++) {
         Object temp = getRawElem(o, slot, cx);
         if (temp != NOT_FOUND) {
            defineElem(cx, result, slot - begin, temp);
         }
      }

      setLengthProperty(cx, result, Math.max(0L, end - begin));
      return result;
   }

   private static long toSliceIndex(double value, long length) {
      long result;
      if (value < 0.0) {
         if (value + length < 0.0) {
            result = 0L;
         } else {
            result = (long)(value + length);
         }
      } else if (value > length) {
         result = length;
      } else {
         result = (long)value;
      }

      return result;
   }

   private static Object js_indexOf(Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
      Object compareTo = args.length > 0 ? args[0] : Undefined.INSTANCE;
      Scriptable o = ScriptRuntime.toObject(cx, scope, thisObj);
      long length = getLengthProperty(cx, o, false);
      long start;
      if (args.length < 2) {
         start = 0L;
      } else {
         start = (long)ScriptRuntime.toInteger(cx, args[1]);
         if (start < 0L) {
            start += length;
            if (start < 0L) {
               start = 0L;
            }
         }

         if (start > length - 1L) {
            return NEGATIVE_ONE;
         }
      }

      if (o instanceof NativeArray na && na.denseOnly) {
         Scriptable proto = na.getPrototype(cx);

         for (int i = (int)start; i < length; i++) {
            Object val = na.dense[i];
            if (val == NOT_FOUND && proto != null) {
               val = getProperty(proto, i, cx);
            }

            if (val != NOT_FOUND && ScriptRuntime.shallowEq(cx, val, compareTo)) {
               return (long)i;
            }
         }

         return NEGATIVE_ONE;
      } else {
         for (long i = start; i < length; i++) {
            Object val = getRawElem(o, i, cx);
            if (val != NOT_FOUND && ScriptRuntime.shallowEq(cx, val, compareTo)) {
               return i;
            }
         }

         return NEGATIVE_ONE;
      }
   }

   private static Object js_lastIndexOf(Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
      Object compareTo = args.length > 0 ? args[0] : Undefined.INSTANCE;
      Scriptable o = ScriptRuntime.toObject(cx, scope, thisObj);
      long length = getLengthProperty(cx, o, false);
      long start;
      if (args.length < 2) {
         start = length - 1L;
      } else {
         start = (long)ScriptRuntime.toInteger(cx, args[1]);
         if (start >= length) {
            start = length - 1L;
         } else if (start < 0L) {
            start += length;
         }

         if (start < 0L) {
            return NEGATIVE_ONE;
         }
      }

      if (o instanceof NativeArray na && na.denseOnly) {
         Scriptable proto = na.getPrototype(cx);

         for (int i = (int)start; i >= 0; i--) {
            Object val = na.dense[i];
            if (val == NOT_FOUND && proto != null) {
               val = getProperty(proto, i, cx);
            }

            if (val != NOT_FOUND && ScriptRuntime.shallowEq(cx, val, compareTo)) {
               return (long)i;
            }
         }

         return NEGATIVE_ONE;
      } else {
         for (long i = start; i >= 0L; i--) {
            Object val = getRawElem(o, i, cx);
            if (val != NOT_FOUND && ScriptRuntime.shallowEq(cx, val, compareTo)) {
               return i;
            }
         }

         return NEGATIVE_ONE;
      }
   }

   private static Boolean js_includes(Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
      Object compareTo = args.length > 0 ? args[0] : Undefined.INSTANCE;
      Scriptable o = ScriptRuntime.toObject(cx, scope, thisObj);
      long len = ScriptRuntime.toLength(cx, new Object[]{getProperty(thisObj, "length", cx)}, 0);
      if (len == 0L) {
         return Boolean.FALSE;
      }

      long k;
      if (args.length < 2) {
         k = 0L;
      } else {
         k = (long)ScriptRuntime.toInteger(cx, args[1]);
         if (k < 0L) {
            k += len;
            if (k < 0L) {
               k = 0L;
            }
         }

         if (k > len - 1L) {
            return Boolean.FALSE;
         }
      }

      if (o instanceof NativeArray na && na.denseOnly) {
         Scriptable proto = na.getPrototype(cx);

         for (int i = (int)k; i < len; i++) {
            Object elementK = na.dense[i];
            if (elementK == NOT_FOUND && proto != null) {
               elementK = getProperty(proto, i, cx);
            }

            if (elementK == NOT_FOUND) {
               elementK = Undefined.INSTANCE;
            }

            if (ScriptRuntime.sameZero(cx, elementK, compareTo)) {
               return Boolean.TRUE;
            }
         }

         return Boolean.FALSE;
      } else {
         while (k < len) {
            Object elementK = getRawElem(o, k, cx);
            if (elementK == NOT_FOUND) {
               elementK = Undefined.INSTANCE;
            }

            if (ScriptRuntime.sameZero(cx, elementK, compareTo)) {
               return Boolean.TRUE;
            }

            k++;
         }

         return Boolean.FALSE;
      }
   }

   private static Object js_fill(Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
      Scriptable o = ScriptRuntime.toObject(cx, scope, thisObj);
      long len = getLengthProperty(cx, o, false);
      long relativeStart = 0L;
      if (args.length >= 2) {
         relativeStart = (long)ScriptRuntime.toInteger(cx, args[1]);
      }

      long k;
      if (relativeStart < 0L) {
         k = Math.max(len + relativeStart, 0L);
      } else {
         k = Math.min(relativeStart, len);
      }

      long relativeEnd = len;
      if (args.length >= 3 && !Undefined.isUndefined(args[2])) {
         relativeEnd = (long)ScriptRuntime.toInteger(cx, args[2]);
      }

      long fin;
      if (relativeEnd < 0L) {
         fin = Math.max(len + relativeEnd, 0L);
      } else {
         fin = Math.min(relativeEnd, len);
      }

      Object value = args.length > 0 ? args[0] : Undefined.INSTANCE;

      for (long i = k; i < fin; i++) {
         setRawElem(cx, thisObj, i, value);
      }

      return thisObj;
   }

   private static Object js_copyWithin(Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
      Scriptable o = ScriptRuntime.toObject(cx, scope, thisObj);
      long len = getLengthProperty(cx, o, false);
      Object targetArg = args.length >= 1 ? args[0] : Undefined.INSTANCE;
      long relativeTarget = (long)ScriptRuntime.toInteger(cx, targetArg);
      long to;
      if (relativeTarget < 0L) {
         to = Math.max(len + relativeTarget, 0L);
      } else {
         to = Math.min(relativeTarget, len);
      }

      Object startArg = args.length >= 2 ? args[1] : Undefined.INSTANCE;
      long relativeStart = (long)ScriptRuntime.toInteger(cx, startArg);
      long from;
      if (relativeStart < 0L) {
         from = Math.max(len + relativeStart, 0L);
      } else {
         from = Math.min(relativeStart, len);
      }

      long relativeEnd = len;
      if (args.length >= 3 && !Undefined.isUndefined(args[2])) {
         relativeEnd = (long)ScriptRuntime.toInteger(cx, args[2]);
      }

      long fin;
      if (relativeEnd < 0L) {
         fin = Math.max(len + relativeEnd, 0L);
      } else {
         fin = Math.min(relativeEnd, len);
      }

      long count = Math.min(fin - from, len - to);
      int direction = 1;
      if (from < to && to < from + count) {
         direction = -1;
         from = from + count - 1L;
         to = to + count - 1L;
      }

      if (o instanceof NativeArray na && count <= 2147483647L && na.denseOnly) {
         while (count > 0L) {
            na.dense[(int)to] = na.dense[(int)from];
            from += direction;
            to += direction;
            count--;
         }

         return thisObj;
      } else {
         while (count > 0L) {
            Object temp = getRawElem(o, from, cx);
            if (temp != NOT_FOUND && !Undefined.isUndefined(temp)) {
               setElem(cx, o, to, temp);
            } else {
               deleteElem(o, to, cx);
            }

            from += direction;
            to += direction;
            count--;
         }

         return thisObj;
      }
   }

   private static Object iterativeMethod(Context cx, IdFunctionObject idFunctionObject, Scriptable scope, Scriptable thisObj, Object[] args) {
      Scriptable o = ScriptRuntime.toObject(cx, scope, thisObj);
      int id = Math.abs(idFunctionObject.methodId());
      if (22 == id || 23 == id) {
         ScriptRuntimeES6.requireObjectCoercible(cx, o, idFunctionObject);
      }

      long length = getLengthProperty(cx, o, id == 20);
      Object callbackArg = args.length > 0 ? args[0] : Undefined.INSTANCE;
      if (callbackArg != null && callbackArg instanceof Function f) {
         if (callbackArg instanceof NativeRegExp) {
            throw ScriptRuntime.notFunctionError(cx, callbackArg);
         }

         Scriptable parent = getTopLevelScope(f);
         Scriptable thisArg;
         if (args.length >= 2 && args[1] != null && args[1] != Undefined.INSTANCE) {
            thisArg = ScriptRuntime.toObject(cx, scope, args[1]);
         } else {
            thisArg = parent;
         }

         Scriptable array = null;
         if (id == 18 || id == 20) {
            int resultLength = id == 20 ? (int)length : 0;
            array = cx.newArray(scope, resultLength);
         }

         long j = 0L;

         for (long i = 0L; i < length; i++) {
            Object[] innerArgs = new Object[3];
            Object elem = getRawElem(o, i, cx);
            if (elem == NOT_FOUND) {
               if (id != 22 && id != 23) {
                  continue;
               }

               elem = Undefined.INSTANCE;
            }

            innerArgs[0] = elem;
            innerArgs[1] = i;
            innerArgs[2] = o;
            Object result = f.call(cx, parent, thisArg, innerArgs);
            switch (id) {
               case 17:
                  if (!ScriptRuntime.toBoolean(cx, result)) {
                     return Boolean.FALSE;
                  }
                  break;
               case 18:
                  if (ScriptRuntime.toBoolean(cx, result)) {
                     defineElem(cx, array, j++, innerArgs[0]);
                  }
               case 19:
               default:
                  break;
               case 20:
                  defineElem(cx, array, i, result);
                  break;
               case 21:
                  if (ScriptRuntime.toBoolean(cx, result)) {
                     return Boolean.TRUE;
                  }
                  break;
               case 22:
                  if (ScriptRuntime.toBoolean(cx, result)) {
                     return elem;
                  }
                  break;
               case 23:
                  if (ScriptRuntime.toBoolean(cx, result)) {
                     return ScriptRuntime.wrapNumber(i);
                  }
            }
         }
         return switch (id) {
            case 17 -> Boolean.TRUE;
            case 18, 20 -> array;
            default -> Undefined.INSTANCE;
            case 21 -> Boolean.FALSE;
            case 23 -> ScriptRuntime.wrapNumber(-1.0);
         };
      } else {
         throw ScriptRuntime.notFunctionError(cx, callbackArg);
      }
   }

   private static Object reduceMethod(Context cx, int id, Scriptable scope, Scriptable thisObj, Object[] args) {
      Scriptable o = ScriptRuntime.toObject(cx, scope, thisObj);
      long length = getLengthProperty(cx, o, false);
      Object callbackArg = args.length > 0 ? args[0] : Undefined.INSTANCE;
      if (callbackArg != null && callbackArg instanceof Function f) {
         Scriptable parent = getTopLevelScope(f);
         boolean movingLeft = id == 24;
         Object value = args.length > 1 ? args[1] : NOT_FOUND;

         for (long i = 0L; i < length; i++) {
            long index = movingLeft ? i : length - 1L - i;
            Object elem = getRawElem(o, index, cx);
            if (elem != NOT_FOUND) {
               if (value == NOT_FOUND) {
                  value = elem;
               } else {
                  Object[] innerArgs = new Object[]{value, elem, index, o};
                  value = f.call(cx, parent, parent, innerArgs);
               }
            }
         }

         if (value == NOT_FOUND) {
            throw ScriptRuntime.typeError0(cx, "msg.empty.array.reduce");
         } else {
            return value;
         }
      } else {
         throw ScriptRuntime.notFunctionError(cx, callbackArg);
      }
   }

   private static boolean js_isArray(Object o) {
      return o instanceof NativeJavaList || o instanceof List || o instanceof Scriptable s && "Array".equals(s.getClassName());
   }

   public NativeArray(Context cx, long lengthArg) {
      this.localContext = cx;
      this.denseOnly = lengthArg <= maximumInitialCapacity;
      if (this.denseOnly) {
         int intLength = (int)lengthArg;
         if (intLength < 10) {
            intLength = 10;
         }

         this.dense = new Object[intLength];
         Arrays.fill(this.dense, NOT_FOUND);
      }

      this.length = lengthArg;
   }

   public NativeArray(Context cx, Object[] array) {
      this.localContext = cx;
      this.denseOnly = true;
      this.dense = array;
      this.length = array.length;
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder("[");

      for (int i = 0; i < this.size(); i++) {
         if (i > 0) {
            sb.append(", ");
         }

         sb.append(this.get(i));
      }

      return sb.append(']').toString();
   }

   @Override
   public String getClassName() {
      return "Array";
   }

   @Override
   protected int getMaxInstanceId() {
      return 1;
   }

   @Override
   protected void setInstanceIdAttributes(int id, int attr, Context cx) {
      if (id == 1) {
         this.lengthAttr = attr;
      }
   }

   @Override
   protected int findInstanceIdInfo(String s, Context cx) {
      return s.equals("length") ? instanceIdInfo(this.lengthAttr, 1) : super.findInstanceIdInfo(s, cx);
   }

   @Override
   protected String getInstanceIdName(int id) {
      return id == 1 ? "length" : super.getInstanceIdName(id);
   }

   @Override
   protected Object getInstanceIdValue(int id, Context cx) {
      return id == 1 ? ScriptRuntime.wrapNumber(this.length) : super.getInstanceIdValue(id, cx);
   }

   @Override
   protected void setInstanceIdValue(int id, Object value, Context cx) {
      if (id == 1) {
         this.setLength(cx, value);
      } else {
         super.setInstanceIdValue(id, value, cx);
      }
   }

   @Override
   protected void fillConstructorProperties(IdFunctionObject ctor, Context cx) {
      this.addIdFunctionProperty(ctor, ARRAY_TAG, -5, "join", 1, cx);
      this.addIdFunctionProperty(ctor, ARRAY_TAG, -6, "reverse", 0, cx);
      this.addIdFunctionProperty(ctor, ARRAY_TAG, -7, "sort", 1, cx);
      this.addIdFunctionProperty(ctor, ARRAY_TAG, -8, "push", 1, cx);
      this.addIdFunctionProperty(ctor, ARRAY_TAG, -9, "pop", 0, cx);
      this.addIdFunctionProperty(ctor, ARRAY_TAG, -10, "shift", 0, cx);
      this.addIdFunctionProperty(ctor, ARRAY_TAG, -11, "unshift", 1, cx);
      this.addIdFunctionProperty(ctor, ARRAY_TAG, -12, "splice", 2, cx);
      this.addIdFunctionProperty(ctor, ARRAY_TAG, -13, "concat", 1, cx);
      this.addIdFunctionProperty(ctor, ARRAY_TAG, -14, "slice", 2, cx);
      this.addIdFunctionProperty(ctor, ARRAY_TAG, -15, "indexOf", 1, cx);
      this.addIdFunctionProperty(ctor, ARRAY_TAG, -16, "lastIndexOf", 1, cx);
      this.addIdFunctionProperty(ctor, ARRAY_TAG, -17, "every", 1, cx);
      this.addIdFunctionProperty(ctor, ARRAY_TAG, -18, "filter", 1, cx);
      this.addIdFunctionProperty(ctor, ARRAY_TAG, -19, "forEach", 1, cx);
      this.addIdFunctionProperty(ctor, ARRAY_TAG, -20, "map", 1, cx);
      this.addIdFunctionProperty(ctor, ARRAY_TAG, -21, "some", 1, cx);
      this.addIdFunctionProperty(ctor, ARRAY_TAG, -22, "find", 1, cx);
      this.addIdFunctionProperty(ctor, ARRAY_TAG, -23, "findIndex", 1, cx);
      this.addIdFunctionProperty(ctor, ARRAY_TAG, -24, "reduce", 1, cx);
      this.addIdFunctionProperty(ctor, ARRAY_TAG, -25, "reduceRight", 1, cx);
      this.addIdFunctionProperty(ctor, ARRAY_TAG, -26, "isArray", 1, cx);
      this.addIdFunctionProperty(ctor, ARRAY_TAG, -27, "of", 0, cx);
      this.addIdFunctionProperty(ctor, ARRAY_TAG, -28, "from", 1, cx);
      super.fillConstructorProperties(ctor, cx);
   }

   @Override
   protected void initPrototypeId(int id, Context cx) {
      if (id == 32) {
         this.initPrototypeMethod(ARRAY_TAG, id, SymbolKey.ITERATOR, "[Symbol.iterator]", 0, cx);
      } else {
         String fnName = null;
         String s;
         int arity;
         switch (id) {
            case 1:
               arity = 1;
               s = "constructor";
               break;
            case 2:
               arity = 0;
               s = "toString";
               break;
            case 3:
               arity = 0;
               s = "toLocaleString";
               break;
            case 4:
               arity = 0;
               s = "toSource";
               break;
            case 5:
               arity = 1;
               s = "join";
               break;
            case 6:
               arity = 0;
               s = "reverse";
               break;
            case 7:
               arity = 1;
               s = "sort";
               break;
            case 8:
               arity = 1;
               s = "push";
               break;
            case 9:
               arity = 0;
               s = "pop";
               break;
            case 10:
               arity = 0;
               s = "shift";
               break;
            case 11:
               arity = 1;
               s = "unshift";
               break;
            case 12:
               arity = 2;
               s = "splice";
               break;
            case 13:
               arity = 1;
               s = "concat";
               break;
            case 14:
               arity = 2;
               s = "slice";
               break;
            case 15:
               arity = 1;
               s = "indexOf";
               break;
            case 16:
               arity = 1;
               s = "lastIndexOf";
               break;
            case 17:
               arity = 1;
               s = "every";
               break;
            case 18:
               arity = 1;
               s = "filter";
               break;
            case 19:
               arity = 1;
               s = "forEach";
               break;
            case 20:
               arity = 1;
               s = "map";
               break;
            case 21:
               arity = 1;
               s = "some";
               break;
            case 22:
               arity = 1;
               s = "find";
               break;
            case 23:
               arity = 1;
               s = "findIndex";
               break;
            case 24:
               arity = 1;
               s = "reduce";
               break;
            case 25:
               arity = 1;
               s = "reduceRight";
               break;
            case 26:
               arity = 1;
               s = "fill";
               break;
            case 27:
               arity = 0;
               s = "keys";
               break;
            case 28:
               arity = 0;
               s = "values";
               break;
            case 29:
               arity = 0;
               s = "entries";
               break;
            case 30:
               arity = 1;
               s = "includes";
               break;
            case 31:
               arity = 2;
               s = "copyWithin";
               break;
            default:
               throw new IllegalArgumentException(String.valueOf(id));
         }

         this.initPrototypeMethod(ARRAY_TAG, id, s, fnName, arity, cx);
      }
   }

   @Override
   public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
      if (!f.hasTag(ARRAY_TAG)) {
         return super.execIdCall(f, cx, scope, thisObj, args);
      }

      int id = f.methodId();

      while (true) {
         switch (id) {
            case -28:
               return js_from(cx, scope, thisObj, args);
            case -27:
               return js_of(cx, scope, thisObj, args);
            case -26:
               return args.length > 0 && js_isArray(args[0]);
            case -25:
            case -24:
            case -23:
            case -22:
            case -21:
            case -20:
            case -19:
            case -18:
            case -17:
            case -16:
            case -15:
            case -14:
            case -13:
            case -12:
            case -11:
            case -10:
            case -9:
            case -8:
            case -7:
            case -6:
            case -5:
               if (args.length > 0) {
                  thisObj = ScriptRuntime.toObject(cx, scope, args[0]);
                  Object[] newArgs = new Object[args.length - 1];
                  System.arraycopy(args, 1, newArgs, 0, newArgs.length);
                  args = newArgs;
               }

               id = -id;
               break;
            case -4:
            case -3:
            case -2:
            case -1:
            case 0:
            default:
               throw new IllegalArgumentException("Array.prototype has no method: " + f.getFunctionName());
            case 1:
               boolean inNewExpr = thisObj == null;
               if (!inNewExpr) {
                  return f.construct(cx, scope, args);
               }

               return jsConstructor(cx, scope, args);
            case 2:
               return toStringHelper(cx, scope, thisObj, false);
            case 3:
               return toStringHelper(cx, scope, thisObj, true);
            case 4:
               return toSource(cx, scope, thisObj);
            case 5:
               return js_join(cx, scope, thisObj, args);
            case 6:
               return js_reverse(cx, scope, thisObj, args);
            case 7:
               return js_sort(cx, scope, thisObj, args);
            case 8:
               return js_push(cx, scope, thisObj, args);
            case 9:
               return js_pop(cx, scope, thisObj, args);
            case 10:
               return js_shift(cx, scope, thisObj, args);
            case 11:
               return js_unshift(cx, scope, thisObj, args);
            case 12:
               return js_splice(cx, scope, thisObj, args);
            case 13:
               return js_concat(cx, scope, thisObj, args);
            case 14:
               return js_slice(cx, scope, thisObj, args);
            case 15:
               return js_indexOf(cx, scope, thisObj, args);
            case 16:
               return js_lastIndexOf(cx, scope, thisObj, args);
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
               return iterativeMethod(cx, f, scope, thisObj, args);
            case 24:
            case 25:
               return reduceMethod(cx, id, scope, thisObj, args);
            case 26:
               return js_fill(cx, scope, thisObj, args);
            case 27:
               thisObj = ScriptRuntime.toObject(cx, scope, thisObj);
               return new NativeArrayIterator(cx, scope, thisObj, NativeArrayIterator.ArrayIteratorType.KEYS);
            case 28:
            case 32:
               thisObj = ScriptRuntime.toObject(cx, scope, thisObj);
               return new NativeArrayIterator(cx, scope, thisObj, NativeArrayIterator.ArrayIteratorType.VALUES);
            case 29:
               thisObj = ScriptRuntime.toObject(cx, scope, thisObj);
               return new NativeArrayIterator(cx, scope, thisObj, NativeArrayIterator.ArrayIteratorType.ENTRIES);
            case 30:
               return js_includes(cx, scope, thisObj, args);
            case 31:
               return js_copyWithin(cx, scope, thisObj, args);
         }
      }
   }

   @Override
   public Object get(Context cx, int index, Scriptable start) {
      if (!this.denseOnly && this.isGetterOrSetter(null, index, false)) {
         return super.get(cx, index, start);
      } else {
         return this.dense != null && 0 <= index && index < this.dense.length ? this.dense[index] : super.get(cx, index, start);
      }
   }

   @Override
   public boolean has(Context cx, int index, Scriptable start) {
      if (!this.denseOnly && this.isGetterOrSetter(null, index, false)) {
         return super.has(cx, index, start);
      } else {
         return this.dense != null && 0 <= index && index < this.dense.length ? this.dense[index] != NOT_FOUND : super.has(cx, index, start);
      }
   }

   @Override
   public void put(Context cx, String id, Scriptable start, Object value) {
      super.put(cx, id, start, value);
      if (start == this) {
         long index = toArrayIndex(cx, id);
         if (index >= this.length) {
            this.length = index + 1L;
            this.denseOnly = false;
         }
      }
   }

   private boolean ensureCapacity(int capacity) {
      if (capacity > this.dense.length) {
         if (capacity > 1431655764) {
            this.denseOnly = false;
            return false;
         }

         capacity = Math.max(capacity, (int)(this.dense.length * 1.5));
         Object[] newDense = new Object[capacity];
         System.arraycopy(this.dense, 0, newDense, 0, this.dense.length);
         Arrays.fill(newDense, this.dense.length, newDense.length, NOT_FOUND);
         this.dense = newDense;
      }

      return true;
   }

   @Override
   public void put(Context cx, int index, Scriptable start, Object value) {
      if (start == this && !this.isSealed(cx) && this.dense != null && 0 <= index && (this.denseOnly || !this.isGetterOrSetter(null, index, true))) {
         if (!this.isExtensible() && this.length <= index) {
            return;
         }

         if (index < this.dense.length) {
            this.dense[index] = value;
            if (this.length <= index) {
               this.length = index + 1L;
            }

            return;
         }

         if (this.denseOnly && index < this.dense.length * 1.5 && this.ensureCapacity(index + 1)) {
            this.dense[index] = value;
            this.length = index + 1L;
            return;
         }

         this.denseOnly = false;
      }

      super.put(cx, index, start, value);
      if (start == this && (this.lengthAttr & 1) == 0 && this.length <= index) {
         this.length = index + 1L;
      }
   }

   @Override
   public void delete(Context cx, int index) {
      if (this.dense == null || 0 > index || index >= this.dense.length || this.isSealed(cx) || !this.denseOnly && this.isGetterOrSetter(null, index, true)) {
         super.delete(cx, index);
      } else {
         this.dense[index] = NOT_FOUND;
      }
   }

   @Override
   public Object[] getIds(Context cx, boolean nonEnumerable, boolean getSymbols) {
      Object[] superIds = super.getIds(cx, nonEnumerable, getSymbols);
      if (this.dense == null) {
         return superIds;
      }

      int N = this.dense.length;
      long currentLength = this.length;
      if (N > currentLength) {
         N = (int)currentLength;
      }

      if (N == 0) {
         return superIds;
      }

      int superLength = superIds.length;
      Object[] ids = new Object[N + superLength];
      int presentCount = 0;

      for (int i = 0; i != N; i++) {
         if (this.dense[i] != NOT_FOUND) {
            ids[presentCount] = i;
            presentCount++;
         }
      }

      if (presentCount != N) {
         Object[] tmp = new Object[presentCount + superLength];
         System.arraycopy(ids, 0, tmp, 0, presentCount);
         ids = tmp;
      }

      System.arraycopy(superIds, 0, ids, presentCount, superLength);
      return ids;
   }

   public List<Integer> getIndexIds(Context cx) {
      Object[] ids = this.getIds(cx);
      List<Integer> indices = new ArrayList<>(ids.length);

      for (Object id : ids) {
         int int32Id = ScriptRuntime.toInt32(cx, id);
         if (int32Id >= 0 && ScriptRuntime.toString(cx, int32Id).equals(ScriptRuntime.toString(cx, id))) {
            indices.add(int32Id);
         }
      }

      return indices;
   }

   private ScriptableObject defaultIndexPropertyDescriptor(Object value, Context cx) {
      Scriptable scope = this.getParentScope();
      if (scope == null) {
         scope = this;
      }

      ScriptableObject desc = new NativeObject(cx.factory);
      ScriptRuntime.setBuiltinProtoAndParent(cx, scope, desc, TopLevel.Builtins.Object);
      desc.defineProperty(cx, "value", value, 0);
      desc.defineProperty(cx, "writable", Boolean.TRUE, 0);
      desc.defineProperty(cx, "enumerable", Boolean.TRUE, 0);
      desc.defineProperty(cx, "configurable", Boolean.TRUE, 0);
      return desc;
   }

   @Override
   public int getAttributes(Context cx, int index) {
      return this.dense != null && index >= 0 && index < this.dense.length && this.dense[index] != NOT_FOUND ? 0 : super.getAttributes(cx, index);
   }

   @Override
   protected ScriptableObject getOwnPropertyDescriptor(Context cx, Object id) {
      if (this.dense != null) {
         int index = toDenseIndex(cx, id);
         if (0 <= index && index < this.dense.length && this.dense[index] != NOT_FOUND) {
            Object value = this.dense[index];
            return this.defaultIndexPropertyDescriptor(value, cx);
         }
      }

      return super.getOwnPropertyDescriptor(cx, id);
   }

   @Override
   protected void defineOwnProperty(Context cx, Object id, ScriptableObject desc, boolean checkValid) {
      if (this.dense != null) {
         Object[] values = this.dense;
         this.dense = null;
         this.denseOnly = false;

         for (int i = 0; i < values.length; i++) {
            if (values[i] != NOT_FOUND) {
               this.put(cx, i, this, values[i]);
            }
         }
      }

      long index = toArrayIndex(cx, id);
      if (index >= this.length) {
         this.length = index + 1L;
      }

      super.defineOwnProperty(cx, id, desc, checkValid);
   }

   public long getLength() {
      return this.length;
   }

   private void setLength(Context cx, Object val) {
      if ((this.lengthAttr & 1) == 0) {
         double d = ScriptRuntime.toNumber(cx, val);
         long longVal = ScriptRuntime.toUint32(d);
         if (longVal != d) {
            String msg = ScriptRuntime.getMessage0("msg.arraylength.bad");
            throw ScriptRuntime.rangeError(cx, msg);
         }

         if (this.denseOnly) {
            if (longVal < this.length) {
               Arrays.fill(this.dense, (int)longVal, this.dense.length, NOT_FOUND);
               this.length = longVal;
               return;
            }

            if (longVal < 1431655764L && longVal < this.length * 1.5 && this.ensureCapacity((int)longVal)) {
               this.length = longVal;
               return;
            }

            this.denseOnly = false;
         }

         if (longVal < this.length) {
            if (this.length - longVal > 4096L) {
               Object[] e = this.getIds(cx);

               for (Object id : e) {
                  if (id instanceof String strId) {
                     long index = toArrayIndex(cx, strId);
                     if (index >= longVal) {
                        this.delete(cx, strId);
                     }
                  } else {
                     int index = (Integer)id;
                     if (index >= longVal) {
                        this.delete(cx, index);
                     }
                  }
               }
            } else {
               for (long i = longVal; i < this.length; i++) {
                  deleteElem(this, i, cx);
               }
            }
         }

         this.length = longVal;
      }
   }

   void setDenseOnly(boolean denseOnly) {
      if (denseOnly && !this.denseOnly) {
         throw new IllegalArgumentException();
      }

      this.denseOnly = denseOnly;
   }

   @Override
   public boolean contains(Object o) {
      return this.indexOf(o) > -1;
   }

   @Override
   public Object[] toArray() {
      return this.toArray(ScriptRuntime.EMPTY_OBJECTS);
   }

   @Override
   public Object[] toArray(Object[] a) {
      long longLen = this.length;
      if (longLen > 2147483647L) {
         throw new IllegalStateException();
      }

      int len = (int)longLen;
      Object[] array = a.length >= len ? a : (Object[])Array.newInstance(a.getClass().getComponentType(), len);

      for (int i = 0; i < len; i++) {
         array[i] = this.get(i);
      }

      return array;
   }

   @Override
   public boolean containsAll(Collection c) {
      for (Object aC : c) {
         if (!this.contains(aC)) {
            return false;
         }
      }

      return true;
   }

   @Override
   public int size() {
      long longLen = this.length;
      if (longLen > 2147483647L) {
         throw new IllegalStateException();
      } else {
         return (int)longLen;
      }
   }

   @Override
   public boolean isEmpty() {
      return this.length == 0L;
   }

   public Object get(long index, Context cx) {
      if (index >= 0L && index < this.length) {
         Object value = getRawElem(this, index, cx);
         if (value == NOT_FOUND || value == Undefined.INSTANCE) {
            return null;
         } else {
            return value instanceof Wrapper ? ((Wrapper)value).unwrap() : value;
         }
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   @Override
   public Object get(int index) {
      return this.get(index, this.localContext);
   }

   @Override
   public int indexOf(Object o) {
      long longLen = this.length;
      if (longLen > 2147483647L) {
         throw new IllegalStateException();
      }

      int len = (int)longLen;
      if (o == null) {
         for (int i = 0; i < len; i++) {
            if (this.get(i) == null) {
               return i;
            }
         }
      } else {
         for (int i = 0; i < len; i++) {
            if (o.equals(this.get(i))) {
               return i;
            }
         }
      }

      return -1;
   }

   @Override
   public int lastIndexOf(Object o) {
      long longLen = this.length;
      if (longLen > 2147483647L) {
         throw new IllegalStateException();
      }

      int len = (int)longLen;
      if (o == null) {
         for (int i = len - 1; i >= 0; i--) {
            if (this.get(i) == null) {
               return i;
            }
         }
      } else {
         for (int i = len - 1; i >= 0; i--) {
            if (o.equals(this.get(i))) {
               return i;
            }
         }
      }

      return -1;
   }

   @Override
   public Iterator iterator() {
      return this.listIterator(0);
   }

   @Override
   public ListIterator listIterator() {
      return this.listIterator(0);
   }

   @Override
   public ListIterator listIterator(final int start) {
      long longLen = this.length;
      if (longLen > 2147483647L) {
         throw new IllegalStateException();
      } else {
         final int len = (int)longLen;
         if (start >= 0 && start <= len) {
            return new ListIterator() {
               int cursor = start;

               @Override
               public boolean hasNext() {
                  return this.cursor < len;
               }

               @Override
               public Object next() {
                  if (this.cursor == len) {
                     throw new NoSuchElementException();
                  } else {
                     return NativeArray.this.get(this.cursor++);
                  }
               }

               @Override
               public boolean hasPrevious() {
                  return this.cursor > 0;
               }

               @Override
               public Object previous() {
                  if (this.cursor == 0) {
                     throw new NoSuchElementException();
                  } else {
                     return NativeArray.this.get(--this.cursor);
                  }
               }

               @Override
               public int nextIndex() {
                  return this.cursor;
               }

               @Override
               public int previousIndex() {
                  return this.cursor - 1;
               }

               @Override
               public void remove() {
                  throw new UnsupportedOperationException();
               }

               @Override
               public void add(Object o) {
                  throw new UnsupportedOperationException();
               }

               @Override
               public void set(Object o) {
                  throw new UnsupportedOperationException();
               }
            };
         } else {
            throw new IndexOutOfBoundsException("Index: " + start);
         }
      }
   }

   @Override
   public boolean add(Object o) {
      throw new UnsupportedOperationException();
   }

   @Override
   public boolean remove(Object o) {
      throw new UnsupportedOperationException();
   }

   @Override
   public boolean addAll(Collection c) {
      throw new UnsupportedOperationException();
   }

   @Override
   public boolean removeAll(Collection c) {
      throw new UnsupportedOperationException();
   }

   @Override
   public boolean retainAll(Collection c) {
      throw new UnsupportedOperationException();
   }

   @Override
   public void clear() {
      throw new UnsupportedOperationException();
   }

   @Override
   public void add(int index, Object element) {
      throw new UnsupportedOperationException();
   }

   @Override
   public boolean addAll(int index, Collection c) {
      throw new UnsupportedOperationException();
   }

   @Override
   public Object set(int index, Object element) {
      throw new UnsupportedOperationException();
   }

   @Override
   public Object remove(int index) {
      throw new UnsupportedOperationException();
   }

   @Override
   public List subList(int fromIndex, int toIndex) {
      throw new UnsupportedOperationException();
   }

   @Override
   protected int findPrototypeId(Symbol k) {
      return SymbolKey.ITERATOR.equals(k) ? 32 : 0;
   }

   @Override
   protected int findPrototypeId(String s) {
      return switch (s) {
         case "constructor" -> 1;
         case "toString" -> 2;
         case "toLocaleString" -> 3;
         case "toSource" -> 4;
         case "join" -> 5;
         case "reverse" -> 6;
         case "sort" -> 7;
         case "push" -> 8;
         case "pop" -> 9;
         case "shift" -> 10;
         case "unshift" -> 11;
         case "splice" -> 12;
         case "concat" -> 13;
         case "slice" -> 14;
         case "indexOf" -> 15;
         case "lastIndexOf" -> 16;
         case "every" -> 17;
         case "filter" -> 18;
         case "forEach" -> 19;
         case "map" -> 20;
         case "some" -> 21;
         case "find" -> 22;
         case "findIndex" -> 23;
         case "reduce" -> 24;
         case "reduceRight" -> 25;
         case "fill" -> 26;
         case "keys" -> 27;
         case "values" -> 28;
         case "entries" -> 29;
         case "includes" -> 30;
         case "copyWithin" -> 31;
         default -> 0;
      };
   }

   @Override
   public <T> T createDataObject(Supplier<T> instanceFactory, Context cx) {
      List<T> list = this.createDataObjectList(instanceFactory, cx);
      if (list.isEmpty()) {
         throw new ArrayIndexOutOfBoundsException("Array doesn't contain any objects");
      } else {
         return list.getFirst();
      }
   }

   @Override
   public <T> List<T> createDataObjectList(Supplier<T> instanceFactory, Context cx) {
      List<T> list = new ArrayList<>();

      for (Object o : this) {
         if (o instanceof DataObject) {
            list.add(((DataObject)o).createDataObject(instanceFactory, cx));
         } else {
            list.add((T)o);
         }
      }

      return list;
   }

   @Override
   public boolean isDataObjectList() {
      return true;
   }

   public record ElementComparator(Comparator<Object> child) implements Comparator<Object> {
      @Override
      public int compare(Object x, Object y) {
         if (x == Undefined.INSTANCE) {
            if (y == Undefined.INSTANCE) {
               return 0;
            } else {
               return y == Scriptable.NOT_FOUND ? -1 : 1;
            }
         } else if (x == Scriptable.NOT_FOUND) {
            return y == Scriptable.NOT_FOUND ? 0 : 1;
         } else if (y == Scriptable.NOT_FOUND) {
            return -1;
         } else {
            return y == Undefined.INSTANCE ? -1 : this.child.compare(x, y);
         }
      }
   }

   public record StringLikeComparator(Context cx) implements Comparator<Object> {
      @Override
      public int compare(Object x, Object y) {
         String a = ScriptRuntime.toString(this.cx, x);
         String b = ScriptRuntime.toString(this.cx, y);
         return a.compareTo(b);
      }
   }
}
