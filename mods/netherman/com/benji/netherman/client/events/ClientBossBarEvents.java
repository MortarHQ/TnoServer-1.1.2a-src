package com.benji.netherman.client.events;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.entity.AzazelEntity;
import com.benji.netherman.common.entity.AzazelHumanEntity;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent.BossEventProgress;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent.Post;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(modid = "netherman", value = Dist.CLIENT)
public class ClientBossBarEvents {
   private static final ResourceLocation FRAME_TEXTURE = NetherExp.location("textures/gui/azazel_frame.png");
   private static final ResourceLocation PROGRESS_TEXTURE = NetherExp.location("textures/gui/azazel_progress.png");
   private static final ResourceLocation SUN_TEXTURE = NetherExp.location("textures/gui/azazel_frame_sun.png");
   private static final ResourceLocation CINEMATIC_TEXTURE = NetherExp.location("textures/gui/cinematic.png");
   private static final ResourceLocation SUN_LOWHP_TEXTURE = NetherExp.location("textures/gui/azazel_frame_sun_lowhp.png");
   private static final ResourceLocation HUMAN_SUN_TEXTURE = NetherExp.location("textures/gui/azazel_human_sun.png");
   private static final ResourceLocation HUMAN_SUN_LOWHP_TEXTURE = NetherExp.location("textures/gui/azazel_human_sun_lowhp.png");

   @SubscribeEvent
   public static void onRenderMercyText(Post event) {
      if (event.getName().equals(VanillaGuiLayers.HOTBAR)) {
         Minecraft mc = Minecraft.getInstance();
         if (mc.player != null && mc.level != null) {
            AzazelEntity azazel = null;

            for (AzazelEntity entity : mc.level.getEntitiesOfClass(AzazelEntity.class, mc.player.getBoundingBox().inflate(30.0))) {
               int state = (Integer)entity.getEntityData().get(AzazelEntity.ATTACK_STATE);
               if (state >= 6 && state <= 9) {
                  azazel = entity;
                  break;
               }
            }

            if (azazel != null) {
               int state = (Integer)azazel.getEntityData().get(AzazelEntity.ATTACK_STATE);
               GuiGraphics graphics = event.getGuiGraphics();
               int screenWidth = graphics.guiWidth();
               int screenHeight = graphics.guiHeight();
               if (state == 8 || state == 9) {
                  graphics.blit(CINEMATIC_TEXTURE, 0, 0, 0.0F, 0.0F, screenWidth, screenHeight, screenWidth, screenHeight);
               }

               if (state == 8) {
                  return;
               }

               int mercyTick = (Integer)azazel.getEntityData().get(AzazelEntity.MERCY_TICK);
               String fullText = "";
               if (state == 6) {
                  fullText = I18n.get("entity.netherman.azazel.surrender", new Object[0]);
               } else if (state == 7) {
                  fullText = I18n.get("entity.netherman.azazel.mercy", new Object[0]);
               } else if (state == 9) {
                  fullText = I18n.get("entity.netherman.azazel.death", new Object[0]);
               }

               int charsToShow = Math.min(fullText.length(), mercyTick / 2);
               String textToRender = fullText.substring(0, charsToShow);
               int boxWidth = mc.font.width(fullText) + 20;
               int boxHeight = 24;
               int boxX = (screenWidth - boxWidth) / 2;
               int boxY = screenHeight - 120;
               graphics.fill(boxX, boxY, boxX + boxWidth, boxY + boxHeight, -1728053248);
               graphics.drawString(mc.font, textToRender, boxX + 10, boxY + 8, 16777045, false);
            }
         }
      }
   }

   @SubscribeEvent
   public static void onRenderBossBar(BossEventProgress event) {
      Component name = event.getBossEvent().getName();
      if (name.getString().contains("Azazel")) {
         event.setCanceled(true);
         Minecraft mc = Minecraft.getInstance();
         if (mc.player == null || mc.level == null) {
            return;
         }

         AzazelEntity azazel = null;
         Iterator azazelHuman = mc.level.getEntitiesOfClass(AzazelEntity.class, mc.player.getBoundingBox().inflate(100.0)).iterator();
         if (azazelHuman.hasNext()) {
            AzazelEntity entity = (AzazelEntity)azazelHuman.next();
            azazel = entity;
         }

         AzazelHumanEntity azazelHumanx = null;
         Iterator var22 = mc.level.getEntitiesOfClass(AzazelHumanEntity.class, mc.player.getBoundingBox().inflate(100.0)).iterator();
         if (var22.hasNext()) {
            Entity entity = (Entity)var22.next();
            azazelHumanx = (AzazelHumanEntity)entity;
         }

         GuiGraphics guiGraphics = event.getGuiGraphics();
         int screenWidth = guiGraphics.guiWidth();
         int y = event.getY();
         int frameWidth = 186;
         int frameHeight = 42;
         int frameX = screenWidth / 2 - frameWidth / 2;
         int frameY = y;
         if (azazelHumanx != null) {
            NetherExp.location("textures/gui/azazel_human_frame.png");
         }

         if (azazelHumanx != null && azazelHumanx.getHealth() / azazelHumanx.getMaxHealth() < 0.3F) {
            ResourceLocation frameToUse = NetherExp.location("textures/gui/azazel_human_frame_lowhp.png");
         }

         guiGraphics.blit(FRAME_TEXTURE, frameX, frameY, 0.0F, 0.0F, frameWidth, frameHeight, frameWidth, frameHeight);
         float progress = event.getBossEvent().getProgress();
         int progressMaxWidth = 182;
         int progressHeight = 5;
         int currentProgressWidth = (int)(progressMaxWidth * progress);
         int progressX = frameX + 8;
         int progressOffsetY = 18;
         int progressY = frameY + progressOffsetY;
         if (currentProgressWidth > 0) {
            guiGraphics.blit(PROGRESS_TEXTURE, progressX, progressY, 0.0F, 0.0F, currentProgressWidth, progressHeight, progressMaxWidth, progressHeight);
         }

         ResourceLocation currentSunTexture = null;
         if (azazel != null) {
            currentSunTexture = SUN_TEXTURE;
            if ((Integer)azazel.getEntityData().get(AzazelEntity.PHASE_STATE) == 2) {
               currentSunTexture = SUN_LOWHP_TEXTURE;
            }
         } else if (azazelHumanx != null) {
            currentSunTexture = HUMAN_SUN_TEXTURE;
            if (azazelHumanx.getHealth() / azazelHumanx.getMaxHealth() < 0.3F) {
               currentSunTexture = HUMAN_SUN_LOWHP_TEXTURE;
            }
         }

         if (currentSunTexture != null) {
            guiGraphics.blit(currentSunTexture, frameX, frameY, 0.0F, 0.0F, frameWidth, frameHeight, frameWidth, frameHeight);
         }

         event.setIncrement(frameHeight + 5);
      }
   }
}
