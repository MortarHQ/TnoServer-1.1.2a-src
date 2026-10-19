package it.hurts.octostudios.immersiveui.client.particle;

import it.hurts.octostudios.immersiveui.util.VectorUtils;
import it.hurts.octostudios.octolib.client.particle.ExtendedUIParticle;
import it.hurts.octostudios.octolib.client.particle.UIParticle.Layer;
import it.hurts.octostudios.octolib.client.particle.UIParticle.Texture2D;
import it.hurts.octostudios.octolib.util.OctoColor;
import java.util.Random;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector2f;

public class FlameUIParticle extends ExtendedUIParticle {
   private static final Texture2D TEXTURE = new Texture2D(ResourceLocation.withDefaultNamespace("textures/particle/flame.png"), 8, 8);

   public FlameUIParticle(float xStart, float yStart, int lifeTime) {
      super(TEXTURE, 1.0F, lifeTime, xStart, yStart, Layer.SCREEN, 232.0F);
      Random rand = new Random();
      this.enableBlend(false);
      this.getTransform().setSize(new Vector2f(1.25F, 1.25F));
      this.setColors(new OctoColor[]{OctoColor.WHITE});
      this.setGravityDirection(new Vector2f(0.0F, -1.0F));
      this.setGravity(0.2F);
      this.setDirection(VectorUtils.rotate(new Vector2f(0.0F, -1.0F), (rand.nextFloat() - 0.5F) * 60.0F));
      this.getTransform().updateOldValues();
   }

   public void tick() {
      super.tick();
      float timeRatio = this.getTimeRatio(0.0F);
      this.getTransform().setSize(new Vector2f(1.0F - timeRatio, 1.0F - timeRatio));
   }
}
