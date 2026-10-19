package toni.doesittick.mixin;

import java.util.List;
import net.minecraft.core.Holder.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import toni.doesittick.DoesItTick;
import toni.doesittick.api.Tickable;

@Mixin(EntityType.class)
public class EntityTypeMixin implements Tickable.EntityType {
   @Shadow
   @Final
   private Reference<EntityType<?>> builtInRegistryHolder;
   @Unique
   private Boolean doespotatotick$shouldAlwaysTick = null;
   @Unique
   private Boolean doespotatotick$shouldAlwaysTickInRaid = null;

   @Override
   public Boolean doespotatotick$shouldAlwaysTick() {
      if (this.doespotatotick$shouldAlwaysTick == null) {
         ResourceLocation id = this.builtInRegistryHolder.key().location();
         this.doespotatotick$shouldAlwaysTick = ((List)DoesItTick.ENTITIES_WHITELIST.get()).contains(id.toString())
            || ((List)DoesItTick.ENTITIES_MOD_ID_WHITELIST.get()).contains(id.getNamespace());
      }

      return this.doespotatotick$shouldAlwaysTick;
   }

   @Override
   public Boolean doespotatotick$shouldAlwaysTickInRaid() {
      if (this.doespotatotick$shouldAlwaysTickInRaid == null) {
         ResourceLocation id = this.builtInRegistryHolder.key().location();
         this.doespotatotick$shouldAlwaysTickInRaid = ((List)DoesItTick.RAID_ENTITIES_WHITELIST.get()).contains(id.toString())
            || ((List)DoesItTick.RAID_ENTITIES_MOD_ID_LIST.get()).contains(id.getNamespace());
      }

      return this.doespotatotick$shouldAlwaysTickInRaid;
   }
}
