package mezz.jei.common.transfer;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;

public record TransferOperation(int inventorySlotId, int craftingSlotId) {
   public static final StreamCodec<ByteBuf, TransferOperation> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.VAR_INT, p -> p.inventorySlotId, ByteBufCodecs.VAR_INT, p -> p.craftingSlotId, TransferOperation::new
   );

   public Slot inventorySlot(AbstractContainerMenu container) {
      return container.getSlot(this.inventorySlotId);
   }

   public Slot craftingSlot(AbstractContainerMenu container) {
      return container.getSlot(this.craftingSlotId);
   }
}
