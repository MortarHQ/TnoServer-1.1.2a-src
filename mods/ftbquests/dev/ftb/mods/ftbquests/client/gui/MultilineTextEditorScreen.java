package dev.ftb.mods.ftbquests.client.gui;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.serialization.JsonOps;
import dev.ftb.mods.ftblibrary.config.ColorConfig;
import dev.ftb.mods.ftblibrary.config.ConfigCallback;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.config.ImageResourceConfig;
import dev.ftb.mods.ftblibrary.config.ListConfig;
import dev.ftb.mods.ftblibrary.config.StringConfig;
import dev.ftb.mods.ftblibrary.config.ui.EditConfigScreen;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.icon.RainbowIcon;
import dev.ftb.mods.ftblibrary.ui.BaseScreen;
import dev.ftb.mods.ftblibrary.ui.ColorSelectorPanel;
import dev.ftb.mods.ftblibrary.ui.ContextMenu;
import dev.ftb.mods.ftblibrary.ui.ContextMenuItem;
import dev.ftb.mods.ftblibrary.ui.MultilineTextBox;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.PanelScrollBar;
import dev.ftb.mods.ftblibrary.ui.SimpleTextButton;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.MultilineTextBox.StringExtents;
import dev.ftb.mods.ftblibrary.ui.ScrollBar.Plane;
import dev.ftb.mods.ftblibrary.ui.input.Key;
import dev.ftb.mods.ftblibrary.ui.input.KeyModifiers;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.ui.misc.NordColors;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import dev.ftb.mods.ftblibrary.util.client.ImageComponent;
import dev.ftb.mods.ftblibrary.util.client.ImageComponent.ImageAlign;
import dev.ftb.mods.ftbquests.api.FTBQuestsAPI;
import dev.ftb.mods.ftbquests.client.FTBQuestsClient;
import dev.ftb.mods.ftbquests.client.gui.quests.QuestScreen;
import dev.ftb.mods.ftbquests.client.gui.quests.ViewQuestPanel;
import dev.ftb.mods.ftbquests.quest.QuestObject;
import dev.ftb.mods.ftbquests.quest.QuestObjectType;
import dev.ftb.mods.ftbquests.util.ConfigQuestObject;
import dev.ftb.mods.ftbquests.util.TextUtils;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.regex.Pattern;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.Util.OS;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Whence;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MultilineTextEditorScreen extends BaseScreen {
   public static final Icon LINK_ICON = Icon.getIcon(FTBQuestsAPI.rl("textures/gui/chain_link.png")).withPadding(2);
   public static final Icon CLEAR_FORMATTING_ICON = Icon.getIcon(FTBQuestsAPI.rl("textures/gui/eraser.png")).withPadding(2);
   private static final Pattern STRIP_FORMATTING_PATTERN = Pattern.compile("(?i)([&\\u00A7])([0-9A-FK-ORZ]|#[0-9A-Fa-f]{6})");
   private static final int MAX_UNDO = 10;
   protected static final String LINK_TEXT_TEMPLATE = "{ \"text\": \"%s\", \"underlined\": true, \"clickEvent\": { \"action\": \"change_page\", \"value\": \"%016X\" } }";
   private final Component title;
   private final ListConfig<String, StringConfig> config;
   private final ConfigCallback callback;
   private final Panel outerPanel;
   private final Panel toolbarPanel;
   private final Panel textBoxPanel;
   private final MultilineTextBox textBox;
   private final PanelScrollBar scrollBar;
   private long ticksOpen = 0L;
   private final String initialText;
   private long lastChange = 0L;
   private final Deque<MultilineTextEditorScreen.HistoryElement> redoStack = new ArrayDeque<>();
   private final Map<Integer, Runnable> hotKeys = Map.of(
      66,
      () -> this.insertFormatting(ChatFormatting.BOLD),
      73,
      () -> this.insertFormatting(ChatFormatting.ITALIC),
      85,
      () -> this.insertFormatting(ChatFormatting.UNDERLINE),
      83,
      () -> this.insertFormatting(ChatFormatting.STRIKETHROUGH),
      82,
      this::resetFormatting,
      80,
      () -> this.insertAtEndOfLine("\n{@pagebreak}"),
      77,
      this::openImageSelector,
      90,
      this::undoLast,
      76,
      this::openLinkInsert,
      74,
      this::convertToJson
   );

   public MultilineTextEditorScreen(Component title, ListConfig<String, StringConfig> config, ConfigCallback callback) {
      this.title = title;
      this.config = config;
      this.callback = callback;
      this.outerPanel = new MultilineTextEditorScreen.OuterPanel(this);
      this.toolbarPanel = new MultilineTextEditorScreen.ToolbarPanel(this.outerPanel);
      this.textBoxPanel = new MultilineTextEditorScreen.TextBoxPanel(this.outerPanel);
      this.textBox = new MultilineTextBox(this.textBoxPanel);
      this.textBox.setText(String.join("\n", (Iterable<? extends CharSequence>)config.getValue()));
      this.textBox.setFocused(true);
      this.textBox.setValueListener(this::onValueChanged);
      this.textBox.seekCursor(Whence.ABSOLUTE, 0);
      this.redoStack.addLast(new MultilineTextEditorScreen.HistoryElement(this.textBox.getText(), this.textBox.cursorPos()));
      this.scrollBar = new PanelScrollBar(this.outerPanel, Plane.VERTICAL, this.textBoxPanel);
      this.scrollBar.setScrollStep(this.getTheme().getFontHeight());
      this.initialText = this.textBox.getText();
   }

   private void onValueChanged(String newValue) {
      this.lastChange = Minecraft.getInstance().level.getGameTime();
   }

   public void tick() {
      super.tick();
      this.ticksOpen++;
      if (this.lastChange > 0L && Minecraft.getInstance().level.getGameTime() - this.lastChange > 5L) {
         this.redoStack.addLast(new MultilineTextEditorScreen.HistoryElement(this.textBox.getText(), this.textBox.cursorPos()));

         while (this.redoStack.size() > 10) {
            this.redoStack.removeFirst();
         }

         this.lastChange = 0L;
      }
   }

   public boolean onInit() {
      this.setWidth(this.getWindow().getGuiScaledWidth() / 5 * 4);
      this.setHeight(this.getWindow().getGuiScaledHeight() / 5 * 4);
      this.ticksOpen = 0L;
      return true;
   }

   public void addWidgets() {
      this.add(this.outerPanel);
   }

   public void alignWidgets() {
      this.outerPanel.setPosAndSize(0, 0, this.width, this.height);
      this.toolbarPanel.setPosAndSize(2, 2, this.width - 4, 18);
      this.toolbarPanel.alignWidgets();
      this.textBoxPanel.setPosAndSize(2, this.toolbarPanel.height + 4, this.width - 18, this.height - this.toolbarPanel.height - 6);
      this.textBoxPanel.alignWidgets();
      this.scrollBar.setPosAndSize(this.width - 14, this.textBoxPanel.posY, 12, this.height - this.textBoxPanel.posY - 4);
   }

   public void drawBackground(GuiGraphics matrixStack, Theme theme, int x, int y, int w, int h) {
      super.drawBackground(matrixStack, theme, x, y, w, h);
      theme.drawString(matrixStack, this.title, x + (this.width - theme.getStringWidth(this.title)) / 2, y - theme.getFontHeight() - 2, 2);
   }

   public boolean keyPressed(Key key) {
      if (key.esc()) {
         this.cancel();
         return true;
      } else if (key.enter() && Screen.hasShiftDown()) {
         this.saveAndExit();
         return true;
      } else if (this.textBox.isFocused()) {
         this.textBox.keyPressed(key);
         return true;
      } else {
         return false;
      }
   }

   public void keyReleased(Key key) {
      this.executeHotkey(key.keyCode, true);
   }

   private void executeHotkey(int keycode, boolean checkModifier) {
      if (this.hotKeys.containsKey(keycode) && (!checkModifier || isHotKeyModifierPressed(keycode))) {
         this.hotKeys.get(keycode).run();
         this.textBox.setFocused(true);
      }
   }

   public boolean charTyped(char c, KeyModifiers modifiers) {
      if (this.ticksOpen < 2L) {
         return true;
      }

      int keyCode = Character.toUpperCase(c);
      return isHotKeyModifierPressed(keyCode) && this.hotKeys.containsKey(keyCode) ? false : super.charTyped(c, modifiers);
   }

   private static boolean isHotKeyModifierPressed(int keycode) {
      return keycode != 90 && Util.getPlatform() != OS.OSX ? Screen.hasAltDown() : Screen.hasControlDown();
   }

   public Theme getTheme() {
      return FTBQuestsTheme.INSTANCE;
   }

   private void replaceText(StringExtents extents, String replacement) {
      this.textBox.setSelecting(false);
      this.textBox.seekCursor(Whence.ABSOLUTE, extents.start());
      this.textBox.setSelecting(true);
      this.textBox.seekCursor(Whence.ABSOLUTE, extents.end());
      this.textBox.insertText(replacement);
      this.textBox.setSelecting(false);
   }

   private void convertToJson() {
      if (Screen.hasShiftDown()) {
         List<String> newDoc = new ArrayList<>();

         for (String line : this.textBox.getText().split("\\n")) {
            if (line.startsWith("{")) {
               newDoc.add(line);
            } else {
               Component parsed = TextUtils.parseRawText(line, FTBQuestsClient.holderLookup());
               ComponentSerialization.CODEC.encodeStart(JsonOps.INSTANCE, parsed).ifSuccess(json -> newDoc.add(this.canonicalizeJson(json).toString()));
            }
         }

         this.textBox.setText(String.join("\n", newDoc));
      } else {
         StringExtents lineExtents = this.getPhysicalLineExtents();
         if (lineExtents.start() > this.textBox.getText().length() || lineExtents.start() >= lineExtents.end()) {
            return;
         }

         String thisLine = this.textBox.getText().substring(lineExtents.start(), lineExtents.end());
         if (!thisLine.startsWith("{")) {
            Component parsed = TextUtils.parseRawText(thisLine, FTBQuestsClient.holderLookup());
            ComponentSerialization.CODEC
               .encodeStart(JsonOps.INSTANCE, parsed)
               .ifSuccess(json -> this.replaceText(lineExtents, this.canonicalizeJson(json).toString()));
         }
      }
   }

   private JsonElement canonicalizeJson(JsonElement json) {
      return json instanceof JsonPrimitive p ? (JsonElement)Util.make(new JsonObject(), o -> {
         o.addProperty("text", "");
         o.add("extra", (JsonElement)Util.make(new JsonArray(), a -> a.add(p.getAsString())));
      }) : json;
   }

   private boolean looksLikeJsonText(String line) {
      return (line.startsWith("{") || line.startsWith("["))
         && (line.endsWith("}") || line.endsWith("]"))
         && !line.startsWith("{image")
         && !line.startsWith("{@");
   }

   private void openLinkInsert() {
      ConfigQuestObject<QuestObject> config = new ConfigQuestObject<>(QuestObjectType.QUEST.or(QuestObjectType.QUEST_LINK));
      new SelectQuestObjectScreen<>(config, accepted -> {
         int pos = this.textBox.cursorPos();
         if (accepted) {
            this.doLinkInsertion(((QuestObject)config.getValue()).id);
         }

         this.run();
         this.textBox.seekCursor(Whence.ABSOLUTE, pos);
      }).openGui();
   }

   private void doLinkInsertion(long questID) {
      String text = this.textBox.getSelectedText();
      if (!text.contains("\n")) {
         StringExtents lineExtents = this.getPhysicalLineExtents();
         StringExtents selectionExtents = this.textBox.getSelected();
         if (lineExtents.start() == lineExtents.end() || selectionExtents.start() == selectionExtents.end()) {
            return;
         }

         String thisLine = this.textBox.getText().substring(lineExtents.start(), lineExtents.end());
         if (thisLine.startsWith("[") && thisLine.endsWith("]")) {
            this.errorToPlayer("ftbquests.gui.error.already_component");
            return;
         }

         List<String> parts = List.of(
            this.textBox.getText().substring(lineExtents.start(), selectionExtents.start()),
            this.textBox.getText().substring(selectionExtents.start(), selectionExtents.end()),
            this.textBox.getText().substring(selectionExtents.end(), lineExtents.end())
         );
         StringBuilder builder = new StringBuilder("[ ");
         if (!parts.get(0).isEmpty()) {
            builder.append("\"").append(parts.get(0)).append("\", ");
         }

         builder.append(
            String.format(
               "{ \"text\": \"%s\", \"underlined\": true, \"clickEvent\": { \"action\": \"change_page\", \"value\": \"%016X\" } }",
               parts.get(1).isEmpty() ? "EDIT HERE" : parts.get(1),
               questID
            )
         );
         if (!parts.get(2).isEmpty()) {
            builder.append(", ").append("\"").append(parts.get(2)).append("\"");
         }

         builder.append(" ]");
         this.replaceText(lineExtents, builder.toString());
      } else {
         this.errorToPlayer("ftbquests.gui.error.selection_multiple_lines");
      }
   }

   private StringExtents getPhysicalLineExtents() {
      int pos = 0;
      String thisLine = "";

      for (String line : this.textBox.getText().split("\\n")) {
         if (this.textBox.cursorPos() >= pos && this.textBox.cursorPos() < pos + line.length()) {
            thisLine = line;
            break;
         }

         pos += line.length() + 1;
      }

      return new StringExtents(pos, pos + thisLine.length());
   }

   private void errorToPlayer(String msg, Object... args) {
      QuestScreen.displayError(Component.translatable(String.format(msg, args)).withStyle(ChatFormatting.RED));
   }

   private void openImageSelector() {
      int cursor = this.textBox.cursorPos();
      ImageComponent component = new ImageComponent();
      ConfigGroup group = new ConfigGroup("ftbquests.gui.image", accepted -> {
         this.openGui();
         if (accepted) {
            this.textBox.seekCursor(Whence.ABSOLUTE, cursor);
            this.insertAtEndOfLine("\n" + component);
         }
      });
      ((ImageResourceConfig)group.add(
            "image",
            new ImageResourceConfig(),
            ImageResourceConfig.getResourceLocation(component.getImage()),
            v -> component.setImage(Icon.getIcon(v)),
            ImageResourceConfig.NONE
         ))
         .setOrder(-127);
      group.addInt("width", component.getWidth(), component::setWidth, 0, 1, 1000);
      group.addInt("height", component.getHeight(), component::setHeight, 0, 1, 1000);
      group.addEnum("align", component.getAlign(), component::setAlign, ImageAlign.NAME_MAP, ImageAlign.CENTER);
      group.addBool("fit", component.isFit(), component::setFit, false);
      new EditConfigScreen(group).openGui();
   }

   private void cancel() {
      if (!this.textBox.getText().equals(this.initialText)) {
         this.getGui().openYesNo(Component.translatable("ftbquests.gui.confirm_esc"), Component.empty(), () -> this.callback.save(false));
      } else {
         this.callback.save(false);
      }
   }

   private void saveAndExit() {
      ((List)this.config.getValue()).clear();
      Collections.addAll((Collection<? super String>)this.config.getValue(), this.textBox.getText().split("\n"));
      this.closeGui();
      this.callback.save(true);
   }

   private void insertFormatting(ChatFormatting c) {
      this.insertFormatting(String.valueOf(c.getChar()));
   }

   private void insertFormatting(ColorConfig color) {
      this.insertFormatting(((Color4I)color.getValue()).toString());
   }

   private void insertFormatting(String formatting) {
      if (this.textBox.hasSelection()) {
         this.textBox.insertText("&" + formatting + this.textBox.getSelectedText() + "&r");
      } else {
         this.textBox.insertText("&" + formatting);
      }

      this.textBox.setFocused(true);
   }

   private void resetFormatting() {
      if (this.textBox.hasSelection()) {
         this.textBox.insertText(stripFormatting(this.textBox.getSelectedText()));
      } else {
         this.textBox.insertText("&r");
      }
   }

   private static String stripFormatting(@NotNull String selectedText) {
      return STRIP_FORMATTING_PATTERN.matcher(selectedText).replaceAll("");
   }

   private void insertAtEndOfLine(String toInsert) {
      this.textBox.keyPressed(new Key(269, -1, 0));
      this.textBox.insertText(toInsert);
   }

   private void undoLast() {
      if (this.redoStack.size() > 1) {
         this.redoStack.removeLast();
         MultilineTextEditorScreen.HistoryElement h = this.redoStack.peekLast();
         this.textBox.setValueListener(s -> {});
         this.textBox.setText(Objects.requireNonNull(h).text());
         this.textBox.setValueListener(this::onValueChanged);
         this.textBox.setSelecting(false);
         this.textBox.seekCursor(Whence.ABSOLUTE, h.cursorPos());
      }
   }

   private record HistoryElement(@NotNull String text, int cursorPos) {
   }

   private class OuterPanel extends Panel {
      public OuterPanel(MultilineTextEditorScreen screen) {
         super(screen);
      }

      public void drawBackground(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
         theme.drawPanelBackground(graphics, x, y, w, h);
      }

      public void addWidgets() {
         this.addAll(
            List.of(MultilineTextEditorScreen.this.toolbarPanel, MultilineTextEditorScreen.this.textBoxPanel, MultilineTextEditorScreen.this.scrollBar)
         );
      }

      public void alignWidgets() {
      }
   }

   private class TextBoxPanel extends Panel {
      private int cursorPos;
      private int lastWidth = -1;

      public TextBoxPanel(Panel outerPanel) {
         super(outerPanel);
      }

      public void addWidgets() {
         this.add(MultilineTextEditorScreen.this.textBox);
      }

      public void refreshWidgets() {
         this.cursorPos = MultilineTextEditorScreen.this.textBox.cursorPos();
         super.refreshWidgets();
      }

      public void alignWidgets() {
         int newWidth = this.width - 3;
         if (newWidth != this.lastWidth) {
            this.lastWidth = newWidth;
            MultilineTextEditorScreen.this.textBox.setWidth(newWidth);
            this.setScrollY(0.0);
         }

         MultilineTextEditorScreen.this.textBox.seekCursor(Whence.ABSOLUTE, this.cursorPos);
      }

      public boolean mousePressed(MouseButton button) {
         boolean res = super.mousePressed(button);
         MultilineTextEditorScreen.this.textBox.setFocused(this.isMouseOver());
         return res;
      }
   }

   private static class ToolbarButton extends SimpleTextButton {
      private final Runnable onClick;
      private final List<Component> tooltip = new ArrayList<>();
      private boolean visible = true;
      private BooleanSupplier activePredicate = () -> true;

      public ToolbarButton(Panel panel, Component txt, Icon icon, Runnable onClick) {
         super(panel, txt, icon);
         this.onClick = onClick;
      }

      public ToolbarButton(Panel panel, Component txt, Runnable onClick) {
         this(panel, txt, Color4I.empty(), onClick);
      }

      public Component getTitle() {
         return (Component)(this.activePredicate.getAsBoolean() ? super.getTitle() : super.getTitle().copy().withStyle(ChatFormatting.DARK_GRAY));
      }

      public void setVisible(boolean visible) {
         this.visible = visible;
      }

      public void onClicked(MouseButton button) {
         if (this.visible && this.activePredicate.getAsBoolean()) {
            this.onClick.run();
         }
      }

      public void addMouseOverText(TooltipList list) {
         if (this.getGui().getTheme().getStringWidth(this.title) > 0) {
            super.addMouseOverText(list);
         }

         if (this.visible) {
            this.tooltip.forEach(list::add);
         }
      }

      public void draw(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
         if (this.visible) {
            super.draw(graphics, theme, x, y, w, h);
         }
      }

      public MultilineTextEditorScreen.ToolbarButton withTooltip(Component... lines) {
         this.tooltip.addAll(Arrays.asList(lines));
         return this;
      }

      public MultilineTextEditorScreen.ToolbarButton withActivePredicate(BooleanSupplier predicate) {
         this.activePredicate = predicate;
         return this;
      }
   }

   private class ToolbarPanel extends Panel {
      private final MultilineTextEditorScreen.ToolbarButton acceptButton = new MultilineTextEditorScreen.ToolbarButton(
            this, Component.translatable("gui.accept"), Icons.ACCEPT, MultilineTextEditorScreen.this::saveAndExit
         )
         .withTooltip(hotkey("Enter", "Shift"));
      private final MultilineTextEditorScreen.ToolbarButton cancelButton = new MultilineTextEditorScreen.ToolbarButton(
            this, Component.translatable("gui.cancel"), Icons.CANCEL, MultilineTextEditorScreen.this::cancel
         )
         .withTooltip(hotkey("Escape", null));
      private final MultilineTextEditorScreen.ToolbarButton boldButton = new MultilineTextEditorScreen.ToolbarButton(
            this, Component.literal("B").withStyle(ChatFormatting.BOLD), () -> MultilineTextEditorScreen.this.executeHotkey(66, false)
         )
         .withTooltip(hotkey("B", "Alt"));
      private final MultilineTextEditorScreen.ToolbarButton italicButton = new MultilineTextEditorScreen.ToolbarButton(
            this, Component.literal("I").withStyle(ChatFormatting.ITALIC), () -> MultilineTextEditorScreen.this.executeHotkey(73, false)
         )
         .withTooltip(hotkey("I", "Alt"));
      private final MultilineTextEditorScreen.ToolbarButton underlineButton = new MultilineTextEditorScreen.ToolbarButton(
            this, Component.literal("U").withStyle(ChatFormatting.UNDERLINE), () -> MultilineTextEditorScreen.this.executeHotkey(85, false)
         )
         .withTooltip(hotkey("U", "Alt"));
      private final MultilineTextEditorScreen.ToolbarButton strikethroughButton = new MultilineTextEditorScreen.ToolbarButton(
            this, Component.literal("S").withStyle(ChatFormatting.STRIKETHROUGH), () -> MultilineTextEditorScreen.this.executeHotkey(83, false)
         )
         .withTooltip(hotkey("S", "Alt"));
      private final MultilineTextEditorScreen.ToolbarButton colorButton = new MultilineTextEditorScreen.ToolbarButton(
         this, Component.empty(), Icons.COLOR_RGB.withPadding(2), this::openColorContextMenu
      );
      private final MultilineTextEditorScreen.ToolbarButton resetButton;
      private final MultilineTextEditorScreen.ToolbarButton pageBreakButton;
      private final MultilineTextEditorScreen.ToolbarButton imageButton;
      private final MultilineTextEditorScreen.ToolbarButton undoButton;
      private final MultilineTextEditorScreen.ToolbarButton linkButton = new MultilineTextEditorScreen.ToolbarButton(
            this, Component.empty(), MultilineTextEditorScreen.LINK_ICON, () -> MultilineTextEditorScreen.this.executeHotkey(76, false)
         )
         .withTooltip(Component.translatable("ftbquests.gui.insert_link"), hotkey("L", "Alt"))
         .withActivePredicate(this::isOkForLinkInsertion);
      private final MultilineTextEditorScreen.ToolbarButton convertButton;

      public ToolbarPanel(Panel outerPanel) {
         super(outerPanel);
         this.resetButton = new MultilineTextEditorScreen.ToolbarButton(
               this, Component.empty(), MultilineTextEditorScreen.CLEAR_FORMATTING_ICON, () -> MultilineTextEditorScreen.this.executeHotkey(82, false)
            )
            .withTooltip(Component.translatable("ftbquests.gui.clear_formatting"), hotkey("R", "Alt"));
         this.pageBreakButton = new MultilineTextEditorScreen.ToolbarButton(
               this, Component.empty(), ViewQuestPanel.PAGEBREAK_ICON.withPadding(2), () -> MultilineTextEditorScreen.this.executeHotkey(80, false)
            )
            .withTooltip(Component.translatable("ftbquests.gui.page_break"), hotkey("P", "Alt"));
         this.imageButton = new MultilineTextEditorScreen.ToolbarButton(
               this, Component.empty(), Icons.ART.withPadding(2), () -> MultilineTextEditorScreen.this.executeHotkey(77, false)
            )
            .withTooltip(Component.translatable("ftbquests.chapter.image"), hotkey("M", "Alt"));
         this.undoButton = new MultilineTextEditorScreen.ToolbarButton(
               this, Component.empty(), Icons.REFRESH, () -> MultilineTextEditorScreen.this.executeHotkey(90, false)
            )
            .withTooltip(Component.translatable("ftbquests.gui.undo"), hotkey("Z", "Ctrl"));
         this.convertButton = new MultilineTextEditorScreen.ToolbarButton(
               this, Component.literal("{}"), Icon.empty(), () -> MultilineTextEditorScreen.this.executeHotkey(74, false)
            )
            .withTooltip(Component.translatable("ftbquests.gui.convert_json"), Component.translatable("ftbquests.gui.convert_json.2"), hotkey("J", "Alt"));
      }

      private boolean isOkForLinkInsertion() {
         return MultilineTextEditorScreen.this.textBox.hasSelection() && !MultilineTextEditorScreen.this.textBox.getSelectedText().contains("\n");
      }

      private static Component hotkey(String key, @Nullable String modifier) {
         boolean isMac = Util.getPlatform() == OS.OSX;
         String adaptedModifier = modifier != null && modifier.equalsIgnoreCase("alt") ? (isMac ? "Ctrl" : "Alt") : modifier;
         String modifierDisplay = modifier == null ? "" : adaptedModifier + " + ";
         return Component.literal("[" + modifierDisplay + key + "]").withStyle(ChatFormatting.DARK_GRAY);
      }

      private void openColorContextMenu() {
         List<ContextMenuItem> items = new ArrayList<>();

         for (ChatFormatting cf : ChatFormatting.values()) {
            if (cf.getColor() != null) {
               items.add(new ContextMenuItem(Component.empty(), Color4I.rgb(cf.getColor()), b -> MultilineTextEditorScreen.this.insertFormatting(cf)));
            }
         }

         ColorConfig colorConfig = new ColorConfig();
         items.add(new ContextMenuItem(Component.empty(), Icons.COLOR_HSB, btn -> ColorSelectorPanel.popupAtMouse(this.parent.getGui(), colorConfig, b -> {
            if (b) {
               MultilineTextEditorScreen.this.insertFormatting(colorConfig);
            }
         })));
         items.add(new ContextMenuItem(Component.empty(), new RainbowIcon(), btn -> MultilineTextEditorScreen.this.insertFormatting("z")));
         ContextMenu cMenu = new ContextMenu(MultilineTextEditorScreen.this, items);
         cMenu.setMaxRows(4);
         cMenu.setDrawVerticalSeparators(false);
         MultilineTextEditorScreen.this.openContextMenu(cMenu);
      }

      public void tick() {
         this.undoButton.setVisible(MultilineTextEditorScreen.this.redoStack.size() > 1);
      }

      public void addWidgets() {
         this.addAll(
            List.of(
               this.acceptButton,
               this.cancelButton,
               this.boldButton,
               this.italicButton,
               this.underlineButton,
               this.strikethroughButton,
               this.colorButton,
               this.linkButton,
               this.resetButton,
               this.pageBreakButton,
               this.imageButton,
               this.undoButton,
               this.convertButton
            )
         );
      }

      public void alignWidgets() {
         this.acceptButton.setPosAndSize(1, 1, 16, 16);
         this.boldButton.setPosAndSize(27, 1, 16, 16);
         this.italicButton.setPosAndSize(43, 1, 16, 16);
         this.underlineButton.setPosAndSize(59, 1, 16, 16);
         this.strikethroughButton.setPosAndSize(75, 1, 16, 16);
         this.colorButton.setPosAndSize(91, 1, 16, 16);
         this.linkButton.setPosAndSize(107, 1, 16, 16);
         this.resetButton.setPosAndSize(123, 1, 16, 16);
         this.pageBreakButton.setPosAndSize(149, 1, 16, 16);
         this.imageButton.setPosAndSize(165, 1, 16, 16);
         this.convertButton.setPosAndSize(181, 1, 16, 16);
         this.undoButton.setPosAndSize(207, 1, 16, 16);
         this.cancelButton.setPosAndSize(this.width - 17, 1, 16, 16);
      }

      public void drawBackground(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
         NordColors.POLAR_NIGHT_0.draw(graphics, x, y, w, h);
         theme.drawPanelBackground(graphics, x, y, w, h);
      }
   }
}
