package com.nododiiiii.ponderer.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReferenceUrlManager implements SnapshotParticipant {
   private final DynamicListState<ReferenceUrlManager.ReferenceUrl> referenceUrls = new DynamicListState<>(
      "reference_url",
      new DynamicListState.ItemCodec<ReferenceUrlManager.ReferenceUrl>() {
         public void snapshot(String prefix, ReferenceUrlManager.ReferenceUrl item, Map<String, String> snapshot) {
            snapshot.put(prefix + "_value", item.url());
            snapshot.put(prefix + "_auto", String.valueOf(item.isAutoAdded()));
         }

         public ReferenceUrlManager.ReferenceUrl restore(String prefix, Map<String, String> snapshot) {
            return new ReferenceUrlManager.ReferenceUrl(
               snapshot.getOrDefault(prefix + "_value", ""), Boolean.parseBoolean(snapshot.getOrDefault(prefix + "_auto", "false"))
            );
         }
      },
      () -> new ReferenceUrlManager.ReferenceUrl("", false),
      0
   );

   public void addUrl(String url, String itemId, boolean isAutoAdded) {
      this.removeAutoUrlsForItem();
      List<ReferenceUrlManager.ReferenceUrl> values = new ArrayList<>(this.referenceUrls.items());
      values.add(0, new ReferenceUrlManager.ReferenceUrl(url, isAutoAdded));
      this.referenceUrls.replaceAll(values);
   }

   public void removeAutoUrlsForItem() {
      List<ReferenceUrlManager.ReferenceUrl> filtered = new ArrayList<>();

      for (ReferenceUrlManager.ReferenceUrl value : this.referenceUrls.items()) {
         if (!value.isAutoAdded()) {
            filtered.add(value);
         }
      }

      this.referenceUrls.replaceAll(filtered);
   }

   public void removeUrl(int index) {
      if (index >= 0 && index < this.referenceUrls.size()) {
         this.referenceUrls.remove(index);
      }
   }

   public void addManualUrl(String url) {
      this.referenceUrls.add(new ReferenceUrlManager.ReferenceUrl(url, false));
   }

   public void updateUrl(int index, String url) {
      if (index >= 0 && index < this.referenceUrls.size()) {
         ReferenceUrlManager.ReferenceUrl oldUrl = this.referenceUrls.get(index);
         this.referenceUrls.set(index, new ReferenceUrlManager.ReferenceUrl(url, oldUrl.isAutoAdded()));
      }
   }

   public List<String> getUrlValues() {
      List<String> urlValues = new ArrayList<>();

      for (ReferenceUrlManager.ReferenceUrl refUrl : this.referenceUrls.items()) {
         urlValues.add(refUrl.url());
      }

      return urlValues;
   }

   public List<Boolean> getUrlAutoAdded() {
      List<Boolean> autoAddedFlags = new ArrayList<>();

      for (ReferenceUrlManager.ReferenceUrl refUrl : this.referenceUrls.items()) {
         autoAddedFlags.add(refUrl.isAutoAdded());
      }

      return autoAddedFlags;
   }

   public void clear() {
      this.referenceUrls.clear();
   }

   public void replaceWith(List<String> urls, List<Boolean> autoAddedFlags) {
      List<ReferenceUrlManager.ReferenceUrl> values = new ArrayList<>();

      for (int i = 0; i < urls.size(); i++) {
         boolean isAutoAdded = i < autoAddedFlags.size() && autoAddedFlags.get(i);
         values.add(new ReferenceUrlManager.ReferenceUrl(urls.get(i), isAutoAdded));
      }

      this.referenceUrls.replaceAll(values);
   }

   @Override
   public void snapshot(Map<String, String> snapshot) {
      this.referenceUrls.snapshot(snapshot);
   }

   @Override
   public void restore(Map<String, String> snapshot) {
      this.referenceUrls.restore(snapshot);
   }

   public record ReferenceUrl(String url, boolean isAutoAdded) {
   }
}
