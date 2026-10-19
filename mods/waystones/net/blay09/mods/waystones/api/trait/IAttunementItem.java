package net.blay09.mods.waystones.api.trait;

import java.util.Optional;
import net.blay09.mods.waystones.api.Waystone;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface IAttunementItem {
   Optional<Waystone> getWaystoneAttunedTo(@Nullable MinecraftServer var1, @Nullable Player var2, ItemStack var3);

   void setWaystoneAttunedTo(ItemStack var1, @Nullable Waystone var2);
}
