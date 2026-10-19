package com.tensura_tno.mixin.client;

import com.tensura_tno.race.fox_spirit.SpiritSummonEntityHelper;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "twilightforest.entity.boss.BaseTFBoss", remap = false)
public abstract class TwilightForestSpiritSummonLootMixin {
   @Inject(method = {"shouldSpawnLoot", "shouldCreateSpawner"}, at = @At("HEAD"), cancellable = true, require = 0)
   private void tno$disableSummonRewards(CallbackInfoReturnable<Boolean> cir) {
      if (SpiritSummonEntityHelper.isSpiritSummon((Entity)this)) {
         cir.setReturnValue(false);
      }
   }
}
