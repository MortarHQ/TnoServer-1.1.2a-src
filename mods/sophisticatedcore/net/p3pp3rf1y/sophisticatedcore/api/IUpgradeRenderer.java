package net.p3pp3rf1y.sophisticatedcore.api;

import java.util.function.UnaryOperator;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.p3pp3rf1y.sophisticatedcore.renderdata.IUpgradeRenderData;
import org.joml.Vector3f;

public interface IUpgradeRenderer<T extends IUpgradeRenderData> {
   void render(Level var1, RandomSource var2, UnaryOperator<Vector3f> var3, T var4);
}
