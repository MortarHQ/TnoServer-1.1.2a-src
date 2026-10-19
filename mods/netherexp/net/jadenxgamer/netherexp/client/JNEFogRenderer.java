package net.jadenxgamer.netherexp.client;

import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.ViewportEvent.ComputeFogColor;
import net.neoforged.neoforge.client.event.ViewportEvent.RenderFog;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class JNEFogRenderer {
   public static void fogRender(RenderFog event) {
      Entity player = Minecraft.getInstance().getCameraEntity();
      if (!event.isCanceled() && event.getType() == FogType.NONE && player != null) {
         BlockState state = player.level().getBlockState(event.getCamera().getBlockPosition());
         if (state.is(JNETags.Blocks.SOUL_GLASSES)) {
            event.setCanceled(true);
            event.setNearPlaneDistance(-8.0F);
            event.setFarPlaneDistance(event.getFarPlaneDistance() * 0.77F);
         }
      }
   }

   public static void fogColor(ComputeFogColor event) {
      Entity player = Minecraft.getInstance().player;
      if (player != null) {
         BlockState state = player.level().getBlockState(event.getCamera().getBlockPosition());
         if (state.is(JNEBlocks.SOUL_GLASS.get())) {
            event.setRed(0.406F);
            event.setGreen(0.578F);
            event.setBlue(0.571F);
         } else if (state.is(JNEBlocks.WAXEN_SOUL_GLASS.get())) {
            event.setRed(0.45098F);
            event.setGreen(0.0549F);
            event.setBlue(0.0549F);
         }
      }
   }

   public static void skyColor(Minecraft client, Vec3 ignoredPos, float ignoredPartialTick, CallbackInfoReturnable<Vec3> cir) {
      Entity player = client.player;
      if (player != null) {
         BlockState state = player.level().getBlockState(client.gameRenderer.getMainCamera().getBlockPosition());
         if (state.is(JNETags.Blocks.SOUL_GLASSES)) {
            cir.setReturnValue(new Vec3(0.0, 0.0, 0.0));
         }
      }
   }
}
