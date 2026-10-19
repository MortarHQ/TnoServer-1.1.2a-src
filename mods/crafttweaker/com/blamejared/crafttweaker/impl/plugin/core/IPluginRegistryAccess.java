package com.blamejared.crafttweaker.impl.plugin.core;

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
import com.google.common.reflect.TypeToken;
import java.util.Collection;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import org.openzen.zenscript.parser.BracketExpressionParser;

public interface IPluginRegistryAccess {
   void registerLoaders(Collection<IScriptLoader> var1);

   void registerLoadSources(Collection<IScriptLoadSource> var1);

   void registerRunModuleConfigurator(IScriptLoader var1, IScriptRunModuleConfigurator var2);

   void registerPreprocessor(IPreprocessor var1);

   void registerNativeType(IScriptLoader var1, NativeTypeInfo var2);

   void registerZenType(IScriptLoader var1, Class<?> var2, ZenTypeInfo var3, boolean var4);

   void registerBracket(IScriptLoader var1, String var2, BracketExpressionParser var3, IBracketParserRegistrationHandler.DumperData var4);

   <T extends Enum<T>> void registerEnum(IScriptLoader var1, ResourceLocation var2, Class<T> var3);

   <T> void registerTaggableElement(ResourceKey<T> var1, Class<T> var2);

   <T, U extends ITagManager<?>> void registerTaggableElementManager(ResourceKey<T> var1, TagManagerFactory<T, U> var2);

   void registerComponents(Collection<IRecipeComponent<?>> var1);

   <T> void registerEventBusMapping(TypeToken<T> var1, IEventBus<T> var2);

   <T extends Recipe<?>> void registerHandler(Class<? extends T> var1, IRecipeHandler<T> var2);

   void registerTargetingFilters(Collection<ITargetingFilter> var1);

   void registerTargetingStrategy(ResourceLocation var1, ITargetingStrategy var2);

   void applyInheritanceRules();

   void verifyProperRegistration();
}
