package com.blorb.morerelics.render;

import com.blorb.morerelics.MoreRelics;
import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.PoseStack;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MonochromePlayerLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
   private static final Map<ResourceLocation, ResourceLocation> cache = new ConcurrentHashMap<>();
   private static final Map<ResourceLocation, CompletableFuture<ResourceLocation>> pendingRequests = new ConcurrentHashMap<>();

   public MonochromePlayerLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderer) {
      super(renderer);
   }

   public void render(
      PoseStack poseStack,
      MultiBufferSource bufferSource,
      int packedLight,
      AbstractClientPlayer player,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if (!player.isInvisible() && player.hasEffect(MoreRelics.NULLIFICATION)) {
         ResourceLocation graySkin = getGrayscaleSkin(player.getSkin().texture(), player.getGameProfile()).getNow(player.getSkin().texture());
         ((PlayerModel)this.getParentModel())
            .renderToBuffer(
               poseStack, bufferSource.getBuffer(RenderType.entityTranslucent(graySkin)), packedLight, LivingEntityRenderer.getOverlayCoords(player, 0.0F)
            );
      }
   }

   public static CompletableFuture<ResourceLocation> getGrayscaleSkin(ResourceLocation original, GameProfile profile) {
      return cache.containsKey(original) ? CompletableFuture.completedFuture(cache.get(original)) : pendingRequests.computeIfAbsent(original, loc -> {
         CompletableFuture<ResourceLocation> future = CompletableFuture.supplyAsync(() -> {
            NativeImage grayImg = null;

            try {
               NativeImage baseImg = fetchBaseImage(original, profile);
               grayImg = convertToGrayscale(baseImg);
               baseImg.close();
               return uploadToGPU(original, grayImg);
            } catch (Exception e) {
               return original;
            }
         });
         future.whenComplete((res, ex) -> Minecraft.getInstance().execute(() -> pendingRequests.remove(original)));
         return future;
      });
   }

   private static NativeImage fetchBaseImage(ResourceLocation original, GameProfile profile) throws Exception {
      String textureUrl = Minecraft.getInstance().getSkinManager().getInsecureSkin(profile).textureUrl();
      if (textureUrl != null) {
         try (InputStream inputStream = new URI(textureUrl).toURL().openStream()) {
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            NativeImage img = new NativeImage(bufferedImage.getWidth(), bufferedImage.getHeight(), false);

            for (int y = 0; y < bufferedImage.getHeight(); y++) {
               for (int x = 0; x < bufferedImage.getWidth(); x++) {
                  img.setPixelRGBA(x, y, bufferedImage.getRGB(x, y));
               }
            }

            return img;
         }
      } else {
         return NativeImage.read(((Resource)Minecraft.getInstance().getResourceManager().getResource(original).orElseThrow()).open());
      }
   }

   private static NativeImage convertToGrayscale(NativeImage img) {
      NativeImage grayImg = new NativeImage(img.format(), img.getWidth(), img.getHeight(), false);

      for (int y = 0; y < img.getHeight(); y++) {
         for (int x = 0; x < img.getWidth(); x++) {
            int abgr = img.getPixelRGBA(x, y);
            int a = abgr >> 24 & 0xFF;
            int b = abgr >> 16 & 0xFF;
            int g = abgr >> 8 & 0xFF;
            int r = abgr & 0xFF;
            int avg = (int)(0.2126 * r + 0.7152 * g + 0.0722 * b);
            avg = Math.min(255, avg + 40);
            int grayAbgr = a << 24 | avg << 16 | avg << 8 | avg;
            grayImg.setPixelRGBA(x, y, grayAbgr);
         }
      }

      return grayImg;
   }

   private static ResourceLocation uploadToGPU(ResourceLocation original, NativeImage grayImg) {
      Minecraft.getInstance().execute(() -> {
         try {
            DynamicTexture dynamicTexture = new DynamicTexture(grayImg);
            String pathName = "morerelics_gray_" + original.getPath().replaceAll("[^a-zA-Z0-9/]", "_");
            ResourceLocation grayLoc = Minecraft.getInstance().getTextureManager().register(pathName, dynamicTexture);
            cache.put(original, grayLoc);
         } catch (Exception e) {
            grayImg.close();
         }
      });
      return original;
   }
}
