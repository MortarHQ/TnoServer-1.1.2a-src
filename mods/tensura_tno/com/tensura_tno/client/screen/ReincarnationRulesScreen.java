package com.tensura_tno.client.screen;

import com.tensura_tno.network.ReincarnationRulesPackets;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.tensura.client.screen.ReincarnationScreen;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Button.Builder;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;

public class ReincarnationRulesScreen extends Screen {
   public static final ResourceLocation RULES_TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura_tno", "textures/gui/reincarnation/rules_panel.png");
   private static final ResourceLocation REINCARNATION_TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/reincarnation/gui.png");
   private static final int PANEL_W = 176;
   private static final int PANEL_H = 166;
   private static final int BACK_X = 7;
   private static final int BACK_Y = 5;
   private static final int BACK_W = 18;
   private static final int BACK_H = 10;
   private static final int CONTENT_RIGHT_X = 168;
   private static final int BOOLEAN_BUTTON_W = 40;
   private static final int BOOLEAN_BUTTON_H = 12;
   private static final int BOOLEAN_BUTTON_X = 120;
   private static final int EDIT_BOX_W = 40;
   private static final int EDIT_BOX_H = 16;
   private static final int EDIT_BOX_X = 120;
   private static final int RULE_TEXT_X = 16;
   private static final int RULE_TEXT_W = 100;
   private static final int BOOLEAN_BUTTON_U = 38;
   private static final int BOOLEAN_BUTTON_V = 170;
   private static final int BOOLEAN_BUTTON_TEXTURE_W = 63;
   private static final int BOOLEAN_BUTTON_EDGE_W = 4;
   private static final int RULE_X = 16;
   private static final int RULE_W = 152;
   private static final int RULE_START_Y = 28;
   private static final int RULE_ROW_H = 27;
   private static final int UNIQUE_SE_COST_Y = 136;
   private static final float TEXT_SCALE = 0.82F;
   private static final float DESCRIPTION_SCALE = 0.7F;
   private static final int DESCRIPTION_LINE_H = 8;
   private static boolean suppressNextReincarnationRemoved;
   private final Screen parent;
   private int leftPos;
   private int topPos;
   private Button hardcoreRaceButton;
   private Button keepInventoryButton;
   private Button subordinateSkillAiButton;
   private Button uniqueSECostButton;
   private EditBox epDeathPenaltyBox;
   private String lastEpBoxValue = "";

   public ReincarnationRulesScreen(Screen parent) {
      super(Component.empty());
      this.parent = parent;
   }

   public static void prepareOpenFromReincarnationScreen() {
      suppressNextReincarnationRemoved = true;
   }

   public static boolean consumeSuppressReincarnationRemoved(Screen screen) {
      if (suppressNextReincarnationRemoved && screen instanceof ReincarnationScreen) {
         suppressNextReincarnationRemoved = false;
         return true;
      } else {
         return false;
      }
   }

   protected void init() {
      this.leftPos = (this.width - 176) / 2;
      this.topPos = (this.height - 166) / 2;
      ReincarnationRulesPackets.requestSync();
      this.initWidgets();
   }

   public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      this.renderTransparentBackground(graphics);
      graphics.fill(this.leftPos, this.topPos, this.leftPos + 176, this.topPos + 166, -15722458);
      graphics.blit(RULES_TEXTURE, this.leftPos, this.topPos, 0, 0, 176, 166);
      int backU = this.mouseOverBack(mouseX, mouseY) ? 25 : 2;
      graphics.blit(REINCARNATION_TEXTURE, this.leftPos + 7, this.topPos + 5, backU, 181, 18, 10);
      this.updateWidgets();
      this.renderRules(graphics, mouseX, mouseY);
   }

   private void renderRules(GuiGraphics graphics, int mouseX, int mouseY) {
      int y = this.topPos + 28;
      this.renderBooleanRule(
         graphics, mouseX, mouseY, this.leftPos + 16, y, "gamerule.hardcoreRace", "gamerule.hardcoreRace.description", this.hardcoreRaceButton
      );
      y += 27;
      this.renderBooleanRule(
         graphics, mouseX, mouseY, this.leftPos + 16, y, "gamerule.keepInventory", "gamerule.keepInventory.description", this.keepInventoryButton
      );
      y += 27;
      this.renderBooleanRule(
         graphics,
         mouseX,
         mouseY,
         this.leftPos + 16,
         y,
         "gamerule.subordinateSkillAi",
         "gamerule.subordinateSkillAi.description",
         this.subordinateSkillAiButton
      );
      y += 27;
      this.renderIntegerRule(graphics, mouseX, mouseY, this.leftPos + 16, y, "gamerule.epDeathPenalty", "gamerule.epDeathPenalty.description");
      if (ReincarnationRulesPackets.clientUniqueSECostAvailable) {
         y += 27;
         this.renderBooleanRule(
            graphics, mouseX, mouseY, this.leftPos + 16, y, "gamerule.uniqueSECost", "gamerule.uniqueSECost.description", this.uniqueSECostButton
         );
         if (this.mouseOverUniqueSECost(mouseX, mouseY)) {
            this.setTooltipForNextRenderPass(Component.translatable("gamerule.uniqueSECost.tooltip"));
         }
      }
   }

   private void renderBooleanRule(GuiGraphics graphics, int mouseX, int mouseY, int x, int y, String nameKey, String descriptionKey, Button button) {
      this.drawScaledString(graphics, Component.translatable(nameKey).withStyle(ChatFormatting.WHITE), x, y, Color.WHITE.getRGB());
      this.drawScaledWrapped(graphics, Component.translatable(descriptionKey), x, y + 10, 100, -2302756);
      button.render(graphics, mouseX, mouseY, 0.0F);
   }

   private void renderIntegerRule(GuiGraphics graphics, int mouseX, int mouseY, int x, int y, String nameKey, String descriptionKey) {
      this.drawScaledString(graphics, Component.translatable(nameKey).withStyle(ChatFormatting.WHITE), x, y, Color.WHITE.getRGB());
      this.drawScaledWrapped(graphics, Component.translatable(descriptionKey), x, y + 10, 100, -2302756);
      this.epDeathPenaltyBox.render(graphics, mouseX, mouseY, 0.0F);
   }

   private void drawScaledString(GuiGraphics graphics, Component component, int x, int y, int color) {
      float scale = Math.min(0.82F, 100.0F / Math.max(1, this.font.width(component)));
      graphics.pose().pushPose();
      graphics.pose().scale(scale, scale, 1.0F);
      graphics.drawString(this.font, component, Math.round(x / scale), Math.round(y / scale), color, false);
      graphics.pose().popPose();
   }

   private void drawScaledWrapped(GuiGraphics graphics, Component component, int x, int y, int width, int color) {
      int splitWidth = Math.round(width / 0.7F);
      List<FormattedCharSequence> lines = new ArrayList<>(this.font.split(component, splitWidth));
      graphics.pose().pushPose();
      graphics.pose().scale(0.7F, 0.7F, 1.0F);

      for (int i = 0; i < Math.min(2, lines.size()); i++) {
         graphics.drawString(this.font, lines.get(i), Math.round(x / 0.7F), Math.round((y + i * 8) / 0.7F), color, false);
      }

      graphics.pose().popPose();
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (button == 0 && this.mouseOverBack((int)mouseX, (int)mouseY)) {
         this.setFocused(null);
         this.playClick();
         Minecraft.getInstance().setScreen(this.parent);
         return true;
      } else if (this.epDeathPenaltyBox.mouseClicked(mouseX, mouseY, button)) {
         this.setFocused(this.epDeathPenaltyBox);
         return true;
      } else if (this.hardcoreRaceButton.mouseClicked(mouseX, mouseY, button)) {
         this.setFocused(null);
         return true;
      } else if (this.keepInventoryButton.mouseClicked(mouseX, mouseY, button)) {
         this.setFocused(null);
         return true;
      } else if (this.subordinateSkillAiButton.mouseClicked(mouseX, mouseY, button)) {
         this.setFocused(null);
         return true;
      } else if (ReincarnationRulesPackets.clientUniqueSECostAvailable && this.uniqueSECostButton.mouseClicked(mouseX, mouseY, button)) {
         this.setFocused(null);
         return true;
      } else {
         this.setFocused(null);
         return true;
      }
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      return this.epDeathPenaltyBox.keyPressed(keyCode, scanCode, modifiers) ? true : true;
   }

   public boolean charTyped(char codePoint, int modifiers) {
      return this.epDeathPenaltyBox.charTyped(codePoint, modifiers) ? true : true;
   }

   public boolean shouldCloseOnEsc() {
      return false;
   }

   private void initWidgets() {
      Font font = Minecraft.getInstance().font;
      this.hardcoreRaceButton = this.rulesButton(Component.literal(Boolean.toString(ReincarnationRulesPackets.clientHardcoreRace)), button -> {
         ReincarnationRulesPackets.clientHardcoreRace = !ReincarnationRulesPackets.clientHardcoreRace;
         this.sendBooleanRule("hardcoreRace", ReincarnationRulesPackets.clientHardcoreRace);
         button.setMessage(Component.literal(Boolean.toString(ReincarnationRulesPackets.clientHardcoreRace)));
      }, this.leftPos + 120, this.topPos + 30);
      this.addWidget(this.hardcoreRaceButton);
      this.keepInventoryButton = this.rulesButton(Component.literal(Boolean.toString(ReincarnationRulesPackets.clientKeepInventory)), button -> {
         ReincarnationRulesPackets.clientKeepInventory = !ReincarnationRulesPackets.clientKeepInventory;
         this.sendBooleanRule("keepInventory", ReincarnationRulesPackets.clientKeepInventory);
         button.setMessage(Component.literal(Boolean.toString(ReincarnationRulesPackets.clientKeepInventory)));
      }, this.leftPos + 120, this.topPos + 28 + 27 + 2);
      this.addWidget(this.keepInventoryButton);
      this.subordinateSkillAiButton = this.rulesButton(Component.literal(Boolean.toString(ReincarnationRulesPackets.clientSubordinateSkillAi)), button -> {
         ReincarnationRulesPackets.clientSubordinateSkillAi = !ReincarnationRulesPackets.clientSubordinateSkillAi;
         this.sendBooleanRule("subordinateSkillAi", ReincarnationRulesPackets.clientSubordinateSkillAi);
         button.setMessage(Component.literal(Boolean.toString(ReincarnationRulesPackets.clientSubordinateSkillAi)));
      }, this.leftPos + 120, this.topPos + 28 + 54 + 2);
      this.addWidget(this.subordinateSkillAiButton);
      this.uniqueSECostButton = this.rulesButton(Component.literal(Boolean.toString(ReincarnationRulesPackets.clientUniqueSECost)), button -> {
         ReincarnationRulesPackets.clientUniqueSECost = !ReincarnationRulesPackets.clientUniqueSECost;
         this.sendBooleanRule("uniqueSECost", ReincarnationRulesPackets.clientUniqueSECost);
         button.setMessage(Component.literal(Boolean.toString(ReincarnationRulesPackets.clientUniqueSECost)));
      }, this.leftPos + 120, this.topPos + 136 + 2);
      this.addWidget(this.uniqueSECostButton);
      this.epDeathPenaltyBox = new EditBox(font, this.leftPos + 120, this.topPos + 28 + 81 + 2, 40, 16, Component.translatable("gamerule.epDeathPenalty"));
      this.epDeathPenaltyBox.setFilter(value -> value.isEmpty() || value.matches("\\d{0,6}"));
      this.epDeathPenaltyBox.setMaxLength(6);
      this.epDeathPenaltyBox.setValue(Integer.toString(ReincarnationRulesPackets.clientEpDeathPenalty));
      this.lastEpBoxValue = this.epDeathPenaltyBox.getValue();
      this.epDeathPenaltyBox.setResponder(value -> {
         if (!value.isEmpty() && !value.equals(this.lastEpBoxValue)) {
            try {
               int next = Math.max(0, Integer.parseInt(value));
               this.lastEpBoxValue = value;
               ReincarnationRulesPackets.clientEpDeathPenalty = next;
               this.sendIntegerRule("epDeathPenalty", next);
            } catch (NumberFormatException var3) {
            }
         }
      });
      this.addWidget(this.epDeathPenaltyBox);
   }

   private void updateWidgets() {
      this.hardcoreRaceButton.setMessage(Component.literal(Boolean.toString(ReincarnationRulesPackets.clientHardcoreRace)));
      this.keepInventoryButton.setMessage(Component.literal(Boolean.toString(ReincarnationRulesPackets.clientKeepInventory)));
      this.subordinateSkillAiButton.setMessage(Component.literal(Boolean.toString(ReincarnationRulesPackets.clientSubordinateSkillAi)));
      this.uniqueSECostButton.visible = ReincarnationRulesPackets.clientUniqueSECostAvailable;
      this.uniqueSECostButton.active = ReincarnationRulesPackets.clientUniqueSECostAvailable;
      this.uniqueSECostButton.setMessage(Component.literal(Boolean.toString(ReincarnationRulesPackets.clientUniqueSECost)));
      if (!this.epDeathPenaltyBox.isFocused()) {
         String synced = Integer.toString(ReincarnationRulesPackets.clientEpDeathPenalty);
         if (!synced.equals(this.epDeathPenaltyBox.getValue())) {
            this.lastEpBoxValue = synced;
            this.epDeathPenaltyBox.setValue(synced);
         }
      }
   }

   private boolean mouseOverBack(int mouseX, int mouseY) {
      return mouseX >= this.leftPos + 7 && mouseX < this.leftPos + 7 + 18 && mouseY >= this.topPos + 5 && mouseY < this.topPos + 5 + 10;
   }

   private boolean mouseOverUniqueSECost(int mouseX, int mouseY) {
      return mouseX >= this.leftPos + 16 && mouseX < this.leftPos + 16 + 152 && mouseY >= this.topPos + 136 && mouseY < this.topPos + 136 + 27;
   }

   private void sendBooleanRule(String ruleId, boolean value) {
      NetworkManager.sendToServer(new ReincarnationRulesPackets.SetRulePayload(ruleId, Boolean.toString(value)));
   }

   private void sendIntegerRule(String ruleId, int value) {
      NetworkManager.sendToServer(new ReincarnationRulesPackets.SetRulePayload(ruleId, Integer.toString(value)));
   }

   private void playClick() {
      Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
   }

   private Button rulesButton(Component message, OnPress onPress, int x, int y) {
      return Button.builder(message, onPress).bounds(x, y, 40, 12).build(ReincarnationRulesScreen.TexturedRuleButton::new);
   }

   private static class TexturedRuleButton extends Button {
      TexturedRuleButton(Builder builder) {
         super(builder);
      }

      protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
         this.blitButtonTexture(graphics, this.getX(), this.getY(), this.width, this.height);
         if (this.isHovered() && Minecraft.getInstance().mouseHandler.isLeftPressed()) {
            graphics.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, 1711276032);
         }

         Font font = Minecraft.getInstance().font;
         int textColor = this.active ? Color.WHITE.getRGB() : -6250336;
         int textX = this.getX() + (this.width - font.width(this.getMessage())) / 2;
         int textY = this.getY() + 2;
         graphics.drawString(font, this.getMessage(), textX, textY, textColor, false);
      }

      private void blitButtonTexture(GuiGraphics graphics, int x, int y, int width, int height) {
         int centerWidth = Math.max(0, width - 8);
         graphics.blit(ReincarnationRulesScreen.RULES_TEXTURE, x, y, 38, 170, 4, height);
         graphics.blit(ReincarnationRulesScreen.RULES_TEXTURE, x + 4, y, 42, 170, centerWidth, height);
         graphics.blit(ReincarnationRulesScreen.RULES_TEXTURE, x + width - 4, y, 97, 170, 4, height);
      }
   }
}
