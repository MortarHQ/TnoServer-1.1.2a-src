package com.tensura_tno.mixin.client;

import com.tensura_tno.compat.stextras.SkillUnlockEpCost;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.storage.TensuraStorages;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "org.crypticdev.stextras.client.screen.STSkillLockScreen", remap = false)
public class SkillLockScreenEpCostMixin {
   @Shadow
   private ManasSkillInstance selectedAbility;

   @Inject(method = "tryUnlockSelectedSkill()V", at = @At("HEAD"), cancellable = true, require = 0, remap = false)
   private void tno$rejectUnlockWhenCurrentEpIsInsufficient(CallbackInfo ci) {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null && this.tno$isEligibleSkill() && !SkillUnlockEpCost.canAfford(player, TensuraStorages.getExistenceFrom(player))) {
         player.playSound(SoundEvents.ITEM_BREAK, 1.0F, 1.2F);
         player.displayClientMessage(Component.translatable("tensura_tno.skill_lock.not_enough_ep_cost"), true);
         ci.cancel();
      }
   }

   private boolean tno$isEligibleSkill() {
      if (this.selectedAbility != null && this.selectedAbility.getSkill() != null) {
         ResourceLocation id = SkillAPI.getSkillRegistry().getId(this.selectedAbility.getSkill());
         if (id == null) {
            return false;
         }

         try {
            Class<?> storage = Class.forName("org.crypticdev.stextras.storage.STExtarsStorage$Player");
            return Boolean.TRUE.equals(storage.getMethod("isSkillValid", ResourceLocation.class).invoke(null, id))
               && Boolean.TRUE.equals(storage.getMethod("isSkillLocked", ResourceLocation.class).invoke(null, id))
               && !Boolean.TRUE.equals(storage.getMethod("isSkillAdminLocked", ResourceLocation.class).invoke(null, id));
         } catch (ReflectiveOperationException ignored) {
            return false;
         }
      } else {
         return false;
      }
   }
}
