package team.lodestar.lodestone.systems.rendering.rendeertype;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.client.renderer.RenderStateShard.TextureStateShard;
import net.minecraft.resources.ResourceLocation;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypes;

public class RenderTypeToken {
   private static final HashMap<ResourceLocation, RenderTypeToken> CACHED_TEXTURE_TOKENS = new HashMap<>();
   private static final HashMap<TextureStateShard, RenderTypeToken> CACHED_STATE_TOKENS = new HashMap<>();
   private final UUID identifier;
   private final ResourceLocation texture;

   protected RenderTypeToken(TextureStateShard texture) {
      this((ResourceLocation)texture.cutoutTexture().orElseThrow());
   }

   protected RenderTypeToken(ResourceLocation texture) {
      this(UUID.randomUUID(), texture);
   }

   public RenderTypeToken(UUID identifier, ResourceLocation texture) {
      this.identifier = identifier;
      this.texture = texture;
   }

   public static RenderTypeToken createToken(ResourceLocation texture) {
      return CACHED_TEXTURE_TOKENS.computeIfAbsent(texture, RenderTypeToken::new);
   }

   public static RenderTypeToken createToken(TextureStateShard texture) {
      return CACHED_STATE_TOKENS.computeIfAbsent(texture, RenderTypeToken::new);
   }

   public ComplexRenderTypeToken addUniformHandler(ShaderUniformHandler uniformHandler) {
      return new ComplexRenderTypeToken(this).addUniformHandler(uniformHandler);
   }

   public ComplexRenderTypeToken addUniformHandler(Consumer<ShaderUniformHandler> uniformHandler) {
      return new ComplexRenderTypeToken(this).addUniformHandler(uniformHandler);
   }

   public ComplexRenderTypeToken addModifier(Consumer<LodestoneRenderTypes.LodestoneCompositeStateBuilder> modifier) {
      return new ComplexRenderTypeToken(this).addModifier(modifier);
   }

   protected RenderTypeToken unique() {
      return new RenderTypeToken(this.identifier, this.texture);
   }

   public UUID getIdentifier() {
      return this.identifier;
   }

   public ResourceLocation getTexture() {
      return this.texture;
   }

   @Override
   public String toString() {
      return "RenderTypeToken{identifier=" + this.identifier + ", texture=" + this.texture + "}";
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         RenderTypeToken that = (RenderTypeToken)o;
         return Objects.equals(this.identifier, that.identifier) && Objects.equals(this.texture, that.texture);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.identifier, this.texture);
   }
}
