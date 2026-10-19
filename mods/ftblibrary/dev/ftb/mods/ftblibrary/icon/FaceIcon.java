package dev.ftb.mods.ftblibrary.icon;

import com.mojang.authlib.GameProfile;
import java.util.HashMap;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FaceIcon extends Icon {
   private static final Logger LOGGER = LoggerFactory.getLogger(FaceIcon.class);
   private static final HashMap<UUID, FaceIcon> CACHE = new HashMap<>();
   public final GameProfile profile;
   public Icon skin;
   public Icon head;
   public Icon hat;

   private FaceIcon(GameProfile p) {
      this.profile = p;
      this.skin = new ImageIcon(DefaultPlayerSkin.get(this.profile.getId()).texture());
      this.head = this.skin.withUV(8.0F, 8.0F, 8.0F, 8.0F, 64.0F, 64.0F);
      this.hat = Icon.empty();
      Minecraft.getInstance().getSkinManager().getOrLoad(this.profile).whenComplete((playerSkin, throwable) -> {
         if (playerSkin != null) {
            ResourceLocation texture = playerSkin.texture();
            this.skin = new ImageIcon(texture);
            this.head = this.skin.withUV(8.0F, 8.0F, 8.0F, 8.0F, 64.0F, 64.0F);
            this.hat = this.skin.withUV(40.0F, 8.0F, 8.0F, 8.0F, 64.0F, 64.0F);
         } else if (throwable != null) {
            LOGGER.warn("Failed to load skin for {}: {} ", this.profile.getName(), throwable.getMessage());
         } else {
            LOGGER.warn("Failed to load skin for {} ?", this.profile.getName());
         }
      });
   }

   public static FaceIcon getFace(GameProfile profile) {
      FaceIcon icon = CACHE.get(profile.getId());
      if (icon == null) {
         icon = new FaceIcon(profile);
         CACHE.put(profile.getId(), icon);
      }

      return icon;
   }

   @Override
   public void draw(GuiGraphics poseStack, int x, int y, int w, int h) {
      this.head.draw(poseStack, x, y, w, h);
      this.hat.draw(poseStack, x, y, w, h);
   }
}
