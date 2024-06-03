package dev.dexuby.minecraftplugin.asset;

public final class Assets {

    public static final Asset GIT_IGNORE = new Asset(".gitignore", ".gitignore");
    public static final Asset MAVEN_POM = new Asset("pom.xml", "pom.xml");
    public static final Asset PLUGIN = new Asset("src/main/java/resources/plugin.yml", "plugin.yml");
    public static final Asset MAIN_CLASS = new Asset("src/main/java/%GROUP_ID%/%ARTIFACT_ID_SAFE%/%PROJECT_NAME%.java", "Entry.java");

    /**
     * All default assets.
     */

    public static Asset[] DEFAULT_ASSETS = new Asset[]{
            GIT_IGNORE,
            MAVEN_POM,
            PLUGIN,
            MAIN_CLASS
    };

    private Assets() {

        throw new UnsupportedOperationException();

    }

}
