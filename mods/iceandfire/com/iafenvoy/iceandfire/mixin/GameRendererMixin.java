package com.iafenvoy.iceandfire.mixin;

import com.iafenvoy.iceandfire.IceAndFire;
import com.iafenvoy.iceandfire.event.ClientEvents;
import com.iafenvoy.iceandfire.render.RenderVariables;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.server.packs.resources.ResourceProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
   @Shadow
   @Final
   private Camera mainCamera;

   @Inject(
      method = "renderLevel(Lnet/minecraft/client/DeltaTracker;)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/Camera;setup(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/world/entity/Entity;ZZF)V",
         shift = Shift.AFTER
      )
   )
   private void onCameraSetup(DeltaTracker tickCounter, CallbackInfo ci) {
      ClientEvents.onCameraSetup(this.mainCamera);
   }

   @Inject(
      method = "reloadShaders(Lnet/minecraft/server/packs/resources/ResourceProvider;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;shutdownShaders()V")
   )
   private void registerProgram(ResourceProvider factory, CallbackInfo ci, @Local(ordinal = 1) List<Pair<ShaderInstance, Consumer<ShaderInstance>>> list2) {
      try {
         list2.add(
            Pair.of(
               new ShaderInstance(factory, "rendertype_dread_portal", DefaultVertexFormat.POSITION_COLOR),
               (Consumer<ShaderInstance>)program -> RenderVariables.DREAD_PORTAL_PROGRAM = program
            )
         );
      } catch (Exception e) {
         IceAndFire.LOGGER.error("Failed to load dread portal program", e);
      }
   }
}
