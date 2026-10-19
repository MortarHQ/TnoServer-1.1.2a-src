package net.jadenxgamer.elysium_api.impl.mixin.client;

import net.jadenxgamer.elysium_api.impl.registry.ElysiumBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin {
   @Shadow
   @Final
   private Minecraft minecraft;

   @Inject(method = "getMarkerParticleTarget", at = @At("HEAD"), cancellable = true)
   private void elysium_api$appendMarkerParticleTarget(CallbackInfoReturnable<Block> cir) {
      MultiPlayerGameMode gameMode = this.minecraft.gameMode;
      LocalPlayer player = this.minecraft.player;
      if (gameMode != null && player != null && gameMode.getPlayerMode().isCreative() && player.getMainHandItem().getItem() instanceof BlockItem blockItem) {
         Block block = blockItem.getBlock();
         if (block == ElysiumBlocks.MOB_BARRIER.get()) {
            cir.setReturnValue(block);
         }
      }
   }
}
