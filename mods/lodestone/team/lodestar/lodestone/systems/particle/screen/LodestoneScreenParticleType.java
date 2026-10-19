package team.lodestar.lodestone.systems.particle.screen;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.ParticleEngine.MutableSpriteSet;
import team.lodestar.lodestone.systems.particle.screen.base.ScreenParticle;

public class LodestoneScreenParticleType extends ScreenParticleType<ScreenParticleOptions> {
   public record Factory(SpriteSet sprite) implements ScreenParticleType.ParticleProvider<ScreenParticleOptions> {
      @Override
      public ScreenParticle createParticle(ClientLevel pLevel, ScreenParticleOptions options, double x, double y, double pXSpeed, double pYSpeed) {
         return new LodestoneScreenParticle(pLevel, options, (MutableSpriteSet)this.sprite, x, y, pXSpeed, pYSpeed);
      }
   }
}
