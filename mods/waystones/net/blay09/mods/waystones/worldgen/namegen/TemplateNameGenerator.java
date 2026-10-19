package net.blay09.mods.waystones.worldgen.namegen;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import net.blay09.mods.waystones.api.Waystone;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;

public class TemplateNameGenerator implements NameGenerator {
   private final String template;
   private final Map<String, NameGenerator> placeholders = new HashMap<>();

   public TemplateNameGenerator(String template) {
      this.template = template;
   }

   public TemplateNameGenerator with(String key, NameGenerator nameGenerator) {
      this.placeholders.put(key, nameGenerator);
      return this;
   }

   public TemplateNameGenerator with(String key, String value) {
      return this.with(key, new LiteralNameGenerator(value));
   }

   @Override
   public Optional<Component> generateName(LevelAccessor level, Waystone waystone, RandomSource rand) {
      String result = this.template;

      for (Entry<String, NameGenerator> entry : this.placeholders.entrySet()) {
         String key = entry.getKey();
         Component value = entry.getValue().generateName(level, waystone, rand).orElse(null);
         if (value != null) {
            result = result.replaceAll("\\{" + key + "}", value.getString());
         }
      }

      return Optional.of(Component.literal(result));
   }
}
