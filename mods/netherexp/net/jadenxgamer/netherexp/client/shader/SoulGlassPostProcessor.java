package net.jadenxgamer.netherexp.client.shader;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.config.JNEConfigImpl;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Matrix4f;
import team.lodestar.lodestone.systems.postprocess.PostProcessor;

public class SoulGlassPostProcessor extends PostProcessor {
   public static final SoulGlassPostProcessor INSTANCE = new SoulGlassPostProcessor();
   private static float tintR = 0.168627F;
   private static float tintG = 0.529412F;
   private static float tintB = 0.513725F;

   public ResourceLocation getPostChainLocation() {
      return NetherExp.netherexpPath("soul_glass");
   }

   public void beforeProcess(Matrix4f viewModelMatrix) {
      if (this.postChain != null) {
         Minecraft client = Minecraft.getInstance();
         if (client.level != null) {
            float gameTimeTicks = (float)client.level.getGameTime() + client.getTimer().getGameTimeDeltaPartialTick(true);
            float timeInSeconds = gameTimeTicks / 20.0F;
            this.postChain.setUniform("GameTime", timeInSeconds);
            this.postChain.setUniform("TintColorR", tintR);
            this.postChain.setUniform("TintColorG", tintG);
            this.postChain.setUniform("TintColorB", tintB);
            if (JNEConfigImpl.COMMON.isLoaded() && (Boolean)JNEConfigs.NO_SOUL_GLASS_RIPPLE.get()) {
               this.postChain.setUniform("RippleStrength", 0.0F);
               this.postChain.setUniform("RippleFrequency", 0.0F);
               this.postChain.setUniform("RippleSpeed", 0.0F);
               this.postChain.setUniform("RippleRandomness", 0.0F);
            }
         }
      }
   }

   public void afterProcess() {
   }

   public static void tick(BlockState state) {
      if (state.getBlock() == JNEBlocks.SOUL_GLASS.get()) {
         tintR = 0.168627F;
         tintG = 0.529412F;
         tintB = 0.513725F;
      } else if (state.getBlock() == JNEBlocks.WAXEN_SOUL_GLASS.get()) {
         tintR = 0.529412F;
         tintG = 0.1686F;
         tintB = 0.1686F;
      }
   }
}
