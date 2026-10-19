package fuzs.puzzleslib.neoforge.impl.attachment.builder;

import fuzs.puzzleslib.api.attachment.v4.DataAttachmentType;
import fuzs.puzzleslib.impl.attachment.AttachmentTypeAdapter;
import fuzs.puzzleslib.impl.attachment.DataAttachmentTypeImpl;
import fuzs.puzzleslib.impl.attachment.builder.DataAttachmentBuilder;
import fuzs.puzzleslib.neoforge.api.core.v1.NeoForgeModContainerHelper;
import fuzs.puzzleslib.neoforge.impl.attachment.NeoForgeAttachmentTypeAdapter;
import java.util.function.BiConsumer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.AttachmentType.Builder;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;
import org.jetbrains.annotations.MustBeInvokedByOverriders;

public class NeoForgeDataAttachmentBuilder<T extends IAttachmentHolder, V> extends DataAttachmentBuilder<T, V> {
   @Override
   public DataAttachmentType<T, V> build(ResourceLocation resourceLocation) {
      DeferredRegister<AttachmentType<?>> registrar = DeferredRegister.create(Keys.ATTACHMENT_TYPES, resourceLocation.getNamespace());
      NeoForgeModContainerHelper.getOptionalModEventBus(resourceLocation.getNamespace()).ifPresent(registrar::register);
      DeferredHolder<AttachmentType<?>, AttachmentType<V>> attachmentType = registrar.register(resourceLocation.getPath(), () -> {
         Builder<V> builder = AttachmentType.builder(() -> {
            throw new UnsupportedOperationException("Attachment type " + resourceLocation + " does not support a default value!");
         });
         this.configureBuilder(builder);
         return builder.build();
      });
      AttachmentTypeAdapter<T, V> adapter = new NeoForgeAttachmentTypeAdapter<>(attachmentType);
      BiConsumer<T, V> synchronizer = this.getSynchronizer(resourceLocation, adapter);
      return new DataAttachmentTypeImpl<>(adapter, this.defaultValues, synchronizer);
   }

   @MustBeInvokedByOverriders
   void configureBuilder(Builder<V> builder) {
      if (this.codec != null) {
         builder.serialize(this.codec).copyHandler((value, holder, registries) -> value);
      }
   }
}
