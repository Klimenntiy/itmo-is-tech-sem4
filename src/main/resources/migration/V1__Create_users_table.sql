CREATE TYPE public.gender AS ENUM ('MALE', 'FEMALE', 'OTHER');
CREATE TYPE public.hair_color AS ENUM ('BLACK', 'BROWN', 'BLONDE', 'RED', 'GRAY', 'WHITE', 'OTHER');

CREATE TABLE public.users (
                              id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                              login VARCHAR(255) NOT NULL UNIQUE,
                              name VARCHAR(255) NOT NULL,
                              age INT NOT NULL,
                              gender public.gender NOT NULL,
                              hair_color public.hair_color NOT NULL
);

