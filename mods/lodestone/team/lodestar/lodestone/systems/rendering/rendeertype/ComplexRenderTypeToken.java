package team.lodestar.lodestone.systems.rendering.rendeertype;

import java.util.Objects;
import java.util.function.Consumer;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypes;

public class ComplexRenderTypeToken extends RenderTypeToken {
   private ShaderUniformHandler uniformHandler;
   private Consumer<LodestoneRenderTypes.LodestoneCompositeStateBuilder> modifier;

   public ComplexRenderTypeToken(RenderTypeToken token) {
      super(token.getIdentifier(), token.getTexture());
   }

   @Override
   public ComplexRenderTypeToken addUniformHandler(ShaderUniformHandler uniformHandler) {
      this.uniformHandler = uniformHandler;
      return this;
   }

   @Override
   public ComplexRenderTypeToken addUniformHandler(Consumer<ShaderUniformHandler> modifier) {
      if (this.uniformHandler != null) {
         modifier.accept(this.uniformHandler);
      } else {
         this.addUniformHandler(new ShaderUniformHandler());
         modifier.accept(this.uniformHandler);
      }

      return this;
   }

   @Override
   public ComplexRenderTypeToken addModifier(Consumer<LodestoneRenderTypes.LodestoneCompositeStateBuilder> modifier) {
      this.modifier = modifier;
      return this;
   }

   @Override
   protected RenderTypeToken unique() {
      return new ComplexRenderTypeToken(this).addUniformHandler(this.uniformHandler).addModifier(this.modifier);
   }

   public ShaderUniformHandler getUniformHandler() {
      return this.uniformHandler;
   }

   public Consumer<LodestoneRenderTypes.LodestoneCompositeStateBuilder> getModifier() {
      return this.modifier;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      }

      if (o == null || this.getClass() != o.getClass()) {
         return false;
      }

      if (!super.equals(o)) {
         return false;
      }

      ComplexRenderTypeToken that = (ComplexRenderTypeToken)o;
      boolean equalUniform = Objects.equals(this.uniformHandler, that.uniformHandler);
      boolean equalModifier = Objects.equals(this.modifier, that.modifier);
      return equalUniform && equalModifier;
   }

   @Override
   public int hashCode() {
      return Objects.hash(super.hashCode(), this.uniformHandler, this.modifier);
   }
}
