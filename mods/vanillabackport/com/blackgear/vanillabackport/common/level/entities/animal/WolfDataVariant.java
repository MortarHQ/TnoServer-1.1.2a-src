package com.blackgear.vanillabackport.common.level.entities.animal;

import com.blackgear.vanillabackport.common.api.variant.ClientAsset;
import com.blackgear.vanillabackport.common.api.variant.spawn.PriorityProvider;
import com.blackgear.vanillabackport.common.api.variant.spawn.SpawnCondition;
import com.blackgear.vanillabackport.common.api.variant.spawn.SpawnContext;
import com.blackgear.vanillabackport.common.api.variant.spawn.SpawnPrioritySelectors;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;

public record WolfDataVariant(WolfDataVariant.AssetInfo assetInfo, SpawnPrioritySelectors spawnConditions)
   implements PriorityProvider<SpawnContext, SpawnCondition> {
   public static final Codec<WolfDataVariant> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            WolfDataVariant.AssetInfo.CODEC.fieldOf("assets").forGetter(WolfDataVariant::assetInfo),
            SpawnPrioritySelectors.CODEC.fieldOf("spawn_conditions").forGetter(WolfDataVariant::spawnConditions)
         )
         .apply(instance, WolfDataVariant::new)
   );

   @Override
   public List<PriorityProvider.Selector<SpawnContext, SpawnCondition>> selectors() {
      return this.spawnConditions.selectors();
   }

   public record AssetInfo(ClientAsset wild, ClientAsset tame, ClientAsset angry) {
      public static final Codec<WolfDataVariant.AssetInfo> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               ClientAsset.CODEC.fieldOf("wild").forGetter(WolfDataVariant.AssetInfo::wild),
               ClientAsset.CODEC.fieldOf("tame").forGetter(WolfDataVariant.AssetInfo::tame),
               ClientAsset.CODEC.fieldOf("angry").forGetter(WolfDataVariant.AssetInfo::angry)
            )
            .apply(instance, WolfDataVariant.AssetInfo::new)
      );
   }
}
