package org.anti_ad.mc.ipnext.mixin;

import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.phys.Vec3;
import org.anti_ad.mc.ipnext.event.villagers.VillagerTradeManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerboundInteractPacket.class)
public class MixinServerBoundInteractPacket {
   @Shadow
   @Final
   private int entityId;

   @Inject(
      method = "createInteractionPacket(Lnet/minecraft/world/entity/Entity;ZLnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/network/protocol/game/ServerboundInteractPacket;",
      at = @At("HEAD")
   )
   private static void createInteractionPacket(Entity var0, boolean var1, InteractionHand var2, Vec3 var3, CallbackInfoReturnable var4) {
      if (var0 instanceof AbstractVillager) {
         VillagerTradeManager.INSTANCE.setCurrentVillager((AbstractVillager)var0);
      }
   }
}
