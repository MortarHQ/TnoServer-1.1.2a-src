package com.blamejared.crafttweaker.impl.registry;

import com.blamejared.crafttweaker.api.ICraftTweakerRegistry;
import com.blamejared.crafttweaker.api.command.type.IBracketDumperInfo;
import com.blamejared.crafttweaker.api.event.IEventRegistry;
import com.blamejared.crafttweaker.api.recipe.component.IRecipeComponent;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.replacement.IReplacerRegistry;
import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.api.tag.manager.TagManagerFactory;
import com.blamejared.crafttweaker.api.tag.manager.type.KnownTagManager;
import com.blamejared.crafttweaker.api.tag.manager.type.UnknownTagManager;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.IZenClassRegistry;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunModuleConfigurator;
import com.blamejared.crafttweaker.impl.plugin.core.IPluginRegistryAccess;
import com.blamejared.crafttweaker.impl.plugin.core.PluginManager;
import com.blamejared.crafttweaker.impl.recipe.replacement.ReplacerRegistry;
import com.blamejared.crafttweaker.impl.registry.recipe.RecipeComponentRegistry;
import com.blamejared.crafttweaker.impl.registry.recipe.RecipeHandlerRegistry;
import com.blamejared.crafttweaker.impl.registry.zencode.BracketResolverRegistry;
import com.blamejared.crafttweaker.impl.registry.zencode.EnumBracketRegistry;
import com.blamejared.crafttweaker.impl.registry.zencode.PreprocessorRegistry;
import com.blamejared.crafttweaker.impl.registry.zencode.TaggableElementRegistry;
import com.blamejared.crafttweaker.impl.registry.zencode.ZenClassRegistry;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import org.openzen.zenscript.parser.BracketExpressionParser;

public final class CraftTweakerRegistry implements ICraftTweakerRegistry {
   private static final Supplier<CraftTweakerRegistry> REGISTRY = Suppliers.memoize(CraftTweakerRegistry::new);
   private final Registries registries = new Registries(
      new BracketResolverRegistry(),
      new EnumBracketRegistry(),
      new EventRegistry(),
      new LoaderRegistry(),
      new LoadSourceRegistry(),
      new PreprocessorRegistry(),
      new RecipeComponentRegistry(),
      new RecipeHandlerRegistry(),
      new ReplacerRegistry(),
      new ScriptRunModuleConfiguratorRegistry(),
      new TaggableElementRegistry(),
      new ZenClassRegistry()
   );
   private final IPluginRegistryAccess access = new PluginRegistryAccess(this.registries);

   private CraftTweakerRegistry() {
   }

   public static ICraftTweakerRegistry get() {
      return REGISTRY.get();
   }

   public static IPluginRegistryAccess pluginAccess(PluginManager.Req req) {
      Objects.requireNonNull(req);
      return REGISTRY.get().access;
   }

   @Override
   public IScriptLoader findLoader(String name) {
      return this.registries.loaderRegistry().find(name);
   }

   @Override
   public Collection<IScriptLoader> getAllLoaders() {
      return this.registries.loaderRegistry().getAllLoaders();
   }

   @Override
   public IScriptLoadSource findLoadSource(ResourceLocation id) {
      return this.registries.loadSourceRegistry().get(id);
   }

   @Override
   public IZenClassRegistry getZenClassRegistry() {
      return this.registries.zenClassRegistry();
   }

   @Override
   public IScriptRunModuleConfigurator getConfiguratorFor(IScriptLoader loader) {
      return this.registries.scriptRunModuleConfiguratorRegistry().find(loader);
   }

   @Override
   public Map<String, IBracketDumperInfo> getBracketDumpers(IScriptLoader loader) {
      return ImmutableMap.copyOf(this.registries.bracketResolverRegistry().getBracketDumpers(loader));
   }

   @Override
   public Map<String, BracketExpressionParser> getBracketHandlers(IScriptLoader loader, String rootPackage) {
      return ImmutableMap.copyOf(this.registries.bracketResolverRegistry().getBracketResolvers(loader));
   }

   @Override
   public List<IPreprocessor> getPreprocessors() {
      return ImmutableList.copyOf(this.registries.preprocessorRegistry().getPreprocessors());
   }

   @Override
   public <T extends Recipe<?>> IRecipeHandler<T> getRecipeHandlerFor(T recipe) {
      return this.registries.recipeHandlerRegistry().getRecipeHandlerFor(recipe);
   }

   @Override
   public <T extends Recipe<?>> IRecipeHandler<T> getRecipeHandlerFor(Class<T> recipeClazz) {
      return this.registries.recipeHandlerRegistry().getRecipeHandlerFor(recipeClazz);
   }

   @Override
   public <T> IRecipeComponent<T> findRecipeComponent(ResourceLocation id) {
      return this.registries.recipeComponentRegistry().find(id);
   }

   @Override
   public Collection<IRecipeComponent<?>> getAllRecipeComponents() {
      return this.registries.recipeComponentRegistry().allComponents();
   }

   @Override
   public <T extends Enum<T>> T getEnumBracketValue(IScriptLoader loader, ResourceLocation type, String value) {
      Class<T> clazz = this.<T>getEnumBracketFor(loader, type)
         .orElseThrow(() -> new IllegalArgumentException(String.format("No enum found for type '%s'", type)));
      return Enum.valueOf(clazz, value.toUpperCase(Locale.ENGLISH));
   }

   @Override
   public <T extends Enum<T>> Optional<Class<T>> getEnumBracketFor(IScriptLoader loader, ResourceLocation type) {
      return this.registries.enumBracketRegistry().getEnum(loader, type);
   }

   @Override
   public <T> Optional<Class<T>> getTaggableElementFor(ResourceKey<T> key) {
      return this.registries.taggableElementRegistry().getTaggableElement(key);
   }

   @Override
   public <T> TagManagerFactory<T, ? extends ITagManager<?>> getTaggableElementFactory(ResourceKey<Registry<T>> key) {
      return this.registries.taggableElementRegistry().getTaggableElement(key).isPresent()
         ? this.registries.taggableElementRegistry().<T>getManagerFactory(key).orElseGet(() -> KnownTagManager::new)
         : (resourceKey1, tClass) -> new UnknownTagManager(resourceKey1);
   }

   @Override
   public Set<String> getAllEnumStringsForEnumBracket(IScriptLoader loader) {
      return this.registries
         .enumBracketRegistry()
         .getEnums(loader)
         .entrySet()
         .stream()
         .flatMap(it -> Arrays.stream(it.getValue().getEnumConstants()).map(c -> Pair.of(it.getKey(), c)))
         .map(it -> String.format("<constant:%s:%s>", it.getFirst(), ((Enum)it.getSecond()).name().toLowerCase(Locale.ENGLISH)))
         .collect(Collectors.toUnmodifiableSet());
   }

   @Override
   public IReplacerRegistry getReplacerRegistry() {
      return this.registries.replacerRegistry();
   }

   @Override
   public IEventRegistry getEventRegistry() {
      return this.registries.eventRegistry();
   }
}
