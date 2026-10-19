package xaero.hud.minimap.radar.icon.creator.render.form.model.resolver;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.ListModel;
import xaero.hud.minimap.MinimapLogs;
import xaero.hud.util.SeparatedKeysParser;
import xaero.lib.common.reflection.util.ReflectionUtils;

public class RadarIconModelFieldResolver {
   public static final SeparatedKeysParser KEYS_PARSER = new SeparatedKeysParser(c -> c == ',' || c == ';');

   public static Object[] handleDeclaredField(Field f, Object currentChainNode, String matchedFilterElement, Object[] oneResultArray) throws IllegalArgumentException {
      Object referencedObject = ReflectionUtils.getReflectFieldValue(currentChainNode, f);
      if (referencedObject == null) {
         return null;
      }

      RadarIconModelFieldResolver.FieldReferenceType<?> referenceType = getReferenceType(referencedObject);
      Object[] collectionArray = referenceType.getArray(referencedObject, oneResultArray);
      if (collectionArray.length == 0) {
         return collectionArray;
      }

      if (matchedFilterElement != null && matchedFilterElement.endsWith("]")) {
         int lastStartBracket = matchedFilterElement.lastIndexOf(91);
         if (lastStartBracket == -1) {
            throw new IllegalArgumentException("Field name " + matchedFilterElement + " ends with ] but is missing [!");
         }

         try {
            String keysString = matchedFilterElement.substring(lastStartBracket + 1, matchedFilterElement.length() - 1);
            String[] keys = KEYS_PARSER.parseKeys(keysString);
            Object[] result = keys.length == 1 ? oneResultArray : (Object[])Array.newInstance(oneResultArray.getClass().getComponentType(), keys.length);

            for (int i = 0; i < keys.length; i++) {
               String keyString = keys[i];
               Object element = referenceType.getElement(referencedObject, collectionArray, keyString);
               result[i] = element;
            }

            return result;
         } catch (Exception nfe) {
            throw new IllegalArgumentException("Invalid element index/indices in " + matchedFilterElement + "!", nfe);
         }
      } else {
         return collectionArray;
      }
   }

   public static void searchSuperclassFields(
      Object currentChainNode, List<String> filter, RadarIconModelFieldResolver.Listener listener, Object[] oneResultArray
   ) {
      Class<?> nodeClass = currentChainNode.getClass();

      while (nodeClass != EntityModel.class && nodeClass != AgeableListModel.class && nodeClass != ListModel.class && nodeClass != Object.class) {
         Field[] declaredModelFields = nodeClass.getDeclaredFields();
         handleFields(currentChainNode, declaredModelFields, filter, listener, oneResultArray);
         if (listener.shouldStop() || (nodeClass = nodeClass.getSuperclass()) == null) {
            break;
         }
      }
   }

   public static void handleFields(
      Object currentChainNode, Field[] declaredModelFields, List<String> filter, RadarIconModelFieldResolver.Listener listener, Object[] oneResultArray
   ) {
      for (Field f : declaredModelFields) {
         if (listener.isFieldAllowed(f)) {
            try {
               String comparisonName = f.getDeclaringClass().getName() + ";" + f.getName();
               String matchedFilterElement = null;
               if (filter == null || (matchedFilterElement = passesFilter(comparisonName, filter)) != null) {
                  Object[] matchingObjects = handleDeclaredField(f, currentChainNode, matchedFilterElement, oneResultArray);
                  if (matchingObjects != null) {
                     listener.onFieldResolved(matchingObjects, matchedFilterElement);
                  }

                  if (listener.shouldStop()) {
                     break;
                  }
               }
            } catch (Exception e) {
               MinimapLogs.LOGGER.error("suppressed exception", e);
            }
         }
      }
   }

   private static String passesFilter(String entry, List<String> filter) {
      for (String f : filter) {
         if (f.equals(entry)) {
            return f;
         }

         int indexOfBracket = f.lastIndexOf(91);
         if (indexOfBracket != -1 && f.substring(0, indexOfBracket).equals(entry)) {
            return f;
         }
      }

      return null;
   }

   private static RadarIconModelFieldResolver.FieldReferenceType<?> getReferenceType(Object o) {
      if (o instanceof Object[]) {
         return RadarIconModelFieldResolver.FieldReferenceType.ARRAY;
      } else if (o instanceof Collection) {
         return RadarIconModelFieldResolver.FieldReferenceType.COLLECTION;
      } else {
         return o instanceof Map ? RadarIconModelFieldResolver.FieldReferenceType.MAP : RadarIconModelFieldResolver.FieldReferenceType.SINGLE;
      }
   }

   @FunctionalInterface
   private interface FieldReferenceElementGetter<T> {
      Object get(T var1, Object[] var2, String var3);
   }

   private static class FieldReferenceType<T> {
      public static RadarIconModelFieldResolver.FieldReferenceType<Object> SINGLE = new RadarIconModelFieldResolver.FieldReferenceType<>((o, a, k) -> {
         throw new RuntimeException(String.format("%s is not an array/collection!"));
      }, (o, ora) -> {
         ora[0] = o;
         return ora;
      });
      public static RadarIconModelFieldResolver.FieldReferenceType<Object[]> ARRAY = new RadarIconModelFieldResolver.FieldReferenceType<>(
         (o, a, k) -> o[Integer.parseInt(k.trim())], (o, ora) -> o
      );
      public static RadarIconModelFieldResolver.FieldReferenceType<Collection<?>> COLLECTION = new RadarIconModelFieldResolver.FieldReferenceType<>(
         (o, a, k) -> a[Integer.parseInt(k.trim())], (o, ora) -> o.toArray(ora)
      );
      public static RadarIconModelFieldResolver.FieldReferenceType<Map<?, ?>> MAP = new RadarIconModelFieldResolver.FieldReferenceType<>((o, a, k) -> {
         Object result = o.get(k);
         if (result == null) {
            try {
               int integerAttemptKey = Integer.parseInt(k.trim());
               result = o.get(integerAttemptKey);
            } catch (NumberFormatException var5) {
            }
         }

         return result;
      }, (o, ora) -> o.values().toArray(ora));
      private RadarIconModelFieldResolver.FieldReferenceElementGetter<T> elementGetter;
      private BiFunction<T, Object[], Object[]> arrayGetter;

      private FieldReferenceType(RadarIconModelFieldResolver.FieldReferenceElementGetter<T> elementGetter, BiFunction<T, Object[], Object[]> arrayGetter) {
         this.elementGetter = elementGetter;
         this.arrayGetter = arrayGetter;
      }

      public Object[] getArray(Object referencedObject, Object[] oneResultArray) {
         return this.arrayGetter.apply((T)referencedObject, oneResultArray);
      }

      public Object getElement(Object referencedObject, Object[] array, String key) {
         return this.elementGetter.get((T)referencedObject, array, key);
      }
   }

   public interface Listener {
      boolean isFieldAllowed(Field var1);

      boolean shouldStop();

      void onFieldResolved(Object[] var1, String var2);
   }
}
