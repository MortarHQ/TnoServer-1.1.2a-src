package dev.ftbqlang.client;

import com.mojang.math.Axis;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.ui.GuiHelper;
import dev.ftb.mods.ftblibrary.ui.ModalPanel;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.PanelScrollBar;
import dev.ftb.mods.ftblibrary.ui.SimpleTextButton;
import dev.ftb.mods.ftblibrary.ui.TextField;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.Widget;
import dev.ftb.mods.ftblibrary.ui.input.Key;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import dev.ftb.mods.ftbquests.client.gui.quests.QuestScreen;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.LanguageInfo;
import net.minecraft.network.chat.Component;

public final class LanguageSelectionPanel extends ModalPanel {
   private static final int PADDING = 10;
   private static final int ROW_HEIGHT = 24;
   private static final int ENTER_OFFSET = 26;
   private static final String[] WORDS = new String[]{
      "English",
      "中文",
      "日本語",
      "한국어",
      "Русский",
      "العربية",
      "हिन्दी",
      "Ελληνικά",
      "עברית",
      "ไทย",
      "Español",
      "Français",
      "Deutsch",
      "Italiano",
      "Português",
      "Polski",
      "Türkçe",
      "Tiếng Việt",
      "Bahasa Indonesia",
      "Українська",
      "Nederlands",
      "Svenska",
      "Suomi",
      "বাংলা"
   };
   private static final Component[] FLOATING_TEXT = Arrays.stream(WORDS).map(Component::literal).toArray(Component[]::new);
   private static final Icon DEFAULT_BACKGROUND = Icon.getIcon("ftblibrary:textures/gui/background_squares.png; color=#DCFFFFFF; tile_size=64");
   private final List<String> languages;
   private String selected;
   private final Consumer<String> onSelect;
   private final TextField title;
   private final TextField subtitle;
   private final Panel languageList;
   private final PanelScrollBar scrollbar;
   private final SimpleTextButton close;
   private boolean initialScroll = true;
   private final LanguageAnimation animation = new LanguageAnimation(System.nanoTime());
   private double frameCenterY;
   private double frameOffsetY;
   private double frameScaleY = 1.0;
   private int lastLayoutWidth = -1;
   private int lastLayoutHeight = -1;

   public LanguageSelectionPanel(QuestScreen parent, List<String> languages, String selected) {
      this(parent, languages, selected, QuestLanguageClient::selectLanguage);
   }

   public LanguageSelectionPanel(QuestScreen parent, List<String> languages, String selected, Consumer<String> onSelect) {
      super(parent);
      this.languages = List.copyOf(languages);
      this.selected = selected;
      this.onSelect = onSelect;
      this.setExtraZlevel(1250);
      this.setOnlyRenderWidgetsInside(false);
      this.title = new TextField(this).addFlags(6).setScale(1.5F).setText(Component.translatable("ftbqlang.screen.title"));
      this.subtitle = new TextField(this).addFlags(4).setText(Component.translatable("ftbqlang.screen.subtitle"));
      this.languageList = new Panel(this) {
         public void addWidgets() {
            for (int index = 0; index < LanguageSelectionPanel.this.languages.size(); index++) {
               this.add(LanguageSelectionPanel.this.new LanguageButton(this, LanguageSelectionPanel.this.languages.get(index)));
            }
         }

         public void alignWidgets() {
            for (int index = 0; index < this.widgets.size(); index++) {
               ((Widget)this.widgets.get(index)).setPosAndSize(0, index * 24, this.width, 22);
            }
         }

         public int getContentHeight() {
            return Math.max(0, this.widgets.size() * 24 - 2);
         }

         public void draw(GuiGraphics graphics, Theme theme, int x, int y, int width, int height) {
            int top = (int)Math.floor(LanguageSelectionPanel.this.transformedY(y));
            int bottom = (int)Math.ceil(LanguageSelectionPanel.this.transformedY(y + height));
            graphics.flush();
            GuiHelper.pushScissor(this.getWindow(), x, top, width, Math.max(0, bottom - top));

            try {
               super.draw(graphics, theme, x, y, width, height);
               graphics.flush();
            } finally {
               GuiHelper.popScissor(this.getWindow());
            }
         }
      };
      this.languageList.setOnlyRenderWidgetsInside(false);
      this.languageList.setScrollStep(24.0);
      this.scrollbar = new PanelScrollBar(this, this.languageList);
      this.close = new SimpleTextButton(this, Component.translatable("gui.done"), Icon.empty()) {
         public boolean renderTitleInCenter() {
            return true;
         }

         public void onClicked(MouseButton button) {
            if (button == MouseButton.LEFT) {
               LanguageSelectionPanel.this.finish();
            }
         }
      };
   }

   public void addWidgets() {
      this.add(this.title);
      this.add(this.subtitle);
      this.add(this.languageList);
      this.add(this.scrollbar);
      this.add(this.close);
   }

   public void alignWidgets() {
      int availableWidth = Math.max(80, this.getGui().getWidth() - 20);
      int availableHeight = Math.max(80, this.getGui().getHeight() - 20);
      this.setWidth(Math.min(320, availableWidth));
      int textWidth = this.width - 20;
      this.title.setMaxWidth(textWidth).reflow();
      this.title.setPosAndSize(10, 12, textWidth, this.title.getHeight());
      this.subtitle.setMaxWidth(textWidth).reflow();
      this.subtitle.setPosAndSize(10, this.title.getPosY() + this.title.getHeight() + 8, textWidth, this.subtitle.getHeight());
      int listY = this.subtitle.getPosY() + this.subtitle.getHeight() + 10;
      int desiredListHeight = Math.max(22, Math.min(190, this.languages.size() * 24 - 2));
      this.setHeight(Math.min(availableHeight, listY + desiredListHeight + 32));
      this.languageList.setPosAndSize(10, listY, textWidth - 10, Math.max(1, this.height - listY - 32));
      this.languageList.alignWidgets();
      this.scrollbar.setPosAndSize(this.width - 10 - 6, listY, 6, this.languageList.getHeight());
      this.close.setPosAndSize((this.width - Math.min(100, textWidth)) / 2, this.height - 10 - 20, Math.min(100, textWidth), 20);
      int centeredX = (this.getGui().getWidth() - this.width) / 2;
      int centeredY = (this.getGui().getHeight() - this.height) / 2;
      this.setPos(centeredX, centeredY);
      if (this.initialScroll) {
         int index = this.languages.indexOf(this.selected);
         this.scrollbar.setValue(Math.max(0, index * 24 - this.languageList.getHeight() / 2));
         this.initialScroll = false;
      } else {
         this.scrollbar.setValue(this.scrollbar.getValue());
      }
   }

   public void tick() {
      super.tick();
      this.ensureCenteredLayout();
   }

   private void ensureCenteredLayout() {
      int guiWidth = this.getGui().getWidth();
      int guiHeight = this.getGui().getHeight();
      if (guiWidth != this.lastLayoutWidth || guiHeight != this.lastLayoutHeight) {
         double scrollValue = this.scrollbar == null ? 0.0 : this.scrollbar.getValue();
         this.lastLayoutWidth = guiWidth;
         this.lastLayoutHeight = guiHeight;
         this.alignWidgets();
         if (this.scrollbar != null) {
            this.scrollbar.setValue(scrollValue);
         }
      }
   }

   private void finish() {
      if (!this.animation.isClosing()) {
         this.playClickSound();
         this.animation.beginClose(System.nanoTime());
      }
   }

   public boolean isExitComplete() {
      return this.animation.isFinished(System.nanoTime());
   }

   public double backdropOpacity() {
      return this.animation.backdropOpacity(System.nanoTime());
   }

   private double transformedY(double y) {
      return this.frameCenterY + (y - this.frameCenterY) * this.frameScaleY + this.frameOffsetY;
   }

   public void drawBackground(GuiGraphics graphics, Theme theme, int x, int y, int width, int height) {
      DEFAULT_BACKGROUND.draw(graphics, x, y, width, height);
   }

   public void draw(GuiGraphics graphics, Theme theme, int x, int y, int width, int height) {
      this.ensureCenteredLayout();
      long now = System.nanoTime();
      graphics.pose().pushPose();

      try {
         graphics.pose().translate(0.0, 0.0, -1.0);
         this.drawFloatingText(graphics, now);
         graphics.flush();
      } finally {
         graphics.pose().popPose();
      }

      this.frameCenterY = y + height / 2.0;
      this.frameOffsetY = (1.0 - this.animation.entrance(now)) * 26.0;
      this.frameScaleY = this.animation.panelScaleY(now);
      if (this.frameScaleY > 0.0) {
         graphics.pose().pushPose();

         try {
            graphics.pose().translate(0.0, this.frameCenterY + this.frameOffsetY, 0.0);
            graphics.pose().scale(1.0F, (float)this.frameScaleY, 1.0F);
            graphics.pose().translate(0.0, -this.frameCenterY, 0.0);
            super.draw(graphics, theme, x, y, width, height);
            graphics.flush();
         } finally {
            graphics.pose().popPose();
         }
      }

      int lineWidth = (int)Math.ceil(width * this.animation.lineWidth(now));
      if (lineWidth > 0) {
         int lineX = x + (width - lineWidth) / 2;
         int lineY = (int)Math.round(this.frameCenterY + this.frameOffsetY);
         graphics.fill(lineX, lineY, lineX + lineWidth, lineY + 1, -1771521);
      }
   }

   private void drawFloatingText(GuiGraphics graphics, long now) {
      Font font = Minecraft.getInstance().font;
      int screenWidth = this.getWindow().getGuiScaledWidth();
      int screenHeight = this.getWindow().getGuiScaledHeight();

      for (int index = 0; index < FLOATING_TEXT.length; index++) {
         Component word = FLOATING_TEXT[index];
         int textWidth = font.width(word);
         float scale = (float)Math.min(0.9 + index % 3 * 0.12, (screenWidth / 6.0 - 10.0) / Math.max(1, textWidth));
         scale = Math.max(0.4F, scale);
         double drawnWidth = textWidth * scale;
         double drawnHeight = 9.0F * scale;
         LanguageAnimation.WordPose pose = this.animation.word(index, screenWidth, screenHeight, drawnWidth, drawnHeight, now);
         int alpha = (int)Math.round(pose.alpha() * 255.0);
         if (alpha >= 4 && !(pose.y() > screenHeight + drawnWidth) && !(pose.y() + drawnHeight < 0.0)) {
            int tint = switch (index % 3) {
               case 0 -> 13561855;
               case 1 -> 15129087;
               default -> 16773331;
            };
            graphics.pose().pushPose();

            try {
               graphics.pose().translate(pose.x() + drawnWidth / 2.0, pose.y() + drawnHeight / 2.0, 0.0);
               graphics.pose().mulPose(Axis.ZP.rotationDegrees((float)pose.rotation()));
               graphics.pose().scale(scale, scale, 1.0F);
               graphics.drawString(font, word, -textWidth / 2, -9 / 2, alpha << 24 | tint, true);
            } finally {
               graphics.pose().popPose();
            }
         }
      }
   }

   public boolean checkMouseOver(int mouseX, int mouseY) {
      return true;
   }

   public boolean mousePressed(MouseButton button) {
      if (!this.animation.isClosing() && this.animation.entrance(System.nanoTime()) >= 1.0) {
         super.mousePressed(button);
      }

      return true;
   }

   public void onClosed() {
      QuestLanguageClient.selectorClosed((QuestScreen)this.getGui());
      super.onClosed();
   }

   public boolean keyPressed(Key key) {
      if (key.enter()) {
         this.finish();
      }

      return true;
   }

   public static Component languageName(String code) {
      LanguageInfo language = Minecraft.getInstance().getLanguageManager().getLanguage(code);
      return (Component)(language == null ? Component.literal(code) : language.toComponent());
   }

   private final class LanguageButton extends SimpleTextButton {
      private final String code;

      private LanguageButton(Panel parent, String code) {
         super(parent, LanguageSelectionPanel.languageName(code), (Icon)(code.equals(LanguageSelectionPanel.this.selected) ? Icons.ACCEPT : Icon.empty()));
         this.code = code;
      }

      public void onClicked(MouseButton button) {
         if (button == MouseButton.LEFT && !LanguageSelectionPanel.this.animation.isClosing()) {
            this.playClickSound();
            LanguageSelectionPanel.this.selected = this.code;
            LanguageSelectionPanel.this.onSelect.accept(this.code);

            for (Widget widget : LanguageSelectionPanel.this.languageList.getWidgets()) {
               if (widget instanceof LanguageSelectionPanel.LanguageButton language) {
                  language.setIcon((Icon)(language.code.equals(LanguageSelectionPanel.this.selected) ? Icons.ACCEPT : Icon.empty()));
               }
            }
         }
      }

      public void addMouseOverText(TooltipList list) {
         list.add(this.getTitle());
         list.add(Component.literal(this.code));
         if (this.code.equals(LanguageSelectionPanel.this.selected)) {
            list.add(Component.translatable("ftbqlang.screen.selected"));
         }
      }
   }
}
