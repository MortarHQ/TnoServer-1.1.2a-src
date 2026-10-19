package com.mafuyu404.oneenoughitem.client.gui.editor;

import com.mafuyu404.oneenoughitem.client.gui.components.DataDisplayWidget;
import com.mafuyu404.oneenoughitem.client.gui.components.ScrollablePanel;
import com.mafuyu404.oneenoughitem.client.gui.components.TagDisplayWidget;
import java.util.List;

public class PanelsLayoutHelper {
   private static final int ITEM_SIZE = 18;
   private static final int ITEM_SPACING = 2;
   private static final int CONTENT_OFFSET_Y = 2;
   private static final int ITEMS_PER_ROW = 10;
   private static final int TAG_COLS = 2;
   private static final int TAG_SPACING_X = 8;
   private static final int TAG_ROW_HEIGHT = 20;
   private static final int FOUR_ROWS_VISIBLE_HEIGHT = 80;

   public void rebuildPanels(
      ScrollablePanel matchPanel,
      ScrollablePanel resultPanel,
      List<DataDisplayWidget> matchItemWidgets,
      List<TagDisplayWidget> matchTagWidgets,
      DataDisplayWidget resultItemWidget,
      TagDisplayWidget resultTagWidget
   ) {
      matchPanel.clearWidgets();
      resultPanel.clearWidgets();
      int index = 0;

      for (DataDisplayWidget widget : matchItemWidgets) {
         int row = index / 10;
         int col = index % 10;
         int x = matchPanel.getX() + col * 20;
         int y = matchPanel.getY() + 2 + row * 20;
         widget.setPosition(x, y);
         matchPanel.addWidget(widget);
         index++;
      }

      int tagStartY = 2 + (matchItemWidgets.size() + 10 - 1) / 10 * 20 + 10;
      index = 0;

      for (TagDisplayWidget widget : matchTagWidgets) {
         int row = index / 2;
         int col = index % 2;
         int widgetW = widget.getWidth();
         int widgetH = widget.getHeight();
         int x = matchPanel.getX() + col * (widgetW + 8);
         int y = matchPanel.getY() + tagStartY + row * (widgetH + 4);
         widget.setPosition(x, y);
         matchPanel.addWidget(widget);
         index++;
      }

      if (resultItemWidget != null) {
         resultItemWidget.setPosition(resultPanel.getX() + resultPanel.getWidth() / 2 - 9, resultPanel.getY() + 28);
         resultPanel.addWidget(resultItemWidget);
      }

      if (resultTagWidget != null) {
         resultTagWidget.setPosition(resultPanel.getX() + resultPanel.getWidth() / 2 - 35, resultPanel.getY() + 28);
         resultPanel.addWidget(resultTagWidget);
      }

      int visibleHeight = getVisibleHeight(matchItemWidgets, matchTagWidgets);
      matchPanel.setVisibleHeight(visibleHeight);
      matchPanel.updateWidgetPositions();
      resultPanel.updateWidgetPositions();
   }

   private static int getVisibleHeight(List<DataDisplayWidget> matchItemWidgets, List<TagDisplayWidget> matchTagWidgets) {
      int itemsRows = (matchItemWidgets.size() + 10 - 1) / 10;
      int itemsBottom = itemsRows > 0 ? 2 + (itemsRows - 1) * 20 + 18 : 0;
      int tagsBottom = 0;
      int tagRows = (matchTagWidgets.size() + 2 - 1) / 2;
      if (tagRows > 0) {
         int tagStart = 2 + itemsRows * 20 + 10;
         tagsBottom = tagStart + (tagRows - 1) * 24 + 20;
      }

      int contentHeight = Math.max(itemsBottom, tagsBottom);
      return Math.min(contentHeight, 80);
   }
}
