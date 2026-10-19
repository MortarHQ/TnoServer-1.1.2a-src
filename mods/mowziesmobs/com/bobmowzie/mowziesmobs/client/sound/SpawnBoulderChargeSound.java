package com.bobmowzie.mowziesmobs.client.sound;

import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.abilities.player.geomancy.SpawnBoulderAbility;
import com.bobmowzie.mowziesmobs.server.capability.AbilityData;
import com.bobmowzie.mowziesmobs.server.capability.DataHandler;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;

public class SpawnBoulderChargeSound extends AbstractTickableSoundInstance {
   private final LivingEntity user;
   private final SpawnBoulderAbility ability;

   public SpawnBoulderChargeSound(LivingEntity user) {
      super((SoundEvent)MMSounds.EFFECT_GEOMANCY_BOULDER_CHARGE.get(), SoundSource.PLAYERS, SoundInstance.createUnseededRandom());
      this.user = user;
      this.volume = 1.0F;
      this.pitch = 1.0F;
      this.x = (float)user.getX();
      this.y = (float)user.getY();
      this.z = (float)user.getZ();
      AbilityData data = DataHandler.getData(user, DataHandler.ABILITY_DATA);
      this.ability = (SpawnBoulderAbility)data.getAbilityMap().get(AbilityHandler.SPAWN_BOULDER_ABILITY);
   }

   public void tick() {
      if (this.ability == null) {
         this.stop();
      } else {
         if (!this.ability.isUsing() || this.ability.getCurrentSection().sectionType != AbilitySection.AbilitySectionType.STARTUP) {
            this.stop();
         }
      }
   }
}
