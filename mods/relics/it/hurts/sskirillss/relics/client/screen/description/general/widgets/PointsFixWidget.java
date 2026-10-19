package it.hurts.sskirillss.relics.client.screen.description.general.widgets;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import it.hurts.sskirillss.relics.client.screen.base.IHoverableWidget;
import it.hurts.sskirillss.relics.client.screen.base.IRelicScreenProvider;
import it.hurts.sskirillss.relics.client.screen.description.general.widgets.base.AbstractDescriptionWidget;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionUtils;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.network.NetworkHandler;
import it.hurts.sskirillss.relics.network.packets.leveling.FixLevelingPoints;
import it.hurts.sskirillss.relics.utils.data.GUIRenderer;
import it.hurts.sskirillss.relics.utils.data.SpriteAnchor;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;

@Deprecated(forRemoval = true)
public class PointsFixWidget extends AbstractDescriptionWidget implements IHoverableWidget {
   private IRelicScreenProvider provider;

   public PointsFixWidget(int x, int y, IRelicScreenProvider provider) {
      super(x, y, 18, 18);
      this.provider = provider;
   }

   @Override
   public void onPress() {
      if (this.provider.getStack().getItem() instanceof IRelicItem relic && relic.isSomethingWrongWithLevelingPoints(this.provider.getStack())) {
         NetworkHandler.sendToServer(new FixLevelingPoints(this.provider.getContainer(), this.provider.getSlot()));
      }
   }

   public void renderWidget(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null
         && this.provider.getStack().getItem() instanceof IRelicItem relic
         && relic.isSomethingWrongWithLevelingPoints(this.provider.getStack())) {
         PoseStack poseStack = guiGraphics.pose();
         poseStack.pushPose();
         float color = (float)(1.25 + Math.sin(player.tickCount) * 0.5);
         GUIRenderer.begin(ResourceLocation.fromNamespaceAndPath("relics", "textures/gui/description/general/fix.png"), poseStack)
            .anchor(SpriteAnchor.TOP_LEFT)
            .color(color, color, color, 1.0F)
            .pos(this.getX(), this.getY())
            .end();
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         poseStack.popPose();
      }
   }

   @Override
   public void onHovered(GuiGraphics guiGraphics, int mouseX, int mouseY) {
      ItemStack stack = this.getProvider().getStack();
      if (stack.getItem() instanceof IRelicItem relic && relic.isSomethingWrongWithLevelingPoints(this.provider.getStack())) {
         PoseStack poseStack = guiGraphics.pose();
         List<FormattedCharSequence> tooltip = Lists.newArrayList();
         int maxWidth = 150;
         int renderWidth = 0;

         for (MutableComponent entry : Lists.newArrayList(
            new MutableComponent[]{
               Component.literal(
                  "Looks like you have an incorrect number of leveling points. This may be a compatibility issue with previous releases of Relics. If you're sure something is wrong, click this button to fix it! This action cannot be undone!"
               )
            }
         )) {
            int entryWidth = this.minecraft.font.width(entry) / 2;
            if (entryWidth > renderWidth) {
               renderWidth = Math.min(entryWidth + 2, maxWidth);
            }

            tooltip.addAll(this.minecraft.font.split(entry, maxWidth * 2));
         }

         poseStack.pushPose();
         poseStack.translate(0.0F, 0.0F, 100.0F);
         DescriptionUtils.drawTooltipBackground(guiGraphics, renderWidth, tooltip.size() * 5, mouseX - 9 - renderWidth / 2, mouseY);
         poseStack.scale(0.5F, 0.5F, 0.5F);
         int yOff = 0;

         for (FormattedCharSequence entry : tooltip) {
            guiGraphics.drawString(this.minecraft.font, entry, (mouseX - renderWidth / 2 + 1) * 2, (mouseY + yOff + 9) * 2, 6696723, false);
            yOff += 5;
         }

         poseStack.popPose();
      }
   }

   public IRelicScreenProvider getProvider() {
      return this.provider;
   }
}
