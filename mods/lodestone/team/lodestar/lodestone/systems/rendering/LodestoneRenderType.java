package team.lodestar.lodestone.systems.rendering;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderType.CompositeRenderType;
import net.minecraft.client.renderer.RenderType.CompositeState;
import net.minecraft.client.renderer.RenderType.OutlineProperty;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypes;
import team.lodestar.lodestone.systems.rendering.rendeertype.ShaderUniformHandler;

public class LodestoneRenderType extends RenderType {
   public final HashMap<Object, LodestoneRenderType> copies = new HashMap<>();
   public final CompositeState state;
   private final RenderType outline;
   private final boolean isOutline;
   private final boolean isAdditive;
   private final ShaderUniformHandler uniformHandler;

   public LodestoneRenderType(
      String pName,
      VertexFormat pFormat,
      Mode pMode,
      int pBufferSize,
      boolean pAffectsCrumbling,
      boolean pSortOnUpload,
      CompositeState pState,
      @Nullable ShaderUniformHandler uniformHandler
   ) {
      super(
         pName,
         pFormat,
         pMode,
         pBufferSize,
         pAffectsCrumbling,
         pSortOnUpload,
         () -> pState.states.forEach(RenderStateShard::setupRenderState),
         () -> pState.states.forEach(RenderStateShard::clearRenderState)
      );
      this.state = pState;
      this.outline = pState.outlineProperty == OutlineProperty.AFFECTS_OUTLINE
         ? pState.textureState.cutoutTexture().map(p_173270_ -> (RenderType)CompositeRenderType.OUTLINE.apply(p_173270_, pState.cullState)).orElse(null)
         : null;
      this.isOutline = pState.outlineProperty == OutlineProperty.IS_OUTLINE;
      this.isAdditive = isAdditive(this);
      this.uniformHandler = uniformHandler;
   }

   protected LodestoneRenderType(String name, LodestoneRenderType original) {
      this(name, original.format, original.mode, original.bufferSize, original.affectsCrumbling, original.sortOnUpload, original.state, original.uniformHandler);
   }

   protected LodestoneRenderType(String name, LodestoneRenderType original, ShaderUniformHandler uniformHandler) {
      this(name, original.format, original.mode, original.bufferSize, original.affectsCrumbling, original.sortOnUpload, original.state, uniformHandler);
   }

   protected LodestoneRenderType(String name, LodestoneRenderType original, Consumer<ShaderUniformHandler> uniformHandler) {
      this(
         name,
         original.format,
         original.mode,
         original.bufferSize,
         original.affectsCrumbling,
         original.sortOnUpload,
         original.state,
         new ShaderUniformHandler(original.uniformHandler).accept(uniformHandler)
      );
   }

   protected LodestoneRenderType(
      String name, LodestoneRenderType original, ShaderUniformHandler uniformHandler, Consumer<LodestoneRenderTypes.LodestoneCompositeStateBuilder> modifier
   ) {
      this(
         name,
         original.format,
         original.mode,
         original.bufferSize,
         original.affectsCrumbling,
         original.sortOnUpload,
         LodestoneRenderTypes.builder(original.state).accepts(modifier).createCompositeState(),
         uniformHandler
      );
   }

   protected LodestoneRenderType(
      String name,
      LodestoneRenderType original,
      Consumer<ShaderUniformHandler> uniformHandler,
      Consumer<LodestoneRenderTypes.LodestoneCompositeStateBuilder> modifier
   ) {
      this(
         name,
         original.format,
         original.mode,
         original.bufferSize,
         original.affectsCrumbling,
         original.sortOnUpload,
         LodestoneRenderTypes.builder(original.state).accepts(modifier).createCompositeState(),
         new ShaderUniformHandler(original.uniformHandler).accept(uniformHandler)
      );
   }

   public LodestoneRenderType copy(Object key) {
      if (!this.copies.containsKey(key)) {
         this.copies.put(key, new LodestoneRenderType(this.name, this));
      }

      return this.copies.get(key);
   }

   public LodestoneRenderType copy(Object key, ShaderUniformHandler uniformHandler) {
      if (!this.copies.containsKey(key)) {
         this.copies.put(key, new LodestoneRenderType(this.name, this, uniformHandler));
      }

      return this.copies.get(key);
   }

   public LodestoneRenderType copy(Object key, Consumer<ShaderUniformHandler> uniformHandler) {
      if (!this.copies.containsKey(key)) {
         this.copies.put(key, new LodestoneRenderType(this.name, this, uniformHandler));
      }

      return this.copies.get(key);
   }

   public LodestoneRenderType copy(Object key, ShaderUniformHandler uniformHandler, Consumer<LodestoneRenderTypes.LodestoneCompositeStateBuilder> modifier) {
      if (!this.copies.containsKey(key)) {
         this.copies.put(key, new LodestoneRenderType(this.name, this, uniformHandler, modifier));
      }

      return this.copies.get(key);
   }

   public LodestoneRenderType copy(
      Object key, Consumer<ShaderUniformHandler> uniformHandler, Consumer<LodestoneRenderTypes.LodestoneCompositeStateBuilder> modifier
   ) {
      if (!this.copies.containsKey(key)) {
         this.copies.put(key, new LodestoneRenderType(this.name, this, uniformHandler, modifier));
      }

      return this.copies.get(key);
   }

   public static boolean isAdditive(LodestoneRenderType renderType) {
      return renderType.state.transparencyState.equals(StateShards.ADDITIVE_TRANSPARENCY) || renderType.state.transparencyState.equals(ADDITIVE_TRANSPARENCY);
   }

   @Nullable
   public ShaderUniformHandler getUniformHandler() {
      return this.uniformHandler;
   }

   public Optional<RenderType> outline() {
      return Optional.ofNullable(this.outline);
   }

   public boolean isOutline() {
      return this.isOutline;
   }

   public String toString() {
      return "RenderType[" + this.name + ":" + this.state + "]";
   }

   public void draw(MeshData meshData) {
      RenderSystem.depthMask(this.state.writeMaskState.writeDepth);
      this.setupRenderState();
      BufferUploader.drawWithShader(meshData);
      this.clearRenderState();
   }

   public boolean isAdditive() {
      return this.isAdditive;
   }
}
