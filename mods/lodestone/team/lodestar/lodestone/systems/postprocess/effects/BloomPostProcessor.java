package team.lodestar.lodestone.systems.postprocess.effects;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderStateShard.OutputStateShard;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import team.lodestar.lodestone.LodestoneLib;
import team.lodestar.lodestone.systems.postprocess.PostProcessor;

public class BloomPostProcessor extends PostProcessor {
   private RenderTarget bloomTarget;
   private final OutputStateShard bloomOutput = new OutputStateShard("bloomTarget", () -> {
      if (this.bloomTarget != null) {
         this.bloomTarget.bindWrite(false);
      }
   }, () -> Minecraft.getInstance().getMainRenderTarget().bindWrite(false));
   private boolean forceDisabled;

   public BloomPostProcessor() {
      this.setActive(false);
   }

   @Override
   public ResourceLocation getPostChainLocation() {
      return LodestoneLib.lodestonePath("bloom");
   }

   @Override
   public void init() {
      super.init();
      if (this.postChain != null) {
         this.bloomTarget = this.postChain.getTempTarget("bloomColor");
         this.bloomTarget.setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
      }
   }

   @Override
   public void beforeProcess(Matrix4f viewModelMatrix) {
   }

   @Override
   public void afterProcess() {
      this.bloomTarget.clear(Minecraft.ON_OSX);
   }

   @Override
   public void resize(int width, int height) {
      super.resize(width, height);
      if (this.bloomTarget != null) {
         this.bloomTarget.resize(width, height, Minecraft.ON_OSX);
      }
   }

   public void forceDisable() {
      this.forceDisabled = true;
      this.setActive(false);
   }

   @Override
   public void setActive(boolean active) {
      if (this.forceDisabled) {
         active = false;
      }

      super.setActive(active);
   }

   public OutputStateShard getBloomOutput() {
      return this.bloomOutput;
   }

   public RenderTarget getBloomTarget() {
      return this.bloomTarget;
   }

   public void copyDepthFromMain() {
      this.copyDepthFrom(Minecraft.getInstance().getMainRenderTarget());
   }

   public void copyDepthFrom(RenderTarget src) {
      if (this.bloomTarget != null) {
         this.bloomTarget.copyDepthFrom(src);
         GlStateManager._glBindFramebuffer(36009, src.frameBufferId);
      }
   }
}
