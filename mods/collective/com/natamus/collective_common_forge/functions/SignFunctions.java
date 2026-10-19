package com.natamus.collective_common_forge.functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;

public class SignFunctions {
   public static List<String> getSignText(BlockEntity blockEntity) {
      if (blockEntity instanceof HangingSignBlockEntity) {
         return getSignText((HangingSignBlockEntity)blockEntity);
      } else {
         return blockEntity instanceof SignBlockEntity ? getSignText((SignBlockEntity)blockEntity) : new ArrayList<>();
      }
   }

   public static List<String> getSignText(SignBlockEntity signBlockEntity) {
      return getSignText(Arrays.asList(signBlockEntity.getFrontText(), signBlockEntity.getBackText()));
   }

   public static List<String> getSignText(HangingSignBlockEntity hangingSignBlockEntity) {
      return getSignText(Arrays.asList(hangingSignBlockEntity.getFrontText(), hangingSignBlockEntity.getBackText()));
   }

   public static List<String> getSignText(List<SignText> signTextList) {
      List<String> lines = new ArrayList<>();

      for (SignText signText : signTextList) {
         for (Component line : signText.getMessages(false)) {
            if (line.equals(Component.EMPTY)) {
               lines.add("");
            } else {
               lines.add(line.getString());
            }
         }
      }

      return lines;
   }
}
