package cc.sighs.oelib.network.serialization;

import cc.sighs.oelib.OELib;
import cc.sighs.oelib.network.util.ReflectionUtil;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.RecordComponent;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

final class CustomCodecResolver {
   private static final ConcurrentHashMap<CustomCodecResolver.CodecCacheKey, StreamCodec<RegistryFriendlyByteBuf, Object>> CACHE = new ConcurrentHashMap<>();
   private static final ConcurrentHashMap<CustomCodecResolver.RegistryCacheKey, StreamCodec<RegistryFriendlyByteBuf, Object>> REGISTRY_CACHE = new ConcurrentHashMap<>();
   private static final ConcurrentHashMap<CustomCodecResolver.JsonCacheKey, StreamCodec<RegistryFriendlyByteBuf, Object>> JSON_CACHE = new ConcurrentHashMap<>();

   private CustomCodecResolver() {
   }

   static StreamCodec<RegistryFriendlyByteBuf, Object> resolve(Class<?> recordClass, RecordComponent component) {
      NetFieldCodec net = component.getAnnotation(NetFieldCodec.class);
      if (net != null) {
         CustomCodecResolver.CodecCacheKey key = new CustomCodecResolver.CodecCacheKey(net.holder(), component.getGenericType());
         return CACHE.computeIfAbsent(key, k -> resolveInternal(recordClass, component, net));
      } else {
         RegistryCodec reg = component.getAnnotation(RegistryCodec.class);
         if (reg != null) {
            CustomCodecResolver.RegistryCacheKey key = new CustomCodecResolver.RegistryCacheKey(reg.value(), component.getGenericType());
            return REGISTRY_CACHE.computeIfAbsent(key, k -> resolveRegistryCodec(component, reg));
         } else {
            JsonCodec json = component.getAnnotation(JsonCodec.class);
            if (json != null) {
               CustomCodecResolver.JsonCacheKey key = new CustomCodecResolver.JsonCacheKey(json.holder(), json.field(), component.getGenericType());
               return JSON_CACHE.computeIfAbsent(key, k -> resolveJsonCodec(component, json));
            } else {
               return null;
            }
         }
      }
   }

   private static StreamCodec<RegistryFriendlyByteBuf, Object> resolveInternal(Class<?> recordClass, RecordComponent component, NetFieldCodec meta) {
      Class<?> holder = meta.holder();
      String fieldName = meta.field();
      if (fieldName != null && !fieldName.isEmpty()) {
         StreamCodec<RegistryFriendlyByteBuf, Object> direct = tryResolveByName(recordClass, holder, fieldName);
         if (direct != null) {
            return direct;
         }
      }

      StreamCodec<RegistryFriendlyByteBuf, Object> scanned = resolveBySemanticScan(recordClass, holder, component);
      if (scanned != null) {
         return scanned;
      } else {
         throw new IllegalStateException(
            "Failed to resolve codec for component " + component.getName() + " of record " + recordClass.getName() + " using holder " + holder.getName()
         );
      }
   }

   private static StreamCodec<RegistryFriendlyByteBuf, Object> tryResolveByName(Class<?> recordClass, Class<?> holder, String fieldName) {
      try {
         Field field = holder.getDeclaredField(fieldName);
         if (Modifier.isStatic(field.getModifiers()) && StreamCodec.class.isAssignableFrom(field.getType())) {
            StreamCodec<RegistryFriendlyByteBuf, Object> codec = ReflectionUtil.getStaticFieldAsCodec(recordClass, holder, field);
            OELib.LOGGER.debug("NetFieldCodec: Resolved codec for {} via {}.{}", new Object[]{recordClass.getSimpleName(), holder.getSimpleName(), fieldName});
            return codec;
         } else {
            return null;
         }
      } catch (NoSuchFieldException e) {
         OELib.LOGGER.warn("NetFieldCodec: Field {}.{} not found", holder.getSimpleName(), fieldName);
         return null;
      }
   }

   private static StreamCodec<RegistryFriendlyByteBuf, Object> resolveBySemanticScan(Class<?> recordClass, Class<?> holder, RecordComponent component) {
      Field[] fields = holder.getDeclaredFields();
      if (fields.length == 0) {
         return null;
      }

      Type componentType = component.getGenericType();
      boolean isList = isListType(componentType);
      boolean isOptional = isOptionalType(componentType);
      StreamCodec<RegistryFriendlyByteBuf, Object> bestCodec = null;
      int bestScore = Integer.MIN_VALUE;

      for (Field field : fields) {
         int mods = field.getModifiers();
         if (Modifier.isPublic(mods) && Modifier.isStatic(mods) && Modifier.isFinal(mods) && StreamCodec.class.isAssignableFrom(field.getType())) {
            int score = scoreCodecField(field, componentType, isList, isOptional);
            if (score > bestScore) {
               bestScore = score;
               bestCodec = ReflectionUtil.getStaticFieldAsCodec(recordClass, holder, field);
            }
         }
      }

      if (bestCodec != null) {
         OELib.LOGGER.debug("NetFieldCodec: Resolved codec for {} via semantic scan in {}", recordClass.getSimpleName(), holder.getSimpleName());
      }

      return bestCodec;
   }

   private static int scoreCodecField(Field field, Type componentType, boolean componentIsList, boolean componentIsOptional) {
      int score = 0;
      Type genericType = field.getGenericType();
      Type bufferType = null;
      Type valueType = null;
      if (genericType instanceof ParameterizedType pt && pt.getActualTypeArguments().length == 2) {
         bufferType = pt.getActualTypeArguments()[0];
         valueType = pt.getActualTypeArguments()[1];
      }

      if (isSameType(valueType, componentType)) {
         score += 100;
      }

      String name = field.getName().toUpperCase(Locale.ROOT);
      if (componentIsList && name.contains("LIST")) {
         score += 40;
      }

      if (componentIsOptional && name.contains("OPTIONAL")) {
         score += 40;
      }

      if (bufferType == RegistryFriendlyByteBuf.class) {
         score += 20;
      }

      if (bufferType == ByteBuf.class) {
         score -= 50;
      }

      return score;
   }

   private static boolean isListType(Type type) {
      if (type instanceof ParameterizedType pt) {
         if (pt.getRawType() instanceof Class<?> c && List.class.isAssignableFrom(c)) {
            return true;
         }

         if (isOptionalType(type)) {
            Type[] args = pt.getActualTypeArguments();
            return args.length == 1 && isListType(args[0]);
         }
      }

      return false;
   }

   private static boolean isOptionalType(Type type) {
      if (type instanceof ParameterizedType pt) {
         Type raw = pt.getRawType();
         return raw == Optional.class;
      } else {
         return false;
      }
   }

   private static boolean isSameType(Type a, Type b) {
      if (a == null || b == null) {
         return false;
      } else if (a.equals(b)) {
         return true;
      } else if (a instanceof Class<?> ca && b instanceof Class<?> cb) {
         return ca.isAssignableFrom(cb) || cb.isAssignableFrom(ca);
      } else {
         return a instanceof ParameterizedType pa && b instanceof ParameterizedType pb ? pa.getRawType().equals(pb.getRawType()) : false;
      }
   }

   private static StreamCodec<RegistryFriendlyByteBuf, Object> resolveRegistryCodec(RecordComponent component, RegistryCodec meta) {
      ResourceLocation regId = ResourceLocation.parse(meta.value());
      final ResourceKey<Registry<Object>> key = ResourceKey.createRegistryKey(regId);
      return new StreamCodec<RegistryFriendlyByteBuf, Object>() {
         public Object decode(RegistryFriendlyByteBuf buf) {
            RegistryAccess access = buf.registryAccess();
            Registry<Object> registry = access.registryOrThrow(key);
            int id = buf.readVarInt();
            return registry.getHolder(id).orElseThrow(() -> new IllegalArgumentException("Invalid ID " + id + " for registry " + key.location()));
         }

         public void encode(RegistryFriendlyByteBuf buf, Object value) {
            RegistryAccess access = buf.registryAccess();
            Registry<Object> registry = access.registryOrThrow(key);
            if (value instanceof Holder<?> holder) {
               Object rawValue = holder.value();
               buf.writeVarInt(registry.getId(rawValue));
            }
         }
      };
   }

   private static StreamCodec<RegistryFriendlyByteBuf, Object> resolveJsonCodec(RecordComponent component, JsonCodec meta) {
      Class<? extends Object> searchIn = (Class<? extends Object>)(meta.holder() == Void.class ? component.getType() : meta.holder());
      String fieldName = meta.field();
      if (fieldName != null && !fieldName.isEmpty()) {
         StreamCodec<RegistryFriendlyByteBuf, Object> direct = tryResolveJsonByName(component.getDeclaringRecord(), searchIn, fieldName);
         if (direct != null) {
            return direct;
         }
      }

      StreamCodec<RegistryFriendlyByteBuf, Object> scanned = resolveJsonBySemanticScan(component.getDeclaringRecord(), searchIn, component);
      if (scanned != null) {
         return scanned;
      } else {
         throw new IllegalStateException("Failed to resolve JSON Codec for component " + component.getName() + " using holder/type " + searchIn.getName());
      }
   }

   private static StreamCodec<RegistryFriendlyByteBuf, Object> tryResolveJsonByName(Class<?> recordClass, Class<?> holder, String fieldName) {
      try {
         Field f = holder.getDeclaredField(fieldName);
         int mods = f.getModifiers();
         if (Modifier.isStatic(mods) && Codec.class.isAssignableFrom(f.getType())) {
            Codec<Object> codec = ReflectionUtil.getStaticField(recordClass, holder, f, Codec.class);
            OELib.LOGGER.debug("JsonCodec: Resolved JSON codec for {} via {}.{}", new Object[]{recordClass.getSimpleName(), holder.getSimpleName(), fieldName});
            return NetworkSerialization.jsonCodec(codec);
         } else {
            return null;
         }
      } catch (NoSuchFieldException e) {
         OELib.LOGGER.warn("JsonCodec: Field {}.{} not found", holder.getSimpleName(), fieldName);
         return null;
      }
   }

   private static StreamCodec<RegistryFriendlyByteBuf, Object> resolveJsonBySemanticScan(Class<?> recordClass, Class<?> holder, RecordComponent component) {
      Field[] fields = holder.getDeclaredFields();
      if (fields.length == 0) {
         return null;
      }

      Type componentType = component.getGenericType();
      StreamCodec<RegistryFriendlyByteBuf, Object> best = null;
      int bestScore = Integer.MIN_VALUE;

      for (Field f : fields) {
         int mods = f.getModifiers();
         if (Modifier.isPublic(mods) && Modifier.isStatic(mods) && Modifier.isFinal(mods) && Codec.class.isAssignableFrom(f.getType())) {
            int score = 0;
            Type valueType = null;
            if (f.getGenericType() instanceof ParameterizedType pt && pt.getActualTypeArguments().length == 1) {
               valueType = pt.getActualTypeArguments()[0];
            }

            if (isSameType(valueType, componentType)) {
               score += 100;
            }

            String name = f.getName().toUpperCase(Locale.ROOT);
            if (name.contains("CODEC")) {
               score += 20;
            }

            if (score > bestScore) {
               bestScore = score;
               Codec codec = ReflectionUtil.getStaticField(recordClass, holder, f, Codec.class);
               best = NetworkSerialization.jsonCodec(codec);
            }
         }
      }

      if (best != null) {
         OELib.LOGGER.debug("JsonCodec: Resolved JSON codec for {} via semantic scan in {}", recordClass.getSimpleName(), holder.getSimpleName());
      }

      return best;
   }

   private record CodecCacheKey(Class<?> holder, Type componentType) {
   }

   private record JsonCacheKey(Class<?> holder, String field, Type componentType) {
   }

   private record RegistryCacheKey(String registryId, Type componentType) {
   }
}
