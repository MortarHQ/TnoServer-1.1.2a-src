package net.p3pp3rf1y.sophisticatedcore.upgrades.cooking;

import java.util.function.UnaryOperator;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.p3pp3rf1y.sophisticatedcore.api.IUpgradeRenderer;
import org.joml.Vector3f;

public class CookingUpgradeRenderer implements IUpgradeRenderer<CookingUpgradeRenderData> {
   public void render(Level level, RandomSource rand, UnaryOperator<Vector3f> getPositionFromOffset, CookingUpgradeRenderData upgradeRenderData) {
      if (upgradeRenderData.isBurning()) {
         if (level.random.nextDouble() < 0.1) {
            Vector3f renderCenter = getPositionFromOffset.apply(new Vector3f());
            level.playLocalSound(renderCenter.x(), renderCenter.y(), renderCenter.z(), SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
         }

         float xOffset = level.random.nextFloat() * 0.6F - 0.3F;
         float yOffset = level.random.nextFloat() * 6.0F / 16.0F;
         float zOffset = 0.02F;
         Vector3f randomAtTheBack = getPositionFromOffset.apply(new Vector3f(xOffset, yOffset, zOffset));
         level.addParticle(ParticleTypes.SMOKE, randomAtTheBack.x(), randomAtTheBack.y(), randomAtTheBack.z(), 0.0, 0.0, 0.0);
         level.addParticle(ParticleTypes.FLAME, randomAtTheBack.x(), randomAtTheBack.y(), randomAtTheBack.z(), 0.0, 0.0, 0.0);
      }
   }
}
