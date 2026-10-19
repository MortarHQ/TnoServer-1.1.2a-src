package com.iafenvoy.iceandfire.registry;

import com.iafenvoy.iceandfire.data.BestiaryPage;
import com.iafenvoy.iceandfire.data.DragonColor;
import com.iafenvoy.iceandfire.data.DragonType;
import com.iafenvoy.iceandfire.data.HippogryphType;
import com.iafenvoy.iceandfire.data.SeaSerpentType;
import com.iafenvoy.iceandfire.data.TrollType;
import com.mojang.serialization.Lifecycle;
import java.util.Locale;
import net.minecraft.core.DefaultedMappedRegistry;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public final class IafRegistries {
   public static final DefaultedRegistry<BestiaryPage> BESTIARY_PAGE = create(
      String.format(Locale.ROOT, "%s:introduction", "iceandfire"), IafRegistryKeys.BESTIARY_PAGE
   );
   public static final DefaultedRegistry<DragonColor> DRAGON_COLOR = create(String.format(Locale.ROOT, "%s:red", "iceandfire"), IafRegistryKeys.DRAGON_COLOR);
   public static final DefaultedRegistry<DragonType> DRAGON_TYPE = create(String.format(Locale.ROOT, "%s:fire", "iceandfire"), IafRegistryKeys.DRAGON_TYPE);
   public static final DefaultedRegistry<HippogryphType> HIPPOGRYPH_TYPE = create(
      String.format(Locale.ROOT, "%s:black", "iceandfire"), IafRegistryKeys.HIPPOGRYPH_TYPE
   );
   public static final DefaultedRegistry<SeaSerpentType> SEA_SERPENT_TYPE = create(
      String.format(Locale.ROOT, "%s:blue", "iceandfire"), IafRegistryKeys.SEA_SERPENT_TYPE
   );
   public static final DefaultedRegistry<TrollType> TROLL_TYPE = create(String.format(Locale.ROOT, "%s:forest", "iceandfire"), IafRegistryKeys.TROLL_TYPE);

   private static <T> DefaultedRegistry<T> create(String defaultId, ResourceKey<Registry<T>> key) {
      return new DefaultedMappedRegistry(defaultId, key, Lifecycle.stable(), false);
   }
}
