package fuzs.puzzleslib.impl.attachment;

import net.minecraft.resources.ResourceLocation;

public interface AttachmentTypeAdapter<T, A> {
   ResourceLocation resourceLocation();

   boolean hasData(T var1);

   A getData(T var1);

   A setData(T var1, A var2);

   A removeData(T var1);
}
