package it.hurts.octostudios.immersiveui.client.particle;

import it.hurts.octostudios.octolib.client.particle.ExtendedUIParticle;
import it.hurts.octostudios.octolib.client.particle.UIParticle.Layer;
import it.hurts.octostudios.octolib.client.particle.UIParticle.Texture2D;
import it.hurts.octostudios.octolib.util.OctoColor;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector2f;

public class RarityUIParticle extends ExtendedUIParticle {
   public static final Texture2D TEXTURE = new Texture2D(ResourceLocation.fromNamespaceAndPath("immersiveui", "textures/gui/spark.png"), 8, 8);

   public RarityUIParticle(
      float maxSpeed, int lifetime, float xStart, float yStart, float xDir, float yDir, float roll, int rarityColor, Layer layer, float zOffset
   ) {
      super(TEXTURE, maxSpeed, lifetime, xStart, yStart, layer, zOffset);
      this.setDirection(xDir, yDir);
      this.setRollVelocity(roll);
      this.setColors(new OctoColor[]{new OctoColor(-16777216 + rarityColor), new OctoColor(rarityColor)});
   }

   public void tick() {
      super.tick();
      float timeRatio = this.getTimeRatio(0.0F);
      this.getTransform().setSize(new Vector2f(1.0F - timeRatio, 1.0F - timeRatio));
   }
}
