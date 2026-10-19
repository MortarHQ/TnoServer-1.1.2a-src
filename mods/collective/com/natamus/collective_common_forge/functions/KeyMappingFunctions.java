package com.natamus.collective_common_forge.functions;

import com.mojang.blaze3d.platform.InputConstants.Key;
import net.minecraft.client.KeyMapping;

public class KeyMappingFunctions {
   public static Key getKey(KeyMapping keyMapping) {
      return keyMapping.key;
   }
}
