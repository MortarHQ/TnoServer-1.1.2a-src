package team.lodestar.lodestone.systems.rendering.shader;

import com.mojang.blaze3d.vertex.VertexFormat;
import java.io.IOException;
import java.util.function.Supplier;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.RenderStateShard.ShaderStateShard;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import team.lodestar.lodestone.LodestoneLib;

public class ShaderHolder implements LodestoneShader {
   protected final ResourceLocation shaderLocation;
   protected final VertexFormat shaderFormat;
   protected ExtendedShaderInstance shaderInstance;
   protected ShaderStateShard shard;

   public ShaderHolder(ResourceLocation shaderLocation, VertexFormat shaderFormat) {
      this.shaderLocation = shaderLocation;
      this.shaderFormat = shaderFormat;
   }

   public ExtendedShaderInstance createInstance(ResourceProvider provider) throws IOException {
      return new ExtendedShaderInstance(provider, this);
   }

   public ResourceLocation getShaderLocation() {
      return this.shaderLocation;
   }

   public VertexFormat getShaderFormat() {
      return this.shaderFormat;
   }

   public ExtendedShaderInstance getShaderInstance() {
      return this.shaderInstance;
   }

   public Supplier<ShaderInstance> supplyShaderInstance() {
      return this::getShaderInstance;
   }

   public void setShaderInstance(ShaderInstance reloadedShaderInstance) {
      this.shaderInstance = (ExtendedShaderInstance)reloadedShaderInstance;
   }

   public ShaderStateShard getShard() {
      if (this.shard == null) {
         this.shard = new ShaderStateShard(this.supplyShaderInstance());
      }

      return this.shard;
   }

   @Override
   public void register(RegisterShadersEvent event) {
      try {
         ResourceProvider provider = event.getResourceProvider();
         event.registerShader(this.createInstance(provider), this::setShaderInstance);
      } catch (IOException e) {
         LodestoneLib.LOGGER.error("Error registering shader", e);
         e.printStackTrace();
      }
   }
}
