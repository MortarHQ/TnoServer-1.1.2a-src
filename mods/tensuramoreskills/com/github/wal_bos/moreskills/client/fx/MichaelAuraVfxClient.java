package com.github.wal_bos.moreskills.client.fx;

import com.github.wal_bos.moreskills.net.msg.MichaelAuraVfxPayload;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.common.NeoForge;
import org.joml.Matrix4f;

public final class MichaelAuraVfxClient {
   private static final Map<Integer, MichaelAuraVfxClient.Aura> ACTIVE = new LinkedHashMap<>();
   private static final List<MichaelAuraVfxClient.TrailMote> TRAILS = new ArrayList<>();
   private static boolean initialized;

   private MichaelAuraVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(MichaelAuraVfxClient::tick);
         NeoForge.EVENT_BUS.addListener(MichaelAuraVfxClient::render);
      }
   }

   public static void play(MichaelAuraVfxPayload payload) {
      init();
      int key = payload.casterId() == -1 ? ACTIVE.size() * -1 - 1 : payload.casterId();
      if (payload.duration() <= 0) {
         ACTIVE.remove(key);
      } else {
         MichaelAuraVfxClient.Aura aura = ACTIVE.get(key);
         if (aura == null) {
            ACTIVE.put(key, new MichaelAuraVfxClient.Aura(payload));
         } else {
            aura.refresh(payload);
         }

         while (ACTIVE.size() > 48) {
            Integer first = ACTIVE.keySet().iterator().next();
            ACTIVE.remove(first);
         }
      }
   }

   private static void tick(Post event) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.level == null) {
         ACTIVE.clear();
         TRAILS.clear();
      } else {
         Iterator<Entry<Integer, MichaelAuraVfxClient.Aura>> auraIterator = ACTIVE.entrySet().iterator();

         while (auraIterator.hasNext()) {
            MichaelAuraVfxClient.Aura aura = auraIterator.next().getValue();
            aura.age++;
            if (aura.dead()) {
               auraIterator.remove();
            } else {
               aura.tick(mc);
            }
         }

         TRAILS.removeIf(MichaelAuraVfxClient.TrailMote::tick);

         while (TRAILS.size() > 496) {
            TRAILS.remove(0);
         }
      }
   }

   private static void render(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_PARTICLES || event.getStage() == Stage.AFTER_TRANSLUCENT_BLOCKS) {
         Minecraft mc = Minecraft.getInstance();
         if (mc.level != null && !TRAILS.isEmpty()) {
            Vec3 cam = MichaelAuraVfxClient.H.camera();
            Matrix4f matrix = event.getPoseStack().last().pose();
            float partial = event.getPartialTick().getGameTimeDeltaPartialTick(false);
            BufferBuilder buffer = MichaelAuraVfxClient.H.begin();
            boolean wrote = false;

            for (MichaelAuraVfxClient.TrailMote mote : TRAILS) {
               wrote |= mote.render(buffer, matrix, cam, partial);
            }

            MichaelAuraVfxClient.H.finish(buffer, wrote);
         }
      }
   }

   private static final class Aura {
      private final int casterId;
      private final long seed;
      private Vec3 payloadWorld;
      private Vec3 lastWorld;
      private Vec3 lastMotionDir = Vec3.ZERO;
      private float bodyHeight = 1.8F;
      private int duration;
      private int age;
      private int spawnClock;

      private Aura(MichaelAuraVfxPayload payload) {
         this.casterId = payload.casterId();
         this.payloadWorld = new Vec3(payload.x(), payload.y(), payload.z());
         this.lastWorld = null;
         this.duration = Math.max(12, payload.duration());
         this.seed = payload.seed();
      }

      private void refresh(MichaelAuraVfxPayload payload) {
         this.payloadWorld = new Vec3(payload.x(), payload.y(), payload.z());
         this.duration = Math.max(12, payload.duration());
         this.age = 0;
      }

      private boolean dead() {
         return this.age > this.duration + 8;
      }

      private void tick(Minecraft mc) {
         Entity entity = this.casterId != -1 && mc.level != null ? mc.level.getEntity(this.casterId) : null;
         Vec3 current;
         if (entity instanceof LivingEntity living) {
            current = living.position();
            this.bodyHeight = Math.max(0.95F, living.getBbHeight());
         } else if (entity != null) {
            current = entity.position();
            this.bodyHeight = Math.max(0.95F, entity.getBbHeight());
         } else {
            current = this.payloadWorld;
            this.bodyHeight = Math.max(1.2F, this.bodyHeight);
         }

         if (this.lastWorld == null) {
            this.lastWorld = current;
         } else {
            Vec3 delta = current.subtract(this.lastWorld);
            double horizontal = delta.horizontalDistance();
            double moved = delta.length();
            if (moved < 0.018) {
               this.lastWorld = current;
            } else {
               Vec3 moveDir = horizontal > 0.006
                  ? new Vec3(delta.x, 0.0, delta.z).normalize()
                  : (this.lastMotionDir.lengthSqr() > 0.001 ? this.lastMotionDir : new Vec3(0.0, 0.0, 1.0));
               this.lastMotionDir = moveDir;
               Vec3 side = new Vec3(moveDir.z, 0.0, -moveDir.x).normalize();
               double intensity = Mth.clamp(horizontal * 2.6 + Math.abs(delta.y) * 0.75, 0.32, 1.35);
               int count = Mth.clamp((int)Math.ceil(3.2 + intensity * 4.0), 3, 10);
               long tickSeed = this.seed ^ this.age * 92821 + this.spawnClock * 1013;

               for (int i = 0; i < count; i++) {
                  float h0 = MichaelAuraVfxClient.H.hash(tickSeed, 1000 + i * 31);
                  float h1 = MichaelAuraVfxClient.H.hash(tickSeed, 2000 + i * 37);
                  float h2 = MichaelAuraVfxClient.H.hash(tickSeed, 3000 + i * 41);
                  float h3 = MichaelAuraVfxClient.H.hash(tickSeed, 4000 + i * 43);
                  double trailLerp = 0.1 + h0 * 0.82;
                  double bodyY = 0.12 + h1 * this.bodyHeight * 0.92;
                  double back = 0.16 + h2 * (0.38 + intensity * 0.34);
                  double sideOffset = (h3 - 0.5) * (0.42 + intensity * 0.22);
                  Vec3 anchor = this.lastWorld.lerp(current, trailLerp);
                  Vec3 pos = anchor.subtract(moveDir.scale(back)).add(side.scale(sideOffset)).add(0.0, bodyY, 0.0);
                  Vec3 drift = moveDir.scale(-(0.006 + h2 * 0.02 + intensity * 0.006)).add(side.scale((h3 - 0.5) * 0.01)).add(0.0, 0.004 + h1 * 0.016, 0.0);
                  float size = 0.016F + h2 * 0.034F + (i % 7 == 0 ? 0.03F + h1 * 0.034F : 0.0F);
                  int life = 28 + (int)(h0 * 26.0F) + (i % 5 == 0 ? 12 : 0);
                  int palette = i % 6;
                  int r = palette == 0 ? 255 : (palette == 1 ? 116 : (palette == 2 ? 208 : (palette == 3 ? 255 : (palette == 4 ? 92 : 224))));
                  int g = palette == 0 ? 72 : (palette == 1 ? 236 : (palette == 2 ? 58 : (palette == 3 ? 124 : (palette == 4 ? 204 : 76))));
                  int b = palette == 3 ? 188 : 255;
                  MichaelAuraVfxClient.TRAILS.add(new MichaelAuraVfxClient.TrailMote(pos, drift, size, life, r, g, b, tickSeed + i * 7919L, i % 6 == 0));
               }

               if ((this.spawnClock++ & 3) == 0) {
                  for (int i = 0; i < 2; i++) {
                     float h0 = MichaelAuraVfxClient.H.hash(tickSeed, 7000 + i * 97);
                     float h1 = MichaelAuraVfxClient.H.hash(tickSeed, 7100 + i * 101);
                     double bodyY = 0.2 + h0 * this.bodyHeight * 0.86;
                     Vec3 pos = current.subtract(moveDir.scale(0.32 + h1 * 0.72)).add(side.scale((h0 - 0.5) * 0.7)).add(0.0, bodyY, 0.0);
                     Vec3 drift = moveDir.scale(-(0.012 + h1 * 0.018)).add(0.0, 0.006 + h0 * 0.012, 0.0);
                     MichaelAuraVfxClient.TRAILS
                        .add(
                           new MichaelAuraVfxClient.TrailMote(
                              pos, drift, 0.044F + h0 * 0.036F, 52 + (int)(h1 * 24.0F), 255, h0 > 0.5F ? 82 : 232, 255, tickSeed ^ i * 311L, true
                           )
                        );
                  }
               }

               this.lastWorld = current;
            }
         }
      }
   }

   private static final class H {
      public static BufferBuilder begin() {
         RenderSystem.enableBlend();
         RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
         RenderSystem.disableCull();
         RenderSystem.enableDepthTest();
         RenderSystem.depthMask(false);
         RenderSystem.setShader(GameRenderer::getPositionColorShader);
         return Tesselator.getInstance().begin(Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
      }

      public static void finish(BufferBuilder buffer, boolean wrote) {
         if (wrote) {
            try {
               BufferUploader.drawWithShader(buffer.buildOrThrow());
            } catch (IllegalStateException var3) {
            }
         }

         RenderSystem.depthMask(true);
         RenderSystem.enableCull();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableBlend();
      }

      public static Vec3 camera() {
         Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
         return camera.getPosition();
      }

      public static Vec3 rel(Vec3 p, Vec3 cam) {
         return p.subtract(cam);
      }

      public static float smooth(float x) {
         x = Mth.clamp(x, 0.0F, 1.0F);
         return x * x * (3.0F - 2.0F * x);
      }

      public static float hash(long seed, int salt) {
         long x = seed + salt * -7046029254386353131L;
         x ^= x >>> 30;
         x *= -4658895280553007687L;
         x ^= x >>> 27;
         x *= -7723592293110705685L;
         x ^= x >>> 31;
         return (float)(x & 16777215L) / 1.6777216E7F;
      }

      public static boolean star(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float size, int r, int g, int bl, float alpha) {
         if (alpha <= 0.001F) {
            return false;
         }

         Vec3 right = new Vec3(size, 0.0, 0.0);
         Vec3 up = new Vec3(0.0, size, 0.0);
         Vec3 f = new Vec3(0.0, 0.0, size * 0.32);
         tri(buffer, matrix, center.add(up), center.add(right.scale(0.18)), center.add(f), r, g, bl, alpha);
         tri(buffer, matrix, center.add(right), center.add(up.scale(-0.18)), center.add(f), r, g, bl, alpha);
         tri(buffer, matrix, center.add(up.scale(-1.0)), center.add(right.scale(-0.18)), center.add(f), r, g, bl, alpha);
         tri(buffer, matrix, center.add(right.scale(-1.0)), center.add(up.scale(0.18)), center.add(f), r, g, bl, alpha);
         tri(buffer, matrix, center.add(up.scale(0.38)), center.add(right.scale(0.38)), center.add(up.scale(-0.38)), r, g, bl, alpha * 0.3F);
         tri(buffer, matrix, center.add(up.scale(-0.38)), center.add(right.scale(-0.38)), center.add(up.scale(0.38)), r, g, bl, alpha * 0.3F);
         return true;
      }

      public static boolean spark(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float size, int r, int g, int bl, float alpha) {
         if (alpha <= 0.001F) {
            return false;
         }

         Vec3 up = new Vec3(0.0, size, 0.0);
         Vec3 right = new Vec3(size, 0.0, 0.0);
         tri(buffer, matrix, center.add(up), center.add(right.scale(0.32)), center.add(right.scale(-0.32)), r, g, bl, alpha);
         tri(buffer, matrix, center.add(up.scale(-1.0)), center.add(right.scale(-0.32)), center.add(right.scale(0.32)), r, g, bl, alpha * 0.66F);
         return true;
      }

      public static void tri(BufferBuilder buffer, Matrix4f matrix, Vec3 a, Vec3 b, Vec3 c, int r, int g, int bl, float alpha) {
         int aa = Mth.clamp((int)(alpha * 255.0F), 0, 255);
         if (aa > 0) {
            buffer.addVertex(matrix, (float)a.x, (float)a.y, (float)a.z).setColor(Mth.clamp(r, 0, 255), Mth.clamp(g, 0, 255), Mth.clamp(bl, 0, 255), aa);
            buffer.addVertex(matrix, (float)b.x, (float)b.y, (float)b.z).setColor(Mth.clamp(r, 0, 255), Mth.clamp(g, 0, 255), Mth.clamp(bl, 0, 255), aa);
            buffer.addVertex(matrix, (float)c.x, (float)c.y, (float)c.z).setColor(Mth.clamp(r, 0, 255), Mth.clamp(g, 0, 255), Mth.clamp(bl, 0, 255), aa);
         }
      }
   }

   private static final class TrailMote {
      private Vec3 pos;
      private final Vec3 drift;
      private final float size;
      private final int life;
      private final int r;
      private final int g;
      private final int b;
      private final long seed;
      private final boolean big;
      private int age;

      private TrailMote(Vec3 pos, Vec3 drift, float size, int life, int r, int g, int b, long seed, boolean big) {
         this.pos = pos;
         this.drift = drift;
         this.size = size;
         this.life = Math.max(12, life);
         this.r = r;
         this.g = g;
         this.b = b;
         this.seed = seed;
         this.big = big;
      }

      private boolean tick() {
         this.age++;
         this.pos = this.pos.add(this.drift).add(0.0, Math.sin((this.age + MichaelAuraVfxClient.H.hash(this.seed, 55) * 30.0) * 0.12) * 0.0018, 0.0);
         return this.age >= this.life;
      }

      private boolean render(BufferBuilder buffer, Matrix4f matrix, Vec3 cam, float partial) {
         float t = this.age + partial;
         float born = MichaelAuraVfxClient.H.smooth(Math.min(1.0F, t / 5.0F));
         float fade = 1.0F - MichaelAuraVfxClient.H.smooth(Math.max(0.0F, (t - this.life * 0.48F) / Math.max(1.0F, this.life * 0.52F)));
         float pulse = 0.72F
            + 0.28F
               * Mth.sin(
                  (this.age + partial) * (0.18F + MichaelAuraVfxClient.H.hash(this.seed, 91) * 0.12F)
                     + MichaelAuraVfxClient.H.hash(this.seed, 92) * (float) (Math.PI * 2)
               );
         float alpha = born * fade * pulse * (this.big ? 0.66F : 0.5F);
         if (alpha <= 0.001F) {
            return false;
         } else {
            Vec3 c = MichaelAuraVfxClient.H.rel(this.pos, cam);
            if (this.big) {
               boolean wrote = MichaelAuraVfxClient.H.star(buffer, matrix, c, this.size * (1.15F + pulse * 0.25F), this.r, this.g, this.b, alpha);
               return wrote | MichaelAuraVfxClient.H.spark(buffer, matrix, c.add(0.0, this.size * 0.12, 0.0), this.size * 0.62F, 255, 232, 255, alpha * 0.36F);
            } else {
               return MichaelAuraVfxClient.H.star(buffer, matrix, c, this.size * (0.88F + pulse * 0.18F), this.r, this.g, this.b, alpha);
            }
         }
      }
   }
}
