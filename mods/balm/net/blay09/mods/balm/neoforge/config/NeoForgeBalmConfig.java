package net.blay09.mods.balm.neoforge.config;

import com.electronwill.nightconfig.core.EnumGetMethod;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.mojang.datafixers.util.Pair;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.config.MutableLoadedConfig;
import net.blay09.mods.balm.api.config.schema.BalmConfigSchema;
import net.blay09.mods.balm.api.config.schema.ConfiguredBoolean;
import net.blay09.mods.balm.api.config.schema.ConfiguredDouble;
import net.blay09.mods.balm.api.config.schema.ConfiguredEnum;
import net.blay09.mods.balm.api.config.schema.ConfiguredFloat;
import net.blay09.mods.balm.api.config.schema.ConfiguredInt;
import net.blay09.mods.balm.api.config.schema.ConfiguredList;
import net.blay09.mods.balm.api.config.schema.ConfiguredLong;
import net.blay09.mods.balm.api.config.schema.ConfiguredProperty;
import net.blay09.mods.balm.api.config.schema.ConfiguredResourceLocation;
import net.blay09.mods.balm.api.config.schema.ConfiguredSet;
import net.blay09.mods.balm.api.config.schema.ConfiguredString;
import net.blay09.mods.balm.api.config.schema.builder.ConfigCategory;
import net.blay09.mods.balm.api.event.ConfigLoadedEvent;
import net.blay09.mods.balm.api.event.ConfigReloadedEvent;
import net.blay09.mods.balm.common.config.AbstractBalmConfig;
import net.blay09.mods.balm.common.config.ConfigLocalization;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.config.IConfigSpec;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;

public class NeoForgeBalmConfig extends AbstractBalmConfig {
   private static final Map<ResourceLocation, Table<String, String, ConfigValue<?>>> properties = new HashMap<>();
   private static final Map<ResourceLocation, ModConfig> modConfigs = new HashMap<>();

   private static ConfigValue<?> addPropertyToSpec(ConfiguredProperty<?> property, Builder spec) {
      spec.comment(property.comment());
      spec.translation(ConfigLocalization.forProperty(property));

      return (ConfigValue<?>)(switch (property) {
         case ConfiguredBoolean configuredBoolean -> spec.define(configuredBoolean.name(), configuredBoolean.defaultValue());
         case ConfiguredDouble configuredDouble -> spec.define(configuredDouble.name(), configuredDouble.defaultValue());
         case ConfiguredEnum<?> configuredEnum -> defineEnum(spec, configuredEnum);
         case ConfiguredFloat configuredFloat -> spec.define(configuredFloat.name(), configuredFloat.defaultValue().doubleValue());
         case ConfiguredInt configuredInt -> spec.define(configuredInt.name(), configuredInt.defaultValue());
         case ConfiguredList<?> configuredList -> spec.defineListAllowEmpty(
            configuredList.name(),
            mapConfigCollectionToNeoForge(configuredList.defaultValue()),
            () -> newListElement(configuredList),
            it -> validateListElement(configuredList, it)
         );
         case ConfiguredLong configuredLong -> spec.define(configuredLong.name(), configuredLong.defaultValue());
         case ConfiguredResourceLocation configuredResourceLocation -> spec.define(
            configuredResourceLocation.name(), configuredResourceLocation.defaultValue().toString()
         );
         case ConfiguredSet<?> configuredSet -> spec.defineListAllowEmpty(
            configuredSet.name(),
            mapConfigCollectionToNeoForge(configuredSet.defaultValue()),
            () -> newSetElement(configuredSet),
            it -> validateSetElement(configuredSet, it)
         );
         case ConfiguredString configuredString -> spec.define(configuredString.name(), configuredString.defaultValue());
         default -> throw new IllegalStateException("Unexpected value: " + property);
      });
   }

   public static List<?> mapConfigCollectionToNeoForge(Collection<?> values) {
      return values.stream().map(NeoForgeBalmConfig::mapConfigValueToNeoForge).toList();
   }

   public static Object mapConfigValueToNeoForge(Object value) {
      return switch (value) {
         case ResourceLocation resourceLocation -> resourceLocation.toString();
         case Float floatValue -> floatValue.doubleValue();
         case Set<?> setValue -> mapConfigCollectionToNeoForge(setValue);
         case List<?> listValue -> mapConfigCollectionToNeoForge(listValue);
         case null, default -> value;
      };
   }

   public static List<?> mapConfigListFromNeoForge(ConfiguredList<?> property, List<?> value) {
      return value.stream().map(it -> mapConfigValueFromNeoForge(property.nestedType(), it)).toList();
   }

   public static Set<?> mapConfigSetFromNeoForge(ConfiguredSet<?> property, List<?> value) {
      return value.stream().map(it -> mapConfigValueFromNeoForge(property.nestedType(), it)).collect(Collectors.toSet());
   }

   public static Object mapConfigValueFromNeoForge(ConfiguredProperty<?> property, Object value) {
      return switch (property) {
         case ConfiguredResourceLocation ignored -> ResourceLocation.parse((String)value);
         case ConfiguredFloat ignored -> ((Double)value).floatValue();
         case ConfiguredList<?> configuredList -> mapConfigListFromNeoForge(configuredList, (List<?>)value);
         case ConfiguredSet<?> configuredSet -> mapConfigSetFromNeoForge(configuredSet, (List<?>)value);
         case null, default -> value;
      };
   }

   private static Object mapConfigValueFromNeoForge(Class<?> nestedType, Object value) {
      if (nestedType == ResourceLocation.class) {
         return ResourceLocation.parse((String)value);
      } else if (nestedType == Float.class) {
         return ((Double)value).floatValue();
      } else {
         return nestedType.isEnum() && value instanceof String ? stringToEnum(value, nestedType) : value;
      }
   }

   private static <T> T newListElement(ConfiguredList<T> configuredList) {
      return newCollectionElement(configuredList.nestedType());
   }

   private static <T> T newSetElement(ConfiguredSet<T> configuredSet) {
      return newCollectionElement(configuredSet.nestedType());
   }

   private static <T> T newCollectionElement(Class<T> nestedType) {
      if (nestedType == Boolean.class) {
         return (T)Boolean.FALSE;
      } else if (nestedType == Double.class) {
         return (T)0.0;
      } else if (nestedType == Float.class) {
         return (T)0.0;
      } else if (nestedType == Integer.class) {
         return (T)0;
      } else if (nestedType == Long.class) {
         return (T)0L;
      } else if (nestedType == ResourceLocation.class) {
         return (T)ResourceLocation.fromNamespaceAndPath("minecraft", "air").toString();
      } else if (nestedType == String.class) {
         return (T)"";
      } else if (nestedType.isEnum()) {
         return nestedType.getEnumConstants()[0];
      } else {
         throw new IllegalArgumentException("Unsupported type " + nestedType);
      }
   }

   private static <T> boolean validateListElement(ConfiguredList<T> configuredList, Object value) {
      return validateCollectionElement(configuredList.nestedType(), value);
   }

   private static <T> boolean validateSetElement(ConfiguredSet<T> configuredSet, Object value) {
      return validateCollectionElement(configuredSet.nestedType(), value);
   }

   private static <T> boolean validateCollectionElement(Class<T> nestedType, Object value) {
      if (nestedType == Boolean.class) {
         return value instanceof Boolean || "true".equals(value) || "false".equals(value);
      }

      if (nestedType == Double.class) {
         try {
            return value instanceof Double || !Double.isNaN(Double.parseDouble(value.toString()));
         } catch (NumberFormatException e) {
            return false;
         }
      } else if (nestedType == Float.class) {
         try {
            return value instanceof Float || !Float.isNaN(Float.parseFloat(value.toString()));
         } catch (NumberFormatException e) {
            return false;
         }
      } else if (nestedType == Integer.class) {
         try {
            if (value instanceof Integer) {
               return true;
            }

            Integer.parseInt(value.toString());
            return true;
         } catch (NumberFormatException e) {
            return false;
         }
      } else if (nestedType == Long.class) {
         try {
            if (value instanceof Long) {
               return true;
            }

            Long.parseLong(value.toString());
            return true;
         } catch (NumberFormatException e) {
            return false;
         }
      } else if (nestedType == ResourceLocation.class) {
         return value instanceof String && ResourceLocation.tryParse(value.toString()) != null;
      } else if (nestedType == String.class) {
         return value instanceof String;
      } else if (!nestedType.isEnum()) {
         throw new IllegalArgumentException("Unsupported type " + nestedType);
      } else {
         return value instanceof String && validateEnum(value, nestedType);
      }
   }

   private static <T extends Enum<T>> boolean validateEnum(Object value, Class<?> unknownClass) {
      if (unknownClass.isEnum()) {
         return EnumGetMethod.NAME_IGNORECASE.validate(value, unknownClass);
      } else {
         throw new IllegalArgumentException("Not an enum class: " + unknownClass.getName());
      }
   }

   private static <T extends Enum<T>> T stringToEnum(Object value, Class<?> unknownClass) {
      if (unknownClass.isEnum()) {
         return (T)EnumGetMethod.NAME_IGNORECASE.get(value, unknownClass);
      } else {
         throw new IllegalArgumentException("Not an enum class: " + unknownClass.getName());
      }
   }

   private static <T extends Enum<T>> ConfigValue<T> defineEnum(Builder spec, ConfiguredEnum<T> configuredEnum) {
      return spec.defineEnum(configuredEnum.name(), configuredEnum.defaultValue(), EnumGetMethod.NAME_IGNORECASE);
   }

   @Override
   public File getConfigDir() {
      return FMLPaths.CONFIGDIR.get().toFile();
   }

   @Override
   public void registerConfig(BalmConfigSchema schema) {
      super.registerConfig(schema);
      String namespace = schema.identifier().getNamespace();
      ModContainer modContainer = (ModContainer)ModList.get()
         .getModContainerById(namespace)
         .orElseThrow(() -> new IllegalStateException("Mod container for " + namespace + " not found when registering config."));
      IEventBus eventBus = modContainer.getEventBus();
      if (eventBus == null) {
         throw new IllegalStateException("Missing event bus for " + schema.identifier().getNamespace() + " when registering config.");
      }

      eventBus.addListener(event -> {
         ModConfig modConfig = event.getConfig();
         ResourceLocation identifier = ResourceLocation.fromNamespaceAndPath(modConfig.getModId(), modConfig.getType().extension());
         if (schema.identifier().equals(identifier)) {
            modConfigs.put(schema.identifier(), modConfig);
            LoadedNeoForgeConfig wrappedConfig = new LoadedNeoForgeConfig(schema, modConfig, properties.get(schema.identifier()));
            this.setLocalConfig(schema, wrappedConfig);
            this.setActiveConfig(schema, wrappedConfig);
            Balm.getEvents().fireEvent(new ConfigLoadedEvent(schema));
         }
      });
      eventBus.addListener(event -> {
         ModConfig modConfig = event.getConfig();
         ResourceLocation identifier = ResourceLocation.fromNamespaceAndPath(modConfig.getModId(), modConfig.getType().extension());
         if (schema.identifier().equals(identifier)) {
            modConfigs.put(schema.identifier(), modConfig);
            LoadedNeoForgeConfig wrappedConfig = new LoadedNeoForgeConfig(schema, modConfig, properties.get(schema.identifier()));
            this.setLocalConfig(schema, wrappedConfig);
            this.updateActiveFromLocal(schema, wrappedConfig);
            Balm.getEvents().fireEvent(new ConfigReloadedEvent(schema));
         }
      });
      String stringType = schema.identifier().getPath();

      Type configType = switch (stringType) {
         case "common" -> Type.COMMON;
         case "client" -> Type.CLIENT;
         case "startup" -> Type.STARTUP;
         default -> throw new IllegalArgumentException("Unsupported config type: " + stringType + " - only 'common', 'client' and 'startup' are supported.");
      };
      Pair<ModConfigSpec, HashBasedTable<String, String, ConfigValue<?>>> mappedConfigSpec = this.mapToConfigSpec(schema);
      properties.put(schema.identifier(), (Table<String, String, ConfigValue<?>>)mappedConfigSpec.getSecond());
      modContainer.registerConfig(configType, (IConfigSpec)mappedConfigSpec.getFirst());
      if (FMLEnvironment.dist == Dist.CLIENT) {
         this.initializeConfigurationScreen(modContainer);
      }
   }

   @Override
   public void saveLocalConfig(BalmConfigSchema schema, MutableLoadedConfig config) {
      super.saveLocalConfig(schema, config);
      ModConfig modConfig = modConfigs.get(schema.identifier());
      if (modConfig == null) {
         throw new IllegalStateException("Backing config not available for " + schema.identifier());
      }

      LoadedNeoForgeConfig wrappedConfig = new LoadedNeoForgeConfig(schema, modConfig, properties.get(schema.identifier()));
      wrappedConfig.applyFrom(schema, config);
      ((ModConfigSpec)modConfig.getSpec()).save();
   }

   private void initializeConfigurationScreen(ModContainer modContainer) {
      modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
   }

   private Pair<ModConfigSpec, HashBasedTable<String, String, ConfigValue<?>>> mapToConfigSpec(BalmConfigSchema schema) {
      Builder spec = new Builder();
      HashBasedTable<String, String, ConfigValue<?>> properties = HashBasedTable.create();

      for (ConfiguredProperty<?> rootProperty : schema.rootProperties()) {
         properties.put("", rootProperty.name(), addPropertyToSpec(rootProperty, spec));
      }

      for (ConfigCategory category : schema.categories()) {
         spec.push(category.name());

         for (ConfiguredProperty<?> property : category.properties()) {
            properties.put(category.name(), property.name(), addPropertyToSpec(property, spec));
         }

         spec.pop();
      }

      return Pair.of(spec.build(), properties);
   }
}
