package twilightforest.enums.extensions;

import net.minecraft.world.entity.vehicle.Boat.Type;
import net.neoforged.neoforge.common.util.Lazy;
import twilightforest.beanification.Autowired;
import twilightforest.beanification.Component;
import twilightforest.util.ModidPrefixUtil;

@Component
public class TFBoatTypeEnumExtension {
   @Autowired
   private ModidPrefixUtil modidPrefixUtil;
   public final Lazy<Type> TWILIGHT_OAK = Lazy.of(() -> Type.byName(this.modidPrefixUtil.stringPrefix("twilight_oak")));
   public final Lazy<Type> CANOPY = Lazy.of(() -> Type.byName(this.modidPrefixUtil.stringPrefix("canopy")));
   public final Lazy<Type> MANGROVE = Lazy.of(() -> Type.byName(this.modidPrefixUtil.stringPrefix("mangrove")));
   public final Lazy<Type> DARK = Lazy.of(() -> Type.byName(this.modidPrefixUtil.stringPrefix("dark")));
   public final Lazy<Type> TIME = Lazy.of(() -> Type.byName(this.modidPrefixUtil.stringPrefix("time")));
   public final Lazy<Type> TRANSFORMATION = Lazy.of(() -> Type.byName(this.modidPrefixUtil.stringPrefix("transformation")));
   public final Lazy<Type> MINING = Lazy.of(() -> Type.byName(this.modidPrefixUtil.stringPrefix("mining")));
   public final Lazy<Type> SORTING = Lazy.of(() -> Type.byName(this.modidPrefixUtil.stringPrefix("sorting")));
}
