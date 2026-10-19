package it.hurts.sskirillss.relics.client.screen.description.ability.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import it.hurts.sskirillss.relics.client.screen.description.experience.ExperienceDescriptionScreen;
import it.hurts.sskirillss.relics.client.screen.description.general.widgets.base.AbstractDescriptionWidget;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionTextures;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.utils.data.GUIRenderer;
import it.hurts.sskirillss.relics.utils.data.SpriteAnchor;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;

public class ExperienceSourcePageWidget extends AbstractDescriptionWidget {
   private ExperienceDescriptionScreen screen;
   private int step;

   public ExperienceSourcePageWidget(int x, int y, ExperienceDescriptionScreen screen, int step) {
      super(x, y, 12, 17);
      this.screen = screen;
      this.step = step;
   }

   @Override
   public void onPress() {
      ItemStack stack = this.screen.getStack();
      if (stack.getItem() instanceof IRelicItem relic) {
         List var8 = relic.getLevelingSourcesData().getSources().keySet().stream().filter(entry -> relic.isLevelingSourceEnabled(stack, entry)).toList();
         byte maxEntries = 5;
         int size = var8.size();
         int totalPages = (int)Math.ceil((double)size / maxEntries);
         if (totalPages > 0) {
            int newPage = this.screen.getPage() + this.step;
            if (newPage >= totalPages) {
               newPage = 0;
            }

            if (newPage < 0) {
               newPage = totalPages - 1;
            }

            this.screen.setPage(newPage);
            this.screen.rebuildWidgets();
         }
      }
   }

   public void renderWidget(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
      PoseStack poseStack = guiGraphics.pose();
      poseStack.pushPose();
      GUIRenderer.begin(this.step > 0 ? DescriptionTextures.PAGE_ARROW_DOWN : DescriptionTextures.PAGE_ARROW_UP, poseStack)
         .anchor(SpriteAnchor.TOP_LEFT)
         .pos(this.getX(), this.getY())
         .end();
      poseStack.popPose();
   }

   public ExperienceDescriptionScreen getScreen() {
      return this.screen;
   }

   public int getStep() {
      return this.step;
   }
}
