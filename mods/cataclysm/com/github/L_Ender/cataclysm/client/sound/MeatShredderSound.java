package com.github.L_Ender.cataclysm.client.sound;

import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class MeatShredderSound extends ItemTickableSound {
   public MeatShredderSound(LivingEntity user) {
      super(user, (SoundEvent)ModSounds.SHREDDER_LOOP.get());
   }

   @Override
   public void tickVolume(ItemStack itemStack) {
      this.volume = 0.4F;
      this.pitch = 1.0F;
   }

   @Override
   public boolean isValidItem(ItemStack itemStack) {
      return itemStack.is((Item)ModItems.MEAT_SHREDDER.get());
   }
}
