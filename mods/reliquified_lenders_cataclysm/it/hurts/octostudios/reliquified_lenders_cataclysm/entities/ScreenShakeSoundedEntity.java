package it.hurts.octostudios.reliquified_lenders_cataclysm.entities;

import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.init.ModSounds;
import it.hurts.octostudios.reliquified_lenders_cataclysm.utils.relics.VoidCloakUtils;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ScreenShakeSoundedEntity extends ScreenShake_Entity {
   private int layersSpawned = 0;

   public ScreenShakeSoundedEntity(EntityType<?> type, Level level) {
      super(type, level);
   }

   public ScreenShakeSoundedEntity(Level level, Vec3 pos, int radius, int layersSpawned, int wavesNum, int fadeDuration) {
      super(level, pos, radius, 1.0F / wavesNum, wavesNum * VoidCloakUtils.getWaveTicks(), fadeDuration);
      this.layersSpawned = layersSpawned;
   }

   public void tick() {
      super.tick();
      if (this.level().isClientSide || this.tickCount > this.getDuration()) {
         this.discard();
      }

      for (int i = 0; i < this.getLayersSpawned(); i++) {
         int fangLifespan = 30;
         int tickCountDecreased = this.tickCount - fangLifespan * i;
         if (this.tickCount % 3 == 0 && tickCountDecreased >= 1 && tickCountDecreased <= (this.getRadius() - 1.0F) * 3.0F || this.tickCount == 1) {
            this.level().playSound(null, this.blockPosition(), (SoundEvent)ModSounds.VOID_RUNE_RISING.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
            break;
         }
      }
   }

   public int getLayersSpawned() {
      return this.layersSpawned;
   }
}
