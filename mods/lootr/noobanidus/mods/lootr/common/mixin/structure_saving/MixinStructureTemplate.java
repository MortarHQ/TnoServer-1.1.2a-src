package noobanidus.mods.lootr.common.mixin.structure_saving;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import noobanidus.mods.lootr.common.api.LootrAPI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(StructureTemplate.class)
public class MixinStructureTemplate {
   @WrapOperation(
      method = "fillFromWorld",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/level/block/entity/BlockEntity;saveWithId(Lnet/minecraft/core/HolderLookup$Provider;)Lnet/minecraft/nbt/CompoundTag;"
      )
   )
   private CompoundTag LootrInjectStructureSavingStart(BlockEntity instance, Provider provider, Operation<CompoundTag> original) {
      LootrAPI.shouldDiscardIdAndOpeners = true;
      CompoundTag result = (CompoundTag)original.call(new Object[]{instance, provider});
      LootrAPI.shouldDiscardIdAndOpeners = false;
      return result;
   }
}
