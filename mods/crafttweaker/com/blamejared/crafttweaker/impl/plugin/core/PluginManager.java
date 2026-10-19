package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.plugin.CraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.ICraftTweakerPlugin;
import com.blamejared.crafttweaker.api.util.ClassUtil;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptRunConfiguration;
import com.blamejared.crafttweaker.impl.registry.CraftTweakerRegistry;
import com.mojang.datafixers.util.Pair;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.ResourceLocationException;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.Logger;

public final class PluginManager {
   private static final Logger LOGGER = CraftTweakerAPI.getLogger("CraftTweaker-Plugins");
   private final List<DecoratedCraftTweakerPlugin> plugins;
   private final PluginManager.Req req;
   private final PluginManager.Listeners listeners;

   private PluginManager(List<DecoratedCraftTweakerPlugin> plugins) {
      this.plugins = List.copyOf(plugins);
      this.req = new PluginManager.Req();
      this.listeners = new PluginManager.Listeners();
      this.performInitializationPass();
   }

   public static PluginManager of() {
      return new PluginManager(discoverPlugins());
   }

   private static List<DecoratedCraftTweakerPlugin> discoverPlugins() {
      return ClassUtil.findClassesWithAnnotation(CraftTweakerPlugin.class)
         .map(PluginManager::checkAndCast)
         .map(PluginManager::initPlugin)
         .filter(Objects::nonNull)
         .toList();
   }

   private static Pair<ResourceLocation, Class<? extends ICraftTweakerPlugin>> checkAndCast(Class<?> clazz) {
      if (!ICraftTweakerPlugin.class.isAssignableFrom(clazz)) {
         throw new IllegalArgumentException("Invalid plugin class annotated with @CraftTweakerPlugin: it must implement ICraftTweakerPlugin");
      }

      ResourceLocation id;
      try {
         String targetId = Objects.requireNonNull(clazz.getAnnotation(CraftTweakerPlugin.class)).value();
         id = ResourceLocation.parse(targetId);
         if (id.getNamespace().equals("minecraft")) {
            throw new ResourceLocationException("Illegal namespace 'minecraft'");
         }
      } catch (ResourceLocationException e) {
         throw new IllegalArgumentException("Invalid plugin class ID: not a valid resource location", e);
      }

      return Pair.of(id, clazz);
   }

   private static DecoratedCraftTweakerPlugin initPlugin(Pair<ResourceLocation, Class<? extends ICraftTweakerPlugin>> pluginData) {
      try {
         ResourceLocation id = (ResourceLocation)pluginData.getFirst();
         ICraftTweakerPlugin plugin = (ICraftTweakerPlugin)((Class)pluginData.getSecond()).getConstructor().newInstance();
         LOGGER.info("Successfully identified and loaded plugin {}", id);
         return new DecoratedCraftTweakerPlugin(id, plugin);
      } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
         LOGGER.error("Unable to load plugin class '" + ((Class)pluginData.getSecond()).getName() + "' due to an error", e);
         return null;
      }
   }

   public void loadPlugins() {
      IPluginRegistryAccess pluginRegistryAccess = CraftTweakerRegistry.pluginAccess(this.req);
      this.gatherListeners();
      this.handleZenDataRegistration(pluginRegistryAccess);
      this.applyInheritanceRules(pluginRegistryAccess);
      this.handleAdditionalRegistration(pluginRegistryAccess);
      this.verifyProperRegistration(pluginRegistryAccess);
   }

   public void broadcastSetupEnd() {
      this.callListeners("initialization end", this.listeners.endListeners());
   }

   public void broadcastRunExecution(ScriptRunConfiguration configuration) {
      this.callListeners("run execution", this.listeners.executeRunListeners(), configuration);
   }

   private void performInitializationPass() {
      this.verifying("initializing plugins", () -> this.plugins.forEach(ICraftTweakerPlugin::initialize));
   }

   private void gatherListeners() {
      ListenerRegistrationHandler handler = this.verifying(
         "gathering listeners", () -> ListenerRegistrationHandler.of(this.onEach(ICraftTweakerPlugin::registerListeners))
      );
      this.listeners.endListeners().addAll(handler.endListeners());
      this.listeners.zenListeners().addAll(handler.zenListeners());
      this.listeners.executeRunListeners().addAll(handler.executeRunListeners());
   }

   private void handleZenDataRegistration(IPluginRegistryAccess access) {
      Map<String, IScriptLoader> loaders = this.verifying(
         "registering loaders", () -> LoaderRegistrationHandler.gather(this.onEach(ICraftTweakerPlugin::registerLoaders))
      );
      Function<String, IScriptLoader> loaderFinder = name -> {
         if (loaders.containsKey(name)) {
            return loaders.get(name);
         } else {
            throw new IllegalArgumentException("Unknown loader '" + name + "' queried: missing registration?");
         }
      };
      access.registerLoaders(loaders.values());
      access.registerLoadSources(
         this.verifying("registering load sources", () -> LoadSourceRegistrationHandler.gather(this.onEach(ICraftTweakerPlugin::registerLoadSource)))
      );
      this.verifying(
         "registering script run module configurations",
         () -> ScriptRunModuleConfiguratorRegistrationHandler.gather(this.onEach(ICraftTweakerPlugin::registerModuleConfigurators))
            .forEach(it -> access.registerRunModuleConfigurator(loaderFinder.apply(it.getKey()), it.getValue()))
      );
      JavaNativeIntegrationRegistrationHandler javaHandler = this.verifying(
         "gathering ZenCode integration data", () -> JavaNativeIntegrationRegistrationHandler.of(this.onEach(ICraftTweakerPlugin::manageJavaNativeIntegration))
      );
      this.manageZenRegistration(access, javaHandler, loaderFinder);
      BracketParserRegistrationHandler bracketHandler = this.verifying(
         "gathering BEP data", () -> BracketParserRegistrationHandler.of(this.onEach(ICraftTweakerPlugin::registerBracketParsers))
      );
      this.manageBracketRegistration(access, bracketHandler, loaderFinder);
      TaggableElementsRegistrationHandler taggableElementsHandler = this.verifying(
         "gathering taggable elements", () -> TaggableElementsRegistrationHandler.of(this.onEach(ICraftTweakerPlugin::registerTaggableElements))
      );
      this.manageTaggableElementRegistration(access, taggableElementsHandler);
      this.callListeners("ZenCode registration end", this.listeners.zenListeners());
   }

   private void applyInheritanceRules(IPluginRegistryAccess access) {
      this.verifying("applying inheritance rules", access::applyInheritanceRules);
   }

   private void verifyProperRegistration(IPluginRegistryAccess access) {
      this.verifying("verifying correct registration", access::verifyProperRegistration);
   }

   private void handleAdditionalRegistration(IPluginRegistryAccess access) {
      this.verifying(
         "registering recipe components",
         () -> access.registerComponents(RecipeComponentRegistrationHandler.gather(this.onEach(ICraftTweakerPlugin::registerRecipeComponents)))
      );
      this.verifying(
         "registering recipe handlers",
         () -> RecipeHandlerRegistrationHandler.gather(this.onEach(ICraftTweakerPlugin::registerRecipeHandlers))
            .forEach(it -> access.registerHandler(GenericUtil.uncheck(it.recipeClass()), it.handler()))
      );
      this.verifying(
         "registering villager trade converters",
         () -> VillagerTradeConverterRegistrationHandler.gather(this.onEach(ICraftTweakerPlugin::registerVillagerTradeConverters))
      );
      this.verifying("registering commands", () -> CommandRegistrationHandler.gather(this.onEach(ICraftTweakerPlugin::registerCommands)));
      this.verifying(
         "registering recipe replacement components",
         () -> this.manageReplacerRegistration(access, ReplacerComponentsRegistrationHandler.of(this.onEach(ICraftTweakerPlugin::registerReplacerComponents)))
      );
      this.verifying(
         "registering events",
         () -> EventRegistrationHandler.gather(this.onEach(ICraftTweakerPlugin::registerEvents))
            .forEach((token, bus) -> access.registerEventBusMapping(GenericUtil.uncheck(token), bus))
      );
   }

   private void manageZenRegistration(
      IPluginRegistryAccess access, JavaNativeIntegrationRegistrationHandler handler, Function<String, IScriptLoader> loaderGetter
   ) {
      this.verifying("registering preprocessors", () -> handler.preprocessors().forEach(access::registerPreprocessor));
      this.verifying(
         "registering native types", () -> handler.nativeClassRequests().forEach(r -> access.registerNativeType(loaderGetter.apply(r.loader()), r.info()))
      );
      this.verifying(
         "registering Zen types",
         () -> handler.zenClassRequests()
            .object2BooleanEntrySet()
            .stream()
            .sorted(Comparator.comparing(it -> ((JavaNativeIntegrationRegistrationHandler.ZenClassRequest)it.getKey()).info().kind()))
            .forEach(
               entry -> access.registerZenType(
                  loaderGetter.apply(((JavaNativeIntegrationRegistrationHandler.ZenClassRequest)entry.getKey()).loader()),
                  ((JavaNativeIntegrationRegistrationHandler.ZenClassRequest)entry.getKey()).clazz(),
                  ((JavaNativeIntegrationRegistrationHandler.ZenClassRequest)entry.getKey()).info(),
                  entry.getBooleanValue()
               )
            )
      );
   }

   private void manageTaggableElementRegistration(IPluginRegistryAccess access, TaggableElementsRegistrationHandler handler) {
      this.verifying(
         "registering taggable elements",
         () -> handler.elementRequests().forEach(it -> access.registerTaggableElement(it.key(), GenericUtil.uncheck(it.elementClass())))
      );
      this.verifying(
         "registering taggable element managers",
         () -> handler.managerRequests().forEach(it -> access.registerTaggableElementManager(it.key(), GenericUtil.uncheck(it.factory())))
      );
   }

   private void manageBracketRegistration(IPluginRegistryAccess access, BracketParserRegistrationHandler handler, Function<String, IScriptLoader> loaderFinder) {
      this.verifying(
         "registering BEPs",
         () -> handler.bracketRequests()
            .forEach(it -> access.registerBracket(loaderFinder.apply(it.loader()), it.parserName(), it.parser(), it.parserDumper()))
      );
      this.verifying(
         "registering enum brackets",
         () -> handler.enumRequests().forEach(it -> access.registerEnum(loaderFinder.apply(it.loader()), it.id(), GenericUtil.uncheck(it.enumClass())))
      );
   }

   private void manageReplacerRegistration(IPluginRegistryAccess access, ReplacerComponentsRegistrationHandler handler) {
      access.registerTargetingFilters(handler.filters());
      handler.strategies().forEach(access::registerTargetingStrategy);
   }

   private void verifying(String what, Runnable block) {
      this.verifying(what, () -> {
         block.run();
         return null;
      });
   }

   private <T> T verifying(String what, Supplier<T> block) {
      try {
         return block.get();
      } catch (Throwable t) {
         throw new IllegalStateException("An error occurred while " + what, t);
      }
   }

   private <T> Consumer<T> onEach(BiConsumer<ICraftTweakerPlugin, T> consumer) {
      return handler -> this.plugins.forEach(plugin -> {
         try {
            consumer.accept(plugin, (T)handler);
         } catch (Exception e) {
            throw new IllegalStateException("Plugin " + plugin.id() + " failed to initialize", e);
         }
      });
   }

   private void callListeners(String type, Collection<Runnable> listeners) {
      listeners.forEach(it -> this.verifying("calling " + type + " listener", it));
   }

   private <T> void callListeners(String type, Collection<Consumer<T>> listeners, T instance) {
      listeners.forEach(it -> this.verifying("calling " + type + " listener with " + instance, () -> it.accept(instance)));
   }

   private record Listeners(List<Runnable> zenListeners, List<Runnable> endListeners, List<Consumer<ScriptRunConfiguration>> executeRunListeners) {
      Listeners() {
         this(new ArrayList<>(), new ArrayList<>(), new LinkedList<>());
      }
   }

   public static final class Req {
      private static final AtomicBoolean X = new AtomicBoolean(false);

      private Req() {
         if (X.get()) {
            throw new RuntimeException("Unable to make multiple instances of PluginManager!");
         }

         X.set(true);
      }
   }
}
