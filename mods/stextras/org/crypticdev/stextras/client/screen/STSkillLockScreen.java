package org.crypticdev.stextras.client.screen;

import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.client.screen.templates.IScrollBar;
import io.github.manasmods.tensura.client.screen.templates.SimpleScreen;
import io.github.manasmods.tensura.client.screen.widgets.ExpandedEditBox;
import io.github.manasmods.tensura.client.screen.widgets.SimpleButton;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ability.IAbility;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.storage.player.ITensuraPlayer;
import io.github.manasmods.tensura.util.client.RenderHelper;
import io.github.manasmods.tensura.util.client.ScreenHelper;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Player;
import org.crypticdev.stextras.client.screen.skilllock.STSkillLockFilters;
import org.crypticdev.stextras.client.screen.skilllock.STSkillLockRules;
import org.crypticdev.stextras.client.screen.skilllock.STSkillLockTooltips;
import org.crypticdev.stextras.net.C2S.OpenSkillLockScreenPacket;
import org.crypticdev.stextras.net.C2S.SkillLockPacket;
import org.crypticdev.stextras.net.C2S.SkillUnlockPacket;
import org.crypticdev.stextras.registary.sounds.STExtrasSoundEvents;
import org.crypticdev.stextras.storage.STExtarsStorage;
import org.crypticdev.stextras.utils.TextUtil;

public class STSkillLockScreen extends SimpleScreen implements IScrollBar {
   public static final Component TITLE = Component.translatable("stextras.skill_lock_menu");
   public static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath("stextras", "textures/gui/skill/lock/skill_lock_screen.png");
   public static final ResourceLocation ABILITY_BAR = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/ability_button.png");
   public static final ResourceLocation LOCKED_CHAINS = ResourceLocation.fromNamespaceAndPath(
      "stextras", "textures/gui/skill/lock/locked_skill_button_wrapper.png"
   );
   public static final ResourceLocation ADMIN_LOCKED_CHAINS = ResourceLocation.fromNamespaceAndPath(
      "stextras", "textures/gui/skill/lock/locked_skill_button_wrapper_admin.png"
   );
   public static final ResourceLocation LOCK = ResourceLocation.fromNamespaceAndPath("stextras", "textures/gui/skill/lock/skill_lock_lock.png");
   private static final int ROW_HEIGHT = 13;
   private static final int VISIBLE_ROWS = 8;
   private static final int LIST_X = 6;
   private static final int LIST_Y = 49;
   private static final int LIST_W = 89;
   private static final int LIST_H = 102;
   private static final int SCROLL_X = 98;
   private static final int SCROLL_Y = 49;
   private static final int SCROLL_H = 104;
   private static final int DESC_X = 123;
   private static final int DESC_Y = 66;
   private static final int DESC_W = 98;
   private static final int DESC_H = 64;
   private static final int LOCK_BUTTON_X = 118;
   private static final int LOCK_BUTTON_Y = 141;
   private static final int LOCK_BUTTON_W = 43;
   private static final int LOCK_BUTTON_H = 16;
   private static final int UNLOCK_BUTTON_X = 181;
   private static final int UNLOCK_BUTTON_Y = 141;
   private static final int UNLOCK_BUTTON_W = 43;
   private static final int UNLOCK_BUTTON_H = 16;
   protected ITensuraPlayer tensuraPlayerData;
   protected IAbility abilityData;
   private final List<ManasSkillInstance> abilities = new ArrayList<>();
   private final List<ManasSkillInstance> filtered = new ArrayList<>();
   private ManasSkillInstance selectedAbility;
   protected IExistence existenceData;
   private float scrollOffset;
   private boolean scrolling;
   private int listStartIndex;
   private int textStartIndex;
   private boolean debugGrid;
   protected ExpandedEditBox searchField;
   protected String nameFilter = "";

   public STSkillLockScreen() {
      super(TITLE, 256, 163);
      this.shouldRenderWidgets = true;
   }

   protected void init() {
      super.init();
      Player player = this.player;
      this.tensuraPlayerData = TensuraStorages.getPlayerDataFrom(player);
      this.abilityData = TensuraStorages.getAbilityFrom(player);
      this.existenceData = TensuraStorages.getExistenceFrom(player);
      List<SimpleButton> tabs = ScreenHelper.getMenuTabs(-5, guiLeft, guiTop);
      tabs.forEach(x$0 -> {
         SimpleButton var10000 = (SimpleButton)this.addRenderableWidget(x$0);
      });
      SimpleButton returnButton = new SimpleButton(
         guiLeft + 5,
         guiTop + 28,
         18,
         10,
         null,
         null,
         Component.translatable("tooltip.tensura.return"),
         self -> ScreenHelper.clicked(SoundEvents.UI_BUTTON_CLICK, () -> ScreenHelper.openScreen(4))
      );
      this.addRenderableWidget(returnButton);
      Component hint = Component.translatable("tensura.ability_selection.suggestion").withColor(8750469);
      List<Component> suggestions = new ArrayList<>(
         Arrays.asList(
            Component.literal("active"),
            Component.literal("passive"),
            Component.literal("mastered"),
            Component.literal("learned"),
            Component.literal("learning"),
            Component.literal("temporary"),
            Component.literal("toggleable"),
            Component.literal("toggledon"),
            Component.literal("toggledoff"),
            Component.literal("oncooldown"),
            Component.literal("offcooldown")
         )
      );
      this.searchField = new ExpandedEditBox(this.font, 0, 0, 78, 9, Component.empty());
      this.searchField.setHint(hint);
      this.searchField.setBordered(false);
      this.searchField.setMultipleSuggestions(suggestions);
      this.searchField.setResponder(s -> {
         this.nameFilter = s;
         this.setScrollOffset(0.0F);
         this.setListStartIndex(0);
         this.updateFilteredAbilities();
         this.searchField.setShouldRenderMultipleSuggestions(() -> s.startsWith("f:"));
      });
      this.searchField.setX(guiLeft + 28);
      this.searchField.setY(guiTop + 31);
      this.addRenderableWidget(this.searchField);
      if (this.nameFilter != null && !this.nameFilter.isEmpty()) {
         this.searchField.setValue(this.nameFilter);
      }

      this.updateAllAbilities();
      this.updateFilteredAbilities();
      OpenSkillLockScreenPacket.sendToServer();
   }

   public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      graphics.drawString(this.font, TITLE, guiLeft + 105, guiTop + 10, Color.WHITE.getRGB());
      graphics.blit(BACKGROUND, guiLeft, guiTop, 0, 0, this.imageWidth, this.imageHeight);
      super.renderWidgets(graphics, mouseX, mouseY, partialTick);
      ScreenHelper.renderTabIcon(graphics, this, 4, guiLeft, guiTop, mouseX, mouseY);
      this.renderSkillList(graphics, mouseX, mouseY);
      this.renderScrollBar(graphics, mouseX, mouseY);
      this.renderSelectedSkill(graphics, mouseX, mouseY);
      this.renderLockButton(graphics, mouseX, mouseY);
      this.renderUnlockButton(graphics, mouseX, mouseY);
      this.renderLock(graphics);
   }

   private void renderLock(GuiGraphics gfx) {
      int x = guiLeft + 118 + 46;
      int y = guiTop + 141 - 2;
      boolean isLockedIcon = false;
      ResourceLocation id = STSkillLockRules.skillId(this.selectedAbility);
      if (id != null) {
         isLockedIcon = STSkillLockRules.isSkillLocked(id);
      }

      int u = 0;
      int v = isLockedIcon ? 20 : 0;
      int width = 16;
      int height = 20;
      gfx.blit(LOCK, x, y, u, v, width, height, width, 40);
   }

   private void renderSkillList(GuiGraphics gfx, int mouseX, int mouseY) {
      int x = guiLeft + 6;
      int y = guiTop + 49;
      int visibleCount = Math.min(8, Math.max(0, this.filtered.size() - this.listStartIndex));
      int end = this.listStartIndex + visibleCount;

      for (int i = 0; i < visibleCount; i++) {
         gfx.blit(ABILITY_BAR, x, y + i * 13, 0.0F, 0.0F, 89, 13, 89, 26);
      }

      int pX = x;
      int pY = y;

      for (int i = this.listStartIndex; i < end; i++) {
         ManasSkillInstance instance = this.filtered.get(i);
         ManasSkill manasSkill = instance.getSkill();
         if (manasSkill instanceof TensuraSkill tensuraSkill) {
            Component name = tensuraSkill.getColoredName();
            if (name == null) {
               pY += 13;
            } else {
               String string = name.getString();
               TextColor color = name.getStyle().getColor();
               if (color == null) {
                  color = TextColor.fromRgb(16777215);
               }

               boolean hovered = RenderHelper.mouseOver(mouseX, mouseY, pX, pX + 89, pY, pY + 13);
               if (string.length() > 14) {
                  string = string.substring(0, 14) + "...";
                  name = Component.literal(string).setStyle(name.getStyle());
               }

               ResourceLocation skillId = SkillAPI.getSkillRegistry().getId(manasSkill);
               boolean adminLocked = STSkillLockRules.isSkillAdminLocked(skillId);
               boolean locked = STSkillLockRules.isSkillLocked(skillId);
               boolean valid = STSkillLockRules.isSkillValid(skillId) || adminLocked;
               int barU = 0;
               int barV = hovered && !locked ? 13 : 0;
               gfx.blit(ABILITY_BAR, pX, pY, barU, barV, 89, 13, 89, 26);
               int nameColor = color.getValue();
               if (!valid) {
                  nameColor = 11184810;
               } else if (locked) {
                  nameColor = 7829367;
               }

               gfx.drawString(this.font, name, pX + 3, pY + 3, nameColor);
               if (locked) {
                  int chainsV = hovered ? 13 : 0;
                  ResourceLocation chainsTexture = adminLocked ? ADMIN_LOCKED_CHAINS : LOCKED_CHAINS;
                  gfx.pose().pushPose();
                  gfx.pose().translate(0.0F, 0.0F, 200.0F);
                  gfx.blit(chainsTexture, pX, pY, 0.0F, chainsV, 89, 13, 89, 26);
                  gfx.pose().popPose();
               }

               if (hovered) {
                  MutableComponent tooltip = Component.empty();
                  tooltip.append(tensuraSkill.getColoredName() != null ? tensuraSkill.getColoredName().copy() : Component.literal("Unknown Skill"));
                  this.setTooltipForNextRenderPass(tooltip);
               }

               pY += 13;
            }
         } else {
            pY += 13;
         }
      }
   }

   private boolean canLockSelectedSkill() {
      return STSkillLockRules.canLockSelectedSkill(this.selectedAbility);
   }

   private boolean canUnlockSelectedSkill() {
      return STSkillLockRules.canUnlockSelectedSkill(this.selectedAbility, this.existenceData);
   }

   private void renderSelectedSkill(GuiGraphics gfx, int mouseX, int mouseY) {
      if (this.selectedAbility != null) {
         ManasSkill manasSkill = this.selectedAbility.getSkill();
         if (manasSkill instanceof TensuraSkill tensuraSkill) {
            ResourceLocation icon = manasSkill.getSkillIcon();
            if (icon != null) {
               gfx.blit(icon, guiLeft + 159, guiTop + 29, 0.0F, 0.0F, 26, 26, 26, 26);
            }

            Component description = tensuraSkill.getSkillDescription();
            if (description == null) {
               description = Component.literal("No description.");
            }

            List<FormattedCharSequence> lines = this.font.split(description, 94);
            int maxLines = Math.max(1, 64 / 9);
            int startIdx = Math.clamp(this.textStartIndex, 0, Math.max(0, lines.size() - maxLines));
            int endIdx = Math.min(startIdx + maxLines, lines.size());
            this.textStartIndex = startIdx;
            boolean hoveringDesc = RenderHelper.mouseOver(mouseX, mouseY, guiLeft + 123, guiLeft + 123 + 98, guiTop + 66, guiTop + 66 + 64);
            RenderHelper.drawScrollableTextInAreaSetHighlight(
               gfx, this.font, lines, guiLeft + 123, guiTop + 66, 98, 64, 0, startIdx, endIdx, hoveringDesc, 11184810, 864082
            );
         }
      }
   }

   private void renderLockButton(GuiGraphics gfx, int mouseX, int mouseY) {
      int x = guiLeft + 118;
      int y = guiTop + 141;
      boolean hovered = this.mouseOverLockButton(mouseX, mouseY);
      boolean enabled = this.canLockSelectedSkill();
      int v = 164;
      if (enabled && hovered) {
         v = 180;
      }

      gfx.blit(BACKGROUND, x, y, 0.0F, v, 43, 16, 256, 256);
      String text = "Lock";
      int textColor;
      if (enabled) {
         textColor = hovered ? 16776960 : 16777215;
      } else {
         textColor = 11184810;
      }

      int textWidth = this.font.width(text);
      int textHeight = 9;
      int textX = x + (43 - textWidth) / 2;
      int textY = y + (16 - textHeight) / 2;
      gfx.drawString(this.font, text, textX, textY + 1, textColor);
      if (hovered && !enabled) {
         this.setTooltipForNextRenderPass(STSkillLockTooltips.lockDisabledReason(this.selectedAbility));
      }
   }

   private void renderUnlockButton(GuiGraphics gfx, int mouseX, int mouseY) {
      int x = guiLeft + 181;
      int y = guiTop + 141;
      boolean hovered = this.mouseOverUnlockButton(mouseX, mouseY);
      boolean enabled = this.canUnlockSelectedSkill();
      int v = 164;
      if (enabled && hovered) {
         v = 180;
      }

      gfx.blit(BACKGROUND, x, y, 0.0F, v, 43, 16, 256, 256);
      String text = "Unlock";
      int textColor;
      if (enabled) {
         textColor = hovered ? 16733525 : 16777215;
      } else {
         textColor = 11184810;
      }

      int textWidth = this.font.width(text);
      int textHeight = 9;
      int textX = x + (43 - textWidth) / 2;
      int textY = y + (16 - textHeight) / 2;
      gfx.drawString(this.font, text, textX, textY + 1, textColor);
      if (hovered) {
         List<Component> tooltip = STSkillLockTooltips.unlockTooltip(this.selectedAbility, this.existenceData);
         MutableComponent combined = Component.empty().copy();

         for (int i = 0; i < tooltip.size(); i++) {
            combined.append(tooltip.get(i));
            if (i < tooltip.size() - 1) {
               combined.append(Component.literal("\n"));
            }
         }

         this.setTooltipForNextRenderPass(combined);
      }
   }

   private boolean mouseOverLockButton(double mouseX, double mouseY) {
      int x = guiLeft + 118;
      int y = guiTop + 141;
      return RenderHelper.mouseOver(mouseX, mouseY, x, x + 43, y, y + 16);
   }

   private boolean mouseOverUnlockButton(double mouseX, double mouseY) {
      int x = guiLeft + 181;
      int y = guiTop + 141;
      return RenderHelper.mouseOver(mouseX, mouseY, x, x + 43, y, y + 16);
   }

   private void tryLockSelectedSkill() {
      if (this.selectedAbility != null) {
         ResourceLocation id = STSkillLockRules.skillId(this.selectedAbility);
         if (id != null) {
            if (this.selectedAbility.isTemporarySkill()) {
               this.player.playSound(SoundEvents.ITEM_BREAK, 1.0F, 0.8F);
               this.player.displayClientMessage(Component.translatable("stextras.skill_lock.cannot_lock.temporary"), true);
            } else if (!STSkillLockRules.isSkillValid(id)) {
               this.player.playSound(SoundEvents.ITEM_BREAK, 1.0F, 0.8F);
               this.player.displayClientMessage(Component.translatable("stextras.skill_lock.invalid_skill"), true);
            } else if (STSkillLockRules.isSkillLocked(id)) {
               this.player.playSound(SoundEvents.ITEM_BREAK, 1.0F, 1.2F);
               this.player.displayClientMessage(Component.translatable("stextras.skill_lock.already_locked"), true);
            } else if (!STSkillLockRules.canLockSkill()) {
               this.player.playSound(SoundEvents.ITEM_BREAK, 1.0F, 0.5F);
               this.player.displayClientMessage(Component.translatable("stextras.skill_lock.no_slots"), true);
            } else {
               SkillLockPacket.sendToServer(id);
               this.player.playSound((SoundEvent)STExtrasSoundEvents.SKILLLOCK_LOCK.get(), 1.0F, 1.0F);
            }
         }
      }
   }

   private void tryUnlockSelectedSkill() {
      if (this.selectedAbility != null) {
         ResourceLocation id = STSkillLockRules.skillId(this.selectedAbility);
         if (id != null) {
            if (!STSkillLockRules.isSkillLocked(id)) {
               this.player.playSound(SoundEvents.ITEM_BREAK, 1.0F, 1.2F);
               this.player.displayClientMessage(Component.translatable("stextras.skill_lock.not_locked"), true);
            } else if (STSkillLockRules.isSkillAdminLocked(id)) {
               this.player.playSound(SoundEvents.ITEM_BREAK, 1.0F, 1.2F);
               this.player.displayClientMessage(Component.translatable("stextras.skill_lock.admin.deny_unlock"), true);
            } else if (!STSkillLockRules.isSkillValid(id)) {
               this.player.playSound(SoundEvents.ITEM_BREAK, 1.0F, 1.2F);
               this.player.displayClientMessage(Component.translatable("stextras.skill_lock.invalid_skill"), true);
            } else {
               double requiredEP = STExtarsStorage.Player.getRequiredEpToUnlockSkill();
               int requiredSoulGrade = STExtarsStorage.Player.getRequiredSoulGradeToUnlockSkill();
               double requiredMagicule = STSkillLockRules.requiredMagicule();
               if (this.existenceData.getEP() < requiredEP) {
                  this.player.playSound(SoundEvents.ITEM_BREAK, 1.0F, 1.2F);
                  this.player
                     .displayClientMessage(
                        Component.translatable("stextras.skill_lock.not_enough_ep", new Object[]{TextUtil.formatNumber((long)requiredEP)}), true
                     );
               } else if (STExtarsStorage.Player.getSoulGrade() < requiredSoulGrade) {
                  this.player.playSound(SoundEvents.ITEM_BREAK, 1.0F, 1.2F);
                  this.player.displayClientMessage(Component.translatable("stextras.skill_lock.not_enough_soul_grade", new Object[]{requiredSoulGrade}), true);
               } else if (this.existenceData.getMagicule() < requiredMagicule) {
                  this.player.playSound(SoundEvents.ITEM_BREAK, 1.0F, 1.2F);
                  this.player
                     .displayClientMessage(
                        Component.translatable("stextras.skill_lock.not_enough_magicule", new Object[]{TextUtil.formatNumber((long)requiredMagicule)}), true
                     );
               } else {
                  SkillUnlockPacket.sendToServer(id);
                  this.player.playSound((SoundEvent)STExtrasSoundEvents.SKILLLOCK_UNLOCK.get(), 1.0F, 1.0F);
               }
            }
         }
      }
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (this.searchField != null) {
         int pX = this.searchField.getX();
         int pY = this.searchField.getY();
         int sfW = this.searchField.getWidth();
         int sfH = this.searchField.getHeight();
         if (!RenderHelper.mouseOver(mouseX, mouseY, pX, pX + sfW, pY, pY + sfH)) {
            this.searchField.setFocused(false);
         }
      }

      if (this.clickedScrollBar(mouseX, mouseY)) {
         return true;
      }

      if (this.mouseOverLockButton(mouseX, mouseY) && button == 0) {
         this.tryLockSelectedSkill();
         return true;
      }

      if (this.mouseOverUnlockButton(mouseX, mouseY) && button == 0) {
         this.tryUnlockSelectedSkill();
         return true;
      }

      int pX = guiLeft + 6;
      int pY = guiTop + 49;
      int visibleCount = Math.min(8, Math.max(0, this.filtered.size() - this.listStartIndex));
      int end = this.listStartIndex + visibleCount;

      for (int i = this.listStartIndex; i < end; i++) {
         if (RenderHelper.mouseOver(mouseX, mouseY, pX, pX + 89, pY, pY + 13)) {
            this.selectedAbility = this.filtered.get(i);
            this.textStartIndex = 0;
            ScreenHelper.clicked();
            return true;
         }

         pY += 13;
      }

      return super.mouseClicked(mouseX, mouseY, button);
   }

   public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
      return this.draggedScrollBar(mouseY) ? true : super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double deltaX, double deltaY) {
      if (RenderHelper.mouseOver(mouseX, mouseY, guiLeft + 6, guiLeft + 6 + 89, guiTop + 49, guiTop + 49 + 102)) {
         this.scrolledScrollBar(deltaY);
         return true;
      } else if (RenderHelper.mouseOver(mouseX, mouseY, guiLeft + 123, guiLeft + 123 + 98, guiTop + 66, guiTop + 66 + 64)) {
         int maxLines = Math.max(1, 64 / 9);
         this.textStartIndex = Math.max(this.textStartIndex + (int)(-deltaY), 0);
         return true;
      } else {
         return super.mouseScrolled(mouseX, mouseY, deltaX, deltaY);
      }
   }

   protected void updateAllAbilities() {
      this.abilities.clear();
      this.abilities.addAll(STSkillLockFilters.collectAvailableAbilities(this.player));
   }

   protected void updateFilteredAbilities() {
      this.filtered.clear();
      this.filtered.addAll(STSkillLockFilters.filterAndSort(this.abilities, this.nameFilter, this.player));
      this.scrolledScrollBar(0.0);
   }

   public int getScrollBarX() {
      return guiLeft + 98;
   }

   public int getScrollBarY() {
      return guiTop + 49;
   }

   public int getScrollBarTotalSpace() {
      return 104;
   }

   public int getScrollBarListSize() {
      return this.filtered.size();
   }

   public int getScrollBarRenderCount() {
      return 8;
   }

   public float getScrollOffset() {
      return this.scrollOffset;
   }

   public boolean isScrolling() {
      return this.scrolling;
   }

   public void setListStartIndex(int i) {
      this.listStartIndex = i;
   }

   public void setScrolling(boolean value) {
      this.scrolling = value;
   }

   public void setScrollOffset(float value) {
      this.scrollOffset = value;
   }
}
