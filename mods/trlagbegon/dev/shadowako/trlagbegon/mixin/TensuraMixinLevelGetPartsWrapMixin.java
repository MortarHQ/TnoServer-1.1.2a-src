package dev.shadowako.trlagbegon.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.shadowako.trlagbegon.part.TensuraPartChunkIndex;
import io.github.manasmods.tensura.entity.template.TensuraPartEntity;
import io.github.manasmods.tensura.world.subclass.IMultipartLevel;
import java.util.Collection;
import java.util.function.Predicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Level.class, priority = 999)
public class TensuraMixinLevelGetPartsWrapMixin {
   @WrapOperation(
      method = "getEntityParts(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V",
      at = @At(value = "INVOKE", target = "Lio/github/manasmods/tensura/world/subclass/IMultipartLevel;getParts()Ljava/util/Collection;", remap = false),
      remap = false,
      require = 0
   )
   private Collection<TensuraPartEntity> trlag$filterParts1(
      Level self,
      Entity entity,
      AABB aabb,
      Predicate<? super Entity> predicate,
      CallbackInfoReturnable<?> callback,
      Operation<Collection<TensuraPartEntity>> original
   ) {
      return selectPartsForQuery(self, aabb, original);
   }

   @WrapOperation(
      method = "getEntityParts(Lnet/minecraft/world/level/entity/EntityTypeTest;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V",
      at = @At(value = "INVOKE", target = "Lio/github/manasmods/tensura/world/subclass/IMultipartLevel;getParts()Ljava/util/Collection;", remap = false),
      remap = false,
      require = 0
   )
   private <T extends Entity> Collection<TensuraPartEntity> trlag$filterParts2(
      Level self,
      EntityTypeTest<Entity, T> typeTest,
      AABB aabb,
      Predicate<? super T> predicate,
      CallbackInfoReturnable<?> callback,
      Operation<Collection<TensuraPartEntity>> original
   ) {
      return selectPartsForQuery(self, aabb, original);
   }

   private static Collection<TensuraPartEntity> selectPartsForQuery(Level level, AABB aabb, Operation<Collection<TensuraPartEntity>> allPartsOp) {
      if (level instanceof IMultipartLevel m) {
         if (TensuraPartChunkIndex.trackedPartCount(level) < m.tensura$getParts().size()) {
            TensuraPartChunkIndex.rebuildAllFromTensuraMap(level, m);
         }

         return TensuraPartChunkIndex.queryOverlappingParts(level, aabb);
      } else {
         return (Collection<TensuraPartEntity>)allPartsOp.call(new Object[0]);
      }
   }
}
