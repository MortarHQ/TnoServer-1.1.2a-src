package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungscavebiomes.services.Services;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;

@AutoRegister("yungscavebiomes")
public class DecoratedPotPatternsModule {
   public static final Set<ResourceKey<DecoratedPotPattern>> ALL_PATTERNS = new HashSet<>();
   public static final Map<Item, ResourceKey<DecoratedPotPattern>> RESOUCE_KEY_BY_ITEM = new HashMap<>();
   public static final ResourceKey<DecoratedPotPattern> HOURGLASS = Services.PLATFORM
      .registerDecoratedPotPattern("hourglass_pottery_pattern", ItemModule.HOURGLASS_POTTERY_SHERD);
   public static final ResourceKey<DecoratedPotPattern> CLOCK = Services.PLATFORM
      .registerDecoratedPotPattern("clock_pottery_pattern", ItemModule.CLOCK_POTTERY_SHERD);

   @AutoRegister("_ignored")
   public static void init() {
   }

   @Nullable
   public static ResourceKey<DecoratedPotPattern> getResourceKeyForItem(Item item) {
      return RESOUCE_KEY_BY_ITEM.get(item);
   }

   public static boolean isCustomRegisteredKey(ResourceKey<String> key) {
      return ALL_PATTERNS.contains(key);
   }
}
