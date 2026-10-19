package net.darkhax.pricklemc.common.api.config.comment;

import java.io.IOException;
import java.lang.reflect.Field;
import net.darkhax.pricklemc.common.api.annotations.Value;
import org.jetbrains.annotations.Nullable;

public interface ICommentResolver {
   @Nullable
   IComment resolve(Field var1, @Nullable Object var2, Value var3) throws IOException;

   static boolean hasComment(Value valueMeta) {
      return valueMeta != null && valueMeta.comment() != null && !valueMeta.comment().isBlank();
   }
}
