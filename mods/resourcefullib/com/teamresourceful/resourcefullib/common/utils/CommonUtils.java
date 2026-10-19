package com.teamresourceful.resourcefullib.common.utils;

import com.teamresourceful.resourcefullib.common.exceptions.UtilityClassException;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;

public final class CommonUtils {
   private CommonUtils() throws UtilityClassException {
      throw new UtilityClassException();
   }

   public static <T> T generate(Predicate<T> validator, Supplier<T> getter) {
      T value;
      do {
         value = getter.get();
      } while (!validator.test(value));

      return value;
   }

   public static Component serverTranslatable(String key, Object... args) {
      return args.length == 0
         ? Component.translatableWithFallback(key, Language.getInstance().getOrDefault(key, key))
         : Component.translatableWithFallback(key, Language.getInstance().getOrDefault(key, key), args);
   }
}
