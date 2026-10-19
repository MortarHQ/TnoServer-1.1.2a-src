package it.hurts.octostudios.octolib.module.particle.trail;

import net.minecraft.world.entity.projectile.Arrow;

public class TestArrowTrail extends EntityTrailProvider<Arrow> {
   public TestArrowTrail(Arrow entity) {
      super(entity);
   }

   @Override
   public int getTrailUpdateFrequency() {
      return 1;
   }

   @Override
   public int getTrailMaxLength() {
      return 10;
   }

   @Override
   public int getTrailFadeInColor() {
      return 255;
   }

   @Override
   public int getTrailFadeOutColor() {
      return -65281;
   }

   @Override
   public double getTrailScale() {
      return 0.15;
   }
}
