package com.bobmowzie.mowziesmobs.client.particle.util;

import com.bobmowzie.mowziesmobs.client.model.tools.SimplexNoise;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.function.Function;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public abstract class ParticleComponent {
   public void init(AdvancedParticleBase particle) {
   }

   public void preUpdate(AdvancedParticleBase particle) {
   }

   public void postUpdate(AdvancedParticleBase particle) {
   }

   public void preRender(AdvancedParticleBase particle, float partialTicks) {
   }

   public void postRender(AdvancedParticleBase particle, VertexConsumer buffer, Camera renderInfo, float partialTicks, int lightmap) {
   }

   public static ParticleComponent.Constant constant(float value) {
      return new ParticleComponent.Constant(value);
   }

   public abstract static class AnimData {
      public float evaluate(float t) {
         return 0.0F;
      }
   }

   public static class AnimatedTexture extends ParticleComponent {
   }

   public static class Attractor extends ParticleComponent {
      private final Vec3[] location;
      private final ParticleComponent.AnimData strengthData;
      private final float killDist;
      private final ParticleComponent.Attractor.EnumAttractorBehavior behavior;
      private Vec3 startLocation;

      public Attractor(Vec3[] location, float strength, float killDist, ParticleComponent.Attractor.EnumAttractorBehavior behavior) {
         this(location, new ParticleComponent.Constant(strength), killDist, behavior);
      }

      public Attractor(Vec3[] location, ParticleComponent.AnimData strength, float killDist, ParticleComponent.Attractor.EnumAttractorBehavior behavior) {
         this.location = location;
         this.strengthData = strength;
         this.killDist = killDist;
         this.behavior = behavior;
      }

      @Override
      public void init(AdvancedParticleBase particle) {
         this.startLocation = new Vec3(particle.getPosX(), particle.getPosY(), particle.getPosZ());
      }

      @Override
      public void preUpdate(AdvancedParticleBase particle) {
         float ageFrac = particle.getAge() / (particle.getLifetime() - 1);
         double strength = this.strengthData.evaluate(ageFrac);
         if (this.location.length > 0) {
            Vec3 destinationVec = this.location[0];
            Vec3 currPos = new Vec3(particle.getPosX(), particle.getPosY(), particle.getPosZ());
            Vec3 diff = destinationVec.subtract(currPos);
            if (diff.length() < this.killDist) {
               particle.remove();
            }

            if (this.behavior == ParticleComponent.Attractor.EnumAttractorBehavior.EXPONENTIAL) {
               Vec3 path = destinationVec.subtract(this.startLocation).scale(Math.pow(ageFrac, strength)).add(this.startLocation).subtract(currPos);
               particle.move(path.x, path.y, path.z);
            } else if (this.behavior == ParticleComponent.Attractor.EnumAttractorBehavior.LINEAR) {
               Vec3 path = destinationVec.subtract(this.startLocation).scale(ageFrac).add(this.startLocation).subtract(currPos);
               particle.move(path.x, path.y, path.z);
            } else {
               double dist = Math.max(diff.length(), 0.001);
               diff = diff.normalize().scale(strength / (dist * dist));
               particle.setMotionX(Math.min(particle.getMotionX() + diff.x, 5.0));
               particle.setMotionY(Math.min(particle.getMotionY() + diff.y, 5.0));
               particle.setMotionZ(Math.min(particle.getMotionZ() + diff.z, 5.0));
            }
         }
      }

      public enum EnumAttractorBehavior {
         LINEAR,
         EXPONENTIAL,
         SIMULATED;
      }
   }

   public static class Constant extends ParticleComponent.AnimData {
      float value;

      public Constant(float value) {
         this.value = value;
      }

      @Override
      public float evaluate(float t) {
         return this.value;
      }
   }

   public static class CurlNoise extends ParticleComponent {
      float strength;
      float frequency;

      public CurlNoise(float strength, float frequency) {
         this.strength = strength;
         this.frequency = frequency;
      }

      @Override
      public void preUpdate(AdvancedParticleBase particle) {
         super.preUpdate(particle);
         Vec3 curlNoise = this.curlNoise(particle.getPos().scale(this.frequency)).scale(this.strength);
         particle.setMotionX(particle.getMotionX() + curlNoise.x());
         particle.setMotionY(particle.getMotionY() + curlNoise.y());
         particle.setMotionZ(particle.getMotionZ() + curlNoise.z());
      }

      Vec3 snoiseVec3(Vec3 x) {
         double s = SimplexNoise.noise(x.x, x.y, x.z);
         double s1 = SimplexNoise.noise(x.y - 19.1, x.z + 33.4, x.x + 47.2);
         double s2 = SimplexNoise.noise(x.z + 74.2, x.x - 124.5, x.y + 99.4);
         return new Vec3(s, s1, s2);
      }

      Vec3 curlNoise(Vec3 p) {
         float e = 0.1F;
         Vec3 dx = new Vec3(e, 0.0, 0.0);
         Vec3 dy = new Vec3(0.0, e, 0.0);
         Vec3 dz = new Vec3(0.0, 0.0, e);
         Vec3 p_x0 = this.snoiseVec3(p.subtract(dx));
         Vec3 p_x1 = this.snoiseVec3(p.add(dx));
         Vec3 p_y0 = this.snoiseVec3(p.subtract(dy));
         Vec3 p_y1 = this.snoiseVec3(p.add(dy));
         Vec3 p_z0 = this.snoiseVec3(p.subtract(dz));
         Vec3 p_z1 = this.snoiseVec3(p.add(dz));
         double x = p_y1.z - p_y0.z - p_z1.y + p_z0.y;
         double y = p_z1.x - p_z0.x - p_x1.z + p_x0.z;
         double z = p_x1.y - p_x0.y - p_y1.x + p_y0.x;
         double divisor = 1.0 / (2.0 * e);
         return new Vec3(x, y, z).scale(divisor).normalize();
      }
   }

   public static class Expression extends ParticleComponent.AnimData {
      private final Function<Float, Float> expression;

      public Expression(Function<Float, Float> expression) {
         this.expression = expression;
      }

      @Override
      public float evaluate(float t) {
         return this.expression.apply(t);
      }
   }

   public static class FaceMotion extends ParticleComponent {
      @Override
      public void preRender(AdvancedParticleBase particle, float partialTicks) {
         super.preRender(particle, partialTicks);
         double dx = particle.getPosX() - particle.getPrevPosX();
         double dy = particle.getPosY() - particle.getPrevPosY();
         double dz = particle.getPosZ() - particle.getPrevPosZ();
         double d = Math.sqrt(dx * dx + dy * dy + dz * dz);
         if (d != 0.0) {
            if (particle.rotation instanceof ParticleRotation.EulerAngles eulerRot) {
               double a = dy / d;
               a = Math.max(-1.0, Math.min(1.0, a));
               float pitch = -((float)Math.asin(a));
               float yaw = -((float)(Math.atan2(dz, dx) + Math.PI));
               eulerRot.roll = pitch;
               eulerRot.yaw = yaw;
            } else if (particle.rotation instanceof ParticleRotation.OrientVector orientRot) {
               orientRot.orientation = new Vec3(dx, dy, dz).normalize();
            }
         }
      }
   }

   public static class ForceOverTime extends ParticleComponent {
      ParticleComponent.AnimData fx;
      ParticleComponent.AnimData fy;
      ParticleComponent.AnimData fz;

      public ForceOverTime(Vec3 force) {
         this.fx = new ParticleComponent.Constant((float)force.x());
         this.fy = new ParticleComponent.Constant((float)force.y());
         this.fz = new ParticleComponent.Constant((float)force.z());
      }

      public ForceOverTime(ParticleComponent.AnimData fx, ParticleComponent.AnimData fy, ParticleComponent.AnimData fz) {
         this.fx = fx;
         this.fy = fy;
         this.fz = fz;
      }

      @Override
      public void preUpdate(AdvancedParticleBase particle) {
         super.preUpdate(particle);
         float ageFrac = particle.getAge() / particle.getLifetime();
         particle.setMotionX(particle.getMotionX() + this.fx.evaluate(ageFrac));
         particle.setMotionY(particle.getMotionY() + this.fy.evaluate(ageFrac));
         particle.setMotionZ(particle.getMotionZ() + this.fz.evaluate(ageFrac));
      }
   }

   public static class Gravity extends ParticleComponent {
      private final ParticleComponent.AnimData animData;

      public Gravity(ParticleComponent.AnimData gravityOverTime) {
         this.animData = gravityOverTime;
      }

      public Gravity(float gravity) {
         this(new ParticleComponent.Constant(gravity));
      }

      @Override
      public void init(AdvancedParticleBase particle) {
         particle.setGravity(this.animData.evaluate(0.0F));
      }

      @Override
      public void preUpdate(AdvancedParticleBase particle) {
         super.preUpdate(particle);
         float ageFrac = particle.getAge() / particle.getLifetime();
         particle.setGravity(this.animData.evaluate(ageFrac));
      }
   }

   public static class KeyTrack extends ParticleComponent.AnimData {
      float[] values;
      float[] times;

      public KeyTrack(float[] values, float[] times) {
         this.values = values;
         this.times = times;
         if (values.length != times.length) {
            System.out.println("Malformed key track. Must have same number of keys and values or key track will evaluate to 0.");
         }
      }

      @Override
      public float evaluate(float t) {
         if (this.values.length != this.times.length) {
            return 0.0F;
         }

         for (int i = 0; i < this.times.length; i++) {
            float time = this.times[i];
            if (t == time) {
               return this.values[i];
            }

            if (t < time) {
               if (i == 0) {
                  return this.values[0];
               }

               float a = (t - this.times[i - 1]) / (time - this.times[i - 1]);
               return this.values[i - 1] * (1.0F - a) + this.values[i] * a;
            }

            if (i == this.values.length - 1) {
               return this.values[i];
            }
         }

         return 0.0F;
      }

      public static ParticleComponent.KeyTrack startAndEnd(float startValue, float endValue) {
         return new ParticleComponent.KeyTrack(new float[]{startValue, endValue}, new float[]{0.0F, 1.0F});
      }

      public static ParticleComponent.KeyTrack oscillate(float value1, float value2, int frequency) {
         if (frequency <= 1) {
            new ParticleComponent.KeyTrack(new float[]{value1, value2}, new float[]{0.0F, 1.0F});
         }

         float step = 1.0F / frequency;
         float[] times = new float[frequency + 1];
         float[] values = new float[frequency + 1];

         for (int i = 0; i < frequency + 1; i++) {
            float value = i % 2 == 0 ? value1 : value2;
            times[i] = step * i;
            values[i] = value;
         }

         return new ParticleComponent.KeyTrack(values, times);
      }
   }

   public static class Orbit extends ParticleComponent {
      private final Vec3[] location;
      private final ParticleComponent.AnimData phase;
      private final ParticleComponent.AnimData radius;
      private final ParticleComponent.AnimData axisX;
      private final ParticleComponent.AnimData axisY;
      private final ParticleComponent.AnimData axisZ;
      private final boolean faceCamera;

      public Orbit(
         Vec3[] location,
         ParticleComponent.AnimData phase,
         ParticleComponent.AnimData radius,
         ParticleComponent.AnimData axisX,
         ParticleComponent.AnimData axisY,
         ParticleComponent.AnimData axisZ,
         boolean faceCamera
      ) {
         this.location = location;
         this.phase = phase;
         this.radius = radius;
         this.axisX = axisX;
         this.axisY = axisY;
         this.axisZ = axisZ;
         this.faceCamera = faceCamera;
      }

      @Override
      public void init(AdvancedParticleBase particle) {
         this.apply(particle, 0.0F);
      }

      @Override
      public void preUpdate(AdvancedParticleBase particle) {
         float ageFrac = particle.getAge() / particle.getLifetime();
         this.apply(particle, ageFrac);
      }

      private void apply(AdvancedParticleBase particle, float t) {
         float p = this.phase.evaluate(t);
         float r = this.radius.evaluate(t);
         Vector3f axis;
         if (this.faceCamera && Minecraft.getInstance().player != null) {
            Vec3 lookAngle = Minecraft.getInstance().player.getLookAngle();
            axis = new Vector3f((float)lookAngle.x(), (float)lookAngle.y(), (float)lookAngle.z());
            axis.normalize();
         } else {
            axis = new Vector3f(this.axisX.evaluate(t), this.axisY.evaluate(t), this.axisZ.evaluate(t));
            axis.normalize();
         }

         Quaternionf quat = new Quaternionf(new AxisAngle4f(p * (float) Math.PI * 2.0F, axis));
         Vector3f up = new Vector3f(0.0F, 1.0F, 0.0F);
         Vector3f start = axis;
         if (Math.abs(axis.dot(up)) > 0.99) {
            start = new Vector3f(1.0F, 0.0F, 0.0F);
         }

         start.cross(up);
         start.normalize();
         Vector3f newPos = start;
         quat.transform(newPos);
         newPos.mul(r);
         if (this.location.length > 0 && this.location[0] != null) {
            newPos.add((float)this.location[0].x, (float)this.location[0].y, (float)this.location[0].z);
         }

         particle.setPos(newPos.x(), newPos.y(), newPos.z());
      }
   }

   public static class Oscillator extends ParticleComponent.AnimData {
      float value1;
      float value2;
      float frequency;
      float phaseShift;

      public Oscillator(float value1, float value2, float frequency, float phaseShift) {
         this.value1 = value1;
         this.value2 = value2;
         this.frequency = frequency;
         this.phaseShift = phaseShift;
      }

      @Override
      public float evaluate(float t) {
         float a = (this.value2 - this.value1) / 2.0F;
         return (float)(this.value1 + a + a * Math.cos(t * this.frequency + this.phaseShift));
      }
   }

   public static class PinLocation extends ParticleComponent {
      private final Vec3[] location;
      private int pinDuration = -1;
      private boolean inheritVelocity = false;
      private Vec3 velocityOnRelease;

      public PinLocation(Vec3[] location) {
         this.location = location;
      }

      public PinLocation(Vec3[] location, int pinDuration) {
         this.location = location;
         this.pinDuration = pinDuration;
      }

      @Override
      public void init(AdvancedParticleBase particle) {
         if (this.location != null && this.location.length > 0 && this.location[0] != null) {
            particle.setPos(this.location[0].x, this.location[0].y, this.location[0].z);
         }
      }

      @Override
      public void preUpdate(AdvancedParticleBase particle) {
         if (this.location != null && this.location.length > 0 && this.location[0] != null && (particle.getAge() <= this.pinDuration || this.pinDuration == -1)
            )
          {
            particle.setPos(this.location[0].x, this.location[0].y, this.location[0].z);
            particle.setMotionX(0.0);
            particle.setMotionY(0.0);
            particle.setMotionZ(0.0);
         }

         if (particle.getAge() == this.pinDuration) {
            if (this.inheritVelocity) {
               particle.setMotionX(particle.getPosX() - particle.getPrevPosX());
               particle.setMotionY(particle.getPosY() - particle.getPrevPosY());
               particle.setMotionZ(particle.getPosZ() - particle.getPrevPosZ());
            }

            if (this.velocityOnRelease != null) {
               particle.setMotionX(particle.getMotionX() + this.velocityOnRelease.x());
               particle.setMotionY(particle.getMotionY() + this.velocityOnRelease.y());
               particle.setMotionZ(particle.getMotionZ() + this.velocityOnRelease.z());
            }
         }
      }

      @Override
      public void preRender(AdvancedParticleBase particle, float partialTicks) {
         super.preRender(particle, partialTicks);
         particle.doRender = this.location != null && this.location.length > 0 && this.location[0] != null;
      }

      public ParticleComponent.PinLocation setVelocityOnRelease(float x, float y, float z) {
         this.velocityOnRelease = new Vec3(x, y, z);
         return this;
      }

      public ParticleComponent.PinLocation setInheritVelocity(boolean inheritVelocity) {
         this.inheritVelocity = inheritVelocity;
         return this;
      }
   }

   public static class PropertyControl extends ParticleComponent {
      private final ParticleComponent.AnimData animData;
      private final ParticleComponent.PropertyControl.EnumParticleProperty property;
      private final boolean additive;

      public PropertyControl(ParticleComponent.PropertyControl.EnumParticleProperty property, ParticleComponent.AnimData animData, boolean additive) {
         this.property = property;
         this.animData = animData;
         this.additive = additive;
      }

      @Override
      public void init(AdvancedParticleBase particle) {
         float value = this.animData.evaluate(0.0F);
         this.applyUpdate(particle, value);
         this.applyRender(particle, value);
      }

      @Override
      public void preRender(AdvancedParticleBase particle, float partialTicks) {
         float ageFrac = (particle.getAge() + partialTicks) / particle.getLifetime();
         float value = this.animData.evaluate(ageFrac);
         this.applyRender(particle, value);
      }

      @Override
      public void preUpdate(AdvancedParticleBase particle) {
         float ageFrac = particle.getAge() / particle.getLifetime();
         float value = this.animData.evaluate(ageFrac);
         this.applyUpdate(particle, value);
      }

      private void applyUpdate(AdvancedParticleBase particle, float value) {
         if (this.property == ParticleComponent.PropertyControl.EnumParticleProperty.POS_X) {
            if (this.additive) {
               particle.setPosX(particle.getPosX() + value);
            } else {
               particle.setPosX(value);
            }
         } else if (this.property == ParticleComponent.PropertyControl.EnumParticleProperty.POS_Y) {
            if (this.additive) {
               particle.setPosY(particle.getPosY() + value);
            } else {
               particle.setPosY(value);
            }
         } else if (this.property == ParticleComponent.PropertyControl.EnumParticleProperty.POS_Z) {
            if (this.additive) {
               particle.setPosZ(particle.getPosZ() + value);
            } else {
               particle.setPosZ(value);
            }
         } else if (this.property == ParticleComponent.PropertyControl.EnumParticleProperty.MOTION_X) {
            if (this.additive) {
               particle.setMotionX(particle.getMotionX() + value);
            } else {
               particle.setMotionX(value);
            }
         } else if (this.property == ParticleComponent.PropertyControl.EnumParticleProperty.MOTION_Y) {
            if (this.additive) {
               particle.setMotionY(particle.getMotionY() + value);
            } else {
               particle.setMotionY(value);
            }
         } else if (this.property == ParticleComponent.PropertyControl.EnumParticleProperty.MOTION_Z) {
            if (this.additive) {
               particle.setMotionZ(particle.getMotionZ() + value);
            } else {
               particle.setMotionZ(value);
            }
         } else if (this.property == ParticleComponent.PropertyControl.EnumParticleProperty.AIR_DRAG) {
            if (this.additive) {
               particle.airDrag += value;
            } else {
               particle.airDrag = value;
            }
         } else if (this.property == ParticleComponent.PropertyControl.EnumParticleProperty.SCALE) {
            if (this.additive) {
               particle.scale += value;
            } else {
               particle.scale = value;
            }
         }
      }

      private void applyRender(AdvancedParticleBase particle, float value) {
         if (this.property == ParticleComponent.PropertyControl.EnumParticleProperty.RED) {
            if (this.additive) {
               particle.red += value;
            } else {
               particle.red = value;
            }
         } else if (this.property == ParticleComponent.PropertyControl.EnumParticleProperty.GREEN) {
            if (this.additive) {
               particle.green += value;
            } else {
               particle.green = value;
            }
         } else if (this.property == ParticleComponent.PropertyControl.EnumParticleProperty.BLUE) {
            if (this.additive) {
               particle.blue += value;
            } else {
               particle.blue = value;
            }
         } else if (this.property == ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA) {
            if (this.additive) {
               particle.alpha += value;
            } else {
               particle.alpha = value;
            }
         } else if (this.property == ParticleComponent.PropertyControl.EnumParticleProperty.YAW) {
            if (particle.rotation instanceof ParticleRotation.EulerAngles eulerRot) {
               if (this.additive) {
                  eulerRot.yaw += value;
               } else {
                  eulerRot.yaw = value;
               }
            }
         } else if (this.property == ParticleComponent.PropertyControl.EnumParticleProperty.PITCH) {
            if (particle.rotation instanceof ParticleRotation.EulerAngles eulerRot) {
               if (this.additive) {
                  eulerRot.pitch += value;
               } else {
                  eulerRot.pitch = value;
               }
            }
         } else if (this.property == ParticleComponent.PropertyControl.EnumParticleProperty.ROLL) {
            if (particle.rotation instanceof ParticleRotation.EulerAngles eulerRot) {
               if (this.additive) {
                  eulerRot.roll += value;
               } else {
                  eulerRot.roll = value;
               }
            }
         } else if (this.property == ParticleComponent.PropertyControl.EnumParticleProperty.PARTICLE_ANGLE
            && particle.rotation instanceof ParticleRotation.FaceCamera faceCameraRot) {
            if (this.additive) {
               faceCameraRot.angle += value;
            } else {
               faceCameraRot.angle = value;
            }
         }
      }

      public enum EnumParticleProperty {
         POS_X,
         POS_Y,
         POS_Z,
         MOTION_X,
         MOTION_Y,
         MOTION_Z,
         RED,
         GREEN,
         BLUE,
         ALPHA,
         SCALE,
         YAW,
         PITCH,
         ROLL,
         PARTICLE_ANGLE,
         AIR_DRAG;
      }
   }

   public static class ScreenSpace extends ParticleComponent {
      private final ParticleComponent.AnimData x;
      private final ParticleComponent.AnimData y;
      private final ParticleComponent.AnimData z;

      public ScreenSpace(ParticleComponent.AnimData x, ParticleComponent.AnimData y, ParticleComponent.AnimData z) {
         this.x = x;
         this.y = y;
         this.z = z;
      }

      public ScreenSpace(float x, float y, float z) {
         this(new ParticleComponent.Constant(x), new ParticleComponent.Constant(y), new ParticleComponent.Constant(z));
      }

      @Override
      public void preUpdate(AdvancedParticleBase particle) {
         super.preUpdate(particle);
         particle.isScreenSpace = true;
         float ageFrac = particle.getAge() / particle.getLifetime();
         particle.screenXo = particle.screenX;
         particle.screenYo = particle.screenY;
         particle.screenZo = particle.screenZ;
         particle.screenX = this.x.evaluate(ageFrac);
         particle.screenY = this.y.evaluate(ageFrac);
         particle.screenZ = this.z.evaluate(ageFrac);
      }
   }

   public static class Vortex extends ParticleComponent {
      private Vec3 axis;
      private Vec3 origin;
      private ParticleComponent.AnimData amount;

      public Vortex(Vec3 axis, Vec3 origin, ParticleComponent.AnimData amount) {
         this.axis = axis;
         this.origin = origin;
         this.amount = amount;
      }

      @Override
      public void preUpdate(AdvancedParticleBase particle) {
         super.preUpdate(particle);
         float ageFrac = particle.getAge() / particle.getLifetime();
         float amount = this.amount.evaluate(ageFrac);
         Vec3 diff = this.origin.subtract(particle.getPos()).normalize();
         Vec3 force = diff.cross(this.axis).scale(amount);
         particle.setMotionX(particle.getMotionX() + force.x);
         particle.setMotionY(particle.getMotionY() + force.y);
         particle.setMotionZ(particle.getMotionZ() + force.z);
      }
   }
}
