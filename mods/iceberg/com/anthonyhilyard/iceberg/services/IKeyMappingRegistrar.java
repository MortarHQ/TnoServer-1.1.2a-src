package com.anthonyhilyard.iceberg.services;

import net.minecraft.client.KeyMapping;

public interface IKeyMappingRegistrar {
   KeyMapping registerMapping(KeyMapping var1);

   KeyMapping registerMapping(KeyMapping var1, IKeyMappingRegistrar.KeyMappingContext var2);

   enum KeyMappingContext {
      UNIVERSAL,
      GUI,
      IN_GAME,
      NO_CONFLICT;
   }
}
