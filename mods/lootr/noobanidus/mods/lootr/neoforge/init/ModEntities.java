package noobanidus.mods.lootr.neoforge.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import noobanidus.mods.lootr.common.api.LootrConstants;
import noobanidus.mods.lootr.common.entity.LootrChestMinecartEntity;
import noobanidus.mods.lootr.common.entity.LootrItemFrame;

public class ModEntities {
   private static final DeferredRegister<EntityType<?>> REGISTER = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, "lootr");
   public static final DeferredHolder<EntityType<?>, EntityType<LootrChestMinecartEntity>> LOOTR_MINECART_ENTITY = REGISTER.register(
      "lootr_minecart", () -> Builder.of(LootrChestMinecartEntity::new, MobCategory.MISC).sized(0.98F, 0.7F).clientTrackingRange(8).build("lootr_minecart")
   );
   public static final DeferredHolder<EntityType<?>, EntityType<LootrItemFrame>> ITEM_FRAME = REGISTER.register(
      LootrConstants.ITEM_FRAME.getPath(),
      () -> Builder.of(LootrItemFrame::new, MobCategory.MISC)
         .sized(0.5F, 0.5F)
         .eyeHeight(0.0F)
         .clientTrackingRange(10)
         .updateInterval(Integer.MAX_VALUE)
         .build(LootrConstants.ITEM_FRAME.getPath())
   );

   public static void register(IEventBus bus) {
      REGISTER.register(bus);
   }
}
