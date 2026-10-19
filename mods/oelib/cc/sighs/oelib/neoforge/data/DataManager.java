package cc.sighs.oelib.neoforge.data;

import cc.sighs.oelib.OELib;
import cc.sighs.oelib.data.api.DataDriven;
import cc.sighs.oelib.data.api.DataValidator;
import cc.sighs.oelib.data.net.DataSyncPacket;
import cc.sighs.oelib.neoforge.event.DataReloadEvent;
import cc.sighs.oelib.util.CodecUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

@EventBusSubscriber(modid = "oelib")
public class DataManager<T> extends SimpleJsonResourceReloadListener {
   private static final Gson GSON = new GsonBuilder().setLenient().create();
   private static final Map<Class<?>, DataManager<?>> managers = new ConcurrentHashMap<>();
   private static final Map<Class<?>, Set<String>> runtimeRegisteredNamespaces = new ConcurrentHashMap<>();
   private static boolean serverStarted = false;
   private final Class<T> dataClass;
   private final DataDriven annotation;
   private final Codec<T> codec;
   private final Map<ResourceLocation, T> loadedData = new ConcurrentHashMap<>();
   private final Map<ResourceLocation, T> deferredData = new ConcurrentHashMap<>();
   private final Map<String, Set<T>> cache = new ConcurrentHashMap<>();
   private final Map<String, Class<? extends DataValidator<?>>> namespaceValidatorClasses = new ConcurrentHashMap<>();
   private final Map<String, DataValidator<T>> namespaceValidators = new ConcurrentHashMap<>();
   private final DataValidator<T> defaultValidator;

   private DataManager(Class<T> dataClass) {
      super(GSON, getFolder(dataClass));
      this.dataClass = dataClass;
      this.annotation = dataClass.getAnnotation(DataDriven.class);
      this.codec = CodecUtils.getCodec(dataClass);
      this.defaultValidator = this.createValidator(this.annotation.validator());

      for (DataDriven.ValidatorBinding binding : this.annotation.namespaceValidators()) {
         if (binding != null && binding.namespace() != null && !binding.namespace().isBlank() && binding.validator() != null) {
            this.namespaceValidatorClasses.put(binding.namespace(), binding.validator());
         }
      }
   }

   public static <T> void registerNamespaceValidator(Class<T> dataClass, String namespace, Class<? extends DataValidator<?>> validatorClass) {
      DataManager<T> mgr = get(dataClass);
      if (mgr != null && namespace != null && !namespace.isBlank() && validatorClass != null) {
         mgr.namespaceValidatorClasses.put(namespace, validatorClass);
      }
   }

   public static <T> DataManager<T> register(Class<T> dataClass) {
      if (!dataClass.isAnnotationPresent(DataDriven.class)) {
         throw new IllegalArgumentException("Class " + dataClass.getSimpleName() + " must be annotated with @DataDriven");
      } else {
         return (DataManager<T>)managers.computeIfAbsent(dataClass, DataManager::new);
      }
   }

   public static <T> void registerNamespace(Class<T> dataClass, String namespace) {
      runtimeRegisteredNamespaces.computeIfAbsent(dataClass, k -> ConcurrentHashMap.newKeySet()).add(namespace);
   }

   public static <T> DataManager<T> get(Class<T> dataClass) {
      return (DataManager<T>)managers.get(dataClass);
   }

   private static String getFolder(Class<?> dataClass) {
      DataDriven annotation = dataClass.getAnnotation(DataDriven.class);
      return annotation.folder();
   }

   public static MinecraftServer getCurrentServer() {
      return ServerLifecycleHooks.getCurrentServer();
   }

   @SubscribeEvent
   public static void onServerStarted(ServerStartedEvent event) {
      serverStarted = true;
   }

   @SubscribeEvent
   public static void onPlayerJoin(PlayerLoggedInEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         for (DataManager<?> manager : managers.values()) {
            if (manager.annotation.syncToClient()) {
               manager.syncToPlayer(player);
            }
         }
      }
   }

   public Map<ResourceLocation, T> getAllData() {
      return new HashMap<>(this.loadedData);
   }

   public T getData(ResourceLocation location) {
      return this.loadedData.get(location);
   }

   public List<T> getDataList() {
      List<T> result = new ArrayList<>(this.loadedData.values());
      result.addAll(this.deferredData.values());
      return result;
   }

   public Set<T> getCachedData(String cacheKey) {
      if (!this.annotation.enableCache()) {
         throw new UnsupportedOperationException("Cache is disabled for " + this.dataClass.getSimpleName());
      } else {
         return this.cache.getOrDefault(cacheKey, Collections.emptySet());
      }
   }

   public void addToCache(String cacheKey, T data) {
      if (this.annotation.enableCache()) {
         this.cache.computeIfAbsent(cacheKey, k -> ConcurrentHashMap.newKeySet()).add(data);
      }
   }

   public void clearCache() {
      this.cache.clear();
   }

   public void updateClientData(Map<ResourceLocation, T> data) {
      this.loadedData.clear();
      this.loadedData.putAll(data);
      this.clearCache();
      if (this.annotation.enableCache()) {
         for (Entry<ResourceLocation, T> entry : data.entrySet()) {
            this.buildCache(entry.getValue());
         }
      }

      OELib.LOGGER.debug("Updated client data for {}: {} entries", this.dataClass.getSimpleName(), data.size());
      NeoForge.EVENT_BUS.post(new DataReloadEvent(this.dataClass, data.size(), 0));
   }

   public void syncToPlayer(ServerPlayer player) {
      if (this.annotation.syncToClient() && (!this.loadedData.isEmpty() || !this.deferredData.isEmpty())) {
         try {
            Map<ResourceLocation, T> allData = new HashMap<>(this.loadedData);
            allData.putAll(this.deferredData);
            DataSyncPacket<T> packet = new DataSyncPacket<>(this.dataClass, allData);
            packet.sendTo(player);
            OELib.LOGGER.debug("Synced {} data to player: {}", this.dataClass.getSimpleName(), player.getName().getString());
         } catch (Exception e) {
            OELib.LOGGER.error("Failed to sync {} data to player {}", new Object[]{this.dataClass.getSimpleName(), player.getName().getString(), e});
         }
      }
   }

   protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler) {
      this.loadedData.clear();
      this.deferredData.clear();
      this.clearCache();
      Codec<T> currentCodec = CodecUtils.getCodec(this.dataClass);
      Set<String> allowNamespaces = new HashSet<>();
      if (!this.annotation.modid().isEmpty()) {
         allowNamespaces.add(this.annotation.modid());
      }

      Set<String> runtimeSet = runtimeRegisteredNamespaces.getOrDefault(this.dataClass, Collections.emptySet());
      allowNamespaces.addAll(runtimeSet);
      Map<ResourceLocation, JsonElement> filteredObject = object.entrySet()
         .stream()
         .filter(entryx -> allowNamespaces.isEmpty() || allowNamespaces.contains(((ResourceLocation)entryx.getKey()).getNamespace()))
         .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
      OELib.LOGGER.info("Loading {} data from {} files (namespaces: {})", new Object[]{this.dataClass.getSimpleName(), filteredObject.size(), allowNamespaces});
      int validCount = 0;
      int deferredCount = 0;
      int invalidCount = 0;

      for (Entry<ResourceLocation, JsonElement> entry : filteredObject.entrySet()) {
         ResourceLocation location = entry.getKey();
         JsonElement json = entry.getValue();

         try {
            if (this.annotation.supportArray() && json.isJsonArray()) {
               JsonArray jsonArray = json.getAsJsonArray();

               for (int i = 0; i < jsonArray.size(); i++) {
                  JsonElement element = jsonArray.get(i);
                  ResourceLocation elementLocation = ResourceLocation.fromNamespaceAndPath(location.getNamespace(), location.getPath() + "_" + i);
                  DataResult<T> result = currentCodec.parse(JsonOps.INSTANCE, element);
                  if (result.result().isPresent()) {
                     T data = (T)result.result().get();
                     DataValidator<T> v = this.getValidatorForNamespace(location.getNamespace());
                     DataValidator.ValidationResult vr = this.validateData(data, elementLocation);
                     if (vr.valid()) {
                        if (vr.deferrable()) {
                           this.deferredData.put(elementLocation, data);
                           deferredCount++;
                        } else {
                           this.loadedData.put(elementLocation, data);
                           if (this.annotation.enableCache()) {
                              this.buildCache(data);
                           }

                           validCount++;
                        }
                     } else {
                        invalidCount++;
                        OELib.LOGGER.warn("Invalid {} data in array[{}] of {}: {}", new Object[]{this.dataClass.getSimpleName(), i, location, vr.message()});
                     }
                  } else {
                     invalidCount++;
                     OELib.LOGGER
                        .error(
                           "Failed to parse {} data from array[{}] of {}: {}",
                           new Object[]{this.dataClass.getSimpleName(), i, location, result.error().orElse(null)}
                        );
                  }
               }
            } else {
               DataResult<T> result = currentCodec.parse(JsonOps.INSTANCE, json);
               if (result.result().isPresent()) {
                  T data = (T)result.result().get();
                  DataValidator<T> v = this.getValidatorForNamespace(location.getNamespace());
                  DataValidator.ValidationResult vr = this.validateData(data, location);
                  if (vr.valid()) {
                     if (vr.deferrable()) {
                        this.deferredData.put(location, data);
                        deferredCount++;
                     } else {
                        this.loadedData.put(location, data);
                        if (this.annotation.enableCache()) {
                           this.buildCache(data);
                        }

                        validCount++;
                     }
                  } else {
                     invalidCount++;
                     OELib.LOGGER.warn("Invalid {} data in {}: {}", new Object[]{this.dataClass.getSimpleName(), location, vr.message()});
                  }
               } else {
                  invalidCount++;
                  OELib.LOGGER
                     .error("Failed to parse {} data from {}: {}", new Object[]{this.dataClass.getSimpleName(), location, result.error().orElse(null)});
               }
            }
         } catch (Exception e) {
            invalidCount++;
            OELib.LOGGER.error("Error loading {} data from {}", new Object[]{this.dataClass.getSimpleName(), location, e});
         }
      }

      OELib.LOGGER
         .info(
            "Loaded {} valid {} entries, {} deferred entries, {} invalid entries were skipped",
            new Object[]{validCount, this.dataClass.getSimpleName(), deferredCount, invalidCount}
         );
      if (this.annotation.syncToClient() && serverStarted && getCurrentServer() != null) {
         this.syncToAllPlayers();
      }

      NeoForge.EVENT_BUS.post(new DataReloadEvent(this.dataClass, validCount, invalidCount));
   }

   private DataValidator.ValidationResult validateData(T data, ResourceLocation source) {
      DataValidator<T> vr = this.getValidatorForNamespace(source.getNamespace());
      return vr instanceof DataValidator.ServerContextAware<T> contextAwareValidator
         ? contextAwareValidator.validateWithContext(data, source, getCurrentServer())
         : vr.validate(data, source);
   }

   protected void buildCache(T data) {
      this.addToCache("all", data);
   }

   private DataValidator<T> getValidatorForNamespace(String namespace) {
      return namespace == null ? this.defaultValidator : this.namespaceValidators.computeIfAbsent(namespace, ns -> {
         Class<? extends DataValidator<?>> cls = this.namespaceValidatorClasses.get(ns);
         if (cls == null) {
            return this.defaultValidator;
         }

         try {
            return (DataValidator<T>)cls.getDeclaredConstructor().newInstance();
         } catch (Exception e) {
            OELib.LOGGER.warn("Failed to instantiate validator for namespace '{}', fallback to default", ns, e);
            return this.defaultValidator;
         }
      });
   }

   private void syncToAllPlayers() {
      try {
         if (getCurrentServer() != null && (!this.loadedData.isEmpty() || !this.deferredData.isEmpty())) {
            Map<ResourceLocation, T> allData = new HashMap<>(this.loadedData);
            allData.putAll(this.deferredData);
            DataSyncPacket<T> packet = new DataSyncPacket<>(this.dataClass, allData);
            packet.sendToAll();
            OELib.LOGGER.debug("Synced {} data to all players", this.dataClass.getSimpleName());
         }
      } catch (Exception e) {
         OELib.LOGGER.error("Failed to sync {} data to all players", this.dataClass.getSimpleName(), e);
      }
   }

   private DataValidator<T> createValidator(Class<? extends DataValidator<?>> validatorClass) {
      if (validatorClass == DataValidator.NoValidator.class) {
         return new DataValidator.NoValidator();
      }

      try {
         return (DataValidator<T>)validatorClass.getDeclaredConstructor().newInstance();
      } catch (Exception e) {
         OELib.LOGGER.warn("Failed to create validator {}, using no validator", validatorClass.getSimpleName(), e);
         return new DataValidator.NoValidator();
      }
   }
}
