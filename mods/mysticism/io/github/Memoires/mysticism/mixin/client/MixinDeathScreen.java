package io.github.Memoires.mysticism.mixin.client;

import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillUtils;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundClientCommandPacket;
import net.minecraft.network.protocol.game.ServerboundClientCommandPacket.Action;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DeathScreen.class)
public abstract class MixinDeathScreen {
   @Shadow
   @Final
   private boolean hardcore;
   @Shadow
   @Final
   private List<Button> exitButtons;

   @Inject(method = "init()V", at = @At("TAIL"))
   private void myst$overrideHardcoreRespawn(CallbackInfo ci) {
      if (this.hardcore) {
         Minecraft mc = Minecraft.getInstance();
         if (mc.player != null) {
            if (mysticism$canReviveClient(mc.player)) {
               if (!this.exitButtons.isEmpty()) {
                  Button original = this.exitButtons.get(0);
                  ((DeathScreen)this).removeWidget(original);
                  Button respawn = Button.builder(Component.translatable("deathScreen.respawn"), btn -> {
                     mc.player.connection.send(new ServerboundClientCommandPacket(Action.PERFORM_RESPAWN));
                     btn.active = false;
                  }).bounds(original.getX(), original.getY(), original.getWidth(), original.getHeight()).build();
                  ((DeathScreen)this).addRenderableWidget(respawn);
                  this.exitButtons.set(0, respawn);
               }
            }
         }
      }
   }

   @Unique
   private static boolean mysticism$canReviveClient(LocalPlayer player) {
      ManasSkillInstance skill = SkillUtils.getSkillOrNull(player, (ManasSkill)UniqueMysticismSkills.SCHRODINGER.get());
      return skill != null && skill.getOrCreateTag().getInt("Lives") > 0;
   }
}
