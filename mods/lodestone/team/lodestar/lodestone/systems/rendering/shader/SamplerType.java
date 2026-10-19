package team.lodestar.lodestone.systems.rendering.shader;

public enum SamplerType {
   TEXTURE_1D("1d", 3552),
   TEXTURE_2D("2d", 3553),
   TEXTURE_3D("3d", 32879),
   TEXTURE_CUBE("cube", 34067);

   final String type;
   final int glType;

   SamplerType(String type, int glType) {
      this.type = type;
      this.glType = glType;
   }

   public int getGlType() {
      return this.glType;
   }

   public static SamplerType fromString(String type) {
      for (SamplerType samplerType : values()) {
         if (samplerType.type.equalsIgnoreCase(type)) {
            return samplerType;
         }
      }

      return null;
   }

   public static SamplerType getDefault() {
      return TEXTURE_2D;
   }
}
