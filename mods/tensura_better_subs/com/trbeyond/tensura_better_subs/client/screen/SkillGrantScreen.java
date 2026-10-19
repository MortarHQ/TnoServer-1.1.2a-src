package com.trbeyond.tensura_better_subs.client.screen;

import com.trbeyond.tensura_better_subs.ability.skill.SkillGranting;
import com.trbeyond.tensura_better_subs.network.c2s.SubordinateSkillGrantingPacket;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.resist.ResistSkill;
import io.github.manasmods.tensura.client.screen.templates.IScrollBar;
import io.github.manasmods.tensura.client.screen.templates.SimpleScreen;
import io.github.manasmods.tensura.client.screen.widgets.ExpandedEditBox;
import io.github.manasmods.tensura.client.screen.widgets.SimpleButton;
import io.github.manasmods.tensura.util.client.RenderHelper;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class SkillGrantScreen extends SimpleScreen implements IScrollBar {
   public static Component TITLE = Component.translatable("tensura_better_subs.skill_grant_menu");
   protected static final ResourceLocation ABILITY_BAR = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/ability_button.png");
   protected static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(
      "tensura", "textures/gui/ability_selection/ability_selection_gui.png"
   );
   protected final List<ManasSkillInstance> abilities = new ArrayList<>();
   protected final List<ManasSkillInstance> filtered = new ArrayList<>();
   protected final SimpleButton returnButton;
   protected ExpandedEditBox searchField;
   protected ManasSkillInstance selectedAbility;
   protected String nameFilter;
   protected float scrollOffset;
   protected boolean scrolling;
   protected int listStartIndex;
   protected int textStartIndex;
   private int interactionCd;
   protected LivingEntity entity;
   protected ResourceLocation skillGrantingSkill;
   private static final int WIDTH = 233;
   private static final int HEIGHT = 140;
   private static final int HEIGHT_OFFSET = 23;

   public SkillGrantScreen(LivingEntity entity, ResourceLocation skillGrantingSkill) {
      super(TITLE, 233, 140);
      this.entity = entity;
      this.skillGrantingSkill = skillGrantingSkill;
      this.returnButton = new SimpleButton(
         0,
         0,
         18,
         10,
         null,
         null,
         Component.translatable("tooltip.tensura.return"),
         self -> ScreenHelperSubordinates.clicked(SoundEvents.UI_BUTTON_CLICK, () -> this.minecraft.setScreen(null))
      );
   }

   protected void init() {
      super.init();
      this.shouldRenderWidgets = false;
      Component hint = Component.translatable("tensura.ability_selection.suggestion").withColor(11184810);
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
            Component.literal("toggledoff")
         )
      );
      this.searchField = new ExpandedEditBox(this.font, 0, 0, 78, 9, Component.empty());
      this.searchField.setHint(hint);
      this.searchField.setBordered(false);
      this.searchField.setMultipleSuggestions(suggestions, "f:");
      this.searchField.setResponder(s -> {
         this.nameFilter = s;
         this.setScrollOffset(0.0F);
         this.setListStartIndex(0);
         this.updateFilteredAbilities();
      });
      this.searchField.setShouldRenderMultipleSuggestions(() -> this.searchField.getValue().startsWith("f:"));
      this.searchField.setX(guiLeft + 28);
      this.searchField.setY(guiTop + 31 - 23);
      this.returnButton.setX(guiLeft + 5);
      this.returnButton.setY(guiTop + 28 - 23);
      this.addRenderableWidget(this.returnButton);
      this.addRenderableWidget(this.searchField);
      if (this.nameFilter == null) {
         this.nameFilter = "";
      } else {
         this.searchField.setValue(this.nameFilter);
      }
   }

   protected void updateFilteredAbilities() {
      this.filtered.clear();
      if (this.nameFilter != null && !this.nameFilter.isEmpty() && !this.nameFilter.isBlank()) {
         Predicate<? super ManasSkillInstance> predicate = instance -> {
            if (instance.getSkill() instanceof TensuraSkill skill) {
               return switch (this.nameFilter) {
                  case "f:" -> false;
                  case "f:active" -> skill.canBeSlotted(instance, this.entity, 0);
                  case "f:passive" -> !skill.canBeSlotted(instance, this.entity, 0);
                  case "f:mastered" -> instance.isMastered(this.entity);
                  case "f:learned" -> instance.getMastery() >= 0.0 || instance.isMastered(this.entity);
                  case "f:learning" -> instance.getMastery() < 0.0;
                  case "f:temporary" -> instance.isTemporarySkill();
                  case "f:toggleable" -> instance.canBeToggled(this.entity);
                  case "f:toggledon" -> instance.isToggled();
                  case "f:toggledoff" -> instance.canBeToggled(this.entity) && !instance.isToggled();
                  default -> {
                     Component name = skill.getName();
                     yield name == null ? true : name.getString().toLowerCase().contains(this.nameFilter.toLowerCase());
                  }
               };
            } else {
               return false;
            }
         };
         this.filtered.addAll(this.abilities.stream().filter(predicate).toList());
      } else {
         this.filtered.addAll(this.abilities);
      }

      this.filtered.sort(Comparator.comparing(skill -> skill.getDisplayName().getString()));
      this.scrolledScrollBar(0.0);
   }

   protected void updateAllAbilities() {
      this.abilities.clear();
      this.abilities.addAll(new ArrayList<>(SkillAPI.getSkillsFrom(this.player).getLearnedSkills()));
      this.abilities.removeIf(ability -> !this.isValidSkillInstance(ability));

      for (ManasSkillInstance skillInstance : new ArrayList(SkillAPI.getSkillsFrom(this.entity).getLearnedSkills())) {
         this.abilities.removeIf(skill -> skill.getSkillId().equals(skillInstance.getSkillId()));
      }
   }

   public boolean isValidSkillInstance(ManasSkillInstance instance) {
      return SkillGranting.isValidSkillInstance(instance, this.player);
   }

   public void renderBackground(GuiGraphics graphics, int mX, int mY, float partialTick) {
      if (!ScreenHelperSubordinates.isScreenValid(this.entity, this.player)) {
         this.minecraft.setScreen(null);
      }

      graphics.drawString(this.font, TITLE, guiLeft + 105, guiTop + 10 - 23, Color.WHITE.getRGB());
      graphics.blit(BACKGROUND, guiLeft, guiTop, 0, 23, this.imageWidth, this.imageHeight);
      graphics.blit(BACKGROUND, guiLeft + 5, guiTop + 145 - 23, 5, 168, 104, 15);
      super.renderWidgets(graphics, mX, mY, partialTick);
      if (this.interactionCd > 0) {
         this.interactionCd--;
      }

      this.updateAllAbilities();
      this.updateFilteredAbilities();
      int update = this.abilities.indexOf(this.selectedAbility);
      if (update != -1) {
         this.selectedAbility = this.abilities.get(update);
      }

      int pX = guiLeft + 6;
      int pY = guiTop + 46 - 23;

      for (int i = this.listStartIndex; i < this.listStartIndex + this.getScrollBarRenderCount() && i < this.filtered.size(); i++) {
         ManasSkillInstance instance = this.filtered.get(i);
         ManasSkill manasSkill = instance.getSkill();
         Component name = ((TensuraSkill)manasSkill).getColoredName();
         if (name != null) {
            String string = name.getString();
            TextColor color = name.getStyle().getColor();
            if (color == null) {
               color = TextColor.fromRgb(16711680);
            }

            boolean hovered = RenderHelper.mouseOver(mX, mY, pX, pX + 89, pY, pY + 13);
            if (hovered) {
               this.setTooltipForNextRenderPass(name);
            }

            if (string.length() > 14) {
               string = string.substring(0, 14).concat("...");
               name = Component.literal(string).setStyle(name.getStyle());
            }

            if (instance.getMastery() < 0.0) {
               name = name.copy().withColor(8750469);
            }

            graphics.blit(ABILITY_BAR, pX, pY, 0.0F, hovered ? 13.0F : 0.0F, 89, 13, 89, 26);
            graphics.drawString(this.font, name, pX + 3, pY + 3, color.getValue());
            pY += 13;
         }
      }

      this.renderScrollBar(graphics, mX, mY);
      this.renderAbility(graphics, mX, mY);
      if (this.selectedAbility != null) {
         graphics.drawString(this.font, Component.literal("Provide Skill"), guiLeft + 9, guiTop + 149 - 23, Color.CYAN.getRGB());
      }
   }

   protected void renderAbility(GuiGraphics graphics, double mX, double mY) {
      if (this.selectedAbility != null) {
         TensuraSkill tensuraSkill = (TensuraSkill)this.selectedAbility.getSkill();
         int mastery = (int)this.selectedAbility.getMastery();
         int masteryUV = 220;
         Component masteryTooltip;
         if (mastery < 0) {
            int points = mastery + 100;
            if (tensuraSkill instanceof ResistSkill resistSkill) {
               points = mastery + resistSkill.getLearningPointRequirement();
               int learning = resistSkill.getLearningPointRequirement();
               mastery = 106 * (mastery + learning) / learning;
            } else {
               mastery = (int)(106 * (mastery + 100) / 100.0F);
            }

            masteryUV += 9;
            masteryTooltip = Component.translatable("tensura.ability_selection.learning", new Object[]{points});
         } else {
            int maxMastery = tensuraSkill.getMaxMastery();
            int points = mastery * 100 / maxMastery;
            mastery = 106 * mastery / maxMastery;
            masteryTooltip = Component.translatable("tensura.ability_selection.mastery", new Object[]{points + "%"});
         }

         int masteryX = guiLeft + 119;
         int masteryY = guiTop + 146 - 23;
         ResourceLocation icon = tensuraSkill.getSkillIcon();
         Component description = tensuraSkill.getSkillDescription();
         List<FormattedCharSequence> sequences = this.font.split(description, 94);
         int descriptionStart = Mth.clamp(this.textStartIndex, 0, Math.max(0, sequences.size() - 7));
         int descriptionEnd = Math.min(descriptionStart + 7, sequences.size());
         this.textStartIndex = descriptionStart;
         boolean hoveringMastery = RenderHelper.mouseOver(mX, mY, masteryX, masteryX + 106, masteryY, masteryY + 9);
         boolean hoveringIcon = false;
         boolean hoveringDescription = false;
         if (!hoveringMastery) {
            hoveringIcon = RenderHelper.mouseOver(mX, mY, guiLeft + 156, guiLeft + 188, guiTop + 29 - 23, guiTop + 61 - 23);
            if (!hoveringIcon) {
               hoveringDescription = RenderHelper.mouseOver(mX, mY, guiLeft + 123, guiLeft + 221, guiTop + 71 - 23, guiTop + 137 - 23);
            }
         }

         graphics.blit(BACKGROUND, masteryX, masteryY, 1, masteryUV, mastery, 9);
         if (icon != null) {
            graphics.blit(icon, guiLeft + 156, guiTop + 29 - 23, 0.0F, 0.0F, 32, 32, 32, 32);
         }

         if (hoveringMastery) {
            this.setTooltipForNextRenderPass(masteryTooltip);
         } else if (hoveringIcon) {
            MutableComponent var10000;
            if (tensuraSkill instanceof TensuraSkill skill) {
               var10000 = skill.getColoredName();
            } else {
               var10000 = tensuraSkill.getChatDisplayName(false);
            }

            MutableComponent hoverMessage = var10000;
            ResourceLocation identifier = SkillAPI.getSkillRegistry().getId(tensuraSkill);
            if (identifier != null) {
               hoverMessage.append("\n").append(Component.literal(identifier.toString()).withColor(5592405));
            }

            this.setTooltipForNextRenderPass(hoverMessage);
         }

         RenderHelper.drawScrollableTextInAreaSetHighlight(
            graphics, this.font, sequences, guiLeft + 123, guiTop + 71 - 23, 98, 64, 0, descriptionStart, descriptionEnd, hoveringDescription, 8750469, 863042
         );
      }
   }

   public boolean mouseClicked(double mX, double mY, int button) {
      int pX = this.searchField.getX();
      int pY = this.searchField.getY();
      int sfW = this.searchField.getWidth();
      int sfH = this.searchField.getHeight();
      if (!RenderHelper.mouseOver(mX, mY, pX, pX + sfW, pY, pY + sfH)) {
         this.searchField.setFocused(false);
      }

      if (this.clickedScrollBar(mX, mY)) {
         return true;
      }

      pX = guiLeft + 6;
      pY = guiTop + 33 - 23;

      for (int i = this.listStartIndex; i < this.listStartIndex + this.getScrollBarRenderCount() && i < this.filtered.size(); i++) {
         pY += 13;
         if (RenderHelper.mouseOver(mX, mY, pX, pX + 89, pY, pY + 13)) {
            this.selectedAbility = this.filtered.get(i);
            this.textStartIndex = 0;
            ScreenHelperSubordinates.clicked();
            return true;
         }
      }

      if (RenderHelper.mouseOver(mX, mY, guiLeft + 5, guiLeft + 5 + 104, guiTop + 145 - 23, guiTop + 145 - 23 + 15) && this.selectedAbility != null) {
         NetworkManager.sendToServer(new SubordinateSkillGrantingPacket(this.entity.getUUID(), this.selectedAbility.getSkillId(), this.skillGrantingSkill));
         ScreenHelperSubordinates.clicked();
         this.minecraft.setScreen(null);
      }

      return super.mouseClicked(mX, mY, button);
   }

   public boolean mouseDragged(double mX, double mY, int button, double dragX, double dragY) {
      return this.draggedScrollBar(mY) ? true : super.mouseDragged(mX, mY, button, dragX, dragY);
   }

   public boolean mouseScrolled(double mX, double mY, double deltaX, double deltaY) {
      if (RenderHelper.mouseOver(mX, mY, guiLeft + 5, guiLeft + 109, guiTop + 45 - 23, guiTop + 138 - 23)) {
         this.scrolledScrollBar(deltaY);
      }

      if (RenderHelper.mouseOver(mX, mY, guiLeft + 123, guiLeft + 221, guiTop + 78 - 23, guiTop + 135 - 23)) {
         this.textStartIndex = Math.max(this.textStartIndex + (int)(-deltaY), 0);
      }

      return super.mouseScrolled(mX, mY, deltaX, deltaY);
   }

   public int getScrollBarX() {
      return guiLeft + 98;
   }

   public int getScrollBarY() {
      return guiTop + 46 - 23;
   }

   public int getScrollBarTotalSpace() {
      return 91;
   }

   public int getScrollBarListSize() {
      return this.filtered.size();
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
}
