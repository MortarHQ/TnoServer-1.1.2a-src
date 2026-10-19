package com.chronodawn.gametest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dev.architectury.registry.registries.RegistrySupplier;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.Item;

public final class TranslationKeyTests {
   private static final List<String> SUPPORTED_LANGUAGES = List.of("en_us", "ja_jp");

   private TranslationKeyTests() {
   }

   public static <T> List<T> generate(Class<?> itemsClass, Map<String, String> idOverrides, TranslationKeyTests.TestFactory<T> factory) {
      List<T> tests = new ArrayList<>();

      for (String langCode : SUPPORTED_LANGUAGES) {
         tests.addAll(generateForLanguage(itemsClass, idOverrides, factory, langCode));
      }

      return tests;
   }

   private static <T> List<T> generateForLanguage(
      Class<?> itemsClass, Map<String, String> idOverrides, TranslationKeyTests.TestFactory<T> factory, String langCode
   ) {
      Map<String, String> langMap = loadLangFile(langCode);
      List<T> tests = new ArrayList<>();

      for (Field field : itemsClass.getDeclaredFields()) {
         if (isRegistrySupplierField(field)) {
            String fieldName = field.getName();
            String expectedId = idOverrides.getOrDefault(fieldName, fieldName.toLowerCase());
            String testName = "translation_key_" + langCode + "_" + expectedId;
            tests.add(factory.create(testName, helper -> helper.runAfterDelay(1L, () -> {
               try {
                  RegistrySupplier<Item> supplier = (RegistrySupplier<Item>)field.get(null);
                  Item item = (Item)supplier.get();
                  String descriptionId = item.getDescriptionId();
                  if (langMap.containsKey(descriptionId)) {
                     helper.succeed();
                  } else {
                     helper.fail(fieldName + " has descriptionId \"" + descriptionId + "\" which is not in " + langCode + ".json");
                  }
               } catch (Exception e) {
                  helper.fail("Failed to check translation for " + fieldName + ": " + e.getMessage());
               }
            })));
         }
      }

      return tests;
   }

   private static Map<String, String> loadLangFile(String langCode) {
      try (InputStream is = TranslationKeyTests.class.getResourceAsStream("/assets/chronodawn/lang/" + langCode + ".json")) {
         if (is == null) {
            return Collections.emptyMap();
         }

         String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
         Gson gson = new Gson();
         return (Map<String, String>)gson.fromJson(content, (new TypeToken<Map<String, String>>() {}).getType());
      } catch (Exception e) {
         return Collections.emptyMap();
      }
   }

   private static boolean isRegistrySupplierField(Field field) {
      return Modifier.isPublic(field.getModifiers())
         && Modifier.isStatic(field.getModifiers())
         && Modifier.isFinal(field.getModifiers())
         && RegistrySupplier.class.isAssignableFrom(field.getType());
   }

   @FunctionalInterface
   public interface TestFactory<T> {
      T create(String var1, Consumer<GameTestHelper> var2);
   }
}
