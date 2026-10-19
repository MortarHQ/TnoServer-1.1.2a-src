package io.github.Memoires.mysticism.mixin;

import io.github.Memoires.mysticism.entity.skill.SolidificationShieldEntity;
import io.github.Memoires.mysticism.util.SelectionUtils;
import io.github.manasmods.tensura.entity.magic.beam.BeamProjectile;
import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BeamProjectile.class)
public abstract class MixinBeamProjectile {
   @Unique
   private final List<Class<? extends Entity>> OBSTRUCTING_ENTITIES = List.of(SolidificationShieldEntity.class);

   @Shadow
   public abstract List<Entity> collectEntityCollision(Vec3 var1, Vec3 var2);

   @Inject(method = "updateCollisionPos(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;)Ljava/util/List;", at = @At("HEAD"), cancellable = true)
   private void entityObstruction(Vec3 from, Vec3 to, CallbackInfoReturnable<List<Entity>> cir) {
      BeamProjectile beam = (BeamProjectile)this;
      if (beam.getOwner() instanceof LivingEntity owner) {
         Vec3 position = SelectionUtils.getClosestEntityInViewPosition(this.OBSTRUCTING_ENTITIES, beam, beam.getRange(), beam.getSize());
         if (position != null) {
            beam.side = owner.getNearestViewDirection();
            beam.setCollidePos((float)position.x, (float)position.y, (float)position.z);
            cir.cancel();
            cir.setReturnValue(this.collectEntityCollision(from, position));
         }
      }
   }
}
