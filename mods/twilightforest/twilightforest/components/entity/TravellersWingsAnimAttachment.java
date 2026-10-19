package twilightforest.components.entity;

import java.util.Random;

public class TravellersWingsAnimAttachment {
   public double accumulatedPhase = new Random().nextDouble(0.0, Math.PI * 2);
   public double oldAgeInTicks = 0.0;
   public float xRotOld = 0.0F;
   public float yRotOldLeft = 0.0F;
   public float yRotOldRight = 0.0F;
   public float zRotOld = 0.0F;
   public boolean doubleJump = false;
}
