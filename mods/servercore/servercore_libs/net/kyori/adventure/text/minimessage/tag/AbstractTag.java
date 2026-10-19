package servercore_libs.net.kyori.adventure.text.minimessage.tag;

import servercore_libs.net.kyori.adventure.internal.Internals;
import servercore_libs.net.kyori.examination.Examinable;

abstract class AbstractTag implements Tag, Examinable {
   @Override
   public final String toString() {
      return Internals.toString(this);
   }
}
