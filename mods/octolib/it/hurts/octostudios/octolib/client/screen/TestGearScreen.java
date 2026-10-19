package it.hurts.octostudios.octolib.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import it.hurts.octostudios.octolib.client.animation.Tween;
import it.hurts.octostudios.octolib.client.particle.GalacticUIParticle;
import it.hurts.octostudios.octolib.client.particle.UIParticle;
import it.hurts.octostudios.octolib.client.screen.widget.TestGear;
import it.hurts.octostudios.octolib.util.RenderUtils;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;

public class TestGearScreen extends Screen {
   Tween tween = Tween.create().setLoops(-1);
   boolean shouldTick;

   public TestGearScreen() {
      super(Component.empty());
      this.tween.tweenRunnable(() -> Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.WOODEN_BUTTON_CLICK_ON, 2.0F)));
      this.tween.tweenInterval(0.5);
      this.tween.tweenRunnable(() -> Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.WOODEN_BUTTON_CLICK_OFF, 1.6F)));
      this.tween.tweenInterval(0.5);
      this.tween.tweenRunnable(() -> Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.WOODEN_BUTTON_CLICK_OFF, 1.6F)));
      this.tween.tweenInterval(0.5);
      this.tween.tweenRunnable(() -> Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.WOODEN_BUTTON_CLICK_OFF, 1.6F)));
      this.tween.tweenInterval(0.5);
      this.tween.start();
   }

   public void tick() {
      super.tick();
      if (Minecraft.getInstance().player.tickCount % 20 == 0) {
         this.shouldTick = true;
      }
   }

   protected void init() {
      super.init();
      this.addRenderableWidget(new TestGear((int)(this.width / 2.0F - 64.0F), (int)(this.height / 2.0F - 64.0F)));
   }

   public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
      super.render(guiGraphics, mouseX, mouseY, partialTick);
      if (this.shouldTick) {
         this.shouldTick = false;
         UIParticle particle = new GalacticUIParticle(1.25F, 40, mouseX, mouseY, UIParticle.Layer.SCREEN, 0.0F);
         particle.setScreen(this);
         particle.setRollVelocity(new Random().nextFloat(-5.0F, 5.0F));
         particle.instantiate();
      }

      RenderSystem.setShaderTexture(0, ResourceLocation.withDefaultNamespace("textures/particle/sga_").withSuffix("a.png"));
      RenderUtils.renderTilingTexture(guiGraphics.pose(), 10.0F, 10.0F, 0.0F, 0.0F, 8.0F, 8.0F, 80.0F, 80.0F, 0.0F, true, false);
   }

   public void onClose() {
      super.onClose();
      if (this.tween != null) {
         this.tween.kill();
      }
   }

   public boolean isPauseScreen() {
      return false;
   }
}
