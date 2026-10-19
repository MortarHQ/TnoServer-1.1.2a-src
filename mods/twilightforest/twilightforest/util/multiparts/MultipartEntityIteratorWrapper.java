package twilightforest.util.multiparts;

import java.util.Iterator;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.entity.PartEntity;
import org.jetbrains.annotations.Nullable;
import twilightforest.entity.TFPart;

public class MultipartEntityIteratorWrapper implements Iterator<Entity> {
   private final Iterator<Entity> delegate;
   private TFPart<?> @Nullable [] parts;
   private int partIndex;

   MultipartEntityIteratorWrapper(Iterator<Entity> iter) {
      this.delegate = iter;
   }

   @Override
   public boolean hasNext() {
      return this.parts != null || this.delegate.hasNext();
   }

   public Entity next() {
      if (this.parts != null) {
         Entity next = this.parts[this.partIndex];
         this.partIndex++;
         if (this.partIndex >= this.parts.length) {
            this.parts = null;
         }

         return next;
      } else {
         Entity next = this.delegate.next();
         if (next.isMultipartEntity()) {
            PartEntity<?>[] arr = next.getParts();
            if (arr != null) {
               int size = 0;

               for (PartEntity<?> partEntity : arr) {
                  if (partEntity instanceof TFPart) {
                     size++;
                  }
               }

               if (size > 0) {
                  this.partIndex = 0;
                  this.parts = new TFPart[size];
                  int index = 0;

                  for (PartEntity<?> partEntity : arr) {
                     if (partEntity instanceof TFPart<?> part) {
                        this.parts[index] = part;
                        index++;
                     }
                  }
               }
            }
         }

         return next;
      }
   }

   @Override
   public void remove() {
      if (this.parts == null || this.partIndex <= 0) {
         this.delegate.remove();
      } else if (this.partIndex >= this.parts.length) {
         this.parts = null;
      } else {
         System.arraycopy(this.parts, this.partIndex, this.parts, this.partIndex - 1, this.parts.length - 1 - this.partIndex - 1);
      }
   }
}
