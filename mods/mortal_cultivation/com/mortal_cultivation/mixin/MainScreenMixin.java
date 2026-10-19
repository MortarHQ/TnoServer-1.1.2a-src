package com.mortal_cultivation.mixin;

import com.mortal_cultivation.client.gui.SpiritRootTextRenderer;
import com.mortal_cultivation.spiritroot.SpiritRootClientState;
import com.mortal_cultivation.spiritroot.SpiritRootData;
import com.mortal_cultivation.spiritroot.SpiritRootType;
import io.github.manasmods.tensura.client.screen.MainScreen;
import io.github.manasmods.tensura.storage.TensuraStorages;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MainScreen.class)
public abstract class MainScreenMixin extends Screen {
   @Unique
   private final SpiritRootTextRenderer mortal_cultivation$rootText = new SpiritRootTextRenderer();

   protected MainScreenMixin(Component title) {
      super(title);
   }

   @Inject(method = "renderBackground(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", at = @At("TAIL"), remap = false)
   private void renderSpiritRoot(GuiGraphics graphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
      Player player = Minecraft.getInstance().player;
      if (player != null && this.font != null) {
         Optional<SpiritRootType> root = SpiritRootClientState.getCurrentRoot();
         if (root.isEmpty()) {
            this.mortal_cultivation$rootText.reset();
         } else {
            Component rootLabel = SpiritRootData.createStatusDisplay(root.get());
            double souls = TensuraStorages.getExistenceFrom(player).getSoulPoints() / 100 / 10.0;
            Component soulsLabel = Component.translatable("tensura.main_menu.souls", new Object[]{souls});
            int guiLeft = (this.width - 256) / 2;
            int guiTop = (this.height - 163) / 2;
            int preferredX = guiLeft + 200 - this.font.width(rootLabel);
            int collisionSafeX = guiLeft + 95 + this.font.width(soulsLabel) + 6;
            int x = Math.max(preferredX, collisionSafeX);
            int y = guiTop + (ModList.get().isLoaded("stextras") ? 68 : 52);
            this.mortal_cultivation$rootText.draw(this, graphics, this.font, rootLabel, rootLabel, x, y, guiLeft + 223 - x, mouseX, mouseY);
         }
      } else {
         this.mortal_cultivation$rootText.reset();
      }
   }
}
