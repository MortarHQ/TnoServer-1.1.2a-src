package noobanidus.mods.lootr.common.api.adapter;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

public non-sealed interface ILootrItemFrameAdapter<T> extends ILootrAdapter<T> {
   Direction getDirection(T var1);

   ItemStack getItem(T var1);

   int getRotation(T var1);

   BlockPos getPos(T var1);

   boolean isFixed(T var1);

   boolean isInvisible(T var1);
}
