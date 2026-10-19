package com.github.wal_bos.moreskills.client.fx;

import com.github.wal_bos.moreskills.net.msg.MichaelHalberdShotVfxPayload;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.common.NeoForge;
import org.joml.Matrix4f;

public final class MichaelHalberdShotVfxClient {
   private static final List<MichaelHalberdShotVfxClient.Shot> ACTIVE = new CopyOnWriteArrayList<>();
   private static boolean initialized;

   private MichaelHalberdShotVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(MichaelHalberdShotVfxClient::tick);
         NeoForge.EVENT_BUS.addListener(MichaelHalberdShotVfxClient::render);
      }
   }

   public static void play(MichaelHalberdShotVfxPayload payload) {
      init();
      ACTIVE.add(new MichaelHalberdShotVfxClient.Shot(payload));

      while (ACTIVE.size() > 18) {
         ACTIVE.remove(0);
      }
   }

   private static void tick(Post event) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.level == null) {
         ACTIVE.clear();
      } else {
         for (MichaelHalberdShotVfxClient.Shot s : ACTIVE) {
            s.age++;
            if (s.age > s.life) {
               ACTIVE.remove(s);
            }
         }
      }
   }

   private static void render(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_PARTICLES && !ACTIVE.isEmpty()) {
         Minecraft mc = Minecraft.getInstance();
         if (mc.level != null) {
            Vec3 cam = MichaelHalberdShotVfxClient.H.camera();
            Matrix4f matrix = event.getPoseStack().last().pose();
            float partial = event.getPartialTick().getGameTimeDeltaPartialTick(false);
            BufferBuilder buffer = MichaelHalberdShotVfxClient.H.begin();
            boolean wrote = false;

            for (MichaelHalberdShotVfxClient.Shot s : ACTIVE) {
               wrote |= s.render(mc, buffer, matrix, cam, partial);
            }

            MichaelHalberdShotVfxClient.H.finish(buffer, wrote);
         }
      }
   }

   private static final class CommissionOverdrive {
      private static boolean render(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         wrote |= layer0(buffer, matrix, center, time, seed, alpha);
         wrote |= layer1(buffer, matrix, center, time, seed, alpha);
         wrote |= layer2(buffer, matrix, center, time, seed, alpha);
         wrote |= layer3(buffer, matrix, center, time, seed, alpha);
         wrote |= layer4(buffer, matrix, center, time, seed, alpha);
         wrote |= layer5(buffer, matrix, center, time, seed, alpha);
         wrote |= layer6(buffer, matrix, center, time, seed, alpha);
         wrote |= layer7(buffer, matrix, center, time, seed, alpha);
         wrote |= layer8(buffer, matrix, center, time, seed, alpha);
         wrote |= layer9(buffer, matrix, center, time, seed, alpha);
         wrote |= layer10(buffer, matrix, center, time, seed, alpha);
         wrote |= layer11(buffer, matrix, center, time, seed, alpha);
         wrote |= layer12(buffer, matrix, center, time, seed, alpha);
         wrote |= layer13(buffer, matrix, center, time, seed, alpha);
         wrote |= layer14(buffer, matrix, center, time, seed, alpha);
         wrote |= layer15(buffer, matrix, center, time, seed, alpha);
         wrote |= layer16(buffer, matrix, center, time, seed, alpha);
         wrote |= layer17(buffer, matrix, center, time, seed, alpha);
         wrote |= layer18(buffer, matrix, center, time, seed, alpha);
         wrote |= layer19(buffer, matrix, center, time, seed, alpha);
         wrote |= layer20(buffer, matrix, center, time, seed, alpha);
         wrote |= layer21(buffer, matrix, center, time, seed, alpha);
         wrote |= layer22(buffer, matrix, center, time, seed, alpha);
         wrote |= layer23(buffer, matrix, center, time, seed, alpha);
         wrote |= layer24(buffer, matrix, center, time, seed, alpha);
         wrote |= layer25(buffer, matrix, center, time, seed, alpha);
         wrote |= layer26(buffer, matrix, center, time, seed, alpha);
         wrote |= layer27(buffer, matrix, center, time, seed, alpha);
         wrote |= layer28(buffer, matrix, center, time, seed, alpha);
         wrote |= layer29(buffer, matrix, center, time, seed, alpha);
         wrote |= layer30(buffer, matrix, center, time, seed, alpha);
         wrote |= layer31(buffer, matrix, center, time, seed, alpha);
         wrote |= layer32(buffer, matrix, center, time, seed, alpha);
         wrote |= layer33(buffer, matrix, center, time, seed, alpha);
         wrote |= layer34(buffer, matrix, center, time, seed, alpha);
         wrote |= layer35(buffer, matrix, center, time, seed, alpha);
         wrote |= layer36(buffer, matrix, center, time, seed, alpha);
         wrote |= layer37(buffer, matrix, center, time, seed, alpha);
         wrote |= layer38(buffer, matrix, center, time, seed, alpha);
         wrote |= layer39(buffer, matrix, center, time, seed, alpha);
         wrote |= layer40(buffer, matrix, center, time, seed, alpha);
         wrote |= layer41(buffer, matrix, center, time, seed, alpha);
         wrote |= layer42(buffer, matrix, center, time, seed, alpha);
         wrote |= layer43(buffer, matrix, center, time, seed, alpha);
         wrote |= layer44(buffer, matrix, center, time, seed, alpha);
         wrote |= layer45(buffer, matrix, center, time, seed, alpha);
         wrote |= layer46(buffer, matrix, center, time, seed, alpha);
         wrote |= layer47(buffer, matrix, center, time, seed, alpha);
         wrote |= layer48(buffer, matrix, center, time, seed, alpha);
         wrote |= layer49(buffer, matrix, center, time, seed, alpha);
         wrote |= layer50(buffer, matrix, center, time, seed, alpha);
         wrote |= layer51(buffer, matrix, center, time, seed, alpha);
         wrote |= layer52(buffer, matrix, center, time, seed, alpha);
         wrote |= layer53(buffer, matrix, center, time, seed, alpha);
         wrote |= layer54(buffer, matrix, center, time, seed, alpha);
         wrote |= layer55(buffer, matrix, center, time, seed, alpha);
         wrote |= layer56(buffer, matrix, center, time, seed, alpha);
         wrote |= layer57(buffer, matrix, center, time, seed, alpha);
         wrote |= layer58(buffer, matrix, center, time, seed, alpha);
         wrote |= layer59(buffer, matrix, center, time, seed, alpha);
         wrote |= layer60(buffer, matrix, center, time, seed, alpha);
         wrote |= layer61(buffer, matrix, center, time, seed, alpha);
         wrote |= layer62(buffer, matrix, center, time, seed, alpha);
         wrote |= layer63(buffer, matrix, center, time, seed, alpha);
         wrote |= layer64(buffer, matrix, center, time, seed, alpha);
         wrote |= layer65(buffer, matrix, center, time, seed, alpha);
         wrote |= layer66(buffer, matrix, center, time, seed, alpha);
         wrote |= layer67(buffer, matrix, center, time, seed, alpha);
         wrote |= layer68(buffer, matrix, center, time, seed, alpha);
         wrote |= layer69(buffer, matrix, center, time, seed, alpha);
         wrote |= layer70(buffer, matrix, center, time, seed, alpha);
         wrote |= layer71(buffer, matrix, center, time, seed, alpha);
         wrote |= layer72(buffer, matrix, center, time, seed, alpha);
         wrote |= layer73(buffer, matrix, center, time, seed, alpha);
         wrote |= layer74(buffer, matrix, center, time, seed, alpha);
         wrote |= layer75(buffer, matrix, center, time, seed, alpha);
         wrote |= layer76(buffer, matrix, center, time, seed, alpha);
         wrote |= layer77(buffer, matrix, center, time, seed, alpha);
         wrote |= layer78(buffer, matrix, center, time, seed, alpha);
         wrote |= layer79(buffer, matrix, center, time, seed, alpha);
         wrote |= layer80(buffer, matrix, center, time, seed, alpha);
         wrote |= layer81(buffer, matrix, center, time, seed, alpha);
         wrote |= layer82(buffer, matrix, center, time, seed, alpha);
         wrote |= layer83(buffer, matrix, center, time, seed, alpha);
         wrote |= layer84(buffer, matrix, center, time, seed, alpha);
         wrote |= layer85(buffer, matrix, center, time, seed, alpha);
         wrote |= layer86(buffer, matrix, center, time, seed, alpha);
         wrote |= layer87(buffer, matrix, center, time, seed, alpha);
         wrote |= layer88(buffer, matrix, center, time, seed, alpha);
         wrote |= layer89(buffer, matrix, center, time, seed, alpha);
         wrote |= layer90(buffer, matrix, center, time, seed, alpha);
         wrote |= layer91(buffer, matrix, center, time, seed, alpha);
         wrote |= layer92(buffer, matrix, center, time, seed, alpha);
         wrote |= layer93(buffer, matrix, center, time, seed, alpha);
         wrote |= layer94(buffer, matrix, center, time, seed, alpha);
         wrote |= layer95(buffer, matrix, center, time, seed, alpha);
         wrote |= layer96(buffer, matrix, center, time, seed, alpha);
         wrote |= layer97(buffer, matrix, center, time, seed, alpha);
         wrote |= layer98(buffer, matrix, center, time, seed, alpha);
         wrote |= layer99(buffer, matrix, center, time, seed, alpha);
         wrote |= layer100(buffer, matrix, center, time, seed, alpha);
         wrote |= layer101(buffer, matrix, center, time, seed, alpha);
         wrote |= layer102(buffer, matrix, center, time, seed, alpha);
         wrote |= layer103(buffer, matrix, center, time, seed, alpha);
         wrote |= layer104(buffer, matrix, center, time, seed, alpha);
         wrote |= layer105(buffer, matrix, center, time, seed, alpha);
         wrote |= layer106(buffer, matrix, center, time, seed, alpha);
         wrote |= layer107(buffer, matrix, center, time, seed, alpha);
         wrote |= layer108(buffer, matrix, center, time, seed, alpha);
         wrote |= layer109(buffer, matrix, center, time, seed, alpha);
         wrote |= layer110(buffer, matrix, center, time, seed, alpha);
         wrote |= layer111(buffer, matrix, center, time, seed, alpha);
         wrote |= layer112(buffer, matrix, center, time, seed, alpha);
         wrote |= layer113(buffer, matrix, center, time, seed, alpha);
         wrote |= layer114(buffer, matrix, center, time, seed, alpha);
         wrote |= layer115(buffer, matrix, center, time, seed, alpha);
         wrote |= layer116(buffer, matrix, center, time, seed, alpha);
         wrote |= layer117(buffer, matrix, center, time, seed, alpha);
         wrote |= layer118(buffer, matrix, center, time, seed, alpha);
         wrote |= layer119(buffer, matrix, center, time, seed, alpha);
         wrote |= layer120(buffer, matrix, center, time, seed, alpha);
         wrote |= layer121(buffer, matrix, center, time, seed, alpha);
         wrote |= layer122(buffer, matrix, center, time, seed, alpha);
         wrote |= layer123(buffer, matrix, center, time, seed, alpha);
         wrote |= layer124(buffer, matrix, center, time, seed, alpha);
         wrote |= layer125(buffer, matrix, center, time, seed, alpha);
         wrote |= layer126(buffer, matrix, center, time, seed, alpha);
         wrote |= layer127(buffer, matrix, center, time, seed, alpha);
         wrote |= layer128(buffer, matrix, center, time, seed, alpha);
         wrote |= layer129(buffer, matrix, center, time, seed, alpha);
         wrote |= layer130(buffer, matrix, center, time, seed, alpha);
         wrote |= layer131(buffer, matrix, center, time, seed, alpha);
         wrote |= layer132(buffer, matrix, center, time, seed, alpha);
         wrote |= layer133(buffer, matrix, center, time, seed, alpha);
         wrote |= layer134(buffer, matrix, center, time, seed, alpha);
         wrote |= layer135(buffer, matrix, center, time, seed, alpha);
         wrote |= layer136(buffer, matrix, center, time, seed, alpha);
         wrote |= layer137(buffer, matrix, center, time, seed, alpha);
         wrote |= layer138(buffer, matrix, center, time, seed, alpha);
         wrote |= layer139(buffer, matrix, center, time, seed, alpha);
         wrote |= layer140(buffer, matrix, center, time, seed, alpha);
         wrote |= layer141(buffer, matrix, center, time, seed, alpha);
         wrote |= layer142(buffer, matrix, center, time, seed, alpha);
         wrote |= layer143(buffer, matrix, center, time, seed, alpha);
         wrote |= layer144(buffer, matrix, center, time, seed, alpha);
         wrote |= layer145(buffer, matrix, center, time, seed, alpha);
         wrote |= layer146(buffer, matrix, center, time, seed, alpha);
         wrote |= layer147(buffer, matrix, center, time, seed, alpha);
         wrote |= layer148(buffer, matrix, center, time, seed, alpha);
         return wrote | layer149(buffer, matrix, center, time, seed, alpha);
      }

      private static boolean layer0(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1000);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2000);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3000);
         double spin = time * 0.012 + h0 * Math.PI * 2.0;
         double radius = 0.42 + h1 * 0.58;
         double lift = -1.1 + Math.sin(time * 0.021 + h2 * 6.28318) * 0.18;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.27) * (radius * 0.58), lift + 0.2, Math.sin(spin + 0.27) * (radius * 0.58));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.005F, 118, 28, 255, alpha * 0.18F);
         if (((int)(time + 0.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.018F, 255, 66, 235, alpha * 0.3F);
         }

         if (((int)(time + 0.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.72, 0.006F, 118, 28, 255, alpha * 0.06F, 24
            );
         }

         return wrote;
      }

      private static boolean layer1(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1013);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2017);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3019);
         double spin = time * 0.015 + h0 * Math.PI * 2.0;
         double radius = 0.475 + h1 * 0.62;
         double lift = -1.005 + Math.sin(time * 0.025 + h2 * 6.28318) * 0.205;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.301) * (radius * 0.615), lift + 0.235, Math.sin(spin + 0.301) * (radius * 0.615));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0067F, 141, 45, 255, alpha * 0.192F);
         if (((int)(time + 1.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.022F, 82, 220, 255, alpha * 0.325F);
         }

         if (((int)(time + 1.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.738, 0.0075F, 141, 45, 255, alpha * 0.068F, 32
            );
         }

         return wrote;
      }

      private static boolean layer2(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1026);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2034);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3038);
         double spin = time * 0.018 + h0 * Math.PI * 2.0;
         double radius = 0.53 + h1 * 0.66;
         double lift = -0.91 + Math.sin(time * 0.029 + h2 * 6.28318) * 0.23;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.332) * (radius * 0.65), lift + 0.27, Math.sin(spin + 0.332) * (radius * 0.65));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0084F, 164, 62, 255, alpha * 0.204F);
         if (((int)(time + 2.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.026F, 246, 66, 255, alpha * 0.35F);
         }

         if (((int)(time + 2.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.756, 0.009F, 164, 62, 255, alpha * 0.076F, 40
            );
         }

         return wrote;
      }

      private static boolean layer3(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1039);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2051);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3057);
         double spin = time * 0.021 + h0 * Math.PI * 2.0;
         double radius = 0.585 + h1 * 0.7;
         double lift = -0.815 + Math.sin(time * 0.033 + h2 * 6.28318) * 0.255;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.363) * (radius * 0.685), lift + 0.305, Math.sin(spin + 0.363) * (radius * 0.685));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0101F, 187, 79, 255, alpha * 0.216F);
         if (((int)(time + 3.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.03F, 96, 230, 255, alpha * 0.375F);
         }

         if (((int)(time + 3.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.774, 0.0105F, 187, 79, 255, alpha * 0.084F, 48
            );
         }

         return wrote;
      }

      private static boolean layer4(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1052);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2068);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3076);
         double spin = time * 0.024 + h0 * Math.PI * 2.0;
         double radius = 0.64 + h1 * 0.74;
         double lift = -0.72 + Math.sin(time * 0.037 + h2 * 6.28318) * 0.28;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.394) * (radius * 0.72), lift + 0.34, Math.sin(spin + 0.394) * (radius * 0.72));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0118F, 210, 96, 255, alpha * 0.228F);
         if (((int)(time + 4.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.034F, 110, 215, 255, alpha * 0.4F);
         }

         if (((int)(time + 4.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.792, 0.012F, 210, 96, 255, alpha * 0.092F, 56
            );
         }

         return wrote;
      }

      private static boolean layer5(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1065);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2085);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3095);
         double spin = time * 0.027 + h0 * Math.PI * 2.0;
         double radius = 0.695 + h1 * 0.78;
         double lift = -0.625 + Math.sin(time * 0.041 + h2 * 6.28318) * 0.18;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.425) * (radius * 0.755), lift + 0.375, Math.sin(spin + 0.425) * (radius * 0.755));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0135F, 233, 113, 255, alpha * 0.24F);
         if (((int)(time + 5.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.038F, 255, 96, 230, alpha * 0.425F);
         }

         if (((int)(time + 5.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.81, 0.0135F, 233, 113, 255, alpha * 0.1F, 24
            );
         }

         return wrote;
      }

      private static boolean layer6(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1078);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2102);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3114);
         double spin = time * 0.03 + h0 * Math.PI * 2.0;
         double radius = 0.75 + h1 * 0.82;
         double lift = -0.53 + Math.sin(time * 0.045 + h2 * 6.28318) * 0.205;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.456) * (radius * 0.58), lift + 0.41, Math.sin(spin + 0.456) * (radius * 0.58));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0152F, 118, 34, 255, alpha * 0.252F);
         if (((int)(time + 6.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.042F, 255, 86, 238, alpha * 0.45F);
         }

         if (((int)(time + 6.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.828, 0.006F, 118, 34, 255, alpha * 0.108F, 32
            );
         }

         return wrote;
      }

      private static boolean layer7(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1091);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2119);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3133);
         double spin = time * 0.033 + h0 * Math.PI * 2.0;
         double radius = 0.805 + h1 * 0.86;
         double lift = -0.435 + Math.sin(time * 0.021 + h2 * 6.28318) * 0.23;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.487) * (radius * 0.615), lift + 0.2, Math.sin(spin + 0.487) * (radius * 0.615));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0169F, 141, 51, 255, alpha * 0.264F);
         if (((int)(time + 7.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.046F, 255, 239, 255, alpha * 0.475F);
         }

         if (((int)(time + 7.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.846, 0.0075F, 141, 51, 255, alpha * 0.116F, 40
            );
         }

         return wrote;
      }

      private static boolean layer8(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1104);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2136);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3152);
         double spin = time * 0.036 + h0 * Math.PI * 2.0;
         double radius = 0.86 + h1 * 0.9;
         double lift = -0.34 + Math.sin(time * 0.025 + h2 * 6.28318) * 0.255;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.27) * (radius * 0.65), lift + 0.235, Math.sin(spin + 0.27) * (radius * 0.65));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0186F, 164, 68, 255, alpha * 0.276F);
         if (((int)(time + 8.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.018F, 255, 246, 255, alpha * 0.5F);
         }

         if (((int)(time + 8.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.864, 0.009F, 164, 68, 255, alpha * 0.124F, 48
            );
         }

         return wrote;
      }

      private static boolean layer9(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1117);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2153);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3171);
         double spin = time * 0.012 + h0 * Math.PI * 2.0;
         double radius = 0.915 + h1 * 0.94;
         double lift = -0.245 + Math.sin(time * 0.029 + h2 * 6.28318) * 0.28;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.301) * (radius * 0.685), lift + 0.27, Math.sin(spin + 0.301) * (radius * 0.685));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.005F, 187, 85, 255, alpha * 0.288F);
         if (((int)(time + 9.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.022F, 255, 253, 255, alpha * 0.525F);
         }

         if (((int)(time + 9.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.882, 0.0105F, 187, 85, 255, alpha * 0.06F, 56
            );
         }

         return wrote;
      }

      private static boolean layer10(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1130);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2170);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3190);
         double spin = time * 0.015 + h0 * Math.PI * 2.0;
         double radius = 0.97 + h1 * 0.98;
         double lift = -0.15 + Math.sin(time * 0.033 + h2 * 6.28318) * 0.18;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.332) * (radius * 0.72), lift + 0.305, Math.sin(spin + 0.332) * (radius * 0.72));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0067F, 210, 102, 255, alpha * 0.3F);
         if (((int)(time + 10.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.026F, 255, 195, 255, alpha * 0.3F);
         }

         if (((int)(time + 10.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.9, 0.012F, 210, 102, 255, alpha * 0.068F, 24
            );
         }

         return wrote;
      }

      private static boolean layer11(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1143);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2187);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3209);
         double spin = time * 0.018 + h0 * Math.PI * 2.0;
         double radius = 1.025 + h1 * 0.58;
         double lift = -0.055 + Math.sin(time * 0.037 + h2 * 6.28318) * 0.205;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.363) * (radius * 0.755), lift + 0.34, Math.sin(spin + 0.363) * (radius * 0.755));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0084F, 233, 119, 255, alpha * 0.312F);
         if (((int)(time + 11.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.03F, 255, 202, 255, alpha * 0.325F);
         }

         if (((int)(time + 11.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.918, 0.0135F, 233, 119, 255, alpha * 0.076F, 32
            );
         }

         return wrote;
      }

      private static boolean layer12(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1156);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2204);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3228);
         double spin = time * 0.021 + h0 * Math.PI * 2.0;
         double radius = 1.08 + h1 * 0.62;
         double lift = 0.04 + Math.sin(time * 0.041 + h2 * 6.28318) * 0.23;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.394) * (radius * 0.58), lift + 0.375, Math.sin(spin + 0.394) * (radius * 0.58));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0101F, 118, 40, 255, alpha * 0.324F);
         if (((int)(time + 12.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.034F, 255, 209, 255, alpha * 0.35F);
         }

         if (((int)(time + 12.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.72, 0.006F, 118, 40, 255, alpha * 0.084F, 40
            );
         }

         return wrote;
      }

      private static boolean layer13(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1169);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2221);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3247);
         double spin = time * 0.024 + h0 * Math.PI * 2.0;
         double radius = 1.135 + h1 * 0.66;
         double lift = 0.135 + Math.sin(time * 0.045 + h2 * 6.28318) * 0.255;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.425) * (radius * 0.615), lift + 0.41, Math.sin(spin + 0.425) * (radius * 0.615));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0118F, 141, 57, 255, alpha * 0.18F);
         if (((int)(time + 13.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.038F, 255, 76, 240, alpha * 0.375F);
         }

         if (((int)(time + 13.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.738, 0.0075F, 141, 57, 255, alpha * 0.092F, 48
            );
         }

         return wrote;
      }

      private static boolean layer14(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1182);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2238);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3266);
         double spin = time * 0.027 + h0 * Math.PI * 2.0;
         double radius = 1.19 + h1 * 0.7;
         double lift = 0.23 + Math.sin(time * 0.021 + h2 * 6.28318) * 0.28;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.456) * (radius * 0.65), lift + 0.2, Math.sin(spin + 0.456) * (radius * 0.65));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0135F, 164, 74, 255, alpha * 0.192F);
         if (((int)(time + 14.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.042F, 255, 223, 255, alpha * 0.4F);
         }

         if (((int)(time + 14.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.756, 0.009F, 164, 74, 255, alpha * 0.1F, 56
            );
         }

         return wrote;
      }

      private static boolean layer15(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1195);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2255);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3285);
         double spin = time * 0.03 + h0 * Math.PI * 2.0;
         double radius = 1.245 + h1 * 0.74;
         double lift = 0.325 + Math.sin(time * 0.025 + h2 * 6.28318) * 0.18;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.487) * (radius * 0.685), lift + 0.235, Math.sin(spin + 0.487) * (radius * 0.685));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0152F, 187, 91, 255, alpha * 0.204F);
         if (((int)(time + 15.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.046F, 112, 232, 255, alpha * 0.425F);
         }

         if (((int)(time + 15.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.774, 0.0105F, 187, 91, 255, alpha * 0.108F, 24
            );
         }

         return wrote;
      }

      private static boolean layer16(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1208);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2272);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3304);
         double spin = time * 0.033 + h0 * Math.PI * 2.0;
         double radius = 1.3 + h1 * 0.78;
         double lift = 0.42 + Math.sin(time * 0.029 + h2 * 6.28318) * 0.205;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.27) * (radius * 0.72), lift + 0.27, Math.sin(spin + 0.27) * (radius * 0.72));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0169F, 210, 108, 255, alpha * 0.216F);
         if (((int)(time + 16.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.018F, 255, 237, 255, alpha * 0.45F);
         }

         if (((int)(time + 16.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.792, 0.012F, 210, 108, 255, alpha * 0.116F, 32
            );
         }

         return wrote;
      }

      private static boolean layer17(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1221);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2289);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3323);
         double spin = time * 0.036 + h0 * Math.PI * 2.0;
         double radius = 0.42 + h1 * 0.82;
         double lift = 0.515 + Math.sin(time * 0.033 + h2 * 6.28318) * 0.23;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.301) * (radius * 0.755), lift + 0.305, Math.sin(spin + 0.301) * (radius * 0.755));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0186F, 233, 29, 255, alpha * 0.228F);
         if (((int)(time + 17.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.022F, 255, 94, 232, alpha * 0.475F);
         }

         if (((int)(time + 17.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.81, 0.0135F, 233, 29, 255, alpha * 0.124F, 40
            );
         }

         return wrote;
      }

      private static boolean layer18(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1234);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2306);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3342);
         double spin = time * 0.012 + h0 * Math.PI * 2.0;
         double radius = 0.475 + h1 * 0.86;
         double lift = 0.61 + Math.sin(time * 0.037 + h2 * 6.28318) * 0.255;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.332) * (radius * 0.58), lift + 0.34, Math.sin(spin + 0.332) * (radius * 0.58));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.005F, 118, 46, 255, alpha * 0.24F);
         if (((int)(time + 18.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.026F, 255, 251, 255, alpha * 0.5F);
         }

         if (((int)(time + 18.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.828, 0.006F, 118, 46, 255, alpha * 0.06F, 48
            );
         }

         return wrote;
      }

      private static boolean layer19(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1247);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2323);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3361);
         double spin = time * 0.015 + h0 * Math.PI * 2.0;
         double radius = 0.53 + h1 * 0.9;
         double lift = 0.705 + Math.sin(time * 0.041 + h2 * 6.28318) * 0.28;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.363) * (radius * 0.615), lift + 0.375, Math.sin(spin + 0.363) * (radius * 0.615));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0067F, 141, 63, 255, alpha * 0.252F);
         if (((int)(time + 19.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.03F, 255, 193, 255, alpha * 0.525F);
         }

         if (((int)(time + 19.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.846, 0.0075F, 141, 63, 255, alpha * 0.068F, 56
            );
         }

         return wrote;
      }

      private static boolean layer20(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1260);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2340);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3380);
         double spin = time * 0.018 + h0 * Math.PI * 2.0;
         double radius = 0.585 + h1 * 0.94;
         double lift = 0.8 + Math.sin(time * 0.045 + h2 * 6.28318) * 0.18;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.394) * (radius * 0.65), lift + 0.41, Math.sin(spin + 0.394) * (radius * 0.65));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0084F, 164, 80, 255, alpha * 0.264F);
         if (((int)(time + 20.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.034F, 255, 200, 255, alpha * 0.3F);
         }

         if (((int)(time + 20.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.864, 0.009F, 164, 80, 255, alpha * 0.076F, 24
            );
         }

         return wrote;
      }

      private static boolean layer21(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1273);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2357);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3399);
         double spin = time * 0.021 + h0 * Math.PI * 2.0;
         double radius = 0.64 + h1 * 0.98;
         double lift = 0.895 + Math.sin(time * 0.021 + h2 * 6.28318) * 0.205;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.425) * (radius * 0.685), lift + 0.2, Math.sin(spin + 0.425) * (radius * 0.685));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0101F, 187, 97, 255, alpha * 0.276F);
         if (((int)(time + 21.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.038F, 255, 207, 255, alpha * 0.325F);
         }

         if (((int)(time + 21.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.882, 0.0105F, 187, 97, 255, alpha * 0.084F, 32
            );
         }

         return wrote;
      }

      private static boolean layer22(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1286);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2374);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3418);
         double spin = time * 0.024 + h0 * Math.PI * 2.0;
         double radius = 0.695 + h1 * 0.58;
         double lift = 0.99 + Math.sin(time * 0.025 + h2 * 6.28318) * 0.23;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.456) * (radius * 0.72), lift + 0.235, Math.sin(spin + 0.456) * (radius * 0.72));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0118F, 210, 114, 255, alpha * 0.288F);
         if (((int)(time + 22.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.042F, 255, 214, 255, alpha * 0.35F);
         }

         if (((int)(time + 22.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.9, 0.012F, 210, 114, 255, alpha * 0.092F, 40
            );
         }

         return wrote;
      }

      private static boolean layer23(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1299);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2391);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3437);
         double spin = time * 0.027 + h0 * Math.PI * 2.0;
         double radius = 0.75 + h1 * 0.62;
         double lift = -1.1 + Math.sin(time * 0.029 + h2 * 6.28318) * 0.255;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.487) * (radius * 0.755), lift + 0.27, Math.sin(spin + 0.487) * (radius * 0.755));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0135F, 233, 35, 255, alpha * 0.3F);
         if (((int)(time + 23.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.046F, 255, 221, 255, alpha * 0.375F);
         }

         if (((int)(time + 23.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.918, 0.0135F, 233, 35, 255, alpha * 0.1F, 48
            );
         }

         return wrote;
      }

      private static boolean layer24(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1312);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2408);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3456);
         double spin = time * 0.03 + h0 * Math.PI * 2.0;
         double radius = 0.805 + h1 * 0.66;
         double lift = -1.005 + Math.sin(time * 0.033 + h2 * 6.28318) * 0.28;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.27) * (radius * 0.58), lift + 0.305, Math.sin(spin + 0.27) * (radius * 0.58));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0152F, 118, 52, 255, alpha * 0.312F);
         if (((int)(time + 24.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.018F, 252, 82, 255, alpha * 0.4F);
         }

         if (((int)(time + 24.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.72, 0.006F, 118, 52, 255, alpha * 0.108F, 56
            );
         }

         return wrote;
      }

      private static boolean layer25(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1325);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2425);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3475);
         double spin = time * 0.033 + h0 * Math.PI * 2.0;
         double radius = 0.86 + h1 * 0.7;
         double lift = -0.91 + Math.sin(time * 0.037 + h2 * 6.28318) * 0.18;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.301) * (radius * 0.615), lift + 0.34, Math.sin(spin + 0.301) * (radius * 0.615));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0169F, 141, 69, 255, alpha * 0.324F);
         if (((int)(time + 25.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.022F, 118, 238, 255, alpha * 0.425F);
         }

         if (((int)(time + 25.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.738, 0.0075F, 141, 69, 255, alpha * 0.116F, 24
            );
         }

         return wrote;
      }

      private static boolean layer26(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1338);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2442);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3494);
         double spin = time * 0.036 + h0 * Math.PI * 2.0;
         double radius = 0.915 + h1 * 0.74;
         double lift = -0.815 + Math.sin(time * 0.041 + h2 * 6.28318) * 0.205;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.332) * (radius * 0.65), lift + 0.375, Math.sin(spin + 0.332) * (radius * 0.65));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0186F, 164, 86, 255, alpha * 0.18F);
         if (((int)(time + 26.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.026F, 255, 242, 255, alpha * 0.45F);
         }

         if (((int)(time + 26.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.756, 0.009F, 164, 86, 255, alpha * 0.124F, 32
            );
         }

         return wrote;
      }

      private static boolean layer27(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1351);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2459);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3513);
         double spin = time * 0.012 + h0 * Math.PI * 2.0;
         double radius = 0.97 + h1 * 0.78;
         double lift = -0.72 + Math.sin(time * 0.045 + h2 * 6.28318) * 0.23;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.363) * (radius * 0.685), lift + 0.41, Math.sin(spin + 0.363) * (radius * 0.685));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.005F, 187, 103, 255, alpha * 0.192F);
         if (((int)(time + 27.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.03F, 255, 249, 255, alpha * 0.475F);
         }

         if (((int)(time + 27.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.774, 0.0105F, 187, 103, 255, alpha * 0.06F, 40
            );
         }

         return wrote;
      }

      private static boolean layer28(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1364);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2476);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3532);
         double spin = time * 0.015 + h0 * Math.PI * 2.0;
         double radius = 1.025 + h1 * 0.82;
         double lift = -0.625 + Math.sin(time * 0.021 + h2 * 6.28318) * 0.255;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.394) * (radius * 0.72), lift + 0.2, Math.sin(spin + 0.394) * (radius * 0.72));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0067F, 210, 120, 255, alpha * 0.204F);
         if (((int)(time + 28.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.034F, 255, 191, 255, alpha * 0.5F);
         }

         if (((int)(time + 28.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.792, 0.012F, 210, 120, 255, alpha * 0.068F, 48
            );
         }

         return wrote;
      }

      private static boolean layer29(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1377);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2493);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3551);
         double spin = time * 0.018 + h0 * Math.PI * 2.0;
         double radius = 1.08 + h1 * 0.86;
         double lift = -0.53 + Math.sin(time * 0.025 + h2 * 6.28318) * 0.28;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.425) * (radius * 0.755), lift + 0.235, Math.sin(spin + 0.425) * (radius * 0.755));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0084F, 233, 41, 255, alpha * 0.216F);
         if (((int)(time + 29.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.038F, 255, 198, 255, alpha * 0.525F);
         }

         if (((int)(time + 29.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.81, 0.0135F, 233, 41, 255, alpha * 0.076F, 56
            );
         }

         return wrote;
      }

      private static boolean layer30(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1390);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2510);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3570);
         double spin = time * 0.021 + h0 * Math.PI * 2.0;
         double radius = 1.135 + h1 * 0.9;
         double lift = -0.435 + Math.sin(time * 0.029 + h2 * 6.28318) * 0.18;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.456) * (radius * 0.58), lift + 0.27, Math.sin(spin + 0.456) * (radius * 0.58));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0101F, 118, 58, 255, alpha * 0.228F);
         if (((int)(time + 30.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.042F, 255, 205, 255, alpha * 0.3F);
         }

         if (((int)(time + 30.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.828, 0.006F, 118, 58, 255, alpha * 0.084F, 24
            );
         }

         return wrote;
      }

      private static boolean layer31(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1403);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2527);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3589);
         double spin = time * 0.024 + h0 * Math.PI * 2.0;
         double radius = 1.19 + h1 * 0.94;
         double lift = -0.34 + Math.sin(time * 0.033 + h2 * 6.28318) * 0.205;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.487) * (radius * 0.615), lift + 0.305, Math.sin(spin + 0.487) * (radius * 0.615));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0118F, 141, 75, 255, alpha * 0.24F);
         if (((int)(time + 31.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.046F, 255, 212, 255, alpha * 0.325F);
         }

         if (((int)(time + 31.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.846, 0.0075F, 141, 75, 255, alpha * 0.092F, 32
            );
         }

         return wrote;
      }

      private static boolean layer32(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1416);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2544);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3608);
         double spin = time * 0.027 + h0 * Math.PI * 2.0;
         double radius = 1.245 + h1 * 0.98;
         double lift = -0.245 + Math.sin(time * 0.037 + h2 * 6.28318) * 0.23;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.27) * (radius * 0.65), lift + 0.34, Math.sin(spin + 0.27) * (radius * 0.65));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0135F, 164, 92, 255, alpha * 0.252F);
         if (((int)(time + 32.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.018F, 255, 219, 255, alpha * 0.35F);
         }

         if (((int)(time + 32.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.864, 0.009F, 164, 92, 255, alpha * 0.1F, 40
            );
         }

         return wrote;
      }

      private static boolean layer33(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1429);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2561);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3627);
         double spin = time * 0.03 + h0 * Math.PI * 2.0;
         double radius = 1.3 + h1 * 0.58;
         double lift = -0.15 + Math.sin(time * 0.041 + h2 * 6.28318) * 0.255;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.301) * (radius * 0.685), lift + 0.375, Math.sin(spin + 0.301) * (radius * 0.685));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0152F, 187, 109, 255, alpha * 0.264F);
         if (((int)(time + 33.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.022F, 96, 222, 255, alpha * 0.375F);
         }

         if (((int)(time + 33.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.882, 0.0105F, 187, 109, 255, alpha * 0.108F, 48
            );
         }

         return wrote;
      }

      private static boolean layer34(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1442);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2578);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3646);
         double spin = time * 0.033 + h0 * Math.PI * 2.0;
         double radius = 0.42 + h1 * 0.62;
         double lift = -0.055 + Math.sin(time * 0.045 + h2 * 6.28318) * 0.28;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.332) * (radius * 0.72), lift + 0.41, Math.sin(spin + 0.332) * (radius * 0.72));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0169F, 210, 30, 255, alpha * 0.276F);
         if (((int)(time + 34.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.026F, 255, 233, 255, alpha * 0.4F);
         }

         if (((int)(time + 34.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.9, 0.012F, 210, 30, 255, alpha * 0.116F, 56
            );
         }

         return wrote;
      }

      private static boolean layer35(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1455);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2595);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3665);
         double spin = time * 0.036 + h0 * Math.PI * 2.0;
         double radius = 0.475 + h1 * 0.66;
         double lift = 0.04 + Math.sin(time * 0.021 + h2 * 6.28318) * 0.18;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.363) * (radius * 0.755), lift + 0.2, Math.sin(spin + 0.363) * (radius * 0.755));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0186F, 233, 47, 255, alpha * 0.288F);
         if (((int)(time + 35.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.03F, 255, 70, 230, alpha * 0.425F);
         }

         if (((int)(time + 35.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.918, 0.0135F, 233, 47, 255, alpha * 0.124F, 24
            );
         }

         return wrote;
      }

      private static boolean layer36(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1468);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2612);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3684);
         double spin = time * 0.012 + h0 * Math.PI * 2.0;
         double radius = 0.53 + h1 * 0.7;
         double lift = 0.135 + Math.sin(time * 0.025 + h2 * 6.28318) * 0.205;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.394) * (radius * 0.58), lift + 0.235, Math.sin(spin + 0.394) * (radius * 0.58));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.005F, 118, 64, 255, alpha * 0.3F);
         if (((int)(time + 36.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.034F, 255, 247, 255, alpha * 0.45F);
         }

         if (((int)(time + 36.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.72, 0.006F, 118, 64, 255, alpha * 0.06F, 32
            );
         }

         return wrote;
      }

      private static boolean layer37(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1481);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2629);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3703);
         double spin = time * 0.015 + h0 * Math.PI * 2.0;
         double radius = 0.585 + h1 * 0.74;
         double lift = 0.23 + Math.sin(time * 0.029 + h2 * 6.28318) * 0.23;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.425) * (radius * 0.615), lift + 0.27, Math.sin(spin + 0.425) * (radius * 0.615));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0067F, 141, 81, 255, alpha * 0.312F);
         if (((int)(time + 37.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.038F, 255, 254, 255, alpha * 0.475F);
         }

         if (((int)(time + 37.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.738, 0.0075F, 141, 81, 255, alpha * 0.068F, 40
            );
         }

         return wrote;
      }

      private static boolean layer38(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1494);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2646);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3722);
         double spin = time * 0.018 + h0 * Math.PI * 2.0;
         double radius = 0.64 + h1 * 0.78;
         double lift = 0.325 + Math.sin(time * 0.033 + h2 * 6.28318) * 0.255;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.456) * (radius * 0.65), lift + 0.305, Math.sin(spin + 0.456) * (radius * 0.65));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0084F, 164, 98, 255, alpha * 0.324F);
         if (((int)(time + 38.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.042F, 255, 196, 255, alpha * 0.5F);
         }

         if (((int)(time + 38.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.756, 0.009F, 164, 98, 255, alpha * 0.076F, 48
            );
         }

         return wrote;
      }

      private static boolean layer39(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1507);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2663);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3741);
         double spin = time * 0.021 + h0 * Math.PI * 2.0;
         double radius = 0.695 + h1 * 0.82;
         double lift = 0.42 + Math.sin(time * 0.037 + h2 * 6.28318) * 0.28;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.487) * (radius * 0.685), lift + 0.34, Math.sin(spin + 0.487) * (radius * 0.685));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0101F, 187, 115, 255, alpha * 0.18F);
         if (((int)(time + 39.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.046F, 255, 203, 255, alpha * 0.525F);
         }

         if (((int)(time + 39.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.774, 0.0105F, 187, 115, 255, alpha * 0.084F, 56
            );
         }

         return wrote;
      }

      private static boolean layer40(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1520);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2680);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3760);
         double spin = time * 0.024 + h0 * Math.PI * 2.0;
         double radius = 0.75 + h1 * 0.86;
         double lift = 0.515 + Math.sin(time * 0.041 + h2 * 6.28318) * 0.18;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.27) * (radius * 0.72), lift + 0.375, Math.sin(spin + 0.27) * (radius * 0.72));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0118F, 210, 36, 255, alpha * 0.192F);
         if (((int)(time + 40.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.018F, 255, 210, 255, alpha * 0.3F);
         }

         if (((int)(time + 40.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.792, 0.012F, 210, 36, 255, alpha * 0.092F, 24
            );
         }

         return wrote;
      }

      private static boolean layer41(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1533);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2697);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3779);
         double spin = time * 0.027 + h0 * Math.PI * 2.0;
         double radius = 0.805 + h1 * 0.9;
         double lift = 0.61 + Math.sin(time * 0.045 + h2 * 6.28318) * 0.205;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.301) * (radius * 0.755), lift + 0.41, Math.sin(spin + 0.301) * (radius * 0.755));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0135F, 233, 53, 255, alpha * 0.204F);
         if (((int)(time + 41.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.022F, 255, 217, 255, alpha * 0.325F);
         }

         if (((int)(time + 41.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.81, 0.0135F, 233, 53, 255, alpha * 0.1F, 32
            );
         }

         return wrote;
      }

      private static boolean layer42(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1546);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2714);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3798);
         double spin = time * 0.03 + h0 * Math.PI * 2.0;
         double radius = 0.86 + h1 * 0.94;
         double lift = 0.705 + Math.sin(time * 0.021 + h2 * 6.28318) * 0.23;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.332) * (radius * 0.58), lift + 0.2, Math.sin(spin + 0.332) * (radius * 0.58));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0152F, 118, 70, 255, alpha * 0.216F);
         if (((int)(time + 42.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.026F, 85, 226, 255, alpha * 0.35F);
         }

         if (((int)(time + 42.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.828, 0.006F, 118, 70, 255, alpha * 0.108F, 40
            );
         }

         return wrote;
      }

      private static boolean layer43(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1559);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2731);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3817);
         double spin = time * 0.033 + h0 * Math.PI * 2.0;
         double radius = 0.915 + h1 * 0.98;
         double lift = 0.8 + Math.sin(time * 0.025 + h2 * 6.28318) * 0.255;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.363) * (radius * 0.615), lift + 0.235, Math.sin(spin + 0.363) * (radius * 0.615));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0169F, 141, 87, 255, alpha * 0.228F);
         if (((int)(time + 43.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.03F, 255, 231, 255, alpha * 0.375F);
         }

         if (((int)(time + 43.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.846, 0.0075F, 141, 87, 255, alpha * 0.116F, 48
            );
         }

         return wrote;
      }

      private static boolean layer44(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1572);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2748);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3836);
         double spin = time * 0.036 + h0 * Math.PI * 2.0;
         double radius = 0.97 + h1 * 0.58;
         double lift = 0.895 + Math.sin(time * 0.029 + h2 * 6.28318) * 0.28;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.394) * (radius * 0.65), lift + 0.27, Math.sin(spin + 0.394) * (radius * 0.65));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0186F, 164, 104, 255, alpha * 0.24F);
         if (((int)(time + 44.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.034F, 116, 224, 255, alpha * 0.4F);
         }

         if (((int)(time + 44.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.864, 0.009F, 164, 104, 255, alpha * 0.124F, 56
            );
         }

         return wrote;
      }

      private static boolean layer45(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1585);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2765);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3855);
         double spin = time * 0.012 + h0 * Math.PI * 2.0;
         double radius = 1.025 + h1 * 0.62;
         double lift = 0.99 + Math.sin(time * 0.033 + h2 * 6.28318) * 0.18;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.425) * (radius * 0.685), lift + 0.305, Math.sin(spin + 0.425) * (radius * 0.685));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.005F, 187, 121, 255, alpha * 0.252F);
         if (((int)(time + 45.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.038F, 86, 235, 255, alpha * 0.425F);
         }

         if (((int)(time + 45.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.882, 0.0105F, 187, 121, 255, alpha * 0.06F, 24
            );
         }

         return wrote;
      }

      private static boolean layer46(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1598);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2782);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3874);
         double spin = time * 0.015 + h0 * Math.PI * 2.0;
         double radius = 1.08 + h1 * 0.66;
         double lift = -1.1 + Math.sin(time * 0.037 + h2 * 6.28318) * 0.205;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.456) * (radius * 0.72), lift + 0.34, Math.sin(spin + 0.456) * (radius * 0.72));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0067F, 210, 42, 255, alpha * 0.264F);
         if (((int)(time + 46.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.042F, 255, 252, 255, alpha * 0.45F);
         }

         if (((int)(time + 46.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.9, 0.012F, 210, 42, 255, alpha * 0.068F, 32
            );
         }

         return wrote;
      }

      private static boolean layer47(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1611);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2799);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3893);
         double spin = time * 0.018 + h0 * Math.PI * 2.0;
         double radius = 1.135 + h1 * 0.7;
         double lift = -1.005 + Math.sin(time * 0.041 + h2 * 6.28318) * 0.23;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.487) * (radius * 0.755), lift + 0.375, Math.sin(spin + 0.487) * (radius * 0.755));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0084F, 233, 59, 255, alpha * 0.276F);
         if (((int)(time + 47.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.046F, 255, 194, 255, alpha * 0.475F);
         }

         if (((int)(time + 47.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.918, 0.0135F, 233, 59, 255, alpha * 0.076F, 40
            );
         }

         return wrote;
      }

      private static boolean layer48(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1624);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2816);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3912);
         double spin = time * 0.021 + h0 * Math.PI * 2.0;
         double radius = 1.19 + h1 * 0.74;
         double lift = -0.91 + Math.sin(time * 0.045 + h2 * 6.28318) * 0.255;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.27) * (radius * 0.58), lift + 0.41, Math.sin(spin + 0.27) * (radius * 0.58));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0101F, 118, 76, 255, alpha * 0.288F);
         if (((int)(time + 48.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.018F, 255, 201, 255, alpha * 0.5F);
         }

         if (((int)(time + 48.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.72, 0.006F, 118, 76, 255, alpha * 0.084F, 48
            );
         }

         return wrote;
      }

      private static boolean layer49(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1637);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2833);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3931);
         double spin = time * 0.024 + h0 * Math.PI * 2.0;
         double radius = 1.245 + h1 * 0.78;
         double lift = -0.815 + Math.sin(time * 0.021 + h2 * 6.28318) * 0.28;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.301) * (radius * 0.615), lift + 0.2, Math.sin(spin + 0.301) * (radius * 0.615));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0118F, 141, 93, 255, alpha * 0.3F);
         if (((int)(time + 49.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.022F, 255, 208, 255, alpha * 0.525F);
         }

         if (((int)(time + 49.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.738, 0.0075F, 141, 93, 255, alpha * 0.092F, 56
            );
         }

         return wrote;
      }

      private static boolean layer50(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1650);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2850);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3950);
         double spin = time * 0.027 + h0 * Math.PI * 2.0;
         double radius = 1.3 + h1 * 0.82;
         double lift = -0.72 + Math.sin(time * 0.025 + h2 * 6.28318) * 0.18;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.332) * (radius * 0.65), lift + 0.235, Math.sin(spin + 0.332) * (radius * 0.65));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0135F, 164, 110, 255, alpha * 0.312F);
         if (((int)(time + 50.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.026F, 255, 215, 255, alpha * 0.3F);
         }

         if (((int)(time + 50.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.756, 0.009F, 164, 110, 255, alpha * 0.1F, 24
            );
         }

         return wrote;
      }

      private static boolean layer51(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1663);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2867);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3969);
         double spin = time * 0.03 + h0 * Math.PI * 2.0;
         double radius = 0.42 + h1 * 0.86;
         double lift = -0.625 + Math.sin(time * 0.029 + h2 * 6.28318) * 0.205;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.363) * (radius * 0.685), lift + 0.27, Math.sin(spin + 0.363) * (radius * 0.685));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0152F, 187, 31, 255, alpha * 0.324F);
         if (((int)(time + 51.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.03F, 255, 222, 255, alpha * 0.325F);
         }

         if (((int)(time + 51.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.774, 0.0105F, 187, 31, 255, alpha * 0.108F, 32
            );
         }

         return wrote;
      }

      private static boolean layer52(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1676);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2884);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 3988);
         double spin = time * 0.033 + h0 * Math.PI * 2.0;
         double radius = 0.475 + h1 * 0.9;
         double lift = -0.53 + Math.sin(time * 0.033 + h2 * 6.28318) * 0.23;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.394) * (radius * 0.72), lift + 0.305, Math.sin(spin + 0.394) * (radius * 0.72));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0169F, 210, 48, 255, alpha * 0.18F);
         if (((int)(time + 52.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.034F, 255, 229, 255, alpha * 0.35F);
         }

         if (((int)(time + 52.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.792, 0.012F, 210, 48, 255, alpha * 0.116F, 40
            );
         }

         return wrote;
      }

      private static boolean layer53(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1689);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2901);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4007);
         double spin = time * 0.036 + h0 * Math.PI * 2.0;
         double radius = 0.53 + h1 * 0.94;
         double lift = -0.435 + Math.sin(time * 0.037 + h2 * 6.28318) * 0.255;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.425) * (radius * 0.755), lift + 0.34, Math.sin(spin + 0.425) * (radius * 0.755));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0186F, 233, 65, 255, alpha * 0.192F);
         if (((int)(time + 53.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.038F, 255, 112, 236, alpha * 0.375F);
         }

         if (((int)(time + 53.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.81, 0.0135F, 233, 65, 255, alpha * 0.124F, 48
            );
         }

         return wrote;
      }

      private static boolean layer54(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1702);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2918);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4026);
         double spin = time * 0.012 + h0 * Math.PI * 2.0;
         double radius = 0.585 + h1 * 0.98;
         double lift = -0.34 + Math.sin(time * 0.041 + h2 * 6.28318) * 0.28;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.456) * (radius * 0.58), lift + 0.375, Math.sin(spin + 0.456) * (radius * 0.58));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.005F, 118, 82, 255, alpha * 0.204F);
         if (((int)(time + 54.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.042F, 255, 243, 255, alpha * 0.4F);
         }

         if (((int)(time + 54.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.828, 0.006F, 118, 82, 255, alpha * 0.06F, 56
            );
         }

         return wrote;
      }

      private static boolean layer55(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1715);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2935);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4045);
         double spin = time * 0.015 + h0 * Math.PI * 2.0;
         double radius = 0.64 + h1 * 0.58;
         double lift = -0.245 + Math.sin(time * 0.045 + h2 * 6.28318) * 0.18;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.487) * (radius * 0.615), lift + 0.41, Math.sin(spin + 0.487) * (radius * 0.615));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0067F, 141, 99, 255, alpha * 0.216F);
         if (((int)(time + 55.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.046F, 255, 250, 255, alpha * 0.425F);
         }

         if (((int)(time + 55.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.846, 0.0075F, 141, 99, 255, alpha * 0.068F, 24
            );
         }

         return wrote;
      }

      private static boolean layer56(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1728);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2952);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4064);
         double spin = time * 0.018 + h0 * Math.PI * 2.0;
         double radius = 0.695 + h1 * 0.62;
         double lift = -0.15 + Math.sin(time * 0.021 + h2 * 6.28318) * 0.205;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.27) * (radius * 0.65), lift + 0.2, Math.sin(spin + 0.27) * (radius * 0.65));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0084F, 164, 116, 255, alpha * 0.228F);
         if (((int)(time + 56.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.018F, 255, 192, 255, alpha * 0.45F);
         }

         if (((int)(time + 56.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.864, 0.009F, 164, 116, 255, alpha * 0.076F, 32
            );
         }

         return wrote;
      }

      private static boolean layer57(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1741);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2969);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4083);
         double spin = time * 0.021 + h0 * Math.PI * 2.0;
         double radius = 0.75 + h1 * 0.66;
         double lift = -0.055 + Math.sin(time * 0.025 + h2 * 6.28318) * 0.23;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.301) * (radius * 0.685), lift + 0.235, Math.sin(spin + 0.301) * (radius * 0.685));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0101F, 187, 37, 255, alpha * 0.24F);
         if (((int)(time + 57.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.022F, 255, 199, 255, alpha * 0.475F);
         }

         if (((int)(time + 57.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.882, 0.0105F, 187, 37, 255, alpha * 0.084F, 40
            );
         }

         return wrote;
      }

      private static boolean layer58(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1754);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 2986);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4102);
         double spin = time * 0.024 + h0 * Math.PI * 2.0;
         double radius = 0.805 + h1 * 0.7;
         double lift = 0.04 + Math.sin(time * 0.029 + h2 * 6.28318) * 0.255;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.332) * (radius * 0.72), lift + 0.27, Math.sin(spin + 0.332) * (radius * 0.72));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0118F, 210, 54, 255, alpha * 0.252F);
         if (((int)(time + 58.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.026F, 255, 206, 255, alpha * 0.5F);
         }

         if (((int)(time + 58.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.9, 0.012F, 210, 54, 255, alpha * 0.092F, 48
            );
         }

         return wrote;
      }

      private static boolean layer59(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1767);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3003);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4121);
         double spin = time * 0.027 + h0 * Math.PI * 2.0;
         double radius = 0.86 + h1 * 0.74;
         double lift = 0.135 + Math.sin(time * 0.033 + h2 * 6.28318) * 0.28;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.363) * (radius * 0.755), lift + 0.305, Math.sin(spin + 0.363) * (radius * 0.755));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0135F, 233, 71, 255, alpha * 0.264F);
         if (((int)(time + 59.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.03F, 255, 213, 255, alpha * 0.525F);
         }

         if (((int)(time + 59.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.918, 0.0135F, 233, 71, 255, alpha * 0.1F, 56
            );
         }

         return wrote;
      }

      private static boolean layer60(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1780);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3020);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4140);
         double spin = time * 0.03 + h0 * Math.PI * 2.0;
         double radius = 0.915 + h1 * 0.78;
         double lift = 0.23 + Math.sin(time * 0.037 + h2 * 6.28318) * 0.18;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.394) * (radius * 0.58), lift + 0.34, Math.sin(spin + 0.394) * (radius * 0.58));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0152F, 118, 88, 255, alpha * 0.276F);
         if (((int)(time + 60.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.034F, 255, 80, 228, alpha * 0.3F);
         }

         if (((int)(time + 60.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.72, 0.006F, 118, 88, 255, alpha * 0.108F, 24
            );
         }

         return wrote;
      }

      private static boolean layer61(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1793);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3037);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4159);
         double spin = time * 0.033 + h0 * Math.PI * 2.0;
         double radius = 0.97 + h1 * 0.82;
         double lift = 0.325 + Math.sin(time * 0.041 + h2 * 6.28318) * 0.205;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.425) * (radius * 0.615), lift + 0.375, Math.sin(spin + 0.425) * (radius * 0.615));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0169F, 141, 105, 255, alpha * 0.288F);
         if (((int)(time + 61.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.038F, 255, 227, 255, alpha * 0.325F);
         }

         if (((int)(time + 61.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.738, 0.0075F, 141, 105, 255, alpha * 0.116F, 32
            );
         }

         return wrote;
      }

      private static boolean layer62(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1806);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3054);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4178);
         double spin = time * 0.036 + h0 * Math.PI * 2.0;
         double radius = 1.025 + h1 * 0.86;
         double lift = 0.42 + Math.sin(time * 0.045 + h2 * 6.28318) * 0.23;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.456) * (radius * 0.65), lift + 0.41, Math.sin(spin + 0.456) * (radius * 0.65));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0186F, 164, 122, 255, alpha * 0.3F);
         if (((int)(time + 62.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.042F, 255, 234, 255, alpha * 0.35F);
         }

         if (((int)(time + 62.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.756, 0.009F, 164, 122, 255, alpha * 0.124F, 40
            );
         }

         return wrote;
      }

      private static boolean layer63(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1819);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3071);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4197);
         double spin = time * 0.012 + h0 * Math.PI * 2.0;
         double radius = 1.08 + h1 * 0.9;
         double lift = 0.515 + Math.sin(time * 0.021 + h2 * 6.28318) * 0.255;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.487) * (radius * 0.685), lift + 0.2, Math.sin(spin + 0.487) * (radius * 0.685));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.005F, 187, 43, 255, alpha * 0.312F);
         if (((int)(time + 63.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.046F, 255, 241, 255, alpha * 0.375F);
         }

         if (((int)(time + 63.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.774, 0.0105F, 187, 43, 255, alpha * 0.06F, 48
            );
         }

         return wrote;
      }

      private static boolean layer64(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1832);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3088);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4216);
         double spin = time * 0.015 + h0 * Math.PI * 2.0;
         double radius = 1.135 + h1 * 0.94;
         double lift = 0.61 + Math.sin(time * 0.025 + h2 * 6.28318) * 0.28;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.27) * (radius * 0.72), lift + 0.235, Math.sin(spin + 0.27) * (radius * 0.72));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0067F, 210, 60, 255, alpha * 0.324F);
         if (((int)(time + 64.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.018F, 255, 248, 255, alpha * 0.4F);
         }

         if (((int)(time + 64.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.792, 0.012F, 210, 60, 255, alpha * 0.068F, 56
            );
         }

         return wrote;
      }

      private static boolean layer65(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1845);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3105);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4235);
         double spin = time * 0.018 + h0 * Math.PI * 2.0;
         double radius = 1.19 + h1 * 0.98;
         double lift = 0.705 + Math.sin(time * 0.029 + h2 * 6.28318) * 0.18;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.301) * (radius * 0.755), lift + 0.27, Math.sin(spin + 0.301) * (radius * 0.755));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0084F, 233, 77, 255, alpha * 0.18F);
         if (((int)(time + 65.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.022F, 255, 66, 235, alpha * 0.425F);
         }

         if (((int)(time + 65.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.81, 0.0135F, 233, 77, 255, alpha * 0.076F, 24
            );
         }

         return wrote;
      }

      private static boolean layer66(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1858);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3122);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4254);
         double spin = time * 0.021 + h0 * Math.PI * 2.0;
         double radius = 1.245 + h1 * 0.58;
         double lift = 0.8 + Math.sin(time * 0.033 + h2 * 6.28318) * 0.205;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.332) * (radius * 0.58), lift + 0.305, Math.sin(spin + 0.332) * (radius * 0.58));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0101F, 118, 94, 255, alpha * 0.192F);
         if (((int)(time + 66.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.026F, 82, 220, 255, alpha * 0.45F);
         }

         if (((int)(time + 66.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.828, 0.006F, 118, 94, 255, alpha * 0.084F, 32
            );
         }

         return wrote;
      }

      private static boolean layer67(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1871);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3139);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4273);
         double spin = time * 0.024 + h0 * Math.PI * 2.0;
         double radius = 1.3 + h1 * 0.62;
         double lift = 0.895 + Math.sin(time * 0.037 + h2 * 6.28318) * 0.23;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.363) * (radius * 0.615), lift + 0.34, Math.sin(spin + 0.363) * (radius * 0.615));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0118F, 141, 111, 255, alpha * 0.204F);
         if (((int)(time + 67.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.03F, 246, 66, 255, alpha * 0.475F);
         }

         if (((int)(time + 67.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.846, 0.0075F, 141, 111, 255, alpha * 0.092F, 40
            );
         }

         return wrote;
      }

      private static boolean layer68(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1884);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3156);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4292);
         double spin = time * 0.027 + h0 * Math.PI * 2.0;
         double radius = 0.42 + h1 * 0.66;
         double lift = 0.99 + Math.sin(time * 0.041 + h2 * 6.28318) * 0.255;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.394) * (radius * 0.65), lift + 0.375, Math.sin(spin + 0.394) * (radius * 0.65));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0135F, 164, 32, 255, alpha * 0.216F);
         if (((int)(time + 68.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.034F, 96, 230, 255, alpha * 0.5F);
         }

         if (((int)(time + 68.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.864, 0.009F, 164, 32, 255, alpha * 0.1F, 48
            );
         }

         return wrote;
      }

      private static boolean layer69(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1897);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3173);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4311);
         double spin = time * 0.03 + h0 * Math.PI * 2.0;
         double radius = 0.475 + h1 * 0.7;
         double lift = -1.1 + Math.sin(time * 0.045 + h2 * 6.28318) * 0.28;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.425) * (radius * 0.685), lift + 0.41, Math.sin(spin + 0.425) * (radius * 0.685));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0152F, 187, 49, 255, alpha * 0.228F);
         if (((int)(time + 69.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.038F, 110, 215, 255, alpha * 0.525F);
         }

         if (((int)(time + 69.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.882, 0.0105F, 187, 49, 255, alpha * 0.108F, 56
            );
         }

         return wrote;
      }

      private static boolean layer70(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1910);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3190);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4330);
         double spin = time * 0.033 + h0 * Math.PI * 2.0;
         double radius = 0.53 + h1 * 0.74;
         double lift = -1.005 + Math.sin(time * 0.021 + h2 * 6.28318) * 0.18;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.456) * (radius * 0.72), lift + 0.2, Math.sin(spin + 0.456) * (radius * 0.72));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0169F, 210, 66, 255, alpha * 0.24F);
         if (((int)(time + 70.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.042F, 255, 96, 230, alpha * 0.3F);
         }

         if (((int)(time + 70.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.9, 0.012F, 210, 66, 255, alpha * 0.116F, 24
            );
         }

         return wrote;
      }

      private static boolean layer71(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1923);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3207);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4349);
         double spin = time * 0.036 + h0 * Math.PI * 2.0;
         double radius = 0.585 + h1 * 0.78;
         double lift = -0.91 + Math.sin(time * 0.025 + h2 * 6.28318) * 0.205;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.487) * (radius * 0.755), lift + 0.235, Math.sin(spin + 0.487) * (radius * 0.755));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0186F, 233, 83, 255, alpha * 0.252F);
         if (((int)(time + 71.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.046F, 255, 86, 238, alpha * 0.325F);
         }

         if (((int)(time + 71.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.918, 0.0135F, 233, 83, 255, alpha * 0.124F, 32
            );
         }

         return wrote;
      }

      private static boolean layer72(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1936);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3224);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4368);
         double spin = time * 0.012 + h0 * Math.PI * 2.0;
         double radius = 0.64 + h1 * 0.82;
         double lift = -0.815 + Math.sin(time * 0.029 + h2 * 6.28318) * 0.23;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.27) * (radius * 0.58), lift + 0.27, Math.sin(spin + 0.27) * (radius * 0.58));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.005F, 118, 100, 255, alpha * 0.264F);
         if (((int)(time + 72.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.018F, 255, 239, 255, alpha * 0.35F);
         }

         if (((int)(time + 72.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.72, 0.006F, 118, 100, 255, alpha * 0.06F, 40
            );
         }

         return wrote;
      }

      private static boolean layer73(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1949);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3241);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4387);
         double spin = time * 0.015 + h0 * Math.PI * 2.0;
         double radius = 0.695 + h1 * 0.86;
         double lift = -0.72 + Math.sin(time * 0.033 + h2 * 6.28318) * 0.255;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.301) * (radius * 0.615), lift + 0.305, Math.sin(spin + 0.301) * (radius * 0.615));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0067F, 141, 117, 255, alpha * 0.276F);
         if (((int)(time + 73.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.022F, 255, 246, 255, alpha * 0.375F);
         }

         if (((int)(time + 73.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.738, 0.0075F, 141, 117, 255, alpha * 0.068F, 48
            );
         }

         return wrote;
      }

      private static boolean layer74(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1962);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3258);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4406);
         double spin = time * 0.018 + h0 * Math.PI * 2.0;
         double radius = 0.75 + h1 * 0.9;
         double lift = -0.625 + Math.sin(time * 0.037 + h2 * 6.28318) * 0.28;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.332) * (radius * 0.65), lift + 0.34, Math.sin(spin + 0.332) * (radius * 0.65));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0084F, 164, 38, 255, alpha * 0.288F);
         if (((int)(time + 74.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.026F, 255, 253, 255, alpha * 0.4F);
         }

         if (((int)(time + 74.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.756, 0.009F, 164, 38, 255, alpha * 0.076F, 56
            );
         }

         return wrote;
      }

      private static boolean layer75(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1975);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3275);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4425);
         double spin = time * 0.021 + h0 * Math.PI * 2.0;
         double radius = 0.805 + h1 * 0.94;
         double lift = -0.53 + Math.sin(time * 0.041 + h2 * 6.28318) * 0.18;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.363) * (radius * 0.685), lift + 0.375, Math.sin(spin + 0.363) * (radius * 0.685));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0101F, 187, 55, 255, alpha * 0.3F);
         if (((int)(time + 75.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.03F, 255, 195, 255, alpha * 0.425F);
         }

         if (((int)(time + 75.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.774, 0.0105F, 187, 55, 255, alpha * 0.084F, 24
            );
         }

         return wrote;
      }

      private static boolean layer76(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 1988);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3292);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4444);
         double spin = time * 0.024 + h0 * Math.PI * 2.0;
         double radius = 0.86 + h1 * 0.98;
         double lift = -0.435 + Math.sin(time * 0.045 + h2 * 6.28318) * 0.205;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.394) * (radius * 0.72), lift + 0.41, Math.sin(spin + 0.394) * (radius * 0.72));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0118F, 210, 72, 255, alpha * 0.312F);
         if (((int)(time + 76.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.034F, 255, 202, 255, alpha * 0.45F);
         }

         if (((int)(time + 76.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.792, 0.012F, 210, 72, 255, alpha * 0.092F, 32
            );
         }

         return wrote;
      }

      private static boolean layer77(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2001);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3309);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4463);
         double spin = time * 0.027 + h0 * Math.PI * 2.0;
         double radius = 0.915 + h1 * 0.58;
         double lift = -0.34 + Math.sin(time * 0.021 + h2 * 6.28318) * 0.23;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.425) * (radius * 0.755), lift + 0.2, Math.sin(spin + 0.425) * (radius * 0.755));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0135F, 233, 89, 255, alpha * 0.324F);
         if (((int)(time + 77.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.038F, 255, 209, 255, alpha * 0.475F);
         }

         if (((int)(time + 77.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.81, 0.0135F, 233, 89, 255, alpha * 0.1F, 40
            );
         }

         return wrote;
      }

      private static boolean layer78(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2014);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3326);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4482);
         double spin = time * 0.03 + h0 * Math.PI * 2.0;
         double radius = 0.97 + h1 * 0.62;
         double lift = -0.245 + Math.sin(time * 0.025 + h2 * 6.28318) * 0.255;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.456) * (radius * 0.58), lift + 0.235, Math.sin(spin + 0.456) * (radius * 0.58));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0152F, 118, 106, 255, alpha * 0.18F);
         if (((int)(time + 78.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.042F, 255, 76, 240, alpha * 0.5F);
         }

         if (((int)(time + 78.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.828, 0.006F, 118, 106, 255, alpha * 0.108F, 48
            );
         }

         return wrote;
      }

      private static boolean layer79(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2027);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3343);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4501);
         double spin = time * 0.033 + h0 * Math.PI * 2.0;
         double radius = 1.025 + h1 * 0.66;
         double lift = -0.15 + Math.sin(time * 0.029 + h2 * 6.28318) * 0.28;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.487) * (radius * 0.615), lift + 0.27, Math.sin(spin + 0.487) * (radius * 0.615));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0169F, 141, 123, 255, alpha * 0.192F);
         if (((int)(time + 79.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.046F, 255, 223, 255, alpha * 0.525F);
         }

         if (((int)(time + 79.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.846, 0.0075F, 141, 123, 255, alpha * 0.116F, 56
            );
         }

         return wrote;
      }

      private static boolean layer80(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2040);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3360);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4520);
         double spin = time * 0.036 + h0 * Math.PI * 2.0;
         double radius = 1.08 + h1 * 0.7;
         double lift = -0.055 + Math.sin(time * 0.033 + h2 * 6.28318) * 0.18;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.27) * (radius * 0.65), lift + 0.305, Math.sin(spin + 0.27) * (radius * 0.65));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0186F, 164, 44, 255, alpha * 0.204F);
         if (((int)(time + 80.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.018F, 112, 232, 255, alpha * 0.3F);
         }

         if (((int)(time + 80.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.864, 0.009F, 164, 44, 255, alpha * 0.124F, 24
            );
         }

         return wrote;
      }

      private static boolean layer81(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2053);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3377);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4539);
         double spin = time * 0.012 + h0 * Math.PI * 2.0;
         double radius = 1.135 + h1 * 0.74;
         double lift = 0.04 + Math.sin(time * 0.037 + h2 * 6.28318) * 0.205;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.301) * (radius * 0.685), lift + 0.34, Math.sin(spin + 0.301) * (radius * 0.685));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.005F, 187, 61, 255, alpha * 0.216F);
         if (((int)(time + 81.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.022F, 255, 237, 255, alpha * 0.325F);
         }

         if (((int)(time + 81.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.882, 0.0105F, 187, 61, 255, alpha * 0.06F, 32
            );
         }

         return wrote;
      }

      private static boolean layer82(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2066);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3394);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4558);
         double spin = time * 0.015 + h0 * Math.PI * 2.0;
         double radius = 1.19 + h1 * 0.78;
         double lift = 0.135 + Math.sin(time * 0.041 + h2 * 6.28318) * 0.23;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.332) * (radius * 0.72), lift + 0.375, Math.sin(spin + 0.332) * (radius * 0.72));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0067F, 210, 78, 255, alpha * 0.228F);
         if (((int)(time + 82.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.026F, 255, 94, 232, alpha * 0.35F);
         }

         if (((int)(time + 82.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.9, 0.012F, 210, 78, 255, alpha * 0.068F, 40
            );
         }

         return wrote;
      }

      private static boolean layer83(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2079);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3411);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4577);
         double spin = time * 0.018 + h0 * Math.PI * 2.0;
         double radius = 1.245 + h1 * 0.82;
         double lift = 0.23 + Math.sin(time * 0.045 + h2 * 6.28318) * 0.255;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.363) * (radius * 0.755), lift + 0.41, Math.sin(spin + 0.363) * (radius * 0.755));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0084F, 233, 95, 255, alpha * 0.24F);
         if (((int)(time + 83.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.03F, 255, 251, 255, alpha * 0.375F);
         }

         if (((int)(time + 83.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.918, 0.0135F, 233, 95, 255, alpha * 0.076F, 48
            );
         }

         return wrote;
      }

      private static boolean layer84(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2092);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3428);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4596);
         double spin = time * 0.021 + h0 * Math.PI * 2.0;
         double radius = 1.3 + h1 * 0.86;
         double lift = 0.325 + Math.sin(time * 0.021 + h2 * 6.28318) * 0.28;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.394) * (radius * 0.58), lift + 0.2, Math.sin(spin + 0.394) * (radius * 0.58));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0101F, 118, 112, 255, alpha * 0.252F);
         if (((int)(time + 84.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.034F, 255, 193, 255, alpha * 0.4F);
         }

         if (((int)(time + 84.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.72, 0.006F, 118, 112, 255, alpha * 0.084F, 56
            );
         }

         return wrote;
      }

      private static boolean layer85(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2105);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3445);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4615);
         double spin = time * 0.024 + h0 * Math.PI * 2.0;
         double radius = 0.42 + h1 * 0.9;
         double lift = 0.42 + Math.sin(time * 0.025 + h2 * 6.28318) * 0.18;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.425) * (radius * 0.615), lift + 0.235, Math.sin(spin + 0.425) * (radius * 0.615));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0118F, 141, 33, 255, alpha * 0.264F);
         if (((int)(time + 85.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.038F, 255, 200, 255, alpha * 0.425F);
         }

         if (((int)(time + 85.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.738, 0.0075F, 141, 33, 255, alpha * 0.092F, 24
            );
         }

         return wrote;
      }

      private static boolean layer86(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2118);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3462);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4634);
         double spin = time * 0.027 + h0 * Math.PI * 2.0;
         double radius = 0.475 + h1 * 0.94;
         double lift = 0.515 + Math.sin(time * 0.029 + h2 * 6.28318) * 0.205;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.456) * (radius * 0.65), lift + 0.27, Math.sin(spin + 0.456) * (radius * 0.65));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0135F, 164, 50, 255, alpha * 0.276F);
         if (((int)(time + 86.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.042F, 255, 207, 255, alpha * 0.45F);
         }

         if (((int)(time + 86.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.756, 0.009F, 164, 50, 255, alpha * 0.1F, 32
            );
         }

         return wrote;
      }

      private static boolean layer87(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2131);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3479);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4653);
         double spin = time * 0.03 + h0 * Math.PI * 2.0;
         double radius = 0.53 + h1 * 0.98;
         double lift = 0.61 + Math.sin(time * 0.033 + h2 * 6.28318) * 0.23;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.487) * (radius * 0.685), lift + 0.305, Math.sin(spin + 0.487) * (radius * 0.685));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0152F, 187, 67, 255, alpha * 0.288F);
         if (((int)(time + 87.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.046F, 255, 214, 255, alpha * 0.475F);
         }

         if (((int)(time + 87.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.774, 0.0105F, 187, 67, 255, alpha * 0.108F, 40
            );
         }

         return wrote;
      }

      private static boolean layer88(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2144);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3496);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4672);
         double spin = time * 0.033 + h0 * Math.PI * 2.0;
         double radius = 0.585 + h1 * 0.58;
         double lift = 0.705 + Math.sin(time * 0.037 + h2 * 6.28318) * 0.255;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.27) * (radius * 0.72), lift + 0.34, Math.sin(spin + 0.27) * (radius * 0.72));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0169F, 210, 84, 255, alpha * 0.3F);
         if (((int)(time + 88.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.018F, 255, 221, 255, alpha * 0.5F);
         }

         if (((int)(time + 88.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.792, 0.012F, 210, 84, 255, alpha * 0.116F, 48
            );
         }

         return wrote;
      }

      private static boolean layer89(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2157);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3513);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4691);
         double spin = time * 0.036 + h0 * Math.PI * 2.0;
         double radius = 0.64 + h1 * 0.62;
         double lift = 0.8 + Math.sin(time * 0.041 + h2 * 6.28318) * 0.28;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.301) * (radius * 0.755), lift + 0.375, Math.sin(spin + 0.301) * (radius * 0.755));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0186F, 233, 101, 255, alpha * 0.312F);
         if (((int)(time + 89.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.022F, 252, 82, 255, alpha * 0.525F);
         }

         if (((int)(time + 89.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.81, 0.0135F, 233, 101, 255, alpha * 0.124F, 56
            );
         }

         return wrote;
      }

      private static boolean layer90(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2170);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3530);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4710);
         double spin = time * 0.012 + h0 * Math.PI * 2.0;
         double radius = 0.695 + h1 * 0.66;
         double lift = 0.895 + Math.sin(time * 0.045 + h2 * 6.28318) * 0.18;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.332) * (radius * 0.58), lift + 0.41, Math.sin(spin + 0.332) * (radius * 0.58));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.005F, 118, 118, 255, alpha * 0.324F);
         if (((int)(time + 90.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.026F, 118, 238, 255, alpha * 0.3F);
         }

         if (((int)(time + 90.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.828, 0.006F, 118, 118, 255, alpha * 0.06F, 24
            );
         }

         return wrote;
      }

      private static boolean layer91(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2183);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3547);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4729);
         double spin = time * 0.015 + h0 * Math.PI * 2.0;
         double radius = 0.75 + h1 * 0.7;
         double lift = 0.99 + Math.sin(time * 0.021 + h2 * 6.28318) * 0.205;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.363) * (radius * 0.615), lift + 0.2, Math.sin(spin + 0.363) * (radius * 0.615));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0067F, 141, 39, 255, alpha * 0.18F);
         if (((int)(time + 91.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.03F, 255, 242, 255, alpha * 0.325F);
         }

         if (((int)(time + 91.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.846, 0.0075F, 141, 39, 255, alpha * 0.068F, 32
            );
         }

         return wrote;
      }

      private static boolean layer92(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2196);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3564);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4748);
         double spin = time * 0.018 + h0 * Math.PI * 2.0;
         double radius = 0.805 + h1 * 0.74;
         double lift = -1.1 + Math.sin(time * 0.025 + h2 * 6.28318) * 0.23;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.394) * (radius * 0.65), lift + 0.235, Math.sin(spin + 0.394) * (radius * 0.65));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0084F, 164, 56, 255, alpha * 0.192F);
         if (((int)(time + 92.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.034F, 255, 249, 255, alpha * 0.35F);
         }

         if (((int)(time + 92.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.864, 0.009F, 164, 56, 255, alpha * 0.076F, 40
            );
         }

         return wrote;
      }

      private static boolean layer93(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2209);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3581);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4767);
         double spin = time * 0.021 + h0 * Math.PI * 2.0;
         double radius = 0.86 + h1 * 0.78;
         double lift = -1.005 + Math.sin(time * 0.029 + h2 * 6.28318) * 0.255;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.425) * (radius * 0.685), lift + 0.27, Math.sin(spin + 0.425) * (radius * 0.685));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0101F, 187, 73, 255, alpha * 0.204F);
         if (((int)(time + 93.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.038F, 255, 191, 255, alpha * 0.375F);
         }

         if (((int)(time + 93.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.882, 0.0105F, 187, 73, 255, alpha * 0.084F, 48
            );
         }

         return wrote;
      }

      private static boolean layer94(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2222);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3598);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4786);
         double spin = time * 0.024 + h0 * Math.PI * 2.0;
         double radius = 0.915 + h1 * 0.82;
         double lift = -0.91 + Math.sin(time * 0.033 + h2 * 6.28318) * 0.28;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.456) * (radius * 0.72), lift + 0.305, Math.sin(spin + 0.456) * (radius * 0.72));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0118F, 210, 90, 255, alpha * 0.216F);
         if (((int)(time + 94.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.042F, 255, 198, 255, alpha * 0.4F);
         }

         if (((int)(time + 94.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.9, 0.012F, 210, 90, 255, alpha * 0.092F, 56
            );
         }

         return wrote;
      }

      private static boolean layer95(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2235);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3615);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4805);
         double spin = time * 0.027 + h0 * Math.PI * 2.0;
         double radius = 0.97 + h1 * 0.86;
         double lift = -0.815 + Math.sin(time * 0.037 + h2 * 6.28318) * 0.18;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.487) * (radius * 0.755), lift + 0.34, Math.sin(spin + 0.487) * (radius * 0.755));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0135F, 233, 107, 255, alpha * 0.228F);
         if (((int)(time + 95.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.046F, 255, 205, 255, alpha * 0.425F);
         }

         if (((int)(time + 95.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.918, 0.0135F, 233, 107, 255, alpha * 0.1F, 24
            );
         }

         return wrote;
      }

      private static boolean layer96(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2248);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3632);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4824);
         double spin = time * 0.03 + h0 * Math.PI * 2.0;
         double radius = 1.025 + h1 * 0.9;
         double lift = -0.72 + Math.sin(time * 0.041 + h2 * 6.28318) * 0.205;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.27) * (radius * 0.58), lift + 0.375, Math.sin(spin + 0.27) * (radius * 0.58));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0152F, 118, 28, 255, alpha * 0.24F);
         if (((int)(time + 96.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.018F, 255, 212, 255, alpha * 0.45F);
         }

         if (((int)(time + 96.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.72, 0.006F, 118, 28, 255, alpha * 0.108F, 32
            );
         }

         return wrote;
      }

      private static boolean layer97(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2261);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3649);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4843);
         double spin = time * 0.033 + h0 * Math.PI * 2.0;
         double radius = 1.08 + h1 * 0.94;
         double lift = -0.625 + Math.sin(time * 0.045 + h2 * 6.28318) * 0.23;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.301) * (radius * 0.615), lift + 0.41, Math.sin(spin + 0.301) * (radius * 0.615));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0169F, 141, 45, 255, alpha * 0.252F);
         if (((int)(time + 97.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.022F, 255, 219, 255, alpha * 0.475F);
         }

         if (((int)(time + 97.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.738, 0.0075F, 141, 45, 255, alpha * 0.116F, 40
            );
         }

         return wrote;
      }

      private static boolean layer98(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2274);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3666);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4862);
         double spin = time * 0.036 + h0 * Math.PI * 2.0;
         double radius = 1.135 + h1 * 0.98;
         double lift = -0.53 + Math.sin(time * 0.021 + h2 * 6.28318) * 0.255;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.332) * (radius * 0.65), lift + 0.2, Math.sin(spin + 0.332) * (radius * 0.65));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0186F, 164, 62, 255, alpha * 0.264F);
         if (((int)(time + 98.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.026F, 96, 222, 255, alpha * 0.5F);
         }

         if (((int)(time + 98.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.756, 0.009F, 164, 62, 255, alpha * 0.124F, 48
            );
         }

         return wrote;
      }

      private static boolean layer99(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2287);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3683);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4881);
         double spin = time * 0.012 + h0 * Math.PI * 2.0;
         double radius = 1.19 + h1 * 0.58;
         double lift = -0.435 + Math.sin(time * 0.025 + h2 * 6.28318) * 0.28;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.363) * (radius * 0.685), lift + 0.235, Math.sin(spin + 0.363) * (radius * 0.685));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.005F, 187, 79, 255, alpha * 0.276F);
         if (((int)(time + 99.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.03F, 255, 233, 255, alpha * 0.525F);
         }

         if (((int)(time + 99.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.774, 0.0105F, 187, 79, 255, alpha * 0.06F, 56
            );
         }

         return wrote;
      }

      private static boolean layer100(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2300);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3700);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4900);
         double spin = time * 0.015 + h0 * Math.PI * 2.0;
         double radius = 1.245 + h1 * 0.62;
         double lift = -0.34 + Math.sin(time * 0.029 + h2 * 6.28318) * 0.18;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.394) * (radius * 0.72), lift + 0.27, Math.sin(spin + 0.394) * (radius * 0.72));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0067F, 210, 96, 255, alpha * 0.288F);
         if (((int)(time + 100.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.034F, 255, 70, 230, alpha * 0.3F);
         }

         if (((int)(time + 100.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.792, 0.012F, 210, 96, 255, alpha * 0.068F, 24
            );
         }

         return wrote;
      }

      private static boolean layer101(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2313);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3717);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4919);
         double spin = time * 0.018 + h0 * Math.PI * 2.0;
         double radius = 1.3 + h1 * 0.66;
         double lift = -0.245 + Math.sin(time * 0.033 + h2 * 6.28318) * 0.205;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.425) * (radius * 0.755), lift + 0.305, Math.sin(spin + 0.425) * (radius * 0.755));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0084F, 233, 113, 255, alpha * 0.3F);
         if (((int)(time + 101.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.038F, 255, 247, 255, alpha * 0.325F);
         }

         if (((int)(time + 101.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.81, 0.0135F, 233, 113, 255, alpha * 0.076F, 32
            );
         }

         return wrote;
      }

      private static boolean layer102(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2326);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3734);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4938);
         double spin = time * 0.021 + h0 * Math.PI * 2.0;
         double radius = 0.42 + h1 * 0.7;
         double lift = -0.15 + Math.sin(time * 0.037 + h2 * 6.28318) * 0.23;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.456) * (radius * 0.58), lift + 0.34, Math.sin(spin + 0.456) * (radius * 0.58));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0101F, 118, 34, 255, alpha * 0.312F);
         if (((int)(time + 102.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.042F, 255, 254, 255, alpha * 0.35F);
         }

         if (((int)(time + 102.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.828, 0.006F, 118, 34, 255, alpha * 0.084F, 40
            );
         }

         return wrote;
      }

      private static boolean layer103(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2339);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3751);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4957);
         double spin = time * 0.024 + h0 * Math.PI * 2.0;
         double radius = 0.475 + h1 * 0.74;
         double lift = -0.055 + Math.sin(time * 0.041 + h2 * 6.28318) * 0.255;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.487) * (radius * 0.615), lift + 0.375, Math.sin(spin + 0.487) * (radius * 0.615));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0118F, 141, 51, 255, alpha * 0.324F);
         if (((int)(time + 103.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.046F, 255, 196, 255, alpha * 0.375F);
         }

         if (((int)(time + 103.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.846, 0.0075F, 141, 51, 255, alpha * 0.092F, 48
            );
         }

         return wrote;
      }

      private static boolean layer104(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2352);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3768);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4976);
         double spin = time * 0.027 + h0 * Math.PI * 2.0;
         double radius = 0.53 + h1 * 0.78;
         double lift = 0.04 + Math.sin(time * 0.045 + h2 * 6.28318) * 0.28;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.27) * (radius * 0.65), lift + 0.41, Math.sin(spin + 0.27) * (radius * 0.65));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0135F, 164, 68, 255, alpha * 0.18F);
         if (((int)(time + 104.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.018F, 255, 203, 255, alpha * 0.4F);
         }

         if (((int)(time + 104.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.864, 0.009F, 164, 68, 255, alpha * 0.1F, 56
            );
         }

         return wrote;
      }

      private static boolean layer105(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2365);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3785);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 4995);
         double spin = time * 0.03 + h0 * Math.PI * 2.0;
         double radius = 0.585 + h1 * 0.82;
         double lift = 0.135 + Math.sin(time * 0.021 + h2 * 6.28318) * 0.18;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.301) * (radius * 0.685), lift + 0.2, Math.sin(spin + 0.301) * (radius * 0.685));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0152F, 187, 85, 255, alpha * 0.192F);
         if (((int)(time + 105.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.022F, 255, 210, 255, alpha * 0.425F);
         }

         if (((int)(time + 105.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.882, 0.0105F, 187, 85, 255, alpha * 0.108F, 24
            );
         }

         return wrote;
      }

      private static boolean layer106(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2378);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3802);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5014);
         double spin = time * 0.033 + h0 * Math.PI * 2.0;
         double radius = 0.64 + h1 * 0.86;
         double lift = 0.23 + Math.sin(time * 0.025 + h2 * 6.28318) * 0.205;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.332) * (radius * 0.72), lift + 0.235, Math.sin(spin + 0.332) * (radius * 0.72));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0169F, 210, 102, 255, alpha * 0.204F);
         if (((int)(time + 106.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.026F, 255, 217, 255, alpha * 0.45F);
         }

         if (((int)(time + 106.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.9, 0.012F, 210, 102, 255, alpha * 0.116F, 32
            );
         }

         return wrote;
      }

      private static boolean layer107(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2391);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3819);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5033);
         double spin = time * 0.036 + h0 * Math.PI * 2.0;
         double radius = 0.695 + h1 * 0.9;
         double lift = 0.325 + Math.sin(time * 0.029 + h2 * 6.28318) * 0.23;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.363) * (radius * 0.755), lift + 0.27, Math.sin(spin + 0.363) * (radius * 0.755));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0186F, 233, 119, 255, alpha * 0.216F);
         if (((int)(time + 107.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.03F, 85, 226, 255, alpha * 0.475F);
         }

         if (((int)(time + 107.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.918, 0.0135F, 233, 119, 255, alpha * 0.124F, 40
            );
         }

         return wrote;
      }

      private static boolean layer108(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2404);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3836);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5052);
         double spin = time * 0.012 + h0 * Math.PI * 2.0;
         double radius = 0.75 + h1 * 0.94;
         double lift = 0.42 + Math.sin(time * 0.033 + h2 * 6.28318) * 0.255;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.394) * (radius * 0.58), lift + 0.305, Math.sin(spin + 0.394) * (radius * 0.58));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.005F, 118, 40, 255, alpha * 0.228F);
         if (((int)(time + 108.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.034F, 255, 231, 255, alpha * 0.5F);
         }

         if (((int)(time + 108.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.72, 0.006F, 118, 40, 255, alpha * 0.06F, 48
            );
         }

         return wrote;
      }

      private static boolean layer109(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2417);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3853);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5071);
         double spin = time * 0.015 + h0 * Math.PI * 2.0;
         double radius = 0.805 + h1 * 0.98;
         double lift = 0.515 + Math.sin(time * 0.037 + h2 * 6.28318) * 0.28;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.425) * (radius * 0.615), lift + 0.34, Math.sin(spin + 0.425) * (radius * 0.615));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0067F, 141, 57, 255, alpha * 0.24F);
         if (((int)(time + 109.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.038F, 116, 224, 255, alpha * 0.525F);
         }

         if (((int)(time + 109.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.738, 0.0075F, 141, 57, 255, alpha * 0.068F, 56
            );
         }

         return wrote;
      }

      private static boolean layer110(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2430);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3870);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5090);
         double spin = time * 0.018 + h0 * Math.PI * 2.0;
         double radius = 0.86 + h1 * 0.58;
         double lift = 0.61 + Math.sin(time * 0.041 + h2 * 6.28318) * 0.18;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.456) * (radius * 0.65), lift + 0.375, Math.sin(spin + 0.456) * (radius * 0.65));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0084F, 164, 74, 255, alpha * 0.252F);
         if (((int)(time + 110.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.042F, 86, 235, 255, alpha * 0.3F);
         }

         if (((int)(time + 110.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.756, 0.009F, 164, 74, 255, alpha * 0.076F, 24
            );
         }

         return wrote;
      }

      private static boolean layer111(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2443);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3887);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5109);
         double spin = time * 0.021 + h0 * Math.PI * 2.0;
         double radius = 0.915 + h1 * 0.62;
         double lift = 0.705 + Math.sin(time * 0.045 + h2 * 6.28318) * 0.205;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.487) * (radius * 0.685), lift + 0.41, Math.sin(spin + 0.487) * (radius * 0.685));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0101F, 187, 91, 255, alpha * 0.264F);
         if (((int)(time + 111.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.046F, 255, 252, 255, alpha * 0.325F);
         }

         if (((int)(time + 111.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.774, 0.0105F, 187, 91, 255, alpha * 0.084F, 32
            );
         }

         return wrote;
      }

      private static boolean layer112(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2456);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3904);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5128);
         double spin = time * 0.024 + h0 * Math.PI * 2.0;
         double radius = 0.97 + h1 * 0.66;
         double lift = 0.8 + Math.sin(time * 0.021 + h2 * 6.28318) * 0.23;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.27) * (radius * 0.72), lift + 0.2, Math.sin(spin + 0.27) * (radius * 0.72));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0118F, 210, 108, 255, alpha * 0.276F);
         if (((int)(time + 112.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.018F, 255, 194, 255, alpha * 0.35F);
         }

         if (((int)(time + 112.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.792, 0.012F, 210, 108, 255, alpha * 0.092F, 40
            );
         }

         return wrote;
      }

      private static boolean layer113(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2469);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3921);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5147);
         double spin = time * 0.027 + h0 * Math.PI * 2.0;
         double radius = 1.025 + h1 * 0.7;
         double lift = 0.895 + Math.sin(time * 0.025 + h2 * 6.28318) * 0.255;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.301) * (radius * 0.755), lift + 0.235, Math.sin(spin + 0.301) * (radius * 0.755));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0135F, 233, 29, 255, alpha * 0.288F);
         if (((int)(time + 113.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.022F, 255, 201, 255, alpha * 0.375F);
         }

         if (((int)(time + 113.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.81, 0.0135F, 233, 29, 255, alpha * 0.1F, 48
            );
         }

         return wrote;
      }

      private static boolean layer114(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2482);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3938);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5166);
         double spin = time * 0.03 + h0 * Math.PI * 2.0;
         double radius = 1.08 + h1 * 0.74;
         double lift = 0.99 + Math.sin(time * 0.029 + h2 * 6.28318) * 0.28;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.332) * (radius * 0.58), lift + 0.27, Math.sin(spin + 0.332) * (radius * 0.58));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0152F, 118, 46, 255, alpha * 0.3F);
         if (((int)(time + 114.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.026F, 255, 208, 255, alpha * 0.4F);
         }

         if (((int)(time + 114.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.828, 0.006F, 118, 46, 255, alpha * 0.108F, 56
            );
         }

         return wrote;
      }

      private static boolean layer115(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2495);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3955);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5185);
         double spin = time * 0.033 + h0 * Math.PI * 2.0;
         double radius = 1.135 + h1 * 0.78;
         double lift = -1.1 + Math.sin(time * 0.033 + h2 * 6.28318) * 0.18;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.363) * (radius * 0.615), lift + 0.305, Math.sin(spin + 0.363) * (radius * 0.615));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0169F, 141, 63, 255, alpha * 0.312F);
         if (((int)(time + 115.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.03F, 255, 215, 255, alpha * 0.425F);
         }

         if (((int)(time + 115.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.846, 0.0075F, 141, 63, 255, alpha * 0.116F, 24
            );
         }

         return wrote;
      }

      private static boolean layer116(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2508);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3972);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5204);
         double spin = time * 0.036 + h0 * Math.PI * 2.0;
         double radius = 1.19 + h1 * 0.82;
         double lift = -1.005 + Math.sin(time * 0.037 + h2 * 6.28318) * 0.205;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.394) * (radius * 0.65), lift + 0.34, Math.sin(spin + 0.394) * (radius * 0.65));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0186F, 164, 80, 255, alpha * 0.324F);
         if (((int)(time + 116.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.034F, 255, 222, 255, alpha * 0.45F);
         }

         if (((int)(time + 116.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.864, 0.009F, 164, 80, 255, alpha * 0.124F, 32
            );
         }

         return wrote;
      }

      private static boolean layer117(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2521);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 3989);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5223);
         double spin = time * 0.012 + h0 * Math.PI * 2.0;
         double radius = 1.245 + h1 * 0.86;
         double lift = -0.91 + Math.sin(time * 0.041 + h2 * 6.28318) * 0.23;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.425) * (radius * 0.685), lift + 0.375, Math.sin(spin + 0.425) * (radius * 0.685));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.005F, 187, 97, 255, alpha * 0.18F);
         if (((int)(time + 117.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.038F, 255, 229, 255, alpha * 0.475F);
         }

         if (((int)(time + 117.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.882, 0.0105F, 187, 97, 255, alpha * 0.06F, 40
            );
         }

         return wrote;
      }

      private static boolean layer118(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2534);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 4006);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5242);
         double spin = time * 0.015 + h0 * Math.PI * 2.0;
         double radius = 1.3 + h1 * 0.9;
         double lift = -0.815 + Math.sin(time * 0.045 + h2 * 6.28318) * 0.255;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.456) * (radius * 0.72), lift + 0.41, Math.sin(spin + 0.456) * (radius * 0.72));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0067F, 210, 114, 255, alpha * 0.192F);
         if (((int)(time + 118.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.042F, 255, 112, 236, alpha * 0.5F);
         }

         if (((int)(time + 118.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.9, 0.012F, 210, 114, 255, alpha * 0.068F, 48
            );
         }

         return wrote;
      }

      private static boolean layer119(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2547);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 4023);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5261);
         double spin = time * 0.018 + h0 * Math.PI * 2.0;
         double radius = 0.42 + h1 * 0.94;
         double lift = -0.72 + Math.sin(time * 0.021 + h2 * 6.28318) * 0.28;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.487) * (radius * 0.755), lift + 0.2, Math.sin(spin + 0.487) * (radius * 0.755));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0084F, 233, 35, 255, alpha * 0.204F);
         if (((int)(time + 119.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.046F, 255, 243, 255, alpha * 0.525F);
         }

         if (((int)(time + 119.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.918, 0.0135F, 233, 35, 255, alpha * 0.076F, 56
            );
         }

         return wrote;
      }

      private static boolean layer120(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2560);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 4040);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5280);
         double spin = time * 0.021 + h0 * Math.PI * 2.0;
         double radius = 0.475 + h1 * 0.98;
         double lift = -0.625 + Math.sin(time * 0.025 + h2 * 6.28318) * 0.18;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.27) * (radius * 0.58), lift + 0.235, Math.sin(spin + 0.27) * (radius * 0.58));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0101F, 118, 52, 255, alpha * 0.216F);
         if (((int)(time + 120.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.018F, 255, 250, 255, alpha * 0.3F);
         }

         if (((int)(time + 120.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.72, 0.006F, 118, 52, 255, alpha * 0.084F, 24
            );
         }

         return wrote;
      }

      private static boolean layer121(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2573);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 4057);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5299);
         double spin = time * 0.024 + h0 * Math.PI * 2.0;
         double radius = 0.53 + h1 * 0.58;
         double lift = -0.53 + Math.sin(time * 0.029 + h2 * 6.28318) * 0.205;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.301) * (radius * 0.615), lift + 0.27, Math.sin(spin + 0.301) * (radius * 0.615));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0118F, 141, 69, 255, alpha * 0.228F);
         if (((int)(time + 121.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.022F, 255, 192, 255, alpha * 0.325F);
         }

         if (((int)(time + 121.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.738, 0.0075F, 141, 69, 255, alpha * 0.092F, 32
            );
         }

         return wrote;
      }

      private static boolean layer122(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2586);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 4074);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5318);
         double spin = time * 0.027 + h0 * Math.PI * 2.0;
         double radius = 0.585 + h1 * 0.62;
         double lift = -0.435 + Math.sin(time * 0.033 + h2 * 6.28318) * 0.23;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.332) * (radius * 0.65), lift + 0.305, Math.sin(spin + 0.332) * (radius * 0.65));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0135F, 164, 86, 255, alpha * 0.24F);
         if (((int)(time + 122.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.026F, 255, 199, 255, alpha * 0.35F);
         }

         if (((int)(time + 122.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.756, 0.009F, 164, 86, 255, alpha * 0.1F, 40
            );
         }

         return wrote;
      }

      private static boolean layer123(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2599);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 4091);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5337);
         double spin = time * 0.03 + h0 * Math.PI * 2.0;
         double radius = 0.64 + h1 * 0.66;
         double lift = -0.34 + Math.sin(time * 0.037 + h2 * 6.28318) * 0.255;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.363) * (radius * 0.685), lift + 0.34, Math.sin(spin + 0.363) * (radius * 0.685));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0152F, 187, 103, 255, alpha * 0.252F);
         if (((int)(time + 123.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.03F, 255, 206, 255, alpha * 0.375F);
         }

         if (((int)(time + 123.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.774, 0.0105F, 187, 103, 255, alpha * 0.108F, 48
            );
         }

         return wrote;
      }

      private static boolean layer124(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2612);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 4108);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5356);
         double spin = time * 0.033 + h0 * Math.PI * 2.0;
         double radius = 0.695 + h1 * 0.7;
         double lift = -0.245 + Math.sin(time * 0.041 + h2 * 6.28318) * 0.28;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.394) * (radius * 0.72), lift + 0.375, Math.sin(spin + 0.394) * (radius * 0.72));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0169F, 210, 120, 255, alpha * 0.264F);
         if (((int)(time + 124.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.034F, 255, 213, 255, alpha * 0.4F);
         }

         if (((int)(time + 124.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.792, 0.012F, 210, 120, 255, alpha * 0.116F, 56
            );
         }

         return wrote;
      }

      private static boolean layer125(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2625);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 4125);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5375);
         double spin = time * 0.036 + h0 * Math.PI * 2.0;
         double radius = 0.75 + h1 * 0.74;
         double lift = -0.15 + Math.sin(time * 0.045 + h2 * 6.28318) * 0.18;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.425) * (radius * 0.755), lift + 0.41, Math.sin(spin + 0.425) * (radius * 0.755));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0186F, 233, 41, 255, alpha * 0.276F);
         if (((int)(time + 125.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.038F, 255, 80, 228, alpha * 0.425F);
         }

         if (((int)(time + 125.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.81, 0.0135F, 233, 41, 255, alpha * 0.124F, 24
            );
         }

         return wrote;
      }

      private static boolean layer126(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2638);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 4142);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5394);
         double spin = time * 0.012 + h0 * Math.PI * 2.0;
         double radius = 0.805 + h1 * 0.78;
         double lift = -0.055 + Math.sin(time * 0.021 + h2 * 6.28318) * 0.205;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.456) * (radius * 0.58), lift + 0.2, Math.sin(spin + 0.456) * (radius * 0.58));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.005F, 118, 58, 255, alpha * 0.288F);
         if (((int)(time + 126.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.042F, 255, 227, 255, alpha * 0.45F);
         }

         if (((int)(time + 126.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.828, 0.006F, 118, 58, 255, alpha * 0.06F, 32
            );
         }

         return wrote;
      }

      private static boolean layer127(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2651);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 4159);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5413);
         double spin = time * 0.015 + h0 * Math.PI * 2.0;
         double radius = 0.86 + h1 * 0.82;
         double lift = 0.04 + Math.sin(time * 0.025 + h2 * 6.28318) * 0.23;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.487) * (radius * 0.615), lift + 0.235, Math.sin(spin + 0.487) * (radius * 0.615));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0067F, 141, 75, 255, alpha * 0.3F);
         if (((int)(time + 127.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.046F, 255, 234, 255, alpha * 0.475F);
         }

         if (((int)(time + 127.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.846, 0.0075F, 141, 75, 255, alpha * 0.068F, 40
            );
         }

         return wrote;
      }

      private static boolean layer128(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2664);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 4176);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5432);
         double spin = time * 0.018 + h0 * Math.PI * 2.0;
         double radius = 0.915 + h1 * 0.86;
         double lift = 0.135 + Math.sin(time * 0.029 + h2 * 6.28318) * 0.255;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.27) * (radius * 0.65), lift + 0.27, Math.sin(spin + 0.27) * (radius * 0.65));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0084F, 164, 92, 255, alpha * 0.312F);
         if (((int)(time + 128.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.018F, 255, 241, 255, alpha * 0.5F);
         }

         if (((int)(time + 128.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.864, 0.009F, 164, 92, 255, alpha * 0.076F, 48
            );
         }

         return wrote;
      }

      private static boolean layer129(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2677);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 4193);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5451);
         double spin = time * 0.021 + h0 * Math.PI * 2.0;
         double radius = 0.97 + h1 * 0.9;
         double lift = 0.23 + Math.sin(time * 0.033 + h2 * 6.28318) * 0.28;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.301) * (radius * 0.685), lift + 0.305, Math.sin(spin + 0.301) * (radius * 0.685));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0101F, 187, 109, 255, alpha * 0.324F);
         if (((int)(time + 129.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.022F, 255, 248, 255, alpha * 0.525F);
         }

         if (((int)(time + 129.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.882, 0.0105F, 187, 109, 255, alpha * 0.084F, 56
            );
         }

         return wrote;
      }

      private static boolean layer130(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2690);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 4210);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5470);
         double spin = time * 0.024 + h0 * Math.PI * 2.0;
         double radius = 1.025 + h1 * 0.94;
         double lift = 0.325 + Math.sin(time * 0.037 + h2 * 6.28318) * 0.18;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.332) * (radius * 0.72), lift + 0.34, Math.sin(spin + 0.332) * (radius * 0.72));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0118F, 210, 30, 255, alpha * 0.18F);
         if (((int)(time + 130.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.026F, 255, 66, 235, alpha * 0.3F);
         }

         if (((int)(time + 130.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.9, 0.012F, 210, 30, 255, alpha * 0.092F, 24
            );
         }

         return wrote;
      }

      private static boolean layer131(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2703);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 4227);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5489);
         double spin = time * 0.027 + h0 * Math.PI * 2.0;
         double radius = 1.08 + h1 * 0.98;
         double lift = 0.42 + Math.sin(time * 0.041 + h2 * 6.28318) * 0.205;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.363) * (radius * 0.755), lift + 0.375, Math.sin(spin + 0.363) * (radius * 0.755));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0135F, 233, 47, 255, alpha * 0.192F);
         if (((int)(time + 131.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.03F, 82, 220, 255, alpha * 0.325F);
         }

         if (((int)(time + 131.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.918, 0.0135F, 233, 47, 255, alpha * 0.1F, 32
            );
         }

         return wrote;
      }

      private static boolean layer132(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2716);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 4244);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5508);
         double spin = time * 0.03 + h0 * Math.PI * 2.0;
         double radius = 1.135 + h1 * 0.58;
         double lift = 0.515 + Math.sin(time * 0.045 + h2 * 6.28318) * 0.23;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.394) * (radius * 0.58), lift + 0.41, Math.sin(spin + 0.394) * (radius * 0.58));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0152F, 118, 64, 255, alpha * 0.204F);
         if (((int)(time + 132.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.034F, 246, 66, 255, alpha * 0.35F);
         }

         if (((int)(time + 132.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.72, 0.006F, 118, 64, 255, alpha * 0.108F, 40
            );
         }

         return wrote;
      }

      private static boolean layer133(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2729);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 4261);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5527);
         double spin = time * 0.033 + h0 * Math.PI * 2.0;
         double radius = 1.19 + h1 * 0.62;
         double lift = 0.61 + Math.sin(time * 0.021 + h2 * 6.28318) * 0.255;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.425) * (radius * 0.615), lift + 0.2, Math.sin(spin + 0.425) * (radius * 0.615));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0169F, 141, 81, 255, alpha * 0.216F);
         if (((int)(time + 133.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.038F, 96, 230, 255, alpha * 0.375F);
         }

         if (((int)(time + 133.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.738, 0.0075F, 141, 81, 255, alpha * 0.116F, 48
            );
         }

         return wrote;
      }

      private static boolean layer134(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2742);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 4278);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5546);
         double spin = time * 0.036 + h0 * Math.PI * 2.0;
         double radius = 1.245 + h1 * 0.66;
         double lift = 0.705 + Math.sin(time * 0.025 + h2 * 6.28318) * 0.28;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.456) * (radius * 0.65), lift + 0.235, Math.sin(spin + 0.456) * (radius * 0.65));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0186F, 164, 98, 255, alpha * 0.228F);
         if (((int)(time + 134.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.042F, 110, 215, 255, alpha * 0.4F);
         }

         if (((int)(time + 134.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.756, 0.009F, 164, 98, 255, alpha * 0.124F, 56
            );
         }

         return wrote;
      }

      private static boolean layer135(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2755);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 4295);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5565);
         double spin = time * 0.012 + h0 * Math.PI * 2.0;
         double radius = 1.3 + h1 * 0.7;
         double lift = 0.8 + Math.sin(time * 0.029 + h2 * 6.28318) * 0.18;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.487) * (radius * 0.685), lift + 0.27, Math.sin(spin + 0.487) * (radius * 0.685));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.005F, 187, 115, 255, alpha * 0.24F);
         if (((int)(time + 135.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.046F, 255, 96, 230, alpha * 0.425F);
         }

         if (((int)(time + 135.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.774, 0.0105F, 187, 115, 255, alpha * 0.06F, 24
            );
         }

         return wrote;
      }

      private static boolean layer136(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2768);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 4312);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5584);
         double spin = time * 0.015 + h0 * Math.PI * 2.0;
         double radius = 0.42 + h1 * 0.74;
         double lift = 0.895 + Math.sin(time * 0.033 + h2 * 6.28318) * 0.205;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.27) * (radius * 0.72), lift + 0.305, Math.sin(spin + 0.27) * (radius * 0.72));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0067F, 210, 36, 255, alpha * 0.252F);
         if (((int)(time + 136.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.018F, 255, 86, 238, alpha * 0.45F);
         }

         if (((int)(time + 136.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.792, 0.012F, 210, 36, 255, alpha * 0.068F, 32
            );
         }

         return wrote;
      }

      private static boolean layer137(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2781);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 4329);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5603);
         double spin = time * 0.018 + h0 * Math.PI * 2.0;
         double radius = 0.475 + h1 * 0.78;
         double lift = 0.99 + Math.sin(time * 0.037 + h2 * 6.28318) * 0.23;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.301) * (radius * 0.755), lift + 0.34, Math.sin(spin + 0.301) * (radius * 0.755));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0084F, 233, 53, 255, alpha * 0.264F);
         if (((int)(time + 137.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.022F, 255, 239, 255, alpha * 0.475F);
         }

         if (((int)(time + 137.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.81, 0.0135F, 233, 53, 255, alpha * 0.076F, 40
            );
         }

         return wrote;
      }

      private static boolean layer138(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2794);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 4346);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5622);
         double spin = time * 0.021 + h0 * Math.PI * 2.0;
         double radius = 0.53 + h1 * 0.82;
         double lift = -1.1 + Math.sin(time * 0.041 + h2 * 6.28318) * 0.255;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.332) * (radius * 0.58), lift + 0.375, Math.sin(spin + 0.332) * (radius * 0.58));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0101F, 118, 70, 255, alpha * 0.276F);
         if (((int)(time + 138.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.026F, 255, 246, 255, alpha * 0.5F);
         }

         if (((int)(time + 138.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.828, 0.006F, 118, 70, 255, alpha * 0.084F, 48
            );
         }

         return wrote;
      }

      private static boolean layer139(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2807);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 4363);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5641);
         double spin = time * 0.024 + h0 * Math.PI * 2.0;
         double radius = 0.585 + h1 * 0.86;
         double lift = -1.005 + Math.sin(time * 0.045 + h2 * 6.28318) * 0.28;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.363) * (radius * 0.615), lift + 0.41, Math.sin(spin + 0.363) * (radius * 0.615));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0118F, 141, 87, 255, alpha * 0.288F);
         if (((int)(time + 139.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.03F, 255, 253, 255, alpha * 0.525F);
         }

         if (((int)(time + 139.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.846, 0.0075F, 141, 87, 255, alpha * 0.092F, 56
            );
         }

         return wrote;
      }

      private static boolean layer140(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2820);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 4380);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5660);
         double spin = time * 0.027 + h0 * Math.PI * 2.0;
         double radius = 0.64 + h1 * 0.9;
         double lift = -0.91 + Math.sin(time * 0.021 + h2 * 6.28318) * 0.18;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.394) * (radius * 0.65), lift + 0.2, Math.sin(spin + 0.394) * (radius * 0.65));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0135F, 164, 104, 255, alpha * 0.3F);
         if (((int)(time + 140.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.034F, 255, 195, 255, alpha * 0.3F);
         }

         if (((int)(time + 140.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.864, 0.009F, 164, 104, 255, alpha * 0.1F, 24
            );
         }

         return wrote;
      }

      private static boolean layer141(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2833);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 4397);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5679);
         double spin = time * 0.03 + h0 * Math.PI * 2.0;
         double radius = 0.695 + h1 * 0.94;
         double lift = -0.815 + Math.sin(time * 0.025 + h2 * 6.28318) * 0.205;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.425) * (radius * 0.685), lift + 0.235, Math.sin(spin + 0.425) * (radius * 0.685));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0152F, 187, 121, 255, alpha * 0.312F);
         if (((int)(time + 141.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.038F, 255, 202, 255, alpha * 0.325F);
         }

         if (((int)(time + 141.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.882, 0.0105F, 187, 121, 255, alpha * 0.108F, 32
            );
         }

         return wrote;
      }

      private static boolean layer142(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2846);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 4414);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5698);
         double spin = time * 0.033 + h0 * Math.PI * 2.0;
         double radius = 0.75 + h1 * 0.98;
         double lift = -0.72 + Math.sin(time * 0.029 + h2 * 6.28318) * 0.23;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.456) * (radius * 0.72), lift + 0.27, Math.sin(spin + 0.456) * (radius * 0.72));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0169F, 210, 42, 255, alpha * 0.324F);
         if (((int)(time + 142.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.042F, 255, 209, 255, alpha * 0.35F);
         }

         if (((int)(time + 142.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.9, 0.012F, 210, 42, 255, alpha * 0.116F, 40
            );
         }

         return wrote;
      }

      private static boolean layer143(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2859);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 4431);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5717);
         double spin = time * 0.036 + h0 * Math.PI * 2.0;
         double radius = 0.805 + h1 * 0.58;
         double lift = -0.625 + Math.sin(time * 0.033 + h2 * 6.28318) * 0.255;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.487) * (radius * 0.755), lift + 0.305, Math.sin(spin + 0.487) * (radius * 0.755));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0186F, 233, 59, 255, alpha * 0.18F);
         if (((int)(time + 143.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.046F, 255, 76, 240, alpha * 0.375F);
         }

         if (((int)(time + 143.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.918, 0.0135F, 233, 59, 255, alpha * 0.124F, 48
            );
         }

         return wrote;
      }

      private static boolean layer144(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2872);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 4448);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5736);
         double spin = time * 0.012 + h0 * Math.PI * 2.0;
         double radius = 0.86 + h1 * 0.62;
         double lift = -0.53 + Math.sin(time * 0.037 + h2 * 6.28318) * 0.28;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.27) * (radius * 0.58), lift + 0.34, Math.sin(spin + 0.27) * (radius * 0.58));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.005F, 118, 76, 255, alpha * 0.192F);
         if (((int)(time + 144.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.018F, 255, 223, 255, alpha * 0.4F);
         }

         if (((int)(time + 144.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.72, 0.006F, 118, 76, 255, alpha * 0.06F, 56
            );
         }

         return wrote;
      }

      private static boolean layer145(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2885);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 4465);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5755);
         double spin = time * 0.015 + h0 * Math.PI * 2.0;
         double radius = 0.915 + h1 * 0.66;
         double lift = -0.435 + Math.sin(time * 0.041 + h2 * 6.28318) * 0.18;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.301) * (radius * 0.615), lift + 0.375, Math.sin(spin + 0.301) * (radius * 0.615));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0067F, 141, 93, 255, alpha * 0.204F);
         if (((int)(time + 145.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.022F, 112, 232, 255, alpha * 0.425F);
         }

         if (((int)(time + 145.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.738, 0.0075F, 141, 93, 255, alpha * 0.068F, 24
            );
         }

         return wrote;
      }

      private static boolean layer146(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2898);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 4482);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5774);
         double spin = time * 0.018 + h0 * Math.PI * 2.0;
         double radius = 0.97 + h1 * 0.7;
         double lift = -0.34 + Math.sin(time * 0.045 + h2 * 6.28318) * 0.205;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.332) * (radius * 0.65), lift + 0.41, Math.sin(spin + 0.332) * (radius * 0.65));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0084F, 164, 110, 255, alpha * 0.216F);
         if (((int)(time + 146.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.026F, 255, 237, 255, alpha * 0.45F);
         }

         if (((int)(time + 146.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.756, 0.009F, 164, 110, 255, alpha * 0.076F, 32
            );
         }

         return wrote;
      }

      private static boolean layer147(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2911);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 4499);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5793);
         double spin = time * 0.021 + h0 * Math.PI * 2.0;
         double radius = 1.025 + h1 * 0.74;
         double lift = -0.245 + Math.sin(time * 0.021 + h2 * 6.28318) * 0.23;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.363) * (radius * 0.685), lift + 0.2, Math.sin(spin + 0.363) * (radius * 0.685));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0101F, 187, 31, 255, alpha * 0.228F);
         if (((int)(time + 147.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.03F, 255, 94, 232, alpha * 0.475F);
         }

         if (((int)(time + 147.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.774, 0.0105F, 187, 31, 255, alpha * 0.084F, 40
            );
         }

         return wrote;
      }

      private static boolean layer148(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2924);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 4516);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5812);
         double spin = time * 0.024 + h0 * Math.PI * 2.0;
         double radius = 1.08 + h1 * 0.78;
         double lift = -0.15 + Math.sin(time * 0.025 + h2 * 6.28318) * 0.255;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.394) * (radius * 0.72), lift + 0.235, Math.sin(spin + 0.394) * (radius * 0.72));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0118F, 210, 48, 255, alpha * 0.24F);
         if (((int)(time + 148.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.034F, 255, 251, 255, alpha * 0.5F);
         }

         if (((int)(time + 148.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.792, 0.012F, 210, 48, 255, alpha * 0.092F, 48
            );
         }

         return wrote;
      }

      private static boolean layer149(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 2937);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 4533);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 5831);
         double spin = time * 0.027 + h0 * Math.PI * 2.0;
         double radius = 1.135 + h1 * 0.82;
         double lift = -0.055 + Math.sin(time * 0.029 + h2 * 6.28318) * 0.28;
         Vec3 a = center.add(Math.cos(spin) * radius, lift, Math.sin(spin) * radius);
         Vec3 b = center.add(Math.cos(spin + 0.425) * (radius * 0.755), lift + 0.27, Math.sin(spin + 0.425) * (radius * 0.755));
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, a, b, 0.0135F, 233, 65, 255, alpha * 0.252F);
         if (((int)(time + 149.0F) & 3) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, a, 0.038F, 255, 193, 255, alpha * 0.525F);
         }

         if (((int)(time + 149.0F) & 7) == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, lift * 0.18, 0.0), radius * 0.81, 0.0135F, 233, 65, 255, alpha * 0.1F, 56
            );
         }

         return wrote;
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

      public static Vec3 entityCenter(Entity entity, float partial, double heightScale) {
         return entity.getPosition(partial).add(0.0, entity.getBbHeight() * heightScale, 0.0);
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

      public static boolean ribbon(BufferBuilder buffer, Matrix4f matrix, Vec3 a, Vec3 b, float width, int r, int g, int bl, float alpha) {
         if (!(alpha <= 0.001F) && !(a.distanceToSqr(b) <= 1.0E-4)) {
            Vec3 mid = a.add(b).scale(0.5);
            Vec3 toCam = mid.scale(-1.0);
            Vec3 dir = b.subtract(a).normalize();
            Vec3 side = dir.cross(toCam);
            if (side.lengthSqr() < 1.0E-4) {
               side = dir.cross(new Vec3(0.0, 1.0, 0.0));
            }

            if (side.lengthSqr() < 1.0E-4) {
               side = new Vec3(1.0, 0.0, 0.0);
            }

            side = side.normalize().scale(width);
            Vec3 p0 = a.add(side);
            Vec3 p1 = b.add(side);
            Vec3 p2 = b.subtract(side);
            Vec3 p3 = a.subtract(side);
            tri(buffer, matrix, p0, p1, p2, r, g, bl, alpha);
            tri(buffer, matrix, p2, p3, p0, r, g, bl, alpha);
            return true;
         } else {
            return false;
         }
      }

      public static boolean star(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float size, int r, int g, int bl, float alpha) {
         if (alpha <= 0.001F) {
            return false;
         }

         Vec3 right = new Vec3(size, 0.0, 0.0);
         Vec3 up = new Vec3(0.0, size, 0.0);
         Vec3 f = new Vec3(0.0, 0.0, size * 0.35);
         tri(buffer, matrix, center.add(up), center.add(right.scale(0.22)), center.add(f), r, g, bl, alpha);
         tri(buffer, matrix, center.add(right), center.add(up.scale(-0.22)), center.add(f), r, g, bl, alpha);
         tri(buffer, matrix, center.add(up.scale(-1.0)), center.add(right.scale(-0.22)), center.add(f), r, g, bl, alpha);
         tri(buffer, matrix, center.add(right.scale(-1.0)), center.add(up.scale(0.22)), center.add(f), r, g, bl, alpha);
         tri(buffer, matrix, center.add(up.scale(0.45)), center.add(right.scale(0.45)), center.add(up.scale(-0.45)), r, g, bl, alpha * 0.45F);
         tri(buffer, matrix, center.add(up.scale(-0.45)), center.add(right.scale(-0.45)), center.add(up.scale(0.45)), r, g, bl, alpha * 0.45F);
         return true;
      }

      public static boolean diamond(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float size, int r, int g, int bl, float alpha) {
         if (alpha <= 0.001F) {
            return false;
         }

         Vec3 up = new Vec3(0.0, size, 0.0);
         Vec3 right = new Vec3(size * 0.68, 0.0, 0.0);
         Vec3 down = up.scale(-1.0);
         Vec3 left = right.scale(-1.0);
         tri(buffer, matrix, center.add(up), center.add(right), center, r, g, bl, alpha);
         tri(buffer, matrix, center.add(right), center.add(down), center, r, g, bl, alpha * 0.75F);
         tri(buffer, matrix, center.add(down), center.add(left), center, r, g, bl, alpha * 0.75F);
         tri(buffer, matrix, center.add(left), center.add(up), center, r, g, bl, alpha);
         return true;
      }

      public static boolean ring(BufferBuilder buffer, Matrix4f matrix, Vec3 c, double radius, float width, int r, int g, int bl, float alpha, int segments) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;

         for (int i = 0; i < segments; i++) {
            double a0 = i * (float) (Math.PI * 2) / segments;
            double a1 = (i + 1) * (float) (Math.PI * 2) / segments;
            Vec3 p0 = c.add(Math.cos(a0) * radius, 0.0, Math.sin(a0) * radius);
            Vec3 p1 = c.add(Math.cos(a1) * radius, 0.0, Math.sin(a1) * radius);
            wrote |= ribbon(buffer, matrix, p0, p1, width, r, g, bl, alpha * (0.82F + 0.18F * Mth.sin(i * 0.45F)));
         }

         return wrote;
      }

      public static boolean freeRing(
         BufferBuilder buffer, Matrix4f matrix, Vec3 c, Vec3 normal, double radius, float width, int r, int g, int bl, float alpha, int segments
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         normal = normal.lengthSqr() < 0.001 ? new Vec3(0.0, 1.0, 0.0) : normal.normalize();
         Vec3 side = normal.cross(new Vec3(0.0, 1.0, 0.0));
         if (side.lengthSqr() < 0.001) {
            side = new Vec3(1.0, 0.0, 0.0);
         }

         side = side.normalize();
         Vec3 up = side.cross(normal).normalize();
         boolean wrote = false;

         for (int i = 0; i < segments; i++) {
            double a0 = i * (float) (Math.PI * 2) / segments;
            double a1 = (i + 1) * (float) (Math.PI * 2) / segments;
            Vec3 p0 = c.add(side.scale(Math.cos(a0) * radius)).add(up.scale(Math.sin(a0) * radius));
            Vec3 p1 = c.add(side.scale(Math.cos(a1) * radius)).add(up.scale(Math.sin(a1) * radius));
            wrote |= ribbon(buffer, matrix, p0, p1, width, r, g, bl, alpha);
         }

         return wrote;
      }

      public static boolean orientedRing(
         BufferBuilder buffer, Matrix4f matrix, Vec3 c, Vec3 side, Vec3 up, double radius, float width, int r, int g, int bl, float alpha, int segments
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         if (!(side.lengthSqr() < 0.001) && !(up.lengthSqr() < 0.001)) {
            side = side.normalize();
            up = up.normalize();
            boolean wrote = false;

            for (int i = 0; i < segments; i++) {
               double a0 = i * (float) (Math.PI * 2) / segments;
               double a1 = (i + 1) * (float) (Math.PI * 2) / segments;
               Vec3 p0 = c.add(side.scale(Math.cos(a0) * radius)).add(up.scale(Math.sin(a0) * radius));
               Vec3 p1 = c.add(side.scale(Math.cos(a1) * radius)).add(up.scale(Math.sin(a1) * radius));
               wrote |= ribbon(buffer, matrix, p0, p1, width, r, g, bl, alpha * (0.72F + 0.28F * Mth.sin(i * 0.51F)));
            }

            return wrote;
         } else {
            return false;
         }
      }

      public static boolean spinningStar(
         BufferBuilder buffer, Matrix4f matrix, Vec3 center, Vec3 dir, Vec3 side, Vec3 up, float size, double spin, int r, int g, int bl, float alpha
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         Vec3 s0 = side.scale(Math.cos(spin)).add(up.scale(Math.sin(spin))).normalize();
         Vec3 u0 = s0.cross(dir).normalize();
         float longArm = size * 0.58F;
         float shortArm = size * 0.18F;
         boolean wrote = false;
         wrote |= triStar(buffer, matrix, center, s0, u0, longArm, shortArm, r, g, bl, alpha);
         wrote |= triStar(buffer, matrix, center, u0, dir, longArm * 0.74F, shortArm * 0.82F, Math.min(255, r + 12), Math.min(255, g + 20), bl, alpha * 0.72F);
         return wrote
            | triStar(buffer, matrix, center, dir, s0, longArm * 0.58F, shortArm * 0.7F, Math.min(255, r + 24), Math.min(255, g + 10), bl, alpha * 0.54F);
      }

      private static boolean triStar(
         BufferBuilder buffer, Matrix4f matrix, Vec3 center, Vec3 axis, Vec3 cross, float longArm, float shortArm, int r, int g, int bl, float alpha
      ) {
         Vec3 tip0 = center.add(axis.scale(longArm));
         Vec3 tip1 = center.subtract(axis.scale(longArm));
         Vec3 c0 = center.add(cross.scale(shortArm));
         Vec3 c1 = center.subtract(cross.scale(shortArm));
         tri(buffer, matrix, tip0, c0, c1, r, g, bl, alpha);
         tri(buffer, matrix, tip1, c1, c0, r, g, bl, alpha * 0.82F);
         return true;
      }

      public static boolean diamond3D(
         BufferBuilder buffer, Matrix4f matrix, Vec3 center, Vec3 dir, Vec3 side, Vec3 up, float size, int r, int g, int bl, float alpha
      ) {
         if (alpha <= 0.001F) {
            return false;
         }

         Vec3 tip = center.add(dir.scale(size * 0.62F));
         Vec3 back = center.subtract(dir.scale(size * 0.62F));
         Vec3 p0 = center.add(side.scale(size * 0.34F));
         Vec3 p1 = center.add(up.scale(size * 0.34F));
         Vec3 p2 = center.subtract(side.scale(size * 0.34F));
         Vec3 p3 = center.subtract(up.scale(size * 0.34F));
         tri(buffer, matrix, tip, p0, p1, r, g, bl, alpha);
         tri(buffer, matrix, tip, p1, p2, Math.min(255, r + 18), Math.min(255, g + 10), bl, alpha * 0.88F);
         tri(buffer, matrix, tip, p2, p3, Math.max(0, r - 28), Math.max(0, g - 16), bl, alpha * 0.74F);
         tri(buffer, matrix, tip, p3, p0, r, g, bl, alpha * 0.86F);
         tri(buffer, matrix, back, p1, p0, Math.max(0, r - 24), Math.max(0, g - 18), bl, alpha * 0.58F);
         tri(buffer, matrix, back, p2, p1, Math.max(0, r - 36), Math.max(0, g - 20), bl, alpha * 0.52F);
         tri(buffer, matrix, back, p3, p2, Math.max(0, r - 48), Math.max(0, g - 28), bl, alpha * 0.48F);
         tri(buffer, matrix, back, p0, p3, Math.max(0, r - 16), Math.max(0, g - 12), bl, alpha * 0.56F);
         return true;
      }

      public static boolean cube(BufferBuilder buffer, Matrix4f matrix, Vec3 c, float size, int r, int g, int bl, float alpha) {
         if (alpha <= 0.001F) {
            return false;
         }

         float s = size * 0.5F;
         Vec3[] p = new Vec3[]{
            c.add(-s, -s, -s), c.add(s, -s, -s), c.add(s, s, -s), c.add(-s, s, -s), c.add(-s, -s, s), c.add(s, -s, s), c.add(s, s, s), c.add(-s, s, s)
         };
         face(buffer, matrix, p[0], p[1], p[2], p[3], r, g, bl, alpha * 0.42F);
         face(buffer, matrix, p[4], p[7], p[6], p[5], r, g, bl, alpha * 0.5F);
         face(buffer, matrix, p[0], p[4], p[5], p[1], r, g, bl, alpha * 0.32F);
         face(buffer, matrix, p[2], p[6], p[7], p[3], r, g, bl, alpha * 0.28F);
         return true;
      }

      public static void face(BufferBuilder buffer, Matrix4f matrix, Vec3 a, Vec3 b, Vec3 c, Vec3 d, int r, int g, int bl, float alpha) {
         tri(buffer, matrix, a, b, c, r, g, bl, alpha);
         tri(buffer, matrix, c, d, a, r, g, bl, alpha);
      }

      public static void tri(BufferBuilder buffer, Matrix4f matrix, Vec3 a, Vec3 b, Vec3 c, int r, int g, int bl, float alpha) {
         int aa = Mth.clamp((int)(alpha * 255.0F), 0, 255);
         buffer.addVertex(matrix, (float)a.x, (float)a.y, (float)a.z).setColor(r, g, bl, aa);
         buffer.addVertex(matrix, (float)b.x, (float)b.y, (float)b.z).setColor(r, g, bl, aa);
         buffer.addVertex(matrix, (float)c.x, (float)c.y, (float)c.z).setColor(r, g, bl, aa);
      }
   }

   private static final class HertaStarlightBloom {
      private static boolean render(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         if (alpha <= 0.001F) {
            return false;
         }

         boolean wrote = false;
         float pulse = 0.72F + 0.28F * (float)Math.sin(time * 0.18 + MichaelHalberdShotVfxClient.H.hash(seed, 60001) * 6.28318);
         float live = alpha * pulse;
         wrote |= coreBloom(buffer, matrix, center, time, seed, live);
         wrote |= crownSigil(buffer, matrix, center, time, seed, live);
         wrote |= crossingStars(buffer, matrix, center, time, seed, live);
         wrote |= auraLayer0(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer1(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer2(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer3(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer4(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer5(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer6(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer7(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer8(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer9(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer10(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer11(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer12(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer13(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer14(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer15(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer16(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer17(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer18(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer19(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer20(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer21(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer22(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer23(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer24(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer25(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer26(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer27(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer28(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer29(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer30(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer31(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer32(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer33(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer34(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer35(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer36(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer37(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer38(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer39(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer40(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer41(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer42(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer43(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer44(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer45(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer46(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer47(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer48(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer49(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer50(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer51(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer52(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer53(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer54(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer55(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer56(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer57(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer58(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer59(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer60(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer61(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer62(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer63(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer64(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer65(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer66(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer67(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer68(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer69(buffer, matrix, center, time, seed, alpha);
         wrote |= auraLayer70(buffer, matrix, center, time, seed, alpha);
         return wrote | auraLayer71(buffer, matrix, center, time, seed, alpha);
      }

      private static boolean coreBloom(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;

         for (int i = 0; i < 16; i++) {
            double spin = i * Math.PI * 2.0 / 16.0 + time * (0.015 + i * 0.001);
            double r = 0.45 + i * 0.09 + Math.sin(time * 0.055 + i) * 0.025;
            Vec3 p0 = center.add(Math.cos(spin) * r, Math.sin(time * 0.04 + i) * 0.18, Math.sin(spin) * r);
            Vec3 p1 = center.add(Math.cos(spin + 0.16) * (r + 0.36), 0.08 + Math.cos(time * 0.035 + i) * 0.22, Math.sin(spin + 0.16) * (r + 0.36));
            wrote |= MichaelHalberdShotVfxClient.H.ribbon(
               buffer, matrix, p0, p1, 0.018F + i * 0.0012F, 180 + i * 4, 36 + i * 3, 255, alpha * (0.18F + i * 0.008F)
            );
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.052F + i * 0.003F, 116, 224, 255, alpha * 0.36F);
         }

         for (int ring = 0; ring < 5; ring++) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer,
               matrix,
               center.add(0.0, ring * 0.018, 0.0),
               0.62 + ring * 0.42 + Math.sin(time * 0.035 + ring) * 0.06,
               0.018F + ring * 0.005F,
               150 + ring * 20,
               25 + ring * 18,
               255,
               alpha * (0.22F - ring * 0.025F),
               96
            );
         }

         return wrote;
      }

      private static boolean crownSigil(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         Vec3 lift = center.add(0.0, 1.45 + Math.sin(time * 0.08) * 0.12, 0.0);

         for (int i = 0; i < 8; i++) {
            double a0 = i * Math.PI * 2.0 / 8.0 + time * 0.022;
            double a1 = a0 + (Math.PI / 4);
            Vec3 p0 = lift.add(Math.cos(a0) * 0.75, Math.sin(a0 * 2.0 + time * 0.03) * 0.06, Math.sin(a0) * 0.75);
            Vec3 p1 = lift.add(Math.cos(a1) * 0.75, Math.sin(a1 * 2.0 + time * 0.03) * 0.06, Math.sin(a1) * 0.75);
            Vec3 peak = lift.add(Math.cos((a0 + a1) * 0.5) * 1.24, 0.36 + i % 2 * 0.18, Math.sin((a0 + a1) * 0.5) * 1.24);
            wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, peak, 0.02F, 255, 210, 255, alpha * 0.62F);
            wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, peak, p1, 0.02F, 178, 48, 255, alpha * 0.58F);
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, peak, 0.075F, 86, 235, 255, alpha * 0.9F);
         }

         wrote |= MichaelHalberdShotVfxClient.H.ring(buffer, matrix, lift, 1.08, 0.015F, 255, 66, 235, alpha * 0.52F, 96);
         return wrote | MichaelHalberdShotVfxClient.H.ring(buffer, matrix, lift.add(0.0, 0.045, 0.0), 0.56, 0.01F, 86, 235, 255, alpha * 0.7F, 72);
      }

      private static boolean crossingStars(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;

         for (int i = 0; i < 28; i++) {
            float h = MichaelHalberdShotVfxClient.H.hash(seed, 65000 + i * 29);
            float q = MichaelHalberdShotVfxClient.H.hash(seed, 65100 + i * 31);
            double a = h * Math.PI * 2.0 + time * (0.01 + q * 0.018);
            double r = 0.7 + Math.sqrt(q) * 2.75;
            Vec3 p = center.add(Math.cos(a) * r, -1.0 + h * 3.2, Math.sin(a) * r);
            Vec3 p2 = p.add(Math.cos(a + 1.5707) * (0.28 + q * 0.42), 0.16 + h * 0.44, Math.sin(a + 1.5707) * (0.28 + q * 0.42));
            wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p, p2, 0.01F + h * 0.016F, 85, 226, 255, alpha * (0.34F + q * 0.3F));
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p2, 0.042F + q * 0.06F, 86, 235, 255, alpha * 0.58F);
         }

         return wrote;
      }

      private static boolean auraLayer0(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 70000);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 71000);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 72000);
         double angle = h0 * Math.PI * 2.0 + time * 0.01;
         double sweep = angle + Math.sin(time * 0.028 + h2 * 9.0) * 0.16;
         double radius = 0.8 + h1 * 0.72;
         double height = -1.4 + Math.sin(time * 0.018 + h0 * 6.28318) * 0.2;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.2) * radius * 0.66, height + 0.22, Math.sin(sweep + 0.2) * radius * 0.66);
         Vec3 p2 = center.add(Math.cos(sweep - 0.34) * radius * 1.08, height * 0.55 + 0.08, Math.sin(sweep - 0.34) * radius * 1.08);
         float live = alpha * (0.16F + h2 * 0.075F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.01F, 115, 16, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.006F, 95, 212, 255, live);
         if ((0 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.035F, 255, 70, 230, live * 1.25F);
         }

         if ((0 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.04F, 115, 16, 255, live * 0.95F);
         }

         if ((0 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.7, 0.007F, 115, 16, 255, live * 0.3F, 48
            );
         }

         return wrote;
      }

      private static boolean auraLayer1(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 70037);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 71053);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 72071);
         double angle = h0 * Math.PI * 2.0 + time * 0.0125;
         double sweep = angle + Math.sin(time * 0.031 + h2 * 9.0) * 0.178;
         double radius = 0.875 + h1 * 0.775;
         double height = -1.3 + Math.sin(time * 0.022 + h0 * 6.28318) * 0.23;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.221) * radius * 0.685, height + 0.248, Math.sin(sweep + 0.221) * radius * 0.685);
         Vec3 p2 = center.add(Math.cos(sweep - 0.359) * radius * 1.105, height * 0.55 + 0.1, Math.sin(sweep - 0.359) * radius * 1.105);
         float live = alpha * (0.171F + h2 * 0.083F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.0125F, 144, 57, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0077F, 255, 184, 255, live);
         if ((1 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.041F, 255, 70, 230, live * 1.25F);
         }

         if ((1 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.046F, 144, 57, 255, live * 0.95F);
         }

         if ((1 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.718, 0.0085F, 144, 57, 255, live * 0.3F, 56
            );
         }

         return wrote;
      }

      private static boolean auraLayer2(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 70074);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 71106);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 72142);
         double angle = h0 * Math.PI * 2.0 + time * 0.015;
         double sweep = angle + Math.sin(time * 0.034 + h2 * 9.0) * 0.196;
         double radius = 0.95 + h1 * 0.83;
         double height = -1.2 + Math.sin(time * 0.026 + h0 * 6.28318) * 0.26;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.242) * radius * 0.71, height + 0.276, Math.sin(sweep + 0.242) * radius * 0.71);
         Vec3 p2 = center.add(Math.cos(sweep - 0.378) * radius * 1.13, height * 0.55 + 0.12, Math.sin(sweep - 0.378) * radius * 1.13);
         float live = alpha * (0.182F + h2 * 0.091F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.015F, 173, 98, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0094F, 255, 193, 255, live);
         if ((2 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.047F, 255, 70, 230, live * 1.25F);
         }

         if ((2 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.052F, 173, 98, 255, live * 0.95F);
         }

         if ((2 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.736, 0.01F, 173, 98, 255, live * 0.3F, 64
            );
         }

         return wrote;
      }

      private static boolean auraLayer3(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 70111);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 71159);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 72213);
         double angle = h0 * Math.PI * 2.0 + time * 0.0175;
         double sweep = angle + Math.sin(time * 0.037 + h2 * 9.0) * 0.214;
         double radius = 1.025 + h1 * 0.885;
         double height = -1.1 + Math.sin(time * 0.03 + h0 * 6.28318) * 0.29;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.263) * radius * 0.735, height + 0.304, Math.sin(sweep + 0.263) * radius * 0.735);
         Vec3 p2 = center.add(Math.cos(sweep - 0.397) * radius * 1.155, height * 0.55 + 0.14, Math.sin(sweep - 0.397) * radius * 1.155);
         float live = alpha * (0.193F + h2 * 0.099F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.0175F, 202, 23, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0111F, 255, 202, 255, live);
         if ((3 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.053F, 255, 70, 230, live * 1.25F);
         }

         if ((3 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.058F, 202, 23, 255, live * 0.95F);
         }

         if ((3 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.754, 0.0115F, 202, 23, 255, live * 0.3F, 72
            );
         }

         return wrote;
      }

      private static boolean auraLayer4(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 70148);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 71212);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 72284);
         double angle = h0 * Math.PI * 2.0 + time * 0.02;
         double sweep = angle + Math.sin(time * 0.04 + h2 * 9.0) * 0.232;
         double radius = 1.1 + h1 * 0.94;
         double height = -1.0 + Math.sin(time * 0.034 + h0 * 6.28318) * 0.32;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.284) * radius * 0.76, height + 0.332, Math.sin(sweep + 0.284) * radius * 0.76);
         Vec3 p2 = center.add(Math.cos(sweep - 0.416) * radius * 1.18, height * 0.55 + 0.16, Math.sin(sweep - 0.416) * radius * 1.18);
         float live = alpha * (0.204F + h2 * 0.107F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.02F, 231, 64, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0128F, 96, 230, 255, live);
         if ((4 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.059F, 255, 70, 230, live * 1.25F);
         }

         if ((4 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.064F, 231, 64, 255, live * 0.95F);
         }

         if ((4 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.772, 0.013F, 231, 64, 255, live * 0.3F, 80
            );
         }

         return wrote;
      }

      private static boolean auraLayer5(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 70185);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 71265);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 72355);
         double angle = h0 * Math.PI * 2.0 + time * 0.0225;
         double sweep = angle + Math.sin(time * 0.043 + h2 * 9.0) * 0.25;
         double radius = 1.175 + h1 * 0.995;
         double height = -0.9 + Math.sin(time * 0.038 + h0 * 6.28318) * 0.35;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.305) * radius * 0.785, height + 0.36, Math.sin(sweep + 0.305) * radius * 0.785);
         Vec3 p2 = center.add(Math.cos(sweep - 0.435) * radius * 1.205, height * 0.55 + 0.08, Math.sin(sweep - 0.435) * radius * 1.205);
         float live = alpha * (0.215F + h2 * 0.115F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.0225F, 119, 105, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0145F, 255, 80, 228, live);
         if ((5 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.065F, 255, 70, 230, live * 1.25F);
         }

         if ((5 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.07F, 119, 105, 255, live * 0.95F);
         }

         if ((5 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.79, 0.0145F, 119, 105, 255, live * 0.3F, 88
            );
         }

         return wrote;
      }

      private static boolean auraLayer6(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 70222);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 71318);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 72426);
         double angle = h0 * Math.PI * 2.0 + time * 0.025;
         double sweep = angle + Math.sin(time * 0.046 + h2 * 9.0) * 0.268;
         double radius = 1.25 + h1 * 1.05;
         double height = -0.8 + Math.sin(time * 0.042 + h0 * 6.28318) * 0.2;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.326) * radius * 0.81, height + 0.388, Math.sin(sweep + 0.326) * radius * 0.81);
         Vec3 p2 = center.add(Math.cos(sweep - 0.454) * radius * 1.08, height * 0.55 + 0.1, Math.sin(sweep - 0.454) * radius * 1.08);
         float live = alpha * (0.226F + h2 * 0.075F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.025F, 148, 30, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.006F, 255, 229, 255, live);
         if ((6 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.071F, 255, 70, 230, live * 1.25F);
         }

         if ((6 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.076F, 148, 30, 255, live * 0.95F);
         }

         if ((6 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.808, 0.007F, 148, 30, 255, live * 0.3F, 48
            );
         }

         return wrote;
      }

      private static boolean auraLayer7(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 70259);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 71371);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 72497);
         double angle = h0 * Math.PI * 2.0 + time * 0.0275;
         double sweep = angle + Math.sin(time * 0.049 + h2 * 9.0) * 0.16;
         double radius = 1.325 + h1 * 1.105;
         double height = -0.7 + Math.sin(time * 0.046 + h0 * 6.28318) * 0.23;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.347) * radius * 0.835, height + 0.416, Math.sin(sweep + 0.347) * radius * 0.835);
         Vec3 p2 = center.add(Math.cos(sweep - 0.473) * radius * 1.105, height * 0.55 + 0.12, Math.sin(sweep - 0.473) * radius * 1.105);
         float live = alpha * (0.237F + h2 * 0.083F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.0275F, 177, 71, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0077F, 116, 224, 255, live);
         if ((7 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.077F, 255, 70, 230, live * 1.25F);
         }

         if ((7 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.082F, 177, 71, 255, live * 0.95F);
         }

         if ((7 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.826, 0.0085F, 177, 71, 255, live * 0.3F, 56
            );
         }

         return wrote;
      }

      private static boolean auraLayer8(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 70296);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 71424);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 72568);
         double angle = h0 * Math.PI * 2.0 + time * 0.03;
         double sweep = angle + Math.sin(time * 0.052 + h2 * 9.0) * 0.178;
         double radius = 1.4 + h1 * 1.16;
         double height = -0.6 + Math.sin(time * 0.018 + h0 * 6.28318) * 0.26;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.368) * radius * 0.66, height + 0.444, Math.sin(sweep + 0.368) * radius * 0.66);
         Vec3 p2 = center.add(Math.cos(sweep - 0.492) * radius * 1.13, height * 0.55 + 0.14, Math.sin(sweep - 0.492) * radius * 1.13);
         float live = alpha * (0.248F + h2 * 0.091F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.01F, 206, 112, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0094F, 255, 247, 255, live);
         if ((8 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.083F, 255, 70, 230, live * 1.25F);
         }

         if ((8 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.04F, 206, 112, 255, live * 0.95F);
         }

         if ((8 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.844, 0.01F, 206, 112, 255, live * 0.3F, 64
            );
         }

         return wrote;
      }

      private static boolean auraLayer9(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 70333);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 71477);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 72639);
         double angle = h0 * Math.PI * 2.0 + time * 0.0325;
         double sweep = angle + Math.sin(time * 0.028 + h2 * 9.0) * 0.196;
         double radius = 1.475 + h1 * 1.215;
         double height = -0.5 + Math.sin(time * 0.022 + h0 * 6.28318) * 0.29;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.389) * radius * 0.685, height + 0.22, Math.sin(sweep + 0.389) * radius * 0.685);
         Vec3 p2 = center.add(Math.cos(sweep - 0.511) * radius * 1.155, height * 0.55 + 0.16, Math.sin(sweep - 0.511) * radius * 1.155);
         float live = alpha * (0.259F + h2 * 0.099F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.0125F, 235, 37, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0111F, 255, 176, 255, live);
         if ((9 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.089F, 255, 70, 230, live * 1.25F);
         }

         if ((9 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.046F, 235, 37, 255, live * 0.95F);
         }

         if ((9 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.862, 0.0115F, 235, 37, 255, live * 0.3F, 72
            );
         }

         return wrote;
      }

      private static boolean auraLayer10(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 70370);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 71530);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 72710);
         double angle = h0 * Math.PI * 2.0 + time * 0.035;
         double sweep = angle + Math.sin(time * 0.031 + h2 * 9.0) * 0.214;
         double radius = 1.55 + h1 * 0.72;
         double height = -0.4 + Math.sin(time * 0.026 + h0 * 6.28318) * 0.32;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.41) * radius * 0.71, height + 0.248, Math.sin(sweep + 0.41) * radius * 0.71);
         Vec3 p2 = center.add(Math.cos(sweep - 0.34) * radius * 1.18, height * 0.55 + 0.08, Math.sin(sweep - 0.34) * radius * 1.18);
         float live = alpha * (0.27F + h2 * 0.107F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.015F, 123, 78, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0128F, 255, 185, 255, live);
         if ((10 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.035F, 255, 70, 230, live * 1.25F);
         }

         if ((10 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.052F, 123, 78, 255, live * 0.95F);
         }

         if ((10 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.88, 0.013F, 123, 78, 255, live * 0.3F, 80
            );
         }

         return wrote;
      }

      private static boolean auraLayer11(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 70407);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 71583);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 72781);
         double angle = h0 * Math.PI * 2.0 + time * 0.01;
         double sweep = angle + Math.sin(time * 0.034 + h2 * 9.0) * 0.232;
         double radius = 1.625 + h1 * 0.775;
         double height = -0.3 + Math.sin(time * 0.03 + h0 * 6.28318) * 0.35;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.431) * radius * 0.735, height + 0.276, Math.sin(sweep + 0.431) * radius * 0.735);
         Vec3 p2 = center.add(Math.cos(sweep - 0.359) * radius * 1.205, height * 0.55 + 0.1, Math.sin(sweep - 0.359) * radius * 1.205);
         float live = alpha * (0.281F + h2 * 0.115F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.0175F, 152, 119, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0145F, 255, 194, 255, live);
         if ((11 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.041F, 255, 70, 230, live * 1.25F);
         }

         if ((11 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.058F, 152, 119, 255, live * 0.95F);
         }

         if ((11 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.898, 0.0145F, 152, 119, 255, live * 0.3F, 88
            );
         }

         return wrote;
      }

      private static boolean auraLayer12(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 70444);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 71636);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 72852);
         double angle = h0 * Math.PI * 2.0 + time * 0.0125;
         double sweep = angle + Math.sin(time * 0.037 + h2 * 9.0) * 0.25;
         double radius = 1.7 + h1 * 0.83;
         double height = -0.2 + Math.sin(time * 0.034 + h0 * 6.28318) * 0.2;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.2) * radius * 0.76, height + 0.304, Math.sin(sweep + 0.2) * radius * 0.76);
         Vec3 p2 = center.add(Math.cos(sweep - 0.378) * radius * 1.08, height * 0.55 + 0.12, Math.sin(sweep - 0.378) * radius * 1.08);
         float live = alpha * (0.292F + h2 * 0.075F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.02F, 181, 44, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.006F, 255, 203, 255, live);
         if ((12 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.047F, 255, 70, 230, live * 1.25F);
         }

         if ((12 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.064F, 181, 44, 255, live * 0.95F);
         }

         if ((12 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.7, 0.007F, 181, 44, 255, live * 0.3F, 48
            );
         }

         return wrote;
      }

      private static boolean auraLayer13(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 70481);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 71689);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 72923);
         double angle = h0 * Math.PI * 2.0 + time * 0.015;
         double sweep = angle + Math.sin(time * 0.04 + h2 * 9.0) * 0.268;
         double radius = 1.775 + h1 * 0.885;
         double height = -0.1 + Math.sin(time * 0.038 + h0 * 6.28318) * 0.23;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.221) * radius * 0.785, height + 0.332, Math.sin(sweep + 0.221) * radius * 0.785);
         Vec3 p2 = center.add(Math.cos(sweep - 0.397) * radius * 1.105, height * 0.55 + 0.14, Math.sin(sweep - 0.397) * radius * 1.105);
         float live = alpha * (0.303F + h2 * 0.083F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.0225F, 210, 85, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0077F, 255, 212, 255, live);
         if ((13 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.053F, 255, 70, 230, live * 1.25F);
         }

         if ((13 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.07F, 210, 85, 255, live * 0.95F);
         }

         if ((13 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.718, 0.0085F, 210, 85, 255, live * 0.3F, 56
            );
         }

         return wrote;
      }

      private static boolean auraLayer14(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 70518);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 71742);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 72994);
         double angle = h0 * Math.PI * 2.0 + time * 0.0175;
         double sweep = angle + Math.sin(time * 0.043 + h2 * 9.0) * 0.16;
         double radius = 1.85 + h1 * 0.94;
         double height = 0.0 + Math.sin(time * 0.042 + h0 * 6.28318) * 0.26;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.242) * radius * 0.81, height + 0.36, Math.sin(sweep + 0.242) * radius * 0.81);
         Vec3 p2 = center.add(Math.cos(sweep - 0.416) * radius * 1.13, height * 0.55 + 0.16, Math.sin(sweep - 0.416) * radius * 1.13);
         float live = alpha * (0.16F + h2 * 0.091F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.025F, 239, 126, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0094F, 255, 221, 255, live);
         if ((14 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.059F, 255, 70, 230, live * 1.25F);
         }

         if ((14 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.076F, 239, 126, 255, live * 0.95F);
         }

         if ((14 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.736, 0.01F, 239, 126, 255, live * 0.3F, 64
            );
         }

         return wrote;
      }

      private static boolean auraLayer15(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 70555);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 71795);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 73065);
         double angle = h0 * Math.PI * 2.0 + time * 0.02;
         double sweep = angle + Math.sin(time * 0.046 + h2 * 9.0) * 0.178;
         double radius = 1.925 + h1 * 0.995;
         double height = 0.1 + Math.sin(time * 0.046 + h0 * 6.28318) * 0.29;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.263) * radius * 0.835, height + 0.388, Math.sin(sweep + 0.263) * radius * 0.835);
         Vec3 p2 = center.add(Math.cos(sweep - 0.435) * radius * 1.155, height * 0.55 + 0.08, Math.sin(sweep - 0.435) * radius * 1.155);
         float live = alpha * (0.171F + h2 * 0.099F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.0275F, 127, 51, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0111F, 112, 232, 255, live);
         if ((15 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.065F, 255, 70, 230, live * 1.25F);
         }

         if ((15 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.082F, 127, 51, 255, live * 0.95F);
         }

         if ((15 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.754, 0.0115F, 127, 51, 255, live * 0.3F, 72
            );
         }

         return wrote;
      }

      private static boolean auraLayer16(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 70592);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 71848);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 73136);
         double angle = h0 * Math.PI * 2.0 + time * 0.0225;
         double sweep = angle + Math.sin(time * 0.049 + h2 * 9.0) * 0.196;
         double radius = 2.0 + h1 * 1.05;
         double height = 0.2 + Math.sin(time * 0.018 + h0 * 6.28318) * 0.32;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.284) * radius * 0.66, height + 0.416, Math.sin(sweep + 0.284) * radius * 0.66);
         Vec3 p2 = center.add(Math.cos(sweep - 0.454) * radius * 1.18, height * 0.55 + 0.1, Math.sin(sweep - 0.454) * radius * 1.18);
         float live = alpha * (0.182F + h2 * 0.107F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.01F, 156, 92, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0128F, 255, 239, 255, live);
         if ((16 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.071F, 255, 70, 230, live * 1.25F);
         }

         if ((16 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.04F, 156, 92, 255, live * 0.95F);
         }

         if ((16 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.772, 0.013F, 156, 92, 255, live * 0.3F, 80
            );
         }

         return wrote;
      }

      private static boolean auraLayer17(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 70629);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 71901);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 73207);
         double angle = h0 * Math.PI * 2.0 + time * 0.025;
         double sweep = angle + Math.sin(time * 0.052 + h2 * 9.0) * 0.214;
         double radius = 2.075 + h1 * 1.105;
         double height = 0.3 + Math.sin(time * 0.022 + h0 * 6.28318) * 0.35;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.305) * radius * 0.685, height + 0.444, Math.sin(sweep + 0.305) * radius * 0.685);
         Vec3 p2 = center.add(Math.cos(sweep - 0.473) * radius * 1.205, height * 0.55 + 0.12, Math.sin(sweep - 0.473) * radius * 1.205);
         float live = alpha * (0.193F + h2 * 0.115F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.0125F, 185, 17, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0145F, 255, 248, 255, live);
         if ((17 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.077F, 255, 70, 230, live * 1.25F);
         }

         if ((17 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.046F, 185, 17, 255, live * 0.95F);
         }

         if ((17 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.79, 0.0145F, 185, 17, 255, live * 0.3F, 88
            );
         }

         return wrote;
      }

      private static boolean auraLayer18(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 70666);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 71954);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 73278);
         double angle = h0 * Math.PI * 2.0 + time * 0.0275;
         double sweep = angle + Math.sin(time * 0.028 + h2 * 9.0) * 0.232;
         double radius = 0.8 + h1 * 1.16;
         double height = 0.4 + Math.sin(time * 0.026 + h0 * 6.28318) * 0.2;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.326) * radius * 0.71, height + 0.22, Math.sin(sweep + 0.326) * radius * 0.71);
         Vec3 p2 = center.add(Math.cos(sweep - 0.492) * radius * 1.08, height * 0.55 + 0.14, Math.sin(sweep - 0.492) * radius * 1.08);
         float live = alpha * (0.204F + h2 * 0.075F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.015F, 214, 58, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.006F, 255, 177, 255, live);
         if ((18 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.083F, 255, 70, 230, live * 1.25F);
         }

         if ((18 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.052F, 214, 58, 255, live * 0.95F);
         }

         if ((18 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.808, 0.007F, 214, 58, 255, live * 0.3F, 48
            );
         }

         return wrote;
      }

      private static boolean auraLayer19(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 70703);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 72007);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 73349);
         double angle = h0 * Math.PI * 2.0 + time * 0.03;
         double sweep = angle + Math.sin(time * 0.031 + h2 * 9.0) * 0.25;
         double radius = 0.875 + h1 * 1.215;
         double height = 0.5 + Math.sin(time * 0.03 + h0 * 6.28318) * 0.23;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.347) * radius * 0.735, height + 0.248, Math.sin(sweep + 0.347) * radius * 0.735);
         Vec3 p2 = center.add(Math.cos(sweep - 0.511) * radius * 1.105, height * 0.55 + 0.16, Math.sin(sweep - 0.511) * radius * 1.105);
         float live = alpha * (0.215F + h2 * 0.083F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.0175F, 243, 99, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0077F, 255, 186, 255, live);
         if ((19 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.089F, 255, 70, 230, live * 1.25F);
         }

         if ((19 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.058F, 243, 99, 255, live * 0.95F);
         }

         if ((19 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.826, 0.0085F, 243, 99, 255, live * 0.3F, 56
            );
         }

         return wrote;
      }

      private static boolean auraLayer20(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 70740);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 72060);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 73420);
         double angle = h0 * Math.PI * 2.0 + time * 0.0325;
         double sweep = angle + Math.sin(time * 0.034 + h2 * 9.0) * 0.268;
         double radius = 0.95 + h1 * 0.72;
         double height = 0.6 + Math.sin(time * 0.034 + h0 * 6.28318) * 0.26;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.368) * radius * 0.76, height + 0.276, Math.sin(sweep + 0.368) * radius * 0.76);
         Vec3 p2 = center.add(Math.cos(sweep - 0.34) * radius * 1.13, height * 0.55 + 0.08, Math.sin(sweep - 0.34) * radius * 1.13);
         float live = alpha * (0.226F + h2 * 0.091F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.02F, 131, 24, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0094F, 255, 195, 255, live);
         if ((20 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.035F, 255, 70, 230, live * 1.25F);
         }

         if ((20 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.064F, 131, 24, 255, live * 0.95F);
         }

         if ((20 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.844, 0.01F, 131, 24, 255, live * 0.3F, 64
            );
         }

         return wrote;
      }

      private static boolean auraLayer21(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 70777);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 72113);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 73491);
         double angle = h0 * Math.PI * 2.0 + time * 0.035;
         double sweep = angle + Math.sin(time * 0.037 + h2 * 9.0) * 0.16;
         double radius = 1.025 + h1 * 0.775;
         double height = 0.7 + Math.sin(time * 0.038 + h0 * 6.28318) * 0.29;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.389) * radius * 0.785, height + 0.304, Math.sin(sweep + 0.389) * radius * 0.785);
         Vec3 p2 = center.add(Math.cos(sweep - 0.359) * radius * 1.155, height * 0.55 + 0.1, Math.sin(sweep - 0.359) * radius * 1.155);
         float live = alpha * (0.237F + h2 * 0.099F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.0225F, 160, 65, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0111F, 246, 66, 255, live);
         if ((21 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.041F, 255, 70, 230, live * 1.25F);
         }

         if ((21 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.07F, 160, 65, 255, live * 0.95F);
         }

         if ((21 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.862, 0.0115F, 160, 65, 255, live * 0.3F, 72
            );
         }

         return wrote;
      }

      private static boolean auraLayer22(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 70814);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 72166);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 73562);
         double angle = h0 * Math.PI * 2.0 + time * 0.01;
         double sweep = angle + Math.sin(time * 0.04 + h2 * 9.0) * 0.178;
         double radius = 1.1 + h1 * 0.83;
         double height = 0.8 + Math.sin(time * 0.042 + h0 * 6.28318) * 0.32;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.41) * radius * 0.81, height + 0.332, Math.sin(sweep + 0.41) * radius * 0.81);
         Vec3 p2 = center.add(Math.cos(sweep - 0.378) * radius * 1.18, height * 0.55 + 0.12, Math.sin(sweep - 0.378) * radius * 1.18);
         float live = alpha * (0.248F + h2 * 0.107F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.025F, 189, 106, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0128F, 255, 213, 255, live);
         if ((22 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.047F, 255, 70, 230, live * 1.25F);
         }

         if ((22 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.076F, 189, 106, 255, live * 0.95F);
         }

         if ((22 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.88, 0.013F, 189, 106, 255, live * 0.3F, 80
            );
         }

         return wrote;
      }

      private static boolean auraLayer23(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 70851);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 72219);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 73633);
         double angle = h0 * Math.PI * 2.0 + time * 0.0125;
         double sweep = angle + Math.sin(time * 0.043 + h2 * 9.0) * 0.196;
         double radius = 1.175 + h1 * 0.885;
         double height = 0.9 + Math.sin(time * 0.046 + h0 * 6.28318) * 0.35;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.431) * radius * 0.835, height + 0.36, Math.sin(sweep + 0.431) * radius * 0.835);
         Vec3 p2 = center.add(Math.cos(sweep - 0.397) * radius * 1.205, height * 0.55 + 0.14, Math.sin(sweep - 0.397) * radius * 1.205);
         float live = alpha * (0.259F + h2 * 0.115F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.0275F, 218, 31, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0145F, 255, 222, 255, live);
         if ((23 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.053F, 255, 70, 230, live * 1.25F);
         }

         if ((23 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.082F, 218, 31, 255, live * 0.95F);
         }

         if ((23 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.898, 0.0145F, 218, 31, 255, live * 0.3F, 88
            );
         }

         return wrote;
      }

      private static boolean auraLayer24(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 70888);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 72272);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 73704);
         double angle = h0 * Math.PI * 2.0 + time * 0.015;
         double sweep = angle + Math.sin(time * 0.046 + h2 * 9.0) * 0.214;
         double radius = 1.25 + h1 * 0.94;
         double height = 1.0 + Math.sin(time * 0.018 + h0 * 6.28318) * 0.2;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.2) * radius * 0.66, height + 0.388, Math.sin(sweep + 0.2) * radius * 0.66);
         Vec3 p2 = center.add(Math.cos(sweep - 0.416) * radius * 1.08, height * 0.55 + 0.16, Math.sin(sweep - 0.416) * radius * 1.08);
         float live = alpha * (0.27F + h2 * 0.075F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.01F, 247, 72, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.006F, 255, 231, 255, live);
         if ((24 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.059F, 255, 70, 230, live * 1.25F);
         }

         if ((24 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.04F, 247, 72, 255, live * 0.95F);
         }

         if ((24 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.7, 0.007F, 247, 72, 255, live * 0.3F, 48
            );
         }

         return wrote;
      }

      private static boolean auraLayer25(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 70925);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 72325);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 73775);
         double angle = h0 * Math.PI * 2.0 + time * 0.0175;
         double sweep = angle + Math.sin(time * 0.049 + h2 * 9.0) * 0.232;
         double radius = 1.325 + h1 * 0.995;
         double height = 1.1 + Math.sin(time * 0.022 + h0 * 6.28318) * 0.23;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.221) * radius * 0.685, height + 0.416, Math.sin(sweep + 0.221) * radius * 0.685);
         Vec3 p2 = center.add(Math.cos(sweep - 0.435) * radius * 1.105, height * 0.55 + 0.08, Math.sin(sweep - 0.435) * radius * 1.105);
         float live = alpha * (0.281F + h2 * 0.083F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.0125F, 135, 113, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0077F, 255, 70, 230, live);
         if ((25 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.065F, 255, 70, 230, live * 1.25F);
         }

         if ((25 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.046F, 135, 113, 255, live * 0.95F);
         }

         if ((25 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.718, 0.0085F, 135, 113, 255, live * 0.3F, 56
            );
         }

         return wrote;
      }

      private static boolean auraLayer26(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 70962);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 72378);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 73846);
         double angle = h0 * Math.PI * 2.0 + time * 0.02;
         double sweep = angle + Math.sin(time * 0.052 + h2 * 9.0) * 0.25;
         double radius = 1.4 + h1 * 1.05;
         double height = 1.2 + Math.sin(time * 0.026 + h0 * 6.28318) * 0.26;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.242) * radius * 0.71, height + 0.444, Math.sin(sweep + 0.242) * radius * 0.71);
         Vec3 p2 = center.add(Math.cos(sweep - 0.454) * radius * 1.13, height * 0.55 + 0.1, Math.sin(sweep - 0.454) * radius * 1.13);
         float live = alpha * (0.292F + h2 * 0.091F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.015F, 164, 38, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0094F, 255, 249, 255, live);
         if ((26 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.071F, 255, 70, 230, live * 1.25F);
         }

         if ((26 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.052F, 164, 38, 255, live * 0.95F);
         }

         if ((26 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.736, 0.01F, 164, 38, 255, live * 0.3F, 64
            );
         }

         return wrote;
      }

      private static boolean auraLayer27(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 70999);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 72431);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 73917);
         double angle = h0 * Math.PI * 2.0 + time * 0.0225;
         double sweep = angle + Math.sin(time * 0.028 + h2 * 9.0) * 0.268;
         double radius = 1.475 + h1 * 1.105;
         double height = 1.3 + Math.sin(time * 0.03 + h0 * 6.28318) * 0.29;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.263) * radius * 0.735, height + 0.22, Math.sin(sweep + 0.263) * radius * 0.735);
         Vec3 p2 = center.add(Math.cos(sweep - 0.473) * radius * 1.155, height * 0.55 + 0.12, Math.sin(sweep - 0.473) * radius * 1.155);
         float live = alpha * (0.303F + h2 * 0.099F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.0175F, 193, 79, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0111F, 255, 178, 255, live);
         if ((27 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.077F, 255, 70, 230, live * 1.25F);
         }

         if ((27 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.058F, 193, 79, 255, live * 0.95F);
         }

         if ((27 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.754, 0.0115F, 193, 79, 255, live * 0.3F, 72
            );
         }

         return wrote;
      }

      private static boolean auraLayer28(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 71036);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 72484);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 73988);
         double angle = h0 * Math.PI * 2.0 + time * 0.025;
         double sweep = angle + Math.sin(time * 0.031 + h2 * 9.0) * 0.16;
         double radius = 1.55 + h1 * 1.16;
         double height = 1.4 + Math.sin(time * 0.034 + h0 * 6.28318) * 0.32;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.284) * radius * 0.76, height + 0.248, Math.sin(sweep + 0.284) * radius * 0.76);
         Vec3 p2 = center.add(Math.cos(sweep - 0.492) * radius * 1.18, height * 0.55 + 0.14, Math.sin(sweep - 0.492) * radius * 1.18);
         float live = alpha * (0.16F + h2 * 0.107F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.02F, 222, 120, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0128F, 255, 187, 255, live);
         if ((28 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.083F, 255, 70, 230, live * 1.25F);
         }

         if ((28 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.064F, 222, 120, 255, live * 0.95F);
         }

         if ((28 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.772, 0.013F, 222, 120, 255, live * 0.3F, 80
            );
         }

         return wrote;
      }

      private static boolean auraLayer29(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 71073);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 72537);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 74059);
         double angle = h0 * Math.PI * 2.0 + time * 0.0275;
         double sweep = angle + Math.sin(time * 0.034 + h2 * 9.0) * 0.178;
         double radius = 1.625 + h1 * 1.215;
         double height = -1.4 + Math.sin(time * 0.038 + h0 * 6.28318) * 0.35;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.305) * radius * 0.785, height + 0.276, Math.sin(sweep + 0.305) * radius * 0.785);
         Vec3 p2 = center.add(Math.cos(sweep - 0.511) * radius * 1.205, height * 0.55 + 0.16, Math.sin(sweep - 0.511) * radius * 1.205);
         float live = alpha * (0.171F + h2 * 0.115F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.0225F, 251, 45, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0145F, 255, 196, 255, live);
         if ((29 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.089F, 255, 70, 230, live * 1.25F);
         }

         if ((29 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.07F, 251, 45, 255, live * 0.95F);
         }

         if ((29 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.79, 0.0145F, 251, 45, 255, live * 0.3F, 88
            );
         }

         return wrote;
      }

      private static boolean auraLayer30(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 71110);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 72590);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 74130);
         double angle = h0 * Math.PI * 2.0 + time * 0.03;
         double sweep = angle + Math.sin(time * 0.037 + h2 * 9.0) * 0.196;
         double radius = 1.7 + h1 * 0.72;
         double height = -1.3 + Math.sin(time * 0.042 + h0 * 6.28318) * 0.2;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.326) * radius * 0.81, height + 0.304, Math.sin(sweep + 0.326) * radius * 0.81);
         Vec3 p2 = center.add(Math.cos(sweep - 0.34) * radius * 1.08, height * 0.55 + 0.08, Math.sin(sweep - 0.34) * radius * 1.08);
         float live = alpha * (0.182F + h2 * 0.075F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.025F, 139, 86, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.006F, 255, 205, 255, live);
         if ((30 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.035F, 255, 70, 230, live * 1.25F);
         }

         if ((30 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.076F, 139, 86, 255, live * 0.95F);
         }

         if ((30 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.808, 0.007F, 139, 86, 255, live * 0.3F, 48
            );
         }

         return wrote;
      }

      private static boolean auraLayer31(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 71147);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 72643);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 74201);
         double angle = h0 * Math.PI * 2.0 + time * 0.0325;
         double sweep = angle + Math.sin(time * 0.04 + h2 * 9.0) * 0.214;
         double radius = 1.775 + h1 * 0.775;
         double height = -1.2 + Math.sin(time * 0.046 + h0 * 6.28318) * 0.23;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.347) * radius * 0.835, height + 0.332, Math.sin(sweep + 0.347) * radius * 0.835);
         Vec3 p2 = center.add(Math.cos(sweep - 0.359) * radius * 1.105, height * 0.55 + 0.1, Math.sin(sweep - 0.359) * radius * 1.105);
         float live = alpha * (0.193F + h2 * 0.083F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.0275F, 168, 127, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0077F, 255, 214, 255, live);
         if ((31 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.041F, 255, 70, 230, live * 1.25F);
         }

         if ((31 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.082F, 168, 127, 255, live * 0.95F);
         }

         if ((31 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.826, 0.0085F, 168, 127, 255, live * 0.3F, 56
            );
         }

         return wrote;
      }

      private static boolean auraLayer32(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 71184);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 72696);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 74272);
         double angle = h0 * Math.PI * 2.0 + time * 0.035;
         double sweep = angle + Math.sin(time * 0.043 + h2 * 9.0) * 0.232;
         double radius = 1.85 + h1 * 0.83;
         double height = -1.1 + Math.sin(time * 0.018 + h0 * 6.28318) * 0.26;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.368) * radius * 0.66, height + 0.36, Math.sin(sweep + 0.368) * radius * 0.66);
         Vec3 p2 = center.add(Math.cos(sweep - 0.378) * radius * 1.13, height * 0.55 + 0.12, Math.sin(sweep - 0.378) * radius * 1.13);
         float live = alpha * (0.204F + h2 * 0.091F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.01F, 197, 52, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0094F, 255, 223, 255, live);
         if ((32 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.047F, 255, 70, 230, live * 1.25F);
         }

         if ((32 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.04F, 197, 52, 255, live * 0.95F);
         }

         if ((32 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.844, 0.01F, 197, 52, 255, live * 0.3F, 64
            );
         }

         return wrote;
      }

      private static boolean auraLayer33(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 71221);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 72749);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 74343);
         double angle = h0 * Math.PI * 2.0 + time * 0.01;
         double sweep = angle + Math.sin(time * 0.046 + h2 * 9.0) * 0.25;
         double radius = 1.925 + h1 * 0.885;
         double height = -1.0 + Math.sin(time * 0.022 + h0 * 6.28318) * 0.29;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.389) * radius * 0.685, height + 0.388, Math.sin(sweep + 0.389) * radius * 0.685);
         Vec3 p2 = center.add(Math.cos(sweep - 0.397) * radius * 1.155, height * 0.55 + 0.14, Math.sin(sweep - 0.397) * radius * 1.155);
         float live = alpha * (0.215F + h2 * 0.099F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.0125F, 226, 93, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0111F, 255, 86, 238, live);
         if ((33 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.053F, 255, 70, 230, live * 1.25F);
         }

         if ((33 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.046F, 226, 93, 255, live * 0.95F);
         }

         if ((33 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.862, 0.0115F, 226, 93, 255, live * 0.3F, 72
            );
         }

         return wrote;
      }

      private static boolean auraLayer34(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 71258);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 72802);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 74414);
         double angle = h0 * Math.PI * 2.0 + time * 0.0125;
         double sweep = angle + Math.sin(time * 0.049 + h2 * 9.0) * 0.268;
         double radius = 2.0 + h1 * 0.94;
         double height = -0.9 + Math.sin(time * 0.026 + h0 * 6.28318) * 0.32;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.41) * radius * 0.71, height + 0.416, Math.sin(sweep + 0.41) * radius * 0.71);
         Vec3 p2 = center.add(Math.cos(sweep - 0.416) * radius * 1.18, height * 0.55 + 0.16, Math.sin(sweep - 0.416) * radius * 1.18);
         float live = alpha * (0.226F + h2 * 0.107F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.015F, 255, 18, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0128F, 255, 241, 255, live);
         if ((34 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.059F, 255, 70, 230, live * 1.25F);
         }

         if ((34 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.052F, 255, 18, 255, live * 0.95F);
         }

         if ((34 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.88, 0.013F, 255, 18, 255, live * 0.3F, 80
            );
         }

         return wrote;
      }

      private static boolean auraLayer35(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 71295);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 72855);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 74485);
         double angle = h0 * Math.PI * 2.0 + time * 0.015;
         double sweep = angle + Math.sin(time * 0.052 + h2 * 9.0) * 0.16;
         double radius = 2.075 + h1 * 0.995;
         double height = -0.8 + Math.sin(time * 0.03 + h0 * 6.28318) * 0.35;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.431) * radius * 0.735, height + 0.444, Math.sin(sweep + 0.431) * radius * 0.735);
         Vec3 p2 = center.add(Math.cos(sweep - 0.435) * radius * 1.205, height * 0.55 + 0.08, Math.sin(sweep - 0.435) * radius * 1.205);
         float live = alpha * (0.237F + h2 * 0.115F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.0175F, 143, 59, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0145F, 255, 250, 255, live);
         if ((35 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.065F, 255, 70, 230, live * 1.25F);
         }

         if ((35 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.058F, 143, 59, 255, live * 0.95F);
         }

         if ((35 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.898, 0.0145F, 143, 59, 255, live * 0.3F, 88
            );
         }

         return wrote;
      }

      private static boolean auraLayer36(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 71332);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 72908);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 74556);
         double angle = h0 * Math.PI * 2.0 + time * 0.0175;
         double sweep = angle + Math.sin(time * 0.028 + h2 * 9.0) * 0.178;
         double radius = 0.8 + h1 * 1.05;
         double height = -0.7 + Math.sin(time * 0.034 + h0 * 6.28318) * 0.2;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.2) * radius * 0.76, height + 0.22, Math.sin(sweep + 0.2) * radius * 0.76);
         Vec3 p2 = center.add(Math.cos(sweep - 0.454) * radius * 1.08, height * 0.55 + 0.1, Math.sin(sweep - 0.454) * radius * 1.08);
         float live = alpha * (0.248F + h2 * 0.075F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.02F, 172, 100, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.006F, 255, 179, 255, live);
         if ((36 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.071F, 255, 70, 230, live * 1.25F);
         }

         if ((36 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.064F, 172, 100, 255, live * 0.95F);
         }

         if ((36 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.7, 0.007F, 172, 100, 255, live * 0.3F, 48
            );
         }

         return wrote;
      }

      private static boolean auraLayer37(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 71369);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 72961);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 74627);
         double angle = h0 * Math.PI * 2.0 + time * 0.02;
         double sweep = angle + Math.sin(time * 0.031 + h2 * 9.0) * 0.196;
         double radius = 0.875 + h1 * 1.105;
         double height = -0.6 + Math.sin(time * 0.038 + h0 * 6.28318) * 0.23;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.221) * radius * 0.785, height + 0.248, Math.sin(sweep + 0.221) * radius * 0.785);
         Vec3 p2 = center.add(Math.cos(sweep - 0.473) * radius * 1.105, height * 0.55 + 0.12, Math.sin(sweep - 0.473) * radius * 1.105);
         float live = alpha * (0.259F + h2 * 0.083F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.0225F, 201, 25, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0077F, 255, 188, 255, live);
         if ((37 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.077F, 255, 70, 230, live * 1.25F);
         }

         if ((37 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.07F, 201, 25, 255, live * 0.95F);
         }

         if ((37 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.718, 0.0085F, 201, 25, 255, live * 0.3F, 56
            );
         }

         return wrote;
      }

      private static boolean auraLayer38(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 71406);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 73014);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 74698);
         double angle = h0 * Math.PI * 2.0 + time * 0.0225;
         double sweep = angle + Math.sin(time * 0.034 + h2 * 9.0) * 0.214;
         double radius = 0.95 + h1 * 1.16;
         double height = -0.5 + Math.sin(time * 0.042 + h0 * 6.28318) * 0.26;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.242) * radius * 0.81, height + 0.276, Math.sin(sweep + 0.242) * radius * 0.81);
         Vec3 p2 = center.add(Math.cos(sweep - 0.492) * radius * 1.13, height * 0.55 + 0.14, Math.sin(sweep - 0.492) * radius * 1.13);
         float live = alpha * (0.27F + h2 * 0.091F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.025F, 230, 66, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0094F, 82, 220, 255, live);
         if ((38 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.083F, 255, 70, 230, live * 1.25F);
         }

         if ((38 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.076F, 230, 66, 255, live * 0.95F);
         }

         if ((38 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.736, 0.01F, 230, 66, 255, live * 0.3F, 64
            );
         }

         return wrote;
      }

      private static boolean auraLayer39(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 71443);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 73067);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 74769);
         double angle = h0 * Math.PI * 2.0 + time * 0.025;
         double sweep = angle + Math.sin(time * 0.037 + h2 * 9.0) * 0.232;
         double radius = 1.025 + h1 * 1.215;
         double height = -0.4 + Math.sin(time * 0.046 + h0 * 6.28318) * 0.29;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.263) * radius * 0.835, height + 0.304, Math.sin(sweep + 0.263) * radius * 0.835);
         Vec3 p2 = center.add(Math.cos(sweep - 0.511) * radius * 1.155, height * 0.55 + 0.16, Math.sin(sweep - 0.511) * radius * 1.155);
         float live = alpha * (0.281F + h2 * 0.099F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.0275F, 118, 107, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0111F, 255, 206, 255, live);
         if ((39 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.089F, 255, 70, 230, live * 1.25F);
         }

         if ((39 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.082F, 118, 107, 255, live * 0.95F);
         }

         if ((39 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.754, 0.0115F, 118, 107, 255, live * 0.3F, 72
            );
         }

         return wrote;
      }

      private static boolean auraLayer40(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 71480);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 73120);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 74840);
         double angle = h0 * Math.PI * 2.0 + time * 0.0275;
         double sweep = angle + Math.sin(time * 0.04 + h2 * 9.0) * 0.25;
         double radius = 1.1 + h1 * 0.72;
         double height = -0.3 + Math.sin(time * 0.018 + h0 * 6.28318) * 0.32;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.284) * radius * 0.66, height + 0.332, Math.sin(sweep + 0.284) * radius * 0.66);
         Vec3 p2 = center.add(Math.cos(sweep - 0.34) * radius * 1.18, height * 0.55 + 0.08, Math.sin(sweep - 0.34) * radius * 1.18);
         float live = alpha * (0.292F + h2 * 0.107F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.01F, 147, 32, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0128F, 255, 215, 255, live);
         if ((40 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.035F, 255, 70, 230, live * 1.25F);
         }

         if ((40 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.04F, 147, 32, 255, live * 0.95F);
         }

         if ((40 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.772, 0.013F, 147, 32, 255, live * 0.3F, 80
            );
         }

         return wrote;
      }

      private static boolean auraLayer41(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 71517);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 73173);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 74911);
         double angle = h0 * Math.PI * 2.0 + time * 0.03;
         double sweep = angle + Math.sin(time * 0.043 + h2 * 9.0) * 0.268;
         double radius = 1.175 + h1 * 0.775;
         double height = -0.2 + Math.sin(time * 0.022 + h0 * 6.28318) * 0.35;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.305) * radius * 0.685, height + 0.36, Math.sin(sweep + 0.305) * radius * 0.685);
         Vec3 p2 = center.add(Math.cos(sweep - 0.359) * radius * 1.205, height * 0.55 + 0.1, Math.sin(sweep - 0.359) * radius * 1.205);
         float live = alpha * (0.303F + h2 * 0.115F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.0125F, 176, 73, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0145F, 85, 226, 255, live);
         if ((41 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.041F, 255, 70, 230, live * 1.25F);
         }

         if ((41 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.046F, 176, 73, 255, live * 0.95F);
         }

         if ((41 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.79, 0.0145F, 176, 73, 255, live * 0.3F, 88
            );
         }

         return wrote;
      }

      private static boolean auraLayer42(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 71554);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 73226);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 74982);
         double angle = h0 * Math.PI * 2.0 + time * 0.0325;
         double sweep = angle + Math.sin(time * 0.046 + h2 * 9.0) * 0.16;
         double radius = 1.25 + h1 * 0.83;
         double height = -0.1 + Math.sin(time * 0.026 + h0 * 6.28318) * 0.2;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.326) * radius * 0.71, height + 0.388, Math.sin(sweep + 0.326) * radius * 0.71);
         Vec3 p2 = center.add(Math.cos(sweep - 0.378) * radius * 1.08, height * 0.55 + 0.12, Math.sin(sweep - 0.378) * radius * 1.08);
         float live = alpha * (0.16F + h2 * 0.075F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.015F, 205, 114, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.006F, 255, 233, 255, live);
         if ((42 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.047F, 255, 70, 230, live * 1.25F);
         }

         if ((42 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.052F, 205, 114, 255, live * 0.95F);
         }

         if ((42 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.808, 0.007F, 205, 114, 255, live * 0.3F, 48
            );
         }

         return wrote;
      }

      private static boolean auraLayer43(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 71591);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 73279);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 75053);
         double angle = h0 * Math.PI * 2.0 + time * 0.035;
         double sweep = angle + Math.sin(time * 0.049 + h2 * 9.0) * 0.178;
         double radius = 1.325 + h1 * 0.885;
         double height = 0.0 + Math.sin(time * 0.03 + h0 * 6.28318) * 0.23;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.347) * radius * 0.735, height + 0.416, Math.sin(sweep + 0.347) * radius * 0.735);
         Vec3 p2 = center.add(Math.cos(sweep - 0.397) * radius * 1.105, height * 0.55 + 0.14, Math.sin(sweep - 0.397) * radius * 1.105);
         float live = alpha * (0.171F + h2 * 0.083F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.0175F, 234, 39, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0077F, 255, 242, 255, live);
         if ((43 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.053F, 255, 70, 230, live * 1.25F);
         }

         if ((43 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.058F, 234, 39, 255, live * 0.95F);
         }

         if ((43 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.826, 0.0085F, 234, 39, 255, live * 0.3F, 56
            );
         }

         return wrote;
      }

      private static boolean auraLayer44(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 71628);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 73332);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 75124);
         double angle = h0 * Math.PI * 2.0 + time * 0.01;
         double sweep = angle + Math.sin(time * 0.052 + h2 * 9.0) * 0.196;
         double radius = 1.4 + h1 * 0.94;
         double height = 0.1 + Math.sin(time * 0.034 + h0 * 6.28318) * 0.26;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.368) * radius * 0.76, height + 0.444, Math.sin(sweep + 0.368) * radius * 0.76);
         Vec3 p2 = center.add(Math.cos(sweep - 0.416) * radius * 1.13, height * 0.55 + 0.16, Math.sin(sweep - 0.416) * radius * 1.13);
         float live = alpha * (0.182F + h2 * 0.091F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.02F, 122, 80, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0094F, 255, 251, 255, live);
         if ((44 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.059F, 255, 70, 230, live * 1.25F);
         }

         if ((44 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.064F, 122, 80, 255, live * 0.95F);
         }

         if ((44 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.844, 0.01F, 122, 80, 255, live * 0.3F, 64
            );
         }

         return wrote;
      }

      private static boolean auraLayer45(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 71665);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 73385);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 75195);
         double angle = h0 * Math.PI * 2.0 + time * 0.0125;
         double sweep = angle + Math.sin(time * 0.028 + h2 * 9.0) * 0.214;
         double radius = 1.475 + h1 * 0.995;
         double height = 0.2 + Math.sin(time * 0.038 + h0 * 6.28318) * 0.29;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.389) * radius * 0.785, height + 0.22, Math.sin(sweep + 0.389) * radius * 0.785);
         Vec3 p2 = center.add(Math.cos(sweep - 0.435) * radius * 1.155, height * 0.55 + 0.08, Math.sin(sweep - 0.435) * radius * 1.155);
         float live = alpha * (0.193F + h2 * 0.099F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.0225F, 151, 121, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0111F, 255, 180, 255, live);
         if ((45 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.065F, 255, 70, 230, live * 1.25F);
         }

         if ((45 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.07F, 151, 121, 255, live * 0.95F);
         }

         if ((45 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.862, 0.0115F, 151, 121, 255, live * 0.3F, 72
            );
         }

         return wrote;
      }

      private static boolean auraLayer46(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 71702);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 73438);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 75266);
         double angle = h0 * Math.PI * 2.0 + time * 0.015;
         double sweep = angle + Math.sin(time * 0.031 + h2 * 9.0) * 0.232;
         double radius = 1.55 + h1 * 1.05;
         double height = 0.3 + Math.sin(time * 0.042 + h0 * 6.28318) * 0.32;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.41) * radius * 0.81, height + 0.248, Math.sin(sweep + 0.41) * radius * 0.81);
         Vec3 p2 = center.add(Math.cos(sweep - 0.454) * radius * 1.18, height * 0.55 + 0.1, Math.sin(sweep - 0.454) * radius * 1.18);
         float live = alpha * (0.204F + h2 * 0.107F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.025F, 180, 46, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0128F, 255, 189, 255, live);
         if ((46 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.071F, 255, 70, 230, live * 1.25F);
         }

         if ((46 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.076F, 180, 46, 255, live * 0.95F);
         }

         if ((46 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.88, 0.013F, 180, 46, 255, live * 0.3F, 80
            );
         }

         return wrote;
      }

      private static boolean auraLayer47(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 71739);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 73491);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 75337);
         double angle = h0 * Math.PI * 2.0 + time * 0.0175;
         double sweep = angle + Math.sin(time * 0.034 + h2 * 9.0) * 0.25;
         double radius = 1.625 + h1 * 1.105;
         double height = 0.4 + Math.sin(time * 0.046 + h0 * 6.28318) * 0.35;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.431) * radius * 0.835, height + 0.276, Math.sin(sweep + 0.431) * radius * 0.835);
         Vec3 p2 = center.add(Math.cos(sweep - 0.473) * radius * 1.205, height * 0.55 + 0.12, Math.sin(sweep - 0.473) * radius * 1.205);
         float live = alpha * (0.215F + h2 * 0.115F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.0275F, 209, 87, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0145F, 255, 198, 255, live);
         if ((47 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.077F, 255, 70, 230, live * 1.25F);
         }

         if ((47 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.082F, 209, 87, 255, live * 0.95F);
         }

         if ((47 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.898, 0.0145F, 209, 87, 255, live * 0.3F, 88
            );
         }

         return wrote;
      }

      private static boolean auraLayer48(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 71776);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 73544);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 75408);
         double angle = h0 * Math.PI * 2.0 + time * 0.02;
         double sweep = angle + Math.sin(time * 0.037 + h2 * 9.0) * 0.268;
         double radius = 1.7 + h1 * 1.16;
         double height = 0.5 + Math.sin(time * 0.018 + h0 * 6.28318) * 0.2;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.2) * radius * 0.66, height + 0.304, Math.sin(sweep + 0.2) * radius * 0.66);
         Vec3 p2 = center.add(Math.cos(sweep - 0.492) * radius * 1.08, height * 0.55 + 0.14, Math.sin(sweep - 0.492) * radius * 1.08);
         float live = alpha * (0.226F + h2 * 0.075F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.01F, 238, 128, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.006F, 255, 207, 255, live);
         if ((48 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.083F, 255, 70, 230, live * 1.25F);
         }

         if ((48 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.04F, 238, 128, 255, live * 0.95F);
         }

         if ((48 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.7, 0.007F, 238, 128, 255, live * 0.3F, 48
            );
         }

         return wrote;
      }

      private static boolean auraLayer49(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 71813);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 73597);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 75479);
         double angle = h0 * Math.PI * 2.0 + time * 0.0225;
         double sweep = angle + Math.sin(time * 0.04 + h2 * 9.0) * 0.16;
         double radius = 1.775 + h1 * 1.215;
         double height = 0.6 + Math.sin(time * 0.022 + h0 * 6.28318) * 0.23;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.221) * radius * 0.685, height + 0.332, Math.sin(sweep + 0.221) * radius * 0.685);
         Vec3 p2 = center.add(Math.cos(sweep - 0.511) * radius * 1.105, height * 0.55 + 0.16, Math.sin(sweep - 0.511) * radius * 1.105);
         float live = alpha * (0.237F + h2 * 0.083F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.0125F, 126, 53, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0077F, 255, 76, 240, live);
         if ((49 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.089F, 255, 70, 230, live * 1.25F);
         }

         if ((49 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.046F, 126, 53, 255, live * 0.95F);
         }

         if ((49 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.718, 0.0085F, 126, 53, 255, live * 0.3F, 56
            );
         }

         return wrote;
      }

      private static boolean auraLayer50(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 71850);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 73650);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 75550);
         double angle = h0 * Math.PI * 2.0 + time * 0.025;
         double sweep = angle + Math.sin(time * 0.043 + h2 * 9.0) * 0.178;
         double radius = 1.85 + h1 * 0.72;
         double height = 0.7 + Math.sin(time * 0.026 + h0 * 6.28318) * 0.26;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.242) * radius * 0.71, height + 0.36, Math.sin(sweep + 0.242) * radius * 0.71);
         Vec3 p2 = center.add(Math.cos(sweep - 0.34) * radius * 1.13, height * 0.55 + 0.08, Math.sin(sweep - 0.34) * radius * 1.13);
         float live = alpha * (0.248F + h2 * 0.091F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.015F, 155, 94, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0094F, 255, 96, 230, live);
         if ((50 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.035F, 255, 70, 230, live * 1.25F);
         }

         if ((50 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.052F, 155, 94, 255, live * 0.95F);
         }

         if ((50 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.736, 0.01F, 155, 94, 255, live * 0.3F, 64
            );
         }

         return wrote;
      }

      private static boolean auraLayer51(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 71887);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 73703);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 75621);
         double angle = h0 * Math.PI * 2.0 + time * 0.0275;
         double sweep = angle + Math.sin(time * 0.046 + h2 * 9.0) * 0.196;
         double radius = 1.925 + h1 * 0.775;
         double height = 0.8 + Math.sin(time * 0.03 + h0 * 6.28318) * 0.29;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.263) * radius * 0.735, height + 0.388, Math.sin(sweep + 0.263) * radius * 0.735);
         Vec3 p2 = center.add(Math.cos(sweep - 0.359) * radius * 1.155, height * 0.55 + 0.1, Math.sin(sweep - 0.359) * radius * 1.155);
         float live = alpha * (0.259F + h2 * 0.099F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.0175F, 184, 19, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0111F, 255, 234, 255, live);
         if ((51 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.041F, 255, 70, 230, live * 1.25F);
         }

         if ((51 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.058F, 184, 19, 255, live * 0.95F);
         }

         if ((51 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.754, 0.0115F, 184, 19, 255, live * 0.3F, 72
            );
         }

         return wrote;
      }

      private static boolean auraLayer52(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 71924);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 73756);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 75692);
         double angle = h0 * Math.PI * 2.0 + time * 0.03;
         double sweep = angle + Math.sin(time * 0.049 + h2 * 9.0) * 0.214;
         double radius = 2.0 + h1 * 0.83;
         double height = 0.9 + Math.sin(time * 0.034 + h0 * 6.28318) * 0.32;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.284) * radius * 0.76, height + 0.416, Math.sin(sweep + 0.284) * radius * 0.76);
         Vec3 p2 = center.add(Math.cos(sweep - 0.378) * radius * 1.18, height * 0.55 + 0.12, Math.sin(sweep - 0.378) * radius * 1.18);
         float live = alpha * (0.27F + h2 * 0.107F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.02F, 213, 60, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0128F, 255, 243, 255, live);
         if ((52 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.047F, 255, 70, 230, live * 1.25F);
         }

         if ((52 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.064F, 213, 60, 255, live * 0.95F);
         }

         if ((52 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.772, 0.013F, 213, 60, 255, live * 0.3F, 80
            );
         }

         return wrote;
      }

      private static boolean auraLayer53(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 71961);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 73809);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 75763);
         double angle = h0 * Math.PI * 2.0 + time * 0.0325;
         double sweep = angle + Math.sin(time * 0.052 + h2 * 9.0) * 0.232;
         double radius = 2.075 + h1 * 0.885;
         double height = 1.0 + Math.sin(time * 0.038 + h0 * 6.28318) * 0.35;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.305) * radius * 0.785, height + 0.444, Math.sin(sweep + 0.305) * radius * 0.785);
         Vec3 p2 = center.add(Math.cos(sweep - 0.397) * radius * 1.205, height * 0.55 + 0.14, Math.sin(sweep - 0.397) * radius * 1.205);
         float live = alpha * (0.281F + h2 * 0.115F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.0225F, 242, 101, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0145F, 255, 252, 255, live);
         if ((53 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.053F, 255, 70, 230, live * 1.25F);
         }

         if ((53 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.07F, 242, 101, 255, live * 0.95F);
         }

         if ((53 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.79, 0.0145F, 242, 101, 255, live * 0.3F, 88
            );
         }

         return wrote;
      }

      private static boolean auraLayer54(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 71998);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 73862);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 75834);
         double angle = h0 * Math.PI * 2.0 + time * 0.035;
         double sweep = angle + Math.sin(time * 0.028 + h2 * 9.0) * 0.25;
         double radius = 0.8 + h1 * 0.94;
         double height = 1.1 + Math.sin(time * 0.042 + h0 * 6.28318) * 0.2;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.326) * radius * 0.81, height + 0.22, Math.sin(sweep + 0.326) * radius * 0.81);
         Vec3 p2 = center.add(Math.cos(sweep - 0.416) * radius * 1.08, height * 0.55 + 0.16, Math.sin(sweep - 0.416) * radius * 1.08);
         float live = alpha * (0.292F + h2 * 0.075F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.025F, 130, 26, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.006F, 255, 181, 255, live);
         if ((54 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.059F, 255, 70, 230, live * 1.25F);
         }

         if ((54 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.076F, 130, 26, 255, live * 0.95F);
         }

         if ((54 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.808, 0.007F, 130, 26, 255, live * 0.3F, 48
            );
         }

         return wrote;
      }

      private static boolean auraLayer55(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 72035);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 73915);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 75905);
         double angle = h0 * Math.PI * 2.0 + time * 0.01;
         double sweep = angle + Math.sin(time * 0.031 + h2 * 9.0) * 0.268;
         double radius = 0.875 + h1 * 0.995;
         double height = 1.2 + Math.sin(time * 0.046 + h0 * 6.28318) * 0.23;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.347) * radius * 0.835, height + 0.248, Math.sin(sweep + 0.347) * radius * 0.835);
         Vec3 p2 = center.add(Math.cos(sweep - 0.435) * radius * 1.105, height * 0.55 + 0.08, Math.sin(sweep - 0.435) * radius * 1.105);
         float live = alpha * (0.303F + h2 * 0.083F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.0275F, 159, 67, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0077F, 255, 66, 235, live);
         if ((55 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.065F, 255, 70, 230, live * 1.25F);
         }

         if ((55 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.082F, 159, 67, 255, live * 0.95F);
         }

         if ((55 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.826, 0.0085F, 159, 67, 255, live * 0.3F, 56
            );
         }

         return wrote;
      }

      private static boolean auraLayer56(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 72072);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 73968);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 75976);
         double angle = h0 * Math.PI * 2.0 + time * 0.0125;
         double sweep = angle + Math.sin(time * 0.034 + h2 * 9.0) * 0.16;
         double radius = 0.95 + h1 * 1.05;
         double height = 1.3 + Math.sin(time * 0.018 + h0 * 6.28318) * 0.26;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.368) * radius * 0.66, height + 0.276, Math.sin(sweep + 0.368) * radius * 0.66);
         Vec3 p2 = center.add(Math.cos(sweep - 0.454) * radius * 1.13, height * 0.55 + 0.1, Math.sin(sweep - 0.454) * radius * 1.13);
         float live = alpha * (0.16F + h2 * 0.091F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.01F, 188, 108, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0094F, 255, 199, 255, live);
         if ((56 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.071F, 255, 70, 230, live * 1.25F);
         }

         if ((56 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.04F, 188, 108, 255, live * 0.95F);
         }

         if ((56 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.844, 0.01F, 188, 108, 255, live * 0.3F, 64
            );
         }

         return wrote;
      }

      private static boolean auraLayer57(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 72109);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 74021);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 76047);
         double angle = h0 * Math.PI * 2.0 + time * 0.015;
         double sweep = angle + Math.sin(time * 0.037 + h2 * 9.0) * 0.178;
         double radius = 1.025 + h1 * 1.105;
         double height = 1.4 + Math.sin(time * 0.022 + h0 * 6.28318) * 0.29;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.389) * radius * 0.685, height + 0.304, Math.sin(sweep + 0.389) * radius * 0.685);
         Vec3 p2 = center.add(Math.cos(sweep - 0.473) * radius * 1.155, height * 0.55 + 0.12, Math.sin(sweep - 0.473) * radius * 1.155);
         float live = alpha * (0.171F + h2 * 0.099F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.0125F, 217, 33, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0111F, 255, 208, 255, live);
         if ((57 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.077F, 255, 70, 230, live * 1.25F);
         }

         if ((57 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.046F, 217, 33, 255, live * 0.95F);
         }

         if ((57 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.862, 0.0115F, 217, 33, 255, live * 0.3F, 72
            );
         }

         return wrote;
      }

      private static boolean auraLayer58(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 72146);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 74074);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 76118);
         double angle = h0 * Math.PI * 2.0 + time * 0.0175;
         double sweep = angle + Math.sin(time * 0.04 + h2 * 9.0) * 0.196;
         double radius = 1.1 + h1 * 1.16;
         double height = -1.4 + Math.sin(time * 0.026 + h0 * 6.28318) * 0.32;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.41) * radius * 0.71, height + 0.332, Math.sin(sweep + 0.41) * radius * 0.71);
         Vec3 p2 = center.add(Math.cos(sweep - 0.492) * radius * 1.18, height * 0.55 + 0.14, Math.sin(sweep - 0.492) * radius * 1.18);
         float live = alpha * (0.182F + h2 * 0.107F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.015F, 246, 74, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0128F, 255, 217, 255, live);
         if ((58 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.083F, 255, 70, 230, live * 1.25F);
         }

         if ((58 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.052F, 246, 74, 255, live * 0.95F);
         }

         if ((58 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.88, 0.013F, 246, 74, 255, live * 0.3F, 80
            );
         }

         return wrote;
      }

      private static boolean auraLayer59(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 72183);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 74127);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 76189);
         double angle = h0 * Math.PI * 2.0 + time * 0.02;
         double sweep = angle + Math.sin(time * 0.043 + h2 * 9.0) * 0.214;
         double radius = 1.175 + h1 * 1.215;
         double height = -1.3 + Math.sin(time * 0.03 + h0 * 6.28318) * 0.35;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.431) * radius * 0.735, height + 0.36, Math.sin(sweep + 0.431) * radius * 0.735);
         Vec3 p2 = center.add(Math.cos(sweep - 0.511) * radius * 1.205, height * 0.55 + 0.16, Math.sin(sweep - 0.511) * radius * 1.205);
         float live = alpha * (0.193F + h2 * 0.115F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.0175F, 134, 115, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0145F, 96, 222, 255, live);
         if ((59 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.089F, 255, 70, 230, live * 1.25F);
         }

         if ((59 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.058F, 134, 115, 255, live * 0.95F);
         }

         if ((59 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.898, 0.0145F, 134, 115, 255, live * 0.3F, 88
            );
         }

         return wrote;
      }

      private static boolean auraLayer60(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 72220);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 74180);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 76260);
         double angle = h0 * Math.PI * 2.0 + time * 0.0225;
         double sweep = angle + Math.sin(time * 0.046 + h2 * 9.0) * 0.232;
         double radius = 1.25 + h1 * 0.72;
         double height = -1.2 + Math.sin(time * 0.034 + h0 * 6.28318) * 0.2;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.2) * radius * 0.76, height + 0.388, Math.sin(sweep + 0.2) * radius * 0.76);
         Vec3 p2 = center.add(Math.cos(sweep - 0.34) * radius * 1.08, height * 0.55 + 0.08, Math.sin(sweep - 0.34) * radius * 1.08);
         float live = alpha * (0.204F + h2 * 0.075F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.02F, 163, 40, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.006F, 118, 238, 255, live);
         if ((60 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.035F, 255, 70, 230, live * 1.25F);
         }

         if ((60 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.064F, 163, 40, 255, live * 0.95F);
         }

         if ((60 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.7, 0.007F, 163, 40, 255, live * 0.3F, 48
            );
         }

         return wrote;
      }

      private static boolean auraLayer61(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 72257);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 74233);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 76331);
         double angle = h0 * Math.PI * 2.0 + time * 0.025;
         double sweep = angle + Math.sin(time * 0.049 + h2 * 9.0) * 0.25;
         double radius = 1.325 + h1 * 0.775;
         double height = -1.1 + Math.sin(time * 0.038 + h0 * 6.28318) * 0.23;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.221) * radius * 0.785, height + 0.416, Math.sin(sweep + 0.221) * radius * 0.785);
         Vec3 p2 = center.add(Math.cos(sweep - 0.359) * radius * 1.105, height * 0.55 + 0.1, Math.sin(sweep - 0.359) * radius * 1.105);
         float live = alpha * (0.215F + h2 * 0.083F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.0225F, 192, 81, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0077F, 255, 94, 232, live);
         if ((61 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.041F, 255, 70, 230, live * 1.25F);
         }

         if ((61 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.07F, 192, 81, 255, live * 0.95F);
         }

         if ((61 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.718, 0.0085F, 192, 81, 255, live * 0.3F, 56
            );
         }

         return wrote;
      }

      private static boolean auraLayer62(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 72294);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 74286);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 76402);
         double angle = h0 * Math.PI * 2.0 + time * 0.0275;
         double sweep = angle + Math.sin(time * 0.052 + h2 * 9.0) * 0.268;
         double radius = 1.4 + h1 * 0.83;
         double height = -1.0 + Math.sin(time * 0.042 + h0 * 6.28318) * 0.26;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.242) * radius * 0.81, height + 0.444, Math.sin(sweep + 0.242) * radius * 0.81);
         Vec3 p2 = center.add(Math.cos(sweep - 0.378) * radius * 1.13, height * 0.55 + 0.12, Math.sin(sweep - 0.378) * radius * 1.13);
         float live = alpha * (0.226F + h2 * 0.091F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.025F, 221, 122, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0094F, 255, 253, 255, live);
         if ((62 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.047F, 255, 70, 230, live * 1.25F);
         }

         if ((62 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.076F, 221, 122, 255, live * 0.95F);
         }

         if ((62 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.736, 0.01F, 221, 122, 255, live * 0.3F, 64
            );
         }

         return wrote;
      }

      private static boolean auraLayer63(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 72331);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 74339);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 76473);
         double angle = h0 * Math.PI * 2.0 + time * 0.03;
         double sweep = angle + Math.sin(time * 0.028 + h2 * 9.0) * 0.16;
         double radius = 1.475 + h1 * 0.885;
         double height = -0.9 + Math.sin(time * 0.046 + h0 * 6.28318) * 0.29;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.263) * radius * 0.835, height + 0.22, Math.sin(sweep + 0.263) * radius * 0.835);
         Vec3 p2 = center.add(Math.cos(sweep - 0.397) * radius * 1.155, height * 0.55 + 0.14, Math.sin(sweep - 0.397) * radius * 1.155);
         float live = alpha * (0.237F + h2 * 0.099F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.0275F, 250, 47, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0111F, 255, 182, 255, live);
         if ((63 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.053F, 255, 70, 230, live * 1.25F);
         }

         if ((63 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.082F, 250, 47, 255, live * 0.95F);
         }

         if ((63 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.754, 0.0115F, 250, 47, 255, live * 0.3F, 72
            );
         }

         return wrote;
      }

      private static boolean auraLayer64(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 72368);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 74392);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 76544);
         double angle = h0 * Math.PI * 2.0 + time * 0.0325;
         double sweep = angle + Math.sin(time * 0.031 + h2 * 9.0) * 0.178;
         double radius = 1.55 + h1 * 0.94;
         double height = -0.8 + Math.sin(time * 0.018 + h0 * 6.28318) * 0.32;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.284) * radius * 0.66, height + 0.248, Math.sin(sweep + 0.284) * radius * 0.66);
         Vec3 p2 = center.add(Math.cos(sweep - 0.416) * radius * 1.18, height * 0.55 + 0.16, Math.sin(sweep - 0.416) * radius * 1.18);
         float live = alpha * (0.248F + h2 * 0.107F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.01F, 138, 88, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0128F, 255, 191, 255, live);
         if ((64 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.059F, 255, 70, 230, live * 1.25F);
         }

         if ((64 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.04F, 138, 88, 255, live * 0.95F);
         }

         if ((64 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.772, 0.013F, 138, 88, 255, live * 0.3F, 80
            );
         }

         return wrote;
      }

      private static boolean auraLayer65(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 72405);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 74445);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 76615);
         double angle = h0 * Math.PI * 2.0 + time * 0.035;
         double sweep = angle + Math.sin(time * 0.034 + h2 * 9.0) * 0.196;
         double radius = 1.625 + h1 * 0.995;
         double height = -0.7 + Math.sin(time * 0.022 + h0 * 6.28318) * 0.35;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.305) * radius * 0.685, height + 0.276, Math.sin(sweep + 0.305) * radius * 0.685);
         Vec3 p2 = center.add(Math.cos(sweep - 0.435) * radius * 1.205, height * 0.55 + 0.08, Math.sin(sweep - 0.435) * radius * 1.205);
         float live = alpha * (0.259F + h2 * 0.115F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.0125F, 167, 129, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0145F, 255, 200, 255, live);
         if ((65 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.065F, 255, 70, 230, live * 1.25F);
         }

         if ((65 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.046F, 167, 129, 255, live * 0.95F);
         }

         if ((65 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.79, 0.0145F, 167, 129, 255, live * 0.3F, 88
            );
         }

         return wrote;
      }

      private static boolean auraLayer66(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 72442);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 74498);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 76686);
         double angle = h0 * Math.PI * 2.0 + time * 0.01;
         double sweep = angle + Math.sin(time * 0.037 + h2 * 9.0) * 0.214;
         double radius = 1.7 + h1 * 1.05;
         double height = -0.6 + Math.sin(time * 0.026 + h0 * 6.28318) * 0.2;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.326) * radius * 0.71, height + 0.304, Math.sin(sweep + 0.326) * radius * 0.71);
         Vec3 p2 = center.add(Math.cos(sweep - 0.454) * radius * 1.08, height * 0.55 + 0.1, Math.sin(sweep - 0.454) * radius * 1.08);
         float live = alpha * (0.27F + h2 * 0.075F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.015F, 196, 54, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.006F, 255, 209, 255, live);
         if ((66 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.071F, 255, 70, 230, live * 1.25F);
         }

         if ((66 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.052F, 196, 54, 255, live * 0.95F);
         }

         if ((66 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.808, 0.007F, 196, 54, 255, live * 0.3F, 48
            );
         }

         return wrote;
      }

      private static boolean auraLayer67(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 72479);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 74551);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 76757);
         double angle = h0 * Math.PI * 2.0 + time * 0.0125;
         double sweep = angle + Math.sin(time * 0.04 + h2 * 9.0) * 0.232;
         double radius = 1.775 + h1 * 1.105;
         double height = -0.5 + Math.sin(time * 0.03 + h0 * 6.28318) * 0.23;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.347) * radius * 0.735, height + 0.332, Math.sin(sweep + 0.347) * radius * 0.735);
         Vec3 p2 = center.add(Math.cos(sweep - 0.473) * radius * 1.105, height * 0.55 + 0.12, Math.sin(sweep - 0.473) * radius * 1.105);
         float live = alpha * (0.281F + h2 * 0.083F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.0175F, 225, 95, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0077F, 110, 215, 255, live);
         if ((67 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.077F, 255, 70, 230, live * 1.25F);
         }

         if ((67 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.058F, 225, 95, 255, live * 0.95F);
         }

         if ((67 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.826, 0.0085F, 225, 95, 255, live * 0.3F, 56
            );
         }

         return wrote;
      }

      private static boolean auraLayer68(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 72516);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 74604);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 76828);
         double angle = h0 * Math.PI * 2.0 + time * 0.015;
         double sweep = angle + Math.sin(time * 0.043 + h2 * 9.0) * 0.25;
         double radius = 1.85 + h1 * 1.16;
         double height = -0.4 + Math.sin(time * 0.034 + h0 * 6.28318) * 0.26;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.368) * radius * 0.76, height + 0.36, Math.sin(sweep + 0.368) * radius * 0.76);
         Vec3 p2 = center.add(Math.cos(sweep - 0.492) * radius * 1.13, height * 0.55 + 0.14, Math.sin(sweep - 0.492) * radius * 1.13);
         float live = alpha * (0.292F + h2 * 0.091F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.02F, 254, 20, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0094F, 255, 227, 255, live);
         if ((68 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.083F, 255, 70, 230, live * 1.25F);
         }

         if ((68 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.064F, 254, 20, 255, live * 0.95F);
         }

         if ((68 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.844, 0.01F, 254, 20, 255, live * 0.3F, 64
            );
         }

         return wrote;
      }

      private static boolean auraLayer69(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 72553);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 74657);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 76899);
         double angle = h0 * Math.PI * 2.0 + time * 0.0175;
         double sweep = angle + Math.sin(time * 0.046 + h2 * 9.0) * 0.268;
         double radius = 1.925 + h1 * 1.215;
         double height = -0.3 + Math.sin(time * 0.038 + h0 * 6.28318) * 0.29;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.389) * radius * 0.785, height + 0.388, Math.sin(sweep + 0.389) * radius * 0.785);
         Vec3 p2 = center.add(Math.cos(sweep - 0.511) * radius * 1.155, height * 0.55 + 0.16, Math.sin(sweep - 0.511) * radius * 1.155);
         float live = alpha * (0.303F + h2 * 0.099F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.0225F, 142, 61, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0111F, 255, 112, 236, live);
         if ((69 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.089F, 255, 70, 230, live * 1.25F);
         }

         if ((69 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.07F, 142, 61, 255, live * 0.95F);
         }

         if ((69 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.862, 0.0115F, 142, 61, 255, live * 0.3F, 72
            );
         }

         return wrote;
      }

      private static boolean auraLayer70(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 72590);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 74710);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 76970);
         double angle = h0 * Math.PI * 2.0 + time * 0.02;
         double sweep = angle + Math.sin(time * 0.049 + h2 * 9.0) * 0.16;
         double radius = 2.0 + h1 * 0.72;
         double height = -0.2 + Math.sin(time * 0.042 + h0 * 6.28318) * 0.32;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.41) * radius * 0.81, height + 0.416, Math.sin(sweep + 0.41) * radius * 0.81);
         Vec3 p2 = center.add(Math.cos(sweep - 0.34) * radius * 1.18, height * 0.55 + 0.08, Math.sin(sweep - 0.34) * radius * 1.18);
         float live = alpha * (0.16F + h2 * 0.107F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.025F, 171, 102, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0128F, 86, 235, 255, live);
         if ((70 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.035F, 255, 70, 230, live * 1.25F);
         }

         if ((70 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.076F, 171, 102, 255, live * 0.95F);
         }

         if ((70 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.88, 0.013F, 171, 102, 255, live * 0.3F, 80
            );
         }

         return wrote;
      }

      private static boolean auraLayer71(BufferBuilder buffer, Matrix4f matrix, Vec3 center, float time, long seed, float alpha) {
         boolean wrote = false;
         float h0 = MichaelHalberdShotVfxClient.H.hash(seed, 72627);
         float h1 = MichaelHalberdShotVfxClient.H.hash(seed, 74763);
         float h2 = MichaelHalberdShotVfxClient.H.hash(seed, 77041);
         double angle = h0 * Math.PI * 2.0 + time * 0.0225;
         double sweep = angle + Math.sin(time * 0.052 + h2 * 9.0) * 0.178;
         double radius = 2.075 + h1 * 0.775;
         double height = -0.1 + Math.sin(time * 0.046 + h0 * 6.28318) * 0.35;
         Vec3 p0 = center.add(Math.cos(sweep) * radius, height, Math.sin(sweep) * radius);
         Vec3 p1 = center.add(Math.cos(sweep + 0.431) * radius * 0.835, height + 0.444, Math.sin(sweep + 0.431) * radius * 0.835);
         Vec3 p2 = center.add(Math.cos(sweep - 0.359) * radius * 1.205, height * 0.55 + 0.1, Math.sin(sweep - 0.359) * radius * 1.205);
         float live = alpha * (0.171F + h2 * 0.115F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p0, p1, 0.0275F, 200, 27, 255, live * 0.8F);
         wrote |= MichaelHalberdShotVfxClient.H.ribbon(buffer, matrix, p1, p2, 0.0145F, 255, 254, 255, live);
         if ((71 + (int)time) % 3 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p1, 0.041F, 255, 70, 230, live * 1.25F);
         }

         if ((71 + (int)time) % 5 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.diamond(buffer, matrix, p0, 0.082F, 200, 27, 255, live * 0.95F);
         }

         if ((71 + (int)time) % 9 == 0) {
            wrote |= MichaelHalberdShotVfxClient.H.ring(
               buffer, matrix, center.add(0.0, height * 0.24, 0.0), radius * 0.898, 0.0145F, 200, 27, 255, live * 0.3F, 88
            );
         }

         return wrote;
      }
   }

   private static final class Shot {
      private final int casterId;
      private final Vec3 a;
      private final Vec3 b;
      private final int life;
      private final long seed;
      private int age;

      private Shot(MichaelHalberdShotVfxPayload payload) {
         this.casterId = payload.casterId();
         this.a = new Vec3(payload.x(), payload.y(), payload.z());
         Vec3 rawEnd = new Vec3(payload.x2(), payload.y2(), payload.z2());
         Vec3 rawDir = rawEnd.subtract(this.a);
         double rawDistance = rawDir.length();
         if (rawDistance < 0.001) {
            rawDir = new Vec3(0.0, 0.0, 1.0);
         } else {
            rawDir = rawDir.normalize();
         }

         double visualRange = Math.max(100.0, Math.max(payload.range(), rawDistance));
         this.b = this.a.add(rawDir.scale(visualRange));
         this.life = Math.max(52, Math.min(82, (int)Math.ceil(visualRange / 2.75) + 24));
         this.seed = payload.seed();
      }

      private boolean render(Minecraft mc, BufferBuilder buffer, Matrix4f matrix, Vec3 cam, float partial) {
         Vec3 start = this.a;
         Vec3 end = this.b;
         Vec3 dir = end.subtract(start);
         if (dir.lengthSqr() < 0.001) {
            Entity caster = this.casterId == -1 ? null : mc.level.getEntity(this.casterId);
            dir = caster == null ? new Vec3(0.0, 0.0, 1.0) : caster.getLookAngle();
         }

         if (dir.lengthSqr() < 0.001) {
            dir = new Vec3(0.0, 0.0, 1.0);
         }

         dir = dir.normalize();
         Vec3 side = dir.cross(new Vec3(0.0, 1.0, 0.0));
         if (side.lengthSqr() < 0.001) {
            side = dir.cross(new Vec3(1.0, 0.0, 0.0));
         }

         if (side.lengthSqr() < 0.001) {
            side = new Vec3(1.0, 0.0, 0.0);
         }

         side = side.normalize();
         Vec3 up = side.cross(dir).normalize();
         float t = this.age + partial;
         double distance = Math.max(1.0, start.distanceTo(end));
         float travelTicks = Mth.clamp((float)(distance / 4.75), 8.0F, 24.0F);
         float rawProgress = Math.min(1.0F, t / travelTicks);
         float progress = rawProgress < 0.18F ? rawProgress * 1.16F : 0.2088F + (rawProgress - 0.18F) * 0.9649F;
         progress = Math.min(1.0F, progress);
         float born = MichaelHalberdShotVfxClient.H.smooth(Math.min(1.0F, t / 0.85F));
         float fade = MichaelHalberdShotVfxClient.H.smooth(Math.min(1.0F, (this.life - t) / 11.0F));
         float alpha = born * fade;
         if (alpha <= 0.002F) {
            return false;
         }

         Vec3 headWorld = start.lerp(end, progress);
         float wakeStep = (float)Math.min(0.14, 3.85 / distance);
         Vec3 prevWorld = start.lerp(end, Math.max(0.0F, progress - wakeStep));
         Vec3 head = MichaelHalberdShotVfxClient.H.rel(headWorld, cam);
         Vec3 prev = MichaelHalberdShotVfxClient.H.rel(prevWorld, cam);
         double spin = t * 0.42 + (this.seed & 2047L) * 0.0031;
         boolean wrote = false;

         for (int i = 0; i < 3; i++) {
            double turn = spin + i * 2.09439510239;
            Vec3 ringSide = side.scale(Math.cos(turn)).add(up.scale(Math.sin(turn))).normalize();
            Vec3 ringUp = ringSide.cross(dir).normalize();
            double r = 1.08 + i * 0.18 + Math.sin(t * 0.18 + i) * 0.04;
            int cr = i == 0 ? 255 : (i == 1 ? 114 : 232);
            int cg = i == 0 ? 68 : (i == 1 ? 225 : 78);
            int cb = 255;
            wrote |= MichaelHalberdShotVfxClient.H.orientedRing(
               buffer, matrix, head.add(dir.scale((i - 1) * 0.035)), ringSide, ringUp, r, 0.02F + i * 0.005F, cr, cg, cb, alpha * (0.54F - i * 0.08F), 72
            );
         }

         wrote |= MichaelHalberdShotVfxClient.H.diamond3D(buffer, matrix, head, dir, side, up, 0.82F, 255, 70, 235, alpha * 0.92F);
         wrote |= MichaelHalberdShotVfxClient.H.diamond3D(buffer, matrix, head.add(dir.scale(0.18)), dir, side, up, 0.48F, 92, 232, 255, alpha * 0.76F);
         wrote |= MichaelHalberdShotVfxClient.H.spinningStar(buffer, matrix, head, dir, side, up, 1.35F, spin, 255, 76, 238, alpha * 0.9F);
         wrote |= MichaelHalberdShotVfxClient.H.spinningStar(
            buffer, matrix, head.add(dir.scale(-0.1)), dir, side, up, 1.05F, -spin * 1.22, 92, 226, 255, alpha * 0.7F
         );

         for (int i = 0; i < 34; i++) {
            float h0 = MichaelHalberdShotVfxClient.H.hash(this.seed, 10000 + i * 37);
            float h1 = MichaelHalberdShotVfxClient.H.hash(this.seed, 11000 + i * 41);
            float h2 = MichaelHalberdShotVfxClient.H.hash(this.seed, 12000 + i * 43);
            float h3 = MichaelHalberdShotVfxClient.H.hash(this.seed, 13000 + i * 47);
            double a0 = h0 * Math.PI * 2.0 + spin * (0.45 + h2 * 0.8) * ((i & 1) == 0 ? 1.0 : -1.0);
            double shell = 0.2 + h1 * 1.18;
            double back = h2 * 0.42;
            Vec3 p = head.subtract(dir.scale(back)).add(side.scale(Math.cos(a0) * shell)).add(up.scale(Math.sin(a0) * shell)).add(dir.scale((h3 - 0.5) * 0.1));
            int palette = i % 5;
            int r = palette == 0 ? 255 : (palette == 1 ? 94 : (palette == 2 ? 178 : (palette == 3 ? 255 : 120)));
            int g = palette == 0 ? 72 : (palette == 1 ? 226 : (palette == 2 ? 62 : (palette == 3 ? 116 : 216)));
            int bl = 255;
            wrote |= MichaelHalberdShotVfxClient.H.star(buffer, matrix, p, 0.032F + h3 * 0.075F, r, g, bl, alpha * (0.5F + h2 * 0.36F));
         }

         for (int i = 0; i < 16; i++) {
            float h0 = MichaelHalberdShotVfxClient.H.hash(this.seed, 15000 + i * 53);
            float h1 = MichaelHalberdShotVfxClient.H.hash(this.seed, 16000 + i * 59);
            float h2 = MichaelHalberdShotVfxClient.H.hash(this.seed, 17000 + i * 61);
            double a0 = h0 * Math.PI * 2.0 - spin * (0.2 + h1 * 0.28);
            double back = 0.24 + i * 0.105;
            double spread = (1.0 - i / 16.0) * (0.2 + h2 * 0.34);
            Vec3 p = head.subtract(dir.scale(back)).add(side.scale(Math.cos(a0) * spread)).add(up.scale(Math.sin(a0) * spread));
            wrote |= MichaelHalberdShotVfxClient.H.star(
               buffer, matrix, p, 0.022F + h1 * 0.046F, i % 2 == 0 ? 255 : 88, i % 2 == 0 ? 76 : 230, 255, alpha * (0.46F - i * 0.018F)
            );
         }

         float wakeAlpha = alpha * 0.22F;
         return wrote | MichaelHalberdShotVfxClient.H.spinningStar(buffer, matrix, prev, dir, side, up, 0.78F, spin * -0.55, 145, 42, 255, wakeAlpha);
      }
   }
}
