package net.p3pp3rf1y.sophisticatedcore.client.render;

import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public interface IItemActionPayloadBuilder<T> {
   ResourceLocation getPayloadHandlerId();

   Optional<T> buildClientRequestData(Player var1);
}
