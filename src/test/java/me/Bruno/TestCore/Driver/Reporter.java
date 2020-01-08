package me.Bruno.TestCore.Driver;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reporter {

    private static File report;
    private static BufferedWriter writer;
    public static int steps;
    public static boolean alternateColor;
    private static String testName;

    @BeforeClass
    public static void setUp() throws IOException {
        if (!new File(TestBase.deployPath).exists()) {
            new File(TestBase.deployPath).mkdirs();
        }
        String filename = "Deploy_" + new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date()) + ".html";
        String junitReportFile = TestBase.deployPath + filename;
        String date = new SimpleDateFormat("dd/MM/yyyy [HH:mm:ss]").format(new Date());
        report = new File(junitReportFile);
        writer = new BufferedWriter(new FileWriter(report, true));
        writeReport("<html><head><style>* {margin: 0; padding: 0;}#titulo {padding: 15px; background-color: #fdcb6e; text-align: center;}#escuro {color: #2d3436; background-color:#95a5a6;}#claro {color: #2d3436; background-color:#7f8c8d;}#pass {color: #55efc4}#fail {color: #d63031}</style></head><body>");
        writeReport("<div id='titulo'><h1>#test_name# - " + date + "   Steps: <strong>#steps#</strong></h1></div>");
    }

    @AfterClass
    public static void tearDown() throws IOException {
        writeReport("</body></html>");
        writer.close();
        replaceInFile("#test_name#", testName);
        replaceInFile("#steps#", "" +steps);
        System.out.println(report.toURI().getPath().replaceFirst("/", ""));
    }

    @Rule
    public TestRule watchman = new TestWatcher() {

        @Override
        public Statement apply(Statement base, Description description) {
            return super.apply(base, description);
        }

        @Override
        protected void succeeded(Description description) {
            try {
                if (testName != description.getClassName()) {
                    testName = description.getClassName();
                }
                writeReport(description.getDisplayName() + " success! <br>");
            } catch (Exception e1) {
                System.out.println(e1.getMessage());
            }
        }

        @Override
        protected void failed(Throwable e, Description description) {
            try {
                if (testName != description.getClassName()) {
                    testName = description.getClassName();
                }
                writeReport(description.getDisplayName() + " "+ e.getClass().getSimpleName());
            } catch (Exception e2) {
                System.out.println(e2.getMessage());
            }
        }
    };

    public static void writeReport(String msg) {
        try {
            writer.write(msg);
        } catch (IOException e) {

        }
    }

    private static void replaceInFile(String var, String value) {
        try {
            Path path = Paths.get(report.toURI());
            Charset charset = StandardCharsets.UTF_8;

            String content = new String(Files.readAllBytes(path), charset);
            content = content.replaceAll(var, value);
            Files.write(path, content.getBytes(charset));
        } catch (IOException e) {

        }
    }

}
