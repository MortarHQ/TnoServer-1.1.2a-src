package cc.sighs.oelib.network.serialization;

import cc.sighs.oelib.network.util.ReflectionUtil;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.lang.reflect.Type;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public final class NetworkRecordCodecBuilder {
   private NetworkRecordCodecBuilder() {
   }

   public static <T> StreamCodec<RegistryFriendlyByteBuf, T> build(Class<T> recordClass) {
      final RecordComponent[] components = recordClass.getRecordComponents();
      Class<?>[] parameterTypes = new Class[components.length];
      final Method[] accessors = new Method[components.length];
      final StreamCodec<RegistryFriendlyByteBuf, Object>[] customCodecs = new StreamCodec[components.length];
      Type[] genericTypes = new Type[components.length];
      final ComponentIO.ComponentPlan[] plans = new ComponentIO.ComponentPlan[components.length];

      for (int i = 0; i < components.length; i++) {
         RecordComponent component = components[i];
         parameterTypes[i] = component.getType();
         accessors[i] = component.getAccessor();
         accessors[i].setAccessible(true);
         customCodecs[i] = CustomCodecResolver.resolve(recordClass, component);
         genericTypes[i] = component.getGenericType();
         if (customCodecs[i] == null) {
            plans[i] = ComponentIO.planOf(parameterTypes[i], genericTypes[i]);
         }
      }

      final Constructor<T> constructor = ReflectionUtil.findCanonicalConstructor(recordClass, parameterTypes);
      return new StreamCodec<RegistryFriendlyByteBuf, T>() {
         public T decode(RegistryFriendlyByteBuf buf) {
            Object[] values = new Object[components.length];

            for (int i = 0; i < components.length; i++) {
               StreamCodec<RegistryFriendlyByteBuf, Object> codec = customCodecs[i];
               if (codec != null) {
                  values[i] = codec.decode(buf);
               } else {
                  values[i] = ComponentIO.decodeWithPlan(buf, plans[i], 0, components[i].getName(), recordClass);
               }
            }

            return ReflectionUtil.newInstance(constructor, values);
         }

         public void encode(RegistryFriendlyByteBuf buf, T value) {
            for (int i = 0; i < components.length; i++) {
               Object fieldValue = ReflectionUtil.invoke(accessors[i], value);
               StreamCodec<RegistryFriendlyByteBuf, Object> codec = customCodecs[i];
               if (codec != null) {
                  codec.encode(buf, fieldValue);
               } else {
                  ComponentIO.encodeWithPlan(buf, plans[i], fieldValue, 0, components[i].getName(), recordClass);
               }
            }
         }
      };
   }
}
