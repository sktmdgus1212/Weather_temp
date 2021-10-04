package com.example.weather;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Data;

/**
 * OpenWeath API - 현재 날씨 데이터
 * @author hgko
 */
@Data
public class OpenWeather {

    public List<Weather> weather;

    /** 내부 매개 변수 */
    /** 내부 매개 변수 */
    public String base;

    public Main main;

    public Wind wind;

    public Clouds clouds;

    public Rain rain;

    public Snow snow;

    public Coord coord;

    public Sys sys;
    /** 가시성 */
    public int visibility;

    /** 데이터 계산 시간, 유닉스, UTC */
    public long dt;

    /** UTC에서 초 단위로 이동 */
    public int timezone;

    /** 도시 ID */
    public long id;

    /** 도시 이름 */
    public String name;

    /** 내부 매개 변수 */
    public int cod;


    @Data
    public static class Coord {

        /** 위도 */
        private float lon;

        /** 경도 */
        private float lat;
    }

    @Data
    public static class Weather {

        /** 기상 조건 ID */
        int id;

        /** 날씨 매개 변수 그룹 (비, 눈, 극한 등) */
        String main;

        /** 그룹 내 날씨 조건 */
        String description;

        /** 날씨 아이콘 ID */
        String icon;
    }

    @Data
    public static class Main {

        /** 온도. 단위 기본값 : 켈빈, 미터법 : 섭씨, 임페리얼 : 화씨 */
        public float temp;

        /** 온도. 단위 기본값 : 켈빈, 미터법 : 섭씨, 임페리얼 : 화씨 */
        public float feels_like;

        /** 현재 최저 온도.(대규모 대도시 및 도시 지역 내) */
        public float temp_min;

        /** 현재 최대 온도.(대규모 대도시 및 도시 지역 내)*/
        public float temp_max;

        /** 대기압 (해수면, 해수면 또는 grnd_level 데이터가 없는 경우), hPa */
        public int pressure;

        /** 습도, % */
        public float humidity;

        /** 해수면의 대기압, hPa */
        public float sea_level;

        /** 지면에서의 대기압, hPa */
        public float grnd_level;
    }

    @Data
    public static class Wind {

        /** 바람의 속도. 단위 기본값 : meter/sec, 미터법 : meter/sec, 임페리얼 : miles/hour */
        public float speed;

        /** 풍향,도 (기상) */
        public int deg;

        /**  바람 돌풍. 단위 기본값 : meter/sec, 미터법 : meter/sec, 임페리얼 : miles/hour */
        public float gust;
    }

    @Data
    public static class Clouds {

        /** 흐림, % */
        public int all;
    }

    @Data
    public static class Rain {

        /** 지난 1 시간 동안의 강우량, mm */
        @JsonProperty("1h")
        public float rain1h;

        /** 지난 3 시간 동안의 강우량, mm */
        @JsonProperty("3h")
        public float rain3h;
    }

    @Data
    public static class Snow {

        /** 지난 1 시간 동안의 눈량, mm */
        @JsonProperty("1h")
        public float snow1h;

        /** 지난 3 시간 동안의 눈량, mm */
        @JsonProperty("3h")
        public float snow3h;
    }

    @Data
    public static class Sys {

        public int type;

        public int id;

        /** 국가 코드 (GB, JP 등) */
        public String country;

        /** 일출 시간, 유닉스, UTC */
        public long sunrise;

        /** 일몰 시간, 유닉스, UTC */
        public long sunset;
    }
}


