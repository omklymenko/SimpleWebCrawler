package com.agileengine.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SimpleWebCrawler {

    private static String CHARSET_NAME = "utf8";

    public static void main(String[] args) {

        if(args.length < 3){
            System.out.println("Not enough arguments");
            return;
        }

        String resourcePathOrigin = args[0];
        String resourcePathDiff = args[1];
        String targetElementId = args[2];

        Element buttonOpt = findElementById(new File(resourcePathOrigin), targetElementId).orElse(null);

        if (buttonOpt != null) {
            System.out.println(findSameElement(new File(resourcePathDiff), buttonOpt));
        }
    }

    private static Optional<Element> findElementById(File htmlFile, String targetElementId) {
        try {
            Document doc = Jsoup.parse(
                    htmlFile,
                    CHARSET_NAME,
                    htmlFile.getAbsolutePath());
            return Optional.of(doc.getElementById(targetElementId));
        } catch (IOException e) {
            System.out.println("Error reading document");
            return Optional.empty();
        }
    }

    private static String findSameElement(File htmlFile, Element element) {
        Document doc;

        Map<String, String> attributes = element.attributes().asList()
                .stream().collect(Collectors.toMap(Attribute::getKey, Attribute::getValue));

        try {
            doc = Jsoup.parse(
                    htmlFile,
                    CHARSET_NAME,
                    htmlFile.getAbsolutePath());
        } catch (IOException e) {
            return "Error reading document";
        }

        List<Element> elementList = doc.body().getElementsByTag(element.tagName()).stream().filter(e -> {
            return e.attributes().get("class").equals(attributes.get("class")) ||
                    e.attributes().get("href").equals(attributes.get("href")) ||
                    e.attributes().get("onClick").equals(attributes.get("onClick")) ||
                    e.text().equals(element.text());
        }).collect(Collectors.toList());

        if (elementList.size() == 1) {
            return elementList.get(0).cssSelector();
        } else if (elementList.size() > 1) {
            Map<Element, Integer> elementCountMap = elementList.stream()
                    .collect(Collectors.toMap(e -> e, e -> attributesMatchCount(e, attributes, element.text())));
            return elementCountMap.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey().cssSelector();
        }
        return "Element not found";
    }

    private static int attributesMatchCount(Element e, Map<String, String> attributes, String elementText) {
        int count = 0;
        if (e.attributes().get("class").equals(attributes.get("class"))) {
            count++;
        }
        if (e.attributes().get("href").equals(attributes.get("href"))) {
            count++;
        }
        if (e.attributes().get("onClick").equals(attributes.get("onClick"))) {
            count++;
        }
        if (e.text().equals(elementText)) {
            count++;
        }
        return count;
    }
}
