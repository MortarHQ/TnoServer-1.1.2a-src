package com.github.ysbbbbbb.kaleidoscopecookery.crafting.soupbase;

import com.github.ysbbbbbb.kaleidoscopecookery.api.client.render.ISoupBaseRender;
import com.github.ysbbbbbb.kaleidoscopecookery.client.render.soupbase.MobSoupBaseRender;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class MobSoupBase extends FluidSoupBase {
   private final EntityType<?> type;

   public MobSoupBase(ResourceLocation name, Item bucket, int bubbleColor) {
      super(name, bucket, bubbleColor);
      if (bucket instanceof MobBucketItem mobBucketItem) {
         this.type = mobBucketItem.type;
      } else {
         throw new IllegalArgumentException("Mob bucket item must have a valid entity type!");
      }
   }

   public MobSoupBase(ResourceLocation name, Item bucket) {
      this(name, bucket, 4159204);
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public ISoupBaseRender getRender() {
      return new MobSoupBaseRender(this.fluid, this.type);
   }
}
