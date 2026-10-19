package io.github.Memoires.mysticism.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.Memoires.mysticism.storage.MysticismStorages;
import io.github.Memoires.mysticism.storage.effect.IMysticismEffect;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(EntityRenderDispatcher.class)
public class MixinEntityRenderDispatcher {
   @Unique
   private static final Material BRIMSTONE_FLAMES_0 = new Material(
      TextureAtlas.LOCATION_BLOCKS, ResourceLocation.fromNamespaceAndPath("mysticism", "block/brimstone_flames_0")
   );
   @Unique
   private static final Material BRIMSTONE_FLAMES_1 = new Material(
      TextureAtlas.LOCATION_BLOCKS, ResourceLocation.fromNamespaceAndPath("mysticism", "block/brimstone_flames_1")
   );

   @ModifyVariable(
      method = "renderFlame(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/Entity;Lorg/joml/Quaternionf;)V",
      at = @At(
         value = "INVOKE_ASSIGN",
         target = "Lnet/minecraft/client/resources/model/Material;sprite()Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;",
         ordinal = 0
      ),
      ordinal = 0
   )
   public TextureAtlasSprite onRenderFlameAtSprite0(TextureAtlasSprite value, PoseStack poseStack, MultiBufferSource multiBufferSource, Entity entity) {
      if (entity instanceof LivingEntity living) {
         IMysticismEffect effectStorage = MysticismStorages.getEffectDataFrom(living);
         if (effectStorage != null && effectStorage.isOnBrimstoneFlames()) {
            return BRIMSTONE_FLAMES_0.sprite();
         }
      }

      return value;
   }

   @ModifyVariable(
      method = "renderFlame(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/Entity;Lorg/joml/Quaternionf;)V",
      at = @At(
         value = "INVOKE_ASSIGN",
         target = "Lnet/minecraft/client/resources/model/Material;sprite()Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;",
         ordinal = 1
      ),
      ordinal = 1
   )
   public TextureAtlasSprite onRenderFlameAtSprite1(TextureAtlasSprite value, PoseStack poseStack, MultiBufferSource multiBufferSource, Entity entity) {
      if (entity instanceof LivingEntity living) {
         IMysticismEffect effectStorage = MysticismStorages.getEffectDataFrom(living);
         if (effectStorage != null && effectStorage.isOnBrimstoneFlames()) {
            return BRIMSTONE_FLAMES_1.sprite();
         }
      }

      return value;
   }
}
