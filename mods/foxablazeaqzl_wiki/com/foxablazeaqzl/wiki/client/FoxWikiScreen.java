package com.foxablazeaqzl.wiki.client;

import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.client.screen.templates.IScrollBar;
import io.github.manasmods.tensura.client.screen.templates.SimpleScreen;
import io.github.manasmods.tensura.client.screen.widgets.SimpleButton;
import io.github.manasmods.tensura.util.client.RenderHelper;
import io.github.manasmods.tensura.util.client.ScreenHelper;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

public final class FoxWikiScreen extends SimpleScreen implements IScrollBar {
   public static final int MENU_ID = 6;
   public static final Component TITLE = Component.translatable("foxablazeaqzl_wiki.menu.skill_intro");
   private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath("foxablazeaqzl_wiki", "textures/gui/wiki.png");
   private static final ResourceLocation ABILITY_BAR = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/ability_button.png");
   private static final Component PLACEHOLDER = Component.translatable("foxablazeaqzl_wiki.placeholder");
   private static final int GUI_Y_OFFSET = 2;
   private static final int WIKI_TITLE_X = 121;
   private static final int WIKI_TITLE_Y = 33;
   private static final int WIKI_TITLE_WIDTH = 102;
   private static final int WIKI_TITLE_HEIGHT = 15;
   private static final int WIKI_DESCRIPTION_X = 121;
   private static final int WIKI_DESCRIPTION_Y = 59;
   private static final int WIKI_DESCRIPTION_WIDTH = 102;
   private static final int WIKI_DESCRIPTION_HEIGHT = 95;
   private static final int SEARCH_FIELD_X = 19;
   private static final int SEARCH_FIELD_Y = 50;
   private static final int SKILL_LIST_X = 6;
   private static final int SKILL_LIST_Y = 66;
   private static final int SKILL_LIST_HOVER_X = 5;
   private static final int SKILL_LIST_HOVER_Y = 65;
   private static final int SKILL_LIST_HOVER_WIDTH = 104;
   private static final int SKILL_LIST_HOVER_HEIGHT = 93;
   private static final int SKILL_MARKER_TEXT_GAP = 1;
   private static final int SKILL_MARKER_RIGHT_PADDING = 5;
   private static final float MODE_TEXT_SCALE = 0.8F;
   private final List<ManasSkillInstance> learnedSkills = new ArrayList<>();
   private final List<ManasSkillInstance> filteredSkills = new ArrayList<>();
   private ManasSkillInstance selectedSkill;
   private EditBox searchField;
   private String nameFilter = "";
   private float scrollOffset;
   private boolean scrolling;
   private int listStartIndex;
   private int textStartIndex;
   private final ResourceLocation initialSkillId;
   private final MutableComponent initialSkillName;
   private ResourceLocation externalSkillId;
   private MutableComponent externalSkillName;

   public FoxWikiScreen() {
      this(null, null);
   }

   public FoxWikiScreen(ManasSkill initialSkill) {
      this(initialSkill == null ? null : initialSkill.getRegistryName(), getSkillName(initialSkill));
   }

   private FoxWikiScreen(ResourceLocation initialSkillId, MutableComponent initialSkillName) {
      super(TITLE, 256, 167);
      this.shouldFade = false;
      this.initialSkillId = initialSkillId;
      this.initialSkillName = initialSkillName == null ? null : initialSkillName.copy();
   }

   protected void init() {
      super.init();
      guiTop += 2;
      guiCenterY += 2;
      this.shouldRenderWidgets = false;
      this.scrollOffset = 0.0F;
      this.listStartIndex = 0;
      this.textStartIndex = 0;
      this.externalSkillId = this.initialSkillId;
      this.externalSkillName = this.initialSkillName == null ? null : this.initialSkillName.copy();
      ScreenHelper.getMenuTabs(4, guiLeft, guiTop).stream().filter(button -> button.getX() >= guiLeft + 200).forEach(x$0 -> {
         SimpleButton var10000 = (SimpleButton)this.addRenderableWidget(x$0);
      });
      FoxWikiTabButtons.disableOriginalThirdRightTab(this, guiLeft, guiTop);
      this.addRenderableWidget(FoxWikiTabButtons.createMainButton(guiLeft, guiTop));
      if (FoxWikiTabButtons.isTrBeyondLoaded()) {
         FoxWikiTabButtons.disableTrBeyondTab(this, guiLeft, guiTop);
         this.addRenderableWidget(FoxWikiTabButtons.createTrBeyondButton(guiLeft, guiTop));
      }

      this.searchField = new EditBox(this.font, guiLeft + 19, guiTop + 50, 79, 9, Component.empty());
      this.searchField.setBordered(false);
      this.searchField.setResponder(value -> {
         this.nameFilter = value;
         this.setScrollOffset(0.0F);
         this.setListStartIndex(0);
         this.updateFilteredSkills();
      });
      this.addRenderableWidget(this.searchField);
      this.searchField.setValue(this.nameFilter == null ? "" : this.nameFilter);
      this.updateLearnedSkills();
      this.updateFilteredSkills();
      this.selectInitialSkill();
   }

   public void renderBackground(GuiGraphics graphics, int mX, int mY, float partialTick) {
      graphics.blit(BACKGROUND, guiLeft, guiTop, 0, 0, this.imageWidth, this.imageHeight);
      FoxWikiTopTabs.render(graphics, this, guiLeft, guiTop, mX, mY);
      graphics.drawString(this.font, TITLE, guiLeft + 105, guiTop + 10, 16777215);
      if (FoxWikiTabButtons.isTrBeyondLoaded()) {
         FoxWikiTabButtons.renderTrBeyondTabBase(graphics, guiLeft, guiTop, mX, mY);
      }

      super.renderWidgets(graphics, mX, mY, partialTick);
      FoxWikiTabButtons.renderCurrentWikiTab(graphics, this, guiLeft, guiTop, mX, mY);
      this.updateLearnedSkills();
      this.updateFilteredSkills();
      this.renderSkillList(graphics, mX, mY);
      this.renderScrollBar(graphics, mX, mY);
      this.renderWikiText(graphics, mX, mY);
   }

   public boolean mouseClicked(double mX, double mY, int button) {
      if (FoxWikiTopTabs.clicked(mX, mY, button, guiLeft, guiTop, FoxWikiTopTabs.Tab.SKILL)) {
         return true;
      }

      if (this.clickedScrollBar(mX, mY)) {
         return true;
      }

      if (!RenderHelper.mouseOver(
         mX,
         mY,
         this.searchField.getX(),
         this.searchField.getX() + this.searchField.getWidth(),
         this.searchField.getY(),
         this.searchField.getY() + this.searchField.getHeight()
      )) {
         this.searchField.setFocused(false);
      }

      int x = guiLeft + 6;
      int y = guiTop + 66;
      int lastVisibleIndex = Math.min(this.listStartIndex + this.getScrollBarRenderCount(), this.filteredSkills.size());

      for (int i = this.listStartIndex; i < lastVisibleIndex; i++) {
         int rowY = y + (i - this.listStartIndex) * 13;
         if (RenderHelper.mouseOver(mX, mY, x, x + 89, rowY, rowY + 13)) {
            this.selectedSkill = this.filteredSkills.get(i);
            this.externalSkillId = null;
            this.externalSkillName = null;
            this.textStartIndex = 0;
            FoxWikiUnreadState.markViewed(this.selectedSkill.getSkillId());
            ScreenHelper.clicked();
            return true;
         }
      }

      return super.mouseClicked(mX, mY, button);
   }

   public boolean mouseDragged(double mX, double mY, int button, double dragX, double dragY) {
      return this.draggedScrollBar(mY) || super.mouseDragged(mX, mY, button, dragX, dragY);
   }

   public boolean mouseScrolled(double mX, double mY, double deltaX, double deltaY) {
      if (RenderHelper.mouseOver(mX, mY, guiLeft + 121, guiLeft + 121 + 102, guiTop + 59, guiTop + 59 + 95)) {
         this.textStartIndex = Math.max(this.textStartIndex + (int)(-deltaY), 0);
         return true;
      } else if (RenderHelper.mouseOver(mX, mY, guiLeft + 5, guiLeft + 5 + 104, guiTop + 65, guiTop + 65 + 93)) {
         this.scrolledScrollBar(deltaY);
         return true;
      } else {
         return super.mouseScrolled(mX, mY, deltaX, deltaY);
      }
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (this.searchField.keyPressed(keyCode, scanCode, modifiers)) {
         return true;
      } else {
         return this.searchField.isFocused() && this.searchField.isVisible() && keyCode != 256 ? true : super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   private void renderSkillList(GuiGraphics graphics, int mX, int mY) {
      int x = guiLeft + 6;
      int y = guiTop + 66;
      int lastVisibleIndex = Math.min(this.listStartIndex + this.getScrollBarRenderCount(), this.filteredSkills.size());

      for (int i = this.listStartIndex; i < lastVisibleIndex; i++) {
         int rowY = y + (i - this.listStartIndex) * 13;
         ManasSkillInstance instance = this.filteredSkills.get(i);
         MutableComponent name = this.getSkillName(instance);
         TextColor color = name.getStyle().getColor();
         int textColor = color == null ? 16777215 : color.getValue();
         boolean hovered = RenderHelper.mouseOver(mX, mY, x, x + 89, rowY, rowY + 13);
         boolean selected = this.selectedSkill != null && this.selectedSkill.getSkillId().equals(instance.getSkillId());
         boolean unread = FoxWikiUnreadState.isUnread(instance.getSkillId());
         graphics.blit(ABILITY_BAR, x, rowY, 0.0F, !hovered && !selected ? 0.0F : 13.0F, 89, 13, 89, 26);
         if (unread) {
            int textX = x + 3;
            int markerX = Math.min(textX + this.font.width(name) + 1, x + 89 - 8 - 5);
            int textWidth = Math.max(1, markerX - textX - 1);
            RenderHelper.drawScrollingText(graphics, this.font, name, textX, rowY + 3, textWidth, textColor);
            FoxWikiMarker.render(graphics, markerX, rowY + 2, 8);
         } else {
            RenderHelper.drawScrollingText(graphics, this.font, name, x + 3, rowY + 3, 83, textColor);
         }

         if (hovered) {
            this.setTooltipForNextRenderPass(name);
         }
      }
   }

   private void renderWikiText(GuiGraphics graphics, int mX, int mY) {
      ResourceLocation skillId = this.getDisplayedSkillId();
      MutableComponent fallbackTitle = this.getDisplayedSkillTitle();
      if (skillId != null && fallbackTitle != null) {
         MutableComponent title = FoxWikiData.getName(skillId).map(name -> Component.literal(name).withStyle(fallbackTitle.getStyle())).orElse(fallbackTitle);
         TextColor color = fallbackTitle.getStyle().getColor();
         int titleColor = color == null ? 16777215 : color.getValue();
         int titleY = guiTop + 33 + (15 - 9) / 2;
         RenderHelper.drawCenteredText(graphics, this.font, title, guiLeft + 121, titleY, 102, titleColor);
         List<FoxWikiScreen.DescriptionLine> sequences = this.splitDescription(FoxWikiData.getDescription(skillId).orElse(PLACEHOLDER.getString()));
         int start = Math.clamp(this.textStartIndex, 0, Math.max(0, sequences.size() - 1));
         this.textStartIndex = start;
         this.renderDescriptionLines(graphics, sequences, start);
      }
   }

   private List<FoxWikiScreen.DescriptionLine> splitDescription(String text) {
      List<FoxWikiScreen.DescriptionLine> lines = new ArrayList<>();
      boolean hasContentLine = false;
      boolean hasModeHeader = false;
      boolean lastRenderedLineBlank = false;
      int splitWidth = Math.max(1, Math.round(127.5F));

      for (String rawLine : normalizeDescriptionText(text).split("\\R", -1)) {
         String line = rawLine.trim();
         if (line.isBlank()) {
            if (hasContentLine && !lines.isEmpty()) {
               lines.add(new FoxWikiScreen.DescriptionLine(Component.empty().getVisualOrderText()));
               lastRenderedLineBlank = true;
            }
         } else {
            boolean modeHeader = isModeHeader(line);
            if (modeHeader && hasModeHeader && !lastRenderedLineBlank) {
               lines.add(new FoxWikiScreen.DescriptionLine(Component.empty().getVisualOrderText()));
               lastRenderedLineBlank = true;
            }

            String displayLine = formatModeLine(line);

            for (FormattedCharSequence sequence : this.font.split(Component.literal(displayLine), splitWidth)) {
               lines.add(new FoxWikiScreen.DescriptionLine(sequence));
            }

            if (modeHeader) {
               hasModeHeader = true;
            }

            hasContentLine = true;
            lastRenderedLineBlank = false;
         }
      }

      return lines;
   }

   private static String normalizeDescriptionText(String text) {
      String normalized = text.replace("\\r\\n", "\n").replace("\\n", "\n").replace("\\r", "\n");
      return normalized.replaceAll("(^|[\\n。！？；:：])n(?=\\[(?:主动|被动|模式|战斗姿态|相位|潜行|长按|精通)[^\\]]*\\])", "$1\n");
   }

   private static boolean isModeHeader(String line) {
      if (line.startsWith("-")) {
         return false;
      }

      int separator = line.indexOf(58);
      if (separator <= 0) {
         return false;
      }

      String heading = line.substring(0, separator);
      return isModeHeading(heading);
   }

   private static String formatModeLine(String line) {
      if (line.startsWith("-")) {
         return line;
      }

      int separator = line.indexOf(58);
      if (separator <= 0) {
         return line;
      }

      String heading = line.substring(0, separator).trim();
      if (!isModeHeading(heading)) {
         return line;
      }

      String normalizedHeading = normalizeModeHeading(heading);
      String prefix = "[" + normalizedHeading + "]";
      String body = line.substring(separator + 1).stripLeading();
      if (body.startsWith(prefix)) {
         body = body.substring(prefix.length()).stripLeading();
      }

      body = normalizeModeBody(body, normalizedHeading);
      return prefix + body;
   }

   private static boolean isModeHeading(String heading) {
      String lowerHeading = heading.toLowerCase(Locale.ROOT);
      return heading.indexOf(45) >= 0
         || heading.equals("主动")
         || heading.equals("被动")
         || heading.equals("用法")
         || heading.equals("反技能")
         || heading.equals("技艺")
         || heading.equals("魔法")
         || heading.equals("备注")
         || lowerHeading.startsWith("active")
         || lowerHeading.startsWith("passive")
         || lowerHeading.startsWith("usage")
         || lowerHeading.startsWith("note")
         || lowerHeading.startsWith("skills gained");
   }

   private static String normalizeModeHeading(String heading) {
      String[] parts = heading.split("-");
      List<String> normalizedParts = new ArrayList<>();

      for (String rawPart : parts) {
         String part = rawPart.trim();
         if (!part.isBlank()) {
            if (!normalizedParts.isEmpty()) {
               String firstPart = normalizedParts.get(0);
               if (part.equals(firstPart)) {
                  continue;
               }

               part = removeRepeatedModeSuffix(part, firstPart);
               if (part.isBlank()) {
                  continue;
               }
            }

            normalizedParts.add(part);
         }
      }

      return String.join("-", normalizedParts);
   }

   private static String removeRepeatedModeSuffix(String part, String mode) {
      return (mode.equals("主动") || mode.equals("被动")) && part.endsWith(mode) && part.length() > mode.length()
         ? part.substring(0, part.length() - mode.length())
         : part;
   }

   private static String normalizeModeBody(String body, String normalizedHeading) {
      String normalizedBody = body.stripLeading();
      String[] redundantPrefixes = new String[]{"按下型主动：", "长按型主动：", "开关型主动：", "开关型被动：", "槽位被动：", "常驻被动：", "被动槽位效果："};

      for (String prefix : redundantPrefixes) {
         if (normalizedBody.startsWith(prefix)) {
            normalizedBody = normalizedBody.substring(prefix.length()).stripLeading();
            break;
         }
      }

      String[] headingParts = normalizedHeading.split("-");
      if (headingParts.length >= 3) {
         String modeName = headingParts[headingParts.length - 1];
         if (normalizedBody.startsWith(modeName + "。") || normalizedBody.startsWith(modeName + "：") || normalizedBody.startsWith(modeName + ":")) {
            normalizedBody = normalizedBody.substring(modeName.length() + 1).stripLeading();
         }
      }

      return normalizedBody;
   }

   private void renderDescriptionLines(GuiGraphics graphics, List<FoxWikiScreen.DescriptionLine> lines, int start) {
      int x = guiLeft + 121;
      int y = guiTop + 59;
      int bottom = y + 95;
      graphics.enableScissor(x, y, x + 102, bottom);

      for (int i = start; i < lines.size() && y < bottom; i++) {
         FoxWikiScreen.DescriptionLine line = lines.get(i);
         int lineHeight = Math.max(1, Math.round(9.0F * 0.8F));
         graphics.pose().pushPose();
         graphics.pose().translate(x, y, 0.0F);
         graphics.pose().scale(0.8F, 0.8F, 1.0F);
         graphics.drawString(this.font, line.text(), 0, 0, 16777215, false);
         graphics.pose().popPose();
         y += lineHeight;
      }

      graphics.disableScissor();
   }

   private void updateLearnedSkills() {
      this.learnedSkills.clear();
      if (this.player == null) {
         this.selectedSkill = null;
      } else {
         this.learnedSkills.addAll(SkillAPI.getSkillsFrom(this.player).getLearnedSkills());
         this.learnedSkills
            .removeIf(instance -> instance.getSkill() == null || instance.getDisplayName() == null || !FoxWikiData.hasDescription(instance.getSkillId()));
         FoxWikiUnreadState.observeLearnedWikiSkills(this.player);
         if (this.selectedSkill != null) {
            this.selectedSkill = this.learnedSkills
               .stream()
               .filter(instance -> instance.getSkillId().equals(this.selectedSkill.getSkillId()))
               .findFirst()
               .orElse(null);
         }
      }
   }

   private void selectInitialSkill() {
      if (this.initialSkillId != null && FoxWikiData.hasDescription(this.initialSkillId)) {
         this.selectedSkill = this.learnedSkills.stream().filter(instance -> this.initialSkillId.equals(instance.getSkillId())).findFirst().orElse(null);
         if (this.selectedSkill != null) {
            FoxWikiUnreadState.markViewed(this.selectedSkill.getSkillId());
         } else {
            FoxWikiUnreadState.markViewed(this.initialSkillId);
         }
      } else {
         this.externalSkillId = null;
         this.externalSkillName = null;
      }
   }

   private void updateFilteredSkills() {
      this.filteredSkills.clear();
      String filter = this.nameFilter == null ? "" : this.nameFilter.trim().toLowerCase(Locale.ROOT);
      this.filteredSkills
         .addAll(
            this.learnedSkills
               .stream()
               .filter(instance -> filter.isEmpty() || this.matchesFilter(instance, filter))
               .sorted(Comparator.comparing(instance -> instance.getDisplayName().getString()))
               .toList()
         );
      this.scrolledScrollBar(0.0);
   }

   private boolean matchesFilter(ManasSkillInstance instance, String filter) {
      if (instance.getDisplayName().getString().toLowerCase(Locale.ROOT).contains(filter)) {
         return true;
      }

      ResourceLocation id = instance.getSkillId();
      return id != null && id.toString().toLowerCase(Locale.ROOT).contains(filter);
   }

   private MutableComponent getSkillName(ManasSkillInstance instance) {
      if (instance.getSkill() instanceof TensuraSkill tensuraSkill && tensuraSkill.getColoredName() != null) {
         return tensuraSkill.getColoredName();
      } else {
         MutableComponent name = instance.getDisplayName();
         return name == null ? Component.literal(instance.getSkillId().toString()) : name.copy();
      }
   }

   private static MutableComponent getSkillName(ManasSkill skill) {
      if (skill == null) {
         return null;
      } else if (skill instanceof TensuraSkill tensuraSkill && tensuraSkill.getColoredName() != null) {
         return tensuraSkill.getColoredName();
      } else {
         MutableComponent name = skill.getName();
         ResourceLocation id = skill.getRegistryName();
         return name == null ? Component.literal(id == null ? "" : id.toString()) : name.copy();
      }
   }

   private ResourceLocation getDisplayedSkillId() {
      return this.selectedSkill != null ? this.selectedSkill.getSkillId() : this.externalSkillId;
   }

   private MutableComponent getDisplayedSkillTitle() {
      if (this.selectedSkill != null) {
         return this.getSkillName(this.selectedSkill);
      } else if (this.externalSkillName != null) {
         return this.externalSkillName.copy();
      } else {
         return this.externalSkillId == null ? null : Component.literal(this.externalSkillId.toString());
      }
   }

   public int getScrollBarX() {
      return guiLeft + 98;
   }

   public int getScrollBarY() {
      return guiTop + 66;
   }

   public int getScrollBarTotalSpace() {
      return 91;
   }

   public int getScrollBarListSize() {
      return this.filteredSkills.size();
   }

   public int getScrollBarRenderCount() {
      return 7;
   }

   public float getScrollOffset() {
      return this.scrollOffset;
   }

   public boolean isScrolling() {
      return this.scrolling;
   }

   public void setListStartIndex(int value) {
      this.listStartIndex = value;
   }

   public void setScrolling(boolean value) {
      this.scrolling = value;
   }

   public void setScrollOffset(float value) {
      this.scrollOffset = value;
   }

   private record DescriptionLine(FormattedCharSequence text) {
   }
}
