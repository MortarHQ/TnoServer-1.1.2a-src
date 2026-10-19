package it.hurts.sskirillss.relics.client.screen.description.general.widgets.base;

import com.mojang.blaze3d.vertex.PoseStack;
import it.hurts.sskirillss.relics.client.screen.base.IHoverableWidget;
import it.hurts.sskirillss.relics.client.screen.base.IRelicScreenProvider;
import it.hurts.sskirillss.relics.client.screen.base.ITickingWidget;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionTextures;
import it.hurts.sskirillss.relics.utils.data.GUIRenderer;
import it.hurts.sskirillss.relics.utils.data.SpriteAnchor;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public abstract class AbstractPlateWidget extends AbstractDescriptionWidget implements IHoverableWidget, ITickingWidget {
   private IRelicScreenProvider provider;
   private final String icon;

   public abstract String getValue(ItemStack var1);

   public AbstractPlateWidget(int x, int y, IRelicScreenProvider provider, String icon) {
      super(x, y, 54, 19);
      this.provider = provider;
      this.icon = icon;
   }

   public final void renderWidget(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
      PoseStack poseStack = guiGraphics.pose();
      poseStack.pushPose();
      poseStack.translate(
         this.getX() + Math.sin((this.minecraft.player.tickCount + pPartialTick + this.icon.length() * 10) * 0.075F),
         this.getY() + Math.cos((this.minecraft.player.tickCount + pPartialTick + this.icon.length() * 10) * 0.075F) * 0.5,
         0.0
      );
      GUIRenderer.begin(DescriptionTextures.PLATE_BACKGROUND, poseStack).anchor(SpriteAnchor.TOP_LEFT).end();
      GUIRenderer.begin(ResourceLocation.fromNamespaceAndPath("relics", "textures/gui/description/general/icons/" + this.icon + ".png"), poseStack)
         .anchor(SpriteAnchor.TOP_LEFT)
         .pos(3.0F, 3.0F)
         .end();
      MutableComponent value = Component.literal(this.getValue(this.provider.getStack())).withStyle(ChatFormatting.BOLD);
      guiGraphics.drawString(this.minecraft.font, value, 19, 6, 16769656, true);
      this.renderContent(guiGraphics, pMouseX, pMouseY, pPartialTick);
      if (this.isHovered()) {
         GUIRenderer.begin(DescriptionTextures.PLATE_OUTLINE, poseStack).anchor(SpriteAnchor.TOP_LEFT).end();
      }

      poseStack.popPose();
   }

   public void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
   }

   @Override
   public void playDownSound(SoundManager handler) {
   }

   public IRelicScreenProvider getProvider() {
      return this.provider;
   }

   public String getIcon() {
      return this.icon;
   }
}
