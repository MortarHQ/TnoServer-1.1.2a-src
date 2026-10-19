package bigchadguys.sellingbin.mixin;

import bigchadguys.sellingbin.block.entity.BaseBlockEntity;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.RegistryAccess.Frozen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public abstract class MixinClientPlayNetworkHandler {
   @Shadow
   private ClientLevel level;

   @Shadow
   public abstract Frozen registryAccess();

   @Inject(
      method = "handleBlockEntityData(Lnet/minecraft/network/protocol/game/ClientboundBlockEntityDataPacket;)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/network/protocol/PacketUtils;ensureRunningOnSameThread(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;Lnet/minecraft/util/thread/BlockableEventLoop;)V",
         shift = Shift.AFTER
      ),
      cancellable = true
   )
   private void onBlockEntityUpdate(ClientboundBlockEntityDataPacket packet, CallbackInfo ci) {
      ClientLevel world = this.level;
      if (world != null) {
         BlockEntity entity = (BlockEntity)world.getBlockEntity(packet.getPos(), packet.getType()).orElse(null);
         if (entity instanceof BaseBlockEntity baseEntity) {
            CompoundTag nbt = packet.getTag() != null ? packet.getTag() : new CompoundTag();
            baseEntity.readNbt(nbt, this.registryAccess(), BaseBlockEntity.UpdateType.UPDATE_PACKET);
            ci.cancel();
         }
      }
   }
}
