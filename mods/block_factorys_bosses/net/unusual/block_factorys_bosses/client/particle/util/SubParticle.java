package net.unusual.block_factorys_bosses.client.particle.util;

import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SubParticle {
   public static final float TICK_DELTA = 0.05F;
   public final SubParticleEmitter parent;
   public final int parentLifetime;
   public final SpriteSet set;
   public final int lifeTime;
   public final float random1;
   public final float random2;
   public final float random3;
   public final float random4;
   public TextureAtlasSprite sprite;
   public int age = 0;
   public Vec3 pos = Vec3.ZERO;
   public Vec3 pos0 = Vec3.ZERO;
   public Vec3 velocity = Vec3.ZERO;
   public Vec3 linearAcceleration = Vec3.ZERO;
   public double linearDragCoefficient = 0.0;
   protected final List<Pair<Float, BiConsumer<SubParticle, Float>>> lifetimeEvents;

   public SubParticle(SubParticleEmitter parent, SpriteSet set, int lifeTime, RandomSource random) {
      this.parent = parent;
      this.parentLifetime = parent.getLifetime();
      this.set = set;
      this.lifeTime = lifeTime;
      this.random1 = Mth.randomBetween(random, 0.0F, 1.0F);
      this.random2 = Mth.randomBetween(random, 0.0F, 1.0F);
      this.random3 = Mth.randomBetween(random, 0.0F, 1.0F);
      this.random4 = Mth.randomBetween(random, 0.0F, 1.0F);
      this.sprite = this.set.get(this.age, this.lifeTime);
      this.lifetimeEvents = this.createLifetimeEvents();
   }

   protected List<Pair<Float, BiConsumer<SubParticle, Float>>> createLifetimeEvents() {
      return new ArrayList<>();
   }

   public void tick() {
      if (this.age <= this.lifeTime) {
         this.tickSprite();
         this.pos0 = new Vec3(this.pos.x, this.pos.y, this.pos.z);
         Vec3 acceleration = this.getLinearAcceleration().subtract(this.velocity.scale(this.linearDragCoefficient));
         this.velocity = this.velocity.add(acceleration.scale(0.05F));
         this.pos = this.pos.add(this.velocity.scale(0.05F));
      }
   }

   protected void tickSprite() {
      this.sprite = this.set.get(this.age, this.lifeTime);
   }

   public void onRender(float partialTick) {
      if (!this.lifetimeEvents.isEmpty()) {
         float ageInSeconds = (this.age + partialTick) / 20.0F;
         Pair<Float, BiConsumer<SubParticle, Float>> pair = this.lifetimeEvents.getFirst();
         if (ageInSeconds >= (Float)pair.getFirst()) {
            ((BiConsumer)pair.getSecond()).accept(this, partialTick);
            this.lifetimeEvents.removeFirst();
         }
      }
   }

   public Vec3 getLinearAcceleration() {
      return this.linearAcceleration;
   }

   public float getParentAgeInSeconds(float partialTick) {
      return (this.parent.isAlive() ? this.parent.getAge() + partialTick : this.parentLifetime) / 20.0F;
   }

   public float getU0() {
      return this.sprite.getU0();
   }

   public float getU1() {
      return this.sprite.getU1();
   }

   public float getV0() {
      return this.sprite.getV0();
   }

   public float getV1() {
      return this.sprite.getV1();
   }

   public Vec3 getDirection() {
      return this.velocity.normalize();
   }

   public void setDirection(Vec3 direction) {
      this.velocity = direction.scale(this.velocity.length());
   }

   public double getSpeed() {
      return this.velocity.length();
   }

   public void setSpeed(double speed) {
      this.velocity = this.getDirection().scale(speed);
   }

   public Vec3 getVelocity() {
      return this.velocity;
   }

   public void setVelocity(Vec3 velocity) {
      this.velocity = velocity;
   }

   public void setVelocity(Vec3 direction, float speed) {
      this.setVelocity(direction.scale(speed));
   }

   public Vec3 getActualPosition(float partialTick, float ageInSeconds) {
      return this.parent.getActualSubPos(this, partialTick, ageInSeconds);
   }
}
