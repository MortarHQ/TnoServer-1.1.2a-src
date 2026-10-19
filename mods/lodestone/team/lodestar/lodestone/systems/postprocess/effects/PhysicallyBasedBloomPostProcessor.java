package team.lodestar.lodestone.systems.postprocess.effects;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.client.renderer.RenderStateShard.OutputStateShard;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import team.lodestar.lodestone.LodestoneLib;
import team.lodestar.lodestone.helpers.StateShardHelper;
import team.lodestar.lodestone.systems.postprocess.PostProcessor;

public class PhysicallyBasedBloomPostProcessor extends PostProcessor {
   private RenderTarget bloomTarget;
   private final OutputStateShard bloomOutput = StateShardHelper.createOutputState("bloomTarget", () -> {
      if (this.bloomTarget != null) {
         this.bloomTarget.bindWrite(false);
      }
   });
   private RenderTarget BLURX2;
   private RenderTarget BLURY2;
   private RenderTarget BLURX4;
   private RenderTarget BLURY4;
   private RenderTarget BLURX8;
   private RenderTarget BLURY8;
   private boolean forceDisabled;

   public PhysicallyBasedBloomPostProcessor() {
      this.setActive(false);
   }

   @Override
   public ResourceLocation getPostChainLocation() {
      return LodestoneLib.lodestonePath("pb_bloom");
   }

   @Override
   public void init() {
      super.init();
      if (this.postChain != null) {
         this.bloomTarget = this.postChain.getTempTarget("bloomColor");
         this.BLURX2 = this.postChain.getTempTarget("blurX2");
         this.BLURY2 = this.postChain.getTempTarget("blurY2");
         this.BLURX4 = this.postChain.getTempTarget("blurX4");
         this.BLURY4 = this.postChain.getTempTarget("blurY4");
         this.BLURX8 = this.postChain.getTempTarget("blurX8");
         this.BLURY8 = this.postChain.getTempTarget("blurY8");
         Window window = Minecraft.getInstance().getWindow();
         this.resize(window.getWidth(), window.getHeight());
      }
   }

   @Override
   public void beforeProcess(Matrix4f viewModelMatrix) {
   }

   @Override
   public void afterProcess() {
      if (this.bloomTarget != null) {
         this.bloomTarget.clear(Minecraft.ON_OSX);
      }
   }

   @Override
   public void resize(int width, int height) {
      if (this.postChain != null) {
         this.postChain.screenWidth = width;
         this.postChain.screenHeight = this.postChain.screenTarget.height;
         this.postChain.updateOrthoMatrix();

         for (PostPass postpass : this.postChain.passes) {
            postpass.setOrthoMatrix(this.postChain.shaderOrthoMatrix);
         }

         this.bloomTarget.resize(width, height, Minecraft.ON_OSX);
         this.resize(this.BLURX2, width, height, 2);
         this.resize(this.BLURY2, width, height, 2);
         this.resize(this.BLURX4, width, height, 4);
         this.resize(this.BLURY4, width, height, 4);
         this.resize(this.BLURX8, width, height, 8);
         this.resize(this.BLURY8, width, height, 8);
      }
   }

   private void resize(RenderTarget target, int width, int height, int divisor) {
      if (target != null) {
         target.resize(width / divisor, height / divisor, Minecraft.ON_OSX);
         target.setFilterMode(9729);
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
      if (this.bloomTarget != null && src != null) {
         this.bloomTarget.copyDepthFrom(src);
         GlStateManager._glBindFramebuffer(36009, src.frameBufferId);
      }
   }
}
