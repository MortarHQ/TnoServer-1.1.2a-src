package team.creative.creativecore.common.level;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import team.creative.creativecore.common.util.math.matrix.IVecOrigin;
import team.creative.creativecore.common.util.math.vec.Vec3d;

public interface IOrientatedLevel extends LevelAccessor {
   IVecOrigin getOrigin();

   void setOrigin(Vec3d var1);

   Entity getHolder();

   void setHolder(Entity var1);

   void unload();
}
