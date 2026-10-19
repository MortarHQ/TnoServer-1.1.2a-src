package noobanidus.mods.lootr.common.impl.type;

import com.google.auto.service.AutoService;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import noobanidus.mods.lootr.common.api.BuiltInLootrTypes;
import noobanidus.mods.lootr.common.api.ILootrType;
import noobanidus.mods.lootr.common.api.data.ILootrInfo;
import org.jetbrains.annotations.Nullable;

@AutoService(ILootrType.class)
public class SimpleLootrType implements ILootrType {
   @Override
   public String getName() {
      return BuiltInLootrTypes.TYPE_SIMPLE;
   }

   @Nullable
   @Override
   public Block getReplacementBlock() {
      return null;
   }

   @Nullable
   @Override
   public EntityType<?> getReplacementEntity() {
      return null;
   }

   @Override
   public void callback() {
      BuiltInLootrTypes.SIMPLE = this;
   }

   @Nullable
   @Override
   public Container getContainer(ILootrInfo info, ServerLevel level) {
      return level.getBlockEntity(info.getInfoPos()) instanceof Container container ? container : null;
   }
}
