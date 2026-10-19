package com.tensura_tno.ai;

import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;
import org.jetbrains.annotations.Nullable;

public final class SubordinateClonePolicy {
   private static final Pattern CLONING_NAME = Pattern.compile("(?:^|[.:/_-])(?:clones?|cloning|body_double|doppelganger|parallel_existence)(?:$|[.:/_-])");
   private static final Set<String> CLONING_MODES = Set.of(
      "falsifier.illusion", "falsifier.fake_death", "reaper.attack", "unyielding.backup", "velgrynd.liberation", "azathoth.dragon_liberation"
   );

   private SubordinateClonePolicy() {
   }

   public static boolean isCloningSkill(String path) {
      return hasCloningName(path.toLowerCase(Locale.ROOT));
   }

   public static boolean isCloningMode(@Nullable String modeId) {
      if (modeId == null) {
         return false;
      }

      String name = modeId.toLowerCase(Locale.ROOT);
      int namespace = name.indexOf(58);
      if (namespace >= 0) {
         name = name.substring(namespace + 1);
      }

      return CLONING_MODES.contains(name) || hasCloningName(name);
   }

   private static boolean hasCloningName(String name) {
      return CLONING_NAME.matcher(name).find();
   }
}
