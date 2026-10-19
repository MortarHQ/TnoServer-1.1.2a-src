package com.tensura_tno.mixin.client;

import com.tensura_tno.ability.skill.HumanFormSkill;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PlayerRenderer.class, priority = 1500)
public abstract class KumodesuPlayerModelOverrideMixin {
   @Inject(method = "setModelProperties(Lnet/minecraft/client/player/AbstractClientPlayer;)V", at = @At("TAIL"), require = 0)
   private void tensuraTno$forceHumanModel(AbstractClientPlayer player, CallbackInfo ci) {
      if (player != null) {
         if (isHumanFormActive(player)) {
            PlayerModel<AbstractClientPlayer> model = (PlayerModel<AbstractClientPlayer>)((PlayerRenderer)this).getModel();
            model.setAllVisible(true);
         }
      }
   }

   private static boolean isHumanFormActive(AbstractClientPlayer player) {
      AttributeInstance scale = player.getAttribute(Attributes.SCALE);
      return scale == null ? false : scale.getModifier(HumanFormSkill.HUMAN_FORM_SCALE) != null;
   }
}
