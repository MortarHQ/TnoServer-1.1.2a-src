package mcjty.incontrol.tools.rules;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;

public interface IEventQuery<T> {
   LevelAccessor getWorld(T var1);

   BlockPos getPos(T var1);

   BlockPos getValidBlockPos(T var1);

   int getY(T var1);

   Entity getEntity(T var1);

   DamageSource getSource(T var1);

   Entity getAttacker(T var1);

   Player getPlayer(T var1);

   ItemStack getItem(T var1);
}
