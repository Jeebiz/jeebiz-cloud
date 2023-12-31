package io.hiwepy.cloud.base.core;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GlobalCoordinates;
import org.springframework.data.redis.core.GeoTemplate;

import java.util.ArrayList;
import java.util.List;


public class CaculateDistanceTest {

    private static GeoTemplate geoTemplate = new GeoTemplate();

    public static void main(String[] args) {

        List<String> xxx = new ArrayList<>();
        xxx.add("2");
        xxx.add("23");
        xxx.add("323");
        xxx.add("232");
        xxx.add("ww");
        xxx.add("ew");
        xxx.add("fdf");

        System.out.println(xxx.subList(0, 7));

        double meterx = geoTemplate.getDistance(116.401394, 39.95676, 114.499574, 36.63014);
        System.out.println("原始坐标系计算结果：" + meterx + "米");

        GlobalCoordinates source = new GlobalCoordinates(39.95676, 116.401394);
        GlobalCoordinates target = new GlobalCoordinates(36.63014, 114.499574);

        double meter1 = geoTemplate.getDistance(source, target, Ellipsoid.Sphere);
        double meter2 = geoTemplate.getDistance(source, target, Ellipsoid.WGS84);

        System.out.println("Sphere坐标系计算结果：" + meter1 + "米");
        System.out.println("WGS84坐标系计算结果：" + meter2 + "米");

        // 坐标系计算结果：405858.3127090019米
        // Sphere坐标系计算结果：405404.16586678155米
        // WGS84坐标系计算结果：404982.95610057615米


    }


}
