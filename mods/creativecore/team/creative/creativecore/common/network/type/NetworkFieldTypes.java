package team.creative.creativecore.common.network.type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.BiPredicate;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.BundlePacket;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;
import org.joml.Vector3f;
import team.creative.creativecore.CreativeCore;
import team.creative.creativecore.common.network.BundlePacketWrapper;
import team.creative.creativecore.common.network.CreativeNetworkUtils;
import team.creative.creativecore.common.util.filter.BiFilter;
import team.creative.creativecore.common.util.filter.Filter;
import team.creative.creativecore.common.util.math.matrix.IntMatrix3;
import team.creative.creativecore.common.util.math.matrix.IntMatrix3c;
import team.creative.creativecore.common.util.math.vec.Vec1d;
import team.creative.creativecore.common.util.math.vec.Vec1f;
import team.creative.creativecore.common.util.math.vec.Vec2d;
import team.creative.creativecore.common.util.math.vec.Vec2f;
import team.creative.creativecore.common.util.math.vec.Vec3d;
import team.creative.creativecore.common.util.math.vec.Vec3f;
import team.creative.creativecore.common.util.registry.exception.RegistryException;
import team.creative.creativecore.common.util.type.Bunch;
import team.creative.creativecore.common.util.type.itr.IterableIterator;
import team.creative.creativecore.common.util.type.itr.SingleIterator;

public class NetworkFieldTypes {
   private static final Gson GSON = new Gson();
   private static final List<NetworkFieldTypeSpecial> SPECIAL_PARSERS = new ArrayList<>();
   private static final HashMap<Class, NetworkFieldType> PARSERS = new HashMap<>();

   public static <T> void register(NetworkFieldType<T> parser, Class<T> classType) {
      PARSERS.put(classType, parser);
   }

   public static <T> StreamCodec<RegistryFriendlyByteBuf, T> registerAndCodec(NetworkFieldTypeClass<T> parser, Class<T> classType) {
      register(parser, classType);
      return StreamCodec.of((x, y) -> parser.writeContent((T)y, x), parser::readContent);
   }

   public static <T> void register(NetworkFieldType<T> parser, Class<? extends T>... classType) {
      for (Class<? extends T> clazz : classType) {
         PARSERS.put(clazz, parser);
      }
   }

   public static <T> void register(NetworkFieldTypeSpecial parser) {
      SPECIAL_PARSERS.add(parser);
   }

   public static NetworkFieldType get(Field field) {
      return get(field.getType(), field.getGenericType());
   }

   public static <T> NetworkFieldType<T> get(Class<T> classType) {
      try {
         NetworkFieldType parser = PARSERS.get(classType);
         if (parser != null) {
            return parser;
         }
      } catch (Exception e1) {
         CreativeCore.LOGGER.error(e1);
      }

      throw new RuntimeException("No field type found for " + classType.getSimpleName());
   }

   public static NetworkFieldType get(Class classType, Type genericType) {
      try {
         NetworkFieldType parser = PARSERS.get(classType);
         if (parser != null) {
            return parser;
         }

         for (int i = 0; i < SPECIAL_PARSERS.size(); i++) {
            if (SPECIAL_PARSERS.get(i).predicate.test(classType, genericType)) {
               return SPECIAL_PARSERS.get(i);
            }
         }
      } catch (Exception e1) {
         CreativeCore.LOGGER.error(e1);
      }

      throw new RuntimeException("No field type found for " + classType.getSimpleName());
   }

   public static <T> void write(Class<T> clazz, T object, RegistryFriendlyByteBuf buffer, PacketFlow flow) {
      get(clazz).write(object, clazz, null, buffer, flow);
   }

   public static <T> void writeMany(Class<T> clazz, Bunch<T> bunch, RegistryFriendlyByteBuf buffer, PacketFlow flow) {
      buffer.writeInt(bunch.size());
      NetworkFieldType<T> type = get(clazz);

      for (T t : bunch) {
         type.write(t, clazz, null, buffer, flow);
      }
   }

   public static <T> void writeMany(Class<T> clazz, Collection<T> collection, RegistryFriendlyByteBuf buffer, PacketFlow flow) {
      buffer.writeInt(collection.size());
      NetworkFieldType<T> type = get(clazz);

      for (T t : collection) {
         type.write(t, clazz, null, buffer, flow);
      }
   }

   public static <T> void writeMany(Class<T> clazz, T[] collection, RegistryFriendlyByteBuf buffer, PacketFlow flow) {
      buffer.writeInt(collection.length);
      NetworkFieldType<T> type = get(clazz);

      for (T t : collection) {
         type.write(t, clazz, null, buffer, flow);
      }
   }

   public static <T> T read(Class<T> clazz, RegistryFriendlyByteBuf buffer, PacketFlow flow) {
      return get(clazz).read(clazz, null, buffer, flow);
   }

   public static <T> Iterable<T> readMany(final Class<T> clazz, final RegistryFriendlyByteBuf buffer, final PacketFlow flow) {
      final int length = buffer.readInt();
      final NetworkFieldType<T> type = get(clazz);
      return new IterableIterator<T>() {
         int index = 0;

         @Override
         public boolean hasNext() {
            return this.index < length;
         }

         @Override
         public T next() {
            this.index++;
            return type.read(clazz, null, buffer, flow);
         }
      };
   }

   public static void writeIntArray(int[] array, RegistryFriendlyByteBuf buffer) {
      buffer.writeInt(array.length);

      for (int i = 0; i < array.length; i++) {
         buffer.writeInt(array[i]);
      }
   }

   public static int[] readIntArray(RegistryFriendlyByteBuf buffer) {
      int[] array = new int[buffer.readInt()];

      for (int i = 0; i < array.length; i++) {
         array[i] = buffer.readInt();
      }

      return array;
   }

   static {
      register(new NetworkFieldTypeClass<Boolean>() {
         protected void writeContent(Boolean content, RegistryFriendlyByteBuf buffer) {
            buffer.writeBoolean(content);
         }

         protected Boolean readContent(RegistryFriendlyByteBuf buffer) {
            return buffer.readBoolean();
         }
      }, boolean.class, Boolean.class);
      register(new NetworkFieldTypeClass<Byte>() {
         protected void writeContent(Byte content, RegistryFriendlyByteBuf buffer) {
            buffer.writeByte(content);
         }

         protected Byte readContent(RegistryFriendlyByteBuf buffer) {
            return buffer.readByte();
         }
      }, byte.class, Byte.class);
      register(new NetworkFieldTypeClass<Short>() {
         protected void writeContent(Short content, RegistryFriendlyByteBuf buffer) {
            buffer.writeShort(content);
         }

         protected Short readContent(RegistryFriendlyByteBuf buffer) {
            return buffer.readShort();
         }
      }, short.class, Short.class);
      register(new NetworkFieldTypeClass<Integer>() {
         protected void writeContent(Integer content, RegistryFriendlyByteBuf buffer) {
            buffer.writeInt(content);
         }

         protected Integer readContent(RegistryFriendlyByteBuf buffer) {
            return buffer.readInt();
         }
      }, int.class, Integer.class);
      register(new NetworkFieldTypeClass<Long>() {
         protected void writeContent(Long content, RegistryFriendlyByteBuf buffer) {
            buffer.writeLong(content);
         }

         protected Long readContent(RegistryFriendlyByteBuf buffer) {
            return buffer.readLong();
         }
      }, long.class, Long.class);
      register(new NetworkFieldTypeClass<Float>() {
         protected void writeContent(Float content, RegistryFriendlyByteBuf buffer) {
            buffer.writeFloat(content);
         }

         protected Float readContent(RegistryFriendlyByteBuf buffer) {
            return buffer.readFloat();
         }
      }, float.class, Float.class);
      register(new NetworkFieldTypeClass<Double>() {
         protected void writeContent(Double content, RegistryFriendlyByteBuf buffer) {
            buffer.writeDouble(content);
         }

         protected Double readContent(RegistryFriendlyByteBuf buffer) {
            return buffer.readDouble();
         }
      }, double.class, Double.class);
      register(new NetworkFieldTypeClass<BlockPos>() {
         protected void writeContent(BlockPos content, RegistryFriendlyByteBuf buffer) {
            buffer.writeBlockPos(content);
         }

         protected BlockPos readContent(RegistryFriendlyByteBuf buffer) {
            return buffer.readBlockPos();
         }
      }, BlockPos.class);
      register(new NetworkFieldTypeClass<String>() {
         protected void writeContent(String content, RegistryFriendlyByteBuf buffer) {
            buffer.writeUtf(content);
         }

         protected String readContent(RegistryFriendlyByteBuf buffer) {
            return buffer.readUtf(32767);
         }
      }, String.class);
      register(new NetworkFieldTypeClass<Component>() {
         protected void writeContent(Component content, RegistryFriendlyByteBuf buffer) {
            FriendlyByteBuf.writeNullable(buffer, content, ComponentSerialization.STREAM_CODEC);
         }

         protected Component readContent(RegistryFriendlyByteBuf buffer) {
            return (Component)FriendlyByteBuf.readNullable(buffer, ComponentSerialization.STREAM_CODEC);
         }
      }, Component.class);
      register(new NetworkFieldTypeClass<CompoundTag>() {
         protected void writeContent(CompoundTag content, RegistryFriendlyByteBuf buffer) {
            buffer.writeNbt(content);
         }

         protected CompoundTag readContent(RegistryFriendlyByteBuf buffer) {
            return buffer.readNbt();
         }
      }, CompoundTag.class);
      register(new NetworkFieldTypeClass<ItemStack>() {
         protected void writeContent(ItemStack content, RegistryFriendlyByteBuf buffer) {
            FriendlyByteBuf.writeNullable(buffer, content, ItemStack.OPTIONAL_STREAM_CODEC);
         }

         protected ItemStack readContent(RegistryFriendlyByteBuf buffer) {
            return (ItemStack)FriendlyByteBuf.readNullable(buffer, ItemStack.OPTIONAL_STREAM_CODEC);
         }
      }, ItemStack.class);
      register(new NetworkFieldTypeClass<ResourceLocation>() {
         protected void writeContent(ResourceLocation content, RegistryFriendlyByteBuf buffer) {
            buffer.writeResourceLocation(content);
         }

         protected ResourceLocation readContent(RegistryFriendlyByteBuf buffer) {
            return buffer.readResourceLocation();
         }
      }, ResourceLocation.class);
      register(new NetworkFieldTypeClass<BlockState>() {
         protected void writeContent(BlockState content, RegistryFriendlyByteBuf buffer) {
            buffer.writeInt(Block.getId(content));
         }

         protected BlockState readContent(RegistryFriendlyByteBuf buffer) {
            return Block.stateById(buffer.readInt());
         }
      }, BlockState.class);
      register(new NetworkFieldTypeClass<Block>() {
         protected void writeContent(Block content, RegistryFriendlyByteBuf buffer) {
            buffer.writeResourceLocation(BuiltInRegistries.BLOCK.getKey(content));
         }

         protected Block readContent(RegistryFriendlyByteBuf buffer) {
            return (Block)BuiltInRegistries.BLOCK.get(buffer.readResourceLocation());
         }
      }, Block.class);
      register(new NetworkFieldTypeClass<Item>() {
         protected void writeContent(Item content, RegistryFriendlyByteBuf buffer) {
            buffer.writeResourceLocation(BuiltInRegistries.ITEM.getKey(content));
         }

         protected Item readContent(RegistryFriendlyByteBuf buffer) {
            return (Item)BuiltInRegistries.ITEM.get(buffer.readResourceLocation());
         }
      }, Item.class);
      register(new NetworkFieldTypeClass<Vector3d>() {
         protected void writeContent(Vector3d content, RegistryFriendlyByteBuf buffer) {
            buffer.writeDouble(content.x);
            buffer.writeDouble(content.y);
            buffer.writeDouble(content.z);
         }

         protected Vector3d readContent(RegistryFriendlyByteBuf buffer) {
            return new Vector3d(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
         }
      }, Vector3d.class);
      register(new NetworkFieldTypeClass<Vec3>() {
         protected void writeContent(Vec3 content, RegistryFriendlyByteBuf buffer) {
            buffer.writeDouble(content.x);
            buffer.writeDouble(content.y);
            buffer.writeDouble(content.z);
         }

         protected Vec3 readContent(RegistryFriendlyByteBuf buffer) {
            return new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
         }
      }, Vec3.class);
      register(new NetworkFieldTypeClass<Vec1d>() {
         protected void writeContent(Vec1d content, RegistryFriendlyByteBuf buffer) {
            buffer.writeDouble(content.x);
         }

         protected Vec1d readContent(RegistryFriendlyByteBuf buffer) {
            return new Vec1d(buffer.readDouble());
         }
      }, Vec1d.class);
      register(new NetworkFieldTypeClass<Vec1f>() {
         protected void writeContent(Vec1f content, RegistryFriendlyByteBuf buffer) {
            buffer.writeFloat(content.x);
         }

         protected Vec1f readContent(RegistryFriendlyByteBuf buffer) {
            return new Vec1f(buffer.readFloat());
         }
      }, Vec1f.class);
      register(new NetworkFieldTypeClass<Vec2d>() {
         protected void writeContent(Vec2d content, RegistryFriendlyByteBuf buffer) {
            buffer.writeDouble(content.x);
            buffer.writeDouble(content.y);
         }

         protected Vec2d readContent(RegistryFriendlyByteBuf buffer) {
            return new Vec2d(buffer.readDouble(), buffer.readDouble());
         }
      }, Vec2d.class);
      register(new NetworkFieldTypeClass<Vec2f>() {
         protected void writeContent(Vec2f content, RegistryFriendlyByteBuf buffer) {
            buffer.writeFloat(content.x);
            buffer.writeFloat(content.y);
         }

         protected Vec2f readContent(RegistryFriendlyByteBuf buffer) {
            return new Vec2f(buffer.readFloat(), buffer.readFloat());
         }
      }, Vec2f.class);
      register(new NetworkFieldTypeClass<Vec3d>() {
         protected void writeContent(Vec3d content, RegistryFriendlyByteBuf buffer) {
            buffer.writeDouble(content.x);
            buffer.writeDouble(content.y);
            buffer.writeDouble(content.z);
         }

         protected Vec3d readContent(RegistryFriendlyByteBuf buffer) {
            return new Vec3d(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
         }
      }, Vec3d.class);
      register(new NetworkFieldTypeClass<Vec3f>() {
         protected void writeContent(Vec3f content, RegistryFriendlyByteBuf buffer) {
            buffer.writeFloat(content.x);
            buffer.writeFloat(content.y);
            buffer.writeFloat(content.z);
         }

         protected Vec3f readContent(RegistryFriendlyByteBuf buffer) {
            return new Vec3f(buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
         }
      }, Vec3f.class);
      register(new NetworkFieldTypeClass<Vector3f>() {
         protected void writeContent(Vector3f content, RegistryFriendlyByteBuf buffer) {
            buffer.writeFloat(content.x());
            buffer.writeFloat(content.y());
            buffer.writeFloat(content.z());
         }

         protected Vector3f readContent(RegistryFriendlyByteBuf buffer) {
            return new Vector3f(buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
         }
      }, Vector3f.class);
      register(new NetworkFieldTypeClass<UUID>() {
         protected void writeContent(UUID content, RegistryFriendlyByteBuf buffer) {
            buffer.writeUtf(content.toString());
         }

         protected UUID readContent(RegistryFriendlyByteBuf buffer) {
            return UUID.fromString(buffer.readUtf(32767));
         }
      }, UUID.class);
      register(new NetworkFieldTypeClass<JsonObject>() {
         protected void writeContent(JsonObject content, RegistryFriendlyByteBuf buffer) {
            buffer.writeUtf(content.toString());
         }

         protected JsonObject readContent(RegistryFriendlyByteBuf buffer) {
            return (JsonObject)NetworkFieldTypes.GSON.fromJson(buffer.readUtf(32767), JsonObject.class);
         }
      }, JsonObject.class);
      register(new NetworkFieldTypeClass<Holder>() {
         protected void writeContent(Holder content, RegistryFriendlyByteBuf buffer) {
            ResourceKey key = (ResourceKey)content.unwrapKey().get();
            buffer.writeResourceLocation(key.registry());
            buffer.writeResourceLocation(key.location());
         }

         protected Holder readContent(RegistryFriendlyByteBuf buffer) {
            ResourceKey<?> key = ResourceKey.create(buffer.readRegistryKey(), buffer.readResourceLocation());
            RegistryLookup l = buffer.registryAccess().lookupOrThrow(key.registryKey());
            return l.getOrThrow(key);
         }
      }, Holder.class);
      register(new NetworkFieldTypeSpecial<Object>((x, y) -> x.isArray()) {
         @Override
         public void write(Object content, Class classType, Type genericType, RegistryFriendlyByteBuf buffer, PacketFlow flow) {
            Class subClass = classType.getComponentType();
            NetworkFieldType subParser = NetworkFieldTypes.get(subClass, null);
            int length = Array.getLength(content);
            buffer.writeInt(length);

            for (int i = 0; i < length; i++) {
               subParser.write(Array.get(content, i), subClass, null, buffer, flow);
            }
         }

         @Override
         public Object read(Class classType, Type genericType, RegistryFriendlyByteBuf buffer, PacketFlow flow) {
            int length = buffer.readInt();
            Class subClass = classType.getComponentType();
            NetworkFieldType subParser = NetworkFieldTypes.get(subClass, null);
            Object object = Array.newInstance(subClass, length);

            for (int i = 0; i < length; i++) {
               Array.set(object, i, subParser.read(subClass, null, buffer, flow));
            }

            return object;
         }
      });
      register(new NetworkFieldTypeSpecial((x, y) -> x.equals(ArrayList.class) || x.equals(List.class)) {
         @Override
         public void write(Object content, Class classType, Type genericType, RegistryFriendlyByteBuf buffer, PacketFlow flow) {
            if (!(genericType instanceof ParameterizedType)) {
               throw new RuntimeException("Missing generic type");
            }

            Type[] types = ((ParameterizedType)genericType).getActualTypeArguments();
            if (types.length != 1) {
               throw new RuntimeException("Invalid generic type");
            }

            Class subClass = (Class)((ParameterizedType)genericType).getActualTypeArguments()[0];
            Type subType = types[0];
            NetworkFieldType subParser = NetworkFieldTypes.get(subClass, subType);
            if (subParser == null) {
               throw new RuntimeException("Invalid class type " + classType.getComponentType().getName());
            }

            int length = ((List)content).size();
            buffer.writeInt(length);

            for (int i = 0; i < length; i++) {
               subParser.write(((List)content).get(i), subClass, subType, buffer, flow);
            }
         }

         @Override
         public Object read(Class classType, Type genericType, RegistryFriendlyByteBuf buffer, PacketFlow flow) {
            if (!(genericType instanceof ParameterizedType)) {
               throw new RuntimeException("Missing generic type");
            }

            Type[] types = ((ParameterizedType)genericType).getActualTypeArguments();
            if (types.length != 1) {
               throw new RuntimeException("Invalid generic type");
            }

            Class subClass = (Class)((ParameterizedType)genericType).getActualTypeArguments()[0];
            Type subType = types[0];
            NetworkFieldType subParser = NetworkFieldTypes.get(subClass, subType);
            if (subParser == null) {
               throw new RuntimeException("Invalid class type " + classType.getComponentType().getName());
            }

            int length = buffer.readInt();
            List list = new ArrayList(length);

            for (int j = 0; j < length; j++) {
               list.add(subParser.read(subClass, subType, buffer, flow));
            }

            return list;
         }
      });
      register(new NetworkFieldTypeSpecial<Object>((x, y) -> x.isEnum()) {
         @Override
         public void write(Object content, Class classType, Type genericType, RegistryFriendlyByteBuf buffer, PacketFlow flow) {
            buffer.writeEnum((Enum)content);
         }

         @Override
         public Object read(Class classType, Type genericType, RegistryFriendlyByteBuf buffer, PacketFlow flow) {
            return buffer.readEnum(classType);
         }
      });
      register(new NetworkFieldTypeClass<Filter>() {
         protected void writeContent(Filter content, RegistryFriendlyByteBuf buffer) {
            try {
               buffer.writeNbt(Filter.SERIALIZER.write(content));
            } catch (RegistryException e) {
               CreativeCore.LOGGER.error(e);
            }
         }

         protected Filter readContent(RegistryFriendlyByteBuf buffer) {
            try {
               return Filter.SERIALIZER.read((CompoundTag)buffer.readNbt(NbtAccounter.unlimitedHeap()));
            } catch (RegistryException e) {
               CreativeCore.LOGGER.error(e);
               return Filter.or();
            }
         }
      }, Filter.class);
      register(new NetworkFieldTypeClass<BiFilter>() {
         protected void writeContent(BiFilter content, RegistryFriendlyByteBuf buffer) {
            try {
               buffer.writeNbt(BiFilter.SERIALIZER.write(content));
            } catch (RegistryException e) {
               CreativeCore.LOGGER.error(e);
            }
         }

         protected BiFilter readContent(RegistryFriendlyByteBuf buffer) {
            try {
               return BiFilter.SERIALIZER.read((CompoundTag)buffer.readNbt(NbtAccounter.unlimitedHeap()));
            } catch (RegistryException e) {
               CreativeCore.LOGGER.error(e);
               return BiFilter.or();
            }
         }
      }, BiFilter.class);
      register(new NetworkFieldTypeClass<Component>() {
         private static final Gson GSON = (Gson)Util.make(() -> {
            GsonBuilder gsonbuilder = new GsonBuilder();
            gsonbuilder.disableHtmlEscaping();
            return gsonbuilder.create();
         });

         protected void writeContent(Component content, RegistryFriendlyByteBuf buffer) {
            buffer.writeUtf(GSON.toJson(content));
         }

         protected Component readContent(RegistryFriendlyByteBuf buffer) {
            return (Component)GsonHelper.fromJson(GSON, buffer.readUtf(), MutableComponent.class, false);
         }
      }, Component.class);
      register(new NetworkFieldTypeSpecial<Tag>((x, y) -> Tag.class.isAssignableFrom(x)) {
         public void write(Tag content, Class classType, Type genericType, RegistryFriendlyByteBuf buffer, PacketFlow flow) {
            buffer.writeNbt(content);
         }

         public Tag read(Class classType, Type genericType, RegistryFriendlyByteBuf buffer, PacketFlow flow) {
            return buffer.readNbt(NbtAccounter.unlimitedHeap());
         }
      });
      register(new NetworkFieldTypeSpecial<Packet>((x, y) -> Packet.class.isAssignableFrom(x)) {
         public void write(Packet content, Class classType, Type genericType, RegistryFriendlyByteBuf buffer, PacketFlow flow) {
            StreamCodec<ByteBuf, Packet<? extends PacketListener>> codec = CreativeNetworkUtils.getPacketCodec(buffer, flow);
            boolean bundle = content instanceof BundlePacket;
            if (bundle) {
               List<Packet> packets = CreativeNetworkUtils.flatten(new SingleIterator<>(content));
               buffer.writeInt(packets.size());

               for (Packet packet : packets) {
                  buffer.writeNullable(packet, codec);
               }
            } else {
               buffer.writeInt(0);
               buffer.writeNullable(content, codec);
            }
         }

         public Packet read(Class classType, Type genericType, RegistryFriendlyByteBuf buffer, PacketFlow flow) {
            StreamCodec<ByteBuf, Packet<? extends PacketListener>> codec = CreativeNetworkUtils.getPacketCodec(buffer, flow);
            int size = buffer.readInt();
            if (size == 0) {
               return (Packet)buffer.readNullable(codec);
            }

            List<Packet> packets = new ArrayList<>(size);

            for (int i = 0; i < size; i++) {
               packets.add((Packet)buffer.readNullable(codec));
            }

            return new BundlePacketWrapper(packets);
         }
      });
      register(new NetworkFieldTypeClass<Class>() {
         protected void writeContent(Class content, RegistryFriendlyByteBuf buffer) {
            buffer.writeUtf(content.getName());
         }

         protected Class readContent(RegistryFriendlyByteBuf buffer) {
            try {
               return Class.forName(buffer.readUtf(32767));
            } catch (ClassNotFoundException e) {
               return null;
            }
         }
      }, Class.class);
      register(
         new NetworkFieldTypeClass<IntMatrix3c>() {
            protected void writeContent(IntMatrix3c content, RegistryFriendlyByteBuf buffer) {
               buffer.writeInt(content.m00());
               buffer.writeInt(content.m01());
               buffer.writeInt(content.m02());
               buffer.writeInt(content.m10());
               buffer.writeInt(content.m11());
               buffer.writeInt(content.m12());
               buffer.writeInt(content.m20());
               buffer.writeInt(content.m21());
               buffer.writeInt(content.m22());
            }

            protected IntMatrix3c readContent(RegistryFriendlyByteBuf buffer) {
               return new IntMatrix3(
                  buffer.readInt(),
                  buffer.readInt(),
                  buffer.readInt(),
                  buffer.readInt(),
                  buffer.readInt(),
                  buffer.readInt(),
                  buffer.readInt(),
                  buffer.readInt(),
                  buffer.readInt()
               );
            }
         },
         IntMatrix3c.class,
         IntMatrix3.class
      );
      register(new NetworkFieldTypeClass<DataComponentPatch>() {
         protected void writeContent(DataComponentPatch content, RegistryFriendlyByteBuf buffer) {
            DataComponentPatch.STREAM_CODEC.encode(buffer, content);
         }

         protected DataComponentPatch readContent(RegistryFriendlyByteBuf buffer) {
            return (DataComponentPatch)DataComponentPatch.STREAM_CODEC.decode(buffer);
         }
      }, DataComponentPatch.class);
   }
}
