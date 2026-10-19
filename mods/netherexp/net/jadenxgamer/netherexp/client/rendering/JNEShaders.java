package net.jadenxgamer.netherexp.client.rendering;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.jadenxgamer.netherexp.NetherExp;
import team.lodestar.lodestone.systems.rendering.shader.LodestoneShaderRegistry;
import team.lodestar.lodestone.systems.rendering.shader.ShaderHolder;

public class JNEShaders {
   public static final LodestoneShaderRegistry SHADERS = new LodestoneShaderRegistry("netherexp");
   public static final ShaderHolder NO_SHADE_ENTITY_CUTOUT = (ShaderHolder)SHADERS.register(
      new ShaderHolder(NetherExp.idPath("netherexp", "rendertype_no_shade_entity_cutout"), DefaultVertexFormat.NEW_ENTITY)
   );
   public static final ShaderHolder NO_SHADE_ENTITY_CUTOUT_NO_CULL = (ShaderHolder)SHADERS.register(
      new ShaderHolder(NetherExp.idPath("netherexp", "rendertype_no_shade_entity_cutout_no_cull"), DefaultVertexFormat.NEW_ENTITY)
   );
   public static final ShaderHolder ENTITY_ADDITIVE = (ShaderHolder)SHADERS.register(
      new ShaderHolder(NetherExp.idPath("netherexp", "rendertype_entity_additive"), DefaultVertexFormat.NEW_ENTITY)
   );
   public static final ShaderHolder FIRE_OVERLAY = (ShaderHolder)SHADERS.register(
      new ShaderHolder(NetherExp.idPath("netherexp", "rendertype_fire_overlay"), DefaultVertexFormat.NEW_ENTITY)
   );
   public static final ShaderHolder PARTICLE_OVERLAY = (ShaderHolder)SHADERS.register(
      new ShaderHolder(NetherExp.idPath("netherexp", "rendertype_particle_overlay"), DefaultVertexFormat.PARTICLE)
   );
   public static final ShaderHolder BURN_PALETTE = (ShaderHolder)SHADERS.register(
      new ShaderHolder(NetherExp.idPath("netherexp", "rendertype_burn_palette"), DefaultVertexFormat.PARTICLE)
   );
}
