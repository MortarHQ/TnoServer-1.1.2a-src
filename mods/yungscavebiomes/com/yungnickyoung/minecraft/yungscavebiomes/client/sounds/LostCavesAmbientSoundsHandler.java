package com.yungnickyoung.minecraft.yungscavebiomes.client.sounds;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.client.render.sandstorm.ISandstormClientDataProvider;
import com.yungnickyoung.minecraft.yungscavebiomes.client.render.sandstorm.SandstormClientData;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BiomeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.SoundModule;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AmbientSoundHandler;
import net.minecraft.client.resources.sounds.BiomeAmbientSoundsHandler.LoopSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;

public class LostCavesAmbientSoundsHandler implements AmbientSoundHandler {
   private static final ResourceLocation regularAmbientSound = YungsCaveBiomesCommon.id("ambient.lost_caves.loop");
   private static final ResourceLocation sandstormAmbientSound = YungsCaveBiomesCommon.id("ambient.lost_caves.sandstorm_loop");
   private final LocalPlayer player;
   private final SoundManager soundManager;
   private final BiomeManager biomeManager;
   private LoopSoundInstance lostCavesSound = new LoopSoundInstance((SoundEvent)SoundModule.AMBIENT_LOST_CAVES.get());
   private boolean inLostCaves = false;

   public LostCavesAmbientSoundsHandler(LocalPlayer localPlayer, SoundManager soundManager, BiomeManager biomeManager) {
      this.player = localPlayer;
      this.soundManager = soundManager;
      this.biomeManager = biomeManager;
   }

   public void tick() {
      if (!this.player.isAlive() && this.lostCavesSound != null) {
         this.lostCavesSound.fadeOut();
      }

      Holder<Biome> currBiome = this.biomeManager.getNoiseBiomeAtPosition(this.player.getX(), this.player.getY(), this.player.getZ());
      if (currBiome.is(BiomeModule.LOST_CAVES) && !this.inLostCaves) {
         this.inLostCaves = true;
         this.changeAmbientsound((SoundEvent)SoundModule.AMBIENT_LOST_CAVES.get());
      }

      if (!currBiome.is(BiomeModule.LOST_CAVES)) {
         this.inLostCaves = false;
         this.lostCavesSound.fadeOut();
      }

      SandstormClientData sandstormClientData = ((ISandstormClientDataProvider)this.player.level()).getSandstormClientData();
      if (this.inLostCaves) {
         if (sandstormClientData.isSandstormActive() && !this.lostCavesSound.getLocation().equals(sandstormAmbientSound)) {
            this.changeAmbientsound((SoundEvent)SoundModule.SANDSTORM_AMBIENT_LOST_CAVES.get());
         }

         if (!sandstormClientData.isSandstormActive() && !this.lostCavesSound.getLocation().equals(regularAmbientSound)) {
            this.changeAmbientsound((SoundEvent)SoundModule.AMBIENT_LOST_CAVES.get());
         }
      }
   }

   private void changeAmbientsound(SoundEvent newAmbientSoundEvent) {
      if (this.lostCavesSound != null) {
         this.lostCavesSound.fadeOut();
      }

      this.lostCavesSound = new LoopSoundInstance(newAmbientSoundEvent);
      this.soundManager.play(this.lostCavesSound);
      this.lostCavesSound.fadeIn();
   }
}
