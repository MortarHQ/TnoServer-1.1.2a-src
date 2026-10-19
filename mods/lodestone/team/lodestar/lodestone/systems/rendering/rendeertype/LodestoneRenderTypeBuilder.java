package team.lodestar.lodestone.systems.rendering.rendeertype;

import java.util.Objects;
import java.util.function.Consumer;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypes;
import team.lodestar.lodestone.systems.rendering.LodestoneRenderType;

public class LodestoneRenderTypeBuilder {
   private final RenderTypeProvider provider;
   private RenderTypeToken token;

   public LodestoneRenderTypeBuilder(RenderTypeProvider provider, RenderTypeToken token) {
      this.provider = provider;
      this.token = token;
   }

   public LodestoneRenderTypeBuilder withUniformHandler(ShaderUniformHandler uniformHandler) {
      this.token = this.token.addUniformHandler(uniformHandler);
      return this;
   }

   public LodestoneRenderTypeBuilder withUniformHandler(Consumer<ShaderUniformHandler> modifier) {
      this.token = this.token.addUniformHandler(modifier);
      return this;
   }

   public LodestoneRenderTypeBuilder withModifier(Consumer<LodestoneRenderTypes.LodestoneCompositeStateBuilder> modifier) {
      this.token = this.token.addModifier(modifier);
      return this;
   }

   public LodestoneRenderType getRenderType() {
      return this.provider.createRenderType(this.token);
   }

   public RenderTypeProvider getProvider() {
      return this.provider;
   }

   public RenderTypeToken getToken() {
      return this.token;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         LodestoneRenderTypeBuilder that = (LodestoneRenderTypeBuilder)o;
         return Objects.equals(this.token, that.token);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.token);
   }
}
