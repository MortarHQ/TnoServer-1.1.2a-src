package net.p3pp3rf1y.sophisticatedcore.upgrades.jukebox;

import java.util.function.UnaryOperator;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.p3pp3rf1y.sophisticatedcore.api.IUpgradeRenderer;
import net.p3pp3rf1y.sophisticatedcore.init.ModParticles;
import org.joml.Vector3f;

public class JukeboxUpgradeRenderer implements IUpgradeRenderer<JukeboxUpgradeRenderData> {
   public void render(Level level, RandomSource rand, UnaryOperator<Vector3f> getPositionFromOffset, JukeboxUpgradeRenderData upgradeRenderData) {
      if (upgradeRenderData.isPlaying() && rand.nextInt(2) == 0) {
         float xOffset = level.random.nextFloat() * 0.6F - 0.3F;
         float yOffset = 0.5F + level.random.nextFloat() * 6.0F / 16.0F;
         float zOffset = level.random.nextFloat() * 0.6F - 0.1F;
         Vector3f randomAtTheBack = getPositionFromOffset.apply(new Vector3f(xOffset, yOffset, zOffset));
         level.addParticle(ModParticles.JUKEBOX_NOTE.get(), randomAtTheBack.x(), randomAtTheBack.y(), randomAtTheBack.z(), rand.nextFloat(), 0.0, 0.0);
      }
   }
}
