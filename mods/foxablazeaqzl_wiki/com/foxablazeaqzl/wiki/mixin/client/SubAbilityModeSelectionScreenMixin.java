package com.foxablazeaqzl.wiki.mixin.client;

import com.foxablazeaqzl.wiki.client.FoxWikiData;
import com.foxablazeaqzl.wiki.client.FoxWikiScreen;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.client.screen.SubAbilityModeSelectionScreen;
import io.github.manasmods.tensura.client.screen.templates.SimpleScreen;
import io.github.manasmods.tensura.storage.ability.AbilitySlot;
import io.github.manasmods.tensura.util.client.RenderHelper;
import io.github.manasmods.tensura.util.client.ScreenHelper;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SubAbilityModeSelectionScreen.class)
public abstract class SubAbilityModeSelectionScreenMixin extends SimpleScreen {
   private static final Component WIKI_LINK = Component.translatable("foxablazeaqzl_wiki.sub_ability.open");
   private static final int WIKI_LINK_RIGHT = 221;
   private static final int WIKI_LINK_Y = 108;
   private static final int WIKI_LINK_HEIGHT = 9;
   private static final int WIKI_CLICK_X = 121;
   private static final int WIKI_CLICK_Y = 46;
   private static final int WIKI_CLICK_WIDTH = 102;
   private static final int WIKI_CLICK_HEIGHT = 73;
   private static final float WIKI_LINK_SCALE = 0.75F;
   private static final int WIKI_LINK_COLOR = 5636095;
   @Shadow(remap = false)
   protected AbilitySlot selectedAbility;

   protected SubAbilityModeSelectionScreenMixin(Component title, int width, int height) {
      super(title, width, height);
   }

   @Inject(method = "renderBackground", at = @At("TAIL"))
   private void foxWiki$renderWikiLink(GuiGraphics graphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
      ManasSkill skill = this.foxWiki$getSelectedWikiSkill();
      if (skill != null) {
         int right = guiLeft + 221;
         int y = guiTop + 108;
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
      if (button == 0) {
         ManasSkill skill = this.foxWiki$getSelectedWikiSkill();
         if (skill != null) {
            if (RenderHelper.mouseOver(mouseX, mouseY, guiLeft + 121, guiLeft + 121 + 102, guiTop + 46, guiTop + 46 + 73)) {
               ScreenHelper.clicked(() -> ScreenHelper.openScreen(new FoxWikiScreen(skill)));
               cir.setReturnValue(true);
            }
         }
      }
   }

   private ManasSkill foxWiki$getSelectedWikiSkill() {
      if (this.selectedAbility != null && !this.selectedAbility.isEmpty()) {
         ManasSkill skill = this.selectedAbility.getSkill();
         if (skill == null) {
            return null;
         }

         ResourceLocation skillId = skill.getRegistryName();
         return skillId != null && FoxWikiData.hasDescription(skillId) ? skill : null;
      } else {
         return null;
      }
   }
}
