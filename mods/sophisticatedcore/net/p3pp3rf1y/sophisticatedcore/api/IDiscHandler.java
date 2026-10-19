package net.p3pp3rf1y.sophisticatedcore.api;

import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public interface IDiscHandler<I> {
   Optional<I> getSongInfo(ItemStack var1, Level var2);

   void playDisc(ServerLevel var1, BlockPos var2, UUID var3, ItemStack var4, Runnable var5);

   void playDisc(ServerLevel var1, Vec3 var2, UUID var3, ItemStack var4, int var5, Runnable var6);

   Optional<Integer> getMusicLengthInTicks(ItemStack var1, Level var2);

   boolean supports(ItemStack var1);

   Optional<ItemStack> getRandomDisc(RandomSource var1);

   int getMusicDiscSize();
}
