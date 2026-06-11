CREATE TABLE IF NOT EXISTS public."Users"
(
    "Username" text COLLATE pg_catalog."default" NOT NULL,
    "Password" text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "Users_pkey" PRIMARY KEY ("Username")
)

    TABLESPACE pg_default;

-- ALTER TABLE IF EXISTS public."Users"
--     OWNER to postgres;

CREATE TABLE IF NOT EXISTS public."UrlData"
(
    "SlicedUrl" text COLLATE pg_catalog."default" NOT NULL,
    "TrueUrl" text COLLATE pg_catalog."default" NOT NULL,
    "RefCount" bigint DEFAULT 0,
    "Username" text COLLATE pg_catalog."default" NOT NULL,
    "ExpDate" date NOT NULL,
    "IsActive" boolean DEFAULT true,
    CONSTRAINT "UrlData_pkey" PRIMARY KEY ("SlicedUrl"),
    CONSTRAINT "User_fkey" FOREIGN KEY("Username") REFERENCES "Users" ("Username")

    )

    TABLESPACE pg_default;

-- ALTER TABLE IF EXISTS public."UrlData"
--     OWNER to postgres;