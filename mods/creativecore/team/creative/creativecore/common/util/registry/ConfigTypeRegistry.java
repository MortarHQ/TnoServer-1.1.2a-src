package team.creative.creativecore.common.util.registry;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import team.creative.creativecore.Side;
import team.creative.creativecore.common.config.core.ConfigEqualChecker;
import team.creative.creativecore.common.config.core.ICreativeRegistry;
import team.creative.creativecore.common.config.gui.GuiConfigSubControlNested;
import team.creative.creativecore.common.config.holder.ConfigHolderObject;
import team.creative.creativecore.common.util.mc.JsonUtils;
import team.creative.creativecore.common.util.mc.NBTUtils;

public class ConfigTypeRegistry<T> implements ICreativeRegistry {
   public static final Predicate<Field> DEFAULT_FIELD_PREDICATE = x -> Modifier.isPublic(x.getModifiers())
      && !Modifier.isTransient(x.getModifiers())
      && !Modifier.isStatic(x.getModifiers());
   private final HashMap<Class<? extends T>, ConfigTypeRegistry<T>.ConfigurationType> types = new LinkedHashMap<>();
   private final HashMap<String, ConfigTypeRegistry<T>.ConfigurationType> loaders = new LinkedHashMap<>();
   private final ConfigEqualChecker equalChecker = new ConfigEqualChecker();
   private ConfigTypeRegistry<T>.ConfigurationType defaultType;
   private Predicate<Field> fieldPredicate = DEFAULT_FIELD_PREDICATE;
   private boolean keepUnrelatedData = true;

   public ConfigTypeRegistry<T> setFieldPredicate(Predicate<Field> fieldPredicate) {
      this.fieldPredicate = fieldPredicate;
      return this;
   }

   public ConfigTypeRegistry<T> setKeepUnrelatedData(boolean keep) {
      this.keepUnrelatedData = keep;
      return this;
   }

   public <C extends T> void register(String id, Class<C> clazz, C defaultReference, Supplier<C> factory) {
      if (this.types.containsKey(clazz)) {
         throw new IllegalArgumentException("Class " + clazz.getSimpleName() + " is already registered!");
      }

      ConfigTypeRegistry<T>.ConfigurationType type = new ConfigTypeRegistry.ConfigurationType(id, clazz, defaultReference, factory);
      this.types.put(clazz, type);
      this.loaders.put(id, type);
   }

   protected ConfigTypeRegistry<T>.ConfigurationType get(T data) {
      return this.types.get(data.getClass());
   }

   public boolean contains(Class<? extends T> clazz) {
      return this.types.containsKey(clazz);
   }

   public String getId(Class<? extends T> clazz) {
      return this.types.get(clazz).id;
   }

   public T load(Provider provider, CompoundTag nbt, Side side) {
      ConfigTypeRegistry<T>.ConfigurationType type = this.loaders.getOrDefault(nbt.getString("t"), this.defaultType);
      return (T)type.load(provider, nbt, side);
   }

   public T loadOrCreateDefault(Provider provider, CompoundTag nbt, String id, Side side) {
      return nbt.getString("t").equals(id) ? this.load(provider, nbt, side) : this.createDefault(id);
   }

   public CompoundTag save(Provider provider, T data, CompoundTag nbt, Side side) {
      ConfigTypeRegistry<T>.ConfigurationType type = this.get(data);
      return type.save(provider, data, nbt, side);
   }

   public GuiConfigSubControlNested create(String name, T data, Side side) {
      ConfigTypeRegistry<T>.ConfigurationType type = this.get(data);
      return new GuiConfigSubControlNested(name, type.create(side, data), data, side, null, true);
   }

   public T createDefault(String id) {
      return (T)this.loaders.get(id).factory.get();
   }

   @Override
   public ConfigEqualChecker getEqualChecker() {
      return this.equalChecker;
   }

   @Override
   public boolean is(Field field) {
      return this.fieldPredicate.test(field);
   }

   private class ConfigurationType {
      private final String id;
      private final Supplier<? extends T> factory;
      private final T defaultReference;

      public ConfigurationType(String id, Class<? extends T> clazz, T defaultReference, Supplier<? extends T> factory) {
         this.id = id;
         this.factory = factory;
         this.defaultReference = defaultReference;
      }

      public ConfigHolderObject create(Side side, T data) {
         return ConfigHolderObject.createUnrelated(ConfigTypeRegistry.this, side, data, this.defaultReference);
      }

      public CompoundTag save(Provider provider, T data, CompoundTag nbt, Side side) {
         nbt = NBTUtils.of(this.create(side, data).save(provider, true, true, side), ConfigTypeRegistry.this.keepUnrelatedData ? nbt : new CompoundTag());
         nbt.putString("t", this.id);
         return nbt;
      }

      public T load(Provider provider, CompoundTag nbt, Side side) {
         T data = (T)this.factory.get();
         this.create(side, data).load(provider, true, true, JsonUtils.of(nbt), side);
         return data;
      }
   }
}
