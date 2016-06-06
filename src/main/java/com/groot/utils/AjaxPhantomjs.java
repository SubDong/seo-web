package com.groot.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AjaxPhantomjs {

    private static final Logger LOGGER = LoggerFactory.getLogger(AjaxPhantomjs.class);
    private static final String PHANTOMJS = "phantomjs";

    private String scriptPath = null;
    private int threadNum;

    public AjaxPhantomjs() {
        init();
    }

    private void init() {
        if (scriptPath == null) {
            synchronized (this) {
                if (scriptPath == null) {
                    try {
                        scriptPath = JavascriptHelper.getScriptPath().toFile().getAbsolutePath();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    protected String getPage(String url) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        String cmd = PHANTOMJS + " " + scriptPath + " " + url;
        Process process = runtime.exec(cmd);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(),"utf-8"))) {
            StringBuilder html = new StringBuilder();
            br.lines().forEach(html::append);
            return html.toString();
        }
    }

    /**
     * the AjaxDownloader is based on phantomjs
     */
    static final class JavascriptHelper {

        private static final String TMP_DIR = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator");

        private static final String JAVASCRIPT_PREFIX = "crawl";

        private static final String JAVASCRIPT_SUFFIX = ".js";

        private static final byte[] JAVASCRIPT_CONTENT = ("var system = require('system');\n" +
                "var url = system.args[1];\n" +
                "\n" +
                "var page = require('webpage').create();\n" +
                "page.settings.loadImages = false;\n" +
                "page.settings.userAgent = '';\n" +
                "page.settings.resourceTimeout = 5000;\n" +
                "\n" +
                "page.open(url, function (status) {\n" +
                "    if (status != 'success') {\n" +
                "        console.log(\"HTTP request failed!\");\n" +
                "    } else {\n" +
                "        console.log(page.content);\n" +
                "    }\n" +
                "\n" +
                "    page.close();\n" +
                "    phantom.exit();\n" +
                "});").getBytes();

        private static final Path JAVASCRIPT_PATH = Paths.get(TMP_DIR + JAVASCRIPT_PREFIX + JAVASCRIPT_SUFFIX);


        public static Path getScriptPath() throws IOException {
            if (Files.notExists(JAVASCRIPT_PATH))
                Files.write(JAVASCRIPT_PATH, JAVASCRIPT_CONTENT);
            return JAVASCRIPT_PATH;
        }

        public static boolean deleteScript(Path path) throws IOException {
            return Files.deleteIfExists(path);
        }

    }

}
