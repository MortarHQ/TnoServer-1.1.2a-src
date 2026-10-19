package fzzyhmstrs.emi_loot.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.SlotWidget;
import dev.emi.emi.api.widget.WidgetHolder;
import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.EMILootAgnos;
import fzzyhmstrs.emi_loot.EMILootClientAgnos;
import fzzyhmstrs.emi_loot.client.ClientBuiltPool;
import fzzyhmstrs.emi_loot.client.ClientMobLootTable;
import fzzyhmstrs.emi_loot.client.ClientResourceData;
import fzzyhmstrs.emi_loot.util.ConditionalStack;
import fzzyhmstrs.emi_loot.util.FloatTrimmer;
import fzzyhmstrs.emi_loot.util.IconGroupEmiWidget;
import fzzyhmstrs.emi_loot.util.LText;
import fzzyhmstrs.emi_loot.util.SymbolText;
import fzzyhmstrs.emi_loot.util.TrimmedTitle;
import fzzyhmstrs.emi_loot.util.WidgetRowBuilder;
import fzzyhmstrs.emi_loot.util.stack.EntityEmiStack;
import fzzyhmstrs.emi_loot.util.stack.MobLootEmiStack;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import me.fzzyhmstrs.fzzy_config.util.FcText;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

public class MobLootRecipe implements EmiRecipe {
   private static final ResourceLocation ARROW_ID = ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/downturn_arrow.png");
   private final ResourceLocation lootId;
   private final EmiStack inputStack;
   private final List<EmiStack> outputStacks;
   private final TrimmedTitle name;
   private final EntityType<?> type;
   @Nullable
   private final EmiStack egg;
   private final MobLootEmiStack lootStack;
   private final List<WidgetRowBuilder> rowBuilderList = new ArrayList<>();

   public MobLootRecipe(MobLootRecipe.MobLootRecipeData data) {
      this.lootId = data.loot.id;
      if (data.entity != null) {
         this.inputStack = EntityEmiStack.ofScaled(data.entity, data.scale);
      } else {
         this.inputStack = EmiStack.EMPTY;
      }

      List<EmiStack> list = new ArrayList<>();
      data.loot.builtItems.forEach(builtPool -> {
         builtPool.stacks().forEach(cs -> list.addAll(cs.getStacks()));

         try {
            this.addWidgetBuilders(builtPool, false);
         } catch (Throwable e) {
            EMILoot.LOGGER.error("Error encountered while preparing layout for mob recipe {}, display may be incomplete.", data.loot.id);
            EMILoot.LOGGER.error("Thrown error:", e);
         }
      });
      this.outputStacks = list;
      this.egg = data.spawnEggItem != null ? EmiStack.of(data.spawnEggItem) : null;
      this.lootStack = new MobLootEmiStack(this.lootId);
      this.name = data.name;
      this.type = data.entity != null ? data.entity.getType() : null;
   }

   private void addWidgetBuilders(ClientBuiltPool newPool, boolean recursive) {
      if (recursive || this.rowBuilderList.isEmpty()) {
         this.rowBuilderList.add(new WidgetRowBuilder(154));
      }

      for (WidgetRowBuilder builder : this.rowBuilderList) {
         if (builder.canAddPool(newPool)) {
            builder.addAndTrim(newPool);
            return;
         }
      }

      Optional<ClientBuiltPool> opt = this.rowBuilderList.get(this.rowBuilderList.size() - 1).addAndTrim(newPool);
      if (!opt.isEmpty()) {
         this.addWidgetBuilders(opt.get(), true);
      }
   }

   public EmiRecipeCategory getCategory() {
      return EmiClientPlugin.MOB_CATEGORY;
   }

   @Nullable
   public ResourceLocation getId() {
      return ResourceLocation.fromNamespaceAndPath(
         "emi_loot", "/" + this.getCategory().id.getPath() + "/" + this.lootId.getNamespace() + "/" + this.lootId.getPath()
      );
   }

   public List<EmiIngredient> getInputs() {
      return this.egg != null ? List.of(this.egg) : Collections.emptyList();
   }

   public List<EmiIngredient> getCatalysts() {
      return super.getCatalysts();
   }

   public List<EmiStack> getOutputs() {
      return this.outputStacks;
   }

   public int getDisplayWidth() {
      return EMILoot.config.isTooltipStyle() ? 144 : 154;
   }

   public int getDisplayHeight() {
      if (!EMILoot.config.isTooltipStyle()) {
         return this.rowBuilderList.size() <= 1 && this.rowBuilderList.get(0).getWidth() <= 94 ? 34 : 51 + 29 * (this.rowBuilderList.size() - 1);
      }

      int stacks = this.outputStacks.size();
      if (stacks <= 4) {
         return 29;
      }

      if (!EMILoot.config.isCompact(EMILoot.Type.MOB)) {
         return 29 + 18 * ((stacks - 5) / 8 + 1);
      }

      int ingredients = 0;

      for (WidgetRowBuilder builder : this.rowBuilderList) {
         ingredients += builder.ingredientCount();
      }

      return ingredients <= 4 ? 29 : 29 + 18 * ((ingredients - 5) / 8 + 1);
   }

   public void addWidgets(WidgetHolder widgets) {
      int x = 0;
      int y = 0;
      if (!ClientResourceData.MOB_OFFSETS.containsKey(this.type)) {
         widgets.addSlot(this.inputStack, x, y).large(true);
      } else {
         int offset = ClientResourceData.MOB_OFFSETS.getOrDefault(this.type, 0);
         widgets.addTexture(EmiTexture.LARGE_SLOT, x, y);
         widgets.addDrawable(x, y, 16, 16, (matrices, mx, my, delta) -> this.inputStack.render(matrices, 5, 6 + offset, delta));
         widgets.addTooltip(this.inputStack.getTooltip(), x, y, 24, 24);
      }

      if (this.egg == null) {
         widgets.addText(this.name.title(), 30, 0, 4210752, false);
         if (this.name.trimmed()) {
            widgets.addTooltipText(List.of(this.name.rawTitle()), 30, 0, EMILoot.config.isTooltipStyle() ? 108 : 118, 10);
         }
      } else {
         widgets.addText(this.name.title(), 49, 0, 4210752, false);
         if (this.name.trimmed()) {
            widgets.addTooltipText(List.of(this.name.rawTitle()), 30, 0, EMILoot.config.isTooltipStyle() ? 89 : 99, 10);
         }

         widgets.addSlot(this.egg, 28, 0);
      }

      if (EMILoot.config.isTooltipStyle()) {
         if (this.egg == null) {
            widgets.addTexture(new EmiTexture(ARROW_ID, 0, 16, 28, 15, 28, 15, 64, 32), 30, 10);
         } else {
            widgets.addTexture(new EmiTexture(ARROW_ID, 32, 16, 28, 15, 28, 15, 64, 32), 28, 15);
         }

         List<ConditionalStack> stacks = this.outputStacks.size() > 4 && EMILoot.config.isCompact(EMILoot.Type.MOB)
            ? this.rowBuilderList.stream().map(WidgetRowBuilder::ingredients).collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll)
            : this.rowBuilderList.stream().map(WidgetRowBuilder::stacks).collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll);
         int i = 4;
         int j = 0;

         for (ConditionalStack stack : stacks) {
            SlotWidget widget = widgets.addSlot(stack.getIngredient(), i * 18, 11 + 18 * j);
            String rounded = FloatTrimmer.trimFloatString(Math.max(stack.weight() / 100.0F, 0.01F), (Integer)EMILoot.config.chanceDecimalPlaces.get());
            widget.appendTooltip(FcText.INSTANCE.translatable("emi_loot.rolls", new Object[]{rounded}).withStyle(ChatFormatting.GRAY));
            if (EMILoot.config.isNotPlain()) {
               for (Tuple<Integer, Component> pair : stack.conditions()) {
                  widget.appendTooltip(SymbolText.of((Integer)pair.getA(), (Component)pair.getB()));
               }
            }

            if (++i > 7) {
               i = 0;
               j++;
            }
         }
      } else if (this.rowBuilderList.size() == 1 && this.rowBuilderList.get(0).getWidth() <= 94) {
         if (this.egg == null) {
            widgets.addTexture(new EmiTexture(ARROW_ID, 0, 16, 27, 15, 27, 15, 64, 32), 30, 10);
         } else {
            widgets.addTexture(new EmiTexture(ARROW_ID, 32, 16, 28, 15, 28, 15, 64, 32), 28, 15);
         }

         x = 60;
         int var15 = 11;
         WidgetRowBuilder builder = this.rowBuilderList.get(0);

         for (ClientBuiltPool pool : builder.getPoolList()) {
            IconGroupEmiWidget widget = EMILootClientAgnos.createIconGroupEmiWidget(x, var15, pool, this);
            widgets.add(widget);
            x += widget.getWidth() + 6;
         }
      } else {
         if (this.egg == null) {
            widgets.addTexture(new EmiTexture(ARROW_ID, 0, 0, 39, 15, 39, 15, 64, 32), 30, 10);
         } else {
            widgets.addTexture(new EmiTexture(ARROW_ID, 0, 0, 39, 15, 39, 15, 64, 32), 49, 10);
         }

         y += 28;

         for (WidgetRowBuilder builder : this.rowBuilderList) {
            for (ClientBuiltPool pool : builder.getPoolList()) {
               IconGroupEmiWidget widget = EMILootClientAgnos.createIconGroupEmiWidget(x, y, pool, this);
               widgets.add(widget);
               x += widget.getWidth() + 6;
            }

            y += 29;
            x = 0;
         }
      }
   }

   public boolean supportsRecipeTree() {
      return false;
   }

   public boolean hideCraftable() {
      return super.hideCraftable();
   }

   public record MobLootRecipeData(ClientMobLootTable loot, SpawnEggItem spawnEggItem, Entity entity, double scale, TrimmedTitle name) {
      public static MobLootRecipe.MobLootRecipeData of(ClientMobLootTable loot) {
         loot.build(Minecraft.getInstance().level, Blocks.AIR);
         ResourceLocation mobId = loot.mobId;
         EntityType<?> type = (EntityType<?>)BuiltInRegistries.ENTITY_TYPE.get(mobId);
         SpawnEggItem eggItem = SpawnEggItem.byId(type);
         if (eggItem == null) {
            eggItem = EMILootAgnos.getSpawnEgg(type);
         }

         Minecraft client = Minecraft.getInstance();
         Entity entity = null;

         try {
            entity = type.create(client.level);
         } catch (Exception e) {
            EMILoot.LOGGER.error("Encountered critical exception trying to initialize entity of type: " + type.getDescription().getString(), e);
         }

         double scale = 0.125;
         Component rawTitle;
         if (entity != null) {
            AABB box = entity.getBoundingBox();
            double len = box.getSize();
            if (len > 1.05) {
               len = (len + Math.sqrt(len)) / 2.0;
            }

            if (entity instanceof Slime) {
               ((Slime)entity).setSize(5, false);
            }

            if (entity instanceof Sheep && !Objects.equals(loot.color, "")) {
               DyeColor color = DyeColor.byName(loot.color, DyeColor.WHITE);
               MutableComponent colorName = LText.translatable("color.minecraft." + color.getName());
               rawTitle = LText.translatable("emi_loot.color_name", colorName, entity.getName());
               ((Sheep)entity).setColor(color);
            } else {
               rawTitle = entity.getName();
            }

            scale = 1.05 / len * 8.0;
            if (ClientResourceData.MOB_SCALES.containsKey(type)) {
               scale *= ClientResourceData.MOB_SCALES.getOrDefault(type, 1.0F);
            }
         } else {
            rawTitle = LText.translatable("emi_loot.missing_entity");
         }

         TrimmedTitle name = TrimmedTitle.of(rawTitle, (EMILoot.config.isTooltipStyle() ? 138 : 158) - (eggItem != null ? 49 : 30));
         return new MobLootRecipe.MobLootRecipeData(loot, eggItem, entity, scale, name);
      }
   }
}
