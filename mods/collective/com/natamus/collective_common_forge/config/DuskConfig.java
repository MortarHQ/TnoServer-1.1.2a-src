package com.natamus.collective_common_forge.config;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonWriter;
import com.natamus.collective_common_forge.functions.DataFunctions;
import com.natamus.collective_common_forge.functions.NumberFunctions;
import com.natamus.collective_common_forge.services.Services;
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
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
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
            info.name = Component.translatable(e.name());
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
            Function<Object, Component> func = value -> Component.translatable((Boolean)value ? "gui.yes" : "gui.no")
               .withStyle((Boolean)value ? ChatFormatting.GREEN : ChatFormatting.RED);
            info.widget = new SimpleEntry<>(button -> {
               info.value = !(Boolean)info.value;
               button.setMessage(func.apply(info.value));
            }, func);
         } else if (type.isEnum()) {
            List<?> values = Arrays.asList(field.getType().getEnumConstants());
            Function<Object, Component> func = value -> Component.translatable(modid + ".duskconfig.enum." + type.getSimpleName() + "." + info.value.toString());
            info.widget = new SimpleEntry<>(button -> {
               int index = values.indexOf(info.value) + 1;
               info.value = values.get(index >= values.size() ? 0 : index);
               button.setMessage(func.apply(info.value));
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
      info.widget = (BiFunction<EditBox, Button, Predicate<String>>)(t, b) -> s -> {
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
                  Component.literal(
                     value.doubleValue() < min
                        ? "§cMinimum " + (isNumber ? "value" : "length") + (cast ? " is " + (int)min : " is " + min)
                        : "§cMaximum " + (isNumber ? "value" : "length") + (cast ? " is " + (int)max : " is " + max)
                  )
               );
         }

         info.tempValue = s;
         t.setTextColor(inLimits ? -1 : -34953);
         info.inLimits = inLimits;
         b.active = entryHashMap.get(modid).stream().allMatch(e -> e.inLimits);
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
               info.colorButton.setMessage(Component.literal("⬛").setStyle(Style.EMPTY.withColor(Color.decode(info.tempValue).getRGB())));
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

   public static class ButtonEntry extends net.minecraft.client.gui.components.ContainerObjectSelectionList.Entry<DuskConfig.ButtonEntry> {
      private static final Font font;
      public final List<AbstractWidget> buttons;
      private final Component text;
      public final DuskConfig.EntryInfo info;
      private final List<AbstractWidget> children = new ArrayList<>();
      public static final Map<AbstractWidget, Component> buttonsWithComponent = new HashMap<>();

      private ButtonEntry(List<AbstractWidget> buttons, Component text, DuskConfig.EntryInfo info) {
         if (!buttons.isEmpty()) {
            buttonsWithComponent.put(buttons.getFirst(), text);
         }

         this.buttons = buttons;
         this.text = text;
         this.info = info;
         this.children.addAll(buttons);
      }

      public static DuskConfig.ButtonEntry create(List<AbstractWidget> buttons, Component text, DuskConfig.EntryInfo info) {
         return new DuskConfig.ButtonEntry(buttons, text, info);
      }

      public void render(
         @NotNull GuiGraphics matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta
      ) {
         this.buttons.forEach(b -> {
            b.setY(y);
            b.render(matrices, mouseX, mouseY, tickDelta);
         });
         if (this.text != null && (!this.text.getString().contains("spacer") || !this.buttons.isEmpty())) {
            if (this.info.centered) {
               matrices.drawString(
                  font, this.text, (int)(Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2.0F - font.width(this.text) / 2.0F), y + 5, 16777215
               );
            } else {
               matrices.drawString(font, this.text, 12, y + 5, 16777215);
            }
         }
      }

      @NotNull
      public List<? extends GuiEventListener> children() {
         return this.children;
      }

      @NotNull
      public List<? extends NarratableEntry> narratables() {
         return this.children;
      }

      static {
         font = Minecraft.getInstance().font;
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

   public static class DuskConfigListWidget extends ContainerObjectSelectionList<DuskConfig.ButtonEntry> {
      Font font;

      public DuskConfigListWidget(Minecraft client, int width, int height, int y, int itemHeight, int m) {
         super(client, width, height - 68, y, m);
         this.centerListVertically = false;
         this.font = client.font;
      }

      public int getScrollbarPosition() {
         return this.width - 7;
      }

      public void addButton(List<AbstractWidget> buttons, Component text, DuskConfig.EntryInfo info) {
         this.addEntry(DuskConfig.ButtonEntry.create(buttons, text, info));
      }

      public int getRowWidth() {
         return 10000;
      }

      public Optional<AbstractWidget> getHoveredButton(double mouseX, double mouseY) {
         for (DuskConfig.ButtonEntry buttonEntry : this.children()) {
            if (!buttonEntry.buttons.isEmpty() && buttonEntry.buttons.getFirst().isMouseOver(mouseX, mouseY)) {
               return Optional.of(buttonEntry.buttons.getFirst());
            }
         }

         return Optional.empty();
      }
   }

   public static class DuskConfigScreen extends Screen {
      public final String translationPrefix;
      public final Screen parent;
      public final String modid;
      public DuskConfig.DuskConfigListWidget list;
      public boolean reload = false;

      protected DuskConfigScreen(Screen parent, String modid) {
         super(Component.literal(StringUtils.capitalize(DuskConfig.modidToName.get(modid)) + " Config"));
         this.parent = parent;
         this.modid = modid;
         this.translationPrefix = modid + ".duskconfig.";
      }

      public static Screen getScreen(Screen parent, String modid) {
         return new DuskConfig.DuskConfigScreen(parent, modid);
      }

      public void renderBackground(@NotNull GuiGraphics guiGraphics, int a, int b, float c) {
         if (this.minecraft.level == null) {
            this.renderPanorama(guiGraphics, c);
         }

         this.renderBlurredBackground(c);
      }

      public void tick() {
         super.tick();

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
            for (DuskConfig.ButtonEntry entry : this.list.children()) {
               if (entry.buttons != null && entry.buttons.size() > 1 && entry.buttons.get(1) instanceof Button button) {
                  button.active = !Objects.equals(entry.info.value.toString(), entry.info.defaultValue.toString());
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

      public void init() {
         super.init();
         if (!this.reload) {
            this.loadValues();
         }

         this.addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, button -> {
            this.loadValues();
            Objects.requireNonNull(this.minecraft).setScreen(this.parent);
         }).pos(this.width / 2 - 154, this.height - 28).size(150, 20).build());
         Button done = (Button)this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> {
            for (DuskConfig.EntryInfo infox : DuskConfig.entryHashMap.get(this.modid)) {
               if (infox.id.equals(this.modid)) {
                  try {
                     infox.field.set(null, infox.value);
                  } catch (IllegalAccessException var5x) {
                  }
               }
            }

            DuskConfig.write(this.modid);
            Objects.requireNonNull(this.minecraft).setScreen(this.parent);
         }).pos(this.width / 2 + 4, this.height - 28).size(150, 20).build());
         this.list = new DuskConfig.DuskConfigListWidget(this.minecraft, this.width, this.height, 32, this.height - 32, 25);
         this.addWidget(this.list);

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
               Component name = Component.literal(formattedName);
               Button resetButton = Button.builder(Component.literal("Reset").withStyle(ChatFormatting.RED), button -> {
                  info.value = info.defaultValue;
                  info.tempValue = info.defaultValue.toString();
                  info.index = 0;
                  double scrollAmount = this.list.getScrollAmount();
                  this.reload = true;
                  Objects.requireNonNull(this.minecraft).setScreen(this);
                  this.list.setScrollAmount(scrollAmount);
               }).pos(this.width - 205, 0).size(40, 20).build();
               if (info.widget instanceof Map.Entry<OnPress, Function<Object, Component>> widget) {
                  if (info.field.getType().isEnum()) {
                     widget.setValue(
                        value -> Component.translatable(this.translationPrefix + "enum." + info.field.getType().getSimpleName() + "." + info.value.toString())
                     );
                  }

                  this.list
                     .addButton(
                        List.of(
                           Button.builder(widget.getValue().apply(info.value), widget.getKey()).pos(this.width - 160, 0).size(150, 20).build(), resetButton
                        ),
                        name,
                        info
                     );
               } else if (info.field.getType() == List.class) {
                  if (!this.reload) {
                     info.index = 0;
                  }

                  EditBox widget = new EditBox(this.font, this.width - 160, 0, 150, 20, null);
                  widget.setMaxLength(Integer.MAX_VALUE);
                  if (info.index < ((List)info.value).size()) {
                     widget.setValue(String.valueOf(((List)info.value).get(info.index)));
                  } else {
                     widget.setValue("");
                  }

                  Predicate<String> processor = (Predicate<String>)((BiFunction)info.widget).apply(widget, done);
                  widget.setFilter(processor);
                  resetButton.setWidth(20);
                  resetButton.setMessage(Component.literal("R").withStyle(ChatFormatting.RED));
                  Button cycleButton = Button.builder(Component.literal(String.valueOf(info.index)), button -> {
                     ((List)info.value).remove("");
                     double scrollAmount = this.list.getScrollAmount();
                     this.reload = true;
                     info.index++;
                     if (info.index > ((List)info.value).size()) {
                        info.index = 0;
                     }

                     Objects.requireNonNull(this.minecraft).setScreen(this);
                     this.list.setScrollAmount(scrollAmount);
                  }).pos(this.width - 185, 0).size(20, 20).build();
                  this.list.addButton(List.of(widget, resetButton, cycleButton), name, info);
               } else if (info.widget != null) {
                  EditBox widget = new EditBox(this.font, this.width - 160, 0, 150, 20, null);
                  widget.setMaxLength(Integer.MAX_VALUE);
                  widget.setValue(info.tempValue);
                  Predicate<String> processor = (Predicate<String>)((BiFunction)info.widget).apply(widget, done);
                  widget.setFilter(processor);
                  if (info.field.getAnnotation(DuskConfig.Entry.class).isColor()) {
                     resetButton.setWidth(20);
                     resetButton.setMessage(Component.literal("R").withStyle(ChatFormatting.RED));
                     Button colorButton = Button.builder(Component.literal("⬛"), button -> {}).pos(this.width - 185, 0).size(20, 20).build();

                     try {
                        colorButton.setMessage(Component.literal("⬛").setStyle(Style.EMPTY.withColor(Color.decode(info.tempValue).getRGB())));
                     } catch (Exception var14) {
                     }

                     info.colorButton = colorButton;
                     colorButton.active = false;
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
                  EditBox label = new EditBox(this.font, this.width - 155, 0, 145, 20, null);
                  label.setValue(rangeValue);
                  label.setBordered(false);
                  label.setEditable(false);
                  this.list.addButton(List.of(label), Component.literal(""), info);
               }
            }

            this.updateResetButtons();
         }
      }

      public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
         super.render(guiGraphics, mouseX, mouseY, delta);
         if (this.minecraft == null || Objects.requireNonNull(this.minecraft).level == null) {
            guiGraphics.pose().translate(0.0F, 0.0F, -100.0F);
         }

         this.list.render(guiGraphics, mouseX, mouseY, delta);
         guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 15, 16777215);
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
      Map.Entry<EditBox, Component> error;
      Object defaultValue;
      Object value;
      String tempValue;
      boolean inLimits = true;
      String id;
      Component name;
      String description = "";
      String range = "";
      int index;
      AbstractWidget colorButton;
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
