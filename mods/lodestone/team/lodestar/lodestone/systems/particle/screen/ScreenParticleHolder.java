package team.lodestar.lodestone.systems.particle.screen;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import team.lodestar.lodestone.config.ClientConfig;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneScreenParticleRenderType;
import team.lodestar.lodestone.systems.particle.screen.base.ScreenParticle;

public class ScreenParticleHolder {
   public static final Tesselator TESSELATOR = new Tesselator();
   public final Map<LodestoneScreenParticleRenderType, ArrayList<ScreenParticle>> particles = new HashMap<>();

   public void tick() {
      this.particles.forEach((pair, particles) -> {
         Iterator<ScreenParticle> iterator = particles.iterator();

         while (iterator.hasNext()) {
            ScreenParticle particle = iterator.next();
            particle.tick();
            if (!particle.isAlive()) {
               iterator.remove();
            }
         }
      });
   }

   public void render() {
      this.render((PoseStack)null);
   }

   public void render(GuiGraphics graphics) {
      this.render(graphics.pose());
   }

   public void render(@Nullable PoseStack poseStack) {
      if (ClientConfig.ENABLE_SCREEN_PARTICLES.getConfigValue()) {
         this.particles.forEach((renderType, particles) -> {
            if (!particles.isEmpty()) {
               BufferBuilder builder = renderType.begin(TESSELATOR, Minecraft.getInstance().getTextureManager());

               for (ScreenParticle next : particles) {
                  next.render(builder, poseStack);
               }

               renderType.end(builder);
            }
         });
      }
   }

   public void addFrom(ScreenParticleHolder otherHolder) {
      this.particles.putAll(otherHolder.particles);
   }

   public boolean isEmpty() {
      return this.particles.values().stream().allMatch(ArrayList::isEmpty);
   }
}
