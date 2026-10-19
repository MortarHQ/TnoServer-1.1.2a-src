package toni.lib.animation;

import net.minecraft.client.gui.GuiGraphics;
import org.joml.Quaternionf;

public class PoseUtils {
   public static void applyScale(GuiGraphics context, float scale) {
      context.pose().translate(context.guiWidth() / scale, context.guiHeight() / scale, 0.0F);
      context.pose().scale(scale, scale, scale);
      context.pose().translate(context.guiWidth() / (-scale * scale), context.guiHeight() / (-scale * scale), 0.0F);
   }

   public static void applyYRotation(GuiGraphics context, float scale, float objectWidth, float objectHeight, float rotY) {
      float d2r = (float) (Math.PI / 180.0);
      Quaternionf quat = new Quaternionf().rotationXYZ(0.0F * d2r, rotY * d2r, 0.0F * d2r);
      float width = objectWidth / 2.0F;
      float height = objectHeight / 2.0F;
      context.pose().translate(width, height, 0.0F);
      context.pose().mulPose(quat);
      context.pose().translate(-width, -height, 0.0F);
   }

   public static void applyXRotation(GuiGraphics context, float scale, float objectWidth, float objectHeight, float rotX) {
      float d2r = (float) (Math.PI / 180.0);
      Quaternionf quat = new Quaternionf().rotationXYZ(rotX * d2r, d2r, 0.0F * d2r);
      float width = objectWidth / 2.0F;
      float height = objectHeight / 2.0F;
      context.pose().translate(width, height, 0.0F);
      context.pose().mulPose(quat);
      context.pose().translate(-width, -height, 0.0F);
   }

   public static void applyZRotation(GuiGraphics context, float scale, float objectWidth, float objectHeight, float rotX) {
      float d2r = (float) (Math.PI / 180.0);
      Quaternionf quat = new Quaternionf().rotationXYZ(0.0F * d2r, d2r, rotX * d2r);
      float width = objectWidth / 2.0F;
      float height = objectHeight / 2.0F;
      context.pose().translate(width, height, 0.0F);
      context.pose().mulPose(quat);
      context.pose().translate(-width, -height, 0.0F);
   }

   public static void applyPosition(GuiGraphics context, float scale, float objectWidth, float objectHeight, float posX, float posY, float posZ) {
      float centerX = posX / scale + (context.guiWidth() - objectWidth * scale) / (2.0F * scale);
      float centerY = posY / scale + (context.guiHeight() - objectHeight * scale) / (2.0F * scale);
      context.pose().translate(centerX, centerY, posZ);
   }
}
