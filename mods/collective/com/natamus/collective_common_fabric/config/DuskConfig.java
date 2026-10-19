package com.natamus.collective_common_fabric.config;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonWriter;
import com.natamus.collective_common_fabric.functions.DataFunctions;
import com.natamus.collective_common_fabric.functions.NumberFunctions;
import com.natamus.collective_common_fabric.services.Services;
import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.AbstractMap.SimpleEntry;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.class_124;
import net.minecraft.class_2561;
import net.minecraft.class_2583;
import net.minecraft.class_310;
import net.minecraft.class_327;
import net.minecraft.class_332;
import net.minecraft.class_339;
import net.minecraft.class_342;
import net.minecraft.class_364;
import net.minecraft.class_4185;
import net.minecraft.class_4265;
import net.minecraft.class_437;
import net.minecraft.class_5244;
import net.minecraft.class_6379;
import net.minecraft.class_4185.class_4241;
import net.minecraft.class_4265.class_4266;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

public abstract class DuskConfig {
   private static final Pattern INTEGER_ONLY = Pattern.compile("(-?[0-9]*)");
   private static final Pattern DECIMAL_ONLY = Pattern.compile("-?(\\d+\\.?\\d*|\\d*\\.?\\d+|\\.)");
   private static final Pattern HEXADECIMAL_ONLY = Pattern.compile("(-?[#0-9a-fA-F]*)");
   private static final HashMap<String, List<DuskConfig.EntryInfo>> entryHashMap = new HashMap<>();
   public static final Map<String, Class<?>> configClass = new HashMap<>();
   private static final HashMap<String, String> modidToName = new HashMap<>();
   private static final HashMap<String, Path> pathMap = new HashMap<>();
   private static final HashMap<String, HashMap<String, List<String>>> modConfigMetaData = new HashMap<>();
   private static final Gson gson = new GsonBuilder()
      .excludeFieldsWithModifiers(new int[]{128})
      .excludeFieldsWithModifiers(new int[]{2})
      .addSerializationExclusionStrategy(new DuskConfig.HiddenAnnotationExclusionStrategy())
      .setPrettyPrinting()
      .create();

   public static void init(String name, String modid, Class<?> config) {
      init(name, modid, config, true);
   }

   public static void init(String name, String modid, Class<?> config, Boolean initial) {
      pathMap.put(modid, DataFunctions.getConfigDirectoryPath().resolve(modid + ".json5"));
      configClass.put(modid, config);
      modidToName.put(modid, name);
      entryHashMap.put(modid, new ArrayList<>());
      HashMap<String, List<String>> configMetaData = null;

      try {
         for (Field field : config.getDeclaredFields()) {
            if (field.getName().equals("configMetaData")) {
               configMetaData = (HashMap<String, List<String>>)field.get(config);
               break;
            }
         }
      } catch (Exception var15) {
      }

      if (configMetaData != null) {
         modConfigMetaData.put(modid, configMetaData);
      }

      for (Field field : config.getFields()) {
         DuskConfig.EntryInfo info = new DuskConfig.EntryInfo();
         if ((field.isAnnotationPresent(DuskConfig.Entry.class) || field.isAnnotationPresent(DuskConfig.Comment.class))
            && !field.isAnnotationPresent(DuskConfig.Server.class)
            && !field.isAnnotationPresent(DuskConfig.Hidden.class)
            && Services.MODLOADER.isClientSide()) {
            initClient(modid, field, info, configMetaData);
         }

         if (field.isAnnotationPresent(DuskConfig.Comment.class)) {
            info.centered = field.getAnnotation(DuskConfig.Comment.class).centered();
         }

         if (field.isAnnotationPresent(DuskConfig.Entry.class)) {
            try {
               info.defaultValue = field.get(null);
            } catch (IllegalAccessException var13) {
            }
         }
      }

      try {
         loadJson(pathMap.get(modid), modid);
      } catch (Exception e) {
         write(modid);
      }

      try {
         for (DuskConfig.EntryInfo info : entryHashMap.get(modid)) {
            if (info.field.isAnnotationPresent(DuskConfig.Entry.class)) {
               try {
                  info.value = info.field.get(null);
                  info.tempValue = info.value.toString();
               } catch (IllegalAccessException var11) {
               }
            }
         }
      } catch (NullPointerException ex) {
         if (initial) {
            init(name, modid, config, false);
         }
      }
   }

   private static void initClient(String modid, Field field, DuskConfig.EntryInfo info, HashMap<String, List<String>> configMetaData) {
      Class<?> type = field.getType();
      DuskConfig.Entry e = field.getAnnotation(DuskConfig.Entry.class);
      info.width = e != null ? e.width() : 0;
      info.field = field;
      info.id = modid;
      if (e != null) {
         if (!e.name().equals("")) {
            info.name = class_2561.method_43471(e.name());
         }

         if (type == int.class) {
            if (e.min() != Double.MIN_NORMAL) {
               info.minValue = (int)e.min();
               info.maxValue = (int)e.max();
               info.minMaxSet = true;
            }

            textField(modid, info, Integer::parseInt, INTEGER_ONLY, (int)e.min(), (int)e.max(), true);
         } else if (type == float.class) {
            if (e.min() != Double.MIN_NORMAL) {
               info.minValue = (int)e.min();
               info.maxValue = (int)e.max();
               info.minMaxSet = true;
            }

            textField(modid, info, Float::parseFloat, DECIMAL_ONLY, (float)e.min(), (float)e.max(), false);
         } else if (type == double.class) {
            if (e.min() != Double.MIN_NORMAL) {
               info.minValue = (int)e.min();
               info.maxValue = (int)e.max();
               info.minMaxSet = true;
            }

            textField(modid, info, Double::parseDouble, DECIMAL_ONLY, e.min(), e.max(), false);
         } else if (type == String.class || type == List.class) {
            info.max = e.max() == Double.MAX_VALUE ? Integer.MAX_VALUE : (int)e.max();
            textField(modid, info, String::length, null, Math.min(e.min(), 0.0), Math.max(e.max(), 1.0), true);
         } else if (type == boolean.class) {
            Function<Object, class_2561> func = value -> class_2561.method_43471((Boolean)value ? "gui.yes" : "gui.no")
               .method_27692((Boolean)value ? class_124.field_1060 : class_124.field_1061);
            info.widget = new SimpleEntry<>(button -> {
               info.value = !(Boolean)info.value;
               button.method_25355(func.apply(info.value));
            }, func);
         } else if (type.isEnum()) {
            List<?> values = Arrays.asList(field.getType().getEnumConstants());
            Function<Object, class_2561> func = value -> class_2561.method_43471(
               modid + ".duskconfig.enum." + type.getSimpleName() + "." + info.value.toString()
            );
            info.widget = new SimpleEntry<>(button -> {
               int index = values.indexOf(info.value) + 1;
               info.value = values.get(index >= values.size() ? 0 : index);
               button.method_25355(func.apply(info.value));
            }, func);
         }
      }

      String fieldname = field.getName();
      if (configMetaData != null && configMetaData.containsKey(fieldname)) {
         List<String> metaData = configMetaData.get(fieldname);
         if (metaData.size() > 0) {
            info.description = metaData.get(0);
            if (metaData.size() > 1) {
               info.range = metaData.get(1);
            }
         }
      }

      if (!entryHashMap.containsKey(modid)) {
         entryHashMap.put(modid, new ArrayList<>());
      }

      try {
         entryHashMap.get(modid).add(info);
      } catch (Exception e0) {
         try {
            entryHashMap.get(modid).add(info);
         } catch (Exception e1) {
            entryHashMap.put(modid, Arrays.asList(info));
         }
      }
   }

   private static void textField(String modid, DuskConfig.EntryInfo info, Function<String, Number> f, Pattern pattern, double min, double max, boolean cast) {
      boolean isNumber = pattern != null;
      info.widget = (BiFunction<class_342, class_4185, Predicate<String>>)(t, b) -> s -> {
         if (!s.isEmpty() && isNumber && !pattern.matcher(s).matches()) {
            return false;
         }

         Number value = 0;
         boolean inLimits = false;
         info.error = null;
         if ((!isNumber || !s.isEmpty()) && !s.equals("-") && !s.equals(".")) {
            value = f.apply(s);
            inLimits = value.doubleValue() >= min && value.doubleValue() <= max;
            info.error = inLimits
               ? null
               : new SimpleEntry<>(
                  t,
                  class_2561.method_43470(
                     value.doubleValue() < min
                        ? "§cMinimum " + (isNumber ? "value" : "length") + (cast ? " is " + (int)min : " is " + min)
                        : "§cMaximum " + (isNumber ? "value" : "length") + (cast ? " is " + (int)max : " is " + max)
                  )
               );
         }

         info.tempValue = s;
         t.method_1868(inLimits ? -1 : -34953);
         info.inLimits = inLimits;
         b.field_22763 = entryHashMap.get(modid).stream().allMatch(e -> e.inLimits);
         if (inLimits && info.field.getType() != List.class) {
            info.value = isNumber ? value : s;
         } else if (inLimits) {
            if (((List)info.value).size() == info.index) {
               ((List)info.value).add("");
            }

            ((List)info.value).set(info.index, Arrays.stream(info.tempValue.replace("[", "").replace("]", "").split(", ")).toList().getFirst());
         }

         if (info.field.getAnnotation(DuskConfig.Entry.class).isColor()) {
            if (!s.contains("#")) {
               s = "#" + s;
            }

            if (!HEXADECIMAL_ONLY.matcher(s).matches()) {
               return false;
            }

            try {
               info.colorButton
                  .method_25355(class_2561.method_43470("⬛").method_10862(class_2583.field_24360.method_36139(Color.decode(info.tempValue).getRGB())));
            } catch (Exception var16) {
            }
         }

         return true;
      };
   }

   public static void write(String modid) {
      Path path = DataFunctions.getConfigDirectoryPath().resolve(modid + ".json5");
      pathMap.put(modid, path);

      try {
         if (!Files.exists(path)) {
            Files.createDirectories(path.getParent());
            Files.createFile(path);
         }

         Class<?> cC = configClass.get(modid);
         if (cC == null) {
            return;
         }

         HashMap<String, List<String>> configMetaData = null;
         String lastline = "";
         StringBuilder json5 = new StringBuilder("{");

         for (Field field : cC.getDeclaredFields()) {
            if (field.getName().equals("configMetaData")) {
               configMetaData = (HashMap<String, List<String>>)field.get(cC);
               break;
            }
         }

         for (Field field : cC.getDeclaredFields()) {
            String fieldname = field.getName();
            if (!fieldname.equals("configMetaData")) {
               if (configMetaData != null && configMetaData.containsKey(fieldname)) {
                  for (String metaDataValue : configMetaData.get(fieldname)) {
                     json5.append("\n\t// ").append(metaDataValue);
                  }

                  if (field.isAnnotationPresent(DuskConfig.Entry.class)) {
                     DuskConfig.Entry e = field.getAnnotation(DuskConfig.Entry.class);
                     if (e.min() != Double.MIN_NORMAL) {
                        String rangeValue = "min: " + e.min() + ", max: " + e.max();
                        if (field.getType() == int.class) {
                           rangeValue = rangeValue.replace(".0", "");
                        }

                        json5.append("\n\t// ").append(rangeValue);
                     }
                  }
               }

               if (field.getType().isAssignableFrom(String.class)) {
                  String fieldvalue = field.get(cC).toString();
                  if (fieldvalue.length() > 0) {
                     if (fieldvalue.charAt(0) != '"') {
                        fieldvalue = "\"" + fieldvalue + "\"";
                     }
                  } else {
                     fieldvalue = "\"\"";
                  }

                  json5.append("\n\t\"").append(fieldname).append("\": ").append(fieldvalue).append(",");
               } else {
                  json5.append("\n\t\"").append(fieldname).append("\": ").append(field.get(cC)).append(",");
               }
            }
         }

         json5.setLength(json5.length() - 1);
         json5.append("\n}");
         Files.write(path, json5.toString().getBytes());
      } catch (Exception var14) {
      }
   }

   public static void loadJson(Path path, String modid) throws JsonSyntaxException, IOException {
      boolean rewriteConfig = false;
      Path oldFabricJsonPath = DataFunctions.getConfigDirectoryPath().resolve(modid + ".json");
      if (Files.exists(oldFabricJsonPath)) {
         Files.move(oldFabricJsonPath, path, StandardCopyOption.REPLACE_EXISTING);
         rewriteConfig = true;
      }

      Path oldForgeTomlPath = DataFunctions.getConfigDirectoryPath().resolve(modid + "-common.toml");
      if (Files.exists(oldForgeTomlPath)) {
         convertTomlToJson(oldForgeTomlPath, path);
         rewriteConfig = true;
      }

      if (!Files.exists(path)) {
         rewriteConfig = true;
      }

      String content = Files.readString(path);
      content = Stream.of(content.split("\n")).filter(s -> !s.startsWith("\t//")).collect(Collectors.joining("\n"));
      gson.fromJson(new StringReader(content), configClass.get(modid));
      if (rewriteConfig) {
         write(modid);
      }
   }

   private static void convertTomlToJson(Path oldTomlPath, Path newJsonPath) throws IOException {
      JsonWriter jsonWriter = new JsonWriter(new FileWriter(newJsonPath.toString()));
      jsonWriter.beginObject();
      String tomlContent = Files.readString(oldTomlPath, StandardCharsets.UTF_8);

      for (String line : tomlContent.split("\n")) {
         line = line.trim();
         if (!line.startsWith("[") && !line.startsWith("#")) {
            String[] linespl = line.split(" = ", 2);
            if (linespl.length == 2) {
               String key = linespl[0];
               String rawValue = linespl[1].strip();
               if (rawValue.equals("true") || rawValue.equals("false")) {
                  jsonWriter.name(key).value(Boolean.parseBoolean(rawValue));
               } else if (NumberFunctions.isNumeric(rawValue)) {
                  if (rawValue.contains(".")) {
                     jsonWriter.name(key).value(Double.parseDouble(rawValue));
                  } else {
                     jsonWriter.name(key).value(Integer.parseInt(rawValue));
                  }
               } else {
                  jsonWriter.name(key).value(rawValue);
               }
            }
         }
      }

      jsonWriter.endObject();
      jsonWriter.close();
      Files.delete(oldTomlPath);
   }

   public static class ButtonEntry extends class_4266<DuskConfig.ButtonEntry> {
      private static final class_327 font = class_310.method_1551().field_1772;
      public final List<class_339> buttons;
      private final class_2561 text;
      public final DuskConfig.EntryInfo info;
      private final List<class_339> children = new ArrayList<>();
      public static final Map<class_339, class_2561> buttonsWithComponent = new HashMap<>();

      private ButtonEntry(List<class_339> buttons, class_2561 text, DuskConfig.EntryInfo info) {
         if (!buttons.isEmpty()) {
            buttonsWithComponent.put(buttons.getFirst(), text);
         }

         this.buttons = buttons;
         this.text = text;
         this.info = info;
         this.children.addAll(buttons);
      }

      public static DuskConfig.ButtonEntry create(List<class_339> buttons, class_2561 text, DuskConfig.EntryInfo info) {
         return new DuskConfig.ButtonEntry(buttons, text, info);
      }

      public void method_25343(
         @NotNull class_332 matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta
      ) {
         this.buttons.forEach(b -> {
            b.method_46419(y);
            b.method_25394(matrices, mouseX, mouseY, tickDelta);
         });
         if (this.text != null && (!this.text.getString().contains("spacer") || !this.buttons.isEmpty())) {
            if (this.info.centered) {
               matrices.method_27535(
                  font, this.text, (int)(class_310.method_1551().method_22683().method_4486() / 2.0F - font.method_27525(this.text) / 2.0F), y + 5, 16777215
               );
            } else {
               matrices.method_27535(font, this.text, 12, y + 5, 16777215);
            }
         }
      }

      @NotNull
      public List<? extends class_364> method_25396() {
         return this.children;
      }

      @NotNull
      public List<? extends class_6379> method_37025() {
         return this.children;
      }
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.FIELD)
   public @interface Client {
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.FIELD)
   public @interface Comment {
      boolean centered() default false;
   }

   public static class DuskConfigListWidget extends class_4265<DuskConfig.ButtonEntry> {
      class_327 font;

      public DuskConfigListWidget(class_310 client, int width, int height, int y, int itemHeight, int m) {
         super(client, width, height - 68, y, m);
         this.field_22744 = false;
         this.font = client.field_1772;
      }

      public int method_25329() {
         return this.field_22758 - 7;
      }

      public void addButton(List<class_339> buttons, class_2561 text, DuskConfig.EntryInfo info) {
         this.method_25321(DuskConfig.ButtonEntry.create(buttons, text, info));
      }

      public int method_25322() {
         return 10000;
      }

      public Optional<class_339> getHoveredButton(double mouseX, double mouseY) {
         for (DuskConfig.ButtonEntry buttonEntry : this.method_25396()) {
            if (!buttonEntry.buttons.isEmpty() && buttonEntry.buttons.getFirst().method_25405(mouseX, mouseY)) {
               return Optional.of(buttonEntry.buttons.getFirst());
            }
         }

         return Optional.empty();
      }
   }

   public static class DuskConfigScreen extends class_437 {
      public final String translationPrefix;
      public final class_437 parent;
      public final String modid;
      public DuskConfig.DuskConfigListWidget list;
      public boolean reload = false;

      protected DuskConfigScreen(class_437 parent, String modid) {
         super(class_2561.method_43470(StringUtils.capitalize(DuskConfig.modidToName.get(modid)) + " Config"));
         this.parent = parent;
         this.modid = modid;
         this.translationPrefix = modid + ".duskconfig.";
      }

      public static class_437 getScreen(class_437 parent, String modid) {
         return new DuskConfig.DuskConfigScreen(parent, modid);
      }

      public void method_25420(@NotNull class_332 guiGraphics, int a, int b, float c) {
         if (this.field_22787.field_1687 == null) {
            this.method_57728(guiGraphics, c);
         }

         this.method_57734(c);
      }

      public void method_25393() {
         super.method_25393();

         for (DuskConfig.EntryInfo info : DuskConfig.entryHashMap.get(this.modid)) {
            try {
               info.field.set(null, info.value);
            } catch (IllegalAccessException var4) {
            }
         }

         this.updateResetButtons();
      }

      public void updateResetButtons() {
         if (this.list != null) {
            for (DuskConfig.ButtonEntry entry : this.list.method_25396()) {
               if (entry.buttons != null && entry.buttons.size() > 1 && entry.buttons.get(1) instanceof class_4185 button) {
                  button.field_22763 = !Objects.equals(entry.info.value.toString(), entry.info.defaultValue.toString());
               }
            }
         }
      }

      public void loadValues() {
         try {
            DuskConfig.loadJson(DuskConfig.pathMap.get(this.modid), this.modid);
         } catch (Exception e) {
            DuskConfig.write(this.modid);
         }

         for (DuskConfig.EntryInfo info : DuskConfig.entryHashMap.get(this.modid)) {
            if (info.field.isAnnotationPresent(DuskConfig.Entry.class)) {
               try {
                  info.value = info.field.get(null);
                  info.tempValue = info.value.toString();
               } catch (IllegalAccessException var4) {
               }
            }
         }
      }

      public void method_25426() {
         super.method_25426();
         if (!this.reload) {
            this.loadValues();
         }

         this.method_37063(class_4185.method_46430(class_5244.field_24335, button -> {
            this.loadValues();
            Objects.requireNonNull(this.field_22787).method_1507(this.parent);
         }).method_46433(this.field_22789 / 2 - 154, this.field_22790 - 28).method_46437(150, 20).method_46431());
         class_4185 done = (class_4185)this.method_37063(class_4185.method_46430(class_5244.field_24334, button -> {
            for (DuskConfig.EntryInfo infox : DuskConfig.entryHashMap.get(this.modid)) {
               if (infox.id.equals(this.modid)) {
                  try {
                     infox.field.set(null, infox.value);
                  } catch (IllegalAccessException var5x) {
                  }
               }
            }

            DuskConfig.write(this.modid);
            Objects.requireNonNull(this.field_22787).method_1507(this.parent);
         }).method_46433(this.field_22789 / 2 + 4, this.field_22790 - 28).method_46437(150, 20).method_46431());
         this.list = new DuskConfig.DuskConfigListWidget(this.field_22787, this.field_22789, this.field_22790, 32, this.field_22790 - 32, 25);
         this.method_25429(this.list);

         for (DuskConfig.EntryInfo info : DuskConfig.entryHashMap.get(this.modid)) {
            if (info.id.equals(this.modid)) {
               String namestring = info.field.getName();
               namestring = namestring.replace("_", " ").strip();
               String[] nsspl = namestring.split("(?<=.)(?=\\p{Lu})");
               List<String> correctNameParts = new ArrayList<>();

               try {
                  for (int i = 0; i < nsspl.length; i++) {
                     StringBuilder namePart = new StringBuilder(nsspl[i]);
                     if (namePart.toString().strip().length() == 1) {
                        boolean append = false;

                        while (i < nsspl.length) {
                           if (append) {
                              namePart.append(nsspl[i]);
                           }

                           if (i + 1 >= nsspl.length || nsspl[i + 1].strip().length() != 1) {
                              break;
                           }

                           append = true;
                           i++;
                        }
                     }

                     if (!correctNameParts.contains(namePart.toString())) {
                        correctNameParts.add(namePart.toString());
                     }
                  }
               } catch (Exception ex) {
                  correctNameParts = new ArrayList<>(Arrays.asList(nsspl));
               }

               String formattedName = StringUtils.capitalize(String.join(" ", correctNameParts));
               class_2561 name = class_2561.method_43470(formattedName);
               class_4185 resetButton = class_4185.method_46430(class_2561.method_43470("Reset").method_27692(class_124.field_1061), button -> {
                  info.value = info.defaultValue;
                  info.tempValue = info.defaultValue.toString();
                  info.index = 0;
                  double scrollAmount = this.list.method_25341();
                  this.reload = true;
                  Objects.requireNonNull(this.field_22787).method_1507(this);
                  this.list.method_25307(scrollAmount);
               }).method_46433(this.field_22789 - 205, 0).method_46437(40, 20).method_46431();
               if (info.widget instanceof Map.Entry<class_4241, Function<Object, class_2561>> widget) {
                  if (info.field.getType().isEnum()) {
                     widget.setValue(
                        value -> class_2561.method_43471(this.translationPrefix + "enum." + info.field.getType().getSimpleName() + "." + info.value.toString())
                     );
                  }

                  this.list
                     .addButton(
                        List.of(
                           class_4185.method_46430(widget.getValue().apply(info.value), widget.getKey())
                              .method_46433(this.field_22789 - 160, 0)
                              .method_46437(150, 20)
                              .method_46431(),
                           resetButton
                        ),
                        name,
                        info
                     );
               } else if (info.field.getType() == List.class) {
                  if (!this.reload) {
                     info.index = 0;
                  }

                  class_342 widget = new class_342(this.field_22793, this.field_22789 - 160, 0, 150, 20, null);
                  widget.method_1880(Integer.MAX_VALUE);
                  if (info.index < ((List)info.value).size()) {
                     widget.method_1852(String.valueOf(((List)info.value).get(info.index)));
                  } else {
                     widget.method_1852("");
                  }

                  Predicate<String> processor = (Predicate<String>)((BiFunction)info.widget).apply(widget, done);
                  widget.method_1890(processor);
                  resetButton.method_25358(20);
                  resetButton.method_25355(class_2561.method_43470("R").method_27692(class_124.field_1061));
                  class_4185 cycleButton = class_4185.method_46430(class_2561.method_43470(String.valueOf(info.index)), button -> {
                     ((List)info.value).remove("");
                     double scrollAmount = this.list.method_25341();
                     this.reload = true;
                     info.index++;
                     if (info.index > ((List)info.value).size()) {
                        info.index = 0;
                     }

                     Objects.requireNonNull(this.field_22787).method_1507(this);
                     this.list.method_25307(scrollAmount);
                  }).method_46433(this.field_22789 - 185, 0).method_46437(20, 20).method_46431();
                  this.list.addButton(List.of(widget, resetButton, cycleButton), name, info);
               } else if (info.widget != null) {
                  class_342 widget = new class_342(this.field_22793, this.field_22789 - 160, 0, 150, 20, null);
                  widget.method_1880(Integer.MAX_VALUE);
                  widget.method_1852(info.tempValue);
                  Predicate<String> processor = (Predicate<String>)((BiFunction)info.widget).apply(widget, done);
                  widget.method_1890(processor);
                  if (info.field.getAnnotation(DuskConfig.Entry.class).isColor()) {
                     resetButton.method_25358(20);
                     resetButton.method_25355(class_2561.method_43470("R").method_27692(class_124.field_1061));
                     class_4185 colorButton = class_4185.method_46430(class_2561.method_43470("⬛"), button -> {})
                        .method_46433(this.field_22789 - 185, 0)
                        .method_46437(20, 20)
                        .method_46431();

                     try {
                        colorButton.method_25355(
                           class_2561.method_43470("⬛").method_10862(class_2583.field_24360.method_36139(Color.decode(info.tempValue).getRGB()))
                        );
                     } catch (Exception var14) {
                     }

                     info.colorButton = colorButton;
                     colorButton.field_22763 = false;
                     this.list.addButton(List.of(widget, resetButton, colorButton), name, info);
                  } else {
                     this.list.addButton(List.of(widget, resetButton), name, info);
                  }
               } else {
                  this.list.addButton(List.of(), name, info);
               }

               String rangeValue = "";
               if (info.minMaxSet) {
                  rangeValue = "min: " + info.minValue + ", max: " + info.maxValue;
                  if (info.field.getType() == int.class) {
                     rangeValue = rangeValue.replace(".0", "");
                  }
               } else if (!info.range.equals("")) {
                  rangeValue = info.range;
               }

               if (!rangeValue.equals("")) {
                  class_342 label = new class_342(this.field_22793, this.field_22789 - 155, 0, 145, 20, null);
                  label.method_1852(rangeValue);
                  label.method_1858(false);
                  label.method_1888(false);
                  this.list.addButton(List.of(label), class_2561.method_43470(""), info);
               }
            }

            this.updateResetButtons();
         }
      }

      public void method_25394(@NotNull class_332 guiGraphics, int mouseX, int mouseY, float delta) {
         super.method_25394(guiGraphics, mouseX, mouseY, delta);
         if (this.field_22787 == null || Objects.requireNonNull(this.field_22787).field_1687 == null) {
            guiGraphics.method_51448().method_46416(0.0F, 0.0F, -100.0F);
         }

         this.list.method_25394(guiGraphics, mouseX, mouseY, delta);
         guiGraphics.method_27534(this.field_22793, this.field_22785, this.field_22789 / 2, 15, 16777215);
      }
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.FIELD)
   public @interface Entry {
      int width() default 100;

      double min() default Double.MIN_NORMAL;

      double max() default Double.MAX_VALUE;

      String name() default "";

      boolean isColor() default false;
   }

   public static class EntryInfo {
      Field field;
      Object widget;
      int width;
      double minValue;
      double maxValue;
      boolean minMaxSet = false;
      int max;
      boolean centered;
      Map.Entry<class_342, class_2561> error;
      Object defaultValue;
      Object value;
      String tempValue;
      boolean inLimits = true;
      String id;
      class_2561 name;
      String description = "";
      String range = "";
      int index;
      class_339 colorButton;
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.FIELD)
   public @interface Hidden {
   }

   public static class HiddenAnnotationExclusionStrategy implements ExclusionStrategy {
      public boolean shouldSkipClass(Class<?> clazz) {
         return false;
      }

      public boolean shouldSkipField(FieldAttributes fieldAttributes) {
         return fieldAttributes.getAnnotation(DuskConfig.Entry.class) == null;
      }
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.FIELD)
   public @interface Server {
   }
}
