package dev.ftb.mods.ftbquests.client.gui.quests;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftblibrary.config.StringConfig;
import dev.ftb.mods.ftblibrary.config.ui.EditStringConfigOverlay;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.ui.Button;
import dev.ftb.mods.ftblibrary.ui.ContextMenuItem;
import dev.ftb.mods.ftblibrary.ui.GuiHelper;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.Widget;
import dev.ftb.mods.ftblibrary.ui.WidgetLayout;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import dev.ftb.mods.ftblibrary.util.client.PositionedIngredient;
import dev.ftb.mods.ftbquests.client.ClientQuestFile;
import dev.ftb.mods.ftbquests.client.FTBQuestsClient;
import dev.ftb.mods.ftbquests.client.FTBQuestsClientConfig;
import dev.ftb.mods.ftbquests.client.gui.ChangeChapterGroupScreen;
import dev.ftb.mods.ftbquests.client.gui.ContextMenuBuilder;
import dev.ftb.mods.ftbquests.client.gui.CustomToast;
import dev.ftb.mods.ftbquests.net.CreateObjectMessage;
import dev.ftb.mods.ftbquests.net.MoveChapterGroupMessage;
import dev.ftb.mods.ftbquests.net.MoveChapterMessage;
import dev.ftb.mods.ftbquests.quest.Chapter;
import dev.ftb.mods.ftbquests.quest.ChapterGroup;
import dev.ftb.mods.ftbquests.quest.theme.property.ThemeProperties;
import dev.ftb.mods.ftbquests.quest.translation.TranslationKey;
import dev.ftb.mods.ftbquests.util.TextUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.Mth;

public class ChapterPanel extends Panel {
   public static final Icon ARROW_COLLAPSED = Icon.getIcon("ftbquests:textures/gui/arrow_collapsed.png");
   public static final Icon ARROW_EXPANDED = Icon.getIcon("ftbquests:textures/gui/arrow_expanded.png");
   private static final Pattern NON_EMPTY_PAT = Pattern.compile("^.+$");
   private final QuestScreen questScreen;
   boolean expanded = this.isPinned();
   int curX;
   int prevX;

   public ChapterPanel(Panel panel) {
      super(panel);
      this.questScreen = (QuestScreen)panel.getGui();
   }

   public void addWidgets() {
      this.add(new ChapterPanel.ModpackButton(this, this.questScreen.file));
      boolean canEdit = this.questScreen.file.canEdit();

      for (Chapter chapter : this.questScreen.file.getDefaultChapterGroup().getVisibleChapters(this.questScreen.file.selfTeamData)) {
         this.add(new ChapterPanel.ChapterButton(this, chapter));
      }

      this.questScreen.file.forAllChapterGroups(group -> {
         if (!group.isDefaultGroup()) {
            ChapterPanel.ChapterGroupButton button = new ChapterPanel.ChapterGroupButton(this, group);
            if (canEdit || !button.visibleChapters.isEmpty()) {
               this.add(button);
               if (!group.isGuiCollapsed()) {
                  button.visibleChapters.forEach(chapterx -> this.add(new ChapterPanel.ChapterButton(this, chapterx)));
               }
            }
         }
      });
   }

   public void alignWidgets() {
      int wd = 100;

      for (Widget w : this.widgets) {
         wd = Math.min(Math.max(wd, ((ChapterPanel.ListButton)w).getActualWidth(this.questScreen)), 800);
      }

      this.setPosAndSize(!this.expanded && !this.isPinned() ? -wd : 0, 0, wd, this.questScreen.height);

      for (Widget w : this.widgets) {
         w.setWidth(wd);
      }

      this.align(WidgetLayout.VERTICAL);
      if (this.getContentHeight() <= this.height) {
         this.setScrollY(0.0);
      }

      this.curX = this.expanded ? 0 : -this.width;
   }

   public void tick() {
      super.tick();
      this.prevX = this.curX;
      if (this.expanded && this.curX < 0) {
         this.curX = Math.min(this.curX + 40, 0);
      } else if (!this.expanded && this.curX > -this.width) {
         this.curX = Math.max(this.curX - 40, -this.width);
      }
   }

   public void updateMouseOver(int mouseX, int mouseY) {
      super.updateMouseOver(mouseX, mouseY);
      if (this.expanded && !this.isPinned() && !this.isMouseOver()) {
         this.setExpanded(false);
      }
   }

   public boolean shouldDraw() {
      return !this.questScreen.isViewingQuest() && super.shouldDraw();
   }

   public int getX() {
      return Mth.lerpInt(Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true), this.prevX, this.curX);
   }

   public void drawBackground(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
      ThemeProperties.CHAPTER_PANEL_BACKGROUND.get().draw(graphics, x, y, w, h);
   }

   public void onClosed() {
      super.onClosed();
      if (!this.isPinned()) {
         this.curX = -this.width;
      }
   }

   public void draw(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
      graphics.pose().pushPose();
      graphics.pose().translate(0.0F, 0.0F, 900.0F);
      RenderSystem.enableDepthTest();
      super.draw(graphics, theme, x, y, w, h);
      graphics.pose().popPose();
   }

   public void setExpanded(boolean b) {
      this.expanded = b;
   }

   boolean isPinned() {
      return (Boolean)FTBQuestsClientConfig.CHAPTER_PANEL_PINNED.get();
   }

   public static class ChapterButton extends ChapterPanel.ListButton {
      private final Chapter chapter;
      private final List<? extends Component> description;
      private final boolean xlateWarningTitle;
      private final boolean xlateWarningSubtitle;

      public ChapterButton(ChapterPanel panel, Chapter c) {
         super(panel, c.getTitle(), c.getIcon());
         this.chapter = c;
         this.description = this.chapter
            .getRawSubtitle()
            .stream()
            .map(line -> TextUtils.parseRawText(line, this.chapter.holderLookup()).copy().withStyle(ChatFormatting.GRAY))
            .toList();
         this.xlateWarningTitle = (Boolean)FTBQuestsClientConfig.HILITE_MISSING.get()
            && this.chapter.getQuestFile().getTranslationManager().hasMissingTranslation(this.chapter, TranslationKey.TITLE);
         this.xlateWarningSubtitle = (Boolean)FTBQuestsClientConfig.HILITE_MISSING.get()
            && this.chapter.getQuestFile().getTranslationManager().hasMissingTranslation(this.chapter, TranslationKey.CHAPTER_SUBTITLE);
      }

      public void onClicked(MouseButton button) {
         if (this.chapterPanel.questScreen.file.canEdit() || this.chapter.hasAnyVisibleChildren()) {
            this.playClickSound();
            if (this.chapterPanel.questScreen.file.canEdit() && button.isLeft()) {
               if (isKeyDown(342)) {
                  this.chapter.onEditButtonClicked(this.chapterPanel.questScreen);
               } else if (isKeyDown(346)) {
                  FTBQuestsClient.copyToClipboard(this.chapter);
                  Minecraft.getInstance()
                     .getToasts()
                     .addToast(
                        new CustomToast(Component.translatable("ftbquests.quest.copied"), Icons.INFO, Component.literal(this.chapter.getTitle().getString()))
                     );
               } else if (this.chapterPanel.questScreen.selectedChapter != this.chapter) {
                  this.chapterPanel.questScreen.open(this.chapter, false);
                  this.chapter.getAutofocus().ifPresent(this.chapterPanel.questScreen::scrollTo);
               }
            } else if (this.chapterPanel.questScreen.selectedChapter != this.chapter) {
               this.chapterPanel.questScreen.open(this.chapter, false);
               this.chapter.getAutofocus().ifPresent(this.chapterPanel.questScreen::scrollTo);
            }
         }

         if (this.chapterPanel.questScreen.file.canEdit() && button.isRight()) {
            ContextMenuBuilder.create(this.chapter, this.chapterPanel.questScreen)
               .insertAtTop(
                  List.of(
                     new ContextMenuItem(
                           Component.translatable("gui.move"),
                           ThemeProperties.MOVE_UP_ICON.get(),
                           b -> NetworkManager.sendToServer(new MoveChapterMessage(this.chapter.id, true))
                        )
                        .setEnabled(this.chapter.getIndex() > 0)
                        .setCloseMenu(false),
                     new ContextMenuItem(
                           Component.translatable("gui.move"),
                           ThemeProperties.MOVE_DOWN_ICON.get(),
                           b -> NetworkManager.sendToServer(new MoveChapterMessage(this.chapter.id, false))
                        )
                        .setEnabled(this.chapter.getIndex() < this.chapter.getGroup().getChapters().size() - 1)
                        .setCloseMenu(false),
                     new ContextMenuItem(
                        Component.translatable("ftbquests.gui.change_group"),
                        Icons.COLOR_RGB,
                        b -> new ChangeChapterGroupScreen(this.chapter, this.chapterPanel.questScreen).openGui()
                     )
                  )
               )
               .openContextMenu(this.chapterPanel.questScreen);
         }
      }

      public void draw(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
         GuiHelper.setupDrawing();
         if (this.xlateWarningTitle || this.xlateWarningSubtitle) {
            Color4I.RED.withAlpha(40).draw(graphics, x, y, w, h);
         }

         if (this.chapterPanel.questScreen.selectedChapter != null && this.chapter.id == this.chapterPanel.questScreen.selectedChapter.id) {
            GuiHelper.drawGradientRect(graphics, x + 1, y, w - 2, h, ThemeProperties.SELECTED_HILITE_1.get(), ThemeProperties.SELECTED_HILITE_2.get());
            GuiHelper.drawHollowRect(graphics, x + 1, y, w - 2, h, Color4I.GRAY.withAlpha(192), false);
         } else if (this.isMouseOver()) {
            Color4I.WHITE.withAlpha(40).draw(graphics, x + 1, y, w - 2, h);
         }

         Color4I c = this.chapter.getProgressColor(this.chapterPanel.questScreen.file.selfTeamData, !this.isMouseOver());
         int xOff = this.chapter.getGroup().isDefaultGroup() ? 0 : 7;
         this.icon.draw(graphics, x + 2 + xOff, y + 1, 12, 12);
         MutableComponent text = Component.literal("").append(this.title).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(c.rgb())));
         theme.drawString(graphics, text, x + 16 + xOff, y + 3);
         GuiHelper.setupDrawing();
         if (!this.chapter.hasAnyVisibleChildren()) {
            ThemeProperties.CLOSE_ICON.get().draw(graphics, x + w - 12, y + 3, 8, 8);
         } else if (this.chapterPanel.questScreen.file.selfTeamData.hasUnclaimedRewards(Minecraft.getInstance().player.getUUID(), this.chapter)) {
            ThemeProperties.ALERT_ICON.get().draw(graphics, x + w - 12, y + 3, 8, 8);
         }
      }

      @Override
      public void addMouseOverText(TooltipList list) {
         this.chapterPanel.questScreen.addInfoTooltip(list, this.chapter);

         for (Component s : this.description) {
            list.add(s);
         }

         if (this.xlateWarningTitle) {
            ClientQuestFile.addTranslationWarning(list, TranslationKey.TITLE);
         }

         if (this.xlateWarningSubtitle) {
            ClientQuestFile.addTranslationWarning(list, TranslationKey.CHAPTER_SUBTITLE);
         }
      }

      @Override
      public int getActualWidth(QuestScreen screen) {
         int extra = this.chapter.getGroup().isDefaultGroup() ? 0 : 7;
         if (!this.chapter.hasAnyVisibleChildren()
            || this.chapterPanel.questScreen.file.selfTeamData.hasUnclaimedRewards(Minecraft.getInstance().player.getUUID(), this.chapter)) {
            extra += 16;
         }

         return screen.getTheme().getStringWidth(this.title) + 20 + extra;
      }
   }

   public static class ChapterGroupButton extends ChapterPanel.ListButton {
      public final ChapterGroup group;
      public final List<Chapter> visibleChapters;
      private final boolean xlateWarning;

      public ChapterGroupButton(ChapterPanel panel, ChapterGroup g) {
         super(panel, g.getTitle(), g.getIcon());
         this.setSize(100, 18);
         this.group = g;
         this.visibleChapters = g.getVisibleChapters(panel.questScreen.file.selfTeamData);
         this.xlateWarning = g.getQuestFile().getTranslationManager().hasMissingTranslation(this.group, TranslationKey.TITLE);
      }

      public void onClicked(MouseButton button) {
         ClientQuestFile file = this.chapterPanel.questScreen.file;
         if (file.canEdit()) {
            if (button.isLeft()) {
               if (Screen.hasAltDown()) {
                  this.group.onEditButtonClicked(this.chapterPanel.questScreen);
                  return;
               }

               if (this.getMouseX() > this.getX() + this.width - 15) {
                  this.showAddChapterDialog();
                  return;
               }
            } else if (button.isRight() && !this.group.isDefaultGroup()) {
               ContextMenuBuilder.create(this.group, this.chapterPanel.questScreen)
                  .insertAtTop(
                     List.of(
                        new ContextMenuItem(
                              Component.translatable("gui.move"),
                              ThemeProperties.MOVE_UP_ICON.get(),
                              b -> NetworkManager.sendToServer(new MoveChapterGroupMessage(this.group.id, true))
                           )
                           .setEnabled(!this.group.isFirstGroup())
                           .setCloseMenu(false),
                        new ContextMenuItem(
                              Component.translatable("gui.move"),
                              ThemeProperties.MOVE_DOWN_ICON.get(),
                              b -> NetworkManager.sendToServer(new MoveChapterGroupMessage(this.group.id, false))
                           )
                           .setEnabled(!this.group.isLastGroup())
                           .setCloseMenu(false)
                     )
                  )
                  .openContextMenu(this.chapterPanel.questScreen);
               return;
            }
         }

         this.group.toggleCollapsed();
         this.parent.refreshWidgets();
      }

      private void showAddChapterDialog() {
         ClientQuestFile file = this.chapterPanel.questScreen.file;
         this.playClickSound();
         StringConfig c = new StringConfig(ChapterPanel.NON_EMPTY_PAT);
         EditStringConfigOverlay<String> overlay = new EditStringConfigOverlay(this.parent.getParent(), c, accepted -> {
            this.chapterPanel.questScreen.openGui();
            if (accepted && !((String)c.getValue()).isEmpty()) {
               Chapter chapter = new Chapter(0L, file, file.getDefaultChapterGroup(), Chapter.titleToID((String)c.getValue()).orElse(""));
               CompoundTag extra = (CompoundTag)Util.make(new CompoundTag(), t -> t.putLong("group", this.group.id));
               file.getTranslationManager().addInitialTranslation(extra, file.getLocale(), TranslationKey.TITLE, (String)c.getValue());
               NetworkManager.sendToServer(CreateObjectMessage.create(chapter, extra));
            }

            this.run();
         }, Component.translatable("ftbquests.chapter")).atMousePosition();
         overlay.setWidth(150);
         overlay.setExtraZlevel(910);
         this.getGui().pushModalPanel(overlay);
      }

      public void draw(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
         GuiHelper.setupDrawing();
         if (this.xlateWarning) {
            Color4I.RED.withAlpha(40).draw(graphics, x, y, w, h);
         }

         if (this.isMouseOver()) {
            Color4I.WHITE.withAlpha(40).draw(graphics, x + 1, y, w - 2, h);
         }

         ChatFormatting f = this.isMouseOver() ? ChatFormatting.WHITE : ChatFormatting.GRAY;
         (this.group.isGuiCollapsed() ? ChapterPanel.ARROW_COLLAPSED : ChapterPanel.ARROW_EXPANDED)
            .withColor(Color4I.getChatFormattingColor(f))
            .draw(graphics, x + 3, y + 5, 8, 8);
         theme.drawString(graphics, Component.literal("").append(this.title).withStyle(f), x + 15, y + 5);
         boolean canEdit = this.chapterPanel.questScreen.file.canEdit();
         if (canEdit) {
            ThemeProperties.ADD_ICON.get().draw(graphics, x + w - 14, y + 3, 12, 12);
         }
      }

      @Override
      public int getActualWidth(QuestScreen screen) {
         boolean canEdit = this.chapterPanel.questScreen.file.canEdit();
         return screen.getTheme().getStringWidth(this.title) + 20 + (canEdit ? 16 : 0);
      }

      @Override
      public void addMouseOverText(TooltipList list) {
         this.chapterPanel.questScreen.addInfoTooltip(list, this.group);
         if (this.xlateWarning) {
            ClientQuestFile.addTranslationWarning(list, TranslationKey.TITLE);
         }
      }
   }

   public abstract static class ListButton extends Button {
      public final ChapterPanel chapterPanel;

      public ListButton(ChapterPanel panel, Component t, Icon i) {
         super(panel, t, i);
         this.setSize(100, 14);
         this.chapterPanel = panel;
      }

      public int getActualWidth(QuestScreen screen) {
         return screen.getTheme().getStringWidth(this.title) + 20;
      }

      public void addMouseOverText(TooltipList list) {
      }

      public Optional<PositionedIngredient> getIngredientUnderMouse() {
         return PositionedIngredient.of(this.icon.getIngredient(), this);
      }
   }

   public static class ModpackButton extends ChapterPanel.ListButton {
      public ModpackButton(ChapterPanel panel, ClientQuestFile f) {
         super(panel, f.getTitle(), f.getIcon());
         this.setSize(100, 18);
      }

      public void onClicked(MouseButton button) {
         if (this.getMouseX() > this.getX() + this.width - 18) {
            this.playClickSound();
            FTBQuestsClientConfig.setChapterPanelPinned(!(Boolean)FTBQuestsClientConfig.CHAPTER_PANEL_PINNED.get());
         } else {
            ClientQuestFile file = this.chapterPanel.questScreen.file;
            if (file.canEdit()) {
               if (this.getMouseX() > this.getX() + this.width - 34) {
                  this.showAddChapterOrGroupDialog(file);
               } else if (button.isLeft() && Screen.hasAltDown()) {
                  file.onEditButtonClicked(this.chapterPanel.questScreen);
               }
            }
         }
      }

      private void showAddChapterOrGroupDialog(ClientQuestFile file) {
         this.playClickSound();
         List<ContextMenuItem> contextMenu = new ArrayList<>();
         contextMenu.add(new ContextMenuItem(Component.translatable("ftbquests.chapter"), ThemeProperties.ADD_ICON.get(), b -> {
            StringConfig c = new StringConfig(ChapterPanel.NON_EMPTY_PAT);
            EditStringConfigOverlay<String> overlay = new EditStringConfigOverlay(this.parent.getParent(), c, accepted -> {
               this.chapterPanel.questScreen.openGui();
               if (accepted && !((String)c.getValue()).isEmpty()) {
                  Chapter chapter = new Chapter(0L, file, file.getDefaultChapterGroup(), Chapter.titleToID((String)c.getValue()).orElse(""));
                  CompoundTag extra = (CompoundTag)Util.make(new CompoundTag(), t -> t.putLong("group", 0L));
                  file.getTranslationManager().addInitialTranslation(extra, file.getLocale(), TranslationKey.TITLE, (String)c.getValue());
                  NetworkManager.sendToServer(CreateObjectMessage.create(chapter, extra));
               }

               this.run();
            }, b.getTitle()).atMousePosition();
            overlay.setWidth(150);
            overlay.setExtraZlevel(910);
            this.getGui().pushModalPanel(overlay);
         }));
         contextMenu.add(new ContextMenuItem(Component.translatable("ftbquests.chapter_group"), ThemeProperties.ADD_ICON.get(), b -> {
            StringConfig c = new StringConfig(ChapterPanel.NON_EMPTY_PAT);
            EditStringConfigOverlay<String> overlay = new EditStringConfigOverlay(this.parent.getParent(), c, accepted -> {
               this.chapterPanel.questScreen.openGui();
               if (accepted) {
                  ChapterGroup group = new ChapterGroup(0L, ClientQuestFile.INSTANCE);
                  CompoundTag extra = (CompoundTag)Util.make(new CompoundTag(), t -> t.putLong("group", 0L));
                  file.getTranslationManager().addInitialTranslation(extra, file.getLocale(), TranslationKey.TITLE, (String)c.getValue());
                  NetworkManager.sendToServer(CreateObjectMessage.create(group, extra));
               }
            }, b.getTitle()).atMousePosition();
            overlay.setWidth(150);
            overlay.setExtraZlevel(910);
            this.getGui().pushModalPanel(overlay);
         }));
         this.chapterPanel.questScreen.openContextMenu(contextMenu);
      }

      public void draw(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
         GuiHelper.setupDrawing();
         if (this.isMouseOver()) {
            Color4I.WHITE.withAlpha(40).draw(graphics, x + 1, y + 1, w - 2, h - 2);
         }

         ChatFormatting f = this.isMouseOver() ? ChatFormatting.WHITE : ChatFormatting.GRAY;
         this.icon.draw(graphics, x + 2, y + 3, 12, 12);
         theme.drawString(graphics, Component.literal("").append(this.title).withStyle(f), x + 16, y + 5);
         ThemeProperties.WIDGET_BORDER.get(ClientQuestFile.INSTANCE).draw(graphics, x, y + h - 1, w, 1);
         boolean canEdit = this.chapterPanel.questScreen.file.canEdit();
         (this.chapterPanel.isPinned() ? ThemeProperties.PIN_ICON_ON : ThemeProperties.PIN_ICON_OFF).get().draw(graphics, x + w - 16, y + 3, 12, 12);
         if (canEdit) {
            ThemeProperties.ADD_ICON.get().draw(graphics, x + w - 31, y + 3, 12, 12);
         }
      }

      @Override
      public int getActualWidth(QuestScreen screen) {
         boolean canEdit = this.chapterPanel.questScreen.file.canEdit();
         return screen.getTheme().getStringWidth(this.title) + 36 + (canEdit ? 16 : 0);
      }

      @Override
      public void addMouseOverText(TooltipList list) {
         this.chapterPanel.questScreen.addInfoTooltip(list, this.chapterPanel.questScreen.file);
         if (this.chapterPanel.questScreen.file.canEdit() && this.getMouseX() > this.getX() + this.width - 34) {
            list.translate("gui.add", new Object[0]);
         }
      }
   }
}
