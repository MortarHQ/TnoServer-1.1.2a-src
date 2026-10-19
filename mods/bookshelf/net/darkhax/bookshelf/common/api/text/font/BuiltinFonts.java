package net.darkhax.bookshelf.common.api.text.font;

import java.util.Set;
import net.minecraft.resources.ResourceLocation;

public enum BuiltinFonts implements IFontEntry {
   DEFAULT("default"),
   ALT("alt"),
   ILLAGER("illageralt"),
   UNIFORM("uniform");

   public static final Set<ResourceLocation> FONT_IDS;
   private final ResourceLocation fontId;

   BuiltinFonts(String path) {
      this(ResourceLocation.tryBuild("minecraft", path));
   }

   BuiltinFonts(ResourceLocation fontID) {
      this.fontId = fontID;
   }

   @Override
   public ResourceLocation identifier() {
      return this.fontId;
   }

   static {
      FONT_IDS = Set.of(DEFAULT.fontId, ALT.fontId, ILLAGER.fontId, UNIFORM.fontId);
   }
}
