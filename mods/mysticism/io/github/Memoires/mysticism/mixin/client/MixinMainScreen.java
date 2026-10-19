package io.github.Memoires.mysticism.mixin.client;

import io.github.Memoires.mysticism.storage.MysticismStorages;
import io.github.Memoires.mysticism.storage.element.IElement;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.tensura.ability.magic.Element;
import io.github.manasmods.tensura.client.screen.MainScreen;
import io.github.manasmods.tensura.client.screen.templates.SimpleScreen;
import io.github.manasmods.tensura.util.client.RenderHelper;
import java.awt.Color;
import java.util.Optional;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MainScreen.class)
public class MixinMainScreen extends SimpleScreen {
   protected MixinMainScreen(Component title, int width, int height) {
      super(title, width, height);
   }

   @Inject(method = "renderRaceStats(Lnet/minecraft/client/gui/GuiGraphics;II)V", at = @At("TAIL"), cancellable = true)
   private void myst$elementStat(GuiGraphics graphics, int mX, int mY, CallbackInfo ci) {
      Optional<ManasRaceInstance> optional = RaceAPI.getRaceFrom(this.player).getRace();
      if (!optional.isEmpty()) {
         IElement elementStorage = MysticismStorages.getElementFrom(this.player);
         if (elementStorage != null) {
            String elementText = Component.translatable("mysticism.menu.element").getString();
            Element element = elementStorage.getElement();
            if (element != null) {
               String elementName = element.getName().getString();
               RenderHelper.drawTextWithTooltip(
                  graphics, this.font, elementText, guiLeft + 95, guiTop + 120, Color.WHITE.getRGB(), mX, mY, Component.empty(), this
               );
               RenderHelper.drawTextWithTooltip(
                  graphics, this.font, elementName, guiLeft + 140, guiTop + 120, element.getColor(), mX, mY, Component.empty(), this
               );
            }
         }
      }
   }
}
