package com.github.L_Ender.cataclysm;

import com.github.L_Ender.cataclysm.client.sound.MeatShredderSound;
import com.github.L_Ender.cataclysm.client.sound.SandstormSound;
import com.github.L_Ender.cataclysm.entity.effect.Sandstorm_Entity;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ClientProxy extends ServerProxy {
   public static final Int2ObjectMap<AbstractTickableSoundInstance> ENTITY_SOUND_INSTANCE_MAP = new Int2ObjectOpenHashMap();
   public static final Map<BlockEntity, AbstractTickableSoundInstance> BLOCK_ENTITY_SOUND_INSTANCE_MAP = new HashMap<>();
   public static List<UUID> blockedEntityRenders = new ArrayList<>();
   public static Map<UUID, ClientProxy.BossBarData> bossBarRenderTypes = new HashMap<>();

   @Override
   public boolean isFirstPersonPlayer(Entity entity) {
      return entity.equals(Minecraft.getInstance().cameraEntity) && Minecraft.getInstance().options.getCameraType().isFirstPerson();
   }

   @Override
   public void blockRenderingEntity(UUID id) {
      blockedEntityRenders.add(id);
   }

   @Override
   public void releaseRenderingEntity(UUID id) {
      blockedEntityRenders.remove(id);
   }

   @Override
   public Player getClientSidePlayer() {
      return Minecraft.getInstance().player;
   }

   @Override
   public float getPartialTicks() {
      return Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false);
   }

   @Override
   public void playWorldSound(@Nullable Object soundEmitter, byte type) {
      if (!(soundEmitter instanceof Entity entity && !entity.level().isClientSide)) {
         switch (type) {
            case 1:
               if (soundEmitter instanceof LivingEntity livingEntity) {
                  AbstractTickableSoundInstance old = (AbstractTickableSoundInstance)ENTITY_SOUND_INSTANCE_MAP.get(livingEntity.getId());
                  MeatShredderSound sound;
                  if (old != null && old instanceof MeatShredderSound shredderSound && shredderSound.isSameEntity(livingEntity)) {
                     sound = (MeatShredderSound)old;
                  } else {
                     sound = new MeatShredderSound(livingEntity);
                     ENTITY_SOUND_INSTANCE_MAP.put(livingEntity.getId(), sound);
                  }

                  if (!Minecraft.getInstance().getSoundManager().isActive(sound) && sound.canPlaySound()) {
                     Minecraft.getInstance().getSoundManager().queueTickingSound(sound);
                  }
               }
               break;
            case 2:
               if (soundEmitter instanceof Sandstorm_Entity sandstom) {
                  AbstractTickableSoundInstance old = (AbstractTickableSoundInstance)ENTITY_SOUND_INSTANCE_MAP.get(sandstom.getId());
                  SandstormSound sound;
                  if (old != null && old instanceof SandstormSound sandstomSound && sandstomSound.isSameEntity(sandstom)) {
                     sound = (SandstormSound)old;
                  } else {
                     sound = new SandstormSound(sandstom);
                     ENTITY_SOUND_INSTANCE_MAP.put(sandstom.getId(), sound);
                  }

                  if (!Minecraft.getInstance().getSoundManager().isActive(sound) && sound.canPlaySound()) {
                     Minecraft.getInstance().getSoundManager().queueTickingSound(sound);
                  }
               }
         }
      }
   }

   @Override
   public void clearSoundCacheFor(Entity entity) {
      ENTITY_SOUND_INSTANCE_MAP.remove(entity.getId());
   }

   @Override
   public void clearSoundCacheFor(BlockEntity entity) {
      BLOCK_ENTITY_SOUND_INSTANCE_MAP.remove(entity);
   }

   public record BossBarData(int renderType, int remainLife) {
   }
}
