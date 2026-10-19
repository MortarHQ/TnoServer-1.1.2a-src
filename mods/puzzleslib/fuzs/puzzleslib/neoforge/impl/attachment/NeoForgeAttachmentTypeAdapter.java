package fuzs.puzzleslib.neoforge.impl.attachment;

import fuzs.puzzleslib.impl.attachment.AttachmentTypeAdapter;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.registries.DeferredHolder;

public record NeoForgeAttachmentTypeAdapter<T extends IAttachmentHolder, A>(DeferredHolder<AttachmentType<?>, AttachmentType<A>> attachmentType)
   implements AttachmentTypeAdapter<T, A> {
   @Override
   public ResourceLocation resourceLocation() {
      return this.attachmentType.getKey().location();
   }

   public boolean hasData(T holder) {
      return holder.hasData(this.attachmentType);
   }

   public A getData(T holder) {
      return (A)holder.getData(this.attachmentType);
   }

   public A setData(T holder, A value) {
      return (A)holder.setData(this.attachmentType, value);
   }

   public A removeData(T holder) {
      return (A)holder.removeData(this.attachmentType);
   }
}
