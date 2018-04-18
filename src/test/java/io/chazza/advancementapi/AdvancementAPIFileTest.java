package io.chazza.advancementapi;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.NamespacedKey;
import org.junit.After;
import org.junit.Test;

public class AdvancementAPIFileTest {
    @SuppressWarnings("deprecation")
    private static final NamespacedKey nsk = new NamespacedKey("tests", "id");
    private final File worldFile = new File("test-world");

    private AdvancementAPI underTest;

    @After
    public void tearDown() throws IOException {
        delete(worldFile);
    }

    private static void delete(File file) throws IOException {
        for (File childFile : file.listFiles()) {
            if (childFile.isDirectory()) {
                delete(childFile);
            } else {
                if (!childFile.delete()) {
                    throw new IOException();
                }
            }
        }
        if (!file.delete()) {
            throw new IOException();
        }
    }

    @Test
    public void testAdvancement_Save_THEN_SaveAdvancementToLocalFileDir() throws Exception {
        underTest = AdvancementAPI.builder(nsk).build();

        assertThat(underTest.save(worldFile), is(true));

        File advancement = new File(worldFile, "data/advancements/tests/id.json");
        assertThat(advancement.exists(), is(true));
        BufferedReader br = new BufferedReader(new FileReader(advancement));
        String line;
        while ((line = br.readLine()) != null) {
            assertThat(line.length(), not(0));
        }
        br.close();
    }

    @Test
    public void testAdvancement_SavedFileExists_THEN_OverrideFileProperly() throws Exception {
        underTest = AdvancementAPI.builder(nsk).build();

        File advancement = new File(worldFile, "data/advancements/tests/id.json");
        advancement.getParentFile().mkdirs();
        new FileWriter(advancement).close();

        assertThat(underTest.save(worldFile), is(true));

        BufferedReader br = new BufferedReader(new FileReader(advancement));
        String line;
        while ((line = br.readLine()) != null) {
            assertThat(line.length(), not(0));
        }
        br.close();
    }

    @Test
    public void testAdvancement_Delete_THEN_DeleteAdvancementFromLoveFileDir() {
        underTest = AdvancementAPI.builder(nsk).build();
        underTest.save(worldFile);

        assertThat(underTest.delete(worldFile), is(true));
        File advancement = new File(worldFile, "data/advancements/tests/id.json");
        assertThat(advancement.exists(), is(false));
    }

    @Test
    public void testAdvancement_ExternalDeletedFile_THEN_HandleNotExistingFileProperly() {
        underTest = AdvancementAPI.builder(nsk).build();
        underTest.save(worldFile);

        File advancement = new File(worldFile, "data/advancements/tests/id.json");
        advancement.delete();

        assertThat(underTest.delete(worldFile), is(false));
    }
}