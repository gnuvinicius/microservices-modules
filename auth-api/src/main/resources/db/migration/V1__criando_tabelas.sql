-- public.tenant definition
-- Drop table
-- DROP TABLE public.tenant;
CREATE TABLE public.tenant (
    id uuid NOT NULL,
    atualizado_em timestamp(6) NULL,
    cnpj varchar(255) NULL,
    criado_em timestamp(6) NULL,
    email varchar(255) NULL,
    endereco varchar(255) NULL,
    nome varchar(255) NULL,
    status int2 NULL,
    CONSTRAINT tenant_pkey PRIMARY KEY (id),
    CONSTRAINT tenant_status_check CHECK (
        (
            (status >= 0)
            AND (status <= 1)
        )
    ),
    CONSTRAINT uk_k1oumevyg0yooai1tgkjlvare UNIQUE (cnpj)
);

-- public.usuario definition
-- Drop table
-- DROP TABLE public.usuario;
CREATE TABLE public.usuario (
    id uuid NOT NULL,
    atualizado_em timestamp(6) NULL,
    criado_em timestamp(6) NULL,
    status int2 NULL,
    email varchar(255) NOT NULL,
    nome varchar(255) NOT NULL,
    "password" varchar(255) NOT NULL,
    primeiro_acesso bool NULL,
    token_refresh_password varchar(255) NULL,
    token_refresh_password_valid bool NOT NULL,
    ultimo_acesso timestamp(6) NULL,
    tenant_id uuid NOT NULL,
    CONSTRAINT uk_5171l57faosmj8myawaucatdw UNIQUE (email),
    CONSTRAINT usuario_pkey PRIMARY KEY (id),
    CONSTRAINT usuario_status_check CHECK (
        (
            (status >= 0)
            AND (status <= 1)
        )
    )
);

-- public.usuario foreign keys
ALTER TABLE
    public.usuario
ADD
    CONSTRAINT fka10giac3ef9545ra7eyhmn4q1 FOREIGN KEY (tenant_id) REFERENCES public.tenant(id);

-- public."role" definition
-- Drop table
-- DROP TABLE public."role";
CREATE TABLE public."role" (
    role_id uuid NOT NULL,
    role_name varchar(255) NOT NULL,
    CONSTRAINT role_pkey PRIMARY KEY (role_id),
    CONSTRAINT uk_iubw515ff0ugtm28p8g3myt0h UNIQUE (role_name)
);

-- public.user_role definition
-- Drop table
-- DROP TABLE public.user_role;
CREATE TABLE public.user_role (
    user_id uuid NOT NULL,
    role_id uuid NOT NULL,
    CONSTRAINT user_role_pkey PRIMARY KEY (user_id, role_id)
);

-- public.user_role foreign keys
ALTER TABLE
    public.user_role
ADD
    CONSTRAINT fka68196081fvovjhkek5m97n3y FOREIGN KEY (role_id) REFERENCES public."role"(role_id);

ALTER TABLE
    public.user_role
ADD
    CONSTRAINT fkn3vbvm68n43dvr2tv0xljh018 FOREIGN KEY (user_id) REFERENCES public.usuario(id);

INSERT INTO
    "role" (role_id, role_name)
VALUES
    (
        '0d39a2f4-07be-4d72-ac7a-9c6f3b6846ce',
        'ROLE_ROOT'
    );

INSERT INTO
    "role" (role_id, role_name)
VALUES
    (
        '64e4b21e-166c-4068-b1ea-4fc1f9d3c2c7',
        'ROLE_ADMIN'
    );

INSERT INTO
    "role" (role_id, role_name)
VALUES
    (
        '7de7ec5c-74ad-4dcd-a4ba-7b72b3d8c83f',
        'ROLE_USER'
    );