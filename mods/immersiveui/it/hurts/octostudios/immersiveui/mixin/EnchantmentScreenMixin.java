package it.hurts.octostudios.immersiveui.mixin;

import it.hurts.octostudios.immersiveui.ImmersiveUI;
import it.hurts.octostudios.immersiveui.util.VectorUtils;
import it.hurts.octostudios.octolib.client.particle.ExtendedUIParticle;
import it.hurts.octostudios.octolib.client.particle.GalacticUIParticle;
import it.hurts.octostudios.octolib.client.particle.UIParticle.Layer;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.inventory.Slot;
import org.joml.Vector2f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentScreen.class)
public class EnchantmentScreenMixin {
   @Inject(
      method = "mouseClicked(DDI)Z",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;handleInventoryButtonClick(II)V")
   )
   public void onMouseClicked(double d, double e, int i, CallbackInfoReturnable<Boolean> ci) {
      if (ImmersiveUI.CONFIG.isEnableEnchantParticles()) {
         AbstractContainerScreen<EnchantmentMenu> screen = (AbstractContainerScreen<EnchantmentMenu>)this;
         Slot slot = (Slot)((EnchantmentMenu)screen.getMenu()).slots.getFirst();

         for (int ii = 0; ii < 8; ii++) {
            ExtendedUIParticle particleData = new GalacticUIParticle(3.0F, 52 + ii, slot.x + 8, slot.y + 8, Layer.SCREEN, 200.0F);
            particleData.setDirection(VectorUtils.rotate(new Vector2f(0.0F, 1.0F), ii * 45.0F));
            particleData.getTransform().setSize(new Vector2f(0.75F, 0.75F));
            particleData.setSpeed(2.0F);
            particleData.setAngularVelocity(-8.0F);
            particleData.setScreen(screen);
            particleData.setGravity(0.0F);
            particleData.setFriction(0.0F);
            particleData.instantiate();
            ExtendedUIParticle var11 = new GalacticUIParticle(3.0F, 40 + ii, slot.x + 8, slot.y + 8, Layer.SCREEN, 200.0F);
            var11.setDirection(VectorUtils.rotate(new Vector2f(0.0F, 1.0F), ii * 45.0F));
            var11.setSpeed(0.75F);
            var11.setAngularVelocity(0.0F);
            var11.setScreen(screen);
            var11.setGravity(0.0F);
            var11.setFriction(0.0F);
            var11.instantiate();
         }
      }
   }
}
