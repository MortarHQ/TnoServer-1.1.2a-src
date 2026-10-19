package cn.leolezury.eternalstarlight.common.entity.attack;

import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class Coldsnap extends Whip {
   public final ConcurrentLinkedQueue<Vec3> tipPositions = new ConcurrentLinkedQueue<>();
   private Vec3 lastParticlePos;
   public float lastParticleTick = 0.0F;
   private boolean cloudSpawned = false;

   public Coldsnap(EntityType<? extends Coldsnap> entityType, Level level) {
      super(entityType, level);
   }

   public Coldsnap(Level level, Player player, @Nullable ItemStack weapon, float damageScale) {
      super(ESEntities.COLDSNAP.get(), level, player, weapon, damageScale);
   }

   public boolean isCloudSpawned() {
      return this.cloudSpawned;
   }

   public void setCloudSpawned() {
      this.cloudSpawned = true;
   }

   @Override
   public void tick() {
      super.tick();
      Vec3 pos;
      if (this.level().isClientSide) {
         while ((pos = this.tipPositions.poll()) != null) {
            if (this.distanceToSqr(pos) < 400.0) {
               if (this.getSpawnedTicks() > 1) {
                  this.level()
                     .addParticle(
                        ParticleTypes.SNOWFLAKE,
                        pos.x,
                        pos.y,
                        pos.z,
                        (this.getRandom().nextDouble() - 0.5) * 0.2,
                        0.0,
                        (this.getRandom().nextDouble() - 0.5) * 0.2
                     );
                  if (this.lastParticlePos != null) {
                     int count = (int)(this.lastParticlePos.distanceTo(pos) / 0.4);
                     if (count > 0 && count < 20) {
                        for (int i = 0; i < count; i++) {
                           Vec3 extraPos = ESMathUtil.lerpVec((float)(i + 1) / (count + 1), this.lastParticlePos, pos);
                           this.level()
                              .addParticle(
                                 ParticleTypes.SNOWFLAKE,
                                 extraPos.x,
                                 extraPos.y,
                                 extraPos.z,
                                 (this.getRandom().nextDouble() - 0.5) * 0.2,
                                 0.0,
                                 (this.getRandom().nextDouble() - 0.5) * 0.2
                              );
                        }
                     }
                  }
               }

               this.lastParticlePos = pos;
            }
         }
      }
   }

   @Override
   public int getLifespan() {
      return 10;
   }

   @Override
   public float getWhipRange(float interactionRange) {
      return interactionRange + 2.5F;
   }
}
