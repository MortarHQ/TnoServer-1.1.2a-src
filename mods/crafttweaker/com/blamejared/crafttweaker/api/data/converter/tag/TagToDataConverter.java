package com.blamejared.crafttweaker.api.data.converter.tag;

import com.blamejared.crafttweaker.api.data.IData;
import javax.annotation.Nullable;
import net.minecraft.nbt.Tag;

public class TagToDataConverter {
   @Nullable
   public static IData convert(Tag tag) {
      if (tag == null) {
         return null;
      }

      TagToDataVisitor visitor = new TagToDataVisitor();
      return visitor.visit(tag);
   }
}
