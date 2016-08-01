package com.logicmaster63.mechanical_expansion;

import org.apache.commons.io.IOUtils;

import java.io.*;

public class Config {

        public static String copyConfigFromJar(String fileName, boolean replaceIfExists) throws IOException {

            if(replaceIfExists || !getConfigFile(fileName).exists()) {
                IOUtils.copy(Config.class.getResourceAsStream(getConfigResourcePath(fileName)), new FileOutputStream(getConfigFile(fileName)));
            }
            return IOUtils.toString(new FileReader(getConfigFile(fileName)));
        }

        public static String readConfigFile(String fileName) throws IOException {
            return IOUtils.toString(new FileReader(getConfigFile(fileName)));
        }

        public static String readConfigFileFromClassPath(String fileName) throws IOException {
            return readFileFromClassPath(getConfigResourcePath(fileName));
        }

        public static String getConfigResourcePath(String name) {
            return "/assets/MechanicalExpansion/config/" + name;
        }

        public static File getConfigFile(String name) {
            return new File(Reference.CONFIG_DIRECTORY, name);
        }

        public static String readFileFromClassPath(String fileName) throws IOException {
            InputStream in = Config.class.getResourceAsStream(fileName);
            if(in == null) {
                throw new IOException("Could find resource " + fileName + " in classpath. ");
            }
            return IOUtils.toString(in);
        }

    public static void writeTextFileToConfig(String fileName, String contents) throws IOException {
        writeTextFile(getConfigResourcePath(fileName), contents);
    }

    public static void writeTextFile(String fileName, String contents) throws IOException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName, false));
            writer.write(contents);
        } finally {
        IOUtils.closeQuietly(writer);
        }
    }
}
