package com.natamus.collective_common_fabric.functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.class_2561;
import net.minecraft.class_2586;
import net.minecraft.class_2625;
import net.minecraft.class_7717;
import net.minecraft.class_8242;

public class SignFunctions {
   public static List<String> getSignText(class_2586 blockEntity) {
      if (blockEntity instanceof class_7717) {
         return getSignText((class_7717)blockEntity);
      } else {
         return blockEntity instanceof class_2625 ? getSignText((class_2625)blockEntity) : new ArrayList<>();
      }
   }

   public static List<String> getSignText(class_2625 signBlockEntity) {
      return getSignText(Arrays.asList(signBlockEntity.method_49853(), signBlockEntity.method_49854()));
   }

   public static List<String> getSignText(class_7717 hangingSignBlockEntity) {
      return getSignText(Arrays.asList(hangingSignBlockEntity.method_49853(), hangingSignBlockEntity.method_49854()));
   }

   public static List<String> getSignText(List<class_8242> signTextList) {
      List<String> lines = new ArrayList<>();

      for (class_8242 signText : signTextList) {
         for (class_2561 line : signText.method_49877(false)) {
            if (line.equals(class_2561.field_25310)) {
               lines.add("");
            } else {
               lines.add(line.getString());
            }
         }
      }

      return lines;
   }
}
