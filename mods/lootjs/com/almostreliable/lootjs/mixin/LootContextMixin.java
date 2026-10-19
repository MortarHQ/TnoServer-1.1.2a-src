package com.almostreliable.lootjs.mixin;

import com.almostreliable.lootjs.core.LootType;
import com.almostreliable.lootjs.loot.extension.LootContextExtension;
import com.almostreliable.lootjs.loot.extension.LootParamsExtension;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LootContext.class)
public class LootContextMixin implements LootContextExtension {
   @Shadow
   @Final
   private LootParams params;
   @Unique
   @Nullable
   private Map<String, Object> lootjs$data;

   @Override
   public LootContext lootjs$self() {
      return (LootContext)this;
   }

   @Override
   public Map<String, Object> lootjs$getData() {
      if (this.lootjs$data == null) {
         this.lootjs$data = new HashMap<>();
      }

      return this.lootjs$data;
   }

   @Override
   public LootType lootjs$getType() {
      return ((LootParamsExtension)this.params).lootjs$getType();
   }
}
