package it.hurts.sskirillss.relics.utils.data;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public final class GUIRenderer {
   private static final GUIRenderer INSTANCE = new GUIRenderer();
   private ResourceLocation texture;
   private PoseStack pose;
   private float posX;
   private float posY;
   private int texWidth;
   private int texHeight;
   private int patternWidth;
   private int patternHeight;
   private int texOffX;
   private int texOffY;
   private float scale;
   private float red;
   private float green;
   private float blue;
   private float alpha;
   private Supplier<Long> time;
   private AnimationData animation;
   private SpriteAnchor anchor;
   private List<SpriteMirror> mirror;

   public static GUIRenderer begin(ResourceLocation texture, PoseStack pose) {
      GUIRenderer renderer = INSTANCE;
      renderer.texture = texture;
      renderer.pose = pose;
      renderer.posX = 0.0F;
      renderer.posY = 0.0F;
      renderer.texWidth = -1;
      renderer.texHeight = -1;
      renderer.patternWidth = -1;
      renderer.patternHeight = -1;
      renderer.texOffX = 0;
      renderer.texOffY = 0;
      renderer.scale = 1.0F;
      renderer.red = -1.0F;
      renderer.green = -1.0F;
      renderer.blue = -1.0F;
      renderer.alpha = -1.0F;
      renderer.time = () -> {
         ClientLevel level = Minecraft.getInstance().level;
         return level == null ? 0L : level.getGameTime();
      };
      renderer.animation = AnimationData.builder().frame(0, Integer.MAX_VALUE);
      renderer.anchor = SpriteAnchor.CENTER;
      renderer.mirror = new ArrayList<>();
      return renderer;
   }

   public GUIRenderer pos(float posX, float posY) {
      GUIRenderer renderer = INSTANCE;
      renderer.posX = posX;
      renderer.posY = posY;
      return renderer;
   }

   public GUIRenderer texSize(int texWidth, int texHeight) {
      GUIRenderer renderer = INSTANCE;
      renderer.texWidth = texWidth;
      renderer.texHeight = texHeight;
      return renderer;
   }

   public GUIRenderer patternSize(int patternWidth, int patternHeight) {
      GUIRenderer renderer = INSTANCE;
      renderer.patternWidth = patternWidth;
      renderer.patternHeight = patternHeight;
      return renderer;
   }

   public GUIRenderer texOff(int texOffX, int texOffY) {
      GUIRenderer renderer = INSTANCE;
      renderer.texOffX = texOffX;
      renderer.texOffY = texOffY;
      return renderer;
   }

   public GUIRenderer color(float red, float green, float blue, float alpha) {
      GUIRenderer renderer = INSTANCE;
      renderer.red = red;
      renderer.green = green;
      renderer.blue = blue;
      renderer.alpha = alpha;
      return renderer;
   }

   public GUIRenderer color(int red, int green, int blue, int alpha) {
      return this.color(red / 255.0F, green / 255.0F, blue / 255.0F, alpha / 255.0F);
   }

   public GUIRenderer color(Color color) {
      return this.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
   }

   public GUIRenderer color(int color) {
      return this.color(new Color(color));
   }

   public GUIRenderer mirror(SpriteMirror... mirror) {
      GUIRenderer renderer = INSTANCE;
      renderer.mirror.addAll(Arrays.asList(mirror));
      return renderer;
   }

   public void end() {
      BufferBuilder builder = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
      Minecraft.getInstance().getTextureManager().getTexture(this.texture).bind();
      float[] color = Arrays.copyOf(RenderSystem.getShaderColor(), RenderSystem.getShaderColor().length);
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderTexture(0, this.texture);
      RenderSystem.setShaderColor(
         this.red == -1.0F ? color[0] : this.red,
         this.green == -1.0F ? color[1] : this.green,
         this.blue == -1.0F ? color[2] : this.blue,
         this.alpha == -1.0F ? color[3] : this.alpha
      );
      RenderSystem.disableCull();
      if (this.texHeight == -1) {
         this.texHeight = GlStateManager._getTexLevelParameter(3553, 0, 4097);
      }

      if (this.texWidth == -1) {
         this.texWidth = GlStateManager._getTexLevelParameter(3553, 0, 4096);
      }

      if (this.patternHeight == -1) {
         this.patternHeight = this.texHeight;
      }

      if (this.patternWidth == -1) {
         this.patternWidth = this.texWidth;
      }

      this.texOffY = this.patternHeight * (Integer)this.animation.getFrameByTime(this.time.get()).getKey();
      this.pose.pushPose();
      float xOff = 0.0F;
      float yOff = 0.0F;
      switch (this.anchor) {
         case CENTER:
            xOff = this.patternWidth / 2.0F * this.scale;
            yOff = this.patternHeight / 2.0F * this.scale;
            break;
         case TOP_RIGHT:
            xOff = this.patternWidth * this.scale;
            yOff = 0.0F;
            break;
         case TOP_LEFT:
            xOff = 0.0F;
            yOff = 0.0F;
            break;
         case BOTTOM_LEFT:
            xOff = 0.0F;
            yOff = this.patternHeight * this.scale;
            break;
         case BOTTOM_RIGHT:
            xOff = this.patternWidth * this.scale;
            yOff = this.patternHeight * this.scale;
            break;
         case TOP_CENTER:
            xOff = this.patternWidth / 2.0F * this.scale;
            yOff = 0.0F;
            break;
         case CENTER_LEFT:
            xOff = 0.0F;
            yOff = this.patternHeight / 2.0F * this.scale;
            break;
         case CENTER_RIGHT:
            xOff = this.patternWidth * this.scale;
            yOff = this.patternHeight / 2.0F * this.scale;
            break;
         case BOTTOM_CENTER:
            xOff = this.patternWidth / 2.0F * this.scale;
            yOff = this.patternHeight * this.scale;
      }

      this.pose.translate(this.posX - xOff, this.posY - yOff, 0.0F);
      this.pose.scale(this.scale, this.scale, 0.0F);
      Matrix4f m = this.pose.last().pose();
      float u1 = (float)this.texOffX / this.texWidth;
      float u2 = (float)(this.texOffX + this.patternWidth) / this.texWidth;
      float v1 = (float)(this.texOffY + this.patternHeight) / this.texHeight;
      float v2 = (float)this.texOffY / this.texHeight;

      for (SpriteMirror mirror : this.mirror) {
         switch (mirror) {
            case HORIZONTAL:
               float var12 = u1 + u2;
               u2 = u1;
               u1 = var12 - u1;
               break;
            case VERTICAL:
               float var10000 = v1 + v2;
               v2 = v1;
               v1 = var10000 - v1;
         }
      }

      builder.addVertex(m, 0.0F, this.patternHeight, 0.0F).setUv(u1, v1);
      builder.addVertex(m, this.patternWidth, this.patternHeight, 0.0F).setUv(u2, v1);
      builder.addVertex(m, this.patternWidth, 0.0F, 0.0F).setUv(u2, v2);
      builder.addVertex(m, 0.0F, 0.0F, 0.0F).setUv(u1, v2);
      this.pose.popPose();
      BufferUploader.drawWithShader(builder.buildOrThrow());
      RenderSystem.enableCull();
      RenderSystem.setShaderColor(color[0], color[1], color[2], color[3]);
   }

   public GUIRenderer posX(float posX) {
      this.posX = posX;
      return this;
   }

   public GUIRenderer posY(float posY) {
      this.posY = posY;
      return this;
   }

   public GUIRenderer texWidth(int texWidth) {
      this.texWidth = texWidth;
      return this;
   }

   public GUIRenderer texHeight(int texHeight) {
      this.texHeight = texHeight;
      return this;
   }

   public GUIRenderer patternWidth(int patternWidth) {
      this.patternWidth = patternWidth;
      return this;
   }

   public GUIRenderer patternHeight(int patternHeight) {
      this.patternHeight = patternHeight;
      return this;
   }

   public GUIRenderer texOffX(int texOffX) {
      this.texOffX = texOffX;
      return this;
   }

   public GUIRenderer texOffY(int texOffY) {
      this.texOffY = texOffY;
      return this;
   }

   public GUIRenderer scale(float scale) {
      this.scale = scale;
      return this;
   }

   public GUIRenderer red(float red) {
      this.red = red;
      return this;
   }

   public GUIRenderer green(float green) {
      this.green = green;
      return this;
   }

   public GUIRenderer blue(float blue) {
      this.blue = blue;
      return this;
   }

   public GUIRenderer alpha(float alpha) {
      this.alpha = alpha;
      return this;
   }

   public GUIRenderer time(Supplier<Long> time) {
      this.time = time;
      return this;
   }

   public GUIRenderer animation(AnimationData animation) {
      this.animation = animation;
      return this;
   }

   public GUIRenderer anchor(SpriteAnchor anchor) {
      this.anchor = anchor;
      return this;
   }
}
