package com.benji.netherman.client.gui;

import com.benji.netherman.NetherExp;
import com.benji.netherman.client.events.ClientZoneAmbientEvents;
import com.benji.netherman.common.entity.AzazelHumanEntity;
import com.benji.netherman.common.network.AzazelCutscenePayload;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.PacketDistributor;

public class AzazelHumanCutsceneScreen extends Screen {
   private static final ResourceLocation CINEMATIC = NetherExp.location("textures/gui/cinematic_large.png");
   private static final ResourceLocation ICON_SURRENDER = NetherExp.location("textures/gui/surrender_icon.png");
   private static final ResourceLocation ICON_SURRENDER_HOVER = NetherExp.location("textures/gui/surrender_icon_choose.png");
   private static final ResourceLocation ICON_ATTACK = NetherExp.location("textures/gui/attack_icon.png");
   private static final ResourceLocation ICON_ATTACK_HOVER = NetherExp.location("textures/gui/attack_icon_choose.png");
   private final AzazelHumanEntity boss;

   public AzazelHumanCutsceneScreen(AzazelHumanEntity boss) {
      super(Component.literal("Azazel Cutscene"));
      this.boss = boss;
   }

   public boolean isPauseScreen() {
      return false;
   }

   public boolean shouldCloseOnEsc() {
      return false;
   }

   public void tick() {
      super.tick();
      if (this.minecraft != null && this.minecraft.player != null) {
         int state = (Integer)this.boss.getEntityData().get(AzazelHumanEntity.BOSS_STATE);
         if (this.boss.isAlive() && (state <= 3 || state == 100 || state == 101)) {
            if (state == 2 || state == 3) {
               this.minecraft.player.lookAt(Anchor.EYES, this.boss.position().add(0.0, 12.0, 0.0));
            } else if ((state == 100 || state == 101) && !this.minecraft.mouseHandler.isMouseGrabbed()) {
               this.minecraft.mouseHandler.grabMouse();
            }
         } else {
            this.minecraft.setScreen(null);
         }
      }
   }

   public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      int width = graphics.guiWidth();
      int height = graphics.guiHeight();
      graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
      graphics.blit(CINEMATIC, 0, 0, 0.0F, 0.0F, width, height, width, height);
      int state = (Integer)this.boss.getEntityData().get(AzazelHumanEntity.BOSS_STATE);
      int tick = (Integer)this.boss.getEntityData().get(AzazelHumanEntity.DIALOGUE_TICK);
      if (state == 2) {
         int currentLine = tick / 90;
         if (currentLine > 9) {
            currentLine = 9;
         }

         int charsVisible = tick % 90 / 2;
         String fullText = I18n.get("boss.netherman.azazel_human.line" + (currentLine + 1), new Object[0]);
         String visibleText = fullText.substring(0, Math.min(charsVisible, fullText.length()));
         int textWidth = this.font.width(visibleText);
         graphics.drawString(this.font, visibleText, (width - textWidth) / 2, height - 40, -1, true);
      }

      if ((state == 100 || state == 101) && tick > 45) {
         int charsVisible = (tick - 45) / 2;
         String fullText = I18n.get("boss.netherman.azazel_human.death_line", new Object[0]);
         String visibleText = fullText.substring(0, Math.min(charsVisible, fullText.length()));
         int textWidth = this.font.width(visibleText);
         graphics.drawString(this.font, visibleText, (width - textWidth) / 2, height - 40, 16777215, true);
      }

      if (state == 3) {
         int btnSize = 22;
         int centerX = width / 2;
         int baseY = height - 60;
         int surrenderX = centerX - 40;
         int attackX = centerX + 40 - btnSize;
         boolean hoverSurrender = mouseX >= surrenderX && mouseX <= surrenderX + btnSize && mouseY >= baseY && mouseY <= baseY + btnSize;
         boolean hoverAttack = mouseX >= attackX && mouseX <= attackX + btnSize && mouseY >= baseY && mouseY <= baseY + btnSize;
         graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
         graphics.blit(hoverSurrender ? ICON_SURRENDER_HOVER : ICON_SURRENDER, surrenderX, baseY, 0.0F, 0.0F, btnSize, btnSize, btnSize, btnSize);
         graphics.blit(hoverAttack ? ICON_ATTACK_HOVER : ICON_ATTACK, attackX, baseY, 0.0F, 0.0F, btnSize, btnSize, btnSize, btnSize);
      }
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if ((Integer)this.boss.getEntityData().get(AzazelHumanEntity.BOSS_STATE) == 3 && button == 0) {
         int btnSize = 22;
         int centerX = this.width / 2;
         int baseY = this.height - 60;
         int surrenderX = centerX - 40;
         int attackX = centerX + 40 - btnSize;
         if (mouseX >= surrenderX && mouseX <= surrenderX + btnSize && mouseY >= baseY && mouseY <= baseY + btnSize) {
            ClientZoneAmbientEvents.flagClick();
            PacketDistributor.sendToServer(new AzazelCutscenePayload(0, this.boss.getId()), new CustomPacketPayload[0]);
            this.minecraft.setScreen(null);
            return true;
         }

         if (mouseX >= attackX && mouseX <= attackX + btnSize && mouseY >= baseY && mouseY <= baseY + btnSize) {
            ClientZoneAmbientEvents.flagClick();
            PacketDistributor.sendToServer(new AzazelCutscenePayload(1, this.boss.getId()), new CustomPacketPayload[0]);
            this.minecraft.setScreen(null);
            return true;
         }
      }

      return super.mouseClicked(mouseX, mouseY, button);
   }
}
