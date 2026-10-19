package com.trbeyond.tensura_better_subs.client.screen;

import com.trbeyond.tensura_better_subs.network.c2s.RequestSubordinateAbilityModeChangePacket;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.magic.Magic.MagicType;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.client.TensuraKeybinds;
import io.github.manasmods.tensura.client.screen.AbilityCategoriesScreen.AbilityType;
import io.github.manasmods.tensura.client.screen.templates.SimpleScreen;
import io.github.manasmods.tensura.client.screen.widgets.SimpleButton;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ability.IAbility;
import io.github.manasmods.tensura.util.client.RenderHelper;
import java.awt.Color;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;

public class AbilityCategoriesScreenEntity extends SimpleScreen {
   public static final Component TITLE = ScreenHelperSubordinates.getMenuName(1);
   protected static final ResourceLocation BACKGROUND_SKILL = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/skill/skill_gui.png");
   protected static final ResourceLocation BACKGROUND_MAGIC = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/magic/magic_gui.png");
   protected static final ResourceLocation PRESET_BUTTON = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/skill/preset_button.png");
   protected static final ResourceLocation RESISTANCE = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/skill/resistance.png");
   protected static final ResourceLocation COMMON = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/skill/common.png");
   protected static final ResourceLocation INTRINSIC = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/skill/intrinsic.png");
   protected static final ResourceLocation EXTRA = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/skill/extra.png");
   protected static final ResourceLocation UNIQUE = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/skill/unique.png");
   protected static final ResourceLocation ULTIMATE = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/skill/ultimate.png");
   protected static final ResourceLocation ASPECTUAL = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/magic/aspectual_magic_button.png");
   protected static final ResourceLocation SPIRITUAL = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/magic/spiritual_magic_button.png");
   protected static final ResourceLocation SUMMONING = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/magic/summoning_magic_button.png");
   protected static final ResourceLocation MISCELLANEOUS = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/magic/misc_magic_button.png");
   protected final AbilityType abilityType;
   protected final SimpleButton returnButton;
   protected Component title;
   protected IAbility abilityData;
   protected ManasSkill slot1;
   protected ManasSkill slot2;
   protected ManasSkill slot3;
   protected ManasSkillInstance slot1Instance;
   protected ManasSkillInstance slot2Instance;
   protected ManasSkillInstance slot3Instance;
   protected int activePreset;
   protected int selectedPreset;
   private int interactionCd;
   protected LivingEntity entity;

   public AbilityCategoriesScreenEntity(AbilityType abilityType, LivingEntity entity) {
      super(TITLE, 254, 163);
      this.entity = entity;
      this.abilityType = abilityType;
      this.title = (Component)(abilityType == AbilityType.SKILL ? TITLE : Component.translatable("tensura.magic_menu"));
      this.abilityData = TensuraStorages.getAbilityFrom(this.entity);
      this.activePreset = this.abilityData.getActivePreset();
      this.selectedPreset = this.activePreset;
      this.shouldRenderWidgets = false;
      this.returnButton = new SimpleButton(
         0,
         0,
         18,
         10,
         null,
         null,
         Component.translatable("tooltip.tensura.return"),
         self -> ScreenHelperSubordinates.clicked(SoundEvents.UI_BUTTON_CLICK, () -> ScreenHelperSubordinates.openScreen(4, this.entity))
      );
      this.loadPreset();
   }

   protected void init() {
      super.init();
      guiLeft--;
      int tab = this.abilityType == AbilityType.SKILL ? 1 : 2;
      List<SimpleButton> tabs = ScreenHelperSubordinates.getMenuTabs(tab, guiLeft, guiTop, this.entity);
      tabs.forEach(buttonx -> {
         SimpleButton var10000 = (SimpleButton)this.addRenderableWidget(buttonx);
      });
      this.returnButton.setX(guiLeft + 5);
      this.returnButton.setY(guiTop + 28);
      this.addRenderableWidget(this.returnButton);

      for (int i = 0; i < 9; i++) {
         int finalI = i;
         SimpleButton button = new SimpleButton(
            guiLeft + 128 + i * 10,
            guiTop + 40,
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
                  this.loadPreset();
               }
            }
         );
         button.setUvOffsetCheck(() -> this.selectedPreset == finalI || button.isHovered());
         this.addRenderableWidget(button);
      }

      if (this.abilityType == AbilityType.SKILL) {
         this.addSkillCategories();
      } else {
         this.addMagicCategories();
      }
   }

   // $VF: Unable to simplify switch-on-enum, as the enum class was not able to be found.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public void renderBackground(GuiGraphics graphics, int mX, int mY, float partialTick) {
      if (!ScreenHelperSubordinates.isScreenValid(this.entity, this.player)) {
         this.minecraft.setScreen(null);
      }

      if (this.interactionCd > 0) {
         this.interactionCd--;
      }

      graphics.drawString(this.font, this.title, guiLeft + 105, guiTop + 10, Color.WHITE.getRGB());
      if (this.abilityType == AbilityType.SKILL) {
         graphics.blit(BACKGROUND_SKILL, guiLeft, guiTop, 0, 0, this.imageWidth, this.imageHeight);
      } else {
         graphics.blit(BACKGROUND_MAGIC, guiLeft, guiTop, 0, 0, this.imageWidth, this.imageHeight);
      }

      super.renderWidgets(graphics, mX, mY, partialTick);
      byte var10000 = 0;
      switch (this.abilityType.ordinal()) {
         case 0:
            var10000 = 1;
            break;
         case 1:
            var10000 = 2;
            break;
         case 2:
            var10000 = 3;
      }

      int id = var10000;
      ScreenHelperSubordinates.renderTabIcon(graphics, this, id, guiLeft, guiTop, mX, mY);

      for (int i = 0; i < 9; i++) {
         int color = i == this.activePreset ? Color.GREEN.getRGB() : Color.LIGHT_GRAY.getRGB();
         Component text = Component.literal(String.valueOf(i + 1));
         RenderHelper.drawCenteredText(graphics, this.font, text, guiLeft + 128 + i * 10, guiTop + 42, 10, color, false);
         if (i < 3) {
            int xName = guiLeft + 148;
            int yName = guiTop + 88 + i * 22;
            ManasSkill manasSkill = i == 0 ? this.slot1 : (i == 1 ? this.slot2 : this.slot3);
            MutableComponent name = this.skillName(manasSkill);
            TextColor nameColor = name.getStyle().getColor();
            if (nameColor == null) {
               nameColor = TextColor.fromRgb(16711680);
            }

            if (manasSkill == null) {
               name = name.setStyle(name.getStyle());
               graphics.drawString(this.font, name.withStyle(ChatFormatting.ITALIC), xName, yName, 8750469);
            } else {
               ResourceLocation icon = manasSkill.getSkillIcon();
               ManasSkillInstance instance = this.getSkillInstance(manasSkill);
               if (instance != null && instance.getMastery() < 0.0) {
                  name = name.withColor(8750469);
               }

               boolean hoveringName = RenderHelper.mouseOver(mX, mY, guiLeft + 146, guiLeft + 220, guiTop + 84 + i * 22, guiTop + 99 + i * 22);
               boolean hoveringIcon = RenderHelper.mouseOver(mX, mY, guiLeft + 126, guiLeft + 146, guiTop + 81 + i * 22, guiTop + 101 + i * 22);
               RenderHelper.drawScrollingText(graphics, this.font, name, xName, yName, 69, nameColor.getValue());
               if (icon != null) {
                  graphics.blit(icon, guiLeft + 128, guiTop + 83 + i * 22, 0.0F, 0.0F, 16, 16, 16, 16);
               }

               if (hoveringName || hoveringIcon) {
                  MutableComponent var22;
                  if (manasSkill instanceof TensuraSkill skill) {
                     var22 = skill.getColoredName();
                  } else {
                     var22 = manasSkill.getChatDisplayName(false);
                  }

                  MutableComponent hoverMessage = var22;
                  ResourceLocation identifier = SkillAPI.getSkillRegistry().getId(manasSkill);
                  if (identifier != null) {
                     hoverMessage.append("\n").append(Component.literal(identifier.toString()).withColor(5592405));
                  }

                  this.setTooltipForNextRenderPass(hoverMessage);
               }
            }
         }
      }

      this.abilityData = TensuraStorages.getAbilityFrom(this.entity);
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

   protected int getHoveredSlot(double mX, double mY) {
      int x1 = guiLeft + 146;
      int x2 = guiLeft + 220;
      if (RenderHelper.mouseOver(mX, mY, x1, x2, guiTop + 84, guiTop + 99)) {
         return 1;
      } else if (RenderHelper.mouseOver(mX, mY, x1, x2, guiTop + 106, guiTop + 121)) {
         return 2;
      } else {
         return RenderHelper.mouseOver(mX, mY, x1, x2, guiTop + 128, guiTop + 143) ? 3 : 0;
      }
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

   protected void loadPreset() {
      this.slot1 = this.abilityData.getAbilitySlot(this.selectedPreset, 0).getSkill();
      this.slot2 = this.abilityData.getAbilitySlot(this.selectedPreset, 1).getSkill();
      this.slot3 = this.abilityData.getAbilitySlot(this.selectedPreset, 2).getSkill();
      Collection<ManasSkillInstance> abilities = SkillAPI.getSkillsFrom(this.entity).getLearnedSkills();
      if (this.slot1 != null) {
         this.slot1Instance = abilities.stream()
            .filter(ability -> ability.getSkill().equals(this.slot1))
            .findFirst()
            .orElse(this.slot1.createDefaultInstance());
      }

      if (this.slot2 != null) {
         this.slot2Instance = abilities.stream()
            .filter(ability -> ability.getSkill().equals(this.slot2))
            .findFirst()
            .orElse(this.slot2.createDefaultInstance());
      }

      if (this.slot3 != null) {
         this.slot3Instance = abilities.stream()
            .filter(ability -> ability.getSkill().equals(this.slot3))
            .findFirst()
            .orElse(this.slot3.createDefaultInstance());
      }
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (this.minecraft != null) {
         if (TensuraKeybinds.MAIN_GUI.matches(keyCode, scanCode)) {
            this.minecraft.setScreen((Screen)null);
            this.minecraft.mouseHandler.grabMouse();
            return true;
         }

         if (this.minecraft.options.keyInventory.matches(keyCode, scanCode)) {
            return true;
         }
      }

      return super.keyPressed(keyCode, scanCode, modifiers);
   }

   public boolean mouseClicked(double mX, double mY, int button) {
      if (button == 2) {
         for (int i = 0; i < 3; i++) {
            boolean hoveringName = RenderHelper.mouseOver(mX, mY, guiLeft + 146, guiLeft + 220, guiTop + 84 + i * 22, guiTop + 99 + i * 22);
            boolean hoveringIcon = RenderHelper.mouseOver(mX, mY, guiLeft + 126, guiLeft + 146, guiTop + 81 + i * 22, guiTop + 101 + i * 22);
            if (hoveringName || hoveringIcon) {
               boolean changed = false;
               if (i == 0 && this.slot1 != null) {
                  this.slot1 = null;
                  changed = true;
               } else if (i == 1 && this.slot2 != null) {
                  this.slot2 = null;
                  changed = true;
               } else if (i == 2 && this.slot3 != null) {
                  this.slot3 = null;
                  changed = true;
               }

               if (changed) {
                  this.entity.playSound(SoundEvents.ITEM_FRAME_REMOVE_ITEM);
                  NetworkManager.sendToServer(
                     RequestSubordinateAbilityModeChangePacket.changeAbilityPacket(this.entity.getUUID(), i, this.selectedPreset, null)
                  );
                  this.abilityData = TensuraStorages.getAbilityFrom(this.entity);
                  return true;
               }
            }
         }
      }

      return super.mouseClicked(mX, mY, button);
   }

   public boolean mouseScrolled(double mX, double mY, double deltaX, double deltaY) {
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
         NetworkManager.sendToServer(RequestSubordinateAbilityModeChangePacket.changeModePacket(this.entity.getUUID(), slotIndex, reverse));
         int nextMode = skill.nextMode(this.entity, instance, this.abilityData.getAbilitySlot(this.selectedPreset, slotIndex).getMode(), reverse);
         if (nextMode != -1) {
            this.abilityData.setAbilitySlot(this.selectedPreset, slotIndex, skill, nextMode);
         }
      }
   }

   protected void addSkillCategories() {
      for (int i = 0; i < 6; i++) {
         SkillType type;
         ResourceLocation texture;
         switch (i) {
            case 0:
               type = SkillType.RESISTANCE;
               texture = RESISTANCE;
               break;
            case 1:
               type = SkillType.INTRINSIC;
               texture = INTRINSIC;
               break;
            case 2:
               type = SkillType.COMMON;
               texture = COMMON;
               break;
            case 3:
               type = SkillType.EXTRA;
               texture = EXTRA;
               break;
            case 4:
               type = SkillType.UNIQUE;
               texture = UNIQUE;
               break;
            default:
               type = SkillType.ULTIMATE;
               texture = ULTIMATE;
         }

         int finalI = i;
         SimpleButton categoryButton = new SimpleButton(
            i % 2 == 0 ? guiLeft + 19 : guiLeft + 63,
            i < 2 ? guiTop + 45 : (i < 4 ? guiTop + 83 : guiTop + 121),
            32,
            32,
            texture,
            null,
            type.getName(),
            self -> ScreenHelperSubordinates.clicked(
               () -> ScreenHelperSubordinates.openAbilityScreenSubordinate(this.abilityType, getSkillCategoryById(finalI), null, this.entity)
            )
         );
         this.addRenderableWidget(categoryButton);
      }
   }

   protected void addMagicCategories() {
      for (int i = 0; i < 4; i++) {
         MagicType type;
         ResourceLocation var10000;
         switch (i) {
            case 0:
               type = MagicType.ASPECTUAL;
               var10000 = ASPECTUAL;
               break;
            case 1:
               type = MagicType.SPIRITUAL;
               var10000 = SPIRITUAL;
               break;
            case 2:
               type = null;
               var10000 = ScreenHelperSubordinates.COMING_SOON_ICON;
               break;
            default:
               type = MagicType.SUMMONING;
               var10000 = SUMMONING;
         }

         ResourceLocation texture = var10000;
         SimpleButton categoryButton;
         if (type == null) {
            categoryButton = new SimpleButton(
               guiLeft + 19,
               guiTop + 83,
               32,
               32,
               texture,
               (Component)null,
               ScreenHelperSubordinates.getComingSoonComponent(),
               self -> ScreenHelperSubordinates.clicked()
            );
         } else {
            int finalI = i;
            categoryButton = new SimpleButton(
               i % 2 == 0 ? guiLeft + 19 : guiLeft + 63,
               i < 2 ? guiTop + 45 : guiTop + 83,
               32,
               32,
               texture,
               (Component)null,
               type.getName(),
               self -> ScreenHelperSubordinates.clicked(
                  () -> ScreenHelperSubordinates.openAbilityScreenSubordinate(this.abilityType, (SkillType)null, getMagicCategoryById(finalI), this.entity)
               )
            );
         }

         this.addRenderableWidget(categoryButton);
      }

      MagicType type = MagicType.MISC;
      ResourceLocation texture = MISCELLANEOUS;
      SimpleButton categoryButton = new SimpleButton(
         guiLeft + 41,
         guiTop + 121,
         32,
         32,
         texture,
         (Component)null,
         type.getName(),
         self -> ScreenHelperSubordinates.clicked(
            () -> ScreenHelperSubordinates.openAbilityScreenSubordinate(this.abilityType, (SkillType)null, getMagicCategoryById(4), this.entity)
         )
      );
      this.addRenderableWidget(categoryButton);
   }

   protected MutableComponent skillName(ManasSkill skill) {
      return skill != null && skill.getName() != null ? ((TensuraSkill)skill).getColoredName() : Component.translatable("tensura.skill.empty");
   }

   protected ManasSkillInstance getSkillInstance(ManasSkill skill) {
      Optional<ManasSkillInstance> instance = SkillAPI.getSkillsFrom(this.entity).getSkill(skill);
      return instance.orElse((ManasSkillInstance)null);
   }

   public static SkillType getSkillCategoryById(int id) {
      return switch (id) {
         case 0 -> SkillType.RESISTANCE;
         case 1 -> SkillType.INTRINSIC;
         case 2 -> SkillType.COMMON;
         case 3 -> SkillType.EXTRA;
         case 4 -> SkillType.UNIQUE;
         default -> SkillType.ULTIMATE;
      };
   }

   public static MagicType getMagicCategoryById(int id) {
      return switch (id) {
         case 0 -> MagicType.ASPECTUAL;
         case 1 -> MagicType.SPIRITUAL;
         case 2 -> null;
         case 3 -> MagicType.SUMMONING;
         default -> MagicType.MISC;
      };
   }

   public void onClose() {
      super.onClose();
   }
}
