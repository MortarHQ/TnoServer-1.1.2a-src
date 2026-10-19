package com.foxablazeaqzl.wiki.mixin.client;

import com.foxablazeaqzl.wiki.client.FoxWikiData;
import com.foxablazeaqzl.wiki.client.FoxWikiScreen;
import com.foxablazeaqzl.wiki.client.FoxWikiTabButtons;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.client.screen.AbilitySelectionScreen;
import io.github.manasmods.tensura.client.screen.templates.SimpleScreen;
import io.github.manasmods.tensura.util.client.RenderHelper;
import io.github.manasmods.tensura.util.client.ScreenHelper;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbilitySelectionScreen.class)
public abstract class AbilitySelectionScreenMixin extends SimpleScreen {
   private static final Component WIKI_LINK = Component.translatable("foxablazeaqzl_wiki.sub_ability.open");
   private static final int WIKI_LINK_RIGHT = 221;
   private static final int WIKI_LINK_Y = 126;
   private static final int WIKI_LINK_HEIGHT = 9;
   private static final int WIKI_CLICK_X = 123;
   private static final int WIKI_CLICK_Y = 71;
   private static final int WIKI_CLICK_WIDTH = 98;
   private static final int WIKI_CLICK_HEIGHT = 66;
   private static final float WIKI_LINK_SCALE = 0.75F;
   private static final int WIKI_LINK_COLOR = 5636095;
   @Shadow(remap = false)
   protected ManasSkillInstance selectedAbility;

   protected AbilitySelectionScreenMixin(Component title, int width, int height) {
      super(title, width, height);
   }

   @Inject(method = "init", at = @At("TAIL"))
   private void foxWiki$addWikiButton(CallbackInfo ci) {
      FoxWikiTabButtons.disableOriginalThirdRightTab(this, guiLeft, guiTop);
      this.addRenderableWidget(FoxWikiTabButtons.createWikiButton(guiLeft, guiTop));
   }

   @Inject(method = "renderBackground", at = @At("TAIL"))
   private void foxWiki$renderWikiLink(GuiGraphics graphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
      if (this.foxWiki$hasSelectedWikiSkill()) {
         int right = guiLeft + 221;
         int y = guiTop + 126;
         int textWidth = this.font.width(WIKI_LINK);
         float drawX = right - textWidth * 0.75F;
         float drawY = y + (9.0F - 9.0F * 0.75F) / 2.0F;
         graphics.pose().pushPose();
         graphics.pose().translate(drawX, drawY, 0.0F);
         graphics.pose().scale(0.75F, 0.75F, 1.0F);
         graphics.drawString(this.font, WIKI_LINK, 0, 0, 5636095, false);
         graphics.pose().popPose();
      }
   }

   @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
   private void foxWiki$openWikiFromDescription(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
      if (button == 0 && this.foxWiki$hasSelectedWikiSkill()) {
         if (RenderHelper.mouseOver(mouseX, mouseY, guiLeft + 123, guiLeft + 123 + 98, guiTop + 71, guiTop + 71 + 66)) {
            ScreenHelper.clicked(() -> ScreenHelper.openScreen(new FoxWikiScreen(this.selectedAbility.getSkill())));
            cir.setReturnValue(true);
         }
      }
   }

   private boolean foxWiki$hasSelectedWikiSkill() {
      return this.selectedAbility != null && this.selectedAbility.getSkill() != null && FoxWikiData.hasDescription(this.selectedAbility.getSkillId());
   }
}
