package cn.leolezury.eternalstarlight.common.client.particle.advanced;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteSet;

public interface ParticleSpawner {
   Particle spawn(ClientLevel var1, double var2, double var4, double var6, SpriteSet var8, AdvancedParticleOptions var9);
}
