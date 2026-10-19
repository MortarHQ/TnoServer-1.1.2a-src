package servercore_libs.net.kyori.adventure.text.event;

import org.jetbrains.annotations.NotNull;
import servercore_libs.net.kyori.adventure.nbt.api.BinaryTagHolder;
import servercore_libs.net.kyori.examination.Examinable;

public interface DataComponentValue extends Examinable {
   static DataComponentValue.@NotNull Removed removed() {
      return RemovedDataComponentValueImpl.REMOVED;
   }

   interface Removed extends DataComponentValue {
   }

   interface TagSerializable extends DataComponentValue {
      @NotNull
      BinaryTagHolder asBinaryTag();
   }
}
