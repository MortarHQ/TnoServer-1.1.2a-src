package it.hurts.octostudios.immersiveui.client;

import it.hurts.octostudios.immersiveui.ImmersiveUI;

public class RenderInfo {
   public float currentAngle = 0.0F;
   public float targetAngle = 0.0F;
   public float currentAngleVelocity = 0.0F;
   public float easingSpeed = ImmersiveUI.CONFIG.getFloatingItemEasingSpeed();
   public final float inertiaDamping = 0.75F;
}
