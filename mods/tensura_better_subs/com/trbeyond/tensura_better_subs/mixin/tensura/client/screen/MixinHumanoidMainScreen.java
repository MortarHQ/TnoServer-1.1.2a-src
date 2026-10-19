package com.trbeyond.tensura_better_subs.mixin.tensura.client.screen;

import com.trbeyond.tensura_better_subs.client.screen.ScreenHelperSubordinates;
import io.github.manasmods.tensura.client.screen.HumanoidMainScreen;
import io.github.manasmods.tensura.entity.template.TensuraHumanoidEntity;
import io.github.manasmods.tensura.menu.HumanoidMainMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HumanoidMainScreen.class)
public abstract class MixinHumanoidMainScreen {
   @Shadow(remap = false)
   @Final
   private TensuraHumanoidEntity humanoid;
   protected Font font;
   @Unique
   private static final ResourceLocation Skill_Tab = ResourceLocation.fromNamespaceAndPath("tensura_better_subs", "textures/skill_icon.png");
   private static final int baseX = 30;
   private static final int baseY = -106;
   private static final int assetWidth = 24;
   private static final int assetHeight = 21;

   @Inject(method = "<init>", at = @At("RETURN"))
   void initStorage(HumanoidMainMenu menu, Inventory inventory, TensuraHumanoidEntity entity, CallbackInfo ci) {
      this.font = Minecraft.getInstance().font;
   }

   @Inject(method = "renderBg(Lnet/minecraft/client/gui/GuiGraphics;FII)V", at = @At("RETURN"), remap = true)
   private void renderSkillButton(GuiGraphics graphics, float f, int x, int y, CallbackInfo callbackInfo) {
      if (this instanceof HumanoidMainScreen mainScreen) {
         int width = mainScreen.width / 2;
         int height = mainScreen.height / 2;
         boolean hovering = x >= width + 30 && x < width + 30 + 24 && y >= height + -106 && y < height + -106 + 21;
         graphics.blit(Skill_Tab, width + 30, height + -106, 0.0F, hovering ? 21 : 0, 24, 21, 24, 42);
      }
   }

   @Inject(method = "renderTooltip(Lnet/minecraft/client/gui/GuiGraphics;II)V", at = @At("RETURN"), remap = true)
   private void renderSkillTooltip(GuiGraphics graphics, int x, int y, CallbackInfo callbackInfo) {
      if (this instanceof HumanoidMainScreen mainScreen) {
         int width = mainScreen.width / 2;
         int height = mainScreen.height / 2;
         boolean hovering = x >= width + 30 && x < width + 30 + 24 && y >= height + -106 && y < height + -106 + 21;
         if (hovering) {
            graphics.renderTooltip(
               this.font, Component.translatable("tooltip.tensura_better_subs.humanoid_menu.skill_button"), x - mainScreen.leftPos, y - mainScreen.topPos
            );
         }
      }
   }

   @Inject(method = "mouseClicked(DDI)Z", at = @At("RETURN"), remap = true)
   private void handleSkillButtonClick(double x, double y, int i, CallbackInfoReturnable<Boolean> cir) {
      if (this instanceof HumanoidMainScreen mainScreen) {
         int width = mainScreen.width / 2;
         int height = mainScreen.height / 2;
         boolean overButton = x >= width + 30 && x < width + 30 + 24 && y >= height + -106 && y < height + -106 + 21;
         if (overButton) {
            Minecraft.getInstance().player.closeContainer();
            ScreenHelperSubordinates.openScreen(4, this.humanoid);
         }
      }
   }
}
