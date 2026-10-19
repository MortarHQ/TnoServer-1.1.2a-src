package cc.sighs.oelib.neoforge.bless;

import cc.sighs.oelib.bless.OverlayRegistry;
import cc.sighs.oelib.bless.render.AbstractShaderOverlay;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent.Post;

@EventBusSubscriber(modid = "oelib", value = Dist.CLIENT)
public class OverlayRenderer {
   private OverlayRenderer() {
   }

   @SubscribeEvent
   public static void onRenderOverlay(Post event) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.player != null && minecraft.level != null) {
         Window window = minecraft.getWindow();
         int width = window.getGuiScaledWidth();
         int height = window.getGuiScaledHeight();
         double mouseX = minecraft.mouseHandler.xpos() * width / window.getScreenWidth();
         double mouseY = minecraft.mouseHandler.ypos() * height / window.getScreenHeight();
         GuiGraphics guiGraphics = event.getGuiGraphics();
         PoseStack pose = guiGraphics.pose();
         pose.pushPose();
         pose.translate(0.0F, 0.0F, 500.0F);
         float partialTick = event.getPartialTick().getGameTimeDeltaTicks();

         for (AbstractShaderOverlay overlay : OverlayRegistry.REGISTERED_OVERLAYS) {
            if (overlay.isActive()) {
               overlay.render(guiGraphics, partialTick, (int)mouseX, (int)mouseY, width, height);
            }
         }

         pose.popPose();
      }
   }
}
