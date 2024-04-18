package club.someoneice.safety_backpack;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cpw.mods.fml.common.Loader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
public final class ConfigInventory {
    static final List<String> slotPosList = Lists.newArrayListWithExpectedSize(8);
    static final StringBuilder playerInventoryStartPos = new StringBuilder();

    static final File configPath = new File(Loader.instance().getConfigDir(), SafetyBackpack.NAME + ".json");

    static void configInit() throws IOException {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        dataInit();

        if (!configPath.exists() || !configPath.isFile()) {
            Files.createFile(configPath.toPath());

            final Map<String, Object> data = Maps.newHashMap();
            data.put("slotPos", slotPosList);
            data.put("inventoryPos", playerInventoryStartPos.toString());

            Files.write(configPath.toPath(), gson.toJson(data).getBytes());
            return;
        }

        String str = new String(Files.readAllBytes(configPath.toPath()));
        final Map<String, Object> data = Maps.newHashMap();
        data.putAll(gson.fromJson(str, new TypeToken<Map<String, Object>>() {}.getType()));

        if (data.containsKey("slotPos")) {
            slotPosList.clear();
            slotPosList.addAll((List<String>) data.get("slotPos"));
        }

        if (data.containsKey("inventoryPos")) {
            playerInventoryStartPos.setLength(0);
            playerInventoryStartPos.append(data.get("inventoryPos"));
        }
    }

    private static void dataInit() {
        slotPosList.add("52,6");
        slotPosList.add("70,6");
        slotPosList.add("88,6");
        slotPosList.add("106,6");

        slotPosList.add("52,24");
        slotPosList.add("70,24");
        slotPosList.add("88,24");
        slotPosList.add("106,24");

        playerInventoryStartPos.append("8,59");
    }
}
