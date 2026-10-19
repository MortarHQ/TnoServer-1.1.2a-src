package de.keksuccino.konkrete.mixin.mixins.client;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.commands.SharedSuggestionProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientPacketListener.class)
public interface IMixinClientPacketListener {
   @Accessor("commands")
   CommandDispatcher<SharedSuggestionProvider> getCommandsKonkrete();

   @Accessor("commands")
   void setCommandsKonkrete(CommandDispatcher<SharedSuggestionProvider> var1);
}
