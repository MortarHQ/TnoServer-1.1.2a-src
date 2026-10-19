package org.openzen.zenscript.javabytecode.compiler;

import org.objectweb.asm.Label;

public class JavaSwitchLabel {
   public final int key;
   public final Label label;

   public JavaSwitchLabel(int key, Label label) {
      this.key = key;
      this.label = label;
   }
}
