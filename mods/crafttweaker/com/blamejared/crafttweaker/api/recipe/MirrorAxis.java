package com.blamejared.crafttweaker.api.recipe;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.StringRepresentable.EnumCodec;
import org.jetbrains.annotations.NotNull;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/recipe/MirrorAxis")
@ZenCodeType.Name("crafttweaker.api.recipe.MirrorAxis")
@BracketEnum("minecraft:mirroraxis")
public enum MirrorAxis implements StringRepresentable {
   @ZenCodeType.Field
   ALL("all", true, true, true),
   @ZenCodeType.Field
   DIAGONAL("diagonal", false, false, true),
   @ZenCodeType.Field
   HORIZONTAL("horizontal", true, false, false),
   @ZenCodeType.Field
   NONE("none", false, false, false),
   @ZenCodeType.Field
   VERTICAL("vertical", false, true, false);

   public static final EnumCodec<MirrorAxis> CODEC = StringRepresentable.fromEnum(MirrorAxis::values);
   private final String name;
   private final boolean horizontal;
   private final boolean vertical;
   private final boolean diagonal;

   MirrorAxis(String name, boolean horizontal, boolean vertical, boolean diagonal) {
      this.name = name;
      this.horizontal = horizontal;
      this.vertical = vertical;
      this.diagonal = diagonal;
   }

   public boolean isMirrored() {
      return this.horizontal || this.vertical || this.diagonal;
   }

   public boolean isHorizontal() {
      return this.horizontal;
   }

   public boolean isVertical() {
      return this.vertical;
   }

   public boolean isDiagonal() {
      return this.diagonal;
   }

   @NotNull
   public String getSerializedName() {
      return this.name;
   }
}
