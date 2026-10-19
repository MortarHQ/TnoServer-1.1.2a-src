package com.blamejared.crafttweaker.api.bracket.custom;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.recipe.component.IRecipeComponent;
import com.blamejared.crafttweaker.api.util.ParseUtil;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.IZenClassRegistry;
import com.google.gson.reflect.TypeToken;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.ResourceLocationException;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.lexer.ZSTokenParser;
import org.openzen.zenscript.parser.BracketExpressionParser;
import org.openzen.zenscript.parser.expression.ParsedCallArguments;
import org.openzen.zenscript.parser.expression.ParsedExpression;
import org.openzen.zenscript.parser.expression.ParsedExpressionCall;
import org.openzen.zenscript.parser.expression.ParsedExpressionMember;
import org.openzen.zenscript.parser.type.IParsedType;

@ZenCodeType.Name("crafttweaker.api.bracket.RecipeComponentBracketHandler")
@ZenRegister
public final class RecipeComponentBracketHandler implements BracketExpressionParser {
   private static final String BRACKET_METHOD_NAME = "bracket";
   private static final Map<Class<?>, String> STDLIB_TYPES = (Map<Class<?>, String>)Util.make(() -> {
      Map<Class<?>, String> m = new Object2ObjectArrayMap();
      m.put(List.class, "stdlib.List");
      m.put(String.class, "string");
      put(m, Void.class, void.class, BasicTypeID.VOID);
      put(m, Boolean.class, boolean.class, BasicTypeID.BOOL);
      put(m, Byte.class, byte.class, BasicTypeID.SBYTE);
      put(m, Short.class, short.class, BasicTypeID.SHORT);
      put(m, Integer.class, int.class, BasicTypeID.INT);
      put(m, Long.class, long.class, BasicTypeID.LONG);
      put(m, Float.class, float.class, BasicTypeID.FLOAT);
      put(m, Double.class, double.class, BasicTypeID.DOUBLE);
      put(m, Character.class, char.class, BasicTypeID.CHAR);
      return m;
   });

   @ZenCodeType.Method("bracket")
   public static <T> IRecipeComponent<T> bracket(ResourceLocation name) {
      return IRecipeComponent.find(name);
   }

   public static Supplier<Stream<String>> getDumperData() {
      return () -> CraftTweakerAPI.getRegistry().getAllRecipeComponents().stream().map(IRecipeComponent::getCommandString).distinct();
   }

   private static void put(Map<Class<?>, String> m, Class<?> boxed, Class<?> primitive, BasicTypeID id) {
      m.put(primitive, id.name);
      m.put(boxed, id.name + "?");
   }

   @Override
   public ParsedExpression parse(CodePosition position, ZSTokenParser tokens) throws ParseException {
      String name = ParseUtil.readBracketContent(position, tokens);

      ResourceLocation rl;
      try {
         rl = ResourceLocation.parse(name);
      } catch (ResourceLocationException e) {
         throw new ParseException(position, "Invalid name, expected <recipecomponent:modid:location>", e);
      }

      IRecipeComponent<?> component = IRecipeComponent.find(rl);
      if (component == null) {
         throw new ParseException(position, "Unknown component " + rl);
      }

      TypeToken<?> token = component.objectType();
      return this.call(position, token, rl);
   }

   private <T> ParsedExpression call(CodePosition position, TypeToken<T> token, ResourceLocation rl) throws ParseException {
      ParsedExpression thisClass = this.compileClassCall(position);
      ParsedExpression method = new ParsedExpressionMember(position, thisClass, "bracket", List.of());
      ParsedExpression argument = ParseUtil.createResourceLocationArgument(position, rl);
      IParsedType generic = this.makeParsedType(token, position);
      ParsedCallArguments arguments = new ParsedCallArguments(List.of(generic), List.of(argument));
      return new ParsedExpressionCall(position, method, arguments);
   }

   private ParsedExpression compileClassCall(CodePosition position) {
      return ParseUtil.staticMemberExpression(position, this.className(this.getClass()));
   }

   private <T> IParsedType makeParsedType(TypeToken<T> token, CodePosition position) throws ParseException {
      try {
         return ParseUtil.readParsedType(this.makeParsedType(token.getType()), position);
      } catch (UnsupportedOperationException | IllegalStateException | NoSuchElementException e) {
         throw new ParseException(position, "Unable to resolve component generic " + token + " due to an error", e);
      }
   }

   private String makeParsedType(Type type) {
      if (type instanceof ParameterizedType generic) {
         String rawType = this.makeParsedType(generic.getRawType());
         return Arrays.stream(generic.getActualTypeArguments()).map(this::makeParsedType).collect(Collectors.joining(", ", rawType + "<", ">"));
      } else if (type instanceof Class<?> clazz) {
         return this.className(clazz);
      } else if (type instanceof TypeVariable) {
         throw new UnsupportedOperationException("Unresolved generic identified " + type);
      } else {
         throw new IllegalStateException();
      }
   }

   private String className(Class<?> clazz) {
      String stdlibType = STDLIB_TYPES.get(clazz);
      if (stdlibType != null) {
         return stdlibType;
      }

      IScriptLoader loader = CraftTweakerAPI.getScriptRunManager().currentRunInfo().loader();
      IZenClassRegistry registry = CraftTweakerAPI.getRegistry().getZenClassRegistry();
      return registry.getNameFor(loader, clazz).orElseThrow();
   }
}
