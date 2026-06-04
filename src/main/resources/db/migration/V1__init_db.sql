CREATE TABLE IF NOT EXISTS public."SlidedUrl"
(
    "SlicedUrl" text COLLATE pg_catalog."default" NOT NULL,
    "TrueUrl" text COLLATE pg_catalog."default" NOT NULL,
    "RefCount" bigint DEFAULT 0,
    CONSTRAINT "SlidedUrl_pkey" PRIMARY KEY ("SlicedUrl")
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."SlidedUrl"
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public."Users"
(
    "Username" text COLLATE pg_catalog."default" NOT NULL,
    "Passwort" text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "Users_pkey" PRIMARY KEY ("Username")
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."Users"
    OWNER to postgres;