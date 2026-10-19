package net.p3pp3rf1y.sophisticatedcore.client.gui.controls;

import java.util.Map;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TextureBlitData;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TranslationHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.UV;
import net.p3pp3rf1y.sophisticatedcore.common.gui.SortBy;
import net.p3pp3rf1y.sophisticatedcore.upgrades.PrimaryMatch;

public class ButtonDefinitions {
   public static final ButtonDefinition.Toggle<Boolean> ALLOW_LIST = createToggleButtonDefinition(
      getBooleanStateData(
         GuiHelper.getButtonStateData(new UV(0, 0), TranslationHelper.INSTANCE.translUpgradeButton("allow"), Dimension.SQUARE_16, new Position(1, 1)),
         GuiHelper.getButtonStateData(new UV(16, 0), TranslationHelper.INSTANCE.translUpgradeButton("block"), Dimension.SQUARE_16, new Position(1, 1))
      )
   );
   public static final ButtonDefinition.Toggle<Boolean> MATCH_DURABILITY = createToggleButtonDefinition(
      getBooleanStateData(
         GuiHelper.getButtonStateData(
            new UV(0, 16), TranslationHelper.INSTANCE.translUpgradeButton("match_durability"), Dimension.SQUARE_16, new Position(1, 1)
         ),
         GuiHelper.getButtonStateData(
            new UV(16, 16), TranslationHelper.INSTANCE.translUpgradeButton("ignore_durability"), Dimension.SQUARE_16, new Position(1, 1)
         )
      )
   );
   public static final ButtonDefinition.Toggle<Boolean> MATCH_NBT = createToggleButtonDefinition(
      getBooleanStateData(
         GuiHelper.getButtonStateData(new UV(32, 0), TranslationHelper.INSTANCE.translUpgradeButton("match_nbt"), Dimension.SQUARE_16, new Position(1, 1)),
         GuiHelper.getButtonStateData(new UV(48, 0), TranslationHelper.INSTANCE.translUpgradeButton("ignore_nbt"), Dimension.SQUARE_16, new Position(1, 1))
      )
   );
   public static final ButtonDefinition.Toggle<PrimaryMatch> PRIMARY_MATCH = createToggleButtonDefinition(
      Map.of(
         PrimaryMatch.ITEM,
         GuiHelper.getButtonStateData(new UV(48, 16), TranslationHelper.INSTANCE.translUpgradeButton("match_item"), Dimension.SQUARE_16, new Position(1, 1)),
         PrimaryMatch.MOD,
         GuiHelper.getButtonStateData(new UV(32, 16), TranslationHelper.INSTANCE.translUpgradeButton("match_mod"), Dimension.SQUARE_16, new Position(1, 1)),
         PrimaryMatch.TAGS,
         GuiHelper.getButtonStateData(new UV(64, 0), TranslationHelper.INSTANCE.translUpgradeButton("match_tags"), Dimension.SQUARE_16, new Position(1, 1))
      )
   );
   public static final ButtonDefinition.Toggle<SortBy> SORT_BY = createSmallToggleButtonDefinition(
      Map.of(
         SortBy.NAME,
         GuiHelper.getButtonStateData(new UV(24, 144), TranslationHelper.INSTANCE.translButton("sort_by_name"), Dimension.SQUARE_12),
         SortBy.MOD,
         GuiHelper.getButtonStateData(new UV(0, 156), TranslationHelper.INSTANCE.translButton("sort_by_mod"), Dimension.SQUARE_12),
         SortBy.COUNT,
         GuiHelper.getButtonStateData(new UV(36, 144), TranslationHelper.INSTANCE.translButton("sort_by_count"), Dimension.SQUARE_12),
         SortBy.TAGS,
         GuiHelper.getButtonStateData(new UV(12, 144), TranslationHelper.INSTANCE.translButton("sort_by_tags"), Dimension.SQUARE_12)
      )
   );
   private static final TextureBlitData TRANSFER_TO_STORAGE_FOREGROUND = new TextureBlitData(
      GuiHelper.ICONS, Dimension.SQUARE_256, new UV(0, 144), Dimension.SQUARE_12
   );
   public static final ButtonDefinition TRANSFER_TO_STORAGE = new ButtonDefinition(
      Dimension.SQUARE_12,
      GuiHelper.SMALL_BUTTON_BACKGROUND,
      GuiHelper.SMALL_BUTTON_HOVERED_BACKGROUND,
      TRANSFER_TO_STORAGE_FOREGROUND,
      Component.translatable(TranslationHelper.INSTANCE.translButton("transfer_to_storage"))
   );
   private static final TextureBlitData TRANSFER_TO_STORAGE_FILTERED_FOREGROUND = new TextureBlitData(
      GuiHelper.ICONS, Dimension.SQUARE_256, new UV(36, 156), Dimension.SQUARE_12
   );
   public static final ButtonDefinition TRANSFER_TO_STORAGE_FILTERED = new ButtonDefinition(
      Dimension.SQUARE_12,
      GuiHelper.SMALL_BUTTON_BACKGROUND,
      GuiHelper.SMALL_BUTTON_HOVERED_BACKGROUND,
      TRANSFER_TO_STORAGE_FILTERED_FOREGROUND,
      Component.translatable(TranslationHelper.INSTANCE.translButton("transfer_to_storage_filtered")),
      Component.translatable(TranslationHelper.INSTANCE.translButton("transfer_to_storage_filtered.transfer_all")).withStyle(ChatFormatting.DARK_GRAY)
   );
   private static final TextureBlitData TRANSFER_TO_INVENTORY_FOREGROUND = new TextureBlitData(
      GuiHelper.ICONS, Dimension.SQUARE_256, new UV(12, 156), Dimension.SQUARE_12
   );
   public static final ButtonDefinition TRANSFER_TO_INVENTORY = new ButtonDefinition(
      Dimension.SQUARE_12,
      GuiHelper.SMALL_BUTTON_BACKGROUND,
      GuiHelper.SMALL_BUTTON_HOVERED_BACKGROUND,
      TRANSFER_TO_INVENTORY_FOREGROUND,
      Component.translatable(TranslationHelper.INSTANCE.translButton("transfer_to_inventory"))
   );
   private static final TextureBlitData TRANSFER_TO_INVENTORY_FILTERED_FOREGROUND = new TextureBlitData(
      GuiHelper.ICONS, Dimension.SQUARE_256, new UV(24, 156), Dimension.SQUARE_12
   );
   public static final ButtonDefinition TRANSFER_TO_INVENTORY_FILTERED = new ButtonDefinition(
      Dimension.SQUARE_12,
      GuiHelper.SMALL_BUTTON_BACKGROUND,
      GuiHelper.SMALL_BUTTON_HOVERED_BACKGROUND,
      TRANSFER_TO_INVENTORY_FILTERED_FOREGROUND,
      Component.translatable(TranslationHelper.INSTANCE.translButton("transfer_to_inventory_filtered")),
      Component.translatable(TranslationHelper.INSTANCE.translButton("transfer_to_inventory_filtered.transfer_all")).withStyle(ChatFormatting.DARK_GRAY)
   );
   private static final TextureBlitData SORT_BUTTON_FOREGROUND = new TextureBlitData(GuiHelper.ICONS, Dimension.SQUARE_256, new UV(0, 144), Dimension.SQUARE_12);
   public static final ButtonDefinition SORT = new ButtonDefinition(
      Dimension.SQUARE_12,
      GuiHelper.SMALL_BUTTON_BACKGROUND,
      GuiHelper.SMALL_BUTTON_HOVERED_BACKGROUND,
      SORT_BUTTON_FOREGROUND,
      Component.translatable(TranslationHelper.INSTANCE.translButton("sort_action"))
   );
   private static final TextureBlitData UPGRADE_SWITCH_BACKGROUND = new TextureBlitData(
      GuiHelper.GUI_CONTROLS, Dimension.SQUARE_256, new UV(65, 0), Dimension.RECTANGLE_6_12
   );
   private static final TextureBlitData UPGRADE_SWITCH_HOVERED_BACKGROUND = new TextureBlitData(
      GuiHelper.GUI_CONTROLS, Dimension.SQUARE_256, new UV(71, 0), Dimension.RECTANGLE_6_12
   );
   public static final ButtonDefinition.Toggle<Boolean> UPGRADE_SWITCH = new ButtonDefinition.Toggle<>(
      Dimension.RECTANGLE_6_12,
      UPGRADE_SWITCH_BACKGROUND,
      Map.of(
         true,
         GuiHelper.getButtonStateData(
            new UV(4, 128),
            Dimension.RECTANGLE_4_10,
            new Position(1, 1),
            TranslationHelper.INSTANCE.translColoredButton("upgrade_switch_enabled", ChatFormatting.GREEN)
         ),
         false,
         GuiHelper.getButtonStateData(
            new UV(0, 128),
            Dimension.RECTANGLE_4_10,
            new Position(1, 1),
            TranslationHelper.INSTANCE.translColoredButton("upgrade_switch_disabled", ChatFormatting.RED)
         )
      ),
      UPGRADE_SWITCH_HOVERED_BACKGROUND
   );
   private static final TextureBlitData UPGRADE_SWITCH_INACTIVE_FOREGROUND = new TextureBlitData(
      GuiHelper.ICONS, new Position(1, 1), Dimension.SQUARE_256, new UV(8, 128), Dimension.RECTANGLE_4_10
   );
   public static final ButtonDefinition UPGRADE_SWITCH_INACTIVE = new ButtonDefinition(
      Dimension.RECTANGLE_6_12,
      UPGRADE_SWITCH_BACKGROUND,
      UPGRADE_SWITCH_BACKGROUND,
      UPGRADE_SWITCH_INACTIVE_FOREGROUND,
      TranslationHelper.INSTANCE.translColoredButton("upgrade_switch_inactive", ChatFormatting.GRAY)
   );
   public static final ButtonDefinition.Toggle<Boolean> WORK_IN_GUI = createToggleButtonDefinition(
      Map.of(
         true,
         GuiHelper.getButtonStateData(new UV(0, 48), TranslationHelper.INSTANCE.translUpgradeButton("works_in_gui"), Dimension.SQUARE_16, new Position(1, 1)),
         false,
         GuiHelper.getButtonStateData(new UV(16, 48), TranslationHelper.INSTANCE.translUpgradeButton("only_automatic"), Dimension.SQUARE_16, new Position(1, 1))
      )
   );
   public static final ButtonDefinition.Toggle<Boolean> MATCH_ANY_TAG = createToggleButtonDefinition(
      Map.of(
         true,
         GuiHelper.getButtonStateData(
            new UV(0, 80),
            Dimension.SQUARE_16,
            new Position(1, 1),
            TranslationHelper.INSTANCE.getTranslatedLines(TranslationHelper.INSTANCE.translUpgradeButton("match_any_tag"))
         ),
         false,
         GuiHelper.getButtonStateData(
            new UV(16, 80),
            Dimension.SQUARE_16,
            new Position(1, 1),
            TranslationHelper.INSTANCE.getTranslatedLines(TranslationHelper.INSTANCE.translUpgradeButton("match_all_tags"))
         )
      )
   );
   private static final TextureBlitData ADD_TAG_FOREGROUND = new TextureBlitData(
      GuiHelper.ICONS, new Position(1, 1), Dimension.SQUARE_256, new UV(96, 32), Dimension.SQUARE_16
   );
   public static final ButtonDefinition ADD_TAG = new ButtonDefinition(
      Dimension.SQUARE_18, GuiHelper.DEFAULT_BUTTON_BACKGROUND, GuiHelper.DEFAULT_BUTTON_HOVERED_BACKGROUND, ADD_TAG_FOREGROUND, Component.literal("")
   );
   private static final TextureBlitData REMOVE_TAG_FOREGROUND = new TextureBlitData(
      GuiHelper.ICONS, new Position(1, 1), Dimension.SQUARE_256, new UV(112, 32), Dimension.SQUARE_16
   );
   public static final ButtonDefinition REMOVE_TAG = new ButtonDefinition(
      Dimension.SQUARE_18, GuiHelper.DEFAULT_BUTTON_BACKGROUND, GuiHelper.DEFAULT_BUTTON_HOVERED_BACKGROUND, REMOVE_TAG_FOREGROUND, Component.literal("")
   );
   private static final TextureBlitData CONFIRM_BUTTON_FOREGROUND = new TextureBlitData(
      GuiHelper.ICONS, new Position(1, 1), Dimension.SQUARE_256, new UV(192, 64), Dimension.SQUARE_16
   );
   public static final ButtonDefinition CONFIRM = new ButtonDefinition(
      Dimension.SQUARE_16,
      GuiHelper.DEFAULT_BUTTON_BACKGROUND,
      GuiHelper.DEFAULT_BUTTON_HOVERED_BACKGROUND,
      CONFIRM_BUTTON_FOREGROUND,
      Component.translatable(TranslationHelper.INSTANCE.translButton("confirm"))
   );
   private static final TextureBlitData CANCEL_BUTTON_FOREGROUND = new TextureBlitData(
      GuiHelper.ICONS, new Position(1, 1), Dimension.SQUARE_256, new UV(208, 64), Dimension.SQUARE_16
   );
   public static final ButtonDefinition CANCEL = new ButtonDefinition(
      Dimension.SQUARE_16,
      GuiHelper.DEFAULT_BUTTON_BACKGROUND,
      GuiHelper.DEFAULT_BUTTON_HOVERED_BACKGROUND,
      CANCEL_BUTTON_FOREGROUND,
      Component.translatable(TranslationHelper.INSTANCE.translButton("cancel"))
   );
   private static final TextureBlitData TRANSPARENT_BUTTON_FOREGROUND = new TextureBlitData(
      GuiHelper.ICONS, new Position(1, 1), Dimension.SQUARE_256, new UV(224, 64), Dimension.SQUARE_16
   );
   public static final ButtonDefinition TRANSPARENT = new ButtonDefinition(
      Dimension.SQUARE_16,
      GuiHelper.DEFAULT_BUTTON_BACKGROUND,
      GuiHelper.DEFAULT_BUTTON_HOVERED_BACKGROUND,
      TRANSPARENT_BUTTON_FOREGROUND,
      Component.translatable(TranslationHelper.INSTANCE.translButton("transparent"))
   );

   private ButtonDefinitions() {
   }

   public static Map<Boolean, ToggleButton.StateData> getBooleanStateData(ToggleButton.StateData onStateData, ToggleButton.StateData offStateData) {
      return Map.of(true, onStateData, false, offStateData);
   }

   public static <T extends Comparable<T>> ButtonDefinition.Toggle<T> createToggleButtonDefinition(Map<T, ToggleButton.StateData> stateData) {
      return new ButtonDefinition.Toggle<>(Dimension.SQUARE_18, GuiHelper.DEFAULT_BUTTON_BACKGROUND, stateData, GuiHelper.DEFAULT_BUTTON_HOVERED_BACKGROUND);
   }

   public static <T extends Comparable<T>> ButtonDefinition.Toggle<T> createSmallToggleButtonDefinition(Map<T, ToggleButton.StateData> stateData) {
      return new ButtonDefinition.Toggle<>(Dimension.SQUARE_12, GuiHelper.SMALL_BUTTON_BACKGROUND, stateData, GuiHelper.SMALL_BUTTON_HOVERED_BACKGROUND);
   }
}
