package com.blamejared.crafttweaker.impl.registry;

import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.natives.NativeTypeInfo;
import com.blamejared.crafttweaker.api.plugin.IBracketParserRegistrationHandler;
import com.blamejared.crafttweaker.api.recipe.component.IRecipeComponent;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.replacement.ITargetingFilter;
import com.blamejared.crafttweaker.api.recipe.replacement.ITargetingStrategy;
import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.api.tag.manager.TagManagerFactory;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.ZenTypeInfo;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunModuleConfigurator;
import com.blamejared.crafttweaker.impl.plugin.core.IPluginRegistryAccess;
import com.google.common.reflect.TypeToken;
import java.util.Collection;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import org.openzen.zenscript.parser.BracketExpressionParser;

final class PluginRegistryAccess implements IPluginRegistryAccess {
   private final Registries registries;

   PluginRegistryAccess(Registries registries) {
      this.registries = registries;
   }

   @Override
   public void registerLoaders(Collection<IScriptLoader> loader) {
      this.registries.loaderRegistry().registerLoaders(loader);
      this.registries.bracketResolverRegistry().fillLoaderData(loader);
      this.registries.enumBracketRegistry().fillLoaderData(loader);
      this.registries.zenClassRegistry().fillLoaderData(loader);
   }

   @Override
   public void registerLoadSources(Collection<IScriptLoadSource> sources) {
      this.registries.loadSourceRegistry().registerLoadSources(sources);
   }

   @Override
   public void registerRunModuleConfigurator(IScriptLoader loader, IScriptRunModuleConfigurator configurator) {
      this.registries.scriptRunModuleConfiguratorRegistry().register(loader, configurator);
   }

   @Override
   public void registerPreprocessor(IPreprocessor preprocessor) {
      this.registries.preprocessorRegistry().register(preprocessor);
   }

   @Override
   public void registerNativeType(IScriptLoader loader, NativeTypeInfo info) {
      this.registries.zenClassRegistry().registerNativeType(loader, info);
   }

   @Override
   public void registerZenType(IScriptLoader loader, Class<?> clazz, ZenTypeInfo info, boolean globals) {
      this.registries.zenClassRegistry().registerZenType(loader, clazz, info, globals);
   }

   @Override
   public void registerBracket(
      IScriptLoader loader, String name, BracketExpressionParser bracketParser, IBracketParserRegistrationHandler.DumperData dumperData
   ) {
      this.registries.bracketResolverRegistry().registerBracket(loader, name, bracketParser, dumperData);
   }

   @Override
   public <T extends Enum<T>> void registerEnum(IScriptLoader loader, ResourceLocation id, Class<T> enumClass) {
      this.registries.enumBracketRegistry().register(loader, id, enumClass);
   }

   @Override
   public <T> void registerTaggableElement(ResourceKey<T> key, Class<T> elementClass) {
      this.registries.taggableElementRegistry().registerElement(key, elementClass);
   }

   @Override
   public <T, U extends ITagManager<?>> void registerTaggableElementManager(ResourceKey<T> key, TagManagerFactory<T, U> factory) {
      this.registries.taggableElementRegistry().registerManager(key, factory);
   }

   @Override
   public void registerComponents(Collection<IRecipeComponent<?>> components) {
      this.registries.recipeComponentRegistry().registerComponents(components);
   }

   @Override
   public <T> void registerEventBusMapping(TypeToken<T> token, IEventBus<T> bus) {
      this.registries.eventRegistry().registerBusMapping(token, bus);
   }

   @Override
   public <T extends Recipe<?>> void registerHandler(Class<? extends T> clazz, IRecipeHandler<T> handler) {
      this.registries.recipeHandlerRegistry().register(clazz, handler);
   }

   @Override
   public void registerTargetingFilters(Collection<ITargetingFilter> filters) {
      this.registries.replacerRegistry().castedFilters(filters);
   }

   @Override
   public void registerTargetingStrategy(ResourceLocation id, ITargetingStrategy strategy) {
      this.registries.replacerRegistry().strategy(id, strategy);
   }

   @Override
   public void applyInheritanceRules() {
      this.registries.bracketResolverRegistry().applyInheritanceRules();
      this.registries.enumBracketRegistry().applyInheritanceRules();
      this.registries.zenClassRegistry().applyInheritanceRules();
   }

   @Override
   public void verifyProperRegistration() {
      this.registries.scriptRunModuleConfiguratorRegistry().verify(this.registries.loaderRegistry().getAllLoaders());
   }
}
