CREATE TABLE IF NOT EXISTS public."SD_GUEST_SCORE"
(
    "NAME" text COLLATE pg_catalog."default" NOT NULL,
    "SCORE" numeric,
    "WINS" integer,
    "ADR" integer,
    CONSTRAINT "SD_GUEST_SCORE_pkey" PRIMARY KEY ("NAME")
)

CREATE TABLE IF NOT EXISTS public."SD_SCORE"
(
    "NAME" text COLLATE pg_catalog."default" NOT NULL,
    "SCORE" numeric,
    "WINS" integer,
    "ADR" integer,
    CONSTRAINT "SD_SCORE_pkey" PRIMARY KEY ("NAME")
)