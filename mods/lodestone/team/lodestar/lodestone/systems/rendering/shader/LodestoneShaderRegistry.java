package team.lodestar.lodestone.systems.rendering.shader;

import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import team.lodestar.lodestone.LodestoneLib;

public class LodestoneShaderRegistry {
   public final List<LodestoneShader> shaders = new ArrayList<>();
   public final String modId;

   public LodestoneShaderRegistry(String modId) {
      this.modId = modId;
   }

   public ShaderHolder register(String id, VertexFormat format) {
      return this.register(new ShaderHolder(ResourceLocation.fromNamespaceAndPath(this.modId, id), format));
   }

   public <T extends LodestoneShader> T register(T shader) {
      this.shaders.add(shader);
      return shader;
   }

   public void init(RegisterShadersEvent event) {
      LodestoneLib.LOGGER.info("Registering shaders for mod: {}", this.modId);
      this.shaders.forEach(shader -> shader.register(event));
   }
}
