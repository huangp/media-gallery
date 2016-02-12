package com.github.huangp.media.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

/**
 * @author Patrick Huang <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
// TODO add unknown field handler
@JsonIgnoreProperties(ignoreUnknown = true)
public class EXIF {
    @JsonProperty private String Model; //  "Nexus 4"
    @JsonProperty private int ImageWidth; //  2048
    @JsonProperty private int YCbCrPositioning; //  1
    @JsonProperty private int ResolutionUnit; //  2
    @JsonProperty private int ImageHeight; //  1536
    @JsonProperty private int YResolution; //  72
    @JsonProperty private int Orientation; //  1
    @JsonProperty private int XResolution; //  72
    @JsonProperty private String Make; //  "LGE"
    @JsonProperty private String GPSLongitude; //  153.02765555555555
    @JsonProperty private String GPSLatitudeRef; //  "S"
    @JsonProperty private String GPSDateStamp; //  "2015:03:29"
    @JsonProperty private List<Integer> GPSTimeStamp; //  [
//    6
//            52
//            1
//            ]
    @JsonProperty private String GPSLongitudeRef; //  "E"
    @JsonProperty private String GPSLatitude; //  -27.485783333333334
    @JsonProperty private int ColorSpace; //  1
    @JsonProperty private long CreateDate; //  1427647925
    @JsonProperty private String FNumber; //  2.65
    @JsonProperty private String FocalLength; //  4.6
    @JsonProperty private String ApertureValue; //  2.65
    @JsonProperty private int WhiteBalance; //  1
    @JsonProperty private int ExifImageWidth; //  2048
    @JsonProperty private long SubSecTime; //  "802808"
    @JsonProperty private long DateTimeOriginal; //  1427647925
    @JsonProperty private long SubSecTimeDigitized; //  "802808"
    @JsonProperty private int ExifImageHeight; //  1536
    @JsonProperty private int Flash; //  45312
    @JsonProperty private int FocalLengthIn35mmFormat; //  34
    @JsonProperty private int ISO; //  1600
    @JsonProperty private long SubSecTimeOriginal; //  "802808"
    @JsonProperty private String ExposureTime; //  0.0625
    @JsonProperty private String InteropIndex; //  "R98"

    public static void main(String[] args) {
        String json = "\"Model\": \"Nexus 4\",\n" +
                "      \"ImageWidth\": 2048,\n" +
                "      \"YCbCrPositioning\": 1,\n" +
                "      \"ResolutionUnit\": 2,\n" +
                "      \"ImageHeight\": 1536,\n" +
                "      \"YResolution\": 72,\n" +
                "      \"Orientation\": 1,\n" +
                "      \"XResolution\": 72,\n" +
                "      \"Make\": \"LGE\",\n" +
                "      \"GPSLongitude\": 153.02765555555555,\n" +
                "      \"GPSLatitudeRef\": \"S\",\n" +
                "      \"GPSDateStamp\": \"2015:03:29\",\n" +
                "      \"GPSTimeStamp\": [\n" +
                "        6,\n" +
                "        52,\n" +
                "        1\n" +
                "      ],\n" +
                "      \"GPSLongitudeRef\": \"E\",\n" +
                "      \"GPSLatitude\": -27.485783333333334,\n" +
                "      \"ColorSpace\": 1,\n" +
                "      \"CreateDate\": 1427647925,\n" +
                "      \"FNumber\": 2.65,\n" +
                "      \"FocalLength\": 4.6,\n" +
                "      \"ApertureValue\": 2.65,\n" +
                "      \"WhiteBalance\": 1,\n" +
                "      \"ExifImageWidth\": 2048,\n" +
                "      \"SubSecTime\": \"802808\",\n" +
                "      \"DateTimeOriginal\": 1427647925,\n" +
                "      \"SubSecTimeDigitized\": \"802808\",\n" +
                "      \"ExifImageHeight\": 1536,\n" +
                "      \"Flash\": 45312,\n" +
                "      \"FocalLengthIn35mmFormat\": 34,\n" +
                "      \"ISO\": 1600,\n" +
                "      \"SubSecTimeOriginal\": \"802808\",\n" +
                "      \"ExposureTime\": 0.0625,\n" +
                "      \"InteropIndex\": \"R98\"";

        ArrayList<String> lines =
                Lists.newArrayList(Splitter.on(",\n").trimResults().split(json));
//        lines.stream().map(line -> line.)
//        System.out.println(lines);
        lines.forEach(line -> {
            int firstColon = line.indexOf(":");
            if (firstColon <= 0) {
                System.out.println(line);
                return;
            }
            String fieldName = line.substring(1, firstColon - 1);
            String fieldValue = line.length() > firstColon + 2 ? line.substring(firstColon + 1) : "";
            String type = "String";
            try {
                Integer.parseInt(stripQuote(fieldValue.trim()));
                type = "int";
            } catch (Exception e) {
            }
            System.out.printf("@JsonProperty private %s %s; // %s%n", type, fieldName,
                    fieldValue);

        });
    }

    private static String stripQuote(String value) {
        return value.substring(1, value.length() - 1);
    }
}
