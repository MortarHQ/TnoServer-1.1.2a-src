package net.unusual.block_factorys_bosses.mixins.client;

import com.mojang.blaze3d.vertex.PoseStack;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.entity.boss.yeti.IceSpikeClusterEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
   @Shadow
   @Final
   private EntityRenderDispatcher entityRenderDispatcher;

   @Inject(method = "renderEntity", at = @At("HEAD"), cancellable = true)
   private void renderEntityMixin(
      Entity entity, double camX, double camY, double camZ, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, CallbackInfo ci
   ) {
      if (entity instanceof IceSpikeClusterEntity iceSpike && iceSpike.getOwner() instanceof Player player && iceSpike.getHeld()) {
         Vec3 move = iceSpike.heldPos(player, partialTick);
         this.entityRenderDispatcher
            .render(
               entity,
               move.x - camX,
               move.y - camY,
               move.z - camZ,
               Mth.lerp(partialTick, player.yRotO, player.getYRot()),
               partialTick,
               poseStack,
               bufferSource,
               this.entityRenderDispatcher.getPackedLightCoords(entity, partialTick)
            );
         ci.cancel();
      }
   }
}
