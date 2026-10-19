package io.github.Memoires.mysticism.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.Memoires.mysticism.storage.MysticismStorages;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ScreenEffectRenderer.class)
public abstract class MixinScreenEffectRenderer {
   @Unique
   private static final Material BRIMSTONE_FLAMES_1 = new Material(
      TextureAtlas.LOCATION_BLOCKS, ResourceLocation.fromNamespaceAndPath("mysticism", "block/brimstone_flames_1")
   );

   @ModifyVariable(
      method = "renderFire(Lnet/minecraft/client/Minecraft;Lcom/mojang/blaze3d/vertex/PoseStack;)V",
      at = @At(
         value = "INVOKE_ASSIGN",
         target = "Lnet/minecraft/client/resources/model/Material;sprite()Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;"
      )
   )
   private static TextureAtlasSprite onRenderFire(TextureAtlasSprite value, Minecraft minecraft, PoseStack poseStack) {
      return minecraft.player != null && MysticismStorages.getEffectDataFrom(minecraft.player).isOnBrimstoneFlames() ? BRIMSTONE_FLAMES_1.sprite() : value;
   }
}
