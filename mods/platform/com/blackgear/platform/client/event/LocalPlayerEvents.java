package com.blackgear.platform.client.event;

import com.blackgear.platform.core.util.event.Event;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public interface LocalPlayerEvents {
   Event<LocalPlayerEvents.PlayerJoin> ON_LOGIN = Event.create(LocalPlayerEvents.PlayerJoin.class);
   Event<LocalPlayerEvents.PlayerLeave> ON_LOGOUT = Event.create(LocalPlayerEvents.PlayerLeave.class);
   Event<LocalPlayerEvents.PlayerRespawn> ON_RESPAWN = Event.create(LocalPlayerEvents.PlayerRespawn.class);

   interface PlayerJoin {
      void onLogin(LocalPlayer var1);
   }

   interface PlayerLeave {
      void onLogout(@Nullable LocalPlayer var1);
   }

   interface PlayerRespawn {
      void onRespawn(LocalPlayer var1, LocalPlayer var2);
   }
}
