package com.trbeyond.tensura_better_subs.client.screen;

import com.trbeyond.tensura_better_subs.network.c2s.RequestSubordinateAbilityModeChangePacket;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.battlewill.Battlewill;
import io.github.manasmods.tensura.ability.magic.Magic;
import io.github.manasmods.tensura.ability.magic.Magic.MagicType;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.skill.resist.ResistSkill;
import io.github.manasmods.tensura.client.screen.AbilityCategoriesScreen.AbilityType;
import io.github.manasmods.tensura.client.screen.templates.IScrollBar;
import io.github.manasmods.tensura.client.screen.templates.SimpleScreen;
import io.github.manasmods.tensura.client.screen.widgets.ExpandedEditBox;
import io.github.manasmods.tensura.client.screen.widgets.SimpleButton;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ability.IAbility;
import io.github.manasmods.tensura.util.client.RenderHelper;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class AbilitySelectionScreenEntity extends SimpleScreen implements IScrollBar {
   public static final Component TITLE = ScreenHelperSubordinates.getMenuName(0);
   protected static final ResourceLocation CHECKBOX = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/checkbox.png");
   protected static final ResourceLocation ABILITY_BAR = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/ability_button.png");
   protected static final ResourceLocation PRESET_BUTTON = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/skill/preset_button.png");
   protected static final ResourceLocation COOLDOWN_CLOCK = ResourceLocation.fromNamespaceAndPath(
      "tensura", "textures/gui/ability_selection/cooldown_clock.png"
   );
   protected static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(
      "tensura", "textures/gui/ability_selection/ability_selection_gui.png"
   );
   protected final SkillType skillFilter;
   protected final MagicType magicFilter;
   protected final AbilityType abilityType;
   protected final List<ManasSkillInstance> abilities = new ArrayList<>();
   protected final List<ManasSkillInstance> filtered = new ArrayList<>();
   protected SimpleButton returnButton;
   protected ExpandedEditBox searchField;
   protected IAbility abilityData;
   protected ManasSkill slot1;
   protected ManasSkill slot2;
   protected ManasSkill slot3;
   protected ManasSkillInstance selectedAbility;
   protected ManasSkillInstance slot1Instance;
   protected ManasSkillInstance slot2Instance;
   protected ManasSkillInstance slot3Instance;
   protected String nameFilter;
   protected float scrollOffset;
   protected boolean scrolling;
   protected boolean slotsDropdown;
   protected int listStartIndex;
   protected int textStartIndex;
   protected int activePreset;
   protected int selectedPreset;
   private int interactionCd;
   protected LivingEntity entity;

   public AbilitySelectionScreenEntity(AbilityType abilityType, SkillType skillFilter, MagicType magicFilter, LivingEntity entity) {
      super(TITLE, 254, 163);
      this.abilityType = abilityType;
      this.skillFilter = skillFilter;
      this.magicFilter = magicFilter;
      this.entity = entity;
   }

   protected void init() {
      super.init();
      this.shouldRenderWidgets = false;
      this.abilityData = TensuraStorages.getAbilityFrom(this.entity);
      this.activePreset = this.abilityData.getActivePreset();
      this.selectedPreset = this.activePreset;
      this.loadPreset(false);
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

      byte var10000 = switch (this.abilityType) {
         case SKILL -> 1;
         case MAGIC -> 2;
         default -> 4;
      };
      int tab = var10000;
      this.returnButton = new SimpleButton(
         0,
         0,
         18,
         10,
         (ResourceLocation)null,
         (Component)null,
         Component.translatable("tooltip.tensura.return"),
         self -> ScreenHelperSubordinates.clicked(SoundEvents.UI_BUTTON_CLICK, () -> ScreenHelperSubordinates.openScreen(tab, this.entity))
      );
      guiLeft--;
      switch (this.abilityType) {
         case SKILL:
            var10000 = 1;
            break;
         case MAGIC:
            var10000 = 2;
            break;
         case BATTLEWILL:
            var10000 = 3;
      }

      int currentTab = var10000;
      List<SimpleButton> tabs = ScreenHelperSubordinates.getMenuTabs(currentTab, guiLeft, guiTop, this.entity);
      tabs.forEach(x$0 -> {
         SimpleButton buttonx = (SimpleButton)this.addRenderableWidget(x$0);
      });
      this.searchField.setX(guiLeft + 28);
      this.searchField.setY(guiTop + 31);
      this.returnButton.setX(guiLeft + 5);
      this.returnButton.setY(guiTop + 28);
      this.addRenderableWidget(this.returnButton);
      this.addRenderableWidget(this.searchField);
      if (this.nameFilter == null) {
         this.nameFilter = "";
      } else {
         this.searchField.setValue(this.nameFilter);
      }

      for (int i = 0; i < 9; i++) {
         int finalI = i;
         SimpleButton button = new SimpleButton(
            guiLeft + 5 + i * 10,
            guiTop + 147,
            10,
            11,
            PRESET_BUTTON,
            (Component)null,
            (Component)null,
            self -> {
               if (finalI == this.selectedPreset && this.activePreset != this.selectedPreset) {
                  NetworkManager.sendToServer(
                     RequestSubordinateAbilityModeChangePacket.changePresetPacket(this.entity.getUUID(), finalI - this.activePreset, true)
                  );
                  this.activePreset = this.selectedPreset;
               } else {
                  this.selectedPreset = finalI;
                  this.loadPreset(true);
               }

               this.slotsDropdown = true;
            }
         );
         button.setUvOffsetCheck(() -> this.selectedPreset == finalI || button.isHovered());
         this.addRenderableWidget(button);
      }
   }

   public void renderBackground(GuiGraphics graphics, int mX, int mY, float partialTick) {
      if (!ScreenHelperSubordinates.isScreenValid(this.entity, this.player)) {
         this.minecraft.setScreen(null);
      }

      graphics.drawString(this.font, TITLE, guiLeft + 105, guiTop + 10, Color.WHITE.getRGB());
      graphics.blit(BACKGROUND, guiLeft, guiTop, 0, 0, this.imageWidth, this.imageHeight);
      super.renderWidgets(graphics, mX, mY, partialTick);
      byte var10000 = 0;
      switch (this.abilityType) {
         case SKILL:
            var10000 = 1;
            break;
         case MAGIC:
            var10000 = 2;
            break;
         case BATTLEWILL:
            var10000 = 3;
      }

      int id = var10000;
      ScreenHelperSubordinates.renderTabIcon(graphics, this, id, guiLeft, guiTop, mX, mY);
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
      int pY = guiTop + 46;

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
      this.renderPreset(graphics, mX, mY);
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

      if (RenderHelper.mouseOver(mX, mY, guiLeft + 96, guiLeft + 109, guiTop + 147, guiTop + 158)) {
         this.slotsDropdown = !this.slotsDropdown;
         ScreenHelperSubordinates.clicked();
         return true;
      }

      if (RenderHelper.mouseOver(mX, mY, guiLeft + 198, guiLeft + 211, guiTop + 38, guiTop + 52)) {
         if (this.interactionCd > 0) {
            return false;
         }

         if (this.selectedAbility == null) {
            return false;
         }

         if (!this.selectedAbility.canBeToggled(this.entity)) {
            return false;
         }

         if (!this.selectedAbility.canInteractSkill(this.entity)) {
            return false;
         }

         ScreenHelperSubordinates.clicked();
         NetworkManager.sendToServer(RequestSubordinateAbilityModeChangePacket.toggleAbilityPacket(this.entity.getUUID(), this.selectedAbility.getSkillId()));
         this.interactionCd = 10;
         return true;
      } else {
         pX = guiLeft + 6;
         pY = guiTop + 33;

         for (int i = this.listStartIndex; i < this.listStartIndex + this.getScrollBarRenderCount() && i < this.filtered.size(); i++) {
            pY += 13;
            if (RenderHelper.mouseOver(mX, mY, pX, pX + 89, pY, pY + 13)) {
               this.selectedAbility = this.filtered.get(i);
               this.textStartIndex = 0;
               ScreenHelperSubordinates.clicked();
               return true;
            }
         }

         int hoveredSlot = this.getHoveredSlot(mX, mY);
         return hoveredSlot != 0 ? this.handleClickedSlot(hoveredSlot, button) : super.mouseClicked(mX, mY, button);
      }
   }

   public boolean mouseDragged(double mX, double mY, int button, double dragX, double dragY) {
      return this.draggedScrollBar(mY) ? true : super.mouseDragged(mX, mY, button, dragX, dragY);
   }

   public boolean mouseScrolled(double mX, double mY, double deltaX, double deltaY) {
      if (RenderHelper.mouseOver(mX, mY, guiLeft + 5, guiLeft + 109, guiTop + 45, guiTop + 138)) {
         this.scrolledScrollBar(deltaY);
      }

      if (RenderHelper.mouseOver(mX, mY, guiLeft + 123, guiLeft + 221, guiTop + 78, guiTop + 135)) {
         this.textStartIndex = Math.max(this.textStartIndex + (int)(-deltaY), 0);
      }

      int hoveredSlot = this.getHoveredSlot(mX, mY);
      if (hoveredSlot != 0 && this.interactionCd == 0) {
         switch (hoveredSlot) {
            case 1:
               if (this.slot1 != null && this.slot1Instance.getModes() > 1) {
                  this.handleScrolledSlot(0, this.slot1, this.slot1Instance, deltaY < 0.0);
               }
               break;
            case 2:
               if (this.slot2 != null && this.slot2Instance.getModes() > 1) {
                  this.handleScrolledSlot(1, this.slot2, this.slot2Instance, deltaY < 0.0);
               }
               break;
            case 3:
               if (this.slot3 != null && this.slot3Instance.getModes() > 1) {
                  this.handleScrolledSlot(2, this.slot3, this.slot3Instance, deltaY < 0.0);
               }
         }

         return true;
      } else {
         return super.mouseScrolled(mX, mY, deltaX, deltaY);
      }
   }

   protected void handleScrolledSlot(int slotIndex, ManasSkill slot, ManasSkillInstance instance, boolean reverse) {
      if (this.interactionCd == 0 && instance.canInteractSkill(this.entity) && slot instanceof TensuraSkill skill) {
         this.interactionCd = 10;
         NetworkManager.sendToServer(RequestSubordinateAbilityModeChangePacket.changeModePacket(this.entity.getUUID(), slotIndex, this.selectedPreset, reverse));
         int nextMode = skill.nextMode(this.entity, instance, this.abilityData.getAbilitySlot(this.selectedPreset, slotIndex).getMode(), reverse);
         if (nextMode != -1) {
            this.abilityData.setAbilitySlot(this.selectedPreset, slotIndex, skill, nextMode);
         }
      }
   }

   public void renderTooltip(GuiGraphics graphics, int mX, int mY) {
      int hoveredSlot = this.getHoveredSlot(mX, mY);
      if (hoveredSlot != 0) {
         Component tooltip = null;
         switch (hoveredSlot) {
            case 1:
               tooltip = this.getSlotTooltip(hoveredSlot, this.slot1, this.slot1Instance);
               break;
            case 2:
               tooltip = this.getSlotTooltip(hoveredSlot, this.slot2, this.slot2Instance);
               break;
            case 3:
               tooltip = this.getSlotTooltip(hoveredSlot, this.slot3, this.slot3Instance);
         }

         if (tooltip != null) {
            this.setTooltipForNextRenderPass(tooltip);
         }
      }
   }

   protected void renderAbility(GuiGraphics graphics, double mX, double mY) {
      if (this.selectedAbility != null) {
         ManasSkill manasSkill = this.selectedAbility.getSkill();
         int mastery = (int)this.selectedAbility.getMastery();
         int masteryUV = 220;
         Component masteryTooltip;
         if (mastery < 0) {
            int points = mastery + 100;
            if (manasSkill instanceof ResistSkill resistSkill) {
               points = mastery + resistSkill.getLearningPointRequirement();
               int learning = resistSkill.getLearningPointRequirement();
               mastery = 106 * (mastery + learning) / learning;
            } else {
               mastery = (int)(106 * (mastery + 100) / 100.0F);
            }

            masteryUV += 9;
            masteryTooltip = Component.translatable("tensura.ability_selection.learning", new Object[]{points});
         } else {
            int maxMastery = manasSkill.getMaxMastery();
            int points = mastery * 100 / maxMastery;
            mastery = 106 * mastery / maxMastery;
            masteryTooltip = Component.translatable("tensura.ability_selection.mastery", new Object[]{points + "%"});
         }

         int masteryX = guiLeft + 119;
         int masteryY = guiTop + 146;
         int[] cooldowns = this.getModeCooldowns(this.selectedAbility);
         ResourceLocation icon = manasSkill.getSkillIcon();
         Component description = manasSkill.getSkillDescription();
         List<FormattedCharSequence> sequences = this.font.split(description, 94);
         Component tooltip = Component.translatable("tensura.ability_selection.untoggleable");
         int descriptionStart = Mth.clamp(this.textStartIndex, 0, Math.max(0, sequences.size() - 7));
         int descriptionEnd = Math.min(descriptionStart + 7, sequences.size());
         this.textStartIndex = descriptionStart;
         boolean hasCooldowns = false;
         boolean isToggled = this.selectedAbility.isToggled();
         boolean canBeToggled = this.selectedAbility.canBeToggled(this.entity);
         boolean canToggleAndInteract = canBeToggled && this.selectedAbility.canInteractSkill(this.entity);
         boolean hoveringMastery = RenderHelper.mouseOver(mX, mY, masteryX, masteryX + 106, masteryY, masteryY + 9);
         boolean hoveringIcon = false;
         boolean hoveringToggle = false;
         boolean hoveringDescription = false;
         boolean hoveringCooldownClock = false;
         if (!hoveringMastery) {
            hoveringIcon = RenderHelper.mouseOver(mX, mY, guiLeft + 156, guiLeft + 188, guiTop + 29, guiTop + 61);
            if (!hoveringIcon) {
               hoveringToggle = RenderHelper.mouseOver(mX, mY, guiLeft + 198, guiLeft + 211, guiTop + 38, guiTop + 52);
               if (!hoveringToggle) {
                  hoveringDescription = RenderHelper.mouseOver(mX, mY, guiLeft + 123, guiLeft + 221, guiTop + 71, guiTop + 137);
                  if (!hoveringDescription) {
                     hoveringCooldownClock = RenderHelper.mouseOver(mX, mY, guiLeft + 134, guiLeft + 146, guiTop + 38, guiTop + 52);
                  }
               }
            }
         }

         if (canBeToggled) {
            tooltip = Component.translatable("options." + (isToggled ? "on" : "off"));
         }

         graphics.blit(BACKGROUND, masteryX, masteryY, 1, masteryUV, mastery, 9);
         if (icon != null) {
            graphics.blit(icon, guiLeft + 156, guiTop + 29, 0.0F, 0.0F, 32, 32, 32, 32);
         }

         if (canToggleAndInteract) {
            graphics.blit(CHECKBOX, guiLeft + 198, guiTop + 39, hoveringToggle ? 13.0F : 0.0F, isToggled ? 13.0F : 0.0F, 13, 13, 26, 26);
         }

         for (int cooldown : cooldowns) {
            if (cooldown > 0) {
               hasCooldowns = true;
               break;
            }
         }

         if (hasCooldowns) {
            graphics.blit(COOLDOWN_CLOCK, guiLeft + 134, guiTop + 38, 0.0F, 0.0F, 12, 14, 12, 14);
         }

         if (hoveringToggle) {
            this.setTooltipForNextRenderPass(tooltip);
         } else if (hoveringMastery) {
            this.setTooltipForNextRenderPass(masteryTooltip);
         } else if (hoveringIcon) {
            MutableComponent var10000;
            if (manasSkill instanceof TensuraSkill skill) {
               var10000 = skill.getColoredName();
            } else {
               var10000 = manasSkill.getChatDisplayName(false);
            }

            MutableComponent hoverMessage = var10000;
            ResourceLocation identifier = SkillAPI.getSkillRegistry().getId(manasSkill);
            if (identifier != null) {
               hoverMessage.append("\n").append(Component.literal(identifier.toString()).withColor(5592405));
            }

            this.setTooltipForNextRenderPass(hoverMessage);
         } else if (hasCooldowns && hoveringCooldownClock && this.selectedAbility.getSkill() instanceof TensuraSkill skill) {
            boolean changed = false;
            MutableComponent hoverMessage = Component.translatable("tensura.ability_selection.cooldowns").withColor(5636095);

            for (int mode = 0; mode < cooldowns.length; mode++) {
               if (cooldowns[mode] != 0) {
                  changed = true;
                  hoverMessage.append(Component.literal("\n"))
                     .append(skill.getModeName(this.selectedAbility, mode))
                     .append(String.format(": %ds", cooldowns[mode]));
               }
            }

            if (changed) {
               this.setTooltipForNextRenderPass(hoverMessage);
            }
         }

         RenderHelper.drawScrollableTextInAreaSetHighlight(
            graphics, this.font, sequences, guiLeft + 123, guiTop + 71, 98, 64, 0, descriptionStart, descriptionEnd, hoveringDescription, 8750469, 863042
         );
      }
   }

   protected void renderPreset(GuiGraphics graphics, double mX, double mY) {
      int pX = guiLeft + 96;
      int pY = guiTop + 147;
      boolean hoveringDropdown = RenderHelper.mouseOver(mX, mY, pX, guiLeft + 109, pY, guiTop + 158);

      for (int i = 0; i < 9; i++) {
         int color = i == this.activePreset ? Color.GREEN.getRGB() : Color.LIGHT_GRAY.getRGB();
         Component text = Component.literal(String.valueOf(i + 1));
         RenderHelper.drawCenteredText(graphics, this.font, text, guiLeft + 5 + i * 10, guiTop + 149, 10, color, false);
      }

      if (this.slotsDropdown) {
         graphics.blit(BACKGROUND, guiLeft, guiTop + 160, 1, 164, 112, 55);

         for (int i = 0; i < 3; i++) {
            ManasSkill manasSkill = i == 0 ? this.slot1 : (i == 1 ? this.slot2 : this.slot3);
            MutableComponent name = this.skillName(manasSkill);
            TextColor color = name.getStyle().getColor();
            if (color == null) {
               color = TextColor.fromRgb(16711680);
            }

            int xName = guiLeft + 8;
            int yName = guiTop + 168 + i * 16;
            if (manasSkill == null) {
               name = name.setStyle(name.getStyle());
               graphics.drawString(this.font, name.withStyle(ChatFormatting.ITALIC), xName, yName, 8750469);
            } else {
               ManasSkillInstance instance = this.getSkillInstance(manasSkill);
               if (instance != null && instance.getMastery() < 0.0) {
                  name = name.withColor(8750469);
               }

               RenderHelper.drawScrollingText(graphics, this.font, name, xName, yName, 97, color.getValue());
            }
         }
      }

      graphics.blit(BACKGROUND, guiLeft + 96, guiTop + 147, this.slotsDropdown ? 114 : 127, hoveringDropdown ? 175 : 164, 13, 11);
   }

   protected MutableComponent getSlotTooltip(int hoveredSlot, ManasSkill skill, ManasSkillInstance instance) {
      if (instance != null && skill instanceof TensuraSkill tensuraSkill) {
         Component component = tensuraSkill.getColoredName();
         return component == null
            ? null
            : component.copy()
               .append("\n")
               .append(tensuraSkill.getModeName(instance, this.abilityData.getAbilitySlot(this.selectedPreset, hoveredSlot - 1).getMode()));
      } else {
         return null;
      }
   }

   protected boolean handleClickedSlot(int slot, int button) {
      if (button == 0) {
         if (this.selectedAbility == null) {
            return false;
         }

         ManasSkill manasSkill = this.selectedAbility.getSkill();
         if (manasSkill instanceof TensuraSkill tensuraSkill) {
            if (!tensuraSkill.canBeSlotted(this.selectedAbility, this.entity, 0)) {
               this.entity.playSound(SoundEvents.ITEM_BREAK);
               return false;
            }

            if (slot == 1 && this.slot1 != manasSkill) {
               this.slot1 = manasSkill;
               this.slot1Instance = this.selectedAbility;
               NetworkManager.sendToServer(
                  RequestSubordinateAbilityModeChangePacket.changeAbilityPacket(
                     this.entity.getUUID(), 0, this.selectedPreset, this.selectedAbility.getSkillId()
                  )
               );
               this.abilityData = TensuraStorages.getAbilityFrom(this.entity);
            } else if (slot == 2 && this.slot2 != manasSkill) {
               this.slot2 = manasSkill;
               this.slot2Instance = this.selectedAbility;
               NetworkManager.sendToServer(
                  RequestSubordinateAbilityModeChangePacket.changeAbilityPacket(
                     this.entity.getUUID(), 1, this.selectedPreset, this.selectedAbility.getSkillId()
                  )
               );
               this.abilityData = TensuraStorages.getAbilityFrom(this.entity);
            } else if (slot == 3 && this.slot3 != manasSkill) {
               this.slot3 = manasSkill;
               this.slot3Instance = this.selectedAbility;
               NetworkManager.sendToServer(
                  RequestSubordinateAbilityModeChangePacket.changeAbilityPacket(
                     this.entity.getUUID(), 2, this.selectedPreset, this.selectedAbility.getSkillId()
                  )
               );
               this.abilityData = TensuraStorages.getAbilityFrom(this.entity);
            }

            this.entity.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP);
            return true;
         } else {
            return false;
         }
      } else if (button == 1) {
         if (slot == 1 && this.slot1 != null) {
            this.selectedAbility = this.getSkillInstance(this.slot1);
         } else if (slot == 2 && this.slot2 != null) {
            this.selectedAbility = this.getSkillInstance(this.slot2);
         } else if (slot == 3 && this.slot3 != null) {
            this.selectedAbility = this.getSkillInstance(this.slot3);
         }

         this.entity.playSound(SoundEvents.BOOK_PAGE_TURN);
         return true;
      } else {
         if (button != 2) {
            return false;
         }

         boolean changed = false;
         if (slot == 1 && this.slot1 != null) {
            this.slot1 = null;
            changed = true;
         } else if (slot == 2 && this.slot2 != null) {
            this.slot2 = null;
            changed = true;
         } else if (slot == 3 && this.slot3 != null) {
            this.slot3 = null;
            changed = true;
         }

         if (!changed) {
            return false;
         }

         this.entity.playSound(SoundEvents.ITEM_FRAME_REMOVE_ITEM);
         NetworkManager.sendToServer(
            RequestSubordinateAbilityModeChangePacket.changeAbilityPacket(this.entity.getUUID(), slot - 1, this.selectedPreset, (ResourceLocation)null)
         );
         this.abilityData = TensuraStorages.getAbilityFrom(this.entity);
         return true;
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
         switch (this.abilityType) {
            case SKILL:
               Predicate<ManasSkillInstance> predicate = instance -> ((Skill)instance.getSkill()).getType() == this.skillFilter;
               this.filtered.addAll(this.abilities.stream().filter(predicate).toList());
               break;
            case MAGIC:
               Predicate<ManasSkillInstance> predicate2 = instance -> ((Magic)instance.getSkill()).getType() == this.magicFilter;
               this.filtered.addAll(this.abilities.stream().filter(predicate2).toList());
               break;
            case BATTLEWILL:
               this.filtered.addAll(this.abilities);
         }
      }

      this.filtered.sort(Comparator.comparing(skill -> skill.getDisplayName().getString()));
      this.scrolledScrollBar(0.0);
   }

   protected void updateAllAbilities() {
      this.abilities.clear();
      this.abilities.addAll(new ArrayList<>(SkillAPI.getSkillsFrom(this.entity).getLearnedSkills()));
      this.abilities.removeIf(ability -> {
         if (ability.getDisplayName() == null) {
            return true;
         } else if (this.abilityType == AbilityType.SKILL) {
            return !(ability.getSkill() instanceof Skill);
         } else {
            return this.abilityType == AbilityType.MAGIC ? !(ability.getSkill() instanceof Magic) : !(ability.getSkill() instanceof Battlewill);
         }
      });
   }

   protected int[] getModeCooldowns(ManasSkillInstance instance) {
      int[] array = new int[instance.getModes()];

      for (int mode = 0; mode < instance.getModes(); mode++) {
         array[mode] = instance.getCoolDown(mode);
      }

      return array;
   }

   protected int getHoveredSlot(double mX, double mY) {
      if (!this.slotsDropdown) {
         return 0;
      } else {
         int x1 = guiLeft + 4;
         int x2 = guiLeft + 108;
         if (RenderHelper.mouseOver(mX, mY, x1, x2, guiTop + 164, guiTop + 179)) {
            return 1;
         } else if (RenderHelper.mouseOver(mX, mY, x1, x2, guiTop + 180, guiTop + 195)) {
            return 2;
         } else {
            return RenderHelper.mouseOver(mX, mY, x1, x2, guiTop + 196, guiTop + 211) ? 3 : 0;
         }
      }
   }

   protected void loadPreset(boolean update) {
      if (update) {
         this.abilityData = TensuraStorages.getAbilityFrom(this.entity);
      }

      this.slot1 = this.abilityData.getAbilitySlot(this.selectedPreset, 0).getSkill();
      this.slot2 = this.abilityData.getAbilitySlot(this.selectedPreset, 1).getSkill();
      this.slot3 = this.abilityData.getAbilitySlot(this.selectedPreset, 2).getSkill();
      if (this.slot1 != null) {
         this.slot1Instance = this.abilities
            .stream()
            .filter(ability -> ability.getSkill().equals(this.slot1))
            .findFirst()
            .orElse(this.slot1.createDefaultInstance());
      }

      if (this.slot2 != null) {
         this.slot2Instance = this.abilities
            .stream()
            .filter(ability -> ability.getSkill().equals(this.slot2))
            .findFirst()
            .orElse(this.slot2.createDefaultInstance());
      }

      if (this.slot3 != null) {
         this.slot3Instance = this.abilities
            .stream()
            .filter(ability -> ability.getSkill().equals(this.slot3))
            .findFirst()
            .orElse(this.slot3.createDefaultInstance());
      }
   }

   protected MutableComponent skillName(ManasSkill skill) {
      return skill != null && skill.getName() != null ? ((TensuraSkill)skill).getColoredName() : Component.translatable("tensura.skill.empty");
   }

   protected ManasSkillInstance getSkillInstance(ManasSkill skill) {
      Optional<ManasSkillInstance> instance = SkillAPI.getSkillsFrom(this.entity).getSkill(skill);
      return instance.orElse(null);
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

   public int getScrollBarX() {
      return guiLeft + 98;
   }

   public int getScrollBarY() {
      return guiTop + 46;
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
}
