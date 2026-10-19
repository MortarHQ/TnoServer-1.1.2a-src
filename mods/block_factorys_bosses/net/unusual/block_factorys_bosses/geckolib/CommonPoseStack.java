package net.unusual.block_factorys_bosses.geckolib;

import org.joml.Matrix4f;
import org.joml.Quaternionf;

public interface CommonPoseStack {
   void bosses_rise_java$push();

   void bosses_rise_java$pop();

   void bosses_rise_java$translate(double var1, double var3, double var5);

   void bosses_rise_java$translate(float var1, float var2, float var3);

   void bosses_rise_java$scale(float var1, float var2, float var3);

   void bosses_rise_java$mulPose(Matrix4f var1);

   void bosses_rise_java$mulPose(Quaternionf var1);

   Matrix4f bosses_rise_java$last();
}
