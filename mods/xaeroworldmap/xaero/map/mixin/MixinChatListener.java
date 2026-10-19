package xaero.map.mixin;

import net.minecraft.client.multiplayer.chat.ChatListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ChatType.Bound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xaero.map.core.XaeroWorldMapCore;

@Mixin(ChatListener.class)
public class MixinChatListener {
   @Inject(method = "handleDisguisedChatMessage", cancellable = true, at = @At("HEAD"))
   public void onHandleDisguisedChatMessag(Component component, Bound bound, CallbackInfo info) {
      if (!XaeroWorldMapCore.onHandleDisguisedChatMessage(bound, component)) {
         info.cancel();
      }
   }

   @Inject(
      method = "handleSystemMessage",
      cancellable = true,
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ChatComponent;addMessage(Lnet/minecraft/network/chat/Component;)V")
   )
   public void onHandleSystemChat(Component component, boolean bl, CallbackInfo info) {
      if (XaeroWorldMapCore.onSystemChat(component)) {
         info.cancel();
      }
   }
}
