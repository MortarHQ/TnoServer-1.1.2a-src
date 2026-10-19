package team.creative.creativecore.client.sound;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@Environment(EnvType.CLIENT)
public class EntitySound extends AbstractTickableSoundInstance {
   private final Entity entity;

   public EntitySound(SoundEvent event, Entity entity, float volume, float pitch, SoundSource category) {
      super(event, category, SoundInstance.createUnseededRandom());
      this.entity = entity;
      this.volume = volume;
      this.pitch = pitch;
   }

   public void tick() {
      Minecraft mc = Minecraft.getInstance();
      Entity view = mc.cameraEntity;
      float partialTicks = mc.getTimer().getGameTimeDeltaPartialTick(false);
      if (view == null) {
         this.stop();
      } else {
         Vec3 viewVec = view.getEyePosition(partialTicks);
         AABB bb = this.entity.getBoundingBox();
         if (bb.contains(viewVec)) {
            this.x = (float)viewVec.x;
            this.y = (float)viewVec.y;
            this.z = (float)viewVec.z;
         } else {
            if (viewVec.x > bb.minX) {
               if (viewVec.x > bb.maxX) {
                  this.x = (float)bb.maxX;
               } else {
                  this.x = (float)viewVec.x;
               }
            } else {
               this.x = (float)bb.minX;
            }

            if (viewVec.y > bb.minY) {
               if (viewVec.y > bb.maxY) {
                  this.y = (float)bb.maxY;
               } else {
                  this.y = (float)viewVec.y;
               }
            } else {
               this.y = (float)bb.minY;
            }

            if (viewVec.z > bb.minZ) {
               if (viewVec.z > bb.maxZ) {
                  this.z = (float)bb.maxZ;
               } else {
                  this.z = (float)viewVec.z;
               }
            } else {
               this.z = (float)bb.minZ;
            }
         }
      }
   }
}
