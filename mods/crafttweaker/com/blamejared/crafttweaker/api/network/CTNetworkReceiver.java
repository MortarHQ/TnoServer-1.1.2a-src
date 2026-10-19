package com.blamejared.crafttweaker.api.network;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@FunctionalInterface
@ZenRegister
@ZenCodeType.Name("vanilla/api/network/NetworkReceiver")
@Document("vanilla/api/network/NetworkReceiver")
public interface CTNetworkReceiver {
   void receive(IData var1, CTNetworkContext var2);
}
