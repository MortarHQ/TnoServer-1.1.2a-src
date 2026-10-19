package com.tensura_tno.mixin.client;

import com.tensura_tno.compat.stextras.SkillUnlockEpCost;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.storage.ep.IExistence;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "org.crypticdev.stextras.client.screen.skilllock.STSkillLockRules", remap = false)
public class SkillLockRulesEpCostMixin {
   @Inject(
      method = "canUnlockSelectedSkill(Lio/github/manasmods/manascore/skill/api/ManasSkillInstance;Lio/github/manasmods/tensura/storage/ep/IExistence;)Z",
      at = @At("RETURN"),
      cancellable = true,
      require = 0,
      remap = false
   )
   private static void tno$requireEnoughCurrentEp(ManasSkillInstance selectedAbility, IExistence existence, CallbackInfoReturnable<Boolean> cir) {
      LocalPlayer player = Minecraft.getInstance().player;
      if (cir.getReturnValueZ() && player != null && !SkillUnlockEpCost.canAfford(player, existence)) {
         cir.setReturnValue(false);
      }
   }
}
