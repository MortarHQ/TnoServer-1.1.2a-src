package team.lodestar.lodestone.systems.sound;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;

public class CachedBlockEntitySoundInstance<T extends LodestoneBlockEntity> extends LodestoneBlockEntitySoundInstance<T> {
   private static final Map<BlockPos, CachedBlockEntitySoundInstance<?>> ACTIVE_SOUNDS = new WeakHashMap<>();

   public CachedBlockEntitySoundInstance(T blockEntity, Supplier<SoundEvent> soundEvent, float volume, float pitch) {
      super(blockEntity, soundEvent.get(), volume, pitch);
      BlockPos pos = blockEntity.getBlockPos();
      this.x = pos.getX() + 0.5F;
      this.y = pos.getY() + 0.5F;
      this.z = pos.getZ() + 0.5F;
   }

   @Override
   public void tick() {
      super.tick();
      if (this.isStopped()) {
         ACTIVE_SOUNDS.remove(this.blockEntity.getBlockPos());
      }
   }

   public static void playSound(LodestoneBlockEntity blockEntity, CachedBlockEntitySoundInstance<?> sound) {
      BlockPos blockPos = blockEntity.getBlockPos();
      boolean success = false;
      if (ACTIVE_SOUNDS.containsKey(blockPos)) {
         CachedBlockEntitySoundInstance<?> existingSound = ACTIVE_SOUNDS.get(blockPos);
         if (!existingSound.location.equals(sound.location)) {
            existingSound.stop();
            ACTIVE_SOUNDS.put(blockPos, sound);
            success = true;
         }
      } else {
         ACTIVE_SOUNDS.put(blockPos, sound);
         success = true;
      }

      if (success) {
         Minecraft.getInstance().getSoundManager().queueTickingSound(sound);
      }
   }
}
