package noobanidus.mods.lootr.common.api.advancement;

import java.util.UUID;
import net.minecraft.server.level.ServerPlayer;

public interface IContainerTrigger extends ITrigger {
   void trigger(ServerPlayer var1, UUID var2);
}
