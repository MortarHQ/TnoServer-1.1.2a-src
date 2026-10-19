package twilightforest.compat.curios;

import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import net.neoforged.neoforge.network.PacketDistributor;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.event.DropRulesEvent;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurio.DropRule;
import top.theillusivec4.curios.api.type.capability.ICurio.SoundInfo;
import twilightforest.client.model.TFModelLayers;
import twilightforest.compat.curios.model.CharmOfLifeNecklaceModel;
import twilightforest.compat.curios.renderer.CharmOfKeepingRenderer;
import twilightforest.compat.curios.renderer.CharmOfLifeNecklaceRenderer;
import twilightforest.compat.curios.renderer.CurioHeadRenderer;
import twilightforest.events.CharmEvents;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFItems;
import twilightforest.network.CreateMovingCicadaSoundPacket;

public class CuriosCompat {
   public static void registerCuriosCapabilities(RegisterCapabilitiesEvent event) {
      event.registerItem(
         CuriosCapability.ITEM,
         (stack, context) -> new ICurio() {
            public ItemStack getStack() {
               return stack;
            }

            @Nonnull
            public SoundInfo getEquipSound(SlotContext slotContext) {
               return new SoundInfo((SoundEvent)SoundEvents.ARMOR_EQUIP_GENERIC.value(), 1.0F, 1.0F);
            }

            public void onEquip(SlotContext context, ItemStack prevStack) {
               if (!context.entity().getItemBySlot(EquipmentSlot.HEAD).is(((Block)TFBlocks.CICADA.get()).asItem())
                  && stack.is(((Block)TFBlocks.CICADA.get()).asItem())
                  && !context.entity().level().isClientSide()) {
                  PacketDistributor.sendToPlayersTrackingEntityAndSelf(
                     context.entity(), new CreateMovingCicadaSoundPacket(context.entity().getId()), new CustomPacketPayload[0]
                  );
               }
            }

            public boolean canEquipFromUse(SlotContext slotContext) {
               return true;
            }
         },
         new ItemLike[]{
            TFItems.CHARM_OF_KEEPING_1,
            TFItems.CHARM_OF_KEEPING_2,
            TFItems.CHARM_OF_KEEPING_3,
            TFItems.CHARM_OF_LIFE_1,
            TFItems.CHARM_OF_LIFE_2,
            TFItems.NAGA_TROPHY,
            TFItems.LICH_TROPHY,
            TFItems.MINOSHROOM_TROPHY,
            TFItems.HYDRA_TROPHY,
            TFItems.KNIGHT_PHANTOM_TROPHY,
            TFItems.UR_GHAST_TROPHY,
            TFItems.ALPHA_YETI_TROPHY,
            TFItems.SNOW_QUEEN_TROPHY,
            TFItems.QUEST_RAM_TROPHY,
            TFBlocks.CICADA,
            TFBlocks.FIREFLY,
            TFBlocks.MOONWORM,
            TFItems.SKELETON_SKULL_CANDLE,
            TFItems.WITHER_SKELETON_SKULL_CANDLE,
            TFItems.ZOMBIE_SKULL_CANDLE,
            TFItems.CREEPER_SKULL_CANDLE,
            TFItems.PLAYER_SKULL_CANDLE,
            TFItems.PIGLIN_SKULL_CANDLE
         }
      );
   }

   public static void keepCurios(DropRulesEvent event) {
      if (event.getEntity() instanceof Player player) {
         CompoundTag playerData = CharmEvents.getPlayerData(player);
         if (!player.level().isClientSide()
            && playerData.contains("CharmStack")
            && playerData.contains("TFCharmInventory")
            && !playerData.getList("TFCharmInventory", 10).isEmpty()) {
            CuriosApi.getCuriosInventory(player).ifPresent(modifiable -> {
               for (int i = 0; i < modifiable.getSlots(); i++) {
                  int finalI = i;
                  event.addOverride(stack -> stack == modifiable.getEquippedCurios().getStackInSlot(finalI), DropRule.ALWAYS_KEEP);
               }
            });
         }
      }
   }

   public static void registerCurioLayers(RegisterLayerDefinitions event) {
      event.registerLayerDefinition(TFModelLayers.CHARM_OF_LIFE, CharmOfLifeNecklaceModel::create);
   }

   public static void registerCurioRenderers(FMLClientSetupEvent event) {
      event.enqueueWork(
         () -> {
            CuriosRendererRegistry.register(
               (Item)TFItems.CHARM_OF_LIFE_1.get(), () -> new CharmOfLifeNecklaceRenderer(ARGB32.colorFromFloat(1.0F, 1.0F, 0.5F, 0.5F))
            );
            CuriosRendererRegistry.register(
               (Item)TFItems.CHARM_OF_LIFE_2.get(), () -> new CharmOfLifeNecklaceRenderer(ARGB32.colorFromFloat(1.0F, 1.0F, 0.9F, 0.0F))
            );
            CuriosRendererRegistry.register((Item)TFItems.CHARM_OF_KEEPING_1.get(), CharmOfKeepingRenderer::new);
            CuriosRendererRegistry.register((Item)TFItems.CHARM_OF_KEEPING_2.get(), CharmOfKeepingRenderer::new);
            CuriosRendererRegistry.register((Item)TFItems.CHARM_OF_KEEPING_3.get(), CharmOfKeepingRenderer::new);
            CuriosRendererRegistry.register((Item)TFItems.NAGA_TROPHY.get(), CurioHeadRenderer::new);
            CuriosRendererRegistry.register((Item)TFItems.LICH_TROPHY.get(), CurioHeadRenderer::new);
            CuriosRendererRegistry.register((Item)TFItems.MINOSHROOM_TROPHY.get(), CurioHeadRenderer::new);
            CuriosRendererRegistry.register((Item)TFItems.HYDRA_TROPHY.get(), CurioHeadRenderer::new);
            CuriosRendererRegistry.register((Item)TFItems.KNIGHT_PHANTOM_TROPHY.get(), CurioHeadRenderer::new);
            CuriosRendererRegistry.register((Item)TFItems.UR_GHAST_TROPHY.get(), CurioHeadRenderer::new);
            CuriosRendererRegistry.register((Item)TFItems.ALPHA_YETI_TROPHY.get(), CurioHeadRenderer::new);
            CuriosRendererRegistry.register((Item)TFItems.SNOW_QUEEN_TROPHY.get(), CurioHeadRenderer::new);
            CuriosRendererRegistry.register((Item)TFItems.QUEST_RAM_TROPHY.get(), CurioHeadRenderer::new);
            CuriosRendererRegistry.register(((Block)TFBlocks.CICADA.get()).asItem(), CurioHeadRenderer::new);
            CuriosRendererRegistry.register(((Block)TFBlocks.FIREFLY.get()).asItem(), CurioHeadRenderer::new);
            CuriosRendererRegistry.register(((Block)TFBlocks.MOONWORM.get()).asItem(), CurioHeadRenderer::new);
            CuriosRendererRegistry.register((Item)TFItems.CREEPER_SKULL_CANDLE.get(), CurioHeadRenderer::new);
            CuriosRendererRegistry.register((Item)TFItems.PIGLIN_SKULL_CANDLE.get(), CurioHeadRenderer::new);
            CuriosRendererRegistry.register((Item)TFItems.PLAYER_SKULL_CANDLE.get(), CurioHeadRenderer::new);
            CuriosRendererRegistry.register((Item)TFItems.SKELETON_SKULL_CANDLE.get(), CurioHeadRenderer::new);
            CuriosRendererRegistry.register((Item)TFItems.WITHER_SKELETON_SKULL_CANDLE.get(), CurioHeadRenderer::new);
            CuriosRendererRegistry.register((Item)TFItems.ZOMBIE_SKULL_CANDLE.get(), CurioHeadRenderer::new);
         }
      );
   }

   public static boolean isCurioEquipped(LivingEntity entity, Predicate<ItemStack> stackPredicate) {
      return CuriosApi.getCuriosInventory(entity).flatMap(handler -> handler.findFirstCurio(stackPredicate)).isPresent();
   }

   public static boolean isCurioEquippedAndVisible(LivingEntity entity, Predicate<ItemStack> stackPredicate) {
      Optional<SlotResult> slot = CuriosApi.getCuriosInventory(entity).flatMap(handler -> handler.findFirstCurio(stackPredicate));
      return slot.isPresent() && slot.get().slotContext() != null && slot.get().slotContext().visible();
   }

   public static boolean findAndConsumeCurio(Item item, Player player) {
      Optional<SlotResult> slot = CuriosApi.getCuriosInventory(player).flatMap(handler -> handler.findFirstCurio(item));
      if (slot.isPresent()) {
         CharmEvents.getPlayerData(player).put("CharmStack", slot.get().stack().save(player.registryAccess()));
         slot.get().stack().shrink(1);
         return true;
      } else {
         return false;
      }
   }
}
