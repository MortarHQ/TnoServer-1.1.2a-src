package org.crypticdev.stextras.mixin.tensura.client.screen;

import io.github.manasmods.tensura.client.screen.AbilityCategoriesScreen;
import io.github.manasmods.tensura.client.screen.AbilityCategoriesScreen.AbilityType;
import io.github.manasmods.tensura.client.screen.widgets.SimpleButton;
import io.github.manasmods.tensura.util.client.ScreenHelper;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.crypticdev.stextras.client.screen.STSkillLockScreen;
import org.crypticdev.stextras.mixin.access.SimpleScreenAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbilityCategoriesScreen.class)
public abstract class AbilityCategoriesScreenMixin {
   @Shadow
   @Final
   protected static ResourceLocation BACKGROUND_SKILL;
   @Shadow(remap = false)
   @Final
   protected AbilityType abilityType;
   @Unique
   private static final ResourceLocation STEXTRAS_BACKGROUND_SKILL = ResourceLocation.fromNamespaceAndPath("stextras", "textures/gui/skill/skill_gui.png");
   @Unique
   private static final ResourceLocation STEXTRAS_SKILL_LOCK = ResourceLocation.fromNamespaceAndPath("stextras", "textures/gui/skill/skill_lock_button.png");
   @Unique
   private SimpleButton stextras$lockBtn;

   @Redirect(
      method = "renderBackground(Lnet/minecraft/client/gui/GuiGraphics;IIF)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V")
   )
   private void strextras$swapSkillBackground(GuiGraphics g, ResourceLocation tex, int x, int y, int u, int v, int w, int h) {
      if (this.abilityType == AbilityType.SKILL && BACKGROUND_SKILL.equals(tex)) {
         g.blit(STEXTRAS_BACKGROUND_SKILL, x, y, u, v, w, h);
      } else {
         g.blit(tex, x, y, u, v, w, h);
      }
   }

   @Inject(method = "init()V", at = @At("TAIL"))
   private void strextras$adjustSkillGrid(CallbackInfo ci) {
      if (this.abilityType != AbilityType.SKILL) {
         this.stextras$lockBtn = null;
      } else {
         SimpleScreenAccessor self = (SimpleScreenAccessor)this;
         int guiLeft = self.stextras$getGuiLeft();
         int guiTop = self.stextras$getGuiTop();
         int leftColXOld = guiLeft + 19;
         int rightColXOld = guiLeft + 63;
         int rowY0Old = guiTop + 45;
         int rowY1Old = guiTop + 83;
         int rowY2Old = guiTop + 121;
         int leftColX = guiLeft + 19;
         int rightColX = guiLeft + 63;
         int baseY = guiTop + 39;
         int rowStep = 34;
         int[] rowsNew = new int[]{baseY, baseY + 34, baseY + 68};
         List<? extends GuiEventListener> renderables = ((Screen)this).children();
         List<AbstractWidget> moved = new ArrayList<>(6);

         for (GuiEventListener r : renderables) {
            if (r instanceof SimpleButton sb) {
               AbstractWidget w = sb;
               if (w.getWidth() == 32 && w.getHeight() == 32) {
                  int x = w.getX();
                  int y = w.getY();
                  boolean colOK = x == leftColXOld || x == rightColXOld;
                  int rowIdx = y == rowY0Old ? 0 : (y == rowY1Old ? 1 : (y == rowY2Old ? 2 : -1));
                  if (colOK && rowIdx != -1) {
                     int newX = x == leftColXOld ? leftColX : rightColX;
                     int newY = rowsNew[rowIdx];
                     w.setPosition(newX, newY);
                     moved.add(w);
                  }
               }
            }
         }

         if (!moved.isEmpty()) {
            int uniqueX = leftColX;
            int uniqueY = rowsNew[2];
            int btnX = uniqueX + 30;
            int btnY = uniqueY + 32;
            SimpleButton lockBtn = new SimpleButton(
               btnX,
               btnY,
               16,
               17,
               STEXTRAS_SKILL_LOCK,
               null,
               Component.translatable("stextras.ability_categories_screen.skill_lock"),
               selfBtn -> ScreenHelper.clicked(() -> ScreenHelper.openScreen(new STSkillLockScreen()))
            );
            this.stextras$lockBtn = lockBtn;
            this.stextras$registerChild(lockBtn);
         }
      }
   }

   @Inject(method = "renderTooltip(Lnet/minecraft/client/gui/GuiGraphics;II)V", at = @At("HEAD"))
   private void strextras$renderLockButton(GuiGraphics graphics, int mouseX, int mouseY, CallbackInfo ci) {
      if (this.abilityType == AbilityType.SKILL && this.stextras$lockBtn != null) {
         this.stextras$lockBtn.render(graphics, mouseX, mouseY, 0.0F);
      }
   }

   @Unique
   private void stextras$registerChild(GuiEventListener listener) {
      ((Screen)this).children().add(listener);
   }
}
