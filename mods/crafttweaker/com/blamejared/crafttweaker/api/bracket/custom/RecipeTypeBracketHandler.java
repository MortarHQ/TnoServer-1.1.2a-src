package com.blamejared.crafttweaker.api.bracket.custom;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.ICraftTweakerRegistry;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.logging.CommonLoggers;
import com.blamejared.crafttweaker.api.recipe.manager.RecipeManagerWrapper;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.api.util.InstantiationUtil;
import com.blamejared.crafttweaker.api.util.ParseUtil;
import com.blamejared.crafttweaker.impl.script.ScriptRecipeType;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.lexer.ZSTokenParser;
import org.openzen.zenscript.parser.BracketExpressionParser;
import org.openzen.zenscript.parser.expression.ParsedCallArguments;
import org.openzen.zenscript.parser.expression.ParsedExpression;
import org.openzen.zenscript.parser.expression.ParsedExpressionCall;
import org.openzen.zenscript.parser.expression.ParsedExpressionCast;
import org.openzen.zenscript.parser.expression.ParsedExpressionMember;
import org.openzen.zenscript.parser.expression.ParsedExpressionString;
import org.openzen.zenscript.parser.expression.ParsedExpressionVariable;
import org.openzen.zenscript.parser.type.IParsedType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.bracket.RecipeTypeBracketHandler")
public class RecipeTypeBracketHandler implements BracketExpressionParser {
   private static final Set<Supplier<RecipeType<?>>> FILTERED_TYPES = ImmutableSet.of(Suppliers.memoize(() -> ScriptRecipeType.INSTANCE));
   private static final Object LOCK = new Object();
   private static final Map<RecipeType<Recipe<?>>, IRecipeManager<Recipe<?>>> registeredTypes = new HashMap<>();
   private static final Map<Class<? extends IRecipeManager<Recipe<? extends RecipeInput>>>, IRecipeManager<Recipe<?>>> managerInstances = new HashMap<>();
   private static volatile Runnable enqueuedRegistration = null;

   public RecipeTypeBracketHandler() {
      enqueuedRegistration = () -> {
         ICraftTweakerRegistry registry = CraftTweakerAPI.getRegistry();
         registeredTypes.clear();
         registry.getAllLoaders()
            .stream()
            .map(it -> CraftTweakerAPI.getRegistry().getZenClassRegistry().getImplementationsOf(it, IRecipeManager.class))
            .flatMap(Collection::stream)
            .filter(it -> RecipeManagerWrapper.class != it)
            .map(GenericUtil::uncheck)
            .forEach(this::registerRecipeManager);
      };
   }

   public static Collection<IRecipeManager<Recipe<? extends RecipeInput>>> getManagerInstances() {
      if (enqueuedRegistration != null) {
         synchronized (LOCK) {
            if (enqueuedRegistration != null) {
               enqueuedRegistration.run();
               enqueuedRegistration = null;
            }
         }
      }

      return managerInstances.values();
   }

   public static IRecipeManager<Recipe<?>> getOrDefault(ResourceLocation location) {
      return getOrDefault(lookup(location));
   }

   public static IRecipeManager<Recipe<?>> getOrDefault(RecipeType type) {
      return FILTERED_TYPES.stream().anyMatch(it -> type == it.get()) ? null : registeredTypes().computeIfAbsent(type, RecipeManagerWrapper::makeOrNull);
   }

   @ZenCodeType.Method
   public static <T extends IRecipeManager<?>> T getRecipeManager(String location) {
      RecipeType<?> recipeType = lookup(ResourceLocation.parse(location));
      if (recipeType == null) {
         throw new IllegalArgumentException("Unknown recipe type: " + location);
      } else {
         return (T)registeredTypes().get(recipeType);
      }
   }

   private static Map<RecipeType<Recipe<?>>, IRecipeManager<Recipe<?>>> registeredTypes() {
      if (enqueuedRegistration != null) {
         synchronized (LOCK) {
            if (enqueuedRegistration != null) {
               enqueuedRegistration.run();
               enqueuedRegistration = null;
            }
         }
      }

      return registeredTypes;
   }

   private static Map<Class<? extends IRecipeManager<Recipe<?>>>, IRecipeManager<Recipe<?>>> managerInstances() {
      if (enqueuedRegistration != null) {
         synchronized (LOCK) {
            if (enqueuedRegistration != null) {
               enqueuedRegistration.run();
               enqueuedRegistration = null;
            }
         }
      }

      return managerInstances;
   }

   private void registerRecipeManager(Class<? extends IRecipeManager<Recipe<?>>> managerClass) {
      if (managerInstances.containsKey(managerClass)) {
         IRecipeManager<Recipe<?>> manager = managerInstances.get(managerClass);
         this.registerInstance(manager);
      } else {
         IRecipeManager<Recipe<?>> manager = InstantiationUtil.getOrCreateInstance((Class<IRecipeManager<Recipe<?>>>)managerClass);
         if (manager == null) {
            CommonLoggers.zenCode().error("Could not add RecipeManager for {}, please report to the author", managerClass);
         } else {
            managerInstances.put(managerClass, manager);
            this.registerInstance(manager);
         }
      }
   }

   private void registerInstance(IRecipeManager<Recipe<?>> manager) {
      ResourceLocation bracketResourceLocation = manager.getBracketResourceLocation();
      Class<? extends IRecipeManager> managerClass = (Class<? extends IRecipeManager>)manager.getClass();
      if (managerClass.getAnnotation(ZenCodeType.Name.class) == null) {
         String canonicalName = managerClass.getCanonicalName();
         CommonLoggers.zenCode().warn("No Name Annotation found on manager '{}', it will not be registered!", canonicalName);
      } else {
         registeredTypes.put(lookup(bracketResourceLocation), manager);
      }
   }

   @Override
   public ParsedExpression parse(CodePosition position, ZSTokenParser tokens) throws ParseException {
      String name = ParseUtil.readBracketContent(position, tokens);
      ResourceLocation resourceLocation = ResourceLocation.tryParse(name);
      if (resourceLocation == null) {
         throw new ParseException(position, "Invalid ResourceLocation, expected: <recipetype:modid:location>");
      } else if (registeredTypes().containsKey(lookup(resourceLocation))) {
         return this.getCall(name, registeredTypes().get(lookup(resourceLocation)), position);
      } else if (BuiltInRegistries.RECIPE_TYPE.keySet().contains(resourceLocation)) {
         return this.getCallFallback(name, position);
      } else {
         throw new ParseException(position, String.format("Unknown RecipeType: <recipetype:%s>", name));
      }
   }

   private ParsedExpression getCallFallback(String location, CodePosition position) {
      ParsedExpressionVariable crafttweaker = new ParsedExpressionVariable(position, "crafttweaker", null);
      ParsedExpressionMember api = new ParsedExpressionMember(position, crafttweaker, "api", Collections.emptyList());
      ParsedExpressionMember bracket = new ParsedExpressionMember(position, api, "bracket", Collections.emptyList());
      ParsedExpressionMember bracketHandlers = new ParsedExpressionMember(position, bracket, "BracketHandlers", null);
      ParsedExpressionMember getRecipeManager = new ParsedExpressionMember(position, bracketHandlers, "getRecipeManager", null);
      return new ParsedExpressionCall(
         position,
         getRecipeManager,
         new ParsedCallArguments(Collections.emptyList(), Collections.singletonList(new ParsedExpressionString(position, location, false)))
      );
   }

   private ParsedExpression getCall(String location, IRecipeManager<?> manager, CodePosition position) throws ParseException {
      ParsedExpressionVariable crafttweaker = new ParsedExpressionVariable(position, "crafttweaker", null);
      ParsedExpressionMember api = new ParsedExpressionMember(position, crafttweaker, "api", Collections.emptyList());
      ParsedExpressionMember bracket = new ParsedExpressionMember(position, api, "bracket", Collections.emptyList());
      ParsedExpressionMember recipeTypeBracketHandler = new ParsedExpressionMember(position, bracket, "RecipeTypeBracketHandler", null);
      ParsedExpressionMember getRecipeManager = new ParsedExpressionMember(position, recipeTypeBracketHandler, "getRecipeManager", null);
      String nameContent = manager.getClass().getAnnotation(ZenCodeType.Name.class).value();
      IParsedType parsedType = ParseUtil.readParsedType(nameContent, position);
      ParsedCallArguments arguments = new ParsedCallArguments(
         Collections.singletonList(parsedType), Collections.singletonList(new ParsedExpressionString(position, location, false))
      );
      ParsedExpressionCall parsedExpressionCall = new ParsedExpressionCall(position, getRecipeManager, arguments);
      return new ParsedExpressionCast(position, parsedExpressionCall, parsedType, false);
   }

   private static RecipeType<Recipe<?>> lookup(ResourceLocation location) {
      return GenericUtil.uncheck(BuiltInRegistries.RECIPE_TYPE.get(location));
   }

   public static Supplier<Stream<String>> getDumperData() {
      return () -> BuiltInRegistries.RECIPE_TYPE
         .keySet()
         .stream()
         .filter(rl -> !rl.equals(ScriptRecipeType.INSTANCE.id()))
         .map(rl -> String.format(Locale.ENGLISH, "<recipetype:%s>", rl));
   }
}
