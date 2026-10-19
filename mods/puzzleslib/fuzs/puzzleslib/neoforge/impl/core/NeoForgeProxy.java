package fuzs.puzzleslib.neoforge.impl.core;

import fuzs.puzzleslib.api.network.v3.ClientboundMessage;
import fuzs.puzzleslib.api.network.v3.ServerboundMessage;
import fuzs.puzzleslib.impl.core.ProxyImpl;
import fuzs.puzzleslib.impl.network.codec.CustomPacketPayloadAdapter;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public interface NeoForgeProxy extends ProxyImpl {
   <M1, M2> CompletableFuture<Void> registerClientReceiver(CustomPacketPayloadAdapter<M1> var1, IPayloadContext var2, Function<M1, ClientboundMessage<M2>> var3);

   <M1, M2> CompletableFuture<Void> registerServerReceiver(CustomPacketPayloadAdapter<M1> var1, IPayloadContext var2, Function<M1, ServerboundMessage<M2>> var3);
}
