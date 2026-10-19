package com.cerbon.bosses_of_mass_destruction.client.render;

import com.cerbon.cerbons_api.api.static_utilities.MathUtils;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class NodeBossBarRenderer {
   private final String entityTypeKey;
   private final List<Float> hpPercentages;
   private final ResourceLocation noteTexture;
   private final int textureSize;

   public NodeBossBarRenderer(String entityTypeKey, List<Float> hpPercentages, ResourceLocation noteTexture, int textureSize) {
      this.entityTypeKey = entityTypeKey;
      this.hpPercentages = hpPercentages;
      this.noteTexture = noteTexture;
      this.textureSize = textureSize;
   }

   public void renderBossBar(
      ResourceLocation[] backgroundTexture,
      ResourceLocation[] progressTexture,
      GuiGraphics guiGraphics,
      int x,
      int y,
      BossEvent bossEvent,
      CallbackInfo callbackInfo
   ) {
      Component name = bossEvent.getName();
      if (name.getContents() instanceof TranslatableContents translatableContents && translatableContents.getKey().equals(this.entityTypeKey)) {
         int colorLocation = bossEvent.getColor().ordinal();
         guiGraphics.blitSprite(backgroundTexture[colorLocation], 182, 5, 0, 0, x, y, 182, 5);
         int i = Mth.lerpDiscrete(bossEvent.getProgress(), 0, 182);
         if (i > 0) {
            int progressLocation = bossEvent.getColor().ordinal();
            guiGraphics.blitSprite(progressTexture[progressLocation], 182, 5, 0, 0, x, y, i, 5);
         }

         this.renderBossNodes(bossEvent, guiGraphics, x, y);
         callbackInfo.cancel();
      }
   }

   private void renderBossNodes(BossEvent bossEvent, GuiGraphics guiGraphics, int x, int y) {
      int steppedPercentage = (int)(192.0F * MathUtils.roundedStep(bossEvent.getProgress(), this.hpPercentages, true)) + 7;
      guiGraphics.blit(this.noteTexture, x - 3, y - 1, 0.0F, 0.0F, steppedPercentage, 7, this.textureSize, this.textureSize);
      int steppedPercentageReverse = 192 - steppedPercentage;
      guiGraphics.blit(
         this.noteTexture, x - 3 + steppedPercentage, y - 1, steppedPercentage, 7.0F, steppedPercentageReverse, 7, this.textureSize, this.textureSize
      );
   }
}
