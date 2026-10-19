package it.hurts.sskirillss.relics.items.relics.base.data.leveling;

import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionTextures;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.GemColor;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.GemShape;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import java.util.function.Function;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;

public class LevelingSourceData {
   private final String id;
   private int initialValue;
   private Pair<UpgradeOperation, Integer> upgradeModifier;
   private int maxLevel;
   private int cost;
   private int requiredLevel;
   private String requiredAbility;
   private Function<ItemStack, ResourceLocation> icon;
   private Function<ItemStack, String> translationPath;
   private GemShape shape;
   private GemColor color;

   private static LevelingSourceData.LevelingSourceDataBuilder builder(String id) {
      LevelingSourceData.LevelingSourceDataBuilder builder = new LevelingSourceData.LevelingSourceDataBuilder();
      builder.id(id);
      return builder;
   }

   public static LevelingSourceData.LevelingSourceDataBuilder genericBuilder(String id) {
      LevelingSourceData.LevelingSourceDataBuilder builder = builder(id);
      builder.translationPath(stack -> "tooltip.relics.leveling_source.generic." + id);
      builder.genericIcon(id);
      return builder;
   }

   public static LevelingSourceData.LevelingSourceDataBuilder abilityBuilder(String id, String ability) {
      LevelingSourceData.LevelingSourceDataBuilder builder = builder(id);
      builder.requiredAbility(ability);
      builder.abilityIcon(ability);
      builder.translationPath(stack -> "tooltip.relics." + BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath() + ".leveling_source." + id);
      return builder;
   }

   public static LevelingSourceData.LevelingSourceDataBuilder abilityBuilder(String ability) {
      return abilityBuilder(ability, ability);
   }

   private static int $default$initialValue() {
      return 1;
   }

   private static int $default$maxLevel() {
      return 0;
   }

   private static int $default$cost() {
      return 0;
   }

   private static int $default$requiredLevel() {
      return 0;
   }

   private static String $default$requiredAbility() {
      return "";
   }

   private static GemShape $default$shape() {
      return GemShape.SQUARE;
   }

   private static GemColor $default$color() {
      return GemColor.RED;
   }

   LevelingSourceData(
      String id,
      int initialValue,
      Pair<UpgradeOperation, Integer> upgradeModifier,
      int maxLevel,
      int cost,
      int requiredLevel,
      String requiredAbility,
      Function<ItemStack, ResourceLocation> icon,
      Function<ItemStack, String> translationPath,
      GemShape shape,
      GemColor color
   ) {
      this.id = id;
      this.initialValue = initialValue;
      this.upgradeModifier = upgradeModifier;
      this.maxLevel = maxLevel;
      this.cost = cost;
      this.requiredLevel = requiredLevel;
      this.requiredAbility = requiredAbility;
      this.icon = icon;
      this.translationPath = translationPath;
      this.shape = shape;
      this.color = color;
   }

   public String getId() {
      return this.id;
   }

   public int getInitialValue() {
      return this.initialValue;
   }

   public Pair<UpgradeOperation, Integer> getUpgradeModifier() {
      return this.upgradeModifier;
   }

   public int getMaxLevel() {
      return this.maxLevel;
   }

   public int getCost() {
      return this.cost;
   }

   public int getRequiredLevel() {
      return this.requiredLevel;
   }

   public String getRequiredAbility() {
      return this.requiredAbility;
   }

   public Function<ItemStack, ResourceLocation> getIcon() {
      return this.icon;
   }

   public Function<ItemStack, String> getTranslationPath() {
      return this.translationPath;
   }

   public GemShape getShape() {
      return this.shape;
   }

   public GemColor getColor() {
      return this.color;
   }

   public void setInitialValue(int initialValue) {
      this.initialValue = initialValue;
   }

   public void setUpgradeModifier(Pair<UpgradeOperation, Integer> upgradeModifier) {
      this.upgradeModifier = upgradeModifier;
   }

   public void setMaxLevel(int maxLevel) {
      this.maxLevel = maxLevel;
   }

   public void setCost(int cost) {
      this.cost = cost;
   }

   public void setRequiredLevel(int requiredLevel) {
      this.requiredLevel = requiredLevel;
   }

   public void setRequiredAbility(String requiredAbility) {
      this.requiredAbility = requiredAbility;
   }

   public void setIcon(Function<ItemStack, ResourceLocation> icon) {
      this.icon = icon;
   }

   public void setTranslationPath(Function<ItemStack, String> translationPath) {
      this.translationPath = translationPath;
   }

   public void setShape(GemShape shape) {
      this.shape = shape;
   }

   public void setColor(GemColor color) {
      this.color = color;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof LevelingSourceData other)) {
         return false;
      } else {
         if (!other.canEqual(this)) {
            return false;
         }

         if (this.getInitialValue() != other.getInitialValue()) {
            return false;
         }

         if (this.getMaxLevel() != other.getMaxLevel()) {
            return false;
         }

         if (this.getCost() != other.getCost()) {
            return false;
         }

         if (this.getRequiredLevel() != other.getRequiredLevel()) {
            return false;
         }

         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$upgradeModifier = this.getUpgradeModifier();
            Object other$upgradeModifier = other.getUpgradeModifier();
            if (this$upgradeModifier == null ? other$upgradeModifier == null : this$upgradeModifier.equals(other$upgradeModifier)) {
               Object this$requiredAbility = this.getRequiredAbility();
               Object other$requiredAbility = other.getRequiredAbility();
               if (this$requiredAbility == null ? other$requiredAbility == null : this$requiredAbility.equals(other$requiredAbility)) {
                  Object this$icon = this.getIcon();
                  Object other$icon = other.getIcon();
                  if (this$icon == null ? other$icon == null : this$icon.equals(other$icon)) {
                     Object this$translationPath = this.getTranslationPath();
                     Object other$translationPath = other.getTranslationPath();
                     if (this$translationPath == null ? other$translationPath == null : this$translationPath.equals(other$translationPath)) {
                        Object this$shape = this.getShape();
                        Object other$shape = other.getShape();
                        if (this$shape == null ? other$shape == null : this$shape.equals(other$shape)) {
                           Object this$color = this.getColor();
                           Object other$color = other.getColor();
                           return this$color == null ? other$color == null : this$color.equals(other$color);
                        } else {
                           return false;
                        }
                     } else {
                        return false;
                     }
                  } else {
                     return false;
                  }
               } else {
                  return false;
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof LevelingSourceData;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + this.getInitialValue();
      result = result * 59 + this.getMaxLevel();
      result = result * 59 + this.getCost();
      result = result * 59 + this.getRequiredLevel();
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $upgradeModifier = this.getUpgradeModifier();
      result = result * 59 + ($upgradeModifier == null ? 43 : $upgradeModifier.hashCode());
      Object $requiredAbility = this.getRequiredAbility();
      result = result * 59 + ($requiredAbility == null ? 43 : $requiredAbility.hashCode());
      Object $icon = this.getIcon();
      result = result * 59 + ($icon == null ? 43 : $icon.hashCode());
      Object $translationPath = this.getTranslationPath();
      result = result * 59 + ($translationPath == null ? 43 : $translationPath.hashCode());
      Object $shape = this.getShape();
      result = result * 59 + ($shape == null ? 43 : $shape.hashCode());
      Object $color = this.getColor();
      return result * 59 + ($color == null ? 43 : $color.hashCode());
   }

   @Override
   public String toString() {
      return "LevelingSourceData(id="
         + this.getId()
         + ", initialValue="
         + this.getInitialValue()
         + ", upgradeModifier="
         + this.getUpgradeModifier()
         + ", maxLevel="
         + this.getMaxLevel()
         + ", cost="
         + this.getCost()
         + ", requiredLevel="
         + this.getRequiredLevel()
         + ", requiredAbility="
         + this.getRequiredAbility()
         + ", icon="
         + this.getIcon()
         + ", translationPath="
         + this.getTranslationPath()
         + ", shape="
         + this.getShape()
         + ", color="
         + this.getColor()
         + ")";
   }

   public static class LevelingSourceDataBuilder {
      private String id;
      private boolean initialValue$set;
      private int initialValue$value;
      private boolean maxLevel$set;
      private int maxLevel$value;
      private boolean cost$set;
      private int cost$value;
      private boolean requiredLevel$set;
      private int requiredLevel$value;
      private boolean requiredAbility$set;
      private String requiredAbility$value;
      private boolean shape$set;
      private GemShape shape$value;
      private boolean color$set;
      private GemColor color$value;
      private Function<ItemStack, ResourceLocation> icon = stack -> ResourceLocation.fromNamespaceAndPath("relics", "textures/abilities/missing.png");
      private Function<ItemStack, String> translationPath = stack -> "";
      private Pair<UpgradeOperation, Integer> upgradeModifier = Pair.of(UpgradeOperation.ADD, 0);

      private LevelingSourceData.LevelingSourceDataBuilder id(String id) {
         this.id = id;
         return this;
      }

      public LevelingSourceData.LevelingSourceDataBuilder upgradeModifier(UpgradeOperation operation, int step) {
         this.upgradeModifier = Pair.of(operation, step);
         return this;
      }

      private LevelingSourceData.LevelingSourceDataBuilder icon(Function<ItemStack, ResourceLocation> icon) {
         this.icon = icon;
         return this;
      }

      public LevelingSourceData.LevelingSourceDataBuilder manualIcon(Function<ItemStack, ResourceLocation> icon) {
         return this.icon(icon);
      }

      public LevelingSourceData.LevelingSourceDataBuilder genericIcon(String icon) {
         return this.manualIcon(stack -> ResourceLocation.fromNamespaceAndPath("relics", "textures/leveling_source/generic/" + icon + ".png"));
      }

      public LevelingSourceData.LevelingSourceDataBuilder abilityIcon(String ability) {
         return this.manualIcon(stack -> DescriptionTextures.getAbilityCardTexture(stack, ability));
      }

      public LevelingSourceData.LevelingSourceDataBuilder gem(GemShape shape, GemColor color) {
         this.shape(shape);
         this.color(color);
         return this;
      }

      LevelingSourceDataBuilder() {
      }

      public LevelingSourceData.LevelingSourceDataBuilder initialValue(int initialValue) {
         this.initialValue$value = initialValue;
         this.initialValue$set = true;
         return this;
      }

      public LevelingSourceData.LevelingSourceDataBuilder maxLevel(int maxLevel) {
         this.maxLevel$value = maxLevel;
         this.maxLevel$set = true;
         return this;
      }

      public LevelingSourceData.LevelingSourceDataBuilder cost(int cost) {
         this.cost$value = cost;
         this.cost$set = true;
         return this;
      }

      public LevelingSourceData.LevelingSourceDataBuilder requiredLevel(int requiredLevel) {
         this.requiredLevel$value = requiredLevel;
         this.requiredLevel$set = true;
         return this;
      }

      public LevelingSourceData.LevelingSourceDataBuilder requiredAbility(String requiredAbility) {
         this.requiredAbility$value = requiredAbility;
         this.requiredAbility$set = true;
         return this;
      }

      public LevelingSourceData.LevelingSourceDataBuilder translationPath(Function<ItemStack, String> translationPath) {
         this.translationPath = translationPath;
         return this;
      }

      public LevelingSourceData.LevelingSourceDataBuilder shape(GemShape shape) {
         this.shape$value = shape;
         this.shape$set = true;
         return this;
      }

      public LevelingSourceData.LevelingSourceDataBuilder color(GemColor color) {
         this.color$value = color;
         this.color$set = true;
         return this;
      }

      public LevelingSourceData build() {
         int initialValue$value = this.initialValue$value;
         if (!this.initialValue$set) {
            initialValue$value = LevelingSourceData.$default$initialValue();
         }

         int maxLevel$value = this.maxLevel$value;
         if (!this.maxLevel$set) {
            maxLevel$value = LevelingSourceData.$default$maxLevel();
         }

         int cost$value = this.cost$value;
         if (!this.cost$set) {
            cost$value = LevelingSourceData.$default$cost();
         }

         int requiredLevel$value = this.requiredLevel$value;
         if (!this.requiredLevel$set) {
            requiredLevel$value = LevelingSourceData.$default$requiredLevel();
         }

         String requiredAbility$value = this.requiredAbility$value;
         if (!this.requiredAbility$set) {
            requiredAbility$value = LevelingSourceData.$default$requiredAbility();
         }

         GemShape shape$value = this.shape$value;
         if (!this.shape$set) {
            shape$value = LevelingSourceData.$default$shape();
         }

         GemColor color$value = this.color$value;
         if (!this.color$set) {
            color$value = LevelingSourceData.$default$color();
         }

         return new LevelingSourceData(
            this.id,
            initialValue$value,
            this.upgradeModifier,
            maxLevel$value,
            cost$value,
            requiredLevel$value,
            requiredAbility$value,
            this.icon,
            this.translationPath,
            shape$value,
            color$value
         );
      }

      @Override
      public String toString() {
         return "LevelingSourceData.LevelingSourceDataBuilder(id="
            + this.id
            + ", initialValue$value="
            + this.initialValue$value
            + ", upgradeModifier="
            + this.upgradeModifier
            + ", maxLevel$value="
            + this.maxLevel$value
            + ", cost$value="
            + this.cost$value
            + ", requiredLevel$value="
            + this.requiredLevel$value
            + ", requiredAbility$value="
            + this.requiredAbility$value
            + ", icon="
            + this.icon
            + ", translationPath="
            + this.translationPath
            + ", shape$value="
            + this.shape$value
            + ", color$value="
            + this.color$value
            + ")";
      }
   }
}
