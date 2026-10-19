package cc.sighs.oelib.network.serialization;

import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.PublicKey;
import java.time.Instant;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.joml.Quaternionf;
import org.joml.Vector3f;

final class ComponentIO {
   static final int MAX_DEPTH = 64;

   private ComponentIO() {
   }

   static Object decodeWithPlan(RegistryFriendlyByteBuf buf, ComponentIO.ComponentPlan plan, int depth, String name, Class<?> owner) {
      if (depth > 64) {
         throw new IllegalStateException("Decoding depth exceeded for record " + owner.getName() + "#" + name);
      }

      if (plan.readHandle != null) {
         try {
            return (Object)plan.readHandle.invoke((RegistryFriendlyByteBuf)buf);
         } catch (Throwable t) {
            throw new IllegalStateException("Failed to decode component for record " + owner.getName() + "#" + name, t);
         }
      } else {
         switch (plan.kind) {
            case INT:
               return buf.readVarInt();
            case LONG:
               return buf.readVarLong();
            case BOOLEAN:
               return buf.readBoolean();
            case FLOAT:
               return buf.readFloat();
            case DOUBLE:
               return buf.readDouble();
            case BYTE:
               return buf.readByte();
            case SHORT:
               return buf.readShort();
            case STRING:
               return buf.readUtf();
            case UUID:
               return buf.readUUID();
            case BYTE_ARRAY:
               return buf.readByteArray();
            case INT_ARRAY:
               return buf.readVarIntArray();
            case LONG_ARRAY:
               return buf.readLongArray();
            case DATE:
               return buf.readDate();
            case INSTANT:
               return buf.readInstant();
            case BITSET:
               return buf.readBitSet();
            case PUBLIC_KEY:
               return buf.readPublicKey();
            case BLOCK_POS:
               return buf.readBlockPos();
            case CHUNK_POS:
               return buf.readChunkPos();
            case SECTION_POS:
               return buf.readSectionPos();
            case GLOBAL_POS:
               return buf.readGlobalPos();
            case VEC3:
               return buf.readVec3();
            case VECTOR3F:
               return buf.readVector3f();
            case QUATERNIONF:
               return buf.readQuaternion();
            case RESOURCE_LOCATION:
               return buf.readResourceLocation();
            case BLOCK_HIT_RESULT:
               return buf.readBlockHitResult();
            case COMPOUND_TAG:
               return buf.readNbt();
            case TAG:
               return buf.readNbt(NbtAccounter.unlimitedHeap());
            case ENUM:
               return buf.readEnum(plan.enumClass);
            case RECORD:
               return plan.codec.decode(buf);
            case PAIR: {
               Object left = decodeWithPlan(buf, plan.key, depth + 1, name, owner);
               Object right = decodeWithPlan(buf, plan.value, depth + 1, name, owner);
               if (plan.pairRawClass == com.mojang.datafixers.util.Pair.class) {
                  return com.mojang.datafixers.util.Pair.of(left, right);
               }

               return Pair.of(left, right);
            }
            case EITHER: {
               boolean isLeft = buf.readBoolean();
               if (isLeft) {
                  Object leftx = decodeWithPlan(buf, plan.key, depth + 1, name, owner);
                  return Either.left(leftx);
               }

               Object right = decodeWithPlan(buf, plan.value, depth + 1, name, owner);
               return Either.right(right);
            }
            case TRIPLE: {
               Object left = decodeWithPlan(buf, plan.key, depth + 1, name, owner);
               Object middle = decodeWithPlan(buf, plan.middle, depth + 1, name, owner);
               Object right = decodeWithPlan(buf, plan.value, depth + 1, name, owner);
               return Triple.of(left, middle, right);
            }
            case OPTIONAL:
               boolean present = buf.readBoolean();
               if (!present) {
                  return Optional.empty();
               }

               Object v = decodeWithPlan(buf, plan.element, depth + 1, name, owner);
               return Optional.of(v);
            case LIST:
               return readListGeneric(buf, plan, depth, name, owner);
            case SET:
               return readSetGeneric(buf, plan, depth, name, owner);
            case MAP:
               return readMapGeneric(buf, plan, depth, name, owner);
            case ENUM_SET:
               return readEnumSetGeneric(buf, plan.enumClass);
            case INT_LIST:
               return buf.readIntIdList();
            default:
               throw new IllegalStateException("Unsupported plan for record " + owner.getName() + "#" + name);
         }
      }
   }

   static void encodeWithPlan(RegistryFriendlyByteBuf buf, ComponentIO.ComponentPlan plan, Object value, int depth, String name, Class<?> owner) {
      if (depth > 64) {
         throw new IllegalStateException("Encoding depth exceeded for record " + owner.getName() + "#" + name);
      }

      if (plan.writeHandle != null) {
         try {
            plan.writeHandle.invoke((RegistryFriendlyByteBuf)buf, (Object)value);
         } catch (Throwable t) {
            throw new IllegalStateException("Failed to encode component for record " + owner.getName() + "#" + name, t);
         }
      } else {
         switch (plan.kind) {
            case INT:
               buf.writeVarInt((Integer)value);
               break;
            case LONG:
               buf.writeVarLong((Long)value);
               break;
            case BOOLEAN:
               buf.writeBoolean((Boolean)value);
               break;
            case FLOAT:
               buf.writeFloat((Float)value);
               break;
            case DOUBLE:
               buf.writeDouble((Double)value);
               break;
            case BYTE:
               buf.writeByte((Byte)value);
               break;
            case SHORT:
               buf.writeShort((Short)value);
               break;
            case STRING:
               buf.writeUtf((String)value);
               break;
            case UUID:
               buf.writeUUID((UUID)value);
               break;
            case BYTE_ARRAY:
               buf.writeByteArray((byte[])value);
               break;
            case INT_ARRAY:
               buf.writeVarIntArray((int[])value);
               break;
            case LONG_ARRAY:
               buf.writeLongArray((long[])value);
               break;
            case DATE:
               buf.writeDate((Date)value);
               break;
            case INSTANT:
               buf.writeInstant((Instant)value);
               break;
            case BITSET:
               buf.writeBitSet((BitSet)value);
               break;
            case PUBLIC_KEY:
               buf.writePublicKey((PublicKey)value);
               break;
            case BLOCK_POS:
               buf.writeBlockPos((BlockPos)value);
               break;
            case CHUNK_POS:
               buf.writeChunkPos((ChunkPos)value);
               break;
            case SECTION_POS:
               buf.writeSectionPos((SectionPos)value);
               break;
            case GLOBAL_POS:
               buf.writeGlobalPos((GlobalPos)value);
               break;
            case VEC3:
               buf.writeVec3((Vec3)value);
               break;
            case VECTOR3F:
               buf.writeVector3f((Vector3f)value);
               break;
            case QUATERNIONF:
               buf.writeQuaternion((Quaternionf)value);
               break;
            case RESOURCE_LOCATION:
               buf.writeResourceLocation((ResourceLocation)value);
               break;
            case BLOCK_HIT_RESULT:
               buf.writeBlockHitResult((BlockHitResult)value);
               break;
            case COMPOUND_TAG:
               buf.writeNbt((CompoundTag)value);
               break;
            case TAG:
               buf.writeNbt((Tag)value);
               break;
            case ENUM:
               buf.writeEnum((Enum)value);
               break;
            case RECORD:
               plan.codec.encode(buf, value);
               break;
            case PAIR:
               Object left;
               Object right;
               if (value instanceof com.mojang.datafixers.util.Pair<?, ?> p) {
                  left = p.getFirst();
                  right = p.getSecond();
               } else {
                  if (!(value instanceof Pair<?, ?> p)) {
                     throw new IllegalStateException("Pair value type not supported: " + value.getClass().getName());
                  }

                  left = p.getLeft();
                  right = p.getRight();
               }

               encodeWithPlan(buf, plan.key, left, depth + 1, name, owner);
               encodeWithPlan(buf, plan.value, right, depth + 1, name, owner);
               break;
            case EITHER:
               Either<?, ?> e = (Either<?, ?>)value;
               if (e.left().isPresent()) {
                  buf.writeBoolean(true);
                  encodeWithPlan(buf, plan.key, e.left().get(), depth + 1, name, owner);
               } else {
                  buf.writeBoolean(false);
                  encodeWithPlan(buf, plan.value, e.right().orElse(null), depth + 1, name, owner);
               }
               break;
            case TRIPLE:
               Triple<?, ?, ?> t = (Triple<?, ?, ?>)value;
               encodeWithPlan(buf, plan.key, t.getLeft(), depth + 1, name, owner);
               encodeWithPlan(buf, plan.middle, t.getMiddle(), depth + 1, name, owner);
               encodeWithPlan(buf, plan.value, t.getRight(), depth + 1, name, owner);
               break;
            case OPTIONAL:
               Optional<?> opt = (Optional<?>)value;
               boolean present = opt != null && opt.isPresent();
               buf.writeBoolean(present);
               if (present) {
                  encodeWithPlan(buf, plan.element, opt.get(), depth + 1, name, owner);
               }
               break;
            case LIST:
            case SET:
               writeCollectionGeneric(buf, (Collection<?>)value, plan.element, depth, name, owner);
               break;
            case MAP:
               writeMapGeneric(buf, (Map<?, ?>)value, plan.key, plan.value, depth, name, owner);
               break;
            case ENUM_SET:
               writeEnumSetGeneric(buf, (EnumSet<?>)value, plan.enumClass);
               break;
            case INT_LIST:
               buf.writeIntIdList((IntList)value);
               break;
            default:
               throw new IllegalStateException("Unsupported plan for record " + owner.getName() + "#" + name);
         }
      }
   }

   static ComponentIO.ComponentPlan planOf(Class<?> rawType, Type genericType) {
      if (rawType.isRecord()) {
         ComponentIO.ComponentPlan p = new ComponentIO.ComponentPlan(ComponentIO.Kind.RECORD);
         p.codec = NetworkSerialization.autoCodec((Class<Object>)rawType);
         return p;
      }

      if (rawType == int.class || rawType == Integer.class) {
         return bindStatic(new ComponentIO.ComponentPlan(ComponentIO.Kind.INT), "readVarIntW", int.class, "writeVarIntW", int.class);
      }

      if (rawType == long.class || rawType == Long.class) {
         return bindStatic(new ComponentIO.ComponentPlan(ComponentIO.Kind.LONG), "readVarLongW", long.class, "writeVarLongW", long.class);
      }

      if (rawType == boolean.class || rawType == Boolean.class) {
         return bindStatic(new ComponentIO.ComponentPlan(ComponentIO.Kind.BOOLEAN), "readBooleanW", boolean.class, "writeBooleanW", boolean.class);
      }

      if (rawType == float.class || rawType == Float.class) {
         return bindStatic(new ComponentIO.ComponentPlan(ComponentIO.Kind.FLOAT), "readFloatW", float.class, "writeFloatW", float.class);
      }

      if (rawType == double.class || rawType == Double.class) {
         return bindStatic(new ComponentIO.ComponentPlan(ComponentIO.Kind.DOUBLE), "readDoubleW", double.class, "writeDoubleW", double.class);
      }

      if (rawType == byte.class || rawType == Byte.class) {
         return bindStatic(new ComponentIO.ComponentPlan(ComponentIO.Kind.BYTE), "readByteW", byte.class, "writeByteW", byte.class);
      }

      if (rawType == short.class || rawType == Short.class) {
         return bindStatic(new ComponentIO.ComponentPlan(ComponentIO.Kind.SHORT), "readShortW", short.class, "writeShortW", short.class);
      }

      if (rawType == String.class) {
         return bindStatic(new ComponentIO.ComponentPlan(ComponentIO.Kind.STRING), "readUtfW", String.class, "writeUtfW", String.class);
      }

      if (rawType == UUID.class) {
         return bindStatic(new ComponentIO.ComponentPlan(ComponentIO.Kind.UUID), "readUUIDW", UUID.class, "writeUUIDW", UUID.class);
      }

      if (rawType == byte[].class) {
         return bindStatic(new ComponentIO.ComponentPlan(ComponentIO.Kind.BYTE_ARRAY), "readByteArrayW", byte[].class, "writeByteArrayW", byte[].class);
      }

      if (rawType == int[].class) {
         return bindStatic(new ComponentIO.ComponentPlan(ComponentIO.Kind.INT_ARRAY), "readVarIntArrayW", int[].class, "writeVarIntArrayW", int[].class);
      }

      if (rawType == long[].class) {
         return bindStatic(new ComponentIO.ComponentPlan(ComponentIO.Kind.LONG_ARRAY), "readLongArrayW", long[].class, "writeLongArrayW", long[].class);
      }

      if (rawType == Date.class) {
         return bindStatic(new ComponentIO.ComponentPlan(ComponentIO.Kind.DATE), "readDateW", Date.class, "writeDateW", Date.class);
      }

      if (rawType == Instant.class) {
         return bindStatic(new ComponentIO.ComponentPlan(ComponentIO.Kind.INSTANT), "readInstantW", Instant.class, "writeInstantW", Instant.class);
      }

      if (rawType == BitSet.class) {
         return bindStatic(new ComponentIO.ComponentPlan(ComponentIO.Kind.BITSET), "readBitSetW", BitSet.class, "writeBitSetW", BitSet.class);
      }

      if (rawType == PublicKey.class) {
         return bindStatic(new ComponentIO.ComponentPlan(ComponentIO.Kind.PUBLIC_KEY), "readPublicKeyW", PublicKey.class, "writePublicKeyW", PublicKey.class);
      }

      if (rawType == IntList.class) {
         return bindStatic(new ComponentIO.ComponentPlan(ComponentIO.Kind.INT_LIST), "readIntIdListW", IntList.class, "writeIntIdListW", IntList.class);
      }

      if (rawType == ResourceKey.class) {
         return bindStatic(
            new ComponentIO.ComponentPlan(ComponentIO.Kind.RESOURCE_KEY), "readRegistryKeyW", ResourceKey.class, "writeResourceKeyW", ResourceKey.class
         );
      }

      if (rawType == BlockPos.class) {
         return bindStatic(new ComponentIO.ComponentPlan(ComponentIO.Kind.BLOCK_POS), "readBlockPosW", BlockPos.class, "writeBlockPosW", BlockPos.class);
      }

      if (rawType == ChunkPos.class) {
         return bindStatic(new ComponentIO.ComponentPlan(ComponentIO.Kind.CHUNK_POS), "readChunkPosW", ChunkPos.class, "writeChunkPosW", ChunkPos.class);
      }

      if (rawType == SectionPos.class) {
         return bindStatic(
            new ComponentIO.ComponentPlan(ComponentIO.Kind.SECTION_POS), "readSectionPosW", SectionPos.class, "writeSectionPosW", SectionPos.class
         );
      }

      if (rawType == GlobalPos.class) {
         return bindStatic(new ComponentIO.ComponentPlan(ComponentIO.Kind.GLOBAL_POS), "readGlobalPosW", GlobalPos.class, "writeGlobalPosW", GlobalPos.class);
      }

      if (rawType == Vec3.class) {
         return bindStatic(new ComponentIO.ComponentPlan(ComponentIO.Kind.VEC3), "readVec3W", Vec3.class, "writeVec3W", Vec3.class);
      }

      if (rawType == Vector3f.class) {
         return bindStatic(new ComponentIO.ComponentPlan(ComponentIO.Kind.VECTOR3F), "readVector3fW", Vector3f.class, "writeVector3fW", Vector3f.class);
      }

      if (rawType == Quaternionf.class) {
         return bindStatic(
            new ComponentIO.ComponentPlan(ComponentIO.Kind.QUATERNIONF), "readQuaternionW", Quaternionf.class, "writeQuaternionW", Quaternionf.class
         );
      }

      if (rawType == ResourceLocation.class) {
         return bindStatic(
            new ComponentIO.ComponentPlan(ComponentIO.Kind.RESOURCE_LOCATION),
            "readResourceLocationW",
            ResourceLocation.class,
            "writeResourceLocationW",
            ResourceLocation.class
         );
      }

      if (rawType == BlockHitResult.class) {
         return bindStatic(
            new ComponentIO.ComponentPlan(ComponentIO.Kind.BLOCK_HIT_RESULT),
            "readBlockHitResultW",
            BlockHitResult.class,
            "writeBlockHitResultW",
            BlockHitResult.class
         );
      }

      if (rawType == CompoundTag.class) {
         return bindStatic(
            new ComponentIO.ComponentPlan(ComponentIO.Kind.COMPOUND_TAG), "readCompoundTagW", CompoundTag.class, "writeCompoundTagW", CompoundTag.class
         );
      }

      if (rawType == Tag.class) {
         return bindStatic(new ComponentIO.ComponentPlan(ComponentIO.Kind.TAG), "readTagW", Tag.class, "writeTagW", Tag.class);
      }

      if (rawType.isEnum()) {
         ComponentIO.ComponentPlan p = new ComponentIO.ComponentPlan(ComponentIO.Kind.ENUM);
         p.enumClass = (Class<? extends Enum>)rawType;

         try {
            Lookup lookup = MethodHandles.lookup();
            MethodHandle r = lookup.findStatic(ComponentIO.class, "readEnumW", MethodType.methodType(Enum.class, RegistryFriendlyByteBuf.class, Class.class));
            p.readHandle = MethodHandles.insertArguments(r, 1, p.enumClass);
            p.writeHandle = lookup.findStatic(ComponentIO.class, "writeEnumW", MethodType.methodType(void.class, RegistryFriendlyByteBuf.class, Enum.class));
            return p;
         } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new IllegalStateException(e);
         }
      } else {
         if (rawType == IntSet.class) {
            ComponentIO.ComponentPlan p = new ComponentIO.ComponentPlan(ComponentIO.Kind.SET);
            p.rawType = IntSet.class;
            p.element = planOf(Integer.class, Integer.class);
            return p;
         }

         if (rawType == LongSet.class) {
            ComponentIO.ComponentPlan p = new ComponentIO.ComponentPlan(ComponentIO.Kind.SET);
            p.rawType = LongSet.class;
            p.element = planOf(Long.class, Long.class);
            return p;
         }

         if (rawType == Int2IntMap.class) {
            ComponentIO.ComponentPlan p = new ComponentIO.ComponentPlan(ComponentIO.Kind.MAP);
            p.rawType = Int2IntMap.class;
            p.key = planOf(Integer.class, Integer.class);
            p.value = planOf(Integer.class, Integer.class);
            return p;
         }

         if (rawType == Object2IntMap.class) {
            return planMap(rawType, genericType, String.class, Integer.class);
         }

         if (rawType == Object2LongMap.class) {
            return planMap(rawType, genericType, String.class, Long.class);
         }

         if (rawType == Object2ObjectMap.class) {
            return planMap(rawType, genericType, String.class, String.class);
         }

         if (rawType == Int2ObjectMap.class) {
            return planMap(rawType, genericType, Integer.class, String.class);
         }

         if (rawType == Long2ObjectMap.class) {
            return planMap(rawType, genericType, Long.class, String.class);
         }

         if (genericType instanceof ParameterizedType pt) {
            Type raw = pt.getRawType();
            if (raw == Optional.class) {
               Type arg = pt.getActualTypeArguments()[0];
               Class<?> argRaw = erasureOf(arg);
               ComponentIO.ComponentPlan p = new ComponentIO.ComponentPlan(ComponentIO.Kind.OPTIONAL);
               p.element = planOf(argRaw, arg);
               return p;
            }

            if (raw instanceof Class<?> rawClass) {
               if (rawClass == com.mojang.datafixers.util.Pair.class || rawClass == Pair.class) {
                  Type[] args = pt.getActualTypeArguments();
                  Type leftType = args[0];
                  Type rightType = args[1];
                  ComponentIO.ComponentPlan p = new ComponentIO.ComponentPlan(ComponentIO.Kind.PAIR);
                  p.key = planOf(erasureOf(leftType), leftType);
                  p.value = planOf(erasureOf(rightType), rightType);
                  p.pairRawClass = rawClass;
                  return p;
               }

               if (rawClass == Either.class) {
                  Type[] args = pt.getActualTypeArguments();
                  Type leftType = args[0];
                  Type rightType = args[1];
                  ComponentIO.ComponentPlan p = new ComponentIO.ComponentPlan(ComponentIO.Kind.EITHER);
                  p.key = planOf(erasureOf(leftType), leftType);
                  p.value = planOf(erasureOf(rightType), rightType);
                  return p;
               }

               if (rawClass == Triple.class) {
                  Type[] args = pt.getActualTypeArguments();
                  Type leftType = args[0];
                  Type midType = args[1];
                  Type rightType = args[2];
                  ComponentIO.ComponentPlan p = new ComponentIO.ComponentPlan(ComponentIO.Kind.TRIPLE);
                  p.key = planOf(erasureOf(leftType), leftType);
                  p.middle = planOf(erasureOf(midType), midType);
                  p.value = planOf(erasureOf(rightType), rightType);
                  return p;
               }

               if (List.class.isAssignableFrom(rawClass)) {
                  Type elemType = pt.getActualTypeArguments()[0];
                  Class<?> elemRaw = erasureOf(elemType);
                  ComponentIO.ComponentPlan p = new ComponentIO.ComponentPlan(ComponentIO.Kind.LIST);
                  p.rawType = rawClass;
                  if (rawClass == ObjectList.class) {
                     p.collectionFactory = ObjectArrayList::new;
                  } else {
                     p.collectionFactory = ArrayList::new;
                  }

                  p.element = planOf(elemRaw, elemType);
                  return p;
               }

               if (rawClass == EnumSet.class) {
                  Type arg = pt.getActualTypeArguments()[0];
                  Class<?> argRaw = erasureOf(arg);
                  if (!Enum.class.isAssignableFrom(argRaw)) {
                     throw new IllegalStateException("EnumSet element type must be an enum");
                  }

                  ComponentIO.ComponentPlan p = new ComponentIO.ComponentPlan(ComponentIO.Kind.ENUM_SET);
                  p.enumClass = (Class<? extends Enum>)argRaw;
                  return p;
               }

               if (Set.class.isAssignableFrom(rawClass)) {
                  Type elemType = pt.getActualTypeArguments()[0];
                  Class<?> elemRaw = erasureOf(elemType);
                  ComponentIO.ComponentPlan p = new ComponentIO.ComponentPlan(ComponentIO.Kind.SET);
                  p.rawType = rawClass;
                  if (rawClass == ObjectSet.class) {
                     p.collectionFactory = ObjectOpenHashSet::new;
                  } else if (rawClass == IntSet.class) {
                     p.collectionFactory = IntOpenHashSet::new;
                  } else if (rawClass == LongSet.class) {
                     p.collectionFactory = LongOpenHashSet::new;
                  } else {
                     p.collectionFactory = LinkedHashSet::new;
                  }

                  p.element = planOf(elemRaw, elemType);
                  return p;
               }

               if (Map.class.isAssignableFrom(rawClass)) {
                  Type[] args = pt.getActualTypeArguments();
                  if (args.length == 2) {
                     Type keyType = args[0];
                     Type valueType = args[1];
                     Class<?> keyRaw = erasureOf(keyType);
                     Class<?> valueRaw = erasureOf(valueType);
                     ComponentIO.ComponentPlan p = new ComponentIO.ComponentPlan(ComponentIO.Kind.MAP);
                     p.rawType = rawClass;
                     p.key = planOf(keyRaw, keyType);
                     p.value = planOf(valueRaw, valueType);
                     return p;
                  }
               }
            }
         }

         throw new IllegalStateException("Unsupported type " + rawType.getName());
      }
   }

   private static ComponentIO.ComponentPlan planMap(Class<?> rawType, Type genericType, Class<?> defaultKey, Class<?> defaultValue) {
      ComponentIO.ComponentPlan p = new ComponentIO.ComponentPlan(ComponentIO.Kind.MAP);
      p.rawType = rawType;
      Type[] typeArgs = genericType instanceof ParameterizedType pt ? pt.getActualTypeArguments() : new Type[0];
      if (rawType == Int2ObjectMap.class || rawType == Int2IntMap.class) {
         p.key = planOf(Integer.class, Integer.class);
      } else if (rawType == Long2ObjectMap.class) {
         p.key = planOf(Long.class, Long.class);
      } else {
         Type keyType = typeArgs.length >= 1 ? typeArgs[0] : defaultKey;
         p.key = planOf(erasureOf(keyType), keyType);
      }

      if (rawType == Object2IntMap.class) {
         p.value = planOf(Integer.class, Integer.class);
      } else if (rawType == Object2LongMap.class) {
         p.value = planOf(Long.class, Long.class);
      } else {
         int valIdx = rawType == Object2ObjectMap.class ? 1 : 0;
         Type valType = typeArgs.length > valIdx ? typeArgs[valIdx] : defaultValue;
         p.value = planOf(erasureOf(valType), valType);
      }

      return p;
   }

   static Class<?> erasureOf(Type type) {
      if (type instanceof Class<?> c) {
         return c;
      } else if (type instanceof ParameterizedType pt && pt.getRawType() instanceof Class<?> c) {
         return c;
      } else {
         throw new IllegalArgumentException("Unsupported type: " + type);
      }
   }

   static <E extends Enum<E>> EnumSet<E> readEnumSetGeneric(RegistryFriendlyByteBuf buf, Class<? extends Enum> enumClassRaw) {
      Class<E> ec = (Class<E>)enumClassRaw;
      return buf.readEnumSet(ec);
   }

   static <E extends Enum<E>> void writeEnumSetGeneric(RegistryFriendlyByteBuf buf, EnumSet<?> setRaw, Class<? extends Enum> enumClassRaw) {
      EnumSet<E> set = (EnumSet<E>)setRaw;
      Class<E> ec = (Class<E>)enumClassRaw;
      buf.writeEnumSet(set, ec);
   }

   static <T> List<T> readListGeneric(RegistryFriendlyByteBuf buf, ComponentIO.ComponentPlan containerPlan, int depth, String name, Class<?> owner) {
      List<T> base = buf.readList(b -> decodeWithPlan((RegistryFriendlyByteBuf)b, containerPlan.element, depth + 1, name, owner));
      return (List<T>)convertListToRawType(base, containerPlan.rawType);
   }

   static <T> Set<T> readSetGeneric(RegistryFriendlyByteBuf buf, ComponentIO.ComponentPlan containerPlan, int depth, String name, Class<?> owner) {
      Set<T> base = (Set<T>)buf.readCollection(
         LinkedHashSet::new, b -> decodeWithPlan((RegistryFriendlyByteBuf)b, containerPlan.element, depth + 1, name, owner)
      );
      return (Set<T>)convertSetToRawType(base, containerPlan.rawType);
   }

   static <K, V> Map<K, V> readMapGeneric(RegistryFriendlyByteBuf buf, ComponentIO.ComponentPlan containerPlan, int depth, String name, Class<?> owner) {
      Map<K, V> base = buf.readMap(
         b -> decodeWithPlan((RegistryFriendlyByteBuf)b, containerPlan.key, depth + 1, name, owner),
         b -> decodeWithPlan((RegistryFriendlyByteBuf)b, containerPlan.value, depth + 1, name, owner)
      );
      return (Map<K, V>)convertMapToRawType(base, containerPlan.rawType);
   }

   static void writeCollectionGeneric(
      RegistryFriendlyByteBuf buf, Collection<?> collection, ComponentIO.ComponentPlan elemPlan, int depth, String name, Class<?> owner
   ) {
      buf.writeCollection(collection, (b, e) -> encodeWithPlan((RegistryFriendlyByteBuf)b, elemPlan, e, depth + 1, name, owner));
   }

   static void writeMapGeneric(
      RegistryFriendlyByteBuf buf,
      Map<?, ?> map,
      ComponentIO.ComponentPlan keyPlan,
      ComponentIO.ComponentPlan valuePlan,
      int depth,
      String name,
      Class<?> owner
   ) {
      buf.writeMap(
         map,
         (b, k) -> encodeWithPlan((RegistryFriendlyByteBuf)b, keyPlan, k, depth + 1, name, owner),
         (b, v) -> encodeWithPlan((RegistryFriendlyByteBuf)b, valuePlan, v, depth + 1, name, owner)
      );
   }

   static Map<?, ?> convertMapToRawType(Map<?, ?> base, Class<?> rawType) {
      if (rawType == null) {
         return base;
      }

      if (rawType == Object2IntMap.class) {
         Object2IntMap<Object> m = new Object2IntOpenHashMap();

         for (Entry<?, ?> e : base.entrySet()) {
            m.put(e.getKey(), ((Number)e.getValue()).intValue());
         }

         return m;
      } else if (rawType == Object2LongMap.class) {
         Object2LongMap<Object> m = new Object2LongOpenHashMap();

         for (Entry<?, ?> e : base.entrySet()) {
            m.put(e.getKey(), ((Number)e.getValue()).longValue());
         }

         return m;
      } else {
         if (rawType == Object2ObjectMap.class) {
            Object2ObjectMap<Object, Object> m = new Object2ObjectOpenHashMap();
            m.putAll(base);
            return m;
         }

         if (rawType == Int2ObjectMap.class) {
            Int2ObjectMap<Object> m = new Int2ObjectOpenHashMap();

            for (Entry<?, ?> e : base.entrySet()) {
               m.put(((Number)e.getKey()).intValue(), e.getValue());
            }

            return m;
         } else if (rawType == Int2IntMap.class) {
            Int2IntMap m = new Int2IntOpenHashMap();

            for (Entry<?, ?> e : base.entrySet()) {
               m.put(((Number)e.getKey()).intValue(), ((Number)e.getValue()).intValue());
            }

            return m;
         } else {
            if (rawType != Long2ObjectMap.class) {
               return base;
            }

            Long2ObjectMap<Object> m = new Long2ObjectOpenHashMap();

            for (Entry<?, ?> e : base.entrySet()) {
               m.put(((Number)e.getKey()).longValue(), e.getValue());
            }

            return m;
         }
      }
   }

   static List<?> convertListToRawType(List<?> base, Class<?> rawType) {
      if (rawType == null) {
         return base;
      } else {
         return (List<?>)(rawType == ObjectList.class ? new ObjectArrayList(base) : base);
      }
   }

   static Set<?> convertSetToRawType(Set<?> base, Class<?> rawType) {
      if (rawType == null) {
         return base;
      }

      if (rawType == ObjectSet.class) {
         return new ObjectOpenHashSet(base);
      }

      if (rawType == IntSet.class) {
         IntOpenHashSet set = new IntOpenHashSet();

         for (Object e : base) {
            set.add(((Number)e).intValue());
         }

         return set;
      } else {
         if (rawType != LongSet.class) {
            return base;
         }

         LongOpenHashSet set = new LongOpenHashSet();

         for (Object e : base) {
            set.add(((Number)e).longValue());
         }

         return set;
      }
   }

   static PublicKey readPublicKeyW(RegistryFriendlyByteBuf buf) {
      return buf.readPublicKey();
   }

   static void writePublicKeyW(RegistryFriendlyByteBuf buf, PublicKey v) {
      buf.writePublicKey(v);
   }

   private static ComponentIO.ComponentPlan bindStatic(ComponentIO.ComponentPlan p, String readName, Class<?> readType, String writeName, Class<?> writeArgType) {
      try {
         Lookup lookup = MethodHandles.lookup();
         p.readHandle = lookup.findStatic(ComponentIO.class, readName, MethodType.methodType(readType, RegistryFriendlyByteBuf.class));
         p.writeHandle = lookup.findStatic(ComponentIO.class, writeName, MethodType.methodType(void.class, RegistryFriendlyByteBuf.class, writeArgType));
         return p;
      } catch (NoSuchMethodException | IllegalAccessException e) {
         throw new IllegalStateException(e);
      }
   }

   static int readVarIntW(RegistryFriendlyByteBuf buf) {
      return buf.readVarInt();
   }

   static void writeVarIntW(RegistryFriendlyByteBuf buf, int v) {
      buf.writeVarInt(v);
   }

   static long readVarLongW(RegistryFriendlyByteBuf buf) {
      return buf.readVarLong();
   }

   static void writeVarLongW(RegistryFriendlyByteBuf buf, long v) {
      buf.writeVarLong(v);
   }

   static boolean readBooleanW(RegistryFriendlyByteBuf buf) {
      return buf.readBoolean();
   }

   static void writeBooleanW(RegistryFriendlyByteBuf buf, boolean v) {
      buf.writeBoolean(v);
   }

   static float readFloatW(RegistryFriendlyByteBuf buf) {
      return buf.readFloat();
   }

   static void writeFloatW(RegistryFriendlyByteBuf buf, float v) {
      buf.writeFloat(v);
   }

   static double readDoubleW(RegistryFriendlyByteBuf buf) {
      return buf.readDouble();
   }

   static void writeDoubleW(RegistryFriendlyByteBuf buf, double v) {
      buf.writeDouble(v);
   }

   static byte readByteW(RegistryFriendlyByteBuf buf) {
      return buf.readByte();
   }

   static void writeByteW(RegistryFriendlyByteBuf buf, byte v) {
      buf.writeByte(v);
   }

   static short readShortW(RegistryFriendlyByteBuf buf) {
      return buf.readShort();
   }

   static void writeShortW(RegistryFriendlyByteBuf buf, short v) {
      buf.writeShort(v);
   }

   static String readUtfW(RegistryFriendlyByteBuf buf) {
      return buf.readUtf();
   }

   static void writeUtfW(RegistryFriendlyByteBuf buf, String v) {
      buf.writeUtf(v);
   }

   static UUID readUUIDW(RegistryFriendlyByteBuf buf) {
      return buf.readUUID();
   }

   static void writeUUIDW(RegistryFriendlyByteBuf buf, UUID v) {
      buf.writeUUID(v);
   }

   static byte[] readByteArrayW(RegistryFriendlyByteBuf buf) {
      return buf.readByteArray();
   }

   static void writeByteArrayW(RegistryFriendlyByteBuf buf, byte[] v) {
      buf.writeByteArray(v);
   }

   static int[] readVarIntArrayW(RegistryFriendlyByteBuf buf) {
      return buf.readVarIntArray();
   }

   static void writeVarIntArrayW(RegistryFriendlyByteBuf buf, int[] v) {
      buf.writeVarIntArray(v);
   }

   static long[] readLongArrayW(RegistryFriendlyByteBuf buf) {
      return buf.readLongArray();
   }

   static void writeLongArrayW(RegistryFriendlyByteBuf buf, long[] v) {
      buf.writeLongArray(v);
   }

   static Date readDateW(RegistryFriendlyByteBuf buf) {
      return buf.readDate();
   }

   static void writeDateW(RegistryFriendlyByteBuf buf, Date v) {
      buf.writeDate(v);
   }

   static Instant readInstantW(RegistryFriendlyByteBuf buf) {
      return buf.readInstant();
   }

   static void writeInstantW(RegistryFriendlyByteBuf buf, Instant v) {
      buf.writeInstant(v);
   }

   static BitSet readBitSetW(RegistryFriendlyByteBuf buf) {
      return buf.readBitSet();
   }

   static void writeBitSetW(RegistryFriendlyByteBuf buf, BitSet v) {
      buf.writeBitSet(v);
   }

   static BlockPos readBlockPosW(RegistryFriendlyByteBuf buf) {
      return buf.readBlockPos();
   }

   static void writeBlockPosW(RegistryFriendlyByteBuf buf, BlockPos v) {
      buf.writeBlockPos(v);
   }

   static ChunkPos readChunkPosW(RegistryFriendlyByteBuf buf) {
      return buf.readChunkPos();
   }

   static void writeChunkPosW(RegistryFriendlyByteBuf buf, ChunkPos v) {
      buf.writeChunkPos(v);
   }

   static SectionPos readSectionPosW(RegistryFriendlyByteBuf buf) {
      return buf.readSectionPos();
   }

   static void writeSectionPosW(RegistryFriendlyByteBuf buf, SectionPos v) {
      buf.writeSectionPos(v);
   }

   static GlobalPos readGlobalPosW(RegistryFriendlyByteBuf buf) {
      return buf.readGlobalPos();
   }

   static void writeGlobalPosW(RegistryFriendlyByteBuf buf, GlobalPos v) {
      buf.writeGlobalPos(v);
   }

   static Vec3 readVec3W(RegistryFriendlyByteBuf buf) {
      return buf.readVec3();
   }

   static void writeVec3W(RegistryFriendlyByteBuf buf, Vec3 v) {
      buf.writeVec3(v);
   }

   static Vector3f readVector3fW(RegistryFriendlyByteBuf buf) {
      return buf.readVector3f();
   }

   static void writeVector3fW(RegistryFriendlyByteBuf buf, Vector3f v) {
      buf.writeVector3f(v);
   }

   static Quaternionf readQuaternionW(RegistryFriendlyByteBuf buf) {
      return buf.readQuaternion();
   }

   static void writeQuaternionW(RegistryFriendlyByteBuf buf, Quaternionf v) {
      buf.writeQuaternion(v);
   }

   static ResourceLocation readResourceLocationW(RegistryFriendlyByteBuf buf) {
      return buf.readResourceLocation();
   }

   static void writeResourceLocationW(RegistryFriendlyByteBuf buf, ResourceLocation v) {
      buf.writeResourceLocation(v);
   }

   static BlockHitResult readBlockHitResultW(RegistryFriendlyByteBuf buf) {
      return buf.readBlockHitResult();
   }

   static void writeBlockHitResultW(RegistryFriendlyByteBuf buf, BlockHitResult v) {
      buf.writeBlockHitResult(v);
   }

   static CompoundTag readCompoundTagW(RegistryFriendlyByteBuf buf) {
      return buf.readNbt();
   }

   static void writeCompoundTagW(RegistryFriendlyByteBuf buf, CompoundTag v) {
      buf.writeNbt(v);
   }

   static Tag readTagW(RegistryFriendlyByteBuf buf) {
      return buf.readNbt(NbtAccounter.unlimitedHeap());
   }

   static void writeTagW(RegistryFriendlyByteBuf buf, Tag v) {
      buf.writeNbt(v);
   }

   static Enum<?> readEnumW(RegistryFriendlyByteBuf buf, Class<? extends Enum<?>> enumClass) {
      return buf.readEnum(enumClass);
   }

   static void writeEnumW(RegistryFriendlyByteBuf buf, Enum<?> v) {
      buf.writeEnum(v);
   }

   static IntList readIntIdListW(RegistryFriendlyByteBuf buf) {
      return buf.readIntIdList();
   }

   static void writeIntIdListW(RegistryFriendlyByteBuf buf, IntList v) {
      buf.writeIntIdList(v);
   }

   static <T> ResourceKey<T> readResourceKeyW(RegistryFriendlyByteBuf buf, ResourceKey<? extends Registry<T>> resourceKeyClass) {
      return buf.readResourceKey(resourceKeyClass);
   }

   static void writeResourceKeyW(RegistryFriendlyByteBuf buf, ResourceKey<?> v) {
      buf.writeResourceKey(v);
   }

   static <T> ResourceKey<? extends Registry<T>> readRegistryKeyW(RegistryFriendlyByteBuf buf) {
      return buf.readRegistryKey();
   }

   static final class ComponentPlan {
      final ComponentIO.Kind kind;
      ComponentIO.ComponentPlan element;
      ComponentIO.ComponentPlan key;
      ComponentIO.ComponentPlan value;
      ComponentIO.ComponentPlan middle;
      Class<? extends Enum> enumClass;
      MethodHandle readHandle;
      MethodHandle writeHandle;
      StreamCodec<RegistryFriendlyByteBuf, Object> codec;
      Class<?> pairRawClass;
      Class<?> rawType;
      Supplier<Collection<?>> collectionFactory;

      ComponentPlan(ComponentIO.Kind k) {
         this.kind = k;
      }
   }

   enum Kind {
      INT,
      LONG,
      BOOLEAN,
      FLOAT,
      DOUBLE,
      BYTE,
      SHORT,
      STRING,
      UUID,
      BYTE_ARRAY,
      INT_ARRAY,
      LONG_ARRAY,
      DATE,
      INSTANT,
      BITSET,
      PUBLIC_KEY,
      BLOCK_POS,
      CHUNK_POS,
      SECTION_POS,
      GLOBAL_POS,
      VEC3,
      VECTOR3F,
      QUATERNIONF,
      RESOURCE_LOCATION,
      BLOCK_HIT_RESULT,
      COMPOUND_TAG,
      TAG,
      ENUM,
      RECORD,
      PAIR,
      EITHER,
      TRIPLE,
      OPTIONAL,
      LIST,
      SET,
      MAP,
      ENUM_SET,
      INT_LIST,
      RESOURCE_KEY;
   }
}
