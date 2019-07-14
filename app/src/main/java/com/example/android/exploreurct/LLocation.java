package com.example.android.exploreurct;

/**
 * Created by Admin on 02/10/2018.
 */

public class LLocation {
    private String id,name,cat,lat,lang,url,disc,vf,distance;    // dates needed to be modified later on
        public LLocation() {}

        public LLocation(String id, String name, String cat, String lat, String lang ,String url , String disc ,String vf,String distance) {
            this.id = id;
            this.name= name;
            this.cat = cat;
            this.lat = lat;
            this.lang = lang;
            this.url = url;
            this.disc = disc;
            this.vf = vf;
            this.distance=distance;
        }

        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }
        public void setName(String title) {
            this.name = title;
        }

        public String getCat() {
            return cat;
        }
        public void setCat(String subject) {
            this.cat = subject;
        }

        public String getLat() {
            return lat;
        }
        public void setLat(String start_date) {
            this.lat = start_date;
        }

        public String getLang() {
            return lang;
        }
        public void setLang(String end_date) {
            this.lang = end_date;
        }

        public String getUrl() {
        return url;
    }
        public void setUrl(String end_date) {
        this.url = end_date;
    }

        public String getDisc() {
        return disc;
    }
        public void setDisc(String end_date) {
        this.disc= end_date;
    }

        public String getVf() {
        return vf;
    }
        public void setVf(String end_date) {
        this.vf= end_date;
    }

        public String getDistance() {
        return distance;
    }
        public void setDistance(String distance) {
        this.distance = distance;
    }
}
