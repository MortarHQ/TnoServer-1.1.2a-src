package io.github.Memoires.mysticism.client.screen;

import dev.architectury.networking.NetworkManager;
import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.network.c2s.RequestButcheringEffects;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.client.screen.templates.SimpleScreen;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.client.RenderHelper;
import io.github.manasmods.tensura.util.client.ScreenHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;

public class ButcheringScreen extends SimpleScreen {
   private final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath("mysticism", "textures/gui/butchering.png");
   protected static final UniqueMysticismSkillConfig.Butcher CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class)).Butcher;
   protected int totalSelected;
   protected boolean selectedHead;
   protected boolean selectedTorso;
   protected boolean selectedLeftArm;
   protected boolean selectedRightArm;
   protected boolean selectedLeftLeg;
   protected boolean selectedRightLeg;
   protected ManasSkill skill;
   protected final Component HEAD = Component.translatable("mysticism.skill.butcher.butchering_screen.head").withStyle(ChatFormatting.DARK_RED);
   protected final Component TORSO = Component.translatable("mysticism.skill.butcher.butchering_screen.torso").withStyle(ChatFormatting.DARK_RED);
   protected final Component ARM = Component.translatable("mysticism.skill.butcher.butchering_screen.arm").withStyle(ChatFormatting.DARK_RED);
   protected final Component LEG = Component.translatable("mysticism.skill.butcher.butchering_screen.leg").withStyle(ChatFormatting.DARK_RED);
   protected LivingEntity target;

   public ButcheringScreen(Component pTitle, int targetId) {
      super(pTitle, 150, 150);
      if (this.player.level().getEntity(targetId) instanceof LivingEntity living) {
         this.target = living;
      } else {
         this.onClose();
      }
   }

   protected void init() {
      if (this.target != null) {
         super.init();
      }
   }

   public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
      super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
      this.renderTooltip(guiGraphics, pMouseX, pMouseY);
   }

   public void renderTooltip(GuiGraphics graphics, int mX, int mY) {
      super.renderTooltip(graphics, mX, mY);
   }

   private void renderPart(
      GuiGraphics graphics, Component tooltip, int posX, int posY, int width, int height, int offsetX, int offsetY, int mX, int mY, boolean isSelected
   ) {
      boolean isHovered = RenderHelper.mouseOver(mX, mY, posX, posX + width, posY, posY + height);
      if (isHovered || isSelected) {
         if (isHovered) {
            this.setTooltipForNextRenderPass(tooltip);
         }

         graphics.blit(this.BACKGROUND, posX, posY, offsetX, offsetY, width, height, 256, 256);
      }
   }

   public void renderBackground(GuiGraphics graphics, int mX, int mY, float partialTick) {
      graphics.blit(this.BACKGROUND, guiLeft, guiTop, 0, 0, this.imageWidth, this.imageHeight);
      RenderHelper.drawCenteredText(
         graphics, this.font, Component.translatable("mysticism.skill.mode.butcher.血淋林的爱"), guiLeft + 76, guiTop + 6, 16711680, false
      );
      this.renderButtons(graphics, mX, mY);
      RenderHelper.drawCenteredText(
         graphics, this.font, Component.translatable("mysticism.skill.butcher.butchering_screen.butcher"), guiLeft + 76, guiTop + 127, 16733525, false
      );
      float sizeMultiplier = this.target.getScale();
      sizeMultiplier = sizeMultiplier > 1.0F ? 1.0F / sizeMultiplier : 1.0F;
      RenderHelper.renderEntityInInventoryFollowsMouse(
         graphics, guiLeft + 4, guiTop + 20, guiLeft + 64, guiTop + 110, 30.0F * sizeMultiplier, mX, mY, false, this.target
      );
   }

   protected void renderButtons(GuiGraphics guiGraphics, int mX, int mY) {
      int headOffsetX = this.selectedHead ? 57 : 95;
      int torsoOffsetX = this.selectedTorso ? 57 : 95;
      int leftArmOffsetX = this.selectedLeftArm ? 76 : 114;
      int rightArmOffsetX = this.selectedRightArm ? 76 : 114;
      int leftLegOffsetX = this.selectedLeftLeg ? 86 : 124;
      int rightLegOffsetX = this.selectedRightLeg ? 86 : 124;
      this.renderPart(guiGraphics, this.HEAD, guiLeft + 107, guiTop + 31, 18, 18, headOffsetX, 152, mX, mY, this.selectedHead);
      this.renderPart(guiGraphics, this.TORSO, guiLeft + 107, guiTop + 50, 18, 26, torsoOffsetX, 171, mX, mY, this.selectedTorso);
      this.renderPart(guiGraphics, this.ARM, guiLeft + 97, guiTop + 50, 9, 26, leftArmOffsetX, 152, mX, mY, this.selectedLeftArm);
      this.renderPart(guiGraphics, this.ARM, guiLeft + 126, guiTop + 50, 9, 26, rightArmOffsetX, 152, mX, mY, this.selectedRightArm);
      this.renderPart(guiGraphics, this.LEG, guiLeft + 107, guiTop + 77, 8, 23, leftLegOffsetX, 152, mX, mY, this.selectedLeftLeg);
      this.renderPart(guiGraphics, this.LEG, guiLeft + 117, guiTop + 77, 8, 23, rightLegOffsetX, 152, mX, mY, this.selectedRightLeg);
      if (RenderHelper.mouseOver(mX, mY, guiLeft + 47, guiLeft + 102, guiTop + 123, guiTop + 138)) {
         guiGraphics.blit(this.BACKGROUND, guiLeft + 47, guiTop + 123, 0.0F, 152.0F, 56, 15, 256, 256);
      }
   }

   public boolean mouseClicked(double mX, double mY, int button) {
      int clickedPart = this.getClickedPart(mX, mY);
      if (clickedPart != -1) {
         switch (clickedPart) {
            case 0:
               this.togglePart(clickedPart, this.selectedHead);
               break;
            case 1:
               this.togglePart(clickedPart, this.selectedTorso);
               break;
            case 2:
               this.togglePart(clickedPart, this.selectedLeftArm);
               break;
            case 3:
               this.togglePart(clickedPart, this.selectedRightArm);
               break;
            case 4:
               this.togglePart(clickedPart, this.selectedLeftLeg);
               break;
            case 5:
               this.togglePart(clickedPart, this.selectedRightLeg);
         }

         return true;
      } else if (RenderHelper.mouseOver(mX, mY, guiLeft + 47, guiLeft + 102, guiTop + 123, guiTop + 138)) {
         if (this.totalSelected > 0) {
            NetworkManager.sendToServer(
               new RequestButcheringEffects(
                  this.player.getId(),
                  this.selectedHead,
                  this.selectedTorso,
                  this.selectedLeftArm,
                  this.selectedRightArm,
                  this.selectedLeftLeg,
                  this.selectedRightLeg,
                  this.target.getId()
               )
            );
            this.onClose();
         } else {
            this.player.playSound((SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), 1.0F, 1.0F);
            this.player
               .displayClientMessage(Component.translatable("mysticism.skill.butcher.butchering_screen.no_parts_selected").withStyle(ChatFormatting.RED), false);
         }

         return true;
      } else {
         return super.mouseClicked(mX, mY, button);
      }
   }

   protected void togglePart(int part, boolean isSelected) {
      if (this.totalSelected >= 3 && !isSelected) {
         this.player.playSound((SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), 1.0F, 1.0F);
         this.player
            .displayClientMessage(Component.translatable("mysticism.skill.butcher.butchering_screen.selected_too_many").withStyle(ChatFormatting.RED), false);
      } else {
         if (isSelected) {
            this.totalSelected--;
         } else {
            this.totalSelected++;
         }

         ScreenHelper.clicked();
         switch (part) {
            case 0:
               this.selectedHead = !this.selectedHead;
               break;
            case 1:
               this.selectedTorso = !this.selectedTorso;
               break;
            case 2:
               this.selectedLeftArm = !this.selectedLeftArm;
               break;
            case 3:
               this.selectedRightArm = !this.selectedRightArm;
               break;
            case 4:
               this.selectedLeftLeg = !this.selectedLeftLeg;
               break;
            case 5:
               this.selectedRightLeg = !this.selectedRightLeg;
         }
      }
   }

   protected int getClickedPart(double mX, double mY) {
      if (RenderHelper.mouseOver(mX, mY, guiLeft + 107, guiLeft + 125, guiTop + 31, guiTop + 49)) {
         return 0;
      } else if (RenderHelper.mouseOver(mX, mY, guiLeft + 107, guiLeft + 125, guiTop + 50, guiTop + 76)) {
         return 1;
      } else if (RenderHelper.mouseOver(mX, mY, guiLeft + 97, guiLeft + 106, guiTop + 50, guiTop + 76)) {
         return 2;
      } else if (RenderHelper.mouseOver(mX, mY, guiLeft + 126, guiLeft + 135, guiTop + 50, guiTop + 76)) {
         return 3;
      } else if (RenderHelper.mouseOver(mX, mY, guiLeft + 107, guiLeft + 115, guiTop + 77, guiTop + 100)) {
         return 4;
      } else {
         return RenderHelper.mouseOver(mX, mY, guiLeft + 117, guiLeft + 135, guiTop + 77, guiTop + 100) ? 5 : -1;
      }
   }
}
