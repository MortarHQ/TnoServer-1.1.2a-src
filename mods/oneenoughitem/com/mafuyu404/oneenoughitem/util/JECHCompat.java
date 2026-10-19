package com.mafuyu404.oneenoughitem.util;

import me.towdium.jecharacters.utils.Match;
import net.neoforged.fml.ModList;

public class JECHCompat {
   public static boolean JECHLoaded = ModList.get().isLoaded("jecharacters");

   public static boolean contains(String chinese, String pinyin) {
      return JECHLoaded ? Match.contains(chinese, pinyin) : chinese.contains(pinyin);
   }
}
