package models


trait IDictionatyEntity{
  val id: Option[Int]
  val name: String
}

case class Region(id: Option[Int], name: String) extends IDictionatyEntity
case class PointType(id: Option[Int], name: String) extends IDictionatyEntity
case class TrustLevel(id: Option[Int], name: String) extends IDictionatyEntity
case class Category(id: Option[Int], name: String) extends IDictionatyEntity

case class Kadastr(id: Option[Int], num: Option[String], num2: Option[String], name: Option[String], l: Option[Double],
                   a: Option[Double], v: Option[Double], regionid: Option[Int], categoryid: Option[Int],
                   comment: Option[String])

case class Point(id: Option[Int], name: String, lat: BigDecimal, lon: BigDecimal, altitude: Option[BigDecimal],
                 precision: Option[BigDecimal], description: Option[String], pointtypeid: Int, trustlevelid: Int,
                 dataid: Option[Int])

/**
-- Table: public.kadastr

-- DROP TABLE public.kadastr;

CREATE TABLE public.kadastr
(
  id integer NOT NULL DEFAULT nextval('kadastr_id_seq'::regclass),
  num character varying(7),
  num2 character varying(10),
  name character varying(50),
  l double precision,
  a double precision,
  v double precision,
  regionid integer,
  categoryid integer,
  comment character varying(150),
  CONSTRAINT pk_kadastr PRIMARY KEY (id),
  CONSTRAINT fk_kadastr_categoryid FOREIGN KEY (categoryid)
      REFERENCES public.category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_kadastr_regionid FOREIGN KEY (regionid)
      REFERENCES public.region (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.kadastr
  OWNER TO postgres;

  -- Table: public.point

-- DROP TABLE public.point;

CREATE TABLE public.point
(
  id integer NOT NULL DEFAULT nextval('point_id_seq'::regclass),
  name character varying(14) NOT NULL,
  lat numeric(11,8) NOT NULL,
  lon numeric(11,8) NOT NULL,
  altitude numeric(7,2),
  "precision" numeric(3,1),
  description character varying(50),
  pointtypeid integer NOT NULL,
  trustlevelid integer NOT NULL,
  dataid integer,
  CONSTRAINT pk_point PRIMARY KEY (id),
  CONSTRAINT fk_point_pointtypeid FOREIGN KEY (pointtypeid)
      REFERENCES public.pointtype (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_point_trustlevelid FOREIGN KEY (trustlevelid)
      REFERENCES public.trustlevel (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.point
  OWNER TO postgres;


  -- View: public.vpointsexport

-- DROP VIEW public.vpointsexport;

CREATE OR REPLACE VIEW public.vpointsexport AS
 SELECT p.id,
    p.name,
    p.lat,
    p.lon,
    p.altitude,
        CASE
            WHEN p.description IS NULL AND p.dataid IS NOT NULL THEN ((((((((((((((((((((k.num::text ||
            CASE
                WHEN k.num2 IS NOT NULL THEN ('('::text || k.num2::text) || ') '::text
                ELSE ' '::text
            END) || COALESCE(k.name, '-'::character varying)::text) || ' '::text) || 'L'::text) ||
            CASE
                WHEN k.l IS NOT NULL THEN k.l::character varying(10)
                ELSE '-'::character varying
            END::text) || '/'::text) || 'A'::text) ||
            CASE
                WHEN k.a IS NOT NULL THEN k.a::character varying(10)
                ELSE '-'::character varying
            END::text) || '/'::text) || 'V'::text) ||
            CASE
                WHEN k.v IS NOT NULL THEN k.v::character varying(10)
                ELSE '-'::character varying
            END::text) || '/'::text) || 'P'::text) ||
            CASE
                WHEN p."precision" IS NOT NULL THEN p."precision"::character varying(10)
                ELSE '-'::character varying
            END::text) || '/'::text) || 'T'::text) ||
            CASE
                WHEN p.trustlevelid IS NOT NULL THEN p.trustlevelid::character varying(10)
                ELSE '-'::character varying
            END::text) || '/'::text) || 'C'::text) ||
            CASE
                WHEN k.categoryid IS NOT NULL THEN k.categoryid::character varying(10)
                ELSE '-'::character varying
            END::text)::character varying
            ELSE p.description
        END AS description,
    p.trustlevelid
   FROM point p
     LEFT JOIN kadastr k ON p.dataid = k.id;

ALTER TABLE public.vpointsexport
  OWNER TO postgres;


  */