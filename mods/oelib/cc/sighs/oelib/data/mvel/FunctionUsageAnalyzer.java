package cc.sighs.oelib.data.mvel;

import cc.sighs.oelib.OELib;
import cc.sighs.oelib.data.DataManager;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.resources.ResourceLocation;

public final class FunctionUsageAnalyzer {
   private static final Pattern FUNCTION_PATTERN = Pattern.compile("([a-zA-Z_][a-zA-Z0-9_]*)\\s*\\(");

   public static <T> Set<String> analyzeUsedFunctions(Map<ResourceLocation, T> dataPackData, FunctionUsageAnalyzer.DataExpressionExtractor<T> dataExtractor) {
      Set<String> usedFunctions = new HashSet<>();

      for (Entry<ResourceLocation, T> entry : dataPackData.entrySet()) {
         T data = entry.getValue();
         if (shouldLoadDataPackFunctions(data, entry.getKey(), dataExtractor)) {
            usedFunctions.addAll(analyzeSingleFile(data, dataExtractor));
         }
      }

      OELib.LOGGER.debug("Found {} used functions in data packages: {}", usedFunctions.size(), usedFunctions);
      return usedFunctions;
   }

   private static <T> boolean shouldLoadDataPackFunctions(T data, ResourceLocation location, FunctionUsageAnalyzer.DataExpressionExtractor<T> dataExtractor) {
      Map<String, String> vars = dataExtractor.extractVariables(data);
      if (vars == null) {
         return true;
      }

      String modLoadedExpression = vars.get("modLoaded");
      if (modLoadedExpression == null) {
         return true;
      }

      try {
         String modId = extractModIdFromExpression(modLoadedExpression);
         if (modId != null) {
            boolean isLoaded = DataManager.isModLoaded(modId);
            OELib.LOGGER.debug("Mod '{}' loaded status: {} for datapack {}", new Object[]{modId, isLoaded, location});
            return isLoaded;
         }
      } catch (Exception e) {
         OELib.LOGGER.debug("Failed to evaluate modLoaded expression for {}: {}", location, e.getMessage());
      }

      return true;
   }

   private static String extractModIdFromExpression(String expression) {
      if (expression == null) {
         return null;
      }

      Pattern pattern = Pattern.compile("isModLoaded\\s*\\(\\s*['\"]([^'\"]+)['\"]\\s*\\)");
      Matcher matcher = pattern.matcher(expression);
      return matcher.find() ? matcher.group(1) : null;
   }

   private static <T> Set<String> analyzeSingleFile(T data, FunctionUsageAnalyzer.DataExpressionExtractor<T> dataExtractor) {
      Set<String> functions = new HashSet<>();

      for (String expression : dataExtractor.extractAllExpressions(data)) {
         functions.addAll(extractFunctionsFromExpression(expression));
      }

      return functions;
   }

   public static Set<String> extractFunctionsFromExpression(String expression) {
      Set<String> functions = new HashSet<>();
      if (expression != null && !expression.trim().isEmpty()) {
         Matcher matcher = FUNCTION_PATTERN.matcher(expression);

         while (matcher.find()) {
            String functionName = matcher.group(1);
            if (!isKeyword(functionName)) {
               functions.add(functionName);
            }
         }

         return functions;
      } else {
         return functions;
      }
   }

   private static boolean isKeyword(String word) {
      Set<String> keywords = Set.of(
         "if",
         "else",
         "for",
         "while",
         "do",
         "switch",
         "case",
         "default",
         "try",
         "catch",
         "finally",
         "throw",
         "throws",
         "return",
         "break",
         "continue",
         "new",
         "this",
         "super",
         "null",
         "true",
         "false",
         "instanceof",
         "public",
         "private",
         "protected",
         "static",
         "final",
         "abstract",
         "class",
         "interface",
         "extends",
         "implements",
         "package",
         "import",
         "int",
         "long",
         "float",
         "double",
         "boolean",
         "char",
         "byte",
         "short",
         "void",
         "String",
         "Object",
         "List",
         "Map",
         "Set"
      );
      return keywords.contains(word);
   }

   public static Set<String> getCoreRequiredFunctions() {
      return Set.of("isModLoaded");
   }

   public interface DataExpressionExtractor<T> {
      Map<String, String> extractVariables(T var1);

      Set<String> extractAllExpressions(T var1);
   }
}
