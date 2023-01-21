-- Table: public.SD_GUEST_SCORE

-- DROP TABLE IF EXISTS public."SD_GUEST_SCORE";

CREATE TABLE IF NOT EXISTS public."SD_GUEST_SCORE"
(
    "NAME" text COLLATE pg_catalog."default" NOT NULL,
    "SCORE" numeric,
    "WINS" integer,
    "ADR" integer,
    "RANK" text COLLATE pg_catalog."default",
    CONSTRAINT "SD_GUEST_SCORE_pkey" PRIMARY KEY ("NAME")
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."SD_GUEST_SCORE"
    OWNER to postgres;

-- Table: public.SD_SCORE

-- DROP TABLE IF EXISTS public."SD_SCORE";

CREATE TABLE IF NOT EXISTS public."SD_SCORE"
(
    "NAME" text COLLATE pg_catalog."default" NOT NULL,
    "SCORE" numeric,
    "WINS" integer,
    "ADR" integer,
    "RANK" text COLLATE pg_catalog."default",
    CONSTRAINT "SD_SCORE_pkey" PRIMARY KEY ("NAME")
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."SD_SCORE"
    OWNER to postgres;