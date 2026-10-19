package com.blamejared.crafttweaker.api.zencode;

import com.blamejared.crafttweaker.api.natives.NativeTypeInfo;

public record ZenTypeInfo(String targetName, ZenTypeInfo.TypeKind kind) {
   public static ZenTypeInfo from(NativeTypeInfo nativeInfo) {
      return new ZenTypeInfo(nativeInfo.name(), ZenTypeInfo.TypeKind.EXPANSION);
   }

   public enum TypeKind {
      CLASS,
      EXPANSION;
   }
}
