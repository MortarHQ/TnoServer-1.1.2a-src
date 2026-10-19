package cc.sighs.oelib.bless;

import cc.sighs.oelib.registry.extra.ShaderRegister;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShaderEvents {
   public static void register() {
      ShaderRegister.register(
         ResourceLocation.fromNamespaceAndPath("oelib", "chongyang"), DefaultVertexFormat.POSITION_TEX, ShaderToastResources::setSpiritToastShader
      );
      ShaderRegister.register(
         ResourceLocation.fromNamespaceAndPath("oelib", "new_year"), DefaultVertexFormat.POSITION_TEX, ShaderToastResources::setNewYearShader
      );
      ShaderRegister.register(
         ResourceLocation.fromNamespaceAndPath("oelib", "valentine"), DefaultVertexFormat.POSITION_TEX, ShaderToastResources::setValentineShader
      );
   }
}
