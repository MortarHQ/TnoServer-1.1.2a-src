package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.entity.PlayerIllusionEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityRenderers;
import com.faboslav.friendsandfoes.common.util.PlayerSkinProvider;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.resources.PlayerSkin.Model;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {
   @Unique
   private Map<Model, EntityRenderer<? extends PlayerIllusionEntity>> friendsandfoes$illusionModelRenderers = ImmutableMap.of();

   @Inject(
      method = "getRenderer(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/client/renderer/entity/EntityRenderer;",
      at = @At("HEAD"),
      cancellable = true
   )
   public <T extends Entity> void friendsandfoes$getRenderer(T entity, CallbackInfoReturnable<EntityRenderer<? super T>> cir) {
      if (entity instanceof PlayerIllusionEntity playerIllusion) {
         Model model = PlayerSkinProvider.getSkinTextures(playerIllusion).model();
         EntityRenderer<? extends PlayerIllusionEntity> entityRenderer = this.friendsandfoes$illusionModelRenderers.get(model);
         entityRenderer = entityRenderer != null ? entityRenderer : this.friendsandfoes$illusionModelRenderers.get(Model.WIDE);
         cir.setReturnValue(entityRenderer);
      }
   }

   @ModifyVariable(method = "onResourceManagerReload", ordinal = 0, at = @At("LOAD"))
   public Context friendsandfoes$reload(Context context) {
      this.friendsandfoes$illusionModelRenderers = FriendsAndFoesEntityRenderers.reloadPlayerIllusionRenderers(context);
      return context;
   }
}
