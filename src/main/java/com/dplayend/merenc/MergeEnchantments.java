package com.dplayend.merenc;

import com.dplayend.merenc.handler.HandlerConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MergeEnchantments implements ModInitializer {
    public static final String MOD_ID = "merenc";
    public static final String MOD_NAME = "merge_enchantments";
    public static Logger LOGGER = LogManager.getFormatterLogger(MOD_NAME);
    public static HandlerConfig CONFIG;
    public static MergeEnchantments instance;

    @Override
    public void onInitialize() {
        loadConfig();
        instance = this;
    }

    public void loadConfig() {
        File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), MOD_NAME + "-common.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if (configFile.exists()) {
            try {
                FileReader fileReader = new FileReader(configFile);
                CONFIG = gson.fromJson(fileReader, HandlerConfig.class);
                fileReader.close();
            } catch (IOException e) {
                LOGGER.warn("could not load config options: " + e.getLocalizedMessage());
            }
        } else {
            CONFIG = new HandlerConfig();
            saveConfig();
        }
    }

    public void saveConfig() {
        File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), MOD_NAME + "-common.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if (!configFile.getParentFile().exists()) {
            configFile.getParentFile().mkdir();
        }
        try {
            FileWriter fileWriter = new FileWriter(configFile);
            fileWriter.write(gson.toJson(CONFIG));
            fileWriter.close();
        } catch (IOException e) {
            LOGGER.warn("could not save config options: " + e.getLocalizedMessage());
        }
    }
}
