package io.github.Memoires.mysticism.util.fourth_wall;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;

public class SkinOverrideManager {
   private static final ResourceLocation FOURTH_WALL_TEXTURE = ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/skill/obfuscation.png");

   public static ResourceLocation resolveSkin(AbstractClientPlayer player) {
      return player.getSkin().texture();
   }
}
