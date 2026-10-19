package cn.leolezury.eternalstarlight.common.item.interfaces;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface TickableArmor {
   void tick(Level var1, LivingEntity var2, ItemStack var3);
}
