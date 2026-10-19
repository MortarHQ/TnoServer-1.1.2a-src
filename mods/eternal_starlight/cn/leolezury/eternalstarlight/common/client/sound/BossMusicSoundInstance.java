package cn.leolezury.eternalstarlight.common.client.sound;

import cn.leolezury.eternalstarlight.common.client.handler.ESClientHandler;
import cn.leolezury.eternalstarlight.common.entity.living.boss.ESBoss;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance.Attenuation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class BossMusicSoundInstance extends AbstractTickableSoundInstance {
   protected final ESBoss boss;

   public BossMusicSoundInstance(SoundEvent soundEvent, ESBoss boss) {
      super(soundEvent, SoundSource.MUSIC, SoundInstance.createUnseededRandom());
      this.boss = boss;
      this.attenuation = Attenuation.NONE;
      this.looping = true;
      this.delay = 0;
      this.volume = 1.0F;
   }

   public ESBoss getBoss() {
      return this.boss;
   }

   public void tick() {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null) {
         this.x = player.getX();
         this.y = player.getY();
         this.z = player.getZ();
      }
   }

   public void stopMusic() {
      this.stop();
   }

   public boolean shouldStopMusic(LocalPlayer player) {
      return !this.boss.isAlive()
         || !player.isAlive()
         || this.boss.distanceTo(player) > 160.0F
         || this.boss.level().dimension() != player.level().dimension()
         || !this.boss.shouldPlayBossMusic();
   }

   public boolean canPlaySound() {
      return ESClientHandler.bossMusicInstance == this;
   }
}
