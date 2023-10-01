package com.dplayend.merenc.handler;

import com.dplayend.merenc.MergeEnchantments;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class HandlerClothConfig {
    HandlerConfig config = MergeEnchantments.CONFIG;

    public Screen getConfigScreen(Screen parent, boolean isTransparent) {
        ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parent).setTitle(Text.translatable("text." + MergeEnchantments.MOD_ID + ".config.title"));
        builder.setDefaultBackgroundTexture(new Identifier("minecraft:textures/block/dirt.png"));
        builder.setSavingRunnable(() -> MergeEnchantments.instance.saveConfig());
        ConfigCategory general = builder.getOrCreateCategory(Text.of("general"));
        ConfigEntryBuilder configEntryBuilder = builder.entryBuilder();

        general.addEntry(configEntryBuilder
                .startStrList(Text.translatable("text." + MergeEnchantments.MOD_ID + ".config.blackList"), config.blackList)
                .setDefaultValue(HandlerConfig.dv_blackList)
                .setSaveConsumer(newValue -> config.blackList = newValue)
                .setTooltip(Text.translatable("text." + MergeEnchantments.MOD_ID + ".config.blackList.tooltip"))
                .build()
        );

        return builder.setTransparentBackground(isTransparent).build();
    }
}
