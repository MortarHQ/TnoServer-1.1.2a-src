package twilightforest.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LightLayer;

public class FireflyParticle extends TextureSheetParticle {
   private final int halfLife;
   private final boolean checkSkylight;

   public FireflyParticle(
      ClientLevel level, double x, double y, double z, float movementX, float movementY, float movementZ, int minlife, boolean checkSkylight
   ) {
      super(level, x, y, z, 0.0, 0.0, 0.0);
      this.xd *= movementX;
      this.yd *= movementY;
      this.zd *= movementZ;
      this.rCol = 0.5F + this.random.nextFloat() * 0.25F;
      this.gCol = 0.85F - this.random.nextFloat() * 0.25F;
      this.bCol = 0.0F;
      this.quadSize = 0.2F + this.random.nextFloat() * 0.1F;
      this.lifetime = minlife + this.random.nextInt(21);
      this.halfLife = this.lifetime / 2;
      this.hasPhysics = true;
      this.checkSkylight = checkSkylight;
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
   }

   public void render(VertexConsumer buffer, Camera camera, float partialTicks) {
      this.alpha = this.getGlowBrightness();
      super.render(buffer, camera, partialTicks);
   }

   public void tick() {
      if (this.checkSkylight && this.level.getBrightness(LightLayer.SKY, BlockPos.containing(this.x, this.y, this.z)) <= 12) {
         this.remove();
      }

      super.tick();
   }

   public float getGlowBrightness() {
      int lifeTime = this.lifetime - this.age;
      return lifeTime <= this.halfLife ? (float)lifeTime / this.halfLife : Math.max(1.0F - ((float)lifeTime - this.halfLife) / this.halfLife, 0.0F);
   }

   public int getLightColor(float partialTicks) {
      return 15728880;
   }

   public record ParticleSpawnerProvider(SpriteSet sprite) implements ParticleProvider<SimpleParticleType> {
      public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         FireflyParticle particle = new FireflyParticle(level, x, y, z, 0.1F, 0.1F, 0.1F, 30, false);
         RandomSource rand = level.getRandom();
         particle.xd += rand.nextFloat() * (rand.nextBoolean() ? -3.9 : 3.9) * rand.nextFloat() * 0.1;
         particle.yd += rand.nextFloat() * -0.25 * rand.nextFloat() * 0.1;
         particle.zd += rand.nextFloat() * (rand.nextBoolean() ? -3.9 : 3.9) * rand.nextFloat() * 0.1;
         particle.pickSprite(this.sprite());
         return particle;
      }
   }

   public record StationaryProvider(SpriteSet sprite) implements ParticleProvider<SimpleParticleType> {
      public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         FireflyParticle particle = new FireflyParticle(level, x, y, z, 0.0F, 0.0F, 0.0F, 10, false);
         particle.pickSprite(this.sprite());
         return particle;
      }
   }

   public record WanderingProvider(SpriteSet sprite) implements ParticleProvider<SimpleParticleType> {
      public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         FireflyParticle particle = new FireflyParticle(level, x, y, z, 0.1F, 0.1F, 0.1F, 30, true);
         RandomSource rand = level.getRandom();
         particle.xd += rand.nextFloat() * (rand.nextBoolean() ? -3.9 : 3.9) * rand.nextFloat() * 0.1;
         particle.yd += rand.nextFloat() * -0.25 * rand.nextFloat() * 0.1;
         particle.zd += rand.nextFloat() * (rand.nextBoolean() ? -3.9 : 3.9) * rand.nextFloat() * 0.1;
         particle.pickSprite(this.sprite());
         return particle;
      }
   }
}
