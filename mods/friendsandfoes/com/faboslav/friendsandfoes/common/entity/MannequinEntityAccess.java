package com.faboslav.friendsandfoes.common.entity;

import java.util.UUID;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public interface MannequinEntityAccess {
   void friendsandfoes$setPlayerUuid(@Nullable UUID var1);

   void friendsandfoes$setPlayer(Player var1);

   void friendsandfoes$setIsIllusion(boolean var1);

   void friendsandfoes$setTicksUntilDespawn(int var1);
}
