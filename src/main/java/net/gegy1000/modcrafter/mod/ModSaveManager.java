package net.gegy1000.modcrafter.mod;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.gegy1000.modcrafter.ModCrafter;
import net.gegy1000.modcrafter.ModCrafterAPI;
import net.gegy1000.modcrafter.json.JsonMod;
import net.gegy1000.modcrafter.mod.sprite.Sprite;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.*;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ModSaveManager
{
    public static final String modFormat = ".mcmod";
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void saveMod(Mod mod) throws IOException
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        File file = new File(ModCrafter.proxy.getModsFile(), "modcrafter/" + mod.getFileName() + modFormat);

        if (!file.exists())
        {
            file.createNewFile();
        }

        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(file));
        ZipEntry entry = new ZipEntry("mod.json");
        out.putNextEntry(entry);

        JsonMod jsonMod = new JsonMod(mod);
        jsonMod.lastModified = System.currentTimeMillis();

        byte[] data = gson.toJson(jsonMod, JsonMod.class).getBytes();
        out.write(data, 0, data.length);

        out.closeEntry();
        out.close();
    }

    private static void makeTestProject(Mod mod)
    {
        mod.getSprites().clear();

        for (int i = 0; i < 12; i++)
        {
            Sprite sprite = new Sprite(ModCrafterAPI.getSpriteById("sprite_item"), mod, "Test Sprite" + RandomStringUtils.randomAscii(5));

            mod.addSprite(sprite);
        }
    }

    public static List<Mod> discoverMods()
    {
        List<Mod> mods = Lists.newArrayList();

        File modsDirectory = new File(ModCrafter.proxy.getModsFile(), "modcrafter");

        try
        {
            for (File mod : modsDirectory.listFiles())
            {
                if (!mod.isDirectory() && mod.getName().endsWith(modFormat))
                {
                    File tempFile = File.createTempFile("tempmod", modFormat);
                    tempFile.deleteOnExit();

                    try (FileOutputStream out = new FileOutputStream(tempFile))
                    {
                        IOUtils.copy(new FileInputStream(mod), out);
                    }

                    ZipFile zipFile = new ZipFile(tempFile);
                    Enumeration<? extends ZipEntry> entries = zipFile.entries();

                    while (entries.hasMoreElements())
                    {
                        ZipEntry entry = entries.nextElement();

                        if (entry.getName().equals("mod.json"))
                        {
                            InputStream stream = zipFile.getInputStream(entry);

                            JsonMod jsonMod = gson.fromJson(new InputStreamReader(stream), JsonMod.class);

                            mods.add(jsonMod.toMod(mod.getName().split(modFormat)[0]));
                        }
                    }

                    zipFile.close();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Collections.sort(mods);

        return mods;
    }
}
