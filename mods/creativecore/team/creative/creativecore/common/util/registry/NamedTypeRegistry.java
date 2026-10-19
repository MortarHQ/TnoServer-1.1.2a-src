package team.creative.creativecore.common.util.registry;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import team.creative.creativecore.common.util.registry.exception.IdNotFoundException;
import team.creative.creativecore.common.util.registry.exception.RegistryException;

public class NamedTypeRegistry<T> {
   private final HashMap<String, Class<? extends T>> types = new LinkedHashMap<>();
   private final HashMap<Class<? extends T>, String> typesInv = new LinkedHashMap<>();
   private final List<Class[]> possibleConstructors = new ArrayList<>();
   private boolean allowOverwrite = false;

   public NamedTypeRegistry<T> addConstructorPattern(Class... classes) {
      this.possibleConstructors.add(classes);
      return this;
   }

   public NamedTypeRegistry<T> allowOverwrite() {
      this.allowOverwrite = true;
      return this;
   }

   protected String printConstructors() {
      StringBuilder builder = new StringBuilder("[");

      for (Class[] classes : this.possibleConstructors) {
         builder.append("(");

         for (int i = 0; i < classes.length; i++) {
            if (i > 0) {
               builder.append(",");
            }

            builder.append(classes[i].getSimpleName());
         }

         builder.append(")");
      }

      builder.append("]");
      return builder.toString();
   }

   protected void checkConstructor(Class<? extends T> clazz) {
      if (this.possibleConstructors.isEmpty()) {
         this.possibleConstructors.add(new Class[0]);
      }

      Constructor[] constructors = clazz.getConstructors();

      for (Constructor constructor : constructors) {
         Parameter[] pars = constructor.getParameters();

         for (Class[] classes : this.possibleConstructors) {
            if (pars.length == classes.length) {
               int i = 0;

               while (i < classes.length && pars[i].getType().isAssignableFrom(classes[i])) {
                  i++;
               }

               return;
            }
         }
      }

      throw new IllegalArgumentException("Invalid class " + clazz + ". Missing constructor " + this.printConstructors());
   }

   protected void checkConstructor(Object... objects) throws RegistryException {
      for (Class[] classes : this.possibleConstructors) {
         if (objects.length == classes.length) {
            int i = 0;

            while (i < classes.length && classes[i].isAssignableFrom(objects[i].getClass())) {
               i++;
            }

            return;
         }
      }

      throw new NamedTypeRegistry.ConstructorNotFoundException(objects);
   }

   public void register(String id, Class<? extends T> type) {
      if (!this.allowOverwrite && this.types.containsKey(id)) {
         throw new IllegalArgumentException("'" + id + "' already exists");
      }

      this.types.put(id, type);
      this.typesInv.put(type, id);
   }

   public String getId(T type) {
      return this.typesInv.get(type.getClass());
   }

   public String getIdOrDefault(T type, String defaultValue) {
      return this.typesInv.getOrDefault(type.getClass(), defaultValue);
   }

   public String getId(Class<? extends T> type) {
      return this.typesInv.get(type);
   }

   public Class<? extends T> get(String id) {
      return this.types.get(id);
   }

   public T create(String id, Object... objects) throws RegistryException {
      Class<? extends T> clazz = this.types.get(id);
      if (clazz == null) {
         throw new IdNotFoundException(id);
      }

      this.checkConstructor(objects);
      Class[] classes = new Class[objects.length];

      for (int i = 0; i < classes.length; i++) {
         classes[i] = objects[i].getClass();
      }

      try {
         return (T)clazz.getConstructor(classes).newInstance(objects);
      } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
         throw new NamedTypeRegistry.ConstructorForbiddenException(classes, clazz);
      }
   }

   public T createSafe(Class<? extends T> ifFailed, String id, Object... objects) {
      try {
         return this.create(id, objects);
      } catch (RegistryException e) {
         Class[] classes = new Class[objects.length];

         for (int i = 0; i < classes.length; i++) {
            classes[i] = objects[i].getClass();
         }

         try {
            return (T)ifFailed.getConstructor(classes).newInstance(objects);
         } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e2) {
            throw new RuntimeException("Constructor " + toString(classes) + " is not reachable", e2);
         }
      }
   }

   public Collection<String> keys() {
      return this.types.keySet();
   }

   public Set<Entry<String, Class<? extends T>>> entrySet() {
      return this.types.entrySet();
   }

   public Collection<Class<? extends T>> values() {
      return this.types.values();
   }

   public boolean contains(String id) {
      return this.types.containsKey(id);
   }

   protected static String toString(Object[] var0) {
      if (var0 == null) {
         return "null";
      }

      int var1 = var0.length - 1;
      if (var1 == -1) {
         return "[]";
      }

      StringBuilder var2 = new StringBuilder();
      var2.append('[');
      int var3 = 0;

      while (true) {
         var2.append(var0[var3].getClass().getSimpleName());
         if (var3 == var1) {
            return var2.append(']').toString();
         }

         var2.append(", ");
         var3++;
      }
   }

   protected static String toString(Class[] var0) {
      if (var0 == null) {
         return "null";
      }

      int var1 = var0.length - 1;
      if (var1 == -1) {
         return "[]";
      }

      StringBuilder var2 = new StringBuilder();
      var2.append('[');
      int var3 = 0;

      while (true) {
         var2.append(var0[var3].getSimpleName());
         if (var3 == var1) {
            return var2.append(']').toString();
         }

         var2.append(", ");
         var3++;
      }
   }

   public static class ConstructorForbiddenException extends RegistryException {
      public ConstructorForbiddenException(Class[] classes, Class clazz) {
         super("Constructor " + NamedTypeRegistry.toString(classes) + " is not reachable in " + clazz.getSimpleName());
      }
   }

   public static class ConstructorNotFoundException extends RegistryException {
      public ConstructorNotFoundException(Object[] objects) {
         super("Constructor " + NamedTypeRegistry.toString(objects) + " does not exists");
      }
   }
}
