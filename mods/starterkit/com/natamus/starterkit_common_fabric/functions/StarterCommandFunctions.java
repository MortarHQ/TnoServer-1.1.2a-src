package com.natamus.starterkit_common_fabric.functions;

import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.class_2168;
import net.minecraft.class_2172;
import net.minecraft.class_2561;

public class StarterCommandFunctions {
   public static final SuggestionProvider<class_2168> activeKitSuggestions = (context, builder) -> class_2172.method_35510(
      StarterGearFunctions.getActiveKitNames(),
      builder,
      value -> value.replace(" ", "_").toLowerCase(),
      value -> class_2561.method_43470(value.replace(" ", "_").toLowerCase())
   );
   public static final SuggestionProvider<class_2168> inactiveKitSuggestions = (context, builder) -> class_2172.method_35510(
      StarterGearFunctions.getInactiveKitNames(),
      builder,
      value -> value.replace(" ", "_").toLowerCase(),
      value -> class_2561.method_43470(value.replace(" ", "_").toLowerCase())
   );
   public static final SuggestionProvider<class_2168> activeKitSuggestionsWithAll = (context, builder) -> class_2172.method_35510(
      StarterGearFunctions.getActiveKitNames(true),
      builder,
      value -> value.replace(" ", "_").toLowerCase(),
      value -> class_2561.method_43470(value.replace(" ", "_").toLowerCase())
   );
   public static final SuggestionProvider<class_2168> inactiveKitSuggestionsWithAll = (context, builder) -> class_2172.method_35510(
      StarterGearFunctions.getInactiveKitNames(true),
      builder,
      value -> value.replace(" ", "_").toLowerCase(),
      value -> class_2561.method_43470(value.replace(" ", "_").toLowerCase())
   );
}
