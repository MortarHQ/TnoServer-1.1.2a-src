package cn.leolezury.eternalstarlight.common.client.particle.advanced;

import net.minecraft.world.phys.Vec3;
import org.joml.Vector4f;

public interface ParticleOperator {
   Vec3 getOldPos();

   void setOldPos(Vec3 var1);

   Vec3 getPos();

   void setPos(Vec3 var1);

   Vec3 getSpeed();

   void setSpeed(Vec3 var1);

   float getOldRoll();

   void setOldRoll(float var1);

   float getRoll();

   void setRoll(float var1);

   float getQuadSize();

   void setQuadSize(float var1);

   float getAge();

   void setAge(int var1);

   int getLifetime();

   void setLifetime(int var1);

   Vector4f getColor();

   void setColor(Vector4f var1);

   void remove();
}
