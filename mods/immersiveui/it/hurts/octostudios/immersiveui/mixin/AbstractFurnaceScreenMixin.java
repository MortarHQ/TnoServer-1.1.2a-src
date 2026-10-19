package it.hurts.octostudios.immersiveui.mixin;

import it.hurts.octostudios.immersiveui.util.CommonCode;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFurnaceScreen.class)
public class AbstractFurnaceScreenMixin {
   @Unique
   public AtomicBoolean shouldBurst = new AtomicBoolean(false);

   @Inject(method = "render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", at = @At("HEAD"))
   public void render(GuiGraphics guiGraphics, int i, int j, float f, CallbackInfo ci) {
      AbstractFurnaceScreen<?> screen = (AbstractFurnaceScreen<?>)this;
      AbstractFurnaceMenu menu = (AbstractFurnaceMenu)screen.getMenu();
      CommonCode.renderFurnaceParticles(screen, menu.getSlot(1), menu.getLitProgress() == 1.0F, menu.isLit(), this.shouldBurst);
   }
}
