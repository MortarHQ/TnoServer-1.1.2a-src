package mebahelcreaturesdwarven.block.entity.custom;

import mebahelcreaturesdwarven.block.entity.ModBlockEntities;
import net.minecraft.class_2338;
import net.minecraft.class_2586;
import net.minecraft.class_2680;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;

public class DwemerSpiderBlockEntity extends class_2586 implements GeoBlockEntity {
   private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
   public int age = 0;

   public DwemerSpiderBlockEntity(class_2338 pos, class_2680 state) {
      super(ModBlockEntities.DWEMER_SPIDER_BLOCK_ENTITY, pos, state);
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public void registerControllers(ControllerRegistrar controllers) {
   }
}
