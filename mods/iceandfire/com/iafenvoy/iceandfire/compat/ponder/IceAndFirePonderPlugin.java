package com.iafenvoy.iceandfire.compat.ponder;

import com.iafenvoy.iceandfire.entity.FireDragonEntity;
import com.iafenvoy.iceandfire.entity.IceDragonEntity;
import com.iafenvoy.iceandfire.entity.LightningDragonEntity;
import com.iafenvoy.iceandfire.registry.IafBlocks;
import com.iafenvoy.iceandfire.registry.IafDragonTypes;
import com.iafenvoy.iceandfire.registry.IafEntities;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public class IceAndFirePonderPlugin implements PonderPlugin {
   private static final ResourceLocation DRAGON_TAG_ID = ResourceLocation.fromNamespaceAndPath("iceandfire", "dragon_forge");

   @NotNull
   public String getModId() {
      return "iceandfire";
   }

   public void registerScenes(@NotNull PonderSceneRegistrationHelper<ResourceLocation> helper) {
      helper.forComponents(new ResourceLocation[]{IafBlocks.DRAGONFORGE_FIRE_CORE_DISABLED.getId()})
         .addStoryBoard(
            DragonForgeStoryBoard.id(IafDragonTypes.FIRE),
            new DragonForgeStoryBoard<>(((EntityType)IafEntities.FIRE_DRAGON.get())::create, FireDragonEntity.class)
         );
      helper.forComponents(new ResourceLocation[]{IafBlocks.DRAGONFORGE_ICE_CORE_DISABLED.getId()})
         .addStoryBoard(
            DragonForgeStoryBoard.id(IafDragonTypes.ICE),
            new DragonForgeStoryBoard<>(((EntityType)IafEntities.ICE_DRAGON.get())::create, IceDragonEntity.class)
         );
      helper.forComponents(new ResourceLocation[]{IafBlocks.DRAGONFORGE_LIGHTNING_CORE_DISABLED.getId()})
         .addStoryBoard(
            DragonForgeStoryBoard.id(IafDragonTypes.LIGHTNING),
            new DragonForgeStoryBoard<>(((EntityType)IafEntities.LIGHTNING_DRAGON.get())::create, LightningDragonEntity.class)
         );
   }

   public void registerTags(@NotNull PonderTagRegistrationHelper<ResourceLocation> helper) {
      helper.registerTag(DRAGON_TAG_ID)
         .addToIndex()
         .item((ItemLike)IafBlocks.DRAGONFORGE_FIRE_CORE_DISABLED.get(), true, false)
         .title("Dragon Forge")
         .description("Dragon Forge")
         .register();
      helper.addToTag(DRAGON_TAG_ID)
         .add(IafBlocks.DRAGONFORGE_FIRE_CORE_DISABLED.getId())
         .add(IafBlocks.DRAGONFORGE_ICE_CORE_DISABLED.getId())
         .add(IafBlocks.DRAGONFORGE_LIGHTNING_CORE_DISABLED.getId());
   }

   public static void init() {
      PonderIndex.addPlugin(new IceAndFirePonderPlugin());
   }
}
