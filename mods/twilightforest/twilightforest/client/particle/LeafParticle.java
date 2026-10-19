package twilightforest.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import twilightforest.client.particle.data.LeafParticleData;

public class LeafParticle extends TextureSheetParticle {
   private final Vec3 target;
   private float rot;

   public LeafParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz) {
      this(world, x, y, z, vx, vy, vz, 1.0F);
   }

   public LeafParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, float scale) {
      super(world, x, y, z, 0.0, 0.0, 0.0);
      this.target = new Vec3(x, y, z);
      this.xd *= 0.1;
      this.yd *= 0.1;
      this.zd *= 0.1;
      this.xd += vx * 0.4;
      this.yd += vy * 0.4;
      this.zd += vz * 0.4;
      this.rCol = this.gCol = this.bCol = 1.0F;
      this.alpha = 0.0F;
      this.quadSize = this.quadSize * (0.75F * (this.random.nextBoolean() ? -1.0F : 1.0F));
      this.quadSize *= scale;
      this.lifetime = 160 + (int)(this.random.nextFloat() * 30.0F);
      this.lifetime = (int)(this.lifetime * scale);
      this.hasPhysics = true;
      this.oRoll = this.roll = this.random.nextFloat() * 2.0F - 1.0F;
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   public void tick() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      if (this.age++ >= this.lifetime) {
         this.remove();
      }

      this.move(this.xd, this.yd, this.zd);
      this.yd *= 0.7;
      this.yd -= 0.02;
      if (this.onGround) {
         this.xd *= 0.7;
         this.zd *= 0.7;
      } else {
         this.rot += 5.0F;
         if (this.xd == 0.0) {
            this.xd = this.xd + (this.random.nextBoolean() ? 1 : -1) * 0.001F;
         }

         if (this.zd == 0.0) {
            this.zd = this.zd + (this.random.nextBoolean() ? 1 : -1) * 0.001F;
         }

         if (this.random.nextInt(5) == 0) {
            this.xd = this.xd + Math.signum(this.target.x - this.x) * this.random.nextFloat() * 0.005F;
         }

         if (this.random.nextInt(5) == 0) {
            this.zd = this.zd + Math.signum(this.target.z - this.z) * this.random.nextFloat() * 0.005F;
         }
      }
   }

   public void render(VertexConsumer buffer, Camera entity, float partialTicks) {
      this.alpha = Math.min(Mth.clamp(this.age, 0, 20) / 20.0F, Mth.clamp(this.lifetime - this.age, 0, 20) / 20.0F);
      Quaternionf quaternion = new Quaternionf();
      if (this.roll != 0.0F) {
         quaternion.rotateZ(Mth.lerp(partialTicks, this.oRoll, this.roll));
      }

      quaternion.rotateY(Mth.cos((float)Math.toRadians(this.rot % 360.0F)));
      this.renderRotatedQuad(buffer, entity, quaternion, partialTicks);
      quaternion.rotateY((float) -Math.PI).rotateZ((float) (Math.PI / 2));
      this.renderRotatedQuad(buffer, entity, quaternion, partialTicks);
   }

   public int getLightColor(float partialTicks) {
      return 15728880;
   }

   public record Factory(SpriteSet sprite) implements ParticleProvider<LeafParticleData> {
      public Particle createParticle(LeafParticleData data, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         LeafParticle particle = new LeafParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
         particle.setColor(data.r() / 255.0F, data.g() / 255.0F, data.b() / 255.0F);
         particle.pickSprite(this.sprite);
         return particle;
      }
   }
}
