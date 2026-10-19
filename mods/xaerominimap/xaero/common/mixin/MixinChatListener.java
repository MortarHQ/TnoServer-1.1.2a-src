package xaero.common.mixin;

import net.minecraft.client.multiplayer.chat.ChatListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ChatType.Bound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xaero.common.core.XaeroMinimapCore;

@Mixin(ChatListener.class)
public class MixinChatListener {
   @Inject(method = "handleDisguisedChatMessage", cancellable = true, at = @At("HEAD"))
   public void onHandleDisguisedChatMessag(Component component, Bound bound, CallbackInfo info) {
      if (!XaeroMinimapCore.onHandleDisguisedChatMessage(bound, component)) {
         info.cancel();
      }
   }

   @Inject(
      method = "handleSystemMessage",
      cancellable = true,
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ChatComponent;addMessage(Lnet/minecraft/network/chat/Component;)V")
   )
   public void onHandleSystemChat(Component component, boolean bl, CallbackInfo info) {
      if (XaeroMinimapCore.onSystemChat(component)) {
         info.cancel();
      }
   }
}
