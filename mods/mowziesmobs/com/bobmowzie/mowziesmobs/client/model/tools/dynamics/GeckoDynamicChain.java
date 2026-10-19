package com.bobmowzie.mowziesmobs.client.model.tools.dynamics;

import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoBone;
import com.bobmowzie.mowziesmobs.client.render.entity.MowzieGeoEntityRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Quaterniond;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3f;

public class GeckoDynamicChain {
   public Vec3[] p;
   public Vec3[] p0;
   private Vec3[] v;
   private Vec3[] a;
   private float[] m;
   private float[] d;
   private Vec3[] startingDirections;
   private Quaternionf[] startingOrientations;
   private final Entity entity;
   public Vec3[] renderPos;
   public Vec3[] prevRenderPos;
   public Vec3[] pOrig;
   private int prevUpdateTick;
   private float prevUpdateTime;
   private boolean isSimulating;
   public MowzieGeoBone[] chainOrig;
   public MowzieGeoBone[] chainDynamic;
   private float gravity = 0.1F;
   private float damping = 0.02F;
   private float stiffness = 1.0F;
   private float attractStrength = 0.1F;
   private boolean doAttract = false;
   private float attractFalloff = 0.5F;
   private int numUpdates = 10;
   private boolean floorCollision = true;
   private Vector3d chainDirection;
   private boolean isChainHidden = false;
   private float alpha = 1.0F;

   public GeckoDynamicChain(Entity entity) {
      this.entity = entity;
      this.p = new Vec3[0];
      this.p0 = new Vec3[0];
      this.v = new Vec3[0];
      this.a = new Vec3[0];
      this.m = new float[0];
      this.d = new float[0];
      this.startingDirections = new Vec3[0];
      this.pOrig = new Vec3[0];
      this.renderPos = new Vec3[0];
      this.prevRenderPos = new Vec3[0];
      this.prevUpdateTick = -1;
      this.prevUpdateTime = 0.0F;
      this.chainDirection = new Vector3d(0.0, -1.0, 0.0);
      this.isSimulating = true;
   }

   public void setGravity(float gravity) {
      this.gravity = gravity;
   }

   public void setDamping(float damping) {
      this.damping = damping;
   }

   public void setAttractStrength(float attractStrength) {
      this.attractStrength = attractStrength;
   }

   public void setDoAttract(boolean doAttract) {
      this.doAttract = doAttract;
   }

   public void setAttractFalloff(float attractFalloff) {
      this.attractFalloff = attractFalloff;
   }

   public void setNumUpdates(int numUpdates) {
      this.numUpdates = numUpdates;
   }

   public void setFloorCollision(boolean floorCollision) {
      this.floorCollision = floorCollision;
   }

   public void setChainDirection(Vector3d chainDirection) {
      this.chainDirection = chainDirection;
   }

   public void setStiffness(float stiffness) {
      this.stiffness = stiffness;
   }

   public void setAlpha(float alpha) {
      this.alpha = alpha;
   }

   public void setHidden(boolean hidden) {
      if (this.chainDynamic != null) {
         for (MowzieGeoBone bone : this.chainDynamic) {
            bone.setHidden(hidden);
         }

         this.isChainHidden = hidden;
      }
   }

   public void updateSpringConstraint(
      float gravityAmount, float dampAmount, float attractStrength, boolean doAttract, float attractFalloff, int numUpdates, float deltaTime
   ) {
      if (this.isSimulating) {
         float deltaTimePerUpdate = deltaTime / numUpdates;

         for (int j = 0; j < numUpdates; j++) {
            this.p[0] = this.p0[0].add(this.pOrig[0].subtract(this.p0[0]).scale((double)(j + 1) / numUpdates));

            for (int i = 1; i < this.p.length; i++) {
               if (deltaTime > 1.0F) {
                  this.p[i] = new Vec3(this.pOrig[i].x, this.pOrig[i].y, this.pOrig[i].z);
                  this.p0[i] = new Vec3(this.p[i].x, this.p[i].y, this.p[i].z);
                  this.a[i] = new Vec3(0.0, 0.0, 0.0);
               } else {
                  Vec3 prevPosition = new Vec3(this.p[i].x, this.p[i].y, this.p[i].z);
                  Vec3 force = new Vec3(0.0, 0.0, 0.0);
                  Vec3 gravity = new Vec3(0.0, -gravityAmount, 0.0);
                  force = force.add(gravity);
                  if (doAttract) {
                     Vec3 attract = this.pOrig[i].subtract(this.p[i]);
                     force = force.add(attract.scale(attractStrength / (1.0F + i * i * attractFalloff)));
                  }

                  this.a[i] = force.scale(1.0F / this.m[i]);
                  this.p[i] = this.p[i]
                     .add(this.p[i].subtract(this.p0[i]).scale(1.0 - dampAmount))
                     .add(this.a[i].scale(deltaTimePerUpdate * deltaTimePerUpdate).scale(1.0 - dampAmount));
                  Vec3 vectorToPrevious = this.p[i].subtract(this.p[i - 1]);
                  vectorToPrevious = vectorToPrevious.normalize().scale(this.d[i]);
                  this.p[i] = this.p[i - 1].add(vectorToPrevious);
                  this.p0[i] = new Vec3(prevPosition.x, prevPosition.y, prevPosition.z);
               }
            }
         }

         this.p0[0] = new Vec3(this.pOrig[0].x, this.pOrig[0].y, this.pOrig[0].z);
      } else {
         this.p[0] = new Vec3(this.pOrig[0].x, this.pOrig[0].y, this.pOrig[0].z);

         for (int i = 1; i < this.p.length; i++) {
            this.p0[i] = this.p[0];
            Vec3 diff = this.pOrig[i].subtract(this.p[i]);
            this.p[i] = this.pOrig[i].add(diff.scale(deltaTime * 1.0F));
         }

         this.p0[0] = new Vec3(this.p[0].x, this.p[0].y, this.p[0].z);
      }
   }

   public void setChainArrays(MowzieGeoBone[] chainOrig, MowzieGeoBone[] chainDynamic) {
      this.chainOrig = chainOrig;
      this.chainDynamic = chainDynamic;

      for (MowzieGeoBone bone : chainOrig) {
         bone.setTrackingMatrices(true);
      }
   }

   public void setChain(MowzieGeoBone[] chainOrig, MowzieGeoBone[] chainDynamic) {
      this.setChainArrays(chainOrig, chainDynamic);
      this.setChain();
   }

   public void setChain() {
      if (this.chainOrig != null && this.chainDynamic != null) {
         if (this.p.length != this.chainOrig.length || Double.isNaN(this.p[0].x) || this.chainDynamic[0] == null) {
            this.p = new Vec3[this.chainOrig.length];
            this.p0 = new Vec3[this.chainOrig.length];
            this.v = new Vec3[this.chainOrig.length];
            this.a = new Vec3[this.chainOrig.length];
            this.m = new float[this.chainOrig.length];
            this.d = new float[this.chainOrig.length];
            this.startingDirections = new Vec3[this.chainOrig.length];
            this.startingOrientations = new Quaternionf[this.chainOrig.length];
            this.pOrig = new Vec3[this.chainOrig.length];
            this.renderPos = new Vec3[this.chainOrig.length];
            this.prevRenderPos = new Vec3[this.chainOrig.length];

            for (int i = 0; i < this.chainOrig.length; i++) {
               Vector3d pos = this.chainOrig[i].getWorldPosition();
               this.pOrig[i] = new Vec3(pos.x, pos.y, pos.z);
               this.p[i] = new Vec3(pos.x, pos.y, pos.z);
               this.p0[i] = new Vec3(pos.x, pos.y, pos.z);
               this.renderPos[i] = new Vec3(pos.x, pos.y, pos.z);
               this.prevRenderPos[i] = new Vec3(pos.x, pos.y, pos.z);
               this.v[i] = new Vec3(0.0, 0.0, 0.0);
               this.a[i] = new Vec3(0.0, 0.0, 0.0);
               this.m[i] = 1.0F;
               if (i > 0) {
                  this.d[i] = (float)this.pOrig[i].distanceTo(this.pOrig[i - 1]);
                  Vec3 p1 = new Vec3(this.pOrig[i - 1].x, this.pOrig[i - 1].y, this.pOrig[i - 1].z);
                  Vec3 p2 = new Vec3(this.pOrig[i].x, this.pOrig[i].y, this.pOrig[i].z);
                  Vec3 startingDir = p2.subtract(p1).normalize();
                  this.startingDirections[i - 1] = startingDir;
               } else {
                  this.d[i] = 0.0F;
               }

               this.chainOrig[i].setDynamicJoint(true);
            }

            for (int i = 0; i < this.chainOrig.length; i++) {
               if (this.chainDynamic[i] == null) {
                  this.chainDynamic[i] = new MowzieGeoBone(this.chainOrig[i]);
               }
            }
         }
      }
   }

   public void updateChain(float delta, MowzieGeoEntityRenderer renderer) {
      if (this.chainOrig != null && this.chainDynamic != null && this.p.length == this.chainOrig.length && !Double.isNaN(this.p[0].x)) {
         float currentTime = this.entity.tickCount + Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false);

         for (int i = 0; i < this.chainOrig.length; i++) {
            this.prevRenderPos[i] = new Vec3(this.renderPos[i].x, this.renderPos[i].y, this.renderPos[i].z);
         }

         for (int i = 0; i < this.chainOrig.length; i++) {
            Vector3d p = this.chainOrig[i].getWorldPosition();
            this.pOrig[i] = new Vec3(p.x, p.y, p.z);
            if (i > 0) {
               this.d[i] = (float)this.pOrig[i].distanceTo(this.pOrig[i - 1]);
            } else {
               this.d[i] = 0.0F;
            }
         }

         if (!Minecraft.getInstance().isPaused() && this.prevUpdateTime != currentTime) {
            this.updateSpringConstraint(
               this.gravity, this.damping, this.attractStrength, this.doAttract, this.attractFalloff, this.numUpdates, currentTime - this.prevUpdateTime
            );
         }

         for (int i = 0; i < this.chainOrig.length; i++) {
            this.renderPos[i] = new Vec3(this.p[i].x, this.p[i].y, this.p[i].z);
         }

         this.prevUpdateTime = currentTime;
         this.setChainFromRenderPos(this.chainOrig, this.chainDynamic, this.alpha, renderer);
      }
   }

   private void setChainFromRenderPos(MowzieGeoBone[] chainOrig, MowzieGeoBone[] chainDynamic, double alpha, MowzieGeoEntityRenderer renderer) {
      for (int i = chainDynamic.length - 1; i >= 0; i--) {
         if (chainDynamic[i] == null) {
            return;
         }

         chainDynamic[i].setForceMatrixTransform(true);
         chainDynamic[i].setHidden(this.isChainHidden);
         chainOrig[i].setHidden(true);
         chainOrig[i].setDynamicJoint(true);
         Matrix4f xformOverride = new Matrix4f();
         float x = (float)Mth.lerp(alpha, this.pOrig[i].x, this.p[i].x);
         float y = (float)Mth.lerp(alpha, this.pOrig[i].y, this.p[i].y);
         float z = (float)Mth.lerp(alpha, this.pOrig[i].z, this.p[i].z);
         xformOverride = xformOverride.translate(x, y, z);
         if (i < chainOrig.length - 1) {
            new Quaterniond();
            Vector3d p1 = new Vector3d(this.pOrig[i].x, this.pOrig[i].y, this.pOrig[i].z).lerp(new Vector3d(this.p[i].x, this.p[i].y, this.p[i].z), alpha);
            Vector3d p2 = new Vector3d(this.pOrig[i + 1].x, this.pOrig[i + 1].y, this.pOrig[i + 1].z)
               .lerp(new Vector3d(this.p[i + 1].x, this.p[i + 1].y, this.p[i + 1].z), alpha);
            Vector3d desiredDir = p2.sub(p1, new Vector3d()).normalize();
            Quaternionf entityRotations = renderer.getModelRenderMatrix().getNormalizedRotation(new Quaternionf());
            Vector3d startingDir = new Vector3d();
            this.chainDirection.rotate(new Quaterniond(entityRotations), startingDir);
            double dot = desiredDir.dot(startingDir);
            Quaterniond scale;
            if (dot > 0.9999999) {
               scale = new Quaterniond();
            } else {
               Vector3d cross = startingDir.cross(desiredDir, new Vector3d());
               double w = Math.sqrt(desiredDir.lengthSquared() * startingDir.lengthSquared()) + dot;
               scale = new Quaterniond(cross.x, cross.y, cross.z, w).normalize();
            }

            xformOverride.rotate(scale.get(new Quaternionf()));
         }

         Vector3f scale = new Vector3f();
         chainOrig[i].getModelSpaceMatrix().getScale(scale);
         xformOverride.scale(scale);
         chainDynamic[i].setWorldSpaceMatrix(xformOverride);
      }
   }

   public void setSimulating(boolean simulating) {
      this.isSimulating = simulating;
   }

   private static Vec3 fromPitchYaw(float pitch, float yaw) {
      float f = Mth.cos(-yaw - (float) Math.PI);
      float f1 = Mth.sin(-yaw - (float) Math.PI);
      float f2 = -Mth.cos(-pitch);
      float f3 = Mth.sin(-pitch);
      return new Vec3(f1 * f2, f3, f * f2);
   }

   private static Vec3 angleBetween(Vec3 p1, Vec3 p2) {
      float dz = (float)(p2.z - p1.z);
      float dx = (float)(p2.x - p1.x);
      float dy = (float)(p2.y - p1.y);
      float yaw = (float)Mth.atan2(dz, dx);
      float pitch = (float)Mth.atan2(Math.sqrt(dz * dz + dx * dx), dy);
      return wrapAngles(new Vec3(yaw, pitch, 0.0));
   }

   public static Vec3 toPitchYaw(Vec3 vector) {
      double f3 = vector.y;
      double pitch = -Math.asin(f3);
      double f2 = -Math.cos(pitch);
      double f1 = vector.x / f2;
      double yaw = -Math.asin(f1) + (Math.PI / 2);
      return wrapAngles(new Vec3(yaw, pitch, 0.0));
   }

   private static Vec3 toEuler(Vec3 axis, double angle) {
      double s = Math.sin(angle);
      double c = Math.cos(angle);
      double t = 1.0 - c;
      double yaw = 0.0;
      double pitch = 0.0;
      double roll = 0.0;
      double x = axis.x;
      double y = axis.y;
      double z = axis.z;
      if (x * y * t + z * s > 0.998) {
         yaw = 2.0 * Math.atan2(x * Math.sin(angle / 2.0), Math.cos(angle / 2.0));
         pitch = Math.PI / 2;
         roll = 0.0;
      } else if (x * y * t + z * s < -0.998) {
         yaw = -2.0 * Math.atan2(x * Math.sin(angle / 2.0), Math.cos(angle / 2.0));
         pitch = -Math.PI / 2;
         roll = 0.0;
      } else {
         yaw = Math.atan2(y * s - x * z * t, 1.0 - (y * y + z * z) * t);
         pitch = Math.asin(x * y * t + z * s);
         roll = Math.atan2(x * s - y * z * t, 1.0 - (x * x + z * z) * t);
      }

      return new Vec3(yaw, pitch, roll);
   }

   private static Vec3 wrapAngles(Vec3 r) {
      double x = r.x;
      double y = r.y;
      double z = r.z;

      while (x > Math.PI) {
         x -= Math.PI * 2;
      }

      while (x < -Math.PI) {
         x += Math.PI * 2;
      }

      while (y > Math.PI) {
         y -= Math.PI * 2;
      }

      while (y < -Math.PI) {
         y += Math.PI * 2;
      }

      while (z > Math.PI) {
         z -= Math.PI * 2;
      }

      while (z < -Math.PI) {
         z += Math.PI * 2;
      }

      return new Vec3(x, y, z);
   }

   private static Vec3 multiply(Vec3 u, Vec3 v, boolean preserveDir) {
      return preserveDir ? new Vec3(u.x * Math.abs(v.x), u.y * Math.abs(v.y), u.z * Math.abs(v.z)) : new Vec3(u.x * v.x, u.y * v.y, u.z * v.z);
   }
}
