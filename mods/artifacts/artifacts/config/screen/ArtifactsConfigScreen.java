package artifacts.config.screen;

import artifacts.Artifacts;
import artifacts.config.ConfigManager;
import artifacts.config.value.Value;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.impl.builders.AbstractFieldBuilder;
import me.shedaniel.clothconfig2.impl.builders.DropdownMenuBuilder;
import me.shedaniel.clothconfig2.impl.builders.FieldBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ArtifactsConfigScreen {
   private final ConfigBuilder builder;
   private final Map<String, List<AbstractConfigListEntry<?>>> subCategories = new HashMap<>();

   public ArtifactsConfigScreen(Screen parent) {
      this.builder = ConfigBuilder.create()
         .setParentScreen(parent)
         .setTitle(Component.translatable("%s.config.title".formatted("artifacts")))
         .setSavingRunnable(() -> {
            for (ConfigManager config : Artifacts.CONFIG.configs) {
               config.onConfigChanged();
            }
         });
   }

   public Screen build() {
      for (ConfigManager config : Artifacts.CONFIG.configs) {
         this.addConfigs(config);
      }

      this.subCategories.keySet().stream().sorted().forEach(key -> {
         ConfigCategory category = this.builder.getOrCreateCategory(getTitle(key.split("\\.")[0]));
         String name = key.substring(key.lastIndexOf(46) + 1);
         AbstractConfigListEntry<?> subCategory;
         if (ResourceLocation.isValidPath(name) && BuiltInRegistries.ITEM.containsKey(Artifacts.id(name))) {
            subCategory = new ItemSubCategoryListEntry((Item)BuiltInRegistries.ITEM.get(Artifacts.id(name)), this.subCategories.get(key));
         } else {
            subCategory = this.builder.entryBuilder().startSubCategory(getTitle(key), List.copyOf(this.subCategories.get(key))).build();
         }

         category.addEntry(subCategory);
      });
      return this.builder.build();
   }

   private void addConfigs(ConfigManager config) {
      ConfigCategory configBuilder = this.builder.getOrCreateCategory(getTitle(config.getName()));
      config.getValues().keySet().stream().sorted().sorted(Comparator.comparing(key -> !key.endsWith("generateAsLoot"))).forEach(key -> {
         String[] names = key.split("\\.");
         Value.ConfigValue<?> value = config.getValues().get(key);
         AbstractConfigListEntry<?> field = this.createField(config, config.getName(), key, value, config.getDescription(key).size());
         if (names.length == 1) {
            configBuilder.addEntry(field);
         } else {
            List<AbstractConfigListEntry<?>> subCategory = this.getSubCategory(config.getName() + "." + names[0]);
            subCategory.add(field);
         }
      });
   }

   private List<AbstractConfigListEntry<?>> getSubCategory(String key) {
      if (this.subCategories.containsKey(key)) {
         return this.subCategories.get(key);
      }

      this.subCategories.put(key, new ArrayList<>());
      return this.subCategories.get(key);
   }

   private AbstractConfigListEntry<?> createField(ConfigManager config, String categoryName, String key, Value.ConfigValue<?> value, int tooltipCount) {
      String[] names = key.split("\\.");
      key = categoryName + "." + key;
      String name = names[names.length - 1];
      name = !name.equals("cooldown") && !name.equals("enabled") && !name.equals("generateAsLoot") ? key : name;
      Component[] tooltips = getTooltips(key, tooltipCount);
      FieldBuilder<?, ?, ?> configEntry = this.createConfigEntry(config, value, getTitle(name));
      if (configEntry instanceof AbstractFieldBuilder<?, ?, ?> fieldBuilder) {
         fieldBuilder.setTooltip(tooltips);
      } else if (configEntry instanceof DropdownMenuBuilder<?> dropdownBuilder) {
         dropdownBuilder.setTooltip(tooltips);
      }

      return configEntry.build();
   }

   private <T> FieldBuilder<?, ?, ?> createConfigEntry(ConfigManager config, Value.ConfigValue<T> value, Component title) {
      FieldBuilder<?, ?, ?> configEntry = value.type().getConfigEntryFactory().createConfigEntry(config, this.builder.entryBuilder(), title, value);
      configEntry.requireRestart(value.requiresRestart());
      return configEntry;
   }

   private static Component getTitle(String categoryKey) {
      String name = categoryKey.substring(categoryKey.lastIndexOf(46) + 1);
      return (Component)(ResourceLocation.isValidPath(name) && BuiltInRegistries.ITEM.containsKey(Artifacts.id(name))
         ? ((Item)BuiltInRegistries.ITEM.get(Artifacts.id(name))).getDescription()
         : Component.translatable("%s.config.%s.title".formatted("artifacts", categoryKey)));
   }

   private static Component[] getTooltips(String name, int count) {
      Component[] tooltips = new Component[count];
      if (count > 1) {
         for (int i = 0; i < tooltips.length; i++) {
            tooltips[i] = Component.translatable("%s.config.%s.description.%s".formatted("artifacts", name, i));
         }
      } else if (name.endsWith("generateAsLoot")) {
         tooltips[0] = Component.translatable("%s.config.%s.description".formatted("artifacts", "generateAsLoot"));
      } else {
         tooltips[0] = Component.translatable("%s.config.%s.description".formatted("artifacts", name));
      }

      return tooltips;
   }
}
