package com.blamejared.crafttweaker.api;

import com.blamejared.crafttweaker.api.command.type.IBracketDumperInfo;
import com.blamejared.crafttweaker.api.event.IEventRegistry;
import com.blamejared.crafttweaker.api.recipe.component.IRecipeComponent;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.replacement.IReplacerRegistry;
import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.api.tag.manager.TagManagerFactory;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.IZenClassRegistry;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunModuleConfigurator;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.openzen.zenscript.parser.BracketExpressionParser;

public interface ICraftTweakerRegistry {
   IScriptLoader findLoader(String var1);

   Collection<IScriptLoader> getAllLoaders();

   IScriptLoadSource findLoadSource(ResourceLocation var1);

   IZenClassRegistry getZenClassRegistry();

   IScriptRunModuleConfigurator getConfiguratorFor(IScriptLoader var1);

   Map<String, IBracketDumperInfo> getBracketDumpers(IScriptLoader var1);

   Map<String, BracketExpressionParser> getBracketHandlers(IScriptLoader var1, String var2);

   List<IPreprocessor> getPreprocessors();

   <T extends Recipe<?>> IRecipeHandler<T> getRecipeHandlerFor(T var1);

   default <T extends Recipe<?>> IRecipeHandler<T> getRecipeHandlerFor(RecipeHolder<T> holder) {
      return this.getRecipeHandlerFor((T)holder.value());
   }

   <T extends Recipe<?>> IRecipeHandler<T> getRecipeHandlerFor(Class<T> var1);

   <T> IRecipeComponent<T> findRecipeComponent(ResourceLocation var1);

   Collection<IRecipeComponent<?>> getAllRecipeComponents();

   <T extends Enum<T>> T getEnumBracketValue(IScriptLoader var1, ResourceLocation var2, String var3);

   <T extends Enum<T>> Optional<Class<T>> getEnumBracketFor(IScriptLoader var1, ResourceLocation var2);

   <T> Optional<Class<T>> getTaggableElementFor(ResourceKey<T> var1);

   <T> TagManagerFactory<T, ? extends ITagManager<?>> getTaggableElementFactory(ResourceKey<Registry<T>> var1);

   Set<String> getAllEnumStringsForEnumBracket(IScriptLoader var1);

   IReplacerRegistry getReplacerRegistry();

   IEventRegistry getEventRegistry();
}
