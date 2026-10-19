package team.creative.creativecore.common.config.converation;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.creative.creativecore.Side;
import team.creative.creativecore.common.config.api.CreativeConfig;
import team.creative.creativecore.common.config.converation.registry.ConfigTypeRegistryObject;
import team.creative.creativecore.common.config.converation.registry.ConfigTypeRegistryObjectList;
import team.creative.creativecore.common.config.converation.registry.ConfigTypeRegistryTag;
import team.creative.creativecore.common.config.converation.registry.ConfigTypeRegistryTagList;
import team.creative.creativecore.common.config.core.ICreativeRegistry;
import team.creative.creativecore.common.config.field.ConfigField;
import team.creative.creativecore.common.config.gui.IGuiConfigParent;
import team.creative.creativecore.common.config.holder.ICreativeConfigHolder;
import team.creative.creativecore.common.config.key.ConfigKey;
import team.creative.creativecore.common.config.premade.MobEffectConfig;
import team.creative.creativecore.common.config.premade.NamedList;
import team.creative.creativecore.common.config.premade.Permission;
import team.creative.creativecore.common.config.premade.SelectableConfig;
import team.creative.creativecore.common.config.premade.SoundConfig;
import team.creative.creativecore.common.config.premade.ToggleableConfig;
import team.creative.creativecore.common.config.premade.registry.RegistryObjectConfig;
import team.creative.creativecore.common.config.premade.registry.RegistryObjectListConfig;
import team.creative.creativecore.common.config.premade.registry.RegistryTagConfig;
import team.creative.creativecore.common.config.premade.registry.RegistryTagListConfig;
import team.creative.creativecore.common.config.sync.ConfigSynchronization;
import team.creative.creativecore.common.gui.GuiParent;
import team.creative.creativecore.common.gui.control.collection.GuiComboBox;
import team.creative.creativecore.common.gui.control.simple.GuiLabel;
import team.creative.creativecore.common.gui.control.simple.GuiSlider;
import team.creative.creativecore.common.gui.control.simple.GuiTextfield;
import team.creative.creativecore.common.gui.flow.GuiFlow;
import team.creative.creativecore.common.util.math.matrix.IntMatrix3;
import team.creative.creativecore.common.util.math.matrix.IntMatrix3c;
import team.creative.creativecore.common.util.text.TextMapBuilder;
import team.creative.creativecore.common.util.type.list.PairList;

public abstract class ConfigTypeConveration<T> {
   private static final HashMap<Class, Function<ConfigField, ?>> TYPE_CREATORS = new HashMap<>();
   private static final HashMap<Class, Function<ConfigField, Type>> TYPE_GETTERS = new HashMap<>();
   private static final HashMap<Class, Function<ConfigField, ?>> COLLECTION_CREATORS = new HashMap<>();
   private static final HashMap<Class, ConfigTypeConveration> TYPES = new HashMap<>();
   private static final PairList<Predicate<Class>, ConfigTypeConveration> SPECIAL_TYPES = new PairList<>();
   public static final ICreativeConfigHolder FAKE_PARENT = new ICreativeConfigHolder() {
      @Override
      public ConfigSynchronization synchronization() {
         return ConfigSynchronization.UNIVERSAL;
      }

      @Override
      public JsonObject save(Provider provider, boolean saveDefault, boolean ignoreRestart, Side side) {
         return null;
      }

      @Override
      public void restoreDefault(Side side, boolean ignoreRestart) {
      }

      @Override
      public String[] path() {
         return new String[0];
      }

      @Override
      public ICreativeConfigHolder parent() {
         return null;
      }

      @Override
      public Collection<String> names() {
         return null;
      }

      @Override
      public void load(Provider provider, boolean loadDefault, boolean ignoreRestart, JsonObject json, Side side) {
      }

      @Override
      public boolean isEmptyWithoutForce(Side side) {
         return false;
      }

      @Override
      public boolean isEmpty(Side side) {
         return false;
      }

      @Override
      public boolean isDefault(Side side) {
         return false;
      }

      @Override
      public ConfigKey getField(String key) {
         return null;
      }

      @Override
      public Object get(String key) {
         return null;
      }

      @Override
      public Collection<? extends ConfigKey> fields() {
         return null;
      }

      @Override
      public void configured(Side side) {
      }

      @Override
      public ICreativeRegistry getRegistry() {
         throw new UnsupportedOperationException("This fake parent should never be used to get the registry");
      }
   };

   public static boolean isTypeCreator(Class clazz) {
      return TYPE_CREATORS.containsKey(clazz);
   }

   public static <T, U extends T> void registerTypeCreator(Class<U> clazz, Function<ConfigField, T> type) {
      TYPE_CREATORS.put(clazz, type);
   }

   public static <T, U extends T> void registerTypeCreator(Class<U> clazz, Supplier<T> type) {
      TYPE_CREATORS.put(clazz, x -> type.get());
   }

   public static void registerTypeGetter(Class clazz, Function<ConfigField, Type> getter) {
      TYPE_GETTERS.put(clazz, getter);
   }

   public static <T, U extends T> void registerCollectionCreator(Class<U> clazz, Function<ConfigField, T> getter) {
      COLLECTION_CREATORS.put(clazz, getter);
   }

   public static <T, U extends T> ConfigTypeConveration<T> registerType(Class<U> clazz, ConfigTypeConveration<T> type) {
      TYPES.put(clazz, type);
      return type;
   }

   public static <T> void registerTypes(ConfigTypeConveration<T> type, Class<? extends T>... classes) {
      for (int i = 0; i < classes.length; i++) {
         TYPES.put(classes[i], type);
      }
   }

   public static void registerSpecialType(Predicate<Class> predicate, ConfigTypeConveration type) {
      SPECIAL_TYPES.add(predicate, type);
   }

   public static boolean has(Class typeClass) {
      if (TYPES.containsKey(typeClass)) {
         return true;
      }

      if (typeClass.isAnnotationPresent(CreativeConfig.class)) {
         return false;
      }

      for (int i = 0; i < SPECIAL_TYPES.size(); i++) {
         if (((Predicate)SPECIAL_TYPES.get(i).key).test(typeClass)) {
            return true;
         }
      }

      return false;
   }

   public static ConfigTypeConveration get(Class typeClass) {
      ConfigTypeConveration converation = TYPES.get(typeClass);
      if (converation != null) {
         return converation;
      }

      for (int i = 0; i < SPECIAL_TYPES.size(); i++) {
         if (((Predicate)SPECIAL_TYPES.get(i).key).test(typeClass)) {
            return (ConfigTypeConveration)SPECIAL_TYPES.get(i).value;
         }
      }

      throw new RuntimeException("Could not find converation for " + typeClass.getName());
   }

   public static ConfigTypeConveration getUnsafe(Class typeClass) {
      ConfigTypeConveration converation = TYPES.get(typeClass);
      if (converation != null) {
         return converation;
      }

      for (int i = 0; i < SPECIAL_TYPES.size(); i++) {
         if (((Predicate)SPECIAL_TYPES.get(i).key).test(typeClass)) {
            return (ConfigTypeConveration)SPECIAL_TYPES.get(i).value;
         }
      }

      return null;
   }

   public static Object createObject(ConfigField field) {
      Function func = TYPE_CREATORS.get(field.getType());
      if (func != null) {
         return func.apply(field);
      }

      try {
         return field.getType().getConstructor().newInstance();
      } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException var3) {
         return null;
      }
   }

   public static Type getGenericType(ConfigField field) {
      Function<ConfigField, Type> func = TYPE_GETTERS.get(field.getType());
      if (func != null) {
         return func.apply(field);
      } else {
         Type type = field.getGenericType();
         if (type instanceof Class) {
            return type;
         } else if (type instanceof ParameterizedType p) {
            return p.getActualTypeArguments()[0];
         } else {
            throw new UnsupportedOperationException("This type is not supported " + type);
         }
      }
   }

   public static Type getGenericType(ConfigKey key) {
      return getGenericType(key.field());
   }

   public static Object createCollection(ConfigField field) {
      Function func = COLLECTION_CREATORS.get(field.getType());
      return func != null ? func.apply(field) : null;
   }

   public static Object createCollection(ConfigKey key) {
      return createCollection(key.field());
   }

   public abstract T readElement(Provider var1, T var2, boolean var3, boolean var4, JsonElement var5, Side var6, ConfigKey var7);

   public abstract JsonElement writeElement(Provider var1, T var2, boolean var3, boolean var4, Side var5, ConfigKey var6);

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   public abstract void createControls(GuiParent var1, IGuiConfigParent var2, ConfigKey var3, Side var4);

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   public abstract void loadValue(T var1, T var2, GuiParent var3, IGuiConfigParent var4, ConfigKey var5, Side var6);

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   public void restoreDefault(T value, GuiParent parent, IGuiConfigParent configParent, ConfigKey key, Side side) {
      this.loadValue(value, value, parent, configParent, key, side);
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   public boolean shouldSave(T value, GuiParent parent, IGuiConfigParent configParent, ConfigKey key, Side side) {
      return !key.get().equals(value);
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   protected abstract T saveValue(GuiParent var1, IGuiConfigParent var2, ConfigKey var3, Side var4);

   public abstract T set(ConfigKey var1, T var2);

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   public T save(GuiParent parent, IGuiConfigParent configParent, ConfigKey key, Side side) {
      T value = this.saveValue(parent, configParent, key, side);
      return value != null && key != null ? this.set(key, value) : value;
   }

   public boolean areEqual(T one, T two, ConfigKey key, Side side) {
      return one.equals(two);
   }

   static {
      ConfigTypeNumber.init();
      registerType(String.class, new ConfigTypeConveration.SimpleConfigTypeConveration<String>() {
         public String readElement(ConfigKey key, String defaultValue, Side side, JsonElement element) {
            return element.isJsonPrimitive() && ((JsonPrimitive)element).isString() ? element.getAsString() : defaultValue;
         }

         public JsonElement writeElement(String value, ConfigKey key, Side side) {
            return new JsonPrimitive(value);
         }

         @OnlyIn(Dist.CLIENT)
         @Environment(EnvType.CLIENT)
         @Override
         public void createControls(GuiParent parent, ConfigKey key) {
            parent.add(new GuiTextfield("data").setDim(30, 8).setExpandableX());
         }

         @OnlyIn(Dist.CLIENT)
         @Environment(EnvType.CLIENT)
         public void loadValue(String value, GuiParent parent) {
            GuiTextfield button = parent.get("data");
            button.setText(value);
         }

         @OnlyIn(Dist.CLIENT)
         @Environment(EnvType.CLIENT)
         protected String saveValue(GuiParent parent, ConfigKey key) {
            GuiTextfield button = parent.get("data");
            return button.getText();
         }

         public String set(ConfigKey key, String value) {
            return value;
         }
      });
      registerTypeCreator(String.class, () -> "");
      registerType(ResourceLocation.class, new ConfigTypeConveration.SimpleConfigTypeConveration<ResourceLocation>() {
         public ResourceLocation readElement(ConfigKey key, ResourceLocation defaultValue, Side side, JsonElement element) {
            return element.isJsonPrimitive() && ((JsonPrimitive)element).isString() ? ResourceLocation.parse(element.getAsString()) : defaultValue;
         }

         public JsonElement writeElement(ResourceLocation value, ConfigKey key, Side side) {
            return new JsonPrimitive(value.toString());
         }

         @OnlyIn(Dist.CLIENT)
         @Environment(EnvType.CLIENT)
         @Override
         public void createControls(GuiParent parent, ConfigKey key) {
            parent.add(new GuiTextfield("data").setDim(30, 8).setExpandableX());
         }

         @OnlyIn(Dist.CLIENT)
         @Environment(EnvType.CLIENT)
         public void loadValue(ResourceLocation value, GuiParent parent) {
            GuiTextfield button = parent.get("data");
            button.setText(value.toString());
         }

         @OnlyIn(Dist.CLIENT)
         @Environment(EnvType.CLIENT)
         protected ResourceLocation saveValue(GuiParent parent, ConfigKey key) {
            GuiTextfield button = parent.get("data");
            return ResourceLocation.parse(button.getText());
         }

         public ResourceLocation set(ConfigKey key, ResourceLocation value) {
            return value;
         }
      });
      registerTypeCreator(ResourceLocation.class, () -> ResourceLocation.withDefaultNamespace(""));
      registerType(
         SoundConfig.class,
         new ConfigTypeConveration<SoundConfig>() {
            public SoundConfig readElement(
               Provider provider, SoundConfig defaultValue, boolean loadDefault, boolean ignoreRestart, JsonElement element, Side side, ConfigKey key
            ) {
               return element.isJsonObject()
                  ? new SoundConfig(
                     ResourceLocation.parse(element.getAsJsonObject().get("sound").getAsString()),
                     element.getAsJsonObject().get("volume").getAsFloat(),
                     element.getAsJsonObject().get("pitch").getAsFloat()
                  )
                  : defaultValue;
            }

            public JsonElement writeElement(Provider provider, SoundConfig value, boolean saveDefault, boolean ignoreRestart, Side side, ConfigKey key) {
               JsonObject json = new JsonObject();
               json.addProperty("sound", value.event.toString());
               json.addProperty("volume", value.volume);
               json.addProperty("pitch", value.pitch);
               return json;
            }

            @OnlyIn(Dist.CLIENT)
            @Environment(EnvType.CLIENT)
            @Override
            public void createControls(GuiParent parent, IGuiConfigParent configParent, ConfigKey key, Side side) {
               parent.flow = GuiFlow.STACK_Y;
               parent.add(
                  new GuiComboBox<>(
                        "sound",
                        new TextMapBuilder<ResourceLocation>()
                           .addComponent(
                              BuiltInRegistries.SOUND_EVENT.keySet(),
                              x -> x.getNamespace().equals("minecraft") ? Component.literal(x.getPath()) : Component.literal(x.toString())
                           )
                     )
                     .setSearchbar(true)
               );
               GuiParent hBox = new GuiParent(GuiFlow.STACK_X)
                  .add(new GuiLabel("volumeLabel").setTranslate("gui.volume"))
                  .add(new GuiSlider("volume", 1.0, 0.0, 1.0).setDim(40, 10))
                  .add(new GuiLabel("pitchLabel").setTranslate("gui.pitch"))
                  .add(new GuiSlider("pitch", 1.0, 0.5, 2.0).setDim(40, 10));
               parent.add(hBox);
            }

            @OnlyIn(Dist.CLIENT)
            @Environment(EnvType.CLIENT)
            public void loadValue(SoundConfig value, SoundConfig defaultValue, GuiParent parent, IGuiConfigParent configParent, ConfigKey key, Side side) {
               GuiComboBox<ResourceLocation> box = parent.get("sound");
               GuiSlider volume = parent.get("volume");
               GuiSlider pitch = parent.get("pitch");
               box.select(value.event);
               volume.setValue(value.volume);
               pitch.setValue(value.pitch);
            }

            @OnlyIn(Dist.CLIENT)
            @Environment(EnvType.CLIENT)
            protected SoundConfig saveValue(GuiParent parent, IGuiConfigParent configParent, ConfigKey key, Side side) {
               GuiComboBox<ResourceLocation> box = parent.get("sound");
               GuiSlider volume = parent.get("volume");
               GuiSlider pitch = parent.get("pitch");
               return new SoundConfig(box.selected(), (float)volume.getValue(), (float)pitch.getValue());
            }

            public SoundConfig set(ConfigKey key, SoundConfig value) {
               return value;
            }
         }
      );
      registerTypeCreator(SoundConfig.class, () -> new SoundConfig(ResourceLocation.withDefaultNamespace("missing")));
      registerType(RegistryObjectConfig.class, new ConfigTypeRegistryObject());
      registerType(RegistryObjectListConfig.class, new ConfigTypeRegistryObjectList());
      registerType(RegistryTagConfig.class, new ConfigTypeRegistryTag());
      registerType(RegistryTagListConfig.class, new ConfigTypeRegistryTagList());
      registerType(
         SelectableConfig.class,
         new ConfigTypeConveration<SelectableConfig>() {
            public SelectableConfig readElement(
               Provider provider, SelectableConfig defaultValue, boolean loadDefault, boolean ignoreRestart, JsonElement element, Side side, ConfigKey key
            ) {
               if (element.isJsonPrimitive() && ((JsonPrimitive)element).isNumber()) {
                  defaultValue.select(element.getAsInt());
               } else {
                  defaultValue.reset();
               }

               return defaultValue;
            }

            public JsonElement writeElement(Provider provider, SelectableConfig value, boolean saveDefault, boolean ignoreRestart, Side side, ConfigKey key) {
               return new JsonPrimitive(value.getSelected());
            }

            @OnlyIn(Dist.CLIENT)
            @Environment(EnvType.CLIENT)
            @Override
            public void createControls(GuiParent parent, IGuiConfigParent configParent, ConfigKey key, Side side) {
               SelectableConfig value = (SelectableConfig)key.get();
               configParent.setCustomData(value.getSelected());
               parent.add(
                  new GuiComboBox<>("data", new TextMapBuilder<Object>().addComponent(value.getArray(), x -> Component.literal(x.toString()))).setExpandableX()
               );
            }

            @OnlyIn(Dist.CLIENT)
            @Environment(EnvType.CLIENT)
            public void loadValue(
               SelectableConfig value, SelectableConfig defaultValue, GuiParent parent, IGuiConfigParent configParent, ConfigKey key, Side side
            ) {
               GuiComboBox box = parent.get("data");
               box.select(value.getSelected());
            }

            @OnlyIn(Dist.CLIENT)
            @Environment(EnvType.CLIENT)
            public void restoreDefault(SelectableConfig value, GuiParent parent, IGuiConfigParent configParent, ConfigKey key, Side side) {
               value.reset();
               this.loadValue(value, value, parent, configParent, key, side);
            }

            @OnlyIn(Dist.CLIENT)
            @Environment(EnvType.CLIENT)
            protected SelectableConfig saveValue(GuiParent parent, IGuiConfigParent configParent, ConfigKey key, Side side) {
               SelectableConfig config = (SelectableConfig)key.get();
               GuiComboBox box = parent.get("data");
               config.select(box.selectedIndex());
               return config;
            }

            public SelectableConfig set(ConfigKey key, SelectableConfig value) {
               return value;
            }

            @OnlyIn(Dist.CLIENT)
            @Environment(EnvType.CLIENT)
            public boolean shouldSave(SelectableConfig value, GuiParent parent, IGuiConfigParent configParent, ConfigKey key, Side side) {
               return value.getSelected() != (Integer)configParent.getCustomData();
            }
         }
      );
      registerType(NamedList.class, new ConfigTypeNamedList());
      registerType(Permission.class, new ConfigTypePermission());
      registerTypeCreator(
         MobEffectConfig.class, () -> new MobEffectConfig(BuiltInRegistries.MOB_EFFECT, ResourceLocation.withDefaultNamespace("slowness"), 2, 1, false)
      );
      registerType(ToggleableConfig.class, new ConfigTypeToggleable());
      registerTypes(
         new ConfigTypeConveration.SimpleConfigTypeConveration<IntMatrix3c>() {
            public IntMatrix3c readElement(ConfigKey key, IntMatrix3c defaultValue, Side side, JsonElement element) {
               if (element instanceof JsonArray a && a.size() == 9) {
                  int[] array = new int[9];

                  for (int i = 0; i < array.length; i++) {
                     array[i] = a.get(i).getAsInt();
                  }

                  return new IntMatrix3(array);
               } else {
                  return new IntMatrix3(defaultValue);
               }
            }

            public JsonElement writeElement(IntMatrix3c value, ConfigKey key, Side side) {
               JsonArray json = new JsonArray(9);
               int[] array = value.getAsArray();

               for (int i = 0; i < array.length; i++) {
                  json.add(array[i]);
               }

               return json;
            }

            @OnlyIn(Dist.CLIENT)
            @Environment(EnvType.CLIENT)
            @Override
            public void createControls(GuiParent parent, ConfigKey key) {
               parent.flow = GuiFlow.STACK_Y;
               GuiParent r = new GuiParent();
               parent.add(r);
               r.add(new GuiTextfield("m00").setNumbersIncludingNegativeOnly());
               r.add(new GuiTextfield("m01").setNumbersIncludingNegativeOnly());
               r.add(new GuiTextfield("m02").setNumbersIncludingNegativeOnly());
               r = new GuiParent();
               parent.add(r);
               r.add(new GuiTextfield("m10").setNumbersIncludingNegativeOnly());
               r.add(new GuiTextfield("m11").setNumbersIncludingNegativeOnly());
               r.add(new GuiTextfield("m12").setNumbersIncludingNegativeOnly());
               r = new GuiParent();
               parent.add(r);
               r.add(new GuiTextfield("m20").setNumbersIncludingNegativeOnly());
               r.add(new GuiTextfield("m21").setNumbersIncludingNegativeOnly());
               r.add(new GuiTextfield("m22").setNumbersIncludingNegativeOnly());
            }

            @OnlyIn(Dist.CLIENT)
            @Environment(EnvType.CLIENT)
            public void loadValue(IntMatrix3c value, GuiParent parent) {
               parent.get("m00", GuiTextfield.class).setText(value.m00() + "");
               parent.get("m01", GuiTextfield.class).setText(value.m01() + "");
               parent.get("m02", GuiTextfield.class).setText(value.m02() + "");
               parent.get("m10", GuiTextfield.class).setText(value.m10() + "");
               parent.get("m11", GuiTextfield.class).setText(value.m11() + "");
               parent.get("m12", GuiTextfield.class).setText(value.m12() + "");
               parent.get("m20", GuiTextfield.class).setText(value.m20() + "");
               parent.get("m21", GuiTextfield.class).setText(value.m21() + "");
               parent.get("m22", GuiTextfield.class).setText(value.m22() + "");
            }

            @OnlyIn(Dist.CLIENT)
            @Environment(EnvType.CLIENT)
            protected IntMatrix3c saveValue(GuiParent parent, ConfigKey key) {
               return new IntMatrix3(
                  parent.get("m00", GuiTextfield.class).parseInteger(),
                  parent.get("m01", GuiTextfield.class).parseInteger(),
                  parent.get("m02", GuiTextfield.class).parseInteger(),
                  parent.get("m10", GuiTextfield.class).parseInteger(),
                  parent.get("m11", GuiTextfield.class).parseInteger(),
                  parent.get("m12", GuiTextfield.class).parseInteger(),
                  parent.get("m20", GuiTextfield.class).parseInteger(),
                  parent.get("m21", GuiTextfield.class).parseInteger(),
                  parent.get("m22", GuiTextfield.class).parseInteger()
               );
            }

            public IntMatrix3c set(ConfigKey key, IntMatrix3c value) {
               return value;
            }
         },
         IntMatrix3c.class,
         IntMatrix3.class
      );
      registerSpecialType(x -> {
         if (x.isArray()) {
            if (has(x.getComponentType())) {
               return true;
            } else {
               throw new RuntimeException("Array with holders are not permitted");
            }
         } else {
            return false;
         }
      }, new ConfigTypeArray());
      registerSpecialType(
         Enum.class::isAssignableFrom,
         new ConfigTypeConveration.SimpleConfigTypeConveration<Enum>() {
            private static Class getEnumClass(Class clazz) {
               return clazz.isEnum() ? clazz : clazz.getSuperclass();
            }

            public Enum readElement(ConfigKey key, Enum defaultValue, Side side, JsonElement element) {
               return element.isJsonPrimitive() && ((JsonPrimitive)element).isString()
                  ? Enum.valueOf(defaultValue.getDeclaringClass(), element.getAsString())
                  : defaultValue;
            }

            public JsonElement writeElement(Enum value, ConfigKey key, Side side) {
               return new JsonPrimitive(value.name());
            }

            @OnlyIn(Dist.CLIENT)
            @Environment(EnvType.CLIENT)
            @Override
            public void createControls(GuiParent parent, ConfigKey key) {
               parent.add(
                  new GuiComboBox<>(
                     "data",
                     new TextMapBuilder<Object>()
                        .addComponent(getEnumClass(key.field().getType()).getEnumConstants(), x -> Component.literal(((Enum)x).name()))
                  )
               );
            }

            @OnlyIn(Dist.CLIENT)
            @Environment(EnvType.CLIENT)
            public void loadValue(Enum value, GuiParent parent) {
               GuiComboBox box = parent.get("data");
               box.select(value.ordinal());
            }

            @OnlyIn(Dist.CLIENT)
            @Environment(EnvType.CLIENT)
            protected Enum saveValue(GuiParent parent, ConfigKey key) {
               GuiComboBox box = parent.get("data");
               return (Enum)box.selected();
            }

            public Enum set(ConfigKey key, Enum value) {
               return value;
            }
         }
      );
      registerSpecialType(x -> List.class.isAssignableFrom(x) || x == ArrayList.class, new ConfigTypeList());
   }

   public abstract static class SimpleConfigTypeConveration<T> extends ConfigTypeConveration<T> {
      @Override
      public T readElement(Provider provider, T defaultValue, boolean loadDefault, boolean ignoreRestart, JsonElement element, Side side, ConfigKey key) {
         return this.readElement(key, defaultValue, side, element);
      }

      public abstract T readElement(ConfigKey var1, T var2, Side var3, JsonElement var4);

      @Override
      public JsonElement writeElement(Provider provider, T value, boolean ignoreRestart, boolean saveDefault, Side side, ConfigKey key) {
         return this.writeElement(value, key, side);
      }

      public abstract JsonElement writeElement(T var1, ConfigKey var2, Side var3);

      @OnlyIn(Dist.CLIENT)
      @Environment(EnvType.CLIENT)
      @Override
      public void createControls(GuiParent parent, IGuiConfigParent configParent, ConfigKey key, Side side) {
         this.createControls(parent, key);
      }

      @OnlyIn(Dist.CLIENT)
      @Environment(EnvType.CLIENT)
      public abstract void createControls(GuiParent var1, ConfigKey var2);

      @OnlyIn(Dist.CLIENT)
      @Environment(EnvType.CLIENT)
      @Override
      public void loadValue(T value, T defaultValue, GuiParent parent, IGuiConfigParent configParent, ConfigKey key, Side side) {
         this.loadValue(value, parent);
      }

      @OnlyIn(Dist.CLIENT)
      @Environment(EnvType.CLIENT)
      public abstract void loadValue(T var1, GuiParent var2);

      @OnlyIn(Dist.CLIENT)
      @Environment(EnvType.CLIENT)
      @Override
      protected T saveValue(GuiParent parent, IGuiConfigParent configParent, ConfigKey key, Side side) {
         return this.saveValue(parent, key);
      }

      @OnlyIn(Dist.CLIENT)
      @Environment(EnvType.CLIENT)
      protected abstract T saveValue(GuiParent var1, ConfigKey var2);
   }
}
