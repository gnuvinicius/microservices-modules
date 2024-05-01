CREATE table if not exists public.tb_tasks (
    id uuid DEFAULT gen_random_uuid() not null,
    titulo varchar(100) not null,
    atualizado_em timestamp(6) null,
    criado_em timestamp(6) not null,
    status int2 not null default 1,
    tenant_id uuid NOT NULL,
    user_id uuid not null,
    projeto_id uuid not null,
    CONSTRAINT tasks_pkey PRIMARY KEY (id),
    CONSTRAINT fg_projeto_key FOREIGN KEY (projeto_id) REFERENCES public.tb_projetos(id),
    CONSTRAINT usuario_status_check CHECK (((status >= 0) AND (status <= 1)))
);