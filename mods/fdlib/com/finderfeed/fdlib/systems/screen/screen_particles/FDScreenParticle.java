package com.finderfeed.fdlib.systems.screen.screen_particles;

import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.util.Mth;
import org.joml.Vector2f;

public abstract class FDScreenParticle<T extends FDScreenParticle<T>> {
   private double x;
   private double y;
   private double xo;
   private double yo;
   private double xd;
   private double yd;
   private double xa;
   private double ya;
   private float r;
   private float g;
   private float b;
   private float a;
   private float roll;
   private float oRoll;
   private float rollSpeed;
   private double friction = 1.0;
   private boolean removed = false;
   private int lifetime = 60;
   private int age;

   public void tick() {
      if (this.age++ > this.lifetime) {
         this.setRemoved(true);
      } else {
         this.xo = this.x;
         this.yo = this.y;
         this.xd = this.xd + this.xa;
         this.yd = this.yd + this.ya;
         this.xd = this.xd * this.friction;
         this.yd = this.yd * this.friction;
         this.x = this.x + this.xd;
         this.y = this.y + this.yd;
         this.oRoll = this.roll;
         this.roll = this.roll + this.rollSpeed;
      }
   }

   public abstract void render(GuiGraphics var1, BufferBuilder var2, float var3);

   public abstract ParticleRenderType getParticleRenderType();

   public T setRoll(float roll, boolean updateOlds) {
      this.roll = roll;
      if (updateOlds) {
         this.oRoll = roll;
      }

      return (T)this;
   }

   public T setRollSpeed(float rollSpeed) {
      this.rollSpeed = rollSpeed;
      return (T)this;
   }

   public T setPos(double x, double y, boolean updateOlds) {
      this.x = x;
      this.y = y;
      if (updateOlds) {
         this.xo = x;
         this.yo = y;
      }

      return (T)this;
   }

   public T setPos(Vector2f v, boolean updateOlds) {
      return this.setPos(v.x, v.y, updateOlds);
   }

   public T setLifetime(int lifetime) {
      this.lifetime = lifetime;
      return (T)this;
   }

   public T setAcceleration(double xa, double ya) {
      this.xa = xa;
      this.ya = ya;
      return (T)this;
   }

   public T setSpeed(double xd, double yd) {
      this.xd = xd;
      this.yd = yd;
      return (T)this;
   }

   public T setAcceleration(Vector2f v) {
      return this.setAcceleration(v.x, v.y);
   }

   public T setSpeed(Vector2f v) {
      return this.setSpeed(v.x, v.y);
   }

   public T setFriction(double friction) {
      this.friction = friction;
      return (T)this;
   }

   public T setColor(float r, float g, float b, float a) {
      this.r = r;
      this.g = g;
      this.b = b;
      this.a = a;
      return (T)this;
   }

   public T sendToScreen() {
      FDScreenParticleEngine.addScreenParticle(this);
      return (T)this;
   }

   public T sendToOverlay() {
      FDScreenParticleEngine.addOverlayParticle(this);
      return (T)this;
   }

   public float getA() {
      return this.a;
   }

   public float getB() {
      return this.b;
   }

   public float getG() {
      return this.g;
   }

   public float getR() {
      return this.r;
   }

   public double getX(float partialTicks) {
      return Mth.lerp(partialTicks, this.getXo(), this.getX());
   }

   public double getY(float partialTicks) {
      return Mth.lerp(partialTicks, this.getYo(), this.getY());
   }

   public float getRoll(float partialTicks) {
      return Mth.lerp(partialTicks, this.getoRoll(), this.getRoll());
   }

   public float getRoll() {
      return this.roll;
   }

   public float getoRoll() {
      return this.oRoll;
   }

   public float getRollSpeed() {
      return this.rollSpeed;
   }

   public double getX() {
      return this.x;
   }

   public double getXo() {
      return this.xo;
   }

   public double getXa() {
      return this.xa;
   }

   public double getXd() {
      return this.xd;
   }

   public double getY() {
      return this.y;
   }

   public double getYa() {
      return this.ya;
   }

   public double getYd() {
      return this.yd;
   }

   public double getYo() {
      return this.yo;
   }

   public double getFriction() {
      return this.friction;
   }

   public int getAge() {
      return this.age;
   }

   public int getLifetime() {
      return this.lifetime;
   }

   public boolean isRemoved() {
      return this.removed;
   }

   public void setRemoved(boolean removed) {
      this.removed = removed;
   }

   public void onAddedToEngine() {
   }
}
