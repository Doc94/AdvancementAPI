package io.chazza.advancementapi;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.google.gson.Gson;

import net.md_5.bungee.api.chat.TextComponent;

public class DisplayTest {
    private static final Gson gson = new Gson();
    private Display underTest;

    @Test
    public void testDisplay_GIVEN_TitleDescIcon_THEN_ExpectJsonToBeWithTitleDescIcon() {
        underTest = Display.builder("Stew", "", "minecraft:mushroom_stew").build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"title\":{\"text\":\"Stew\"},\"description\":\"\",\"icon\":"
                + "{\"item\":\"minecraft:mushroom_stew\"}}"));
    }

    @Test
    public void testDisplay_GIVEN_TitleTextComponent_THEN_ExpectJsonToBeWithTextComponent() {
        underTest = Display.builder("", "", "").title(new TextComponent("Hello")).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"title\":{\"text\":\"Hello\"},\"description\":\"\",\"icon\":{\"item\":\"\"}}"));
    }

    @Test
    public void testDisplay_GIVEN_DescTextComponent_THEN_ExpectJsonToBeWithTextComponent() {
        TextComponent text = new TextComponent("Wool");
        text.setBold(true);
        underTest = Display.builder("", "", "").description(text).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json,
                is("{\"title\":\"\",\"description\":{\"bold\":true,\"text\":\"Wool\"},\"icon\":{\"item\":\"\"}}"));
    }

    @Test
    public void testDisplay_GIVEN_Icon_THEN_ExpectJsonToBeWithIcon() {
        underTest = Display.builder("", "", "").icon("minecraft:wood").build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"title\":\"\",\"description\":\"\",\"icon\":{\"item\":\"minecraft:wood\"}}"));
    }

    @Test
    public void testDisplay_GIVEN_Background_THEN_ExpectJsonToBeWithBackground() {
        underTest = Display.builder("", "", "").background("minecraft:textures/blocks/gold_block.png").build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"title\":\"\",\"description\":\"\",\"icon\":{\"item\":\"\"},\"background\":"
                + "\"minecraft:textures/blocks/gold_block.png\"}"));
    }

    @Test
    public void testDisplay_GIVEN_Frame_THEN_ExpectJsonToBeWithFrame() {
        underTest = Display.builder("", "", "").frame(FrameType.GOAL).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"title\":\"\",\"description\":\"\",\"icon\":{\"item\":\"\"},\"frame\":\"goal\"}"));
    }

    @Test
    public void testDisplay_GIVEN_DontShowToast_THEN_ExpectJsonToBeWithFalse() {
        underTest = Display.builder("", "", "").toast(false).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"title\":\"\",\"description\":\"\",\"icon\":{\"item\":\"\"},\"show_toast\":false}"));
    }

    @Test
    public void testDisplay_GIVEN_DontAnnounce_THEN_ExpectJsonToBeWithFalse() {
        underTest = Display.builder("", "", "").announce(false).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json,
                is("{\"title\":\"\",\"description\":\"\",\"icon\":{\"item\":\"\"},\"announce_to_chat\":false}"));
    }

    @Test
    public void testDisplay_GIVEN_Hide_THEN_ExpectJsonToBeWithTrue() {
        underTest = Display.builder("", "", "").hidden(true).build();

        String json = gson.toJson(underTest.toJson());
        assertThat(json, is("{\"title\":\"\",\"description\":\"\",\"icon\":{\"item\":\"\"},\"hidden\":true}"));
    }
}