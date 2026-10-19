package net.darkhax.pricklemc.common.api.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.ToNumberPolicy;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import net.darkhax.pricklemc.common.api.config.comment.CommentTypeAdapter;
import net.darkhax.pricklemc.common.api.config.comment.ICommentResolver;
import net.darkhax.pricklemc.common.api.config.comment.WrappedComment;
import net.darkhax.pricklemc.common.api.config.property.IDefaultPropertyAdapters;
import net.darkhax.pricklemc.common.api.config.property.IPropertyAdapter;
import net.darkhax.pricklemc.common.api.config.property.RangedProperty;
import net.darkhax.pricklemc.common.api.config.property.RegexStringProperty;
import net.darkhax.pricklemc.common.api.config.property.array.ArrayProperty;
import net.darkhax.pricklemc.common.api.config.property.array.CollectionArrayProperty;
import net.darkhax.pricklemc.common.api.services.Services;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigManager<T> {
   private final Path filePath;
   private final Logger log;
   private final ConfigObjectSerializer<T> configSerializer;
   private final T obj;

   public static <T> T load(String name, T defaultValue) {
      return load(name, defaultValue, null);
   }

   public static <T> T load(String name, T defaultValue, @Nullable Consumer<ConfigManager.Builder<T>> configure) {
      ConfigManager<T> manager = init(name, defaultValue, configure);
      manager.load();
      manager.save();
      return defaultValue;
   }

   public static <T> ConfigManager<T> init(String name, T defaultValue) {
      return init(name, defaultValue, null);
   }

   public static <T> ConfigManager<T> init(String name, T defaultValue, @Nullable Consumer<ConfigManager.Builder<T>> configure) {
      ConfigManager.Builder<T> builder = new ConfigManager.Builder<>(Services.PLATFORM.getConfigPath().resolve(name + ".json"));

      for (IDefaultPropertyAdapters plugin : Services.loadMany(IDefaultPropertyAdapters.class)) {
         plugin.register(builder::adapter);
      }

      if (configure != null) {
         configure.accept(builder);
      }

      return builder.build(defaultValue);
   }

   private ConfigManager(Path filePath, Logger log, T obj, PropertyResolver resolver) {
      this.filePath = filePath;
      this.log = log;
      this.obj = obj;
      this.configSerializer = new ConfigObjectSerializer<>(resolver, this.obj);
   }

   public void load() {
      if (!Files.exists(this.filePath)) {
         this.save();
      }

      try {
         JsonReader reader = new JsonReader(Files.newBufferedReader(this.filePath));

         try {
            reader.setLenient(true);
            this.configSerializer.read(reader);
         } catch (Throwable var5) {
            try {
               reader.close();
            } catch (Throwable var4) {
               var5.addSuppressed(var4);
            }

            throw var5;
         }

         reader.close();
      } catch (IOException e) {
         this.log.error("Unable to load config file from {}!", this.filePath);
         throw new RuntimeException(e);
      }
   }

   public void save() {
      if (!Files.exists(this.filePath)) {
         try {
            Path parentDir = this.filePath.getParent();
            if (!Files.exists(parentDir)) {
               Files.createDirectories(parentDir);
            }

            Files.createFile(this.filePath);
         } catch (IOException e) {
            this.log.error("Unable to create config file at {}!", this.filePath);
            throw new RuntimeException(e);
         }
      }

      try {
         JsonWriter writer = new JsonWriter(Files.newBufferedWriter(this.filePath, StandardCharsets.UTF_8));

         try {
            writer.setIndent("  ");
            this.configSerializer.write(writer);
         } catch (Throwable var5) {
            try {
               writer.close();
            } catch (Throwable var4) {
               var5.addSuppressed(var4);
            }

            throw var5;
         }

         writer.close();
      } catch (IOException e) {
         this.log.error("Could not save config file to {}!", this.filePath);
         throw new RuntimeException(e);
      }
   }

   public T get() {
      return this.obj;
   }

   public static class Builder<T> {
      private final Path filePath;
      private final List<IPropertyAdapter<?>> propertyAdapters = new LinkedList<>();
      private final Map<Class<?>, IPropertyAdapter<?>> adapterOverrideCache = new HashMap<>();
      private final List<Consumer<GsonBuilder>> gsonConfigs = new LinkedList<>();
      private GsonBuilder gsonBuilder;
      private ICommentResolver commentResolver;
      private Logger logger = null;

      public Builder(Path filePath) {
         this.filePath = filePath;
         this.gsonConfig(
            builder -> builder.setPrettyPrinting()
               .disableHtmlEscaping()
               .serializeSpecialFloatingPointValues()
               .setNumberToNumberStrategy(ToNumberPolicy.BIG_DECIMAL)
               .setLenient()
               .registerTypeAdapter(WrappedComment.class, CommentTypeAdapter.INSTANCE)
         );
         this.adapter(RegexStringProperty.ADAPTER);
         this.adapter(RangedProperty.ADAPTER);
         this.adapter(ArrayProperty.ADAPTER);
         this.adapter(CollectionArrayProperty.ADAPTER);
         this.commentResolver(WrappedComment.RESOLVER);
      }

      public ConfigManager.Builder<T> logger(Logger logger) {
         this.logger = logger;
         return this;
      }

      public ConfigManager.Builder<T> adapter(IPropertyAdapter<?> adapter) {
         this.propertyAdapters.add(adapter);
         return this;
      }

      public ConfigManager.Builder<T> gsonBuilder(GsonBuilder builder) {
         this.gsonBuilder = builder;
         return this;
      }

      public ConfigManager.Builder<T> gsonConfig(Consumer<GsonBuilder> config) {
         this.gsonConfigs.add(config);
         return this;
      }

      public ConfigManager.Builder<T> commentResolver(ICommentResolver resolver) {
         this.commentResolver = resolver;
         return this;
      }

      public ConfigManager<T> build(T cfgData) {
         if (cfgData == null) {
            throw new IllegalStateException("Can not create a config from a null value!");
         }

         if (this.filePath == null) {
            throw new IllegalStateException("Config manager can not be built without a file path!");
         }

         if (this.logger == null) {
            this.logger = LoggerFactory.getLogger(cfgData.getClass());
         }

         if (this.gsonBuilder == null) {
            this.gsonBuilder = new GsonBuilder();
         }

         for (Consumer<GsonBuilder> gsonConfig : this.gsonConfigs) {
            gsonConfig.accept(this.gsonBuilder);
         }

         Gson gson = this.gsonBuilder.create();
         return new ConfigManager<>(this.filePath, this.logger, cfgData, new PropertyResolver(gson, this.logger, this.propertyAdapters, this.commentResolver));
      }
   }
}
