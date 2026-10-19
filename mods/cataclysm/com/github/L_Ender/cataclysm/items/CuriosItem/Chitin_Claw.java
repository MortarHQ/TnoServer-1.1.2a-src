package com.github.L_Ender.cataclysm.items.CuriosItem;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio.SoundInfo;

public class Chitin_Claw extends CuriosItem {
   public Chitin_Claw(Properties group) {
      super(group);
   }

   public SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
      return new SoundInfo((SoundEvent)SoundEvents.ARMOR_EQUIP_NETHERITE.value(), 1.0F, 1.0F);
   }
}
