package org.anti_ad.mc.ipnext.mixin;

import de.maxhenkel.easyvillagers.blocks.tileentity.TraderTileentityBase;
import net.minecraft.world.entity.player.Player;
import org.anti_ad.mc.ipnext.event.villagers.VillagerTradeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TraderTileentityBase.class)
class MixinTraderTileEntityBase {
   @Inject(
      at = @At("HEAD"),
      method = "Lde/maxhenkel/easyvillagers/blocks/tileentity/TraderTileentityBase;openTradingGUI(Lnet/minecraft/world/entity/player/Player;)Z",
      remap = false
   )
   public void openTradingGUI(Player var1, CallbackInfoReturnable var2) {
      TraderTileentityBase var3 = (TraderTileentityBase)this;
      VillagerTradeManager.INSTANCE.setCurrentVillager(var3.getVillagerEntity());
   }
}
