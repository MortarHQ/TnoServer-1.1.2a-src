package dev.aika.smsn.compat;

import dev.aika.smsn.SMSN;
import dev.aika.smsn.annotation.Category;
import dev.aika.smsn.annotation.LoaderSpecific;
import dev.aika.smsn.api.LoaderType;
import dev.aika.smsn.client.gui.components.ComponentBuilder;
import dev.aika.smsn.config.ModConfig;
import dev.aika.smsn.config.SMSNConfigDefault;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.TextListEntry;
import me.shedaniel.clothconfig2.impl.ConfigCategoryImpl;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.HoverEvent.Action;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class ClothConfigCompat {
   private static final Logger log = SMSN.LOGGER;
   private static final Marker marker = MarkerFactory.getMarker("ClothConfigCompat");
   private static final String modid = "smsn";
   private static final String sponsorUrl = "https://afdian.com/a/gizmo";
   private static final Class<?> configDefault = SMSNConfigDefault.class;

   public static Screen ConfigScreen(ModConfig config, Screen parent) {
      ConfigBuilder builder = ConfigBuilder.create()
         .setParentScreen(parent)
         .setSavingRunnable(config::save)
         .setTitle(Component.translatable(String.format("config.%s.title", "smsn")));
      ComponentBuilder componentBuilder = new ComponentBuilder(builder.entryBuilder(), config).setModId("smsn").setDefaultObject(configDefault);
      Field[] fields = config.getClass().getDeclaredFields();

      for (Field field : fields) {
         addEntry(builder, componentBuilder, field);
      }

      builder.transparentBackground();
      return builder.build();
   }

   private static ConfigCategory getCategory(ConfigBuilder builder, ComponentBuilder componentBuilder, String categoryKey) {
      ConfigCategory category = builder.getOrCreateCategory(Component.translatable(String.format("config.%s.%s", "smsn", categoryKey)));
      if (((ConfigCategoryImpl)category).getEntries().isEmpty()) {
         category.addEntry(sponsorDescription(componentBuilder.getEntryBuilder()));
      }

      return category;
   }

   private static boolean isIgnored(Field field) {
      if (Modifier.isFinal(field.getModifiers())) {
         return true;
      }

      List<LoaderType> loaders = new ArrayList<>();
      LoaderSpecific loaderAnnotation = field.getAnnotation(LoaderSpecific.class);
      if (loaderAnnotation != null) {
         loaders.addAll(List.of(loaderAnnotation.value()));
      } else {
         loaders.add(LoaderType.getCurrentLoader());
      }

      return !loaders.contains(LoaderType.getCurrentLoader());
   }

   private static void addEntry(ConfigBuilder builder, ComponentBuilder componentBuilder, Field field) {
      if (!isIgnored(field)) {
         Category categoryAnnotation = field.getAnnotation(Category.class);
         String category = categoryAnnotation != null ? categoryAnnotation.value() : "general";
         ConfigCategory configCategory = getCategory(builder, componentBuilder, category);
         Class<?> fieldType = field.getType();
         if (fieldType != Boolean.class && fieldType != boolean.class) {
            log.warn(marker, "Unsupported field type: {}", fieldType);
         } else {
            configCategory.addEntry(componentBuilder.switchBuilder(field).setCategory(category).build());
         }
      }
   }

   public static TextListEntry sponsorDescription(ConfigEntryBuilder entryBuilder) {
      return entryBuilder.startTextDescription(
            Component.translatable(
               String.format("config.%s.sponsor.description", "smsn"),
               new Object[]{
                  Component.translatable("modmenu.nameTranslation.smsn").withStyle(s -> s.withColor(ChatFormatting.AQUA).withBold(true)),
                  Component.translatable(String.format("config.%s.sponsor.description.afdian", "smsn"))
                     .withStyle(
                        s -> s.withColor(ChatFormatting.DARK_PURPLE)
                           .withBold(true)
                           .withHoverEvent(new HoverEvent(Action.SHOW_TEXT, Component.literal("https://afdian.com/a/gizmo")))
                           .withClickEvent(new ClickEvent(net.minecraft.network.chat.ClickEvent.Action.OPEN_URL, "https://afdian.com/a/gizmo"))
                     )
               }
            )
         )
         .build();
   }
}
