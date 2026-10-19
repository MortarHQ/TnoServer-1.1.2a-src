package cn.star.rhenium.mixin;

import cn.star.rhenium.Config;
import cn.star.rhenium.access.StructureTemplateManagerAccess;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StructureTemplateManager.class)
public abstract class StructureTemplateManagerMixin implements StructureTemplateManagerAccess {
   @Shadow
   private Map<ResourceLocation, Optional<StructureTemplate>> structureRepository;
   @Unique
   private final ArrayDeque<ResourceLocation> rhenium$templateAccessOrder = new ArrayDeque<>();
   @Unique
   private final Object rhenium$templateCacheLock = new Object();

   @Inject(method = "get", at = @At("RETURN"))
   private void rhenium$limitTemplateCache(ResourceLocation id, CallbackInfoReturnable<Optional<StructureTemplate>> cir) {
      if (Config.limitTemplateCache && !((Optional)cir.getReturnValue()).isEmpty()) {
         synchronized (this.rhenium$templateCacheLock) {
            this.rhenium$templateAccessOrder.remove(id);
            this.rhenium$templateAccessOrder.addLast(id);

            while (this.rhenium$templateAccessOrder.size() > Config.templateCacheSize) {
               ResourceLocation evicted = this.rhenium$templateAccessOrder.removeFirst();
               if (!evicted.equals(id)) {
                  this.structureRepository.remove(evicted);
               }
            }
         }
      }
   }

   @Inject(method = "onResourceManagerReload", at = @At("HEAD"))
   private void rhenium$clearTemplateOrder(ResourceManager resourceManager, CallbackInfo ci) {
      synchronized (this.rhenium$templateCacheLock) {
         this.rhenium$templateAccessOrder.clear();
      }
   }

   @Inject(method = "remove", at = @At("HEAD"))
   private void rhenium$forgetRemovedTemplate(ResourceLocation id, CallbackInfo ci) {
      synchronized (this.rhenium$templateCacheLock) {
         this.rhenium$templateAccessOrder.remove(id);
      }
   }

   @Override
   public int rhenium$getCachedTemplateCount() {
      synchronized (this.rhenium$templateCacheLock) {
         int count = 0;

         for (Optional<StructureTemplate> template : this.structureRepository.values()) {
            if (template.isPresent()) {
               count++;
            }
         }

         return count;
      }
   }
}
