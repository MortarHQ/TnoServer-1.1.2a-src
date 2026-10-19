package com.mortal_cultivation.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mortal_cultivation.client.gui.SpiritRootTextRenderer;
import com.mortal_cultivation.spiritroot.SpiritRootClientState;
import com.mortal_cultivation.spiritroot.SpiritRootData;
import com.mortal_cultivation.spiritroot.SpiritRootType;
import io.github.manasmods.tensura.client.screen.ReincarnationScreen;
import io.github.manasmods.tensura.menu.ReincarnationMenu;
import java.util.Optional;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ReincarnationScreen.class)
public abstract class ReincarnationScreenMixin extends AbstractContainerScreen<ReincarnationMenu> {
   @Unique
   private final SpiritRootTextRenderer mortal_cultivation$rootText = new SpiritRootTextRenderer();
   @Unique
   private final SpiritRootTextRenderer mortal_cultivation$raceText = new SpiritRootTextRenderer();
   @Unique
   private int mortal_cultivation$rootTextLeft = -1;

   protected ReincarnationScreenMixin(ReincarnationMenu menu, Inventory inventory, Component title) {
      super(menu, inventory, title);
   }

   @WrapOperation(
      method = "renderRace(Lnet/minecraft/client/gui/GuiGraphics;II)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;IIIZ)I"
      ),
      remap = false,
      require = 2
   )
   private int fitRaceTitle(
      GuiGraphics graphics,
      Font font,
      Component title,
      int x,
      int y,
      int color,
      boolean shadow,
      Operation<Integer> original,
      GuiGraphics raceGraphics,
      int mouseX,
      int mouseY
   ) {
      if (SpiritRootClientState.getCurrentRoot().isEmpty()) {
         this.mortal_cultivation$rootTextLeft = -1;
         this.mortal_cultivation$raceText.reset();
         return (Integer)original.call(new Object[]{graphics, font, title, x, y, color, shadow});
      } else {
         int width = font.width(title);
         int center = x + width / 2;
         int right = this.leftPos + this.imageWidth - 28;
         int maxTitleWidth = Math.max(1, 2 * Math.min(center - (this.leftPos + 28), right - 34 - center));
         int displayedWidth = Math.min(width, maxTitleWidth);
         int titleX = x + (width - displayedWidth) / 2;
         this.mortal_cultivation$rootTextLeft = titleX + displayedWidth + 2;
         return this.mortal_cultivation$raceText
            .draw(
               this,
               graphics,
               font,
               title,
               title,
               titleX,
               y,
               displayedWidth,
               mouseX,
               mouseY,
               () -> (Integer)original.call(new Object[]{graphics, font, title, 0, 0, color, shadow})
            );
      }
   }

   @Inject(method = "renderRace(Lnet/minecraft/client/gui/GuiGraphics;II)V", at = @At("TAIL"), remap = false)
   private void renderSpiritRoot(GuiGraphics graphics, int mouseX, int mouseY, CallbackInfo ci) {
      if (this.font == null) {
         this.mortal_cultivation$rootText.reset();
      } else {
         Optional<SpiritRootType> root = SpiritRootClientState.getCurrentRoot();
         if (!root.isEmpty() && this.mortal_cultivation$rootTextLeft >= 0) {
            int right = this.leftPos + this.imageWidth - 28;
            this.mortal_cultivation$rootText
               .draw(
                  this,
                  graphics,
                  this.font,
                  SpiritRootData.createInlineDisplay(root.get()),
                  SpiritRootData.createStatusDisplay(root.get()),
                  this.mortal_cultivation$rootTextLeft,
                  this.topPos + 7,
                  right - this.mortal_cultivation$rootTextLeft,
                  mouseX,
                  mouseY
               );
         } else {
            this.mortal_cultivation$rootText.reset();
         }
      }
   }
}
