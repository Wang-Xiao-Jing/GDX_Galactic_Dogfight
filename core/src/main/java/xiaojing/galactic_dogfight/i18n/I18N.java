package xiaojing.galactic_dogfight.i18n;

import club.someoneice.json.JSON;
import club.someoneice.json.node.MapNode;
import com.badlogic.gdx.Gdx;
import com.google.common.collect.ImmutableMap;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public final class I18N {
    public final ImmutableMap<String, String> language;

    public I18N(String languageName) {
        final String name = "lang/" + languageName + ".json";
        final ImmutableMap.Builder<String, String> builder = new ImmutableMap.Builder<>();

        var handler = Gdx.files.local(name);
        if (!handler.exists()) {
            loadDefaultLanguage(builder);
            this.language = builder.build();
            return;
        }

        try (InputStream stream = handler.read()) {
            MapNode node = JSON.json.parse(stream, false).asMapNodeOrEmpty();
            node.forEach(it -> builder.put(it.getKey(), it.getValue().toString()));

            this.language = builder.build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String get(String str) {
        return this.language.get(str);
    }

    public boolean contains(String str) {
        return this.language.containsKey(str);
    }

    private void loadDefaultLanguage(ImmutableMap.Builder<String, String> builder) {
        final String name = "lang" + File.pathSeparator + "en_us.json";

        var handler = Gdx.files.local(name);
        try (InputStream stream = handler.read()) {
            MapNode node = JSON.json.parse(stream, false).asMapNodeOrEmpty();
            node.forEach(it -> builder.put(it.getKey(), it.getValue().toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
